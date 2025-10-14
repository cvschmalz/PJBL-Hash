import java.util.Random;

// Programa principal de testes para tabelas hash com encadeamento e endereçamento aberto
// - Mede o tempo de inserção, tempo de busca, número de colisões, tamanhos das maiores cadeias e estatísticas de gaps
// - Todos os resultados são exportados para um arquivo CSV

public class Main {
    public static void main(String[] args) {

        // Tamanhos de tabela que serão testadas
        int[] tamanhosTabela = {10000, 100000, 1000000};

        // Tamanhos dos conjuntos de dados
        int[] tamanhosConjunto = {10000, 50000, 100000};

        long seed = 12345; // seed fixa para gerar os mesmos dados em cada execução
        Random random = new Random(seed);
        EscritorCSV csv = new EscritorCSV("resultados.csv");

        for (int tamanhoTabela : tamanhosTabela) {
            System.out.println("\n=== TAMANHO DA TABELA: " + tamanhoTabela + " ===");

            for (int qtdDados : tamanhosConjunto) {
                System.out.println("\n--- CONJUNTO DE DADOS: " + qtdDados + " ---");

                // Funções hash que serão comparadas
                FuncaoHash[] funcoes = {
                        new FuncaoHashDobramento(),
                        new FuncaoHashMultiplicacao(),
                        new FuncaoHashQuadradoMedio()
                };

                String[] nomes = {"Dobramento", "Multiplicação", "QuadradoMédio"};

                for (int f = 0; f < funcoes.length; f++) {
                    random.setSeed(seed);

                    // Gera o conjunto de chaves aleatórias
                    int[] chaves = new int[qtdDados];
                    for (int i = 0; i < qtdDados; i++) {
                        chaves[i] = random.nextInt(Integer.MAX_VALUE);
                    }

                    /* =======================================================
                       TESTE 1: TABELA HASH COM ENCADEAMENTO
                       ======================================================= */
                    TabelaHashEncadeada encadeada = new TabelaHashEncadeada(tamanhoTabela, funcoes[f]);

                    // Tempo de inserção
                    long inicio = System.nanoTime();
                    for (int chave : chaves) encadeada.inserir(new Registro(chave));
                    long duracao = System.nanoTime() - inicio;
                    double tempoInsercao = duracao / 1e9;

                    // Tempo de busca
                    inicio = System.nanoTime();
                    for (int chave : chaves) encadeada.buscar(chave);
                    double tempoBusca = (System.nanoTime() - inicio) / 1e9;

                    // 3 maiores cadeias
                    int[] maioresCadeias = Metricas.maioresCadeias(encadeada.getTabela(), 3);

                    System.out.printf("Encadeamento (%s): %d colisões, %.3fs inserção, %.3fs busca, maiores cadeias=%d,%d,%d\n",
                            nomes[f], encadeada.getColisoes(), tempoInsercao, tempoBusca,
                            maioresCadeias[0], maioresCadeias[1], maioresCadeias[2]);

                    csv.escrever("Encadeamento", nomes[f], tamanhoTabela, qtdDados,
                            encadeada.getColisoes(), tempoInsercao, tempoBusca, maioresCadeias, null);

                    /* =======================================================
                       TESTE 2: TABELA HASH COM ENDEREÇAMENTO ABERTO
                       ======================================================= */
                    TabelaHashAberta aberta = new TabelaHashAberta(tamanhoTabela, funcoes[f], new FuncaoHashMultiplicacao());

                    // Tempo de inserção
                    inicio = System.nanoTime();
                    for (int chave : chaves) aberta.inserir(new Registro(chave));
                    duracao = System.nanoTime() - inicio;
                    tempoInsercao = duracao / 1e9;

                    // Tempo de busca
                    inicio = System.nanoTime();
                    for (int chave : chaves) aberta.buscar(chave);
                    tempoBusca = (System.nanoTime() - inicio) / 1e9;

                    // Estatísticas de gaps
                    Metricas.EstatisticasGaps estatisticas = Metricas.medidasGaps(aberta.getTabela());

                    System.out.printf("EndereçamentoAberto (%s): %d colisões, %.3fs inserção, %.3fs busca, gaps[min=%d, max=%d, média=%.2f]\n",
                            nomes[f], aberta.getColisoes(), tempoInsercao, tempoBusca,
                            estatisticas.menor, estatisticas.maior, estatisticas.media);

                    csv.escrever("EndereçamentoAberto", nomes[f], tamanhoTabela, qtdDados,
                            aberta.getColisoes(), tempoInsercao, tempoBusca, null, estatisticas);
                }
            }
        }

        csv.fechar();
        System.out.println("\nResultados exportados para resultados.csv");
    }
}
