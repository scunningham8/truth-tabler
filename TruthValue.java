package edu.unl.raikes.discrete.propositions;

/**
 * The type is used for the truth values true and false. An unknown value is provided for the case that a value is
 * unknown.
 */
public enum TruthValue {
    TRUE, FALSE, UNKNOWN;

    /**
     * Convenience function to negate a truth value.
     *
     * @param v the value to negate
     * @return the negated value (or UNKNOWN, if v was UNKNOWN)
     */
    public static TruthValue negate(TruthValue v) {
        if (v == TRUE) {
            return FALSE;
        } else if (v == FALSE) {
            return TRUE;
        }
        return UNKNOWN;
    }
}
