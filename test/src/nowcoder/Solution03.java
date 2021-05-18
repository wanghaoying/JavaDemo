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
        System.out.println(minEditCost("abc","adc",5,3,2));
    }

    /**
     * 给定两个字符串str1和str2，再给定三个整数ic，dc和rc，分别代表插入、删除和替换一个字符的代价，
     * 请输出将str1编辑成str2的最小代价。
     *
     * 思路：动态规划思想：
     *      我们最终的目的是通过有限次的插入、删除、替换操作，来将str1 转换成str2
     *  同时，还要求成本代价最小。 这里假设我们要将str1 转换成 str2，其中经历的插入
     *  的次数为i，删除的次数为d，替换的次数为r，那我们可以得到整体成本代价的计算公式
     *  cost = i*ic + d*dc + r*rc，我们最终的目的就是使选择一种合适的i,d,r，使得
     *  cost最小
     *
     *      对于str1 和 str2 我们可以建立这样的状态表：
     *      dp[i][j]: str1的前i个字符，转换成，str2前j个字符，所需要付出的代价
     *
     *      显然可以得到 dp[i][0] = 删除i个字符的代价
     *                 dp[0][j] = 插入j个字符的代价
     *      那么对于dp[i][j]: (i>0 && j>0)
     *          1、如果i字符和j字符相同，那么dp[i][j] = dp[i-1][j-1]
     *          2、如果字符i和字符j不相同，那么dp[i][j] = min(insert,delete,replace)
     *          其中：（本质是就是将 str1前i个字符 转换成 str2 前j个字符 的操作情况）
     *              insert = dp[i][j-1]+1*ic (将前i个字符转换成前j-1个字符，然后再插入一个新的字符)
     *              delete = dp[i-1][j]+1*dc (将前i-1个字符转换成前j个字符，然后删除掉第i个字符)
     *              replace = dp[i-1][j-1]+1*rc (将前i-1个字符转换成前j-1个字符，然后对第i个字符进行替换)
     *      因此，最终的dp[str1.length()][str2.length] 为最终的结果
     */
    public int minEditCost (String str1, String str2, int ic, int dc, int rc) {
        // 对特殊情况的处理
        if (str1 == null || str2 == null){
            return 0;
        }
        // 当str2为空串的时候，代价就是将str1中的字符全部删掉
        if (str2.length() == 0){
            return str1.length()*dc;
        }
        // 当str1为空串的时候，代价就是插入str2.length()个字符
        if (str1.length() == 0){
            return str2.length()*ic;
        }
        // 初始化状态表
        int[][] cost = new int[str1.length()+1][str2.length()+1];
        for (int i = 0; i < cost.length; i++){
            cost[i][0] = i * dc;
        }
        for (int i = 0; i < cost[0].length; i++){
            cost[0][i] = i * ic;
        }
        // 循环填充代价状态表
        for (int i = 1; i < cost.length; i++){
            for (int j = 1; j < cost[0].length; j++){
                if (str1.charAt(i-1) == str2.charAt(j-1)){
                    cost[i][j] = cost[i-1][j-1];
                }else {
                    cost[i][j] = Math.min(
                            Math.min(
                                    cost[i][j-1]+ic,
                                    cost[i-1][j]+dc
                            ),
                            cost[i-1][j-1]+rc
                    );
                }
            }
        }
        return cost[str1.length()][str2.length()];
    }

    /**
     * 给定一个链表，请判断该链表是否为回文结构。
     *
     * 思路：1、可以将这个链表转换成数组，然后通过数组来进行是否是回文结构的判断，需要额外的开辟一个o(n)的空间
     *   时间复杂度为o(n)
     *      2、可以对链表中间位置之后的节点进行反转，然后进行比较
     */
    public boolean isPail (ListNode head) {
        if (head == null){
            return false;
        }
        // 1、链表转数组，使用中心拓展算法进行判断
//        // 声明一个数组来存储链表中的结构
//        Deque<ListNode> deque = new LinkedList<>();
//        // 将链表转换成数组的结构
//        ListNode p = head;
//        while (p != null){
//            deque.offerLast(p);
//            p = p.next;
//        }
//        // 使用中心拓展算法来进行回文结构的判断
//        while (deque.size() > 1){
//            if (deque.pollFirst().val != deque.pollLast().val){
//                return false;
//            }
//        }
//        return true;
        // 2、使用一个快慢指针找到链表的中间位置节点，然后对其后面的节点进行反转
        ListNode fast = head, slow = head;
        // 找到链表的中间节点
        while (fast != null && fast.next != null){
            slow = slow.next;
            fast = fast.next.next;
        }
        // 对slow之后的节点进行反转
        ListNode prev = null, tmp;
        while (slow != null){
            tmp = slow.next;
            slow.next = prev;
            prev = slow;
            slow = tmp;
        }

        while (prev != null){
            if (head.val != prev.val){
                return false;
            }
            head = head.next;
            prev = prev.next;
        }
        return true;
    }

    /**
     * 给定一个十进制数M，以及需要转换的进制数N。将十进制数M转化为N进制数
     *
     * 思路：2 <= N <= 16
     *      关键在于：如何对这些转换之后的N进制数字来准确的表示。比如16进制需要使用0-9 & A-F的数值来表示16
     *   进制。所以我们可以预先设置一个对应表，这个对应表在每个索引位置上对应不同的数值表示
     */
    public String solve (int M, int N) {
        if (N < 2 && N > 16){
            return "";
        }
        char[] chars = new char[] {
                '0','1','2','3','4','5','6','7','8','9',
                'A','B','C','D','E','F'
        };
        // 对负数进行特殊处理
        boolean flag = false;
        if (M < 0){
            flag = true;
            M = 0-M;
        }
        // 声明最终的结果集
        StringBuilder sb = new StringBuilder();
        // 将10进制的数字M转换成N进制的数字
        while (M != 0){
            sb.append(chars[M % N]);
            M = M / N;
        }
        return flag ? "-"+sb.reverse().toString() : sb.reverse().toString();
    }

    /**
     * 给定一个字符串数组，再给定整数k，请返回出现次数前k名的字符串和对应的次数。
     * 返回的答案应该按字符串出现频率由高到低排序。如果不同的字符串有相同出现频率，按字典序排序。
     * 对于两个字符串，大小关系取决于两个字符串从左到右第一个不同字符的 ASCII 值的大小关系。
     * 比如"ah1x"小于"ahb"，"231"<”32“
     * 字符仅包含数字和字母
     *
     * 思路：统计每个字符串出现的次数，其按照次数排序，次数相同的情况下，按照字符串的字典序进行排序
     *      可以使用一个hashmap来存储字符串和其出现的次数，然后对map中的node数组进行排序，获得
     *      前k个
     */
    public String[][] topKstrings (String[] strings, int k) {
        if (strings == null || strings.length < k){
            return new String[][] {};
        }

        // 声明一个hashmap来记录每个字符串出现的次数
        HashMap<String,Integer> hashMap = new HashMap();
        for (String string : strings){
            if (hashMap.containsKey(string)){
                hashMap.put(string,hashMap.get(string)+1);
            }else {
                hashMap.put(string,1);
            }
        }

        // 获得hashmap中存储数据的node数组
        Set<Map.Entry<String, Integer>> entries = hashMap.entrySet();
        HashMap.Entry<String,Integer>[] es = new HashMap.Entry[entries.size()];
        entries.toArray(es);
        Arrays.sort(es, new Comparator<HashMap.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                if (o1.getValue() > o2.getValue()){
                    return -1;
                }else if (o1.getValue() < o2.getValue()){
                    return 1;
                }else {
                    return o1.getKey().compareTo(o2.getKey());
                }
            }
        });

        String[][] res = new String[k][2];
        for (int i = 0; i < k; i++){
            res[i][0] = es[i].getKey();
            res[i][1] = es[i].getValue().toString();
        }

        return res;
    }

    /**
     * 数组中有一个数字出现的次数超过数组长度的一半，请找出这个数字。
     * 例如输入一个长度为9的数组[1,2,3,2,2,2,5,4,2]。由于数字2在数组中
     * 出现了5次，超过数组长度的一半，因此输出2。你可以假设数组是非空的，
     * 并且给定的数组总是存在多数元素。1<=数组长度<=50000
     *
     * 思路：假设一个数组中的多数元素是x，那么可以通过如下的方式来进行寻找：
     *      1、假设数组中的第一个数字是目标多数元素，然后对这个元素的个数+1
     *      2、遍历后面的元素，如果与之前设定的元素x不一致，那么个数-1
     *      3、当个数为0的时候，重新设置多数元素，则最后剩下的元素一定是x
     */
    public int MoreThanHalfNum_Solution(int [] array) {
        if (array == null || array.length == 0){
            return -1;
        }
        int target = array[0], num = 1;
        for (int i = 1; i < array.length; i++){
            if (array[i] == target){
                num++;
            }else {
                if (num == 0){
                    target = array[i];
                    num++;
                }else {
                    num--;
                }
            }
        }
        return target;
    }

    /**
     * 输入一棵二叉树，判断该二叉树是否是平衡二叉树。
     * 在这里，我们只需要考虑其平衡性，不需要考虑其是不是排序二叉树
     * 平衡二叉树（Balanced Binary Tree），具有以下性质：
     * 它是一棵空树或它的左右两个子树的高度差的绝对值不超过1，
     * 并且左右两个子树都是一棵平衡二叉树。
     *
     * 思路：根据平衡二叉树的性质，我们需要保证两点：
     *      1、左右子树的高度差不能超过1
     *      2、左右子树均为平衡二叉树
     */
    public boolean IsBalanced_Solution(TreeNode root) {
        if (root == null){
            return true;
        }

        int leftDepth = maxDepth(root.left);
        int rightDepth = maxDepth(root.right);

        return Math.abs(leftDepth-rightDepth) <= 1 &&
                IsBalanced_Solution(root.left) &&
                IsBalanced_Solution(root.right);
    }

    /**
     * 请写一个整数计算器，支持加减乘三种运算和括号。
     *
     * 思路：使用一个栈来保存操作数
     *      对于括号，需要优先处理，括号的优先级高于一切
     *      乘法的优先级高于加法和减法，对于减法，采用直接取负数的方式转换成加法运算
     *      对于括号来说，由于不知道括号中的字符串的长度，所以可以选择递归的方式计算
     *  括号中的内容
     */
    public int solve (String s) {
        if (s == null || s.length() == 0){
            return 0;
        }

        // 声明一个操作数栈, 以及一个保存括号的数量和对应索引位置的变量
        Stack<Integer> stack = new Stack<>();
        int num = 0, index = -1;
        char opt = '+';

        // 遍历字符串s中的内容
        int i = 0;
        while (i < s.length()){
            char c = s.charAt(i);
            int tmp = 0;
            // 如果出现括号就递归进行计算
            if (c == '('){
                while (i < s.length()){
                    c = s.charAt(i);
                    if (c == '('){
                        if (num == 0){
                            index = i;
                        }
                        num++;
                    }
                    if (c == ')'){
                        num--;
                        if (num == 0){
                            tmp = solve(s.substring(index+1,i));
                            if (opt == '+'){
                                stack.push(tmp);
                            }else if (opt == '-'){
                                stack.push(0-tmp);
                            }else {
                                stack.push(stack.pop()*tmp);
                            }
                            break;
                        }
                    }
                    i++;
                }
            }else if (c >= '0' && c <= '9'){
                while (i < s.length() && (c = s.charAt(i)) >= '0' && c <= '9'){
                    tmp *= 10;
                    tmp += c - '0';
                    i++;
                }
                i--;
                if (opt == '+'){
                    stack.push(tmp);
                }else if (opt == '-'){
                    stack.push(0-tmp);
                }else {
                    stack.push(stack.pop()*tmp);
                }
            }else {
                opt = c;
            }
            i++;
        }

        int res = 0;
        while (!stack.isEmpty()){
            res += stack.pop();
        }
        return res;
    }

    /**
     *  给定一个 n * m 的矩阵 a，从左上角开始每次只能向右或者向下走，
     *  最后到达右下角的位置，路径上所有的数字累加起来就是路径和，
     *  输出所有的路径中最小的路径和。
     *
     *  思路：对于某一个位置，只能通过他上面的位置或者左边的位置到达，
     *  如果要求到达他的路径和最小，那么就要选择minPath(上面的位置，左面的位置)
     *  因此可以使用动态规划的方式来进行解决，需要使用一个状态表来暂存每个位置上
     *  的最小路径和
     */
    public int minPathSum (int[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0){
            return 0;
        }
        // 声明一个状态表
        int[][] minPathSum = new int[matrix.length][matrix[0].length];

        // 填充状态表
        minPathSum[0][0] = matrix[0][0];

        // 填充第一行
        for (int i = 1; i < minPathSum[0].length; i++){
            minPathSum[0][i] = minPathSum[0][i-1] + matrix[0][i];
        }
        // 填充第一列
        for (int i = 1; i < minPathSum.length; i++){
            minPathSum[i][0] = minPathSum[i-1][0] + matrix[i][0];
        }

        int up = 1, down = minPathSum.length-1;
        int left = 1, right = minPathSum[0].length-1;
        while (up <= down && left <= right){
            int i = up, j = left;
            // 先填充行
            while (j <= right){
                minPathSum[i][j] = Math.min(minPathSum[i-1][j],
                            minPathSum[i][j-1]) + matrix[i][j];
                j++;
            }
            j = left;
            // 在填充列
            while (i <= down){
                minPathSum[i][j] = Math.min(minPathSum[i-1][j],
                            minPathSum[i][j-1]) + matrix[i][j];
                i++;
            }
            up++;
            left++;
        }

        return minPathSum[matrix.length-1][matrix[0].length-1];
    }

    /**
     * 给定一个无序单链表，实现单链表的排序(按升序排序)。
     * 例：[1,3,2,4,5]  --> {1,2,3,4,5}
     *
     * 思路：1、可以采用类似冒泡排序的方式，每次都遍历整个链表，寻找到后面链表中
     *  数字最小的那个节点，然后交换两个节点，但是还需要保存被交换节点的prev节点
     *      2、归并排序：根据前面那个合并K个有序链表的思想，可以将我们的当前链表
     *  看成n个待排序的有序链表，只是每个链表上的节点只有一个。时间复杂度o(nlogn)
     *      3、对链表中的数字进行值排序，而不是交换节点。遍历整个链表，用一个array
     *  存储这个list中的所有节点的val，然后对这个array进行排序，然后再对原先的链表
     *  进行遍历赋值。 代价就是会改变每个节点的值 时间复杂度为o(nlogn)
     */
    public ListNode sortInList (ListNode head) {
        if (head == null){
            return null;
        }
        // 2、归并排序
//        ArrayList<ListNode> arrayList = new ArrayList<>();
//        ListNode p;
//        while (head != null){
//            p = head;
//            head = head.next;
//            p.next = null;
//            arrayList.add(p);
//        }
//        return mergeKLists(arrayList);

        // 3、只对值排序
        ArrayList<Integer> arrayList = new ArrayList<>();
        ListNode p = head;
        while ( p!= null){
            arrayList.add(p.val);
            p = p.next;
        }
        arrayList.sort(null);
        p = head;
        int index = 0;
        while (p != null){
            p.val = arrayList.get(index++);
            p = p.next;
        }
        return head;
    }

    /**
     * 求给定二叉树的最大深度，
     * 最大深度是指树的根结点到最远叶子结点的最长路径上结点的数量
     *
     * 思路：二叉树的最大深度，等于 Max(root.left, root.right)+1
     *      根据上面的递归公式，可以很快写出最大深度
     */
    public int maxDepth (TreeNode root) {
        if (root == null){
            return 0;
        }
        return Math.max(maxDepth(root.left),maxDepth(root.right))+1;
    }

    /**
     * 给定一个字符串，请编写一个函数判断该字符串是否回文。如果回文请返回true，
     * 否则返回false。
     *
     * 思路：
     *      采用中心拓展算法，但是为了避免奇数和偶数长度的回文串的讨论，可以对这些
     *  str中间和两端插入 # 来进行填充
     *      优化：本质上是寻找到合适的left 和 right进行比对，那么对与奇数长度和
     *  偶数长度的字符串，只需要分别确定其left 和 right 就可以了
     */
    public boolean judge (String str) {
        // 对特殊情况进行判断，这里认为null不是回文串，但是长度小于2的字符串都是回文串
        if (str == null){
            return false;
        }
        if (str.length() <= 1){
            return true;
        }
        // 对str中的内容进行 # 填充
        char[] chars = str.toCharArray();

        // 确定left 和 right指针，然后对其进行遍历对比
        int left, right;
        left = chars.length/2 -1;
        if (chars.length % 2 == 0){
            right = chars.length/2;
        }else {
            right = chars.length/2 +1;
        }
        // 根据中心拓展算法进行判断是否是回文串
        while (left >= 0 && right < chars.length){
            if (chars[left] != chars[right]){
                return false;
            }
            left--;
            right++;
        }
        return true;
    }

    /**
     * 给一个01矩阵，1代表是陆地，0代表海洋，如果两个1相邻，
     * 那么这两个1属于同一个岛。我们只考虑上下左右为相邻。
     *
     * 岛屿: 相邻陆地可以组成一个岛屿（相邻:上下左右） 判断岛屿个数。
     *
     * 思路：在遇到1的时候就进行dfs遍历周围的节点，除非周围的节点均为0，然后岛屿数量加1
     * ，同时对访问过的节点进行记录，如果这个1已经在某次遍历中访问过了，那么就不需要再进行
     * dfs遍历了
     */
    public int solve (char[][] grid) {
        if (grid == null || grid.length == 0 || grid[0].length == 0){
            return 0;
        }
        // 声明岛屿数量变量 和 访问标志
        int num = 0;
        boolean[][] flags = new boolean[grid.length][grid[0].length];

        for (int i = 0; i < grid.length; i++){
            for (int j = 0; j < grid[0].length; j++){
                num += dfs(grid,i,j,flags);
            }
        }
        return num;
    }

    private int dfs(char[][] grid, int i, int j, boolean[][] flags){
        // 对特殊情况进行判断
        if (grid[i][j] == '0' || flags[i][j]){
            return 0;
        }
        flags[i][j] = true;

        // 当前的i，j 对应的位置为1
        // 向上
        if (i > 0){
            dfs(grid,i-1,j,flags);
        }
        // 向右
        if (j < grid[0].length-1){
            dfs(grid,i,j+1,flags);
        }
        // 向下
        if (i < grid.length-1){
            dfs(grid, i+1, j, flags);
        }
        // 向左
        if (j > 0){
            dfs(grid,i,j-1,flags);
        }

        return 1;
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
