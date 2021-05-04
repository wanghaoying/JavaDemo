import org.junit.Test;

import java.util.*;

/**
 * 刷题的第三个文件，用来记录刷题思路
 */
public class Solution02 {

    @Test
    public void test(){

    }

    /**
     * 对于一个给定的链表，返回环的入口节点，如果没有环，返回null
     * 拓展：你能给出不利用额外空间的解法么？
     *
     * 思路1：类似判断是否存在环一样，使用一个hashset存储key，第一个重复的节点就是环的入口节点
     *
     * 思路2：类似于判断是否存在环那样的快慢指针，下面有如下假设：
     * A：链表的起始节点
     * B：链表的环的起始节点
     * C：快慢指针首次相遇的节点
     * D：慢指针首次到达B时，快指针所在的位置
     * 假设AB长度为a，BC长度为b，那么则有DB长度为b，CD长度为a-b，CB长度为a
     * 那么则有这样的思路，当快慢指针到达C后，快指针从头开始走，每次直走一步，那么下次他们再次遇到
     * 的位置就是环的起点
     *
     */
    public ListNode detectCycle(ListNode head) {
        if (head == null){
            return head;
        }

        // 思路1：使用一个额外的空间hashset存储之前出现过的node
//        HashSet<ListNode> hashSet = new HashSet<>();
//        while (head != null){
//            if (hashSet.contains(head)){
//                return head;
//            }else {
//                hashSet.add(head);
//            }
//            head = head.next;
//        }

        ListNode fast = head, slow = head;
        // 规避链表长度小于2的情况
        if (fast.next != null){
            fast = fast.next.next;
            slow = slow.next;
        }else {
            return null;
        }

        // 寻找到相遇的节点C
        while (fast != null && fast != slow){
            if (fast.next != null){
                fast = fast.next.next;
                slow = slow.next;
            }else {
                return null;
            }
        }
        // 不存在环
        if (fast == null){
            return null;
        }

        // 存在环
        fast = head;
        while (fast != slow){
            fast = fast.next;
            slow = slow.next;
        }

        return fast;
    }

    /**
     * 给出两个有序的整数数组A和B，请将数组B合并到数组A中，变成一个有序的数组
     * 注意：
     * 可以假设A数组有足够的空间存放B数组的元素，A和B中初始的元素数目分别为M和 N
     *
     * 思路1：可以开辟一个额外的地址空间，O(m+n)的地址空间，类似于归并排序中的两个有序子数组合并那样
     * 将数据合并到临时数组C中，然后再回调到A中
     *
     * 思路2：使用三个指针，指针i，j分别指向数组A 和 数组B的最后一个元素，而指针K指向数组A合并完数组
     * B之后的最后一个元素的位置，每次挑选i或者j中最大的数字进行填充，空间复杂度为O(1)，而且只需要
     * 对数组扫描一次
     */
    public void merge(int A[], int m, int B[], int n) {
//        if (A == null || B == null || A.length != m || B.length != n){
//            return;
//        }

        // 思路1，开辟额外的地址空间
//        int[] c = new int[m+n];
//        int i = 0, j = 0, offset = 0;
//        while (i < m && j < n){
//            if (A[i] <= B[j]){
//                c[offset++] = A[i++];
//            }else {
//                c[offset++] = B[j++];
//            }
//        }
//        // 将未处理完的数组中的元素，继续填充
//        while (i < m){
//            c[offset++] = A[i++];
//        }
//        while (j < n){
//            c[offset++] = B[j++];
//        }
//
//        for (int k = 0; k < m+n; k++){
//            A[k] = c[k];
//        }

        // 思路2，使用三个指针完成操作
        int i = m-1, j = n-1, k = m+n-1;
        while (i >= 0 && j >= 0){
            if (A[i] >= B[j]){
                A[k--] = A[i--];
            }else {
                A[k--] = B[j--];
            }
        }

        while (i >= 0){
            A[k--] = A[i--];
        }
        while (j >= 0){
            A[k--] = B[j--];
        }
    }

