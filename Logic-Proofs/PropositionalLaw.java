package edu.unl.raikes.discrete.logicproofs;

import edu.unl.raikes.discrete.propositions.AndProposition;
import edu.unl.raikes.discrete.propositions.ImpliesProposition;
import edu.unl.raikes.discrete.propositions.NotProposition;
import edu.unl.raikes.discrete.propositions.OrProposition;
import edu.unl.raikes.discrete.propositions.Proposition;
import edu.unl.raikes.discrete.propositions.SentenceProposition;
import edu.unl.raikes.discrete.propositions.VariableProposition;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * The type defines a list of laws that can be applied to generate new truths.
 */
public class PropositionalLaw {

    public static final List<PropositionalLaw> ALL_LAWS;

    public static final PropositionalLaw HYPOTHESIS;
    public static final PropositionalLaw IDEMPOTENT_OR_LAW;
    public static final PropositionalLaw IDEMPOTENT_AND_LAW;
    public static final PropositionalLaw ASSOCIATIVE_OR_LAW;
    public static final PropositionalLaw ASSOCIATIVE_OR_INVERSE_LAW;
    public static final PropositionalLaw ASSOCIATIVE_AND_LAW;
    public static final PropositionalLaw ASSOCIATIVE_AND_INVERSE_LAW;
    public static final PropositionalLaw COMMUTATIVE_OR_LAW;
    public static final PropositionalLaw COMMUTATIVE_AND_LAW;
    public static final PropositionalLaw DISTRIBUTIVE_AND_LAW;
    public static final PropositionalLaw DISTRIBUTIVE_AND_INVERSE_LAW;
    public static final PropositionalLaw DISTRIBUTIVE_OR_LAW;
    public static final PropositionalLaw DISTRIBUTIVE_OR_INVERSE_LAW;
    public static final PropositionalLaw DOUBLE_NEGATION_LAW;
    public static final PropositionalLaw DOUBLE_NEGATION_INVERSE_LAW;
    public static final PropositionalLaw DEMORGANS_AND_LAW;
    public static final PropositionalLaw DEMORGANS_AND_INVERSE_LAW;
    public static final PropositionalLaw DEMORGANS_OR_LAW;
    public static final PropositionalLaw DEMORGANS_OR_INVERSE_LAW;
    public static final PropositionalLaw ABSORPTION_OR_LAW;
    public static final PropositionalLaw ABSORPTION_AND_LAW;
    public static final PropositionalLaw CONDITIONAL_IDENTITY_LAW;
    public static final PropositionalLaw CONDITIONAL_IDENTITY_INVERSE_LAW;
    public static final PropositionalLaw MODUS_TOLLENS_LAW;
    public static final PropositionalLaw MODUS_PONENS_LAW;
    public static final PropositionalLaw SIMPLIFICATION_LAW;
    public static final PropositionalLaw CONJUNCTION_LAW;
    public static final PropositionalLaw HYPOTHETICAL_SYLLOGISM_LAW;
    public static final PropositionalLaw DYSJUNCTIVE_SYLLOGISM_LAW;
    public static final PropositionalLaw RESOLUTION_LAW;

    private static final List<Proposition> TEMPLATE_VARIABLES;

    // list of required components
    private List<Proposition> requiredComponents;
    // new truth
    private Proposition newTruthTemplate;
    // name
    private String name;

