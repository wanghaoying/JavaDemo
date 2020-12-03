package com.jvm.method;


import java.util.HashSet;
import java.util.Set;

/**
 * VM Args: -Xmx6m
 * 从jdk7开始，字符串常量池就被放在java堆中，所以字符串常量池可能会报的OOM错误，受制于java堆的大小
 * 理论上来说，下面这段代码有两种原因会导致出现OOM异常：
 *  1、由于向set中存储内容时，堆空间不足导致的OOM
 *  2、由于向字符串常量池中添加新的字符串的时候导致的OOM
 */
public class RuntimeConstantPoolOOM{

    public static void main(String[] args) {
        // 声明一个set保持对字符串常量池中内容的引用，避免被GC回收
        Set<String> set = new HashSet<>();

        short i = 0;
        while(true){
            set.add(String.valueOf(i++).intern());
        }
    }
}

/**
 * 从jdk7开始，字符串常量池就放到了java堆中，String::intern()方法的功能是：查看这个字符串是否在
 * 字符串常量池中，如果在则返回这个字符串的引用，如果不在，则把他的引用放在字符串常量池中
 */
//public class RuntimeConstantPoolOOM {
//
//    public static void main(String[] args) {
//        String str1 = new StringBuilder("计算机").append("软件").toString();
//        System.out.println(str1.intern() == str1);
//
//        String str2 = new StringBuilder("ja").append("va").toString();
//        System.out.println(str2.intern() == str2);
//    }
//}
