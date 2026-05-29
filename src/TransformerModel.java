import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TransformerModel simulates attention-based processing on the AST.
 * It calculates attention scores between nodes to enrich semantic understanding.
 */
public class TransformerModel {
    private Map<String, Double> attentionWeights;
    private int numHeads;
    private int embeddingDim;

    /**
     * Constructor for TransformerModel
     * @param numHeads Number of attention heads
     * @param embeddingDim Embedding dimension
     */
    public TransformerModel(int numHeads, int embeddingDim) {
        this.numHeads = numHeads;
        this.embeddingDim = embeddingDim;
        this.attentionWeights = new HashMap<>();
    }

    /**
     * Process the AST through the transformer model (simulated)
     * Calculates relevance scores between different nodes
     * @param astRoot The root of the AST
     * @return A processed representation with attention scores
     */
    public Map<String, Object> process(ASTNode astRoot) {
        Map<String, Object> result = new HashMap<>();

        // Flatten AST to get all nodes
        List<ASTNode> allNodes = new ArrayList<>();
        flattenAST(astRoot, allNodes);

        // Simulate attention: calculate relevance between nodes
        List<Double> attentionScores = calculateAttention(allNodes);

        // Simulate multi-head attention
        Map<Integer, List<Double>> multiHeadAttention = new HashMap<>();
        for (int head = 0; head < numHeads; head++) {
            List<Double> headScores = new ArrayList<>();
            for (int i = 0; i < attentionScores.size(); i++) {
                // Simulate different head focusing on different aspects
                double score = attentionScores.get(i) * (0.5 + (head * 0.15));
                headScores.add(Math.min(score, 1.0));
            }
            multiHeadAttention.put(head, headScores);
        }

        // Store results
        result.put("nodes", allNodes);
        result.put("attention_scores", attentionScores);
        result.put("multi_head_attention", multiHeadAttention);
        result.put("semantic_vectors", generateSemanticVectors(allNodes));

        return result;
    }

    /**
     * Calculate attention scores between nodes (simulated)
     * @param nodes The list of nodes to calculate attention for
     * @return A list of attention scores
     */
    private List<Double> calculateAttention(List<ASTNode> nodes) {
        List<Double> scores = new ArrayList<>();

        for (int i = 0; i < nodes.size(); i++) {
            ASTNode node = nodes.get(i);
            
            // Base score based on node importance
            double score = 0.5;

            // Keywords and control flow get higher attention
            if (node.getNodeType().equals("KEYWORD") || 
                node.getNodeType().contains("STATEMENT")) {
                score += 0.3;
            }

            // Variables and identifiers get moderate attention
            if (node.getNodeType().equals("IDENTIFIER")) {
                score += 0.2;
            }

            // Higher scores for deeper, more specific nodes
            score += (node.getDepth() * 0.05);

            // Normalize to [0, 1]
            score = Math.min(score, 1.0);
            scores.add(score);

            attentionWeights.put(node.getValue(), score);
        }

        return scores;
    }

    /**
     * Generate semantic vectors for nodes (simulated embeddings)
     * @param nodes The list of nodes
     * @return A map of node values to their semantic vectors
     */
    private Map<String, List<Double>> generateSemanticVectors(List<ASTNode> nodes) {
        Map<String, List<Double>> vectors = new HashMap<>();

        for (ASTNode node : nodes) {
            List<Double> vector = new ArrayList<>();

            // Generate a simple embedding based on node characteristics
            for (int i = 0; i < embeddingDim; i++) {
                double value;
                
                // Hash-like behavior based on node type and position
                int hash = (node.getNodeType().hashCode() + i) % 100;
                value = (double) hash / 100.0;

                vector.add(value);
            }

            vectors.put(node.getValue(), vector);
        }

        return vectors;
    }

    /**
     * Flatten the AST into a list of all nodes
     * @param node The current node
     * @param result The list to accumulate results
     */
    private void flattenAST(ASTNode node, List<ASTNode> result) {
        result.add(node);
        for (ASTNode child : node.getChildren()) {
            flattenAST(child, result);
        }
    }

    public Map<String, Double> getAttentionWeights() {
        return attentionWeights;
    }
}
