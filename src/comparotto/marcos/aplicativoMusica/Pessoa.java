package comparotto.marcos.aplicativoMusica;

public abstract class Pessoa {

    private long id;
    private String nome;
    private int idade;

    public Pessoa() {
    }

    public Pessoa(long id, String nome, int idade) {
        this.id = id;
        this.nome = nome;
        this.idade = idade;
    }

    public Pessoa(String nome, int idade) {
        this.nome = nome;
        this.idade = idade;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public void exibir() {
        System.out.println("id: " + id + " nome: " + nome + " idade: " + idade);
    }
}