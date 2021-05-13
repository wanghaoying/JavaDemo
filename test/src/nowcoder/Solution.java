package nowcoder;

import org.junit.Test;
import java.util.*;

public class Solution {
    @Test
    public void test() throws InterruptedException {
        System.out.println(InversePairs(new int[] {1,2,3,4,5,6,7,0}));

//        ListNode l1 = new ListNode(2);
//        l1.next = new ListNode(3);
//        l1.next.next = new ListNode(4);

//
//        ListNode l2 = new ListNode(3);
//        l2.next = new ListNode(6);
//        l2.next.next = new ListNode(9);

//        TreeNode t1 = new TreeNode(10);
//        t1.left = new TreeNode(6);
//        t1.left.left = new TreeNode(4);
//        t1.left.right = new TreeNode(8);
//        t1.right = new TreeNode(12);
    }

    /**
     * 在数组中的两个数字，如果前面一个数字大于后面的数字，则这两个数字组成一个逆序对。
     * 输入一个数组,求出这个数组中的逆序对的总数P。并将P对1000000007取模的结果输出。
     * 即输出P%1000000007
     *
     * 思路：可以采用归并排序的思路，在两个有序数组进行合并的时候，计算他们对P的贡献
     * 这里将数组按照从大到小的顺序进行排序
     */
    long p = 0;
    public int InversePairs(int [] array) {
        if (array == null || array.length < 2)
            return 0;
        mergeSort01(array, 0 ,array.length-1);
        return (int) p%1000000007;
    }

    private void mergeSort01(int[] nums, int i, int j){
        if (i >= j)
            return ;
        int mid = (i+j) /2;
        mergeSort01(nums,i,mid);
        mergeSort01(nums,mid+1,j);
        merge01(nums,i,mid,j);
    }

    // 在合并的时候计算贡献度
    private void merge01(int[] nums, int i, int m, int j){
        int left = i, right = m+1;
        int[] tmp = new int[j-i+1];
        int offest = 0;
        while (left <= m && right <= j){
            if (nums[left] > nums[right]){
                p += (j-right+1);
                tmp[offest++] = nums[left];
                left++;
            }else if (nums[left] <= nums[right]){
                tmp[offest++] = nums[right];
                right++;
            }
        }

        while (left <= m){
            tmp[offest++] = nums[left++];
        }
        while (right <= j){
            tmp[offest++] = nums[right++];
        }

        for (int n = 0; n < offest; n++){
            nums[i+n] = tmp[n];
        }
        p %= 1000000007;
    }

    /**
     * 在一个字符串(0<=字符串长度<=10000，全部由字母组成)中找到第一个只出现一次
     * 的字符,并返回它的位置, 如果没有则返回 -1（需要区分大小写）.（从0开始计数）
     *
     *  思路1：可以使用linkedHashmap，如果出现的话就把他放到hashmap中，linkedHashmap
     *  会自动帮我们把这个字符放到最后的位置.
     *
     *  思路2：使用一个中间数组，记录下每个元素最后一次出现的位置，然后对str中的字符
     *  进行遍历，如果这个字符在数组中的位置不等于自己的位置的话(数组相应位置设为-1)，
     *  就跳过这个字符，直到找到只出现一次的字符，或者结束遍历，返回-1
     *
     * @param str
     * @return
     */
    public int FirstNotRepeatingChar(String str) {
        if (str == null || str.length() == 0)
            return -1;

        int[] flags = new int[128];
        for (int i = 0; i < str.length(); i++) {
            flags[str.charAt(i)] = i;
        }

        for (int i = 0; i < str.length(); i++) {
            if (flags[str.charAt(i)] == i){
                return i;
            }else {
                flags[str.charAt(i)] = -1;
            }
        }


//        LinkedHashMap<Character,Integer> hashMap = new LinkedHashMap<>();
//        for (int i = 0; i < str.length(); i++) {
//            if (hashMap.containsKey(str.charAt(i))){
//                hashMap.put(str.charAt(i),hashMap.get(str.charAt(i))+1);
//            }else {
//                hashMap.put(str.charAt(i),1);
//            }
//        }
//        char s = 1;
//        for (Map.Entry<Character, Integer> entry : hashMap.entrySet()) {
//            if (entry.getValue() == 1){
//                s = entry.getKey();
//                break;
//            }
//        }
//        for (int i = 0; i < str.length(); i++) {
//            if (str.charAt(i) == s){
//                return i;
//            }
//        }
        return -1;
    }

    /**
     * 把只包含质因子2、3和5的数称作丑数（Ugly Number）。例如6、8都是丑数，
     * 但14不是，因为它包含质因子7。 习惯上我们把1当做是第一个丑数。求按从小到大
     * 的顺序的第N个丑数。
     *
     * 思路：可以参考归并的思路，设计三个容器，
     * 第一个容器：[2*1, 2*2, 2*3, 2*4, 2*5,...]
     * 第二个容器：[3*1, 3*2, 3*3, 3*4, 3*5,...]
     * 第三个容器: [5*1, 5*2, 5*3, 5*4, 5*5,...]
     *  依次从这三个容器中选择最小的加入到最后的结果中，所以可以考虑设置三个指针
     *
     * @param index
     * @return
     */
    public int GetUglyNumber_Solution(int index) {
        if (index < 1) return 0;

        int[] array = new int[index];
        array[0] = 1;
        int i = 0, j = 0, k = 0;
        for(int offest = 1; offest < index; offest++){
            array[offest] = Math.min(2*array[i],
                    Math.min(3*array[j],5*array[k]));
            if (array[offest] == 2*array[i]){
                i++;
            }
            if(array[offest] == 3*array[j]){
                j++;
            }
            if (array[offest] == 5*array[k]){
                k++;
            }
        }
        return array[index-1];
    }

