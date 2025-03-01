package com.phasmidsoftware.dsaipg.adt.pq;

import java.util.Comparator;

public class FourAryHeap<K> extends PriorityQueue<K> {
    /**
     * 4-ary heap.
     *
     * @param n          The maximum capacity of the heap.
     * @param max        If true, this heap is a Max-Heap; if false, it's a Min-Heap.
     * @param comparator The comparator to define priority order (natural order or custom order).
     * @param floyd      If true, Floyd's heap construction optimization is enabled.
     */
    public FourAryHeap(int n, boolean max, Comparator<K> comparator, boolean floyd) {
        super(n, 1, max, comparator, floyd);
    }

    public FourAryHeap(int n, boolean max, Comparator<K> comparator) {
        super(n, 1, max, comparator, false);
    }

    public FourAryHeap(int n, Comparator<K> comparator) {
        super(n, 1, true, comparator, true);
    }

    @Override
    public int parent(int k) {return (k - 2) / 4 + 1;}

    @Override
    public int firstChild(int k) {return (k - 1) * 4 + 2;}
}

