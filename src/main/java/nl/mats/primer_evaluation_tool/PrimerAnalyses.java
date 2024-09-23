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

    // Calculate maximum 3'-intramolecular identity for a single primer
    private int calculateMax3IntramolecularIdentity(String primer) {
        int length = primer.length();
        int maxIdentity = 0;

        // Get the reverse complement of the primer
        String reverseComplement = getReverseComplement(primer);
        System.out.println("Analyzing primer: " + primer);
        System.out.println("Reverse complement: " + reverseComplement);

        // Compare the last portion of the sequence with the first portion of the reverse complement
        for (int i = 1; i <= length; i++) {
            int currentIdentity = 0;

            System.out.println("\nComparing the last " + i + " bases from the 3' end with the first " + i + " bases from the reverse complement");

            // Compare the last 'i' bases from the 3' end with the reverse complement
            for (int j = 0; j < i; j++) {
                char baseFrom3Prime = primer.charAt(length - i + j);  // Last 'i' bases from the 3' end
                char baseFrom5Prime = reverseComplement.charAt(j);    // First 'i' bases from the reverse complement

                System.out.println("Comparing 3' base " + baseFrom3Prime + " at position " + (length - i + j) +
                        " with reverse complement base " + baseFrom5Prime + " at position " + j);

                if (baseFrom3Prime == baseFrom5Prime) {
                    currentIdentity++;
                    System.out.println("Match found! Current identity count: " + currentIdentity);
                } else {
                    System.out.println("Mismatch found. Stopping comparison for this starting point.");
                    break;  // Stop counting if there's a mismatch
                }
            }

            if (currentIdentity > maxIdentity) {
                maxIdentity = currentIdentity;
                System.out.println("New max identity found: " + maxIdentity);
            }
        }

        System.out.println("Final maximum 3'-intramolecular identity: " + maxIdentity);
        return maxIdentity;
    }

    private String getReverseComplement(String primer) {
        StringBuilder reverseComplement = new StringBuilder();


        for (int i = primer.length() - 1; i >= 0; i--) {
            char base = primer.charAt(i);
            reverseComplement.append(getComplement(base));
        }

        return reverseComplement.toString();
    }

    // Utility method to get the complementary base
    private char getComplement(char base) {
        return switch (base) {
            case 'A' -> 'T';
            case 'T' -> 'A';
            case 'C' -> 'G';
            case 'G' -> 'C';
            default -> base;  // Just return the same base in case of an error
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

    private int calculateMax3IntermolecularIdentity(String primer1, String primer2) {
        if (primer1 == null || primer2 == null || primer1.isEmpty() || primer2.isEmpty()) {
            return 0;
        }

        int minLength = Math.min(primer1.length(), primer2.length());
        int matches = 0;

        // Compare the 3' ends of both primers (complementary direction for reverse)
        for (int i = 0; i < minLength; i++) {
            char forwardBase = primer1.charAt(primer1.length() - 1 - i);
            char reverseBase = getComplement(primer2.charAt(i)); // Get complement of reverse primer
            if (forwardBase == reverseBase) {
                matches++;
            } else {
                break;  // Stop counting if there's a mismatch
            }
        }

        return matches;
    }

    // Calculate maximum 3'-intramolecular identity for a single primer
    private int calculate3IntramolecularIdentity(String primer) {
        int length = primer.length();
        int maxIdentity = 0;
        for (int i = 1; i < length; i++) {
            if (primer.charAt(length - i) == primer.charAt(i - 1)) {
                maxIdentity++;
            } else {
                break;
            }
        }
        return maxIdentity;
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