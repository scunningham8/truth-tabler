package edu.unl.raikes.discrete.logicproofs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import edu.unl.raikes.discrete.PropositionParser;
import edu.unl.raikes.discrete.propositions.AndProposition;
import edu.unl.raikes.discrete.propositions.Proposition;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.junit.Test;

public class PropositionalLawApplierTest {

    @Test
    public void testTruthMatchesTemplateHappyPath() {
        // setup

        Proposition truth1 = PropositionParser.parse("(P&Q)&R");

        Proposition template = PropositionParser.parse("a&b");

        // execute
        boolean actual = PropositionalLawApplier.truthMatchesTemplate(truth1, template);

        assertTrue("listContainsEquation did not find match", actual);
    }

    @Test
    public void testTruthMatchesTemplateHappyPathMultiLevel() {
        // setup

        Proposition truth1 = PropositionParser.parse("(P|(Q&U))&(R>P)");

        Proposition template = PropositionParser.parse("(a|c)&(b>a)");

        // execute
        boolean actual = PropositionalLawApplier.truthMatchesTemplate(truth1, template);

        assertTrue("listContainsEquation did not find match", actual);
    }

    @Test
    public void testTruthMatchesTemplateNoMatch() {
        // setup

        Proposition truth1 = PropositionParser.parse("(P&Q)&R");

        Proposition template = PropositionParser.parse("a>b");

        // execute
        boolean actual = PropositionalLawApplier.truthMatchesTemplate(truth1, template);

        assertFalse("listContainsEquation found match when it shouldn't have", actual);
    }

    @Test
    public void testTruthMatchesTemplateWithMapHappyPath() {
        // setup

        AndProposition truth1 = (AndProposition) (PropositionParser.parse("(P&Q)&R"));
        AndProposition template = (AndProposition) (PropositionParser.parse("a&b"));

        Map<Proposition, Proposition> input = new TreeMap<Proposition, Proposition>();
        input.put(template.getLeft(), truth1.getLeft());

        // execute
        boolean actual = PropositionalLawApplier.truthMatchesTemplateWithMap(input, truth1, template);

        assertTrue("match was not found", actual);
    }

    @Test
    public void testTruthMatchesTemplateWithMapNoMatch() {
        // setup

        AndProposition truth1 = (AndProposition) (PropositionParser.parse("(P&Q)&R"));
        AndProposition template = (AndProposition) (PropositionParser.parse("a&b"));

        Map<Proposition, Proposition> input = new TreeMap<Proposition, Proposition>();
        input.put(template.getLeft(), truth1.getRight());

        // execute
        boolean actual = PropositionalLawApplier.truthMatchesTemplateWithMap(input, truth1, template);

        assertFalse("match was found between two templates with similar structure but incorrect mapping", actual);
    }

    @Test
    public void testGetAbstractToTruthVariableMappingOneVariable() {
        // setup

        Proposition truth1 = PropositionParser.parse("(P&Q)&R");
        Proposition template = PropositionParser.parse("a");

        Map<Proposition, Proposition> expected = new TreeMap<Proposition, Proposition>();

        expected.put(template, truth1);

        // execute
        Map<Proposition, Proposition> actual = PropositionalLawApplier.getAbstractToTruthVariableMapping(truth1,
                template);

        assertEquals("listContainsEquation did not find match", actual, expected);
    }

    @Test
    public void testGetAbstractToTruthVariableMappingTwoVariables() {
        // setup

        AndProposition truth1 = (AndProposition) (PropositionParser.parse("(P&Q)&R"));
        AndProposition template = (AndProposition) (PropositionParser.parse("a&b"));

        Map<Proposition, Proposition> expected = new TreeMap<Proposition, Proposition>();

        expected.put(template.getLeft(), truth1.getLeft());
        expected.put(template.getRight(), truth1.getRight());

        // execute
        Map<Proposition, Proposition> actual = PropositionalLawApplier.getAbstractToTruthVariableMapping(truth1,
                template);

        assertEquals("listContainsEquation did not find match", actual, expected);
    }

    @Test
    public void updateGetAbstractToTruthVariableMappingFirstVariableGiven() {
        // setup

        AndProposition truth1 = (AndProposition) (PropositionParser.parse("(P&Q)&R"));
        AndProposition template = (AndProposition) (PropositionParser.parse("a&b"));

        Map<Proposition, Proposition> input = new TreeMap<Proposition, Proposition>();

        input.put(template.getLeft(), truth1.getLeft());

        Map<Proposition, Proposition> expected = new TreeMap<Proposition, Proposition>();
        expected.put(template.getLeft(), truth1.getLeft());
        expected.put(template.getRight(), truth1.getRight());

        // execute
        Map<Proposition, Proposition> actual = PropositionalLawApplier.updateAbstractToTruthVariableMapping(expected,
                truth1, template);

        // test
        assertEquals("incorrectly mapped values when the unknown value was in right position", actual, expected);
    }

    @Test
    public void testUpdateAbstractToTruthVariableMappingSecondVariableGiven() {
        // setup

        AndProposition truth1 = (AndProposition) (PropositionParser.parse("(P&Q)&R"));
        AndProposition template = (AndProposition) (PropositionParser.parse("a&b"));

        Map<Proposition, Proposition> input = new TreeMap<Proposition, Proposition>();
        input.put(template.getRight(), truth1.getRight());

        Map<Proposition, Proposition> expected = new TreeMap<Proposition, Proposition>();
        expected.put(template.getLeft(), truth1.getLeft());
        expected.put(template.getRight(), truth1.getRight());

        // execute
        Map<Proposition, Proposition> actual = PropositionalLawApplier.updateAbstractToTruthVariableMapping(expected,
                truth1, template);

        // test
        assertEquals("incorrectly mapped values when the unknown value was in left position", actual, expected);
    }

    @Test
    public void testGetNewTruthHappyPath() {
        // setup

        Proposition template = PropositionParser.parse("a&b");

        Map<Proposition, Proposition> input = new TreeMap<Proposition, Proposition>();
        input.put(PropositionParser.parse("a"), PropositionParser.parse("P&Q"));
        input.put(PropositionParser.parse("b"), PropositionParser.parse("R"));

        Proposition expected = PropositionParser.parse("(P&Q)&R");

        // execute
        Proposition actual = PropositionalLawApplier.makeNewTruth(input, template);

        // test
        assertEquals("did not correctly make new truth from template", actual, expected);
    }

    @Test
    public void testApplyToIdempotentOrHappyPath() {
        // setup the list of hypotheses
        List<PropositionalEquation> allTruths = new ArrayList<PropositionalEquation>();
        PropositionalEquation truth = new PropositionalEquation(PropositionParser.parse("-(P&Q)|-(P&Q)"),
                PropositionalLaw.HYPOTHESIS, new ArrayList<PropositionalEquation>());
        allTruths.add(truth);

        // setup expected
        List<PropositionalEquation> parents = new ArrayList<PropositionalEquation>();
        parents.add(truth);

        List<PropositionalEquation> expected = new ArrayList<PropositionalEquation>();
        expected.add(new PropositionalEquation(PropositionParser.parse("-(P&Q)"), PropositionalLaw.IDEMPOTENT_OR_LAW,
                parents));

        // execute actual
        List<PropositionalEquation> actual = PropositionalLawApplier.applyTo(PropositionalLaw.IDEMPOTENT_OR_LAW, truth,
                allTruths);

        assertEquals("Could not apply Idempotent law w.r.t. OR, when the law should be applicable.", expected, actual);
    }

    @Test
    public void testApplyToIdempotentOrNoMatch() {
        // setup the list of hypotheses
        List<PropositionalEquation> allTruths = new ArrayList<PropositionalEquation>();
        PropositionalEquation truth = new PropositionalEquation(PropositionParser.parse("-(P&Q)|-(P&R)"),
                PropositionalLaw.HYPOTHESIS, new ArrayList<PropositionalEquation>());
        allTruths.add(truth);

        // execute actual
        List<PropositionalEquation> actual = PropositionalLawApplier.applyTo(PropositionalLaw.IDEMPOTENT_OR_LAW, truth,
                allTruths);

        assertNull("Applied Idempotent law w.r.t. OR, when the law should not be applicable.", actual);
    }

