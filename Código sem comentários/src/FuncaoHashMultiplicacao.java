public class FuncaoHashMultiplicacao implements FuncaoHash {
    private static final double A = 0.6180339887;

    @Override
    public int hash(int chave, int tamanhoTabela) {
        long k = Integer.toUnsignedLong(chave);
        double parteFracionaria = (k * A) % 1;
        return (int) (tamanhoTabela * parteFracionaria);
    }
}
