package tencent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Mian04 {

    public static boolean isEquals(String s1, String s2){
        if (s1.length() % 2 == 0){
            return (isEquals(s1.substring(0,s1.length()/2),s2.substring(0,s2.length()/2))
                    && isEquals(s1.substring(s1.length()/2),s2.substring(s2.length()/2)))
                    || (isEquals(s1.substring(0,s1.length()/2),s2.substring(s2.length()/2))
                    && isEquals(s1.substring(s1.length()/2),s2.substring(0,s2.length()/2)));
        }else {
            return s1.equals(s2);
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(bufferedReader.readLine().trim());

        for (int i = 0; i< t; i++){
            String s1 = bufferedReader.readLine().trim();
            String s2 = bufferedReader.readLine().trim();

            if (isEquals(s1,s2)){
                System.out.println("YES");
            }else {
                System.out.println("NO");
            }
        }
    }
}