    @Test
    public void testApplyToResolutionHappyPath() {
        // set up list of hypotheses
        List<PropositionalEquation> allTruths = new ArrayList<PropositionalEquation>();
        PropositionalEquation truth = new PropositionalEquation(PropositionParser.parse("(P>R)|-Q"),
                PropositionalLaw.HYPOTHESIS, new ArrayList<PropositionalEquation>());
        PropositionalEquation truth2 = new PropositionalEquation(PropositionParser.parse("-(P>R)|(R&S)"),
                PropositionalLaw.HYPOTHESIS, new ArrayList<PropositionalEquation>());
        allTruths.add(truth);
        allTruths.add(truth2);

        // set up expected
        List<PropositionalEquation> parents = new ArrayList<PropositionalEquation>();
        parents.add(truth);
        parents.add(truth2);

        List<PropositionalEquation> expected = new ArrayList<PropositionalEquation>();
        expected.add(new PropositionalEquation(PropositionParser.parse("-Q|(R&S)"), PropositionalLaw.RESOLUTION_LAW,
                parents));

        // execute actual
        List<PropositionalEquation> actual = PropositionalLawApplier.applyTo(PropositionalLaw.RESOLUTION_LAW, truth,
                allTruths);

        assertEquals("Could not apply Resolution rule, when the rule should be applicable.", expected, actual);
    }

    @Test
    public void testApplyToResolutionNoMatch() {
        // set up list of hypotheses
        List<PropositionalEquation> allTruths = new ArrayList<PropositionalEquation>();
        PropositionalEquation truth = new PropositionalEquation(PropositionParser.parse("(P>R)|-Q"),
                PropositionalLaw.HYPOTHESIS, new ArrayList<PropositionalEquation>());
        PropositionalEquation truth2 = new PropositionalEquation(PropositionParser.parse("-(P>R)&(R&S)"),
                PropositionalLaw.HYPOTHESIS, new ArrayList<PropositionalEquation>());
        allTruths.add(truth);
        allTruths.add(truth2);

        // execute actual
        List<PropositionalEquation> actual = PropositionalLawApplier.applyTo(PropositionalLaw.RESOLUTION_LAW, truth,
                allTruths);

        assertNull("Applied Resolution, when the rule should not be applicable.", actual);
    }

    @Test
    public void testApplyToDysjunctiveSyllogismHappyPath() {
        // set up list of hypotheses
        List<PropositionalEquation> allTruths = new ArrayList<PropositionalEquation>();
        PropositionalEquation truth = new PropositionalEquation(PropositionParser.parse("P|Q"),
                PropositionalLaw.HYPOTHESIS, new ArrayList<PropositionalEquation>());
        PropositionalEquation truth2 = new PropositionalEquation(PropositionParser.parse("-P"),
                PropositionalLaw.HYPOTHESIS, new ArrayList<PropositionalEquation>());
        allTruths.add(truth);
        allTruths.add(truth2);

        // set up expected
        List<PropositionalEquation> parents = new ArrayList<PropositionalEquation>();
        parents.add(truth);
        parents.add(truth2);

        List<PropositionalEquation> expected = new ArrayList<PropositionalEquation>();
        expected.add(new PropositionalEquation(PropositionParser.parse("Q"), PropositionalLaw.DYSJUNCTIVE_SYLLOGISM_LAW,
                parents));

        // execute actual
        List<PropositionalEquation> actual = PropositionalLawApplier.applyTo(PropositionalLaw.DYSJUNCTIVE_SYLLOGISM_LAW,
                truth, allTruths);

        assertEquals("Could not apply Dysjunctive Syllogism rule, when the rule should be applicable.", expected,
                actual);
    }

    @Test
    public void testApplyToDysjunctivesyllogismNoMatch() {
        // set up list of hypotheses
        List<PropositionalEquation> allTruths = new ArrayList<PropositionalEquation>();
        PropositionalEquation truth = new PropositionalEquation(PropositionParser.parse("(P>R)|-Q"),
                PropositionalLaw.HYPOTHESIS, new ArrayList<PropositionalEquation>());
        PropositionalEquation truth2 = new PropositionalEquation(PropositionParser.parse("-(P>R)&(R&S)"),
                PropositionalLaw.HYPOTHESIS, new ArrayList<PropositionalEquation>());
        allTruths.add(truth);
        allTruths.add(truth2);

        // execute actual
        List<PropositionalEquation> actual = PropositionalLawApplier.applyTo(PropositionalLaw.DYSJUNCTIVE_SYLLOGISM_LAW,
                truth, allTruths);

        assertNull("Applied Dysjunctive Syllogism, when the rule should not be applicable.", actual);
    }

    @Test
    public void testApplyToHypotheicalSyllogismHappyPath() {
        // set up list of hypotheses
        List<PropositionalEquation> allTruths = new ArrayList<PropositionalEquation>();
        PropositionalEquation truth = new PropositionalEquation(PropositionParser.parse("P>Q"),
                PropositionalLaw.HYPOTHESIS, new ArrayList<PropositionalEquation>());
        PropositionalEquation truth2 = new PropositionalEquation(PropositionParser.parse("Q>R"),
                PropositionalLaw.HYPOTHESIS, new ArrayList<PropositionalEquation>());
        allTruths.add(truth);
        allTruths.add(truth2);

        // set up expected
        List<PropositionalEquation> parents = new ArrayList<PropositionalEquation>();
        parents.add(truth);
        parents.add(truth2);

        List<PropositionalEquation> expected = new ArrayList<PropositionalEquation>();
        expected.add(new PropositionalEquation(PropositionParser.parse("P>R"),
                PropositionalLaw.HYPOTHETICAL_SYLLOGISM_LAW, parents));

        // execute actual
        List<PropositionalEquation> actual = PropositionalLawApplier
                .applyTo(PropositionalLaw.HYPOTHETICAL_SYLLOGISM_LAW, truth, allTruths);

        assertEquals("Could not apply Hypothetical Syllogism rule, when the rule should be applicable.", expected,
                actual);
    }

    @Test
    public void testApplyToHypotheticalSyllogismNoMatch() {
        // set up list of hypotheses
        List<PropositionalEquation> allTruths = new ArrayList<PropositionalEquation>();
        PropositionalEquation truth = new PropositionalEquation(PropositionParser.parse("(P>R)|-Q"),
                PropositionalLaw.HYPOTHESIS, new ArrayList<PropositionalEquation>());
        PropositionalEquation truth2 = new PropositionalEquation(PropositionParser.parse("-(P>R)&(R&S)"),
                PropositionalLaw.HYPOTHESIS, new ArrayList<PropositionalEquation>());
        allTruths.add(truth);
        allTruths.add(truth2);

        // execute actual
        List<PropositionalEquation> actual = PropositionalLawApplier
                .applyTo(PropositionalLaw.HYPOTHETICAL_SYLLOGISM_LAW, truth, allTruths);

        assertNull("Applied Hypothetical Syllogism, when the rule should not be applicable.", actual);
    }
    
    @Test
    public void testApplyToConjunctionHappyPath() {
        // set up list of hypotheses
        List<PropositionalEquation> allTruths = new ArrayList<PropositionalEquation>();
        PropositionalEquation truth = new PropositionalEquation(PropositionParser.parse("P"),
                PropositionalLaw.HYPOTHESIS, new ArrayList<PropositionalEquation>());
        PropositionalEquation truth2 = new PropositionalEquation(PropositionParser.parse("Q"),
                PropositionalLaw.HYPOTHESIS, new ArrayList<PropositionalEquation>());
        allTruths.add(truth);
        allTruths.add(truth2);

        // set up expected
        List<PropositionalEquation> parents = new ArrayList<PropositionalEquation>();
        parents.add(truth);
        parents.add(truth2);

        List<PropositionalEquation> expected = new ArrayList<PropositionalEquation>();
        expected.add(new PropositionalEquation(PropositionParser.parse("P&Q"), PropositionalLaw.CONJUNCTION_LAW,
                parents));

        // execute actual
        List<PropositionalEquation> actual = PropositionalLawApplier.applyTo(PropositionalLaw.CONJUNCTION_LAW,
                truth, allTruths);

        assertEquals("Could not apply Conjuncion rule, when the rule should be applicable.", expected, actual);
    }
    
