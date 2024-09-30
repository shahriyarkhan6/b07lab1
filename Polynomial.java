import java.io.*;
import java.util.*;

public class Polynomial {
    private double[] coefficients;  // Stores non-zero coefficients
    private int[] exponents;        // Stores corresponding exponents

    // No-argument constructor, sets the polynomial to zero
    public Polynomial() {
        this.coefficients = new double[]{0};
        this.exponents = new int[]{0};
    }

    // Constructor that takes arrays of coefficients and exponents
    public Polynomial(double[] coefficients, int[] exponents) {
        this.coefficients = coefficients.clone();
        this.exponents = exponents.clone();
    }

    // Constructor that takes a File and initializes the polynomial based on its contents
    public Polynomial(File file) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line = br.readLine();
        br.close();

        // Parse the polynomial from the string (e.g., "5-3x2+7x8")
        List<Double> coeffList = new ArrayList<>();
        List<Integer> expoList = new ArrayList<>();

        // Regular expression to parse terms like 5, -3x2, +7x8
        String[] terms = line.split("(?=[+-])");
        for (String term : terms) {
            term = term.replace("x^", "x");  // Handle powers with '^'
            if (term.contains("x")) {
                String[] parts = term.split("x");
                double coefficient = parts[0].isEmpty() ? 1 : Double.parseDouble(parts[0]);
                int exponent = parts.length > 1 ? Integer.parseInt(parts[1]) : 1;
                coeffList.add(coefficient);
                expoList.add(exponent);
            } else {
                coeffList.add(Double.parseDouble(term));
                expoList.add(0);  // Constant term
            }
        }

        this.coefficients = coeffList.stream().mapToDouble(d -> d).toArray();
        this.exponents = expoList.stream().mapToInt(i -> i).toArray();
    }

    // Method to add two polynomials
    public Polynomial add(Polynomial other) {
        Map<Integer, Double> resultMap = new HashMap<>();

        // Add terms from this polynomial
        for (int i = 0; i < this.coefficients.length; i++) {
            resultMap.put(this.exponents[i], this.coefficients[i]);
        }

        // Add terms from the other polynomial
        for (int i = 0; i < other.coefficients.length; i++) {
            resultMap.put(other.exponents[i], resultMap.getOrDefault(other.exponents[i], 0.0) + other.coefficients[i]);
        }

        // Convert result map to arrays
        double[] resultCoeffs = resultMap.values().stream().mapToDouble(d -> d).toArray();
        int[] resultExponents = resultMap.keySet().stream().mapToInt(i -> i).toArray();

        return new Polynomial(resultCoeffs, resultExponents);
    }

    // Method to multiply two polynomials
    public Polynomial multiply(Polynomial other) {
        Map<Integer, Double> resultMap = new HashMap<>();

        // Multiply terms from both polynomials
        for (int i = 0; i < this.coefficients.length; i++) {
            for (int j = 0; j < other.coefficients.length; j++) {
                int newExponent = this.exponents[i] + other.exponents[j];
                double newCoefficient = this.coefficients[i] * other.coefficients[j];
                resultMap.put(newExponent, resultMap.getOrDefault(newExponent, 0.0) + newCoefficient);
            }
        }

        // Convert result map to arrays
        double[] resultCoeffs = resultMap.values().stream().mapToDouble(d -> d).toArray();
        int[] resultExponents = resultMap.keySet().stream().mapToInt(i -> i).toArray();

        return new Polynomial(resultCoeffs, resultExponents);
    }

    // Method to evaluate the polynomial for a given value of x
    public double evaluate(double x) {
        double result = 0;
        for (int i = 0; i < coefficients.length; i++) {
            result += coefficients[i] * Math.pow(x, exponents[i]);
        }
        return result;
    }

    // Method to save the polynomial to a file
    public void saveToFile(String fileName) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));

        for (int i = 0; i < coefficients.length; i++) {
            if (i > 0 && coefficients[i] >= 0) {
                writer.write("+");
            }
            writer.write(coefficients[i] + (exponents[i] == 0 ? "" : "x^" + exponents[i]));
        }
        writer.close();
    }

    // Utility method to display the polynomial as a string
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < coefficients.length; i++) {
            if (i > 0 && coefficients[i] >= 0) {
                sb.append("+");
            }
            sb.append(coefficients[i]);
            if (exponents[i] != 0) {
                sb.append("x^").append(exponents[i]);
            }
        }
        return sb.toString();
    }

    // Main method for testing
    public static void main(String[] args) throws IOException {
        Polynomial p1 = new Polynomial(new double[]{6, -2, 5}, new int[]{0, 1, 3});  // 6 - 2x + 5x^3
        Polynomial p2 = new Polynomial(new double[]{1, 1}, new int[]{0, 1});         // 1 + x

        // Testing addition
        Polynomial sum = p1.add(p2);
        System.out.println("Sum: " + sum);

        // Testing multiplication
        Polynomial product = p1.multiply(p2);
        System.out.println("Product: " + product);

        // Testing evaluation
        System.out.println("p1 evaluated at x = 1: " + p1.evaluate(1));

        // Save to file and load from file
        p1.saveToFile("polynomial.txt");
        Polynomial pFromFile = new Polynomial(new File("polynomial.txt"));
        System.out.println("Loaded from file: " + pFromFile);
    }
}
