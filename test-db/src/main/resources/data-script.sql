INSERT INTO director (first_name, last_name)
VALUES
  ('Steven', 'Spielberg'),
  ('Quentin', 'Tarantino'),
  ('Ridley', 'Scott'),
  ('Peter', 'Jackson');


--    movieTitle VARCHAR(150) NOT NULL,
--   releaseDate DATE NOT NULL,
--   rating INT NULL DEFAULT 0,
--   movieDirectorID int,
INSERT INTO movie (movie_title, release_date, rating, movie_director_id)
VALUES
  ('Saving Private Ryan', '1998-07-24', 8.6, 1),
  ('Catch Me If You Can', '2002-12-25', 8.0, 1),
  ('Schindler''s List', '1993-02-04', 8.9, 1),
  ('Alien', '1979-06-22', 8.5, 3),
  ('The Martian', '2015-10-02', 8.0, 3),
  ('Prometheus', '2012-06-08', 7.0, 3),
  ('Blade Runner', '1982-06-25', 8.2, 3),
  ('The Lord of the Rings: The Fellowship of the Ring', '2001-12-19', 8.8, 4),
  ('The Lord of the Rings: The Two Towers', '2002-12-18', 8.7, 4),
  ('The Hobbit: An Unexpected Journey', '2012-12-14', 7.9, 4),
  ('Kill Bill: Vol. 1', '2003-10-10', 8.1, 2),
  ('Pulp Fiction', '1994-10-14', 8.9, 2),
  ('Reservoir Dogs', '1992-09-02', 8.4, 2);
