package meituan;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    private String isHuiwen(String s){
        if (s == null)
            return null;
        if (s.length() == 1)
            return "0";

        char[] chars = s.toCharArray();

        int count = 1;

        if ((chars.length % 2) == 0){
            //偶数长度
            int i = (chars.length /2) -1;
            int j = chars.length/2;

            while (i >= 0 && j < chars.length){
                if (chars[i] == chars[j]){
                    i--;
                    j++;
                }else{
                    if (count > 0){
                        if (chars[i] > chars[j]){
                            chars[i] = chars[j];
                        }else {
                            chars[j] = chars[i];
                        }
                        count--;
                        continue;
                    }
                    return null;
                }
            }
            return new String(chars);

        }else {
            // 奇数长度
            int i = (chars.length/2)-1;
            int j = (chars.length/2)+1;

            while (i >= 0 && j < chars.length){
                if (chars[i] == chars[j]){
                    i--;
                    j++;
                }else{
                    if (count > 0){
                        if (chars[i] > chars[j]){
                            chars[i] = chars[j];
                        }else {
                            chars[j] = chars[i];
                        }
                        count--;
                        continue;
                    }
                    return null;
                }
            }
            if (count == 1){
                chars[chars.length/2] = '0';
            }
            return new String(chars);
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        Main main = new Main();

        String st = bufferedReader.readLine();
        int T = Integer.parseInt(st.trim());

        for (int i = 0; i< T; i++){
            // 数字个数N 和 字符串 s
            int n = Integer.parseInt(bufferedReader.readLine().trim());
            String s = bufferedReader.readLine().trim();

            // 首先检查这个字符串是否是回文串，或者经过一次替换可以变成回文串
            String flag = main.isHuiwen(s);

            if (flag != null){
                System.out.println(flag);
                continue;
            }
            // 否则将第一个不为0的数字变为0
            char[] chars = s.toCharArray();
            for (int j = 0; j < chars.length; j++){
                if (chars[j] != '0'){
                    chars[j] = '0';
                    break;
                }
            }
            System.out.println(new String(chars));
        }
    }
}
