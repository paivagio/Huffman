
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        System.out.println("\nHuffman  - Giovanni P. M. Paiva , Lucas F. A. de Oliveira, Sarah G. Rodrigues\n");


        while (true) {
            System.out.println("Digite 'bye bye' para sair do programa.");
            System.out.println("Texto: ");
            String text = in.nextLine().trim();

            if (text.equalsIgnoreCase("bye bye")) {
                System.out.println("Bye bye to you too! ;)");
                return;
            }


            System.out.println();
            Huffman huff = new Huffman();
            String data = huff.encode(text);

            int normalSize = text.length() * 8;
            int compressedSize = data.length();
            double rate = 100.0 - (compressedSize * 100.0 / normalSize);
            System.out.println("Tamanho normal: " + normalSize + " bits");
            System.out.println("Tamanho comprimido: " + compressedSize + " bits");
            System.out.printf("O valor comprimido é %.2f%% menor que o original. %n", rate);
            System.out.println();
            System.out.println("Texto codificado:");
            System.out.println(data);
            System.out.println();
            System.out.println("Texto decodificado:");
            System.out.println(huff.decode(data));
            System.out.println();
            System.out.println();
        }
    }
}

