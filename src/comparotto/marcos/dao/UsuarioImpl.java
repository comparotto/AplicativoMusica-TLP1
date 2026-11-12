package comparotto.marcos.dao;

import comparotto.marcos.aplicativoMusica.Usuario;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UsuarioImpl implements UsuarioDao {

    @Override
    public void inserir(Usuario objeto) {
        String sqlPessoa = "insert into pessoa (nome, idade) values (?, ?)";
        String sqlUsuario = "insert into usuario (id, email, data_cadastro) values (?, ?, ?)";
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
        String sql = "select * from usuario where id = ?";
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
                    rs.getDate("data_cadastro")
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
        String sqlUsuario = "update usuario set email = ?, data_cadastro = ? where id = ?";
        try (Connection conn = Conexao.obterConexao()) {
            conn.setAutoCommit(false);
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
        String sql = "delete from pessoa where id = ?";
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
        String sql = "select * from usuario";
        try (Connection conn = Conexao.obterConexao();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new Usuario(
                    rs.getLong("id"),
                    rs.getString("nome"),
                    rs.getInt("idade"),
                    rs.getString("email"),
                    rs.getDate("data_cadastro")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}
