package nl.mats.primer_evaluation_tool;

/**
 * PrimerAnalyses class performs analysis on primer sequences, including calculating
 * GC content, melting point, homopolymer stretch, and intermolecular/intramolecular identities.
 * It generates HTML for color-coded primer visualization and stores analysis results.
 */
public class PrimerAnalyses {
    // Unique identifier for each analysis
    private final int id;

    // Primer names
    private final String forwardPrimerName;
    private final String reversePrimerName;

    // Analysis results for the forward primer
    private final double gcContentForwardPrimer;
    private final double meltingPointForwardPrimer;
    private final int maxHomopolymerStretchForwardPrimer;
    private final int max3IntramolecularIdentityForwardPrimer;
    private final String coloredForwardPrimerHtml;

    // Analysis results for the reverse primer (if provided)
    private double gcContentReversePrimer;
    private double meltingPointReversePrimer;
    private int maxHomopolymerStretchReversePrimer;
    private int max3IntramolecularIdentityReversePrimer;
    private int max3IntermolecularIdentity;
    private String coloredReversePrimerHtml;

    /**
     * Constructs a PrimerAnalyses instance and performs analysis on the given primers.
     *
     * @param id               The unique ID for this analysis.
     * @param forwardPrimer    The forward primer sequence.
     * @param reversePrimer    The reverse primer sequence (optional).
     * @param forwardPrimerName The name of the forward primer.
     * @param reversePrimerName The name of the reverse primer.
     */
    public PrimerAnalyses(int id, String forwardPrimer, String reversePrimer, String forwardPrimerName, String reversePrimerName) {
        this.id = id;
        this.forwardPrimerName = forwardPrimerName;
        this.reversePrimerName = reversePrimerName;

        // Perform analysis on the forward primer
        this.gcContentForwardPrimer = calculateGCContent(forwardPrimer);
        this.meltingPointForwardPrimer = calculateMeltingPoint(forwardPrimer);
        this.maxHomopolymerStretchForwardPrimer = calculateMaxHomopolymerStretch(forwardPrimer);
        this.max3IntramolecularIdentityForwardPrimer = calculateMax3IntramolecularIdentity(forwardPrimer);

        // Generate color-coded HTML representation
        this.coloredForwardPrimerHtml = getColoredPrimerHtml(forwardPrimer);


        // Analyze the reverse primer if it is provided (not null or empty)
        if (reversePrimer != null && !reversePrimer.isEmpty()) {
            this.gcContentReversePrimer = calculateGCContent(reversePrimer);
            this.meltingPointReversePrimer = calculateMeltingPoint(reversePrimer);
            this.maxHomopolymerStretchReversePrimer = calculateMaxHomopolymerStretch(reversePrimer);
            this.max3IntermolecularIdentity = calculateMax3IntermolecularIdentity(forwardPrimer, reversePrimer);
            this.max3IntramolecularIdentityReversePrimer = calculateMax3IntramolecularIdentity(reversePrimer);
            // Generate color-coded HTML representations
            this.coloredReversePrimerHtml = getColoredPrimerHtml(reversePrimer);
        }
    }

    /**
     * Calculates the maximum 3' end identity between two primer sequences.
     *
     * @param sequence1 The first primer sequence.
     * @param sequence2 The second primer sequence.
     * @return The maximum identity at the 3' end overlap.
     */
    private int calculateMax3Identity(String sequence1, String sequence2) {
        int length1 = sequence1.length();
        int length2 = sequence2.length();
        int maxIdentity = 0;

        // Compare overlapping bases at the 3' end of sequence1 and the 5' end of sequence2
        for (int i = 1; i <= Math.min(length1, length2); i++) {
            int currentIdentity = 0;

            // Count matching bases in the overlap
            for (int j = 0; j < i; j++) {
                char baseFrom3Prime = sequence1.charAt(length1 - i + j);
                char baseFrom5Prime = sequence2.charAt(j);

                if (baseFrom3Prime == baseFrom5Prime) {
                    currentIdentity++;
                } else {
                    break;  // Stop counting if there's a mismatch
                }
            }

            // Update max identity if the current overlap is greater
            if (currentIdentity > maxIdentity) {
                maxIdentity = currentIdentity;
            }
        }

        return maxIdentity;
    }

    /**
     * Calculates the maximum 3' intramolecular identity within a single primer sequence.
     *
     * @param primer The primer sequence.
     * @return The maximum intramolecular identity.
     */
    private int calculateMax3IntramolecularIdentity(String primer) {
        if (primer == null || primer.isEmpty()) {
            return 0;
        }

        // Get the reverse complement of the primer
        String reverseComplement = getReverseComplement(primer);

        // Compare the primer with its reverse complement
        return calculateMax3Identity(primer, reverseComplement);
    }

    /**
     * Calculates the maximum 3' intermolecular identity between the forward and reverse primers.
     *
     * @param forwardPrimer The forward primer sequence.
     * @param reversePrimer The reverse primer sequence.
     * @return The maximum intermolecular identity.
     */
    private int calculateMax3IntermolecularIdentity(String forwardPrimer, String reversePrimer) {
        if (forwardPrimer == null || reversePrimer == null || forwardPrimer.isEmpty() || reversePrimer.isEmpty()) {
            return 0;
        }

        // Get the reverse complement of the reverse primer
        String reverseComplement = getReverseComplement(reversePrimer);

        // Compare the forward primer with the reverse complement of the reverse primer
        return calculateMax3Identity(forwardPrimer, reverseComplement);
    }

