package  com.example.dynamic_ride_allocator.graphs;

import com.example.dynamic_ride_allocator.Models.Driver;

import java.util.*;

public class DriverMinHeap {

    static ArrayList<Driver> heap = new ArrayList<>();

    public static void insert(Driver driver) {
        heap.add(driver);
        heapifyUp(heap.size() - 1);
    }

    public static void insertAll(ArrayList<Driver> drivers) {
        for (Driver d : drivers)
            insert(d);
    }

    public static Driver extractMin() {
        if (heap.isEmpty()) return null;
        Driver min  = heap.get(0);
        Driver last = heap.remove(heap.size() - 1);
        if (!heap.isEmpty()) {
            heap.set(0, last);
            heapifyDown(0);
        }
        return min;
    }

    public static Driver peekMin() {
        return heap.isEmpty() ? null : heap.get(0);
    }

    public static ArrayList<Driver> getAllInHeap() {
        return new ArrayList<>(heap);
    }

    public static void clear()        { heap.clear(); }
    public static boolean isEmpty()   { return heap.isEmpty(); }
    public static int size()          { return heap.size(); }

    public static void swap(int i, int j) {
        Driver temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
    }

    public static void heapifyUp(int i) {
        while (i > 0) {
            int parent = (i - 1) / 2;
            if (heap.get(parent).getDistance() > heap.get(i).getDistance()) {
                swap(parent, i);
                i = parent;
            } else break;
        }
    }

    public static void heapifyDown(int i){
        int size=heap.size();
        while (true){
            int left= 2 * i + 1;
            int right = 2 * i + 2;
            int smallest=i;
            if (left <size && heap.get(left).getDistance() < heap.get(smallest).getDistance())
                smallest = left;
            if (right<size && heap.get(right).getDistance() < heap.get(smallest).getDistance())
                smallest = right;
            if (smallest != i){
                swap(i, smallest); i = smallest;
            }
            else break;
        }
    }
}