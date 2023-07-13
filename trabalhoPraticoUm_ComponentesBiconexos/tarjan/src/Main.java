import java.util.Random;

public class Main {

    public static void main(String[] args) {

        
        int numVertices = 4000;
        int numArestas = numVertices * 5;
        Grafo grafo = new Grafo(numVertices, numArestas);
        long startTime = System.currentTimeMillis();
        
        Random random = new Random();
        for (int i = 0; i < numArestas; i++) {
            int u = random.nextInt(numVertices) + 1;
            int v = random.nextInt(numVertices) + 1;
            grafo.adicionarAresta(u, v);
        }



/* grafo.adicionarAresta(1, 2);
        grafo.adicionarAresta(1, 3);
        grafo.adicionarAresta(2, 3);
        grafo.adicionarAresta(2, 4);
        grafo.adicionarAresta(3, 5);
        grafo.adicionarAresta(3, 6);
        grafo.adicionarAresta(4, 7);
        grafo.adicionarAresta(4, 8);
        grafo.adicionarAresta(5, 6);
        grafo.adicionarAresta(5, 9);
        grafo.adicionarAresta(6, 9);
        grafo.adicionarAresta(7, 8);
        grafo.adicionarAresta(7, 10);
        grafo.adicionarAresta(8, 11);
        grafo.adicionarAresta(10, 11);*/

        //grafo.imprimeComponentesBiconexosRecur();
        System.out.println("-----------------");
        grafo.imprimeComponentesBiconexos();
        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        System.out.println("Tempo total de execução: " + totalTime + " ms");
    }
}