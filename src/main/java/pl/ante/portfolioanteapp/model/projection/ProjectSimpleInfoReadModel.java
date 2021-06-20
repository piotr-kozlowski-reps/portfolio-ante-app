package pl.ante.portfolioanteapp.model.projection;

public abstract class ProjectSimpleInfoReadModel {

    private int id;
    private String name;
    private String year;
    private String city;
    private String country;



    public int getId() {
        return id;
    }
    public void setId(final int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(final String name) {
        this.name = name;
    }
    public String getYear() {
        return year;
    }
    public void setYear(final String year) {
        this.year = year;
    }
    public String getCity() {
        return city;
    }
    public void setCity(final String city) {
        this.city = city;
    }
    public String getCountry() {
        return country;
    }
    public void setCountry(final String country) {
        this.country = country;
    }
}
