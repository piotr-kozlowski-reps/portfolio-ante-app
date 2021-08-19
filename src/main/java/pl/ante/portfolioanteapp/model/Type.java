package pl.ante.portfolioanteapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "types")
public class Type {

    //---fields
    @Id
    private Integer id;
    private String type;

//    @JsonIgnore
//    @ManyToMany(
//            fetch = FetchType.EAGER,
//            cascade = {
//                CascadeType.MERGE,
//                CascadeType.DETACH,
//                CascadeType.PERSIST,
//                CascadeType.REFRESH},
//                targetEntity = Project.class)
//    @JoinTable(name = "project_type",
//            joinColumns = @JoinColumn(name = "id_type"),
//            inverseJoinColumns = @JoinColumn(name = "id_project"))
//    private List<Project> projects = new ArrayList<>();



    //---constr
    public Type() {
    }
    public Type(final Integer id, final String type) {
        this.id = id;
        this.type = type;
    }

    //---get/set
    public Integer getId() {
        return id;
    }
    void setId(final Integer id) {
        this.id = id;
    }
    public String getType() {
        return type;
    }
    void setType(final String type) {
        this.type = type;
    }
//    public List<Project> getProjects() {
//        return projects;
//    }
//    void setProjects(List<Project> projects) {
//        this.projects = projects;
//    }



    //---methods
//    public void addProject(Project project) {
//        projects.add(project);
//    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Type type1 = (Type) o;
        return id.equals(type1.id) && type.equals(type1.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type);
    }

    @Override
    public String toString() {
        return this.type;
    }
}
