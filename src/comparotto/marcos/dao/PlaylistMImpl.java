package comparotto.marcos.dao;

import comparotto.marcos.aplicativoMusica.PlaylistM;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PlaylistMImpl implements PlaylistMDao {

    @Override
    public void inserir(PlaylistM objeto) {
        String sql = "insert into playlist_musica (playlist_id, musica_id, posicao) values (?, ?, ?)";
        try (Connection conn = Conexao.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, objeto.getPlaylist().getId());
            stmt.setLong(2, objeto.getMusica().getId());
            stmt.setInt(3, objeto.getPosicao());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<PlaylistM> buscarPorID(long id) {
        throw new UnsupportedOperationException("Busca por ID não aplicável diretamente para tabela reduzida associativa sem ID próprio.");
    }

    @Override
    public void alterar(PlaylistM objeto) {
        String sql = "update playlist_musica set posicao = ? where playlist_id = ? and musica_id = ?";
        try (Connection conn = Conexao.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, objeto.getPosicao());
            stmt.setLong(2, objeto.getPlaylist().getId());
            stmt.setLong(3, objeto.getMusica().getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void excluir(PlaylistM objeto) {
        String sql = "delete from playlist_musica where playlist_id = ? and musica_id = ?";
        try (Connection conn = Conexao.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, objeto.getPlaylist().getId());
            stmt.setLong(2, objeto.getMusica().getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<PlaylistM> listarTodos() {
        List<PlaylistM> lista = new ArrayList<>();
        String sql = "select * from playlist_musica";
        try (Connection conn = Conexao.obterConexao();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}
