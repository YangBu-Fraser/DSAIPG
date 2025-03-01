package com.phasmidsoftware.dsaipg.adt.pq;

import java.util.*;

public class FibonacciHeap<T extends Comparable<T>> {
    private static class Node<T> {
        int degree;
        boolean marked;
        T key;
        Node<T> parent;
        Node<T> child;
        Node<T> left;
        Node<T> right;


        Node(T key) {
            this.key = key;
            this.left = this;
            this.right = this;
        }
    }

    private Node<T> minNode;
    private int size;

    public FibonacciHeap() {
        this.minNode = null;
        this.size = 0;
    }

    public boolean isEmpty() { return minNode == null; }

    public int size() { return size; }

    public Node<T> insert(T key) {
        Node<T> newNode = new Node<>(key);
        if (minNode == null) minNode = newNode;
        else {
            mergeWithRootList(newNode);
            if (newNode.key.compareTo(minNode.key) < 0) {
                minNode = newNode;
            }
        }
        size++;
        return newNode;
    }

    public T extractMin() {
        Node<T> z = minNode;
        if (z != null) {
            if (z.child != null) {
                Node<T> child = z.child;
                do {
                    Node<T> nextChild = child.right;
                    mergeWithRootList(child);
                    child.parent = null;
                    child = nextChild;
                } while (child != z.child);
            }

            if (z == z.right) minNode = null;
            else {
                minNode = z.right;
                removeFromRootList(z);
                consolidate();
            }
            size--;
            return z.key;
        }
        return null;
    }

    public void decreaseKey(Node<T> node, T newKey) {
        if (newKey.compareTo(node.key) > 0) {
            throw new IllegalArgumentException("new key is greater than current key !");
        }
        node.key = newKey;
        Node<T> parent = node.parent;
        if (parent != null && node.key.compareTo(parent.key) < 0) {
            cut(node, parent);
            cascadingCut(parent);
        }
        if (node.key.compareTo(minNode.key) < 0) minNode = node;
    }

    private void mergeWithRootList(Node<T> node) {
        if (minNode == null) minNode = node;
        else {
            node.right = minNode.right;
            node.left = minNode;
            minNode.right.left = node;
            minNode.right = node;
        }
    }

    private void removeFromRootList(Node<T> node) {
        if (node == minNode) minNode = node.right;
        node.left.right = node.right;
        node.right.left = node.left;
    }

    private void consolidate() {
        Map<Integer, Node<T>> degreeTable = new HashMap<>();
        List<Node<T>> nodes = new ArrayList<>();
        Node<T> current = minNode;
        do {
            nodes.add(current);
            current = current.right;
        } while (current != minNode);

        for (Node<T> node : nodes) {
            int degree = node.degree;
            while (degreeTable.containsKey(degree)) {
                Node<T> other = degreeTable.get(degree);
                if (node.key.compareTo(other.key) > 0) {
                    Node<T> temp = node;
                    node = other;
                    other = temp;
                }
                link(other, node);
                degreeTable.remove(degree);
                degree++;
            }
            degreeTable.put(degree, node);
        }

        minNode = null;
        for (Node<T> node : degreeTable.values()) {
            if (minNode == null) {
                minNode = node;
                node.left = node;
                node.right = node;
            } else {
                mergeWithRootList(node);
                if (node.key.compareTo(minNode.key) < 0) minNode = node;
            }
        }
    }

    private void link(Node<T> child, Node<T> parent) {
        removeFromRootList(child);
        child.parent = parent;
        if (parent.child == null) {
            parent.child = child;
            child.left = child;
            child.right = child;
        } else {
            child.right = parent.child.right;
            child.left = parent.child;
            parent.child.right.left = child;
            parent.child.right = child;
        }
        parent.degree++;
        child.marked = false;
    }

    private void cut(Node<T> node, Node<T> parent) {
        removeFromRootList(node);
        parent.degree--;
        if (parent.child == node) {
            parent.child = (node.right != node) ? node.right : null;
        }
        if (parent.degree == 0) {
            parent.child = null;
        }
        node.parent = null;
        node.marked = false;
        mergeWithRootList(node);
    }

    private void cascadingCut(Node<T> node) {
        Node<T> parent = node.parent;
        if (parent != null) {
            if (!node.marked) {
                node.marked = true;
            } else {
                cut(node, parent);
                cascadingCut(parent);
            }
        }
    }
}