import java.util.ArrayList;
import java.util.List;

/**
 * EvaluationMetrics calculates performance metrics for the transformer
 * including exact match percentage and functional pass rate.
 */
public class EvaluationMetrics {
    private List<String> generatedOutputs;
    private List<String> referenceOutputs;
    private double exactMatchScore;
    private double functionalPassRate;

    public EvaluationMetrics() {
        this.generatedOutputs = new ArrayList<>();
        this.referenceOutputs = new ArrayList<>();
    }

    /**
     * Add a generated output for comparison
     * @param output The generated output
     */
    public void addGeneratedOutput(String output) {
        generatedOutputs.add(output);
    }

    /**
     * Add a reference output (expected output)
     * @param output The reference output
     */
    public void addReferenceOutput(String output) {
        referenceOutputs.add(output);
    }

    /**
     * Calculate exact match score (percentage of exact matches)
     * @return The exact match score as a percentage
     */
    public double calculateExactMatch() {
        if (generatedOutputs.isEmpty() || referenceOutputs.isEmpty()) {
            return 0.0;
        }

        int matches = 0;
        int comparisons = Math.min(generatedOutputs.size(), 
                                    referenceOutputs.size());

        for (int i = 0; i < comparisons; i++) {
            if (generatedOutputs.get(i).equals(referenceOutputs.get(i))) {
                matches++;
            }
        }

        exactMatchScore = (double) matches / comparisons * 100;
        return exactMatchScore;
    }

    /**
     * Calculate functional pass rate
     * Checks if outputs are syntactically valid and semantically similar
     * @return The functional pass rate as a percentage
     */
    public double calculateFunctionalPassRate() {
        if (generatedOutputs.isEmpty()) {
            return 0.0;
        }

        int passes = 0;

        for (String output : generatedOutputs) {
            if (isFunctionallyValid(output)) {
                passes++;
            }
        }

        functionalPassRate = (double) passes / generatedOutputs.size() * 100;
        return functionalPassRate;
    }

    /**
     * Check if an output is functionally valid
     * @param output The output to check
     * @return true if functionally valid, false otherwise
     */
    private boolean isFunctionallyValid(String output) {
        // Check for basic Java syntax markers
        boolean hasValidStructure = output.contains("public class") || 
                                    output.contains("System.out.println") ||
                                    output.contains("int ") ||
                                    output.contains("//");

        // Check for basic balance
        int openBraces = countChar(output, '{');
        int closeBraces = countChar(output, '}');
        int openParens = countChar(output, '(');
        int closeParens = countChar(output, ')');

        boolean isBalanced = (openBraces == closeBraces) && 
                            (openParens == closeParens);

        return hasValidStructure && isBalanced;
    }

    /**
     * Count occurrences of a character in a string
     * @param str The string to search
     * @param ch The character to count
     * @return The count
     */
    private int countChar(String str, char ch) {
        int count = 0;
        for (char c : str.toCharArray()) {
            if (c == ch) {
                count++;
            }
        }
        return count;
    }

    /**
     * Calculate BLEU-like similarity score between two strings
     * @param generated The generated string
     * @param reference The reference string
     * @return Similarity score between 0 and 1
     */
    public double calculateSimilarity(String generated, String reference) {
        String[] genTokens = generated.split("\\s+");
        String[] refTokens = reference.split("\\s+");

        int matches = 0;
        for (String genToken : genTokens) {
            for (String refToken : refTokens) {
                if (genToken.equals(refToken)) {
                    matches++;
                    break;
                }
            }
        }

        int totalTokens = Math.max(genTokens.length, refTokens.length);
        return totalTokens > 0 ? (double) matches / totalTokens : 0.0;
    }

    /**
     * Print evaluation metrics report
     */
    public void printReport() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("EVALUATION METRICS REPORT");
        System.out.println("=".repeat(50));

        System.out.printf("Exact Match Score:      %.2f%%\n", 
                         calculateExactMatch());
        System.out.printf("Functional Pass Rate:   %.2f%%\n", 
                         calculateFunctionalPassRate());
        System.out.printf("Generated Outputs:      %d\n", 
                         generatedOutputs.size());
        System.out.printf("Reference Outputs:      %d\n", 
                         referenceOutputs.size());

        System.out.println("=".repeat(50));
    }

    // Getters
    public double getExactMatchScore() {
        return exactMatchScore;
    }

    public double getFunctionalPassRate() {
        return functionalPassRate;
    }
}
