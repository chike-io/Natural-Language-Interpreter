import java.util.ArrayList;
import java.util.List;

/**
 * ExecutionGuardrail validates generated code for simple syntax issues
 * and potential runtime errors before execution.
 */
public class ExecutionGuardrail {
    private List<String> validationErrors;
    private List<String> validationWarnings;

    public ExecutionGuardrail() {
        this.validationErrors = new ArrayList<>();
        this.validationWarnings = new ArrayList<>();
    }

    /**
     * Validate the generated code for syntax and semantic issues
     * @param pseudoCode The pseudo-code to validate
     * @return true if validation passes, false otherwise
     */
    public boolean validate(String pseudoCode) {
        validationErrors.clear();
        validationWarnings.clear();

        String[] lines = pseudoCode.split("\n");

        // Track open braces for matching
        int openBraces = 0;
        int openParens = 0;

        for (int i = 0; i < lines.length; i++) {
            String line = lines[i].trim();

            // Skip comments
            if (line.startsWith("//")) {
                continue;
            }

            // Check for matching braces
            openBraces += countChar(line, '{');
            openBraces -= countChar(line, '}');

            // Check for matching parentheses
            openParens += countChar(line, '(');
            openParens -= countChar(line, ')');

            // Validate variable names
            if (line.contains("int ")) {
                String varName = extractVariableName(line);
                if (!isValidIdentifier(varName)) {
                    validationErrors.add("Line " + (i + 1) + 
                        ": Invalid variable name: " + varName);
                }
            }

            // Check for missing semicolons
            if ((line.contains("=") || line.contains("System.out.println")) && 
                !line.endsWith(";") && !line.endsWith("{")) {
                validationWarnings.add("Line " + (i + 1) + 
                    ": Missing semicolon");
            }

            // Check for undefined functions
            if (line.contains("(") && !line.contains("System.out") && 
                !line.contains("for") && !line.contains("if")) {
                validationWarnings.add("Line " + (i + 1) + 
                    ": Potential undefined function call");
            }
        }

        // Check for unmatched braces/parentheses at end
        if (openBraces != 0) {
            validationErrors.add("Unmatched braces: " + openBraces);
        }
        if (openParens != 0) {
            validationErrors.add("Unmatched parentheses: " + openParens);
        }

        return validationErrors.isEmpty();
    }

    /**
     * Count occurrences of a character in a string
     * @param str The string to search
     * @param ch The character to count
     * @return The count of the character
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
     * Extract variable name from a declaration line
     * @param line The line containing the variable declaration
     * @return The variable name
     */
    private String extractVariableName(String line) {
        if (line.contains("=")) {
            String[] parts = line.split("=");
            String[] nameParts = parts[0].split("\\s+");
            if (nameParts.length >= 2) {
                return nameParts[nameParts.length - 1];
            }
        }
        return "";
    }

    /**
     * Check if a string is a valid Java identifier
     * @param name The name to check
     * @return true if valid, false otherwise
     */
    private boolean isValidIdentifier(String name) {
        if (name.isEmpty()) {
            return false;
        }
        if (!Character.isJavaIdentifierStart(name.charAt(0))) {
            return false;
        }
        for (char c : name.toCharArray()) {
            if (!Character.isJavaIdentifierPart(c)) {
                return false;
            }
        }
        return true;
    }

    public List<String> getErrors() {
        return validationErrors;
    }

    public List<String> getWarnings() {
        return validationWarnings;
    }

    /**
     * Print validation results
     */
    public void printResults() {
        if (validationErrors.isEmpty() && validationWarnings.isEmpty()) {
            System.out.println("✓ Validation passed!");
        } else {
            if (!validationErrors.isEmpty()) {
                System.out.println("\n❌ Errors:");
                for (String error : validationErrors) {
                    System.out.println("  - " + error);
                }
            }
            if (!validationWarnings.isEmpty()) {
                System.out.println("\n⚠️  Warnings:");
                for (String warning : validationWarnings) {
                    System.out.println("  - " + warning);
                }
            }
        }
    }
}
