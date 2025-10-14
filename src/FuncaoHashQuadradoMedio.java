// Implementa a função de hash por Quadrado Médio
// (Eleva a chave ao quadrado e extrai os dígitos centrais)

public class FuncaoHashQuadradoMedio implements FuncaoHash {
    @Override
    public int hash(int chave, int tamanhoTabela) {
        long chaveLong = chave;
        if (chaveLong < 0) chaveLong = -chaveLong; // evita chaves negativas

        long quadrado = chaveLong * chaveLong;
        String s = String.valueOf(quadrado);
        int meio = s.length() / 2;

        int inicio = meio - 2;
        int fim = meio + 2;

        if (inicio < 0) inicio = 0;
        if (fim > s.length()) fim = s.length();

        String central = s.substring(inicio, fim);
        int valorCentral = Integer.parseInt(central);

        if (valorCentral < 0) valorCentral = -valorCentral; // segurança extra
        return valorCentral % tamanhoTabela;
    }
}
