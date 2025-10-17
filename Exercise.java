package com.code.service;

public class Exercise {

    public static void main(String args[]) {
        char[] input = {'a', 'a', 'a', 'b', 'b', 'b', 'b', 'c', 'd', 'd', 'd', 'a'};
        char[] input1 = {'a', 'b', 'c', 'd'};
        char[] input3 = {'a', 'a', 'a', 'b', 'b', 'b', 'b', 'c', 'd', 'd', 'd', 'd', 'd', 'd', 'd', 'd', 'd', 'd', 'd', 'd', 'd', 'd', 'd', 'd', 'd', 'd', 'd', 'd', 'd', 'd', 'd', 'd', 'd', 'd', 'd', 'd', 'd', 'd', 'd', 'd', 'd', 'd', 'd', 'd', 'd', 'd', 'd'};
        char[] input2 = {'a'};
        String outputString = encode(input);

        System.out.println(outputString);
    }

    public static String encode(char[] input) {
        if (input==null || input.length==0) return "";

        StringBuilder sb = new StringBuilder();
        int count = 1;
        int length = input.length;

        for (int i = 1; i <= length; i++) {
            if (i < length && input[i] == input[i - 1]) {
                count++;
            } else {
                sb.append(input[i - 1]).append(count);
                count = 1;
            }
        }
        return sb.toString();
    }
}
