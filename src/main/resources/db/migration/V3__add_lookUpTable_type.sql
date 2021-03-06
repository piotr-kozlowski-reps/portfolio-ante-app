DROP TABLE IF EXISTS types;

CREATE TABLE types(
    id TINYINT UNIQUE,
    type VARCHAR(50)
);

INSERT INTO types(id, type)
VALUES
(1, 'COMPETITION'),
(2, 'INTERIOR'),
(3, 'EXTERIOR'),
(4, 'ANIMATION'),
(5, 'PRODUCT_MODEL'),
(6, 'PANORAMA'),
(7, 'AR_APP');
