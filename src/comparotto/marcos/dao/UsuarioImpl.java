package comparotto.marcos.dao;

import comparotto.marcos.aplicativoMusica.Usuario;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UsuarioImpl implements UsuarioDao {

    @Override
    public void inserir(Usuario objeto) {
        String sqlPessoa = "INSERT INTO pessoa (nome, idade) VALUES (?, ?) RETURNING id";
        String sqlUsuario = "INSERT INTO usuario (id, email, data_cadastro) VALUES (?, ?, ?)";
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
            try (PreparedStatement stmtU = conn.prepareStatement(sqlUsuario)) {
                stmtU.setLong(1, pessoaId);
                stmtU.setString(2, objeto.getEmail());
                stmtU.setTimestamp(3, new Timestamp(objeto.getDataCadastro().getTime()));
                stmtU.executeUpdate();
            }
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Usuario> buscarPorID(long id) {
        String sql = "SELECT p.id, p.nome, p.idade, u.email, u.data_cadastro FROM pessoa p JOIN usuario u ON p.id = u.id WHERE p.id = ?";
        try (Connection conn = Conexao.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Usuario u = new Usuario(
                    rs.getLong("id"),
                    rs.getString("nome"),
                    rs.getInt("idade"),
                    rs.getString("email"),
                    new java.util.Date(rs.getTimestamp("data_cadastro").getTime())
                );
                return Optional.of(u);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public void alterar(Usuario objeto) {
        String sqlPessoa = "UPDATE pessoa SET nome = ?, idade = ? WHERE id = ?";
        String sqlUsuario = "UPDATE usuario SET email = ?, data_cadastro = ? WHERE id = ?";
        try (Connection conn = Conexao.obterConexao()) {
            conn.setAutoCommit(false);
            try (PreparedStatement stmtP = conn.prepareStatement(sqlPessoa)) {
                stmtP.setString(1, objeto.getNome());
                stmtP.setInt(2, objeto.getIdade());
                stmtP.setLong(3, objeto.getId());
                stmtP.executeUpdate();
            }
            try (PreparedStatement stmtU = conn.prepareStatement(sqlUsuario)) {
                stmtU.setString(1, objeto.getEmail());
                stmtU.setTimestamp(2, new Timestamp(objeto.getDataCadastro().getTime()));
                stmtU.setLong(3, objeto.getId());
                stmtU.executeUpdate();
            }
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void excluir(Usuario objeto) {
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
    public List<Usuario> listarTodos() {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT p.id, p.nome, p.idade, u.email, u.data_cadastro FROM pessoa p JOIN usuario u ON p.id = u.id";
        try (Connection conn = Conexao.obterConexao();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new Usuario(
                    rs.getLong("id"),
                    rs.getString("nome"),
                    rs.getInt("idade"),
                    rs.getString("email"),
                    new java.util.Date(rs.getTimestamp("data_cadastro").getTime())
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}
