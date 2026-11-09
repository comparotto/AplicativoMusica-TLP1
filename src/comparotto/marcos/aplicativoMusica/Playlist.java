package comparotto.marcos.aplicativoMusica;

import java.util.ArrayList;
import java.util.List;

public class Playlist {
    private long id;
    private String nomePlaylist;
    private List<PlaylistM> itens;
    private float duracao;
    private List<Usuario> donos;

    public Playlist(long id, String nomePlaylist, List<Usuario> donos) {
        this.id = id;
        this.nomePlaylist = nomePlaylist;
        this.donos = donos != null ? donos : new ArrayList<>();
        this.itens = new ArrayList<>();
        this.duracao = 0.0f;
    }

    public Playlist(String nomePlaylist) {
        this.nomePlaylist = nomePlaylist;
        this.donos = new ArrayList<>();
        this.itens = new ArrayList<>();
        this.duracao = 0.0f;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getNomePlaylist() { return nomePlaylist; }
    public void setNomePlaylist(String nomePlaylist) { this.nomePlaylist = nomePlaylist; }

    public List<PlaylistM> getItens() { return itens; }
    public void setItens(List<PlaylistM> itens) { this.itens = itens; recalcularduracao(); }

    public float getDuracao() { return duracao; }

    public List<Usuario> getDonos() { return donos; }
    public void setDonos(List<Usuario> donos) { this.donos = donos; }

    public void adicionarMusica(Musica musica) {
        int proximaPosicao = itens.size() + 1;
        itens.add(new PlaylistM(musica, this, proximaPosicao));
        recalcularduracao();
    }

    private void recalcularduracao() {
        float soma = 0.0f;
        for (PlaylistM pm : itens) {
            if (pm.getMusica() != null) {
                soma += pm.getMusica().getTempoMin();
            }
        }
        this.duracao = soma;
    }
}
