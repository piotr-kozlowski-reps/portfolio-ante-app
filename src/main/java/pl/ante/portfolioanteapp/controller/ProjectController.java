package pl.ante.portfolioanteapp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.ante.portfolioanteapp.logic.ProjectService;
import pl.ante.portfolioanteapp.model.Project;
import pl.ante.portfolioanteapp.model.ProjectRepository;
import pl.ante.portfolioanteapp.model.projection.ProjectSimpleInfoReadModel;
import pl.ante.portfolioanteapp.model.projection.ProjectWriteModel;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/projects")
class ProjectController {

    //---fields
    private static Logger logger = LoggerFactory.getLogger(ProjectController.class);
    private final ProjectRepository repository;
    private final ProjectService projectService;


    //---constr
    ProjectController(final ProjectRepository repository, final ProjectService projectService) {
        this.repository = repository;
        this.projectService = projectService;
    }



    //---POST
    @PostMapping
    ResponseEntity<Project> createProject(@RequestBody @Valid ProjectWriteModel projectWriteModel) {
        Project result = projectService.createProjectFromWriteModel(projectWriteModel);
        return ResponseEntity.created(URI.create("/" + result.getId())).body(result);
    }



    //---GET
    @GetMapping(params = {"!sort", "!page", "!size"})
    ResponseEntity<List<ProjectSimpleInfoReadModel>> readAllProjects(
            @RequestParam(name = "lang",defaultValue = "PL") String lang,
            @RequestParam(name = "type", required = false) Integer typeId) {
        return ResponseEntity.ok(projectService.createSortedListOfProjectsByType(lang, typeId));
    }

    @GetMapping("/{id}")
    ResponseEntity<?> readById(@PathVariable int id) {
        if (repository.findById(id).isPresent()) {
            Project project = repository.findById(id).get();
            return ResponseEntity.ok(project);
        } else {
            return ResponseEntity.notFound().build();
        }
    }



    //---PUT
    //TODO: tests
    @PutMapping("/{id}")
    ResponseEntity<?> updateProject(@PathVariable int id, @RequestBody @Valid ProjectWriteModel projectWriteModel) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repository.findById(id)
                .ifPresent(project -> {
                    project.updateFromAndWrite(projectWriteModel, projectService);
                });
        return ResponseEntity.noContent().build();
    }


    //TODO: doesn't persist in DB
    //TODO: tests
    //---DELETEs
    @Transactional
    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteProject(@PathVariable int id) {

        if (repository.findById(id).isPresent()) {
//            repository.deleteById(id);
            projectService.deleteProjectById(id);
            logger.info("Project with id: " + id + " has been deleted");
            return ResponseEntity.noContent().build();
        } else {
            logger.info("Deletion aborted, no project with id: " + id);
            return ResponseEntity.notFound().build();
        }


    }



    //---Error handling
    @ExceptionHandler(IllegalArgumentException.class)
    ResponseEntity<?> handleIllegalArgument(IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }





//    @GetMapping(value = "/projects")
//    ResponseEntity<List<Project>> readAllTasks(Pageable page){
//        return ResponseEntity.ok(repository.findAll(page).getContent());  //Without .getContent returns Page<T> with a lot more info for page (Spring - przeglad / Pageable and Page)
//    }





//    //---HATEOAS
//    @GetMapping
//    ResponseEntity<CollectionModel<Project>> readAllProjects() {
//        List<Project> allProjects = repository.findAll();
//        allProjects.forEach(project -> project.add(linkTo(ProjectController.class).slash(project.getId()).withSelfRel()));
//        Link link = linkTo(ProjectController.class).withSelfRel();
//        CollectionModel<Project> projectCollectionModel = new CollectionModel<>(allProjects, link);
//        return ResponseEntity.ok(projectCollectionModel);
//    }
//
//    @GetMapping("/{id}")
//    ResponseEntity<EntityModel<Project>> getProjectById(@PathVariable int id) {
//        Link link = linkTo(ProjectController.class).slash(id).withSelfRel();
//        Optional<Project> projectById = repository.findById(id);
//        EntityModel<Project> projectEntityModel = EntityModel.of(projectById.get(), link);
//        return ResponseEntity.ok(projectEntityModel);
//    }
}
