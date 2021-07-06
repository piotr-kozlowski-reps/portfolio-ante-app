package pl.ante.portfolioanteapp.model.adapter;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.ante.portfolioanteapp.model.Project;
import pl.ante.portfolioanteapp.model.ProjectRepository;

import java.util.Optional;

@Repository
interface SqlProjectRepository extends ProjectRepository, JpaRepository<Project, Integer> {

//    @Override
//    @Query(nativeQuery = true, value = "SELECT COUNT(*) > 0 FROM projects WHERE id=:id")
//    boolean existsById(@Param("id") Integer id);

    @Override
//    @Query("SELECT")
    Optional<Project> findById(Integer id);
}
