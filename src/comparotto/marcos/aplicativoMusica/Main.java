package comparotto.marcos.aplicativoMusica;

import comparotto.marcos.dao.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Aplicativo de Música ===");


        Usuario usuario = new Usuario("Marcos Comparotto", 22, "marcos@email.com", new Date());
        Artista artista1 = new Artista("Chester Bennington", 41, "Chester");

        System.out.println("\n--- Teste de Polimorfismo e Herança ---");
        Pessoa p1 = usuario;
        Pessoa p2 = artista1;
        p1.exibir();
        p2.exibir();

        Playlist playlist = new Playlist("Minhas Favoritas");
        playlist.getDonos().add(usuario);

        Musica m1 = new Musica("Numb", new Date(), 3.07f, "Rock");
        Musica m2 = new Musica("In the End", new Date(), 3.36f, "Rock");
        m1.getArtistas().add(artista1);
        m2.getArtistas().add(artista1);

        playlist.adicionarMusica(m1);
        playlist.adicionarMusica(m2);


        System.out.println("\nPlaylist: " + playlist.getNomePlaylist() + " | Duração Total: " + playlist.getDuracao() + " min");
        System.out.println("Músicas na Playlist (Relacionamento 1:N via PlaylistM):");
        for (PlaylistM item : playlist.getItens()) {
            System.out.println(" - Pos " + item.getPosicao() + ": " + item.getMusica().getNomeMusica() + " (" + item.getMusica().getTempoMin() + " min)");
        }
    }
}
