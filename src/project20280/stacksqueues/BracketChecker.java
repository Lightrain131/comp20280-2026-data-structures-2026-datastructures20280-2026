package project20280.stacksqueues;

import project20280.interfaces.Stack;
import java.util.*;

class BracketChecker {
    private final String input;

    public BracketChecker(String in) {
        input = in;
    }

    public void check() {
        Stack<Character> stack = new ArrayStack<>();

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);

            if (isLeft(c)) {
                stack.push(c);
            }
            else if (isRight(c)) {

                if (stack.isEmpty()) {
                    System.out.println("Missing left delimiter before '" + c + "' at index " + i);
                    System.out.println("Result: NOT balanced");
                    return;
                }

                char left = stack.pop();

                if (!matches(left, c)) {
                    System.out.println("Mismatched delimiters: '" + left + "' does not match '" + c
                            + "' at index " + i);
                    System.out.println("Result: NOT balanced");
                    return;
                }
            }
        }

        if (!stack.isEmpty()) {
            System.out.println("Missing right delimiter(s). Unmatched left delimiter(s): " + stack);
            System.out.println("Result: NOT balanced");
            return;
        }

        System.out.println("Result: balanced");
    }

    private boolean isLeft(char c) {
        return c == '(' || c == '[' || c == '{';
    }

    private boolean isRight(char c) {
        return c == ')' || c == ']' || c == '}';
    }

    private boolean matches(char left, char right) {
        return (left == '(' && right == ')')
                || (left == '[' && right == ']')
                || (left == '{' && right == '}');
    }

    public static void main(String[] args) {
        String[] inputs = {
                "[]]()()", // not correct
                "c[d]", // correct\n" +
                "a{b[c]d}e", // correct\n" +
                "a{b(c]d}e", // not correct; ] doesn't match (\n" +
                "a[b{c}d]e}", // not correct; nothing matches final }\n" +
                "a{b(c) ", // // not correct; Nothing matches opening {
                "{[()]}",
                "{[(])}",
                "{{[[(())]]}}",
                "][]][][[]][]][][[[",
                "(((abc))((d)))))"
        };

        for (String input : inputs) {
            BracketChecker checker = new BracketChecker(input);
            System.out.println("checking: " + input);
            checker.check();
            System.out.println();
        }

        Scanner sc = new Scanner(System.in);
        System.out.print("Enter expression: ");
        String input = sc.nextLine();
        new BracketChecker(input).check();

    }
}