import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;

public class BST<K extends Comparable<K>, V> implements Iterable<K> {
    private Node root;
    private int size;

    private class Node {
        private K key;
        private V value;
        private Node left, right;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    public int size() {
        return size;
    }
    public void put(K key, V value) {
        root = put(root, key, value);
    }

    private Node put(Node x, K key, V value) {
        if (x == null) {
            size++;
            return new Node(key, value);
        }
        Node current = x;
        while (current != null) {
            int cmp = key.compareTo(current.key);
            if (cmp < 0) {
                if (current.left == null) {
                    current.left = new Node(key, value);
                    size++;
                    return x;
                }
                current = current.left;
            } else if (cmp > 0) {
                if (current.right == null) {
                    current.right = new Node(key, value);
                    size++;
                    return x;
                }
                current = current.right;
            } else {
                current.value = value;
                return x;
            }
        }
        return x;
    }

    public V get(K key) {
        Node current = root;
        while (current != null) {
            int cmp = key.compareTo(current.key);
            if (cmp < 0) {
                current = current.left;
            } else if (cmp > 0) {
                current = current.right;
            } else {
                return current.value;
            }
        }
        return null;
    }

    public void delete(K key) {
        root = delete(root, key);
    }

    private Node delete(Node x, K key) {
        if (x == null) return null;
        Node current = x;
        Node parent = null;
        while (current != null) {
            int cmp = key.compareTo(current.key);
            if (cmp < 0) {
                parent = current;
                current = current.left;
            } else if (cmp > 0) {
                parent = current;
                current = current.right;
            } else {
                if (current.right == null) return current.left;
                if (current.left == null) return current.right;
                Node temp = current;
                current = min(temp.right);
                current.right = deleteMin(temp.right);
                current.left = temp.left;
                if (parent.left == temp) {
                    parent.left = current;
                } else {
                    parent.right = current;
                }
                return x;
            }
        }
        return x;
    }

    private Node min(Node x) {
        Node current = x;
        while (current.left != null) {
            current = current.left;
        }
        return current;
    }

    private Node deleteMin(Node x) {
        if (x == null) return null;
        Node current = x;
        Node parent = null;
        while (current.left != null) {
            parent = current;
            current = current.left;
        }
        if (parent != null) {
            parent.left = current.right;
        }
        return x;
    }

    @Override
    public Iterator<K> iterator() {
        return new InOrderIterator();
    }

    private class InOrderIterator implements Iterator<K> {
        private Stack<Node> stack;
        private Node current;

        public InOrderIterator() {
            stack = new Stack<>();
            current = root;
            pushLeft();
        }

        private void pushLeft() {
            while (current != null) {
                stack.push(current);
                current = current.left;
            }
        }

        @Override
        public boolean hasNext() {
            return !stack.isEmpty();
        }

        @Override
        public K next() {
            if (!hasNext()) throw new NoSuchElementException();
            Node top = stack.pop();
            current = top.right;
            pushLeft();
            return top.key;
        }
    }


    public static void main(String[] args) {
        BST<Integer, String> tree = new BST<>();
        tree.put(5, "Five");
        tree.put(10, "Ten");
        tree.put(2, "Two");
        tree.put(100, "Hundred");
        tree.put(20, "Twenty");

        System.out.println("Size: " + tree.size());

        // Example of iteration
        for (Integer key : tree) {
            System.out.println("Key: " + key + ", Value: " + tree.get(key));
        }
    }
}
