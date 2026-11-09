package comparotto.marcos.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    private static final String URL = "jdbc:postgresql://localhost:5432/AplicativoMusica";
    private static final String USUARIO = "postgres";
    private static final String SENHA = "sla"; // Ao professor: está sem a senha porque eu jogo o codigo pro meu github em um repositorio publico
    private Conexao() {}

    public static Connection obterConexao() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, SENHA);
    }
}
