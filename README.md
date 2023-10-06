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
