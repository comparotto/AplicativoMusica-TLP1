package comparotto.marcos.aplicativoMusica;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Usuario extends Pessoa {

    private String email;
    private Date dataCadastro;
    private List<Playlist> playlists;

    public Usuario() {
        this.playlists = new ArrayList<Playlist>();
    }

    public Usuario(String nome, int idade, String email, Date dataCadastro) {
        super(nome, idade);
        this.email = email;
        this.dataCadastro = dataCadastro;
        this.playlists = new ArrayList<Playlist>();
    }

    public Usuario(String nome, int idade, long id, String email, Date dataCadastro) {
        super(id, nome, idade);
        this.email = email;
        this.dataCadastro = dataCadastro;
        this.playlists = new ArrayList<Playlist>();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public List<Playlist> getPlaylists() {
        return playlists;
    }

    public void setPlaylists(List<Playlist> playlists) {
        this.playlists = playlists;
    }

    public void adicionarPlaylist(Playlist playlist) {
        playlist.setUsuario(this);
        this.playlists.add(playlist);
    }

    @Override
    public void exibir() {
        System.out.println("id: " + getId() + " nome: " + getNome() + " idade: " + getIdade() + " email: " + email + " cadastro: " + dataCadastro + " playlists: " + playlists.size());
    }
}