package nl.mats.primer_evaluation_tool;

import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Service;


@Service
public class AnalysesHistory {

    private final LinkedList<PrimerAnalyses> history = new LinkedList<>();
    private int currentId = 1;
    // Add a new analysis to the history
    public void addAnalysis(PrimerAnalyses analysis) {
        if (history.size() >= 5) {
            history.removeFirst();  // Remove the oldest analysis if history exceeds 10
        }
        history.add(analysis);
    }

    // Get the current history
    public List<PrimerAnalyses> getHistory() {
        return history;
    }

    // retriever
    public PrimerAnalyses getAnalysisById(int id) {
        return history.stream()
                .filter(analysis -> analysis.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public int generateNewid() {
        return currentId++;
    }
}