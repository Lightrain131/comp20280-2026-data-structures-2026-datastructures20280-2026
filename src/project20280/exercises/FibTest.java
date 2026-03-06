package project20280.exercises;

public class FibTest {

    // count recursive calls
    static long calls = 0;

    // naive recursive Fibonacci
    public static long fib(int n) {

        calls++;

        if (n <= 1)
            return n;

        return fib(n - 1) + fib(n - 2);
    }

    // memoisation storage
    static long[] memo = new long[100000];

    // memoised Fibonacci
    public static long fibMemo(int n) {

        if (n <= 1)
            return n;

        if (memo[n] != 0)
            return memo[n];

        memo[n] = fibMemo(n - 1) + fibMemo(n - 2);

        return memo[n];
    }

    public static void main(String[] args) {

        System.out.println("=== Fibonacci recursion test ===");

        // ----------------------------
        // Test 1: Fibonacci(5)
        // ----------------------------
        calls = 0;
        long result = fib(5);

        System.out.println("fib(5) = " + result);
        System.out.println("recursive calls = " + calls);


        // ----------------------------
        // Test 2: naive recursion speed test (1 minute)
        // ----------------------------
        System.out.println("\n=== Naive recursion 1 minute test ===");

        long start = System.currentTimeMillis();
        int n = 0;

        while (true) {

            fib(n);

            if (System.currentTimeMillis() - start > 60000)
                break;

            n++;
        }

        System.out.println("largest n computed in 1 minute (naive recursion) = " + (n - 1));


        // ----------------------------
        // Test 3: memoisation speed test (1 minute)
        // ----------------------------
        System.out.println("\n=== Memoisation 1 minute test ===");

        start = System.currentTimeMillis();
        n = 0;

        while (true) {

            fibMemo(n);

            if (System.currentTimeMillis() - start > 60000)
                break;

            n++;
        }

        System.out.println("largest n computed in 1 minute (memoisation) = " + (n - 1));

    }
}