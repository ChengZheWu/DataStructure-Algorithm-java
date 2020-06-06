import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class Board {
    private int[] tiles1d;
    private final int len;
    private int blank;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        len = tiles.length;
        this.tiles1d = new int[tiles.length * tiles.length];
        int index = 0;
        for (int row = 0; row < len; row++)
            for (int col = 0; col < len; col++) {
                this.tiles1d[index] = tiles[row][col];
                if (tiles[row][col] == 0) blank = index;
                index++;
            }
    }

    private int row(int i) {
        return i / size();
    }

    private int col(int i) {
        return i % size();
    }

    private int coord1d(int row, int col) {
        return row * size() + col;
    }

    // string representation of this board
    public String toString() {
        StringBuilder sb = new StringBuilder();
        int n = size();
        sb.append(n + "\n");
        for (int i = 0; i < tiles1d.length; i++) {
            sb.append(String.format("%2d ", tileAt(row(i), col(i))));
            if (i % size() == size() - 1) sb.append("\n");
        }
        String tilestr = sb.toString();
        return tilestr;
    }

    // tile at (row, col) or 0 if blank
    public int tileAt(int row, int col) {
        if (row < 0 || row > len - 1 || col < 0 || col > len - 1)
            throw new IllegalArgumentException();
        return tiles1d[coord1d(row, col)];
    }

    // board size n
    public int size() {
        return len;
    }

    // number of tiles out of place
    public int hamming() {
        int count = 0;
        for (int i = 0; i < tiles1d.length; i++) {
            // StdOut.println(copy1d[i]);
            if ((tiles1d[i] != i + 1) && (tiles1d[i] != 0))
                count++;
        }
        return count;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int sum = 0;
        for (int i = 0; i < tiles1d.length; i++)
            if ((tiles1d[i] != i + 1) && (tiles1d[i] != 0)) {
                int dis = calmandis(i);
                sum += dis;
            }
        return sum;
    }

    // calculate the manhatten distance
    private int calmandis(int i) {
        int goal = tiles1d[i];
        int goal_row = (goal - 1) / size();
        int goal_col = (goal - 1) % size();
        int dis = Math.abs(row(i) - goal_row) + Math.abs(col(i) - goal_col);
        return dis;
    }

    // is this board the goal board?
    public boolean isGoal() {
        for (int i = 0; i < tiles1d.length - 1; i++)
            if (tiles1d[i] != i + 1) return false;
        return true;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        Board yy = (Board) y;
        return Arrays.equals(this.tiles1d, yy.tiles1d);
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        Stack<Board> neighbors = new Stack<>();
        int row_b = blank / size();
        int col_b = blank % size();
        if (row_b > 0) neighbors.push(new Board(swap(row_b, col_b, row_b - 1, col_b)));
        if (row_b < size() - 1) neighbors.push(new Board(swap(row_b, col_b, row_b + 1, col_b)));
        if (col_b > 0) neighbors.push((new Board(swap(row_b, col_b, row_b, col_b - 1))));
        if (col_b < size() - 1) neighbors.push(new Board(swap(row_b, col_b, row_b, col_b + 1)));
        return neighbors;
    }


    // swap the tiles
    private int[][] swap(int row1, int col1, int row2, int col2) {
        int[][] copy = new int[len][len];
        for (int row = 0; row < len; row++)
            for (int col = 0; col < len; col++)
                copy[row][col] = tiles1d[coord1d(row, col)];
        int tmp = copy[row1][col1];
        copy[row1][col1] = copy[row2][col2];
        copy[row2][col2] = tmp;
        return copy;
    }


    // is this board solvable?
    public boolean isSolvable() {
        if (size() % 2 != 0) {
            if (calInversion() % 2 == 0)
                return true;
            else
                return false;
        }
        else {
            int row_b = blank / size();
            if ((calInversion() + row_b) % 2 != 0)
                return true;
            return false;
        }
    }

    // calculate the number of inversion
    private int calInversion() {
        int sum = 0;
        // StdOut.println(copy.length);
        for (int i = 0; i < tiles1d.length; i++)
            if (tiles1d[i] != 0)
                for (int j = i; j < tiles1d.length; j++)
                    if (tiles1d[j] != 0)
                        if (tiles1d[i] > tiles1d[j])
                            sum++;
        return sum;
    }

    // unit testing (required)
    public static void main(String[] args) {
        int n = 3;
        int[][] tiles = new int[n][n];

        tiles[0][0] = 1;
        tiles[0][1] = 2;
        tiles[0][2] = 3;
        // tiles[0][3] = 13;

        tiles[1][0] = 8;
        tiles[1][1] = 4;
        tiles[1][2] = 6;
        // tiles[1][3] = 14;

        tiles[2][0] = 0;
        tiles[2][1] = 5;
        tiles[2][2] = 7;
        // tiles[2][3] = 6;
        //
        // tiles[3][0] = 4;
        // tiles[3][1] = 8;
        // tiles[3][2] = 2;
        // tiles[3][3] = 1;
        Board board = new Board(tiles);

        StdOut.println(board.isGoal());
        StdOut.println(board.isSolvable());
        StdOut.println(board.toString());
        // for (Board tile : board.neighbors()) {
        //     StdOut.println(tile.toString());
        // }
        StdOut.println("inverse:" + board.calInversion());
        StdOut.println("manhattan:" + board.manhattan());
        StdOut.println("hamming:" + board.hamming());
    }
}
