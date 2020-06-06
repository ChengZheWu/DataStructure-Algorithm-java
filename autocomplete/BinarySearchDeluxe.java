import edu.princeton.cs.algs4.StdOut;

import java.util.Comparator;

public class BinarySearchDeluxe {

    // Returns the index of the first key in the sorted array a[]
    // that is equal to the search key, or -1 if no such key.
    public static <Key> int firstIndexOf(Key[] a, Key key, Comparator<Key> comparator) {
        if (a == null || key == null || comparator == null) throw new NullPointerException();
        if (a.length == 0) return -1;
        int lo = 0, hi = a.length - 1;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if (comparator.compare(key, a[mid]) <= 0) hi = mid;
            else lo = mid;
            if (comparator.compare(key, a[lo]) == 0) return lo;
            lo++;
        }
        return -1;
    }

    // Returns the index of the last key in the sorted array a[]
    // that is equal to the search key, or -1 if no such key.
    public static <Key> int lastIndexOf(Key[] a, Key key, Comparator<Key> comparator) {
        if (a == null || key == null || comparator == null) throw new NullPointerException();
        if (a.length == 0) return -1;
        int lo = 0, hi = a.length - 1;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if (comparator.compare(key, a[mid]) >= 0) lo = mid;
            else hi = mid;
            if (comparator.compare(key, a[hi]) == 0) return hi;
            hi--;
        }
        return -1;
    }


    // unit testing (required)
    public static void main(String[] args) {
        String[] a = { "A", "B", "B", "C", "G", "G", "T" };
        int findex = BinarySearchDeluxe.firstIndexOf(a, "B", String.CASE_INSENSITIVE_ORDER);
        int lindex = BinarySearchDeluxe.lastIndexOf(a, "B", String.CASE_INSENSITIVE_ORDER);
        StdOut.println(findex);
        StdOut.println(lindex);
    }
}

