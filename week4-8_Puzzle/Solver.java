package week4;

import java.util.Comparator;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
    private final Stack<Board> result;
    private boolean solvable;
    private State target;

    private class State {
        public Board board;
        public State pre;
        public int move;
        public int priority;

        public State(final Board b, final State p, int m) {
            this.board = b;
            this.pre = p;
            this.move = m;
            this.priority = board.manhattan() + move;
        }

    }

    private MinPQ<State> getQueue() {
        return new MinPQ<State>(new Comparator<State>() {
            public int compare(State a, State b) {
                return Integer.compare(a.priority, b.priority);
            }
        });
    }

    private boolean handleQueue(MinPQ<State> queue) {
        State cur = queue.delMin();
        if (cur.board.isGoal()) {
            target = cur;
            return true;
        }

        // handle neighbors
        State pre = cur.pre;
        for (Board b : cur.board.neighbors()) {
            if (pre == null || !b.equals(pre.board)) {
                queue.insert(new State(b, cur, cur.move + 1));
            }
        }
        return false;
    }

    public Solver(final Board first) {
        if (first == null) {
            throw new java.lang.IllegalArgumentException();
        }

        // initialize
        result = new Stack<Board>();
        MinPQ<State> queue = getQueue();
        MinPQ<State> queue2 = getQueue();
        queue.insert(new State(first, null, 0));
        queue2.insert(new State(first.twin(), null, 0));

        while (true) {
            if (queue.isEmpty() || handleQueue(queue2)) {
                solvable = false;
                break;
            }else if (handleQueue(queue)) {
                solvable = true;
                for (State s = target; s != null; s = s.pre) {
                    result.push(s.board);
                }
                break;
            }
        }

    }

    public boolean isSolvable() {
        return solvable;
    }

    public int moves() {
        if (!isSolvable()) {
            return -1;
        }
        return result.size() - 1;
    }

    public Iterable<Board> solution() {
        if (!isSolvable()) {
            return null;
        }
        return result;
    }

    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }

}
