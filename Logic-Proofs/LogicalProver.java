package edu.unl.raikes.discrete.logicproofs;

import com.bethecoder.ascii_table.ASCIITable;
import com.bethecoder.ascii_table.ASCIITableHeader;
import com.bethecoder.ascii_table.spec.IASCIITable;
import edu.unl.raikes.discrete.PropositionParser;
import edu.unl.raikes.discrete.propositions.Proposition;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Scanner;

/**
 * The LogicalProver class contains static methods for the generation of logical proofs. The class also includes a
 * main() function.
 *
 */
public class LogicalProver {
    private static final int MAX_SIZE_OF_BAG = 100000;
    private static final int MAX_DEPTH_OF_TRUTH = 3;
    List<PropositionalLaw> laws;

    /**
     * This function uses a set of laws of logic (laws list) to form a proof that leads from the provided hypotheses
     * (bagOfTruths list) to the provided conclusion. The proof, if such a proof exists, will be returned with the steps
     * in order and all irrelevant steps (even if provided as hypotheses) omitted. If no such proof is found, function
     * returns null.
     *
     * This function uses a modified breadth-first search to generate more and more new truths, with the hope of
     * generating the conclusion. Because of the possibility of generating new truths forever without finding the
     * conclusion, the algorithm is limited 1.) in the quantity of new truths by MAX_SIZE_OF_BAG, and 2.) limited in the
     * maximum depth of the truth's parse tree (MAX_DEPTH_OF_TRUTH).
     *
     * @param bagOfTruths the initial set of truths, also called hypotheses, from which the proof will be based
     * @param conclusion the truth value the proof will hope to generate via laws of logic
     * @param laws the laws that can be applied in the generation of the proof
     * @return an ordered list of propositional equations providing the justification for the conclusion based on the
     * hypotheses
     */
    public static List<PropositionalEquation> makeLogicalProof(List<PropositionalEquation> bagOfTruths,
            Proposition conclusion, List<PropositionalLaw> laws) {
        int lastTruthProcessed = -1;

        // while the conclusion has still not been found, there are new truths to expand, and the bag of truths isn't
        // too big
        while (PropositionalLawApplier.getPropositionFromList(bagOfTruths, conclusion) == null
                && lastTruthProcessed < bagOfTruths.size() - 1 && lastTruthProcessed < MAX_SIZE_OF_BAG) {

            // keep track of new truths generated in this round. keep them separate from bagOfTruths so as not to
            // disrupt loop logic
            List<PropositionalEquation> bagOfNewTruths = new ArrayList<>();

            // loop through all the truths not yet expanded
            while (lastTruthProcessed < bagOfTruths.size() - 1) {
                lastTruthProcessed++;
                // if the truth to be expanded isn't over the depth limit, we want to expand it
                if (bagOfTruths.get(lastTruthProcessed).getProposition().getMaxDepth() <= MAX_DEPTH_OF_TRUTH) {
                    for (PropositionalLaw law : laws) {
                        // can I apply this law to this truth and gain any new truths?
                        List<PropositionalEquation> newTruths = PropositionalLawApplier.applyTo(law,
                                bagOfTruths.get(lastTruthProcessed), bagOfTruths);
                        if (newTruths != null) {
                            bagOfNewTruths.addAll(newTruths);
                        }
                    }
                }
            }
            bagOfTruths.addAll(bagOfNewTruths);
        }

        // get the equation object for the conclusion, if such an equation exists.
        PropositionalEquation conclusionEquation = PropositionalLawApplier.getPropositionFromList(bagOfTruths,
                conclusion);

        if (conclusionEquation != null) {
            // find all truths that led to the conclusion (parents)
            LinkedHashSet<PropositionalEquation> proof = getRelevantTruths(conclusionEquation, bagOfTruths);
            // convert from linkedhashset to list
            PropositionalEquation[] proofArray = proof.toArray(new PropositionalEquation[proof.size()]);
            List<PropositionalEquation> proofList = Arrays.asList(proofArray);
            // reverse array so parents appear before children
            Collections.reverse(proofList);
            return proofList;
        }

        // conclusion was not found
        return null;
    }

    /**
     *
     * This function creates a set of PropositionalEquation objects from bagOfTruths that are related to one another
     * (equation, equation's parents, those equations' parents, etc.).
     *
     * @param equation the equation whose ancestors you're looking to compile
     * @param bagOfTruths the list of all known truths
     * @return a set containing equation and all it's ancestors
     */
    private static LinkedHashSet<PropositionalEquation> getRelevantTruths(PropositionalEquation equation,
            List<PropositionalEquation> bagOfTruths) {
        LinkedHashSet<PropositionalEquation> ancestors = new LinkedHashSet<PropositionalEquation>();
        ancestors.add(equation);

        for (PropositionalEquation family : equation.getParents()) {
            ancestors.addAll(getRelevantTruths(family, bagOfTruths));
        }
        return ancestors;
    }

    /**
     * This function translates a list of PropositionalEquation objects into the 2d string array structure that allows
     * it to be printed as a table.
     *
     * @param proofList the ordered list of PropositionalEquation objects that make up the proof.
     * @return The items of the proofList formed into the standard three-column structure of a logical proof
     */
    static String[][] composeProofTable(List<PropositionalEquation> proofList) {
        final int COLS = 3;

        // [[(step number), (prop), (law, lines)]....]
        String[][] table = new String[proofList.size()][COLS];
        for (int i = 1; i <= table.length; i++) {
            for (int j = 0; j < table[0].length; j++) {
                if (j == 0) {
                    table[i - 1][j] = i + "";
                } else if (j == 1) {
                    table[i - 1][j] = proofList.get(i - 1).getProposition().toString();
                } else if (j == 2) {
                    String justification = proofList.get(i - 1).getJustification().toString();
                    for (PropositionalEquation parent : proofList.get(i - 1).getParents()) {
                        justification += ", " + (proofList.indexOf(parent) + 1);
                    }

                    table[i - 1][j] = justification;
                }
            }
        }

        return table;
    }

    /**
     * Main function.
     * 
     * @param args arguments
     */
    public static void main(String[] args) {
        Scanner scnr = new Scanner(System.in);
        List<PropositionalEquation> allTruths = new ArrayList<>();

        System.out.println("What would you like your hypothesis to be? (If you don't want any more, enter a new line)");
        String hypothesis = scnr.nextLine();

        while (hypothesis.length() != 0) {
            allTruths.add(new PropositionalEquation(PropositionParser.parse(hypothesis), PropositionalLaw.HYPOTHESIS,
                    new ArrayList<PropositionalEquation>()));
            hypothesis = scnr.nextLine();
        }

        System.out.println("What would you like your conclusion to be?");
        String conclusionString = scnr.next();

        Proposition conclusion = PropositionParser.parse(conclusionString);
        scnr.close();

        // MAKE THE PROOF
        List<PropositionalEquation> actual = LogicalProver.makeLogicalProof(allTruths, conclusion,
                PropositionalLaw.ALL_LAWS);

        if (actual == null) {
            System.out.println("Sorry, that's not possible");
        }

        // DISPLAY THE PROOF
        ASCIITableHeader[] header = { new ASCIITableHeader("Line #", IASCIITable.ALIGN_RIGHT),
                new ASCIITableHeader("Proposition", IASCIITable.ALIGN_LEFT),
                new ASCIITableHeader("Justification", IASCIITable.ALIGN_LEFT) };
        ASCIITable.getInstance().printTable(header, LogicalProver.composeProofTable(actual));

        // SQUARE BOI
        System.out.println("<Square Boi/>");
    }
}
