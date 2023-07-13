public class App {
    public static void main(String[] args) throws Exception {
        Matriz matriz = new Matriz();
        String arquivo = "arquivos/pmed40.txt"; // Nome do arquivo de dados a ser lido
        matriz.gerarMatrizCusto(arquivo);
        int[] raio = new int[5];
        int media = 0;
        //ForcaBruta FB = new ForcaBruta(matriz);
        //FB.gerarCombinacoes();
        for(int i = 0; i <5; i++) {
            Gon gon = new Gon(matriz);
            raio[i] = gon.GonAlgo();
            media = media + raio[i];
        }
        media = media / 5;
        System.out.println("MEDIA = " + media);
    }
}
