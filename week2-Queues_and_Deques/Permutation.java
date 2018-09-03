package week2;

import java.util.Iterator;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {

    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> queue = new RandomizedQueue<String>();
        while(!StdIn.isEmpty()){
            for (String word : StdIn.readString().split(" ")) {
                queue.enqueue(word);
            }
        }
        Iterator<String> iter = queue.iterator();
        for(int i=0; i<k; i++){
           StdOut.println(iter.next());
        }
    }

}
