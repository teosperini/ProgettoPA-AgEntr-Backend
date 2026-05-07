CREATE TABLE IF NOT EXISTS users (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    codiceFiscale TEXT NOT NULL UNIQUE,
    password TEXT NOT NULL,
    nome TEXT,
    cognome TEXT,
    ruolo TEXT,
    anniEsperienza INT
);