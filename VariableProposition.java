package edu.unl.raikes.discrete.propositions;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * The type is used for individual propositions to determine their variables and respective qualities.
 */
public class VariableProposition extends Proposition {
    static Map<Character, VariableProposition> existingVars = new HashMap<Character, VariableProposition>();
    Character varLetter;

    /**
     * Constructor that adds the new variable to a hashMap.
     * 
     * @param varLetter the representing variable letter
     */
    private VariableProposition(Character varLetter) {
        this.varLetter = varLetter;
        existingVars.put(varLetter, this);
    }
    
    /**
     * Makes a copy of an VariableProposition.
     * 
     * @return a copy of this
     */
    public VariableProposition copy() {
        return getVariableByLetter(this.varLetter);
    }

    /**
     * Finds variable in hashMap or adds it if it doesn't exist and returns its key.
     * 
     * @param varLetter the variable being searched for
     * @return the existing or new key for the variable
     */
    public static VariableProposition getVariableByLetter(Character varLetter) {
        if (existingVars.containsKey(varLetter)) {
            return existingVars.get(varLetter);
        } else {
            return new VariableProposition(varLetter);
        }
    }

    @Override
    public TruthValue evaluate(Map<VariableProposition, TruthValue> truthValues) {
        return truthValues.get(this);
    }

    @Override
    public Set<Proposition> getAllSubStatements() {
        return new TreeSet<Proposition>();
    }

    @Override
    public Set<VariableProposition> getAllVariables() {
        Set<VariableProposition> set = new TreeSet<VariableProposition>();
        set.add(this);
        return set;
    }

    @Override
    public int compareTo(Proposition p) {
        if (p instanceof VariableProposition) {
            return this.varLetter.compareTo(((VariableProposition) p).varLetter);
        } else {
            // a variable proposition is the simplest there is.
            // if you're comparing me to any other proposition, it's bigger than me
            return 1;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof VariableProposition)) {
            return false;
        }
        return ((VariableProposition) obj).varLetter.equals(this.varLetter);
    }

    @Override
    public String toString() {
        return "" + this.varLetter;
    }

    @Override
    public Proposition replace(Map<Proposition, Proposition> map) {
        for (Proposition prop : map.keySet()) {
            if (prop.equals(this)) {
                return map.get(prop);
            }

        }
        return this;
    }

    @Override
    public int getMaxDepth() {
        return 1;
    }
}
