package edu.unl.raikes.discrete.tests;

import static org.junit.Assert.*;

import edu.unl.raikes.discrete.PropositionTokenizer;
import org.junit.Test;

public class PropositionTokenizerTest {

    @Test
    public void testTokenizingSingleVar() {
        // s
        PropositionTokenizer pt = new PropositionTokenizer("A&B");
        String expected = "A";

        // e
        String actual = pt.nextProposition();

        // t
        assertEquals("could not get first token from A&B", expected, actual);
    }

    @Test
    public void testTokenizingFirstOp() {
        // s
        PropositionTokenizer pt = new PropositionTokenizer("A&B");
        char expected = '&';

        // e
        pt.nextProposition();
        char actual = pt.nextOperator();

        // t
        assertEquals("could not get first operator from A&B", expected, actual);
    }

    @Test
    public void testTokenizingSecondToken() {
        // s
        PropositionTokenizer pt = new PropositionTokenizer("A&B");
        String expected = "B";

        // e
        pt.nextProposition();
        pt.nextOperator();
        String actual = pt.nextProposition();

        // t
        assertEquals("could not get first operator from A&B", expected, actual);
    }

    @Test
    public void testTokenizingParentheses() {
        // s
        PropositionTokenizer pt = new PropositionTokenizer("A&(-B)|A");
        String expected = "-B";

        // e
        pt.nextProposition();
        pt.nextOperator();
        String actual = pt.nextProposition();

        // t
        assertEquals("could not get parenthesized portion  operator from A&B", expected, actual);
    }

    @Test
    public void testTokenizingOpAfterParentheses() {
        // s
        PropositionTokenizer pt = new PropositionTokenizer("A&(-B)|A");
        final Character expected = '|';

        // e
        pt.nextProposition();
        pt.nextOperator();
        pt.nextProposition();
        Character actual = pt.nextOperator();

        // t
        assertEquals("could not get operator after parentheses \"A&(-B)|A\"", expected, actual);
    }

    @Test
    public void testTokenizingNegatedParentheses() {
        // s
        PropositionTokenizer pt = new PropositionTokenizer("A&-(-B)|A");
        String expected = "-(-B)";

        // e
        pt.nextProposition();
        pt.nextOperator();
        String actual = pt.nextProposition();

        // t
        assertEquals("could not get negated parentheses from \"A&-(-B)|A\"", expected, actual);
    }

    @Test
    public void testTokenizingCapturesEverythingAfterImplies() {
        // s
        PropositionTokenizer pt = new PropositionTokenizer("A>-(-B)|A");
        String expected = "-(-B)|A";

        // e
        pt.nextProposition();
        pt.nextOperator();
        String actual = pt.nextProposition();

        // t
        assertEquals("could not get everything after implies with string \"A>-(-B)|A\"", expected, actual);
    }

}
