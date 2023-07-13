import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class Grafo {

    // criando lista de adjacencia utilizando hash
    private int numVertices;
    private int numArestas;
    protected Map<Integer, List<Integer>> adjacencia = new HashMap<>();
    public List<Integer> visitados = new ArrayList<>();
    private List<Aresta> arestas = new ArrayList<>();

    // -----------------------------------------------------------------
    public void setNumArestas(int numArestas) {
        this.numArestas = numArestas;
    }

    public void setNumVertices(int numVertices) {
        this.numVertices = numVertices;
    }

    public int getNumArestas() {
        return numArestas;
    }

    public int getNumVertices() {
        return numVertices;
    }

    Grafo(int numVertices, int numArestas) {
        setNumVertices(numVertices);
        setNumArestas(numArestas);
    }

    // Adicionar aresta
    public void adicionarAresta(int origem, int destino) {
        if (!adjacencia.containsKey(origem)) {
            adjacencia.put(origem, new ArrayList<>());
        }
        if (!adjacencia.containsKey(destino)) {
            adjacencia.put(destino, new ArrayList<>());
        }
        adjacencia.get(origem).add(destino);
        arestas.add(new Aresta(origem, destino));

        adjacencia.get(destino).add(origem);
        arestas.add(new Aresta(destino, origem));
    }

    public List<List<Integer>> encontrarComponentesBiconexos() {
        Map<Integer, Integer> numero = new HashMap<>(); // Mapa que armazena o número de cada vértice
        Map<Integer, Integer> lowpt = new HashMap<>(); // Mapa que armazena o menor número que pode ser alcançado por um
                                                       // vértice
        Stack<Aresta> pilha = new Stack<>(); // Pilha para armazenar as arestas
        int num = 0; // Inicializa o contador

        List<List<Integer>> componentes = new ArrayList<>(); // Lista de listas que armazena os componentes biconexos

        // Para cada vértice no grafo, verifica se o vértice já foi visitado. Se não,
        // chama o método auxiliar
        for (int v : adjacencia.keySet()) {
            if (!numero.containsKey(v)) {
                encontrarComponentesBiconexosAux(v, null, numero, lowpt, pilha, num, componentes);
            }
        }

        return componentes; // Retorna a lista de componentes biconexos
    }

    private void encontrarComponentesBiconexosAux(int v, Integer pai, Map<Integer, Integer> numero,
            Map<Integer, Integer> lowpt, Stack<Aresta> pilha, int num,
            List<List<Integer>> componentes) {
        // Atribui um número a cada vértice v
        numero.put(v, num);
        // Atribui um valor inicial a lowpt para cada vértice v
        lowpt.put(v, num);
        // Incrementa o valor de num para o próximo vértice
        num++;
        // Pega a lista de vizinhos do vértice v
        List<Integer> vizinhos = adjacencia.get(v);
        // Número de vizinhos de v
        int n = vizinhos.size();
        // Percorre a lista de vizinhos de v
        for (int i = 0; i < n; i++) {
            int w = vizinhos.get(i);
            // Se o vértice w ainda não foi numerado, então é possível fazer uma chamada
            // recursiva
            if (!numero.containsKey(w)) {
                // Empilha a aresta (v, w) para ser utilizada no processo de backtracking
                pilha.push(new Aresta(v, w));
                // Faz uma chamada recursiva com o vértice w como o novo vértice v e o vértice v como o pai de w
                encontrarComponentesBiconexosAux(w, v, numero, lowpt, pilha, num, componentes);
                // Atualiza o valor de lowpt[v] com o mínimo entre lowpt[v] e lowpt[w]
                lowpt.put(v, Math.min(lowpt.get(v), lowpt.get(w)));

                // Se o valor de lowpt[w] for maior ou igual ao número de v, significa que v é um ponto de articulação
                if (lowpt.get(w) >= numero.get(v)) {

                    // Cria uma nova lista de componentes biconexos
                    List<Integer> componente = new ArrayList<>();
                    Aresta e;

                    // Percorre a pilha para desempilhar as arestas e adicionar os vértices no componente biconexo
                    do {
                        e = pilha.pop();
                        if (!componente.contains(e.getOrigem())) {
                            componente.add(e.getOrigem());
                        }
                        if (!componente.contains(e.getDestino())) {
                            componente.add(e.getDestino());
                        }
                    } while (!(e.getOrigem() == v && e.getDestino() == w)
                            && !(e.getOrigem() == w && e.getDestino() == v));
                    if (!componente.contains(e.getOrigem())) {
                        componente.add(e.getOrigem());
                    }
                    if (!componente.contains(e.getDestino())) {
                        componente.add(e.getDestino());
                    }

                    // Adiciona o componente biconexo na lista de componentes biconexos
                    componentes.add(componente);
                }
            }
            // Se o vértice w já foi numerado e não é o pai de v e possui um número menor que o de v
            else if (pai != null && w != pai && numero.get(w) < numero.get(v)) {
                // Empilha a aresta (v, w) para ser utilizada no processo de backtracking
                pilha.push(new Aresta(v, w));
                lowpt.put(v, Math.min(lowpt.get(v), numero.get(w))); //atualizar valor de lowpt
            }
        }
    }

    public void imprimeComponentesBiconexos() {
        List<List<Integer>> componentesBiconexos = encontrarComponentesBiconexos();
        for (int i = 0; i < componentesBiconexos.size(); i++) {
            System.out.printf("Componente biconexo #%d:\n", i + 1);
            for (Integer vertice : componentesBiconexos.get(i)) {
                System.out.printf("(%d) ", vertice);
            }
            System.out.println();
        }
    }

}