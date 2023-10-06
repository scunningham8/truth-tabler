package edu.unl.raikes.discrete;

import edu.unl.raikes.discrete.propositions.TruthValue;
import edu.unl.raikes.discrete.propositions.VariableProposition;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/**
 * This class takes in an array of VariableProposition objects and generates all combinations of
 * true/false for that set of VariableProposition objects.
 *
 * For example, if "A" and "B" are passed in to this class's constructor, the following data is
 * generated:
 *     - A: True,  B: True
 *     - A: True,  B: False
 *     - A: False, B: True
 *     - A: False, B: False
 *
 * This class implements the Iterator interface, which means it has the next() and hasNext()
 * methods implemented.
 */
public class PropositionVariableMapper implements Iterator<Map<VariableProposition, TruthValue>> {
    // we use the metaphor of counting in binary to find all the combinations
    int binaryCounter = 0;
    VariableProposition[] variables;

    /**
     * Constructs an object of the PropositionVariableMapper class.
     *
     * @param variables the variables you'd like to map.
     */
    public PropositionVariableMapper(VariableProposition[] variables) {
        this.variables = variables;
    }

    /**
     * Tests if there another mapping to explore.
     * 
     * @return true if there is another mapping to explore, false otherwise
     */
    @Override
    public boolean hasNext() {
        return this.binaryCounter < Math.pow(2, this.variables.length);
    }

    /**
     * What's the next mapping? (If there isn't one, it returns an empty map.)
     * 
     * @return the next mapping of variables
     */
    @Override
    public Map<VariableProposition, TruthValue> next() {
        Map<VariableProposition, TruthValue> map = new TreeMap<VariableProposition, TruthValue>();

        // get binary counter in binary format
        String binaryString = Integer.toBinaryString(this.binaryCounter);
        binaryString = this.padLeftZeroes(binaryString, this.variables.length);

        // loop through all variables, binary string backward
        for (int i = 0; i < this.variables.length; i++) {
            // 1 is false
            if (binaryString.charAt(i) == '1') {
                map.put(this.variables[i], TruthValue.FALSE);
            } else {
                // 0 is true
                map.put(this.variables[i], TruthValue.TRUE);
            }
        }

        this.binaryCounter++;
        return map;
    }

    /**
     * Function copied from https://www.baeldung.com/java-pad-string in March 2022 This function adds zeroes to the
     * beginning of a string to ensure it is a specified number of characters long.
     *
     * @param inputString the string to which you want to add zeroes
     * @param length the desired length of the string
     * @return a string of the desired length, obtained by adding zeroes to the left
     */
    private String padLeftZeroes(String inputString, int length) {
        if (inputString.length() >= length) {
            return inputString;
        }
        StringBuilder sb = new StringBuilder();
        while (sb.length() < length - inputString.length()) {
            sb.append('0');
        }
        sb.append(inputString);

        return sb.toString();
    }
}
