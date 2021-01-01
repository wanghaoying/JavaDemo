package com.jvm.gc;

/**
 * VM Args: -XX:+PrintGCDetails
 * 下面这段代码演示了对象在gc时，可以自我拯救的过程，主要是利用对象在被回收的时候会被执行finalize方法
 *    对象可以自我拯救，但是这种拯救方式只能成功一次，因为一个对象的finalize方法最多只能被jvm执行一次
 *    并且必须重写finalize方法，如果不重写，默认不会执行
 *
 * 这种拯救对象的方式，极度不推荐！！
 */
public class FinalizeEscapeGC {

    public static FinalizeEscapeGC SAVE_HOOK = null;

    public void isAlive(){
        System.out.println("yes,I'm alive");
    }

    @Override
    protected void finalize() throws Throwable{
        super.finalize();
        System.out.println("finalize method executed");
        FinalizeEscapeGC.SAVE_HOOK = this;
    }

    public static void main(String[] args) throws Throwable {
//        SAVE_HOOK = new FinalizeEscapeGC();
//
//        // 对象第一次拯救自己
//        SAVE_HOOK = null;
//        System.gc();
//        Thread.sleep(500);
//        if(SAVE_HOOK != null){
//            SAVE_HOOK.isAlive();
//        }else {
//            System.out.println("No,I'm dead");
//        }
        Thread.sleep(1000000);
        // 下面这段代码与上面那段一样，但是这次却拯救失败
//        SAVE_HOOK = null;
//        System.gc();
//        Thread.sleep(500);
//        if(SAVE_HOOK != null){
//            SAVE_HOOK.isAlive();
//        }else {
//            System.out.println("No,I'm dead");
//        }
    }
}
