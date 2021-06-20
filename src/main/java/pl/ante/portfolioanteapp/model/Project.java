package pl.ante.portfolioanteapp.model;

import org.hibernate.annotations.TypeDef;
import pl.ante.portfolioanteapp.model.projection.utils.YearType;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.Year;
import java.util.Set;

@Entity
@Table(name = "projects")
@TypeDef(
        typeClass = YearType.class,
        defaultForType = Year.class
)
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "Nazwa projektu nie może być pusta")
    private String namePl;

    @NotBlank(message = "Project name cannot be empty")
    private String nameEn;

    //    @NotBlank(message = "Year field cannot be empty")
//    @Pattern(regexp = "^/d{4}$")
    private Year year;

    @NotBlank(message = "Month field cannot be empty")
    private Month month;

    @NotBlank(message = "Pole miejscowosci nie może być puste")  //TODO: polskie znaki
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


    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "type_id")
    private Set<Type> types;


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

    public void setNamePl(final String name_pl) {
        this.namePl = name_pl;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(final String nameEn) {
        this.nameEn = nameEn;
    }

    public Set<Type> getTypes() {
        return types;
    }

    void setTypes(final Set<Type> types) {
        this.types = types;
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
