package comparotto.marcos.dao;

import comparotto.marcos.aplicativoMusica.Playlist;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PlaylistImpl implements PlaylistDao {

    @Override
    public void inserir(Playlist objeto) {
        String sql = "insert into playlist (nome_playlist, duracao) values (?, ?) ";
        try (Connection conn = Conexao.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, objeto.getNomePlaylist());
            stmt.setFloat(2, objeto.getDuracao());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                objeto.setId(rs.getLong(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Playlist> buscarPorID(long id) {
        String sql = "select * from playlist where id = ?";
        try (Connection conn = Conexao.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Playlist p = new Playlist(
                    rs.getLong("id"),
                    rs.getString("nome_playlist"),
                    null
                );
                return Optional.of(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public void alterar(Playlist objeto) {
        String sql = "update playlist set nome_playlist = ?, duracao = ? where id = ?";
        try (Connection conn = Conexao.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, objeto.getNomePlaylist());
            stmt.setFloat(2, objeto.getDuracao());
            stmt.setLong(3, objeto.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void excluir(Playlist objeto) {
        String sql = "delete from playlist where id = ?";
        try (Connection conn = Conexao.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, objeto.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Playlist> listarTodos() {
        List<Playlist> lista = new ArrayList<>();
        String sql = "select * from playlist";
        try (Connection conn = Conexao.obterConexao();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new Playlist(
                    rs.getLong("id"),
                    rs.getString("nome_playlist"),
                    null
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}
