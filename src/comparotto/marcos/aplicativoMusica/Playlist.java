package comparotto.marcos.aplicativoMusica;

import java.util.ArrayList;
import java.util.List;

public class Playlist {

    private long id;
    private String nomePlaylist;
    private List<PlaylistM> itens;
    private float duracao;
    private Usuario usuario;

    public Playlist() {
        this.itens = new ArrayList<PlaylistM>();
    }

    public Playlist(String nomePlaylist) {
        this.nomePlaylist = nomePlaylist;
        this.itens = new ArrayList<PlaylistM>();
    }

    public Playlist(long id, String nomePlaylist, List<PlaylistM> itens) {
        this.id = id;
        this.nomePlaylist = nomePlaylist;
        this.itens = itens;
    }

    public Playlist(long id, String nomePlaylist, float duracao, Usuario usuario) {
        this.id = id;
        this.nomePlaylist = nomePlaylist;
        this.duracao = duracao;
        this.usuario = usuario;
        this.itens = new ArrayList<PlaylistM>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNomePlaylist() {
        return nomePlaylist;
    }

    public void setNomePlaylist(String nomePlaylist) {
        this.nomePlaylist = nomePlaylist;
    }

    public List<PlaylistM> getItens() {
        return itens;
    }

    public void setItens(List<PlaylistM> itens) {
        this.itens = itens;
    }

    public float getDuracao() {
        return duracao;
    }

    public void setDuracao(float duracao) {
        this.duracao = duracao;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void adicionarMusica(Musica musica) {
        int posicao = this.itens.size() + 1;
        PlaylistM item = new PlaylistM(musica, this, posicao);
        this.itens.add(item);
        recalcularDuracao();
    }

    public void recalcularDuracao() {
        float total = 0;
        for (PlaylistM item : this.itens) {
            total = total + item.getMusica().getTempoMin();
        }
        this.duracao = total;
    }
}