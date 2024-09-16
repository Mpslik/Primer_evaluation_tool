package nl.mats.primer_evaluation_tool;

public class PrimerAnalyses {
    private int id ;
    private String primer1;
    private String primer2;
    private double gcContentPrimer1;
    private double gcContentPrimer2;
    private double meltingPointPrimer1;
    private double meltingPointPrimer2;

    // Constructor
    public PrimerAnalyses(int id, String primer1, String primer2) {
        this.id = id;
        this.primer1 = primer1;
        this.primer2 = primer2;

        // Analyze Primer 1
        this.gcContentPrimer1 = calculateGCContent(primer1);
        this.meltingPointPrimer1 = calculateMeltingPoint(primer1);

        // Analyze Primer 2 if provided (not empty or null)
        if (primer2 != null && !primer2.isEmpty()) {
            this.gcContentPrimer2 = calculateGCContent(primer2);
            this.meltingPointPrimer2 = calculateMeltingPoint(primer2);
        }
    }

    // Method to calculate G/C content
    private double calculateGCContent(String primer) {
        if (primer == null || primer.isEmpty()) {
            return 0.0;  // Return 0 if the primer is empty
        }
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
        if (primer == null || primer.isEmpty()) {
            return 0.0;  // Return 0 if the primer is empty
        }
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
    // Id getter
    public int getId() {
        return id;
    }

    // Getters for Primer 1 results
    public double getGcContentPrimer1() {
        return gcContentPrimer1;
    }

    public double getMeltingPointPrimer1() {
        return meltingPointPrimer1;
    }

    // Getters for Primer 2 results
    public double getGcContentPrimer2() {
        return gcContentPrimer2;
    }

    public double getMeltingPointPrimer2() {
        return meltingPointPrimer2;
    }
}