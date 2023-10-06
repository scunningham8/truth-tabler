package edu.unl.raikes.discrete;

import java.util.Iterator;

/**
 * Handles the tokenization of a user-provided proposition string into digestible parts.
 */
public class PropositionTokenizer implements Iterator<String>, Iterable<String> {
    String s;
    int nextUnparsed = 0;
    Character lastOperator;
    char lastTokenType = 'o'; // s for subsequence, o for operator

    /**
     * Constructs an object of the PropositionTokenizer class.
     *
     * @param s the user-provided string to tokenize
     */
    public PropositionTokenizer(String s) {
        this.s = s;
        this.nextUnparsed = 0;
    }

    /**
     * Determines whether the string has a next token (either an operator or a proposition).
     */
    @Override
    public boolean hasNext() {
        return this.nextUnparsed < this.s.length();
    }

    /**
     * Returns the next token in the string, which is either an operator or a proposition.
     */
    @Override
    public String next() {
        if (this.lastTokenType == 'o') {
            return this.nextProposition();
        } else {
            return this.nextOperator().toString();
        }
    }

    /**
     * Returns the next token in the string, which based on what has been returned previously is supposed to be an
     * operator.
     *
     * @return the next operator in the string
     */
    public Character nextOperator() {
        char o = this.s.charAt(this.nextUnparsed);

        if (o == PropositionParser.AND || o == PropositionParser.OR || o == PropositionParser.IMPLIES) {
            this.nextUnparsed++;
            this.lastOperator = o;
            this.lastTokenType = 'o';
            return o;
        }

        return null;
    }

    /**
     * Returns the next token in the string, which based on what has been returned previously is supposed to be an
     * operator.
     * 
     * @return the next token in the string
     */
    public String nextProposition() {
        int start = this.nextUnparsed;
        this.lastTokenType = 's';
        boolean negated = false;

        // if the last operator was an implies statement, the next token is the rest of the statement
        if (this.lastOperator == PropositionParser.IMPLIES) {
            this.nextUnparsed = this.s.length();
            return this.s.substring(start);
        }

        // if the first char is a not char, we want to capture the next token as well
        if (this.s.charAt(this.nextUnparsed) == PropositionParser.NOT) {
            negated = true;
            this.nextUnparsed++;
        }
        // if it's alphabetic, then it's a single variable
        if (Character.isAlphabetic(this.s.charAt(this.nextUnparsed))) {
            this.nextUnparsed++;

            return this.s.substring(start, this.nextUnparsed);
        } else if (this.s.charAt(this.nextUnparsed) == PropositionParser.O_PAREN) {
            // if it's a paren, then we want to find its closure and return everything inside
            int openCount = 1;

            // find the closing paren
            while (openCount > 0) {
                this.nextUnparsed++;
                if (this.s.charAt(this.nextUnparsed) == PropositionParser.O_PAREN) {
                    openCount++;
                } else if (this.s.charAt(this.nextUnparsed) == PropositionParser.C_PAREN) {
                    openCount--;
                }
            }
            // move one space past the closing paren (so it's not captured in the next token/operator)
            this.nextUnparsed++;

            // make sure you don't capture the closing paren
            int end = this.nextUnparsed - 1;
            // if it's negated, you actually want to keep the closing paren
            if (negated) {
                end++;
            } else {
                start++;
            }
            return this.s.substring(start, end);
        }

        return null;
    }

    @Override
    public Iterator<String> iterator() {
        return this;
    }

}