    static {
        ALL_LAWS = new ArrayList<PropositionalLaw>();

        TEMPLATE_VARIABLES = new ArrayList<Proposition>();

        VariableProposition a = VariableProposition.getVariableByLetter('a');
        VariableProposition b = VariableProposition.getVariableByLetter('b');
        VariableProposition c = VariableProposition.getVariableByLetter('c');

        TEMPLATE_VARIABLES.add(a);
        TEMPLATE_VARIABLES.add(b);
        TEMPLATE_VARIABLES.add(c);

        // make hypothesis
        HYPOTHESIS = makeHypothesis();
        // do not add hypothesis to ALL_LAWS

        // make idempotent laws
        IDEMPOTENT_AND_LAW = makeIdempotentAndLaw();
        ALL_LAWS.add(IDEMPOTENT_AND_LAW);
        IDEMPOTENT_OR_LAW = makeIdempotentOrLaw();
        ALL_LAWS.add(IDEMPOTENT_OR_LAW);

        // make associative laws
        ASSOCIATIVE_AND_LAW = makeAssociativeAndLaw();
        ALL_LAWS.add(ASSOCIATIVE_AND_LAW);
        ASSOCIATIVE_AND_INVERSE_LAW = makeAssociativeAndInverseLaw();
        ALL_LAWS.add(ASSOCIATIVE_AND_INVERSE_LAW);
        ASSOCIATIVE_OR_LAW = makeAssociativeOrLaw();
        ALL_LAWS.add(ASSOCIATIVE_AND_LAW);
        ASSOCIATIVE_OR_INVERSE_LAW = makeAssociativeOrInverseLaw();
        ALL_LAWS.add(ASSOCIATIVE_OR_INVERSE_LAW);

        // make commutative laws
        COMMUTATIVE_OR_LAW = makeCommutativeOrLaw();
        ALL_LAWS.add(COMMUTATIVE_OR_LAW);
        COMMUTATIVE_AND_LAW = makeCommutativeAndLaw();
        ALL_LAWS.add(COMMUTATIVE_AND_LAW);

        // make distributive law
        DISTRIBUTIVE_OR_LAW = makeDistributiveOrLaw();
        ALL_LAWS.add(DISTRIBUTIVE_OR_LAW);
        DISTRIBUTIVE_OR_INVERSE_LAW = makeDistributiveOrInverseLaw();
        ALL_LAWS.add(DISTRIBUTIVE_OR_INVERSE_LAW);
        DISTRIBUTIVE_AND_LAW = makeDistributiveAndLaw();
        ALL_LAWS.add(DISTRIBUTIVE_AND_LAW);
        DISTRIBUTIVE_AND_INVERSE_LAW = makeDistributiveAndInverseLaw();
        ALL_LAWS.add(DISTRIBUTIVE_AND_INVERSE_LAW);

        // make double negation laws
        DOUBLE_NEGATION_LAW = makeDoubleNegationLaw();
        ALL_LAWS.add(DOUBLE_NEGATION_LAW);
        DOUBLE_NEGATION_INVERSE_LAW = makeDoubleNegationInverseLaw();
        ALL_LAWS.add(DOUBLE_NEGATION_INVERSE_LAW);

        // make demorgans laws
        DEMORGANS_OR_LAW = makeDeMorgansOrLaw();
        ALL_LAWS.add(DEMORGANS_OR_LAW);
        DEMORGANS_OR_INVERSE_LAW = makeDeMorgansOrInverseLaw();
        ALL_LAWS.add(DEMORGANS_OR_INVERSE_LAW);
        DEMORGANS_AND_LAW = makeDeMorgansAndLaw();
        ALL_LAWS.add(DEMORGANS_AND_LAW);
        DEMORGANS_AND_INVERSE_LAW = makeDeMorgansAndInverseLaw();
        ALL_LAWS.add(DEMORGANS_AND_INVERSE_LAW);

        // make absorption laws
        ABSORPTION_AND_LAW = makeAbsorptionAndLaw();
        ALL_LAWS.add(ABSORPTION_AND_LAW);
        ABSORPTION_OR_LAW = makeAbsorptionOrLaw();
        ALL_LAWS.add(ABSORPTION_OR_LAW);

        // make conditional identity (implies only; we will not deal with iff)
        CONDITIONAL_IDENTITY_LAW = makeConditionalIdentityLaw();
        ALL_LAWS.add(CONDITIONAL_IDENTITY_LAW);
        CONDITIONAL_IDENTITY_INVERSE_LAW = makeConditionalIdentityInverseLaw();
        ALL_LAWS.add(CONDITIONAL_IDENTITY_INVERSE_LAW);

        // make modus ponens
        MODUS_PONENS_LAW = makeModusPonensLaw();
        ALL_LAWS.add(MODUS_PONENS_LAW);

        // make modus tollens
        MODUS_TOLLENS_LAW = makeModusTollensLaw();
        ALL_LAWS.add(MODUS_TOLLENS_LAW);

        // make simplification law
        SIMPLIFICATION_LAW = makeSimplificationLaw();
        ALL_LAWS.add(SIMPLIFICATION_LAW);

        // make conjunction
        CONJUNCTION_LAW = makeConjunctionLaw();
        ALL_LAWS.add(CONJUNCTION_LAW);

        // make hypothetical syllogism
        HYPOTHETICAL_SYLLOGISM_LAW = makeHypotheticalSyllogismLaw();
        ALL_LAWS.add(HYPOTHETICAL_SYLLOGISM_LAW);

        // make disjunctive syllogism
        DYSJUNCTIVE_SYLLOGISM_LAW = makeDysjunctiveSyllogismLaw();
        ALL_LAWS.add(DYSJUNCTIVE_SYLLOGISM_LAW);

        // make resolution
        RESOLUTION_LAW = makeResolutionLaw();
        ALL_LAWS.add(RESOLUTION_LAW);
    }

    /**
     * Private constructor ensures instantiation can only occur internally.
     *
     * @param name the name of the law
     * @param requiredComponents the templates that determine the structure of propositions for law to be applied
     * @param newTruthTemplate the structure of the new truth to be made in case data meets templates' structure
     */
    private PropositionalLaw(String name, List<Proposition> requiredComponents, Proposition newTruthTemplate) {
        super();
        this.requiredComponents = requiredComponents;
        this.newTruthTemplate = newTruthTemplate;
        this.name = name;
    }

