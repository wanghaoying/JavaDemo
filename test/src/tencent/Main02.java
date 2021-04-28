package tencent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Main02 {
    public static void main(String[] args) throws IOException {
//        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
//        String[] split = bufferedReader.readLine().trim().split(" ");
        Scanner scanner = new Scanner(System.in);
//        int n = Integer.parseInt(split[0]);
//        int k = Integer.parseInt(split[1]);
        int n = scanner.nextInt();
        int k = scanner.nextInt();

        int[] time = new int[n];
        int[] time1 = new int[n];
        for (int i = 0; i < n; i++){
//            time[i] = Integer.parseInt(bufferedReader.readLine().trim());
            time[i] = scanner.nextInt();
            time1[i] = time[i];
        }

        int t = 1;
        int m = 0;
        while ( m < k){
            int min = Integer.MAX_VALUE;
            for (int i = 0; i < n; i++) {
                if (m < k && time1[i] == t){
                    m++;
                    System.out.println(i+1);
                    time1[i] += time[i];
                }
                if (m >= k){
                    break;
                }
                min = Math.min(min,time1[i]);
            }
            t = min;
        }
    }
}
