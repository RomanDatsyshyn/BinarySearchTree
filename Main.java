public class Main {
    public static void main(String[] args) {

        BinTree<String, String> tree = new BinTree<>();

        tree.put("D", "00");
        tree.put("A", "01");
        tree.put("F", "02");
        tree.put("C", "03");
        tree.put("B", "04");
        tree.put("S", "05");
        tree.put("E", "06");

        if (tree.get("S") == null) System.out.println("No elements with key \"S\"");
        else System.out.println("There is element with key \"S\"");
        System.out.println("Tree:" + "\n" + tree);

    }
}

public class BinTree<K extends Comparable, V> {
    private class Node {
        K key;
        V value;
        Node right, left;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public StringBuilder toString(StringBuilder prefix, boolean isTail, StringBuilder sb) {
            if (right != null) {
                right.toString(new StringBuilder().append(prefix).append(isTail ? "   |  " : "    "), false, sb);
            }
            sb.append(prefix).append(isTail ? "   L-- " : "   --- ").append(key.toString()).append(":").append(value.toString()).append("\n");
            if (left != null) {
                left.toString(new StringBuilder().append(prefix).append(isTail ? "   |   " : "   "), true, sb);
            }
            return sb;
        }

        @Override
        public String toString() {
            return this.toString(new StringBuilder(), true, new StringBuilder()).toString();
        }
    }

    private Node root;
    private int size;

    private Node find(K key) {
        if (root == null) {
            return null;
        }
        Node iterator = root;
        Node required = root;
        while (iterator != null) {
            required = iterator;
            if (key.compareTo(iterator.key) > 0) {
                iterator = iterator.right;
            } else if (key.compareTo(iterator.key) < 0) {
                iterator = iterator.left;
            } else {
                iterator = null;
            }
        }
        return required;
    }

    public void put(K key, V value) {
        Node node = new Node(key, value);
        if (root == null) {
            root = node;
            return;
        }
        Node parent = find(key);

        if (key.compareTo(parent.key) > 0) {
            parent.right = node;
        } else if (key.compareTo(parent.key) < 0) {
            parent.left = node;
        } else {
            parent.value = node.value;
        }
        size++;
    }

    public V get(K key) {
        Node required = find(key);
        return required.key.compareTo(key) == 0 ? required.value : null;
    }

    @Override
    public String toString() {
        return root + "";
    }
}
