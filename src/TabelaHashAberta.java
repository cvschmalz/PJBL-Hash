// Implementa uma tabela hash utilizando endereçamento aberto
// Usa hashing duplo para resolver colisões

public class TabelaHashAberta {
    private Registro[] tabela;
    private FuncaoHash h1, h2;
    private int tamanho;
    private long colisoes = 0;
    private int elementos = 0;

    public TabelaHashAberta(int tamanho, FuncaoHash h1, FuncaoHash h2) {
        this.tamanho = tamanho;
        this.tabela = new Registro[tamanho];
        this.h1 = h1;
        this.h2 = h2;
    }

    // Insere um registro
    public void inserir(Registro r) {
        int chave = r.getCodigo();
        int indice1 = h1.hash(chave, tamanho);
        if (indice1 < 0) indice1 = -indice1;
        indice1 %= tamanho;

        int passo = h2.hash(chave, tamanho);
        if (passo < 0) passo = -passo;
        passo = 1 + (passo % (tamanho - 1));

        for (int i = 0; i < tamanho; i++) {
            int indice = (indice1 + i * passo) % tamanho;
            if (indice < 0) indice += tamanho;

            if (tabela[indice] == null) {
                tabela[indice] = r;
                elementos++;
                return;
            }
            colisoes++;
        }
    }

    // Busca um registro pelo código
    public boolean buscar(int chave) {
        int indice1 = h1.hash(chave, tamanho);
        if (indice1 < 0) indice1 = -indice1;
        indice1 %= tamanho;

        int passo = h2.hash(chave, tamanho);
        if (passo < 0) passo = -passo;
        passo = 1 + (passo % (tamanho - 1));

        for (int i = 0; i < tamanho; i++) {
            int indice = (indice1 + i * passo) % tamanho;
            if (indice < 0) indice += tamanho;

            if (tabela[indice] == null) return false;
            if (tabela[indice].getCodigo() == chave) return true;
        }
        return false;
    }

    public long getColisoes() {
        return colisoes;
    }

    public Registro[] getTabela() {
        return tabela;
    }
}
