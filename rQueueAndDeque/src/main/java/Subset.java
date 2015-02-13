public class Subset {

    public static void main(String[] args) {
        int k = Integer.valueOf(args[0]);
        RandomizedQueue<String> strings = new RandomizedQueue<String>();
        String[] input = StdIn.readAllStrings();
        for (String string : input) {
            strings.enqueue(string);
        }
        for (int i = 0; i < k; i++) {
            System.out.println(strings.dequeue());
        }
    }
}
