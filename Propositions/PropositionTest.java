
package edu.unl.raikes.discrete.tests;

import static org.junit.Assert.*;

import edu.unl.raikes.discrete.propositions.AndProposition;
import edu.unl.raikes.discrete.propositions.ImpliesProposition;
import edu.unl.raikes.discrete.propositions.NotProposition;
import edu.unl.raikes.discrete.propositions.OrProposition;
import edu.unl.raikes.discrete.propositions.Proposition;
import edu.unl.raikes.discrete.propositions.TruthValue;
import edu.unl.raikes.discrete.propositions.VariableProposition;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import org.junit.Test;

public class PropositionTest {

    @Test
    public void testGetAllVariablesTwoVariables() {
        // setup
        Proposition prop = new AndProposition(VariableProposition.getVariableByLetter('P'),
                VariableProposition.getVariableByLetter('Q'));
        Set<VariableProposition> expected = new TreeSet<VariableProposition>();
        expected.add(VariableProposition.getVariableByLetter('P'));
        expected.add(VariableProposition.getVariableByLetter('Q'));

        // execute
        Set<VariableProposition> actual = prop.getAllVariables();

        // test
        assertEquals("list of variables in proposition not the same", expected, actual);
    }

    @Test
    public void testGetAllVariablesFourVariables() {
        // setup
        VariableProposition a = VariableProposition.getVariableByLetter('A');
        VariableProposition b = VariableProposition.getVariableByLetter('B');
        VariableProposition c = VariableProposition.getVariableByLetter('C');
        VariableProposition d = VariableProposition.getVariableByLetter('D');
        Proposition prop = new AndProposition(
                new OrProposition(a, new ImpliesProposition(
                        new OrProposition(new AndProposition(a, b), new NotProposition(c)), new OrProposition(d, b))),
                b);
        Set<VariableProposition> expected = new TreeSet<VariableProposition>();
        expected.add(a);
        expected.add(b);
        expected.add(c);
        expected.add(d);

        // execute
        Set<VariableProposition> actual = prop.getAllVariables();

        // test
        assertEquals("list of variables in proposition not the same", expected, actual);
    }

    @Test
    public void testVariablePropositionEvaluate() {
        // s
        char letter = 'A';
        VariableProposition proposition = VariableProposition.getVariableByLetter(letter);
        TruthValue truthValue = TruthValue.TRUE;
        Map<VariableProposition, TruthValue> truthValues = new HashMap<VariableProposition, TruthValue>();
        truthValues.put(proposition, truthValue);

        // e
        TruthValue actual = proposition.evaluate(truthValues);

        // t
        assertEquals("Variableproposition didn't evaluate correctly", actual, truthValue);
    }

    @Test
    public void testNotPropositionEvaluateFalse() {
        // s
        char letter = 'A';
        VariableProposition proposition = VariableProposition.getVariableByLetter(letter);
        NotProposition not = new NotProposition(proposition);
        TruthValue truthValue = TruthValue.FALSE;
        Map<VariableProposition, TruthValue> truthValues = new HashMap<VariableProposition, TruthValue>();
        truthValues.put(proposition, truthValue);
        TruthValue expected = TruthValue.TRUE;

        // e
        TruthValue actual = not.evaluate(truthValues);

        // t
        assertEquals("not proposition didn't evaluate false correctly", actual, expected);
    }

    @Test
    public void testNotPropositionEvaluateTrue() {
        // s
        char letter = 'A';
        VariableProposition proposition = VariableProposition.getVariableByLetter(letter);
        NotProposition not = new NotProposition(proposition);
        TruthValue truthValue = TruthValue.TRUE;
        Map<VariableProposition, TruthValue> truthValues = new HashMap<VariableProposition, TruthValue>();
        truthValues.put(proposition, truthValue);
        TruthValue expected = TruthValue.FALSE;

        // e
        TruthValue actual = not.evaluate(truthValues);

        // t
        assertEquals("not proposition didn't evaluate true correctly", actual, expected);
    }

    @Test
    public void testOrPropositionEvaluateWithTrueFalse() {
        // s
        VariableProposition a = VariableProposition.getVariableByLetter('A');
        VariableProposition b = VariableProposition.getVariableByLetter('B');
        OrProposition or = new OrProposition(a, b);
        TruthValue truthA = TruthValue.TRUE;
        TruthValue truthB = TruthValue.FALSE;
        Map<VariableProposition, TruthValue> truthValuesA = new HashMap<VariableProposition, TruthValue>();
        truthValuesA.put(a, truthA);
        truthValuesA.put(b, truthB);
        TruthValue expected = TruthValue.TRUE;

        // e
        TruthValue actual = or.evaluate(truthValuesA);

        // t
        assertEquals("or proposition didn't evaluate with false and true", actual, expected);
    }

