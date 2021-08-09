import edu.princeton.cs.algs4.StdIn;

public class Permutation {

    public static void main(String[] args) {
        RandomizedQueue<String> randoQue = new RandomizedQueue<String>();
        // k is the number of strings we will print
        int k = Integer.parseInt(args[0]);
        try {
            String input = StdIn.readString();
            randoQue.enqueue(input);
        } catch (java.util.NoSuchElementException e) {
            // the try-catch is used to handel the exception that StdIn.readString() throws.
            // !!! Maybe a better way to do is to find the length of the input using args.length?
            // Wouldn't that tell us n? !!!
        }
        for (int i = 0; i < k; i++) {
            System.out.println(randoQue.dequeue());
        }

    }


}

























