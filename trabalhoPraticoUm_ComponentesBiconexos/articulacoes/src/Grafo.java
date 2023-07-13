import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import java.util.List;
import java.util.Map;

import java.util.Stack;

public class Grafo implements Cloneable {

    // criando lista de adjacencia utilizando hash
    public Map<Integer, List<Integer>> adjacencia = new HashMap<>();


    // ------------------------Vertice--------------------------------
    private int numVertices;
    private int numArestas;
    private Map<Integer, Integer> descoberta = new HashMap<>();
    private Map<Integer, Integer> termino = new HashMap<>();
    private Map<Integer, Integer> pai = new HashMap<>();
    private ArrayList<Integer> articulacoes = new ArrayList<>();
    private List<List<Integer>> componentes = new ArrayList<List<Integer>>();
    private List<List<Integer>> pontes = new ArrayList<List<Integer>>();
    // -----------------------------------------------------------------

    @Override
    public Grafo clone() {
        try {
            Grafo clone = (Grafo) super.clone();
            clone.adjacencia = new HashMap<>();
            for (Map.Entry<Integer, List<Integer>> entry : this.adjacencia.entrySet()) {
                int vertice = entry.getKey();
                List<Integer> vizinhos = new ArrayList<>(entry.getValue());
                clone.adjacencia.put(vertice, vizinhos);
            }

            clone.descoberta = new HashMap<>();
            clone.termino = new HashMap<>();
            clone.pai = new HashMap<>();
            clone.articulacoes = new ArrayList<>(this.articulacoes);
            clone.componentes = new ArrayList<>();
            for (List<Integer> componente : this.componentes) {
                clone.componentes.add(new ArrayList<>(componente));
            }
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    // Lista de vizinhos
    public List<Integer> vizinhos(int vertice) {
        List<Integer> tmp = new ArrayList<>();
        // se o vertice existir tmp recebe os vizinhos do vertice
        if (adjacencia.containsKey(vertice)) {
            tmp = adjacencia.get(vertice);
        }
        // ordenar saida
        Collections.sort(tmp);
        return tmp;
    }

    /**
     * 
     * Função: contaZeros()
     * Esta função em Java é responsável por contar a quantidade de vértices que
     * possuem índice zero em um grafo, representado por um mapa de pais.
     * O mapa de pais é uma estrutura de dados que armazena a informação do pai de
     * cada vértice em um grafo.
     * A importância do método está relacionada à detecção de articulações em um
     * grafo, que são vértices que, quando removidos, dividem o grafo em dois ou
     * mais componentes.
     * Se a quantidade de vértices com índice zero aumentar, significa que foi
     * necessário realizar mais buscas para encontrar os outros componentes, o que
     * pode indicar a presença de uma articulação.
     * O método é utilizado em conjunto com outros algoritmos de busca e análise de
     * grafos para detectar e identificar esses pontos críticos.
     */
    private int contaZeros() {
        int count = 0;
        for (int valorPai : pai.values()) {
            if (valorPai == 0) {
                count++;
            }
        }
        return count;
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

        adjacencia.get(destino).add(origem);
    }

    public void addArestaModificado(int origem, int destino) {
        if (!adjacencia.containsKey(origem) && origem != -1) {
            adjacencia.put(origem, new ArrayList<>());
        }
        if (origem == -1 || destino == -1) {
            return;
        }

        adjacencia.get(origem).add(destino);
    }

    // busca
    public void buscaProfundidade() {
        Stack<Integer> pilha = new Stack<>();
        int tempo = 0;
        List<Integer> visitados = new ArrayList<>();

        // Percorre todos os vértices do grafo
        for (int vertice : adjacencia.keySet()) {
            // Verifica se o vértice já foi visitado antes de realizar a busca em
            // profundidade
            if (!visitados.contains(vertice)) {

                // Adiciona o vértice inicial na pilha e marca como visitado
                pilha.push(vertice);
                visitados.add(vertice);
                descoberta.put(vertice, ++tempo);

                pai.put(vertice, 0);

                while (!pilha.isEmpty()) {
                    int atual = pilha.peek();
                    boolean visitouFilho = false;

                    // Verifica se existem filhos não visitados e adiciona o primeiro na pilha
                    for (int filho : vizinhos(atual)) {
                        if (!visitados.contains(filho)) {
                            pilha.push(filho);
                            visitados.add(filho);
                            descoberta.put(filho, ++tempo);
                            pai.put(filho, atual);

                            visitouFilho = true;
                            break;
                        }
                    }
                    // Se todos os filhos já foram visitados, remove o vértice atual da pilha e
                    // marca o tempo de término
                    if (!visitouFilho) {
                        pilha.pop();
                        termino.put(atual, ++tempo);
                    }
                }
            }

        }
    }

    //Uma busca modificada para encontrar os compontentes de um grafo
    public void buscaProfundidadeComponentes() {
        Stack<Integer> pilha = new Stack<>();
        int tempo = 0;
        List<Integer> visitados = new ArrayList<>();
        // Percorre todos os vértices do grafo
        for (int vertice : adjacencia.keySet()) {

            // Verifica se o vértice já foi visitado antes de realizar a busca em
            // profundidade
            if (!visitados.contains(vertice)) {
                // Adiciona o vértice inicial na pilha e marca como visitado
                pilha.push(vertice);
                visitados.add(vertice);
                descoberta.put(vertice, ++tempo);
                pai.put(vertice, 0);
                ArrayList<Integer> tmp = new ArrayList<>();
                while (!pilha.isEmpty()) {
                    int atual = pilha.peek();
                    boolean visitouFilho = false;
                    if (!tmp.contains(atual)) {
                        tmp.add(atual);
                    }
                    // Verifica se existem filhos não visitados e adiciona o primeiro na pilha
                    for (int filho : vizinhos(atual)) {
                        if (!visitados.contains(filho)) {
                            pilha.push(filho);
                            visitados.add(filho);
                            descoberta.put(filho, ++tempo);
                            pai.put(filho, atual);
                            if (!tmp.contains(atual)) {
                                tmp.add(atual);
                            }
                            visitouFilho = true;
                            break;
                        }
                    }
                    // Se todos os filhos já foram visitados, remove o vértice atual da pilha e
                    // marca o tempo de término
                    if (!visitouFilho) {
                        pilha.pop();
                        termino.put(atual, ++tempo);
                    }
                }
                componentes.add(tmp);
            }

        }
    }
    // --------------------

    public Grafo copiarSemVertice(int v) {

        Grafo clone = clone();

        // Remover vértice v do grafo clonado
        clone.adjacencia.remove(v);
        // Remover arestas que chegam no vértice v
        for (Map.Entry<Integer, List<Integer>> entry : clone.adjacencia.entrySet()) {
            
            List<Integer> vizinhos = entry.getValue();
            if (vizinhos.contains(v)) {
                vizinhos.remove(Integer.valueOf(v));
            }
        }

        return clone;
    }


    // Metodo de encontrar
    public List<Integer> identificarArticulacoes() {

        int countOriginal = 0;
        int countPosRemocao = 0;

        buscaProfundidade();
        countOriginal = contaZeros();

        // para todos os vertices do grafo:
        for (int j = 1; j <= adjacencia.size(); j++) {
            // criar uma copia do grafo sem o vertice i
            Grafo tmp = copiarSemVertice(j);
            // realizar busca em profundidade no grafo sem o vertice j
            tmp.buscaProfundidade();
            // contar o numero de vertices sem pai
            countPosRemocao = tmp.contaZeros();
            // se o numero d evertices sem pai aumentou, j e uma articulacao
            if (countPosRemocao > countOriginal) {
                // adicionar vertice j a lista de articulacoes
                articulacoes.add(j);
            }
        }
        return articulacoes;
    }

    public Grafo encontraComponentes() {
        Grafo novoGrafo = new Grafo();
        identificarArticulacoes();
        novoGrafo = clone();
        
        for (Integer articulacao : articulacoes) {
            novoGrafo.removerVertice(articulacao);
        }

        
        novoGrafo.buscaProfundidadeComponentes();
        return novoGrafo;
    }

    public void encontraComponentesBiconexos() {
        Grafo semArticulacoes = encontraComponentes();
        for (List<Integer> componente : semArticulacoes.componentes) {
            componentes.add(new ArrayList<>(componente));
        }

        for (Integer articulacao : articulacoes) {
            List<Integer> adjacenciaArt = adjacencia.get(articulacao);
            for (Integer integer : adjacenciaArt) {
                for (int i = 0; i < semArticulacoes.componentes.size(); i++) {
                    List<Integer> integer2 = semArticulacoes.componentes.get(i);
                    if (integer2.contains(integer) && !integer2.contains(articulacao)) {
                        if (!componentes.get(i).contains(articulacao)) {
                            componentes.get(i).add(articulacao);
                        }

                    }

                }
            }

        }
        encontrarPontes();
        System.out.println(componentes);
    }

    public List<List<Integer>> encontrarPontes() {
        Grafo novoGrafo = clone();
        buscaProfundidade();
        int original = contaZeros();
        List<List<Integer>> pontes = new ArrayList<>();
        ArrayList<Integer> visitou = new ArrayList<>();
        for (int i = 0; i < articulacoes.size(); i++) {

            int articulacao = articulacoes.get(i);
            List<Integer> tmp = adjacencia.get(articulacao);
            for (int j = 0; j < tmp.size(); j++) {
                int destino = tmp.get(j);

                if (!visitou.contains(destino)) {
                    if (articulacoes.contains(destino)) {
                        novoGrafo = clone();
                        novoGrafo.removerAresta(articulacao, destino);

                        novoGrafo.buscaProfundidade();
                        int zeros = novoGrafo.contaZeros();

                        if (zeros > original) {
                            List<Integer> arestaTmp = new ArrayList<>();
                            componentes.add(arestaTmp);
                            arestaTmp.add(articulacao);
                            arestaTmp.add(destino);
                            if (!pontes.contains(arestaTmp)) {
                                pontes.add(arestaTmp);
                            }
                        }

                    }
                }
            }
            visitou.add(articulacoes.get(i));
        }
        this.pontes = pontes;
        return pontes;
    }
    // Metodos de remover

    public void removerAresta(int origem, int destino) {
        if (!adjacencia.containsKey(origem) || !adjacencia.containsKey(destino)) {
            // one of the vertices doesn't exist
            return;
        }
        adjacencia.get(origem).remove((Integer) destino);
        adjacencia.get(destino).remove((Integer) origem);
    }

    public void removerVertice(int vertice) {
        if (!adjacencia.containsKey(vertice)) {
            // o vértice não existe
            return;
        }
        // remover todas as arestas que contêm este vértice
        for (List<Integer> vizinhos : adjacencia.values()) {
            vizinhos.remove((Integer) vertice);
        }
        // remover o vértice
        adjacencia.remove(vertice);
    }
    

    // Metodos de print
    public void printArticulacoes() {
        System.out.println(articulacoes);
    }

  
    
    public void printGrafo() {
        System.out.println(adjacencia);
    }

    public void imprimeTTTD() {
        // Imprime os tempos de descoberta e término de cada vértice
        for (int vertice : adjacencia.keySet()) {
            System.out.println("Vértice " + vertice + ": descoberta = " + descoberta.get(vertice) + ", término = "
                    + termino.get(vertice) + " Pai = " + pai.get(vertice));
        }
    }

}