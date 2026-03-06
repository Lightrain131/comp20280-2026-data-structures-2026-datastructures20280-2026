package project20280.tree;

import project20280.interfaces.Position;

import java.util.ArrayList;

/**
 * Concrete implementation of a binary tree using a node-based, linked
 * structure.
 */
public class LinkedBinaryTree<E> extends AbstractBinaryTree<E> {

    static java.util.Random rnd = new java.util.Random();
    /**
     * The root of the binary tree
     */
    protected Node<E> root = null; // root of the tree

    // LinkedBinaryTree instance variables
    /**
     * The number of nodes in the binary tree
     */
    private int size = 0; // number of nodes in the tree

    /**
     * Constructs an empty binary tree.
     */
    public LinkedBinaryTree() {
    } // constructs an empty binary tree

    // constructor

    public static LinkedBinaryTree<Integer> makeRandom(int n) {
        LinkedBinaryTree<Integer> bt = new LinkedBinaryTree<>();
        bt.root = randomTree(null, 1, n);
        return bt;
    }

    // nonpublic utility

    public static <T extends Integer> Node<T> randomTree(Node<T> parent, Integer first, Integer last) {
        if (first > last) return null;
        else {
            Integer treeSize = last - first + 1;
            Integer leftCount = rnd.nextInt(treeSize);
            Integer rightCount = treeSize - leftCount - 1;
            Node<T> root = new Node<T>((T) ((Integer) (first + leftCount)), parent, null, null);
            root.setLeft(randomTree(root, first, first + leftCount - 1));
            root.setRight(randomTree(root, first + leftCount + 1, last));
            return root;
        }
    }

    // accessor methods (not already implemented in AbstractBinaryTree)

    public static void main(String [] args) {

        LinkedBinaryTree<String> bt = new LinkedBinaryTree<>();
        String[] arr = { "A", "B", "C", "D", "E", null, "F", null, null, "G", "H", null, null, null, null };

        bt.createLevelOrder(arr);
        System.out.println(bt.toBinaryTreeString());

        Integer [] inorder= {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17,
                18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30};

        Integer [] preorder= {18, 2, 1, 14, 13, 12, 4, 3, 9, 6, 5, 8, 7, 10, 11, 15, 16,
                17, 28, 23, 19, 22, 20, 21, 24, 27, 26, 25, 29, 30};

        LinkedBinaryTree<Integer> bt2 = new LinkedBinaryTree<>();
        bt2.construct(inorder, preorder);

        System.out.println(bt2.toBinaryTreeString());

        experiment();

        System.out.println();
        bt.printLeaves(bt.root());
        System.out.println();
        System.out.println();

        inorderExperiment();
    }


    /**
     * Factory function to create a new node storing element e.
     */
    protected Node<E> createNode(E e, Node<E> parent, Node<E> left, Node<E> right) {
        return new Node<E>(e, parent, left, right);
    }

    /**
     * Verifies that a Position belongs to the appropriate class, and is not one
     * that has been previously removed. Note that our current implementation does
     * not actually verify that the position belongs to this particular list
     * instance.
     *
     * @param p a Position (that should belong to this tree)
     * @return the underlying Node instance for the position
     * @throws IllegalArgumentException if an invalid position is detected
     */
    protected Node<E> validate(Position<E> p) throws IllegalArgumentException {
        if (!(p instanceof Node)) throw new IllegalArgumentException("Not valid position type");
        Node<E> node = (Node<E>) p; // safe cast
        if (node.getParent() == node) // our convention for defunct node
            throw new IllegalArgumentException("p is no longer in the tree");
        return node;
    }

    /**
     * Returns the number of nodes in the tree.
     *
     * @return number of nodes in the tree
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Returns the root Position of the tree (or null if tree is empty).
     *
     * @return root Position of the tree (or null if tree is empty)
     */
    @Override
    public Position<E> root() {
        return root;
    }

    // update methods supported by this class

