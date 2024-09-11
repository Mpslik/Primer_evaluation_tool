package nl.mats.primer_evaluation_tool;

import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class AnalysesHistory {

    // LinkedList to store the history (last 10 analyses)
    private final LinkedList<PrimerAnalyses> history = new LinkedList<>();

    // Add a new analysis to the history
    public void addAnalysis(PrimerAnalyses analysis) {
        if (history.size() >= 10) {
            history.removeFirst();  // Remove the oldest analysis if history exceeds 10
        }
        history.add(analysis);
    }

    // Get the current history
    public List<PrimerAnalyses> getHistory() {
        return history;
    }
}