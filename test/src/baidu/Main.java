package baidu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    // 寻找二叉树中两个节点的最短路径，可以考虑类似二分法进行查找
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        int T = Integer.parseInt(bufferedReader.readLine().trim());

        for (int i = 0; i < T; i++){
            String[] split = bufferedReader.readLine().trim().split(" ");
            int m = Integer.parseInt(split[0]);
            int n = Integer.parseInt(split[1]);

            // m代表小的数字，n代表大的数字
            int tmp = Math.max(m,n);
            m = Math.min(m,n);
            n = tmp;

            if (m == n){
                System.out.println(0);
                continue;
            }

            int distance = 0;
            // 首先查看 m 和 n 在不在一棵树上
            while (n >= 1){
//                if (n % 2 == 0){
//                    // n 是偶数
                if (n/2 == m){
                    distance++;
                    break;
                }else if (n > m){
                    distance++;
                    n = n/2;
                }
                if (n < m){
                    int t = m;
                    m = n;
                    n = t;
                }
//                }else {
//                    // n 是奇数
//                    if ((n-1)/2 == m){
//                        distance++;
//                        break;
//                    }else if (n > m){
//                        distance++;
//                        n = (n-1)/2;
//                    }
//                    if (n < m){
//                        int t = m;
//                        m = n;
//                        n = t;
//                    }
//                }
            }
            System.out.println(distance);
        }
    }
}
