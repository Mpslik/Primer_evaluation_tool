package nl.mats.primer_evaluation_tool;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.context.request.WebRequest;

@org.springframework.stereotype.Controller
public class Controller {

    @Autowired
    private AnalysesHistory analysesHistory;

    // Show the form with the history (from session)
    @GetMapping("/")
    public String showForm(Model model, WebRequest webRequest) {
        // Retrieve the session from WebRequest
        if (webRequest.getAttribute("history", WebRequest.SCOPE_SESSION) == null) {
            webRequest.setAttribute("history", analysesHistory.getHistory(), WebRequest.SCOPE_SESSION);
        }

        // Add the primer form and history to the model
        model.addAttribute("primerForm", new PrimerObject());
        model.addAttribute("history", webRequest.getAttribute("history", WebRequest.SCOPE_SESSION));
        return "start_page"; // This will render start_page.html
    }

    // Analyze the primer, validate, and save the analysis to history
    @PostMapping("/analyze")
    public String analyzePrimer(@ModelAttribute PrimerObject primerObject, Model model, WebRequest webRequest) {
        String forwardPrimer = primerObject.getForwardPrimer();
        String reversePrimer = primerObject.getReversePrimer();
        String forwardPrimerName = primerObject.getForwardPrimerName();
        String reversePrimerName = primerObject.getReversePrimerName();

        // Validate the forward primer
        if (!primerObject.isValidPrimer(forwardPrimer)) {
            model.addAttribute("error", "Invalid forward primer sequence. Only A, T, G, C, U characters are allowed.");
            return "start_page"; // Return to the start page with an error
        }

        // Validate the reverse primer
        if (reversePrimer != null && !reversePrimer.isEmpty() && !primerObject.isValidPrimer(reversePrimer)) {
            model.addAttribute("error", "Invalid reverse primer sequence. Only A, T, G, C, U characters are allowed.");
            return "start_page"; // Return to the start page with an error
        }

        // Perform analysis
        int newId = analysesHistory.generateNewid();
        PrimerAnalyses analysisResult = new PrimerAnalyses(newId, forwardPrimer, reversePrimer, forwardPrimerName, reversePrimerName);

        // Add the analysis result to history
        analysesHistory.addAnalysis(analysisResult);

        // Update the session with the new history
        webRequest.setAttribute("history", analysesHistory.getHistory(), WebRequest.SCOPE_SESSION);

        // Add the analysis result and history to the model for the results page
        model.addAttribute("analysis", analysisResult);
        model.addAttribute("history", analysesHistory.getHistory());

        return "results_page"; // This will render results_page.html
    }

    // View a specific analysis by its ID
    @GetMapping("/analysis/{id}")
    public String viewAnalysis(@PathVariable int id, Model model, WebRequest webRequest) {
        // Retrieve the analysis by ID from the history
        PrimerAnalyses analysis = analysesHistory.getAnalysisById(id);
        if (analysis == null) {
            return "error_page"; // Handle invalid ID
        }

        // Add the retrieved analysis and history to the model
        model.addAttribute("analysis", analysis);
        model.addAttribute("history", webRequest.getAttribute("history", WebRequest.SCOPE_SESSION));

        return "results_page"; // Render the results page for this specific analysis
    }
}
