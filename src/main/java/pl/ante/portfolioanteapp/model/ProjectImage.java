package pl.ante.portfolioanteapp.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "images")
public class ProjectImage {

    //---fields
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String path;
    private Boolean big;
    private Integer orderNumber;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;



    //---constr
    public ProjectImage() {
    }
    public ProjectImage(final String path, final Boolean big, final Integer order) {
        this.path = path;
        this.big = big;
        this.orderNumber = order;
    }


    //---get-set
    public Integer getId() {
        return id;
    }
    void setId(final Integer id) {
        this.id = id;
    }
    public String getPath() {
        return path;
    }
    void setPath(final String path) {
        this.path = path;
    }
    public Boolean getBig() {
        return big;
    }
    void setBig(final Boolean big) {
        this.big = big;
    }
    public Integer getOrder() {
        return orderNumber;
    }
    void setOrder(final Integer order) {
        this.orderNumber = order;
    }


    @PrePersist
    void prePersist() {
        createdOn = LocalDateTime.now();
    }

    @PreUpdate
    void preUpdate() {updatedOn = LocalDateTime.now();}




    //---methods


}