    @Test
    public void testApplyToSimplificationHappyPath() {
        // set up list of hypotheses
        List<PropositionalEquation> allTruths = new ArrayList<PropositionalEquation>();
        PropositionalEquation truth = new PropositionalEquation(PropositionParser.parse("P&Q"),
                PropositionalLaw.HYPOTHESIS, new ArrayList<PropositionalEquation>());
        allTruths.add(truth);

        // set up expected
        List<PropositionalEquation> parents = new ArrayList<PropositionalEquation>();
        parents.add(truth);

        List<PropositionalEquation> expected = new ArrayList<PropositionalEquation>();
        expected.add(new PropositionalEquation(PropositionParser.parse("P"),
                PropositionalLaw.SIMPLIFICATION_LAW, parents));

        // execute actual
        List<PropositionalEquation> actual = PropositionalLawApplier
                .applyTo(PropositionalLaw.SIMPLIFICATION_LAW, truth, allTruths);

        assertEquals("Could not apply simplification rule, when the rule should be applicable.", expected,
                actual);
    }

    @Test
    public void testApplyToSimplificationNoMatch() {
        // set up list of hypotheses
        List<PropositionalEquation> allTruths = new ArrayList<PropositionalEquation>();
        PropositionalEquation truth = new PropositionalEquation(PropositionParser.parse("(P>R)|-Q"),
                PropositionalLaw.HYPOTHESIS, new ArrayList<PropositionalEquation>());
        PropositionalEquation truth2 = new PropositionalEquation(PropositionParser.parse("-(P>R)&(R&S)"),
                PropositionalLaw.HYPOTHESIS, new ArrayList<PropositionalEquation>());
        allTruths.add(truth);
        allTruths.add(truth2);

        // execute actual
        List<PropositionalEquation> actual = PropositionalLawApplier
                .applyTo(PropositionalLaw.SIMPLIFICATION_LAW, truth, allTruths);

        assertNull("Applied Simplification, when the rule should not be applicable.", actual);
    }
    
    @Test
    public void testApplyToModusTollensHappyPath() {
        // set up list of hypotheses
        List<PropositionalEquation> allTruths = new ArrayList<PropositionalEquation>();
        PropositionalEquation truth = new PropositionalEquation(PropositionParser.parse("P>Q"),
                PropositionalLaw.HYPOTHESIS, new ArrayList<PropositionalEquation>());
        PropositionalEquation truth2 = new PropositionalEquation(PropositionParser.parse("-Q"),
                PropositionalLaw.HYPOTHESIS, new ArrayList<PropositionalEquation>());
        allTruths.add(truth);
        allTruths.add(truth2);

        // set up expected
        List<PropositionalEquation> parents = new ArrayList<PropositionalEquation>();
        parents.add(truth);
        parents.add(truth2);

        List<PropositionalEquation> expected = new ArrayList<PropositionalEquation>();
        expected.add(new PropositionalEquation(PropositionParser.parse("-P"),
                PropositionalLaw.MODUS_TOLLENS_LAW, parents));

        // execute actual
        List<PropositionalEquation> actual = PropositionalLawApplier
                .applyTo(PropositionalLaw.MODUS_TOLLENS_LAW, truth, allTruths);

        assertEquals("Could not apply modus tollens rule, when the rule should be applicable.", expected,
                actual);
    }

    @Test
    public void testApplyToModusTollensNoMatch() {
        // set up list of hypotheses
        List<PropositionalEquation> allTruths = new ArrayList<PropositionalEquation>();
        PropositionalEquation truth = new PropositionalEquation(PropositionParser.parse("(P>R)|-Q"),
                PropositionalLaw.HYPOTHESIS, new ArrayList<PropositionalEquation>());
        PropositionalEquation truth2 = new PropositionalEquation(PropositionParser.parse("-(P>R)&(R&S)"),
                PropositionalLaw.HYPOTHESIS, new ArrayList<PropositionalEquation>());
        allTruths.add(truth);
        allTruths.add(truth2);

        // execute actual
        List<PropositionalEquation> actual = PropositionalLawApplier
                .applyTo(PropositionalLaw.MODUS_TOLLENS_LAW, truth, allTruths);

        assertNull("Applied modus tollens, when the rule should not be applicable.", actual);
    }
    
    @Test
    public void testApplyToModusPonensHappyPath() {
        // set up list of hypotheses
        List<PropositionalEquation> allTruths = new ArrayList<PropositionalEquation>();
        PropositionalEquation truth = new PropositionalEquation(PropositionParser.parse("P>Q"),
                PropositionalLaw.HYPOTHESIS, new ArrayList<PropositionalEquation>());
        PropositionalEquation truth2 = new PropositionalEquation(PropositionParser.parse("P"),
                PropositionalLaw.HYPOTHESIS, new ArrayList<PropositionalEquation>());
        allTruths.add(truth);
        allTruths.add(truth2);

        // set up expected
        List<PropositionalEquation> parents = new ArrayList<PropositionalEquation>();
        parents.add(truth);
        parents.add(truth2);

        List<PropositionalEquation> expected = new ArrayList<PropositionalEquation>();
        expected.add(new PropositionalEquation(PropositionParser.parse("Q"),
                PropositionalLaw.MODUS_PONENS_LAW, parents));

        // execute actual
        List<PropositionalEquation> actual = PropositionalLawApplier
                .applyTo(PropositionalLaw.MODUS_PONENS_LAW, truth, allTruths);

        assertEquals("Could not apply modus ponens rule, when the rule should be applicable.", expected,
                actual);
    }

    @Test
    public void testApplyToModusPonensNoMatch() {
        // set up list of hypotheses
        List<PropositionalEquation> allTruths = new ArrayList<PropositionalEquation>();
        PropositionalEquation truth = new PropositionalEquation(PropositionParser.parse("(P>R)|-Q"),
                PropositionalLaw.HYPOTHESIS, new ArrayList<PropositionalEquation>());
        PropositionalEquation truth2 = new PropositionalEquation(PropositionParser.parse("-(P>R)&(R&S)"),
                PropositionalLaw.HYPOTHESIS, new ArrayList<PropositionalEquation>());
        allTruths.add(truth);
        allTruths.add(truth2);

        // execute actual
        List<PropositionalEquation> actual = PropositionalLawApplier
                .applyTo(PropositionalLaw.MODUS_PONENS_LAW, truth, allTruths);

        assertNull("Applied Modus Ponens, when the rule should not be applicable.", actual);
    }
    
    @Test
    public void testApplyToConditionalIdentityInverseHappyPath() {
        // set up list of hypotheses
        List<PropositionalEquation> allTruths = new ArrayList<PropositionalEquation>();
        PropositionalEquation truth = new PropositionalEquation(PropositionParser.parse("-P|Q"),
                PropositionalLaw.HYPOTHESIS, new ArrayList<PropositionalEquation>());
        allTruths.add(truth);

        // set up expected
        List<PropositionalEquation> parents = new ArrayList<PropositionalEquation>();
        parents.add(truth);

        List<PropositionalEquation> expected = new ArrayList<PropositionalEquation>();
        expected.add(new PropositionalEquation(PropositionParser.parse("P>Q"),
                PropositionalLaw.CONDITIONAL_IDENTITY_INVERSE_LAW, parents));

        // execute actual
        List<PropositionalEquation> actual = PropositionalLawApplier
                .applyTo(PropositionalLaw.CONDITIONAL_IDENTITY_INVERSE_LAW, truth, allTruths);

        assertEquals("Could not apply conditional identity inverse rule, when the rule should be applicable.", expected,
                actual);
    }

