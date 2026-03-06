package project20280.exercises;

public class McCarthyTest {

    public static int M(int n){

        if(n > 100)
            return n - 10;

        return M(M(n + 11));
    }

    public static void main(String[] args){

        System.out.println("M(87) = " + M(87));

    }
}