import java.util.Random;

public class Main {
    public static void main(String[] args) {

        int[] tamanhosTabela = {10000, 100000, 1000000};

        int[] tamanhosConjunto = {10000, 50000, 100000};

        long seed = 12345;
        Random random = new Random(seed);
        EscritorCSV csv = new EscritorCSV("resultados.csv");

        for (int tamanhoTabela : tamanhosTabela) {
            System.out.println("\n=== TAMANHO DA TABELA: " + tamanhoTabela + " ===");

            for (int qtdDados : tamanhosConjunto) {
                System.out.println("\n--- CONJUNTO DE DADOS: " + qtdDados + " ---");

                FuncaoHash[] funcoes = {
                        new FuncaoHashDobramento(),
                        new FuncaoHashMultiplicacao(),
                        new FuncaoHashQuadradoMedio()
                };

                String[] nomes = {"Dobramento", "Multiplicação", "QuadradoMédio"};

                for (int f = 0; f < funcoes.length; f++) {
                    random.setSeed(seed);

                    int[] chaves = new int[qtdDados];
                    for (int i = 0; i < qtdDados; i++) {
                        chaves[i] = random.nextInt(Integer.MAX_VALUE);
                    }


                    TabelaHashEncadeada encadeada = new TabelaHashEncadeada(tamanhoTabela, funcoes[f]);


                    long inicio = System.nanoTime();
                    for (int chave : chaves) encadeada.inserir(new Registro(chave));
                    long duracao = System.nanoTime() - inicio;
                    double tempoInsercao = duracao / 1e9;


                    inicio = System.nanoTime();
                    for (int chave : chaves) encadeada.buscar(chave);
                    double tempoBusca = (System.nanoTime() - inicio) / 1e9;


                    int[] maioresCadeias = Metricas.maioresCadeias(encadeada.getTabela(), 3);

                    System.out.printf("Encadeamento (%s): %d colisões, %.3fs inserção, %.3fs busca, maiores cadeias=%d,%d,%d\n",
                            nomes[f], encadeada.getColisoes(), tempoInsercao, tempoBusca,
                            maioresCadeias[0], maioresCadeias[1], maioresCadeias[2]);

                    csv.escrever("Encadeamento", nomes[f], tamanhoTabela, qtdDados,
                            encadeada.getColisoes(), tempoInsercao, tempoBusca, maioresCadeias, null);


                    TabelaHashAberta aberta = new TabelaHashAberta(tamanhoTabela, funcoes[f], new FuncaoHashMultiplicacao());


                    inicio = System.nanoTime();
                    for (int chave : chaves) aberta.inserir(new Registro(chave));
                    duracao = System.nanoTime() - inicio;
                    tempoInsercao = duracao / 1e9;


                    inicio = System.nanoTime();
                    for (int chave : chaves) aberta.buscar(chave);
                    tempoBusca = (System.nanoTime() - inicio) / 1e9;


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
