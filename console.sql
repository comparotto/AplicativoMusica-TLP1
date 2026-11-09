-- Scripts SQL do Banco de Dados AplicativoMusica

CREATE TABLE IF NOT EXISTS pessoa (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    idade INT NOT NULL
);

CREATE TABLE IF NOT EXISTS usuario (
    id BIGINT PRIMARY KEY REFERENCES pessoa(id) ON DELETE CASCADE,
    email VARCHAR(100) NOT NULL UNIQUE,
    data_cadastro TIMESTAMP NOT NULL
);

CREATE TABLE IF NOT EXISTS artista (
    id BIGINT PRIMARY KEY REFERENCES pessoa(id) ON DELETE CASCADE,
    nome_artistico VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS banda (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    genero_musical VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS banda_artista (
    banda_id BIGINT REFERENCES banda(id) ON DELETE CASCADE,
    artista_id BIGINT REFERENCES artista(id) ON DELETE CASCADE,
    PRIMARY KEY (banda_id, artista_id)
);

CREATE TABLE IF NOT EXISTS musica (
    id BIGSERIAL PRIMARY KEY,
    nome_musica VARCHAR(100) NOT NULL,
    data_lancamento DATE NOT NULL,
    tempo_min FLOAT NOT NULL,
    genero_musical VARCHAR(50) NOT NULL,
    banda_id BIGINT REFERENCES banda(id) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS musica_artista (
    musica_id BIGINT REFERENCES musica(id) ON DELETE CASCADE,
    artista_id BIGINT REFERENCES artista(id) ON DELETE CASCADE,
    PRIMARY KEY (musica_id, artista_id)
);

CREATE TABLE IF NOT EXISTS playlist (
    id BIGSERIAL PRIMARY KEY,
    nome_playlist VARCHAR(100) NOT NULL,
    duracao FLOAT DEFAULT 0.0
);

CREATE TABLE IF NOT EXISTS playlist_usuario (
    playlist_id BIGINT REFERENCES playlist(id) ON DELETE CASCADE,
    usuario_id BIGINT REFERENCES usuario(id) ON DELETE CASCADE,
    PRIMARY KEY (playlist_id, usuario_id)
);

CREATE TABLE IF NOT EXISTS playlist_musica (
    playlist_id BIGINT REFERENCES playlist(id) ON DELETE CASCADE,
    musica_id BIGINT REFERENCES musica(id) ON DELETE CASCADE,
    posicao INT NOT NULL,
    PRIMARY KEY (playlist_id, musica_id)
);