    /**
     * 输入一个正整数数组，把数组里所有数字拼接起来排成一个数，打印能拼接出的所有数字
     * 中最小的一个。例如输入数组{3，32，321}，则打印出这三个数字能排成的最小数字为
     * 321323。
     *
     * 思路：可以考虑将数组中的元素按照某种规则来进行排序，然后遍历排序之后的数组组成
     * 字符串返回
     *
     * 规则：将数组中的元素变成字符串，然后对字符串进行比较。
     * 两个字符串从第一个字符开始比较，如果第一个相同，则比较第二个，否则将
     * 第一个字符的比较结果返回，依次比较，如果中间有一个字符串已经遍历完了，就用
     * 他的首字符填充
     *
     *  o1 o2均为字符串
     * 上面的规则可以演变为 (o1+o2).compareTo(o2+o1) ，将这个结果返回
     *
     * @param numbers
     * @return
     */
    public String PrintMinNumber(int [] numbers) {
        if (numbers == null || numbers.length == 0)
            return "";

        String[] strings = new String[numbers.length];
        for (int i = 0; i< numbers.length; i++){
            strings[i] = Integer.valueOf(numbers[i]).toString();
        }

//        Arrays.sort(strings, new Comparator<String>() {
//            @Override
//            public int compare(String o1, String o2) {
//                return (o1+o2).compareTo(o2+o1);
//            }
//        });
        Arrays.sort(strings, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                int i = 0, j = 0;
                char m, n;
                while (i<o1.length()|| j<o2.length()){
                    if (i >= o1.length()){
                        m = o1.charAt(0);
                        n = o2.charAt(j++);
                    }else if (j >= o2.length()){
                        m = o1.charAt(i++);
                        n = o2.charAt(0);
                    }else {
                        m = o1.charAt(i++);
                        n = o2.charAt(j++);
                    }
                    if (m > n){
                        return 1;
                    }else if (m < n){
                        return -1;
                    }
                }
                return 0;
            }
        });

        StringBuilder stringBuilder = new StringBuilder();
        for (String string : strings) {
            stringBuilder.append(string);
        }

        return stringBuilder.toString();
    }

    /**
     * 求出1~13的整数中1出现的次数,并算出100~1300的整数中1出现的次数？
     * 为此他特别数了一下1~13中包含1的数字有1、10、11、12、13因此共出现6次,
     * 但是对于后面问题他就没辙了。ACMer希望你们帮帮他,并把问题更加普遍化,
     * 可以很快的求出任意非负整数区间中1出现的次数（从1 到 n 中1出现的次数）。
     *
     *  思路：对于1～n，我们分别计算每个10进制位上出现1的次数，然后进行累加
     *  对于当前的数位cur,其高位是high，低位是low，当前的位数dight(十位，百位，千位。。)
     *  对于当前的数位cur，存在：
     *          1、cur = 0， 这个位上的1 的个数为 high*dight
     *          2、cur = 1，1的个数为 high*dight + low + 1
     *          3、cur > 1, 1的个数为 (hight+1) * dight
     *
     * @param n
     * @return
     */
    public int NumberOf1Between1AndN_Solution(int n) {
        if (n < 1)
            return 0;
        int sum = 0;
        int high = n /10, cur = n % 10, dight = 1;
        int low = 0;
        while (true){
            if (cur == 0){
                sum += high * dight;
            }else if (cur == 1){
                sum += high * dight + low + 1;
            }else {
                sum += (high+1) * dight;
            }
            if (high == 0)
                break;
            cur = high %10;
            high = high /10;
            dight *= 10;
            low = n-(high*dight*10 + cur * dight);
        }
        return sum;
    }

    /**
     * 输入一个整型数组，数组里有正数也有负数。数组中的一个或连续多个整数组成一个
     * 子数组。求所有子数组的和的最大值。要求时间复杂度为 O(n).
     *
     * 思路：对于一个数字，前面的数字或者子串，能不能加在一起，取决于前面的数字或者
     * 子串的和是否大于0，如果小于0，则加在一起不如这个数字单独的贡献度，如果大于0，则
     * 加在一起的贡献度大于这个数字单独的贡献度
     *
     * 基于这个思考，如果前面子串之和大于0，则加入，否则不加入
     * @param array
     * @return
     */
    public int FindGreatestSumOfSubArray(int[] array) {
        if (array == null || array.length == 0)
            return 0;

        int sum = 0, max = array[0];
        for (int i = 0; i < array.length; i++) {
            if (sum <= 0){
                sum = array[i];
            }else if (sum > 0){
                sum += array[i];
            }
            max = Math.max(max,sum);
        }
        return max;
    }

    /**
     * 给定一个数组，找出其中最小的K个数。例如数组元素是4,5,1,6,2,7,3,8这8个数字，
     * 则最小的4个数字是1,2,3,4。如果K>数组的长度，那么返回一个空的数组
     *
     * 思路：利用快速排序的思想，找到一个中间位置，类似二分的进行查找
     *
     * @param input
     * @param k
     * @return
     */
    public ArrayList<Integer> GetLeastNumbers_Solution(int [] input, int k) {
        if (input == null || input.length == 0 || k > input.length || k == 0)
            return new ArrayList<>();

        quickSearch01(input,0,input.length-1,k);
        ArrayList<Integer> arrayList = new ArrayList<>(k);
        for (int i = 0; i < k; i++){
            arrayList.add(input[i]);
        }
        return arrayList;
    }
    // 返回前k个小元素的索引位置
    private int quickSearch01(int[] nums, int left, int right,int k){
        int index = getIndex01(nums,left,right);

        if (index == k)
            return index;
        else if (index > k)
            return quickSearch01(nums, left, index-1,k);
        else
            return quickSearch01(nums,index,right,k-index);
    }
    // 取一个数字，然后将小于这个数字的数，放在这个数字的左边，大于这个数字的放在右边
    private int getIndex01(int[] nums, int left ,int right){
        int n = nums[left];
        // i用来寻找大于num的数字，j用来寻找小于num的数字
        int i = left, j = right;
        while (i <= j){
            while (i <= right && nums[i] <= n){
                i++;
            }
            while (j >= left && nums[j] > n){
                j--;
            }
            if (i > j) break;

            swap(nums,i,j);
        }

        swap(nums,left,j);
        return j;
    }

    /**
     * 数组中有一个数字出现的次数超过数组长度的一半，请找出这个数字。例如输入一个
     * 长度为9的数组{1,2,3,2,2,2,5,4,2}。由于数字2在数组中出现了5次，超过数组
     * 长度的一半，因此输出2。如果不存在则输出0。
     *
     * 思路1：使用hashmap，key为数字，value为数字出现的次数
     *
     * 思路2：假设这个数字真实存在，那么这个数字出现的次数一定大于 array.length /2,
     * 如果设置这个数字为1，其他数字为-1，则最终，数组中的所有元素加起来，应该大于0
     *
     * 所以开始的时候，认为第一个数字是目标数字，进行加1，开始遍历，遇到不相同的就减1，
     * 当为0时，重新认定目标数字，所以最后保留的那个数字一定是目标数字
     *
     * 由于并不保证，目标数字一定存在，所以在遍历完所有的数字之后，应当对得到的结果进行
     * 检查，如果满足大于一半，则返回，否则返回0
     *
     * @param array
     * @return
     */
    public int MoreThanHalfNum_Solution(int [] array) {
//        if (array == null || array.length == 0)
//            return 0;
//
//        if (array.length == 1)
//            return array[0];
//
//        HashMap<Integer,Integer> hashMap = new HashMap<>();
//        for (int i : array) {
//            if (hashMap.containsKey(i)){
//                int count = hashMap.get(i) + 1;
//                if (count > (array.length/2))
//                    return i;
//                else
//                    hashMap.put(i,count);
//            }else {
//                hashMap.put(i,1);
//            }
//        }

        if (array == null || array.length == 0)
            return 0;

        int sum = 0, target = array[0];
        // 遍历数组寻找目标数字
        for (int i = 0; i < array.length; i++){
            if (sum == 0){
                target = array[i];
                sum++;
            }else {
                if (array[i] == target){
                    sum++;
                }else {
                    sum--;
                }
            }
        }
        // 对目标数字重新进行检查
        sum = 0;
        for (int i : array) {
            if( i == target){
                sum++;
            }
        }
        return sum > (array.length)/2 ?  target : 0;
    }

    /**
     * 输入一个字符串,按字典序打印出该字符串中字符的所有排列。例如输入字符串abc,
     * 则按字典序打印出由字符a,b,c所能排列出来的所有字符串abc,acb,bac,bca,cab
     * 和cba。
     *
     * 思路：对字符串进行dfs遍历，首先确定第一个位置的元素，有s.length种选法，
     * 接下来，第二个位置有s.length-1 种选法，直到确定了最后一个位置的元素
     * @param str
     * @return
     */
    public ArrayList<String> Permutation(String str) {
        if (str == null || str.length() == 0)
            return new ArrayList<>();

        ArrayList<String> res = new ArrayList<>();
        char[] chars = str.toCharArray();

        Permutation(chars,res,0);

        res.sort(null);
        return res;
    }

    private void Permutation(char[] chars, ArrayList<String> strings,
                             int index){
        if (index == chars.length-1)
            strings.add(new String(chars));

        HashSet<Character> hashSet = new HashSet<>();
        // 确定第一个位置上的元素
        for (int i = index; i < chars.length; i++){
            if (!hashSet.contains(chars[i])){
                hashSet.add(chars[i]);
                // 将确定的第一个元素搬移到第0号位置上
                swap(chars,index ,i);
                // 转换成子问题，用来确定[index+1, chars.length-1]之间的元素
                Permutation(chars,strings,index+1);
                // 再将这两个数字交换回来，不影响后续判断
                swap(chars,index,i);
            }else
                continue;
        }
    }

    private void swap(char[] chars, int i, int j){
        char tmp = chars[i];
        chars[i] = chars[j];
        chars[j] = tmp;
    }

    /**
     * 输入一棵二叉搜索树，将该二叉搜索树转换成一个排序的双向链表。
     * 要求不能创建任何新的结点，只能调整树中结点指针的指向。
     *
     * 思路：二叉搜索树左边子树的任何值都小于根节点，右边子树的所有值都大于根节点
     * 题目中要求了有序，所以可以考虑二叉树的中序遍历。
     *
     * 对中序遍历的结果集进行顺序的连接
     *
     * @param pRootOfTree
     * @return
     */
    public TreeNode Convert(TreeNode pRootOfTree) {
        if (pRootOfTree == null)
            return null;

        TreeNode head;
        head = new TreeNode(0);

        inOrder(pRootOfTree,head);
        head.right.left = null;

        return head.right;
    }

    private TreeNode inOrder(TreeNode root,TreeNode cur){
        if (root == null){
            return cur;
        }
        // 先遍历左子树
        cur = inOrder(root.left,cur);
        // 设置根节点
        cur.right = root;
        root.left = cur;
        cur = cur.right;
        // 遍历右子树
        cur = inOrder(root.right,cur);
        return  cur;
    }


    /**
     * 输入一个复杂链表（每个节点中有节点值，以及两个指针，一个指向下一个节点，
     * 另一个特殊指针random指向一个随机节点），请对此链表进行深拷贝，并返回拷贝
     * 后的头结点。（注意，输出结果中请不要返回参数中的节点引用，否则判题程序会
     * 直接返回空）
     *
     * 思路：在每个链表中的每个节点处，复制一个节点，然后将这个节点加入到当前节点之后，
     * 对所有的节点执行这个操作，然后再从头开始扫描，设置random指针，然后再将这个链表
     * 分成一个旧的，一个新的链表
     *
     * @param pHead
     * @return
     */
    public RandomListNode Clone(RandomListNode pHead) {
        if (pHead == null)
            return null;

        RandomListNode p = pHead;
        // 复制节点
        while (p != null){
            RandomListNode tmp = new RandomListNode(p.label);
            tmp.next = p.next;
            p.next = tmp;
            p = p.next.next;
        }
        // 对新节点的random属性进行设置
        p = pHead;
        while (p != null){
            p.next.random = p.random == null ? null : p.random.next;
            p = p.next.next;
        }
        // 进行新旧链表的拆分
        RandomListNode newHead = pHead.next;
        p = newHead;
        pHead.next = p.next;
        pHead = pHead.next;
        while (pHead != null){
            p.next = pHead.next;
            p = p.next;
            pHead.next = p.next;
            pHead = pHead.next;
        }

        return newHead;
    }

    /**
     * 输入一颗二叉树的根节点和一个整数，按字典序打印出二叉树中结点值的和为输入整数的
     * 所有路径。路径定义为从树的根结点开始往下一直到叶结点所经过的结点形成一条路径。
     *
     * 思路：对二叉树进行dfs遍历，并配合剪枝，如果计算当前节点之后，已经大于target，
     * 则后面的自节点不再遍历
     *
     * @param root
     * @param target
     * @return
     */
    private ArrayList<ArrayList<Integer>> pathArray = new ArrayList<>();

    public ArrayList<ArrayList<Integer>> FindPath(TreeNode root,int target) {
        if (root == null)
            return new ArrayList<>();

        dfsForFindPath(root,target,new ArrayList<>());
        return pathArray;
    }

    private void dfsForFindPath(TreeNode root, int target,
                                ArrayList<Integer> array){

        // 将当前的root节点添加到结果数组中
        array.add(root.val);

        if (root.left == null && root.right == null){
            if (root.val == target){
                pathArray.add(new ArrayList<>(array));
                return;
            }
            return;
        }

        if (root.val > target)
            return;
        else {
            if (root.right == null){
                dfsForFindPath(root.left,target-root.val,array);
                array.remove(array.size()-1);
            }else if (root.left == null){
                dfsForFindPath(root.right,target-root.val,array);
                array.remove(array.size()-1);
            }else {
                dfsForFindPath(root.left,target-root.val,array);
                array.remove(array.size()-1);
                dfsForFindPath(root.right,target-root.val,array);
                array.remove(array.size()-1);
            }
        }
    }

    /**
     * 输入一个整数数组，判断该数组是不是某二叉搜索树的后序遍历的结果。如果是则返回true,
     * 否则返回false。
     * 假设输入的数组的任意两个数字都互不相同。（ps：我们约定空树不是二叉搜素树）
     *
     * 思路：根据树的后序遍历的特点，根节点是数组的最后一个元素，同时二叉搜索树要求，左
     * 子树中的所有值，都小于根节点，右子树的所有值都大于根节点
     *
     * 所以在确定根节点的位置后，主要看左子树 和 右子树是不是二叉搜索树的后序遍历
     *
     * @param sequence
     * @return
     */
    public boolean VerifySquenceOfBST(int [] sequence) {
        if (sequence == null || sequence.length == 0)
            return false;

        return isPostOrderOfBST(sequence,0,sequence.length-1);
    }
    private boolean isPostOrderOfBST(int[] nums, int i, int j){
        if (i > j)
            return true;
        // 定位root节点
        int root = nums[j];
        // 定位左子树 和 右子树的分界点
        int right= i;
        while (nums[right] < root)
            right++;

        int left= j;
        while (left >= i && nums[left] >= root)
            left--;

        // 判断左右子树 是否准确的分割成了两边，如果没有，则返回false
        if (right <= left)
            return false;

        return isPostOrderOfBST(nums,i,left) &&
                isPostOrderOfBST(nums,right,j-1);
    }

    /**
     * 从上往下打印出二叉树的每个节点，同层节点从左至右打印。
     *
     * 思路：对二叉树进行bfs遍历，可以考虑使用一个队列存储下层节点
     * @param root
     * @return
     */
    public ArrayList<Integer> PrintFromTopToBottom(TreeNode root) {
        if (root == null)
            return new ArrayList<>();
        ArrayList<Integer> arrayList = new ArrayList<>();
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()){
            for (int i = queue.size(); i > 0; i--){
                TreeNode t = queue.poll();
                arrayList.add(t.val);
                if (t.left != null) queue.offer(t.left);
                if (t.right != null) queue.offer(t.right);
            }
        }
        return arrayList;
    }

    /**
     * 输入两个整数序列，第一个序列表示栈的压入顺序，请判断第二个序列是否可能为该栈的
     * 弹出顺序。假设压入栈的所有数字均不相等。例如序列1,2,3,4,5是某栈的压入顺序，
     * 序列4,5,3,2,1是该压栈序列对应的一个弹出序列，但4,3,5,1,2就不可能是该压栈序列
     * 的弹出序列。（注意：这两个序列的长度是相等的）
     *
     * 思路：使用一个栈来模拟操作，当栈为空的时候，从pushA中拿一个数据push进去，如果
     * 现在栈顶的元素和popA的首元素相同，则出栈，否则再入栈一个元素，然后继续比较
     * 如果pushA中已经没有元素了，但是popA依然不能出栈的话， 则返回false； 如果能出栈
     * 完所有的 元素，则返回true
     *
     * @param pushA
     * @param popA
     * @return
     */
    public boolean IsPopOrder(int [] pushA,int [] popA) {
        if (pushA == null || popA == null)
            return false;
        if (pushA.length != popA.length)
            return false;
        Stack<Integer> stack = new Stack<>();
        // i 和 j 分别为指向pushA 和 popA 中首元素的指针
        int i = 0, j = 0;
        while (i < pushA.length){
            stack.push(pushA[i++]);
            while (j < popA.length && !stack.isEmpty()
                    && stack.peek() == popA[j]){
                stack.pop();
                j++;
            }
        }
        return stack.isEmpty();
    }

    /**
     * 输入一个矩阵，按照从外向里以顺时针的顺序依次打印出每一个数字，例如，如果输入如下
     * 4 X 4矩阵： 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 则依次打印出数字
     * 1,2,3,4,8,12,16,15,14,13,9,5,6,7,11,10.
     *
     * 思路：总结题意，得出目的是将矩阵进行顺时针的遍历打印，可以按照如下的思路进行打印
     *   [up,left] -> [up,right]
     *     ^               ||
     *     ||              V
     *  [down,left] <- [down,right]
     * @param matrix
     * @return
     */
    public ArrayList<Integer> printMatrix(int [][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0)
            return new ArrayList<>();
        // 设置遍历二维数组的边界
        int left = 0, right = matrix[0].length-1;
        int up = 0, down = matrix.length-1;
        // 声明一个返回变量
        ArrayList<Integer> arrayList = new ArrayList<>();
        // 对二维数组matrix开始遍历
        while (up <= down  && left <= right){
            // 对最上层进行遍历
            for (int i = left; i <= right; i++){
                arrayList.add(matrix[up][i]);
            }
            up++;
            // 对最右侧进行遍历
            for (int i = up; i <= down; i++){
                arrayList.add(matrix[i][right]);
            }
            right--;
            // 对最下层进行遍历
            if (up > down) break;      // 确保数字不会重复被添加 （matrix为一行）
            for (int i = right; i >= left; i--){
                arrayList.add(matrix[down][i]);
            }
            down--;
            // 对最左侧进行遍历
            if (left > right) break;   // 确保数字不会重复被添加  (matrix为1列)
            for (int i = down; i >= up; i--){
                arrayList.add(matrix[i][left]);
            }
            left++;
        }

        return arrayList;
    }

    /**
     * 操作给定的二叉树，将其变换为源二叉树的镜像。
     *
     * 思路：可以考虑递归的方式实现，例如 root.left 指向 pRoot.right 的镜像， root.right
     * 指向pRoot.left的镜像
     *
     * 注意：需要新建TreeNode，不能使用原来的treeNode，即不能 root = proot
     * 这样会造成一下问题，比如：
     * root.left -> Mirror(proot.right)
     * root.right -> Miffor(proot.left) ==> 这里的left已经被覆盖了，不是原来的left了
     * 而是变成了Mirror(proot.right)
     *
     * @param pRoot
     * @return
     */
    public TreeNode Mirror (TreeNode pRoot) {
        if (pRoot == null)
            return null;

        TreeNode root = new TreeNode(pRoot.val);
        root.left = Mirror(pRoot.right);
        root.right = Mirror(pRoot.left);
        return root;
    }

    /**
     * 输入两棵二叉树A，B，判断B是不是A的子结构。
     * （ps：我们约定空树不是任意一个树的子结构）
     *
     * 思路：因为约定空树不是任意一个树的子结构，所以我们一开始要对root2为null的情况，
     * 先进行一个判断，然后再去按照下面的规则进行匹配
     *
     * 1、如果root1.val == root2.val ，root2是否是root1的子树有以下几种情况
     *      1)、root2 是否是root1.left 或者 root1.right 的子树
     *      2)、root1.left.val == root2.left.val && root1.right == root2.right
     *          一直递归到root1 或者 root2中没有元素了
     * 2、如果不相等，则root2是否是root1的子树取决于：
     *     root2 是否是root1.left 或者 root1.right 的子树
     * @param root1
     * @param root2
     * @return
     */
    public boolean HasSubtree(TreeNode root1,TreeNode root2) {
        if (root1 == null || root2 == null)
            return false;

        boolean flag = false;
        if (root1.val == root2.val){
            flag = isSubtree(root1.left, root2.left) &&
                    isSubtree(root1.right, root2.right);
        }
        return flag || HasSubtree(root1.left,root2)
                || HasSubtree(root1.right,root2);
    }

    private boolean isSubtree(TreeNode root1, TreeNode root2){
        if (root2 == null)
            return true;
        if (root1 == null)
            return false;

        if (root1.val == root2.val)
            return (isSubtree(root1.left,root2.left) &&
                    isSubtree(root1.right,root2.right));
        return false;
    }

    /**
     * 输入两个单调递增的链表，输出两个链表合成后的链表，当然我们需要合成后的链表满足
     * 单调不减规则。
     *
     * 思路：采用归并排序中的思想，比较两个指针头节点的大小，谁小谁先加入到结果节点中
     * @param list1
     * @param list2
     * @return
     */
    public ListNode Merge(ListNode list1,ListNode list2) {
        // 声明一个新的头节点
        ListNode newHead = new ListNode(0);
        ListNode p = newHead;
        while (list1 != null && list2 != null){
            if (list1.val <= list2.val){
                p.next = list1;
                list1 = list1.next;
            }else {
                p.next = list2;
                list2 = list2.next;
            }
            p = p.next;
        }
        p.next = list1 == null ? list2 : list1;

        return newHead.next;
    }

    /**
     * 输入一个链表，反转链表后，输出新链表的表头。
     *
     * 思路：从表头位置开始，递归的将节点指向前面的节点
     * @param head
     * @return
     */
    public ListNode ReverseList(ListNode head) {
        if (head == null)
            return head;
        // 使用一个节点来指向链表的头节点
        ListNode newHead = new ListNode(0);
        newHead.next = head;
        // 使用prev变量来暂存前一个节点
        ListNode  prev = newHead;
        while (head != null){
            ListNode tmp = head.next;
            head.next = prev;
            prev = head;
            head = tmp;
        }
        // 将原先头节点的next设置为null
        newHead.next.next = null;
        newHead.next = prev;

        return newHead.next;
    }

    /**
     * 输入一个链表，输出该链表中倒数第k个结点。
     *
     *
     * 思路1：计算出链表的长度，然后计算出这个倒数第K节点是数组中的正数第几位，然后从头
     * 重新遍历一次，这种可以应对 k大于链表长度的情况
     *
     * 思路2：对于如果确定k的取值不会超过链表的长度，则可以使用一个快慢指针，快指针先走
     * k步，然后慢指针再加入，当快指针走到null的时候，慢指针所指向的位置就是倒数第K个
     *
     * @param pHead
     * @param k
     * @return
     */
    public ListNode FindKthToTail (ListNode pHead, int k) {
        if (pHead == null || k <= 0) return null;
        ListNode p = pHead, q = pHead;
        int i = 0;
        while (p != null && i < k){
            p = p.next;
            i++;
        }
        if (p == null && i < k){
            return null;
        }

        while (p != null){
            p = p.next;
            q = q.next;
        }

        return q;
    }

    /**
     * 输入一个整数数组，实现一个函数来调整该数组中数字的顺序，使得所有的奇数位于
     * 数组的前半部分，所有的偶数位于数组的后半部分，并保证奇数和奇数，偶数和偶数
     * 之间的相对位置不变。
     *
     * 思路1：使用一个中间数组，依次扫描array，然后奇数从左填，偶数从右填
     *
     * 思路2：因为要保持原来的相对顺序，所以在从左往右寻找奇数的时候，如果中间有偶数
     * 则让偶数递推的向后移动一位，来保证偶数之间的相对顺序
     *
     * @param array
     * @return
     */
    public int[] reOrderArray (int[] array) {
        if (array == null || array.length == 0) return array;
        // offest用来表示当前整理好的奇数的最后一个位置
        int offest = -1;
        for (int i = 0; i < array.length; i++){
            if ((array[i] % 2) == 1){
                int cur = array[i];
                for (int j = i-1; j>offest; j--){
                    array[j+1] = array[j];
                }
                array[++offest] = cur;
            }
        }
        return array;
    }

    /**
     * 给定一个double类型的浮点数base和int类型的整数exponent。求base的exponent次方。
     *
     * 保证base和exponent不同时为0
     *
     * 思路1： 暴力法，直接对base相乘exponent次 时间复杂度是O(n)
     *
     * 思路2： 在上面的问题中，存在这很多重复子问题的求解，比如当n等于8 的时候，需要计算8
     * 次，但是可以通过二分法来减少计算次数，比如 8 可以 分解成 4+4 4可以分解成2+2 2分解成
     * base*base ,所以上面原来要求8次的计算，就可以减少到3次，大大减少了计算次数
     *
     * 思路3： 任何一个数字都可以分解成 1，2，4，8，16，...这些数字之和，如
     * 15 = 8 + 4 + 2 + 1， 分解到比特位上时，如果这个比特位是1，那么exponent分解
     * 的数字列表中就有这个数字，可以实现最高不超过32次的计算
     *
     * @param base
     * @param exponent
     * @return
     */
    public double Power(double base, int exponent) {
//        boolean flag = exponent < 0;
//        exponent = Math.abs(exponent);
//        double res = 1;
//        for (int i = 0; i < exponent; i++) {
//            res *= base;
//        }
//        return flag ? 1.0/res : res;

//        boolean flag = exponent < 0;
//        exponent = Math.abs(exponent);
//        double res = power(base, exponent);
//        return flag ? 1.0/res : res;
        boolean flag = exponent < 0;
        exponent = Math.abs(exponent);
        double res = 1;

        while (exponent != 0){
            if ((exponent & 1) == 1){
                res *= base;
            }
            base *= base;
            exponent >>>= 1;
        }
        return flag ? 1.0/res : res;
    }

    private double power(double base, int ex){
        if (ex == 0)
            return 1;
        double x = power(base, ex/2);
        return (ex % 2) == 1 ? x*x*base : x*x;
    }

    /**
     * 输入一个整数，输出该数32位二进制表示中1的个数。其中负数用补码表示。
     *
     * 思路：一个数字通过与 1相与 可以判断最后一个位上是不是1，如果是1，相与的结果是1，
     * 如果不是1，相与的结果就是0，可以通过这个来判断
     * @param n
     * @return
     */
    public int NumberOf1(int n) {
        int num = 0;
        while (n != 0){
            if ((n & 1) == 1)
                num++;
            n >>>= 1;
        }
        return num;
    }

    /**
     * 我们可以用2*1的小矩形横着或者竖着去覆盖更大的矩形。请问用n个2*1的小矩形无重叠
     * 地覆盖一个2*n的大矩形，总共有多少种方法？ 2为高，已经确定，变化的是矩形的长度n
     *
     * 思路：也是动态规划的问题，可以想象，一个长度为n的矩形，其最左边的情况，只有两种
     * 一种是最左边是一个2*1的小长方体，或者是一个2*2的正方形，被从中间分割开，当最左边
     * 是个2*1的小长方体的时候，他右边的情况有f(n-1)种，当最左边是一个2*2的被从中间
     * 分割开的正方形的时候，其右边情况有f(n-2)种，所以总结出如下的公式：
     *  f(n) = f(n-1) + f(n-2)
     * @param target
     * @return
     */
    public int rectCover(int target) {
        if (target <= 2)
            return target;

        int p =1, q = 2;
        for (int i = 2; i < target; i++){
            int temp = q;
            q = p + q;
            p = temp;
        }
        return q;
    }

    /**
     * 一只青蛙一次可以跳上1级台阶，也可以跳上2级……它也可以跳上n级。求该青蛙跳上一个
     * n级的台阶总共有多少种跳法。
     *
     * 思路：当跳到第n阶台阶上时，可以分解成下面的情况(记f(n)为在第n阶台阶上的跳法数)：
     *      f(n)   = f(0) + f(1) + f(2) + ... + f(n-1)
     *      f(n-1) = f(0) + f(1) + ... + f(n-2)
     *      ||
     *      ||
     *      \/
     *      f(n) = 2* (f(0) + f(1) + ... + f(n-2))
     *      = 4* (f(0) + f(1) + ... + f(n-3))
     *      .
     *      .
     *      = 2^(n-2) (f(0) + f(1)) = 2^ (n-2) * 2 = 2^(n-1)
     * @param target
     * @return
     */
    public int jumpFloorII(int target) {
        if (target < 0)
            return 0;
        if (target == 0)
            return 0;
        return  (int) Math.pow(2,target-1);
    }

    /**
     * 一只青蛙一次可以跳上1级台阶，也可以跳上2级。求该青蛙跳上一个n级的台阶总共有
     * 多少种跳法（先后次序不同算不同的结果）。
     *
     * 思路：和斐波那契数列的思想类似，当在n阶台阶上时，有两种过来的方法，一种是从n-1阶
     * 台阶上来的，另一种是从n-2阶台阶上上来的，所以到n阶台阶的最多跳法，就等于n-1的最多
     * 跳法 + n-2阶的最多跳法
     *
     * @param target
     * @return
     */
    public int JumpFloor(int target) {
        if (target <= 0)
            return 0;
        int p = 0, q= 1;
        for (int i = 1; i < target; i++){
            int temp = p+q;
            p = q;
            q= temp;
        }
        return q;
    }

    /**
     * 大家都知道斐波那契数列，现在要求输入一个整数n，请你输出斐波那契数列的第n项
     * （从0开始，第0项为0，第1项是1）。 f(n) = f(n-1) + f(n-2)
     *
     * 思路1： 根据上面的公式，进行递归计算
     *
     * 思路2： 上面进行递归计算的时候，存在很多的重复子问题，比如计算f(n-1) 的时候，同样
     * 也要计算一遍f(n-2) 的值，所以这就可以考虑使用动态规划来进行求解，因为f(n)的值只
     * 依赖于n-1 和 n-2的，所以只要使用两个临时变量，就可以实现状态表的效果
     *
     * @param n
     * @return
     */
    public int Fibonacci(int n) {
        if (n <= 0)
            return 0;
        int p = 0,q = 1;
        for (int i = 1; i < n; i++){
            int temp = p + q;
            p = q;
            q = temp;
        }
        return q;
    }

    /**
     * 把一个数组最开始的若干个元素搬到数组的末尾，我们称之为数组的旋转。
     * 输入一个非递减排序的数组的一个旋转，输出旋转数组的最小元素。
     * NOTE：给出的所有元素都大于0，若数组大小为0，请返回0。
     *
     * 思路：这个数组是局部有序的，可以堪称 低--高-低--高
     * 可以考虑二分法来进行查找 定位中间的数组mid，比较mid和两边的left 和 right边界的大小
     * 1、如果left < right ,则最小值是left处取得
     * 2、如果left >= right, 则需要看mid的值：
     *      1、如果mid > right，则最小值应该在 [mid+1，right]上取得
     *      2、如果mid = right，则 left > mid, 最小值应该在[left,mid]取的
     *      3、如果mid < right, 则 最小值应该在[left,mid] 处取得
     * @param array
     * @return
     */
    public int minNumberInRotateArray(int [] array) {
        if (array == null || array.length == 0)
            return 0;

        int left = 0, right = array.length-1;
        while (left <= right){
            if (left == right || array[left] < array[right])
                return array[left];
            else {
                int mid = (left + right) /2;
                if (array[mid] > array[right]){
                    left = mid+1;
                }else {
                    right = mid;
                }
            }
        }
        return 0;
    }

    /**
     * 用两个栈来实现一个队列，完成队列的Push和Pop操作。 队列中的元素为int类型。
     *
     * 思路：队列的思想是先进先出(FIFO)，栈的思想是后入先出，所以考虑，存的时候先向一个栈
     * 中存放，当需要取的时候，对这个栈进行全部的出栈操作，放入另外一个栈中，这个时候，对于
     * 这些数据就是按照FIFO的顺序，存放了，stack1 专门用来做push，stack2专门用来做pop
     */
    Stack<Integer> stack1 = new Stack<Integer>();
    Stack<Integer> stack2 = new Stack<Integer>();

    public void push(int node) {
        stack1.push(node);
    }

    public int pop() {
        // 将stack1中的数据放进stack2中，这时stack2的出栈顺序，就是原来序列的FIFO
        if (stack2.isEmpty()){
            while (!stack1.isEmpty()){
                stack2.push(stack1.pop());
            }
        }
        if (stack2.isEmpty())
            return -1;
        return stack2.pop();
    }

    /**
     * 输入某二叉树的前序遍历和中序遍历的结果，请重建出该二叉树。假设输入的前序遍历和
     * 中序遍历的结果中都不含重复的数字。例如输入前序遍历序列{1,2,4,7,3,5,6,8}和中
     * 序遍历序列{4,7,2,1,5,3,8,6}，则重建二叉树并返回。
     *
     * 思路：根据前序遍历和中序遍历的性质，前序遍历的第一个数字就是根节点，然后去中序
     * 遍历中，找到这个数组，这个数字左边就是左子树，右边就是右子树，然后可以对左子树
     * 和右子树重复上面的步骤，难点在于，根据中序遍历中左子树的节点列表，去前序遍历的
     * 数组中找到对应的匹配，这里考虑的是根据左子树和右子树中节点的个数，进行截断
     *
     * @param pre
     * @param in
     * @return
     */
    public TreeNode reConstructBinaryTree(int [] pre,int [] in) {
        if (pre == null || pre.length == 0 || in == null || in.length == 0
                || pre.length != in.length)
            return new TreeNode();
        return buildTree(pre,0,pre.length-1,in,0,in.length-1);
    }

    private TreeNode buildTree(int[] pre, int start1, int end1,
                               int[] in, int start2, int end2){
        if (start1 > end1 || start2 > end2)
            return null;
        // 在先序遍历中寻找到根节点的位置
        int rootVal = pre[start1];
        // 在中序遍历中寻找到根节点的位置
        int offest = start2;
        for (int i = start2; i <= end2; i++){
            if (in[i] == rootVal){
                offest = i;
                break;
            }
        }

        // 建立根节点, 左子树的长度为 offest - start2
        TreeNode root = new TreeNode(rootVal);
        root.left = buildTree(pre,start1+1, start1+(offest-start2),
                        in, start2,offest-1);
        root.right = buildTree(pre,start1+(offest-start2)+1,end1,
                        in, offest+1,end2);
        return root;
    }

    /**
     * 输入一个链表，按链表从尾到头的顺序返回一个ArrayList。
     *
     * 思路1：每次插入时指定插入的位置为0  缺点：每次插入的时候都需要搬移数组
     *
     * 思路2：使用一个中间数组来暂存链表的数据，然后倒序输出
     * @param listNode
     * @return
     */
    public ArrayList<Integer> printListFromTailToHead(ListNode listNode) {
//        if (listNode == null)
//            return new ArrayList<>();
//        ArrayList<Integer> array = new ArrayList<>();
//        while (listNode != null){
//            array.add(0,listNode.val);
//            listNode = listNode.next;
//        }
//        return array;
        if (listNode == null)
            return new ArrayList<>();
        ArrayList<Integer> temp = new ArrayList<>();
        while (listNode != null){
            temp.add(listNode.val);
            listNode = listNode.next;
        }
        ArrayList<Integer> ans = new ArrayList<>(temp.size());
        for (int i = temp.size()-1; i >= 0; i--) {
            ans.add(temp.get(i));
        }

        return ans;
    }

    /**
     * 请实现一个函数，将一个字符串中的每个空格替换成“%20”。例如，当字符串为We Are Happy
     * 则经过替换之后的字符串为We%20Are%20Happy
     *
     * 思路1：可以使用String中的库函数来对字符串进行替换，replace
     *
     * 思路2：使用一个中间字符数组对结果进行保存，当遇到' '时，就添加三个字符'%'，'2'，'0'
     * 最后把中间数组转换成字符串进行返回
     *
     * @param s
     * @return
     */
    public String replaceSpace (String s) {
        if (s == null || s.length() == 0)
            return "";
        // 初始化数组的最大容量
        char[] ans = new char[s.length() * 3];
        int offest = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == ' '){
                ans[offest++] = '%';
                ans[offest++] = '2';
                ans[offest++] = '0';
            }else
                ans[offest++] = s.charAt(i);
        }

        return new String(ans,0,offest);
    }

    /**
     * 在一个二维数组中（每个一维数组的长度相同），每一行都按照从左到右递增的顺序排序，
     * 每一列都按照从上到下递增的顺序排序。请完成一个函数，输入这样的一个二维数组和一个
     * 整数，判断数组中是否含有该整数。
     *
     * 思路1：暴力求解 遍历二维数组中的所有元素，然后比较这个数值是否和给定的target相等
     *
     * 思路2：利用提前剪枝，比如其中一行的第一个数组元素都大于target的话，后面的行就不需要
     * 遍历，如果其中一行的最后一个元素比target小，那么这一行也是不用遍历的，对于其中可能
     * 包含target的数组，因为是顺序排列的，所以可以考虑使用二分法进行数据查找
     * @param target
     * @param array
     * @return
     */
    public boolean Find(int target, int [][] array) {
//        if (array == null || array.length == 0 || array[0].length == 0)
//            return false;
//
//        for (int i = 0; i < array.length; i++) {
//            for (int j = 0; j < array[0].length; j++) {
//                if (array[i][j] == target)
//                    return true;
//            }
//        }
//        return false;
        if (array == null || array.length == 0 || array[0].length == 0)
            return false;
        // 初始化左右边界
        int left = 0, right = array[0].length-1;
        // 对array中的每一行进行判断，其实在对行进行过滤的时候，因为有序，也可以使用二分查找
        for (int i = 0; i < array.length; i++) {
            if (array[i][left] > target)
                break;
            else if (array[i][right] < target)
                continue;
            else if (binarySearch(array[i],left,right,target))
                return true;
        }
        return false;
    }

    private boolean binarySearch(int[] nums, int left, int right, int target){
        if (left > right)
            return false;
        int mid = (left + right) /2;
        // 对于三种情况进行递归判断，这采用了尾递归，可以避免栈溢出
        if (nums[mid] == target)
            return true;
        else if (nums[mid] > target)
            return binarySearch(nums,left,mid-1,target);
        else
            return binarySearch(nums,mid+1,right,target);
    }

    /**
     * 请实现一个函数用来匹配包含'. '和'*'的正则表达式。模式中的字符'.'表示任意一个字符，
     * 而'*'表示它前面的字符可以出现任意次（含0次）。在本题中，匹配是指字符串的所有字符
     * 匹配整个模式。例如，字符串"aaa"与模式"a.a"和"ab*ac*a"匹配，但与"aa.a"和
     * "ab*a"均不匹配。
     *
     * 记录s的长度为n， p的长度为m
     *
     * 思路：对与s 与 p主要考虑以下这么几种情况
     * 1、对于p中最后一个字符为c，则将其与s中的最后一个字符匹配，如果这两个字符相等，则s与p
     * 是否匹配，则由s[0,n-2] 和 p[0-,m-2] 是否匹配来确定，如果c与s中的最后一个字符串不匹配，
     * 那么就直接返回不匹配
     *
     * 2、对于p中最后一个字符为'.'的情况，则s与p是否匹配。取决于 s[0-n-2],p[0,n-2]是否匹配
     *
     * 3、对于p中的最后一个字符为*的情况，取决于如下两种情况：
     *      1>、*之前的元素是否是普通字符，如果是普通字符的话，若这个普通字符与s中的最后一个
     *      字符不匹配，则取决s[0,n-1] 和 p[0,m-3]是否匹配，若这个普通字符与s中的最后一个
     *      字符匹配，则取决于s[0,n-2] 与p[0,m-1]是否匹配
     *      2>、*之前的元素是.，.可以匹配任意元素，则s与p是否匹配，取决于s[0,n-2] 与
     *      p[0,m-1]是否匹配
     *
     *  综上所述：
     *      对于第一种和第二种情况 f[i][j] = f[i-1][j-1]
     *
     *      对于第三种情况，分为看与不看"c*"的情况(主要原因是*可以是0个)：
     *             看：f[i][j] = f[i-1][j]
     *           不看：f[i][j] = f[i][j-2]
     *
     *  特殊判断：
     *  需要考虑空串空正则
     *  空串和空正则是匹配的，f[0][0] = true
     *  空串和非空正则，不能直接定义 true 和 false，必须要计算出来。（比如A='' ,B=a*b*c*）
     *  非空串和空正则必不匹配，f[1][0]=...=f[n][0]=false
     *  非空串和非空正则，那肯定是需要计算的了。
     *
     * *
     * @param s
     * @param p
     * @return
     */
    public boolean isMatch(String s, String p) {
        if (s == null || p == null) return false;
        if (s.length() > 0 && p.length() <= 0) return false;
        if (s.length() == 0 && p.length() == 0) return true;
        // 如果字符串长度为0，需要检测下正则串
        if (s.length() == 0) {
            // 如果正则串长度为奇数，必定不匹配，比如 "."、"ab*",必须是 a*b*这种形式，*在奇数位上
            if (p.length() % 2 != 0) return false;
            int i = 1;
            while (i < p.length()) {
                if (p.charAt(i) != '*') return false;
                i += 2;
            }
            return true;
        }

        char c1 = s.charAt(s.length()-1), c2 = p.charAt(p.length()-1);
        if (c2 != '*'){
            if (c2 == c1 || c2 == '.'){
                return isMatch(s.substring(0,s.length()-1),
                        p.substring(0,p.length()-1));
            }else {
                return false;
            }
        }else {
            if (p.length() > 1){
                if (c1 == p.charAt(p.length()-2) || p.charAt(p.length()-2) == '.'){
                    return isMatch(s.substring(0,s.length()-1),p)
                            || isMatch(s, p.substring(0,p.length()-2));
                }else {
                    return isMatch(s, p.substring(0,p.length()-2));
                }
            }else {
                return false;
            }
        }
    }
    private int getHigh(TreeNode root){
        if (root == null) return 0;
        int left = getHigh(root.left);
        int right = getHigh(root.right);
        return Math.abs(left-right) <= 1 ? Math.max(left,right)+1 : -1;
    }

    private int maxLevel;
    private int k;

    // 返回输入的树节点到叶子结点的最长路径长度，即深度
    private void dfs(TreeNode root){
        if (root == null){
            maxLevel = Math.max(maxLevel,k);
        }
        k++;
        dfs(root.left);
        dfs(root.right);
        k--;
    }
    // 对树节点进行广度遍历，按层累加，直到最大的深度
    private int bfs(TreeNode root){
        // 初始化一个队列用来保存每一层的树节点
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        int maxLevel = 0;

        while (!queue.isEmpty()){
            maxLevel++;
            // 对同一层的节点进行遍历，并将下一层的节点加入进去
            for (int i = queue.size(); i > 0; i--){
                TreeNode t = queue.poll();
                if (t.left != null) queue.offer(t.left);
                if (t.right != null) queue.offer(t.right);
            }
        }
        return maxLevel;
    }

    // 这个方法用来通过二分法来进行查找，如果当前mid不等于nums[mid]，则说明在mid左边发生了
    // 缺少，如果返回-1， 则说明没发现缺失，缺失的是第nums.length+1个数
    private int binarySearch(int[] nums, int left, int right){
        // 结束递归条件
        if (left > right) return -1;
        if (left == right) return nums[left] == left ? -1 : left;

        int mid = (left + right) / 2;
        if (nums[mid] == mid){
            return binarySearch(nums, mid+1, right);
        }else {
            return binarySearch(nums, left, mid);
        }
    }

    public int mergeSort(int[] nums, int left, int right){
        if (left == right) return 0;
        int mid = (left + right) / 2;
        int l = mergeSort(nums, left, mid);
        int r = mergeSort(nums,mid+1, right);

        int count = merge(nums,left,mid,right) + l + r;

        return count;
    }

    public int merge(int[] nums, int left, int mid, int right){
        int count = 0;
        int[] temp = new int[right-left+1];
        int i = left, j = mid+1, k = 0;

        while (i <= mid && j <= right){
            if (nums[i] <= nums[j]){
                temp[k++] = nums[i++];
            }else {
                count += (mid+1 - i);
                temp[k++] = nums[j++];
            }
        }
        while (i <= mid)
            temp[k++] = nums[i++];
        while (j <= right)
            temp[k++] = nums[j++];

        for (int n = left; n <= right; n++){
            nums[n] = temp[n-left];
        }
        return count;
    }


    private int[] quickSearch(int[] arr, int left, int right, int k){
        int index = getIndex(arr,left,right);

        if (k == index+1){
            return Arrays.copyOfRange(arr,0,index+1);
        }else if (k < index+1){
            return quickSearch(arr,left,index-1,k);
        }else {
            return quickSearch(arr,index+1,right,k);
        }
    }

    private void swap(int[] nums, int i, int j){
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    private int getIndex(int[] nums, int left, int right){
        int n = nums[left];
        int i = left, j = right;

        while (true){
            // 从左向右找到第一个比n大的数字
            while (i <= right && nums[i] <= n)
                i++;
            // 从右向左找到第一个比n小的数字
            while (j >= left && nums[j] > n){
                j--;
            }
            if (i > j)
                break;
            // 交换i与j
            swap(nums,i,j);
        }

        swap(nums,left,j);
        return j;
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
        public Node left;
        public Node right;

        public Node() {}

        public Node(int _val) {
            val = _val;
        }

        public Node(int _val,Node _left,Node _right) {
            val = _val;
            left = _left;
            right = _right;
        }
    }

    class RandomListNode {
        int label;
        RandomListNode next = null;
        RandomListNode random = null;

        RandomListNode(int label) {
            this.label = label;
        }
    }

    public static void main(String[] args) {
//        String a = new String("1") + new String("1");
//        a.intern();
//        String b = "11";
//        // java6 之前常量池在方法区，调用intern的结果是拷贝一份字符串内容到方法区，所以会不等于
//        // java7 及之后常量池转移到了堆区，随意再调用intern方法时，如果常量池中没有这个
//        //   字符串，则将这个引用放在常量区，而不是拷贝过去新建，所以这个是等于
//        System.out.println(a == b);
        Heap heap = new Heap(new int[] {1,4,6,7,2,4,9,10,5});
        System.out.println(heap.get(0));

    }
}

