package pl.ante.portfolioanteapp.logic;

import org.apache.commons.lang3.NotImplementedException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.ante.portfolioanteapp.model.*;
import pl.ante.portfolioanteapp.model.projection.ProjectSimpleInfoReadModel;
import pl.ante.portfolioanteapp.model.projection.ProjectWriteModel;

import java.lang.reflect.Field;
import java.time.Month;
import java.time.Year;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ProjectServiceTest {


    //---tests
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
    void createProjectFromWriteModel__goodTypesIdProvided__choosesCorrectTypesAndAddsThemToProjectObject() {

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
    void createProjectFromWriteModel__badIdTypeProvided__throwsIllegalArgumentException() {

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

    @Test
    @DisplayName("should populate list of all polish projects when lang is NULL and type is not valid")
    void createListOfAllProjects__langIsNull__populateListWithPLProjects() {

        //given
        InMemoryProjectRepository inMemoryProjectRepository = inMemoryProjectRepositoryFilledWithProjects();
        //system under test
        var toTest = new ProjectService(inMemoryProjectRepository, null);

        //when
        List<ProjectSimpleInfoReadModel> listOfAllProjects = toTest.createSortedListOfProjectsByType(null, null);

        //then
        assertThat(listOfAllProjects)
                .isNotEmpty()
                .extracting(ProjectSimpleInfoReadModel::getName)
                .contains("nazwaPl_1")
                .doesNotContain("nameEn_1");
        assertThat(listOfAllProjects.get(0).getName()).isEqualTo("nazwaPl_1");
        assertThat(listOfAllProjects.get(1).getName()).isEqualTo("nazwaPl_2");
        assertThat(listOfAllProjects.get(2).getName()).isEqualTo("nazwaPl_3");
    }

    @Test
    @DisplayName("should populate list of all polish projects when lang is not PL or EN")
    void createListOfAllProjects__langIsNotPLOrEN__populateListWithPLProjects() {

        //given
        InMemoryProjectRepository inMemoryProjectRepository = inMemoryProjectRepositoryFilledWithProjects();
        //and
        String lang = "DE";
        //system under test
        var toTest = new ProjectService(inMemoryProjectRepository, null);

        //when
        List<ProjectSimpleInfoReadModel> listOfAllProjects = toTest.createSortedListOfProjectsByType(lang, null);

        //then
        assertThat(listOfAllProjects)
                .isNotEmpty()
                .extracting(ProjectSimpleInfoReadModel::getName)
                .contains("nazwaPl_1")
                .doesNotContain("nameEn_1");
        assertThat(listOfAllProjects.get(0).getName()).isEqualTo("nazwaPl_1");
        assertThat(listOfAllProjects.get(1).getName()).isEqualTo("nazwaPl_2");
        assertThat(listOfAllProjects.get(2).getName()).isEqualTo("nazwaPl_3");
    }

    @Test
    @DisplayName("should populate list of all polish projects when lang is not PL or EN and type in NULL")
    void createListOfAllProjects__langIsNotPLOrENAndCategoryIsNotValid__populateListWithPLProjects() {

        //given
        InMemoryProjectRepository inMemoryProjectRepository = inMemoryProjectRepositoryFilledWithProjects();
        //and
        String lang = "DE";
        //system under test
        var toTest = new ProjectService(inMemoryProjectRepository, null);

        //when
        List<ProjectSimpleInfoReadModel> listOfAllProjects = toTest.createSortedListOfProjectsByType(lang, null);

        //then
        assertThat(listOfAllProjects)
                .isNotEmpty()
                .extracting(ProjectSimpleInfoReadModel::getName)
                .contains("nazwaPl_1")
                .doesNotContain("nameEn_1");
        assertThat(listOfAllProjects.get(0).getName()).isEqualTo("nazwaPl_1");
        assertThat(listOfAllProjects.get(1).getName()).isEqualTo("nazwaPl_2");
        assertThat(listOfAllProjects.get(2).getName()).isEqualTo("nazwaPl_3");
    }

    @ParameterizedTest
    @ValueSource(ints = {-10, -2, 0, 8, 15, 500})
    @DisplayName("should populate list of all polish projects when lang is not PL or EN and type has wrong values")
    void createListOfAllProjects__langIsNotPLOrENAndCategoryHasWrongValues__populateListWithPLProjects(int number) {

        //given
        InMemoryProjectRepository inMemoryProjectRepository = inMemoryProjectRepositoryFilledWithProjects();
        //and
        String lang = "DE";
        //and
        Integer category = number;
        //and
        InMemoryTypeRepository inMemoryTypeRepository = inMemoryTypeRepository();
        //system under test
        var toTest = new ProjectService(inMemoryProjectRepository, inMemoryTypeRepository);

        //when
        List<ProjectSimpleInfoReadModel> listOfAllProjects = toTest.createSortedListOfProjectsByType(lang, category);

        //then
        assertThat(listOfAllProjects)
                .isNotEmpty()
                .extracting(ProjectSimpleInfoReadModel::getName)
                .contains("nazwaPl_1")
                .doesNotContain("nameEn_1");
        assertThat(listOfAllProjects.get(0).getName()).isEqualTo("nazwaPl_1");
        assertThat(listOfAllProjects.get(1).getName()).isEqualTo("nazwaPl_2");
        assertThat(listOfAllProjects.get(2).getName()).isEqualTo("nazwaPl_3");
    }

    @Test
    @DisplayName("should populate list of all polish projects when lang is PL")
    void createListOfAllProjects__langIsPL__populateListWithPLProjects() {

        //given
        InMemoryProjectRepository inMemoryProjectRepository = inMemoryProjectRepositoryFilledWithProjects();
        //and
        String lang = "PL";
        //system under test
        var toTest = new ProjectService(inMemoryProjectRepository, null);

        //when
        List<ProjectSimpleInfoReadModel> listOfAllProjects = toTest.createSortedListOfProjectsByType(lang, null);

        //then
        assertThat(listOfAllProjects)
                .isNotEmpty()
                .extracting(ProjectSimpleInfoReadModel::getName)
                .contains("nazwaPl_1")
                .doesNotContain("nameEn_1");
        assertThat(listOfAllProjects.get(0).getName()).isEqualTo("nazwaPl_1");
        assertThat(listOfAllProjects.get(1).getName()).isEqualTo("nazwaPl_2");
        assertThat(listOfAllProjects.get(2).getName()).isEqualTo("nazwaPl_3");
    }

    @Test
    @DisplayName("should populate list of all English projects when lang is En")
    void createListOfAllProjects__langIsEn__populateListWithPLProjects() {

        //given
        InMemoryProjectRepository inMemoryProjectRepository = inMemoryProjectRepositoryFilledWithProjects();
        //and
        String lang = "EN";
        //system under test
        var toTest = new ProjectService(inMemoryProjectRepository, null);

        //when
        List<ProjectSimpleInfoReadModel> listOfAllProjects = toTest.createSortedListOfProjectsByType(lang, null);

        //then
        assertThat(listOfAllProjects)
                .isNotEmpty()
                .extracting(ProjectSimpleInfoReadModel::getName)
                .contains("nameEn_1")
                .doesNotContain("nazwaPl_1");
        assertThat(listOfAllProjects.get(0).getName()).isEqualTo("nameEn_1");
        assertThat(listOfAllProjects.get(1).getName()).isEqualTo("nameEn_2");
        assertThat(listOfAllProjects.get(2).getName()).isEqualTo("nameEn_3");
    }

    @Test
    @DisplayName("should populate list of typed polish projects when lang is PL and chosen type is OK")
    void createListOfCategorizedProjects__langIsPLAnd__populateListWithPLProjects() {

        //given
        InMemoryProjectRepository inMemoryProjectRepository = inMemoryProjectRepositoryFilledWithCorrectlyTypedProjects();
        //and
        InMemoryTypeRepository inMemoryTypeRepository = inMemoryTypeRepository();
        //and
        String lang = "PL";
        //and
        int typeId = 1;
        //system under test
        var toTest = new ProjectService(inMemoryProjectRepository, inMemoryTypeRepository);

        //when
        List<ProjectSimpleInfoReadModel> listOfCategorisedProjects = toTest.createSortedListOfProjectsByType(lang, typeId);

        //then
        assertThat(listOfCategorisedProjects)
                .isNotEmpty()
                .hasSize(7)
                .extracting(ProjectSimpleInfoReadModel::getName)
                .contains("nazwaCOMP_INT_EXT_ANI_PROD_PANO_AR");

    }

    @ParameterizedTest
    @MethodSource("createTypeAmountArgumentsList")
    @DisplayName("should have exact list objects amount of typed projects when chosen type is OK")
    void createListOfCategorizedProjects__langIsPLAndCategoryWithResultAmountProvided__populateListWithExactProjectsAmount(int typeId, int amountOfProjectsInList) {

        //given
        InMemoryProjectRepository inMemoryProjectRepository = inMemoryProjectRepositoryFilledWithCorrectlyTypedProjects();
        //and
        InMemoryTypeRepository inMemoryTypeRepository = inMemoryTypeRepository();
        //and
        String lang = "PL";
        //system under test
        var toTest = new ProjectService(inMemoryProjectRepository, inMemoryTypeRepository);

        //when
        List<ProjectSimpleInfoReadModel> listOfCategorisedProjects = toTest.createSortedListOfProjectsByType(lang, typeId);

        //then
        assertThat(listOfCategorisedProjects)
                .isNotEmpty()
                .hasSize(amountOfProjectsInList);

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

//        @Override
//        public void delete(final Project entity) {
//
//            for (Map.Entry<Integer, Project> entry : map.entrySet()) {
//                if (entry.getValue().equals(entity)) {
//                    map.remove(entry.getKey());
//                }
//            }
//        }


        @Override
        public void deleteById(final Integer id) {
            if (map.containsKey(id)) {
                map.remove(id);
            }
        }

        @Override
        public boolean existsById(final Integer id) {
            return map.values().stream()
                    .anyMatch(project -> project.getId() == id);
        }
    };
    private InMemoryProjectRepository inMemoryProjectRepositoryFilledWithProjects() {
        var repository =  new InMemoryProjectRepository();
        fillWithProjects(repository);
        return repository;
    }
    private static void fillWithProjects(ProjectRepository repository) {
        Project mockproject1 = createProject("nazwaPl_2", "nameEn_2", Year.of(2000), Month.of(12), List.of(1,3));
        Project mockproject2 = createProject("nazwaPl_3", "nameEn_3",Year.of(2000), Month.of(1), List.of(2));
        Project mockproject3 = createProject("nazwaPl_1", "nameEn_1",Year.of(2020), Month.of(5), List.of(5, 7));
        repository.save(mockproject1);
        repository.save(mockproject2);
        repository.save(mockproject3);

    }
    private InMemoryProjectRepository inMemoryProjectRepositoryFilledWithCorrectlyTypedProjects() {
        var repository =  new InMemoryProjectRepository();
        fillWithCategorizedProjects(repository);
        return repository;
    }
    private static void fillWithCategorizedProjects(ProjectRepository repository) {
        //1, new Type(1, "COMPETITION"),
        //2, new Type(2, "INTERIOR"),
        //3, new Type(3, "EXTERIOR"),
        //4, new Type(4, "ANIMATION"),
        //5, new Type(5, "PRODUCT_MODEL"),
        //6, new Type(6, "PANORAMA"),
        //7, new Type(7, "AR_APP")
        Project mockCOMP = createProject("nazwaPl_COMP", "nameEn_COMP", Year.of(1995), Month.of(4), List.of(1));
        Project mockCOMP_INT = createProject("nazwaPl_COMP_INT", "nameEn_COMP_INT",Year.of(1995), Month.of(1), List.of(1,2));
        Project mockprojectCOMP_INT_EXT= createProject("nazwaPl_COMP_INT_EXT", "nameEn_COMP_INT_EXT",Year.of(2020), Month.of(5), List.of(1, 2, 3));
        Project mockprojectCOMP_INT_EXT_ANI= createProject("nazwaPl_COMP_INT_EXT_ANI", "nameEn_COMP_INT_EXT_ANI",Year.of(2019), Month.of(3), List.of(1, 2, 3, 4));
        Project mockprojectCOMP_INT_EXT_ANI_PROD= createProject("nazwaPl_COMP_INT_EXT_ANI_PROD", "nameEn_COMP_INT_EXT_ANI_PROD",Year.of(2019), Month.of(1), List.of(1, 2, 3, 4, 5));
        Project mockprojectCOMP_INT_EXT_ANI_PROD_PANO= createProject("nazwaPl_COMP_INT_EXT_ANI_PROD_PANO", "nameEn_COMP_INT_EXT_ANI_PROD_PANO",Year.of(2019), Month.of(12), List.of(1, 2, 3, 4, 5, 6));
        Project mockprojectCOMP_INT_EXT_ANI_PROD_PANO_AR= createProject("nazwaCOMP_INT_EXT_ANI_PROD_PANO_AR", "nameEn_COMP_INT_EXT_ANI_PROD_PANO_AR",Year.of(2020), Month.of(5), List.of(1, 2, 3, 4, 5, 6, 7));
        repository.save(mockCOMP);
        repository.save(mockCOMP_INT);
        repository.save(mockprojectCOMP_INT_EXT);
        repository.save(mockprojectCOMP_INT_EXT_ANI);
        repository.save(mockprojectCOMP_INT_EXT_ANI_PROD);
        repository.save(mockprojectCOMP_INT_EXT_ANI_PROD_PANO);
        repository.save(mockprojectCOMP_INT_EXT_ANI_PROD_PANO_AR);

    }
    private static Project createProject(String namePl, String nameEn, Year year, Month month, List<Integer> typesIds) {

        InMemoryTypeRepository inMemoryTypeRepository = inMemoryTypeRepository();

        Project result = new Project();
        result.setNamePl(namePl);
        result.setNameEn(nameEn);
        result.setYear(year);
        result.setMonth(month);
        result.setCityPl("cityPl");
        result.setCityEn("cityEn");
        result.setCountryPl("countryPl");
        result.setCountryEn("countryEn");
        result.setClient("client");
        result.setIcoPath("icoPath");
        result.setTypes(typesIds.stream()
                .map(typeId -> inMemoryTypeRepository.findById(typeId).get())
                .collect(Collectors.toList())
        );
        result.setImages(Set.of(
                new ProjectImage("path1", true, 2),
                new ProjectImage("path2", false, 1)
        ));

        return result;
    }



    //inMemory Type Repo
    private static InMemoryTypeRepository inMemoryTypeRepository() {
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

        @Override
        public List<Type> findAll() {
            return map.values().stream()
                    .collect(Collectors.toList());
        }

        @Override
        public Type save(final Type entity) {
            throw new NotImplementedException();
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
                new ProjectImage("path1", true, 2),
                new ProjectImage("path2", false, 1)
        );
        ProjectWriteModel projectWriteModel = createProjectWriteModel(goodValuesSet, imagesSet);
        return projectWriteModel;
    }

    //createTypeAmountArgumentsList
    private static Stream<Arguments> createTypeAmountArgumentsList() {
        return Stream.of(
                Arguments.of(1, 7),
                Arguments.of(2, 6),
                Arguments.of(3, 5),
                Arguments.of(4, 4),
                Arguments.of(5, 3),
                Arguments.of(6, 2),
                Arguments.of(7, 1)
        );
    }


}