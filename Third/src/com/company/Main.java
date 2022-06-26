package com.company;

import java.math.BigInteger;

public class Main {

    public static void main(String[] args) {
        BigInteger num = factorial(100);
        String number = String.valueOf(num); // Convert to string
        int res = 0;
        for (int i = 0; i < number.length(); i++) {
            // We need to parse our string to Integer because number 648 fits in a range for this data type.
            res += Integer.parseInt(String.valueOf(number.charAt(i)));
        }
        System.out.println(res);
    }

    public static BigInteger factorial(long number) {
        BigInteger result = BigInteger.ONE;
        for (long i = 1; i <= number; i++) {
            result = result.multiply(BigInteger.valueOf(i));
        }
        return result;
    }
}