    /**
     * 判断给定的链表中是否有环。如果有环则返回true，否则返回false。
     * 你能给出空间复杂度的解法么？
     *
     * 思路1：使用一个hashset来存储每个节点，如果有一个节点已经在hashset中出现过了，那么这个节点
     * 就是环的入口节点，这里有个坑（hashset中比较是否存在某个key的时候，如果hashcode相同的情况下，
     * 会使用equals比较这两个key，如果重写了ListNode的hashcode方法，则存在一种可能：两个不同的key
     * 对象，但是其hashcode码相同，key1.equals(key2) 为true，那么虽然key2没有插入过hashset。但是依然
     * 会被判定存在）
     *  比如下面的这段代码就会打印出“true”
     *  ```java
     *  HashSet<String> hashSet = new HashSet<>();
     *  String s1 = new String("hello world");
     *  String s2 = new String("hello world");
     *  hashSet.add(s1);
     *  System.out.println(hashSet.contains(s2));
     *  print：
     *  true
     *  ```
     *
     * 思路2：使用一组快慢指针，快指针每次走两步，慢指针每次走1步，如果存在环，那么一定还有快满指针
     * 相遇的情况，这个使用的是直接地址比较，就没有上面的那个问题
     */
    public boolean hasCycle(ListNode head) {
        if (head == null){
            return false;
        }
        // 声明两个快满指针，并针对特殊情况进行处理
        ListNode fast = head, slow = head;
        if (fast.next != null){
            fast = fast.next.next;
            slow = slow.next;
        }else {
            return false;
        }
        // 判断是否存在环
        while (fast != null && fast != slow){
            if (fast.next != null){
                fast = fast.next.next;
                slow = slow.next;
            }else {
                return false;
            }
        }
        if (fast == slow){
            return true;
        }

        return false;
    }

    /**
     * 将给出的链表中的节点每 k 个一组翻转，返回翻转后的链表
     * 如果链表中的节点数不是 k 的倍数，将最后剩下的节点保持原样
     * 你不能更改节点中的值，只能更改节点本身。
     * 要求空间复杂度 O(1)
     * 例如：
     * 给定的链表是1→2→3→4→5
     * 对于 k=2, 你应该返回 2→1→4→3→5
     * 对于 k=3, 你应该返回 3→2→1→4→5
     *
     * 思路：先统计链表的长度，寻得需要翻转多少组长度为K的链表
     * 然后对着N组链表按照要求进行翻转
     */
    public ListNode reverseKGroup (ListNode head, int k) {
        if (k <= 1){
            return head;
        }
        // 计算链表的长度
        int listLength = 0;
        ListNode p = head;
        while (p != null){
            listLength++;
            p = p.next;
        }
        // 如果链表的长度小于k，则直接返回原来的链表
//        if (listLength < k){
//            return head;
//        }
        // 计算有多少组需要翻转
        int n = listLength / k;
        // 对链表中的每组进行翻转
        ListNode newHead = new ListNode(0);
        newHead.next = head;
        p = newHead;
        for (int i = 0; i < n; i++){
            ListNode tmp, prev = p;
            for (int j = 0; j < k; j++){
                tmp = head.next;
                head.next = prev;
                prev = head;
                head = tmp;
            }
            tmp = p.next;
            tmp.next = head;
            p.next = prev;
            p = tmp;
        }
        return newHead.next;
    }

