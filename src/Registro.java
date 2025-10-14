// Classe que representa um registro armazenado na tabela hash.
// Cada registro contém apenas um código numérico.

public class Registro {
    private final int codigo;

    public Registro(int codigo) {
        this.codigo = codigo;
    }

    public int getCodigo() {
        return codigo;
    }

    @Override
    public String toString() {
        // Retorna o código com zeros à esquerda (9 dígitos)
        return String.format("%09d", codigo);
    }
}
