package com.queue;

/**
 * 一种基于数组实现的有限长度、可以进行数据搬移的顺序队列，不保证线程安全
 * 可以在enQueue和deQueue方法前加synchronized关键字来实现线程安全操作
 * 这是一种非阻塞式队列，当队列满或队列空时直接返回，不阻塞
 */
public class ArrayQueue {
    private Object[] items;
    private int head;
    private int tail;
    private int size;

    public ArrayQueue(int cap) {
        items = new Object[cap];
        size = cap;
    }

    // input data to queue
    public boolean enQueue(Object item){
        if(tail == size){
            if(head == 0){
                return false;
            }
            // 数据迁移
            for(int i = head; i < tail; i++){
                items[i-head] = items[i];
            }
            tail = tail - head;
            head = 0;
        }
        items[tail++] = item;
        return true;
    }

    // output data from queue
    public Object deQueue(){
        if(head == tail){
            return null;
        }
        return items[head++];
    }

    public static void main(String[] args) throws InterruptedException {
        ArrayQueue arrayQueue = new ArrayQueue(10);
        Object item;

        for (int i = 0; i < 10; i++) {
            arrayQueue.enQueue(i);
        }

        for (int i = 0; i < 5; i++) {
            System.out.println(arrayQueue.deQueue());
        }

        for (int i = 0; i < 10; i++) {
            arrayQueue.enQueue(String.valueOf(i));
        }

        while((item = arrayQueue.deQueue()) != null){
            System.out.println(item);
        }



    }
}
