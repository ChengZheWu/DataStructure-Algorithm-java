import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final WeightedQuickUnionUF uf1, uf2;
    private final int num; // 輸入n*n grid 的邊長大小
    private final int topNode; // 頂部的點
    private final int bottomNode; // 底部的點
    private int count = 0; // open 的數量
    private boolean[] arr; // 紀錄open的點
    private boolean limit; // row 跟 col 的邊界條件

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n must be bigger than 0");
        }
        else {
            num = n;
            topNode = num * num;
            bottomNode = topNode + 1;
            uf1 = new WeightedQuickUnionUF(topNode + 2);
            uf2 = new WeightedQuickUnionUF(topNode + 2);
            arr = new boolean[num * num];
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        limit = row < 0 || row > num - 1 || col < 0 || col > num - 1;
        limitRange(limit);
        int site = site1D(row, col);
        if (!arr[site]) {
            arr[site] = true;
            if (row == 0) {
                uf1.union(topNode, site);
                uf2.union(topNode, site);
            }
            if (row == (num - 1)) {
                uf1.union(bottomNode, site);
            }
            if (row - 1 >= 0 && arr[site1D(row - 1, col)]) {
                uf1.union(site, site1D(row - 1, col));
                uf2.union(site, site1D(row - 1, col));
            }
            if (row + 1 <= num - 1 && arr[site1D(row + 1, col)]) {
                uf1.union(site, site1D(row + 1, col));
                uf2.union(site, site1D(row + 1, col));
            }
            if (col - 1 >= 0 && arr[site1D(row, col - 1)]) {
                uf1.union(site, site1D(row, col - 1));
                uf2.union(site, site1D(row, col - 1));
            }
            if (col + 1 <= num - 1 && arr[site1D(row, col + 1)]) {
                uf1.union(site, site1D(row, col + 1));
                uf2.union(site, site1D(row, col + 1));
            }
            count++;
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        limitRange(limit);
        return arr[site1D(row, col)];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        limitRange(limit);
        return uf2.find(topNode) == uf2.find(site1D(row, col));
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return count;
    }

    // does the system percolate?
    public boolean percolates() {
        return uf1.find(topNode) == uf1.find(bottomNode);
    }

    // 將二維的 n*n grid 轉換為一維來記錄
    private int site1D(int row, int col) {
        int site = row * num + col;
        return site;
    }

    // 如果超出指定範圍就丟出錯誤訊息
    private void limitRange(boolean test) {
        if (test) {
            throw new IllegalArgumentException("row or col is out of range");
        }
    }

    // public static void main(String[] args) {
    //     In in = new In("heart25.txt");
    //     int n = in.readInt();
    //     Percolation percolation = new Percolation(n);
    //     while (!in.isEmpty()) {
    //         int row = in.readInt();
    //         int col = in.readInt();
    //         percolation.open(row, col);
    //     }
    //     System.out.println(percolation.isOpen(24, 24));
    // }
}
