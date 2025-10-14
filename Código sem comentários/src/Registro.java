
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

        return String.format("%09d", codigo);
    }
}
