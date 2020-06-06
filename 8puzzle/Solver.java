import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
    private Board initial;
    private MinPQ<SearchNode> pq;

    private class SearchNode implements Comparable<SearchNode> {
        private Board board;
        private SearchNode previousNode;
        private int moves;
        private int priority;

        public SearchNode(Board board, int moves, SearchNode previousNode) {
            this.board = board;
            this.moves = moves;
            this.previousNode = previousNode;
            priority = moves + board.manhattan();
            // priority = moves + board.hamming();
        }

        public int compareTo(SearchNode other) {
            return Integer.compare(this.priority, other.priority);
        }
    }

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) throw new IllegalArgumentException();
        this.initial = initial;
        SearchNode minNode;
        pq = new MinPQ<SearchNode>();
        pq.insert(new SearchNode(initial, 0, null));
        if (pq.min().board.isSolvable()) {
            while (!pq.min().board.isGoal()) {
                minNode = pq.min();
                pq.delMin();
                for (Board neighbor : minNode.board.neighbors()) {
                    // StdOut.println(neighbor.toString());
                    // StdOut.println(neighbor.manhattan());
                    if (minNode.moves == 0 || !neighbor.equals(minNode.previousNode.board))
                        pq.insert(new SearchNode(neighbor, minNode.moves + 1, minNode));
                }
                // StdOut.println(pq.min().board);
            }
        }
    }

    // min number of moves to solve initial board
    public int moves() {
        if (!pq.min().board.isGoal()) return -1;
        return pq.min().moves;
    }

    // sequence of boards in a shortest solution
    public Iterable<Board> solution() {
        if (!pq.min().board.isSolvable()) return null;
        Stack<Board> solutionStack = new Stack<Board>();
        SearchNode currentNode = pq.min();
        while (currentNode.previousNode != null) {
            solutionStack.push(currentNode.board);
            currentNode = currentNode.previousNode;
        }
        return solutionStack;
    }

    // test client (see below)
    public static void main(String[] args) {
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        Solver solver = new Solver(initial);

        if (!initial.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }

}
