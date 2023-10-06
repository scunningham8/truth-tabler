package edu.unl.raikes.discrete.propositions;

import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * The NotProposition type is used to negate a Proposition.
 */
public class NotProposition extends Proposition {
    public Proposition proposition;


    /**
     * Constructs an object of the NotProposition class with the provided proposition. 
     *
     * @param proposition the proposition
     */
    public NotProposition(Proposition proposition) {
        super();
        this.proposition = proposition;
    }

    @Override
    public TruthValue evaluate(Map<VariableProposition, TruthValue> truthValues) {
        // evaluate the truthness of the variables
        TruthValue truthness = this.proposition.evaluate(truthValues);
        
        // apply the operator
        return TruthValue.negate(truthness);
    }

    @Override
    public Set<Proposition> getAllSubStatements() {
        Set<Proposition> subStatements = new TreeSet<Proposition>();
        subStatements.add(this);
        System.out.println(this.proposition.getAllSubStatements());
        subStatements.addAll(this.proposition.getAllSubStatements());
        return subStatements;
    }

    @Override
    public Set<VariableProposition> getAllVariables() {
        return this.proposition.getAllVariables();
    }

    /**
     * Returns the proposition that belongs to the NotProposition class.
     *
     * @return the proposition
     */
    public Proposition getProposition() {
        return this.proposition;
    }

    /**
     * Allows a user of this class to set a NotProposition object's proposition.
     *
     * @param proposition the proposition to set
     */
    public void setProposition(Proposition proposition) {
        this.proposition = proposition;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof NotProposition)) {
            return false;
        }
        Proposition theirProposition = ((NotProposition) object).getProposition();

        return this.proposition.equals(theirProposition);
    }
    
    /*
     * COPY
     * 
     * return new NotProposition(this.proposition.copy());
     */

    @Override
    public String toString() {
        return "-(" + this.proposition.toString() + ")";
    }

    /**
     * Makes a copy of an NotProposition.
     * 
     * @return a copy of this
     */
    public NotProposition copy() {
        Proposition prop = this.proposition.copy();
        
        NotProposition copy = new NotProposition(prop);
        return copy;
    }

    @Override
    public Proposition replace(Map<Proposition, Proposition> map) {
        for (Proposition prop : map.keySet()) {
            if (prop.equals(this.proposition)) {
                this.proposition = map.get(prop);
            }

        }
        this.proposition.replace(map);
        
        return this;
        
    }

    @Override
    public int getMaxDepth() {
        return this.proposition.getMaxDepth() + 1;
    }

}