    @Test
    public void testOrPropositionEvaluateWithTrueTrue() {
        // s
        VariableProposition a = VariableProposition.getVariableByLetter('A');
        VariableProposition b = VariableProposition.getVariableByLetter('B');
        OrProposition or = new OrProposition(a, b);
        TruthValue truthA = TruthValue.TRUE;
        TruthValue truthB = TruthValue.TRUE;
        Map<VariableProposition, TruthValue> truthValuesA = new HashMap<VariableProposition, TruthValue>();
        truthValuesA.put(a, truthA);
        truthValuesA.put(b, truthB);
        TruthValue expected = TruthValue.TRUE;

        // e
        TruthValue actual = or.evaluate(truthValuesA);

        // t
        assertEquals("or proposition didn't evaluate with true and true", actual, expected);
    }

    @Test
    public void testOrPropositionEvaluateWithFalseFalse() {
        // s
        VariableProposition a = VariableProposition.getVariableByLetter('A');
        VariableProposition b = VariableProposition.getVariableByLetter('B');
        OrProposition or = new OrProposition(a, b);
        TruthValue truthA = TruthValue.FALSE;
        TruthValue truthB = TruthValue.FALSE;
        Map<VariableProposition, TruthValue> truthValuesA = new HashMap<VariableProposition, TruthValue>();
        truthValuesA.put(a, truthA);
        truthValuesA.put(b, truthB);
        TruthValue expected = TruthValue.FALSE;

        // e
        TruthValue actual = or.evaluate(truthValuesA);

        // t
        assertEquals("or proposition didn't evaluate with false and false", actual, expected);
    }

    @Test
    public void testAndPropositionEvaluateWithFalseFalse() {
        // s
        VariableProposition a = VariableProposition.getVariableByLetter('A');
        VariableProposition b = VariableProposition.getVariableByLetter('B');
        AndProposition and = new AndProposition(a, b);
        TruthValue truthA = TruthValue.FALSE;
        TruthValue truthB = TruthValue.FALSE;
        Map<VariableProposition, TruthValue> truthValues = new HashMap<VariableProposition, TruthValue>();
        truthValues.put(a, truthA);
        truthValues.put(b, truthB);
        TruthValue expected = TruthValue.FALSE;

        // e
        TruthValue actual = and.evaluate(truthValues);

        // t
        assertEquals("and proposition didn't evaluate with false and false", actual, expected);
    }

    @Test
    public void testAndPropositionEvaluateWithTrueFalse() {
        // s
        VariableProposition a = VariableProposition.getVariableByLetter('A');
        VariableProposition b = VariableProposition.getVariableByLetter('B');
        AndProposition and = new AndProposition(a, b);
        TruthValue truthA = TruthValue.TRUE;
        TruthValue truthB = TruthValue.FALSE;
        Map<VariableProposition, TruthValue> truthValues = new HashMap<VariableProposition, TruthValue>();
        truthValues.put(a, truthA);
        truthValues.put(b, truthB);
        TruthValue expected = TruthValue.FALSE;

        // e
        TruthValue actual = and.evaluate(truthValues);

        // t
        assertEquals("and proposition didn't evaluate with true and false", actual, expected);
    }

    @Test
    public void testAndPropositionEvaluateWithTrueTrue() {
        // s
        VariableProposition a = VariableProposition.getVariableByLetter('A');
        VariableProposition b = VariableProposition.getVariableByLetter('B');
        AndProposition and = new AndProposition(a, b);
        TruthValue truthA = TruthValue.TRUE;
        TruthValue truthB = TruthValue.TRUE;
        Map<VariableProposition, TruthValue> truthValues = new HashMap<VariableProposition, TruthValue>();
        truthValues.put(a, truthA);
        truthValues.put(b, truthB);
        TruthValue expected = TruthValue.TRUE;

        // e
        TruthValue actual = and.evaluate(truthValues);

        // t
        assertEquals("and proposition didn't evaluate with true and true", actual, expected);
    }

