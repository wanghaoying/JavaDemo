package com.queue;

/**
 * 循环队列是使用了一个数组来实现的，它可以不用做数据迁移，可以降低性能消耗
 * 循环队列的难点是：队空和队满条件的判断
 * 对于队满的判断有两种方式：
 *              1、((tail+1)%size) == head 为空，这个时候浪费掉一个存储空间
 *              2、增加一个变量，来标识是否队满或者为空，而不是通过head和tail的情况来判断
 */
public class CircularQueue {
    private Object[] items;
    private int head;
    private int tail;
    private int size;

    public CircularQueue(int cap){
        items = new Object[cap];
        size = cap;
    }
    /*
        下面为第一种实现方式，第二种只需要再加上一个标识队列中数组元素个数的标志count即可
     */
    public boolean enQueue(Object item){
        if(((tail+1)%size) == head){
            return false;
        }

        items[tail] = item;
        tail = (tail +1) % size;
        return true;
    }

    public Object deQueue(){
        if(head == tail){
            return null;
        }
        int res = head;
        head = (head+1) % size;
        return items[res];
    }

    public static void main(String[] args) {
        CircularQueue circularQueue = new CircularQueue(5);

        for(int i = 0; i < 5; i++){
            if(circularQueue.enQueue(i)){
                System.out.println("插入成功");
            }else {
                System.out.println("插入失败");
            }
        }

        for(int i = 0; i< 3; i++){
            System.out.println(circularQueue.deQueue());
        }

        for(int i = 0; i < 5; i++){
            if(circularQueue.enQueue(i)){
                System.out.println("插入成功");
            }else {
                System.out.println("插入失败");
            }
        }

        for(int i = 0; i< 5; i++){
            System.out.println(circularQueue.deQueue());
        }
    }
}
