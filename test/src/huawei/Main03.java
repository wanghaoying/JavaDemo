package huawei;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main03 {

    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        String string = bufferedReader.readLine().trim();
        String pattern = bufferedReader.readLine().trim();

        int index = Integer.parseInt(bufferedReader.readLine().trim());

        // 使用count来记录指针的移动数
        int count = 0;
        // i指向string中指针
        int i = index;
        // j指向pattern中的字符指针
        for (int j = 0; j < pattern.length(); j++){
            i = index;
            char c = pattern.charAt(j);

            int left = -1;
            int right = -1;
            // 从index开始向右查找
            while (i < string.length()){
                if (string.charAt(i) == c){
                    right = i;
                    break;
                }
                i++;
            }

            if (right == -1){
                i = 0;
                while (i < index){
                    if (string.charAt(i) == c){
                        right = i;
                        break;
                    }
                    i++;
                }
            }

            // 从index左边开始查找
            i = index-1;
            while (i >= 0){
                if (string.charAt(i) == c){
                    left = i;
                    break;
                }
                i--;
            }
            if (left == -1){
                i = string.length()-1;
                while (i > index){
                    if (string.charAt(i) == c){
                        left = i;
                        break;
                    }
                    i--;
                }
            }

            if (Math.abs(right - index) < Math.abs(left-index)){
                index = right;
                count += Math.abs(right - index);
            }else if (Math.abs(right - index) > Math.abs(left-index)){
                index = left;
                count += Math.abs(left - index);
            }else { // 相等的情况，需要考虑之后怎么取 ，这个方法就错了
                count += Math.abs(left - index);
                index = right;
            }

        }
        System.out.println(count);
    }

}
