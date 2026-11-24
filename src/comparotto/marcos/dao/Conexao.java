package comparotto.marcos.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

    private static final String URL = "jdbc:postgresql://localhost:5432/aplicativomusica";
    private static final String USUARIO = "";
    private static final String SENHA = ""; // o projeto vai para um repositorio publico no github, por isso que esta sem usuario e senha
                                            // e o banco fica em um computador pessoal
    public static Connection obterConexao() {
        try {
            return DriverManager.getConnection(URL, USUARIO, SENHA);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}