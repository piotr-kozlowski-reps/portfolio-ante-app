DROP TABLE IF EXISTS images;

CREATE TABLE images
(
    id         int PRIMARY KEY AUTO_INCREMENT,
    path       VARCHAR(250) NOT NULL,
    big        bit,
    image_alt_pl VARCHAR(255),
    image_alt_en VARCHAR(255)
);
