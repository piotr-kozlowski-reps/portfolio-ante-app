package pl.ante.portfolioanteapp.model.adapter;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.ante.portfolioanteapp.model.ProjectImage;
import pl.ante.portfolioanteapp.model.ProjectImageRepository;

@Repository
public interface SqlProjectImageRepository extends ProjectImageRepository, JpaRepository<ProjectImage, Integer> {
}
