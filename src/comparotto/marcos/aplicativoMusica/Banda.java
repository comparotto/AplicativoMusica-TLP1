package comparotto.marcos.aplicativoMusica;

import java.util.ArrayList;
import java.util.List;

public class Banda {
    private long id;
    private String nome;
    private String generoMusical;
    private List<Artista> integrantes;
    private List<Musica> musicas;

    public Banda(long id, String nome, String generoMusical, List<Artista> integrantes, List<Musica> musicas) {
        this.id = id;
        this.nome = nome;
        this.generoMusical = generoMusical;
        this.integrantes = integrantes != null ? integrantes : new ArrayList<>();
        this.musicas = musicas != null ? musicas : new ArrayList<>();
    }

    public Banda(String nome, String generoMusical) {
        this.nome = nome;
        this.generoMusical = generoMusical;
        this.integrantes = new ArrayList<>();
        this.musicas = new ArrayList<>();
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getGeneroMusical() { return generoMusical; }
    public void setGeneroMusical(String generoMusical) { this.generoMusical = generoMusical; }

    public List<Artista> getIntegrantes() { return integrantes; }
    public void setIntegrantes(List<Artista> integrantes) { this.integrantes = integrantes; }

    public List<Musica> getMusicas() { return musicas; }
    public void setMusicas(List<Musica> musicas) { this.musicas = musicas; }
}