    /**
     * Returns the Position of p's parent (or null if p is root).
     *
     * @param p A valid Position within the tree
     * @return Position of p's parent (or null if p is root)
     * @throws IllegalArgumentException if p is not a valid Position for this tree.
     */
    @Override
    public Position<E> parent(Position<E> p) throws IllegalArgumentException {
        return ((Node<E>) p).getParent();
    }

    /**
     * Returns the Position of p's left child (or null if no child exists).
     *
     * @param p A valid Position within the tree
     * @return the Position of the left child (or null if no child exists)
     * @throws IllegalArgumentException if p is not a valid Position for this tree
     */
    @Override
    public Position<E> left(Position<E> p) throws IllegalArgumentException {
        return ((Node<E>) p).getLeft();
    }

    /**
     * Returns the Position of p's right child (or null if no child exists).
     *
     * @param p A valid Position within the tree
     * @return the Position of the right child (or null if no child exists)
     * @throws IllegalArgumentException if p is not a valid Position for this tree
     */
    @Override
    public Position<E> right(Position<E> p) throws IllegalArgumentException {
        return ((Node<E>) p).getRight();
    }

    /**
     * Places element e at the root of an empty tree and returns its new Position.
     *
     * @param e the new element
     * @return the Position of the new element
     * @throws IllegalStateException if the tree is not empty
     */
    public Position<E> addRoot(E e) throws IllegalStateException {
        if (!isEmpty()) throw new IllegalStateException("Tree is not empty");
        root = createNode(e, null, null, null);
        size = 1;
        return root;
    }

    public void insert(E e) {
        if (e == null) throw new IllegalArgumentException("Cannot insert null element");
        if (root == null) {
            addRoot(e);
        } else {
            addRecursive(root, e);
        }
    }

    // recursively add Nodes to binary tree in proper position
    private Node<E> addRecursive(Node<E> p, E e) {
        // BST insert: relies on Comparable
        if (!(e instanceof Comparable))
            throw new IllegalArgumentException("Element must be Comparable for BST insert");

        Comparable<? super E> key = (Comparable<? super E>) e;

        int cmp = key.compareTo(p.getElement());
        if (cmp < 0) {
            if (p.getLeft() == null) {
                Node<E> child = createNode(e, p, null, null);
                p.setLeft(child);
                size++;
                return child;
            }
            return addRecursive(p.getLeft(), e);
        } else {
            if (p.getRight() == null) {
                Node<E> child = createNode(e, p, null, null);
                p.setRight(child);
                size++;
                return child;
            }
            return addRecursive(p.getRight(), e);
        }
    }

    /**
     * Creates a new left child of Position p storing element e and returns its
     * Position.
     *
     * @param p the Position to the left of which the new element is inserted
     * @param e the new element
     * @return the Position of the new element
     * @throws IllegalArgumentException if p is not a valid Position for this tree
     * @throws IllegalArgumentException if p already has a left child
     */
    public Position<E> addLeft(Position<E> p, E e) throws IllegalArgumentException {
        Node<E> parent = validate(p);
        if (parent.getLeft() != null) throw new IllegalArgumentException("p already has a left child");
        Node<E> child = createNode(e, parent, null, null);
        parent.setLeft(child);
        size++;
        return child;
    }

    /**
     * Creates a new right child of Position p storing element e and returns its
     * Position.
     *
     * @param p the Position to the right of which the new element is inserted
     * @param e the new element
     * @return the Position of the new element
     * @throws IllegalArgumentException if p is not a valid Position for this tree.
     * @throws IllegalArgumentException if p already has a right child
     */
    public Position<E> addRight(Position<E> p, E e) throws IllegalArgumentException {
        Node<E> parent = validate(p);
        if (parent.getRight() != null) throw new IllegalArgumentException("p already has a right child");
        Node<E> child = createNode(e, parent, null, null);
        parent.setRight(child);
        size++;
        return child;
    }

