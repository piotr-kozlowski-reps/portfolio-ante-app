package pl.ante.portfolioanteapp.model;

import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.Month;
import java.time.Year;

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

    @NotBlank(message = "Opis projektu nie może być pusty")
    private String descriptionPl;

    @NotBlank(message = "Project description cannot be empty")
    private String descriptionEn;

//    @NotBlank(message = "Year field cannot be empty")
//    private Year year;
//
//    @NotBlank(message = "Month field cannot be empty")
//    private Month month;

    private boolean done;


    //TODO: walidacja jak juz sie naucze jak dodawac grafike, musi byc dobra sciezka do pliku
    private String pathImg;



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
    public String getDescriptionPl() {
        return descriptionPl;
    }
    void setDescriptionPl(final String description_pl) {
        this.descriptionPl = description_pl;
    }
    public String getDescriptionEn() {
        return descriptionEn;
    }
    void setDescriptionEn(final String description_en) {
        this.descriptionEn = description_en;
    }
    public String getPathImg() {
        return pathImg;
    }
    void setPathImg(final String path_img) {
        this.pathImg = path_img;
    }

    public boolean isDone() {
        return done;
    }
    void setDone(final boolean done) {
        this.done = done;
    }

    //    public Year getYear() {
//        return year;
//    }
//    void setYear(final Year year) {
//        this.year = year;
//    }
//    public Month getMonth() {
//        return month;
//    }
//    void setMonth(final Month month) {
//        this.month = month;
//    }
}
