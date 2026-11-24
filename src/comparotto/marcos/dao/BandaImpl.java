package comparotto.marcos.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import comparotto.marcos.aplicativoMusica.Banda;

public class BandaImpl implements BandaDao {

    @Override
    public void inserir(Banda objeto) {
        String sql = "insert into banda (nome, genero_musical) values (?, ?) returning id";

        try {
            Connection con = Conexao.obterConexao();

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, objeto.getNome());
            ps.setString(2, objeto.getGeneroMusical());
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                objeto.setId(rs.getLong(1));
            }
            System.out.println("Banda cadastrada");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Optional<Banda> buscarPorID(long id) {
        String sql = "select * from banda where id = ?";

        try {
            Connection con = Conexao.obterConexao();

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Banda b = new Banda();
                b.setId(rs.getLong("id"));
                b.setNome(rs.getString("nome"));
                b.setGeneroMusical(rs.getString("genero_musical"));
                return Optional.of(b);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return Optional.empty();
    }

    @Override
    public void alterar(Banda objeto) {
        String sql = "update banda set nome = ?, genero_musical = ? where id = ?";

        try {
            Connection con = Conexao.obterConexao();

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, objeto.getNome());
            ps.setString(2, objeto.getGeneroMusical());
            ps.setLong(3, objeto.getId());
            ps.executeUpdate();
            System.out.println("Banda alterada");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void excluir(Banda objeto) {
        String sql = "delete from banda where id = ?";

        try {
            Connection con = Conexao.obterConexao();

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, objeto.getId());
            ps.executeUpdate();
            System.out.println("Banda removida");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Banda> listarTodos() {
        String sql = "select * from banda";
        List<Banda> lista = new ArrayList<Banda>();

        try {
            Connection con = Conexao.obterConexao();

            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Banda b = new Banda();
                b.setId(rs.getLong("id"));
                b.setNome(rs.getString("nome"));
                b.setGeneroMusical(rs.getString("genero_musical"));
                lista.add(b);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return lista;
    }
}