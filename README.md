# Expression-Evaluator

A Java library for evaluating and manipulating integer sequences through various operations. This project provides a framework for performing operations on sequences of integers, including projection, prefix sums, and subsequence matching.

## Overview

The Expression Evaluator library allows you to:
- Perform sequence operations (projection, occurrence checking, prefix sums)
- Chain multiple operations together
- Evaluate combined operations using different strategies (concatenation, filtering)

## Features

### Sequence Operations

#### 1. Projection
Projects one sequence onto another, filtering elements from the second sequence that appear in the first sequence while maintaining order.

**Example:**
```java
int[] seq1 = {1, 3, 5};
int[] seq2 = {2, 1, 6, 3, 1, 4, 5, 3};
Projection proj = new Projection(seq1, seq2);
// Result: [1, 3, 1, 5, 3]
```

#### 2. OccursWithin
Checks whether one sequence appears as a contiguous subsequence within another.

**Example:**
```java
int[] seq1 = {3, 1, 4, 5};
int[] seq2 = {2, 1, 6, 3, 1, 4, 5, 3};
OccursWithin occurs = new OccursWithin(seq1, seq2);
// Result: true (seq1 occurs within seq2)
```

#### 3. SumsOfPrefixes
Calculates the sum of each prefix of a sequence, including the empty prefix.

**Example:**
```java
int[] seq = {1, 6, 1};
SumsOfPrefixes sums = new SumsOfPrefixes(seq);
// Result: [0, 1, 7, 8]
// Prefixes: [], [1], [1,6], [1,6,1] with sums 0, 1, 7, 8
```

### Evaluators

#### ConcatAll
Concatenates the results of multiple sequence operations. Only works with operations that produce sequences (Projection and SumsOfPrefixes).

**Example:**
```java
SeqEvaluator evaluator = new ConcatAll(10);
evaluator.addOperation("op:projection", seq1, seq2);
evaluator.addOperation("op:sumsOfPrefixes", seq1, null);
// Concatenates the results of both operations
```

#### FilterAll
Evaluates multiple OccursWithin operations and displays their results, filtering out false values with an underscore (_).

**Example:**
```java
SeqEvaluator evaluator = new FilterAll(10);
evaluator.addOperation("op:occursWithin", seq1, seq4);
evaluator.addOperation("op:occursWithin", seq2, seq4);
evaluator.addOperation("op:occursWithin", seq3, seq4);
// Result: "Filter result is: true, _, true"
```

## Project Structure

```
Expression_Evaluator/
├── src/
│   ├── model/
│   │   ├── SeqOperation.java          # Abstract base class for operations
│   │   ├── BinarySeqOperation.java    # Base for two-sequence operations
│   │   ├── Projection.java            # Projection operation
│   │   ├── OccursWithin.java          # Subsequence matching
│   │   ├── SumsOfPrefixes.java        # Prefix sum calculation
│   │   ├── SeqEvaluator.java          # Abstract evaluator base
│   │   ├── ConcatAll.java             # Concatenation evaluator
│   │   ├── FilterAll.java             # Filtering evaluator
│   │   └── IllegalOperationException.java
│   └── junit_tests/
│       └── StarterTests.java          # Unit tests
└── bin/                                # Compiled classes
```

## Building and Testing

### Prerequisites
- Java Development Kit (JDK) 8 or higher
- JUnit 4 for running tests

### Running Tests
The project includes comprehensive JUnit tests in `StarterTests.java` that demonstrate all functionality:

```bash
# Compile the project
javac -d bin -sourcepath src src/model/*.java src/junit_tests/*.java

# Run tests (requires JUnit on classpath)
java -cp bin:junit-4.x.jar org.junit.runner.JUnitCore junit_tests.StarterTests
```

## Usage Examples

### Basic Sequence Operations

```java
import model.*;

// Projection example
int[] seq1 = {1, 3, 5};
int[] seq2 = {2, 1, 6, 3, 1, 4, 5, 3};
Projection proj = new Projection(seq1, seq2);
System.out.println(proj.toString());
// Output: Projecting [1, 3, 5] to [2, 1, 6, 3, 1, 4, 5, 3] results in: [1, 3, 1, 5, 3]

// Occurrence check
int[] subseq = {3, 1, 4, 5};
OccursWithin occurs = new OccursWithin(subseq, seq2);
System.out.println(occurs.toString());
// Output: [3, 1, 4, 5] occurs within [2, 1, 6, 3, 1, 4, 5, 3]

// Prefix sums
int[] seq3 = {1, 6, 1};
SumsOfPrefixes sums = new SumsOfPrefixes(seq3);
System.out.println(sums.toString());
// Output: Sums of prefixes of [1, 6, 1] is: [0, 1, 7, 8]
```

### Using Evaluators

```java
// ConcatAll evaluator
SeqEvaluator concat = new ConcatAll(10);
concat.addOperation("op:projection", seq1, seq2);
concat.addOperation("op:sumsOfPrefixes", seq1, null);
System.out.println(concat.toString());

// FilterAll evaluator
SeqEvaluator filter = new FilterAll(10);
filter.addOperation("op:occursWithin", seq1, seq2);
filter.addOperation("op:occursWithin", subseq, seq2);
System.out.println(filter.toString());
```

## Error Handling

The library includes error handling for invalid operations:
- `IllegalOperationException` is thrown when an invalid operation type is specified
- Evaluators report incompatible operations when operations don't match the evaluator type

## License

This project is available for educational purposes.

## Contributing

Contributions are welcome! Please ensure all tests pass before submitting changes.
