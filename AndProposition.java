package edu.unl.raikes.discrete.propositions;

import edu.unl.raikes.discrete.PropositionParser;
import java.util.Map;

/**
 * The AndProposition type is used to determine if both propositions in the sentence are true.
 */
public class AndProposition extends SentenceProposition {

    /**
     * Constructs an object of the AndProposition class with the provided propositions.
     *
     * @param left the proposition on the left
     * @param right the proposition on the right
     */
    public AndProposition(Proposition left, Proposition right) {
        super(left, right);
    }

    /**
     * Makes a copy of an AndProposition.
     * 
     * @return a copy of this
     */
    public AndProposition copy() {
        // request copy of child(ren)
        Proposition left = this.left.copy();
        Proposition right = this.right.copy();

        // create a new object
        AndProposition copy = new AndProposition(left, right);

        return copy;
    }

    @Override
    public TruthValue evaluate(Map<VariableProposition, TruthValue> truthValues) {
        // evaluate left
        TruthValue truthL = this.left.evaluate(truthValues);

        // evaluate right
        TruthValue truthR = this.right.evaluate(truthValues);

        // apply operator (use built in java operators)
        if (truthL == TruthValue.TRUE && truthR == TruthValue.TRUE) {
            return TruthValue.TRUE;
        }

        return TruthValue.FALSE;
    }

    @Override
    public boolean equals(Object object) {

        if (!(object instanceof AndProposition)) {
            return false;
        }
        Proposition theirLeftProposition = ((AndProposition) object).getLeft();
        Proposition theirRightProposition = ((AndProposition) object).getRight();

        if (this.left.equals(theirLeftProposition) && this.right.equals(theirRightProposition)) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "(" + this.left.toString() + PropositionParser.AND + this.right.toString() + ")";
    }

}