    /**
     * Returns the name that belongs to the PropositionalLaw class.
     *
     * @return the name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Allows a user of this class to set a PropositionalLaw object's name.
     *
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the requiredComponents that belongs to the PropositionalLaw class.
     *
     * @return the requiredComponents
     */
    public List<Proposition> getRequiredComponents() {
        return this.requiredComponents;
    }

    /**
     * Allows a user of this class to set a PropositionalLaw object's requiredComponents.
     *
     * @param requiredComponents the requiredComponents to set
     */
    public void setRequiredComponents(List<Proposition> requiredComponents) {
        this.requiredComponents = requiredComponents;
    }

    /**
     * Returns the newTruthTemplate that belongs to the PropositionalLaw class.
     *
     * @return the newTruthTemplate
     */
    public Proposition getNewTruthTemplate() {
        return this.newTruthTemplate;
    }

    /**
     * Allows a user of this class to set a PropositionalLaw object's newTruthTemplate.
     *
     * @param newTruthTemplate the newTruthTemplate to set
     */
    public void setNewTruthTemplate(Proposition newTruthTemplate) {
        this.newTruthTemplate = newTruthTemplate;
    }

    /**
     * Returns the abstractpropositionalvars that belongs to the PropositionalLaw class.
     *
     * @return the abstractpropositionalvars
     */
    public static List<Proposition> getTemplateVariables() {
        return TEMPLATE_VARIABLES;
    }

    /**
     * This function allows for the comparison of two Propositional Law objects.
     *
     * @param otherLaw the law to be compared with this one
     * @return true if the objects are equal
     */
    public boolean equals(PropositionalLaw otherLaw) {
        return this.name.equals(otherLaw.name);
    }

    @Override
    public String toString() {
        return this.name;
    }

    /**
     * This function generates the Hypothesis law. The law has no templates, generates no new truths, and is used for
     * initial truths.
     *
     * @return a PropositionalLaw that can be used for initial truths.
     */
    private static PropositionalLaw makeHypothesis() {
        return new PropositionalLaw("Hypothesis", new ArrayList<Proposition>(), null);
    }

    /**
     * This function generates the Idempotent law, as applicable to "or" statements.
     *
     * @return a PropositionalLaw that can be used to apply the Idempotent law to "or" statements.
     */
    private static PropositionalLaw makeIdempotentOrLaw() {
        // the name of the law
        String name = "Idempotent Law (OR)";

        // the variables used in the templates
        Proposition a = TEMPLATE_VARIABLES.get(0);

        // the templates (Idempotent Or law requires (a & a))
        List<Proposition> requiredComponents = new ArrayList<Proposition>();
        requiredComponents.add(new OrProposition(a, a));

        // the template used to create a new truth (Idempotent Or law simplifies (a & a) to a).
        Proposition newTruthTemplate = a;

        return new PropositionalLaw(name, requiredComponents, newTruthTemplate);
    }

    /**
     * This function generates the Resolution rule.
     *
     * @return a PropositionalLaw that can be used to apply the Idempotent law to "or" statements.
     */
    private static PropositionalLaw makeResolutionLaw() {
        // the name of the law
        String name = "Resolution";

        // the variables used in the templates
        Proposition a = TEMPLATE_VARIABLES.get(0);
        Proposition b = TEMPLATE_VARIABLES.get(1);
        Proposition c = TEMPLATE_VARIABLES.get(2);

        // the templates (Idempotent Or law requires (a & a))
        List<Proposition> requiredComponents = new ArrayList<Proposition>();
        requiredComponents.add(new OrProposition(a, b));
        requiredComponents.add(new OrProposition(new NotProposition(a), c));

        // the template used to create a new truth (Idempotent Or law simplifies (a & a) to a).
        Proposition newTruthTemplate = new OrProposition(b, c);

        return new PropositionalLaw(name, requiredComponents, newTruthTemplate);
    }

    /**
     * This function generates the Dysjunctive Syllogism rule.
     *
     * @return a PropositionalLaw that can be used to apply the Idempotent law to "or" statements.
     */
    private static PropositionalLaw makeDysjunctiveSyllogismLaw() {
        // the name of the law
        String name = "Dysjunctive Syllogism";

        // the variables used in the templates
        Proposition a = TEMPLATE_VARIABLES.get(0);
        Proposition b = TEMPLATE_VARIABLES.get(1);

        // the templates (Idempotent Or law requires (a & a))
        List<Proposition> requiredComponents = new ArrayList<Proposition>();
        requiredComponents.add(new OrProposition(a, b));
        requiredComponents.add(new NotProposition(a));

        // the template used to create a new truth (Idempotent Or law simplifies (a & a) to a).
        Proposition newTruthTemplate = b;

        return new PropositionalLaw(name, requiredComponents, newTruthTemplate);
    }

