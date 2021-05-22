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
        System.out.println(solve(new int[] {1,2,3}));
    }

    /**
     * 给出一个升序排序的链表，删除链表中的所有重复出现的元素，只保留原链表中只出现一次的元素。
     * 例如：
     * 给出的链表为1→2→3→3→4→4→5, 返回1→2→5.
     * 给出的链表为1→1→1→2→3, 返回2→3.
     *
     * 思路：因为链表是升序进行排列的，那么对于重复出现的数字一定是连续在一起的，那么我们可以对这些
     * 重复出现的数字不作处理，直接忽略掉，但是这需要存储一个当前元素的前向节点指针
     */
    public ListNode deleteDuplicates (ListNode head) {
        if (head == null){
            return head;
        }
        // 生成一个新的头节点，必须要做，因为可能head的头节点是重复的节点，要被删掉
        ListNode newHead = new ListNode(0);
        ListNode prev = newHead;
        newHead.next = head;

        while (head != null){
            // 判断这个节点是否应该跳过
            ListNode p = head;
            boolean flag = false;
            while (head.next != null){
                if (p.val == head.next.val){
                    head = head.next;
                    flag = true;
                }else {
                    break;
                }
            }
            if (flag){
                head = head.next;
                continue;
            }
            prev.next = head;
            head = head != null ? head.next : null;
            prev = prev.next;
        }
        return newHead.next;
    }


    /**
     * 给定一个单链表，请设定一个函数，将链表的奇数位节点和偶数位节点分别放在一起，重排后输出。
     * 注意是节点的编号而非节点的数值。
     *
     *  ⚠️：题目中要求的是重排的是节点，而不是只是对节点的数值进行交换
     *
     *  1、使用两个指针，一个指向奇数位节点的起始位置，一个指向偶数位节点的起始位置，然后分别形成
     *  两个链表，然后再将这两个链表进行连接起来。
     */
    public ListNode oddEvenList (ListNode head) {
        if (head == null){
            return head;
        }
        // 声明两个指针，以及两个新的头节点
        ListNode odd = head;
        ListNode even = head.next;
        ListNode evenHead = even;

        while (even != null && even.next != null){
            odd.next = even.next;
            odd = odd.next;
            even.next = odd.next;
            even  = even.next;
        }
        odd.next = evenHead;

        return head;
    }

    /**
     * 从0,1,2,...,n这n+1个数中选择n个数，找出这n个数中缺失的那个数，要求O(n)尽可能小
     *
     * 思路1、和上一个问题类似，我们把这n个数索引到对应的位置上，然后检查哪个位置上有缺失的数字,
     * 但是上面的思路存在一个问题，就是如果把0索引到对应的位置上，所以我们可以使用一个变量，存储
     * 数组中的最小元素，如果最小元素大于0，那么就直接返回0就可以了，对于0不做移动，其余其他数字，都
     * 将其移动到对应的索引位置上。
     *
     * 思路2、因为题目中保证了数组a中的是从0-n中选出的n个，因此每个数字仅出现了一次，那么可以分别求出
     * a数组的所有数组和，用0-n的和减去a数组的和就可以得出最后的结果
     *
     * 思路3、如果a中的数字有序的话，那么就可以使用二分查找来进行寻找这个中间缺少的数字
     */
    public int solve (int[] a) {
        if (a == null || a.length == 0){
            return 0;
        }
        // 1、 对数组中的数据进行移动
//        // 获取数组中的最小值，以及移动数字到嘴硬的索引位置上
//        for (int i = 0; i < a.length; i++) {
//            if (a[i] > 0){
//                swap(a,i,a[i]-1);
//            }
//        }
//
//        // 遍历数组，寻找到缺失的数字
//        for (int i = 0; i < a.length; i++){
//            if (a[i] != i+1){
//                return i+1;
//            }
//        }
//        return 0;
        // 3、对顺序的数组进行二分查找
        int l = 0, r = a.length-1;
        while (l <= r){
            int m = (l+r)/2;
            if (a[m] == m){
                l = m+1;
            }else {
                r = m-1;
            }
        }

        return l;
    }

    private void swap(int[] nums, int i, int j){
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }

    /**
     * 给定一个无序数组arr，找到数组中未出现的最小正整数
     * 例如arr = [-1, 2, 3, 4]。返回1
     * arr = [1, 2, 3, 4]。返回5
     * [要求]
     * 时间复杂度为O(n)，空间复杂度为O(1)
     *
     * 思路：1、如果不要求时间复杂度可以，先对arr进行一次排序，然后遍历查找，这样的时间复杂度为
     * o(nlogn)，不满足题意
     *      2、对问题进行总结，我们可以得出一个结论，这个最小的正整数一定出现在[1,arr.length+1]
     *  中。
     *      那么，我们将其中的每个数字，映射到其应该对应的索引位置上，对于超出或者小于
     *  [1,arr.length]的数字，可以不作处理，然后重新扫描整张表，如果索引位置i上对应的数字
     *  不为i+1的话，那么i+1就是我们最终需要寻找的数字
     *
     */
    public int minNumberdisappered (int[] arr) {
        if (arr == null || arr.length == 0){
            return -1;
        }
        // 将arr中在[1,arr.length]中的数字，索引到对应的位置上
        for (int i = 0; i < arr.length; i++){
            int n = arr[i];
            if (n >= 1 && n <= arr.length){
                arr[n-1] = n;
            }
        }
        // 寻找未出现的最小正整数
        for (int i = 0; i < arr.length; i++){
            if (arr[i] != i+1){
                return i+1;
            }
        }
        return arr.length+1;
    }

    /**
     * 已知int一个有序矩阵mat，同时给定矩阵的大小n和m以及需要查找的元素x，
     * 且矩阵的行和列都是从小到大有序的。设计查找算法返回所查找元素的二元数组，
     * 代表该元素的行号和列号(均从零开始)。保证元素互异。
     *
     * 思路：这个矩阵mat的每行、每列都是从小到大顺序排列的，也就是有序的，在有序的情况下进行查找的
     * 首选方式是二分查找，但是应该注意到的一点是，如果要对行进行二分查找，那么有个问题就是他可能
     * 出现在很多候选行，处理起来就比较复杂。所以对行的确定就采用遍历的模式，最多是O(nlogn)的时间
     * 复杂度
     */
    public int[] findElement(int[][] mat, int n, int m, int x) {
        if (mat == null || mat.length == 0 || mat[0].length == 0){
            return new int[] {};
        }

        int height = mat.length, width = mat[0].length;
        for (int i = 0; i < height; i++){
            if (mat[i][0] <= x && mat[i][width-1] >= x){
                int s = binarySearch(mat[i], x);
                if (s != -1){
                    return new int[] {i,s};
                }
            }
        }
        return new int[] {};
    }

    private int binarySearch(int[] nums, int target){
        int left = 0, right = nums.length-1;
        while (left <= right){
            int mid = (left+right) /2;
            if (nums[mid] == target){
                return mid;
            }else if (nums[mid] > target){
                right = mid-1;
            }else {
                left = mid+1;
            }
        }
        return -1;
    }


    /**
     * 给出一组区间，请合并所有重叠的区间。
     * 请保证合并后的区间按区间起点升序排列。
     *
     * 思路：对这些区间进行排序，让start小的尽量靠前，然后再对这些区间进行合并，时间复杂度为O(nlogn)
     */
    public ArrayList<Interval> merge(ArrayList<Interval> intervals) {
        if (intervals == null || intervals.size() <= 1){
            return intervals;
        }
        // 对这些区间进行排序
        intervals.sort(new Comparator<Interval>() {
            @Override
            public int compare(Interval o1, Interval o2) {
                if (o1.start < o2.start){
                    return -1;
                }else if (o1.start > o2.start){
                    return 1;
                }else {
                    return o1.end <= o2.end ? -1 : 1;
                }
            }
        });

        ArrayList<Interval> arrayList = new ArrayList<>();
        // 合并区间
        int index = 0;
        Interval start = intervals.get(index++);

        while (index < intervals.size()){
            Interval tmp = intervals.get(index);
            if (tmp.start <= start.end){
                start.end = Math.max(start.end, tmp.end);
            }else {
                arrayList.add(start);
                start = tmp;
            }
            index++;
        }
        arrayList.add(start);
        return arrayList;
    }

    /**
     * 一个机器人在m×n大小的地图的左上角（起点）。
     * 机器人每次向下或向右移动。机器人要到达地图的右下角（终点）。
     * 可以有多少种不同的路径从起点走到终点？
     *
     * 思路：
     *      1、dfs，当走到终点时，总路径数+1，但是有很多重复子问题，会导致时间复杂度变高，超时，时间复杂度为?
     *      2、动态规划：我们最终的结果的重点是 右下角的那个位置，根据题意我们可以很快的写出递推公式
     *      dp[i][j] = dp[i-1][j] + dp[i][j-1]
     */
    int num = 0;
    public int uniquePaths (int m, int n) {
        if (m == 1 || n == 1){
            return 1;
        }

        // 声明状态表
        int[][] dp = new int[m][n];
        dp[0][0] = 1;
        // 初始化状态表
        for (int i = 1; i < dp.length; i++){
            dp[i][0] = dp[i-1][0];
        }
        for (int i = 1; i < dp[0].length; i++){
            dp[0][i] = dp[0][i-1];
        }
        // 填充状态表
        for (int i = 1; i < dp.length; i++){
            for (int j = 1; j < dp[0].length; j++){
                dp[i][j] = dp[i-1][j] + dp[i][j-1];
            }
        }
        return dp[m-1][n-1];
//        dfs(m,n,1,1);
//        return num;
    }

    private void dfs(int m, int n, int i, int j){
        if (i == m && j == n){
            num++;
            return;
        }
        if (i == m){
            dfs(m,n,i,j+1);
            return;
        }
        if (j == n){
            dfs(m,n,i+1,j);
            return;
        }

        dfs(m,n,i+1,j);
        dfs(m,n,i,j+1);
    }

    /**
     * 将一个链表 m 位置到 n 位置之间的区间反转，要求时间复杂度 O(n)，空间复杂度 O(1)。
     * 例如：给出的链表为 1→2→3→4→5→NULL, m=2,n=4
     * 返回 1→4→3→2→5→NULL
     *
     * 注意：给出的 m,n 满足以下条件：
     *      链表长度1≤m≤n≤链表长度
     */
    public ListNode reverseBetween (ListNode head, int m, int n) {
        if (head == null || m == n){
            return head;
        }
        ListNode newHead = new ListNode(0);
        newHead.next = head;
        ListNode prev = newHead;
        int num = 1;
        // 首先定位到第m个节点
        while (num != m){
            prev = head;
            head = head.next;
            num++;
        }
        // 然后对m-n之间的链表进行反转
        ListNode start = prev, tmp;
        while (num != n){
            tmp = head.next;
            head.next = prev;
            prev = head;
            head = tmp;
            num++;
        }
        start.next.next = head.next;
        head.next = prev;
        start.next = head;
        return newHead.next;
    }

    /**
     * 给定一个二叉树和一个值 sum，请找出所有的根节点到叶子节点的节点值之和等于 sum 的路径，
     *
     * 例如：{1,2},3  输出 [[1,2]]
     *
     * 思路：对整棵树进行遍历，当到达其根节点时，进行比对是否等于sum，如果等于，就他其加入
     * 到最终的结果集中
     */
    ArrayList<ArrayList<Integer>> arrayLists = new ArrayList<>();
    public ArrayList<ArrayList<Integer>> pathSum (TreeNode root, int sum) {
        arrayLists.clear();
        pathSum(root,sum,new ArrayList<Integer>(),0);
        return arrayLists;
    }

    private void pathSum(TreeNode root, int sum,
                         ArrayList<Integer> path, int currentSum){
        if (root == null){
            return;
        }
        // 对叶子节点进行判定
        if (root.left == null && root.right == null){
            if (currentSum+root.val == sum){
                ArrayList<Integer> targetPath = new ArrayList<>(path);
                targetPath.add(root.val);
                arrayLists.add(targetPath);
            }
            return;
        }
        // 提前进行剪枝，因为有节点为负数的情况，所以不能提前剪枝
//        if (root.val + currentSum > sum){
//            return;
//        }
        path.add(root.val);
        pathSum(root.left,sum,path,currentSum+root.val);
        pathSum(root.right,sum,path,currentSum+root.val);
        path.remove(path.size()-1);
    }

    /**
     * 给定一个仅包含数字 0−9 的二叉树，每一条从根节点到叶子节点的路径都可以用一个数字表示。
     * 例如根节点到叶子节点的一条路径是1→2→3,那么这条路径就用 123 来代替。
     * 找出根节点到叶子节点的所有路径表示的数字之和
     *
     * 例如 ：  输入 {1,0}，输出 10
     *         输入 {1,#,9}，输出19
     *
     * 思路：对于最终的结果，走的是一条从根节点到叶子节点的路径，我们可以这么定义一个递归公式
     *    dp[i] 表示从根节点，到这个节点i时的路径表示
     *    那么对于叶子节点j,其父节点为k：
     *                  dp[j] = dp[k]*10 + j.val
     *    那么就可以根据这个递推公式很快的写出结果（动态规划思想）
     */
    public int sumNumbers (TreeNode root) {
        if (root == null){
            return 0;
        }
        return sumNumbers(root,0);
    }

    public int sumNumbers(TreeNode root, int parentNum){
        if (root.left == null && root.right == null){
            return parentNum*10+root.val;
        }

        int num = 0;
        if (root.left != null){
            num += sumNumbers(root.left,parentNum*10+root.val);
        }
        if (root.right != null){
            num += sumNumbers(root.right,parentNum*10+root.val);
        }
        return num;
    }

    /**
     * 给定一棵二叉树，已经其中没有重复值的节点，请判断该二叉树是否为搜索二叉树和完全二叉树。
     *
     * 完全二叉树：是指二叉树中，除了最后一层没有满之外，其他层全都是满二叉树，而且最后一层，需要
     * 节点从左到右一次排列
     *
     * 思路：分别判断是否是二叉搜索树 和 完全二叉树 然后返回
     */
    public boolean[] judgeIt (TreeNode root) {
        boolean isSearchTree = isSearchTree(root,Integer.MIN_VALUE,Integer.MAX_VALUE);
        boolean isCompleteTree = isCompleteTree(root);
        return new boolean[] {isSearchTree,isCompleteTree};
    }

    private boolean isCompleteTree(TreeNode root){
        if (root == null){
            return true;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while (queue.peek() != null){
            TreeNode t = queue.poll();
            queue.offer(t.left);
            queue.offer(t.right);
        }
        while (!queue.isEmpty() && queue.peek()==null){
            queue.poll();
        }
        return queue.isEmpty();
    }

    private boolean isSearchTree(TreeNode root, int min, int max){
        if (root == null){
            return true;
        }

        if (root.val < min || root.val > max){
            return false;
        }

        return isSearchTree(root.left, min, root.val) &&
                isSearchTree(root.right, root.val, max);
    }

    /**
     * 给定两个有序数组arr1和arr2，已知两个数组的长度都为N，求两个数组中所有数的上中位数。
     * 上中位数：假设递增序列长度为n，若n为奇数，则上中位数为第n/2+1个数；否则为第n/2个数
     *
     * 要求：
     * 时间复杂度为O(logN)，额外空间复杂度为O(1)
     *
     * 思路：本质上就是寻找两个数组中排序后的第i个元素，因为两个数组是排序的，所以可以使用二分法来
     * 进行解决这个问题。
     *      如果arr1中i位置上的元素比arr2中j位置上的元素小，那么i之前的所有元素，都要比j小
     */
    public int findMedianinTwoSortedAray (int[] arr1, int[] arr2) {
        int length = arr1.length;
        // 目标就是寻找arr1 中和 arr2 中第index大的元素
        return findKth(arr1, arr2, 0, arr1.length-1,
                0, arr2.length-1,length);
    }
    // 寻找arr1 中和 arr2 中第k大的元素
    private int findKth(int[] arr1, int[] arr2, int l1, int r1, int l2, int r2, int k){
        int m = r1-l1+1;
        int n = r2-l2+1;

        if (m == 0){
            return arr2[l2+k-1];
        }
        if (n == 0){
            return arr1[l1+k-1];
        }
        if (k == 1){
            return Math.min(arr1[l1],arr2[l2]);
        }

        int mid1 = l1 + Math.min(m, k/2) -1;
        int mid2 = l2 + Math.min(n, k/2) -1;

        if (arr1[mid1] < arr2[mid2]){
            return findKth(arr1,arr2,mid1+1,r1,l2,r2,k-(mid1-l1+1));
        }else {
            return findKth(arr1,arr2,l1,r1,mid2+1,r2,k-(mid2-l2+1));
        }
    }

    /**
     * 给定两个字符串str1和str2，输出两个字符串的最长公共子序列。如果最长公共子序列为空，
     * 则返回"-1"。目前给出的数据，仅仅会存在一个最长的公共子序列
     *  例： "1A2C3D4B56","B1D23A456A"
     *  结果："123456"
     *
     * 思路1：可以使用dfs来生成其中一个字符串的所有的子序列，然后对这些子序列进行判断是否是公共的，
     * 从中选出最长的公共子序列来进行返回，生成所有子序列的时间复杂度为o(2^n),进行判断的时间复杂度为
     * o(m+n),最中的时间复杂度为o((m+n)2^n)
     *
     * 思路2：上面直接使用dfs的时间复杂度非常高，呈现指数级的增长趋势，所以这里改用动态规划的方式来进行
     * 问题的求解。
     *     我们注意到问题的目的是，s1 和 s2 的最长公共子序列，那我们可以注意到，最终的结果与我们的
     * s1 和 s2 都有关，如果s1 或者 s2 发生了改变，那么最终的结果也可能发生改变。所以我们的结果和
     * s1 以及 s2 的长度有关，所以我们要建立的递归公式中的变量，要包含 s1 和 s2 的长度
     *
     *     下面我们建立如下的状态表：
     *     dp[i][j]：表示s1 的前i个字符，与 s2 的前j个字符中，存在的最长公共子序列的长度
     *     则，最终dp[s1.length()][s2.length()]的值，就是s1 和 s2 最长公共子序列长度的值
     *
     *     那么 dp[i][j] = {
     *         0,    i = 0  || j = 0
     *         dp[i-1][j-1]+1,  i,j > 0 && s1[i] = s2[j]
     *         Max(dp[i-1][j],dp[i][j-1])    i,j>0 && s1[i] != s2[j]
     *     }
      */
    public String LCS (String s1, String s2) {
        if (s1 == null || s2 == null || s1.length() == 0 || s2.length() == 0){
            return "-1";
        }

        // 声明状态数组
        int[][] dp = new int[s1.length()+1][s2.length()+1];

        // 初始化状态数组，可以跳过这个步骤，因为在dp创建的时候，每个位置上已经进行了默认0填充
        // 填充状态数组
        for (int i = 1; i < dp.length; i++){
            for (int j = 1; j < dp[0].length; j++){
                if (s1.charAt(i-1) == s2.charAt(j-1)){
                    dp[i][j] = dp[i-1][j-1]+1;
                }else {
                    dp[i][j] = Math.max(dp[i-1][j],dp[i][j-1]);
                }
            }
        }

        // 回溯状态数组找到最长的公共子序列
        if (dp[dp.length-1][dp[0].length-1] == 0){
            return "-1";
        }
        StringBuilder sb = new StringBuilder();
        int i = dp.length-1, j = dp[0].length-1;
        while (i > 0 && j > 0){

            if (s1.charAt(i-1) == s2.charAt(j-1)){
                sb.append(s2.charAt(j-1));
                i--;
                j--;
            }else {
                if (dp[i-1][j] > dp[i][j-1]){
                    i--;
                }else {
                    j--;
                }
            }
        }
        if (sb.length() == 0){
            return "-1";
        }
        return sb.reverse().toString();
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

    class Interval{
        int start;
        int end;

        public Interval(){
            this.start = 0;
            this.end = 0;
        }

        public Interval(int start, int end){
            this.start = start;
            this.end = end;
        }
    }

}
