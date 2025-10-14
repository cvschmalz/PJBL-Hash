public class TabelaHashEncadeada {

    public static class Node {
        Registro registro;
        Node proximo;

        Node(Registro data) {
            this.registro = data;
            this.proximo = null;
        }
    }

    private Node[] tabela;
    private FuncaoHash funcaoHash;
    private int colisoes = 0;

    public TabelaHashEncadeada(int tamanho, FuncaoHash funcaoHash) {
        this.tabela = new Node[tamanho];
        this.funcaoHash = funcaoHash;
    }


    public void inserir(Registro r) {
        int idx = funcaoHash.hash(r.getCodigo(), tabela.length);
        if (tabela[idx] != null) colisoes++;
        Node newNode = new Node(r);
        newNode.proximo = tabela[idx];
        tabela[idx] = newNode;
    }



    public boolean buscar(int key) {
        int idx = funcaoHash.hash(key, tabela.length);
        Node cur = tabela[idx];
        while (cur != null) {
            if (cur.registro.getCodigo() == key) return true;
            cur = cur.proximo;
        }
        return false;
    }

    public int getColisoes() {
        return colisoes;
    }

    public Node[] getTabela() {
        return tabela;
    }

}
