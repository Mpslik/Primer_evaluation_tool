package nl.mats.primer_evaluation_tool;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.context.request.WebRequest;

/**
 * Controller class for handling requests in the Primer Evaluation Tool application.
 * This controller handles showing the input form, validating and analyzing primer sequences,
 * saving analysis results to session history, and displaying specific analysis details.
 */
@org.springframework.stereotype.Controller
public class Controller {

    @Autowired
    private AnalysesHistory analysesHistory;

    /**
     * Displays the input form for primer sequences along with the analysis history.
     * If there is no history in the session, it initializes it.
     *
     * @param model      The model used to pass attributes to the view.
     * @param webRequest The WebRequest object used to access session attributes.
     * @return The name of the view to render ("start_page").
     */
    @GetMapping("/")
    public String showForm(Model model, WebRequest webRequest) {
        // Initialize session history if it doesn't already exist
        if (webRequest.getAttribute("history", WebRequest.SCOPE_SESSION) == null) {
            webRequest.setAttribute("history", analysesHistory.getHistory(), WebRequest.SCOPE_SESSION);
        }

        // Add the primer input form and history to the model
        model.addAttribute("primerForm", new PrimerObject());
        model.addAttribute("history", webRequest.getAttribute("history", WebRequest.SCOPE_SESSION));
        return "start_page"; // Renders start_page.html
    }

    /**
     * Analyzes the provided primer sequences, validates them, and saves the analysis result to session history.
     * If validation fails, it displays an error message on the input form page.
     *
     * @param primerObject The PrimerObject containing primer details entered by the user.
     * @param model        The model used to pass attributes to the view.
     * @param webRequest   The WebRequest object used to access session attributes.
     * @return The name of the view to render ("results_page" if successful, "start_page" if validation fails).
     */
    @PostMapping("/analyze")
    public String analyzePrimer(@ModelAttribute PrimerObject primerObject, Model model, WebRequest webRequest) {
        String forwardPrimer = primerObject.getForwardPrimer();
        String reversePrimer = primerObject.getReversePrimer();
        String forwardPrimerName = primerObject.getForwardPrimerName();
        String reversePrimerName = primerObject.getReversePrimerName();

        // Validate the forward primer sequence
        if (!primerObject.isValidPrimer(forwardPrimer)) {
            model.addAttribute("error", "Invalid forward primer sequence. Only A, T, G, C, U characters are allowed.");
            return "start_page"; // Returns to the input form with an error message
        }

        // Validate the reverse primer
        if (reversePrimer != null && !reversePrimer.isEmpty() && !primerObject.isValidPrimer(reversePrimer)) {
            model.addAttribute("error", "Invalid reverse primer sequence. Only A, T, G, C, U characters are allowed.");
            return "start_page"; // Returns to the input form with an error message
        }

        // Create a new analysis result with a unique ID
        int newId = analysesHistory.generateNewid();
        PrimerAnalyses analysisResult = new PrimerAnalyses(newId, forwardPrimer, reversePrimer, forwardPrimerName, reversePrimerName);

        // Save the analysis result to the session history
        analysesHistory.addAnalysis(analysisResult);

        // Update the session attribute for history
        webRequest.setAttribute("history", analysesHistory.getHistory(), WebRequest.SCOPE_SESSION);

        // Add the analysis result and updated history to the model for the results page
        model.addAttribute("analysis", analysisResult);
        model.addAttribute("history", analysesHistory.getHistory());

        return "results_page"; // Renders results_page.html with the analysis results
    }

    /**
     * Displays a specific analysis result based on its unique ID.
     * If the ID is invalid, it returns an error page.
     *
     * @param id         The unique ID of the analysis to display.
     * @param model      The model used to pass attributes to the view.
     * @param webRequest The WebRequest object used to access session attributes.
     * @return The name of the view to render ("results_page" if successful, "error_page" if ID is invalid).
     */
    @GetMapping("/analysis/{id}")
    public String viewAnalysis(@PathVariable int id, Model model, WebRequest webRequest) {
        // Retrieve the analysis result by ID from the session history
        PrimerAnalyses analysis = analysesHistory.getAnalysisById(id);
        if (analysis == null) {
            return "error_page"; // Handles invalid IDs by displaying an error page
        }

        // Add the retrieved analysis and history to the model for display
        model.addAttribute("analysis", analysis);
        model.addAttribute("history", webRequest.getAttribute("history", WebRequest.SCOPE_SESSION));

        return "results_page"; // Renders results_page.html with the specific analysis result
    }
}