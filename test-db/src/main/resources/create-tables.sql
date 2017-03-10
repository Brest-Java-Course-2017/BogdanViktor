DROP TABLE IF EXISTS director;
CREATE TABLE IF NOT EXISTS director (
  director_id INT NOT NULL AUTO_INCREMENT,
  first_name VARCHAR(30) NOT NULL,
  last_name VARCHAR(30) NOT NULL,
  PRIMARY KEY (director_id));

DROP TABLE IF EXISTS movie;
CREATE TABLE IF NOT EXISTS movie (
  movie_id INT NOT NULL AUTO_INCREMENT,
  movie_title VARCHAR(150) NOT NULL,
  release_date DATE NOT NULL,
  rating DOUBLE Not NULL,
  movie_director_id int,
  PRIMARY KEY (movie_id),
  FOREIGN KEY (movie_director_id) REFERENCES director (director_id)  );
