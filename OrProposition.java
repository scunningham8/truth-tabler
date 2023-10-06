package edu.unl.raikes.discrete.propositions;

import java.util.Map;
import java.util.Set;

import edu.unl.raikes.discrete.PropositionParser;

/**
 * The OrProposition type is used to determine if one of the two propositions are true.
 */
public class OrProposition extends SentenceProposition{

    /**
     * Constructs an object of the OrProposition class with the provided left and right subsentences. 
     *
     * @param left the proposition on the left side of this propositional sentence
     * @param right the proposition on the right side of this propositional sentence
     */
    public OrProposition(Proposition left, Proposition right) {
        super(left, right);

    }
    
    /**
     * Makes a copy of an ImpliesProposition.
     * 
     * @return a copy of this
     */
    public OrProposition copy() {
        // request copy of child(ren)
        Proposition left = this.left.copy();
        Proposition right = this.right.copy();

        // create a new object
        OrProposition copy = new OrProposition(left, right);

        return copy;
    }
    
    @Override
    public TruthValue evaluate(Map<VariableProposition, TruthValue> truthValues) {
        // evaluate left
        TruthValue truthL = this.left.evaluate(truthValues);
        
        // evaluate right
        TruthValue truthR = this.right.evaluate(truthValues);
        
        // apply operator
        if (truthL == TruthValue.TRUE || truthR == TruthValue.TRUE) {
            return TruthValue.TRUE;
        }
        
        return TruthValue.FALSE;
    }    
    
    @Override
    public boolean equals(Object object) {
        
        if (!(object instanceof OrProposition)) {
            return false;
        }
        Proposition theirLeftProposition = ((OrProposition) object).getLeft();
        Proposition theirRightProposition = ((OrProposition) object).getRight();

        if (this.left.equals(theirLeftProposition) && this.right.equals(theirRightProposition)) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "(" + this.left.toString() + PropositionParser.OR + this.right.toString() + ")";
    }

}
