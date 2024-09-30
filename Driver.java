import java.io.File;
import java.io.IOException;

public class Driver {
    public static void main(String[] args) {
        // Test Polynomial creation using arrays of coefficients and exponents
        double[] coeffs1 = {6, -2, 5};  // Non-zero coefficients
        int[] exps1 = {0, 1, 3};        // Corresponding exponents for the polynomial 6 - 2x + 5x^3
        Polynomial p1 = new Polynomial(coeffs1, exps1);
        
        double[] coeffs2 = {1, 1};      // Non-zero coefficients
        int[] exps2 = {0, 1};           // Corresponding exponents for the polynomial 1 + x
        Polynomial p2 = new Polynomial(coeffs2, exps2);

        // Test addition
        Polynomial sum = p1.add(p2);
        System.out.println("Sum of p1 and p2: " + sum);

        // Test multiplication
        Polynomial product = p1.multiply(p2);
        System.out.println("Product of p1 and p2: " + product);

        // Test evaluation
        double result = p1.evaluate(2);  // Evaluate p1 at x = 2
        System.out.println("p1(2) = " + result);

        // Test root check
        boolean hasRoot = p1.hasRoot(1);
        System.out.println("p1 has root at x = 1: " + hasRoot);

        // Test file-based constructor and saveToFile method
        try {
            Polynomial p3 = new Polynomial(new File("polynomial.txt"));  // Assuming a valid file format
            System.out.println("Polynomial loaded from file: " + p3);

            p3.saveToFile("outputPolynomial.txt");
            System.out.println("Polynomial saved to outputPolynomial.txt");
        } catch (IOException e) {
            System.out.println("File I/O error: " + e.getMessage());
        }
    }
}
