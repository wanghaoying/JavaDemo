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
        System.out.println(search(new int[] {1,2,4,4,5},4));
    }

    /**
     * 给定一个二叉树和一个值 sum，判断是否有从根节点到叶子节点的节点值之和等于sum 的路径，
     * 例如：
     * 给出如下的二叉树，sum=3，
     * {1,2},3  --> true
     *
     * 思路：寻找左右子树中有没有等于 sum-root.val 的路径
     */
    public boolean hasPathSum (TreeNode root, int sum) {
        if (root == null){
            return false;
        }

        if (root.left == null && root.right == null){
            return root.val == sum;
        }

        return hasPathSum(root.left,sum - root.val) ||
                hasPathSum(root.right, sum - root.val);
    }

    /**
     * 一座大楼有层，地面算作第0层，最高的一层为第 n 层。已知棋子从第0层掉落肯定不会摔碎，
     * 从第i层掉落可能会摔碎，也可能不会摔碎。 (1<=i<=n)
     * 给定整数n作为楼层数，再给定整数k作为棋子数，返回如果想找到棋子不会摔碎的最高层数，
     * 即使在最差的情况下扔的最小次数。一次只能扔一个棋子。
     *
     * 思路： 不会
     */
    public int solve (int n, int k) {
        // write code here
        return 0;
    }

    /**
     * 请实现有重复数字的升序数组的二分查找
     * 给定一个 元素有序的（升序）整型数组 nums 和一个目标值 target  ，
     * 写一个函数搜索 nums 中的 target，如果目标值存在返回下标，否则返回 -1
     *
     * 思路：因为是有重复数字的，那么当我们要抄着的target刚好是一个重复出现的数字的时候，我们需要
     * 返回的是他第一次出现的位置。那么如果我们通过二分查找到某一个元素的时候，我们并不能确定他
     * 是否是第一次出现的位置，所以我们需要围绕这个问题进行一些改进
     *
     */
    public int search (int[] nums, int target) {
        if (nums == null || nums.length == 0){
            return -1;
        }

        int left = 0, right = nums.length-1;
        while (left <= right){
            int mid = (left+right)/2;
            if (nums[mid] < target){
                left = mid+1;
            }else if (nums[mid] > target){
                right = mid-1;
            }else
                // 对mid处的值为target的情况的特殊处理
                if (right - left <= 1){
                    return left;
                }else {
                    right = mid;
                }
        }
        return -1;
    }

    /**
     * 给定一个数组和滑动窗口的大小，找出所有滑动窗口里数值的最大值。例如，
     * 如果输入数组{2,3,4,2,6,2,5,1}及滑动窗口的大小3，那么一共存在6个滑动窗口，
     * 他们的最大值分别为{4,4,6,6,6,5}；
     *
     * 针对数组{2,3,4,2,6,2,5,1}的滑动窗口有以下6个：
     * {[2,3,4],2,6,2,5,1}， {2,[3,4,2],6,2,5,1}， {2,3,[4,2,6],2,5,1}，
     * {2,3,4,[2,6,2],5,1}， {2,3,4,2,[6,2,5],1}， {2,3,4,2,6,[2,5,1]}。
     *
     * 窗口大于数组长度的时候，返回空
     *
     * 思路：
     * 1、暴力寻找，使用两个指针来标识窗口的边界，然后对寻找这个窗口内的最大值；然后继续移动到
     * 下一个窗口的位置，循环如此。 时间复杂度为o(n^2)
     *
     * 2、我们可以注意到这么一个事实：
     *       第i个位置上的数字比第j个位置上的数字小，那么只要i和j在一个窗口中，那么一定有
     *  最大值可能在j位置上取得，并保证一定不会在i位置上取得。
     *       所以我们可以使用一个双端队列来完善我们的思路：如果较大的数字后面进来一个较小的数字，
     *  那么在窗口滑动的过程中，可能会取到这个较小的数字；如果一个较小的数字后面进来一个较大的
     *  数字，那么在后续滑动的过程中，一定不会取到这个较小的数字
     */
    public ArrayList<Integer> maxInWindows(int [] num, int size) {
        if (num == null || num.length < size || num.length == 0 || size == 0){
            return new ArrayList<>();
        }
        // 初始化一个双端队列
        Deque<Integer> deque = new LinkedList<>();
        ArrayList<Integer> arrayList = new ArrayList<>();

        // 先将前三个数字插入进队列中
        for (int i= 0 ; i < size; i++){
            while (!deque.isEmpty() && deque.peekLast() < num[i]){
                deque.pollLast();
            }
            deque.offerLast(num[i]);
        }
        // 处理后面的数字
        int i = 0, j = size;
        while (j < num.length){
            // 判断是否抛出左端元素
            arrayList.add(deque.peekFirst());
            if (num[i] == deque.peekFirst()){
                deque.pollFirst();
            }
            // 对新加入的数字进行处理
            while (!deque.isEmpty() && deque.peekLast() < num[j]){
                deque.pollLast();
            }
            deque.offerLast(num[j++]);
            i++;
        }
        arrayList.add(deque.peekFirst());

        return arrayList;
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

    class TreeNode{
        int val;
        TreeNode left;
        TreeNode right;

        public TreeNode() {
        }

        public TreeNode(int val) {
            this.val = val;
        }

        public TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }
}