package zhanhang;

import org.omg.PortableInterceptor.INACTIVE;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;

public class Main02 {

    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(bufferedReader.readLine().trim());

        for (int i = 0; i < T; i++){

            int n = Integer.parseInt(bufferedReader.readLine().trim());
            int[] nums = new int[n];
            int[] numss = new int[n];

            String[] split = bufferedReader.readLine().trim().split(" ");
            for (int j = 0; j< n; j++){
                nums[j] = Integer.parseInt(split[j]);
                numss[j] = nums[j];
            }

            int index = 0;
            Arrays.sort(numss);

            for (int j = 0; j< n; ){
                int k = j;
                while (k < n && numss[j]== numss[k]){
                    k++;
                }
                if ((k <= n && k-j == 1)){
                    index = j;
                    break;
                }
                if (k == n){
                    index = -1;
                }
                j = k;
            }

            if (index == -1){
                System.out.println(-1);
            }else {
                for (int j = 0; j < n; j++) {
                    if (nums[j] == numss[index]){
                        System.out.println(j);
                        break;
                    }
                }
            }
        }
    }
}
