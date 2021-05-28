package nowcoder;

import org.junit.Test;

import java.util.ArrayList;

/**
 * nowcoder 第5个刷题文件，NC
 *
 * @author W.H
 * @since 2021.5.27
 */
public class Solution04 {

    public static void main(String[] args) {

    }

    @Test
    public void test(){
        System.out.println(restoreIpAddresses("012345"));
    }

    /**
     * 将给定的单链表L： L_0→L_1→…→L_{n-1}→L_ n
     * 重新排序为：L_0→L_n →L_1→L_{n-1}→L_2→L_{n-2}→…
     *
     * 要求使用原地算法，不能只改变节点内部的值，需要对实际的节点进行交换。
     * 例如：
     * 对于给定的单链表{10,20,30,40}，将其重新排序为{10,40,20,30}.
     *
     * 思路：这个链表中的节点交换之后，都变成了将关于中心对称的节点进行了相连，我们可以针对这个特点来进行
     * 处理
     *
     */
    public void reorderList(ListNode head) {

    }

    /**
     * 现在有一个只包含数字的字符串，将该字符串转化成IP地址的形式，返回所有可能的情况。
     * 例如：
     * 给出的字符串为"25525522135",
     * 返回["255.255.22.135", "255.255.221.35"]. (顺序没有关系)
     *
     * 思路：回溯法来进行处理，一个字符串能否转换成ip地址，以上图为例，
     * 1、当最后一个数字是5的时候，剩下的数字能否组成三个部分的ip地址；
     * 2、当最后一个数字是35的时候，剩下的数字能否组成三个部分的ip地址；
     * 3、当最后一个数字为135的时候，剩下的数字能否组成三个部分的ip地址；
     */
    public ArrayList<String> restoreIpAddresses (String s) {
        return generateAddresses(s,4);
    }

    private ArrayList<String> generateAddresses (String s, int n){
        // 声明结果集
        ArrayList<String> res = new ArrayList();
        if (s == null || s.length() == 0 || n == 0 || s.length() < n){
            return res;
        }

        if (n == 1){
            int i = Integer.parseInt(s);
            if ( i <= 255 && ((s.length() > 1 && s.charAt(0) != '0') || s.length() == 1)){
                res.add(s);
            }
            return res;
        }

        for (int i = 1; i <= 3; i++){
            if (i <= s.length()){
                String s1 = s.substring(0,i);

                if (s1.length() > 1 && s1.charAt(0) == '0'){
                    continue;
                }
                if (i == 3){
                    if (Integer.parseInt(s1) > 255){
                        break;
                    }
                }
                ArrayList<String> strings = generateAddresses(s.substring(i, s.length()), n - 1);
                for (String s2 : strings) {
                    res.add(s1+"."+s2);
                }
            }else {
                break;
            }
        }
        return res;
    }

    class ListNode{
        int val;
        int next;

        public ListNode() {
        }

        public ListNode(int val, int next) {
            this.val = val;
            this.next = next;
        }
    }
}