    @Test
    public void testApplyToConditionalIdentityInverseNoMatch() {
        // set up list of hypotheses
        List<PropositionalEquation> allTruths = new ArrayList<PropositionalEquation>();
        PropositionalEquation truth = new PropositionalEquation(PropositionParser.parse("(P>R)|-Q"),
                PropositionalLaw.HYPOTHESIS, new ArrayList<PropositionalEquation>());
        PropositionalEquation truth2 = new PropositionalEquation(PropositionParser.parse("-(P>R)&(R&S)"),
                PropositionalLaw.HYPOTHESIS, new ArrayList<PropositionalEquation>());
        allTruths.add(truth);
        allTruths.add(truth2);

        // execute actual
        List<PropositionalEquation> actual = PropositionalLawApplier
                .applyTo(PropositionalLaw.CONDITIONAL_IDENTITY_INVERSE_LAW, truth, allTruths);

        assertNull("Applied conditional identity inverse, when the rule should not be applicable.", actual);
    }
    
    @Test
    public void testApplyToConditionalIdentityHappyPath() {
        // set up list of hypotheses
        List<PropositionalEquation> allTruths = new ArrayList<PropositionalEquation>();
        PropositionalEquation truth = new PropositionalEquation(PropositionParser.parse("P>Q"),
                PropositionalLaw.HYPOTHESIS, new ArrayList<PropositionalEquation>());
        allTruths.add(truth);

        // set up expected
        List<PropositionalEquation> parents = new ArrayList<PropositionalEquation>();
        parents.add(truth);

        List<PropositionalEquation> expected = new ArrayList<PropositionalEquation>();
        expected.add(new PropositionalEquation(PropositionParser.parse("-P|Q"),
                PropositionalLaw.CONDITIONAL_IDENTITY_LAW, parents));

        // execute actual
        List<PropositionalEquation> actual = PropositionalLawApplier
                .applyTo(PropositionalLaw.CONDITIONAL_IDENTITY_LAW, truth, allTruths);

        assertEquals("Could not apply conditional identity rule, when the rule should be applicable.", expected,
                actual);
    }

    @Test
    public void testApplyToConditionalIdentityNoMatch() {
        // set up list of hypotheses
        List<PropositionalEquation> allTruths = new ArrayList<PropositionalEquation>();
        PropositionalEquation truth = new PropositionalEquation(PropositionParser.parse("(P>R)|-Q"),
                PropositionalLaw.HYPOTHESIS, new ArrayList<PropositionalEquation>());
        PropositionalEquation truth2 = new PropositionalEquation(PropositionParser.parse("-(P>R)&(R&S)"),
                PropositionalLaw.HYPOTHESIS, new ArrayList<PropositionalEquation>());
        allTruths.add(truth);
        allTruths.add(truth2);

        // execute actual
        List<PropositionalEquation> actual = PropositionalLawApplier
                .applyTo(PropositionalLaw.CONDITIONAL_IDENTITY_LAW, truth, allTruths);

        assertNull("Applied conditional identity, when the rule should not be applicable.", actual);
    }
    
    @Test
    public void testApplyToAbsorptionOrHappyPath() {
        // set up list of hypotheses
        List<PropositionalEquation> allTruths = new ArrayList<PropositionalEquation>();
        PropositionalEquation truth = new PropositionalEquation(PropositionParser.parse("P|(P&Q)"),
                PropositionalLaw.HYPOTHESIS, new ArrayList<PropositionalEquation>());
        allTruths.add(truth);

        // set up expected
        List<PropositionalEquation> parents = new ArrayList<PropositionalEquation>();
        parents.add(truth);

        List<PropositionalEquation> expected = new ArrayList<PropositionalEquation>();
        expected.add(new PropositionalEquation(PropositionParser.parse("P"),
                PropositionalLaw.ABSORPTION_OR_LAW, parents));

        // execute actual
        List<PropositionalEquation> actual = PropositionalLawApplier
                .applyTo(PropositionalLaw.ABSORPTION_OR_LAW, truth, allTruths);

        assertEquals("Could not apply Absorption Or rule, when the rule should be applicable.", expected,
                actual);
    }

    @Test
    public void testApplyToAbsorptionOrNoMatch() {
        // set up list of hypotheses
        List<PropositionalEquation> allTruths = new ArrayList<PropositionalEquation>();
        PropositionalEquation truth = new PropositionalEquation(PropositionParser.parse("(P>R)|-Q"),
                PropositionalLaw.HYPOTHESIS, new ArrayList<PropositionalEquation>());
        PropositionalEquation truth2 = new PropositionalEquation(PropositionParser.parse("-(P>R)&(R&S)"),
                PropositionalLaw.HYPOTHESIS, new ArrayList<PropositionalEquation>());
        allTruths.add(truth);
        allTruths.add(truth2);

        // execute actual
        List<PropositionalEquation> actual = PropositionalLawApplier
                .applyTo(PropositionalLaw.ABSORPTION_OR_LAW, truth, allTruths);

        assertNull("Applied Absorption Or rule, when the rule should not be applicable.", actual);
    }
    
    @Test
    public void testApplyToAbsorptionAndHappyPath() {
        // set up list of hypotheses
        List<PropositionalEquation> allTruths = new ArrayList<PropositionalEquation>();
        PropositionalEquation truth = new PropositionalEquation(PropositionParser.parse("P&(P|Q)"),
                PropositionalLaw.HYPOTHESIS, new ArrayList<PropositionalEquation>());
        allTruths.add(truth);

        // set up expected
        List<PropositionalEquation> parents = new ArrayList<PropositionalEquation>();
        parents.add(truth);

        List<PropositionalEquation> expected = new ArrayList<PropositionalEquation>();
        expected.add(new PropositionalEquation(PropositionParser.parse("P"),
                PropositionalLaw.ABSORPTION_AND_LAW, parents));

        // execute actual
        List<PropositionalEquation> actual = PropositionalLawApplier
                .applyTo(PropositionalLaw.ABSORPTION_AND_LAW, truth, allTruths);

        assertEquals("Could not apply absorption and rule, when the rule should be applicable.", expected,
                actual);
    }

    @Test
    public void testApplyToAbsorptionAndNoMatch() {
        // set up list of hypotheses
        List<PropositionalEquation> allTruths = new ArrayList<PropositionalEquation>();
        PropositionalEquation truth = new PropositionalEquation(PropositionParser.parse("(P>R)|-Q"),
                PropositionalLaw.HYPOTHESIS, new ArrayList<PropositionalEquation>());
        PropositionalEquation truth2 = new PropositionalEquation(PropositionParser.parse("-(P>R)&(R&S)"),
                PropositionalLaw.HYPOTHESIS, new ArrayList<PropositionalEquation>());
        allTruths.add(truth);
        allTruths.add(truth2);

        // execute actual
        List<PropositionalEquation> actual = PropositionalLawApplier
                .applyTo(PropositionalLaw.ABSORPTION_AND_LAW, truth, allTruths);

        assertNull("Applied Absorption and, when the rule should not be applicable.", actual);
    }
    
    @Test
    public void testApplyToDemorgansAndInverseHappyPath() {
        // set up list of hypotheses
        List<PropositionalEquation> allTruths = new ArrayList<PropositionalEquation>();
        PropositionalEquation truth = new PropositionalEquation(PropositionParser.parse("-P|-Q"),
                PropositionalLaw.HYPOTHESIS, new ArrayList<PropositionalEquation>());
        allTruths.add(truth);

        // set up expected
        List<PropositionalEquation> parents = new ArrayList<PropositionalEquation>();
        parents.add(truth);

        List<PropositionalEquation> expected = new ArrayList<PropositionalEquation>();
        expected.add(new PropositionalEquation(PropositionParser.parse("-(P&Q)"),
                PropositionalLaw.DEMORGANS_AND_INVERSE_LAW, parents));

        // execute actual
        List<PropositionalEquation> actual = PropositionalLawApplier
                .applyTo(PropositionalLaw.DEMORGANS_AND_INVERSE_LAW, truth, allTruths);

        assertEquals("Could not apply demorgans and inverse rule, when the rule should be applicable.", expected,
                actual);
    }

