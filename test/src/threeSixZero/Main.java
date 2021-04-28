package threeSixZero;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(bufferedReader.readLine().trim());
        int[] nums = new int[n];
        // 接受输入分数数组
        String[] strings = bufferedReader.readLine().trim().split(" ");

        for (int i = 0; i < n; i++) {
            nums[i] = Integer.parseInt(strings[i]);
        }

        // 结果记录变量
        long sum = 0;
        // 只命中一个的情况
        for (int num : nums) {
            sum += num;
        }
        // 命中两个的情况
        for (int i = 0; i< nums.length; i++){
            for (int j = i+1; j < nums.length; j++){
                sum += (nums[i] | nums[j]);
            }
        }

        System.out.println(sum);

    }
}
