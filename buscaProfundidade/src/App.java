import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        // VARIAVEIS
        Scanner sc = new Scanner(System.in);
        String nomeArquivo = "";
        int numVertice = 0;
        boolean valid = false;

        // nome arquivo
        System.out.println("Qual o nome do arquivo de onde deseja ler as informacoes? (sem o .txt)");
        nomeArquivo = sc.nextLine();
        // validar nome != linha vazia
        while (nomeArquivo.isBlank()) {
            System.out.println("Digite um nome valido");
            nomeArquivo = sc.nextLine();
        }
        // adicionar .txt
        nomeArquivo = nomeArquivo.concat(".txt");

        System.out.println("------------------");
        // criar lista de adjacencia
        Lista lista = new Lista();
        lista.criarGrafo(nomeArquivo);

        // input do usuario: de qual vertice deseja ver as informacoes?
        System.out.println("Digite o numero do vertice");
        // validar numero do vertice
        while (!valid) {
            try {
                numVertice = sc.nextInt();
                if ((numVertice > 0) && (numVertice <= lista.getNumVertices())) {
                    valid = true;
                } else {
                    System.out.println("Digite um numero valido");
                }
            } catch (Exception InputMismatchException) {
                System.out.println("Digite um numero valido");
                sc.next();
            }
        }
        // BUSCA EM PROFUNDIDADE
        System.out.println("------------------");
        Busca busca = new Busca();
        busca.inicializacaoBuscaprofundidade(lista, numVertice);
        System.out.println("------------------");

        // fim
        sc.close();
    }
}
