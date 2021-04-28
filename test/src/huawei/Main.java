package huawei;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        // 声明一个hashMap存储得分
        HashMap<Character,Integer> hashMap = new HashMap<>();
        String[] s = bufferedReader.readLine().trim().split(" ");

        String s1 = s[0];
        String s2 = s[1];

        if (s2.charAt(0) - s2.charAt(2) > 0){
            hashMap.put(s1.charAt(0),3);
            hashMap.put(s1.charAt(2),0);
        }else if (s2.charAt(0) - s2.charAt(2) < 0){
            hashMap.put(s1.charAt(0),0);
            hashMap.put(s1.charAt(2),3);
        }else if (s2.charAt(0) - s2.charAt(2) == 0){
            hashMap.put(s1.charAt(0),1);
            hashMap.put(s1.charAt(2),1);
        }

        // 声明队伍的个数和已经接收输入的次数
        int inputCount = 1;
        while (inputCount < hashMap.size()*(hashMap.size()-1)){
            String[] split = bufferedReader.readLine().trim().split(" ");
            char a = split[0].charAt(0);
            char b = split[0].charAt(2);
            int source1 = split[1].charAt(0);
            int source2 = split[1].charAt(2);

            if (source1 > source2){
                if (hashMap.containsKey(a)){
                    hashMap.put(a,hashMap.get(a)+3);
                }else {
                    hashMap.put(a,3);
                }

                if (!hashMap.containsKey(b)){
                    hashMap.put(b,0);
                }
            }else if (source1 < source2){
                if (hashMap.containsKey(b)){
                    hashMap.put(b,hashMap.get(b)+3);
                }else {
                    hashMap.put(b,3);
                }

                if (!hashMap.containsKey(a)){
                    hashMap.put(a,0);
                }

            }else {
                if (hashMap.containsKey(a)){
                    hashMap.put(a,hashMap.get(a)+1);
                }else {
                    hashMap.put(a,1);
                }

                if (hashMap.containsKey(b)){
                    hashMap.put(b,hashMap.get(b)+1);
                }else {
                    hashMap.put(b,1);
                }

            }

            inputCount++;
        }

        // 对hashmap中的entry进行排名
        Object[] objects = hashMap.entrySet().toArray();
        Arrays.sort(objects, new Comparator<Object>() {
            @Override
            public int compare(Object o1, Object o2) {
                Map.Entry<Character,Integer> e1 = (Map.Entry) o1;
                Map.Entry<Character,Integer> e2 = (Map.Entry) o2;

                if (e1.getValue() > e2.getValue()){
                    return -1;
                }else if (e1.getValue() < e2.getValue()){
                    return 1;
                }else{
                    return e1.getKey().compareTo(e2.getKey());
                }
            }
        });

        StringBuilder sb = new StringBuilder();
        for (Object object : objects) {
            Map.Entry<Character,Integer> o = (Map.Entry) object;
            sb.append(o.getKey());
            sb.append(" ");
            sb.append(o.getValue());
            sb.append(",");
        }

        System.out.println(sb.toString().substring(0,sb.length()-1));
    }
}
