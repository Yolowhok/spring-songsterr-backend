CREATE TABLE composition (
    id BIGSERIAL PRIMARY KEY,
    band VARCHAR(30),
    title VARCHAR(30),
    created_at DATE DEFAULT CURRENT_DATE,
    updated_at DATE
);

CREATE TABLE instrument (
    id INTEGER PRIMARY KEY,
    name VARCHAR(30),
    CONSTRAINT unique_instrument UNIQUE (name)
);

CREATE TABLE tuning (
    id BIGINT PRIMARY KEY,
    name VARCHAR(255)
);

CREATE TABLE note (
    id BIGINT PRIMARY KEY,
    name VARCHAR(10),
    CONSTRAINT unique_note UNIQUE (name)
);

CREATE TABLE octave (
    id BIGINT PRIMARY KEY,
    name VARCHAR(30),
    value INTEGER,
    CONSTRAINT unique_octave UNIQUE (name, value)
);

CREATE TABLE note_octave (
    id BIGINT PRIMARY KEY,
    note_id BIGINT NOT NULL REFERENCES note(id),
    octave_id BIGINT NOT NULL REFERENCES octave(id),
    CONSTRAINT unique_note_octave UNIQUE (note_id, octave_id)
);

CREATE TABLE time_signature (
    id BIGINT PRIMARY KEY,
    upper INTEGER,
    lower INTEGER,
    CONSTRAINT unique_time_signature UNIQUE (upper, lower)
);

CREATE TABLE duration (
    id BIGINT PRIMARY KEY,
    name VARCHAR(30),
    value DOUBLE PRECISION,
    CONSTRAINT unique_duration UNIQUE (name, value)
);

CREATE TABLE "position" (
    id BIGINT PRIMARY KEY,
    string INTEGER,
    fret INTEGER
);

CREATE TABLE tuning_string (
    id INTEGER PRIMARY KEY,
    string_number INTEGER,
    tuning_id BIGINT NOT NULL REFERENCES tuning(id),
    note_octave_id BIGINT NOT NULL REFERENCES note_octave(id)
);

CREATE TABLE notesheet (
    id BIGSERIAL PRIMARY KEY,
    instrument_id INTEGER NOT NULL REFERENCES instrument(id) ON DELETE CASCADE,
    composition_id BIGINT NOT NULL REFERENCES composition(id) ON DELETE CASCADE,
    tuning_id BIGINT REFERENCES tuning(id)
);

CREATE TABLE bar (
    id BIGSERIAL PRIMARY KEY,
    temp_in_bpm INTEGER,
    time_signature_id BIGINT REFERENCES time_signature(id) ON DELETE CASCADE,
    notesheet_id BIGINT REFERENCES notesheet(id) ON DELETE CASCADE,
    order_index INTEGER
);

CREATE TABLE beat (
    id BIGSERIAL PRIMARY KEY,
    duration_id BIGINT REFERENCES duration(id) ON DELETE CASCADE,
    bar_id BIGINT REFERENCES bar(id) ON DELETE CASCADE,
    order_index INTEGER
);

CREATE TABLE beat_note (
    id BIGSERIAL PRIMARY KEY,
    beat_id BIGINT REFERENCES beat(id) ON DELETE CASCADE,
    note_octave_id BIGINT REFERENCES note_octave(id),
    position_id BIGINT REFERENCES "position"(id)
);
