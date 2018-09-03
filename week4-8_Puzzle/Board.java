package week4;

import java.util.Arrays;

import edu.princeton.cs.algs4.Stack;

public class Board {
    private final int[][] blocks;
    private final int n;
    private int manhattanPri;
    private int hammingPri;
    private final Stack<Board> neighborStack;

    public Board(final int[][] inputBlocks) {
        this.n = inputBlocks.length;
        this.blocks = copyArr(inputBlocks, n);
        this.neighborStack = new Stack<Board>();

        manhattanPri = 0;
        hammingPri = 0;
        for (int i = 0, right = 1; i < n; i++) {
            for (int j = 0; j < n; j++, right++) {
                int num = blocks[i][j];
                if (num == 0) {
                    continue;
                }
                if (num != right) {
                    int row = (num - 1) / n;
                    int col = (num - 1) % n;
                    manhattanPri += Math.abs(row - i) + Math.abs(col - j);
                    hammingPri++;
                }
            }
        }
    }

    public int dimension() {
        return n;
    }

    public int hamming() {
        return hammingPri;
    }

    public int manhattan() {
        return manhattanPri;
    }

    public boolean isGoal() {
        return hammingPri == 0;
    }

    private int[] getZeroPos() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (blocks[i][j] == 0) {
                    return new int[] { i, j };
                }
            }
        }
        throw new RuntimeException("can't find space");
    }

    private void exchange(int[][] arr, int i1, int j1, int i2, int j2) {
        int temp = arr[i1][j1];
        arr[i1][j1] = arr[i2][j2];
        arr[i2][j2] = temp;
    }

    public Board twin() {
        int zeroR = getZeroPos()[0];
        int[][] newBlocks = copyArr(blocks, n);

        if (zeroR == 0) {
            exchange(newBlocks, zeroR + 1, 0, zeroR + 1, 1);
        } else {
            exchange(newBlocks, 0, 0, 0, 1);
        }
        return new Board(newBlocks);
    }

    public boolean equals(Object other) {
        if (other == this)
            return true;
        if (other == null)
            return false;
        if (other.getClass() != this.getClass())
            return false;
        Board that = (Board) other;
        return Arrays.deepEquals(this.blocks, that.blocks);
    }

    private boolean inRange(int i) {
        return i >= 0 && i < n;
    }

    private boolean inRange(int r, int c) {
        return inRange(r) && inRange(c);
    }

    private int[][] copyArr(int[][] arr, int l) {
        int[][] result = new int[l][];
        for (int i = 0; i < l; i++) {
            result[i] = arr[i].clone();
        }
        return result;
    }

    private void tryNeighbor(int r, int c, int newR, int newC) {
        if (inRange(newR, newC)) {
            int[][] newBlocks = copyArr(blocks, n);
            exchange(newBlocks, r, c, newR, newC);
            neighborStack.push(new Board(newBlocks));
        }
    }

    public Iterable<Board> neighbors() {
        if (neighborStack.isEmpty()) {
            int[] zeroPos = getZeroPos();
            int r = zeroPos[0], c = zeroPos[1];
            tryNeighbor(r, c, r - 1, c);
            tryNeighbor(r, c, r + 1, c);
            tryNeighbor(r, c, r, c - 1);
            tryNeighbor(r, c, r, c + 1);
        }
        return neighborStack;
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(n + "\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s.append(String.format("%2d ", blocks[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    public static void main(String[] args) {
        // unit tests (not graded)
    }
}