package edu.unl.raikes.discrete.propositions;

import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * The Proposition type is the superclass of all proposition classes. Defines the behavior expected by all propositions.
 */
public abstract class Proposition implements Comparable<Proposition> {

    /**
     * This function computes and returns a truth value for the proposition, based on a mapping of truth values provided
     * by the user of this class.
     *
     * @param truthValues a mapping of variables to truth values
     * @return the truth value of this proposition given the truth values of the variables
     */
    public abstract TruthValue evaluate(Map<VariableProposition, TruthValue> truthValues);

    /**
     * This function breaks up this proposition into incremental parts. These parts would form the columns of a truth
     * table built for this proposition.
     *
     * @return the incremental parts of this proposition
     */
    public abstract Set<Proposition> getAllSubStatements();

    /**
     * This function traverses the proposition and returns all the variables used in the proposition.
     *
     * @return a Set of variables used in this proposition.
     */
    public abstract Set<VariableProposition> getAllVariables();

    /**
     * Replaces keys that are identical to children with what they point to.
     * 
     * @param map the map being searched
     * @return the proposition replaced
     */
    public abstract Proposition replace(Map<Proposition, Proposition> map);

    /**
     * Finds the maximum depth of a proposition.
     * 
     * @return the number representing the number of layers
     */
    public abstract int getMaxDepth();
    
    /**
     * Makes a copy of an ImpliesProposition.
     * 
     * @return a copy of this
     */
    public Proposition copy() {        
        return null;
    }

    @Override
    public int compareTo(Proposition o) {
        // short-circuit if same
        if (o == this) { 
            return 0;
        }

        // calculate number of variables (more is greater)
        Set<VariableProposition> myVars = this.getAllVariables();
        Set<VariableProposition> theirVars = o.getAllVariables();
        if (myVars.size() != theirVars.size()) {
            return myVars.size() - theirVars.size();
        }

        // if the number of variables is the same, let's find the variables we don't have in common
        TreeSet<VariableProposition> uniqueVarsInMine = new TreeSet<VariableProposition>();
        uniqueVarsInMine.addAll(myVars);
        uniqueVarsInMine.removeAll(theirVars);

        TreeSet<VariableProposition> uniqueVarsInTheirs = new TreeSet<VariableProposition>();
        uniqueVarsInTheirs.addAll(theirVars);
        uniqueVarsInTheirs.removeAll(myVars);

        // of the first variables we don't have in common, is mine greater? Or theirs?
        if (uniqueVarsInMine.size() > 0 && uniqueVarsInTheirs.size() > 0) {
            return uniqueVarsInMine.first().compareTo(uniqueVarsInTheirs.first());
        } else { // just in case, assume that I am less than o
            return -1;
        }

    }
}
