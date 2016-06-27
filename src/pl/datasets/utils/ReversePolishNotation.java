package pl.datasets.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Lukasz
 * @since 26.06.2016.
 */
public class ReversePolishNotation {

    private static final Operator ADD = new Operator() {
        @Override
        public double compute(double a, double b) {
            return a + b;
        }

        @Override
        public boolean matches(String s) {
            return s.equalsIgnoreCase("+");
        }
    };
    private static final Operator SUB = new Operator() {
        @Override
        public double compute(double a, double b) {
            return a - b;
        }

        @Override
        public boolean matches(String s) {
            return s.equalsIgnoreCase("-");
        }
    };
    private static final Operator MUL = new Operator() {
        @Override
        public double compute(double a, double b) {
            return a * b;
        }

        @Override
        public boolean matches(String s) {
            return s.equalsIgnoreCase("*");
        }
    };
    private static final Operator DIV = new Operator() {
        @Override
        public double compute(double a, double b) {
            return a / b;
        }

        @Override
        public boolean matches(String s) {
            return s.equalsIgnoreCase("/");
        }
    };
    private static Operator[] operators = new Operator[]{ADD, MUL, SUB, DIV};

    public static void main(String[] args) {
        double res = new ReversePolishNotation().compute("12 2 3 4 * 10 5 / + * +");
        System.out.println("Result: " + res);
    }

    private double compute(String input) {
        Stack<String> stack = new Stack<>();
        String[] items = input.split(" ");
        int i = 0;
        while (i < items.length) {
            if (items[i].matches("[0-9]*")) {
                stack.push(items[i]);
            } else {
                Operator op = getMatchingOperator(items[i]);
                double d2 = Double.valueOf(stack.pop());
                double d1 = Double.valueOf(stack.pop());
                double resultOfOperation = op.compute(d1, d2);
                stack.push(String.valueOf(resultOfOperation));
            }
            i++;
        }
        return Double.valueOf(stack.pop());
    }

    private Operator getMatchingOperator(String operator) {
        for (Operator op : operators) if (op.matches(operator)) return op;
        throw new IllegalArgumentException("Cannot find matching operator for " + operator);
    }

    interface Operator {
        double compute(double a, double b);

        boolean matches(String s);
    }

    private static class Stack<T> {
        private List<T> stack = new ArrayList<>();

        void push(T t) {
            stack.add(t);
        }

        T pop() {
            int index = stack.size() - 1;
            T last = stack.get(index);
            stack.remove(index);
            return last;
        }
    }
}
