package pl.ante.portfolioanteapp.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import pl.ante.portfolioanteapp.model.*;
import pl.ante.portfolioanteapp.model.projection.ProjectSimpleInfoReadModel;

import java.time.Month;
import java.time.Year;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProjectControllerE2ETest {

    //---fields
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ProjectRepository repository;

    @Autowired
    private TypeRepository typeRepository;




    //---tests
    @Test
    void HTTPGet__returnsProjects() {

        //given
        fillInTypeRepository();
        createExampleProjects();

        //when
        ProjectSimpleInfoReadModel[] result = restTemplate.getForObject("http://localhost:" + port + "/projects", ProjectSimpleInfoReadModel[].class);

        //then
        assertThat(result)
                .isNotEmpty()
                .hasSize(2);
    }




    //---Utils


    //Type repository
    private void fillInTypeRepository() {
        typeRepository.save(new Type(1, "COMPETITION"));
        typeRepository.save(new Type(2, "INTERIOR"));
        typeRepository.save(new Type(3, "EXTERIOR"));
        typeRepository.save(new Type(4, "ANIMATION"));
        typeRepository.save(new Type(5, "PRODUCT_MODEL"));
        typeRepository.save(new Type(6, "PANORAMA"));
        typeRepository.save(new Type(7, "AR_APP"));
    }

    //creating Projects
    private void createExampleProjects() {
        Project mockproject1 = createProject("nazwaPl_1", "nameEn_1", Year.of(2000), Month.of(12), List.of(3,5));
        Project mockproject2 = createProject("nazwaPl_2", "nameEn_2", Year.of(2000), Month.of(12), List.of(1,3));
        repository.save(mockproject1);
        repository.save(mockproject2);
    }
    private Project createProject(String namePl, String nameEn, Year year, Month month, List<Integer> typesIds) {

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
                .map(typeId -> typeRepository.findById(typeId).get())
                .collect(Collectors.toList())
        );
        result.setImages(Set.of(
                new ProjectImage("path1", true, 2, "altPL", "altEN"),
                new ProjectImage("path2", false, 1, "altPL", "altEN")
        ));

        return result;
    }

}