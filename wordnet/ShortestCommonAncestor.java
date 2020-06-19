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

import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Topological;

public class ShortestCommonAncestor {
    private Digraph dg;

    // constructor takes a rooted DAG as argument
    public ShortestCommonAncestor(Digraph G) {
        if (G == null) throw new IllegalArgumentException();
        Topological topological = new Topological(G);
        if (topological.hasOrder()) this.dg = new Digraph(G);
        else throw new IllegalArgumentException();
    }

    // length of shortest ancestral path between v and w
    public int length(int v, int w) {
        if (v < 0 || w < 0 || v > dg.V() - 1 || w > dg.V() - 1)
            throw new IllegalArgumentException();
        BreadthFirstDirectedPaths vpath = new BreadthFirstDirectedPaths(dg, v);
        BreadthFirstDirectedPaths wpath = new BreadthFirstDirectedPaths(dg, w);
        Stack<Integer> lenstack = new Stack<>();
        for (int a = 0; a < dg.V(); a++) {
            if (vpath.hasPathTo(a) && wpath.hasPathTo(a)) {
                int l = vpath.distTo(a) + wpath.distTo(a);
                if (lenstack.isEmpty()) lenstack.push(l);
                else if (lenstack.peek() > l) lenstack.push(l);
            }
        }
        if (lenstack.isEmpty()) return -1;
        else return lenstack.peek();
    }

    // a shortest common ancestor of vertices v and w
    public int ancestor(int v, int w) {
        if (v < 0 || w < 0 || v > dg.V() - 1 || w > dg.V() - 1)
            throw new IllegalArgumentException();
        BreadthFirstDirectedPaths vpath = new BreadthFirstDirectedPaths(dg, v);
        BreadthFirstDirectedPaths wpath = new BreadthFirstDirectedPaths(dg, w);
        Stack<Integer> lenstack = new Stack<>();
        Stack<Integer> anstack = new Stack<>();
        for (int a = 0; a < dg.V(); a++) {
            if (vpath.hasPathTo(a) && wpath.hasPathTo(a)) {
                int l = vpath.distTo(a) + wpath.distTo(a);
                if (lenstack.isEmpty()) {
                    lenstack.push(l);
                    anstack.push(a);
                }
                else if (lenstack.peek() > l) {
                    lenstack.push(l);
                    anstack.push(a);
                }
            }
        }
        if (lenstack.isEmpty()) return -1;
        else return anstack.peek();
    }

    // length of shortest ancestral path of vertex subsets A and B
    public int lengthSubset(Iterable<Integer> subsetA, Iterable<Integer> subsetB) {
        if (subsetA == null || subsetB == null) throw new IllegalArgumentException();
        Stack<Integer> lenstack = new Stack<>();
        for (int v : subsetA) {
            for (int w : subsetB) {
                BreadthFirstDirectedPaths vpath = new BreadthFirstDirectedPaths(dg, v);
                BreadthFirstDirectedPaths wpath = new BreadthFirstDirectedPaths(dg, w);
                for (int a = 0; a < dg.V(); a++) {
                    if (vpath.hasPathTo(a) && wpath.hasPathTo(a)) {
                        int l = vpath.distTo(a) + wpath.distTo(a);
                        if (lenstack.isEmpty()) lenstack.push(l);
                        else if (lenstack.peek() > l) lenstack.push(l);
                    }
                }
            }
        }
        if (lenstack.isEmpty()) return -1;
        else return lenstack.peek();
    }

    // a shortest common ancestor of vertex subsets A and B
    public int ancestorSubset(Iterable<Integer> subsetA, Iterable<Integer> subsetB) {
        if (subsetA == null || subsetB == null) throw new IllegalArgumentException();
        Stack<Integer> lenstack = new Stack<>();
        Stack<Integer> anstack = new Stack<>();
        for (int v : subsetA) {
            for (int w : subsetB) {
                BreadthFirstDirectedPaths vpath = new BreadthFirstDirectedPaths(dg, v);
                BreadthFirstDirectedPaths wpath = new BreadthFirstDirectedPaths(dg, w);
                for (int a = 0; a < dg.V(); a++) {
                    if (vpath.hasPathTo(a) && wpath.hasPathTo(a)) {
                        int l = vpath.distTo(a) + wpath.distTo(a);
                        if (lenstack.isEmpty()) lenstack.push(l);
                        else if (lenstack.peek() > l) lenstack.push(l);
                    }
                }
            }
        }
        if (lenstack.isEmpty()) return -1;
        else return anstack.peek();
    }

    // unit testing (required)
    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        ShortestCommonAncestor sca = new ShortestCommonAncestor(G);
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length = sca.length(v, w);
            int ancestor = sca.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }

}
