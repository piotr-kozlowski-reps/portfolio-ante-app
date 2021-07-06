package pl.ante.portfolioanteapp.model.projection;

import pl.ante.portfolioanteapp.model.Project;

public class ProjectSimpleInfoReadModelPL extends ProjectSimpleInfoReadModel{

    public ProjectSimpleInfoReadModelPL(Project source) {
        this.setId(source.getId());
        this.setName(source.getNamePl());
        this.setYear(source.getYear().toString());
        this.setMonth(source.getMonth().toString());
        this.setCity(source.getCityPl());
        this.setCountry(source.getCountryPl());
        this.setIcoPath(source.getIcoPath());
        this.setIcoAlt(source.getIcoAltPl());
    }

}
