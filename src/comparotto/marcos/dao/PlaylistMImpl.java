package comparotto.marcos.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import comparotto.marcos.aplicativoMusica.Musica;
import comparotto.marcos.aplicativoMusica.Playlist;
import comparotto.marcos.aplicativoMusica.PlaylistM;

public class PlaylistMImpl implements PlaylistMDao {

    @Override
    public void inserir(PlaylistM objeto) {
        String sql = "insert into playlist_musica (playlist_id, musica_id, posicao) values (?, ?, ?)";

        try {
            Connection con = Conexao.obterConexao();

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, objeto.getPlaylist().getId());
            ps.setLong(2, objeto.getMusica().getId());
            ps.setInt(3, objeto.getPosicao());
            ps.executeUpdate();
            System.out.println("associacao salva");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Optional<PlaylistM> buscarPorID(long id) {
        String sql = "select * from playlist_musica where playlist_id = ?";
        String sqlPlaylist = "select * from playlist where id = ?";
        String sqlMusica = "select * from musica where id = ?";

        try {
            Connection con = Conexao.obterConexao();

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                long playlistId = rs.getLong("playlist_id");
                long musicaId = rs.getLong("musica_id");

                Playlist playlist = new Playlist();
                playlist.setId(playlistId);
                PreparedStatement psPlaylist = con.prepareStatement(sqlPlaylist);
                psPlaylist.setLong(1, playlistId);
                ResultSet rsPlaylist = psPlaylist.executeQuery();
                if (rsPlaylist.next()) {
                    playlist.setNomePlaylist(rsPlaylist.getString("nome_playlist"));
                    playlist.setDuracao(rsPlaylist.getFloat("duracao"));
                }

                Musica musica = new Musica();
                musica.setId(musicaId);
                PreparedStatement psMusica = con.prepareStatement(sqlMusica);
                psMusica.setLong(1, musicaId);
                ResultSet rsMusica = psMusica.executeQuery();
                if (rsMusica.next()) {
                    musica.setNomeMusica(rsMusica.getString("nome_musica"));
                    musica.setDataLancamento(rsMusica.getDate("data_lancamento"));
                    musica.setTempoMin(rsMusica.getFloat("tempo_min"));
                    musica.setGeneroMusical(rsMusica.getString("genero_musical"));
                }

                PlaylistM pm = new PlaylistM();
                pm.setPlaylist(playlist);
                pm.setMusica(musica);
                pm.setPosicao(rs.getInt("posicao"));
                return Optional.of(pm);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return Optional.empty();
    }

    @Override
    public void alterar(PlaylistM objeto) {
        String sql = "update playlist_musica set posicao = ? where playlist_id = ? and musica_id = ?";

        try {
            Connection con = Conexao.obterConexao();

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, objeto.getPosicao());
            ps.setLong(2, objeto.getPlaylist().getId());
            ps.setLong(3, objeto.getMusica().getId());
            ps.executeUpdate();
            System.out.println("associacao alterada");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void excluir(PlaylistM objeto) {
        String sql = "delete from playlist_musica where playlist_id = ? and musica_id = ?";

        try {
            Connection con = Conexao.obterConexao();

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, objeto.getPlaylist().getId());
            ps.setLong(2, objeto.getMusica().getId());
            ps.executeUpdate();
            System.out.println("associacao removida");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<PlaylistM> listarTodos() {
        String sql = "select * from playlist_musica";
        String sqlPlaylist = "select * from playlist where id = ?";
        String sqlMusica = "select * from musica where id = ?";
        List<PlaylistM> lista = new ArrayList<PlaylistM>();

        try {
            Connection con = Conexao.obterConexao();

            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                long playlistId = rs.getLong("playlist_id");
                long musicaId = rs.getLong("musica_id");

                Playlist playlist = new Playlist();
                playlist.setId(playlistId);
                PreparedStatement psPlaylist = con.prepareStatement(sqlPlaylist);
                psPlaylist.setLong(1, playlistId);
                ResultSet rsPlaylist = psPlaylist.executeQuery();
                if (rsPlaylist.next()) {
                    playlist.setNomePlaylist(rsPlaylist.getString("nome_playlist"));
                    playlist.setDuracao(rsPlaylist.getFloat("duracao"));
                }

                Musica musica = new Musica();
                musica.setId(musicaId);
                PreparedStatement psMusica = con.prepareStatement(sqlMusica);
                psMusica.setLong(1, musicaId);
                ResultSet rsMusica = psMusica.executeQuery();
                if (rsMusica.next()) {
                    musica.setNomeMusica(rsMusica.getString("nome_musica"));
                    musica.setDataLancamento(rsMusica.getDate("data_lancamento"));
                    musica.setTempoMin(rsMusica.getFloat("tempo_min"));
                    musica.setGeneroMusical(rsMusica.getString("genero_musical"));
                }

                PlaylistM pm = new PlaylistM();
                pm.setPlaylist(playlist);
                pm.setMusica(musica);
                pm.setPosicao(rs.getInt("posicao"));
                lista.add(pm);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return lista;
    }
}