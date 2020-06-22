/* *****************************************************************************
 *  Name:    Alan Turing
 *  NetID:   aturing
 *  Precept: P00
 *
 *  Partner Name:    Ada Lovelace
 *  Partner NetID:   alovelace
 *  Partner Precept: P00
 *
 *  Description:  Prints 'Hello, World' to the terminal window.
 *                By tradition, this is everyone's first program.
 *                Prof. Brian Kernighan initiated this tradition in 1974.
 *
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.RedBlackBST;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.Stopwatch;

import java.util.LinkedList;


public class PointST<Value> {
    private RedBlackBST<Point2D, Value> points;
    private int call = 0;

    // construct an empty symbol table of points
    public PointST() {
        points = new RedBlackBST<>();
    }

    // is the symbol table empty?
    public boolean isEmpty() {
        return points.isEmpty();
    }

    // number of points
    public int size() {
        return points.size();
    }

    // associate the value val with point p
    public void put(Point2D p, Value val) {
        if (p == null) throw new IllegalArgumentException();
        if (!points.contains(p)) points.put(p, val);
    }

    // value associated with point p
    public Value get(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        return points.get(p);
    }

    // does the symbol table contain point p?
    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        return points.contains(p);
    }

    // all points in the symbol table
    public Iterable<Point2D> points() {
        LinkedList<Point2D> allkey = new LinkedList<>();
        for (Point2D k : points.keys())
            allkey.add(k);
        return allkey;
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException();
        LinkedList<Point2D> inRect = new LinkedList<>();
        for (Point2D k : points.keys())
            if (!rect.contains(k))
                inRect.add(k);
        return inRect;
    }

    // a nearest neighbor of point p; null if the symbol table is empty
    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        if (points.size() == 0) return null;
        Point2D tmp = points.min();
        for (Point2D k : points.keys()) {
            call++;
            if (p.distanceSquaredTo(k) < p.distanceSquaredTo(tmp))
                tmp = k;
        }
        return tmp;
    }

    // unit testing (required)
    public static void main(String[] args) {
        String filename = args[0];
        In in = new In(filename);

        PointST<Integer> ptree = new PointST<>();

        for (int i = 0; !in.isEmpty(); i++) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            ptree.put(p, i);
        }
        double x = StdRandom.uniform(0.0, 1.0);
        double y = StdRandom.uniform(0.0, 1.0);
        Point2D p = new Point2D(x, y);
        Stopwatch stopwatch = new Stopwatch();
        ptree.nearest(p);
        double time = stopwatch.elapsedTime();

        StdOut.println("num of nearest calling: " + ptree.call);
        StdOut.println("time: " + time);
        StdOut.println(ptree.call / time);
    }
}
