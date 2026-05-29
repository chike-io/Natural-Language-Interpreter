import java.util.List;

/**
 * Parser class converts a list of tokens into an Abstract Syntax Tree (AST).
 * This represents the structured, semantic understanding of the input.
 */
public class Parser {
    private List<Token> tokens;
    private int currentPos;

    /**
     * Parses tokens into an AST
     * @param tokens The list of tokens from the tokenizer
     * @return The root node of the generated AST
     */
    public ASTNode parse(List<Token> tokens) {
        this.tokens = tokens;
        this.currentPos = 0;

        ASTNode root = new ASTNode("PROGRAM", "root", 0);

        // Parse multiple statements
        while (currentPos < tokens.size()) {
            ASTNode statement = parseStatement();
            if (statement != null) {
                root.addChild(statement);
            }
        }

        return root;
    }

    /**
     * Parse a single statement from tokens
     * @return An ASTNode representing a statement
     */
    private ASTNode parseStatement() {
        if (currentPos >= tokens.size()) {
            return null;
        }

        Token current = tokens.get(currentPos);

        // Check for assignment (IDENTIFIER = VALUE)
        if (current.getType().equals("IDENTIFIER") && 
            currentPos + 1 < tokens.size() && 
            tokens.get(currentPos + 1).getValue().equals("=")) {
            return parseAssignment();
        }

        // Check for print statement
        if (current.getType().equals("KEYWORD") && current.getValue().equals("print")) {
            return parsePrintStatement();
        }

        // Check for loop
        if (current.getType().equals("KEYWORD") && current.getValue().equals("for")) {
            return parseForLoop();
        }

        // Check for if statement
        if (current.getType().equals("KEYWORD") && current.getValue().equals("if")) {
            return parseIfStatement();
        }

        // Generic statement - consume the token
        ASTNode stmt = new ASTNode("STATEMENT", current.getValue(), 1);
        currentPos++;
        return stmt;
    }

    /**
     * Parse an assignment statement (var = value)
     * @return An ASTNode representing the assignment
     */
    private ASTNode parseAssignment() {
        Token varName = tokens.get(currentPos);
        ASTNode assignNode = new ASTNode("ASSIGNMENT", varName.getValue(), 1);

        currentPos++; // skip variable
        currentPos++; // skip =

        // Parse the right-hand side
        if (currentPos < tokens.size()) {
            Token rhsToken = tokens.get(currentPos);
            ASTNode rhs = new ASTNode("VALUE", rhsToken.getValue(), 2);
            assignNode.addChild(rhs);
            currentPos++;
        }

        return assignNode;
    }

    /**
     * Parse a print statement
     * @return An ASTNode representing the print statement
     */
    private ASTNode parsePrintStatement() {
        ASTNode printNode = new ASTNode("PRINT_STATEMENT", "print", 1);
        currentPos++; // skip 'print'

        // Collect all tokens until end as arguments
        StringBuilder args = new StringBuilder();
        while (currentPos < tokens.size() && 
               !tokens.get(currentPos).getValue().equals(";")) {
            args.append(tokens.get(currentPos).getValue()).append(" ");
            currentPos++;
        }

        ASTNode argNode = new ASTNode("ARGUMENTS", args.toString().trim(), 2);
        printNode.addChild(argNode);

        if (currentPos < tokens.size() && 
            tokens.get(currentPos).getValue().equals(";")) {
            currentPos++; // skip semicolon
        }

        return printNode;
    }

    /**
     * Parse a for loop statement
     * @return An ASTNode representing the for loop
     */
    private ASTNode parseForLoop() {
        ASTNode forNode = new ASTNode("FOR_LOOP", "for", 1);
        currentPos++; // skip 'for'

        // Simplified: collect all tokens until 'do' or similar
        StringBuilder condition = new StringBuilder();
        while (currentPos < tokens.size() && 
               !tokens.get(currentPos).getValue().toLowerCase().equals("do")) {
            condition.append(tokens.get(currentPos).getValue()).append(" ");
            currentPos++;
        }

        ASTNode condNode = new ASTNode("CONDITION", condition.toString().trim(), 2);
        forNode.addChild(condNode);

        if (currentPos < tokens.size()) {
            currentPos++; // skip 'do'
        }

        return forNode;
    }

    /**
     * Parse an if statement
     * @return An ASTNode representing the if statement
     */
    private ASTNode parseIfStatement() {
        ASTNode ifNode = new ASTNode("IF_STATEMENT", "if", 1);
        currentPos++; // skip 'if'

        // Collect condition tokens
        StringBuilder condition = new StringBuilder();
        while (currentPos < tokens.size() && 
               !tokens.get(currentPos).getValue().toLowerCase().equals("then")) {
            condition.append(tokens.get(currentPos).getValue()).append(" ");
            currentPos++;
        }

        ASTNode condNode = new ASTNode("CONDITION", condition.toString().trim(), 2);
        ifNode.addChild(condNode);

        if (currentPos < tokens.size()) {
            currentPos++; // skip 'then'
        }

        return ifNode;
    }
}