    /**
     * This function generates the Hypothetical Syllogism rule.
     *
     * @return a PropositionalLaw that can be used to apply the Idempotent law to "or" statements.
     */
    private static PropositionalLaw makeHypotheticalSyllogismLaw() {
        // the name of the law
        String name = "Hypothetical Syllogism";

        // the variables used in the templates
        Proposition a = TEMPLATE_VARIABLES.get(0);
        Proposition b = TEMPLATE_VARIABLES.get(1);
        Proposition c = TEMPLATE_VARIABLES.get(2);

        // the templates (Idempotent Or law requires (a & a))
        List<Proposition> requiredComponents = new ArrayList<Proposition>();
        requiredComponents.add(new ImpliesProposition(a, b));
        requiredComponents.add(new ImpliesProposition(b, c));

        // the template used to create a new truth (Idempotent Or law simplifies (a & a) to a).
        Proposition newTruthTemplate = new ImpliesProposition(a, c);

        return new PropositionalLaw(name, requiredComponents, newTruthTemplate);
    }

    /**
     * This function generates the Conjunction rule.
     *
     * @return a PropositionalLaw that can be used to apply the Idempotent law to "or" statements.
     */
    private static PropositionalLaw makeConjunctionLaw() {
        // the name of the law
        String name = "Conjunction";

        // the variables used in the templates
        Proposition a = TEMPLATE_VARIABLES.get(0);
        Proposition b = TEMPLATE_VARIABLES.get(1);

        // the templates (Idempotent Or law requires (a & a))
        List<Proposition> requiredComponents = new ArrayList<Proposition>();
        requiredComponents.add(a);
        requiredComponents.add(b);

        // the template used to create a new truth (Idempotent Or law simplifies (a & a) to a).
        Proposition newTruthTemplate = new AndProposition(a, b);

        return new PropositionalLaw(name, requiredComponents, newTruthTemplate);
    }

    /**
     * This function generates the Simplification rule.
     *
     * @return a PropositionalLaw that can be used to apply the Idempotent law to "or" statements.
     */
    private static PropositionalLaw makeSimplificationLaw() {
        // the name of the law
        String name = "Simplification";

        // the variables used in the templates
        Proposition a = TEMPLATE_VARIABLES.get(0);
        Proposition b = TEMPLATE_VARIABLES.get(1);

        // the templates (Idempotent Or law requires (a & a))
        List<Proposition> requiredComponents = new ArrayList<Proposition>();
        requiredComponents.add(new AndProposition(a, b));

        // the template used to create a new truth (Idempotent Or law simplifies (a & a) to a).
        Proposition newTruthTemplate = a;

        return new PropositionalLaw(name, requiredComponents, newTruthTemplate);
    }

    /**
     * This function generates the Modus Tollens rule.
     *
     * @return a PropositionalLaw that can be used to apply the Idempotent law to "or" statements.
     */
    private static PropositionalLaw makeModusTollensLaw() {
        // the name of the law
        String name = "Modus Tollens";

        // the variables used in the templates
        Proposition a = TEMPLATE_VARIABLES.get(0);
        Proposition b = TEMPLATE_VARIABLES.get(1);

        // the templates (Idempotent Or law requires (a & a))
        List<Proposition> requiredComponents = new ArrayList<Proposition>();
        requiredComponents.add(new NotProposition(b));
        requiredComponents.add(new ImpliesProposition(a, b));

        // the template used to create a new truth (Idempotent Or law simplifies (a & a) to a).
        Proposition newTruthTemplate = new NotProposition(a);

        return new PropositionalLaw(name, requiredComponents, newTruthTemplate);
    }

    /**
     * This function generates the Modus Ponens rule.
     *
     * @return a PropositionalLaw that can be used to apply the Idempotent law to "or" statements.
     */
    private static PropositionalLaw makeModusPonensLaw() {
        // the name of the law
        String name = "Modus Ponens";

        // the variables used in the templates
        Proposition a = TEMPLATE_VARIABLES.get(0);
        Proposition b = TEMPLATE_VARIABLES.get(1);

        // the templates (Idempotent Or law requires (a & a))
        List<Proposition> requiredComponents = new ArrayList<Proposition>();
        requiredComponents.add(new ImpliesProposition(a, b));
        requiredComponents.add(a);

        // the template used to create a new truth (Idempotent Or law simplifies (a & a) to a).
        Proposition newTruthTemplate = b;

        return new PropositionalLaw(name, requiredComponents, newTruthTemplate);
    }

