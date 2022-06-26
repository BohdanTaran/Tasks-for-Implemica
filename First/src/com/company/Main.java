package com.company;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        // Variables
        int n_of_brackets = 0;
        int count_of_expressions = 0;
        int count_of_success_expressions = 0;

        String expression = "";

        // Create Scanner for input
        Scanner sc = new Scanner(System.in);

        // Input of user
        while (n_of_brackets <= 0) {
            System.out.print("Enter N of brackets (bigger than 0): ");
            n_of_brackets = sc.nextInt();
        }
        while (count_of_expressions <= 0) {
            System.out.print("Enter count of expressions: ");
            count_of_expressions = sc.nextInt();
        }

        System.out.println("Enter expression(s): ");
        for (int i = 0; i <= count_of_expressions; i++) {
            boolean isRight = false; // Variable for check validation of expression
            expression = sc.nextLine();
            if (checkBrackets(expression, n_of_brackets)) {
                if (funcLogic(expression, isRight)) {
                    count_of_success_expressions++;
                }
            }
        }

        System.out.println("Success brackets = " + count_of_success_expressions);
    }

    // Function for check on count of brackets which entered user
    public static boolean checkBrackets(String expression, int n_of_brackets) {
        boolean isOk = false;
        int count_brackets = 0;

        // Loop for count '('
        for (int i = 0; i < expression.length(); i++) {
            if (expression.charAt(i) == '(') count_brackets++;
        }
        if (count_brackets == n_of_brackets) { // If count_brackets == n_of_brackets (Quantity closed and opened brackets)
            count_brackets = 0;
            // Loop for count ')'
            for (int i = 0; i < expression.length(); i++) {
                if (expression.charAt(i) == ')') count_brackets++;
            }
        }

        // If our result from loops == n_of_brackets we return TRUE
        if (count_brackets == n_of_brackets) isOk = true;

        return isOk;
    }

    // Function with main logic
    public static boolean funcLogic(String expression, boolean isRight) {
        int count = 0;
        for (int k = 0; k < expression.length(); k++) {
            // Goal of this loop - started from opened brackets in order to find closed and compare count
            if (expression.charAt(k) == '(') {
                count++;
                // We start from k position in order to find closed bracket
                for (int h = k; h < expression.length(); h++) {
                    if (expression.charAt(h) == ')') {
                        count--;
                        break;
                    }
                }
            } else { // If we started from closed bracket - pass this iteration
                break;
            }
            if (count == 0) isRight = true;
        }
        return isRight;
    }
}
