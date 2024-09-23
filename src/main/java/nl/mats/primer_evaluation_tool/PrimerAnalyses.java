package nl.mats.primer_evaluation_tool;

public class PrimerAnalyses {
    private int id ;
    private String forwardPrimer;
    private String reversePrimer;
    private String forwardPrimerName;
    private String reversePrimerName;
    private double gcContentForwardPrimer;
    private double gcContentReversePrimer;
    private double meltingPointForwardPrimer;
    private double meltingPointReversePrimer;
    private int maxHomopolymerStretchForwardPrimer;
    private int maxHomopolymerStretchReversePrimer;
    private int max3IntermolecularIdentity;


    // Constructor
    public PrimerAnalyses(int id, String forwardPrimer, String reversePrimer, String forwardPrimerName, String reversePrimerName) {
        this.id = id;
        this.forwardPrimer = forwardPrimer;
        this.reversePrimer = reversePrimer;
        this.forwardPrimerName = forwardPrimerName;
        this.reversePrimerName = reversePrimerName;

        // Analyze Primer 1
        this.gcContentForwardPrimer = calculateGCContent(forwardPrimer);
        this.meltingPointForwardPrimer = calculateMeltingPoint(forwardPrimer);
        this.maxHomopolymerStretchForwardPrimer = calculateMaxHomopolymerStretch(forwardPrimer);

        // Analyze Primer 2 if provided (not empty or null)
        if (reversePrimer != null && !reversePrimer.isEmpty()) {
            this.gcContentReversePrimer = calculateGCContent(reversePrimer);
            this.meltingPointReversePrimer = calculateMeltingPoint(reversePrimer);
            this.maxHomopolymerStretchReversePrimer = calculateMaxHomopolymerStretch(reversePrimer);
            this.max3IntermolecularIdentity = calculateMax3IntermolecularIdentity(forwardPrimer, reversePrimer);
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

    private int calculateMaxHomopolymerStretch(String primer) {
        if (primer == null || primer.isEmpty()) {
            return 0;
        }

        int maxStretch = 1;
        int currentStretch = 1;

        for (int i = 1; i < primer.length(); i++) {
            if (primer.charAt(i) == primer.charAt(i - 1)) {
                currentStretch++;
            } else {
                currentStretch = 1;
            }

            if (currentStretch > maxStretch) {
                maxStretch = currentStretch;
            }
        }

        return maxStretch;
    }

    private int calculateMax3IntermolecularIdentity(String primer1, String primer2) {
        if (primer1 == null || primer2 == null || primer1.isEmpty() || primer2.isEmpty()) {
            return 0;
        }

        int minLength = Math.min(primer1.length(), primer2.length());
        int matches = 0;

        for (int i = 0; i < minLength; i++) {
            if (primer1.charAt(primer1.length() - 1 - i) == primer2.charAt(primer2.length() - 1 - i)) {
                matches++;
            } else {
                break;
            }
        }

        return matches;
    }

    // Id getter
    public int getId() {
        return id;
    }

    public String getForwardPrimerName() {
        return forwardPrimerName;
    }

    public String getReversePrimerName() {
        return reversePrimerName;
    }
    // Getters for Primer 1 results
    public double getGcContentForwardPrimer() {
        return gcContentForwardPrimer;
    }

    public double getMeltingPointForwardPrimer() {
        return meltingPointForwardPrimer;
    }

    // Getters for Primer 2 results
    public double getGcContentReversePrimer() {
        return gcContentReversePrimer;
    }

    public double getMeltingPointReversePrimer() {
        return meltingPointReversePrimer;
    }
}