package pl.ante.portfolioanteapp.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
interface SqlProjectRepository extends ProjectRepository, JpaRepository<Project, Integer> {

//    @Override
//    @Query(nativeQuery = true, value = "SELECT COUNT(*) > 0 FROM projects WHERE id=:id")
//    boolean existsById(@Param("id") Integer id);
}
