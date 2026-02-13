package project20280.stacksqueues;

import project20280.interfaces.Stack;

public class BaseConverter {

    public static String convertToBinary(long dec_num) {
        return convert(dec_num, 2);
    }

    public static String convert(long dec_num, int base) {

        if (base < 2 || base > 9) {
            throw new IllegalArgumentException("Base must be between 2 and 9");
        }

        if (dec_num == 0) return "0";

        Stack<Long> stack = new ArrayStack<>();

        while (dec_num > 0) {
            stack.push(dec_num % base);
            dec_num /= base;
        }

        StringBuilder sb = new StringBuilder();

        while (!stack.isEmpty()) {
            sb.append((char) ('0' + stack.pop().intValue()));
        }

        return sb.toString();
    }

    public static void main(String[] args) {

        System.out.println("Binary tests:");
        System.out.println("23 -> " + convertToBinary(23));  // 10111

        System.out.println("\nOther bases:");
        System.out.println("23 base 8 -> " + convert(23, 8));  // 27
        System.out.println("23 base 3 -> " + convert(23, 3));  // 212
        System.out.println("10 base 2 -> " + convert(10, 2));  // 1010
    }
}


