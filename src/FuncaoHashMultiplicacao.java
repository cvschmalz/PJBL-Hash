// Implementa a função de hash por Multiplicação
// (Usa a fração do número áureo (A) para melhor dispersão)

public class FuncaoHashMultiplicacao implements FuncaoHash {
    private static final double A = 0.6180339887; // fração do número áureo

    @Override
    public int hash(int chave, int tamanhoTabela) {
        long k = Integer.toUnsignedLong(chave);
        double parteFracionaria = (k * A) % 1;
        return (int) (tamanhoTabela * parteFracionaria);
    }
}
