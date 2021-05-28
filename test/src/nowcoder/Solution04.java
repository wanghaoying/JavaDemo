package nowcoder;

import org.junit.Test;
import org.omg.PortableInterceptor.INACTIVE;

import java.util.*;

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
        ArrayList<ArrayList<Integer>> arrayLists = permuteUnique(new int[]{1, 1, 2});
        System.out.println(arrayLists);
    }

    /**
     * 给出一组可能包含重复项的数字，返回该组数字的所有排列。
     *
     * 思路：这个类似于求出num中所有数字组合的全排列，我们可以通过选定不同的位置上的数字，来进行生成
     * 采用回溯法的思想，比如生成的全排列中，我们可以选定第一个位置上的数字，只要第一个位置上的数字不同
     * 那么后面生成的全排列就一定不同
     */
    public ArrayList<ArrayList<Integer>> permuteUnique(int[] num) {
        if (num == null || num.length == 0){
            return new ArrayList<>();
        }
        ArrayList<ArrayList<Integer>> result = new ArrayList<>();
        generateParrten(result, num, 0);
        return result;
    }
    // 生成各种的排列组合
    private void generateParrten(ArrayList<ArrayList<Integer>> res, int[] num, int index){
        if (index == num.length-1){
            ArrayList<Integer> arrayList = new ArrayList<>(num.length);
            for (int i = 0; i < num.length; i++) {
                arrayList.add(i,num[i]);
            }
            res.add(arrayList);
            return;
        }
        // 声明一个Set，用来存储在当前位置上出现过的数字，不能出现重复的数字
        Set<Integer> set = new HashSet<>();
        for (int i = index; i < num.length; i++){
            if (!set.contains(num[i])){
                set.add(num[i]);
                swap(num, i, index);
                generateParrten(res, num, index+1);
                swap(num, i, index);
            }
        }
    }
    // 对一个数组指定为位置的两个数字进行交换
    private void swap(int[] nums, int i, int j){
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }

    /**
     * 删除给出链表中的重复元素（链表中元素从小到大有序），使链表中的所有元素都只出现一次
     * 例如：
     * 给出的链表为1→1→2,返回1→2.
     * 给出的链表为1→1→2→3→3,返回1→2→3.
     *
     * 思路：在每一个节点处，判断如果他的next的val等于他自己的话，那么就继续向后探索，相当于忽略掉
     * 这个节点
     */
    public ListNode deleteDuplicates (ListNode head) {
        if (head == null){
            return head;
        }
        ListNode p = head;
        while (p != null){
            ListNode q = p.next;
            while (q != null){
                if (p.val == q.val){
                    q = q.next;
                }else {
                    break;
                }
            }
            p.next = q;
            p = p.next;
        }
        return head;
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
     * 处理，可以先找到整个链表的中间位置，然后在寻找过程中，对前面的数据进行链表的反转，可以让我们对前
     * 面的节点进行很快的查找
     *      然后，对于前面的节点，我们需要重新修改他们的指向，使得他指向一个关于中心对称的后面的一个节点
     * 然后把这些节点串联起来
     */
    public void reorderList(ListNode head) {
        if (head == null){
            return;
        }
        // 使用一个快慢指针来寻找这个链表的中心位置，循环结束的时候，slow指针所在的位置，就是中心位置
        // 在遍历的时候，对中心之前的节点进行一个反转
        ListNode slow = head, fast = head.next, prev = null, after = head.next;
        while (fast != null && fast.next != null){
            // 对链表的反转动作
            after = slow.next;
            slow.next = prev;
            prev = slow;
            slow = after;

            fast = fast.next.next;
        }
        // 根据fast现在的指向来判断链表的长度是奇数还是偶数，如果现在fast指向的是null，则链表长度
        // 为奇数，slow指针指向的是中心节点；反之为偶数，slow指针指向的是
        if (fast == null){
            after = slow.next;
            slow.next = null;
        }else {
            after = slow.next;
            slow.next = prev;
            prev = slow;
            slow = null;
        }
        // 现在prev指针指向的是以中心为界限，左边的第一个节点，after为以中心为界限，右边的第一个节点
        // slow代表的是当前组合完毕之后after应该的next指针
        ListNode p, q;
        while (prev != null){
            p = prev.next;
            q = after.next;

            prev.next = after;
            after.next = slow;
            slow = prev;

            prev = p;
            after = q;
        }
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
        ListNode next;

        public ListNode() {
        }

        public ListNode(int val) {
            this.val = val;
        }

        public ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }
}
