import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

//  Classe que registra os resultados do teste em um arquivo CSV

public class EscritorCSV {
    private FileWriter arquivo;

    public EscritorCSV(String nomeArquivo) {
        try {
            arquivo = new FileWriter(nomeArquivo);
            arquivo.write("Metodo,FuncaoHash,TamanhoTabela,TamanhoConjunto,Colisoes,TempoInsercao,TempoBusca,TopCadeias,Gaps\n");
        } catch (IOException e) {
            System.err.println("Erro ao criar arquivo CSV: " + e.getMessage());
        }
    }

    public void escrever(String metodo, String funcao, int tamanhoTabela, int tamanhoConjunto,
                         long colisoes, double tempoInsercao, double tempoBusca,
                         int[] topCadeias, Metricas.EstatisticasGaps gaps) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(metodo).append(",")
                    .append(funcao).append(",")
                    .append(tamanhoTabela).append(",")
                    .append(tamanhoConjunto).append(",")
                    .append(colisoes).append(",")
                    .append(String.format("%.6f", tempoInsercao)).append(",")
                    .append(String.format("%.6f", tempoBusca)).append(",");

            if (topCadeias != null)
                sb.append(Arrays.toString(topCadeias));
            sb.append(",");

            if (gaps != null)
                sb.append(String.format("min=%d,max=%d,media=%.2f", gaps.menor, gaps.maior, gaps.media));

            sb.append("\n");
            arquivo.write(sb.toString());
            arquivo.flush();
        } catch (IOException e) {
            System.err.println("Erro ao escrever no CSV: " + e.getMessage());
        }
    }

    public void fechar() {
        try {
            arquivo.close();
        } catch (IOException e) {
            System.err.println("Erro ao fechar CSV: " + e.getMessage());
        }
    }
}
