package edu.unl.raikes.discrete;

import com.bethecoder.ascii_table.ASCIITable;
import edu.unl.raikes.discrete.propositions.Proposition;
import edu.unl.raikes.discrete.propositions.TruthValue;
import edu.unl.raikes.discrete.propositions.VariableProposition;

import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/**
 * This application class displays truth tables for user-entered propositions.
 */
public class TruthTabler {

    /**
     * This function builds and displays a truth table based on a set of variables and a set of propositions.
     *
     * @param variables the variables in the propositions
     * @param subs the propositions to include in the truth table
     */
    private static void buildTable(Set<VariableProposition> variables, Set<Proposition> subs) {

        VariableProposition[] varsArray = variables.toArray(new VariableProposition[variables.size()]);
        Proposition[] subsArray = subs.toArray(new Proposition[subs.size()]);

        // add header row
        String[] tableHeaders = buildTableHeaders(varsArray, subsArray);

        // calculate the table rows
        String[][] tableData = buildTableData(varsArray, subsArray);

        // display the table
        ASCIITable.getInstance().printTable(tableHeaders, tableData);
    }

    /**
     * This function builds the body of the truth table. The first columns provide values for the mapping of truth
     * values to variables, and the remaining columns include the evaluation of the provided propositions provided based
     * on that mapping.
     *
     * @param varsArray an array containing all of the variables in the proposition
     * @param subsArray an array of all the substatements to be displayed in the table
     * @return a table of strings representing the truth values of the truth table
     */
    private static String[][] buildTableData(VariableProposition[] varsArray, Proposition[] subsArray) {
        // gets variables' truth values
        PropositionVariableMapper mapper = new PropositionVariableMapper(varsArray);
        
        String[][] table = new String[(int) Math.pow(2, varsArray.length)][varsArray.length + subsArray.length];

        /*
         * while there is a next mapping 1) get the next mapping (mapper.next -- current row of table -- call
         * evaluate(mapper.next()) 2) loop over columns 3) if you're at a position that is a variable (you are given the
         * vars array so you know how many variables there are) -- print truth value 4) if you're at a position that is
         * a proposition -- figure out which proposition it is (indexing - subtract the length of the variables array)
         * a: get the correct proposition (subsarray) b: evaluate(mapper.next())
         */
        int rowIndex = 0;
        while (mapper.hasNext()) {
            Map<VariableProposition, TruthValue> row = mapper.next();

            for (int i = 0; i < table[0].length; i++) {
                if (i < varsArray.length) {
                    table[rowIndex][i] = row.get(varsArray[i]).toString();
                } else {
                    table[rowIndex][i] = subsArray[i - varsArray.length].evaluate(row).toString();
                }
            }
            rowIndex++;
        }

        return table;
    }

    /**
     * This function generates the table headers for a truth table. The first columns represent the variables and the
     * following columns represent the various substatements. The variables and substatements will be displayed in the
     * order provided by the array parameters.
     *
     * @param varsArray an array containing all of the variables in the proposition
     * @param subsArray an array of all the substatements to be displayed in the table
     * @return an array of Strings representing the headers for the truth table
     */
    private static String[] buildTableHeaders(VariableProposition[] varsArray, Proposition[] subsArray) {
        int columns = varsArray.length + subsArray.length;

        String[] outputTableHeaders = new String[columns];

        for (int col = 0; col < columns; col++) {
            // this column represents a variable
            if (col < varsArray.length) {
                outputTableHeaders[col] = varsArray[col].toString();
            } else {
                // this column represents a substatement
                outputTableHeaders[col] = subsArray[col - varsArray.length].toString();
            }
        }

        return outputTableHeaders;
    }

    /**
     * Prompts the user to enter a proposition, then displays a truth table. The table includes all variables and their
     * truth values in the leftmost columns. In the next columns will be increasingly complex propositional
     * substatements and their evaluated truth values.
     *
     * @param args this program does not accept any arguments
     */
    public static void main(String[] args) {
        // ask the user what they want to see
        Scanner scnr = new Scanner(System.in);
        System.out.println("What proposition would you like explore as a truth table?");
        // no input validation; assume good input
        String statement = scnr.nextLine();
        scnr.close();

        // parse statement
        Proposition p = PropositionParser.parse(statement);

        // get all the variables
        Set<VariableProposition> variables = p.getAllVariables();

        // calculate how many sub-statements there are
        Set<Proposition> subs = p.getAllSubStatements();

        // build the table
        buildTable(variables, subs);
    }

}
