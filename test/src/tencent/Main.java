package tencent;


import org.junit.Test;

import java.util.ArrayList;

class ListNode {
    int val;
    ListNode next = null;

    public ListNode(int val){
        this.val = val;
    }
}
public class Main {

    @Test
    public void test(){
        ListNode node = new ListNode(2);
        node.next = new ListNode(2);
        node.next.next = new ListNode(5);
        node.next.next.next = new ListNode(2);
        node.next.next.next.next = new ListNode(2);
        ListNode res = solve(node);
        while (res!= null){
            System.out.println(res.val);
            res = res.next;
        }
    }

    public ListNode solve (ListNode S) {
        if (S == null)
            return null;

        // 使用一个数据结构来暂存S中最小的val节点
        ArrayList<ListNode> arrayList = new ArrayList<>();
        int minVal = Integer.MAX_VALUE;

        int listLength = 0;
        ListNode p = S;
        while (p != null){
            if (minVal < p.val){
                p = p.next;
                listLength++;
                continue;
            }else if (minVal == p.val){
                arrayList.add(p);
            }else {
                arrayList.clear();
                minVal = p.val;
                arrayList.add(p);
            }
            p = p.next;
            listLength++;
        }
        // 都为最小
        if (arrayList.size() == listLength)
            return S;
        // 只有一个最小
        if (arrayList.size() == 1){
            ListNode n = arrayList.get(0);
            if (S == n)
                return S;
            else {
                p = S;
                while (p != null){
                    if (p.next == n){
                        p.next = null;
                    }
                    p = p.next;
                }

                p = n;
                while (p.next != null){
                    p = p.next;
                }
                p.next = S;
                return n;
            }
        }

        // 有多个最小
        // 复制一个arrayList
        ArrayList<ListNode> array = new ArrayList<>(arrayList);
        while (true){
            for (int i = 0; i < array.size(); i++){
                ListNode node = array.get(i).next != null ? array.get(i).next : S;
                array.set(i,node);
            }

            int min = Integer.MAX_VALUE;
            ArrayList<Integer> offest = new ArrayList();
            ArrayList<Integer> tmp = new ArrayList<>();
            for (int i = 0; i< array.size(); i++ ){
                if (array.get(i).val < min){
                    min = array.get(i).val;
                    if (offest.size() != 0){
                        tmp.addAll(offest);
                    }
                    offest.clear();
                    offest.add(i);
                }else if (array.get(i).val > min){
                    tmp.add(i);
                }else {
                    offest.add(i);
                }
            }

            for (int i = tmp.size(); i > 0; i--){
                array.remove((int)tmp.get(i-1));
                arrayList.remove((int)tmp.get(i-1));
            }

            if (array.size() == 1){
                break;
            }
        }

        ListNode n = arrayList.get(0);
        if (S == n)
            return S;
        else {
            p = S;
            while (p != null){
                if (p.next == n){
                    p.next = null;
                }
                p = p.next;
            }

            p = n;
            while (p.next != null){
                p = p.next;
            }
            p.next = S;
            return n;
        }
    }
}
