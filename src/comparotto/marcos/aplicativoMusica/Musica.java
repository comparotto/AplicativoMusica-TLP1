package comparotto.marcos.aplicativoMusica;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Musica {
    private long id;
    private String nomeMusica;
    private Date dataLancamento;
    private float tempoMin;
    private String generoMusical;
    private List<Artista> artistas;

    public Musica(long id, String nomeMusica, Date dataLancamento, float tempoMin, String generoMusical, List<Artista> artistas) {
        this.id = id;
        this.nomeMusica = nomeMusica;
        this.dataLancamento = dataLancamento;
        this.tempoMin = tempoMin;
        this.generoMusical = generoMusical;
        this.artistas = artistas != null ? artistas : new ArrayList<>();
    }

    public Musica(String nomeMusica, Date dataLancamento, float tempoMin, String generoMusical) {
        this.nomeMusica = nomeMusica;
        this.dataLancamento = dataLancamento;
        this.tempoMin = tempoMin;
        this.generoMusical = generoMusical;
        this.artistas = new ArrayList<>();
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getNomeMusica() { return nomeMusica; }
    public void setNomeMusica(String nomeMusica) { this.nomeMusica = nomeMusica; }

    public Date getDataLancamento() { return dataLancamento; }
    public void setDataLancamento(Date dataLancamento) { this.dataLancamento = dataLancamento; }

    public float getTempoMin() { return tempoMin; }
    public void setTempoMin(float tempoMin) { this.tempoMin = tempoMin; }

    public String getGeneroMusical() { return generoMusical; }
    public void setGeneroMusical(String generoMusical) { this.generoMusical = generoMusical; }

    public List<Artista> getArtistas() { return artistas; }
    public void setArtistas(List<Artista> artistas) { this.artistas = artistas; }
}