    /**
     * This function generates the Conditional Identity Inverse rule.
     *
     * @return a PropositionalLaw that can be used to apply the Idempotent law to "or" statements.
     */
    private static PropositionalLaw makeConditionalIdentityInverseLaw() {
        // the name of the law
        String name = "Condition Identity Inverse";

        // the variables used in the templates
        Proposition a = TEMPLATE_VARIABLES.get(0);
        Proposition b = TEMPLATE_VARIABLES.get(1);

        // the templates (Idempotent Or law requires (a & a))
        List<Proposition> requiredComponents = new ArrayList<Proposition>();
        requiredComponents.add(new OrProposition(new NotProposition(a), b));

        // the template used to create a new truth (Idempotent Or law simplifies (a & a) to a).
        Proposition newTruthTemplate = new ImpliesProposition(a, b);

        return new PropositionalLaw(name, requiredComponents, newTruthTemplate);
    }

    /**
     * This function generates the Conditional Identity rule.
     *
     * @return a PropositionalLaw that can be used to apply the Idempotent law to "or" statements.
     */
    private static PropositionalLaw makeConditionalIdentityLaw() {
        // the name of the law
        String name = "Condition Identity";

        // the variables used in the templates
        Proposition a = TEMPLATE_VARIABLES.get(0);
        Proposition b = TEMPLATE_VARIABLES.get(1);

        // the templates (Idempotent Or law requires (a & a))
        List<Proposition> requiredComponents = new ArrayList<Proposition>();
        requiredComponents.add(new ImpliesProposition(a, b));

        // the template used to create a new truth (Idempotent Or law simplifies (a & a) to a).
        Proposition newTruthTemplate = new OrProposition(new NotProposition(a), b);

        return new PropositionalLaw(name, requiredComponents, newTruthTemplate);
    }

    /**
     * This function generates the Absorption Or rule.
     *
     * @return a PropositionalLaw that can be used to apply the Idempotent law to "or" statements.
     */
    private static PropositionalLaw makeAbsorptionOrLaw() {
        // the name of the law
        String name = "Absorption Or";

        // the variables used in the templates
        Proposition a = TEMPLATE_VARIABLES.get(0);
        Proposition b = TEMPLATE_VARIABLES.get(1);

        // the templates (Idempotent Or law requires (a & a))
        List<Proposition> requiredComponents = new ArrayList<Proposition>();
        requiredComponents.add(new OrProposition(a, new AndProposition(a, b)));

        // the template used to create a new truth (Idempotent Or law simplifies (a & a) to a).
        Proposition newTruthTemplate = a;

        return new PropositionalLaw(name, requiredComponents, newTruthTemplate);
    }

    /**
     * This function generates the Absorption And rule.
     *
     * @return a PropositionalLaw that can be used to apply the Idempotent law to "or" statements.
     */
    private static PropositionalLaw makeAbsorptionAndLaw() {
        // the name of the law
        String name = "Absorption And";

        // the variables used in the templates
        Proposition a = TEMPLATE_VARIABLES.get(0);
        Proposition b = TEMPLATE_VARIABLES.get(1);

        // the templates (Idempotent Or law requires (a & a))
        List<Proposition> requiredComponents = new ArrayList<Proposition>();
        requiredComponents.add(new AndProposition(a, new OrProposition(a, b)));

        // the template used to create a new truth (Idempotent Or law simplifies (a & a) to a).
        Proposition newTruthTemplate = a;

        return new PropositionalLaw(name, requiredComponents, newTruthTemplate);
    }

    /**
     * This function generates the DeMorgans And Inverse rule.
     *
     * @return a PropositionalLaw that can be used to apply the Idempotent law to "or" statements.
     */
    private static PropositionalLaw makeDeMorgansAndInverseLaw() {
        // the name of the law
        String name = "Demorgan's And Inverse";

        // the variables used in the templates
        Proposition a = TEMPLATE_VARIABLES.get(0);
        Proposition b = TEMPLATE_VARIABLES.get(1);

        // the templates (Idempotent Or law requires (a & a))
        List<Proposition> requiredComponents = new ArrayList<Proposition>();
        requiredComponents.add(new OrProposition(new NotProposition(a), new NotProposition(b)));

        // the template used to create a new truth (Idempotent Or law simplifies (a & a) to a).
        Proposition newTruthTemplate = new NotProposition(new AndProposition(a, b));

        return new PropositionalLaw(name, requiredComponents, newTruthTemplate);

    }

