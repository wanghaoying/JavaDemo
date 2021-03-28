package com.Thread.base;

public class AbcTask {

    private static final int COUNT = 10;

    public static void main(String[] args) throws InterruptedException {
        Object a = new Object();
        Object b = new Object();
        Object c = new Object();
        new AbcThread(a, b, "A").start();
        new AbcThread(b, c, "B").start();
        Thread.sleep(1);
        new AbcThread(c, a, "C").start();
    }

    static class AbcThread extends Thread {
        private final Object parent;
        private final Object child;
        private final String value;

        public AbcThread(Object parent, Object child, String value) {
            this.parent = parent;
            this.child = child;
            this.value = value;
        }

        @Override
        public void run() {
            for (int i = 0; i < COUNT; i++) {
                synchronized (parent) {
                    synchronized (child) {
                        System.out.println(this.getName() + ":" + value);
                        child.notifyAll();
                    }
                    try {
                        parent.wait(1000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

}