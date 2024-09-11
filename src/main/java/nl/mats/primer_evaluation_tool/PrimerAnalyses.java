package nl.mats.primer_evaluation_tool;

public class PrimerAnalyses {
    private String primer1;
    private String primer2;
    private double gcContent;
    private double meltingPoint;

    // Constructor
    public PrimerAnalyses(String primer1, String primer2) {
        this.primer1 = primer1;
        this.primer2 = primer2;
        this.gcContent = calculateGCContent(primer1);
        this.meltingPoint = calculateMeltingPoint(primer1);
    }

    // Method to calculate G/C content
    private double calculateGCContent(String primer) {
        int gcCount = 0;
        for (char base : primer.toCharArray()) {
            if (base == 'G' || base == 'C') {
                gcCount++;
            }
        }
        return ((double) gcCount / primer.length()) * 100;
    }

    // Method to calculate melting point (basic formula, expand as needed)
    private double calculateMeltingPoint(String primer) {
        return (2 * (countOccurrences(primer, 'A') + countOccurrences(primer, 'T'))) +
                (4 * (countOccurrences(primer, 'G') + countOccurrences(primer, 'C')));
    }

    private int countOccurrences(String primer, char base) {
        int count = 0;
        for (char c : primer.toCharArray()) {
            if (c == base) {
                count++;
            }
        }
        return count;
    }

    // Getters for result display
    public double getGcContent() {
        return gcContent;
    }

    public double getMeltingPoint() {
        return meltingPoint;
    }
}