    /**
     * This function generates the DeMorgans And rule.
     *
     * @return a PropositionalLaw that can be used to apply the Idempotent law to "or" statements.
     */
    private static PropositionalLaw makeDeMorgansAndLaw() {
        // the name of the law
        String name = "Demorgan's And";

        // the variables used in the templates
        Proposition a = TEMPLATE_VARIABLES.get(0);
        Proposition b = TEMPLATE_VARIABLES.get(1);

        // the templates (Idempotent Or law requires (a & a))
        List<Proposition> requiredComponents = new ArrayList<Proposition>();
        requiredComponents.add(new NotProposition(new AndProposition(a, b)));

        // the template used to create a new truth (Idempotent Or law simplifies (a & a) to a).
        Proposition newTruthTemplate = new OrProposition(new NotProposition(a), new NotProposition(b));

        return new PropositionalLaw(name, requiredComponents, newTruthTemplate);
    }

    /**
     * This function generates the DeMorgans Or Inverse rule.
     *
     * @return a PropositionalLaw that can be used to apply the Idempotent law to "or" statements.
     */
    private static PropositionalLaw makeDeMorgansOrInverseLaw() {
        // the name of the law
        String name = "DeMorgan's Or";

        // the variables used in the templates
        Proposition a = TEMPLATE_VARIABLES.get(0);
        Proposition b = TEMPLATE_VARIABLES.get(1);

        // the templates (Idempotent Or law requires (a & a))
        List<Proposition> requiredComponents = new ArrayList<Proposition>();
        requiredComponents.add(new AndProposition(new NotProposition(a), new NotProposition(b)));

        // the template used to create a new truth (Idempotent Or law simplifies (a & a) to a).
        Proposition newTruthTemplate = new NotProposition(new OrProposition(a, b));

        return new PropositionalLaw(name, requiredComponents, newTruthTemplate);
    }

    /**
     * This function generates the DeMorgans Or rule.
     *
     * @return a PropositionalLaw that can be used to apply the Idempotent law to "or" statements.
     */
    private static PropositionalLaw makeDeMorgansOrLaw() {
        // the name of the law
        String name = "DeMorgan's Or";

        // the variables used in the templates
        Proposition a = TEMPLATE_VARIABLES.get(0);
        Proposition b = TEMPLATE_VARIABLES.get(1);

        // the templates (Idempotent Or law requires (a & a))
        List<Proposition> requiredComponents = new ArrayList<Proposition>();
        requiredComponents.add(new NotProposition(new OrProposition(a, b)));

        // the template used to create a new truth (Idempotent Or law simplifies (a & a) to a).
        Proposition newTruthTemplate = new AndProposition(new NotProposition(a), new NotProposition(b));

        return new PropositionalLaw(name, requiredComponents, newTruthTemplate);
    }

    /**
     * This function generates the Double Negation Inverse rule.
     *
     * @return a PropositionalLaw that can be used to apply the Idempotent law to "or" statements.
     */
    private static PropositionalLaw makeDoubleNegationInverseLaw() {
        // the name of the law
        String name = "Double Negation Inverse";

        // the variables used in the templates
        Proposition a = TEMPLATE_VARIABLES.get(0);

        // the templates (Idempotent Or law requires (a & a))
        List<Proposition> requiredComponents = new ArrayList<Proposition>();
        requiredComponents.add(a);

        // the template used to create a new truth (Idempotent Or law simplifies (a & a) to a).
        Proposition newTruthTemplate = new NotProposition(new NotProposition(a));

        return new PropositionalLaw(name, requiredComponents, newTruthTemplate);
    }

    /**
     * This function generates the Double Negation rule.
     *
     * @return a PropositionalLaw that can be used to apply the Idempotent law to "or" statements.
     */
    private static PropositionalLaw makeDoubleNegationLaw() {
        // the name of the law
        String name = "Double Negation Inverse";

        // the variables used in the templates
        Proposition a = TEMPLATE_VARIABLES.get(0);

        // the templates (Idempotent Or law requires (a & a))
        List<Proposition> requiredComponents = new ArrayList<Proposition>();
        requiredComponents.add(new NotProposition(new NotProposition(a)));

        // the template used to create a new truth (Idempotent Or law simplifies (a & a) to a).
        Proposition newTruthTemplate = a;

        return new PropositionalLaw(name, requiredComponents, newTruthTemplate);
    }

    /**
     * This function generates the Distributive And Inverse rule.
     *
     * @return a PropositionalLaw that can be used to apply the Idempotent law to "or" statements.
     */
    private static PropositionalLaw makeDistributiveAndInverseLaw() {
        // the name of the law
        String name = "Distributive And Inverse";

        // the variables used in the templates
        Proposition a = TEMPLATE_VARIABLES.get(0);
        Proposition b = TEMPLATE_VARIABLES.get(1);
        Proposition c = TEMPLATE_VARIABLES.get(2);

        // the templates (Idempotent Or law requires (a & a))
        List<Proposition> requiredComponents = new ArrayList<Proposition>();
        requiredComponents.add(new OrProposition(new AndProposition(a, b), new AndProposition(a, c)));

        // the template used to create a new truth (Idempotent Or law simplifies (a & a) to a).
        Proposition newTruthTemplate = new AndProposition(a, new OrProposition(b, c));

        return new PropositionalLaw(name, requiredComponents, newTruthTemplate);
    }

