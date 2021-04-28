package threeSixZero;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class Main01 {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String[] strings = bufferedReader.readLine().trim().split(" ");
        int n = Integer.parseInt(strings[0]);
        int m = Integer.parseInt(strings[1]);
        int k = Integer.parseInt(strings[2]);

        int maxDistance = m*k;

        // 声明暂存数组保存输入
        int[] distance = new int[n];
        int[] values = new int[n];

        // 找到一个合适的边界
        int offest = 0;
        for (int i = 0; i < n; i++) {
            String[] s = bufferedReader.readLine().trim().split(" ");
            if (Integer.parseInt(s[0]) > maxDistance
                    || (i > 0 && (Integer.parseInt(s[0]) > distance[offest-1]+m))){
                continue;
            }else {
                distance[offest] = Integer.parseInt(s[0]);
                values[offest++] = Integer.parseInt(s[1]);
            }
        }

        if (offest-1 <= k){
            int sum = 0;
            for (int i = 0; i < offest; i++) {
                sum += values[i];
            }
            System.out.println(sum);
        }else {
            HashMap<Integer,Integer> Ahash = new HashMap<>();
            HashMap<Integer,Integer> Bhash = new HashMap<>();

            Ahash.put(distance[0],values[0]);
            for (int i = 1; i <= k; i++){
                int j = i;
                // 第i 次降落的情况
                while (j < offest && distance[j] <= i*m){
                    for (int x = distance[j]-m; x < distance[j]; x++){
                        if (Ahash.containsKey(x)){
                            if (Bhash.containsKey(distance[j])){
                                if (Bhash.get(distance[j]) < (Ahash.get(x)+values[j])){
                                    Bhash.put(distance[j],Ahash.get(x)+values[j]);
                                }
                            }else {
                                Bhash.put(distance[j],Ahash.get(x)+values[j]);
                            }
                        }
                    }
                    j++;
                }
                Ahash.clear();
                Ahash.putAll(Bhash);
                Bhash.clear();
            }
            int max = 0;
            for (Map.Entry<Integer, Integer> entry : Ahash.entrySet()) {
                max = Math.max(max,entry.getValue());
            }
            System.out.println(max);
        }
    }
}
