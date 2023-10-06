package edu.unl.raikes.discrete.logicproofs;

import static org.junit.Assert.*;

import com.bethecoder.ascii_table.ASCIITable;
import edu.unl.raikes.discrete.PropositionParser;
import edu.unl.raikes.discrete.propositions.OrProposition;
import edu.unl.raikes.discrete.propositions.Proposition;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.Test;

public class LogicalProverTest {

    @Test
    public void testMakeLogicalProofSimpleTwoStep() {
        // setup
        List<PropositionalEquation> allTruths = new ArrayList<PropositionalEquation>();
        PropositionalEquation truth = new PropositionalEquation(PropositionParser.parse("P&Q"),
                PropositionalLaw.HYPOTHESIS, new ArrayList<PropositionalEquation>());
        allTruths.add(truth);

        Proposition conclusion = PropositionParser.parse("Q");

        // setup expected
        List<PropositionalEquation> expected = new ArrayList<PropositionalEquation>();
        expected.add(truth);

        List<PropositionalEquation> parentsOfCommutativeAnd = new ArrayList<PropositionalEquation>();
        parentsOfCommutativeAnd.add(truth);
        PropositionalEquation commutativeAnd = new PropositionalEquation(PropositionParser.parse("Q&P"),
                PropositionalLaw.COMMUTATIVE_AND_LAW, parentsOfCommutativeAnd);
        expected.add(commutativeAnd);

        List<PropositionalEquation> parentsOfSimplification = new ArrayList<PropositionalEquation>();
        parentsOfSimplification.add(commutativeAnd);
        PropositionalEquation simplification = new PropositionalEquation(PropositionParser.parse("Q"),
                PropositionalLaw.SIMPLIFICATION_LAW, parentsOfSimplification);
        expected.add(simplification);

        // execute
        List<PropositionalEquation> actual = LogicalProver.makeLogicalProof(allTruths, conclusion,
                PropositionalLaw.ALL_LAWS);
        assertEquals("two-step logical proof with single hypothesis unsuccessful", expected, actual);
    }

    @Test
    public void testMakeLogicalProofFiveStep() {
        // setup
        List<PropositionalEquation> allTruths = new ArrayList<PropositionalEquation>();
        PropositionalEquation truth1 = new PropositionalEquation(PropositionParser.parse("(P&Q)>R"),
                PropositionalLaw.HYPOTHESIS, new ArrayList<PropositionalEquation>());
        PropositionalEquation truth2 = new PropositionalEquation(PropositionParser.parse("-R"),
                PropositionalLaw.HYPOTHESIS, new ArrayList<PropositionalEquation>());
        PropositionalEquation truth3 = new PropositionalEquation(PropositionParser.parse("Q"),
                PropositionalLaw.HYPOTHESIS, new ArrayList<PropositionalEquation>());
        allTruths.add(truth1);
        allTruths.add(truth2);
        allTruths.add(truth3);

        Proposition conclusion = PropositionParser.parse("-P");

        // setup expected
        List<PropositionalEquation> expected = new ArrayList<PropositionalEquation>();

        // STEP 1: HYPOTHESIS
        expected.add(truth3);

        // STEP 2: DOUBLE NEGATION
        List<PropositionalEquation> parentsOfDoubleNegation = new ArrayList<PropositionalEquation>();
        parentsOfDoubleNegation.add(truth3);
        PropositionalEquation doublenegation = new PropositionalEquation(PropositionParser.parse("-(-Q)"),
                PropositionalLaw.DOUBLE_NEGATION_INVERSE_LAW, parentsOfDoubleNegation);
        expected.add(doublenegation);

        // STEP 3: MORE HYPOTHESES
        expected.add(truth2);
        expected.add(truth1);

        // STEP 4: MODUS TOLLENS
        List<PropositionalEquation> parentsOfModusTollens = new ArrayList<PropositionalEquation>();
        parentsOfModusTollens.add(truth1);
        parentsOfModusTollens.add(truth2);
        PropositionalEquation modusTollens = new PropositionalEquation(PropositionParser.parse("-(P&Q)"),
                PropositionalLaw.MODUS_TOLLENS_LAW, parentsOfModusTollens);
        expected.add(modusTollens);

        // STEP 5: DE MORGANS
        List<PropositionalEquation> parentsOfDeMorgans = new ArrayList<PropositionalEquation>();
        parentsOfDeMorgans.add(modusTollens);
        PropositionalEquation demorgans = new PropositionalEquation(PropositionParser.parse("-P|-Q"),
                PropositionalLaw.DEMORGANS_AND_LAW, parentsOfDeMorgans);
        expected.add(demorgans);

        // STEP 6: COMMUTATIVE
        List<PropositionalEquation> parentsOfCommutative = new ArrayList<PropositionalEquation>();
        parentsOfCommutative.add(demorgans);
        PropositionalEquation commutative = new PropositionalEquation(PropositionParser.parse("-Q|-P"),
                PropositionalLaw.COMMUTATIVE_OR_LAW, parentsOfCommutative);
        expected.add(commutative);

        // STEP 7: DYSJUNCTIVESYLLOGISM
        List<PropositionalEquation> parentsOfDysjunctiveSyllogism = new ArrayList<PropositionalEquation>();
        parentsOfDysjunctiveSyllogism.add(commutative);
        parentsOfDysjunctiveSyllogism.add(doublenegation);
        PropositionalEquation dyssyl = new PropositionalEquation(PropositionParser.parse("-P"),
                PropositionalLaw.DYSJUNCTIVE_SYLLOGISM_LAW, parentsOfDysjunctiveSyllogism);
        expected.add(dyssyl);

        // execute
        List<PropositionalEquation> actual = LogicalProver.makeLogicalProof(allTruths, conclusion,
                PropositionalLaw.ALL_LAWS);

        assertEquals("five-step logical proof with three hypotheses unsuccessful", expected, actual);
    }
    
