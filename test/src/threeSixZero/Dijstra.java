package threeSixZero;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Dijstra {
    // 这里不能超过Integer.Max_Value
    private static int maxWeight = 100000;

    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        String[] split = bufferedReader.readLine().trim().split(" ");
        // n为点数 k为边数
        int n = Integer.parseInt(split[0]);
        int k = Integer.parseInt(split[1]);
        // 声明一个邻接矩阵，并初始化
        int[][] array = new int[n][n];
        for (int i = 0; i < array.length; i++) {
            Arrays.fill(array[i],maxWeight);
        }
        // 获取邻接关系
        for (int i = 0; i < k; i++) {
            String[] s = bufferedReader.readLine().trim().split(" ");
            array[Integer.parseInt(s[0])][Integer.parseInt((s[1]))] = Integer.parseInt(s[2]);
        }
        // 接收起始节点
        int source = Integer.parseInt(bufferedReader.readLine().trim());
        dijstra(array,source);

    }

    private static void dijstra(int[][] array, int source){
        // 声明一个source到各个节点的最短路径长度
        int[] distance = new int[array.length];
        // 声明一个标志数组 查看这个节点是否已经找到最短路径了
        boolean[] flags = new boolean[array.length];
        // 声明一个当前节点到目标节点的路径
        String[] path = new String[array.length];

        // 对path数组进行初始化
        for (int i = 0; i < array.length; i++) {
            path[i] = new String(source+"-->"+i);
        }

        distance[source] = 0;
        flags[source]  = true;

        for (int i = 1; i < array.length; i++) {
            int min = Integer.MAX_VALUE;
            int index = -1;
            // 找到一个距离当前节点最近的，还未被计算过的节点
            for (int j = 0; j < array.length; j++) {
                if (!flags[j] && array[source][j] < min){
                    min = array[source][j];
                    index = j;
                }
            }
            // source到这个节点的最短路径已经被找到
            distance[index] = min;
            flags[index] = true;

            // 更新 source节点到其他节点的距离
            for (int j = 0; j < array.length; j++) {
                if (!flags[j] && (array[source][index] + array[index][j]) < array[source][j]){
                    array[source][j] = array[source][index] + array[index][j];
                    path[j] = path[index] + "--->" + j;
                }
            }
        }

        for (int i = 0; i < array.length; i++) {
            if (i != source){
                System.out.println( source + " 到 " + i + " 的最短路径是："+ path[i]);
                System.out.println("最短路径的长度是：" + distance[i]);
            }
        }

    }
}
