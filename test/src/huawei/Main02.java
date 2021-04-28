package huawei;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class Main02 {

    @Test
    public void test(){
        String s = "[1, 1, 2]";
        String trim = s.substring(1, s.length() - 1);
        String[] split = trim.split(" ");
        System.out.println(split.length);
        for (String s1 : split) {
            System.out.println(s1);
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        String s = bufferedReader.readLine().trim();
        if (s.length() <= 2){
            System.out.println(0);
            return;
        }
        String s1 = s.substring(1,s.length()-1).trim();
        s1.replaceAll(" ","");
        s1 = s1.replaceAll(","," ");

        if (s1.length() == 0){
            System.out.println(0);
            return;
        }

        String[] strings = s1.split(" ");
        HashMap<Integer,Integer> hashMap = new HashMap<>();
        for (String string : strings) {
            if (string.length() == 0)
                continue;
            int n = Integer.parseInt(string)+1;
            if (hashMap.containsKey(n)){
                hashMap.put(n,hashMap.get(n)+1);
            }else {
                hashMap.put(n,1);
            }
        }

        int member = 0;
        // 遍历这个hashmap中的entry
        for (Map.Entry<Integer, Integer> entry : hashMap.entrySet()) {
            int key = entry.getKey();
            int value = entry.getValue();

            if ((value % key) == 0){
                member += (value/key)*key;
            }else {
                member += (value/key)*key + key;
            }
        }

        System.out.println(member);
    }
}
