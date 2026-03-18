---
title: Database DDL
description: "Database DDL"
order: 15
---

{% include ddc-abbreviations.md %}

## Page contents
{:.no_toc:}

- ToC
{:toc}

## Database DDL

```SQLite
PRAGMA foreign_keys = ON;

CREATE TABLE IF NOT EXISTS incubator
(
    incubator_id       INTEGER PRIMARY KEY AUTOINCREMENT,
    name               TEXT    NOT NULL,
    model              TEXT    NOT NULL,
    target_temperature REAL    NOT NULL,
    target_humidity    REAL    NOT NULL,
    notes              TEXT,
    active             INTEGER NOT NULL DEFAULT 1
);

CREATE INDEX IF NOT EXISTS index_incubator_name
    ON incubator (name);

CREATE TABLE IF NOT EXISTS batch
(
    batch_id            INTEGER PRIMARY KEY AUTOINCREMENT,
    incubator_id        INTEGER,
    date_set            INTEGER,
    lockdown_date       INTEGER,
    expected_hatch_date INTEGER,
    num_eggs_set        INTEGER NOT NULL,
    notes               TEXT,
    batch_status        TEXT,
    FOREIGN KEY (incubator_id) REFERENCES incubator (incubator_id)
        ON DELETE SET NULL
);

CREATE INDEX IF NOT EXISTS index_batch_incubator_id
    ON batch (incubator_id);

CREATE TABLE IF NOT EXISTS egg_group
(
    egg_group_id      INTEGER PRIMARY KEY AUTOINCREMENT,
    batch_id          INTEGER NOT NULL,
    breed             TEXT    NOT NULL,
    initial_egg_count INTEGER NOT NULL,
    notes             TEXT,
    FOREIGN KEY (batch_id) REFERENCES batch (batch_id)
        ON DELETE CASCADE
);

CREATE INDEX IF NOT EXISTS index_egg_group_batch_id
    ON egg_group (batch_id);

CREATE TABLE IF NOT EXISTS egg
(
    egg_id       INTEGER PRIMARY KEY AUTOINCREMENT,
    egg_group_id INTEGER NOT NULL,
    hatch_status TEXT,
    final_notes  TEXT,
    FOREIGN KEY (egg_group_id) REFERENCES egg_group (egg_group_id)
        ON DELETE CASCADE
);

CREATE INDEX IF NOT EXISTS index_egg_egg_group_id
    ON egg (egg_group_id);

CREATE TABLE IF NOT EXISTS candling_observation
(
    observation_id     INTEGER PRIMARY KEY AUTOINCREMENT,
    egg_id             INTEGER NOT NULL,
    day_number         INTEGER NOT NULL,
    development_status TEXT,
    notes              TEXT,
    timestamp          INTEGER,
    FOREIGN KEY (egg_id) REFERENCES egg (egg_id)
        ON DELETE CASCADE
);

CREATE INDEX IF NOT EXISTS index_candling_observation_egg_id
    ON candling_observation (egg_id);

CREATE VIEW IF NOT EXISTS batch_with_incubator AS
SELECT b.batch_id,
       b.incubator_id,
       b.date_set,
       b.expected_hatch_date,
       b.num_eggs_set,
       b.batch_status,
       i.name  AS incubator_name,
       i.model AS incubator_model
FROM batch AS b
         LEFT JOIN incubator AS i
                   ON b.incubator_id = i.incubator_id
ORDER BY b.date_set DESC;
```

[Download DDL](sql/ddl.sql)
