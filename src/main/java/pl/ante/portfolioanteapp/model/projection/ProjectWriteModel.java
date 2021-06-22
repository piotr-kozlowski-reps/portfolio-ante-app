package pl.ante.portfolioanteapp.model.projection;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.Set;

public class ProjectWriteModel {

    //---
    @NotBlank(message = "Nazwa projektu nie może być pusta")
    private String namePl;

    @NotBlank(message = "Project name cannot be empty")
    private String nameEn;

//    @Pattern(regexp = "^\\d{4}$", message = "Enter correct year, please")
    @Min(value = 1990, message = "Enter correct year (greater than 1990), please")
    @Max(value = 2050, message = "Enter correct year (lower than 2050), please")
    private Integer year;

//    @Pattern(regexp = "^(1|01|1|02|2|3|03|4|04|5|05|6|06|7|07|8|08|9|09|10|11|12)$", message = "Enter correct month number (from 1 to 12)")
    @Min(value = 1, message = "Enter correct month number (1-12), please")
    @Max(value = 12, message = "Enter correct month number (1-12), please")
    private Integer month;

    @NotBlank(message = "Nazwa miasta nie może być pusta")
    private String cityPl;

    @NotBlank(message = "City name cannot be empty")
    private String cityEn;

    @NotBlank(message = "Nazwa panstwa nie może być pusta") //TODO: polskie znaki
    private String countryPl;

    @NotBlank(message = "Country name cannot be empty")
    private String countryEn;

    @NotBlank(message = "Client field cannot be empty")
    private String client;

    private String icoPath;

    private Set<Integer> types;




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
    public Integer getYear() {
        return year;
    }
    public void setYear(final Integer year) {
        this.year = year;
    }
    public Integer getMonth() {
        return month;
    }
    public void setMonth(final Integer month) {
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
    public Set<Integer> getTypes() {
        return types;
    }
    public void setTypes(final Set<Integer> types) {
        this.types = types;
    }


}
