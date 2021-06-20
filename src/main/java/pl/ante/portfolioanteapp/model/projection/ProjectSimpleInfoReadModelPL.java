package pl.ante.portfolioanteapp.model.projection;

import pl.ante.portfolioanteapp.model.Project;

public class ProjectSimpleInfoReadModelPL extends ProjectSimpleInfoReadModel{

    ProjectSimpleInfoReadModelPL(Project project) {
        this.setId(project.getId());
        this.setName(project.getNamePl());
        this.setYear(project.getYear().toString());


    }

}
