package comparotto.marcos.aplicativoMusica;

public class PlaylistM {
    private Musica musica;
    private Playlist playlist;
    private int posicao;

    public PlaylistM(Musica musica, Playlist playlist, int posicao) {
        this.musica = musica;
        this.playlist = playlist;
        this.posicao = posicao;
    }

    public Musica getMusica() { return musica; }
    public void setMusica(Musica musica) { this.musica = musica; }

    public Playlist getPlaylist() { return playlist; }
    public void setPlaylist(Playlist playlist) { this.playlist = playlist; }

    public int getPosicao() { return posicao; }
    public void setPosicao(int posicao) { this.posicao = posicao; }
}
