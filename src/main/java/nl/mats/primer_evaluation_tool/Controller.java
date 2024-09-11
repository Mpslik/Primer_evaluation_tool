package nl.mats.primer_evaluation_tool;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@org.springframework.stereotype.Controller
public class Controller {

    @Autowired
    private AnalysesHistory analysesHistory;

    @GetMapping("/")
    public String showForm(Model model) {
        model.addAttribute("primerForm", new PrimerObject());  // Assuming PrimerObject represents the form data
        model.addAttribute("history", analysesHistory.getHistory());
        return "start_page";
    }

    @PostMapping("/analyze")
    public String analyzePrimer(@ModelAttribute PrimerObject primerObject, Model model) {
        // Retrieve both primer sequences
        String primer1 = primerObject.getPrimer1();
        String primer2 = primerObject.getPrimer2();

        // Simulate analysis logic
        PrimerAnalyses analysisResult = new PrimerAnalyses(primer1, primer2);

        // Add the result to the history
        analysesHistory.addAnalysis(analysisResult);

        // Add the result and history to the model
        model.addAttribute("result", analysisResult);
        model.addAttribute("history", analysesHistory.getHistory());

        return "results_page";  // Render the results page
    }
}