    /**
     * Generates the reverse complement of a primer sequence.
     *
     * @param primer The primer sequence.
     * @return The reverse complement of the primer.
     */
    private String getReverseComplement(String primer) {
        StringBuilder reverseComplement = new StringBuilder();

        // Traverse the sequence in reverse order and get the complement of each base
        for (int i = primer.length() - 1; i >= 0; i--) {
            char base = primer.charAt(i);
            reverseComplement.append(getComplement(base));
        }
        return reverseComplement.toString();
    }

    /**
     * Returns the complementary base for a given nucleotide.
     *
     * @param base The nucleotide base (A, T, C, G).
     * @return The complementary base.
     */
    private char getComplement(char base) {
        return switch (base) {
            case 'A' -> 'T';
            case 'T' -> 'A';
            case 'C' -> 'G';
            case 'G' -> 'C';
            default -> base;  // Return the same character if it's not A, T, C, or G
        };
    }

    /**
     * Calculates the GC content of a primer sequence.
     *
     * @param primer The primer sequence.
     * @return The GC content as a percentage.
     */
    private double calculateGCContent(String primer) {
        if (primer == null || primer.isEmpty()) {
            return 0.0;
        }
        int gcCount = 0;
        for (char base : primer.toCharArray()) {
            if (base == 'G' || base == 'C') gcCount++;
        }
        return ((double) gcCount / primer.length()) * 100;
    }

    /**
     * Calculates the melting point (Tm) of a primer sequence based on nucleotide content.
     *
     * @param primer The primer sequence.
     * @return The calculated melting point.
     */
    private double calculateMeltingPoint(String primer) {
        if (primer == null || primer.isEmpty()) {
            return 0.0;
        }

        // Count occurrences of A, T, G, C in the primer
        int countA = countOccurrences(primer, 'A');
        int countT = countOccurrences(primer, 'T');
        int countG = countOccurrences(primer, 'G');
        int countC = countOccurrences(primer, 'C');
        int totalBases = countA + countT + countG + countC;

        // Use different formulas based on sequence length
        if (primer.length() < 14) {
            return (countA + countT) * 2 + (countG + countC) * 4;
        } else {
            return 64.9 + 41 * (countG + countC - 16.4) / totalBases;
        }
    }

    /**
     * Counts occurrences of a specific nucleotide in a sequence.
     *
     * @param sequence The nucleotide sequence.
     * @param base     The nucleotide to count.
     * @return The number of occurrences of the nucleotide.
     */
    private int countOccurrences(String sequence, char base) {
        int count = 0;
        for (char nucleotide : sequence.toCharArray()) {
            if (nucleotide == base) count++;
        }
        return count;
    }

    /**
     * Calculates the longest stretch of identical consecutive bases in a primer.
     *
     * @param primer The primer sequence.
     * @return The maximum homopolymer stretch.
     */
    private int calculateMaxHomopolymerStretch(String primer) {
        if (primer == null || primer.isEmpty()) {
            return 0;
        }

        int maxStretch = 1;
        int currentStretch = 1;

        // Traverse the sequence to find consecutive identical bases
        for (int i = 1; i < primer.length(); i++) {
            if (primer.charAt(i) == primer.charAt(i - 1)) {
                currentStretch++;
            } else {
                currentStretch = 1;
            }

            // Update max stretch if the current run is longer
            if (currentStretch > maxStretch) {
                maxStretch = currentStretch;
            }
        }
        return maxStretch;
    }

    /**
     * Generates HTML for color-coding a primer sequence by nucleotide.
     *
     * @param primer The primer sequence.
     * @return An HTML string with colored nucleotides.
     */
    public String getColoredPrimerHtml(String primer) {
        if (primer == null || primer.isEmpty()) return "";

        StringBuilder coloredHtml = new StringBuilder();
        coloredHtml.append("5'–");

        // Append each nucleotide with a color span
        for (char nucleotide : primer.toCharArray()) {
            String color = switch (nucleotide) {
                case 'A' -> "red";
                case 'T' -> "blue";
                case 'G' -> "green";
                case 'C' -> "orange";
                default -> "black";
            };
            coloredHtml.append("<span style=\"color:").append(color).append("\">").append(nucleotide).append("</span>");
        }
        return coloredHtml.append("–3'").toString();
    }

    // Getters for accessing analysis results and primer information
    public int getId() {
        return id;
    }

    public String getForwardPrimerName() {
        return forwardPrimerName;
    }

    public String getReversePrimerName() {
        return reversePrimerName;
    }

    // Getters for forward primer analysis results
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

    // Getters for reverse primer analysis results
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

    public String getColoredForwardPrimerHtml() {
        return coloredForwardPrimerHtml;
    }

    public String getColoredReversePrimerHtml() {
        return coloredReversePrimerHtml;
    }
}