    /**
     * This function generates the Distributive And rule.
     *
     * @return a PropositionalLaw that can be used to apply the Idempotent law to "or" statements.
     */
    private static PropositionalLaw makeDistributiveAndLaw() {
        // the name of the law
        String name = "Distributive And";

        // the variables used in the templates
        Proposition a = TEMPLATE_VARIABLES.get(0);
        Proposition b = TEMPLATE_VARIABLES.get(1);
        Proposition c = TEMPLATE_VARIABLES.get(2);

        // the templates (Idempotent Or law requires (a & a))
        List<Proposition> requiredComponents = new ArrayList<Proposition>();
        requiredComponents.add(new AndProposition(a, new OrProposition(b, c)));

        // the template used to create a new truth (Idempotent Or law simplifies (a & a) to a).
        Proposition newTruthTemplate = new OrProposition(new AndProposition(a, b), new AndProposition(a, c));

        return new PropositionalLaw(name, requiredComponents, newTruthTemplate);
    }

    /**
     * This function generates the Distributive Or Inverse rule.
     *
     * @return a PropositionalLaw that can be used to apply the Idempotent law to "or" statements.
     */
    private static PropositionalLaw makeDistributiveOrInverseLaw() {
        // the name of the law
        String name = "Distributive Or Inverse";

        // the variables used in the templates
        Proposition a = TEMPLATE_VARIABLES.get(0);
        Proposition b = TEMPLATE_VARIABLES.get(1);
        Proposition c = TEMPLATE_VARIABLES.get(2);

        // the templates (Idempotent Or law requires (a & a))
        List<Proposition> requiredComponents = new ArrayList<Proposition>();
        requiredComponents.add(new AndProposition(new OrProposition(a, b), new OrProposition(a, c)));

        // the template used to create a new truth (Idempotent Or law simplifies (a & a) to a).
        Proposition newTruthTemplate = new OrProposition(a, new AndProposition(b, c));

        return new PropositionalLaw(name, requiredComponents, newTruthTemplate);
    }

    /**
     * This function generates the Distributive Or rule.
     *
     * @return a PropositionalLaw that can be used to apply the Idempotent law to "or" statements.
     */
    private static PropositionalLaw makeDistributiveOrLaw() {
        // the name of the law
        String name = "Distributive Or";

        // the variables used in the templates
        Proposition a = TEMPLATE_VARIABLES.get(0);
        Proposition b = TEMPLATE_VARIABLES.get(1);
        Proposition c = TEMPLATE_VARIABLES.get(2);

        // the templates (Idempotent Or law requires (a & a))
        List<Proposition> requiredComponents = new ArrayList<Proposition>();
        requiredComponents.add(new OrProposition(a, new AndProposition(b, c)));

        // the template used to create a new truth (Idempotent Or law simplifies (a & a) to a).
        Proposition newTruthTemplate = new AndProposition(new OrProposition(a, b), new OrProposition(a, c));

        return new PropositionalLaw(name, requiredComponents, newTruthTemplate);
    }

    /**
     * This function generates the Commutative And rule.
     *
     * @return a PropositionalLaw that can be used to apply the Idempotent law to "or" statements.
     */
    private static PropositionalLaw makeCommutativeAndLaw() {
        // the name of the law
        String name = "Commutative And";

        // the variables used in the templates
        Proposition a = TEMPLATE_VARIABLES.get(0);
        Proposition b = TEMPLATE_VARIABLES.get(1);

        // the templates (Idempotent Or law requires (a & a))
        List<Proposition> requiredComponents = new ArrayList<Proposition>();
        requiredComponents.add(new AndProposition(a, b));

        // the template used to create a new truth (Idempotent Or law simplifies (a & a) to a).
        Proposition newTruthTemplate = new AndProposition(b, a);

        return new PropositionalLaw(name, requiredComponents, newTruthTemplate);
    }

    /**
     * This function generates the Commutative Or rule.
     *
     * @return a PropositionalLaw that can be used to apply the Idempotent law to "or" statements.
     */
    private static PropositionalLaw makeCommutativeOrLaw() {
        // the name of the law
        String name = "Commutative Or";

        // the variables used in the templates
        Proposition a = TEMPLATE_VARIABLES.get(0);
        Proposition b = TEMPLATE_VARIABLES.get(1);

        // the templates (Idempotent Or law requires (a & a))
        List<Proposition> requiredComponents = new ArrayList<Proposition>();
        requiredComponents.add(new OrProposition(a, b));

        // the template used to create a new truth (Idempotent Or law simplifies (a & a) to a).
        Proposition newTruthTemplate = new OrProposition(b, a);

        return new PropositionalLaw(name, requiredComponents, newTruthTemplate);
    }

