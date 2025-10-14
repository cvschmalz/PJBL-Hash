
public class Metricas {

    public static int[] maioresCadeias(TabelaHashEncadeada.Node[] tabela, int n) {
        int[] maiores = new int[n];


        for (int i = 0; i < tabela.length; i++) {
            int tamanho = 0;

            TabelaHashEncadeada.Node atual = tabela[i];
            while (atual != null) {
                tamanho++;
                atual = atual.proximo;
            }


            for (int j = 0; j < n; j++) {
                if (tamanho > maiores[j]) {

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