    @Test
    public void testApplyToDemorgansAndInverseNoMatch() {
        // set up list of hypotheses
        List<PropositionalEquation> allTruths = new ArrayList<PropositionalEquation>();
        PropositionalEquation truth = new PropositionalEquation(PropositionParser.parse("(P>R)|-Q"),
                PropositionalLaw.HYPOTHESIS, new ArrayList<PropositionalEquation>());
        PropositionalEquation truth2 = new PropositionalEquation(PropositionParser.parse("-(P>R)&(R&S)"),
                PropositionalLaw.HYPOTHESIS, new ArrayList<PropositionalEquation>());
        allTruths.add(truth);
        allTruths.add(truth2);

        // execute actual
        List<PropositionalEquation> actual = PropositionalLawApplier
                .applyTo(PropositionalLaw.DEMORGANS_AND_INVERSE_LAW, truth, allTruths);

        assertNull("Applied demorgans and inverse, when the rule should not be applicable.", actual);
    }
    
    @Test
    public void testApplyToDemorgansAndHappyPath() {
        // set up list of hypotheses
        List<PropositionalEquation> allTruths = new ArrayList<PropositionalEquation>();
        PropositionalEquation truth = new PropositionalEquation(PropositionParser.parse("-(P&Q)"),
                PropositionalLaw.HYPOTHESIS, new ArrayList<PropositionalEquation>());
        allTruths.add(truth);

        // set up expected
        List<PropositionalEquation> parents = new ArrayList<PropositionalEquation>();
        parents.add(truth);

        List<PropositionalEquation> expected = new ArrayList<PropositionalEquation>();
        expected.add(new PropositionalEquation(PropositionParser.parse("-P|-Q"),
                PropositionalLaw.DEMORGANS_AND_LAW, parents));

        // execute actual
        List<PropositionalEquation> actual = PropositionalLawApplier
                .applyTo(PropositionalLaw.DEMORGANS_AND_LAW, truth, allTruths);

        assertEquals("Could not apply demorgans and rule, when the rule should be applicable.", expected,
                actual);
    }

    @Test
    public void testApplyToDemorgansAndNoMatch() {
        // set up list of hypotheses
        List<PropositionalEquation> allTruths = new ArrayList<PropositionalEquation>();
        PropositionalEquation truth = new PropositionalEquation(PropositionParser.parse("(P>R)|-Q"),
                PropositionalLaw.HYPOTHESIS, new ArrayList<PropositionalEquation>());
        PropositionalEquation truth2 = new PropositionalEquation(PropositionParser.parse("-(P>R)&(R&S)"),
                PropositionalLaw.HYPOTHESIS, new ArrayList<PropositionalEquation>());
        allTruths.add(truth);
        allTruths.add(truth2);

        // execute actual
        List<PropositionalEquation> actual = PropositionalLawApplier
                .applyTo(PropositionalLaw.DEMORGANS_AND_LAW, truth, allTruths);

        assertNull("Applied demorgans and, when the rule should not be applicable.", actual);
    }
    
    @Test
    public void testApplyToDemorgansOrInverseHappyPath() {
        // set up list of hypotheses
        List<PropositionalEquation> allTruths = new ArrayList<PropositionalEquation>();
        PropositionalEquation truth = new PropositionalEquation(PropositionParser.parse("-P&-Q"),
                PropositionalLaw.HYPOTHESIS, new ArrayList<PropositionalEquation>());
        allTruths.add(truth);

        // set up expected
        List<PropositionalEquation> parents = new ArrayList<PropositionalEquation>();
        parents.add(truth);

        List<PropositionalEquation> expected = new ArrayList<PropositionalEquation>();
        expected.add(new PropositionalEquation(PropositionParser.parse("-(P|Q)"),
                PropositionalLaw.DEMORGANS_OR_INVERSE_LAW, parents));

        // execute actual
        List<PropositionalEquation> actual = PropositionalLawApplier
                .applyTo(PropositionalLaw.DEMORGANS_OR_INVERSE_LAW, truth, allTruths);

        assertEquals("Could not apply demorgans or inverse rule, when the rule should be applicable.", expected,
                actual);
    }

    @Test
    public void testApplyToDemorgansOrInverseNoMatch() {
        // set up list of hypotheses
        List<PropositionalEquation> allTruths = new ArrayList<PropositionalEquation>();
        PropositionalEquation truth = new PropositionalEquation(PropositionParser.parse("(P>R)|-Q"),
                PropositionalLaw.HYPOTHESIS, new ArrayList<PropositionalEquation>());
        PropositionalEquation truth2 = new PropositionalEquation(PropositionParser.parse("-(P>R)&(R&S)"),
                PropositionalLaw.HYPOTHESIS, new ArrayList<PropositionalEquation>());
        allTruths.add(truth);
        allTruths.add(truth2);

        // execute actual
        List<PropositionalEquation> actual = PropositionalLawApplier
                .applyTo(PropositionalLaw.DEMORGANS_OR_INVERSE_LAW, truth, allTruths);

        assertNull("Applied demorgans or inverse, when the rule should not be applicable.", actual);
    }
    
    @Test
    public void testApplyToDemorgansOrHappyPath() {
        // set up list of hypotheses
        List<PropositionalEquation> allTruths = new ArrayList<PropositionalEquation>();
        PropositionalEquation truth = new PropositionalEquation(PropositionParser.parse("-(P|Q)"),
                PropositionalLaw.HYPOTHESIS, new ArrayList<PropositionalEquation>());
        allTruths.add(truth);

        // set up expected
        List<PropositionalEquation> parents = new ArrayList<PropositionalEquation>();
        parents.add(truth);

        List<PropositionalEquation> expected = new ArrayList<PropositionalEquation>();
        expected.add(new PropositionalEquation(PropositionParser.parse("-P&-Q"),
                PropositionalLaw.DEMORGANS_OR_LAW, parents));

        // execute actual
        List<PropositionalEquation> actual = PropositionalLawApplier
                .applyTo(PropositionalLaw.DEMORGANS_OR_LAW, truth, allTruths);

        assertEquals("Could not apply demorgans or rule, when the rule should be applicable.", expected,
                actual);
    }

    @Test
    public void testApplyToDemorgansOrNoMatch() {
        // set up list of hypotheses
        List<PropositionalEquation> allTruths = new ArrayList<PropositionalEquation>();
        PropositionalEquation truth = new PropositionalEquation(PropositionParser.parse("(P>R)|-Q"),
                PropositionalLaw.HYPOTHESIS, new ArrayList<PropositionalEquation>());
        PropositionalEquation truth2 = new PropositionalEquation(PropositionParser.parse("-(P>R)&(R&S)"),
                PropositionalLaw.HYPOTHESIS, new ArrayList<PropositionalEquation>());
        allTruths.add(truth);
        allTruths.add(truth2);

        // execute actual
        List<PropositionalEquation> actual = PropositionalLawApplier
                .applyTo(PropositionalLaw.DEMORGANS_OR_LAW, truth, allTruths);

        assertNull("Applied demorgans or, when the rule should not be applicable.", actual);
    }
    
    @Test
    public void testApplyToDoubleNegationInverseHappyPath() {
        // set up list of hypotheses
        List<PropositionalEquation> allTruths = new ArrayList<PropositionalEquation>();
        PropositionalEquation truth = new PropositionalEquation(PropositionParser.parse("P"),
                PropositionalLaw.HYPOTHESIS, new ArrayList<PropositionalEquation>());
        allTruths.add(truth);

        // set up expected
        List<PropositionalEquation> parents = new ArrayList<PropositionalEquation>();
        parents.add(truth);

        List<PropositionalEquation> expected = new ArrayList<PropositionalEquation>();
        expected.add(new PropositionalEquation(PropositionParser.parse("-(-P)"),
                PropositionalLaw.DOUBLE_NEGATION_INVERSE_LAW, parents));

        // execute actual
        List<PropositionalEquation> actual = PropositionalLawApplier
                .applyTo(PropositionalLaw.DOUBLE_NEGATION_INVERSE_LAW, truth, allTruths);

        assertEquals("Could not apply double negation inverse rule, when the rule should be applicable.", expected,
                actual);
    }
    