    @Test
    public void testImpliesPropositionEvaluateWithTrueTrue() {
        // s
        VariableProposition a = VariableProposition.getVariableByLetter('A');
        VariableProposition b = VariableProposition.getVariableByLetter('B');
        ImpliesProposition implies = new ImpliesProposition(a, b);
        TruthValue truthA = TruthValue.TRUE;
        TruthValue truthB = TruthValue.TRUE;
        Map<VariableProposition, TruthValue> truthValues = new HashMap<VariableProposition, TruthValue>();
        truthValues.put(a, truthA);
        truthValues.put(b, truthB);
        TruthValue expected = TruthValue.TRUE;

        // e
        TruthValue actual = implies.evaluate(truthValues);

        // t
        assertEquals("implies proposition didn't evaluate with true and true", actual, expected);
    }

    @Test
    public void testImpliesPropositionEvaluateWithTrueFalse() {
        // s
        VariableProposition a = VariableProposition.getVariableByLetter('A');
        VariableProposition b = VariableProposition.getVariableByLetter('B');
        ImpliesProposition implies = new ImpliesProposition(a, b);
        TruthValue truthA = TruthValue.TRUE;
        TruthValue truthB = TruthValue.FALSE;
        Map<VariableProposition, TruthValue> truthValues = new HashMap<VariableProposition, TruthValue>();
        truthValues.put(a, truthA);
        truthValues.put(b, truthB);
        TruthValue expected = TruthValue.FALSE;

        // e
        TruthValue actual = implies.evaluate(truthValues);

        // t
        assertEquals("implies proposition didn't evaluate with true > false", actual, expected);
    }

    @Test
    public void testImpliesPropositionEvaluateWithFalseTrue() {
        // s
        VariableProposition a = VariableProposition.getVariableByLetter('A');
        VariableProposition b = VariableProposition.getVariableByLetter('B');
        ImpliesProposition implies = new ImpliesProposition(a, b);
        TruthValue truthA = TruthValue.FALSE;
        TruthValue truthB = TruthValue.TRUE;
        Map<VariableProposition, TruthValue> truthValues = new HashMap<VariableProposition, TruthValue>();
        truthValues.put(a, truthA);
        truthValues.put(b, truthB);
        TruthValue expected = TruthValue.TRUE;

        // e
        TruthValue actual = implies.evaluate(truthValues);

        // t
        assertEquals("implies proposition didn't evaluate with false > true", actual, expected);
    }

    @Test
    public void testImpliesPropositionEvaluateWithFalseFalse() {
        // s
        VariableProposition a = VariableProposition.getVariableByLetter('A');
        VariableProposition b = VariableProposition.getVariableByLetter('B');
        ImpliesProposition implies = new ImpliesProposition(a, b);
        TruthValue truthA = TruthValue.FALSE;
        TruthValue truthB = TruthValue.FALSE;
        Map<VariableProposition, TruthValue> truthValues = new HashMap<VariableProposition, TruthValue>();
        truthValues.put(a, truthA);
        truthValues.put(b, truthB);
        TruthValue expected = TruthValue.TRUE;

        // e
        TruthValue actual = implies.evaluate(truthValues);

        // t
        assertEquals("implies proposition didn't evaluate with false > false", actual, expected);
    }

    @Test
    public void testIfNotPropositionReplaceWorksWhenShoudlntAdd() {
        // s
        VariableProposition a = VariableProposition.getVariableByLetter('A');
        VariableProposition b = VariableProposition.getVariableByLetter('B');
        VariableProposition c = VariableProposition.getVariableByLetter('C');
        NotProposition not = new NotProposition(a);
        Map<Proposition, Proposition> map = new HashMap<Proposition, Proposition>();
        map.put(b, c);
        Proposition expected = not;

        // e
        Proposition actual = not.replace(map);

        // t
        assertEquals("Not proposition replace function didn't function properly", expected, actual);
    }

    @Test
    public void testIfNotPropositionReplaceWorksWhenShouldAdd() {
        // s
        VariableProposition a = VariableProposition.getVariableByLetter('A');
        VariableProposition b = VariableProposition.getVariableByLetter('B');
        VariableProposition c = VariableProposition.getVariableByLetter('C');
        NotProposition not = new NotProposition(a);
        Map<Proposition, Proposition> map = new HashMap<Proposition, Proposition>();
        map.put(b, c);
        Proposition expected = not;

        // e
        Proposition actual = not.replace(map);

        // t
        assertEquals("Not proposition replace function didn't function properly", expected, actual);
    }

