import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
    public static void main(String[] args) {
        assert (args.length == 1);
        int k = Integer.parseInt(args[0]);

        RandomizedQueue<String> randQueue = new RandomizedQueue<>();
        while (!StdIn.isEmpty()) {
            randQueue.enqueue(StdIn.readString());
        }

        for (String s : randQueue) {
            if (k <= 0) break;
            StdOut.println(s);
            k--;
        }
    }
}