    @Test
    public void testApplyToDoubleNegationHappyPath() {
        // set up list of hypotheses
        List<PropositionalEquation> allTruths = new ArrayList<PropositionalEquation>();
        PropositionalEquation truth = new PropositionalEquation(PropositionParser.parse("-(-P)"),
                PropositionalLaw.HYPOTHESIS, new ArrayList<PropositionalEquation>());
        allTruths.add(truth);

        // set up expected
        List<PropositionalEquation> parents = new ArrayList<PropositionalEquation>();
        parents.add(truth);

        List<PropositionalEquation> expected = new ArrayList<PropositionalEquation>();
        expected.add(new PropositionalEquation(PropositionParser.parse("P"),
                PropositionalLaw.DOUBLE_NEGATION_LAW, parents));

        // execute actual
        List<PropositionalEquation> actual = PropositionalLawApplier
                .applyTo(PropositionalLaw.DOUBLE_NEGATION_LAW, truth, allTruths);

        assertEquals("Could not apply double negation rule, when the rule should be applicable.", expected,
                actual);
    }

    @Test
    public void testApplyToDoubleNegationNoMatch() {
        // set up list of hypotheses
        List<PropositionalEquation> allTruths = new ArrayList<PropositionalEquation>();
        PropositionalEquation truth = new PropositionalEquation(PropositionParser.parse("(P>R)|-Q"),
                PropositionalLaw.HYPOTHESIS, new ArrayList<PropositionalEquation>());
        PropositionalEquation truth2 = new PropositionalEquation(PropositionParser.parse("-(P>R)&(R&S)"),
                PropositionalLaw.HYPOTHESIS, new ArrayList<PropositionalEquation>());
        allTruths.add(truth);
        allTruths.add(truth2);

        // execute actual
        List<PropositionalEquation> actual = PropositionalLawApplier
                .applyTo(PropositionalLaw.DOUBLE_NEGATION_LAW, truth, allTruths);

        assertNull("Applied double negation, when the rule should not be applicable.", actual);
    }
    
    @Test
    public void testApplyToDistributiveAndInverseHappyPath() {
        // set up list of hypotheses
        List<PropositionalEquation> allTruths = new ArrayList<PropositionalEquation>();
        PropositionalEquation truth = new PropositionalEquation(PropositionParser.parse("(P&Q)|(P&R)"),
                PropositionalLaw.HYPOTHESIS, new ArrayList<PropositionalEquation>());
        allTruths.add(truth);

        // set up expected
        List<PropositionalEquation> parents = new ArrayList<PropositionalEquation>();
        parents.add(truth);

        List<PropositionalEquation> expected = new ArrayList<PropositionalEquation>();
        expected.add(new PropositionalEquation(PropositionParser.parse("P&(Q|R)"),
                PropositionalLaw.DISTRIBUTIVE_AND_INVERSE_LAW, parents));

        // execute actual
        List<PropositionalEquation> actual = PropositionalLawApplier
                .applyTo(PropositionalLaw.DISTRIBUTIVE_AND_INVERSE_LAW, truth, allTruths);

        assertEquals("Could not apply distributive and inverse rule, when the rule should be applicable.", expected,
                actual);
    }

    @Test
    public void testApplyToDistributiveInverseAndNoMatch() {
        // set up list of hypotheses
        List<PropositionalEquation> allTruths = new ArrayList<PropositionalEquation>();
        PropositionalEquation truth = new PropositionalEquation(PropositionParser.parse("(P>R)|-Q"),
                PropositionalLaw.HYPOTHESIS, new ArrayList<PropositionalEquation>());
        PropositionalEquation truth2 = new PropositionalEquation(PropositionParser.parse("-(P>R)&(R&S)"),
                PropositionalLaw.HYPOTHESIS, new ArrayList<PropositionalEquation>());
        allTruths.add(truth);
        allTruths.add(truth2);

        // execute actual
        List<PropositionalEquation> actual = PropositionalLawApplier
                .applyTo(PropositionalLaw.DISTRIBUTIVE_AND_INVERSE_LAW, truth, allTruths);

        assertNull("Applied distributive or inverse, when the rule should not be applicable.", actual);
    }
    
    @Test
    public void testApplyToDistrubutiveAndHappyPath() {
        // set up list of hypotheses
        List<PropositionalEquation> allTruths = new ArrayList<PropositionalEquation>();
        PropositionalEquation truth = new PropositionalEquation(PropositionParser.parse("P&(Q|R)"),
                PropositionalLaw.HYPOTHESIS, new ArrayList<PropositionalEquation>());
        allTruths.add(truth);

        // set up expected
        List<PropositionalEquation> parents = new ArrayList<PropositionalEquation>();
        parents.add(truth);

        List<PropositionalEquation> expected = new ArrayList<PropositionalEquation>();
        expected.add(new PropositionalEquation(PropositionParser.parse("(P&Q)|(P&R)"),
                PropositionalLaw.DISTRIBUTIVE_AND_LAW, parents));

        // execute actual
        List<PropositionalEquation> actual = PropositionalLawApplier
                .applyTo(PropositionalLaw.DISTRIBUTIVE_AND_LAW, truth, allTruths);

        assertEquals("Could not apply distributve and rule, when the rule should be applicable.", expected,
                actual);
    }

    @Test
    public void testApplyToDistributiveAndNoMatch() {
        // set up list of hypotheses
        List<PropositionalEquation> allTruths = new ArrayList<PropositionalEquation>();
        PropositionalEquation truth = new PropositionalEquation(PropositionParser.parse("(P>R)|-Q"),
                PropositionalLaw.HYPOTHESIS, new ArrayList<PropositionalEquation>());
        PropositionalEquation truth2 = new PropositionalEquation(PropositionParser.parse("-(P>R)&(R&S)"),
                PropositionalLaw.HYPOTHESIS, new ArrayList<PropositionalEquation>());
        allTruths.add(truth);
        allTruths.add(truth2);

        // execute actual
        List<PropositionalEquation> actual = PropositionalLawApplier
                .applyTo(PropositionalLaw.DISTRIBUTIVE_AND_LAW, truth, allTruths);

        assertNull("Applied distributive and, when the rule should not be applicable.", actual);
    }

    @Test
    public void testApplyToDistributiveOrInverseHappyPath() {
        // set up list of hypotheses
        List<PropositionalEquation> allTruths = new ArrayList<PropositionalEquation>();
        PropositionalEquation truth = new PropositionalEquation(PropositionParser.parse("(P|Q)&(P|R)"),
                PropositionalLaw.HYPOTHESIS, new ArrayList<PropositionalEquation>());
        allTruths.add(truth);

        // set up expected
        List<PropositionalEquation> parents = new ArrayList<PropositionalEquation>();
        parents.add(truth);

        List<PropositionalEquation> expected = new ArrayList<PropositionalEquation>();
        expected.add(new PropositionalEquation(PropositionParser.parse("P|(Q&R)"),
                PropositionalLaw.DISTRIBUTIVE_OR_INVERSE_LAW, parents));

        // execute actual
        List<PropositionalEquation> actual = PropositionalLawApplier
                .applyTo(PropositionalLaw.DISTRIBUTIVE_OR_INVERSE_LAW, truth, allTruths);

        assertEquals("Could not apply distributive or inverse rule, when the rule should be applicable.", expected,
                actual);
    }

    @Test
    public void testApplyToDistributiveInverseOrNoMatch() {
        // set up list of hypotheses
        List<PropositionalEquation> allTruths = new ArrayList<PropositionalEquation>();
        PropositionalEquation truth = new PropositionalEquation(PropositionParser.parse("(P>R)|-Q"),
                PropositionalLaw.HYPOTHESIS, new ArrayList<PropositionalEquation>());
        PropositionalEquation truth2 = new PropositionalEquation(PropositionParser.parse("-(P>R)&(R&S)"),
                PropositionalLaw.HYPOTHESIS, new ArrayList<PropositionalEquation>());
        allTruths.add(truth);
        allTruths.add(truth2);

        // execute actual
        List<PropositionalEquation> actual = PropositionalLawApplier
                .applyTo(PropositionalLaw.DISTRIBUTIVE_OR_INVERSE_LAW, truth, allTruths);

        assertNull("Applied distributive or, when the rule should not be applicable.", actual);
    }
    
