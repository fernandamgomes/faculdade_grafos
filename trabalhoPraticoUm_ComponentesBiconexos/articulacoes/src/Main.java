import java.util.ArrayList;
import java.util.Random;

public class Main {

    public static void main(String[] args) {
        //2,5,10,20
        int n = 4000; // número de vértices
        int m = n*2; // número de arestas
        
        Grafo grafo = new Grafo();
        
        long startTime = System.currentTimeMillis();
        // criando vértices ordenados
        for (int i = 1; i <= n; i++) {
            grafo.adjacencia.put(i, new ArrayList<>());
        }
        
        // criando m arestas aleatórias
        Random rand = new Random();
        int count = 0;
        while (count < m) {
            int origem = rand.nextInt(n) + 1; // escolhe um vértice aleatório
            int destino = rand.nextInt(n) + 1; // escolhe outro vértice aleatório
            if (origem != destino && !grafo.adjacencia.get(origem).contains(destino)) {
                // adiciona aresta se os vértices forem diferentes e a aresta ainda não existir
                grafo.adicionarAresta(origem, destino);
                count++;
            }
        }
        
        // medir o tempo de execução
        

        grafo.encontraComponentesBiconexos();

        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        System.out.println("Tempo total de execução: " + totalTime + " ms");
    }

}
