package com.phasmidsoftware.dsaipg.adt.pq;

import java.util.Comparator;

public class BinaryHeap<K> {
    private final PriorityQueue<K> priorityQueue;

    /**
     * binary heap
     *
     * @param size        The maximum size of the heap
     * @param isMaxHeap   True for max heap, False for min heap
     * @param comparator  A comparator to define the order
     */
    public BinaryHeap(int size, boolean isMaxHeap, Comparator<K> comparator) {
        this.priorityQueue = new PriorityQueue<>(size, isMaxHeap, comparator);
    }

    public void insert(K element) {priorityQueue.give(element);}

    public K extractRoot() throws PQException {return priorityQueue.take();}

    public int size() {return priorityQueue.size();}


    public boolean isEmpty() {return priorityQueue.isEmpty();}

    public K peekRoot() {
        return priorityQueue.peek(priorityQueue.size());
    }
}

