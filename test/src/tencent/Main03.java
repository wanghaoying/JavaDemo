package tencent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;

/**
 * 1
 * 3
 * 3 1 1
 * 3 6 9
 *
 * 6
 */
public class Main03 {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        int T = Integer.parseInt(bufferedReader.readLine().trim());

        for (int i = 0; i< T; i++){
            int n = Integer.parseInt(bufferedReader.readLine().trim());

            String[] strings = bufferedReader.readLine().trim().split(" ");

            String[] split = bufferedReader.readLine().trim().split(" ");

            int count = 0;
            HashMap<Integer, PriorityQueue<Integer>> hashMap = new HashMap<>();
            for (int j = 0; j < n; j++){
                int k = Integer.parseInt(strings[j]);
                if (!hashMap.containsKey(k)) {
                    // 创建一个大顶堆
                    hashMap.put(k, new PriorityQueue<Integer>(new Comparator<Integer>() {
                        @Override
                        public int compare(Integer o1, Integer o2) {
                            return -o1.compareTo(o2);
                        }
                    }));
                }

                count -= Integer.parseInt(split[j]);
                hashMap.get(k).offer(Integer.parseInt(split[j]));
            }




        }
    }
}
