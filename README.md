# Natural Language Interpreter

A Java-based simulation of a natural language to code compiler that implements a bimodal transformer architecture. This educational project demonstrates how modern AI systems translate human instructions into executable code through tokenization, parsing, attention mechanisms, and code generation.

## 🎯 Overview

This project simulates a complete NLP-to-code pipeline without requiring actual neural networks. Instead, it uses object-oriented design patterns and data structures to logically represent the workflow of:
- **Tokenization** (lexical analysis)
- **Parsing** (AST generation)
- **Transformer Processing** (attention-based semantic enrichment)
- **Code Decoding** (pseudo-code → Java generation)
- **Safety Validation** (syntax guardrails)
- **Performance Metrics** (evaluation & benchmarking)

## 📚 Architecture

**Six Core Components:**

1. **Tokenizer** - Converts natural language input into lexical tokens (keywords, identifiers, operators, literals)
2. **Parser** - Builds an Abstract Syntax Tree (AST) from tokens, organizing them into a structured semantic representation
3. **TransformerModel** - Simulates multi-head attention, calculating relevance scores and generating semantic embeddings for each node
4. **Decoder** - Transforms the processed AST into pseudo-code and Java-like output
5. **ExecutionGuardrail** - Validates generated code for syntax errors, bracket matching, and identifier validity
6. **EvaluationMetrics** - Computes exact match percentage and functional pass rate for quality assessment

## ✨ Features

- ✅ **Pure Java Implementation** - No external ML libraries; uses ArrayList, HashMap, and interfaces
- ✅ **Complete NLP Pipeline** - From natural language input to executable Java code
- ✅ **Simulated Attention Mechanism** - Multi-head attention with configurable heads and embedding dimensions
- ✅ **AST-Based Processing** - Hierarchical semantic understanding via Abstract Syntax Trees
- ✅ **Safety Validation** - Guardrail system for code quality and safety checks
- ✅ **Performance Evaluation** - Metrics for exact match and functional correctness
- ✅ **Dual Modes** - Demo mode with predefined test cases + interactive CLI
- ✅ **Educational Focus** - Clean code, extensive comments, university-level design

## 🚀 Quick Start

### Compile
```bash
javac src/*.java
```

### Run
```bash
java -cp src NaturalLanguageInterpreter
```

## 📝 Example Usage

**Input:** `"create variable counter assign 0"`

**Output:**
```java
public class GeneratedProgram {
    public static void main(String[] args) {
        // Auto-generated from natural language input
        int counter = 0;
    }
}
```

## 📊 Pipeline Example

```
Natural Language Input
        ↓
[STEP 1] Tokenization → Token stream
        ↓
[STEP 2] Parsing → Abstract Syntax Tree
        ↓
[STEP 3] Transformer → Attention weights & semantic vectors
        ↓
[STEP 4] Decoding → Pseudo-code
        ↓
[STEP 5] Validation → Safety checks & syntax verification
        ↓
[STEP 6] Code Generation → Java-like output
```

## 🎓 Use Cases

- Learning compiler design and NLP fundamentals
- Understanding attention mechanisms in practical contexts
- Exploring code generation pipelines
- Building blocks for larger language model simulations
- University-level software engineering project

## 📂 File Structure

```
src/
├── Token.java                      # Lexical token representation
├── Tokenizer.java                  # Lexer/tokenization logic
├── ASTNode.java                    # Abstract Syntax Tree nodes
├── Parser.java                     # Parser and AST construction
├── TransformerModel.java           # Attention mechanism simulation
├── Decoder.java                    # Code generation from AST
├── ExecutionGuardrail.java         # Validation and safety checks
├── EvaluationMetrics.java          # Performance evaluation
└── NaturalLanguageInterpreter.java # Main orchestrator & CLI
```

## 💻 Requirements

- Java 11 or higher
- No external dependencies (pure Java standard library)

## 🔧 Customization

Easily modify:
- Number of attention heads in `TransformerModel`
- Embedding dimensions
- Token recognition patterns in `Tokenizer`
- Grammar rules in `Parser`
- Code generation templates in `Decoder`
- Validation rules in `ExecutionGuardrail`

## 📈 Metrics

The system calculates:
- **Exact Match Score** - Percentage of outputs that exactly match reference outputs
- **Functional Pass Rate** - Percentage of syntactically valid and balanced code
- **Similarity Scores** - Token-based comparison between generated and reference code

## 🎯 Educational Value

This project demonstrates:
- Object-oriented design principles (encapsulation, inheritance, polymorphism)
- Compiler architecture (tokenization → parsing → generation)
- Graph-based data structures (trees, semantic vectors)
- Attention mechanisms without deep learning frameworks
- Software validation and quality assurance
- API design with clear interfaces and responsibilities

## 📝 License

MIT License

## 👤 Author

chike-io

---

For questions or improvements, feel free to open an issue or pull request!
