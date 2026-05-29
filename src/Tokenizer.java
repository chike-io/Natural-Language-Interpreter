import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Tokenizer class acts as a lexer that converts natural language input
 * into a stream of tokens for further processing.
 */
public class Tokenizer {
    private static final Set<String> KEYWORDS = new HashSet<>(Arrays.asList(
        "create", "variable", "assign", "print", "loop", "function", "if", 
        "else", "return", "for", "while", "class", "import", "public", "private"
    ));

    private static final Set<String> OPERATORS = new HashSet<>(Arrays.asList(
        "=", "+", "-", "*", "/", "==", "!=", "<", ">", "<=", ">=", "&&", "||"
    ));

    /**
     * Tokenizes the input string into a list of tokens
     * @param input The natural language input string
     * @return List of tokens extracted from the input
     */
    public List<Token> tokenize(String input) {
        List<Token> tokens = new ArrayList<>();
        String[] words = input.split("\\s+");
        int position = 0;

        for (String word : words) {
            // Check for keywords
            if (KEYWORDS.contains(word.toLowerCase())) {
                tokens.add(new Token("KEYWORD", word, position));
            }
            // Check for operators
            else if (OPERATORS.contains(word)) {
                tokens.add(new Token("OPERATOR", word, position));
            }
            // Check for numbers
            else if (word.matches("\\d+(\\.\\d+)?")) {
                tokens.add(new Token("NUMBER", word, position));
            }
            // Check for strings (quoted)
            else if (word.startsWith("\"") && word.endsWith("\"")) {
                tokens.add(new Token("STRING", word, position));
            }
            // Everything else is an identifier/variable
            else {
                tokens.add(new Token("IDENTIFIER", word, position));
            }

            position++;
        }

        return tokens;
    }
}
