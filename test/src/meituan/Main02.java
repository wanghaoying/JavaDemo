package meituan;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main02 {

    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        String s = bufferedReader.readLine();
        String[] strings = s.trim().split(" ");
        int n = Integer.parseInt(strings[0]);
        long c1 = Long.parseLong(strings[1]);
        long c2 = Long.parseLong(strings[2]);

        long c = Math.min(c1,c2);

        String status = bufferedReader.readLine();
        char[] chars = status.trim().toCharArray();

        int fallCount = 0;
        long sum = 0;
        for (int i = chars.length-1; i >= 0; i--) {
            if (chars[i] == 'T'){
                fallCount = 0;
            }else if (chars[i] == 'F'){
                if (fallCount == 2){
                    sum += c;
                    fallCount = 0;
                }else {
                    fallCount++;
                }
            }
        }

        System.out.println(sum);

    }
}
