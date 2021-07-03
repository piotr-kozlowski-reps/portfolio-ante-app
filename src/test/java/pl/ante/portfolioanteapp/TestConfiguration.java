package pl.ante.portfolioanteapp;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import pl.ante.portfolioanteapp.model.Project;
import pl.ante.portfolioanteapp.model.ProjectRepository;

import javax.sql.DataSource;
import java.util.*;

@Configuration
class TestConfiguration {

    @Bean
    @Primary
    @Profile("!integration")
    DataSource e2eTestDataSource() {
        var result = new DriverManagerDataSource("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1", "", "");
        result.setDriverClassName("org.h2.Driver");
        return result;
    }

    @Bean
    @Primary
    @Profile("integration")
    ProjectRepository testRepo() {
        return new ProjectRepository() {

            private Map<Integer, Project> projects = new HashMap<>();

            @Override
            public List<Project> findAll() {
                return new ArrayList<>(projects.values());
            }

            @Override
            public Optional<Project> findById(final Integer id) {
                return Optional.ofNullable(projects.get(id));
            }

            @Override
            public Project save(final Project entity) {
                int key = projects.size() + 1;

                try {
                    var field = Project.class.getDeclaredField("id");
                    field.setAccessible(true);
                    field.set(entity, key);
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    throw new RuntimeException(e);
                }


                projects.put(key, entity);
                return projects.get(key);
            }

            @Override
            public void deleteById(final Integer id) {
                if (projects.containsKey(id)) {
                    projects.remove(id);
                }
            }

            @Override
            public Page<Project> findAll(final Pageable page) {
                return null;
            }

            @Override
            public boolean existsById(final Integer id) {
                return projects.containsKey(id);
            }

        };
    }
}
