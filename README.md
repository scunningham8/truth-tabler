
# Truth Tabler/Proof Solver

## Example Output

```
Welcome to the Truth Tabler! What proposition would you like to table-ize today?
(Use '&' for AND, '|' for OR, '-' for NOT, and '>' for IMPLIES. Don't add any spaces!)
A&B>-D|-(C|E)

+-------+-------+-------+-------+-------+-------+---------+---------+------------+---------------------+-----------------------------------+
|   A   |   B   |   C   |   D   |   E   |  -(D) | (A & B) | (C | E) | -((C | E)) | (-(D) | -((C | E))) | ((A & B)) > ((-(D) | -((C | E)))) |
+-------+-------+-------+-------+-------+-------+---------+---------+------------+---------------------+-----------------------------------+
|  TRUE |  TRUE |  TRUE |  TRUE |  TRUE | FALSE |    TRUE |    TRUE |      FALSE |               FALSE |                             FALSE |
|  TRUE |  TRUE |  TRUE |  TRUE | FALSE | FALSE |    TRUE |    TRUE |      FALSE |               FALSE |                             FALSE |
|  TRUE |  TRUE |  TRUE | FALSE |  TRUE |  TRUE |    TRUE |    TRUE |      FALSE |                TRUE |                              TRUE |
|  TRUE |  TRUE |  TRUE | FALSE | FALSE |  TRUE |    TRUE |    TRUE |      FALSE |                TRUE |                              TRUE |
|  TRUE |  TRUE | FALSE |  TRUE |  TRUE | FALSE |    TRUE |    TRUE |      FALSE |               FALSE |                             FALSE |
|  TRUE |  TRUE | FALSE |  TRUE | FALSE | FALSE |    TRUE |   FALSE |       TRUE |                TRUE |                              TRUE |
|  TRUE |  TRUE | FALSE | FALSE |  TRUE |  TRUE |    TRUE |    TRUE |      FALSE |                TRUE |                              TRUE |
|  TRUE |  TRUE | FALSE | FALSE | FALSE |  TRUE |    TRUE |   FALSE |       TRUE |                TRUE |                              TRUE |
|  TRUE | FALSE |  TRUE |  TRUE |  TRUE | FALSE |   FALSE |    TRUE |      FALSE |               FALSE |                              TRUE |
|  TRUE | FALSE |  TRUE |  TRUE | FALSE | FALSE |   FALSE |    TRUE |      FALSE |               FALSE |                              TRUE |
|  TRUE | FALSE |  TRUE | FALSE |  TRUE |  TRUE |   FALSE |    TRUE |      FALSE |                TRUE |                              TRUE |
|  TRUE | FALSE |  TRUE | FALSE | FALSE |  TRUE |   FALSE |    TRUE |      FALSE |                TRUE |                              TRUE |
|  TRUE | FALSE | FALSE |  TRUE |  TRUE | FALSE |   FALSE |    TRUE |      FALSE |               FALSE |                              TRUE |
|  TRUE | FALSE | FALSE |  TRUE | FALSE | FALSE |   FALSE |   FALSE |       TRUE |                TRUE |                              TRUE |
|  TRUE | FALSE | FALSE | FALSE |  TRUE |  TRUE |   FALSE |    TRUE |      FALSE |                TRUE |                              TRUE |
|  TRUE | FALSE | FALSE | FALSE | FALSE |  TRUE |   FALSE |   FALSE |       TRUE |                TRUE |                              TRUE |
| FALSE |  TRUE |  TRUE |  TRUE |  TRUE | FALSE |   FALSE |    TRUE |      FALSE |               FALSE |                              TRUE |
| FALSE |  TRUE |  TRUE |  TRUE | FALSE | FALSE |   FALSE |    TRUE |      FALSE |               FALSE |                              TRUE |
| FALSE |  TRUE |  TRUE | FALSE |  TRUE |  TRUE |   FALSE |    TRUE |      FALSE |                TRUE |                              TRUE |
| FALSE |  TRUE |  TRUE | FALSE | FALSE |  TRUE |   FALSE |    TRUE |      FALSE |                TRUE |                              TRUE |
| FALSE |  TRUE | FALSE |  TRUE |  TRUE | FALSE |   FALSE |    TRUE |      FALSE |               FALSE |                              TRUE |
| FALSE |  TRUE | FALSE |  TRUE | FALSE | FALSE |   FALSE |   FALSE |       TRUE |                TRUE |                              TRUE |
| FALSE |  TRUE | FALSE | FALSE |  TRUE |  TRUE |   FALSE |    TRUE |      FALSE |                TRUE |                              TRUE |
| FALSE |  TRUE | FALSE | FALSE | FALSE |  TRUE |   FALSE |   FALSE |       TRUE |                TRUE |                              TRUE |
| FALSE | FALSE |  TRUE |  TRUE |  TRUE | FALSE |   FALSE |    TRUE |      FALSE |               FALSE |                              TRUE |
| FALSE | FALSE |  TRUE |  TRUE | FALSE | FALSE |   FALSE |    TRUE |      FALSE |               FALSE |                              TRUE |
| FALSE | FALSE |  TRUE | FALSE |  TRUE |  TRUE |   FALSE |    TRUE |      FALSE |                TRUE |                              TRUE |
| FALSE | FALSE |  TRUE | FALSE | FALSE |  TRUE |   FALSE |    TRUE |      FALSE |                TRUE |                              TRUE |
| FALSE | FALSE | FALSE |  TRUE |  TRUE | FALSE |   FALSE |    TRUE |      FALSE |               FALSE |                              TRUE |
| FALSE | FALSE | FALSE |  TRUE | FALSE | FALSE |   FALSE |   FALSE |       TRUE |                TRUE |                              TRUE |
| FALSE | FALSE | FALSE | FALSE |  TRUE |  TRUE |   FALSE |    TRUE |      FALSE |                TRUE |                              TRUE |
| FALSE | FALSE | FALSE | FALSE | FALSE |  TRUE |   FALSE |   FALSE |       TRUE |                TRUE |                              TRUE |
+-------+-------+-------+-------+-------+-------+---------+---------+------------+---------------------+-----------------------------------+
```

