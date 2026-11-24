package comparotto.marcos.aplicativoMusica;

public class Artista extends Pessoa {

    private String nomeArtistico;

    public Artista() {
    }

    public Artista(String nome, int idade, String nomeArtistico) {
        super(nome, idade);
        this.nomeArtistico = nomeArtistico;
    }

    public Artista(long id, int idade, String nome, String nomeArtistico) {
        super(id, nome, idade);
        this.nomeArtistico = nomeArtistico;
    }

    public String getNomeArtistico() {
        return nomeArtistico;
    }

    public void setNomeArtistico(String nomeArtistico) {
        this.nomeArtistico = nomeArtistico;
    }

    @Override
    public void exibir() {
        System.out.println("id: " + getId() + " nome: " + getNome() + " nome_artistico: " + nomeArtistico + "idade: " + getIdade());
    }
}