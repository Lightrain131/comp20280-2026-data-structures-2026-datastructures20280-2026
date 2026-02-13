package project20280.list;

import project20280.interfaces.List;
import java.util.NoSuchElementException;
import java.util.Iterator;

public class SinglyLinkedList<E> implements List<E> {

    private static class Node<E> {

        private final E element;            // reference to the element stored at this node

        /**
         * A reference to the subsequent node in the list
         */
        private Node<E> next;         // reference to the subsequent node in the list

        /**
         * Creates a node with the given element and next node.
         *
         * @param e the element to be stored
         * @param n reference to a node that should follow the new node
         */
        public Node(E e, Node<E> n) {
            element = e;
            next = n;
        }

        // Accessor methods

        /**
         * Returns the element stored at the node.
         *
         * @return the element stored at the node
         */
        public E getElement() {
            return element;
        }

        /**
         * Returns the node that follows this one (or null if no such node).
         *
         * @return the following node
         */
        public Node<E> getNext() {
            return next;
        }

        // Modifier methods

        /**
         * Sets the node's next reference to point to Node n.
         *
         * @param n the node that should follow this one
         */
        public void setNext(Node<E> n) {
            next = n;
        }
    } //----------- end of nested Node class -----------

    /**
     * The head node of the list
     */
    private Node<E> head = null;               // head node of the list (or null if empty)
    private Node<E> tail = null;


    /**
     * Number of nodes in the list
     */
    private int size = 0;                      // number of nodes in the list

    public SinglyLinkedList() {
    }              // constructs an initially empty list

    //@Override
    public int size() {
        return size;
    }

    //@Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public E get(int position) {
        if (position < 0 || position >= size) {
            throw new IndexOutOfBoundsException("Invalid position: " + position);
        }

        Node<E> current = head;

        for (int i = 0; i < position; i++) {
            current = current.getNext();
        }

        return current.getElement();
    }

    @Override
    public void add(int position, E e) {
        if (position < 0 || position > size) {
            throw new IndexOutOfBoundsException("Invalid position: " + position);
        }

        if (position == 0) {
            addFirst(e);
            return;
        }

        if (position == size) {
            addLast(e);
            return;
        }

        Node<E> prev = head;
        for (int i = 0; i < position - 1; i++) {
            prev = prev.getNext();
        }

        Node<E> newest = new Node<>(e, prev.getNext());
        prev.setNext(newest);
        size++;
    }


    @Override
    public void addFirst(E e) {
        Node<E> newest = new Node<>(e, head);
        head = newest;

        if (size == 0) {
            tail = head;
        }

        size++;
    }

    @Override
    public void addLast(E e) {
        Node<E> newest = new Node<>(e, null);

        if (isEmpty()) {
            head = newest;
        } else {
            tail.setNext(newest);
        }

        tail = newest;
        size++;
    }

    @Override
    public E remove(int position) {
        if (position < 0 || position >= size) {
            throw new IndexOutOfBoundsException("Invalid position: " + position);
        }

        if (position == 0) {
            return removeFirst();
        }

        if (position == size - 1) {
            return removeLast();
        }

        Node<E> prev = head;
        for (int i = 0; i < position - 1; i++) {
            prev = prev.getNext();
        }

        Node<E> target = prev.getNext();
        E answer = target.getElement();

        prev.setNext(target.getNext());
        size--;

        return answer;
    }

    @Override
    public E removeFirst() {
        if (isEmpty()) {
            return null;
        }

        E answer = head.getElement();
        head = head.getNext();
        size--;

        if (size == 0) {
            tail = null;
        }

        return answer;
    }

    @Override
    public E removeLast() {
        if (isEmpty()) {
            return null;
        }

        if (size == 1) {
            E answer = head.getElement();
            head = null;
            tail = null;
            size = 0;
            return answer;
        }

        Node<E> current = head;
        while (current.getNext() != tail) {
            current = current.getNext();
        }

        E answer = tail.getElement();
        tail = current;
        tail.setNext(null);
        size--;

        return answer;
    }

    //@Override
    public Iterator<E> iterator() {
        return new SinglyLinkedListIterator();
    }

    private class SinglyLinkedListIterator implements Iterator<E> {
        Node<E> curr = (Node<E>) head;

        @Override
        public boolean hasNext() {
            return curr != null;
        }

        @Override
        public E next() {
            E res = curr.getElement();
            curr = curr.next;
            return res;
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        Node<E> curr = head;
        while (curr != null) {
            sb.append(curr.getElement());
            if (curr.getNext() != null)
                sb.append(", ");
            curr = curr.getNext();
        }
        sb.append("]");
        return sb.toString();
    }

    public SinglyLinkedList<E> sortedMerge(SinglyLinkedList<E> other) {

        SinglyLinkedList<E> result = new SinglyLinkedList<>();

        Node<E> p1 = this.head;
        Node<E> p2 = other.head;

        while (p1 != null && p2 != null) {
            E a = p1.getElement();
            E b = p2.getElement();

            if (((Comparable<? super E>) a).compareTo(b) <= 0) {
                result.addLast(a);
                p1 = p1.getNext();
            } else {
                result.addLast(b);
                p2 = p2.getNext();
            }
        }

        while (p1 != null) {
            result.addLast(p1.getElement());
            p1 = p1.getNext();
        }

        while (p2 != null) {
            result.addLast(p2.getElement());
            p2 = p2.getNext();
        }

        return result;
    }

    public void reverse() {

        Node<E> prev = null;
        Node<E> curr = head;
        Node<E> next;

        tail = head;

        while (curr != null) {
            next = curr.getNext();
            curr.setNext(prev);
            prev = curr;
            curr = next;
        }

        head = prev;
    }

    public SinglyLinkedList<E> copy() {

        SinglyLinkedList<E> twin = new SinglyLinkedList<>();

        Node<E> tmp = head;

        while (tmp != null) {
            twin.addLast(tmp.getElement());
            tmp = tmp.getNext();
        }

        return twin;
    }

    public static void main(String[] args) {
        SinglyLinkedList<Integer> ll = new SinglyLinkedList<Integer>();
        System.out.println("ll " + ll + " isEmpty: " + ll.isEmpty());
        //LinkedList<Integer> ll = new LinkedList<Integer>();

        ll.addFirst(0);
        ll.addFirst(1);
        ll.addFirst(2);
        ll.addFirst(3);
        ll.addFirst(4);
        ll.addLast(-1);
        //ll.removeLast();
        //ll.removeFirst();
        //System.out.println("I accept your apology");
        //ll.add(3, 2);
        System.out.println(ll);
        ll.remove(5);
        System.out.println(ll);

        SinglyLinkedList<Integer> l1 = new SinglyLinkedList<>();
        l1.addLast(2); l1.addLast(6); l1.addLast(20); l1.addLast(24);

        SinglyLinkedList<Integer> l2 = new SinglyLinkedList<>();
        l2.addLast(1); l2.addLast(3); l2.addLast(5); l2.addLast(8);
        l2.addLast(12); l2.addLast(19); l2.addLast(25);

        System.out.println(l1.sortedMerge(l2));

        SinglyLinkedList<Integer> test = new SinglyLinkedList<>();
        test.addLast(1);
        test.addLast(2);
        test.addLast(3);
        test.addLast(4);

        System.out.println("original: " + test);

        test.reverse();
        System.out.println("reversed: " + test);

        SinglyLinkedList<Integer> clone = test.copy();
        System.out.println("copy: " + clone);

    }
}