    @Test
    public void testIfNotPropositionGetMaxDepthWorks() {
        // s
        VariableProposition a = VariableProposition.getVariableByLetter('A');
        NotProposition not = new NotProposition(a);
        int expected = 2;

        // e
        int actual = not.getMaxDepth();

        // t
        assertEquals("Not proposition get max depth function didn't function properly", expected, actual);
    }

    @Test
    public void testIfNotPropositionCopyWorks() {
        // s
        VariableProposition a = VariableProposition.getVariableByLetter('A');
        NotProposition not = new NotProposition(a);
        NotProposition expected = not;

        // e
        NotProposition actual = not.copy();

        // t
        assertEquals("Not proposition copy function didn't function properly", expected, actual);
    }

    @Test
    public void testIfVariablePropositionReplaceWorks() {
        // s
        VariableProposition a = VariableProposition.getVariableByLetter('A');
        VariableProposition b = VariableProposition.getVariableByLetter('B');
        VariableProposition c = VariableProposition.getVariableByLetter('C');
        Map<Proposition, Proposition> map = new HashMap<Proposition, Proposition>();
        map.put(b, c);
        VariableProposition expected = a;

        // e
        Proposition actual = a.replace(map);

        // t
        assertEquals("variable proposition replace function didn't function properly", expected, actual);
    }

    @Test
    public void testIfVariablePropositionGetMaxDepthWorks() {
        // s
        VariableProposition a = VariableProposition.getVariableByLetter('A');

        int expected = 1;

        // e
        int actual = a.getMaxDepth();

        // t
        assertEquals("variable proposition getmaxdepth function didn't function properly", expected, actual);
    }

    @Test
    public void testIfSentencePropositionReplaceWorks() {
        // s
        VariableProposition a = VariableProposition.getVariableByLetter('A');
        VariableProposition b = VariableProposition.getVariableByLetter('B');
        VariableProposition c = VariableProposition.getVariableByLetter('C');
        Proposition and = new AndProposition(a, b);
        Map<Proposition, Proposition> map = new HashMap<Proposition, Proposition>();
        map.put(b, c);
        Proposition expected = new AndProposition(a, c);

        // e
        Proposition actual = and.replace(map);

        // t
        assertEquals("sentence proposition replace function didn't function properly", expected, actual);
    }

    @Test
    public void testIfSentencePropositionGetMaxDepthWorks() {
        // s
        VariableProposition a = VariableProposition.getVariableByLetter('A');
        VariableProposition b = VariableProposition.getVariableByLetter('B');
        VariableProposition c = VariableProposition.getVariableByLetter('C');
        Proposition and = new AndProposition(a, b);
        Map<Proposition, Proposition> map = new HashMap<Proposition, Proposition>();
        map.put(b, c);
        int expected = 2;

        // e
        int actual = and.getMaxDepth();

        // t
        assertEquals("sentence proposition get max depth function didn't function properly", expected, actual);
    }

    @Test
    public void testIfNotPropositionEqualsDoesntWork() {
        // s
        VariableProposition a = VariableProposition.getVariableByLetter('A');
        VariableProposition b = VariableProposition.getVariableByLetter('B');
        Proposition not = new NotProposition(a);

        // e
        boolean actual = not.equals(b);

        // t
        assertFalse("not proposition should not be equal to what it's being compared to", actual);
    }

    @Test
    public void testIfNotPropositionEqualsWorks() {
        // s
        VariableProposition a = VariableProposition.getVariableByLetter('A');
        VariableProposition b = VariableProposition.getVariableByLetter('A');
        Proposition not = new NotProposition(a);
        Proposition compare = new NotProposition(b);

        // e
        boolean actual = not.equals(compare);

        // t
        assertTrue("not proposition should be equal to what it's being compared to", actual);
    }

    @Test
    public void testIfOrPropositionEqualsDoesntWork() {
        // s
        VariableProposition a = VariableProposition.getVariableByLetter('A');
        VariableProposition b = VariableProposition.getVariableByLetter('B');
        VariableProposition c = VariableProposition.getVariableByLetter('C');
        Proposition or = new OrProposition(a, b);
        Proposition other = new OrProposition(b, c);

        // e
        boolean actual = or.equals(other);

        // t
        assertFalse("or proposition should not be equal to what it's being compared to", actual);
    }

