package comparotto.marcos.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import comparotto.marcos.aplicativoMusica.Playlist;
import comparotto.marcos.aplicativoMusica.Usuario;

public class PlaylistImpl implements PlaylistDao {

    @Override
    public void inserir(Playlist objeto) {
        String sql = "insert into playlist (nome_playlist, duracao, usuario_id) values (?, ?, ?) returning id";

        try {
            Connection con = Conexao.obterConexao();

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, objeto.getNomePlaylist());
            ps.setFloat(2, objeto.getDuracao());
            ps.setLong(3, objeto.getUsuario().getId());
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                objeto.setId(rs.getLong(1));
            }
            System.out.println("Playlist salva");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Optional<Playlist> buscarPorID(long id) {
        String sql = "select * from playlist where id = ?";
        String sqlUsuario = "select * from usuario where id = ?";
        String sqlPessoa = "select * from pessoa where id = ?";

        try {
            Connection con = Conexao.obterConexao();

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                long usuarioId = rs.getLong("usuario_id");
                Usuario u = new Usuario();
                u.setId(usuarioId);

                PreparedStatement psUsuario = con.prepareStatement(sqlUsuario);
                psUsuario.setLong(1, usuarioId);
                ResultSet rsUsuario = psUsuario.executeQuery();
                if (rsUsuario.next()) {
                    u.setEmail(rsUsuario.getString("email"));
                    u.setDataCadastro(rsUsuario.getTimestamp("data_cadastro"));
                }

                PreparedStatement psPessoa = con.prepareStatement(sqlPessoa);
                psPessoa.setLong(1, usuarioId);
                ResultSet rsPessoa = psPessoa.executeQuery();
                if (rsPessoa.next()) {
                    u.setNome(rsPessoa.getString("nome"));
                    u.setIdade(rsPessoa.getInt("idade"));
                }

                Playlist p = new Playlist();
                p.setId(rs.getLong("id"));
                p.setNomePlaylist(rs.getString("nome_playlist"));
                p.setDuracao(rs.getFloat("duracao"));
                p.setUsuario(u);
                return Optional.of(p);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return Optional.empty();
    }

    @Override
    public void alterar(Playlist objeto) {
        String sql = "update playlist set nome_playlist = ?, duracao = ?, usuario_id = ? where id = ?";

        try {
            Connection con = Conexao.obterConexao();

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, objeto.getNomePlaylist());
            ps.setFloat(2, objeto.getDuracao());
            ps.setLong(3, objeto.getUsuario().getId());
            ps.setLong(4, objeto.getId());
            ps.executeUpdate();
            System.out.println("Playlist alterada");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void excluir(Playlist objeto) {
        String sql = "delete from playlist where id = ?";

        try {
            Connection con = Conexao.obterConexao();

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, objeto.getId());
            ps.executeUpdate();
            System.out.println("Playlist removida");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Playlist> listarTodos() {
        String sql = "select * from playlist";
        String sqlUsuario = "select * from usuario where id = ?";
        String sqlPessoa = "select * from pessoa where id = ?";
        List<Playlist> lista = new ArrayList<Playlist>();

        try {
            Connection con = Conexao.obterConexao();

            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                long usuarioId = rs.getLong("usuario_id");
                Usuario u = new Usuario();
                u.setId(usuarioId);

                PreparedStatement psUsuario = con.prepareStatement(sqlUsuario);
                psUsuario.setLong(1, usuarioId);
                ResultSet rsUsuario = psUsuario.executeQuery();
                if (rsUsuario.next()) {
                    u.setEmail(rsUsuario.getString("email"));
                    u.setDataCadastro(rsUsuario.getTimestamp("data_cadastro"));
                }

                PreparedStatement psPessoa = con.prepareStatement(sqlPessoa);
                psPessoa.setLong(1, usuarioId);
                ResultSet rsPessoa = psPessoa.executeQuery();
                if (rsPessoa.next()) {
                    u.setNome(rsPessoa.getString("nome"));
                    u.setIdade(rsPessoa.getInt("idade"));
                }

                Playlist p = new Playlist();
                p.setId(rs.getLong("id"));
                p.setNomePlaylist(rs.getString("nome_playlist"));
                p.setDuracao(rs.getFloat("duracao"));
                p.setUsuario(u);
                lista.add(p);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return lista;
    }
}