    /**
     * leetcode 227: 基本计数器II
     * 给你一个字符串表达式 s ，请你实现一个基本计算器来计算并返回它的值。
     * 整数除法仅保留整数部分。
     *
     * 例： 输入：s = "3+2*2"    输出：7
     *
     * 思路：使用一个栈来存储数字和操作符，或者用一个栈存储数字，另一个栈存储操作符
     * @param s
     * @return
     */
    public int calculate(String s) {
        if (s == null || s.trim().length() == 0){
            return 0;
        }
        s = s.trim();
        // 声明一个操作数栈 和一个 运算符栈
        Stack<Integer> optNum = new Stack<>();
        Stack<Character> optChar = new Stack<>();

        int num = 0;
        // 每一个操作符和后面的一个数字绑定在一起，假设初始的第一个数字与sign绑定在一起
        char sign = '+';
        for (int i = 0; i < s.length(); i++){
            char c = s.charAt(i);
            // 计算数字
            if (c >= '0' && c <= '9'){
                num = num * 10 + (c - '0');
            }
            // 针对操作符进行处理
            if (c < '0' && c != ' ' || i == s.length()-1){
                if (sign == '+' || sign == '-'){
                    optChar.push(sign);
                    optNum.push(num);
                }

                if (sign == '*'){
                    optNum.push(optNum.pop() * num);
                }

                if (sign == '/'){
                    optNum.push(optNum.pop() / num);
                }

                sign = c;
                num = 0;
            }
        }
        // 针对待计算的加减法再重新计算一次
        int z = 0, f = 0;
        while (optNum.size() > 0){
            if (optChar.pop() == '+'){
                z += optNum.pop();
            }else {
                f += optNum.pop();
            }
        }
        return z - f;
    }

    /**
     * 给定一个数组arr，返回arr的最长无的重复子串的长度(无重复指的是所有数字都不相同)。
     *
     * 思路1：暴力求解，计算以每个数组中的元素为起点的无重复子串的长度，然后取所有情况下的最大值
     * 时间复杂度为O(n^2)
     *
     * 思路2：上面的问题中存在重复求解的问题，那么我们可以使用一个map存储每个数组出现的位置索引，
     * 当遇到一个位置上的数字的时候，去map中查看这个数字是否出现过，如果没有出现过，就把当前的位置
     * 作为value放入map中，否则计算maxLength，然后将这个数字的value，替换成当前的位置
     *
     * @param arr
     * @return
     */
    public int maxLength (int[] arr) {
        if (arr == null || arr.length == 0){
            return 0;
        }
        int maxLength = 1, start = 0;
        // 声明一个hashmap来存储这些数据
        HashMap<Integer,Integer> hashMap = new HashMap<>();

        for (int i = 0; i < arr.length; i++){
            if (hashMap.containsKey(arr[i])){
                maxLength = Math.max(maxLength, i-start);
                start = Math.max(start,hashMap.get(arr[i])+1);
            }
            hashMap.put(arr[i],i);
        }
        // 对于最后一次长度的判断
        maxLength = Math.max(maxLength,arr.length - start);

        return maxLength;
    }

    /**
     * 给定一个数组arr，返回子数组的最大累加和
     * 例如，arr = [1, -2, 3, 5, -2, 6, -1]，所有子数组中，[3, 5, -2, 6]可以累加出最大的
     * 和12，所以返回12.  题目保证没有全为负数的数据
     * [要求]
     * 时间复杂度为O(n)，空间复杂度为O(1)
     *
     * 思路：子数组的最大累加和，当前的子数组是否向后拓展，在于当前的子数组的累加和是否是大于0的，
     * 如果大于0，这说明当前子数组向后拓展之后，可能得到最大的累加和，如果当前的子数组和小于等于
     * 0，则向后拓展后并不能比不拓展更好
     * @param arr
     * @return
     */
    public int maxsumofSubarray (int[] arr) {
        if (arr == null || arr.length == 0){
            return 0;
        }
        int max = arr[0], tmp = arr[0];

        for (int i = 1; i < arr.length; i++){
            if (tmp > 0){
                tmp += arr[i];
            }else {
                tmp = arr[i];
            }
            max = Math.max(max,tmp);
        }

        return max;
    }

    /**
     * 一只青蛙一次可以跳上1级台阶，也可以跳上2级。求该青蛙跳上一个n级的台阶总共有多少种跳法
     * （先后次序不同算不同的结果）。
     *
     * 思路：动态规划 ，动态规划方程 F(n) = F(n-1) + F(n-2)
     * @param target
     * @return
     */
    public int jumpFloor(int target) {
        if (target <= 0)
            return 0;
        if (target == 1)
            return 1;
        int p = 1, q = 1, tmp = 0;
        for (int i = 2; i <= target; i++){
            tmp = p;
            p = p + q;
            q = tmp;
        }
        return p;
    }

