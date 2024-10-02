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
- **G/C Content Calculation**: Provides the percentage of guanine and cytosine nucleotides in the sequence.
- **Melting Point Calculation**: Calculates the melting point (Tm) for primers using two different formulas based on sequence length.
- **Max Homopolymer Stretch**: Detects the longest run of consecutive identical nucleotides in the primer.
- **3' Intramolecular Identity**: Compares the 3' end of a primer with its reverse complement to find the largest matching segment.
- **3' Intermolecular Identity**: Compares the 3' end of the forward primer with the reverse complement of the reverse primer.
- **Analysis History**: Stores and displays the last 5 primer analyses for easy reference.
- **Responsive Design**: Works seamlessly across desktop and mobile browsers.

##  License

## Contact