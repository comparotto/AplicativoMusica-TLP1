package comparotto.marcos.dao;

import comparotto.marcos.aplicativoMusica.Pessoa;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PessoaImpl implements PessoaDao {

    @Override
    public void inserir(Pessoa objeto) {
        String sql = "INSERT INTO pessoa (nome, idade) VALUES (?, ?) RETURNING id";
        try (Connection conn = Conexao.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, objeto.getNome());
            stmt.setInt(2, objeto.getIdade());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                objeto.setId(rs.getLong("id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Pessoa> buscarPorID(long id) {
        String sql = "SELECT * FROM pessoa WHERE id = ?";
        try (Connection conn = Conexao.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String nome = rs.getString("nome");
                int idade = rs.getInt("idade");
                // Retorna como Pessoa generica (classe anonima)
                Pessoa p = new Pessoa(id, nome, idade) {};
                return Optional.of(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public void alterar(Pessoa objeto) {
        String sql = "UPDATE pessoa SET nome = ?, idade = ? WHERE id = ?";
        try (Connection conn = Conexao.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, objeto.getNome());
            stmt.setInt(2, objeto.getIdade());
            stmt.setLong(3, objeto.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void excluir(Pessoa objeto) {
        String sql = "DELETE FROM pessoa WHERE id = ?";
        try (Connection conn = Conexao.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, objeto.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Pessoa> listarTodos() {
        List<Pessoa> lista = new ArrayList<>();
        String sql = "SELECT * FROM pessoa";
        try (Connection conn = Conexao.obterConexao();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                long id = rs.getLong("id");
                String nome = rs.getString("nome");
                int idade = rs.getInt("idade");
                lista.add(new Pessoa(id, nome, idade) {});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}