    /**
     * 用两个栈来实现一个队列，完成队列的Push和Pop操作。 队列中的元素为int类型。
     *
     * 队列：先进先出，栈：后进先出
     *
     * 思路：stack1 专门用来push数据，stack2用来用来pop数据，如果stack2中没有数据的时候，
     * 将stack1中的数据全部出栈再入到stack2中
     */
    Stack<Integer> stack1 = new Stack<Integer>();
    Stack<Integer> stack2 = new Stack<Integer>();

    public void push(int node) {
        stack1.push(node);
    }

    public int pop() {
        if (stack2.isEmpty()){
            while (!stack1.isEmpty()){
                stack2.push(stack1.pop());
            }
        }
        return stack2.pop();
    }

    /**
     *  将两个有序的链表合并为一个新链表，要求新的链表是通过拼接两个链表的节点来生成的，
     *  且合并后新链表依然有序。
     */
    public ListNode mergeTwoLists (ListNode l1, ListNode l2) {
        if (l1 == null)
            return l2;
        if (l2 == null)
            return l1;
        ListNode newHead = new ListNode(0);
        ListNode p = newHead;

        while (l1 != null  && l2 != null){
            if (l1.val <= l2.val){
                p.next = l1;
                l1 = l1.next;
            }else {
                p.next = l2;
                l2 = l2.next;
            }
            p = p.next;
        }

        if (l1 != null)
            p.next = l1;
        if (l2 != null)
            p.next = l2;

        return newHead.next;
    }

    /**
     * 给出一个整数数组，请在数组中找出两个加起来等于目标值的数，
     * 你给出的函数twoSum 需要返回这两个数字的下标（index1，index2），需要满足 index1 小于index2.。注意：下标是从1开始的
     * 假设给出的数组中只存在唯一解
     * 例如：
     * 给出的数组为 {20, 70, 110, 150},目标值为90
     * 输出 index1=1, index2=2
     *
     * 思路1：双层循环，暴力求解 O(n^2)
     *
     * 思路2：使用一个map暂存结果集，实现O(n)复杂度求解
     * @param numbers
     * @param target
     * @return
     */
    public int[] twoSum (int[] numbers, int target) {
        if (numbers == null || numbers.length < 2)
            return new int[] {};
        // 1、暴力求解
//        for (int i = 0; i < numbers.length-1; i++){
//            for (int j = i+1; j < numbers.length; j ++){
//                if (numbers[i] + numbers[j] == target)
//                    return new int[] {i+1,j+1};
//            }
//        }
//        return new int[] {};
        // 2、使用hashmap暂存结果
        HashMap<Integer,Integer> hashMap = new HashMap<>();
        for (int i = 0; i < numbers.length; i++){
            if (hashMap.containsKey(numbers[i])){
                return new int[] {hashMap.get(numbers[i]),i+1};
            }else {
                hashMap.put(target-numbers[i],i+1);
            }
        }
        return new int[] {};
    }

    /**
     * 给定一个二叉树，返回该二叉树层序遍历的结果，（从左到右，一层一层地遍历）
     * 例如：给定的二叉树是{3,9,20,#,#,15,7},
     * 该二叉树层序遍历的结果是
     * [
     * [3],
     * [9,20],
     * [15,7]
     * ]
     *
     * 思路：使用一个队列，存储每一层的节点，然后一次取出上一层的节点，将其子节点存入队列中
     *
     * @param root
     * @return
     */
    public ArrayList<ArrayList<Integer>> levelOrder (TreeNode root) {
        if (root == null) {
            return new ArrayList<>();
        }
        // 声明一个队列
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        // 声明结果集
        ArrayList<ArrayList<Integer>> arrayLists = new ArrayList<>();

        // 层序遍历
        while (!queue.isEmpty()){
            ArrayList<Integer> arrayList = new ArrayList<>();
            for (int i = queue.size(); i > 0; i--){
                TreeNode t = queue.poll();
                arrayList.add(t.val);
                if (t.left != null) {
                    queue.offer(t.left);
                }
                if (t.right != null) {
                    queue.offer(t.right);
                }
            }
            arrayLists.add(arrayList);
        }

        return arrayLists;
    }

