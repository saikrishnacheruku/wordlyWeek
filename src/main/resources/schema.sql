CREATE TABLE writer (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    bio varchar(255)
);

CREATE TABLE magazine (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255),
    publicationdate varchar(255)
);

CREATE TABLE writer_magazine (
    writerid INT,
    magazineid INT,
    PRIMARY KEY (writerid, magazineid),
    FOREIGN KEY (writerid) REFERENCES writer(id),
    FOREIGN KEY (magazineid) REFERENCES magazine(id)
);
