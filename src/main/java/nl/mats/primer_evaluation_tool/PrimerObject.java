package nl.mats.primer_evaluation_tool;

public class PrimerObject {
    private String forwardPrimer;
    private String reversePrimer;
    private String forwardPrimerName;
    private String reversePrimerName;

    // Constructor
    public PrimerObject() {
    }

    public PrimerObject(String forwardPrimer, String reversePrimer) {
        this.forwardPrimer = forwardPrimer;
        this.reversePrimer = reversePrimer;
        this.forwardPrimerName = forwardPrimer;
        this.reversePrimerName = reversePrimer;
    }

    // Getters and setters
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

    // Valid primer check
    public boolean isValidPrimer(String primer) {
        return primer.matches("[ATCGatcg]+");
    }

    @Override
    public String toString() {
        return forwardPrimer + " " + reversePrimer;
    }
}
