package meituan;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

public class Main03 {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        String s = bufferedReader.readLine();
        String[] strings = s.trim().split(" ");
        int n = Integer.parseInt(strings[0]);
        int m = Integer.parseInt(strings[1]);

        String ss = bufferedReader.readLine();
        String[] split = ss.trim().split(" ");

        HashMap<Long, Integer> first = new HashMap<>();
        HashMap<Long, Integer> last  = new HashMap<>();

        for (int i = 0; i < split.length; i++) {
            long num = Long.parseLong(split[i]);
            if (last.containsKey(num)){
                last.put(num, i);
            }else {
                first.put(num,i);
                last.put(num,i);
            }
        }

        for (int i = 0; i < m; i++) {
            String w = bufferedReader.readLine();
            long j = Long.parseLong(w.trim());
            if (first.containsKey(j)){
                System.out.println((first.get(j)+1) + " " + (last.get(j)+1));
            }else {
                System.out.println(0);
            }
        }
    }
}
