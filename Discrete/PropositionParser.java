package edu.unl.raikes.discrete;

import edu.unl.raikes.discrete.propositions.AndProposition;
import edu.unl.raikes.discrete.propositions.ImpliesProposition;
import edu.unl.raikes.discrete.propositions.NotProposition;
import edu.unl.raikes.discrete.propositions.OrProposition;
import edu.unl.raikes.discrete.propositions.Proposition;
import edu.unl.raikes.discrete.propositions.VariableProposition;

/**
 * The PropositionParser type is used for statically parsing propositional sentences represented by variables.
 */
public class PropositionParser {
    public static final Character IMPLIES = '>';
    public static final Character AND = '&';
    public static final Character OR = '|';
    public static final Character NOT = '-';
    public static final Character O_PAREN = '(';
    public static final Character C_PAREN = ')';

    /*
     * Goal: left operator right
     * 
     * 1) figure out what the left side is 2) if there is a right side * use tokenizer class to determine this a:
     * determine what the operator is * use tokenizer class to determine this b: determine what the right side is * use
     * left logic c: build our proposition based on the operator * use sentence proposition based on operator
     */

    /*
     * DONE
     * 
     * Step 1 determining the left side
     * 
     * tokenizer.getNextToken(); - single variable - negated variable - negated expression - call parse on inside -
     * negate that - something bigger - call parse on the token
     */

    /**
     * This function parses a string representing a proposition and returns a Proposition object representing that
     * string.
     *
     * @param s the string to parse into Proposition object(s)
     * @return a Proposition object representing the proposition
     */
    public static Proposition parse(String s) {
        PropositionTokenizer tokenizer = new PropositionTokenizer(s);
        String next = tokenizer.next();

        // base case

        // Your base cases in this function should be if the left or right is a
        // variable and if the left or right is a not proposition.
        if (!tokenizer.hasNext()) {
            // determines if it's a not

            if (next.charAt(0) == NOT) {
                return new NotProposition(parse(next.substring(1)));
            }

            if (next.length() == 1) {
                // returns a new proposition
                return VariableProposition.getVariableByLetter(next.charAt(0));
            }

        }

        // creates left side
        Proposition left = parse(next);
        Proposition entire = null;

        while (tokenizer.hasNext()) {
            // finds next operator
            char operator = tokenizer.nextOperator();

            // finds next proposition
            Proposition right = parse(tokenizer.next());

            // build proposition based on operator
            if (operator == AND) {
                entire = new AndProposition(left, right);
            } else if (operator == OR) {
                entire = new OrProposition(left, right);
            } else if (operator == IMPLIES) {
                entire = new ImpliesProposition(left, right);
            }

            // makes the left side to be the entire proposition
            left = entire;
        }

        return left;
    }

}
