package yuanfudao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(bufferedReader.readLine().trim());

        for (int i = 0; i < N; i++) {
            String[] strings = bufferedReader.readLine().trim().split(" ");
            int K = Integer.parseInt(strings[0]);
            long[] nums = new long[K];
            for (int i1 = 0; i1 < K; i1++) {
                nums[i1] = Long.parseLong(strings[i1+1]);
            }

            if (nums.length <= 1){
                System.out.println("Y");
                break;
            }

            int j = 1;
            if (nums[0] <= nums[nums.length-1]){
                while ( j < nums.length){
                    if (nums[j] > nums[j-1]){
                        break;
                    }else {
                        j++;
                    }
                }
                if (j == nums.length || j == nums.length-1){
                    System.out.println("Y");
                    continue;
                }

                if (nums[j] < nums[0]){
                    System.out.println("N");
                    continue;
                }

                j++;
                while (j < nums.length){
                    if (nums[j] > nums[j-1]){
                        System.out.println("N");
                        break;
                    }else {
                        j++;
                    }
                }
                if (j==nums.length){
                    System.out.println("Y");
                    continue;
                }

            }else {
                while (j < nums.length){
                    if (nums[j] > nums[j-1]){
                        System.out.println("N");
                        break;
                    }else {
                        j++;
                    }
                }
                System.out.println("Y");
            }

        }
    }
}
