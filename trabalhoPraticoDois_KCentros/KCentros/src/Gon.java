import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Gon {
    private Matriz matriz;
    private int raio = Integer.MAX_VALUE;
    private int numCentros;
    private Set<Integer> centros;

    public Gon(Matriz matriz) {
        this.matriz = matriz;
        this.numCentros = matriz.getNumCentros();
        this.centros = new HashSet<>();
    }

    public int GonAlgo() {
        Random random = new Random();
        int c1 = random.nextInt(matriz.getNumVertices() - 1) + 1;
        centros.add(c1);

        for (int i = 1; i < numCentros; i++) {
            executar();
        }

        calcularRaio();
        System.out.println(raio);
        return raio;
    }

    private void executar() {
        // maior distancia entre um vertice e os centros selecionados
        int maxDist = Integer.MIN_VALUE;
        int novoCentro = -1;
        // percorrer vertices
        for (int vertice = 1; vertice <= matriz.getNumVertices(); vertice++) {
            if (centros.contains(vertice)) {
                continue; // Ignorar vértices já selecionados como centros
            }
            int minDist = Integer.MAX_VALUE;
            // percorrer centros e descobrir a qual cluster o centro pertence
            for (Integer centro : centros) {
                // distancia entre o centro e vertice em questao
                int dist = matriz.matrizCusto[centro][vertice];
                if (dist < minDist) {
                    minDist = dist;
                }
            }
            // se a distancia entre o vertice e o centro associado a ele for maior que MaxDist -> atualizar valores 
            if(minDist > maxDist) {
                maxDist = minDist;
                novoCentro = vertice;
            } 
        }

        centros.add(novoCentro);
    }

    private void calcularRaio() {
        int raioAtual = Integer.MIN_VALUE;
        // percorrer vertices da matriz de custo
        for (int i = 1; i <= matriz.getNumVertices(); i++) {
            int minDist = Integer.MAX_VALUE; 
            // calcular a qual centro o vertice esta ligado, percorrendo os centros
            for (Integer centro : centros) {
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
