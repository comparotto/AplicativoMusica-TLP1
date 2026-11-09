package comparotto.marcos.dao;

import comparotto.marcos.aplicativoMusica.Banda;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BandaImpl implements BandaDao {

    @Override
    public void inserir(Banda objeto) {
        String sql = "INSERT INTO banda (nome, genero_musical) VALUES (?, ?) RETURNING id";
        try (Connection conn = Conexao.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, objeto.getNome());
            stmt.setString(2, objeto.getGeneroMusical());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                objeto.setId(rs.getLong(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Banda> buscarPorID(long id) {
        String sql = "SELECT * FROM banda WHERE id = ?";
        try (Connection conn = Conexao.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Banda b = new Banda(
                    rs.getLong("id"),
                    rs.getString("nome"),
                    rs.getString("genero_musical"),
                    null, null
                );
                return Optional.of(b);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public void alterar(Banda objeto) {
        String sql = "UPDATE banda SET nome = ?, genero_musical = ? WHERE id = ?";
        try (Connection conn = Conexao.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, objeto.getNome());
            stmt.setString(2, objeto.getGeneroMusical());
            stmt.setLong(3, objeto.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void excluir(Banda objeto) {
        String sql = "DELETE FROM banda WHERE id = ?";
        try (Connection conn = Conexao.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, objeto.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Banda> listarTodos() {
        List<Banda> lista = new ArrayList<>();
        String sql = "SELECT * FROM banda";
        try (Connection conn = Conexao.obterConexao();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new Banda(
                    rs.getLong("id"),
                    rs.getString("nome"),
                    rs.getString("genero_musical"),
                    null, null
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}
