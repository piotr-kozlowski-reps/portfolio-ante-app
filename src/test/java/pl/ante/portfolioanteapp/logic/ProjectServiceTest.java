package pl.ante.portfolioanteapp.logic;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.ante.portfolioanteapp.model.*;
import pl.ante.portfolioanteapp.model.projection.ProjectWriteModel;

import java.lang.reflect.Field;
import java.time.Month;
import java.time.Year;
import java.util.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ProjectServiceTest {

    @Test
    @DisplayName("should create Project from write model")
    void createProjectFromWriteModel__writeModelOK__createsAndSavesGroup() {

        //given
        InMemoryProjectRepository inMemoryProjectRepository = inMemoryProjectRepository();
        var countBeforeCall = inMemoryProjectRepository.count();
        //and
        InMemoryTypeRepository inMemoryTypeRepository = inMemoryTypeRepository();
        //and
        ProjectWriteModel projectWriteModel = getProjectWriteModelWithCorrectData();
        //system under test
        var toTest = new ProjectService(inMemoryProjectRepository, inMemoryTypeRepository);

        //when
        Project result = toTest.createProjectFromWriteModel(projectWriteModel);

        //then
        assertThat(result.getNamePl()).isEqualTo("namePl");
        assertThat(result.getNameEn()).isEqualTo("nameEn");
        assertThat(result.getYear()).isEqualTo(Year.of(projectWriteModel.getYear()));
        assertThat(result.getMonth()).isEqualTo(Month.of(projectWriteModel.getMonth()));
        assertThat(result.getCityPl()).isEqualTo("cityPl");
        assertThat(result.getCityEn()).isEqualTo("cityEn");
        assertThat(result.getCountryPl()).isEqualTo("countryPl");
        assertThat(result.getCountryEn()).isEqualTo("countryEn");
        assertThat(result.getClient()).isEqualTo("client");
        assertThat(result.getIcoPath()).isEqualTo("icoPath");
        assertThat(result.getTypes())
                .isNotEmpty()
                .hasSize(2)
                .extracting(Type::getType)
                .contains("COMPETITION", "EXTERIOR");
        assertThat(result.getImages())
                .isNotEmpty()
                .hasSize(2)
                .extracting(ProjectImage::getPath)
                .contains("path1", "path2");
        assertThat(countBeforeCall + 1)
                .isEqualTo(inMemoryProjectRepository.count());

    }


    @Test
    @DisplayName("should choose correct Type from Type repository and add COMPETITION and EXTERIOR types")
    void applyTypes__goodTypesIdProvided__choosesCorrectTypesAndAddsThemToProjectObject() {

        //given
        InMemoryProjectRepository inMemoryProjectRepository = inMemoryProjectRepository();
        //and
        InMemoryTypeRepository inMemoryTypeRepository = inMemoryTypeRepository();
        //and
        ProjectWriteModel projectWriteModel = getProjectWriteModelWithCorrectData();
        //system under test
        var toTest = new ProjectService(inMemoryProjectRepository, inMemoryTypeRepository);

        //when
        Project result = toTest.createProjectFromWriteModel(projectWriteModel);

        //then
        assertThat(result.getTypes())
                .isNotEmpty()
                .hasSize(2)
                .extracting(Type::getType)
                .contains("COMPETITION", "EXTERIOR");

    }

    @Test
    @DisplayName("should throw IllegalArgumentException when couldn't find Type by desired id")
    void createProjectFromWriteModel__badIdType__throwsIllegalArgumentException() {

        //given
        InMemoryProjectRepository inMemoryProjectRepository = inMemoryProjectRepository();
        //and
        InMemoryTypeRepository inMemoryTypeRepository = inMemoryTypeRepository();
        //and
        ProjectWriteModel projectWriteModel = getProjectWriteModelWithCorrectData();
        when(projectWriteModel.getTypes()).thenReturn(Set.of(-1, 13));
        //system under test
        var toTest = new ProjectService(inMemoryProjectRepository, inMemoryTypeRepository);

        //when
        var exception = catchThrowable(() -> toTest.createProjectFromWriteModel(projectWriteModel));

        //then
        assertThat(exception)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("no such Type");
    }







    //--- utils

    //inMemory Project Repo
    private InMemoryProjectRepository inMemoryProjectRepository() {
        return new InMemoryProjectRepository();
    }
    private static class InMemoryProjectRepository implements ProjectRepository{

        private int index = 0;
        private Map<Integer, Project> map = new HashMap<>();

        public int count() {
            return map.size();
        }

        @Override
        public List<Project> findAll() {
            return map.values().stream()
                    .collect(Collectors.toList());
        }

        @Override
        public Optional<Project> findById(final Integer id) {
            return Optional.ofNullable(map.get(id));
        }

        @Override
        public Page<Project> findAll(final Pageable page) {
            return null;
        }

        @Override
        public Project save(final Project entity) {
            if (entity.getId() == 0) {
                try {
                    Field idField = Project.class.getDeclaredField("id");
                    idField.setAccessible(true);
                    idField.set(entity, ++index);
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
                map.put(index, entity);
            }
            map.put(entity.getId(), entity);
            return entity;
        }

        @Override
        public boolean existsById(final Integer id) {
            return map.values().stream()
                    .anyMatch(project -> project.getId() == id);
        }
    };


    //inMemory Type Repo
    private InMemoryTypeRepository inMemoryTypeRepository() {
        return new InMemoryTypeRepository();
    }
    private static class InMemoryTypeRepository implements TypeRepository {

        private Map<Integer, Type> map = init();

        private Map<Integer, Type> init() {
            return Map.of(
                    1, new Type(1, "COMPETITION"),
                    2, new Type(2, "INTERIOR"),
                    3, new Type(3, "EXTERIOR"),
                    4, new Type(4, "ANIMATION"),
                    5, new Type(5, "PRODUCT_MODEL"),
                    6, new Type(6, "PANORAMA"),
                    7, new Type(7, "AR_APP")
            );
        }


        @Override
        public Optional<Type> findById(final Integer id) {
            return Optional.ofNullable(map.get(id));
        }
    }

    //mock WriteModel
    private ProjectWriteModel createProjectWriteModel(Set<Integer> typesSet, Set<ProjectImage> projectImagesSet) {
        var projectWriteModel = mock(ProjectWriteModel.class);
        when(projectWriteModel.getNamePl()).thenReturn("namePl");
        when(projectWriteModel.getNameEn()).thenReturn("nameEn");
        when(projectWriteModel.getYear()).thenReturn(2000);
        when(projectWriteModel.getMonth()).thenReturn(10);
        when(projectWriteModel.getCityPl()).thenReturn("cityPl");
        when(projectWriteModel.getCityEn()).thenReturn("cityEn");
        when(projectWriteModel.getCountryPl()).thenReturn("countryPl");
        when(projectWriteModel.getCountryEn()).thenReturn("countryEn");
        when(projectWriteModel.getClient()).thenReturn("client");
        when(projectWriteModel.getIcoPath()).thenReturn("icoPath");
        when(projectWriteModel.getTypes()).thenReturn(typesSet);
        when(projectWriteModel.getImages()).thenReturn(projectImagesSet);
        return projectWriteModel;
    }

    //WriteModelWithCorrectData
    private ProjectWriteModel getProjectWriteModelWithCorrectData() {
        Set<Integer> goodValuesSet = Set.of(1, 3);
        Set<ProjectImage> imagesSet = Set.of(
                new ProjectImage("path1", true),
                new ProjectImage("path2", false)
        );
        ProjectWriteModel projectWriteModel = createProjectWriteModel(goodValuesSet, imagesSet);
        return projectWriteModel;
    }


}