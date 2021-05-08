package tencent;


/**
 * 一个字符串包含若干个单词，单词之间由1到多个空格隔开，要求遍历一遍，
 * 统计单词的个数
 */
public class Main05 {

    public static void main(String[] args) {
        System.out.println(wordCount(" abc "));
    }

    public static int wordCount(String s){
        if (s == null || s.length() == 0)
            return 0;

        // 声明单词的数量
        int count = 0;
        // 声明一个变量，标示空格之前是否是空格
        boolean flag = true;

        // 遍历 s 中的字符
        for (int i = 0; i < s.length(); i++){
            if (s.charAt(i) == ' '){
                // 对空格字符的情况进行处理
                if (!flag){
                    count++;
                }
                flag = true;
            }else {
                // 对非空格字符的情况进行处理
                flag = false;
            }
        }
        return count;
    }
}
