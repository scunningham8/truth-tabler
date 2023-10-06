[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-8d59dc4de5201274e310e4c54b9627a8934c3b88527886e3b421487c677d23eb.svg)](https://classroom.github.com/a/1D96izQ9)
# Truth Tabler/Proof Solver

**This repository holds two assignments, with two separate due dates.** Included in this README are directions for both assignment 1 and assignment 2 (clearly marked). Both assignments will be committed to this repository.

## Assignment 1: Truth Tabler

> __@KaufmAppConsulting__: Do you tire of making long, complicated truth tables?

> __@KaufmAppConsulting__: Do your pencils break after crossing your 5,984th 't'?

> __@KaufmAppConsulting__: Do you sometimes write "frue" or "talse" due to an abundance of wrackspurts swimming in your brain?

> __@KaufmAppConsulting__: You need TruthTabler from the KauffmApp Consulting company. Truth tables on demand with a few taps of the keyboard.

> __@KaufmAppConsulting__: 100% of the writers of this advertisement believe the TruthTabler can solve all your truthing needs.

The PR/marketing department of your consulting firm (a Raikes Sophomore you met in 2FL back in September) just posted this series of tweets on your company Twitter account. It's a little premature of her to post this, but the posts already have over 1 million retweets and the story has been picked up by more than a dozen media sources so far. The world is demanding your product ASAP.

You dabbled with the idea of TruthTabler a few months ago, but you never finished it. Welp, I guess it's time to revisit it. The world is waiting.

In looking through your old files, you came across two useful artifacts. First, you found a sketch of what a generated truth table would look like. It looked something like the below:



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



The second artifact you found was a page of notes that you wrote when you built the initial codebase. Here's to hoping you took good notes!

### Task 1: Make more Propositon classes

The abstract Proposition class has been created, as have the concrete classes VariableProposition and NotProposition. Another abstract class was created for propositional sentences (those propositions with two variables and an operator in the middle, like `A&B` or `P>Q`). In order for the system to work, new classes are needed for and, or, and implies propositions. Where do these classes fit in the Proposition class hierarchy? Gotta figure it out.

### Task 2: Evaluate Propositions

All of the concrete proposition classes have `evaluate` functions, but they're all empty. Truth tables need truth values. Fill in the functions. Rely on polymorphism and recursion wherever possible. Start with the simplest concrete Proposition types and work up to the more complicated. Write some unit tests to make sure you're evaluating correctly. With so much publicity, you can't afford major bugs!

### Task 3: Actually display a truth table

The TruthTabler class has a stub for building the truth table. The first few columns of the table will be the values of the variables, and the remaining columns will be the nested/incremental propositions that need to be solved before solving the last column, the pièce de résistance, the truth values for the custom proposition provided by the user.

Okay, so the part of the code that actually translates a user-provided proposition into a Proposition object isn't wired up yet. But there's a temporary Proposition in place so we can see that the truth table is working properly.

### Task 4: Build the Parser

Gotta build a proposition parser if the system is going to make custom truth tables on demand. Users need to be able to type in the proposition they want to explore. The parser class exists but it needs work. I took a stab at a suite of unit tests for the parser, because (again) it's essential we get it right. Gotta pass 'em all (the tests, that is)!

I wrote a class that tokenizes the user-entered string into its component parts (e.g. `A`, `&`, `-B` ...), so definitely use that to help the Parser.

Oh yeah, don't forget that recursion can add elegance to sticky situations.

### Task 5: Make it release-ready

Clean up the code, ensure you have a battery of unit tests for the `evaluate` and truth tabling functions (and any other features you contribute to).

### Assignment 1 Evaluation Criteria

**Below is a list of criteria for which the assignment will be evaluated and feedback will be given, using the E/S/U/N system.** _The sub-bullet points are provided as examples of what to consider when completing this assignment and are not intended to be comprehensive._

- **Meets spec**
    - The TruthTabler parses user input correctly and according to spec.
    - Propositions are evaluated correctly using polymorphically recursive structures.
    - The TruthTabler outputs truth tables as formatted by the example.
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
- **Extra Credit Assessment: Error Handling**
    - Application throws and catches custom errors (PropositionalParsingError) when a user-submitted proposition is unable to be parsed.
    - Hide exception stack traces from the user.
    - Ask user to reenter proposition on parse failure.

## Assignment 2: Proof Solver

> __@KaufmAppConsulting__: YOU ASKED, WE LISTENED, AND IT'S FINALLY HERE! Our Logical ProofSolver is ready to solve all your problems!

The PR/marketing department did it again. They just posted this tweet on your company Twitter account. The ProofSolver is something you've been dabbling with in your free time, but it's definitely not ready. Due to your success with the TruthTabler, though, you've already received 14 interview requests regarding the launch. It's crunch time. (And time to make a note to give your marketing/social media manager some constructive feedback about premature public announcements.)

### The Mockup
As you've dabbled with the logical proof solver, you mocked up what you want the user interaction to look like:

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

### The Backlog

__Task 0__: Add the java package (folder) that's compressed in the zip file to the edu.unl.raikes.discrete package (folder) of your TruthTabler project.
__Task 1__: Add additional functionality to Proposition classes. Every proposition needs a:
* `copy()` function. This function returns a "deep" copy of the proposition. To make the copying functionality work, without a bunch of work and stinky code, implement this function in each concrete Proposition class, (e.g. AndProposition). The functions are quite simple: just 1.) request a copy of your child(ren), and 2.) create a new object of your class (e.g. AndProposition) with the copies as its children. (Note that VariablePropositions should simply return themselves.)
*  `replace(Map<Proposition,Proposition> map)` function. The implementations of this function should determine whether any of the keys in the map are equal to any of a proposition's children. If the key is equal to a child, replace that child with the value that the child points to in the map, else recursively call the `replace` method. <br/>For example, imagine you are an `AndProposition` representing "`P & (R|Q)`", and, through the replace function,  you received the map ``(P->a; Q->b)``. You will first determine if either of your children are keys in the map. `P` is a key in the map, so you replace your `left` child with `a`.  Your right child is not on the map, so you tell your right child to perform the replace function. When you return from the function, you now represent the proposition "`a & (R|b)`".
* `getMaxDepth()` function. The implementations of this function should find and return the maximum depth of a proposition. For example the proposition `((A | D) & (B | -(C & D)))` has a max depth of 5
  * The first level is an AndProposition with children `((A | D)` and `(B | -(C & D))` The AndProposition will ask for the max depth of each of its children, then return the one that's bigger (plus one, because the AndProposition counts as a level of depth, too).
  * Each proposition that has children will operate in this way. It will find the max of their children, then return `1 + max`. Variable propositions don't have any children, so they always return a depth of 1.

