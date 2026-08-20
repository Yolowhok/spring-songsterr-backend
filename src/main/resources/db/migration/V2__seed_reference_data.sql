INSERT INTO duration (id, name, value) VALUES
    (1, 'WHOLE', 1),
    (3, 'HALF', 0.5),
    (4, 'QUARTER', 0.25),
    (5, 'EIGHTH', 0.125),
    (6, 'SIXTEENTH', 0.0625),
    (7, 'THIRTY_SECOND', 0.03125),
    (8, 'SIXTY_FOUR', 0.015625);

INSERT INTO instrument (id, name) VALUES
    (1, 'GUITAR'),
    (2, 'PIANO');

INSERT INTO note (id, name) VALUES
    (1, 'C'),
    (2, 'C_SHARP'),
    (3, 'D'),
    (4, 'D_SHARP'),
    (5, 'E'),
    (6, 'F'),
    (7, 'F_SHARP'),
    (8, 'G'),
    (9, 'G_SHARP'),
    (10, 'A'),
    (11, 'A_SHARP'),
    (12, 'B');

INSERT INTO octave (id, name, value) VALUES
    (1, 'SUB_CONTRA', 0),
    (2, 'CONTRA', 1),
    (3, 'GREAT', 2),
    (4, 'SMALL', 3),
    (5, 'ONE_LINE', 4),
    (6, 'TWO_LINE', 5),
    (7, 'THREE_LINE', 6),
    (9, 'FOUR_LINE', 7),
    (10, 'FIVE_LINE', 8);

INSERT INTO note_octave (id, note_id, octave_id)
SELECT
    ROW_NUMBER() OVER (ORDER BY n.id, o.id) AS id,
    n.id,
    o.id
FROM note n
CROSS JOIN octave o;

INSERT INTO "position" (id, string, fret)
SELECT
    ROW_NUMBER() OVER (ORDER BY s.string_number, f.fret) AS id,
    s.string_number,
    f.fret
FROM generate_series(1, 6) AS s(string_number)
CROSS JOIN generate_series(0, 24) AS f(fret);

INSERT INTO time_signature (id, upper, lower) VALUES
    (3, 4, 4),
    (6, 3, 4),
    (7, 2, 4),
    (8, 1, 4),
    (9, 1, 1),
    (10, 2, 2),
    (11, 1, 2);

INSERT INTO tuning (id, name) VALUES
    (1, 'Standart E');

INSERT INTO tuning_string (id, string_number, tuning_id, note_octave_id) VALUES
    (1, 1, 1, 41),
    (2, 2, 1, 103),
    (3, 3, 1, 67),
    (4, 4, 1, 22),
    (5, 5, 1, 84),
    (6, 6, 1, 39);
