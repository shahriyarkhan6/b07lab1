public class Polynomial {
    // Field representing the coefficients of the polynomial
    private double[] coefficients;

    // No-argument constructor, sets the polynomial to zero
    public Polynomial() {
        this.coefficients = new double[]{0};
    }

    // Constructor that takes an array of doubles as an argument
    public Polynomial(double[] coefficients) {
        this.coefficients = coefficients.clone();  // Clone to prevent modification of the original array
    }

    // Method to add two polynomials
    public Polynomial add(Polynomial other) {
        int maxLength = Math.max(this.coefficients.length, other.coefficients.length);
        double[] result = new double[maxLength];

        // Adding corresponding coefficients
        for (int i = 0; i < maxLength; i++) {
            double thisCoeff = (i < this.coefficients.length) ? this.coefficients[i] : 0;
            double otherCoeff = (i < other.coefficients.length) ? other.coefficients[i] : 0;
            result[i] = thisCoeff + otherCoeff;
        }

        return new Polynomial(result);
    }

    // Method to evaluate the polynomial for a given value of x
    public double evaluate(double x) {
        double result = 0;
        for (int i = 0; i < coefficients.length; i++) {
            result += coefficients[i] * Math.pow(x, i);
        }
        return result;
    }

    // Method to check if a given value is a root of the polynomial
    public boolean hasRoot(double x) {
        return evaluate(x) == 0;
    }

    // Utility method to display the polynomial (optional, for testing)
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = coefficients.length - 1; i >= 0; i--) {
            if (coefficients[i] != 0) {
                if (i == 0) {
                    sb.append(coefficients[i]);
                } else if (i == 1) {
                    sb.append(coefficients[i]).append("x");
                } else {
                    sb.append(coefficients[i]).append("x^").append(i);
                }
                if (i > 0 && coefficients[i - 1] >= 0) {
                    sb.append(" + ");
                }
            }
        }
        return sb.toString();
    }
    
    // Main method for testing (optional)
    public static void main(String[] args) {
        Polynomial p1 = new Polynomial(new double[]{6, -2, 0, 5});  // 6 - 2x + 5x^3
        Polynomial p2 = new Polynomial(new double[]{1, 1});         // 1 + x

        // Testing addition
        Polynomial sum = p1.add(p2);
        System.out.println("Sum: " + sum);

        // Testing evaluation
        System.out.println("p1 evaluated at x = -1: " + p1.evaluate(-1));

        // Testing hasRoot
        System.out.println("p1 has root at x = 1: " + p1.hasRoot(1));
    }
}
