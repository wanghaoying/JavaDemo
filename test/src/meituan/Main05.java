package meituan;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Main05 {


    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String s = bufferedReader.readLine();
        int n = Integer.parseInt(s.trim());

        // 声明存储数组,和结果array
        int[] nums = new int[n];
        ArrayList<Integer> arrayList = new ArrayList<>();

        String[] strings = bufferedReader.readLine().trim().split(" ");
        for (int i = 0; i < strings.length; i++){
            nums[i] = Integer.parseInt(strings[i]);
        }

        int G = 0;
        int O = 0;
        for (int i = 0; i < nums.length; i++) {
            if ((i % 2) == 0){
                O += nums[i];
            }else {
                G += nums[i];
            }
        }

        int g = 0;
        int o = 0;
        for (int i = 0; i < nums.length; i++) {
            if ((i % 2) == 0){
                O -= nums[i];
                if ((g + O) == (G + o)){
                    arrayList.add(i);
                }
                o += nums[i];
            }else {
                G -= nums[i];
                if ((g + O) == (G + o)){
                    arrayList.add(i);
                }
                g += nums[i];
            }
        }

        if (arrayList.size() == 0){
            System.out.println(0);
        }else {
            System.out.println(arrayList.size());

            arrayList.sort(null);
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < arrayList.size(); i++) {
                sb.append(arrayList.get(i)+1);
                sb.append(" ");
            }
            String res = sb.toString();
            System.out.println(res.substring(0,res.length()-1));
        }
    }
}
