package pl.ante.portfolioanteapp.logic;

import org.apache.commons.lang3.NotImplementedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.ante.portfolioanteapp.model.*;
import pl.ante.portfolioanteapp.model.projection.ProjectSimpleInfoFactory;
import pl.ante.portfolioanteapp.model.projection.ProjectSimpleInfoReadModel;
import pl.ante.portfolioanteapp.model.projection.ProjectSimpleInfoReadModelPL;
import pl.ante.portfolioanteapp.model.projection.ProjectWriteModel;

import java.time.Month;
import java.time.Year;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProjectService {

    //---fields
    Logger logger = LoggerFactory.getLogger(ProjectService.class);
    private ProjectRepository projectRepository;
    private TypeRepository typeRepository;
    private ProjectImageRepository projectImageRepository;


    //---constr
    ProjectService(final ProjectRepository projectRepository, final TypeRepository typeRepository, final ProjectImageRepository projectImageRepository) {
        this.projectRepository = projectRepository;
        this.typeRepository = typeRepository;
        this.projectImageRepository = projectImageRepository;
    }


    //---methods

    //---POST/PUT
    public Project createProjectFromWriteModel(final ProjectWriteModel projectWriteModel) {

        var result = new Project();
        result.setNamePl(projectWriteModel.getNamePl());
        result.setNameEn(projectWriteModel.getNameEn());
        result.setYear(Year.of(projectWriteModel.getYear()));
        result.setMonth(Month.of(projectWriteModel.getMonth()));
        result.setCityPl(projectWriteModel.getCityPl());
        result.setCityEn(projectWriteModel.getCityEn());
        result.setCountryPl(projectWriteModel.getCountryPl());
        result.setCountryEn(projectWriteModel.getCountryEn());
        result.setClient(projectWriteModel.getClient());
        result.setIcoPath(projectWriteModel.getIcoPath());
        result.setIcoAltPl(projectWriteModel.getIcoAltPl());
        result.setIcoAltEn(projectWriteModel.getIcoAltEn());
        result.setTypes(applyTypes(projectWriteModel));
        result.setImages(applyImages(projectWriteModel));

        return projectRepository.save(result);
    }
    public void updateProjectFromWriteModel(final Project project, final ProjectWriteModel projectWriteModel) {
        project.setNamePl(projectWriteModel.getNamePl());
        project.setNameEn(projectWriteModel.getNameEn());
        project.setYear(Year.of(projectWriteModel.getYear()));
        project.setMonth(Month.of(projectWriteModel.getMonth()));
        project.setCityPl(projectWriteModel.getCityPl());
        project.setCityEn(projectWriteModel.getCityEn());
        project.setCountryPl(projectWriteModel.getCountryPl());
        project.setCountryEn(projectWriteModel.getCountryEn());
        project.setClient(projectWriteModel.getClient());
        project.setIcoPath(projectWriteModel.getIcoPath());
        project.setTypes(applyTypes(projectWriteModel));
        project.setImages(applyImages(projectWriteModel));
        projectRepository.save(project);
    }
    private List<Type> applyTypes(final ProjectWriteModel projectWriteModel) {

        List<Type> types = new ArrayList<>();

        projectWriteModel.getTypes().stream()
                .forEach(typeId -> {
                    if(typeRepository.findById(typeId).isEmpty()) {
                        logger.error("There's no such Type when project creation, or Type is eq to 0 -> means show whole list despite of Types");
                        throw new IllegalArgumentException("There's no such Project type");
                    }
                    else types.add(typeRepository.findById(typeId).get());
                });

        return types;
    }
    private Set<ProjectImage> applyImages(final ProjectWriteModel projectWriteModel) {
        return projectWriteModel.getImages().stream()
                .map(imageInfo -> new ProjectImage(imageInfo.getPath(), imageInfo.getBig(), imageInfo.getOrder(), imageInfo.getImageAltPl(), imageInfo.getImageAltEn()))
                .collect(Collectors.toSet());
    }


    //---GET
    public List<ProjectSimpleInfoReadModel> createSortedListOfProjectsByType(final String lang, final Integer typeAsInt) {

        //bad category
        if (isTypeNotValid(typeAsInt)) {
            return createListOfProjectsReadModelsInSpecifiedLangAndOrder(projectRepository.findAll(), lang);
        }

        //good category
        Type foundType = typeRepository.findById(typeAsInt).get();

        List<Project> projectListByType = projectRepository.findAll().stream()
                .filter(project -> {
                    for (Type type : project.getTypes()) {
                        if(type.equals(foundType)) return true;
                    }
                    return false;
                })
                .collect(Collectors.toList());

        return createListOfProjectsReadModelsInSpecifiedLangAndOrder(projectListByType, lang);

    }
    public Project getProjectById(final int id) {
        if (projectRepository.findById(id).isEmpty()) {
            throw new IllegalArgumentException("Project with id " + id + " is not available");
        }
        return projectRepository.findById(id).get();
    }

    //---DELETE
    public void deleteProjectById(final int id) {

        Optional<Project> project = projectRepository.findById(id);

        if (project.isPresent()) {

            Set<ProjectImage> projectImages = project.get().getImages();

            for (Iterator<ProjectImage> iterator = projectImages.iterator(); iterator.hasNext(); ) {
                ProjectImage image = iterator.next();
                projectImages.remove(image);
                projectImageRepository.delete(image);
            }

            List<Type> projectTypes = project.get().getTypes();
            for (Iterator<Type> iterator = projectTypes.iterator(); iterator.hasNext(); ) {
                Type type = iterator.next();
                projectTypes.remove(type);
            }

            projectRepository.delete(project.get());



//            project.get().getImages().stream()
//                    .forEach(image -> projectImageRepository.delete(image));
//
//            projectRepository.delete(project.get());
//            return true;
//
//
//        }
//
//        return false;
        }
    }







    //---utils
    private List<ProjectSimpleInfoReadModel> createListOfProjectsReadModelsInSpecifiedLangAndOrder(
            final List<Project> projects,
            final String lang) {

        //language is not valid
        if (langIsNotPlOrEn(lang)) {
            logger.info("Null or bad language desired - only PL and EN are expected - populated by default PL list");

            return projects.stream()
                    .sorted(Comparator.comparing(Project::getYear).thenComparing(Project::getMonth).thenComparing(Project::getId).reversed())
                    .map(ProjectSimpleInfoReadModelPL::new)
                    .collect(Collectors.toList());
        }

        //language is valid
        return projects.stream()
                .sorted(Comparator.comparing(Project::getYear).thenComparing(Project::getMonth).thenComparing(Project::getId).reversed())
                .map(project -> {
                    ProjectSimpleInfoReadModel result = ProjectSimpleInfoFactory.getInstance().getProjectSimpleInfoReadModel(lang, project);
                    return result;
                })
                .collect(Collectors.toList());
    }


    private boolean langIsNotPlOrEn(final String lang) {
        return lang == null || !lang.toUpperCase().equals("PL") && !lang.toUpperCase().equals("EN");
    }
    private boolean isTypeNotValid(final Integer category) {
        return category == null || category < 1 || category > typeRepository.findAll().size();
    }



}