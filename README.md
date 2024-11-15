# Primer analysis and evaluation tool 

This tool is a web-based application designed to analyse DNA primers and Reverse primer sequences and get a simple analysis of key properties like G/C content, melting point, homopolymer stretch, and identity comparisons. This tool is useful for a quick lookup for researchers working with PCR and other DNA amplification techniques.

## Table of Contents
- [Project Overview](#project-overview)
- [Features](#features)
- [Getting Started](#getting-started)
    - [Prerequisites](#prerequisites)
    - [Installation](#installation)
    - [Running the Application](#running-the-application)
- [Usage](#usage)
    - [Entering Primer Sequences](#entering-primer-sequences)
    - [Analysis Results](#analysis-results)
    - [History of Analyses](#history-of-analyses)
- [License](#license)
- [Contact](#contact)

## Project Overview

This tool provides a easy to use web interface to quickly analyse primer(s) and name them. it gives back a few key properties like G/C content, melting point, homopolymer stretch, and identity comparisons.
Additionally, the application saves the last 5 analyses in a history log, enabling users to quickly reference previous results.

## Features 

- **Primer Sequence Analysis**: Analyze both forward and reverse primers, or just the forward primer if no reverse primer is provided.
- **Colored visualisation of** the primer and its length
- **G/C Content Calculation**: Provides the percentage of guanine and cytosine nucleotides in the sequence.
- **Melting Point Calculation**: Calculates the melting point (Tm) for primers using two different formulas based on sequence length.
- **Max Homopolymer Stretch**: Detects the longest run of consecutive identical nucleotides in the primer.
- **3' Intramolecular Identity**: Compares the 3' end of a primer with its reverse complement to find the largest matching segment.
- **3' Intermolecular Identity**: Compares the 3' end of the forward primer with the reverse complement of the reverse primer.
- **Analysis History**: Stores and displays the last 5 primer analyses for easy reference.

## Getting Started

### Prerequisites

To run this application, you need:

- Java Development Kit (JDK) 11 or higher
- Apache Maven or Gradle (for building and running the application)
- Spring Boot (integrated with the application)

### Installation
1. **Clone the Repository:**
```bash
 git clone https://github.com/Mpslik/Primer_evaluation_tool
```
2. **Navigate to the Project Directory:**
3. **Build the Project**: using gradle, run:
```bash
./gradlew build
```

### Running the Application

1.**Run Gradle:**
```bash
./gradlew bootRun
```

**OR**

2. **Run the Application Class Directly:** Alternatively, you can run the application directly by executing the main method in PrimerEvaluationToolApplication.java:
  - In your IDE (such as IntelliJ IDEA or Eclipse), navigate to: ```src/main/java/nl/mats/primer_evaluation_tool/PrimerEvaluationToolApplication.java```
  - Right-click on ```PrimerEvaluationToolApplication``` and select **Run 'PrimerEvaluationToolApplication'** (or equivalent in your IDE).

Once started, the application will be available at ```http://localhost:8080```

## Usage

### Entering Primer Sequences
1. Open the application in your web browser at ```http://localhost:8080```
2. On the home page, enter the details for your primers:
   - **Forward Primer Name:** Optional descriptive name for the forward primer.
   - **Forward Primer Sequence:** Enter the forward primer sequence (5' to 3' direction).
   - **Reverse Primer Name:** Optional descriptive name for the reverse primer.
   - **Reverse Primer Sequence:** Enter the reverse primer sequence (5' to 3' direction).

3. click **Analzye** to process the sequences.

### Analysis Results

After submitting the primer sequences, the results page will display:

- **Sequence Visualization:** A color-coded representation of each nucleotide in the sequence, along with its length.
- **G/C Content:** The percentage of G and C nucleotides in each primer.
- **Melting Point:** The calculated melting point based on primer length.
- **Max Homopolymer Stretch:** The longest stretch of consecutive identical nucleotides.
- **3' Intramolecular Identity:** Maximum matching segment at the 3' end of the primer and its reverse complement.
- **3' Intermolecular Identity:** Maximum matching segment at the 3' end of the forward primer and the reverse complement of the reverse primer.


### History of Analyses
The last 5 analyses are saved in a history log for easy reference. You can view past results by selecting any entry in the history list, which will load the results of that specific analysis.

## Contact
- Mats Slik
- Email: M.P.Slik@st.hanze.nl