```
Welcome to the Logical ProofSolver! Please enter your hypotheses, followed by a blank line.
(P&Q)>R
-R
Q

Great, thanks. What are you hoping to prove based on these hypotheses?
-P

Okay, lemme think....

Eureka! Here's your proof.

+--------+-----------------+--------------------------------+
| Line # |   Proposition   |          Justification         |
+--------+-----------------+--------------------------------+
|      1 | Q               | Hypothesis                     |
|      2 | -(-(Q))         | Double Negation Inverse Law, 1 |
|      3 | -(R)            | Hypothesis                     |
|      4 | ((P & Q)) > (R) | Hypothesis                     |
|      5 | -((P & Q))      | Modus Tollens, 4, 3            |
|      6 | (-(P) | -(Q))   | De Morgan's Law (AND), 5       |
|      7 | (-(Q) | -(P))   | Commutative Law (OR), 6        |
|      8 | -(P)            | Dysjunctive Syllogism, 7, 2    |
+--------+-----------------+--------------------------------+
```

### Task 1: Make more Propositon classes

The abstract Proposition class has been created, as have the concrete classes VariableProposition and NotProposition. Another abstract class was created for propositional sentences (those propositions with two variables and an operator in the middle, like `A&B` or `P>Q`). In order for the system to work, new classes are needed for and, or, and implies propositions. Where do these classes fit in the Proposition class hierarchy? Gotta figure it out.

### Task 2: Evaluate Propositions

All of the concrete proposition classes have `evaluate` functions, but they're all empty. Truth tables need truth values. Fill in the functions. Rely on polymorphism and recursion wherever possible. Start with the simplest concrete Proposition types and work up to the more complicated. Write some unit tests to make sure you're evaluating correctly. With so much publicity, you can't afford major bugs!

### Task 3: Actually display a truth table

The TruthTabler class has a stub for building the truth table. The first few columns of the table will be the values of the variables, and the remaining columns will be the nested/incremental propositions that need to be solved before solving the last column, the pièce de résistance, the truth values for the custom proposition provided by the user.

Okay, so the part of the code that actually translates a user-provided proposition into a Proposition object isn't wired up yet. But there's a temporary Proposition in place so we can see that the truth table is working properly.

### Task 4: Build the Parser

Gotta build a proposition parser if the system is going to make custom truth tables on demand. Users need to be able to type in the proposition they want to explore. The parser class exists but it needs work. I took a stab at a suite of unit tests for the parser, because (again) it's essential we get it right. Gotta pass 'em all (the tests, that is)!

### Task 5: Make it release-ready

Clean up the code, ensure you have a battery of unit tests for the `evaluate` and truth tabling functions (and any other features you contribute to).

### Standards Met

- **Meets spec**
    - The TruthTabler parses user input correctly and according to spec.
    - Propositions are evaluated correctly using polymorphically recursive structures.
    - The TruthTabler outputs truth tables as formatted by the example.
    - The ProofSolver evaluates user input and applies laws of logic according to spec.
    - Laws of logic are implemented and function properly.
    - The ProofSolver outputs a proof as formatted in the example.
- **Code structure**
    - No code copied many times within a method, many times in a class, etc.
    - Code is structured in an easy-to-read way
- **Testing**
    - All tests run and pass
    - All assert statements have a unique "message" passed to them
- **Software engineering principles**
    - No linter errors
    - Well-chosen variable and method names
- **Recursion**
    - Demonstrates effective knowledge of recursion.
- **Object Oriented Programming Principles**
    - Class heirarchy is appropriate for problem.
    - Function implementations pushed to highest sensical level of hierarchy.
    - Understanding of polymorphism is clear.
- **Propositions**
    - Understands order of operations of propositional sentences.
    - Correctly evaluates propositional statements given a variety of truth value variable mappings.
- **Discrete structures**
    - Understands concepts such as maps, sets, functions, and relationships.
