package ali;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class Main02 {

    private static AtomicInteger state = new AtomicInteger(1);
    private static ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) {

        // 打印3的倍数
        Thread A = new Thread(() ->{
            while (state.get() <= 100) {
                lock.lock();
                // 做二次检查，防止state.get()之后，没获得到锁的情况
                if (state.get() > 100)
                    break;
                if (state.get() % 3 == 0) {
                    System.out.println("打印3的倍数--->" + state.getAndIncrement());
                }
                lock.unlock();
            }
        });
        // 打印5的倍数
        Thread B = new Thread(() ->{
            while (state.get() <= 100) {
                lock.lock();
                // 做二次检查，防止state.get()之后，没获得到锁的情况
                if (state.get() > 100)
                    break;
                // 对于即是3的倍数 又是5的倍数的情况，让A线程处理
                if (state.get() % 3 != 0 && state.get() % 5 == 0) {
                    System.out.println("打印5的倍数--->" + state.getAndIncrement());
                }
                lock.unlock();
            }
        });
        // 打印其他数
        Thread C = new Thread(() ->{
            while (state.get() <= 100) {
                lock.lock();
                // 做二次检查，防止state.get()之后，没获得到锁的情况
                if (state.get() > 100)
                    break;
                if (state.get() % 3 != 0 && state.get() % 5 != 0) {
                    System.out.println("打印其他数--->" + state.getAndIncrement());
                }
                lock.unlock();
            }
        });

        A.start();
        B.start();
        C.start();

    }
}
