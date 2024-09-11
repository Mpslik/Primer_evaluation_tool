package nl.mats.primer_evaluation_tool;

public class PrimerObject {
    private String forwardPrimer;
    private String reversePrimer;

    // Constructor
    public PrimerObject() {
    }

    public PrimerObject(String forwardPrimer, String reversePrimer) {
        this.forwardPrimer = forwardPrimer;
        this.reversePrimer = reversePrimer;
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

    // Valid primer check
    public boolean isValidPrimer(String primer) {
        return primer.matches("[ATCGUatcgu]+");
    }

    @Override
    public String toString() {
        return forwardPrimer + " " + reversePrimer;
    }
}
