package meituan;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main04 {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        String s = bufferedReader.readLine();
        String[] ss = s.trim().split(" ");
        int n = Integer.parseInt(ss[0]);
        int k = Integer.parseInt(ss[1]);

        long[] nums = new long[n];

        String s1 = bufferedReader.readLine();
        String[] strings = s1.trim().split(" ");
        for (int i = 0; i < strings.length; i++) {
            nums[i] = Long.parseLong(strings[i]);
        }

        long max = 0;

        for (int i = 0; i < nums.length; i++) {
            long tmp = nums[i];
            for (int j = i+1; (j < i+k) && (j < nums.length); j++){
                tmp ^= nums[j];
                max = Math.max(tmp,max);
            }
            max = Math.max(tmp,max);
        }

        System.out.println(max);
    }
}
