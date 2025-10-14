// Implementa a função de hash por Dobramento (Folding)
// (Divide a chave em partes e soma os grupos de três dígitos)

public class FuncaoHashDobramento implements FuncaoHash {
    @Override
    public int hash(int chave, int tamanhoTabela) {
        if (chave < 0) chave = -chave; // substitui Math.abs
        String s = String.format("%09d", chave); // completa com zeros
        int soma = 0;

        for (int i = 0; i < s.length(); i += 3) {
            int fim = i + 3;
            if (fim > s.length()) fim = s.length();
            int parte = Integer.parseInt(s.substring(i, fim));
            soma += parte;
        }

        return soma % tamanhoTabela;
    }
}
