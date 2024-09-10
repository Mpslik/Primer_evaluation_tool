package nl.mats.primer_evaluation_tool;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import javax.annotation.processing.Generated;

@Controller
public class PrimerEvaluationToolApplication {

    @Autowired
    private AnalysesHistory analysesHistory;

    @GetMapping("/")
    public String showForm(Model model) {
    model.addAttribute("primerForm", new PrimerObject());

    model.addAttribute("history", analysesHistory.getHistory());

    return "start_page";
    }
}
