package pl.ante.portfolioanteapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pl.ante.portfolioanteapp.model.*;
import pl.ante.portfolioanteapp.model.projection.ProjectWriteModel;

import java.time.Month;
import java.time.Year;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("integration")
class ProjectControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProjectRepository repository;


    //--- GETs
    @Test
    @DisplayName("httpGet(/projects/{id}  -> validIdProvided -> returns 2xxSuccessful)")
    void httpGet__projects_Id__returnsGivenProject() throws Exception{

        //given
        int id = repository.save(createProject("nazwaPl_1", "nameEn_1", Year.of(2000), Month.of(12), List.of(3,5))).getId();

        //when + then
        mockMvc.perform(MockMvcRequestBuilders.get("/projects/" + id))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andDo(print())
                .andExpect(content().string(containsString("nazwaPl_1")));
    }

    @Test
    @DisplayName("httpGet(/projects/{id}  -> inValidIdProvided  -> returns notFound)")
    void httpGet__projects_Id__badId__returnsNotFound() throws Exception{

        //given
        int id = repository.save(createProject("nazwaPl_1", "nameEn_1", Year.of(2000), Month.of(12), List.of(3,5))).getId();

        //when + then
        mockMvc.perform(MockMvcRequestBuilders.get("/projects/" + (id + 1)))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @DisplayName("httpGet(/projects?lang=PL)  -> returns 2 items list with polish description)")
    void httpGet_langPL__returnsListOfPolishProjects() throws Exception{

        //given
        repository.save(createProject("nazwaPl_1", "nameEn_1", Year.of(2000), Month.of(12), List.of(3,5))).getId();
        repository.save(createProject("nazwaPl_2", "nameEn_2", Year.of(2010), Month.of(5), List.of(1,2))).getId();

        //when + then
        mockMvc.perform(MockMvcRequestBuilders.get("/projects")
                .param("lang", "PL")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(content().string(containsString("nazwaPl_2")))
                .andExpect(content().string(containsString("nazwaPl_1")));
    }

    @Test
    @DisplayName("httpGet(/projects) no lang  ->  returns 2 items list with defaultpolish description)")
    void httpGet_noLang__returnsListOfPolishProjects() throws Exception{

        //given
        repository.save(createProject("nazwaPl_1", "nameEn_1", Year.of(2000), Month.of(12), List.of(3,5))).getId();
        repository.save(createProject("nazwaPl_2", "nameEn_2", Year.of(2010), Month.of(5), List.of(1,2))).getId();

        //when + then
        mockMvc.perform(MockMvcRequestBuilders.get("/projects")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(content().string(containsString("nazwaPl_2")))
                .andExpect(content().string(containsString("nazwaPl_1")));
    }

    @Test
    @DisplayName("httpGet(/projects?lang=EN)  -> returns 2 items list with english description)")
    void httpGet_langEN__returnsListOfEnglishProjects() throws Exception{

        //given
        repository.save(createProject("nazwaPl_1", "nameEn_1", Year.of(2000), Month.of(12), List.of(3,5))).getId();
        repository.save(createProject("nazwaPl_2", "nameEn_2", Year.of(2010), Month.of(5), List.of(1,2))).getId();

        //when + then
        mockMvc.perform(MockMvcRequestBuilders.get("/projects")
                .param("lang", "EN")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(content().string(containsString("nameEn_2")))
                .andExpect(content().string(containsString("countryEn")));
    }



    //--- POSTs
    @Test
    @DisplayName("httpPost(/projects)  -> returns returnsNoContent)")
    void httpPost__returnsNoContent() throws Exception{

        String jsonString = "{\n" +
                "    \"namePl\": \"namePl2\",\n" +
                "    \"nameEn\": \"nameEn\",\n" +
                "    \"year\": 2020,\n" +
                "    \"month\": 5,\n" +
                "    \"cityPl\": \"cityPl\",\n" +
                "    \"cityEn\": \"cityEn\",\n" +
                "    \"countryPl\": \"countryPl\",\n" +
                "    \"countryEn\": \"countryEn\",\n" +
                "    \"client\": \"client\",\n" +
                "    \"icoPath\": \"/images/icoPath.jpg\",\n" +
                "    \"types\": [2, 6],\n" +
                "    \"images\": [\n" +
                "    { \"path\": \"path1\", \"big\": true, \"order\": 2},\n" +
                "    { \"path\": \"path2\", \"big\": false, \"order\": 1}\n" +
                "  ]";

        // when + then
        mockMvc.perform(MockMvcRequestBuilders
                .post("/projects")
                .content(jsonString))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    }

    //TODO: POST parametrized test with all mistakes


    //---PUTs
    //TODO: put test

    //--- DELETEs
    //TODO: delete test



//    //---PUTs
//    @PutMapping("/projects/{id}")
//    ResponseEntity<?> updateProject(@PathVariable int id, @RequestBody @Valid ProjectWriteModel projectWriteModel) {
//        if (!repository.existsById(id)) {
//            return ResponseEntity.notFound().build();
//        }
//        repository.findById(id)
//                .ifPresent(project -> {
//                    project.updateFromAndWrite(projectWriteModel, projectService);
//                });
//        return ResponseEntity.noContent().build();
//    }
//
//
//    //TODO: doesn't persist in DB
//    //TODO: tests
//    //---DELETEs
//    @Transactional
//    @DeleteMapping("/projects/{id}")
//    ResponseEntity<Void> deleteProject(@PathVariable int id) {
//
//        if (repository.findById(id).isPresent()) {
//            repository.deleteById(id);
//            logger.info("Project with id: " + id + " has been deleted");
//            return ResponseEntity.noContent().build();
//        } else {
//            logger.info("Deletion aborted, no project with id: " + id);
//            return ResponseEntity.notFound().build();
//        }










    //----utils

    //creating Projects
    private void createExampleProjects() {
        Project mockproject1 = createProject("nazwaPl_1", "nameEn_1", Year.of(2000), Month.of(12), List.of(3,5));
        Project mockproject2 = createProject("nazwaPl_2", "nameEn_2", Year.of(2000), Month.of(12), List.of(1,3));
        repository.save(mockproject1);
        repository.save(mockproject2);
    }
    private Project createProject(String namePl, String nameEn, Year year, Month month, List<Integer> typesIds) {

        TypeRepository mockTypeRepository = mock(TypeRepository.class);
        when(mockTypeRepository.findById(anyInt())).thenReturn(Optional.of(new Type(1, "mocType")));

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
                .map(typeId -> mockTypeRepository.findById(typeId).get())
                .collect(Collectors.toList())
        );
        result.setImages(Set.of(
                new ProjectImage("path1", true, 2),
                new ProjectImage("path2", false, 1)
        ));

        return result;
    }

    //json
    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }



}




//        //given
//        ProjectWriteModel projectWriteModel = mock(ProjectWriteModel.class);
//        when(projectWriteModel.getNamePl()).thenReturn("namePl1");
//        when(projectWriteModel.getNameEn()).thenReturn("nameEn2");
//        when(projectWriteModel.getYear()).thenReturn(2000);
//        when(projectWriteModel.getMonth()).thenReturn(5);
//        when(projectWriteModel.getCityPl()).thenReturn("cityPl");
//        when(projectWriteModel.getCityEn()).thenReturn("cityEn");
//        when(projectWriteModel.getCountryPl()).thenReturn("countryPl");
//        when(projectWriteModel.getCountryEn()).thenReturn("countryEn");
//        when(projectWriteModel.getClient()).thenReturn("client");
//        when(projectWriteModel.getIcoPath()).thenReturn("/images/icoPath.jpg");
//        when(projectWriteModel.getTypes()).thenReturn(Set.of(1, 3));
//        when(projectWriteModel.getImages()).thenReturn(Set.of(
//                new ProjectImage("path1", true, 1),
//                new ProjectImage("path2", false, 2)
//        ));