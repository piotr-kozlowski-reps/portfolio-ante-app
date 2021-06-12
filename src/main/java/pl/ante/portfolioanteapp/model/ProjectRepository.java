package pl.ante.portfolioanteapp.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

@RepositoryRestResource
public interface ProjectRepository extends JpaRepository<Project, Integer> {

    @Override
    @RestResource(exported = false)
    void deleteById(Integer integer);

    @Override
    @RestResource(exported = false)
    void delete(Project project);

    /**
     * final link to access: http://localhost:8080/projects/search/done?state=false
     * */
    @RestResource(path = "done", rel = "done")
    List<Project> findByDone(@Param("state") boolean done);
}
