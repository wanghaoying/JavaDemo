import java.util.BitSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

public class JavaTest {

//    static class Test{
//        static ObjHandler staticObj = new ObjHandler();
//        ObjHandler instanceObj = new ObjHandler();
//
//        void foo(){
//            ObjHandler localObj = new ObjHandler();
//            System.out.println("done");
//        }
//    }
//
//    private static class ObjHandler{ }
//
//    public static void main(String[] args) {
//        Test test = new JavaTest.Test();
//        test.foo();
//    }

    static int i = 0;
    public static void main(String[] args) throws InterruptedException {
//        ReentrantLock lock = new ReentrantLock();
//
//        new Thread(() ->{
//            for(int n = 0;n<100000;n++){
//                lock.lock();
//                try{
//                    i++;
//                }finally {
//                    lock.unlock();
//                }
//            }
//        }).start();
//
//        new Thread(() ->{
//            for(int n = 0;n<100000;n++){
//                lock.lock();
//                try{
//                    i++;
//                }finally {
//                    lock.unlock();
//                }
//            }
//        }).start();
//
//        Thread.sleep(1000);
//        System.out.println(i);

//        BitSet bitSet = new BitSet();
//
//        System.out.println(1L << 65);
//        bitSet.set(155028);
//        System.out.println(bitSet.get(155028));


//        System.out.println(0x7FFFFFFF);
//        System.out.println(Integer.MAX_VALUE);

        System.out.println("127.0.0.1".split("\\.").length);
    }
}