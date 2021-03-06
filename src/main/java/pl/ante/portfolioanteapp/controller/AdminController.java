package pl.ante.portfolioanteapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.ante.portfolioanteapp.logic.ProjectService;
import pl.ante.portfolioanteapp.model.TypeRepository;
import pl.ante.portfolioanteapp.model.projection.ProjectWriteModel;

@Controller
@RequestMapping("/admin")
class AdminController {

    //---fields
    private ProjectService projectService;
    private TypeRepository typeRepository;

    //---constr
    AdminController(final ProjectService projectService, final TypeRepository typeRepository) {
        this.projectService = projectService;
        this.typeRepository = typeRepository;
    }


    //---GET
    @GetMapping
    String adminStart(
            @RequestParam(name = "type", defaultValue = "0") Integer typeAsInt,
            Model model
    ) {
        model.addAttribute("type", typeAsInt);
        model.addAttribute("projects", projectService.createSortedListOfProjectsByType("PL", typeAsInt));
        return "admin";
    }

    @GetMapping("/edit/{id}")
    String adminEditProject(
            @PathVariable int id,
            Model model
    ) {
        model.addAttribute("project", projectService.getProjectById(id));
        return "edit_project";
    }

    @GetMapping("/create")
    String createProjectForm(Model model){
        model.addAttribute("project", new ProjectWriteModel());
        model.addAttribute("types", typeRepository.findAll());
        return "create_project";
    }


}