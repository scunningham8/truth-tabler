package edu.unl.raikes.discrete.tests;

import static org.junit.Assert.*;

import edu.unl.raikes.discrete.PropositionParser;
import edu.unl.raikes.discrete.propositions.AndProposition;
import edu.unl.raikes.discrete.propositions.ImpliesProposition;
import edu.unl.raikes.discrete.propositions.NotProposition;
import edu.unl.raikes.discrete.propositions.OrProposition;
import edu.unl.raikes.discrete.propositions.Proposition;
import edu.unl.raikes.discrete.propositions.VariableProposition;
import org.junit.Test;

public class PropositionParserTest {

    @Test
    public void testParsingSingleVariable() {
        // expected
        Proposition expected = VariableProposition.getVariableByLetter('P');

        // execute
        Proposition actual = PropositionParser.parse("P");

        // test
        assertEquals("couldn't parse single variable proposition", expected, actual);
    }
    
    @Test
    public void testParsingSingleVariableInParenthesis() {
        // expected
        Proposition expected = VariableProposition.getVariableByLetter('P');

        // execute
        Proposition actual = PropositionParser.parse("(P)");

        // test
        assertEquals("couldn't parse single variable proposition", expected, actual);
    }

    @Test
    public void testParsingSingleNegatedVariable() {
        // expected
        Proposition expected = new NotProposition(VariableProposition.getVariableByLetter('P'));

        // execute
        Proposition actual = PropositionParser.parse("-P");

        // test
        assertEquals("couldn't parse single negated variable proposition", expected, actual);
    }

    @Test
    public void testParsingSimpleVariableAndVariable() {
        // expected
        Proposition expected = new AndProposition(VariableProposition.getVariableByLetter('P'),
                VariableProposition.getVariableByLetter('Q'));

        // execute
        Proposition actual = PropositionParser.parse("P&Q");

        // test
        assertEquals("couldn't parse [variable AND variable] proposition", expected, actual);
    }

    @Test
    public void testParsingSimpleVariableAndVariableInParentheses() {
        // expected
        Proposition expected = new AndProposition(VariableProposition.getVariableByLetter('P'),
                VariableProposition.getVariableByLetter('Q'));

        // execute
        Proposition actual = PropositionParser.parse("(P&Q)");

        // test
        assertEquals("couldn't parse ([variable AND variable]) proposition (in parentheses)", expected, actual);
    }

    @Test
    public void testParsingSimpleVariableAndNegatedVariable() {
        // expected
        Proposition expected = new AndProposition(VariableProposition.getVariableByLetter('P'),
                new NotProposition(VariableProposition.getVariableByLetter('Q')));

        // execute
        Proposition actual = PropositionParser.parse("P&-Q");

        // test
        assertEquals("couldn't parse [variable AND variable] proposition", expected, actual);
    }

    @Test
    public void testParsingVariableAndVariableAndVariable() {
        // expected
        Proposition expected = new AndProposition(new AndProposition(VariableProposition.getVariableByLetter('P'),
                VariableProposition.getVariableByLetter('Q')), VariableProposition.getVariableByLetter('R'));

        // execute
        Proposition actual = PropositionParser.parse("P&Q&R");

        // test
        assertEquals("couldn't parse [p AND q AND r] proposition", expected, actual);
    }

    @Test
    public void testParsingNegatedParentheses() {
        // expected
        Proposition expected = new NotProposition(new NotProposition(VariableProposition.getVariableByLetter('P')));

        // execute
        Proposition actual = PropositionParser.parse("-(-P)");

        // test
        assertEquals("couldn't parse negated parentheses", expected, actual);
    }

    @Test
    public void testParsingImplies() {
        // expected
        Proposition expected = new ImpliesProposition(VariableProposition.getVariableByLetter('Q'),
                new NotProposition(new NotProposition(VariableProposition.getVariableByLetter('P'))));

        // execute
        Proposition actual = PropositionParser.parse("Q>-(-P)");

        // test
        assertEquals("couldn't parse negated parentheses", expected, actual);
    }

