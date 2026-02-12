package project20280.list;

import project20280.interfaces.List;

import java.util.Iterator;

public class CircularlyLinkedList<E> implements List<E> {

    private class Node {
        private final E data;
        private Node next;

        public Node(E e, Node n) {
            data = e;
            next = n;
        }

        public E getData() {
            return data;
        }

        public void setNext(Node n) {
            next = n;
        }

        public Node getNext() {
            return next;
        }
    }

    private Node tail = null;
    private int size = 0;

    public CircularlyLinkedList() {

    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public E get(int i) {
        if (i < 0 || i >= size) throw new IndexOutOfBoundsException();
        Node curr = tail.next; // head
        for (int k = 0; k < i; k++) curr = curr.next;
        return curr.data;
    }

    /**
     * Inserts the given element at the specified index of the list, shifting all
     * subsequent elements in the list one position further to make room.
     *
     * @param i the index at which the new element should be stored
     * @param e the new element to be stored
     */
    @Override
    public void add(int i, E e) {
        if (i < 0 || i > size) throw new IndexOutOfBoundsException();

        if (i == 0) { addFirst(e); return; }
        if (i == size) { addLast(e); return; }

        Node prev = tail.next; // head
        for (int k = 0; k < i - 1; k++) prev = prev.next;

        Node newest = new Node(e, prev.next);
        prev.next = newest;
        size++;
    }

    @Override
    public E remove(int i) {
        if (i < 0 || i >= size) throw new IndexOutOfBoundsException();

        if (i == 0) return removeFirst();
        if (i == size - 1) return removeLast();

        Node prev = tail.next; // head
        for (int k = 0; k < i - 1; k++) prev = prev.next;

        Node target = prev.next;
        E answer = target.data;
        prev.next = target.next;
        size--;
        return answer;
    }

    public void rotate() {
        if (tail != null) {
            tail = tail.next;
        }
    }

    private class CircularlyLinkedListIterator implements Iterator<E> {
        private int seen = 0;
        private Node curr = (tail == null) ? null : tail.next; // head

        @Override
        public boolean hasNext() {
            return seen < size;
        }

        @Override
        public E next() {
            E res = curr.data;
            curr = curr.next;
            seen++;
            return res;
        }
    }

    @Override
    public Iterator<E> iterator() {
        return new CircularlyLinkedListIterator();
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public E removeFirst() {
        if (isEmpty()) return null;

        Node head = tail.next;
        E answer = head.data;

        if (size == 1) {
            tail = null;
        } else {
            tail.next = head.next;
        }

        size--;
        return answer;
    }

    @Override
    public E removeLast() {
        if (isEmpty()) return null;
        if (size == 1) return removeFirst();

        Node prev = tail.next; // head
        while (prev.next != tail) {
            prev = prev.next;
        }

        E answer = tail.data;
        prev.next = tail.next; // 指回 head
        tail = prev;
        size--;
        return answer;
    }

    @Override
    public void addFirst(E e) {
        Node newest = new Node(e, null);

        if (isEmpty()) {
            newest.next = newest;
            tail = newest;
        } else {
            newest.next = tail.next;
            tail.next = newest;
        }

        size++;
    }

    @Override
    public void addLast(E e) {
        addFirst(e);
        tail = tail.next;
    }

    public String toString() {
        if (isEmpty()) return "[]";

        StringBuilder sb = new StringBuilder("[");
        Node curr = tail.next; // head
        for (int k = 0; k < size; k++) {
            sb.append(curr.data);
            if (k != size - 1) sb.append(", ");
            curr = curr.next;
        }
        sb.append("]");
        return sb.toString();
    }

    public static void main(String[] args) {
        CircularlyLinkedList<Integer> ll = new CircularlyLinkedList<Integer>();
        for (int i = 10; i < 20; ++i) {
            ll.addLast(i);
        }

        System.out.println(ll);

        ll.removeFirst();
        System.out.println(ll);

        ll.removeLast();
        System.out.println(ll);

        ll.rotate();
        System.out.println(ll);

        ll.removeFirst();
        ll.rotate();
        System.out.println(ll);

        ll.removeLast();
        ll.rotate();
        System.out.println(ll);

        for (Integer e : ll) {
            System.out.println("value: " + e);
        }

    }
}
