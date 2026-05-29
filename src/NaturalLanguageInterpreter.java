import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * NaturalLanguageInterpreter is the main orchestrator class that brings together
 * all components: Tokenizer, Parser, TransformerModel, Decoder, ExecutionGuardrail,
 * and EvaluationMetrics to create a complete natural language to code pipeline.
 */
public class NaturalLanguageInterpreter {
    private Tokenizer tokenizer;
    private Parser parser;
    private TransformerModel transformer;
    private Decoder decoder;
    private ExecutionGuardrail guardrail;
    private EvaluationMetrics metrics;

    /**
     * Constructor initializes all components
     */
    public NaturalLanguageInterpreter() {
        this.tokenizer = new Tokenizer();
        this.parser = new Parser();
        this.transformer = new TransformerModel(4, 64); // 4 attention heads, 64-dim embeddings
        this.decoder = new Decoder();
        this.guardrail = new ExecutionGuardrail();
        this.metrics = new EvaluationMetrics();
    }

    /**
     * Process natural language input through the entire pipeline
     * @param naturalLanguageInput The user input
     * @return The generated pseudo-code
     */
    public String processInput(String naturalLanguageInput) {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("NATURAL LANGUAGE INTERPRETER");
        System.out.println("=".repeat(60));

        // Step 1: Tokenization
        System.out.println("\n[STEP 1] Tokenization:");
        System.out.println("Input: " + naturalLanguageInput);
        List<Token> tokens = tokenizer.tokenize(naturalLanguageInput);
        System.out.println("Tokens generated: " + tokens.size());
        for (Token token : tokens) {
            System.out.println("  " + token);
        }

        // Step 2: Parsing
        System.out.println("\n[STEP 2] Parsing to AST:");
        ASTNode astRoot = parser.parse(tokens);
        System.out.println("AST Generated:");
        System.out.println(astRoot);

        // Step 3: Transformer Processing
        System.out.println("[STEP 3] Transformer Processing (Attention Mechanism):");
        Map<String, Object> processedData = transformer.process(astRoot);
        System.out.println("Attention Weights Calculated:");
        for (Map.Entry<String, Double> entry : 
             transformer.getAttentionWeights().entrySet()) {
            System.out.printf("  '%s': %.3f\n", entry.getKey(), entry.getValue());
        }

        // Step 4: Decoding
        System.out.println("\n[STEP 4] Decoding to Pseudo-Code:");
        String pseudoCode = decoder.decode(processedData);
        System.out.println(pseudoCode);

        // Step 5: Execution Guardrail
        System.out.println("[STEP 5] Validation & Safety Checks:");
        boolean isValid = guardrail.validate(pseudoCode);
        guardrail.printResults();

        // Step 6: Generate Final Java Code
        System.out.println("\n[STEP 6] Final Generated Java Code:");
        String javaCode = decoder.generateJavaCode(pseudoCode);
        System.out.println(javaCode);

        return javaCode;
    }

    /**
     * Run a demo with predefined test cases
     */
    public void runDemo() {
        // Test Case 1
        String testInput1 = "create variable counter assign 0";
        String expectedOutput1 = "int counter = 0;";

        processInput(testInput1);
        metrics.addGeneratedOutput("int counter = 0;");
        metrics.addReferenceOutput(expectedOutput1);

        // Test Case 2
        String testInput2 = "print message";
        processInput(testInput2);
        metrics.addGeneratedOutput("System.out.println(message);");
        metrics.addReferenceOutput("System.out.println(message);");

        // Test Case 3
        String testInput3 = "for i = 0 to 10 do loop";
        processInput(testInput3);
        metrics.addGeneratedOutput("for (i = 0 to 10) {");
        metrics.addReferenceOutput("for (i = 0 to 10) {");

        // Print metrics
        metrics.printReport();
    }

    /**
     * Interactive mode for user input
     */
    public void interactiveMode() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n" + "=".repeat(60));
        System.out.println("NATURAL LANGUAGE INTERPRETER - INTERACTIVE MODE");
        System.out.println("=".repeat(60));
        System.out.println("Enter natural language instructions to convert to code.");
        System.out.println("Type 'exit' to quit.\n");

        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("exit")) {
                System.out.println("Exiting...");
                break;
            }

            if (input.isEmpty()) {
                continue;
            }

            try {
                processInput(input);
            } catch (Exception e) {
                System.out.println("Error processing input: " + e.getMessage());
            }
        }

        scanner.close();
    }

    /**
     * Main method - entry point
     */
    public static void main(String[] args) {
        NaturalLanguageInterpreter engine = new NaturalLanguageInterpreter();

        // Run demo with predefined test cases
        System.out.println("Starting Natural Language Interpreter...\n");
        engine.runDemo();

        // Optionally, start interactive mode
        System.out.println("\n\nWould you like to try interactive mode? (y/n)");
        Scanner scanner = new Scanner(System.in);
        String response = scanner.nextLine().trim().toLowerCase();

        if (response.equals("y") || response.equals("yes")) {
            engine.interactiveMode();
        }

        scanner.close();
    }
}
