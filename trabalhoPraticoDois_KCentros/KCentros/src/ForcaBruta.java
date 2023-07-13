public class ForcaBruta {
    private Matriz matriz;
    private int raio = Integer.MAX_VALUE;
    private static int numCombinacoes = 0;

    public ForcaBruta(Matriz matriz) {
        this.matriz = matriz;
    }

    public void gerarCombinacoes() {
        int escolher = matriz.getNumCentros();
        int totalElementos = matriz.getNumVertices();
        int[] combinacaoAtual = new int[escolher];
        gerarCombinacoesRecursivamente(1, totalElementos, 0, combinacaoAtual, 0);
        System.out.println(numCombinacoes);
        System.out.println(raio);
    }

    private void gerarCombinacoesRecursivamente(int inicio, int fim, int indiceAtual, int[] combinacaoAtual,
                                                int indiceCombinacao) {
        if (indiceCombinacao == combinacaoAtual.length) {
            calcularRaio(combinacaoAtual);
            numCombinacoes++;
            return;
        }

        if (indiceAtual == fim - combinacaoAtual.length + indiceCombinacao + 1) {
            return;
        }

        combinacaoAtual[indiceCombinacao] = indiceAtual;
        gerarCombinacoesRecursivamente(inicio, fim, indiceAtual + 1, combinacaoAtual, indiceCombinacao + 1);
        gerarCombinacoesRecursivamente(inicio, fim, indiceAtual + 1, combinacaoAtual, indiceCombinacao);
    }

    private void calcularRaio(int[] combinacaoAtual) {
        int raioAtual = Integer.MIN_VALUE;
        // percorrer vertices da matriz de custo
        for (int i = 1; i <= matriz.getNumVertices(); i++) {
            int minDist = Integer.MAX_VALUE; 
            // calcular a qual centro o vertice esta ligado, percorrendo os centros
            for (int j = 0; j < combinacaoAtual.length; j++) {
                int centro = combinacaoAtual[j];
                // se a distancia do vertice para aquele centro for menor do que a definida previamente, atualizar
                minDist = Math.min(minDist, matriz.matrizCusto[i][centro]); 
            }
            // se a distancia definida entre o vertice e seu centro e maior que o raio atual (da combinacao), atualizar valor do raio atual
            raioAtual = Math.max(raioAtual, minDist); 
        }
        // se o raio da combinacao (atual) e menor do que o raio da solucao, atualizar valor da solucao
        raio = Math.min(raio, raioAtual);
    }
}