    /**
     * 有一个整数数组，请你根据快速排序的思路，找出数组中第K大的数。
     *
     * 给定一个整数数组a,同时给定它的大小n和要找的K(K在1到n之间)，请返回第K大的数，保证答案存在。
     * @param a
     * @param n
     * @param K
     * @return
     */
    public int findKth(int[] a, int n, int K) {
        if (a == null || a.length == 0 || K <= 0 || K > a.length) {
            return -1;
        }
        // 第K大，则为第n-K+1小的数字，则尝试取最小的n-K+1个数字
        ArrayList<Integer> arrayList = GetLeastNumbers_Solution(a, n - K + 1);
        return arrayList.get(arrayList.size()-1);
    }

    /**
     * 给定一个数组，找出其中最小的K个数。例如数组元素是4,5,1,6,2,7,3,8这8个数字，
     * 则最小的4个数字是1,2,3,4。如果K>数组的长度，那么返回一个空的数组
     *
     * 思路：返回的目标数组，无需有序，所以这个题的题意类似快速排序中getIndex那一步，
     * 因此可以通过快速排序的思路来对问题进行求解。时间复杂度大概是O(n)
     *      如果要求返回的数组有序，则可以先对数据进行排序，然后再返回前K个，或者依然
     * 按照前面的方法，获取前K个元素的无序形式，然后在对这些无序的数据进行排序
     *
     * 思路2：将这个数组转换成小顶堆，抛出K个数字即可
     *
     * @param input
     * @param k
     * @return
     */
    public ArrayList<Integer> GetLeastNumbers_Solution(int [] input, int k) {
        if (input == null || input.length == 0 || k > input.length || k <= 0)
            return new ArrayList<>();

        findTopK(input, k, 0, input.length-1);
        ArrayList<Integer> arrayList = new ArrayList<>(input.length);
        for (int i = 0; i < k; i++){
            arrayList.add(input[i]);
        }
        return arrayList;
    }

    private int findTopK(int[] nums, int k, int i, int j){
        if (i >= j) {
            return i;
        }
        int mid = getIndex(nums,i,j);
        if (mid > k-1){
            return findTopK(nums, k, i, mid-1);
        }else if (mid < k-1){
            return findTopK(nums,k,mid+1,j);
        }else {
            return mid;
        }
    }

    /**
     * 分别按照二叉树先序，中序和后序打印所有的节点。
     * @param root
     * @return
     */
    public int[][] threeOrders (TreeNode root) {
        if (root == null)
            return new int[][] {};

        int[][] res = new int[3][];
        // 先序
        ArrayList<Integer> prevOrder = prevOrder(root);
        int[] prev = new int[prevOrder.size()];
        for (int i = 0; i < prev.length; i++) {
            prev[i] = prevOrder.get(i);
        }
        res[0] = prev;
        // 中序
        ArrayList<Integer> inOrder = inOrder(root);
        int[] in = new int[inOrder.size()];
        for (int i = 0; i < in.length; i++) {
            in[i] = inOrder.get(i);
        }
        res[1] = in;
        // 后序
        ArrayList<Integer> afterOrder = afterOrder(root);
        int[] after = new int[afterOrder.size()];
        for (int i = 0; i < after.length; i++) {
            after[i] = afterOrder.get(i);
        }
        res[2] = after;

        return res;
    }
    // 先序遍历
    private ArrayList<Integer> prevOrder(TreeNode root){
        ArrayList arrayList = new ArrayList();
        if (root == null)
            return arrayList;
        arrayList.add(root.val);
        arrayList.addAll(prevOrder(root.left));
        arrayList.addAll(prevOrder(root.right));
        return arrayList;
    }
    // 中序遍历
    private ArrayList<Integer> inOrder(TreeNode root){
        ArrayList arrayList = new ArrayList();
        if (root == null)
            return arrayList;
        arrayList.addAll(inOrder(root.left));
        arrayList.add(root.val);
        arrayList.addAll(inOrder(root.right));
        return arrayList;
    }
    // 后序遍历
    private ArrayList<Integer> afterOrder(TreeNode root){
        ArrayList arrayList = new ArrayList();
        if (root == null)
            return arrayList;
        arrayList.addAll(afterOrder(root.left));
        arrayList.addAll(afterOrder(root.right));
        arrayList.add(root.val);
        return arrayList;
    }