__Task 2__: Implement the laws. In class, we discussed lots of laws of logic. You will need to implement all of those, except for Addition (because, what's life without whimsy?). There are two examples of how to implement a law, and there are markers to indicate which laws need to be implemented. For each of the laws, I wrote one "happy path" test, and one test where the law is inapplicable, and thus is correctly not applied. There are examples of tests too! Check out TestPropositionalLawApplier.java. (The tests are in this class because the PropositionalLaw class doesn't actually *do* anything. We test the LawApplier class because it does stuff.)

**Task 3**: Write the `getRelevantTruths` and `composeProofTable` functions in the `LogicalProver` class. This function takes a PropositionalEquation object (made up of a proposition, a justification (a law), and a list of parents who were combined by the law to make the proposition) and returns an ordered set of all of the object's ancestors. So if the PropositionalEquation object (let's name it "Bob") had two parents, who each had two parents, who each had no parents, we would return a set including ``{Bob, parent1, parent2, grandparent1, grandparent2, grandparent3, grandparent4}``. This function should be written recursively.

__Task 4__: Update the `main` method to allow users to enter their own hypotheses, like the mockup implies. If the system can't find a proof, tell the user so.

__Task 5__: Add one more test to the TestLogicalProver class for the proof we wrote in class. (Hypotheses `P|Q`, `-P|R`, `-Q`, and conclusion `R`).

### Make it release-ready

Clean up the code, ensure your tests pass, commit, and push to master.

Oh, and inform the marketing team the product is finally ready!

### Assignment 2 Evaluation Criteria

**Below is a list of criteria for which the assignment will be evaluated and feedback will be given, using the E/S/N/U system.** _The sub-bullet points are provided as examples of what to consider when completing this assignment and are not intended to be comprehensive._

- **Meets spec**
    - The ProofSolver evaluates user input and applies laws of logic according to spec.
    - Additional Proposition functionality is implemented and functions according to spec.
    - Laws of logic are implemented and function properly.
    - The ProofSolver outputs a proof as formatted in the example.
- **Code structure**
    - No code is copied many times within a method, many times in a class, etc.
    - Code is structured in an easy-to-read way.
- **Testing**
    - All tests run and pass.
    - All assert statements have a unique "message" passed to them.
- **Software engineering principles**
    - No linter errors present.
    - Variable and method names are well-chosen.
- **Recursion**
    - Demonstrates effective knowledge of recursion.
- **Propositions**
    - Understands laws of propositional logic and how they are applied.
    - Correctly applies laws of propositional logic to create proofs.
- **Discrete structures**
    - Understands concepts such as maps, sets, functions, and relationships.
