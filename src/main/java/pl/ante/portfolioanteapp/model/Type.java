package pl.ante.portfolioanteapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "types")
public class Type {

    //---fields
    @Id
    private Integer id;
    private String type;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH}, mappedBy = "types")
    private List<Project> projects = new ArrayList<>();




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
    public List<Project> getProjects() {
        return projects;
    }
    void setProjects(List<Project> projects) {
        this.projects = projects;
    }



    //---methods
    public void addProject(Project project) {
        projects.add(project);
    }


}
