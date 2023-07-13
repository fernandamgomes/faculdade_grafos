// source: https://www.geeksforgeeks.org/find-edge-disjoint-paths-two-vertices/
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

public class App {
    static int V; // num vertices do grafo

    // Método para buscar um caminho aumentante usando BFS
    static boolean bfs(int rGraph[][], int s, int t, int parent[]) {
        boolean visited[] = new boolean[V];
        Queue<Integer> q = new LinkedList<>();
        q.add(s);
        visited[s] = true;
        parent[s] = -1;

        while (!q.isEmpty()) {
            int u = q.poll();

            for (int v = 0; v < V; v++) {
                if (!visited[v] && rGraph[u][v] > 0) {
                    q.add(v);
                    visited[v] = true;
                    parent[v] = u;
                }
            }
        }

        return visited[t];
    }

    // Método para encontrar os caminhos disjuntos
    static List<List<Integer>> findDisjointPaths(int graph[][], int s, int t) {
        int rGraph[][] = new int[V][V];
        for (int u = 0; u < V; u++)
            for (int v = 0; v < V; v++)
                rGraph[u][v] = graph[u][v];

        int parent[] = new int[V];
        List<List<Integer>> paths = new ArrayList<>();

        while (bfs(rGraph, s, t, parent)) {
            List<Integer> path = new ArrayList<>();
            int pathFlow = Integer.MAX_VALUE;

            // Construir um caminho usando os pais encontrados pelo BFS
            for (int v = t; v != s; v = parent[v]) {
                int u = parent[v];
                pathFlow = Math.min(pathFlow, rGraph[u][v]);
                path.add(0, v);
            }
            path.add(0, s);
            paths.add(path);

            // Reduzir as capacidades residuais ao longo do caminho e
            // atualizar as capacidades reversas correspondentes
            for (int v = t; v != s; v = parent[v]) {
                int u = parent[v];
                rGraph[u][v] -= pathFlow;
                rGraph[v][u] += pathFlow;
            }
        }

        return paths;
    }

    public static void main(String[] args) {
        //VARIAVEIS
        Scanner sc = new Scanner(System.in);
        String nomeArquivo = "";
        int source = -1;
        int target = -1;

        // input: nome do arquivo
        System.out.println("Qual o nome do arquivo de onde deseja ler as informacoes? (sem o .txt)");
        nomeArquivo = sc.nextLine();
        while (nomeArquivo.isBlank()) {
            System.out.println("Digite um nome valido");
            nomeArquivo = sc.nextLine();
        }
        nomeArquivo = nomeArquivo.concat(".txt");
        sc.close();

        try {
             // Marcação de tempo antes da execução do algoritmo
            long startTime = System.currentTimeMillis();
            File file = new File(nomeArquivo);
            Scanner scanner = new Scanner(file);

            // ler origem e destino
            source = scanner.nextInt();
            target = scanner.nextInt();

            // ler numero de vertices e arestas
            V = scanner.nextInt();
            int E = scanner.nextInt();            

            int graph[][] = new int[V][V];
            // ler arestas no arquivo fornecido
            for (int i = 0; i < E; i++) {
                int u = scanner.nextInt();
                int v = scanner.nextInt();
                graph[u][v] = 1;
            }

            scanner.close();
            // encontrar caminhos disjuntos
            List<List<Integer>> paths = findDisjointPaths(graph, source, target);

            // Marcação de tempo após a execução do algoritmo
            long endTime = System.currentTimeMillis();

            // imprimir resultados
            System.out.println("Número máximo de caminhos disjuntos de " + source + " para " + target + ": " + paths.size());
            System.out.println("Caminhos encontrados:");
            for (List<Integer> path : paths) {
                System.out.println(path);
            }

            // Cálculo do tempo de execução
            long executionTime = endTime - startTime;
            System.out.println("Tempo de execução: " + executionTime + "ms");
            } catch (FileNotFoundException e) {
            e.printStackTrace();
            }
    }
}
