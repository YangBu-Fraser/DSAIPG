package com.phasmidsoftware.dsaipg.adt.pq;

import java.util.Collection;
import java.util.Comparator;

public class BinaryHeapWithFloyd<K> {
    private final PriorityQueue<K> priorityQueue;
    /**
     * binary heap with Floyd tick.
     *
     * @param size       the maximum size of the heap.
     * @param isMaxHeap  true for max heap, false for min heap.
     * @param comparator the comparator to define heap order.
     */
    public BinaryHeapWithFloyd(int size, boolean isMaxHeap, Comparator<K> comparator) {
        this.priorityQueue = new PriorityQueue<>(size, isMaxHeap, comparator, true);
    }

    public BinaryHeapWithFloyd(Collection<K> elements, boolean isMaxHeap, Comparator<K> comparator) {
        this.priorityQueue = new PriorityQueue<>(elements, comparator);
    }

    public void insert(K element) {priorityQueue.give(element);}

    public K extractRoot() throws PQException {return priorityQueue.take();}

    public K peekRoot() {return priorityQueue.peek(priorityQueue.size());}

    public int size() {return priorityQueue.size();}

    public boolean isEmpty() {return priorityQueue.isEmpty();}
}
