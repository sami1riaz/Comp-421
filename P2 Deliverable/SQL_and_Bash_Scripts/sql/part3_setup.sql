
-- Due to key constraints, we need to create walls and creators before we can create userts

INSERT INTO wall(description) VALUES ('John');
INSERT INTO wall(description) VALUES ('spidey');
INSERT INTO wall(description) VALUES ('Potus');
INSERT INTO wall(description) VALUES ('OB');
INSERT INTO wall(description) VALUES ('lio');

INSERT INTO creator VALUES ('John', 1);
INSERT INTO creator VALUES ('spidey', 2);
INSERT INTO creator VALUES ('Potus', 3);
INSERT INTO creator VALUES ('OB', 4);
INSERT INTO creator VALUES ('lio', 5);
