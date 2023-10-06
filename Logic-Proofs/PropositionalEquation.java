package edu.unl.raikes.discrete.logicproofs;

import edu.unl.raikes.discrete.propositions.Proposition;
import java.util.ArrayList;
import java.util.List;

/**
 * This class holds the data relevant for one line in a propositional proof (a proposition, a law that generated the
 * proposition, and the parents that afforded the application of the law).
 */
public class PropositionalEquation {

    private Proposition proposition;
    private PropositionalLaw justification;
    private List<PropositionalEquation> parents;

    /**
     * Constructs an object of the PropositionalEquation class.
     *
     * @param proposition the truth value this propositional equation represents
     * @param justification the law that gives us security that the proposition is true
     * @param parents the PropositionalEquation(s) used by the law (justification) to generate the proposition
     */
    public PropositionalEquation(Proposition proposition, PropositionalLaw justification,
            List<PropositionalEquation> parents) {
        this.proposition = proposition;
        this.justification = justification;
        this.parents = parents;
    }

    /**
     * Constructs an object of the PropositionalEquation class. Assumes empty list of parents.
     *
     * @param proposition the truth value this propositional equation represents
     * @param justification the law that gives us security that the proposition is true
     */
    public PropositionalEquation(Proposition proposition, PropositionalLaw justification) {
        this(proposition, justification, new ArrayList<PropositionalEquation>());
    }

    /**
     * Adds a parent to the parent list that belongs to the PropositionalEquation class.
     *
     * @param equation the equation that represents the parent 
     */
    public void addParentEquation(PropositionalEquation equation) {
        this.parents.add(equation);
    }

    /**
     * Returns the proposition that belongs to the PropositionalEquation class.
     *
     * @return the proposition
     */
    public Proposition getProposition() {
        return this.proposition;
    }

    /**
     * Allows a user of this class to set a PropositionalEquation object's proposition.
     *
     * @param proposition the proposition to set
     */
    public void setProposition(Proposition proposition) {
        this.proposition = proposition;
    }

    /**
     * Returns the justification that belongs to the PropositionalEquation class.
     *
     * @return the justification
     */
    public PropositionalLaw getJustification() {
        return this.justification;
    }

    /**
     * Allows a user of this class to set a PropositionalEquation object's justification.
     *
     * @param justification the justification to set
     */
    public void setJustification(PropositionalLaw justification) {
        this.justification = justification;
    }

    /**
     * Returns the parents that belongs to the PropositionalEquation class.
     *
     * @return the parents
     */
    public List<PropositionalEquation> getParents() {
        return this.parents;
    }

    /**
     * Allows a user of this class to set a PropositionalEquation object's parents.
     *
     * @param parents the parents to set
     */
    public void setParents(List<PropositionalEquation> parents) {
        this.parents = parents;
    }

    @Override
    public boolean equals(Object otherLaw) {
        if (!(otherLaw instanceof PropositionalEquation)) {
            return false;
        }
        if (this.proposition.equals(((PropositionalEquation) otherLaw).getProposition())
                && this.justification.equals(((PropositionalEquation) otherLaw).getJustification())
                && this.parents.equals(((PropositionalEquation) otherLaw).getParents())) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        String parentsProps = "";
        for (PropositionalEquation parent : this.parents) {
            parentsProps += parent.getProposition().toString() + ", ";
        }
        if (parentsProps.length() > 2) {
            parentsProps = parentsProps.substring(0, parentsProps.length() - 2);
        }
        return this.proposition.toString() + " (obtained from " + this.justification.toString() + " and parents ["
                + parentsProps + "])";
    }
}
