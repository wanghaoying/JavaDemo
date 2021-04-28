package meituan;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;

public class Main06 {

    public static void main(String[] args) throws IOException, NoSuchFieldException, IllegalAccessException {
        String s = new String("12345");
        Field value = s.getClass().getDeclaredField("value");

        value.setAccessible(true);
        char[] chars = new char[] {'6','7'};
        value.set(s,chars);

        char[] o =(char[]) value.get(s);
        o[1] = '9';

        System.out.println(s);
    }
}