    /**
     * 设计LRU缓存结构，该结构在构造时确定大小，假设大小为K，并有如下两个功能
     * set(key, value)：将记录(key, value)插入该结构
     * get(key)：返回key对应的value值
     *
     * [要求]
     * set和get方法的时间复杂度为O(1)
     * 某个key的set或get操作一旦发生，认为这个key的记录成了最常使用的。
     * 当缓存的大小超过K时，移除最不经常使用的记录，即set或get最久远的。
     * 若opt=1，接下来两个整数x, y，表示set(x, y)
     * 若opt=2，接下来一个整数x，表示get(x)，若x未出现过或已被移除，则返回-1
     * 对于每个操作2，输出一个答案
     *
     * 思路：LRU：最近最少使用算法 （Least Recently Used）
     *      set(key,value) 和 get(key) 方法的时间复杂度是O(1)，能实现O(1)的常用数据结构有
     *   数组和map(map底层也是依靠的数组)，所以这里的底层结构考虑使用map，来存储key-value
     *      又因为LRU的相关性质，所以这里选用LinkedHashMap来做底层的数据是够，并在这个基础上
     *   进行包装（重写他的 removeEldestEntry 方法，这个方法的调用链是：put --> afterNodeInsertion
     *   --> removeEldestEntry，主要是对头节点是否删除的判断）
     *
     *      另外注意：LinkedHashMap中的，accessOrder属性默认是false，也就是get操作后，默认是
     *   不对节点进行移动的，LRU功能需要打开这个移动的功能
     *
     * 示例：
     *   Input：[[1,1,1],[1,2,2],[1,3,2],[2,1],[1,4,4],[2,2]] ,  3
     *   Output：[1,-1]
     *
     * @param operators int整型二维数组 the ops
     * @param k int整型 the k
     * @return int整型一维数组
     */
    public int[] LRU (int[][] operators, int k) {
        if (operators == null || operators.length == 0 || operators[0].length == 0)
            return null;
        if (k < 0)
            return null;
        // 声明一个LRU接口来缓存数据
        LRUStruct lruStruct = new LRUStruct(k);

        int[] res = new int[operators.length];
        int offset = 0;
        for (int[] operator : operators){
            int oper = operator[0];
            if (oper == 1){
                lruStruct.set(operator[1],operator[2]);
            }else {
                res[offset++] = lruStruct.get(operator[1]);
            }
        }
        return Arrays.copyOf(res,offset);
    }

    class LRUStruct extends LinkedHashMap<Integer,Integer> {
        private int capacityK;

        public LRUStruct(){}

        public LRUStruct(int K){
            super(16, (float) 0.75,true);
            this.capacityK = K;
        }

        public void set(Integer key, Integer value){
            super.put(key,value);
        }

        public Integer get(Integer key){
            Integer res = super.get(key);
            if (res == null)
                return -1;
            return res;
        }

        protected boolean removeEldestEntry(Map.Entry<Integer, Integer> eldest) {
            if (size() > capacityK)
                return true;
            else
                return false;
//            return super.removeEldestEntry(eldest);
        }
    }

