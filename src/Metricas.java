// Classe que calcula métricas de tempo e distribuição das tabelas hash,
// tanto para encadeamento (usando Node) quanto para endereçamento aberto
public class Metricas {

    // Retorna as N maiores cadeias da tabela (por tamanho decrescente)
    public static int[] maioresCadeias(TabelaHashEncadeada.Node[] tabela, int n) {
        int[] maiores = new int[n];

        // Roda para cada entrada da tabela
        for (int i = 0; i < tabela.length; i++) {
            int tamanho = 0;
            // Começa no primeiro node naquela posição e busca o próximo até chegar no final da lista encadeada
            TabelaHashEncadeada.Node atual = tabela[i];
            while (atual != null) {
                tamanho++;
                atual = atual.proximo;
            }

            // Insere o valor na lista de maiores, se for grande o suficiente
            for (int j = 0; j < n; j++) {
                if (tamanho > maiores[j]) {
                    // Move os valores menores uma posição para trás
                    for (int k = n - 1; k > j; k--) {
                        maiores[k] = maiores[k - 1];
                    }
                    maiores[j] = tamanho;
                    break;
                }
            }
        }
        return maiores;
    }

    // Classe auxiliar para armazenar estatísticas de gaps
    public static class EstatisticasGaps {
        public int menor;
        public int maior;
        public double media;

        public EstatisticasGaps(int menor, int maior, double media) {
            this.menor = menor;
            this.maior = maior;
            this.media = media;
        }
    }

    // Mede gaps em uma tabela de endereçamento aberto (menor, maior e média)
    public static EstatisticasGaps medidasGaps(Registro[] tabela) {
        int menor = Integer.MAX_VALUE;
        int maior = 0;
        int soma = 0;
        int contagem = 0;
        int atual = 0;

        for (int i = 0; i < tabela.length; i++) {
            Registro r = tabela[i];

            if (r == null) {
                atual++;
            } else if (atual > 0) {
                if (atual < menor) menor = atual;
                if (atual > maior) maior = atual;
                soma += atual;
                contagem++;
                atual = 0;
            }
        }

        // Caso termine com gap no final da tabela
        if (atual > 0) {
            if (atual < menor) menor = atual;
            if (atual > maior) maior = atual;
            soma += atual;
            contagem++;
        }

        double media = 0.0;
        if (contagem != 0) {
            media = (double) soma / contagem;
        }

        if (menor == Integer.MAX_VALUE) menor = 0;

        return new EstatisticasGaps(menor, maior, media);
    }
}
