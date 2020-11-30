import com.sun.org.apache.xerces.internal.xs.datatypes.ObjectList;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * 泛型
 *
 * @param <T>
 */

public class Generic <T>{
    private T t;

    public  Generic(){

    }
    public void setT(T t){
        this.t = t;
    }

    public T getT(){
        return t;
    }

    public void showT(T x){
        System.out.println(x);
    }

    @Test
    public void print(){

//        Pair<String> pair1=new Pair("string",new Integer(1));
//        System.out.println(pair1.getSecond());

//        Generic<String> generic = new Generic<>();
//
//        generic.showT("123");

        /**
         * 这段代码在 os[1] = i; 位置报错，说明jvm中，普通的类型数组，
         * 在jvm中的类型是真实存在的，String就是String，Integer就是Integer
         */
//        String[] strings = new String[10];
//        Object o = strings;
//        Object[] os = (Object[]) o;
//
//        Integer i = new Integer(1);
//        os[1] = i;
//        String s = (String) os[1];
//        System.out.println(s);

//        List<String>[] list = new ArrayList[10];
//        System.out.println(list.getClass());
//        System.out.println(Arrays.asList(list.getClass()));

        /**
         * sun的说明文档中的描述：在java中是”不能创建一个确切的泛型类型的数组”的。
         * 即下面的这段代码执行不过的：
         * List<String> list = new List<String>[10];
         *
         * 下面这段代码在编译的时候并不会报错，但是在运行的时候会报一个RunTimeError
         * 原因如下：
         * 这种情况下，由于JVM泛型的擦除机制，在运行时JVM是不知道泛型信息的，所以可以给
         * oa[1]赋上一个ArrayList而不会出现异常，但是在取出数据的时候却要做一次类型转换，
         * 所以就会出现ClassCastException，如果可以进行泛型数组的声明，上面说的这种情况
         * 在编译期将不会出现任何的警告和错误，只有在运行时才会出错。
         *
         * 而对泛型数组的声明进行限制，对于这样的情况，可以在编译期提示代码有类型安全问题，
         * 比没有任何提示要强很多。
         */
//        List<String>[] lsa = new List[10]; // Not really allowed.
//        Object o = lsa;
//        Object[] oa = (Object[]) o;
//        List<Integer> li = new ArrayList<Integer>();
//        li.add(new Integer(3));
//        oa[1] = li; // Unsound, but passes run time store check
//        String s = lsa[1].get(0); // Run-time error: ClassCastException.
//        System.out.println(s);


        /**
         * 下面这段代码因为在声明的时候没有确定固定的类型，所以无论填充什么类型的都可以
         * 但是只可以使用？通配符
         */
//        List<?>[] lsa = new List<?>[10]; // OK, array of unbounded wildcard type.
//        Object o = lsa;
//        Object[] oa = (Object[]) o;
//        List<Integer> li = new ArrayList<Integer>();
//        li.add(new Integer(3));
//        oa[1] = li; // Correct.
//        List<String> list = new ArrayList<>();
//        list.add("123");
//        oa[0] = list;
//        Integer i = (Integer) lsa[1].get(0); // OK

    }

    /**
     * 如果静态方法要使用泛型的话，必须将静态方法也定义成泛型方法
     * 即使静态方法要使用泛型类中已经声明过的泛型也不可以。
     * @param args
     * @param <T>
     */
    public static <T> void printMsg(T... args){
        for(T t : args){
            System.out.println("泛型测试 t is " + t.getClass());
        }
    }

}