/**
 * 思路：保存数据用正常的队列，再用一个双端队列保存最大的数字列表
 */
class MaxQueue {
    // 初始化两个队列
    private Queue<Integer> queue = new LinkedList<>();
    private Deque<Integer> deque = new LinkedList<>();

    public MaxQueue() {
    }

    public int max_value() {
        if (queue.isEmpty()) return -1;
        return deque.peekFirst();
    }

    public void push_back(int value) {
        queue.offer(value);
        while (!deque.isEmpty() && deque.peekLast() < value){
            deque.pollLast();
        }
        deque.offerLast(value);
    }

    public int pop_front() {
        if (queue.isEmpty()) return -1;
        int val = queue.poll();
        if (val == deque.peekFirst())
            deque.pollFirst();
        return val;
    }
}

class MinStack{
    /**
     * 定义栈的数据结构，请在该类型中实现一个能够得到栈中所含最小元素的min函数
     * （时间复杂度应为O（1））。
     *
     * 思路：这个问题的难点在于，在寻找这个数组中的最小的时候的时间复杂度是O(1)
     * 可以参考动态规划的思想，前n个数的最小值，取决去前n-1个数的最小值和第n个
     * 数字的比较结果
     *  所以这里我们考虑，每在插入一个数字的同时，都插入这前n个数字的最小值，所以
     *  栈中的元素就按照类似下面的这种结构来进行排列
     *  ------------------------------------
     *  |(n1,min1)|(n2,min2)|...|(nn,minn) |
     *  ------------------------------------
     *  所以有两种实现的方式，一种是链表，将链表节点声明成这种形式的节点，另一种实现方式是
     *  使用数组，每次存元素都使用两个位置来进行存储，一个存值的大小，一个存最小值
     */
    ArrayList<Integer> stack = new ArrayList<>();
    int offest;

