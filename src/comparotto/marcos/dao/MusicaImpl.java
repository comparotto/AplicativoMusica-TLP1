package comparotto.marcos.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import comparotto.marcos.aplicativoMusica.Musica;

public class MusicaImpl implements MusicaDao {

    @Override
    public void inserir(Musica objeto) {
        String sql = "insert into musica (nome_musica, data_lancamento, tempo_min, genero_musical) values (?, ?, ?, ?) returning id";

        try {
            Connection con = Conexao.obterConexao();

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, objeto.getNomeMusica());
            if (objeto.getDataLancamento() != null) {
                ps.setDate(2, new Date(objeto.getDataLancamento().getTime()));
            } else {
                ps.setNull(2, Types.DATE);
            }
            ps.setFloat(3, objeto.getTempoMin());
            ps.setString(4, objeto.getGeneroMusical());
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                objeto.setId(rs.getLong(1));
            }
            System.out.println("Musica salva");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Optional<Musica> buscarPorID(long id) {
        String sql = "select * from musica where id = ?";

        try {
            Connection con = Conexao.obterConexao();

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Musica m = new Musica();
                m.setId(rs.getLong("id"));
                m.setNomeMusica(rs.getString("nome_musica"));
                m.setDataLancamento(rs.getDate("data_lancamento"));
                m.setTempoMin(rs.getFloat("tempo_min"));
                m.setGeneroMusical(rs.getString("genero_musical"));
                return Optional.of(m);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return Optional.empty();
    }

    @Override
    public void alterar(Musica objeto) {
        String sql = "update musica set nome_musica = ?, data_lancamento = ?, tempo_min = ?, genero_musical = ? where id = ?";

        try {
            Connection con = Conexao.obterConexao();

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, objeto.getNomeMusica());
            if (objeto.getDataLancamento() != null) {
                ps.setDate(2, new Date(objeto.getDataLancamento().getTime()));
            } else {
                ps.setNull(2, Types.DATE);
            }
            ps.setFloat(3, objeto.getTempoMin());
            ps.setString(4, objeto.getGeneroMusical());
            ps.setLong(5, objeto.getId());
            ps.executeUpdate();
            System.out.println("Musica alterada");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void excluir(Musica objeto) {
        String sql = "delete from musica where id = ?";

        try {
            Connection con = Conexao.obterConexao();

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, objeto.getId());
            ps.executeUpdate();
            System.out.println("Musica removida");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Musica> listarTodos() {
        String sql = "select * from musica";
        List<Musica> lista = new ArrayList<Musica>();

        try {
            Connection con = Conexao.obterConexao();

            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Musica m = new Musica();
                m.setId(rs.getLong("id"));
                m.setNomeMusica(rs.getString("nome_musica"));
                m.setDataLancamento(rs.getDate("data_lancamento"));
                m.setTempoMin(rs.getFloat("tempo_min"));
                m.setGeneroMusical(rs.getString("genero_musical"));
                lista.add(m);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return lista;
    }
}