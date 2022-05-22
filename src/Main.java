import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {

        Scanner reader = new Scanner(System.in);
        Crupie crupie = new Crupie(reader);

        boolean jogarNovamente;
        do {
            crupie.iniciarMainLoopFreecell();

            System.out.println("Jogar novamente ? Digite 1 para sim. ");
            int digito = reader.nextInt();
            jogarNovamente = digito == 1;

        } while(jogarNovamente);

        System.out.println();
        System.out.println("Obrigado pelo jogo ! ");

        reader.close();

    }

}