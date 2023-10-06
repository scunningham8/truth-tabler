package edu.unl.raikes.discrete.propositions;

import java.util.Map;
import java.util.Set;

import edu.unl.raikes.discrete.PropositionParser;

/**
 * The Implies Proposition type is used to determine if the promise implies the result.
 */
public class ImpliesProposition extends SentenceProposition {
    /**
     * Constructs an object of the ImpliesProposition class with the provided proposition.
     *
     * @param left the left proposition
     * @param right the right proposition
     */
    public ImpliesProposition(Proposition left, Proposition right) {
        super(left, right);
    }

    /**
     * Makes a copy of an ImpliesProposition.
     * 
     * @return a copy of this
     */
    public ImpliesProposition copy() {
        // request copy of child(ren)
        Proposition left = this.left.copy();
        Proposition right = this.right.copy();

        // create a new object
        ImpliesProposition copy = new ImpliesProposition(left, right);

        return copy;
    }

    @Override
    public TruthValue evaluate(Map<VariableProposition, TruthValue> truthValues) {
        // evaluate left
        TruthValue truthL = this.left.evaluate(truthValues);

        // evaluate right
        TruthValue truthR = this.right.evaluate(truthValues);

        // apply operator
        if (truthL == TruthValue.FALSE) {
            return TruthValue.TRUE;
        } else if (truthL == TruthValue.TRUE && truthR == TruthValue.TRUE) {
            return TruthValue.TRUE;
        }
        return TruthValue.FALSE;
    }

    @Override
    public boolean equals(Object object) {

        if (!(object instanceof ImpliesProposition)) {
            return false;
        }
        Proposition theirLeftProposition = ((ImpliesProposition) object).getLeft();
        Proposition theirRightProposition = ((ImpliesProposition) object).getRight();

        if (this.left.equals(theirLeftProposition) && this.right.equals(theirRightProposition)) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "(" + this.left.toString() + PropositionParser.IMPLIES + this.right.toString() + ")";
    }

}
