package project20280.stacksqueues;

import project20280.interfaces.Deque;

public class ArrayDeque<E> implements Deque<E> {

    private static final int CAPACITY = 1000;

    private E[] data;
    private int front = 0;
    private int size = 0;

    @SuppressWarnings("unchecked")
    public ArrayDeque(int capacity) {
        data = (E[]) new Object[capacity];
    }

    public ArrayDeque() {
        this(CAPACITY);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public E first() {
        return isEmpty() ? null : data[front];
    }

    @Override
    public E last() {
        if (isEmpty()) return null;
        int back = (front + size - 1 + data.length) % data.length;
        return data[back];
    }

    @Override
    public void addFirst(E e) {
        if (size == data.length) {
            throw new IllegalStateException("Deque is full");
        }
        front = (front - 1 + data.length) % data.length;
        data[front] = e;
        size++;
    }

    @Override
    public void addLast(E e) {
        if (size == data.length) {
            throw new IllegalStateException("Deque is full");
        }
        int avail = (front + size) % data.length;
        data[avail] = e;
        size++;
    }

    @Override
    public E removeFirst() {
        if (isEmpty()) return null;
        E answer = data[front];
        data[front] = null;
        front = (front + 1) % data.length;
        size--;
        return answer;
    }

    @Override
    public E removeLast() {
        if (isEmpty()) return null;
        int back = (front + size - 1 + data.length) % data.length;
        E answer = data[back];
        data[back] = null;
        size--;
        return answer;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < size; ++i) {
            sb.append(data[(front + i) % data.length]);
            if (i != size - 1) sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }

    public static void main(String[] args) {

        Deque<Integer> dq = new ArrayDeque<>(5);

        System.out.println("Initial: " + dq);

        // test addLast
        dq.addLast(1);
        dq.addLast(2);
        dq.addLast(3);
        System.out.println("After addLast 1,2,3: " + dq);

        // test addFirst
        dq.addFirst(0);
        System.out.println("After addFirst 0: " + dq);

        // test first / last
        System.out.println("First: " + dq.first());
        System.out.println("Last: " + dq.last());

        // test removeFirst
        System.out.println("removeFirst: " + dq.removeFirst());
        System.out.println("After removeFirst: " + dq);

        // test removeLast
        System.out.println("removeLast: " + dq.removeLast());
        System.out.println("After removeLast: " + dq);

        // test cycle
        dq.addLast(4);
        dq.addLast(5);
        System.out.println("After addLast 4,5: " + dq);

        dq.removeFirst();
        dq.removeFirst();
        System.out.println("After removing two from front: " + dq);

        dq.addLast(6);
        dq.addLast(7);
        System.out.println("After adding 6,7 (wrap test): " + dq);
    }

}

