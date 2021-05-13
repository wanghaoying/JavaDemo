package nowcoder;

import org.junit.Test;

/**
 * 刷题的第四个文件，NC12 ~ NC
 */
public class Solution03 {
    public static void main(String[] args) {

    }

    @Test
    public void test(){
        System.out.println(search(new int[] {1}, 0));
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

}
