package pl.ante.portfolioanteapp.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pl.ante.portfolioanteapp.logic.ProjectService;
import pl.ante.portfolioanteapp.model.*;

import java.time.Month;
import java.time.Year;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest(ProjectController.class)
class ProjectControllerLightIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProjectRepository repository;

    @Autowired
    private ProjectService projectService;

    @Test
    void httpGet_returnsGivenTask() throws Exception{

        //given
        when(repository.findById(anyInt())).thenReturn(Optional.of(createProject("nazwaPl_1", "nameEn_1", Year.of(2000), Month.of(12), List.of(3,5))));

        //when + then
        mockMvc.perform(MockMvcRequestBuilders.get("/projects/" + 123))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    }













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
                new ProjectImage("path1", true, 2, "altPL", "altEN"),
                new ProjectImage("path2", false, 1, "altPL", "altEN")
        ));

        return result;
    }




}
