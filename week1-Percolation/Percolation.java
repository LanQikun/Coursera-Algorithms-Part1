package week1;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

// 难点：回流问题
// 解决回流的方案:
// 增加一个UF字段，专用于isFull()

public class Percolation {
    private WeightedQuickUnionUF uf;
    private WeightedQuickUnionUF uf2;
    private boolean[] sites;
    private int length;
    private final int TOP = 0;
    // 用于表示是否连接到底端
    private final int BOTTOM;
    private int openCount;

    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("parameter should be positive");
        }
        length = n;
        int siteCount = n * n;
        // 表示site是否open，默认为close
        sites = new boolean[siteCount + 1];
        // 分别表示TOP、sites、BOTTOM
        uf = new WeightedQuickUnionUF(1 + siteCount + 1);
        // 用于isFull()，没有BOTTOM
        uf2 = new WeightedQuickUnionUF(1 + siteCount);
        BOTTOM = siteCount + 1;
        openCount = 0;
    }

    public void open(int row, int col) {
        if (isOpen(row, col)) {
            return;
        }

        int index = convertIndex(row, col);
        sites[index] = true;
        openCount++;

        if (row == 1) {
            uf.union(index, TOP);
            uf2.union(index, TOP);
        }
        if (row == length) {
            uf.union(index, BOTTOM);
        }

        tryConnect(index, row, col - 1);
        tryConnect(index, row, col + 1);
        tryConnect(index, row - 1, col);
        tryConnect(index, row + 1, col);
    }

    private int convertIndex(int row, int col) {
        return (row - 1) * length + col;
    }

    private void checkRange(int row, int col) {
        if (!inRange(row, col)) {
            throw new IllegalArgumentException("Row or column is out of range");
        }
    }

    private boolean inRange(int x, int y) {
        return inRange(x) && inRange(y);
    }

    private boolean inRange(int x) {
        return x >= 1 && x <= length;
    }

    private void tryConnect(int p, int r, int c) {
        if (inRange(r, c) && isOpen(r, c)) {
            uf.union(p, convertIndex(r, c));
            uf2.union(p, convertIndex(r, c));
        }
    }

    public boolean isOpen(int row, int col) {
        checkRange(row, col);
        return sites[convertIndex(row, col)];
    }

    public boolean isFull(int row, int col) {
        checkRange(row, col);
        return uf2.connected(convertIndex(row, col), TOP);

    }

    public int numberOfOpenSites() {
        return openCount;
    }

    public boolean percolates() {
        return uf.connected(TOP, BOTTOM);
    }

    // public static void main(String[] args){
    // Percolation p = new Percolation(2);
    // p.open(1, 1);
    // p.open(2, 1);
    // System.out.println("(1, 1) is open? " + p.isOpen(1, 1));
    // System.out.println("(2, 1) is open? " + p.isOpen(2, 1));
    // System.out.println("(1, 1) is full? " + p.isFull(1, 1));
    // System.out.println("(2, 1) is full? " + p.isFull(2, 1));
    // System.out.println("numberOfOpenSites: " + p.numberOfOpenSites());
    // System.out.println("percolates? " + p.percolates());
    // }

}
