package nl.mats.primer_evaluation_tool;

/**
 * Represents a primer object containing information about forward and reverse primers.
 */
public class PrimerObject {

    /** The forward primer sequence. */
    private String forwardPrimer;

    /** The reverse primer sequence. */
    private String reversePrimer;

    /** The name of the forward primer. */
    private String forwardPrimerName;

    /** The name of the reverse primer. */
    private String reversePrimerName;

    // Constructors
    public PrimerObject() {
    }

    public PrimerObject(String forwardPrimer, String reversePrimer) {
        this.forwardPrimer = forwardPrimer;
        this.reversePrimer = reversePrimer;
        this.forwardPrimerName = forwardPrimer;
        this.reversePrimerName = reversePrimer;
    }

    /**
     * Validates if the given primer sequence contains only valid nucleotide characters (A, T, C, G).
     *
     * @param primer The primer sequence to validate.
     * @return {@code true} if the primer sequence is valid; {@code false} otherwise.
     */
    public boolean isValidPrimer(String primer) {
        return !primer.matches("[ATCGatcg]+");
    }

    // Getters and setters without Javadoc

    public String getForwardPrimer() {
        return forwardPrimer;
    }

    public void setForwardPrimer(String forwardPrimer) {
        this.forwardPrimer = forwardPrimer;
    }

    public String getReversePrimer() {
        return reversePrimer;
    }

    public void setReversePrimer(String reversePrimer) {
        this.reversePrimer = reversePrimer;
    }

    public String getForwardPrimerName() {
        return forwardPrimerName;
    }

    public void setForwardPrimerName(String forwardPrimerName) {
        this.forwardPrimerName = forwardPrimerName;
    }

    public String getReversePrimerName() {
        return reversePrimerName;
    }

    public void setReversePrimerName(String reversePrimerName) {
        this.reversePrimerName = reversePrimerName;
    }

    @Override
    public String toString() {
        return forwardPrimer + " " + reversePrimer;
    }
}