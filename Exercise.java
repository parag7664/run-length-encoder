package com.code.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.counting;

public class Exercise {

    public static void main(String args[]) {
        char[] input = {'a', 'a', 'a', 'b', 'b', 'b', 'b', 'c', 'd', 'd', 'd', 'a'}; //a4b4c1d3
        char[] input1 = {'a', 'b', 'c', 'd'};
        char[] input3 = {'a', 'a', 'a', 'b', 'b', 'b', 'b', 'c', 'd', 'd', 'd', 'd', 'd', 'd', 'd', 'd', 'd', 'd', 'd', 'd', 'd', 'd', 'd', 'd', 'd', 'd', 'd', 'd', 'd', 'd', 'd', 'd', 'd', 'd', 'd', 'd', 'd', 'd', 'd', 'd', 'd', 'd', 'd', 'd', 'd', 'd', 'd'};
        char[] input2 = {'a'};
        String outputString = encode(input);
        System.out.println(outputString);

        String in = "aaabbbccaddeekkkkiii";
        String outputString1 = encodeString(in);
        //System.out.println(outputString1);
        String outputString2 = streamJava(in);
        //System.out.println(outputString2);


        String encoded = "aaabbacc";
        String decoded = encodeHashmap(encoded);
        //System.out.println(decoded);

        char[] arr = {'a', 'a', 'b', 'b', 'b', 'c'};
        int newLen = encodeInPlace(input);
        System.out.println(new String(input, 0, newLen));

        int decodedLen = decodeInPlace(input, newLen);
        System.out.println(java.util.Arrays.toString(java.util.Arrays.copyOf(input, decodedLen))); // Output: [a, a, b, b, b, c]

        String longInputEncodedResult = encodeForLongInput(input);
        System.out.println(longInputEncodedResult);
    }

    //O(n) O(n)
    public static String encode(char[] input) {
        if (input==null || input.length==0) return "";

        StringBuilder sb = new StringBuilder();
        //int count = 1;
        int length = input.length;

        for (int i = 1,count = 1; i <= length; i++) {
            if (i < length && input[i] == input[i - 1]) {
                count++;
            } else {
                sb.append(input[i - 1]).append(count);
                count = 1;
            }
        }
        return sb.toString();
    }

    public static String encodeString(String input) {
        if (input==null || input.isEmpty()) return "";
        String encoded = "";

        for(int i=0, count =1; i<input.length();i++) {
            if(i+1 < input.length() && input.charAt(i) == input.charAt(i+1)) {
                count++;
            } else {
                encoded  = encoded.concat(String.valueOf(input.charAt(i)).concat(Integer.toString(count)));
                count = 1;
            }
        }
        return encoded;
    }

    public static String streamJava(String arr) {
        return Arrays.stream(arr.split(""))
                .collect(Collectors.groupingBy(Function.identity(), LinkedHashMap::new, counting()))
                .entrySet()
                .stream().map(e -> e.getKey() + e.getValue())
                .collect(Collectors.joining(""));

    }

    public static char[] decode(String input) {
        if (input==null || input.isEmpty()) return new char[0];
        StringBuilder decoded = new StringBuilder();
        int i =0;
        while (i < input.length()) {
            char ch = input.charAt(i++);
            //int count = input.charAt(i) - '0';
            int count =0;

            while (i<input.length() && Character.isDigit(input.charAt(i))) {
                count = count * 10 + (input.charAt(i++) - '0');
            }
            for (int j = 0; j < count; j++) {
                decoded.append(ch);
            }
        }
        char[] result = new char[decoded.length()];
        decoded.getChars(0,decoded.length(),result,0);
        return result;
    }

    public static String encodeHashmap(String s) {
        if (s == null || s.isEmpty()) return "";

        Map<Character, Integer> freq = new LinkedHashMap<>(); // preserves order
        for (char c : s.toCharArray()) {
            freq.put(c, freq.getOrDefault(c, 0) + 1);
        }

        StringBuilder sb = new StringBuilder();
        for (Map.Entry<Character, Integer> e : freq.entrySet()) {
            sb.append(e.getKey());
            if (e.getValue() > 1) sb.append(e.getValue());
        }
        return sb.toString();
    }

    public static String encodeHashmapArray(char[] chars) {
        if (chars == null || chars.length == 0) return "";

        Map<Character, Integer> freq = new LinkedHashMap<>();
        for (char c : chars) {
            freq.put(c, freq.getOrDefault(c, 0) + 1);
        }

        StringBuilder sb = new StringBuilder();
        for (Map.Entry<Character, Integer> e : freq.entrySet()) {
            sb.append(e.getKey());
            if (e.getValue() > 1) sb.append(e.getValue());
        }
        return sb.toString();
    }

    public static int encodeInPlace(char[] input) {
        if (input == null || input.length == 0) return 0;

        int writeIdx = 0, count = 1;
        for (int readIdx = 1; readIdx <= input.length; readIdx++) {
            if (readIdx < input.length && input[readIdx] == input[readIdx - 1]) {
                count++;
            } else {
                // Write character
                input[writeIdx++] = input[readIdx - 1];
                // Write count (as chars)
                char[] countChars = Integer.toString(count).toCharArray();
                if (writeIdx + countChars.length > input.length) return -1; // Not enough space
                for (char c : countChars) input[writeIdx++] = c;
                count = 1;
            }
        }
        return writeIdx;
    }

    public static int decodeInPlace(char[] input, int encodedLength) {
        if (input == null || encodedLength == 0) return 0;

        char[] temp = new char[input.length];
        int readIdx = 0, writeIdx = 0;
        while (readIdx < encodedLength) {
            char ch = input[readIdx++];
            int count = 0;
            // Read count
            while (readIdx < encodedLength && Character.isDigit(input[readIdx])) {
                count = count * 10 + (input[readIdx++] - '0');
            }
            if (writeIdx + count > input.length) return -1; // Not enough space
            for (int j = 0; j < count; j++) temp[writeIdx++] = ch;
        }
        // Copying decoded data back
        System.arraycopy(temp, 0, input, 0, writeIdx);
        return writeIdx;
    }

    private static final int MAX_RUN = 999_999_999; // For 9-digit counts per run

    /**
     * splitting runs longer than MAX_RUN into multiple chunks.
     * Example: For 1_000_000_001 'a' chars, output will be "a999999999a1".
     */
    public static String encodeForLongInput(char[] input) {
        if (input == null || input.length == 0) return "";

        StringBuilder encoded = new StringBuilder();
        long count = 1;
        for (int i = 1; i < input.length; i++) {
            if (input[i] == input[i - 1]) {
                count++;
                if (count > MAX_RUN) {
                    encoded.append(input[i - 1]).append(MAX_RUN);
                    count = 1;
                }
            } else {
                encoded.append(input[i - 1]).append(count);
                count = 1;
            }
        }
        encoded.append(input[input.length - 1]).append(count);
        return encoded.toString();
    }
}