    /**
     * Replaces the element at Position p with element e and returns the replaced
     * element.
     *
     * @param p the relevant Position
     * @param e the new element
     * @return the replaced element
     * @throws IllegalArgumentException if p is not a valid Position for this tree.
     */
    public E set(Position<E> p, E e) throws IllegalArgumentException {
        Node<E> node = validate(p);
        E old = node.getElement();
        node.setElement(e);
        return old;
    }

    /**
     * Attaches trees t1 and t2, respectively, as the left and right subtree of the
     * leaf Position p. As a side effect, t1 and t2 are set to empty trees.
     *
     * @param p  a leaf of the tree
     * @param t1 an independent tree whose structure becomes the left child of p
     * @param t2 an independent tree whose structure becomes the right child of p
     * @throws IllegalArgumentException if p is not a valid Position for this tree
     * @throws IllegalArgumentException if p is not a leaf
     */
    public void attach(Position<E> p, LinkedBinaryTree<E> t1, LinkedBinaryTree<E> t2) throws IllegalArgumentException {
        Node<E> node = validate(p);
        if (numChildren(node) != 0) throw new IllegalArgumentException("p must be a leaf");

        if (t1 != null && !t1.isEmpty()) {
            t1.root.setParent(node);
            node.setLeft(t1.root);
            size += t1.size;
            t1.root = null;
            t1.size = 0;
        }

        if (t2 != null && !t2.isEmpty()) {
            t2.root.setParent(node);
            node.setRight(t2.root);
            size += t2.size;
            t2.root = null;
            t2.size = 0;
        }
    }

    /**
     * Removes the node at Position p and replaces it with its child, if any.
     *
     * @param p the relevant Position
     * @return element that was removed
     * @throws IllegalArgumentException if p is not a valid Position for this tree.
     * @throws IllegalArgumentException if p has two children.
     */
    public E remove(Position<E> p) throws IllegalArgumentException {
        Node<E> node = validate(p);
        if (numChildren(node) == 2) throw new IllegalArgumentException("p has two children");

        Node<E> child = (node.getLeft() != null ? node.getLeft() : node.getRight());

        if (child != null) child.setParent(node.getParent());

        if (node == root) {
            root = child;
        } else {
            Node<E> parent = node.getParent();
            if (node == parent.getLeft()) parent.setLeft(child);
            else parent.setRight(child);
        }

        size--;

        E temp = node.getElement();
        node.setElement(null);
        node.setLeft(null);
        node.setRight(null);
        node.setParent(node);
        return temp;
    }

    public String toString() {
        return positions().toString();
    }

    public void createLevelOrder(ArrayList<E> l) {
        size = 0;
        root = createLevelOrderHelper(l, null, 0);
    }

    private Node<E> createLevelOrderHelper(java.util.ArrayList<E> l, Node<E> p, int i) {
        if (l == null || i < 0 || i >= l.size()) return null;
        E val = l.get(i);
        if (val == null) return null;

        Node<E> node = createNode(val, p, null, null);
        size++;

        node.setLeft(createLevelOrderHelper(l, node, 2 * i + 1));
        node.setRight(createLevelOrderHelper(l, node, 2 * i + 2));
        return node;
    }

    public void createLevelOrder(E[] arr) {
        size = 0;
        root = createLevelOrderHelper(arr, null, 0);
    }

    private Node<E> createLevelOrderHelper(E[] arr, Node<E> p, int i) {
        if (arr == null || i < 0 || i >= arr.length) return null;
        E val = arr[i];
        if (val == null) return null;

        Node<E> node = createNode(val, p, null, null);
        size++;

        node.setLeft(createLevelOrderHelper(arr, node, 2 * i + 1));
        node.setRight(createLevelOrderHelper(arr, node, 2 * i + 2));
        return node;
    }

    public String toBinaryTreeString() {
        BinaryTreePrinter<E> btp = new BinaryTreePrinter<>(this);
        return btp.print();
    }

    /**
     * Nested static class for a binary tree node.
     */
    public static class Node<E> implements Position<E> {
        private E element;
        private Node<E> left, right, parent;