    @Test
    public void testApplyToDistrubutiveOrHappyPath() {
        // set up list of hypotheses
        List<PropositionalEquation> allTruths = new ArrayList<PropositionalEquation>();
        PropositionalEquation truth = new PropositionalEquation(PropositionParser.parse("P|(Q&R)"),
                PropositionalLaw.HYPOTHESIS, new ArrayList<PropositionalEquation>());
        allTruths.add(truth);

        // set up expected
        List<PropositionalEquation> parents = new ArrayList<PropositionalEquation>();
        parents.add(truth);

        List<PropositionalEquation> expected = new ArrayList<PropositionalEquation>();
        expected.add(new PropositionalEquation(PropositionParser.parse("(P|Q)&(P|R)"),
                PropositionalLaw.DISTRIBUTIVE_OR_LAW, parents));

        // execute actual
        List<PropositionalEquation> actual = PropositionalLawApplier
                .applyTo(PropositionalLaw.DISTRIBUTIVE_OR_LAW, truth, allTruths);

        assertEquals("Could not apply distributve or rule, when the rule should be applicable.", expected,
                actual);
    }

    @Test
    public void testApplyToDistributiveOrNoMatch() {
        // set up list of hypotheses
        List<PropositionalEquation> allTruths = new ArrayList<PropositionalEquation>();
        PropositionalEquation truth = new PropositionalEquation(PropositionParser.parse("(P>R)|-Q"),
                PropositionalLaw.HYPOTHESIS, new ArrayList<PropositionalEquation>());
        PropositionalEquation truth2 = new PropositionalEquation(PropositionParser.parse("-(P>R)&(R&S)"),
                PropositionalLaw.HYPOTHESIS, new ArrayList<PropositionalEquation>());
        allTruths.add(truth);
        allTruths.add(truth2);

        // execute actual
        List<PropositionalEquation> actual = PropositionalLawApplier
                .applyTo(PropositionalLaw.DISTRIBUTIVE_OR_LAW, truth, allTruths);

        assertNull("Applied distributive or, when the rule should not be applicable.", actual);
    }
    
    @Test
    public void testApplyToCommutativeOrHappyPath() {
        // set up list of hypotheses
        List<PropositionalEquation> allTruths = new ArrayList<PropositionalEquation>();
        PropositionalEquation truth = new PropositionalEquation(PropositionParser.parse("P|Q"),
                PropositionalLaw.HYPOTHESIS, new ArrayList<PropositionalEquation>());
        allTruths.add(truth);

        // set up expected
        List<PropositionalEquation> parents = new ArrayList<PropositionalEquation>();
        parents.add(truth);

        List<PropositionalEquation> expected = new ArrayList<PropositionalEquation>();
        expected.add(new PropositionalEquation(PropositionParser.parse("Q|P"),
                PropositionalLaw.COMMUTATIVE_OR_LAW, parents));

        // execute actual
        List<PropositionalEquation> actual = PropositionalLawApplier
                .applyTo(PropositionalLaw.COMMUTATIVE_OR_LAW, truth, allTruths);

        assertEquals("Could not apply commutative or rule, when the rule should be applicable.", expected,
                actual);
    }

    @Test
    public void testApplyToCommutativeOrNoMatch() {
        // set up list of hypotheses
        List<PropositionalEquation> allTruths = new ArrayList<PropositionalEquation>();
        PropositionalEquation truth = new PropositionalEquation(PropositionParser.parse("(P>R)&-Q"),
                PropositionalLaw.HYPOTHESIS, new ArrayList<PropositionalEquation>());
        PropositionalEquation truth2 = new PropositionalEquation(PropositionParser.parse("-(P>R)&(R&S)"),
                PropositionalLaw.HYPOTHESIS, new ArrayList<PropositionalEquation>());
        allTruths.add(truth);
        allTruths.add(truth2);

        // execute actual
        List<PropositionalEquation> actual = PropositionalLawApplier
                .applyTo(PropositionalLaw.COMMUTATIVE_OR_LAW, truth, allTruths);

        assertNull("Applied commutative or, when the rule should not be applicable.", actual);
    }
    
    @Test
    public void testApplyToCommutativeAndHappyPath() {
        // set up list of hypotheses
        List<PropositionalEquation> allTruths = new ArrayList<PropositionalEquation>();
        PropositionalEquation truth = new PropositionalEquation(PropositionParser.parse("P&Q"),
                PropositionalLaw.HYPOTHESIS, new ArrayList<PropositionalEquation>());
        allTruths.add(truth);

        // set up expected
        List<PropositionalEquation> parents = new ArrayList<PropositionalEquation>();
        parents.add(truth);

        List<PropositionalEquation> expected = new ArrayList<PropositionalEquation>();
        expected.add(new PropositionalEquation(PropositionParser.parse("Q&P"),
                PropositionalLaw.COMMUTATIVE_AND_LAW, parents));

        // execute actual
        List<PropositionalEquation> actual = PropositionalLawApplier
                .applyTo(PropositionalLaw.COMMUTATIVE_AND_LAW, truth, allTruths);

        assertEquals("Could not apply commutative and rule, when the rule should be applicable.", expected,
                actual);
    }

    @Test
    public void testApplyToCommutativeAndNoMatch() {
        // set up list of hypotheses
        List<PropositionalEquation> allTruths = new ArrayList<PropositionalEquation>();
        PropositionalEquation truth = new PropositionalEquation(PropositionParser.parse("(P>R)|-Q"),
                PropositionalLaw.HYPOTHESIS, new ArrayList<PropositionalEquation>());
        PropositionalEquation truth2 = new PropositionalEquation(PropositionParser.parse("-(P>R)&(R&S)"),
                PropositionalLaw.HYPOTHESIS, new ArrayList<PropositionalEquation>());
        allTruths.add(truth);
        allTruths.add(truth2);

        // execute actual
        List<PropositionalEquation> actual = PropositionalLawApplier
                .applyTo(PropositionalLaw.COMMUTATIVE_AND_LAW, truth, allTruths);

        assertNull("Applied commutative and, when the rule should not be applicable.", actual);
    }
    
    @Test
    public void testApplyToAssociativeOrInverseHappyPath() {
        // set up list of hypotheses
        List<PropositionalEquation> allTruths = new ArrayList<PropositionalEquation>();
        PropositionalEquation truth = new PropositionalEquation(PropositionParser.parse("P|(Q|R)"),
                PropositionalLaw.HYPOTHESIS, new ArrayList<PropositionalEquation>());
        allTruths.add(truth);

        // set up expected
        List<PropositionalEquation> parents = new ArrayList<PropositionalEquation>();
        parents.add(truth);

        List<PropositionalEquation> expected = new ArrayList<PropositionalEquation>();
        expected.add(new PropositionalEquation(PropositionParser.parse("(P|Q)|R"),
                PropositionalLaw.ASSOCIATIVE_OR_INVERSE_LAW, parents));

        // execute actual
        List<PropositionalEquation> actual = PropositionalLawApplier
                .applyTo(PropositionalLaw.ASSOCIATIVE_OR_INVERSE_LAW, truth, allTruths);

        assertEquals("Could not apply associative or inverse rule, when the rule should be applicable.", expected,
                actual);
    }

    @Test
    public void testApplyToAssociativeOrInverseNoMatch() {
        // set up list of hypotheses
        List<PropositionalEquation> allTruths = new ArrayList<PropositionalEquation>();
        PropositionalEquation truth = new PropositionalEquation(PropositionParser.parse("(P>R)|-Q"),
                PropositionalLaw.HYPOTHESIS, new ArrayList<PropositionalEquation>());
        PropositionalEquation truth2 = new PropositionalEquation(PropositionParser.parse("-(P>R)&(R&S)"),
                PropositionalLaw.HYPOTHESIS, new ArrayList<PropositionalEquation>());
        allTruths.add(truth);
        allTruths.add(truth2);

        // execute actual
        List<PropositionalEquation> actual = PropositionalLawApplier
                .applyTo(PropositionalLaw.ASSOCIATIVE_OR_INVERSE_LAW, truth, allTruths);

        assertNull("Applied associative or inverse, when the rule should not be applicable.", actual);
    }
    
