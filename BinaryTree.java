package com.zfl9.collection;

import java.util.Queue;
import java.util.Deque;
import java.util.ArrayDeque;

public class BinaryTree {
    static class Node {
        int data;
        Node left;
        Node right;

        Node() {}
        Node(int data) {
            this.data = data;
        }
        Node(int data, Node left, Node right) {
            this.data = data;
            this.left = left;
            this.right = right;
        }

        @Override
        public String toString() {
            return String.valueOf(data);
        }
    }

    final Node ROOT;

    public BinaryTree() {
        // 第一层
        ROOT = new Node(1);
        // 第二层
        ROOT.left = new Node(2);
        ROOT.right = new Node(3);
        // 第三层
        ROOT.left.left = new Node(4);
        ROOT.left.right = new Node(5);
        ROOT.right.left = new Node(6);
        ROOT.right.right = new Node(7);
    }

    public void preOrderRecursion() {
        preOrderRecursion(ROOT);
        System.out.println("\b\b ");
    }
    private static void preOrderRecursion(Node node) {
        if (node != null) {
            System.out.print(node + ", ");
            preOrderRecursion(node.left);
            preOrderRecursion(node.right);
        }
    }

    public void inOrderRecursion() {
        inOrderRecursion(ROOT);
        System.out.println("\b\b ");
    }
    private static void inOrderRecursion(Node node) {
        if (node != null) {
            inOrderRecursion(node.left);
            System.out.print(node + ", ");
            inOrderRecursion(node.right);
        }
    }

    public void postOrderRecursion() {
        postOrderRecursion(ROOT);
        System.out.println("\b\b ");
    }
    private static void postOrderRecursion(Node node) {
        if (node != null) {
            postOrderRecursion(node.left);
            postOrderRecursion(node.right);
            System.out.print(node + ", ");
        }
    }

    public void preOrderTraversal() {
        Deque<Node> stack = new ArrayDeque<>(3);
        Node node = ROOT;
        while (node != null || !stack.isEmpty()) {
            while (node != null) {
                System.out.print(node + ", ");
                stack.push(node);
                node = node.left;
            }
            if (!stack.isEmpty()) {
                node = stack.pop().right;
            }
        }
        System.out.println("\b\b ");
    }

    public void inOrderTraversal() {
        Deque<Node> stack = new ArrayDeque<>(3);
        Node node = ROOT;
        while (node != null || !stack.isEmpty()) {
            while (node != null) {
                stack.push(node);
                node = node.left;
            }
            if (!stack.isEmpty()) {
                node = stack.pop();
                System.out.print(node + ", ");
                node = node.right;
            }
        }
        System.out.println("\b\b ");
    }

    public void postOrderTraversal() {
        Deque<Node> stack = new ArrayDeque<>(3);
        Node node = ROOT;
        Node visited = null;
        while (node != null || !stack.isEmpty()) {
            while (node != null) {
                stack.push(node);
                node = node.left;
            }
            node = stack.peek();
            if (node.right == null || node.right == visited) {
                System.out.print(node + ", ");
                visited = node;
                stack.pop();
                node = null;
            } else {
                node = node.right;
            }
        }
        System.out.println("\b\b ");
    }

    public void levelOrder() {
        if (ROOT == null) {
            return;
        }
        Queue<Node> queue = new ArrayDeque<>(4);
        Node node = ROOT;
        queue.add(node);
        while (!queue.isEmpty()) {
            node = queue.remove();
            System.out.print(node + ", ");
            if (node.left != null) {
                queue.add(node.left);
            }
            if (node.right != null) {
                queue.add(node.right);
            }
        }
        System.out.println("\b\b ");
    }

    public static void main(String[] args) {
        BinaryTree tree = new BinaryTree();

        tree.preOrderRecursion();
        tree.preOrderTraversal();

        tree.inOrderRecursion();
        tree.inOrderTraversal();

        tree.postOrderRecursion();
        tree.postOrderTraversal();

        tree.levelOrder();
    }
}
