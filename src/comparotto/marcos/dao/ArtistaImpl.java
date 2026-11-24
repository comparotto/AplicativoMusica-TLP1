package comparotto.marcos.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import comparotto.marcos.aplicativoMusica.Artista;

public class ArtistaImpl implements ArtistaDao {

    @Override
    public void inserir(Artista objeto) {
        String sqlPessoa = "insert into pessoa (nome, idade) values (?, ?) returning id";
        String sqlArtista = "insert into artista (id, nome_artistico) values (?, ?)";

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

            PreparedStatement psArtista = con.prepareStatement(sqlArtista);
            psArtista.setLong(1, idPessoa);
            psArtista.setString(2, objeto.getNomeArtistico());
            psArtista.executeUpdate();
            System.out.println("Artita cadastrado");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Optional<Artista> buscarPorID(long id) {
        String sql = "select * from artista where id = ?";
        String sqlPessoa = "select * from pessoa where id = ?";

        try {
            Connection con = Conexao.obterConexao();

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Artista a = new Artista();
                a.setId(rs.getLong("id"));
                a.setNomeArtistico(rs.getString("nome_artistico"));

                PreparedStatement psPessoa = con.prepareStatement(sqlPessoa);
                psPessoa.setLong(1, a.getId());
                ResultSet rsPessoa = psPessoa.executeQuery();
                if (rsPessoa.next()) {
                    a.setNome(rsPessoa.getString("nome"));
                    a.setIdade(rsPessoa.getInt("idade"));
                }

                return Optional.of(a);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return Optional.empty();
    }

    @Override
    public void alterar(Artista objeto) {
        String sqlPessoa = "update pessoa set nome = ?, idade = ? where id = ?";
        String sqlArtista = "update artista set nome_artistico = ? where id = ?";

        try {
            Connection con = Conexao.obterConexao();

            PreparedStatement psPessoa = con.prepareStatement(sqlPessoa);
            psPessoa.setString(1, objeto.getNome());
            psPessoa.setInt(2, objeto.getIdade());
            psPessoa.setLong(3, objeto.getId());
            psPessoa.executeUpdate();

            PreparedStatement psArtista = con.prepareStatement(sqlArtista);
            psArtista.setString(1, objeto.getNomeArtistico());
            psArtista.setLong(2, objeto.getId());
            psArtista.executeUpdate();
            System.out.println("Artista alterado");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void excluir(Artista objeto) {
        String sqlArtista = "delete from artista where id = ?";
        String sqlPessoa = "delete from pessoa where id = ?";

        try {
            Connection con = Conexao.obterConexao();

            PreparedStatement psArtista = con.prepareStatement(sqlArtista);
            psArtista.setLong(1, objeto.getId());
            psArtista.executeUpdate();

            PreparedStatement psPessoa = con.prepareStatement(sqlPessoa);
            psPessoa.setLong(1, objeto.getId());
            psPessoa.executeUpdate();
            System.out.println("Artista removido");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Artista> listarTodos() {
        String sql = "select * from artista";
        String sqlPessoa = "select * from pessoa where id = ? ";
        List<Artista> lista = new ArrayList<Artista>();

        try {
            Connection con = Conexao.obterConexao();

            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Artista a = new Artista();
                a.setId(rs.getLong("id"));
                a.setNomeArtistico(rs.getString("nome_artistico"));

                PreparedStatement psPessoa = con.prepareStatement(sqlPessoa);
                psPessoa.setLong(1, a.getId());
                ResultSet rsPessoa = psPessoa.executeQuery();
                if (rsPessoa.next()) {
                    a.setNome(rsPessoa.getString("nome"));
                    a.setIdade(rsPessoa.getInt("idade"));
                }

                lista.add(a);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return lista;
    }
}