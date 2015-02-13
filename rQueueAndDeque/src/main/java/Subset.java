public class Subset {

    public static void main(String[] args) {
        int k = Integer.valueOf(args[0]);
        RandomizedQueue<String> strings = new RandomizedQueue<String>();
        String[] input = StdIn.readAllStrings();
        for (int i = 0; i < k; i++) {
            int r;
            do {
                r = StdRandom.uniform(input.length);
            } while (input[r] == null);
            String output = input[r];
            input[r] = null;
            strings.enqueue(output);
        }
        
        for (String string : strings) {
            System.out.println(string);
        }
    }
}