    /**
     * 给定一个数组，请你编写一个函数，返回该数组排序后的形式。
     * 数组的长度不大于100000，数组中每个数的绝对值不超过10^9
     *
     * 思路：对数组进行排序，常用的排序方法有：冒泡排序、选择排序、插入排序、归并排序、快速排序、堆排序等。
     *
     * 这里主要实现了： 冒泡排序、归并排序、快速排序 和 堆排序
     *
     * @param arr
     * @return
     */
    public int[] MySort (int[] arr) {
        if (arr == null || arr.length < 2)
            return arr;

        // 冒泡排序：时间复杂度o(n^2)，空间复杂度o(1)，时间复杂度比较稳定，无论什么情况，都会检查n^2次
        // 主要思想是：通过每一次查找，将后续序列中最小的数字交换到第i位上
//        for (int i = 0; i < arr.length; i++){
//            for (int j = i+1; j < arr.length; j++){
//                if (arr[i] > arr[j]){
//                    swap(arr,i,j);
//                }
//            }
//        }
//        return arr;

        // 归并排序：时间复杂度为o(nlogn)，空间复杂度为o(n)，时间复杂度比较稳定，无论什么情况，都会有分段合并的过程
        // 主要思想是：对一个大的数组对象，通过分治法，将其分解成比较小的问题，然后再对小问题的结果进行合并，
        // 最终得到大问题的答案。归并排序，底层可以看作是多次有序数组合并的排序。所以归并的这个思路可以用在
        // 合并多个有序数组的问题中。
//        mergeSort(arr,0,arr.length-1);
//        return arr;

        // 快速排序：时间复杂度为o(nlogn)，空间复杂度为o(1)，时间复杂度不稳定，极端情况下会退化成o(n^2)
        // 主要思想是：快速排序也是分治法的思想，找到一个数字，通过一次遍历，使得这个数字左边都是小于这个数字的(无需保证有序)，
        // 右边都是大于这个数字的(无需保证有序)，然后对这两边部分分别再进行快速排序
//        quickSort(arr,0, arr.length-1);
//        return arr;

        // 堆排序：时间复杂度为o(nlogn)，空间复杂度为o(1)，对一个数组建堆的时间复杂度是o(n)，调整堆的时间复杂度是o(logn)
        // 主要思想是：通过构建一个完全二叉树，（以小顶堆为例）这个完全二叉树满足以下的性质，父节点小于左子节点 和 右子节点，
        // 这样根节点，就是全部数字中的最小的数字，只要依次抛出根节点，然后调整堆，抛出n次，就可以得到一个有序的数组
        Heap heap = new Heap(arr);
        int[] res = new int[arr.length];
        for (int i = 0; i < arr.length; i++){
            res[i] = heap.poll();
        }
        return res;
    }

    // 维护一个小顶堆，暂时不支持扩容
    class Heap{
        private int[] table;
        private int size;

        public Heap(){}

        public Heap(int capacity){
            if (capacity < 0){
                throw new IllegalArgumentException("capacity 不能小于0");
            }
            this.table = new int[capacity];
            this.size = 0;
        }

        // 接收用户的一个输入，然后对其进行堆化，排序
        public Heap(int[] nums){
            this.table = nums;
            this.size = nums.length;

            // 赋值完成之后，对table数组进行堆化
            for (int i = size/2; i >= 0; i--){
                heapfyUptoDown(i);
            }
        }

        public boolean offer(int n){
            if (size == table.length){
                return false;
            }
            table[size++] = n;
            heapfyDowntoUp();
            return true;
        }

        public int poll(){
            if (size == 0)
                return -1;

            int result = table[0];
            swap(table,0,--size);
            heapfyUptoDown(0);
            return result;
        }

        // 堆化，从上到下
        private void heapfyUptoDown(int n){
            if (size <= 0)
                return;

            int index = n;
            int min;

            while (2*index+2 < size || 2*index+1 < size){
                min = table[index];
                // 先找到左右节点中最小的数字
                min = table[2*index+1] < min ? table[2*index+1] : min;

                if (2*index+2 < size)
                    min = table[2*index+2] < min ? table[2*index+2] : min;

                // 如果当前index已经是最小的了，那么就不需要再向下寻找了
                if (min == table[index])
                    break;
                // 找到最小的那个点进行交换，并继续比较
                if (min == table[2*index+1]){
                    swap(table,index,2*index+1);
                    index = 2*index+1;
                } else{
                    swap(table,index,2*index+2);
                    index = 2*index+2;
                }
            }
        }

