DROP TABLE IF EXISTS project_type;

CREATE TABLE project_type
(
    id_project int NOT NULL,
    id_type int NOT NULL,
    PRIMARY KEY (id_project, id_type),
    FOREIGN KEY (id_project) REFERENCES projects(id),
    FOREIGN KEY (id_type) REFERENCES types(id)
);