    public void push(int node) {
        if (offest == 0){
            stack.add(node);
            stack.add(node);
        }else {
            stack.add(node);
            stack.add(Math.min(node,stack.get(offest-1)));
        }
        offest += 2;
    }

    public void pop() {
        if (offest >= 2){
            stack.remove(--offest);
            stack.remove(--offest);
        }
    }

    public int top() {
        if (offest < 2)
            return -1;
        return stack.get(offest-2);
    }

    public int min() {
        if (offest < 2)
            return -1;
        return stack.get(offest-1);
    }
}


// 这是一个大顶堆
class Heap{
    private int[] table;
    private int size;

    // 初始化一个堆，并设置堆的容量，暂时不支持扩容
    public Heap(int capacity) {
        if (capacity > 0)
            table = new int[capacity];
        else
            System.out.println("capacity 应该大于0");
    }

    /**
     * 传入一个数组，并对这个数组进行堆化，这个堆化的方式有两种：
     *  1)、从下到上的堆化，比如从1开始，一直到数组的结尾位置，
     *  遍历的进行从下到上的堆化  时间复杂度是O(nlogn)
     *  2)、从上到下的堆化，只需要从非叶子节点开始处理堆化，直到遍历
     *  到最上面的根节点的位置    时间复杂度是O(n)
     */
    public Heap(int[] table){
        this.table = table;
        this.size = this.table.length;
        // 对于一个堆中的非叶子节点进行堆化
        for (int i = (size-1)/2; i >= 0; i--){
            heapifyUpToDown(i);
        }
        System.out.println("对这个数组的堆化工作完成");
    }

