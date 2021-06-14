package pl.ante.portfolioanteapp.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository {


    //GET
    List<Project> findAll();
    Optional<Project> findById(Integer id);
    Page<Project> findAll(Pageable page);


    //POST,PUT
    Project save(Project entity);


    //utils
    boolean existsById(Integer id);




//    /**
//     * final link to access: http://localhost:8080/projects/search/done?state=false
//     * */
//    List<Project> findByDone(@Param("state") boolean done);


}
