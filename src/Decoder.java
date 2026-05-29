import java.util.List;
import java.util.Map;

/**
 * Decoder class generates pseudo-code or Java-like output from
 * the processed AST and attention information.
 */
public class Decoder {

    /**
     * Decode the processed transformer output into pseudo-code
     * @param processedData The map of processed data from the transformer
     * @return Generated pseudo-code as a string
     */
    @SuppressWarnings("unchecked")
    public String decode(Map<String, Object> processedData) {
        StringBuilder pseudoCode = new StringBuilder();

        List<ASTNode> nodes = (List<ASTNode>) processedData.get("nodes");
        List<Double> attentionScores = (List<Double>) processedData.get("attention_scores");

        pseudoCode.append("// Generated Pseudo-Code\n");
        pseudoCode.append("// ========================\n\n");

        // Decode each node based on its type
        for (int i = 0; i < nodes.size(); i++) {
            ASTNode node = nodes.get(i);
            double attention = i < attentionScores.size() ? 
                               attentionScores.get(i) : 0.0;

            // Skip root node
            if (node.getNodeType().equals("PROGRAM")) {
                continue;
            }

            String decoded = decodeNode(node, attention);
            if (!decoded.isEmpty()) {
                pseudoCode.append(decoded).append("\n");
            }
        }

        return pseudoCode.toString();
    }

    /**
     * Decode a single AST node into pseudo-code
     * @param node The AST node
     * @param attention The attention score for this node
     * @return The decoded pseudo-code string
     */
    private String decodeNode(ASTNode node, double attention) {
        String nodeType = node.getNodeType();
        String value = node.getValue();
        String indent = "  ".repeat(Math.max(0, node.getDepth() - 1));

        switch (nodeType) {
            case "ASSIGNMENT":
                return indent + decodeAssignment(node);

            case "PRINT_STATEMENT":
                return indent + "System.out.println(" + 
                       node.getChildren().get(0).getValue() + ")";

            case "FOR_LOOP":
                return indent + "for (" + 
                       node.getChildren().get(0).getValue() + ") {";

            case "IF_STATEMENT":
                return indent + "if (" + 
                       node.getChildren().get(0).getValue() + ") {";

            case "KEYWORD":
                return indent + "// Keyword: " + value;

            case "STATEMENT":
                return indent + "// Statement: " + value + ";";

            case "VALUE":
                return "";

            default:
                return "";
        }
    }

    /**
     * Decode an assignment node
     * @param node The assignment node
     * @return The decoded assignment string
     */
    private String decodeAssignment(ASTNode node) {
        String varName = node.getValue();
        String value = "";

        if (!node.getChildren().isEmpty()) {
            value = node.getChildren().get(0).getValue();
        }

        return "int " + varName + " = " + value + ";";
    }

    /**
     * Generate more readable Java-like code
     * @param pseudoCode The pseudo-code output
     * @return Java-like code wrapped in a class
     */
    public String generateJavaCode(String pseudoCode) {
        StringBuilder javaCode = new StringBuilder();

        javaCode.append("public class GeneratedProgram {\n");
        javaCode.append("    public static void main(String[] args) {\n");
        javaCode.append("        // Auto-generated from natural language input\n");

        // Indent the pseudo-code
        String[] lines = pseudoCode.split("\n");
        for (String line : lines) {
            if (!line.startsWith("//")) {
                javaCode.append("        ").append(line).append("\n");
            }
        }

        javaCode.append("    }\n");
        javaCode.append("}\n");

        return javaCode.toString();
    }
}
