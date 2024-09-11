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
        String forwardPrimer = primerObject.getForwardPrimer();
        String reversePrimer = primerObject.getReversePrimer();

        // Check if both primers are valid
        // Validate the forward primer
        if (!primerObject.isValidPrimer(forwardPrimer)) {
            model.addAttribute("error", "Invalid forward primer sequence. Only A, T, G, C, U characters are allowed.");
            return "start_page";  // Return to the form with an error message
        }

        // Validate the reverse primer
        if (reversePrimer != null && !reversePrimer.isEmpty() && !primerObject.isValidPrimer(reversePrimer)) {
            model.addAttribute("error", "Invalid reverse primer sequence. Only A, T, G, C, U characters are allowed.");
            return "start_page";  // Return to the form with an error message
        }

        // Perform analysis if valid
        PrimerAnalyses analysisResult = new PrimerAnalyses(forwardPrimer, reversePrimer);

        // Add the result to the history
        analysesHistory.addAnalysis(analysisResult);

        // Add the result and history to the model
        model.addAttribute("analysis", analysisResult);
        model.addAttribute("history", analysesHistory.getHistory());

        return "results_page"; // Render the results page
    }
}
