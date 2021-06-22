DROP TABLE IF EXISTS images;

CREATE TABLE images
(
    id         int PRIMARY KEY AUTO_INCREMENT,
    path       VARCHAR(250) NOT NULL,
    big        bit,
    project_id INT NULL,
    FOREIGN KEY (project_id) REFERENCES projects(id)
) ;
