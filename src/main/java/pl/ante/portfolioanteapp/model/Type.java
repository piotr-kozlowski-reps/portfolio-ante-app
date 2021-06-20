package pl.ante.portfolioanteapp.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "types")
class Type {

    @Id
    private short id;
    private String type;



    //---
    public Type() {
    }



    //---
    public short getId() {
        return id;
    }
    void setId(final short id) {
        this.id = id;
    }
    public String getType() {
        return type;
    }
    void setType(final String type) {
        this.type = type;
    }
}
