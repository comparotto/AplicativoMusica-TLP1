package comparotto.marcos.aplicativoMusica;

import comparotto.marcos.dao.*;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        System.out.println("  AplicativoMusica  ");

        UsuarioDao usuarioDao = new UsuarioImpl();
        ArtistaDao artistaDao = new ArtistaImpl();
        BandaDao bandaDao = new BandaImpl();
        MusicaDao musicaDao = new MusicaImpl();
        PlaylistDao playlistDao = new PlaylistImpl();
        PlaylistMDao playlistMDao = new PlaylistMImpl();

        Usuario usuario1 = new Usuario("Lucas Paqueta", 22, "lucas@flamengo.com", new Date());
        Artista artista1 = new Artista("Pericles", 41, "Pericao");
        Artista sabota = new Artista("Mauro Matheus dos Santos", 29, "Sabotage");
        Banda banda1 = new Banda("Exaltasamba", "Pagode");
        Banda banda2 = new Banda("Racionais", "Rap");

        Pessoa p1 = usuario1;
        Pessoa p2 = artista1;
        Pessoa p3 = sabota;
        p1.exibir();
        p2.exibir();
        p3.exibir();

        usuarioDao.inserir(usuario1);
        artistaDao.inserir(artista1);
        artistaDao.inserir(sabota);
        bandaDao.inserir(banda1);
        bandaDao.inserir(banda2);

        Playlist playlist = new Playlist("Playlist do Paqueta");
        usuario1.adicionarPlaylist(playlist);

        Musica m1 = new Musica("Ate que durou", new Date(), 3.07f, "Pagode");
        Musica m2 = new Musica("Mun Ra", new Date(), 3.36f, "Rap");
        m1.getArtistas().add(artista1);
        m2.getArtistas().add(sabota);

        musicaDao.inserir(m1);
        musicaDao.inserir(m2);

        playlist.adicionarMusica(m1);
        playlist.adicionarMusica(m2);
        playlistDao.inserir(playlist);

        playlistMDao.inserir(playlist.getItens().get(0));
        playlistMDao.inserir(playlist.getItens().get(1));

        Optional<Playlist> playlistBuscada = playlistDao.buscarPorID(playlist.getId());
        System.out.println("\nPlaylist encontrada: " + playlistBuscada.get().getNomePlaylist());

        playlist.setNomePlaylist("Playlist do Paqueta Atualizada");
        playlistDao.alterar(playlist);

        bandaDao.excluir(banda2);

        System.out.println("\nUsuarios cadastrados:");
        List<Usuario> usuariosBanco = usuarioDao.listarTodos();
        usuariosBanco.get(0).exibir();

        System.out.println("\nArtistas cadastrados:");
        List<Artista> artistasBanco = artistaDao.listarTodos();
        artistasBanco.get(0).exibir();
        artistasBanco.get(1).exibir();

        System.out.println("\nBandas cadastradas:");
        List<Banda> bandasBanco = bandaDao.listarTodos();
        bandasBanco.get(0).exibir();

        System.out.println("\nMusicas cadastradas:");
        List<Musica> musicasBanco = musicaDao.listarTodos();
        System.out.println(musicasBanco.get(0).getNomeMusica() + " " + musicasBanco.get(0).getTempoMin() + " min " + musicasBanco.get(0).getGeneroMusical());
        System.out.println(musicasBanco.get(1).getNomeMusica() + " " + musicasBanco.get(1).getTempoMin() + " min " + musicasBanco.get(1).getGeneroMusical());

        System.out.println("\nPlaylists cadastradas:");
        List<Playlist> playlistsBanco = playlistDao.listarTodos();
        System.out.println("Playlist: " + playlistsBanco.get(0).getNomePlaylist() + "  Duracao: " + playlistsBanco.get(0).getDuracao() + " min Dono: " + playlistsBanco.get(0).getUsuario().getNome());
    }
}