package ali;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    @Test
    public void test(){
        System.out.println(""+'9');
    }



    private static ArrayList<String> dfs(char[] chars, int left, int right, int n){
        ArrayList<String> arrayList = new ArrayList<>();
        if (n == 0){
            arrayList.add("");
            return arrayList;
        }
        if ((right-left+1) < n){
            return null;
        }

        ArrayList<String> strings = dfs(chars, left + 1, right, n-1);
        if (strings != null){
            for (String string : strings) {
                arrayList.add(chars[left]+string);
            }
        }

        ArrayList<String> strings1 = dfs(chars, left + 1, right, n);
        if (strings1 != null){
            arrayList.addAll(strings1);
        }

        return arrayList;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String[] s = bufferedReader.readLine().trim().split(" ");
        int m = Integer.parseInt(s[0]);
        int n = Integer.parseInt(s[1]);

        // 声明键盘存储数组
        char[] chars = new char[n];
        String[] strings = bufferedReader.readLine().trim().split(" ");
        for (int i = 0; i < strings.length; i++) {
            chars[i] = strings[i].charAt(0);
        }

        Arrays.sort(chars);

        // 找到数字与字母之间的边界
        int bound = 0;
        for (int i = 0; i<chars.length; i++){
            if (chars[i] <'a')
                bound++;
            else
                break;
        }

        if (m < 3)
            System.out.println();

        // 初始化一个结果集
        int count = 0;

        // 选取数字的个数
        for (int i = 1; i <= Math.min(m-2,bound); i++){
            ArrayList<String> strings1 = dfs(chars, 0, bound - 1, i);
            // 选取字符的个数
            int j = m-i;
            ArrayList<String> strings2 = dfs(chars, bound, chars.length - 1, j);

            for (String s1 : strings1) {
                for (String s2 : strings2) {
                    if (count < 666666){
                        System.out.println(s1+s2);
                        count++;
                    }else {
                        break;
                    }

                }
            }
        }

    }
}
