package com.jvm.gc;

/**
 * 这里o1 与 o2发生了循环引用，如果是引用计数法，则还需要额外的处理才能保证这两个被成功回收
 * java中没用使用引用计数器，在下面的代码中，这两个对象被成功回收
 */
public class ReferenceCountingGC {

    public Object instance = null;

    private static final int _1MB = 1024*1024;

    // 声明这个变量的目的是占用一些内容，方便观察GC日志
    private byte[] bigSize = new byte[2*_1MB];

    public static void main(String[] args) {
        ReferenceCountingGC o1 = new ReferenceCountingGC();
        ReferenceCountingGC o2 = new ReferenceCountingGC();

        o1.instance = o2;
        o2.instance = o1;

        o1 = null;
        o2 = null;

        System.gc();
    }
}
