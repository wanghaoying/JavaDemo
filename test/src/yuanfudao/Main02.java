package yuanfudao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main02 {

    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        int length = Integer.parseInt(bufferedReader.readLine().trim());
        int[] arr = new int[length];

        String[] strings = bufferedReader.readLine().trim().split(" ");
        for (int i = 0; i < length; i++) {
            arr[i] = Integer.parseInt(strings[i]);
        }

        long count = 0;

        int target = Integer.parseInt(bufferedReader.readLine().trim());


        for (int i = 0; i < arr.length; i++) {
            int j = i;
            int tmp = arr[i];
            while (j < arr.length){
                tmp |= arr[j];
                if (tmp <= target){
                    count++;
                }else{
                    break;
                }
                j++;
            }
        }


        System.out.println(count);

    }
}
