package pl.ante.portfolioanteapp.model.projection;

import pl.ante.portfolioanteapp.model.Project;

import javax.validation.constraints.Pattern;
import java.time.Month;
import java.time.Year;
import java.util.Set;

public class ProjectWriteModel {

    //---
    private String namePl;
    private String nameEn;

    @Pattern(regexp = "^\\d{4}$")
    private String year;

    private String month;
    private String cityPl;
    private String cityEn;
    private String countryPl;
    private String countryEn;
    private String client;

    private String icoPath;

    private Set<String> types;




    //---
    public String getNamePl() {
        return namePl;
    }
    public void setNamePl(final String namePl) {
        this.namePl = namePl;
    }
    public String getNameEn() {
        return nameEn;
    }
    public void setNameEn(final String nameEn) {
        this.nameEn = nameEn;
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
    public Set<String> getTypes() {
        return types;
    }
    public void setTypes(final Set<String> types) {
        this.types = types;
    }


    //---
    public Project toProject() {

        var result = new Project();
        result.setNamePl(this.getNamePl());
        result.setNameEn(this.getNameEn());
        result.setYear(Year.of(Integer.parseInt(this.getYear())));
        result.setMonth(Month.of(Integer.parseInt(this.getMonth())));
        result.setCityPl(this.getCityPl());
        result.setCityEn(this.getCityEn());
        result.setCountryPl(this.getCountryPl());
        result.setCountryEn(this.getCountryEn());
        result.setClient(this.getClient());
        result.setIcoPath(this.getIcoPath());

        return result;

    }

}