        // 堆化，从下到上
        private void heapfyDowntoUp(){
            if (size <= 0)
                return;
            int index = size-1;
            int root = (index-1)/2;
            while (root >= 0){
                if (table[root] > table[index]){
                    swap(table,root,index);
                    index = root;
                    root = (index-1)/2;
                }
                if (index == 0)
                    break;
            }
            System.out.println("heapfyDowntoUp 执行完成...");
        }

    }

    private void quickSort(int[] nums, int i, int j){
        if (i >= j)
            return;
        int index = getIndex(nums, i, j);
        quickSort(nums, i, index-1);
        quickSort(nums, index+1, j);
    }

    /**
     * 找个一个数字，使其左边都是小于这个数字的，右边都是大于这个数字的，返回这个数字在数组中的位置
     *
     * 可以通过使用一个随机取元或者取中间值的方式，降低极端情况发生的概率
     * @param nums
     * @param i
     * @param j
     * @return
     */
    private int getIndex(int[] nums, int i, int j){
        int mid = (i+j)/2;
        swap(nums, i, mid);
        int n = nums[i];

        int left = i, right = j;
        while (left < right){
            while (left <= j && nums[left] <= n){
                left++;
            }
            while (right >= i && nums[right] > n){
                right--;
            }
            if (left < right) {
                swap(nums,left,right);
            }
        }
        swap(nums,i,right);
        return right;
    }

    /**
     * 归并排序的入口方法，使用了递归的方式来实现
     * @param nums  需要排序的数组
     * @param i     需要排序的起始位置
     * @param j     需要排序的结束位置(包含节点j)
     */
    private void mergeSort(int[] nums, int i, int j){
        if (i >= j)
            return ;
        int mid = (i+j)/2;
        // 对左边进行mergeSort
        mergeSort(nums, i, mid);
        // 对右边进行mergeSort
        mergeSort(nums,mid+1, j);
        // 合并这两个有序的序列
        merge(nums, i, mid, j);
    }

    /**
     * 对数组中两个有序的片段进行合并
     * @param nums    目标数组
     * @param i       第一个序列的起始位置
     * @param mid     第一个序列的结束位置，第二个序列的起始位置为mid+1
     * @param j       第二个序列的结束位置
     */
    private void merge(int[] nums, int i, int mid, int j){
        int[] tmp = new int[j-i+1];
        // 对两个有序序列进行合并
        int left = i, right = mid+1;
        int offset = 0;
        while (left <= mid && right <= j){
            if (nums[left] <= nums[right]){
                tmp[offset++] = nums[left++];
            }else {
                tmp[offset++] = nums[right++];
            }
        }
        while (left <= mid){
            tmp[offset++] = nums[left++];
        }
        while (right <= j){
            tmp[offset++] = nums[right++];
        }

        // 对合并后的数组进行回填
        for (int k = i; k <= j; k++){
            nums[k] = tmp[k-i];
        }
    }

    private void swap(int[] nums, int i, int j){
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }

    /**
     * 输入一个链表，反转链表后，输出新链表的表头。
     *
     * 思路：使用一个prev指针，保存当前节点的前向节点，然后使当前节点的next指针指向prev节点，最后将之前的头节点的next指针设置为null
     * @param head
     * @return
     */
    public ListNode ReverseList(ListNode head) {
        if (head == null){
            return null;
        }
        // 给链表的头部新增一个节点，使其作为head节点存在，同时减少特殊情况的判断
        ListNode newHead = new ListNode(0);
        newHead.next = head;
        // 声明一个prev指针 和 cur指针，prev指针指向cur节点的前向节点
        ListNode prev = newHead;
        ListNode cur = head;

        ListNode tmp;
        while (cur != null){
            tmp = cur.next;
            cur.next = prev;
            prev = cur;
            cur = tmp;
        }

        // 修正第一个节点
        newHead.next.next = null;
        newHead.next = prev;

        return newHead.next;
    }

    class ListNode{
        int val;
        ListNode next;

        public ListNode(int val){
            this.val = val;
        }
    }

    class TreeNode{
        int val;
        TreeNode left;
        TreeNode right;

        public TreeNode(int val) {
            this.val = val;
        }
    }
}
