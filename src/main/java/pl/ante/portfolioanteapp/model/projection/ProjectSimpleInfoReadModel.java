package pl.ante.portfolioanteapp.model.projection;

public abstract class ProjectSimpleInfoReadModel {

    private int id;
    private String name;
    private String year;
    private String month;
    private String city;
    private String country;
    private String icoPath;
    private String icoAlt;


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
    public String getMonth() {
        return month;
    }
    public void setMonth(final String month) {
        this.month = month;
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
    public String getIcoPath() {
        return icoPath;
    }
    public void setIcoPath(final String icoPath) {
        this.icoPath = icoPath;
    }
    public String getIcoAlt() {
        return icoAlt;
    }
    public void setIcoAlt(final String icoAlt) {
        this.icoAlt = icoAlt;
    }
}