    @Test
    public void testParsingComplexStatementWithOneImplies() {
        // expected
        Proposition a = VariableProposition.getVariableByLetter('A');
        Proposition b = VariableProposition.getVariableByLetter('B');
        Proposition c = VariableProposition.getVariableByLetter('C');
        Proposition d = VariableProposition.getVariableByLetter('D');
        Proposition expected = new ImpliesProposition(
                new OrProposition(new AndProposition(a, b), new NotProposition(c)), new OrProposition(d, b));

        // execute
        Proposition actual = PropositionParser.parse("A&B|-C>D|B");

        // test
        assertEquals("couldn't parse complex statement with one implies: \"A&B|-C>D|B\"", expected, actual);
    }

    @Test
    public void testParsingComplexStatementWithOneImpliesInParentheses() {
        // expected
        Proposition a = VariableProposition.getVariableByLetter('A');
        Proposition b = VariableProposition.getVariableByLetter('B');
        Proposition c = VariableProposition.getVariableByLetter('C');
        Proposition d = VariableProposition.getVariableByLetter('D');
        Proposition expected = new AndProposition(
                new OrProposition(a, new ImpliesProposition(
                        new OrProposition(new AndProposition(a, b), new NotProposition(c)), new OrProposition(d, b))),
                b);

        // execute
        Proposition actual = PropositionParser.parse("A|(A&B|-C>D|B)&B");

        // test
        assertEquals("couldn't parse complex statement with parentheses and one implies: \"A|(A&B|-C>D|B)&B\"",
                expected, actual);
    }

    @Test
    public void testParsingComplexStatementWithTwoImplies() {
        // expected
        Proposition a = VariableProposition.getVariableByLetter('A');
        Proposition b = VariableProposition.getVariableByLetter('B');
        Proposition c = VariableProposition.getVariableByLetter('C');
        Proposition d = VariableProposition.getVariableByLetter('D');
        Proposition expected = new ImpliesProposition(new NotProposition(a),
                new ImpliesProposition(new OrProposition(b, new NotProposition(c)), new OrProposition(d, b)));

        // execute
        Proposition actual = PropositionParser.parse("-A>B|-C>D|B");

        // test
        assertEquals("couldn't parse complex statement with two implies: \"A&B|-C>D|B\"", expected, actual);
    }

    @Test
    public void testParsingWithOneOrWithNoParenthesis() {
        // expected
        Proposition a = VariableProposition.getVariableByLetter('A');
        Proposition b = VariableProposition.getVariableByLetter('B');
        Proposition expected = new OrProposition(a, b);

        // execute
        Proposition actual = PropositionParser.parse("A|B");

        // test
        assertEquals("couldn't parse complex statement A|B", expected, actual);
    }



    @Test
    public void testParsingWithTwoOrsWithNoParenthes() {
        // expected
        Proposition a = VariableProposition.getVariableByLetter('A');
        Proposition b = VariableProposition.getVariableByLetter('B');
        Proposition c = VariableProposition.getVariableByLetter('C');
        Proposition expected = new OrProposition(new OrProposition(a, b), c);

        // execute
        Proposition actual = PropositionParser.parse("A|B|C");

        // test
        assertEquals("couldn't parse complex statement A|B|C", expected, actual);
    }

    @Test
    public void testParsingWithTwoOrsWithParenthesOnSecondStatement() {
        // expected
        Proposition a = VariableProposition.getVariableByLetter('A');
        Proposition b = VariableProposition.getVariableByLetter('B');
        Proposition c = VariableProposition.getVariableByLetter('C');
        Proposition expected = new OrProposition(a, new OrProposition(b, c));

        // execute
        Proposition actual = PropositionParser.parse("A|(B|C)");

        // test
        assertEquals("couldn't parse complex statement (A|(B|C)", expected, actual);
    }

    @Test
    public void testParsingWithOneOrAndOneNegativeProposition() {
        // expected
        Proposition a = VariableProposition.getVariableByLetter('A');
        Proposition b = VariableProposition.getVariableByLetter('B');
        Proposition expected = new OrProposition(a, new NotProposition(b));

        // execute
        Proposition actual = PropositionParser.parse("A|-B");

        // test
        assertEquals("couldn't parse complex statement A|-B", expected, actual);
    }

}
