import java.util.ArrayList;

public class Busca {
    static int tempoGlobal;
    private ArrayList<No> estruturaVertices;

    // busca em profundidade
    public void inicializacaoBuscaprofundidade(Lista grafo, int verticeInput) {
        estruturaVertices = new ArrayList<No>();
        tempoGlobal = 0;

        // criar array list de nos (tt = 0, td = 0)
        for(int i = 0; i < grafo.getNumVertices() + 1; i++) {
            estruturaVertices.add(new No(i));
        }

        int vertice = todosDescobertos();
        while(todosDescobertos() != -1){
            buscaProfundidade(vertice, verticeInput, grafo);
            vertice = todosDescobertos();
        }

    }
    // grafo.listaAdj.get(vertice).get(i)
    private void buscaProfundidade(int vertice, int verticeInput, Lista grafo) {
        tempoGlobal = tempoGlobal + 1;
        // definir tempo de descoberta do vertice
        estruturaVertices.get(vertice).tempoDescoberta = tempoGlobal;

        // para toda a vizinhanca do vertice
        for(int i = 0; i < grafo.listaAdj.get(vertice).size(); i++) {
            // se o tempo de descoberta do sucessor I for 0
            if(estruturaVertices.get(grafo.listaAdj.get(vertice).get(i)).tempoDescoberta == 0) {
                // aresta arvore
                if(vertice == verticeInput) {
                    imprimeArestas(vertice,  grafo.listaAdj.get(vertice).get(i), "arvore");
                }
                buscaProfundidade(estruturaVertices.get(grafo.listaAdj.get(vertice).get(i)).getIdVertice(), verticeInput, grafo);
            } 
            else if((estruturaVertices.get(grafo.listaAdj.get(vertice).get(i)).tempoTermino == 0) && (vertice == verticeInput)) {
                imprimeArestas(vertice,  grafo.listaAdj.get(vertice).get(i), "retorno");
            }
            else if((estruturaVertices.get(vertice).tempoDescoberta > estruturaVertices.get(grafo.listaAdj.get(vertice).get(i)).tempoDescoberta) && (vertice == verticeInput))  {
                imprimeArestas(vertice,  grafo.listaAdj.get(vertice).get(i), "cruzamento");
            }
            else if ((estruturaVertices.get(vertice).tempoDescoberta < estruturaVertices.get(grafo.listaAdj.get(vertice).get(i)).tempoDescoberta) && (vertice == verticeInput)) {
                imprimeArestas(vertice,  grafo.listaAdj.get(vertice).get(i), "avanco");
            }
        }
        tempoGlobal = tempoGlobal + 1;
        estruturaVertices.get(vertice).tempoTermino = tempoGlobal;
    }

    private void imprimeArestas(int origem, int destino, String classificacao) {
        System.out.println("A aresta de origem " + origem + " e destino " + destino + " e classificada como: " + classificacao);
    }

    private int todosDescobertos() {
        int vertice = -1;
        for(int i = 1; i < estruturaVertices.size(); i++) {
            if (estruturaVertices.get(i).tempoDescoberta == 0) {
                vertice = i;
                i = estruturaVertices.size();
            }
        }
        return vertice;
    }
}