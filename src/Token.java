/**
 * Token class represents a lexical unit from the tokenizer.
 * Each token has a type and value for semantic understanding.
 */
public class Token {
    private String type;
    private String value;
    private int position;

    /**
     * Constructor for Token
     * @param type The token type (KEYWORD, VARIABLE, NUMBER, OPERATOR, etc.)
     * @param value The actual value of the token
     * @param position Position in the original text
     */
    public Token(String type, String value, int position) {
        this.type = type;
        this.value = value;
        this.position = position;
    }

    // Getters
    public String getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    public int getPosition() {
        return position;
    }

    @Override
    public String toString() {
        return "Token[type=" + type + ", value=" + value + ", pos=" + position + "]";
    }
}
