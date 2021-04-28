package huawei;


/**
 * 给定一个字符串来代表一个学生的出勤记录，这个记录仅包含以下三个字符：
 *
 * 'A' : Absent，缺勤
 * 'L' : Late，迟到
 * 'P' : Present，到场
 * 如果一个学生的出勤记录中不超过一个'A'(缺勤)并且不超过两个连续的'L'(迟到),那么这个学生会被奖赏。
 *
 * 你需要根据这个学生的出勤记录判断他是否会被奖赏。
 *
 * 示例 1:
 *
 * 输入: "PPALLP"
 * 输出: True
 * 示例 2:
 *
 * 输入: "PPALLL"
 * 输出: False
 */
public class Main04 {

    public static void main(String[] args) {
        System.out.println(test("PPALL  "));

        Runtime.getRuntime().availableProcessors();
    }


    public static boolean test(String record){
        if (record == null || record.trim().length() == 0)
            return false;
        // Acount 存储A出现的次数，Loffest记录连续最远的一个L出现的记录位置
        int Acount = 0;
        int Loffest = -1;

        record = record.trim();
        for (int i = 0; i < record.length(); i++) {
            char c = record.charAt(i);
            if (c == 'A'){
                // 对于缺勤情况的处理
                Acount++;
                if (Acount > 1)
                    return false;
            }else if (c == 'L'){
                // 对于迟到情况的处理
                if (Loffest == -1)
                    Loffest = i;
                if (i - Loffest > 1){
                    return false;
                }
            }else if (c == 'P'){
                // 对于正常到的情况，取消掉Loffest的记录
                Loffest = -1;
            }else {
                return false;
            }
        }
        return true;
    }



}
