package pl.ante.portfolioanteapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.ante.portfolioanteapp.logic.ProjectService;

@Controller
@RequestMapping("/")
class MainController {

    //--- fields
    private ProjectService projectService;


    //---constr
    MainController(final ProjectService projectService) {
        this.projectService = projectService;
    }






    //---GET
    @GetMapping
    String showIndex(
            @RequestParam(defaultValue = "PL") String lang,
            @RequestParam(defaultValue = "0", name = "type") Integer typeAsInt,
            Model model) {
        return showIndexInMain(lang, typeAsInt, model);
    }

    @GetMapping("/main")
    String showIndexInMain(
            @RequestParam(defaultValue = "PL") String lang,
            @RequestParam(defaultValue = "0", name = "type") Integer typeAsInt,
            Model model) {
        model.addAttribute("lang", lang);
        model.addAttribute("type", typeAsInt);
        model.addAttribute("projects", projectService.createSortedListOfProjectsByType(lang, typeAsInt));
        return "index";
    }




//    @ModelAttribute("allProjectsPl")
//    List<ProjectSimpleInfoReadModel> getProjectSimpleInfoReadModelPl() {
//        return projectService.createSortedListOfProjectsByType("PL", null);
//    }
//
//    @ModelAttribute("allProjectsEn")
//    List<ProjectSimpleInfoReadModel> getProjectSimpleInfoReadModelEn() {
//        return projectService.createSortedListOfProjectsByType("EN", null);
//    }
}



