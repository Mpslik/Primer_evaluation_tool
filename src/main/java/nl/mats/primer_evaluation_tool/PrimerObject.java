package nl.mats.primer_evaluation_tool;

public class PrimerObject {
    private String primer1;
    private String primer2;

    public PrimerObject() {}

    // constructors

    public PrimerObject(String primer1, String primer2) {
        this.primer1 = primer1;
        this.primer2 = primer2;
    }

    // getters and setters
    public String getPrimer1() {
        return primer1;
    }
    public void setPrimer1(String primer1) {
        this.primer1 = primer1;
    }

    public String getPrimer2() {
        return primer2;
    }
    public void setPrimer2(String primer2) {
        this.primer2 = primer2;
    }

    // to string methode
    public String toString() {
        return primer1 + " " + primer2;
    }

}
