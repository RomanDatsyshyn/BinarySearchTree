import java.util.Scanner;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        AVLTree<Integer> tree = new AVLTree<>();

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter keys: ");
        String[] inputStrings = scanner.nextLine().split("\\s");
        Arrays.sort(inputStrings);
        int[] inputNumbers = new int[inputStrings.length];

        for (int i = 0; i < inputStrings.length; i++) {
            inputNumbers[i] = Integer.parseInt(inputStrings[i]);
            tree.root = tree.insert(inputNumbers[i]);
        }

        System.out.print("Enter depth : ");
        tree.d = scanner.nextInt();

        System.out.println("\nTree: ");
        tree.PrintTree();
        System.out.print("\nElements with depth " + tree.d + ": ");
        tree.PrintDepths();
        System.out.print("\nNumber of elements with depth " + tree.d + ": " + tree.a);
    }
}



public class Node<T extends Comparable<T>> implements Comparable<Node<T>> {

    public T data;
    private Node<T> left;
    private Node<T> right;
    public int level;
    private int depth;

    public Node(T data) {
        this(data, null, null);
    }

    public Node(T data, Node<T> left, Node<T> right) {
        super();
        this.data = data;
        this.left = left;
        this.right = right;
        if (left == null && right == null)
            setDepth(1);
        else if (left == null)
            setDepth(right.getDepth() + 1);
        else if (right == null)
            setDepth(left.getDepth() + 1);
        else
            setDepth(Math.max(left.getDepth(), right.getDepth()) + 1);
    }

    public T getData() {
        return data;
    }

    public Node<T> getLeft() {
        return left;
    }

    public Node<T> getRight() {
        return right;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    @Override
    public int compareTo(Node<T> o) {
        return this.data.compareTo(o.data);
    }

    @Override
    public String toString() {
        return "Depth " + level + ": " + data;
    }

}

import java.util.LinkedList;
import java.util.Queue;

public class AVLTree<T extends Comparable<T>> {
    Node<T> root;

    public AVLTree() {
        root = null;
    }

    private int depth(Node<T> node) {
        if (node == null)
            return 0;
        return node.getDepth();
    }

    public Node<T> insert(T data) {
        root = insert(root, data);
        switch (balanceNumber(root)) {
            case 1:
                root = rotateLeft(root);
                break;
            case -1:
                root = rotateRight(root);
                break;
            default:
                break;
        }
        return root;
    }

    public Node<T> insert(Node<T> node, T data) {
        if (node == null)
            return new Node<T>(data);
        if (node.getData().compareTo(data) > 0) {
            node = new Node<T>(node.getData(), insert(node.getLeft(), data),
                    node.getRight());
        } else if (node.getData().compareTo(data) < 0) {
            node = new Node<T>(node.getData(), node.getLeft(), insert(
                    node.getRight(), data));
        }
        switch (balanceNumber(node)) {
            case 1:
                node = rotateLeft(node);
                break;
            case -1:
                node = rotateRight(node);
                break;
            default:
                return node;
        }
        return node;
    }

    private int balanceNumber(Node<T> node) {
        int L = depth(node.getLeft());
        int R = depth(node.getRight());
        if (L - R >= 2)
            return -1;
        else if (L - R <= -2)
            return 1;
        return 0;
    }

    private Node<T> rotateLeft(Node<T> node) {
        Node<T> q = node;
        Node<T> p = q.getRight();
        Node<T> c = q.getLeft();
        Node<T> a = p.getLeft();
        Node<T> b = p.getRight();
        q = new Node<T>(q.getData(), c, a);
        p = new Node<T>(p.getData(), q, b);
        return p;
    }

    private Node<T> rotateRight(Node<T> node) {
        Node<T> q = node;
        Node<T> p = q.getLeft();
        Node<T> c = q.getRight();
        Node<T> a = p.getLeft();
        Node<T> b = p.getRight();
        q = new Node<T>(q.getData(), b, c);
        p = new Node<T>(p.getData(), a, q);
        return p;
    }

    public String toString() {
        return root.toString();
    }

    public void PrintTree() {
        root.level = 0;
        Queue<Node<T>> queue = new LinkedList<Node<T>>();
        queue.add(root);
        while (!queue.isEmpty()) {
            Node<T> node = queue.poll();
            System.out.println(node);
            int level = node.level;
            Node<T> left = node.getLeft();
            Node<T> right = node.getRight();
            if (left != null) {
                left.level = level + 1;
                queue.add(left);
            }
            if (right != null) {
                right.level = level + 1;
                queue.add(right);
            }
        }
    }

    int d, a;

    public void PrintDepths() {
        root.level = 0;
        Queue<Node<T>> queue = new LinkedList<Node<T>>();
        queue.add(root);
        while (!queue.isEmpty()) {
            Node<T> node = queue.poll();
            if (node.level == d) {
                System.out.print(node.data + " ");
                a++;
            }
            int level = node.level;
            Node<T> left = node.getLeft();
            Node<T> right = node.getRight();
            if (left != null) {
                left.level = level + 1;
                queue.add(left);
            }
            if (right != null) {
                right.level = level + 1;
                queue.add(right);
            }
        }
    }
}
