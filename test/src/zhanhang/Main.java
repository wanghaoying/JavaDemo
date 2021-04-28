package zhanhang;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Scanner;
//import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String[] split = bufferedReader.readLine().trim().split(" ");
        // 输入的矩阵的大小 n*m
        int n = Integer.parseInt(split[0]);
        int m = Integer.parseInt(split[1]);
        // 接受查询的次数
        int p = Integer.parseInt(bufferedReader.readLine().trim());

        if (p < 1000000){
            for (int k = 0; k < p; k++){
                String[] strings = bufferedReader.readLine().trim().split(" ");
                int i = Integer.parseInt(strings[0]);
                int j = Integer.parseInt(strings[1]);


                // 根据i判断第i行的起始位置的数字 index
                long index;
                if (i <= (n+1)/2){
                    index = (2*(i-1)) * m +1;
                }else {
                    index = (2*(n-i)+1)*m +1;
                }
                System.out.println(index+j-1);
            }
        }else {
            long[][] nums = new long[n][m];

            for (int k = 0; k < p; k++){
                String[] strings = bufferedReader.readLine().trim().split(" ");
                int i = Integer.parseInt(strings[0]);
                int j = Integer.parseInt(strings[1]);

                if (nums[i-1][j-1] == 0){
                    // 根据i判断第i行的起始位置的数字 index
                    long index;
                    if (i <= (n+1)/2){
                        index = (2*(i-1)) * m;
                    }else {
                        index = (2*(n-i)+1)*m;
                    }
                    nums[i-1][j-1] = index+j;
                }

                System.out.println(nums[i-1][j-1]);
            }
        }


//        Scanner scanner = new Scanner(System.in);
//
//        int n = scanner.nextInt();
//        int m = scanner.nextInt();
//        int p = scanner.nextInt();
//
//        if (p < 1000000){
//            for (int k = 0; k < p; k++){
//                int i = scanner.nextInt();
//                int j = scanner.nextInt();
//
//                // 根据i判断第i行的起始位置的数字 index
//                long index;
//                if (i <= (n+1)/2){
//                    index = (2*(i-1)) * m +1;
//                }else {
//                    index = (2*(n-i)+1)*m +1;
//                }
//                System.out.println(index+j-1);
//            }
//        }else {
//            long[][] nums = new long[n][m];
//            for (int k = 0; k < p; k++){
//                int i = scanner.nextInt();
//                int j = scanner.nextInt();
//
//                if (nums[i-1][j-1] == 0){
//                    // 根据i判断第i行的起始位置的数字 index
//                    long index;
//                    if (i <= (n+1)/2){
//                        index = (2*(i-1)) * m;
//                    }else {
//                        index = (2*(n-i)+1)*m;
//                    }
//                    nums[i-1][j-1] = index + j;
//                }
//                System.out.println(nums[i-1][j-1]);
//            }
//
//        }

    }
}
