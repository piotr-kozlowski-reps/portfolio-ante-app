package pl.ante.portfolioanteapp.model.projection;

import pl.ante.portfolioanteapp.model.Project;

public class ProjectSimpleInfoFactory {

    private ProjectSimpleInfoFactory(){}

    private static class ProjectSimpleInfoFactoryHolder{
        private static final ProjectSimpleInfoFactory INSTANCE_HOLDER = new ProjectSimpleInfoFactory();
    }

    public static ProjectSimpleInfoFactory getInstance() {
        return ProjectSimpleInfoFactoryHolder.INSTANCE_HOLDER;
    }

    public ProjectSimpleInfoReadModel getProjectSimpleInfoReadModel(String lang, Project project) {

        switch (lang.toUpperCase()){

            case "PL":
                return new ProjectSimpleInfoReadModelPL(project);

            case "EN":
                return new ProjectSimpleInfoReadModelEN(project);

            default:
                return null;

        }
    }


}

