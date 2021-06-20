package pl.ante.portfolioanteapp.model.projection;

import pl.ante.portfolioanteapp.model.Project;

public class ProjectSimpleInfoReadModelEN extends ProjectSimpleInfoReadModel{

    public ProjectSimpleInfoReadModelEN(Project project) {
        this.setId(project.getId());
        this.setName(project.getNameEn());
        this.setYear(project.getYear().toString());
    }

}
