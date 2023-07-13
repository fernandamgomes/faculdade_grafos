import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;

public class Lista {
    private int numVertices;
    private int numArestas;
    private ArrayList<ArrayList<Integer>> listaAdj;

    // --------------- GETTERS ---------------
    public int getNumArestas() {
        return numArestas;
    }

    public int getNumVertices() {
        return numVertices;
    }

    public ArrayList<ArrayList<Integer>> getlistaAdj() {
        return listaAdj;
    }

    // --------------- SETTERS ---------------
    public void setNumArestas(int numArestas) {
        this.numArestas = numArestas;
    }

    public void setNumVertices(int numVertices) {
        this.numVertices = numVertices;
    }

    // ler arquivo e criar lista de adjacencia
    public void criarGrafo(String arquivo) {
        // VARIAVEIS
        int origem = 0;
        int destino = 0;
        int tam = 0;
        int leitura = 0;

        try {
            // ler arquivo
            File myObj = new File(arquivo);
            Scanner sc = new Scanner(myObj);
            // ler numero de vertices e de arestas
            this.numVertices = sc.nextInt();
            this.numArestas = sc.nextInt();

            tam = numVertices + 1;

            listaAdj = new ArrayList<ArrayList<Integer>>(tam);

            for (int i = 0; i < tam; i++) {
                listaAdj.add(new ArrayList<Integer>());
            }

            // ler informacoes arquivo e preencher lista
            while (sc.hasNextInt()) {
                if (leitura % 2 == 0) {
                    origem = sc.nextInt();
                } else {
                    destino = sc.nextInt();
                    addVertice(origem, destino);
                }
                leitura++;
            }
            sc.close();
        } catch (FileNotFoundException e) {
            System.out.println("Erro na leitura do arquivo.");
            System.exit( 0 );
            e.printStackTrace();
        }
    }

    // adicionar vertice
    public void addVertice(int origem, int destino) {
        listaAdj.get(origem).add(destino);
    }

    // imprimir o grafo
    public void printGraph() {
        for (int i = 1; i < listaAdj.size(); i++) {
            System.out.println("\nlista de adjacencia do vertice " + i);
            for (int j = 0; j < listaAdj.get(i).size(); j++) {
                System.out.print(" -> " + listaAdj.get(i).get(j));
            }
            System.out.println();
        }
    }
    
    // imprime lista de sucessores e grau de saida
    public void sucessores(int vertice) {
        System.out.print("Os sucessores do vertice " + vertice + " sao: ");
        for (int i = 0; i < listaAdj.get(vertice).size(); i++) {
            System.out.print(listaAdj.get(vertice).get(i) + " ");
        }
        System.out.println();
        System.out.println("O grau de saida do vertice " + vertice + " e: " + listaAdj.get(vertice).size());
    }

    // imprime lista de predecessores e grau de entrada
    public void predecessores(int vertice) {
        int grauEntrada = 0;
        if (vertice > this.numVertices) {
            System.out.println("vertice invalido");
        } else {
            System.out.print("Os predecessores do vertice " + vertice + " sao: ");
            for (int i = 1; i < listaAdj.size(); i++) {
                for (int j = 0; j < listaAdj.get(i).size(); j++) {
                    if (listaAdj.get(i).get(j) == vertice) {
                        System.out.print(i + " ");
                        grauEntrada++;
                    }
                }
            }
        }
        System.out.println();
        System.out.println("O grau de entrada do vertice " + vertice + " e: " + grauEntrada);
    }
}