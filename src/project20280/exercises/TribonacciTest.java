package project20280.exercises;

public class TribonacciTest {
    public static int trib(int n){

        if(n == 0) return 0;
        if(n == 1) return 0;
        if(n == 2) return 1;

        return trib(n-1) + trib(n-2) + trib(n-3);
    }

    public static void main(String[] args){

        System.out.println("T(9) = " + trib(9));

    }
}
