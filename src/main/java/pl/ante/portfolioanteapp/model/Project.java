package pl.ante.portfolioanteapp.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "projects")
//public class Project extends RepresentationModel {
public class Project{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "Nazwa projektu nie może być pusta")
    private String namePl;

    @NotBlank(message = "Project name cannot be empty")
    private String nameEn;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "type_id")
    private Set<Type> types;

//    @NotBlank(message = "Year field cannot be empty")
//    private Year year;
//
//    @NotBlank(message = "Month field cannot be empty")
//    private Month month;


    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;

//    //TODO: walidacja jak juz sie naucze jak dodawac grafike, musi byc dobra sciezka do pliku
//    private String pathImg;



    //---
    public Project() {
    }




    //---
    public int getId() {
        return id;
    }
    void setId(int id) {
        this.id = id;
    }
    public String getNamePl() {
        return namePl;
    }
    void setNamePl(final String name_pl) {
        this.namePl = name_pl;
    }
    public String getNameEn() {
        return nameEn;
    }
    void setNameEn(final String nameEn) {
        this.nameEn = nameEn;
    }
    public Set<Type> getTypes() {
        return types;
    }
    void setTypes(final Set<Type> types) {
        this.types = types;
    }

    @PrePersist
    void prePersist() {
        createdOn = LocalDateTime.now();
    }

    @PreUpdate
    void preUpdate() {
        updatedOn = LocalDateTime.now();
    }

    //TODO: always update this method when databases changes
    public void updateFrom(Project source) {
        namePl = source.getNamePl();
        nameEn = source.getNameEn();
    }

}
