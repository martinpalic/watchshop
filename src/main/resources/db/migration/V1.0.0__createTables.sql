CREATE TABLE IF NOT EXISTS watch (
    id UUID DEFAULT RANDOM_UUID() NOT NULL PRIMARY KEY,
    title VARCHAR (250),
    price INT,
    description TEXT,
    fountain BLOB
);