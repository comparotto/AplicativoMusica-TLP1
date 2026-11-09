package comparotto.marcos.aplicativoMusica;

import java.util.Date;

public class Usuario extends Pessoa {
    private String email;
    private Date dataCadastro;

    public Usuario(long id, String nome, int idade, String email, Date dataCadastro) {
        super(id, nome, idade);
        this.email = email;
        this.dataCadastro = dataCadastro;
    }

    public Usuario(String nome, int idade, String email, Date dataCadastro) {
        super(nome, idade);
        this.email = email;
        this.dataCadastro = dataCadastro;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    @Override
    public void exibir() {
        System.out.println("--- Usuário ---");
        System.out.println("ID: " + getId() + " | Nome: " + getNome() + " | E-mail: " + this.email + " | Cadastro: " + this.dataCadastro);
    }
}
