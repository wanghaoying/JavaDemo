package nowcoder;

import org.junit.Test;

import java.util.*;

/**
 * 刷题的第四个文件，NC12 ~ NC
 */
public class Solution03 {
    public static void main(String[] args) {

    }

    @Test
    public void test(){
        System.out.println(Permutation("abc"));
    }

    /**
     * 输入一个字符串,按字典序打印出该字符串中字符的所有排列。例如输入字符串abc,
     * 则按字典序打印出由字符a,b,c所能排列出来的所有字符串abc,acb,bac,bca,cab
     * 和cba。
     *
     * 思路：
     *      既然要求打印出的最终结果需要按照字典序进行排列，那么就有两种方案：
     *      1、计算出全部的全排列的组合之后，再对这些所有的组合进行排序，他的排序
     *  时间复杂度为O(n!(logn!))
     *      2、在每次交换之后，对i位置之后的数字进行排序，每个位置都要进行一次排序，
     *  排序的时间复杂度为o(n^2longn)，理论上在n比较大的情况下，这种方式实现的结果
     *  可能更快一些
     */
    public ArrayList<String> Permutation(String str) {
        if (str == null || str.length() == 0){
            return new ArrayList<>();
        }
        // 将str装换成char[]数组
        char[] chars = str.toCharArray();

        // 声明最后的结果集
        ArrayList<String> strings = new ArrayList<>();
        Permutation(chars,0,chars.length-1,strings);
        // 对最终的结果进行排序
        strings.sort(null);
        return strings;
    }

    private ArrayList<String> Permutation(char[] chars, int start, int end, ArrayList<String> strings){
        // 当start==end时，说明完成了一次全排列
        if (start == end){
            strings.add(new String(chars));
        }

        // 递归生成所有的排列组合
        // 声明一个HashSet记录每个索引位置上出现过的
        HashSet<Character> hashSet = new HashSet<>();
        for (int i = start; i < chars.length; i++){
            if (hashSet.contains(chars[i])){
                continue;
            }
            hashSet.add(chars[i]);
            swap(chars, start, i);
            Permutation(chars,start+1,end,strings);
            swap(chars, start, i);
        }
        return strings;
    }

    // 交换char数组中 i 位置上 和 j 位置上的字符
    private void swap(char[] chars, int i, int j){
        char tmp = chars[i];
        chars[i] = chars[j];
        chars[j] = tmp;
    }

    /**
     * 合并 k 个已排序的链表并将其作为一个已排序的链表返回。分析并描述其复杂度。
     *
     * 思路：
     *      有多个已经排序好的链表需要排序，有两种方法：
     *      1、依次merge每个待合并链表，时间复杂度为o(k^2 n)
     *      2、根据分治的思想，对lists中的链表先，两两merge，然后merge之后，
     *  再两两merge，直到最后合并成一个list，然后返回
     */
    public ListNode mergeKLists(ArrayList<ListNode> lists) {
        // 对特殊情况进行判断
        if (lists == null || lists.size() == 0){
            return null;
        }
        // 如果只有一个链表，那么就不需要合并
        if (lists.size() == 1){
            return lists.get(0);
        }
        return mergeKLists(lists,0,lists.size()-1);
    }
    // 合并索引 start - end 之间的所有链表
    private ListNode mergeKLists(ArrayList<ListNode> lists,int start, int end){
        if (start == end){
            return lists.get(start);
        }
        int mid = (start + end) /2;
        return mergeTwoLists(mergeKLists(lists,start,mid),
                mergeKLists(lists,mid+1,end));
    }
    // 合并两个有序的链表
    private ListNode mergeTwoLists(ListNode l1, ListNode l2){
        if (l1 == null){
            return l2;
        }
        if (l2 == null){
            return l1;
        }
        ListNode newHead = new ListNode(0);
        ListNode p = newHead;
        while (l1 != null && l2 != null){
            if (l1.val <= l2.val){
                p.next = l1;
                l1 = l1.next;
            }else {
                p.next = l2;
                l2 = l2.next;
            }
            p = p.next;
        }
        if (l1 != null){
            p.next = l1;
        }
        if (l2 != null){
            p.next = l2;
        }
        return newHead.next;
    }

    /**
     * 假设你有一个数组，其中第 i 个元素是股票在第 i 天的价格。
     * 你有一次买入和卖出的机会。（只有买入了股票以后才能卖出）。
     * 请你设计一个算法来计算可以获得的最大收益。
     *
     * 思路：
     *      收益最大 = 价格差最大，理想情况下，最低价格之后有个最高价格
     *      可以维护一个最小价格的变量，该变量表示的是：到当前位置i之前，所有的
     *  价格中，最低的那个，时间复杂度为o(n)
     */
    public int maxProfit (int[] prices) {
        // 对特殊情况进行判断
        if (prices == null || prices.length == 0){
            return 0;
        }

        int min = prices[0], profit = 0;
        for (int i = 0; i < prices.length; i++) {
            if (prices[i] < min){
                min = prices[i];
            }else {
                profit = Math.max(profit, prices[i]-min);
            }
        }
        return profit;
    }

