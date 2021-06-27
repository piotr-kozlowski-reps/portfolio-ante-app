package pl.ante.portfolioanteapp.model;

import org.hibernate.annotations.TypeDef;
import pl.ante.portfolioanteapp.logic.ProjectService;
import pl.ante.portfolioanteapp.model.projection.ProjectWriteModel;
import pl.ante.portfolioanteapp.model.projection.utils.YearType;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.Year;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "projects")
@TypeDef(
        typeClass = YearType.class,
        defaultForType = Year.class
)
public class Project {

    //---fields
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String namePl;
    private String nameEn;
    private Year year;
    private Month month;

    @NotBlank(message = "Pole miejscowości nie może być puste")
    private String cityPl;

    @NotBlank(message = "Month field cannot be empty")
    private String cityEn;

    @NotBlank(message = "Pole kraju nie może być puste")
    private String countryPl;

    @NotBlank(message = "Country field cannot be empty")
    private String countryEn;

    @NotBlank(message = "Client field cannot be empty")
    private String client;

    private String icoPath;

    @ManyToMany(
            fetch = FetchType.EAGER,
            cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
            name = "project_type",
            joinColumns = @JoinColumn(name = "id_project"),
            inverseJoinColumns = @JoinColumn(name = "id_type"))
    private List<Type> types = new ArrayList<>();

//    @OneToMany(
//            cascade = CascadeType.ALL,
//            mappedBy = "project",
//            orphanRemoval = true)
//    private Set<ProjectImage> images = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "project_id")
    private Set<ProjectImage> images = new HashSet<>();




    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;



    //---constr
    public Project() {
    }


    //---get/set
    public int getId() {
        return id;
    }
    void setId(int id) {
        this.id = id;
    }
    public String getNamePl() {
        return namePl;
    }
    public void setNamePl(final String name_pl) {
        this.namePl = name_pl;
    }
    public String getNameEn() {
        return nameEn;
    }
    public void setNameEn(final String nameEn) {
        this.nameEn = nameEn;
    }
    public Year getYear() {
        return year;
    }
    public void setYear(final Year year) {
        this.year = year;
    }
    public Month getMonth() {
        return month;
    }
    public void setMonth(final Month month) {
        this.month = month;
    }
    public String getCityPl() {
        return cityPl;
    }
    public void setCityPl(final String cityPl) {
        this.cityPl = cityPl;
    }
    public String getCityEn() {
        return cityEn;
    }
    public void setCityEn(final String cityEn) {
        this.cityEn = cityEn;
    }
    public String getCountryPl() {
        return countryPl;
    }
    public void setCountryPl(final String countryPl) {
        this.countryPl = countryPl;
    }
    public String getCountryEn() {
        return countryEn;
    }
    public void setCountryEn(final String countryEn) {
        this.countryEn = countryEn;
    }
    public String getClient() {
        return client;
    }
    public void setClient(final String client) {
        this.client = client;
    }
    public String getIcoPath() {
        return icoPath;
    }
    public void setIcoPath(final String icoPath) {
        this.icoPath = icoPath;
    }
    public List<Type> getTypes() {
        return types;
    }
    public void setTypes(List<Type> types) {
        this.types = types;
    }
    public Set<ProjectImage> getImages() {
        return images;
    }
    public void setImages(final Set<ProjectImage> images) {
        this.images = images;
    }

    @PrePersist
    void prePersist() {
        createdOn = LocalDateTime.now();
    }

    @PreUpdate
    void preUpdate() {
        updatedOn = LocalDateTime.now();
    }




    //---methods
    //TODO: always update this method when databases changes
    public void updateFromAndWrite(ProjectWriteModel source, final ProjectService projectService) {
        projectService.updateProjectFromWriteModel(this, source);
    }

}
