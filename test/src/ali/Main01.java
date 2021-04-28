package ali;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *  将三个cpu打满，三个线程本地测82%，待查找原因，4个线程可以上升到98%
 *  可能和cpu核数有关
 */
public class Main01 {
    public static void main(String[] args) {
        int processors = Runtime.getRuntime().availableProcessors();
        ExecutorService threadPool = Executors.newFixedThreadPool(3*processors);
        for (int i = 0; i < 3*processors; i++){
            threadPool.execute(new TestThread());
        }
//        System.out.println(Runtime.getRuntime().availableProcessors()); //4
    }
}

class TestThread extends Thread{

    @Override
    public void run() {
        while (true){

        }
    }
}