    /**
     * 根据二叉树的前序遍历，中序遍历恢复二叉树，并打印出二叉树的右视图
     *
     * 思路：
     *      1、首先对根据二叉树的前序 和 中序遍历 重建这个二叉树
     *   然后层序遍历这个二叉树，每次选用最右边的数字
     */
    public int[] solve (int[] xianxu, int[] zhongxu) {
        // 构建二叉树
        TreeNode root = reConstructBinaryTree(xianxu, zhongxu);
        if (root == null){
            return new int[] {};
        }
        // 声明存储层序遍历的quque 和 存储结果的array
        Queue<TreeNode> queue = new LinkedList<>();
        List<Integer> list = new ArrayList<>();
        queue.offer(root);
        // 遍历获取右视图的结果
        while (!queue.isEmpty()){
            int n = queue.size();
            for (int i = queue.size(); i > 0; i--){
                TreeNode t = queue.poll();
                if (i == 1){
                    list.add(t.val);
                }
                if (t.left != null){
                    queue.offer(t.left);
                }
                if (t.right != null){
                    queue.offer(t.right);
                }
            }
        }
        // 将结果转换成int[]
        int[] res = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            res[i] = list.get(i);
        }
        return res;
    }


    /**
     * 给定一个整形数组arr，已知其中所有的值都是非负的，将这个数组看作一个容器，
     * 请返回容器能装多少水。
     *
     * 思路：
     *      1、我们将这个数组可以看成一个容器，那么基于木桶理论，容器能装多少水
     *  取决于容器中最短的那个部分
     *      2、我们可以使用双指针，一个指向数组的起始位置，另一个指向数组的尾部
     *  位置，那么形成的容器的装水量，就由小的那个数字决定，然后移动这个数组，找到
     *  一个比另一个数字大的位置，同时计算其每个位置上的装水量，然后继续移动数值较小
     *  的数字
     */
    public long maxWater (int[] arr) {
        // 对特殊情况进行判断
        if (arr == null || arr.length <= 2){
            return 0;
        }
        // 声明首尾指针，以及结果存储变量
        int left = 0, right = arr.length-1;
        int leftMax = arr[left], rightMax = arr[right];
        long res = 0;
        while (left < right){
            res += leftMax - arr[left];
            res += rightMax - arr[right];
            // 移动指针
            if (arr[left] <= arr[right]){
                left++;
                leftMax = Math.max(leftMax,arr[left]);
            }else {
                right--;
                rightMax = Math.max(rightMax,arr[right]);
            }
        }
        return res;
    }

    /**
     * 实现一个特殊功能的栈，在实现栈的基本功能的基础上，再实现返回栈中最小元素的操作。
     *
     * 思路：
     *      1、要实现一个能返回当前栈中最小元素的功能，当我们向栈中push一个新的元素的之后，
     *   栈中的最小的元素，取决于之前的最小元素和当前元素的比较，如果当前元素小于之前的
     *   最小元素，那么当前的最小元素就是新push进来的这个元素，否则还是之前的。
     *      2、基于上面的思想，我们可以额外使用一个栈，来存储每个元素插入后，对应的当前栈中
     *   最小的元素，每个需要返回栈中最小元素的时候，就直接返回这个栈的栈顶元素，时间复杂度
     *   为o(1)
     *      3、对于上面的2步骤，还可以采用每次扫描的方法，时间复杂度为o(n)
     *   以及使用一个小顶堆，时间复杂度为o(logn)
     */
    public int[] getMinStack (int[][] op) {
        // 对特殊情况进行判断
        if (op == null || op.length == 0 || op[0].length == 0){
            return new int[] {};
        }
        // 声明一个存储元素的栈和存储最小元素的栈，以及存储的结果集
        Stack<Integer> valuesStack = new Stack<>();
        Stack<Integer> minValueStack = new Stack<>();
        List<Integer> tmp = new ArrayList<>();

        // 遍历op中的操作
        for (int[] o : op){
            if (o[0] == 1){
                if (valuesStack.isEmpty()){
                    minValueStack.push(o[1]);
                }else {
                    if (minValueStack.peek() >= o[1]){
                        minValueStack.push(o[1]);
                    }else {
                        minValueStack.push(minValueStack.peek());
                    }
                }
                valuesStack.push(o[1]);
            }else if (o[0] == 2){
                // 删除元素
                if (!valuesStack.isEmpty()){
                    valuesStack.pop();
                    minValueStack.pop();
                }else {
                    System.out.println("当前栈为空，删除失败！");
                }
            }else if (o[0] == 3){
                if (!valuesStack.isEmpty()){
                    // 将最小栈的栈顶元素加入到结果中
                    tmp.add(minValueStack.peek());
                }else {
                    System.out.println("当前栈为空，获取最小元素失败");
                }
            }
        }
        int[] result = new int[tmp.size()];
        for (int i = 0; i < tmp.size(); i++) {
            result[i] = tmp.get(i);
        }

        return result;
    }

    /**
     * 给定一个整数数组nums，按升序排序，数组中的元素各不相同。
     * nums数组在传递给search函数之前，会在预先未知的某个下标
     * t（0 <= t <= nums.length-1）上进行旋转，让数组变为
     * [nums[t], nums[t+1], ..., nums[nums.length-1], nums[0], nums[1], ..., nums[t-1]]。
     * 比如，数组[0,2,4,6,8,10]在下标2处旋转之后变为[6,8,10,0,2,4]
     *
     * 现在给定一个旋转后的数组nums和一个整数target，请你查找这个
     * 数组是不是存在这个target，如果存在，那么返回它的下标，如果不存在，返回-1
     *
     * 思路：
     *      这个数组有个非常好的特点就是 **局部有序**
     *      那么理想的情况下，我们可以对有序的部分通过使用二分查找来进行搜索
     */
    public int search (int[] nums, int target) {
        if (nums == null || nums.length == 0){
            return -1;
        }
        // 1、不考虑数组的局部有序性
//        for (int i = 0; i < nums.length; i++){
//            if (nums[i] == target){
//                return i;
//            }
//        }
        // 2、考虑数组的局部有序性
        int left = 0, right = nums.length-1;
        while (left <= right){
            int mid = (left + right) / 2;
            if (nums[mid] == target){
                return mid;
            }else if (nums[mid] < target){
                // 中间的数组比target小
                if (nums[mid] > nums[left] || nums[right] >= target){
                    left = mid+1;
                }else {
                    right = mid-1;
                }
            }else {
                // 中间的数字比target大
                if (nums[mid] < nums[right] || nums[left] <= target){
                    right = mid-1;
                }else {
                    left = mid+1;
                }
            }
        }
        return  -1;
    }

    /**
     * 实现函数 int sqrt(int x).
     * 计算并返回x的平方根（向下取整）
     *
     *  思路：
     *      对于一个数字x的平方根，可以发现，其一定位于0～(x/2+1)之间，
     *      那么由于0～(x/2+1)是递增排列的，那么可以使用二分查找来进行查找
     *  注意：
     *      x越大的时候，mid * mid 的结果可能超出int所能表示的最大值，所以中间最好使用
     *      long来计算，最后进行一次强转
     */
    public int sqrt (int x) {
        if (x <= 0){
            return 0;
        }
        long left = 0, right = (x/2)+1;
        long mul = 0, mid = 0;
        // 二分查找位置
        while (left <= right){
            mid = (left+right)/2;
            mul = mid * mid;
            if (mul == x){
                return (int) mid;
            }else if (mul > x){
                right = mid-1;
            }else {
                left = mid+1;
            }
        }
        if (left * left > x){
            left--;
        }
        return (int) left;
    }

    /**
     * 输入某二叉树的前序遍历和中序遍历的结果，请重建出该二叉树。
     * 假设输入的前序遍历和中序遍历的结果中都不含重复的数字。
     * 例如输入前序遍历序列{1,2,4,7,3,5,6,8}和中序遍历序列{4,7,2,1,5,3,8,6}，
     * 则重建二叉树并返回。
     *
     * 思路：
     *      二叉树的前序遍历：root —-> left --> right
     *      二叉树的中序遍历：left --> root --> right
     *
     * 1、那么在前序遍历中的第一个节点，就是根节点，在中序遍历中找到这个根节点，
     * 其左边为左子树，右边为右子树。
     * 2、那么也可以确定出前序遍历中的左子树部分 和 右子树部分
     * 3、上面就构成了一个递归解决的问题，
     *
     *
     */
    public TreeNode reConstructBinaryTree(int [] pre,int [] in) {
        if (pre == null || in == null || pre.length != in.length){
            return null;
        }
        return buildTree(pre, 0, pre.length-1,
                            in, 0, in.length-1);
    }

    private TreeNode buildTree(int[] pre, int left, int right,
                               int[] in,  int start, int end){
        if (left > right || start > end){
            return null;
        }

        // 获取root节点的val值,并创建当前根节点
        int rootVal = pre[left];
        TreeNode root = new TreeNode(rootVal);

        // root节点对应的数值在in[]中出现的位置
        int index = start;
        for (int i = start; i <= end; i++){
            if (in[i] == rootVal){
                index = i;
                break;
            }
        }
        // 计算处左子树的长度
        int leftLength = index-start;

        root.left = buildTree(pre, left+1,left+leftLength,
                                in, start,index-1);
        root.right = buildTree(pre, left+leftLength+1, right,
                                in, index+1, end);
        return root;
    }

    class TreeNode{
        int val;
        TreeNode left;
        TreeNode right;

        public TreeNode(int val) {
            this.val = val;
        }
    }

    class ListNode{
        int val;
        ListNode next;

        public ListNode() {
        }

        public ListNode(int val){
            this.val = val;
        }
    }

}
