import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class Matriz {
    protected int[][] matrizCusto = null;
    private int numVertices; 
    private int numArestas;
    private int numCentros;

    public int[][] getMatrizCusto() {
        return matrizCusto;
    }

    public int getNumArestas() {
        return numArestas;
    }

    public int getNumCentros() {
        return numCentros;
    }

    public int getNumVertices() {
        return numVertices;
    }

    public void setMatrizCusto(int[][] matrizCusto) {
        this.matrizCusto = matrizCusto;
    }

    public void setNumArestas(int numArestas) {
        this.numArestas = numArestas;
    }

    public void setNumCentros(int numCentros) {
        this.numCentros = numCentros;
    }

    public void setNumVertices(int numVertices) {
        this.numVertices = numVertices;
    }

    public int[][] gerarMatrizCusto(String filename) {
        try {
            File file = new File(filename);
            Scanner scanner = new Scanner(file);

            numVertices = scanner.nextInt();
            numArestas = scanner.nextInt();
            numCentros = scanner.nextInt();

            matrizCusto = new int[numVertices + 1][numVertices + 1];
            for (int i = 1; i <= numVertices; i++) {
                Arrays.fill(matrizCusto[i], Integer.MAX_VALUE);
                matrizCusto[i][i] = 0;
            }

            for (int i = 0; i < numArestas; i++) {
                int vertex1 = scanner.nextInt();
                int vertex2 = scanner.nextInt();
                int cost = scanner.nextInt();

                matrizCusto[vertex1][vertex2] = cost;
                matrizCusto[vertex2][vertex1] = cost;
            }

            for (int k = 1; k <= numVertices; k++) {
                for (int i = 1; i <= numVertices; i++) {
                    for (int j = 1; j <= numVertices; j++) {
                        if (matrizCusto[i][k] != Integer.MAX_VALUE && matrizCusto[k][j] != Integer.MAX_VALUE) {
                            int newCost = matrizCusto[i][k] + matrizCusto[k][j];
                            if (newCost < matrizCusto[i][j]) {
                                matrizCusto[i][j] = newCost;
                                matrizCusto[j][i] = newCost;
                            }
                        }
                    }
                }
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return matrizCusto;
    }

    public void imprimirMatriz() {
        for (int i = 1; i <= numVertices; i++) {
            for (int j = 1; j <= numVertices; j++) {
                if (matrizCusto[i][j] == Integer.MAX_VALUE) {
                    System.out.printf("%-8s", "INF");
                } else {
                    System.out.printf("%-8d", matrizCusto[i][j]);
                }
            }
            System.out.println();
        }
    }
    
    public void imprimirMatrizArq(String filename) {
        try {
            FileWriter writer = new FileWriter(filename);
            for (int i = 1; i <= numVertices; i++) {
                for (int j = 1; j <= numVertices; j++) {
                    if (matrizCusto[i][j] == Integer.MAX_VALUE) {
                        writer.write("INF ");
                    } else {
                        writer.write(matrizCusto[i][j] + " ");
                    }
                }
                writer.write("\n");
            }
            writer.close();
            System.out.println("A matriz foi impressa no arquivo com sucesso.");
        } catch (IOException e) {
            System.out.println("Ocorreu um erro ao imprimir a matriz no arquivo.");
            e.printStackTrace();
        }
    }
}
