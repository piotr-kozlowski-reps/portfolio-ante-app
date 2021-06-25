package pl.ante.portfolioanteapp.controller;

import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.ante.portfolioanteapp.logic.ProjectService;
import pl.ante.portfolioanteapp.model.Project;
import pl.ante.portfolioanteapp.model.ProjectRepository;
import pl.ante.portfolioanteapp.model.projection.ProjectSimpleInfoReadModel;
import pl.ante.portfolioanteapp.model.projection.ProjectWriteModel;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
class ProjectController {


    private final ProjectRepository repository;
    private final ProjectService projectService;


    //---
    ProjectController(final ProjectRepository repository, final ProjectService projectService) {
        this.repository = repository;
        this.projectService = projectService;
    }


    //---POST
    @PostMapping("/projects")
    ResponseEntity<Project> createProject(@RequestBody @Valid ProjectWriteModel projectWriteModel) {
        Project result = projectService.createProjectFromWriteModel(projectWriteModel);
        return ResponseEntity.created(URI.create("/" + result.getId())).body(result);
    }



    //---GETs
    @GetMapping(value = "/projects", params = {"!sort", "!page", "!size"})
    ResponseEntity<List<ProjectSimpleInfoReadModel>> readAllProjects(@Param("lang") String lang, @Param("category") Integer category) {
        return ResponseEntity.ok(projectService.createSortedListOfProjectsByType(lang, category));
    }














//    @GetMapping(value = "/projects", params = {"!sort", "!page", "!size"})
//    ResponseEntity<List<Project>> readAllTasks(){
//        return ResponseEntity.ok(repository.findAll());
//    }
//
//    @GetMapping(value = "/projects")
//    ResponseEntity<List<Project>> readAllTasks(Pageable page){
//        return ResponseEntity.ok(repository.findAll(page).getContent());  //Without .getContent returns Page<T> with a lot more info for page (Spring - przeglad / Pageable and Page)
//    }
//
//    @GetMapping("/projects/{id}")
//    ResponseEntity<Project> readById(@PathVariable int id) {
//        return repository.findById(id)
//                .map(project -> ResponseEntity.ok(project))
//                .orElse(ResponseEntity.notFound().build());
//    }




//        @PostMapping("/projects")
//        ResponseEntity<ProjectSimpleInfoReadModel> createProject(@Param("lang") String lang, @RequestBody @Valid ProjectWriteModel projectWriteModel) {
//            Project project = repository.save(projectWriteModel.toProject());
//            ProjectSimpleInfoReadModel result = ProjectSimpleInfoFactory.getInstance().getProjectSimpleInfoReadModel(lang, project);
//        return ResponseEntity.created(URI.create("/" + project.getId())).body(result);
//    }


//    //PUTs
//    @PutMapping("/projects/{id}")
//    ResponseEntity<?> updateProject(@PathVariable int id, @RequestBody @Valid Project toUpdate) {
//        if (!repository.existsById(id)) {
//            return ResponseEntity.notFound().build();
//        }
//        repository.findById(id)
//                .ifPresent(project -> {
//                    project.updateFrom(toUpdate);
//                    repository.save(project);
//                });
//        return ResponseEntity.noContent().build();
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