    public int insert(int n){
        if (size == table.length)
            System.out.println("堆容量已满");
        else{
            table[size++] = n;
            heapifyDownToUp();
        }
        return n;
    }

    public int get(int index){
        if (index < size && index >= 0){
            return table[index];
        }
        return -1;
    }

    public int remove(){
        if (size > 0){
            int removeNum = table[0];
            table[0] = table[--size];
            heapifyUpToDown(0);
            return removeNum;
        }
        return -1;
    }

    private void swap(int i, int j){
        int temp = table[i];
        table[i] = table[j];
        table[j] = temp;
    }

    // 删除堆顶元素之后，执行从上到下的堆化，时间复杂度O(logn)
    private void heapifyUpToDown(int index){
        if (size > 1){
            // 从根节点开始判断
            int maxIndex = index;
            while (maxIndex < size){
                if (2*index+1 < size && table[2*index+1] > table[maxIndex]){
                    maxIndex = 2*index+1;
                }
                if (2*index+2 < size && table[2*index+2] > table[maxIndex]){
                    maxIndex = 2*index+2;
                }
                if (index == maxIndex) break;
                swap(index,maxIndex);
                index = maxIndex;
            }
        }
    }

    // 在新添入数据时，从下向上的堆化，时间复杂度O(logn)
    private void heapifyDownToUp(){
        if (size > 1){
            // index 为当前插入的节点，rootIndex为当前插入节点的父节点
            int index = size-1;
            int rootIndex = (index-1)/2;
            while (rootIndex >= 0){
                if (table[index] > table[rootIndex]){
                    swap(index,rootIndex);
                    index = rootIndex;
                    rootIndex = (index-1)/2;
                }else {
                    break;
                }
            }
        }
    }

    public static void main(String[] args) {
        StringBuilder sb = new StringBuilder();
        sb.append("abc");
        sb.append("def");

        String s = sb.toString();

        System.out.println(s == s.intern());

        StringBuilder sbb = new StringBuilder();
        sbb.append("a");
        sbb.append("bc");

        String ss = sbb.toString();
        System.out.println(ss == ss.intern());


//        String ss = new String("abc"+"def");
//        System.out.println(ss == ss.intern());

    }
}
