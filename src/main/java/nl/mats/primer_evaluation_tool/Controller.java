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
        model.addAttribute("primerForm", new PrimerObject());
        model.addAttribute("history", analysesHistory.getHistory());
        return "start_page";
    }

    @PostMapping("/analyze")
    public String analyzePrimer(@ModelAttribute PrimerObject primerObject, Model model) {
        String primer1 = primerObject.getPrimer1();
        String primer2 = primerObject.getPrimer2();

        // Check if both primers are valid
        if (!primerObject.isValidPrimer(primer1) || !primerObject.isValidPrimer(primer2)) {
            model.addAttribute("error", "Invalid primer sequence. Only A, T, G, C, U characters are allowed (case-insensitive).");
            return "start_page"; // Return to the form with an error message
        }

        // Perform analysis if valid
        PrimerAnalyses analysisResult = new PrimerAnalyses(primer1, primer2);

        // Add the result to the history
        analysesHistory.addAnalysis(analysisResult);

        // Add the result and history to the model
        model.addAttribute("analysis", analysisResult);
        model.addAttribute("history", analysesHistory.getHistory());

        return "results_page"; // Render the results page
    }
}
