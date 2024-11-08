package nl.mats.primer_evaluation_tool;

import java.util.LinkedList;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * Service class that manages the history of primer analyses.
 * Stores a list of the most recent analyses and provides methods
 * for adding, retrieving, and generating IDs for analyses.
 */
@Service
public class AnalysesHistory {

    /** Stores up to the last 5 {@code PrimerAnalyses} in history. */
    private final LinkedList<PrimerAnalyses> history = new LinkedList<>();

    /** Counter for generating unique IDs for each analysis. */
    private int currentId = 1;

    /**
     * Adds a new primer analysis to the history. If the history exceeds 5 entries,
     * the oldest analysis is removed.
     *
     * @param analysis the {@code PrimerAnalyses} object to add to the history.
     */
    public void addAnalysis(PrimerAnalyses analysis) {
        if (history.size() >= 5) {
            history.removeFirst();  // Remove the oldest analysis if history exceeds 5
        }
        history.add(analysis);
    }

    /**
     * Retrieves the current list of stored primer analyses.
     *
     * @return a {@code List} of {@code PrimerAnalyses} objects representing the history.
     */
    public List<PrimerAnalyses> getHistory() {
        return history;
    }

    /**
     * Retrieves a primer analysis by its unique ID.
     *
     * @param id the ID of the analysis to retrieve.
     * @return the {@code PrimerAnalyses} object with the specified ID, or {@code null} if not found.
     */
    public PrimerAnalyses getAnalysisById(int id) {
        return history.stream()
                .filter(analysis -> analysis.getId() == id)
                .findFirst()
                .orElse(null);
    }

    /**
     * Generates a new unique ID for a primer analysis.
     *
     * @return a unique integer ID.
     */
    public int generateNewid() {
        return currentId++;
    }
}