    @Test
    public void testIfOrPropositionEqualsWorks() {
        // s
        VariableProposition a = VariableProposition.getVariableByLetter('A');
        VariableProposition b = VariableProposition.getVariableByLetter('A');
        Proposition or = new OrProposition(a, b);
        Proposition other = new OrProposition(a, b);

        // e
        boolean actual = or.equals(other);

        // t
        assertTrue("or proposition should be equal to what it's being compared to", actual);
    }

    @Test
    public void testIfAndPropositionEqualsDoesntWork() {
        // s
        VariableProposition a = VariableProposition.getVariableByLetter('A');
        VariableProposition b = VariableProposition.getVariableByLetter('B');
        VariableProposition c = VariableProposition.getVariableByLetter('C');
        Proposition or = new AndProposition(a, b);
        Proposition other = new AndProposition(b, c);

        // e
        boolean actual = or.equals(other);

        // t
        assertFalse("and proposition should not be equal to what it's being compared to", actual);
    }

    @Test
    public void testIfAndPropositionEqualsWorks() {
        // s
        VariableProposition a = VariableProposition.getVariableByLetter('A');
        VariableProposition b = VariableProposition.getVariableByLetter('A');
        Proposition or = new AndProposition(a, b);
        Proposition other = new AndProposition(a, b);

        // e
        boolean actual = or.equals(other);

        // t
        assertTrue("and proposition should be equal to what it's being compared to", actual);
    }

    @Test
    public void testIfImpliesPropositionEqualsDoesntWork() {
        // s
        VariableProposition a = VariableProposition.getVariableByLetter('A');
        VariableProposition b = VariableProposition.getVariableByLetter('B');
        VariableProposition c = VariableProposition.getVariableByLetter('C');
        Proposition or = new ImpliesProposition(a, b);
        Proposition other = new ImpliesProposition(b, c);

        // e
        boolean actual = or.equals(other);

        // t
        assertFalse("implies proposition should not be equal to what it's being compared to", actual);
    }

    @Test
    public void testIfImpliesPropositionEqualsWorks() {
        // s
        VariableProposition a = VariableProposition.getVariableByLetter('A');
        VariableProposition b = VariableProposition.getVariableByLetter('A');
        Proposition or = new ImpliesProposition(a, b);
        Proposition other = new ImpliesProposition(a, b);

        // e
        boolean actual = or.equals(other);

        // t
        assertTrue("implies proposition should be equal to what it's being compared to", actual);
    }

    @Test
    public void testEvaluateForANestedProposition() {
        // s
        VariableProposition a = VariableProposition.getVariableByLetter('A');
        VariableProposition b = VariableProposition.getVariableByLetter('B');
        VariableProposition c = VariableProposition.getVariableByLetter('C');
        Proposition implies = new ImpliesProposition(a, b);
        Proposition and = new AndProposition(implies, c);
        TruthValue truthA = TruthValue.FALSE;
        TruthValue truthB = TruthValue.FALSE;
        Map<VariableProposition, TruthValue> truthValues = new HashMap<VariableProposition, TruthValue>();
        truthValues.put(a, truthA);
        truthValues.put(b, truthB);
        TruthValue expected = TruthValue.FALSE;

        // e
        TruthValue actual = and.evaluate(truthValues);

        // t
        assertEquals("Evaluate is not working for nested proposition", expected, actual);
    }

    @Test
    public void testEvaluateForADoubleNestedProposition() {
        // s
        VariableProposition a = VariableProposition.getVariableByLetter('A');
        VariableProposition b = VariableProposition.getVariableByLetter('B');
        VariableProposition c = VariableProposition.getVariableByLetter('C');
        VariableProposition d = VariableProposition.getVariableByLetter('D');
        Proposition one = new AndProposition(a, b);
        Proposition two = new AndProposition(c, d);
        Proposition total = new ImpliesProposition(one, two);
        TruthValue truthA = TruthValue.FALSE;
        TruthValue truthB = TruthValue.FALSE;
        Map<VariableProposition, TruthValue> truthValues = new HashMap<VariableProposition, TruthValue>();
        truthValues.put(a, truthA);
        truthValues.put(b, truthB);
        TruthValue expected = TruthValue.TRUE;

        // e
        TruthValue actual = total.evaluate(truthValues);

        // t
        assertEquals("Evaluate is not working for double nested proposition", expected, actual);
    }
}