    @Test
    public void testApplyToAssociativeOrHappyPath() {
        // set up list of hypotheses
        List<PropositionalEquation> allTruths = new ArrayList<PropositionalEquation>();
        PropositionalEquation truth = new PropositionalEquation(PropositionParser.parse("(P|Q)|R"),
                PropositionalLaw.HYPOTHESIS, new ArrayList<PropositionalEquation>());
        allTruths.add(truth);

        // set up expected
        List<PropositionalEquation> parents = new ArrayList<PropositionalEquation>();
        parents.add(truth);

        List<PropositionalEquation> expected = new ArrayList<PropositionalEquation>();
        expected.add(new PropositionalEquation(PropositionParser.parse("P|(Q|R)"),
                PropositionalLaw.ASSOCIATIVE_OR_LAW, parents));

        // execute actual
        List<PropositionalEquation> actual = PropositionalLawApplier
                .applyTo(PropositionalLaw.ASSOCIATIVE_OR_LAW, truth, allTruths);

        assertEquals("Could not apply associative or rule, when the rule should be applicable.", expected,
                actual);
    }

    @Test
    public void testApplyToAssociativeOrNoMatch() {
        // set up list of hypotheses
        List<PropositionalEquation> allTruths = new ArrayList<PropositionalEquation>();
        PropositionalEquation truth = new PropositionalEquation(PropositionParser.parse("(P>R)|-Q"),
                PropositionalLaw.HYPOTHESIS, new ArrayList<PropositionalEquation>());
        PropositionalEquation truth2 = new PropositionalEquation(PropositionParser.parse("-(P>R)&(R&S)"),
                PropositionalLaw.HYPOTHESIS, new ArrayList<PropositionalEquation>());
        allTruths.add(truth);
        allTruths.add(truth2);

        // execute actual
        List<PropositionalEquation> actual = PropositionalLawApplier
                .applyTo(PropositionalLaw.ASSOCIATIVE_OR_LAW, truth, allTruths);

        assertNull("Applied associative or, when the rule should not be applicable.", actual);
    }
    
    @Test
    public void testApplyToAssociativeAndInverseHappyPath() {
        // set up list of hypotheses
        List<PropositionalEquation> allTruths = new ArrayList<PropositionalEquation>();
        PropositionalEquation truth = new PropositionalEquation(PropositionParser.parse("P&(Q&R)"),
                PropositionalLaw.HYPOTHESIS, new ArrayList<PropositionalEquation>());
        allTruths.add(truth);

        // set up expected
        List<PropositionalEquation> parents = new ArrayList<PropositionalEquation>();
        parents.add(truth);

        List<PropositionalEquation> expected = new ArrayList<PropositionalEquation>();
        expected.add(new PropositionalEquation(PropositionParser.parse("(P&Q)&R"),
                PropositionalLaw.ASSOCIATIVE_AND_INVERSE_LAW, parents));

        // execute actual
        List<PropositionalEquation> actual = PropositionalLawApplier
                .applyTo(PropositionalLaw.ASSOCIATIVE_AND_INVERSE_LAW, truth, allTruths);

        assertEquals("Could not apply associative and inverse rule, when the rule should be applicable.", expected,
                actual);
    }

    @Test
    public void testApplyToAssociativeAndInverseNoMatch() {
        // set up list of hypotheses
        List<PropositionalEquation> allTruths = new ArrayList<PropositionalEquation>();
        PropositionalEquation truth = new PropositionalEquation(PropositionParser.parse("(P>R)|-Q"),
                PropositionalLaw.HYPOTHESIS, new ArrayList<PropositionalEquation>());
        PropositionalEquation truth2 = new PropositionalEquation(PropositionParser.parse("-(P>R)&(R&S)"),
                PropositionalLaw.HYPOTHESIS, new ArrayList<PropositionalEquation>());
        allTruths.add(truth);
        allTruths.add(truth2);

        // execute actual
        List<PropositionalEquation> actual = PropositionalLawApplier
                .applyTo(PropositionalLaw.ASSOCIATIVE_AND_INVERSE_LAW, truth, allTruths);

        assertNull("Applied associative and inverse, when the rule should not be applicable.", actual);
    }
    
    @Test
    public void testApplyToAssociativeAndHappyPath() {
        // set up list of hypotheses
        List<PropositionalEquation> allTruths = new ArrayList<PropositionalEquation>();
        PropositionalEquation truth = new PropositionalEquation(PropositionParser.parse("(P&Q)&R"),
                PropositionalLaw.HYPOTHESIS, new ArrayList<PropositionalEquation>());
        allTruths.add(truth);

        // set up expected
        List<PropositionalEquation> parents = new ArrayList<PropositionalEquation>();
        parents.add(truth);

        List<PropositionalEquation> expected = new ArrayList<PropositionalEquation>();
        expected.add(new PropositionalEquation(PropositionParser.parse("P&(Q&R)"),
                PropositionalLaw.ASSOCIATIVE_AND_LAW, parents));

        // execute actual
        List<PropositionalEquation> actual = PropositionalLawApplier
                .applyTo(PropositionalLaw.ASSOCIATIVE_AND_LAW, truth, allTruths);

        assertEquals("Could not apply associative and rule, when the rule should be applicable.", expected,
                actual);
    }

    @Test
    public void testApplyToAssociativeAndNoMatch() {
        // set up list of hypotheses
        List<PropositionalEquation> allTruths = new ArrayList<PropositionalEquation>();
        PropositionalEquation truth = new PropositionalEquation(PropositionParser.parse("(P>R)|-Q"),
                PropositionalLaw.HYPOTHESIS, new ArrayList<PropositionalEquation>());
        PropositionalEquation truth2 = new PropositionalEquation(PropositionParser.parse("-(P>R)&(R&S)"),
                PropositionalLaw.HYPOTHESIS, new ArrayList<PropositionalEquation>());
        allTruths.add(truth);
        allTruths.add(truth2);

        // execute actual
        List<PropositionalEquation> actual = PropositionalLawApplier
                .applyTo(PropositionalLaw.ASSOCIATIVE_AND_LAW, truth, allTruths);

        assertNull("Applied associative or, when the rule should not be applicable.", actual);
    }
    
    @Test
    public void testApplyToIndempotentAndHappyPath() {
        // set up list of hypotheses
        List<PropositionalEquation> allTruths = new ArrayList<PropositionalEquation>();
        PropositionalEquation truth = new PropositionalEquation(PropositionParser.parse("P&P"),
                PropositionalLaw.HYPOTHESIS, new ArrayList<PropositionalEquation>());
        allTruths.add(truth);

        // set up expected
        List<PropositionalEquation> parents = new ArrayList<PropositionalEquation>();
        parents.add(truth);

        List<PropositionalEquation> expected = new ArrayList<PropositionalEquation>();
        expected.add(new PropositionalEquation(PropositionParser.parse("P"),
                PropositionalLaw.IDEMPOTENT_AND_LAW, parents));

        // execute actual
        List<PropositionalEquation> actual = PropositionalLawApplier
                .applyTo(PropositionalLaw.IDEMPOTENT_AND_LAW, truth, allTruths);

        assertEquals("Could not apply idempotent and rule, when the rule should be applicable.", expected,
                actual);
    }

    @Test
    public void testApplyToIdempotentAndNoMatch() {
        // set up list of hypotheses
        List<PropositionalEquation> allTruths = new ArrayList<PropositionalEquation>();
        PropositionalEquation truth = new PropositionalEquation(PropositionParser.parse("(P>R)|-Q"),
                PropositionalLaw.HYPOTHESIS, new ArrayList<PropositionalEquation>());
        PropositionalEquation truth2 = new PropositionalEquation(PropositionParser.parse("-(P>R)&(R&S)"),
                PropositionalLaw.HYPOTHESIS, new ArrayList<PropositionalEquation>());
        allTruths.add(truth);
        allTruths.add(truth2);

        // execute actual
        List<PropositionalEquation> actual = PropositionalLawApplier
                .applyTo(PropositionalLaw.IDEMPOTENT_AND_LAW, truth, allTruths);

        assertNull("Applied idempotent or, when the rule should not be applicable.", actual);
    }
    
}
