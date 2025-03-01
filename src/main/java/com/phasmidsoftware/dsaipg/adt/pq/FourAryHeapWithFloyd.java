package com.phasmidsoftware.dsaipg.adt.pq;

import java.util.Collection;
import java.util.Comparator;

public class FourAryHeapWithFloyd<K> extends PriorityQueue<K> {
    /**
     *  4-ary heap with Floyd's trick.
     *
     * @param n          The maximum capacity of the heap.
     * @param max        If true, this heap is a Max-Heap; otherwise, it's a Min-Heap.
     * @param comparator The comparator to define the priority order.
     */
    public FourAryHeapWithFloyd(int n, boolean max, Comparator<K> comparator) {
        super(n, 1, max, comparator, true); // Enable floyd with true
    }

    public FourAryHeapWithFloyd(Iterable<K> elements, boolean max, Comparator<K> comparator) {
        super(((Collection<K>) elements).size(), 1, max, comparator, true);
        int i = 0;
        for (K element : elements) { binHeap[i++] = element; }
        int m = ((Collection<K>) elements).size();

        int k = (m + 1) / 4 - 1;
        for (; k >= 0; k--) sink(k);
    }

    @Override
    public int parent(int k) {
        return (k - 2) / 4 + 1;
    }

    @Override
    public int firstChild(int k) {
        return (k - 1) * 4 + 2;
    }
}

