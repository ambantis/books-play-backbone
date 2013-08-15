-- Sqlite version of schema for the library database

DROP TABLE IF EXISTS book_tags;
DROP TABLE IF EXISTS tags;
DROP TABLE IF EXISTS books;

CREATE TABLE books(
  id INT PRIMARY KEY,
  title TEXT NOT NULL,
  author TEXT NOT NULL,
  released TEXT NOT NULL,
  image TEXT
);

CREATE TABLE tags(
  id INT PRIMARY KEY,
  name TEXT NOT NULL
);

CREATE TABLE book_tags(
  book_id INT,
  tag_id INT,
  CONSTRAINT pk_a PRIMARY KEY (book_id, tag_id),
  CONSTRAINT fk_a FOREIGN KEY (book_id) REFERENCES books(id),
  CONSTRAINT fk_b FOREIGN KEY (tag_id) REFERENCES tags(id)
);

INSERT INTO books VALUES (1, 'JavaScript: The Good Parts', 'Douglas Crockford',
'2008-05-08', 'java_script_the_good_parts.png');
INSERT INTO books VALUES (2, 'The Little Book on CoffeeScript', 'Alex MacCaw',
'2012-01-31', 'the_little_book_on_coffeescript.png');
INSERT INTO books VALUES (3, 'Scala for the Impatient', 'Cay S. Horstmann',
'2012-03-16', 'scala_for_the_impatient.png');
INSERT INTO books VALUES (4, 'Guns, Germs, and Steel', 'Jared Diamond',
'1999-04-01', 'guns_germs_and_steel.png');
INSERT INTO books VALUES (5, 'Eloquent_JavaScript', 'Marijn Haverbeke',
'2011-02-03', 'eloquent_javascript.png');

INSERT INTO tags VALUES (1, 'JavaScript');
INSERT INTO tags VALUES (2, 'CoffeeScript');
INSERT INTO tags VALUES (3, 'Scala');
INSERT INTO tags VALUES (4, 'Teddy Bears');
INSERT INTO tags VALUES (5, 'Hundred Acre Wood');
INSERT INTO tags VALUES (6, 'Fiction');
INSERT INTO tags VALUES (7, 'Programming');

INSERT INTO book_tags VALUES (1, 1);
INSERT INTO book_tags VALUES (1, 7);
INSERT INTO book_tags VALUES (2, 2);
INSERT INTO book_tags VALUES (2, 7);
INSERT INTO book_tags VALUES (3, 3);
INSERT INTO book_tags VALUES (4, 4);
INSERT INTO book_tags VALUES (4, 5);
INSERT INTO book_tags VALUES (4, 6);
INSERT INTO book_tags VALUES (5, 1);
INSERT INTO book_tags VALUES (5, 7);


