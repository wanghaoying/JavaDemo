package nio.input;

import com.sun.net.httpserver.Headers;
import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Iterator;
import java.util.Optional;

public class InputTest {

    // 使用nio来读取文件，这个时候其实是没有起到 no-blocking
    @Test
    public void test01() throws IOException {
        FileChannel in = FileChannel.open(Paths.get("/Users/haowang/desktop/ebook/2020plan.txt"),
                StandardOpenOption.READ);

        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        while (in.read(byteBuffer) > 0){
            byteBuffer.flip();
            System.out.println(byteBuffer);
            byteBuffer.clear();
        }

        in.close();
    }

    // 网络nio, demo1:没有使用NIO中的selector模式，这个类似原先的BIO
    @Test
    public void test02() throws IOException {
        ServerSocketChannel server = ServerSocketChannel.open();
        server.bind(new InetSocketAddress(6688));

        SocketChannel client = server.accept();
        System.out.println("已经建立与客户端的连接了");
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        while (client.read(byteBuffer) > 0){
            byteBuffer.flip();
            for (byte b : byteBuffer.array()) {
                System.out.print((char) b);
            }
            byteBuffer.clear();
        }

        client.close();
        server.close();
    }

    @Test
    public void test03() throws IOException {
        ServerSocketChannel server = ServerSocketChannel.open();
        server.bind(new InetSocketAddress(7788));

        // 开启非阻塞模式
        server.configureBlocking(false);

        Selector selector = Selector.open();

        server.register(selector, SelectionKey.OP_ACCEPT);

        while (selector.select() > 0){
//            System.out.println("来了一个新的事件");

            // 获取返回的事件信息
            Iterator<SelectionKey> selectionKeyIterator = selector.selectedKeys().iterator();

            while (selectionKeyIterator.hasNext()){
                SelectionKey selectionKey = selectionKeyIterator.next();

                // 接收事件就绪
                if (selectionKey.isAcceptable()) {

                    // 8. 获取客户端的链接
                    SocketChannel client = server.accept();

                    // 8.1 切换成非阻塞状态
                    client.configureBlocking(false);

                    // 8.2 注册到选择器上-->拿到客户端的连接为了读取通道的数据(监听读就绪事件)
                    client.register(selector, SelectionKey.OP_READ);

                } else if (selectionKey.isReadable()) { // 读事件就绪

                    // 9. 获取当前选择器读就绪状态的通道
                    SocketChannel client = (SocketChannel) selectionKey.channel();

                    // 9.1读取数据
                    ByteBuffer buffer = ByteBuffer.allocate(1024);

                    while (client.read(buffer) > 0) {
                        // 在读之前都要切换成读模式
                        buffer.flip();

                        for (byte b : buffer.array()) {
                            System.out.print((char) b);
                        }

                        // 读完切换成写模式，能让管道继续读取文件的数据
                        buffer.clear();
                    }
                    client.register(selector, SelectionKey.OP_WRITE);
                }else if (selectionKey.isWritable()){
                    SocketChannel client = (SocketChannel) selectionKey.channel();

                    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

                    try{
                        FileChannel fileChannel = FileChannel.open(
                                Paths.get("2.txt"),StandardOpenOption.READ,
                                StandardOpenOption.WRITE);

                        while (fileChannel.read(byteBuffer)!= -1){
                            byteBuffer.flip();
                            client.write(byteBuffer);
                            byteBuffer.clear();
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }finally {
                        client.close();
                    }

                }

                selectionKeyIterator.remove();
            }
        }
    }


    @Test
    public void test04() throws IOException, InterruptedException {
        // 9.2得到文件通道，将客户端传递过来的图片写到本地项目下(写模式、没有则创建)
        FileChannel outChannel = FileChannel.open(Paths.get("2.txt"), StandardOpenOption.WRITE,StandardOpenOption.CREATE);


        // 1.获取通道
        ServerSocketChannel server = ServerSocketChannel.open();

        // 2.切换成非阻塞模式
        server.configureBlocking(false);

        // 3. 绑定连接
        server.bind(new InetSocketAddress(6688));

        // 4. 获取选择器
        Selector selector = Selector.open();

        // 4.1将通道注册到选择器上，指定接收“监听通道”事件
        server.register(selector, SelectionKey.OP_ACCEPT);

        // 5. 轮训地获取选择器上已“就绪”的事件--->只要select()>0，说明已就绪
        while (selector.select() > 0) {
            // 6. 获取当前选择器所有注册的“选择键”(已就绪的监听事件)
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();

            // 7. 获取已“就绪”的事件，(不同的事件做不同的事)
            while (iterator.hasNext()) {

                SelectionKey selectionKey = iterator.next();

                // 接收事件就绪
                if (selectionKey.isAcceptable()) {

                    // 8. 获取客户端的链接
                    SocketChannel client = server.accept();

                    // 8.1 切换成非阻塞状态
                    client.configureBlocking(false);

                    // 8.2 注册到选择器上-->拿到客户端的连接为了读取通道的数据(监听读就绪事件)
                    client.register(selector, SelectionKey.OP_READ);

                } else if (selectionKey.isReadable()) { // 读事件就绪

                    // 9. 获取当前选择器读就绪状态的通道
                    SocketChannel client = (SocketChannel) selectionKey.channel();

                    // 9.1读取数据
                    ByteBuffer buffer = ByteBuffer.allocate(1024);


                    while (client.read(buffer) > 0) {
                        // 在读之前都要切换成读模式
                        buffer.flip();

                        outChannel.write(buffer);

                        // 读完切换成写模式，能让管道继续读取文件的数据
                        buffer.clear();
                    }

                    client.register(selector,SelectionKey.OP_WRITE);

                }else if (selectionKey.isWritable()){
                    SocketChannel client = (SocketChannel) selectionKey.channel();

                    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

                    Thread.sleep(100000);

                    FileChannel fileChannel = FileChannel.open(
                            Paths.get("1.txt"),StandardOpenOption.READ);

                    while (fileChannel.read(byteBuffer)!= -1){
                        byteBuffer.flip();
                        client.write(byteBuffer);
                        byteBuffer.clear();
                    }
                    System.out.println("发送成功");
                    client.close();
                }
                // 10. 取消选择键(已经处理过的事件，就应该取消掉了)
                iterator.remove();
            }
        }
    }
}
