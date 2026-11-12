package comparotto.marcos.dao;

import comparotto.marcos.aplicativoMusica.Musica;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MusicaImpl implements MusicaDao {

    @Override
    public void inserir(Musica objeto) {
        String sql = "insert into musica (nome_musica, data_lancamento, tempo_min, genero_musical) values (?, ?, ?, ?) ";
        try (Connection conn = Conexao.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, objeto.getNomeMusica());
            stmt.setDate(2, new java.sql.Date(objeto.getDataLancamento().getTime()));
            stmt.setFloat(3, objeto.getTempoMin());
            stmt.setString(4, objeto.getGeneroMusical());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                objeto.setId(rs.getLong(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Musica> buscarPorID(long id) {
        String sql = "select * from musica WHERE id = ?";
        try (Connection conn = Conexao.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Musica m = new Musica(
                    rs.getLong("id"),
                    rs.getString("nome_musica"),
                    rs.getDate("data_lancamento"),
                    rs.getFloat("tempo_min"),
                    rs.getString("genero_musical"),
                    null
                );
                return Optional.of(m);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public void alterar(Musica objeto) {
        String sql = "update musica set nome_musica = ?, data_lancamento = ?, tempo_min = ?, genero_musical = ? where id = ?";
        try (Connection conn = Conexao.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, objeto.getNomeMusica());
            stmt.setDate(2, new java.sql.Date(objeto.getDataLancamento().getTime()));
            stmt.setFloat(3, objeto.getTempoMin());
            stmt.setString(4, objeto.getGeneroMusical());
            stmt.setLong(5, objeto.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void excluir(Musica objeto) {
        String sql = "delete from musica where id = ?";
        try (Connection conn = Conexao.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, objeto.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Musica> listarTodos() {
        List<Musica> lista = new ArrayList<>();
        String sql = "select * from musica";
        try (Connection conn = Conexao.obterConexao();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new Musica(
                    rs.getLong("id"),
                    rs.getString("nome_musica"),
                    rs.getDate("data_lancamento"),
                    rs.getFloat("tempo_min"),
                    rs.getString("genero_musical"),
                    null
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}
