package comparotto.marcos.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import comparotto.marcos.aplicativoMusica.Pessoa;

public class PessoaImpl implements PessoaDao {

    @Override
    public void inserir(Pessoa objeto) {
        String sql = "insert into pessoa (nome, idade) values (?, ?) returning id";

        try {
            Connection con = Conexao.obterConexao();

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, objeto.getNome());
            ps.setInt(2, objeto.getIdade());
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                objeto.setId(rs.getLong(1));
            }
            System.out.println("Pessoa salva(cadastrada)");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Optional<Pessoa> buscarPorID(long id) {
        String sql = "select * from pessoa where id = ?";

        try {
            Connection con = Conexao.obterConexao();

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Pessoa p = new Pessoa(rs.getLong("id"), rs.getString("nome"), rs.getInt("idade")) {};
                return Optional.of(p);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return Optional.empty();
    }

    @Override
    public void alterar(Pessoa objeto) {
        String sql = "update pessoa set nome = ?, idade = ? where id = ?";

        try {
            Connection con = Conexao.obterConexao();

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, objeto.getNome());
            ps.setInt(2, objeto.getIdade());
            ps.setLong(3, objeto.getId());
            ps.executeUpdate();
            System.out.println("Pessoa alterada");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void excluir(Pessoa objeto) {
        String sql = "delete from pessoa where id = ?";

        try {
            Connection con = Conexao.obterConexao();

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, objeto.getId());
            ps.executeUpdate();
            System.out.println("Pessoa removida");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Pessoa> listarTodos() {
        String sql = "select * from pessoa";
        List<Pessoa> lista = new ArrayList<Pessoa>();

        try {
            Connection con = Conexao.obterConexao();

            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Pessoa p = new Pessoa(rs.getLong("id"), rs.getString("nome"), rs.getInt("idade")) {};
                lista.add(p);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return lista;
    }
}