    /**
     * This function generates the Associative Or Inverse rule.
     *
     * @return a PropositionalLaw that can be used to apply the Idempotent law to "or" statements.
     */
    private static PropositionalLaw makeAssociativeOrInverseLaw() {
        // the name of the law
        String name = "Associative Or Inverse";

        // the variables used in the templates
        Proposition a = TEMPLATE_VARIABLES.get(0);
        Proposition b = TEMPLATE_VARIABLES.get(1);
        Proposition c = TEMPLATE_VARIABLES.get(2);

        // the templates (Idempotent Or law requires (a & a))
        List<Proposition> requiredComponents = new ArrayList<Proposition>();
        requiredComponents.add(new OrProposition(a, new OrProposition(b, c)));

        // the template used to create a new truth (Idempotent Or law simplifies (a & a) to a).
        Proposition newTruthTemplate = new OrProposition(new OrProposition(a, b), c);

        return new PropositionalLaw(name, requiredComponents, newTruthTemplate);
    }

    /**
     * This function generates the Associative Or rule.
     *
     * @return a PropositionalLaw that can be used to apply the Idempotent law to "or" statements.
     */
    private static PropositionalLaw makeAssociativeOrLaw() {
        // the name of the law
        String name = "Associative Or";

        // the variables used in the templates
        Proposition a = TEMPLATE_VARIABLES.get(0);
        Proposition b = TEMPLATE_VARIABLES.get(1);
        Proposition c = TEMPLATE_VARIABLES.get(2);

        // the templates (Idempotent Or law requires (a & a))
        List<Proposition> requiredComponents = new ArrayList<Proposition>();
        requiredComponents.add(new OrProposition(new OrProposition(a, b), c));

        // the template used to create a new truth (Idempotent Or law simplifies (a & a) to a).
        Proposition newTruthTemplate = new OrProposition(a, new OrProposition(b, c));

        return new PropositionalLaw(name, requiredComponents, newTruthTemplate);
    }

    /**
     * This function generates the Associative And Inverse rule.
     *
     * @return a PropositionalLaw that can be used to apply the Idempotent law to "or" statements.
     */
    private static PropositionalLaw makeAssociativeAndInverseLaw() {
        // the name of the law
        String name = "Associative And Inverse";

        // the variables used in the templates
        Proposition a = TEMPLATE_VARIABLES.get(0);
        Proposition b = TEMPLATE_VARIABLES.get(1);
        Proposition c = TEMPLATE_VARIABLES.get(2);

        // the templates (Idempotent Or law requires (a & a))
        List<Proposition> requiredComponents = new ArrayList<Proposition>();
        requiredComponents.add(new AndProposition(a, new AndProposition(b, c)));

        // the template used to create a new truth (Idempotent Or law simplifies (a & a) to a).
        Proposition newTruthTemplate = new AndProposition(new AndProposition(a, b), c);

        return new PropositionalLaw(name, requiredComponents, newTruthTemplate);
    }

    /**
     * This function generates the Associative And rule.
     *
     * @return a PropositionalLaw that can be used to apply the Idempotent law to "or" statements.
     */
    private static PropositionalLaw makeAssociativeAndLaw() {
        // the name of the law
        String name = "Associative And";

        // the variables used in the templates
        Proposition a = TEMPLATE_VARIABLES.get(0);
        Proposition b = TEMPLATE_VARIABLES.get(1);
        Proposition c = TEMPLATE_VARIABLES.get(2);

        // the templates (Idempotent Or law requires (a & a))
        List<Proposition> requiredComponents = new ArrayList<Proposition>();
        requiredComponents.add(new AndProposition(new AndProposition(a, b), c));

        // the template used to create a new truth (Idempotent Or law simplifies (a & a) to a).
        Proposition newTruthTemplate = new AndProposition(a, new AndProposition(b, c));

        return new PropositionalLaw(name, requiredComponents, newTruthTemplate);
    }

    /**
     * This function generates the Idempotent And rule.
     *
     * @return a PropositionalLaw that can be used to apply the Idempotent law to "or" statements.
     */
    private static PropositionalLaw makeIdempotentAndLaw() {
        // the name of the law
        String name = "Idempotent And";

        // the variables used in the templates
        Proposition a = TEMPLATE_VARIABLES.get(0);

        // the templates (Idempotent Or law requires (a & a))
        List<Proposition> requiredComponents = new ArrayList<Proposition>();
        requiredComponents.add(new AndProposition(a, a));

        // the template used to create a new truth (Idempotent Or law simplifies (a & a) to a).
        Proposition newTruthTemplate = a;

        return new PropositionalLaw(name, requiredComponents, newTruthTemplate);
    }
}
