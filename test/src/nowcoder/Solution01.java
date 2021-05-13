package nowcoder;

import nowcoder.Solution;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Solution01 {

    @Test
    public void test(){
//        TreeNode root = new TreeNode(8);
//        root.left = new TreeNode(6);
//        root.right = new TreeNode(10);
//        root.left.left = new TreeNode(5);
//        root.left.left.left = new TreeNode(4);
//        root.left.right = new TreeNode(7);
//        root.right.left = new TreeNode(9);
//        root.right.right = new TreeNode(11);
//        System.out.println(Serialize(root));
//        Deserialize(Serialize(root));

        System.out.println(cutRope(8));
//        System.out.println(Integer.MIN_VALUE-1);
//        DecimalFormat df = new DecimalFormat();
//        df.setMaximumFractionDigits(2);
//        System.out.println(df.format(1.2361));
    }

    /**
     * 给你一根长度为n的绳子，请把绳子剪成整数长的m段（m、n都是整数，n>1并且m>1，m<=n），
     * 每段绳子的长度记为k[1],...,k[m]。请问k[1]x...xk[m]可能的最大乘积是多少？
     * 例如，当绳子的长度是8时，我们把它剪成长度分别为2、3、3的三段，此时得到的最大乘积是18。
     *
     * 思路1：dfs
     * 思路2：动态规划问题，使用一个记忆集优化上面dfs的重复子问题 复杂度O(n^2)
     * @param target
     * @return
     */
    public int cutRope(int target) {
        if (target < 2)
            return 0;

        int[] nums = new int[target+1];
        nums[1] = 1;
        nums[2] = 1;
        for (int i = 3; i <= target; i++){
            int max = -1;
            for (int j = 1; j < i; j++){
                // 分成3段的情况
                max = Math.max(max,j*nums[i-j]);
                // 分成2段的情况
                max = Math.max(max,j*(i-j));
            }
            nums[i] = max;
        }

        return nums[target];
    }

    /**
     * 地上有一个m行和n列的方格。一个机器人从坐标0,0的格子开始移动，每一次只能向左，右，上，下四个方向移动一格，
     * 但是不能进入行坐标和列坐标的数位之和大于k的格子。 例如，当k为18时，机器人能够进入方格（35,37），
     * 因为3+5+3+7 = 18。但是，它不能进入方格（35,38），因为3+5+3+8 = 19。请问该机器人能够达到多少个格子？
     *
     * 思路：使用dfs方法，
     * @param threshold
     * @param rows
     * @param cols
     * @return
     */
    public int movingCount(int threshold, int rows, int cols) {
        if (threshold < 0 || rows <= 0 || cols <= 0)
            return 0;
        // 声明一个二维数组来存储是否走过的标志
        boolean[][] flags = new boolean[rows][cols];
        int count = 0;

        if (!isStop(threshold, rows, cols)){
            count = dfs(flags,threshold,rows,cols,0,0);
        }
        return count;
    }

    private int dfs(boolean[][] flags, int threshold, int rows, int cols, int i ,int j){
        int count = 0;

        if (!isStop(threshold,i,j)){
            flags[i][j] = true;
            count += 1;
            // 向上
            if (i > 0 && !flags[i-1][j])
                count += dfs(flags,threshold,rows,cols,i-1,j);
            // 向右
            if (j < cols-1 && !flags[i][j+1])
                count += dfs(flags,threshold,rows,cols,i,j+1);
            // 向下
            if (i < rows-1 && !flags[i+1][j])
                count += dfs(flags,threshold,rows,cols,i+1,j);
            // 向左
            if (j > 0 && !flags[i][j-1])
                count += dfs(flags,threshold,rows,cols,i,j-1);
        }

        return count;
    }

    private boolean isStop(int threshold, int rows, int cols){
        // 使用r 和 c 来表示 rows 和 cols的最低位,使用sum来暂存这些数字的和
        int r = 0;
        int c = 0;
        int sum = 0;

        while (rows != 0 || cols != 0){
            r = rows == 0 ? 0 : rows % 10;
            c = cols == 0 ? 0 : cols % 10;

            sum += r;
            sum += c;

            rows /= 10;
            cols /= 10;
        }

        return sum > threshold;
    }

    /**
     * 请设计一个函数，用来判断在一个矩阵中是否存在一条包含某字符串所有字符的路径。路径可以从矩阵中的任意一个格子开始，
     * 每一步可以在矩阵中向左，向右，向上，向下移动一个格子。如果一条路径经过了矩阵中的某一个格子，
     * 则该路径不能再进入该格子。 例如
     *  [a b c e]
     *  [s f c s]
     *  [a d e e]
     * 矩阵中包含一条字符串"bcced"的路径，但是矩阵中不包含"abcb"路径，因为字符串的第一个字符b占据了矩阵中的第一行
     * 第二个格子之后，路径不能再次进入该格子。
     *
     * 思路：使用回溯法，找到matrix中所有字符为word.charAt(0)的位置，然后以这些位置为起点，使用dfs遍历，寻找是否有
     * 满足的路径，如果有则返回true
     *
     * 上面的时间复杂度较高，存在重复计算的问题，可以考虑使用记忆集来进行优化
     * @param matrix
     * @param word
     * @return
     */
    public boolean hasPath (char[][] matrix, String word) {
        // write code here
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0
                || word == null || word.length() == 0)
            return false;
        // 使用一个二维数组存储，matrix各个位置上的状态
        boolean[][] flag = new boolean[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++){
            for (int j = 0; j < matrix[0].length; j++){
                boolean b = dfs(flag,matrix,i,j,word,0);
                if (b == true)
                    return true;
            }
        }

        return false;
    }

    private boolean dfs(boolean[][] flag, char[][] matrix, int i, int j, String word, int k){
        if (k == word.length()-1){
            if (!flag[i][j] && matrix[i][j] == word.charAt(k))
                return true;
            else
                return false;
        }
        if (!flag[i][j] && matrix[i][j] == word.charAt(k)){
            // 打开这个位置上的访问标志
            flag[i][j] = true;
            boolean f = false;
            // 向上
            if ((i - 1) >= 0)
                f = f || dfs(flag,matrix,i-1,j,word,k+1);
            // 向右
            if ((j + 1) < matrix[0].length)
                f = f || dfs(flag,matrix,i,j+1,word,k+1);
            // 向下
            if ((i + 1) < matrix.length)
                f = f || dfs(flag,matrix,i+1,j,word,k+1);
            // 向左
            if ((j - 1) >= 0)
                f = f || dfs(flag,matrix,i,j-1,word,k+1);

            if (f)
                return f;
            // 关闭访问标示
            flag[i][j] = false;
        }
        return false;
    }

    /**
     * 给定一个数组和滑动窗口的大小，找出所有滑动窗口里数值的最大值。例如，如果输入数组{2,3,4,2,6,2,5,1}及滑动窗口的大小3，
     * 那么一共存在6个滑动窗口，他们的最大值分别为{4,4,6,6,6,5}； 针对数组{2,3,4,2,6,2,5,1}的滑动窗口有以下6个：
     * {[2,3,4],2,6,2,5,1}， {2,[3,4,2],6,2,5,1}， {2,3,[4,2,6],2,5,1}， {2,3,4,[2,6,2],5,1}， {2,3,4,2,[6,2,5],1}]
     * ， {2,3,4,2,6,[2,5,1]}。
     *
     * 窗口大于数组长度的时候，返回空
     *
     * 思路1：记录下初始窗口的最大值，和此时最大值所在的位置，然后移动窗口，比较新加进来的数字，和当前最大值的大小情况，时间复杂度是O(N^2)
     *
     * 思路2：对于上面的方式有重复计算的问题，这里使用一个单调队列，来暂存一个窗口的元素，在任意一个窗口内如果arr[i] < arr[i+1]，那么
     * arr[i]是一次无效的统计，因为如果这两个已经处于一个窗口内之后，那么之后arr[i] and arr[i+1]是绑定在一起的，直到窗口划过arr[i]
     * 的这个位置。所以可以用后面的arr[i+1] 来替换掉 arr[i] 的作用
     *
     *      所以可以使用一个单调队列，这个队列里面存储一个窗口内的数据，如果新进来一个元素，那么如果这个元素大于队尾的元素，那么就删掉
     *  队尾的元素，然后再比较，如果队列为空，直接插入。
     *      如果当前元素，小于队列尾部的元素，则直接插入，因为可能存在新插入的元素为窗口的起始位置的情况
     *
     * @param num
     * @param size
     * @return
     */
    public ArrayList<Integer> maxInWindows(int [] num, int size) {
        if (num == null || size <= 0 || num.length < size)
            return new ArrayList<>();

        ArrayList<Integer> arrayList = new ArrayList<>();
        // 思路1
//        for (int i = 0; i<= num.length-size; i++){
//            int max = num[i];
//            for (int j = i; j < i+size; j++){
//                max = Math.max(max,num[j]);
//            }
//            arrayList.add(max);
//        }

        // 思路2
        Deque<Integer> deque = new LinkedList<>();
        // 初始化双端队列
        for (int i= 0; i < size-1; i++){
            while (!deque.isEmpty() && num[i] > deque.peekLast()){
                deque.pollLast();
            }
            deque.offerLast(num[i]);
        }

        for (int i = 0; i <= num.length-size; i++){
            int j = i+size-1;
            while (!deque.isEmpty() && num[j] > deque.peekLast()){
                deque.pollLast();
            }
            deque.offerLast(num[j]);

            arrayList.add(deque.peekFirst());
            if (num[i] == deque.peekFirst()){
                deque.pollFirst();
            }
        }

        return arrayList;
    }

    /**
     * 如何得到一个数据流中的中位数？如果从数据流中读出奇数个数值，那么中位数就是所有数值排序之后位于中间的数值。如果从数据流中
     * 读出偶数个数值，那么中位数就是所有数值排序之后中间两个数的平均值。我们使用Insert()方法读取数据流，使用GetMedian()方法
     * 获取当前读取数据的中位数。
     *
     * 思路：对于取topK的问题 和 取中位数 以及 类似第90%的数， 都可以使用堆来辅助计算
     * Java中的PriorityQueue底层就是一个堆的数据结构，默认是小顶堆
     *
     * 我们维护一个大小为 n/2的大顶堆，维护一个大小为n/2的小顶堆，两个堆的大小最大相差不超过1
     *
     * @param num
     */
    // 大顶堆
    private PriorityQueue<Integer> leftHeap = new PriorityQueue<>(new Comparator<Integer>() {
        @Override
        public int compare(Integer o1, Integer o2) {
            return -o1.compareTo(o2);
        }
    });
    // 小顶堆
    private PriorityQueue<Integer> rightHeap = new PriorityQueue<>();

    public void Insert(Integer num) {
        if (leftHeap.size() > rightHeap.size()){
            leftHeap.offer(num);
            rightHeap.offer(leftHeap.poll());
        }else {
            rightHeap.offer(num);
            leftHeap.offer(rightHeap.poll());
        }
    }

    public Double GetMedian() {
        if (leftHeap.size() == 0)
            return 0.0;
        if (leftHeap.size() > rightHeap.size()){
            return (double)leftHeap.peek();
        }else {
            return (leftHeap.peek()+rightHeap.peek())/2.0;
        }
    }

    /**
     * 请实现两个函数，分别用来序列化和反序列化二叉树
     *
     * 二叉树的序列化是指：把一棵二叉树按照某种遍历方式的结果以某种格式保存为字符串，
     * 从而使得内存中建立起来的二叉树可以持久保存。序列化可以基于先序、中序、后序、
     * 层序的二叉树遍历方式来进行修改，序列化的结果是一个字符串，序列化时通过 某种符号
     * 表示空节点（#），以 ！ 表示一个结点值的结束（value!）。
     *
     * 二叉树的反序列化是指：根据某种遍历顺序得到的序列化字符串结果str，重构二叉树。
     *
     * 例如，我们可以把一个只有根节点为1的二叉树序列化为"1,"，然后通过自己的函数来解析
     * 回这个二叉树
     *
     * @param root
     * @return
     */
    // 按照层序顺序进行序列化，如果遇到null的情况，就赋值为 #，将一棵二叉树序列化成为一个完全二叉树
    String Serialize(TreeNode root) {
        // 序列化
        if (root == null)
            return "";
        // 用来暂存string
        StringBuilder sb = new StringBuilder();
        // 层序遍历，flag用来标示这一层上是否还有非null的节点
        boolean flag  = true;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()){
            if (!flag)
                break;
            flag = false;
            for (int i = queue.size(); i > 0; i--){
                TreeNode t = queue.poll();
                if (t == null){
                    sb.append("#");
                    queue.offer(null);
                    queue.offer(null);
                }else {
                    sb.append(t.val);
                    if (t.left != null || t.right != null) {
                        flag = true;
                    }
                    queue.offer(t.left);
                    queue.offer(t.right);
                }
                sb.append(",");
            }
        }

        return sb.toString().substring(0,sb.length()-1);
    }

    // 对这个字符串，按照完全二叉树来进行序列化
    TreeNode Deserialize(String str) {
        // 反序列化
        if (str.trim().length() == 0)
            return null;
        str = str.trim();

        // 获取每个节点上的相关信息
        String[] split = str.split(",");

        TreeNode root = new TreeNode(Integer.parseInt(split[0]));
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        int offest  = 1;
        boolean flag  = true;
        while (!queue.isEmpty()){
            if (!flag)
                break;
            flag = false;
            for (int i = queue.size(); i > 0; i--){
                TreeNode t = queue.poll();
                if (t == null){
                    queue.offer(null);
                    queue.offer(null);
                }else {
                    if (2*offest < split.length){
                        if ("#".equals(split[2*offest-1])){
                            t.left = null;
                        }else {
                            t.left = new TreeNode(Integer.parseInt(split[2*offest-1]));
                        }
                        if ("#".equals(split[2*offest])){
                            t.right = null;
                        }else {
                            t.right = new TreeNode(Integer.parseInt(split[2*offest]));
                        }
                        queue.offer(t.left);
                        queue.offer(t.right);

                        flag = true;
                    }else {
                       t.left = null;
                       t.right = null;
                    }
                }
                offest++;
            }
        }

        return root;
    }

    /**
     * 给定一棵二叉搜索树，请找出其中的第k小的TreeNode结点。
     *
     * 思路：二叉搜索树的特点是左子树上的所有值小于root节点上的值，右子树上的所有值都大于root节点上的，因此，可以使用中序遍历来找到第
     * k个小的节点
     *
     * @param pRoot
     * @param k
     * @return
     */
    private int K;
    private int off;
    TreeNode KthNode(TreeNode pRoot, int k) {
        if (pRoot == null || k < 1)
            return null;
        this.K = k;

        return inOrder(pRoot);
    }

    private TreeNode inOrder(TreeNode root){
        if (root == null)
            return null;

        TreeNode left = inOrder(root.left);
        if (left != null)
            return left;

        off++;
        if (off == K)
            return root;

        return inOrder(root.right);
    }

    /**
     * 从上到下按层打印二叉树，同一层结点从左至右输出。每一层输出一行。
     *
     * 思路：对二叉树进行bfs操作，使用一个队列暂存数组
     *
     * @param pRoot
     * @return
     */
    ArrayList<ArrayList<Integer> > Print(TreeNode pRoot) {
        if (pRoot == null)
            return new ArrayList<>();

        // 声明一个队列，用来暂存树节点
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(pRoot);
        ArrayList<ArrayList<Integer>> res = new ArrayList<>();

        while (!queue.isEmpty()){
            // 声明一个临时输出来存储每一层的数据
            ArrayList<Integer> tmp = new ArrayList<>();
            for (int i = queue.size(); i> 0; i--){
                TreeNode t = queue.poll();
                tmp.add(t.val);
                if (t.left != null)
                    queue.offer(t.left);
                if (t.right != null)
                    queue.offer(t.right);
            }
            res.add(tmp);
        }
        return res;
    }

    /**
     *  请实现一个函数按照之字形打印二叉树，即第一行按照从左到右的顺序打印，第二层按照从右至左的顺序打印，第三行按照从左到右的顺序打印，
     *  其他行以此类推。
     *
     *  思路：本质上还是一个bfs，使用一个双端队列，一开始从左向右读，将他们的子节点，从左到右的插入到尾节点的位置。然后从右往左读，
     *  将子节点从右往左的插入头节点的位置上
     * @param pRoot
     * @return
     */
    public ArrayList<ArrayList<Integer> > PrintZhi(TreeNode pRoot) {
        if (pRoot == null){
            return new ArrayList<>();
        }
        // 声明一个双端队列来存储层级遍历时的节点
        Deque<TreeNode> deque = new LinkedList<>();
        deque.offerFirst(pRoot);
        boolean flag = true; // true：标示当前是从左向右读取，false：标示当前是从右向左读

        ArrayList<ArrayList<Integer>>  res = new ArrayList<>();

        while (!deque.isEmpty()){
            ArrayList<Integer> tmp = new ArrayList<>();
            for (int i = deque.size(); i > 0; i--){
                if (flag){
                    // 从左向右读取
                    TreeNode t = deque.pollFirst();
                    tmp.add(t.val);
                    if (t.left != null)
                        deque.offerLast(t.left);
                    if (t.right != null)
                        deque.offerLast(t.right);
                }else {
                    // 从右向左读
                    TreeNode t = deque.pollLast();
                    tmp.add(t.val);
                    if (t.right != null)
                        deque.offerFirst(t.right);
                    if (t.left != null)
                        deque.offerFirst(t.left);
                }
            }
            flag = !flag;
            res.add(tmp);
        }

        return res;
    }

    /**
     * 请实现一个函数，用来判断一棵二叉树是不是对称的。注意，如果一个二叉树同此二叉树的镜像是
     * 同样的，定义其为对称的。
     *
     * 思路：
     * @param pRoot
     * @return
     */
    boolean isSymmetrical(TreeNode pRoot) {
        return isSame(pRoot,pRoot);
    }

    private boolean isSame(TreeNode left, TreeNode right){
        if (left == null && right == null)
            return true;
        if (left == null || right == null)
            return false;

        if (left.val == right.val){
            return isSame(left.left,right.right)
                    && isSame(left.right,right.left);
        }else {
            return false;
        }
    }

    /**
     * 给定一个二叉树和其中的一个结点，请找出中序遍历顺序的下一个结点并且返回。注意，
     * 树中的结点不仅包含左右子结点，同时包含指向父结点的指针。
     *
     * 思路：按照中序遍历的顺序，如果这个节点没有右子树，则返回这个节点的父节点（注意区分当前节点
     * 是其父节点左子节点还是右子节点的情况）
     * 否则寻找右子树中 中序遍历的第一个节点
     * @param pNode
     * @return
     */
    public TreeLinkNode GetNext(TreeLinkNode pNode) {
        if (pNode == null)
            return null;
        // 如果没有右子树，则返回父节点，需要区分当前节点是左节点还是右节点的情况
        if (pNode.right == null){
            // pNode是其父节点的左子节点
            if (pNode.next != null && pNode.next.left == pNode){
                return pNode.next;
            }
            // 对于当前pNode节点是右节点的情况，进行回溯寻找父节点
            while (pNode.next != null && pNode.next.right == pNode){
                pNode = pNode.next;
            }
            if (pNode.next != null && pNode.next.left == pNode){
                return pNode.next;
            }
            return null;
        }
        // 寻找右子树中的第一个节点
        TreeLinkNode t = pNode.right;
        while (t.left != null && t.right != null){
            t = t.left;
        }
        if (t.left == null){
            return t;
        }else {
            return t.left;
        }
    }

    /**
     * 在一个排序的链表中，存在重复的结点，请删除该链表中重复的结点，重复的结点不保留，返回链表头指针。 例如，
     * 链表1->2->3->3->4->4->5 处理后为 1->2->5
     *
     * 思路：使用一个指针指向当前节点的前向节点，如果当前节点和next节点相等，就将当前节点指向next指针
     * ，否则根据标志查看当前节点是否是单独的一个，如果是，prev指向当前节点，cur指向next节点，否则
     * 删除掉prev 到 cur之间的所有节点，prev.next = cur.next
     *
     * @param pHead
     * @return
     */
    public ListNode deleteDuplication(ListNode pHead) {
        if (pHead == null)
            return null;

        // 设置一个新的指针指向pHead节点
        ListNode newHead;
        if (pHead.val > 0){
            newHead = new ListNode(pHead.val - 10);
        }else {
            newHead = new ListNode(pHead.val + 10);
        }
        newHead.next = pHead;

        // 初始化prev指针，cur指针, flag用来标示
        ListNode prev = newHead, cur = pHead;
        boolean flag = false;
        while (cur.next != null){
            if (cur.val == cur.next.val){
                flag = true;
            }else {
                if (!flag){
                    prev = cur;
                }else {
                    prev.next = cur.next;
                }
                flag = false;
            }
            cur = cur.next;
        }
        if (flag)
            prev.next = null;

        return newHead.next;
    }

    /**
     * 给一个链表，若其中包含环，请找出该链表的环的入口结点，否则，输出null。
     *
     * 思路1：使用一个hashSet，保存走过的节点，如果这个节点出现过了，那么这个节点就是环的入口节点
     *
     * 思路2：假设链表的开始位置是A，环的入口地址是B，fast 与 slow 相遇的位置是c，那么设 AB段的长度为X，BC段的长度为Y，
     * 由假设当slow第一次到达B的时候，fast所在的位置是D，则DB段的长度也为Y，又因为 2*(AB+BC) = AB+BC+CD+DB+BC ,所以有
     * CD段的距离为X-Y。 即CDB段的长度为X
     *
     * 基于上面的思路，提出一种寻找入口节点的方式，首先fast指针走两步，slow指针走一步，然后到达第一个相遇的地点C，此时，将fast指针
     * 指向链表的开头位置，slow位置不变，下面两个指针都是每次一步的走，由于CDB的长度为X，AB的长度也为X，则下次相遇的地点一定是
     * 链表的环的入口节点
     * @param pHead
     * @return
     */
    public ListNode EntryNodeOfLoop(ListNode pHead) {
        if (pHead == null)
            return null;

        ListNode fast = pHead, slow = pHead;
        if (pHead.next != null){
            fast = fast.next.next;
            slow = slow.next;
        }else {
            return null;
        }

        while (fast != slow){
            if (fast != null && fast.next != null){
                fast = fast.next.next;
                slow = slow.next;
            }else {
                return null;
            }
        }

        fast = pHead;
        while (fast != slow){
            fast = fast.next;
            slow = slow.next;
        }

        return slow;

//        HashSet<ListNode> hashSet = new HashSet<>();
//
//        while (pHead != null){
//            if (hashSet.contains(pHead)){
//                return pHead;
//            }
//            hashSet.add(pHead);
//            pHead = pHead.next;
//        }
//        return null;
    }

    /**
     * 给你一个整数数组 nums （下标 从 0 开始 计数）以及两个整数：low 和 high ，
     * 请返回 漂亮数对 的数目。
     *
     * 漂亮数对 是一个形如 (i, j) 的数对，其中 0 <= i < j < nums.length 且
     * low <= (nums[i] XOR nums[j]) <= high 。
     *
     * 思路1：暴力解法 双层for循环，存在超时问题
     *
     * 思路2：由于 (a^b) ^ (a^c) = a^b^a^c = b^c
     *     所以
     *
     * @param nums
     * @param low
     * @param high
     * @return
     */
    public int countPairs(int[] nums, int low, int high) {
        if (nums == null || nums.length < 2)
            return 0;
        int count = 0;
        for (int i = 0; i < nums.length; i++){
            for (int j = i+1; j < nums.length; j++){
                if ((nums[i]^nums[j]) >= low && (nums[i]^nums[j]) <= high)
                    count++;
            }
        }
        return count;
    }

    /**
     * 请实现一个函数用来匹配包括'.'和'*'的正则表达式。模式中的字符'.'表示任意一个字符，
     * 而'*'表示它前面的字符可以出现任意次（包含0次）。 在本题中，匹配是指字符串的所有
     * 字符匹配整个模式。例如，字符串"aaa"与模式"a.a"和"ab*ac*a"匹配，但是与"aa.a"
     * 和"ab*a"均不匹配
     *
     * 思路：使用动态规划的思想
     *      按照匹配模式最后一位的字符状态进行分类： s[0:n-1] 字符串 p[0:m-1] 匹配模式
     *      1、普通字符：如果可以s的最后一个字符进行匹配 则返回 s[0:n-2] 与 p[0:m-2]的匹配结果
     *   否则返回false
     *      2、.字符： s 与 p是否匹配，取决于s[0:n-2] 与 p[0:m-2]是否匹配
     *      3、*字符：
     *          *前是普通字符：普通字符如果与s的最后一个字符匹配，则返回s[0:n-2] 与 p[0:m-1]
     *       的匹配结果。如果不匹配，返回 s[0:n-1] 与 p[0:m-3]的匹配结果
     *          *前是.： 匹配或者不匹配，与上面的情况类似
     * @param str
     * @param pattern
     * @return
     */
    public boolean match (String str, String pattern) {
        if (str == null || pattern == null)
            return false;

//        if (str.length() > 0 && pattern.length() == 0)
//            return false;
//        if (str.length() == 0 && pattern.length() == 0)
//            return true;

        // 初始化状态数组，默认每个元素都是false
        boolean[][] flags = new boolean[str.length()+1][pattern.length()+1];
        flags[0][0] = true;
        for (int j = 0; j <= str.length(); j++){
            char s;
            if (j != 0){
                 s = str.charAt(j-1);
            }else {
                 s = '$';
            }
            for (int i = 1; i <= pattern.length(); i++) {
                char p = pattern.charAt(i-1);
                if (p != '*'){
                    if (p == s || p == '.'){
                        if (j != 0)
                            flags[j][i] = flags[j-1][i-1];
                    }
                }else if (p == '*'){
                    if (i > 1){
                        flags[j][i] = flags[j][i-2];
                        if ( j> 0 && (s == pattern.charAt(i-2) || pattern.charAt(i-2) == '.')){
                            flags[j][i] |= flags[j-1][i];
                        }
                    }
                }
            }
        }

        return flags[str.length()][pattern.length()];
    }

    /**
     * 请实现一个函数用来判断字符串是否表示数值（包括整数和小数）。例如，字符串"+100",
     * "5e2","-123","3.1416"和"-1E-16"都表示数值。 但是"12e","1a3.14","1.2.3",
     * "+-5"和"12e+4.3"都不是。
     *
     * 思路：我们对一个字符串是否表示数值大概有如下的判断：
     *     1、 +、- ：只能出现在字符串开始的位置，或者前面是e 或 E的情况，且之后要有数字
     *     2、 . ： 只能出现在数字之后，不能出现在e之后，只能出现在e之前，且之后也要有数字
     *     3、 e、E：只能出现数字之后，且之后也要有数字
     *     4、 数字： 可以出现在任何位置
     *     5、其他字符：出现就为flase
     *
     * @param str
     * @return
     */
    public boolean isNumeric (String str) {
        if (str == null || str.length() == 0)
            return false;

        str = str.trim();

        boolean dot = false;
        boolean e = false;
        boolean num = false;

        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c == '.' && !dot && !e && (num || i == 0 || (i == 1 &&
                    (str.charAt(i-1) == '-') || str.charAt(i-1) =='+'))){
                dot = true;
                num = false;
            }else if ((c == 'e' || c == 'E') && !e && num){
                e = true;
                num = false;
            }else if (c >= '0' && c <= '9'){
                num = true;
            }else if ((c == '+' || c == '-') && ((i == 0) ||
                    str.charAt(i-1) == 'e' || str.charAt(i-1) == 'E')){
                num = false;
            }else
                return false;
        }

        return num;
    }

    /**
     * 请实现一个函数用来找出字符流中第一个只出现一次的字符。例如，当从字符流中只读出前
     * 两个字符"go"时，第一个只出现一次的字符是"g"。当从该字符流中读出前六个字符“google"
     * 时，第一个只出现一次的字符是"l"。
     *
     * 思路：使用一个LinkedHashMap，每输入一个字符就放在linkedHashmap中，获取第一个
     * 只出现一次的字符时，就遍历整个entrySet，因为字符的数量有限制，最多为2字节 65535个
     */
    LinkedHashMap<Character,Boolean> hashMap = new LinkedHashMap<>();
    //Insert one char from stringstream
    public void Insert(char ch)
    {
        hashMap.put(ch,!hashMap.containsKey(ch));
    }
    //return the first appearence once char in current stringstream
    public char FirstAppearingOnce() {
        for (Map.Entry<Character, Boolean> entry : hashMap.entrySet()) {
            if (entry.getValue())
                return entry.getKey();
        }
        return '#';
    }

    /**
     * 给定一个数组A[0,1,...,n-1],请构建一个数组B[0,1,...,n-1],其中B中的元素
     * B[i]=A[0]*A[1]*...*A[i-1]*A[i+1]*...*A[n-1]。 不能使用除法。
     *
     * （注意：规定B[0] = A[1] * A[2] * ... * A[n-1]，
     * B[n-1] = A[0] * A[1] * ... * A[n-2];）
     *
     * 对于A长度为1的情况，B无意义，故而无法构建，因此该情况不会存在。
     *
     * 思路：题目中要求不能使用除法，因此可以考虑使用动态规划，保存下每段的乘积
     * 声明一个数组B，数组B从后往前填充，B[i] = A[i+1] * ... * A[n-1]，然后
     * 从头开始计算B中的元素，使用一个数字 m = A[0] *...* A[i-1]，所以就有
     * B[i] = m * B[i]
     * @param A
     * @return
     */
    public int[] multiply(int[] A) {
        if (A == null || A.length == 0)
            return new int[] {};
        // 声明结果集
        int[] B = new int[A.length];
        // 对数组B的一次填充
        for (int length = A.length-1; length >= 0; length--) {
            if (length == A.length-1){
                B[length] = 1;
            }else {
                B[length] = A[length+1] * B[length+1];
            }
        }

        int m = 1;
        // 对数组B的二次填充
        for (int i = 0; i < B.length; i++) {
            B[i] = m * B[i];
            m *= A[i];
        }

        return B;
    }

    /**
     * 在一个长度为n的数组里的所有数字都在0到n-1的范围内。
     * 数组中某些数字是重复的，但不知道有几个数字是重复的。也不知道每个数字
     * 重复几次。请找出数组中任一一个重复的数字。 例如，如果输入长度为7的数组
     * [2,3,1,0,2,5,3]，那么对应的输出是2或者3。存在不合法的输入的话输出-1
     *
     * 思路1：使用一个容器存储这些数字，如果遇到重复的就返回，否则返回-1；
     *
     * 思路2：数组的长度为n，且数字的范围都在 0-n-1的范围内，则最好的情况是每个
     * 数字刚好只出现一次，这些数字可以顺序的排列在数组对应的槽中，不会产生冲突或者
     * 空余的情况。
     *      基于这个思想，我们可以对numbers从头遍历，如果这个数字的值不等于他所
     *    对应index，则把他放到他对应的index上，如果这个index上已经有了他，那么
     *    说明冲突了；否则交换，继续放置
     *
     * @param numbers
     * @return
     */
    public int duplicate (int[] numbers) {
        if (numbers == null || numbers.length == 0)
            return -1;

        for (int i = 0; i < numbers.length; i++){
            while (i != numbers[i]){
                if (numbers[numbers[i]] == numbers[i]){
                    return numbers[i];
                }else {
                    swap(numbers,i,numbers[i]);
                }
            }
        }


//        HashSet<Integer> hashSet = new HashSet<>();
//        for (int number : numbers) {
//            if (hashSet.contains(number))
//                return number;
//            else
//                hashSet.add(number);
//        }
        return -1;
    }

    private void swap(int[] nums, int i, int j){
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }

    /**
     * 将一个字符串转换成一个整数，要求不能使用字符串转换整数的库函数。
     * 数值为0或者字符串不是一个合法的数值则返回0
     *
     * 思路：首先对字符串进行去除两边的空格，然后检查第一个位置是否是符号
     * 标记下这个符号的正负性； 然后对后面的数字进行逐位计算
     * @param str
     * @return
     */
    public int StrToInt(String str) {
        if (str == null || str.trim().length() == 0)
            return 0;
        String s = str.trim();
        int index = 0;
        long res = 0;
        boolean flag = true;  // 标志正负号s,true代表正号，false代表负号

        // 对第一个位置为符号位的一个判断
        if (s.charAt(index) == '+'){
            flag = true;
            index++;
        }else if (s.charAt(index) == '-'){
            flag = false;
            index++;
        }

        while (index < s.length()){
            char c = s.charAt(index);
            if (c >= '0' && c <= '9'){
                if (flag && res >= Integer.MAX_VALUE)
                    return Integer.MAX_VALUE;
                else if (!flag && res > Integer.MAX_VALUE)
                    return Integer.MIN_VALUE;

                res = res * 10 + (c - '0');
            }else
                return 0;

            index++;
        }

        if (flag && res >= Integer.MAX_VALUE)
            return Integer.MAX_VALUE;
        else if (!flag && res > Integer.MAX_VALUE)
            return Integer.MIN_VALUE;

        return flag ? (int)res : (int)-res;
    }

    /**
     * 写一个函数，求两个整数之和，要求在函数体内不得使用+、-、*、/四则运算符号。
     *
     * 思路：对于两个数的加和，我们思考二进制位上的变化
     * a + b  = a^b + (a&b << 1)
     * 所以可以考虑递归实现 或者 动态规划的方法来进行求解
     *
     * @param num1
     * @param num2
     * @return
     */
    public int Add(int num1,int num2) {
        if (num1 == 0)
            return num2;
        if (num2 == 0)
            return  num1;

        int c;
        while (num1 != 0){
            c = num1 ^ num2;
            num1 = (num1 & num2) <<1;
            num2 = c;
        }
        return num2;

//        return Add(num1^num2, (num1&num2)<<1);
    }

    /**
     * 求1+2+3+...+n，要求不能使用乘除法、for、while、if、else、switch、
     * case等关键字及条件判断语句（A?B:C）。
     *
     * 思路：不能使用循环，所以只能考虑递归的方法实现，但是如何结束递归是个问题
     *  可以考虑使用逻辑运算符来进行计算，and 操作符，具有剪枝的效果
     * @param n
     * @return
     */
    public int Sum_Solution(int n) {
        int res = 1;
        boolean f = n > 1 && (res = Sum_Solution(n-1)+n) > 0;
        return res;
    }

    /**
     * 每年六一儿童节,牛客都会准备一些小礼物去看望孤儿院的小朋友,
     * 今年亦是如此。HF作为牛客的资深元老,自然也准备了一些小游戏。
     * 其中,有个游戏是这样的:首先,让小朋友们围成一个大圈。然后,
     * 他随机指定一个数m,让编号为0的小朋友开始报数。每次喊到m-1的那个小朋友
     * 要出列唱首歌,然后可以在礼品箱中任意的挑选礼物,并且不再回到圈中,
     * 从他的下一个小朋友开始,继续0...m-1报数....这样下去....直到剩下
     * 最后一个小朋友,可以不用表演。请你试着想下,哪个小朋友会得到这份礼品呢？
     * (注：小朋友的编号是从0到n-1)
     *
     * 思路：这是一个 约瑟夫环的问题，如果我们第一次抽中的是 第m个(编号为m-1)
     * 小朋友，就将他剔除，第m+1个(编号为m)的小朋友变成新的数组的头，前面报过数
     * 的小朋友加到队尾中，重新组成一个数组，然后开始报数。
     * 所以可以分解成递归的子问题，我们对一个(n,m)问题，就只需要去递归计算
     * (n-1,m) 的问题。
     *
     * f(n,m) = (f(n-1,m) + m) % n;  容易超出栈的深度而失败
     *
     * 改成动态规划求解，利用递推公式，递推从n=1,n=2,n=3,...,n=n-1,n=n的情况
     *
     * @param n
     * @param m
     * @return
     */
    public int LastRemaining_Solution(int n, int m) {
        if (n < 1)
            return -1;
        if (n == 1)
            return 0;

//        return (LastRemaining_Solution(n-1,m)+m) %n;

        int res = 0;
        for (int i = 1; i< n; i++){
            res = (res + m) % (i+1);
        }
        return res;
    }

    /**
     * LL今天心情特别好,因为他去买了一副扑克牌,发现里面居然有2个大王,
     * 2个小王(一副牌原本是54张^_^)...他随机从中抽出了5张牌,想测测自己的手气,
     * 看看能不能抽到顺子,如果抽到的话,他决定去买体育彩票,嘿嘿！！
     * “红心A,黑桃3,小王,大王,方片5”,“Oh My God!”不是顺子.....LL不高兴了,
     * 他想了想,决定大\小 王可以看成任何数字,并且A看作1,J为11,Q为12,K为13。
     * 上面的5张牌就可以变成“1,2,3,4,5”(大小王分别看作2和4),“So Lucky!”。
     * LL决定去买体育彩票啦。
     *
     * 思路1：遍历numbers，然后记录0的个数，以及数组中最大以及最小的数字，
     * 对于中间空缺的就用0进行填充，查看是否能组成顺子  这种要求不能出现重复的数字
     *
     * @param numbers
     * @return
     */
    public boolean IsContinuous(int [] numbers) {
        if (numbers == null || numbers.length == 0)
            return false;

        Arrays.sort(numbers);
        int zeroCount = 0;
        int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;
        for (int number : numbers) {
            if (number == 0)
                zeroCount++;
            else {
                min = Math.min(min, number);
                max = Math.max(max,number);
            }
        }

        if (max-min == 4)
            return true;
        if (max - min + zeroCount == 4){
            return true;
        }
        return false;
    }

    /**
     * 牛客最近来了一个新员工Fish，每天早晨总是会拿着一本英文杂志，写些句子在本子上。
     * 同事Cat对Fish写的内容颇感兴趣，有一天他向Fish借来翻看，但却读不懂它的意思。
     * 例如，“student. a am I”。后来才意识到，这家伙原来把句子单词的顺序翻转了，
     * 正确的句子应该是“I am a student.”。
     *
     * 思路1：利用String 的API对字符串进行按照空格切分，从后往前拼接字符串。
     *
     *
     * @param str
     * @return
     */
    public String ReverseSentence(String str) {
        if (str == null || str.length() == 0)
            return str;

        String[] strings = str.split(" ");
        // 对于全为空格的情况进行处理，直接返回
        if (strings.length == 0){
            return str;
        }
        StringBuilder sb = new StringBuilder();

        for (int i = strings.length - 1; i >= 0; i--) {
            sb.append(strings[i]);
            sb.append(" ");
        }
        return sb.toString().substring(0,sb.length()-1);
    }

    /**
     * 汇编语言中有一种移位指令叫做循环左移（ROL），现在有个简单的任务，就是用字符串
     * 模拟这个指令的运算结果。对于一个给定的字符序列S，请你把其循环左移K位后的序列输出。
     * 例如，字符序列S=”abcXYZdef”,要求输出循环左移3位后的结果，即“XYZdefabc”。
     *
     * 思路1：对字符串进行截取拼接 使用string类的api
     *
     * 思路2：对字符串的字符数组进行处理
     * @param str
     * @param n
     * @return
     */
    public String LeftRotateString(String str,int n) {
        if (str == null || str.length() <= 1)
            return str;
        n = ((n-1)% str.length()) + 1;

        char[] chars = str.toCharArray();
        char[] res = new char[str.length()];
        int offest = 0;
        for (int i = n; i< chars.length; i++){
            res[offest++] = chars[i];
        }
        for (int i = 0; i < n; i++){
            res[offest++] = chars[i];
        }

        return new String(res);
    }

    /**
     * 输入一个递增排序的数组和一个数字S，在数组中查找两个数，使得他们的和正好是S，
     * 如果有多对数字的和等于S，输出两个数的乘积最小的。
     *
     * 输出：对应每个测试案例，输出两个数，小的先输出。
     *
     * 思路1：使用一个hashmap暂存扫描过的数据，并用一个最小乘积暂存结果，并保存当时的数字
     * 内容
     *
     * 思路2：使用双指针，一个指向头，一个指向尾部，由于数组是递增排列的，所以如果左指针
     * 右移的时候，右指针一定需要左移，这就不会有漏判的情况
     *
     * @param array
     * @param sum
     * @return
     */
    public ArrayList<Integer> FindNumbersWithSum(int [] array,int sum) {
        if (array == null || array.length < 2)
            return new ArrayList<>();

        ArrayList<Integer> arrayList = new ArrayList<>();
        int m = 0, n = 0, min = Integer.MAX_VALUE;
        boolean flag = false;   // 用于判断是否有结果

        int i = 0, j = array.length-1;
        while (i < j){
            if (array[i] + array[j] == sum){
                int x = array[i] * array[j];
                if (x < min){
                    flag = true;
                    min = x;
                    m = array[i];
                    n = array[j];
                }
                i++;
            }else if (array[i] + array[j] < sum){
                i++;
            }else {
                j--;
            }
        }


//        HashMap<Integer,Integer> hashMap = new HashMap<>();
//
//        for (int i = 0; i < array.length; i++) {
//            if (hashMap.containsKey(array[i])){
//                int x = array[i] * hashMap.get(array[i]);
//                if (x < min){
//                    flag = true;
//                    min = x;
//                    m = Math.min(array[i],hashMap.get(array[i]));
//                    n = Math.max(array[i],hashMap.get(array[i]));
//                }
//            }else {
//                hashMap.put(sum-array[i],array[i]);
//            }
//        }
        if (!flag)
            return arrayList;

        arrayList.add(m);
        arrayList.add(n);
        return  arrayList;
    }

    /**
     * 小明很喜欢数学,有一天他在做数学作业时,要求计算出9~16的和,他马上就写出了正确答案
     * 是100。但是他并不满足于此,他在想究竟有多少种连续的正数序列的和为100(至少包括两个
     * 数)。没多久,他就得到另一组连续正数和为100的序列:18,19,20,21,22。现在把问题交
     * 给你,你能不能也很快的找出所有和为S的连续正数序列? Good Luck!
     *
     * 思路：本题要求的是连续的正数序列，假设现在的连续正数序列 是i~j，如果这个序列之和
     * 刚好是target，就满足条件，得到了一个以i开头的连续正数序列；如果当前的正数序列大于
     * target，则说明不存在以i开头的正数序列，需要将i的位置向右挪1；如果刚好小于target，
     * 则说明目前的长度还不够，需要将j向右挪1；
     *
     * 我们需要准确意识到的问题是：** 以i开头的连续正数序列，且和为target，只有一条 **
     * @param sum
     * @return
     */
    public ArrayList<ArrayList<Integer>> FindContinuousSequence(int sum) {
        if (sum < 3)
            return new ArrayList<>();
        ArrayList<ArrayList<Integer>> arrayLists = new ArrayList<>();

        int i = 1, j = 2;
        int s = i;
        while (i <= sum/2){
            while (s < sum && j < sum){
                s += j;
                j++;
            }
            if (s == sum){
                ArrayList<Integer> tmp = new ArrayList<>();
                for (int m = i; m<j;m++){
                    tmp.add(m);
                }
                arrayLists.add(tmp);
            }
            s -= i;
            i++;
        }

        return arrayLists;
    }

    /**
     * 一个整型数组里除了两个数字之外(a,b)，其他的数字都出现了两次。请写程序找出这两个
     * 只出现一次的数字。  数组的长度一定是偶数
     *
     * 思路1：使用一个中间容器保存每个数字出现的次数，然后再遍历array，去容器中寻找
     * 只出现过一次的数字，返回。 由于数组的大小范围不可知，或者范围太大，所以考虑使用hashmap
     * 时间复杂度是O(n) 空间复杂度也是O(n)
     *
     * 思路2：考虑位运算，如果两个数字相等，那么这两个数字异或的结果就为0，因为异或
     * 满足交换律，所以对array中的所有元素进行异或操作，则异或的结果就是 c = a^b
     *
     * 对于c的二进制表示来说，如果一个位置上出现了1，则说明，a 与 b在这个位置上的
     * 二进制位是不一样的，所以可以通过与这个二进制位上的与操作，来将a 与b划分到不同的
     * 分组中，然后对这两个分组进行异或操作，就可以分别得到这两个只出现一次的数字了
     *
     * @param array
     * @return
     */
    public int[] FindNumsAppearOnce (int[] array) {
        if (array == null || array.length < 3)
            return array;

        int c = 0;
        for (int i : array) {
            c ^= i;
        }
        // 寻找从右往左数第几位上的数字为1
        int digit = 0;
        while (c !=0){
            if ((c & 1) == 1)
                break;
            else
                digit++;
            c>>>=1;
        }
        // 对array中的数字，根据第digit位上是否是1，分为两组
        int a = 0, b = 0;
        for (int i : array) {
            if (((i >>> digit) & 1) == 1)
                a ^= i;
            else
                b ^= i;
        }

        return a<b ? new int[] {a,b} : new int[] {b,a};

//        HashMap<Integer,Boolean> hashMap = new HashMap<>();
//        for (int i : array) {
//            hashMap.put(i,!hashMap.containsKey(i));
//        }
//        int[] res = new int[2];
//        int offest = 0;
//        for (Map.Entry<Integer, Boolean> entry : hashMap.entrySet()) {
//            if (entry.getValue())
//                res[offest++] = entry.getKey();
//        }
//        return res;
    }

    /**
     * 输入一棵二叉树，判断该二叉树是否是平衡二叉树。
     * 在这里，我们只需要考虑其平衡性，不需要考虑其是不是排序二叉树
     * 平衡二叉树（Balanced Binary Tree），具有以下性质：它是一棵空树或它的左右
     * 两个子树的高度差的绝对值不超过1，并且左右两个子树都是一棵平衡二叉树。
     *
     * 思路1：一个二叉树是不是平衡二叉树，取决于这个树的左右子树是不是2茶树 和 左右子树的
     * 高度相差不能大于1
     *
     * 思路2: 上面的解决方案中存在一些重复子问题，比如计算树高的时候，下层的树高会被
     * 重复计算，所以可以考虑在计算树高的时候同时判断是否是平衡二叉树
     *
     * @param root
     * @return
     */
    public boolean IsBalanced_Solution(TreeNode root) {
//        if (root == null)
//            return true;
//
//        return IsBalanced_Solution(root.left) && IsBalanced_Solution(root.right) &&
//                (Math.abs(TreeDepth(root.left)-TreeDepth(root.right)) <= 1);
        return dfsBalanced(root) != -1;
    }

    // 这个函数在计算层高的同时，比较左右子树是否是平衡二叉树，如果出现某个节点不是
    // 平衡二叉树，则直接开始抛false，因为这个是从下而上的，所以只需要比较层高就可以了
    private int dfsBalanced(TreeNode root){
        if (root == null) return 0;

        int leftDepth = dfsBalanced(root.left);
        if (leftDepth == -1) return -1;
        int rightDepth = dfsBalanced(root.right);
        if (rightDepth == -1) return -1;

        return Math.abs(leftDepth-rightDepth) <= 1 ?
                Math.max(leftDepth,rightDepth)+1: -1;
    }

    /**
     * 输入一棵二叉树，求该树的深度。从根结点到叶结点依次经过的结点（含根、叶结点）
     * 形成树的一条路径，最长路径的长度为树的深度。
     *
     * 思路：求二叉树的深度，可以对二叉树进行dfs遍历，来计算最大深度，可以考虑递归的思想
     * 当前节点处的最大深度，等于Math.max(左子树的最大深度，右子树的最大深度) + 1;
     */
    // 计算当前节点处的最大深度
    public int TreeDepth(TreeNode root) {
        if (root == null)
            return 0;
        int leftDepth = TreeDepth(root.left);
        int rightDepth = TreeDepth(root.right);

        return Math.max(leftDepth,rightDepth) + 1;
    }

    /**
     * 统计一个数字在升序数组中出现的次数。
     *
     * 思路1： 遍历数组找到这个数字，计算出现的次数
     *
     * 思路2： 因为这个数组是有序的，有序的话就可以考虑使用二分法来进行查找，可以这么使用
     * 二分法，查找这个数字k最后出现的位置j，以及k-1的最后出现的位置i，那么k的个数就是
     * j-i
     * @param array
     * @param k
     * @return
     */
    public int GetNumberOfK(int [] array , int k) {
        if (array == null || array.length == 0)
            return 0;
//        int count = 0;
//        for (int i : array) {
//            if (i > k)
//                return count;
//            else if (i == k)
//                count++;
//        }
//        return count;
        // 返回数字K在数组中出现的最后的位置
        int kIndex = binarySearch(array,0 ,array.length-1, k);
        // 返回数字k-1在数组中最后出现的位置
        int preIndex = binarySearch(array,0,array.length-1, k-1);

        return kIndex - preIndex;
    }

    // 这个方法返回的是数字k 最后出现的位置
    private int binarySearch(int[] array, int left, int right, int k){
        // 如果出现left 大于 right的情况，则只有 left = right +1
        // 如果出现这种情况，说明当前最小的left所指向的位置，刚好是比k大1的数字的最开始
        // 出现的位置
        if (left > right)
            return right;

        int mid = (left + right) /2;
        if (array[mid] <= k){
            return binarySearch(array,mid+1,right,k);
        }else {
            return binarySearch(array,left,mid-1,k);
        }
    }

    /**
     * 输入两个链表，找出它们的第一个公共结点。（注意因为传入数据是链表，所以错误测试数据
     * 的提示是用其他方式显示的，保证传入数据是正确的）
     *
     *  思路： 假设 两条链表有公共节点，则可以假设链表1可以划分为 A+ C,链表2可以划分为
     *  B+C,其中C是两条链表的公共部分， 则我们可以得出 A+ C + b = b+c+a ,因为我们可以设置
     *  两个指针，一个从A开始游走，一个从B开始游走，A走完了就走B，B走完了就走A，如果有公共节点
     *  的话，那他们游走了a+c+b之后，一定会相遇，则这个节点就有公共节点
     *
     * @param pHead1
     * @param pHead2
     * @return
     */
    public ListNode FindFirstCommonNode(ListNode pHead1, ListNode pHead2) {
        if (pHead1 == null || pHead2 == null)
            return null;
        ListNode a = pHead1, b = pHead2;
        // 就算两个链表没有公共节点，那他们在遍历到对方的最后一个null时，也会相遇
        while (a != b){
            a = a == null ? pHead2 : a.next;
            b = b == null ? pHead1 : b.next;
        }
        return a;
    }

    class ListNode{
        int val;
        ListNode next;

        public ListNode(int value, ListNode next) {
            this.val = value;
            this.next = next;
        }

        public ListNode(int value) {
            this.val = value;
        }
    }

    class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode() {}
        TreeNode(int val) { this.val = val; }
        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    class Node {
        public int val;
        public Solution.Node left;
        public Solution.Node right;

        public Node() {}

        public Node(int _val) {
            val = _val;
        }

        public Node(int _val, Solution.Node _left, Solution.Node _right) {
            val = _val;
            left = _left;
            right = _right;
        }
    }

    class TreeLinkNode {
        int val;
        TreeLinkNode left = null;
        TreeLinkNode right = null;
        TreeLinkNode next = null;

        TreeLinkNode(int val) {
            this.val = val;
        }
    }

    private static AtomicInteger state = new AtomicInteger(0);

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
//        String s = new String("123");
//        System.out.println(s);
//
//        Class<? extends String> aClass = s.getClass();
//
//        Constructor<?>[] declaredConstructors = aClass.getDeclaredConstructors();

//        for (Constructor<?> declaredConstructor : declaredConstructors) {
//            Class<?>[] parameterTypes = declaredConstructor.getParameterTypes();
//            for (Class<?> parameterType : parameterTypes) {
//                System.out.println(parameterType);
//            }
//            System.out.println(declaredConstructor);
//        }


//        CopyOnWriteArrayList<Integer> arrayList = new CopyOnWriteArrayList();
//        arrayList.add(0);
//
//
//        new Thread(() -> {
//            while (true){
////                if (state.get() != arrayList.get(0)){
////                    System.out.println("出现问题了");
////                }
//                System.out.println(arrayList.get(0));
//            }
//        }).start();
//
//        new Thread(() -> {
//            while (true){
//                arrayList.add(0,state.getAndIncrement());
//            }
//        }).start();

        System.out.println(Thread.currentThread().getContextClassLoader());

    }
}
