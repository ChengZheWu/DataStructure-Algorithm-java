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

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;

public class KdTreeST<Value> {
    private Node root;
    private int size;

    private class Node {
        private Point2D p;     // the point
        private Value val;     // the symbol table maps the point to this value
        private RectHV rect;   // the axis-aligned rectangle corresponding to this node
        private Node lb;       // the left/bottom subtree
        private Node rt;       // the right/top subtree
        private boolean isVertical;

        public Node(Point2D p, Value val, RectHV rect, boolean isVertical) {
            this.p = p;
            this.val = val;
            this.rect = rect;
            this.isVertical = isVertical;
        }
    }

    // construct an empty symbol table of points
    public KdTreeST() {
        root = null;
        size = 0;
    }

    // is the symbol table empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // number of points
    public int size() {
        return size;
    }

    // associate the value val with point p
    public void put(Point2D p, Value val) {
        if (p == null) throw new IllegalArgumentException();
        root = put(root, null, p, val, 0);
    }

    private Node put(Node n, Node parent, Point2D p, Value val, int goleft) {
        if (n == null) {
            if (isEmpty()) {
                size++;
                return new Node(p, val, new RectHV(0, 0, 1, 1), true);
            }
            RectHV rect;
            if (parent.isVertical) {
                if (goleft > 0)
                    rect = new RectHV(parent.rect.xmin(), parent.rect.ymin(), parent.p.x(),
                                      parent.rect.ymax());
                else
                    rect = new RectHV(parent.p.x(), parent.rect.ymin(), parent.rect.xmax(),
                                      parent.rect.ymax());
            }
            else {
                if (goleft > 0)
                    rect = new RectHV(parent.rect.xmin(), parent.rect.ymin(),
                                      parent.rect.xmax(), parent.p.y());
                else
                    rect = new RectHV(parent.rect.xmin(), parent.p.y(), parent.rect.xmax(),
                                      parent.rect.ymax());
            }
            return new Node(p, val, rect, !parent.isVertical);
        }
        else {
            int cmp;
            if (n.p.equals(p)) cmp = 0;
            else if (n.isVertical) cmp = n.p.x() > p.x() ? 1 : -1;
            else cmp = n.p.y() > p.y() ? 1 : -1;
            if (cmp > 0) n.lb = put(n.lb, n, p, val, cmp);
            else if (cmp < 0) n.rt = put(n.rt, n, p, val, cmp);
            size++;
            return n;
        }
    }

    // value associated with point p
    public Value get(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        return get(root, p, true);
    }

    private Value get(Node n, Point2D p, boolean isVertical) {
        if (p == null) throw new IllegalArgumentException();
        if (n == null) return null;
        if (isVertical) {
            int xcmp = Double.compare(p.x(), n.p.x());
            if (xcmp < 0) return get(n.lb, p, false);
            if (xcmp > 0) return get(n.rt, p, false);
        }
        else {
            int ycmp = Double.compare(p.y(), n.p.y());
            if (ycmp < 0) return get(n.lb, p, true);
            if (ycmp > 0) return get(n.rt, p, true);
        }
        return n.val;
    }

    // does the symbol table contain point p?
    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        if (get(p) != null) return true;
        return false;
    }

    // all points in the symbol table
    public Iterable<Point2D> points() {
        Queue<Point2D> keys = new Queue<>();
        Queue<Node> queue = new Queue<>();
        queue.enqueue(root);
        while (!queue.isEmpty()) {
            Node n = queue.dequeue();
            if (n == null) continue;
            keys.enqueue(n.p);
            queue.enqueue(n.lb);
            queue.enqueue(n.rt);
        }
        return keys;
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException();
        Queue<Point2D> queue = new Queue<>();
        check(root, rect, queue);
        return queue;
    }

    private void check(Node n, RectHV rect, Queue<Point2D> queue) {
        if (n != null) {
            if (!n.rect.intersects(rect)) return;
            if (rect.contains(n.p)) queue.enqueue(n.p);
            check(n.lb, rect, queue);
            check(n.rt, rect, queue);
        }
    }

    // a nearest neighbor of point p; null if the symbol table is empty
    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        if (root != null) return nearest(root, p, root.p);
        return null;
    }

    private Point2D nearest(Node n, Point2D p, Point2D closet) {
        if (n.p.equals(p)) return n.p;
        double closetdis = closet.distanceSquaredTo(p);
        if (Double.compare(n.rect.distanceSquaredTo(p), closetdis) >= 0)
            return closet;
        else {
            if (Double.compare(n.p.distanceSquaredTo(p), closetdis) == -1) {
                closet = n.p;
            }
            if (n.lb != null)
                closet = nearest(n.lb, p, closet);
            if (n.rt != null)
                closet = nearest(n.rt, p, closet);
        }
        return closet;
    }

    public Iterable<Point2D> nearest(Point2D p, int k) {
        Queue<Point2D> queue = new Queue<>();
        for (Point2D key : points())
            if (p.distanceSquaredTo(key) <= k)
                queue.enqueue(key);
        return queue;
    }

    // unit testing (required)
    public static void main(String[] args) {

    }
}
