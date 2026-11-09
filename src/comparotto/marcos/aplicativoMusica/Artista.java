package comparotto.marcos.aplicativoMusica;

public class Artista extends Pessoa {
    private String nomeArtistico;

    public Artista(long id, String nome, int idade, String nomeArtistico) {
        super(id, nome, idade);
        this.nomeArtistico = nomeArtistico;
    }

    public Artista(String nome, int idade, String nomeArtistico) {
        super(nome, idade);
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
        System.out.println("--- Artista ---");
        System.out.println("ID: " + getId() + " | Nome Civil: " + getNome() + " | Nome Artístico: " + this.nomeArtistico + " | Idade: " + getIdade());
    }
}
