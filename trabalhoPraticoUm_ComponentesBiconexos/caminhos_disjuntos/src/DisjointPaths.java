import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class DisjointPaths {
    static int V; // número de vértices do grafo
    static List<Integer>[] adjList; // lista de adjacência do grafo

    // encontra um caminho aumentante usando DFS
    static boolean findAugmentingPath(int[][] residualGraph, int s, int t, int[] parent) {
        boolean[] visited = new boolean[V];
        visited[s] = true;
        parent[s] = -1;

        Stack<Integer> stack = new Stack<>();
        stack.push(s);

        while (!stack.isEmpty()) {
            int u = stack.pop();

            for (int v = 0; v < V; v++) {
                if (!visited[v] && residualGraph[u][v] > 0) {
                    visited[v] = true;
                    parent[v] = u;
                    stack.push(v);

                    if (v == t) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    // encontra todos os caminhos internamente disjuntos
    static ArrayList<ArrayList<Integer>> findDisjointPaths(int s, int t) {

        List<Integer> vertexList = new ArrayList<>();

        int[][] residualGraph = new int[V][V];
        for (int u = 0; u < V; u++) {
            for (int v : adjList[u]) {
                residualGraph[u][v] = 1;
            }
        }

        int[] parent = new int[V];
        ArrayList<ArrayList<Integer>> disjointPaths = new ArrayList<>();

        while (findAugmentingPath(residualGraph, s, t, parent)) {
            ArrayList<Integer> path = new ArrayList<>();
            int v = t;

            while (v != -1) {
                if (!vertexList.contains(v))
                    vertexList.add(v);
                else {
                    if ((v != s) && (v != t)) {
                        disjointPaths.clear();
                        return disjointPaths;
                    }

                }

                path.add(v);
                int u = parent[v];
                residualGraph[u][v] = 0;
                residualGraph[v][u] = 1;
                v = u;
                if (v == s) {
                    break;
                }
            }

            Collections.reverse(path);
            path.add(0, s);

            disjointPaths.add(path);
        }
        return disjointPaths;
    }

    static void findBlock(List<List<Integer>> paths) {
        List<Set<Integer>> sets = new ArrayList<>();
        Set<Integer> curr_set = new HashSet<>();

        // Divide a lista de caminhos em conjuntos distintos
        for (int i = 0; i < paths.size(); i++) {
            List<Integer> path = paths.get(i);
            if (i > 0 && !path.get(path.size() - 1).equals(paths.get(i - 1).get(paths.get(i - 1).size() - 1))) {
                sets.add(curr_set);
                curr_set = new HashSet<>();
            }
            curr_set.addAll(new HashSet<>(path));
        }
        sets.add(curr_set);

        // Verifica se os conjuntos têm mais de um valor em comum e os une
        for (int i = 0; i < sets.size(); i++) {
            for (int j = i + 1; j < sets.size(); j++) {
                Set<Integer> intersection = new HashSet<>(sets.get(i));
                intersection.retainAll(sets.get(j));
                if (intersection.size() > 1) {
                    sets.get(i).addAll(sets.get(j));
                    sets.set(j, new HashSet<>());
                }
            }
        }

        // Remove conjuntos vazios e duplicatas
        sets.removeIf(s -> s.isEmpty());
        Set<List<Integer>> uniqueSets = new HashSet<>(
                sets.stream().map(s -> new ArrayList<>(s)).collect(Collectors.toList()));
        sets = uniqueSets.stream().map(s -> new HashSet<>(s)).collect(Collectors.toList());

        // Imprime a lista de conjuntos
        List<List<Integer>> output = new ArrayList<>(
                sets.stream().map(s -> new ArrayList<>(s)).collect(Collectors.toList()));
        System.out.println(output);
    }

    public static void readTxt() {

        String filename = "graph-test-ex-tp01.txt";

        String vertexEdges = "";

        BufferedReader reader;

        int lineNumber = 0;

        // lista com lista de arestas
        List<List<Integer>> edges = new ArrayList<>();

        // lista arestas (from -> to) (string)
        List<String> edgesListStr = new ArrayList<String>();

        // lista arestas (from -> to) (int)
        List<Integer> edgesList = new ArrayList<Integer>();

        try {

            reader = new BufferedReader(new FileReader(filename));

            String line = reader.readLine().replaceAll("\\s+", " ");

            while (line != null) {
                if (lineNumber == 0) {
                    vertexEdges = line;
                    lineNumber += 1;
                }

                line = reader.readLine();
                if (line != null) {
                    line = line.replaceAll("\\s+", " ");

                    edgesListStr = List.of(line.strip().split(" "));
                    for (String s : edgesListStr)
                        edgesList.add(Integer.valueOf(s));

                    edges.add(new ArrayList<Integer>(edgesList));
                    edgesList.clear();
                }
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // lista com número de Vertices e número Arestas (string)
        List<String> vertexEdgesListStr = new ArrayList<String>(List.of(vertexEdges.split(" ")));

        // lista com número de Vertices e número Arestas (int)
        List<Integer> vertexEdgesList = new ArrayList<Integer>();
        for (String s : vertexEdgesListStr)
            vertexEdgesList.add(Integer.valueOf(s));

        // lista de adjacência do grafo
        V = vertexEdgesList.get(0);
        adjList = new ArrayList[V];

        for (int u = 0; u < V; u++) {
            adjList[u] = new ArrayList<>();
        }

        // add aresta
        for (ListIterator<List<Integer>> iter = edges.listIterator(); iter.hasNext();) {
            List<Integer> edge = iter.next();
            adjList[edge.get(0) - 1].add(edge.get(1) - 1);
            adjList[edge.get(1) - 1].add(edge.get(0) - 1);
        }

        List<List<Integer>> paths = new ArrayList<>();

        for (int u = 0; u < V - 1; u++) {
            for (int v = u + 1; v < V; v++) {

                // for (int u = 0; u < V; u++) {
                // for (int v = 0; v < V; v++) {
                ArrayList<ArrayList<Integer>> disjointPaths = findDisjointPaths(u, v);

                if (disjointPaths.size() > 1) {
                    paths.addAll(disjointPaths);
                }

                // pontes
                else if (disjointPaths.size() == 1) {
                    if (disjointPaths.get(0).size() == 2) {
                        paths.add(disjointPaths.get(0));

                    }
                }
            }
        }

        findBlock(paths);
    }

    public static void randomGraph() {
        V = 100;
        int numArestas = V * 2;
        adjList = new ArrayList[V];

        for (int u = 0; u < V; u++) {
            adjList[u] = new ArrayList<>();
        }

        Random random = new Random();
        int count = 0;
        while (count < numArestas) {
            int origem = random.nextInt(V); // escolhe um vértice aleatório
            int destino = random.nextInt(V); // escolhe outro vértice aleatório
            if (origem != destino && !adjList[origem].contains(destino)) {
                // adiciona aresta se os vértices forem diferentes e a aresta ainda não existir
                adjList[origem].add(destino);
                adjList[destino].add(origem);
                count++;
            }
        }

        List<List<Integer>> paths = new ArrayList<>();

        for (int s = 0; s < V - 1; s++) {
            for (int t = s + 1; t < V; t++) {
                ArrayList<ArrayList<Integer>> disjointPaths = findDisjointPaths(s, t);

                if (disjointPaths.size() > 1) {
                    paths.addAll(disjointPaths);
                }

                // pontes
                else if (disjointPaths.size() == 1) {
                    if (disjointPaths.get(0).size() == 2) {
                        paths.add(disjointPaths.get(0));

                    }
                }
            }
        }

        findBlock(paths);
    }

    public static void main(String[] args) {

        long startTime = System.currentTimeMillis();

        // encontra blocos para grafos aleatorios
        randomGraph();

        // encontra blocos para grafo obtido de .txt
        // readTxt();

        long endTime = System.currentTimeMillis();
        long tempoDeExecucao = endTime - startTime;
        System.out.println("O tempo de execução foi: " + tempoDeExecucao + " milissegundos");
    }
}