    @Test
    public void testMakeLogicalProofClassExample() {
        // setup
        List<PropositionalEquation> allTruths = new ArrayList<PropositionalEquation>();
        PropositionalEquation truth1 = new PropositionalEquation(PropositionParser.parse("P|Q"),
                PropositionalLaw.HYPOTHESIS, new ArrayList<PropositionalEquation>());
        PropositionalEquation truth2 = new PropositionalEquation(PropositionParser.parse("-P|R"),
                PropositionalLaw.HYPOTHESIS, new ArrayList<PropositionalEquation>());
        PropositionalEquation truth3 = new PropositionalEquation(PropositionParser.parse("-Q"),
                PropositionalLaw.HYPOTHESIS, new ArrayList<PropositionalEquation>());
        allTruths.add(truth1);
        allTruths.add(truth2);
        allTruths.add(truth3);

        Proposition conclusion = PropositionParser.parse("R");

        // setup expected
        List<PropositionalEquation> expected = new ArrayList<PropositionalEquation>();

        // STEP 1: HYPOTHESIS
        expected.add(truth3);
        expected.add(truth2);
        expected.add(truth1);

        // STEP 2: RESOLUTION
        List<PropositionalEquation> parentsOfResolution = new ArrayList<PropositionalEquation>();
        parentsOfResolution.add(truth1);
        parentsOfResolution.add(truth2);
        PropositionalEquation resolution = new PropositionalEquation(PropositionParser.parse("Q|R"),
                PropositionalLaw.RESOLUTION_LAW, parentsOfResolution);
        expected.add(resolution);

        // STEP 3: DYSJUNCTIVESYLLOGISM
        List<PropositionalEquation> parentsOfDysjunctiveSyllogism = new ArrayList<PropositionalEquation>();
        parentsOfDysjunctiveSyllogism.add(resolution);
        parentsOfDysjunctiveSyllogism.add(truth3);
        PropositionalEquation dyssyl = new PropositionalEquation(PropositionParser.parse("R"),
                PropositionalLaw.DYSJUNCTIVE_SYLLOGISM_LAW, parentsOfDysjunctiveSyllogism);
        expected.add(dyssyl);

        // execute
        List<PropositionalEquation> actual = LogicalProver.makeLogicalProof(allTruths, conclusion,
                PropositionalLaw.ALL_LAWS);

        assertEquals("two-step logical proof with three hypotheses unsuccessful (example from class)", expected, actual);
    }

}
