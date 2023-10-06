package edu.unl.raikes.discrete.propositions;

import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * The type is an abstract superclass for Propositions that connect two subpropositions with an operator. For example,
 * A&B or B>C.
 */
public abstract class SentenceProposition extends Proposition {

    protected Proposition left;
    protected Proposition right;

    /**
     * Constructs an object of the SentenceProposition class with the provided left and right subsentences.
     *
     * @param left the proposition on the left side of this propositional sentence
     * @param right the proposition on the right side of this propositional sentence
     */
    public SentenceProposition(Proposition left, Proposition right) {
        super();
        this.left = left;
        this.right = right;
    }

    @Override
    public Proposition replace(Map<Proposition, Proposition> map) {

        /*
         * The implementations of this function should determine whether any of the keys in the map are equal to any of
         * a proposition's children. If the key is equal to a child, replace that child with the value that the child
         * points to in the map, else recursively call the replace method.
         */

        // for prop in map
        // check if prop matches left
        // left = map.get(prop)
        // do same thing for right
        // if !left replace on left child
        // same for right

        for (Proposition prop : map.keySet()) {
            if (prop.equals(this.left)) {
                this.left = map.get(prop);
            } else if (prop.equals(this.right)) {
                this.right = map.get(prop);
            }

        }
        this.left.replace(map);
        this.right.replace(map);
        
        return this;
    }

    @Override
    public int getMaxDepth() {
        return Math.max(this.left.getMaxDepth(), this.right.getMaxDepth()) + 1;
    }

    @Override
    public Set<Proposition> getAllSubStatements() {
        Set<Proposition> subStatements = new TreeSet<Proposition>();
        subStatements.add(this);
        subStatements.addAll(this.left.getAllSubStatements());
        subStatements.addAll(this.right.getAllSubStatements());
        return subStatements;
    }

    @Override
    public Set<VariableProposition> getAllVariables() {
        Set<VariableProposition> set = new TreeSet<VariableProposition>();
        set.addAll(this.left.getAllVariables());
        set.addAll(this.right.getAllVariables());
        return set;
    }

    /**
     * Returns the left that belongs to the SentenceProposition class.
     *
     * @return the proposition on the left hand side of this propositional sentence
     */
    public Proposition getLeft() {
        return this.left;
    }

    /**
     * Allows a user of this class to set a SentenceProposition object's left.
     *
     * @param left the left proposition to set
     */
    public void setLeft(Proposition left) {
        this.left = left;
    }

    /**
     * Returns the right that belongs to the SentenceProposition class.
     *
     * @return the proposition on the right hand side of this propositional sentence
     */
    public Proposition getRight() {
        return this.right;
    }

    /**
     * Allows a user of this class to set a SentenceProposition object's right.
     *
     * @param right the right proposition to set
     */
    public void setRight(Proposition right) {
        this.right = right;
    }

}
