package comparotto.marcos.dao;

import java.util.List;
import java.util.Optional;

public interface Dao<T> {
    void inserir(T objeto);
    Optional<T> buscarPorID(long id);
    void alterar(T objeto);
    void excluir(T objeto);
    List<T> listarTodos();
}