        public Node(E e, Node<E> p, Node<E> l, Node<E> r) {
            element = e;
            left = l;
            right = r;
            parent = p;
        }

        // accessor
        public E getElement() {
            return element;
        }

        // modifiers
        public void setElement(E e) {
            element = e;
        }

        public Node<E> getLeft() {
            return left;
        }

        public void setLeft(Node<E> n) {
            left = n;
        }

        public Node<E> getRight() {
            return right;
        }

        public void setRight(Node<E> n) {
            right = n;
        }

        public Node<E> getParent() {
            return parent;
        }

        public void setParent(Node<E> n) {
            parent = n;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            if (element == null) {
                sb.append("\u29B0");
            } else {
                sb.append(element);
            }
            return sb.toString();
        }
    }

    public void construct(E[] inorder, E[] preorder) {
        size = 0;
        root = constructHelper(inorder, preorder, 0, inorder.length - 1, 0, null);
    }

    private Node<E> constructHelper(E[] inorder, E[] preorder,
                                    int inStart, int inEnd,
                                    int preIndex,
                                    Node<E> parent) {

        if (inStart > inEnd || preIndex >= preorder.length)
            return null;

        E rootVal = preorder[preIndex];
        Node<E> node = createNode(rootVal, parent, null, null);
        size++;

        int rootIndex = findIndex(inorder, rootVal, inStart, inEnd);

        int leftSize = rootIndex - inStart;

        node.setLeft(
                constructHelper(inorder, preorder,
                        inStart,
                        rootIndex - 1,
                        preIndex + 1,
                        node)
        );

        node.setRight(
                constructHelper(inorder, preorder,
                        rootIndex + 1,
                        inEnd,
                        preIndex + leftSize + 1,
                        node)
        );

        return node;
    }

    private int findIndex(E[] arr, E val, int start, int end) {
        for (int i = start; i <= end; i++) {
            if (arr[i].equals(val))
                return i;
        }
        return -1;
    }

    public ArrayList<ArrayList<E>> rootToLeafPaths() {
        ArrayList<ArrayList<E>> result = new ArrayList<>();
        ArrayList<E> path = new ArrayList<>();

        rootToLeafHelper(root, path, result);

        return result;
    }

    private void rootToLeafHelper(Node<E> node,
                                  ArrayList<E> path,
                                  ArrayList<ArrayList<E>> result) {

        if (node == null)
            return;

        path.add(node.getElement());

        if (node.getLeft() == null && node.getRight() == null) {
            result.add(new ArrayList<>(path));
        } else {
            rootToLeafHelper(node.getLeft(), path, result);
            rootToLeafHelper(node.getRight(), path, result);
        }

        path.remove(path.size() - 1);
    }

    public int height(Node<E> node) {

        if (node == null)
            return 0;

        return 1 + Math.max(
                height(node.getLeft()),
                height(node.getRight())
        );
    }

    public static void experiment() {

        for (int n = 50; n <= 5000; n += 50) {

            double totalHeight = 0;

            for (int i = 0; i < 100; i++) {

                LinkedBinaryTree<Integer> tree = LinkedBinaryTree.makeRandom(n);

                totalHeight += tree.height(tree.root);
            }

            double avg = totalHeight / 100.0;

            System.out.println(n + "," + avg);
        }
    }

    public void printLeaves(Position<E> p) {

        if (p == null)
            return;

        if (left(p) == null && right(p) == null) {
            System.out.print(p.getElement() + " ");
            return;
        }

        if (left(p) != null)
            printLeaves(left(p));

        if (right(p) != null)
            printLeaves(right(p));
    }

    public static void inorderExperiment() {

        for (int n = 10; n <= 10000; n += 500) {

            LinkedBinaryTree<Integer> tree = LinkedBinaryTree.makeRandom(n);

            long start = System.nanoTime();

            tree.inorder();

            long end = System.nanoTime();

            long time = end - start;

            System.out.println(n + "," + time);
        }
    }
}
