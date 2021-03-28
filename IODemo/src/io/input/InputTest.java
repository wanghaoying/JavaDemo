package io.input;

import org.junit.Test;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeMap;

public class InputTest {

    // 输入流：主要分为两种输入流，一种是字节流，另一种是字符流
    // 字节流：主要是由InputStream下的子类实现的
    // 字符流：主要是由Reader下的子类实现的

    @Test
    public void fileInputStreamTest(){
        // 要保证在不使用流的时候，要关闭掉这个流
        FileInputStream in = null;
        try {
            try {
                in = new FileInputStream(new File(
                        "/Users/haowang/desktop/ebook/2020pla.txt"));
            }catch (FileNotFoundException e){
                System.out.println("文件没有找到");
            }

            if (in != null){
                int num = 0;
                while ((in.read()) != -1){
                    num++;
                }
                System.out.println("读取文件中的字节数为："+ num);
            }

        } catch (IOException e) {
            System.out.println("文件读取发生异常");
        }finally {
            if (in != null){
                try {
                    in.close();
                } catch (IOException e) {
                    System.out.println("文件关闭异常");
                }
            }
        }
    }

    // 以字符的方式从文件中读入
    @Test
    public void FileReaderTest(){
        FileReader in = null;
        try {
            in = new FileReader(new File(
                    "/Users/haowang/desktop/ebook/2020plan.txt"));
        } catch (FileNotFoundException e) {
            System.out.println("这个文件没有找到");
        }

        try {
            if (in != null){
                int b;
                int num = 0;
                while ((b = in.read()) != -1){
                    System.out.print((char) b);
                    num++;
                }
                System.out.println("从这个文件中读出的字符数为："+num);
            }
        }catch (IOException e){
            System.out.println("文件读取发生异常");
        }finally {
            if (in != null){
                try {
                    in.close();
                } catch (IOException e) {
                    System.out.println("文件关闭发生异常");
                }
            }
        }
    }

    // 在上面的这些方法中，我们为了能够对资源进行关闭，嵌套了很多层的try-catch-finally语句
    // 当时上面还是存在一种问题的，当我们的关闭in.close代码也出现异常的时候，代码就会抛出
    // finally处发生的异常，而catch处的异常就会被抑制，然而我们可能更希望得到的是catch处的异常，
    // 就需要按照下面的方式进行处理
    @Test
    public void tryCatchTest() throws Exception {
        FileReader in = null;
        Exception ex = null;
        try {
            // 读取输入流
            try {
                in = new FileReader(new File("/Users/haowang/desktop/ebook/2020plan.txt"));
            }catch (FileNotFoundException e){
                System.out.println("发生了文件未找到异常");
                ex = e;
                throw e;
            }
            // 业务代码
            try {
                if (in != null) {
                    int b;
                    int num = 0;
                    while ((b = in.read()) != -1) {
                        System.out.print((char) b);
                        num++;
                    }
                    System.out.println("从这个文件中读出的字符数为：" + num);
                }
            }catch (IOException e){
                System.out.println("在文件读取的过程中，发生了读取IO异常");
                ex = e;
                throw e;
            }
        }finally {
            if (in != null){
                try {
                    in.close();
                }catch (IOException e){
                    System.out.println("发生了文件关闭异常");
                    if (ex == null)
                        throw e;
                }
            }
        }
    }


    // 上面的关闭流程十分的复杂，在1.7之后，只要资源实现了Closeable或者AutoCloseable接口
    // 就可以使用下面的方式来进行资源的读取与关闭,而且也不会有业务代码中的异常被抑制的情况
    @Test
    public void tryResource(){
        try(FileReader in = new FileReader(new File("/Users/haowang/desktop/ebook/2020plan.txt"))){

            int b;
            int num = 0;
            while ((b = in.read()) != -1) {
                System.out.print((char) b);
                num++;
            }
            System.out.println("从这个文件中读出的字符数为：" + num);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // 这里只能在main方法中写，不能在测试方法中编写类似的代码
    public static void main(String[] args) {
        Scanner scanner = null;

        // 使用一个InputStreamReader来接受System.in的输入，同时传入Scanner中
        scanner = new Scanner(System.in);
        // 使用一个arraylist来暂存输入的数据
        ArrayList<String> strings = new ArrayList<>();
        String line = null;
        // 接收数据并放入string数组中
        while (scanner.hasNextLine()){
            line = scanner.nextLine();
            if ("over".equals(line))
                break;
            strings.add(line);
        }

        System.out.println(strings);
        scanner.close();
    }
}
