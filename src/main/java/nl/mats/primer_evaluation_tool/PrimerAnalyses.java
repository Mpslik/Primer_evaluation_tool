package nl.mats.primer_evaluation_tool;

public class PrimerAnalyses {
    private final int id;
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
    private int max3IntramolecularIdentityForwardPrimer;
    private int max3IntramolecularIdentityReversePrimer;

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
        this.max3IntramolecularIdentityForwardPrimer = calculateMax3IntramolecularIdentity(forwardPrimer);

        // Analyze Primer 2 if provided (not empty or null)
        if (reversePrimer != null && !reversePrimer.isEmpty()) {
            this.gcContentReversePrimer = calculateGCContent(reversePrimer);
            this.meltingPointReversePrimer = calculateMeltingPoint(reversePrimer);
            this.maxHomopolymerStretchReversePrimer = calculateMaxHomopolymerStretch(reversePrimer);
            this.max3IntermolecularIdentity = calculateMax3IntermolecularIdentity(forwardPrimer, reversePrimer);
            this.max3IntramolecularIdentityReversePrimer = calculateMax3IntramolecularIdentity(reversePrimer);
        }
    }

    // Calculation the maximal 3' identity between the primers
    private int calculateMax3Identity(String sequence1, String sequence2) {
        int length1 = sequence1.length();
        int length2 = sequence2.length();
        int maxIdentity = 0;

        // Compare the last 'i' bases of sequence1 with the first 'i' bases of sequence2
        for (int i = 1; i <= Math.min(length1, length2); i++) {
            int currentIdentity = 0;

            for (int j = 0; j < i; j++) {
                char baseFrom3Prime = sequence1.charAt(length1 - i + j);
                char baseFrom5Prime = sequence2.charAt(j);

                if (baseFrom3Prime == baseFrom5Prime) {
                    currentIdentity++;
                } else {
                    break;  // Stop if there's a mismatch
                }
            }

            if (currentIdentity > maxIdentity) {
                maxIdentity = currentIdentity;
            }
        }

        return maxIdentity;
    }

    // Calculate maximal 3' identity for a single primer
    private int calculateMax3IntramolecularIdentity(String primer) {
        if (primer == null || primer.isEmpty()) {
            return 0;
        }

        // Get the reverse complement of the primer
        String reverseComplement = getReverseComplement(primer);


        return calculateMax3Identity(primer, reverseComplement);
    }

    private int calculateMax3IntermolecularIdentity(String forwardPrimer, String reversePrimer) {
        if (forwardPrimer == null || reversePrimer == null || forwardPrimer.isEmpty() || reversePrimer.isEmpty()) {
            return 0;
        }

        // Get the reverse complement of the reverse primer
        String reverseComplement = getReverseComplement(reversePrimer);

        // Use the generic method to calculate the max identity between the forward primer and the reverse complement
        return calculateMax3Identity(forwardPrimer, reverseComplement);
    }

    private String getReverseComplement(String primer) {
        StringBuilder reverseComplement = new StringBuilder();


        for (int i = primer.length() - 1; i >= 0; i--) {
            char base = primer.charAt(i);
            reverseComplement.append(getComplement(base));
        }

        return reverseComplement.toString();
    }

    // Method to get the complementary base
    private char getComplement(char base) {
        return switch (base) {
            case 'A' -> 'T';
            case 'T' -> 'A';
            case 'C' -> 'G';
            case 'G' -> 'C';
            default -> base;
        };
    }

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

    // Getters

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

    public int getMaxHomopolymerStretchForwardPrimer() {
        return maxHomopolymerStretchForwardPrimer;
    }

    public int getMax3IntramolecularIdentityForwardPrimer() {
        return max3IntramolecularIdentityForwardPrimer;
    }

    // Getters for Primer 2 results
    public double getGcContentReversePrimer() {
        return gcContentReversePrimer;
    }

    public double getMeltingPointReversePrimer() {
        return meltingPointReversePrimer;
    }

    public int getMaxHomopolymerStretchReversePrimer() {
        return maxHomopolymerStretchReversePrimer;
    }

    public int getMax3IntramolecularIdentityReversePrimer() {
        return max3IntramolecularIdentityReversePrimer;
    }

    public int getMax3IntermolecularIdentity() {
        return max3IntermolecularIdentity;
    }
}