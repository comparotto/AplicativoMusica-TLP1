package comparotto.marcos.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import comparotto.marcos.aplicativoMusica.Playlist;
import comparotto.marcos.aplicativoMusica.Usuario;

public class UsuarioImpl implements UsuarioDao {

    @Override
    public void inserir(Usuario objeto) {
        String sqlPessoa = "insert into pessoa (nome, idade) values (?, ?) returning id";
        String sqlUsuario = "insert into usuario (id, email, data_cadastro) values (?, ?, ?)";

        try {
            Connection con = Conexao.obterConexao();

            PreparedStatement psPessoa = con.prepareStatement(sqlPessoa);
            psPessoa.setString(1, objeto.getNome());
            psPessoa.setInt(2, objeto.getIdade());
            ResultSet rs = psPessoa.executeQuery();

            long idPessoa = 0;
            if (rs.next()) {
                idPessoa = rs.getLong(1);
                objeto.setId(idPessoa);
            }

            PreparedStatement psUsuario = con.prepareStatement(sqlUsuario);
            psUsuario.setLong(1, idPessoa);
            psUsuario.setString(2, objeto.getEmail());
            psUsuario.setTimestamp(3, new Timestamp(objeto.getDataCadastro().getTime()));
            psUsuario.executeUpdate();
            System.out.println("Usuario salvo");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Optional<Usuario> buscarPorID(long id) {
        String sql = "select * from usuario where id = ?";
        String sqlPessoa = "select * from pessoa where id = ?";
        String sqlPlaylist = "select * from playlist where usuario_id = ?";

        try {
            Connection con = Conexao.obterConexao();

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Usuario u = new Usuario();
                u.setId(rs.getLong("id"));
                u.setEmail(rs.getString("email"));
                u.setDataCadastro(rs.getTimestamp("data_cadastro"));

                PreparedStatement psPessoa = con.prepareStatement(sqlPessoa);
                psPessoa.setLong(1, u.getId());
                ResultSet rsPessoa = psPessoa.executeQuery();
                if (rsPessoa.next()) {
                    u.setNome(rsPessoa.getString("nome"));
                    u.setIdade(rsPessoa.getInt("idade"));
                }

                PreparedStatement psPlaylist = con.prepareStatement(sqlPlaylist);
                psPlaylist.setLong(1, u.getId());
                ResultSet rsPlaylist = psPlaylist.executeQuery();
                while (rsPlaylist.next()) {
                    Playlist p = new Playlist();
                    p.setId(rsPlaylist.getLong("id"));
                    p.setNomePlaylist(rsPlaylist.getString("nome_playlist"));
                    p.setDuracao(rsPlaylist.getFloat("duracao"));
                    p.setUsuario(u);
                    u.getPlaylists().add(p);
                }

                return Optional.of(u);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return Optional.empty();
    }

    @Override
    public void alterar(Usuario objeto) {
        String sqlPessoa = "update pessoa set nome = ?, idade = ? where id = ?";
        String sqlUsuario = "update usuario set email = ?, data_cadastro = ? where id = ?";

        try {
            Connection con = Conexao.obterConexao();

            PreparedStatement psPessoa = con.prepareStatement(sqlPessoa);
            psPessoa.setString(1, objeto.getNome());
            psPessoa.setInt(2, objeto.getIdade());
            psPessoa.setLong(3, objeto.getId());
            psPessoa.executeUpdate();

            PreparedStatement psUsuario = con.prepareStatement(sqlUsuario);
            psUsuario.setString(1, objeto.getEmail());
            psUsuario.setTimestamp(2, new Timestamp(objeto.getDataCadastro().getTime()));
            psUsuario.setLong(3, objeto.getId());
            psUsuario.executeUpdate();
            System.out.println("Usuario alterado");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void excluir(Usuario objeto) {
        String sqlUsuario = "delete from usuario where id = ?";
        String sqlPessoa = "delete from pessoa where id = ?";

        try {
            Connection con = Conexao.obterConexao();

            PreparedStatement psUsuario = con.prepareStatement(sqlUsuario);
            psUsuario.setLong(1, objeto.getId());
            psUsuario.executeUpdate();

            PreparedStatement psPessoa = con.prepareStatement(sqlPessoa);
            psPessoa.setLong(1, objeto.getId());
            psPessoa.executeUpdate();
            System.out.println("Usuario removido");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Usuario> listarTodos() {
        String sql = "select * from usuario";
        String sqlPessoa = "select * from pessoa where id = ?";
        String sqlPlaylist = "select * from playlist where usuario_id = ?";
        List<Usuario> usuarios = new ArrayList<Usuario>();

        try {
            Connection con = Conexao.obterConexao();

            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Usuario u = new Usuario();
                u.setId(rs.getLong("id"));
                u.setEmail(rs.getString("email"));
                u.setDataCadastro(rs.getTimestamp("data_cadastro"));

                PreparedStatement psPessoa = con.prepareStatement(sqlPessoa);
                psPessoa.setLong(1, u.getId());
                ResultSet rsPessoa = psPessoa.executeQuery();
                if (rsPessoa.next()) {
                    u.setNome(rsPessoa.getString("nome"));
                    u.setIdade(rsPessoa.getInt("idade"));
                }

                PreparedStatement psPlaylist = con.prepareStatement(sqlPlaylist);
                psPlaylist.setLong(1, u.getId());
                ResultSet rsPlaylist = psPlaylist.executeQuery();
                while (rsPlaylist.next()) {
                    Playlist p = new Playlist();
                    p.setId(rsPlaylist.getLong("id"));
                    p.setNomePlaylist(rsPlaylist.getString("nome_playlist"));
                    p.setDuracao(rsPlaylist.getFloat("duracao"));
                    p.setUsuario(u);
                    u.getPlaylists().add(p);
                }

                usuarios.add(u);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return usuarios;
    }
}