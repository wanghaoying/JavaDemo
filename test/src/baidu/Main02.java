package baidu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

public class Main02 {

    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        String[] split = bufferedReader.readLine().trim().split(" ");
        int n = Integer.parseInt(split[0]);  // 选手数量
        int m = Integer.parseInt(split[1]);  // 比赛场次
        int p = Integer.parseInt(split[2]);  // 牛牛的编号

        HashMap<Integer, HashSet<Integer>> winHashMap = new HashMap<>();
        HashMap<Integer, HashSet<Integer>> failHashMap = new HashMap<>();
        // 记录下每个人赢了的人 和 输了的人， 尤其记录下牛牛赢了的 和 输了的人
        for (int i = 0; i < m; i++){
            String[] strings = bufferedReader.readLine().trim().split(" ");
            int u = Integer.parseInt(strings[0]);
            int v = Integer.parseInt(strings[1]);  // u 赢了 v

            if (winHashMap.containsKey(u)){
                HashSet<Integer> set = winHashMap.get(u);
                set.add(v);
                winHashMap.put(u,set);
            }else {
                HashSet<Integer> winSet = new HashSet<>();
                HashSet<Integer> failSet = new HashSet<>();
                winSet.add(v);
                winHashMap.put(u,winSet);
                failHashMap.put(u,failSet);
            }

            if (failHashMap.containsKey(v)){
                HashSet<Integer> set = failHashMap.get(v);
                set.add(u);
                failHashMap.put(v,set);
            }else {
                HashSet<Integer> winSet = new HashSet<>();
                HashSet<Integer> failSet = new HashSet<>();
                failSet.add(u);
                winHashMap.put(v,winSet);
                failHashMap.put(v,failSet);
            }
        }

        // 如果牛牛没有参加过比赛，那么牛牛的成绩可以任意取定
        if (!winHashMap.containsKey(p)){
            StringBuilder sb = new StringBuilder();
            for (int i = 1 ; i <= n; i++){
                sb.append(i);
                sb.append(" ");
            }
            System.out.println(sb.toString().substring(0,sb.length()-1));
        }


        // 找出所有不如牛牛的个数 和 所有超过牛牛的个数
        HashSet<Integer> win = new HashSet<>(); // 赢了牛牛的
        HashSet<Integer> fail = new HashSet<>(); // 输给牛牛的

        // 牛牛输了的人
        Queue<Integer> queue = new LinkedList<>();
        for (Integer integer : failHashMap.get(p)) {
            queue.offer(integer);
        }
        while (!queue.isEmpty()){
            for (int i = queue.size(); i > 0; i--){
                int t  = queue.poll();
                fail.add(t);
                for (Integer integer : failHashMap.get(t)) {
                    queue.offer(integer);
                }
            }
        }

        // 牛牛赢了的人
        for (Integer integer : winHashMap.get(p)){
            queue.offer(integer);
        }
        while (!queue.isEmpty()){
            for (int i = queue.size(); i > 0; i--){
                int t  = queue.poll();
                win.add(t);
                for (Integer integer : winHashMap.get(t)) {
                    queue.offer(integer);
                }
            }
        }

        int winSize = win.size();
        int failSize = fail.size();

        StringBuilder sb = new StringBuilder();
        for (int start = failSize+1; start <= n-winSize; start++){
            sb.append(start);
            sb.append(" ");
        }
        System.out.println(sb.toString().substring(0,sb.length()-1));

    }
}
