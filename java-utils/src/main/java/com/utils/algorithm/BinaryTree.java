package com.utils.algorithm;

import java.util.*;

public class BinaryTree {
    public static void main(String[] args) {
        Node nodeA = new Node("A");
        Node nodeB = new Node("B");
        Node nodeC = new Node("C");
        Node nodeD = new Node("D");
        Node nodeE = new Node("E");
        Node nodeF = new Node("F");
        Node nodeG = new Node("G");

        nodeA.left = nodeB;
        nodeA.right = nodeC;
        nodeB.left = nodeD;
        nodeB.right = nodeE;
        nodeC.right = nodeF;
        nodeE.left = nodeG;

        /**
         *              A                       先序：ABDEGCF
         *            /  \                      中序：DBGEACF
         *          B     C                     后序：DGEBFCA
         *        /  \      \                   层次：ABDEGCF
         *      D     E      F
         *           /
         *         G
         */

        preOrder1(nodeA);
        System.out.println();
        midOrder1(nodeA);
        System.out.println();
        postOrder1(nodeA);
        System.out.println();

        preOrder2(nodeA);
        System.out.println();
        midOrder2(nodeA);
        System.out.println();
        postOrder2(nodeA);
        System.out.println();
        System.err.println(bfsOrder(nodeA));


    }

    // 1：前序遍历（先root节点，然后左子树右子树）
    static void preOrder1(Node node) {
        if (node == null) {
            return;
        }
        System.out.print(node.value);
        preOrder1(node.left);
        preOrder1(node.right);
    }

    // 非递归方式
    static void preOrder2(Node node) {
        if (node == null) {
            return;
        }
        // 栈先进后出，所以先放进去root，然后right，然后left
        Stack<Node> stack = new Stack<>();
        stack.push(node);
        while (!stack.isEmpty()) {
            Node pop = stack.pop();
            System.err.print(pop.value);
            if (pop.right != null) {
                stack.push(pop.right);
            }
            if (pop.left != null) {
                stack.push(pop.left);
            }
        }
    }


    // 2: 中序遍历（先左子树，然后root节点右子树）
    static void midOrder1(Node node) {
        if (node == null) {
            return;
        }
        midOrder1(node.left);
        System.out.print(node.value);
        midOrder1(node.right);
    }

    // 非递归遍历
    static void midOrder2(Node node) {
        if (node == null) {
            return;
        }
        Stack<Node> stack = new Stack<>();
        Node temp = node;
        while (!stack.isEmpty() || temp != null) {
            while (temp != null) {
                stack.push(temp);
                temp = temp.left;
            }
            Node pop = stack.pop();
            System.err.print(pop.value);
            if (pop.right != null) {
                temp = pop.right;
            }
        }
    }

    // 3: 后序遍历（先左子树，然后右子树root节点）
    static void postOrder1(Node node) {
        if (node == null) {
            return;
        }
        postOrder1(node.left);
        postOrder1(node.right);
        System.out.print(node.value);
    }

    // 非递归方式遍历
    static void postOrder2(Node node) {
        if (node == null) {
            return;
        }
        // 栈先进后出，所以先放进去root，然后right，然后left
        Stack<Node> stack = new Stack<>();
        Stack<Node> temp = new Stack<>();
        stack.push(node);
        while (!stack.isEmpty()) {
            Node pop = stack.pop();
            temp.push(pop);
            if (pop.left != null) {
                stack.push(pop.left);
            }
            if (pop.right != null) {
                stack.push(pop.right);
            }
        }
        while (!temp.isEmpty()) {
            System.err.print(temp.pop().value);
        }
    }

    // 4：层次遍历（层层访问，符合广度优先算法）
    static List<List<String>> bfsOrder(Node node) {
        // 使用队列先进先出
        List<List<String>> res = new ArrayList<>();
        Queue<Node> queue = new LinkedList<>();
        queue.add(node);
        List<String> temp;
        while (!queue.isEmpty()) {
            int size = queue.size();
            temp = new ArrayList<>();
            while (size-- > 0) {
                Node poll = queue.poll();
                System.out.print(poll.value);
                temp.add(poll.value);
                if (poll.left != null) {
                    queue.add(poll.left);
                }
                if (poll.right != null) {
                    queue.add(poll.right);
                }
            }
            res.add(temp);
        }
        System.out.println();
        return res;
    }

    static class Node {
        String value;
        Node left;
        Node right;

        public Node(String value) {
            this.value = value;
        }
    }
}

