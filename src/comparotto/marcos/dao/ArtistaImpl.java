package comparotto.marcos.dao;

import comparotto.marcos.aplicativoMusica.Artista;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ArtistaImpl implements ArtistaDao {

    @Override
    public void inserir(Artista objeto) {
        String sqlPessoa = "INSERT INTO pessoa (nome, idade) VALUES (?, ?) RETURNING id";
        String sqlArtista = "INSERT INTO artista (id, nome_artistico) VALUES (?, ?)";
        try (Connection conn = Conexao.obterConexao()) {
            conn.setAutoCommit(false);
            long pessoaId = 0;
            try (PreparedStatement stmtP = conn.prepareStatement(sqlPessoa)) {
                stmtP.setString(1, objeto.getNome());
                stmtP.setInt(2, objeto.getIdade());
                ResultSet rs = stmtP.executeQuery();
                if (rs.next()) {
                    pessoaId = rs.getLong(1);
                    objeto.setId(pessoaId);
                }
            }
            try (PreparedStatement stmtA = conn.prepareStatement(sqlArtista)) {
                stmtA.setLong(1, pessoaId);
                stmtA.setString(2, objeto.getNomeArtistico());
                stmtA.executeUpdate();
            }
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Artista> buscarPorID(long id) {
        String sql = "SELECT p.id, p.nome, p.idade, a.nome_artistico FROM pessoa p JOIN artista a ON p.id = a.id WHERE p.id = ?";
        try (Connection conn = Conexao.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Artista a = new Artista(
                    rs.getLong("id"),
                    rs.getString("nome"),
                    rs.getInt("idade"),
                    rs.getString("nome_artistico")
                );
                return Optional.of(a);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public void alterar(Artista objeto) {
        String sqlPessoa = "UPDATE pessoa SET nome = ?, idade = ? WHERE id = ?";
        String sqlArtista = "UPDATE artista SET nome_artistico = ? WHERE id = ?";
        try (Connection conn = Conexao.obterConexao()) {
            conn.setAutoCommit(false);
            try (PreparedStatement stmtP = conn.prepareStatement(sqlPessoa)) {
                stmtP.setString(1, objeto.getNome());
                stmtP.setInt(2, objeto.getIdade());
                stmtP.setLong(3, objeto.getId());
                stmtP.executeUpdate();
            }
            try (PreparedStatement stmtA = conn.prepareStatement(sqlArtista)) {
                stmtA.setString(1, objeto.getNomeArtistico());
                stmtA.setLong(2, objeto.getId());
                stmtA.executeUpdate();
            }
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void excluir(Artista objeto) {
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
    public List<Artista> listarTodos() {
        List<Artista> lista = new ArrayList<>();
        String sql = "SELECT p.id, p.nome, p.idade, a.nome_artistico FROM pessoa p JOIN artista a ON p.id = a.id";
        try (Connection conn = Conexao.obterConexao();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new Artista(
                    rs.getLong("id"),
                    rs.getString("nome"),
                    rs.getInt("idade"),
                    rs.getString("nome_artistico")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}
