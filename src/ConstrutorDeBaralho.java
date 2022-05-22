import java.util.ArrayList;
import java.util.Random;

public class ConstrutorDeBaralho {

    public Pilha construirBaralho(int numeroDeNaipes){

        Naipe[] vetorNaipes = new Naipe[numeroDeNaipes];

        for(int i=0; i<vetorNaipes.length; i++){
            vetorNaipes[i] = new Naipe(i);
        }

        int inicioNumeroDeCartasNaipe = 1;
        int fimNumeroDeCartasNaipe = 13;

        int tamanhoVetorOrdem = fimNumeroDeCartasNaipe -inicioNumeroDeCartasNaipe +1;
        int[] vetorOrdem = new int[tamanhoVetorOrdem];
        for(int i = 0; i < vetorOrdem.length; i++){
            vetorOrdem[i] = i + inicioNumeroDeCartasNaipe;
        }



        int tamanhoBaralho = tamanhoVetorOrdem * vetorNaipes.length;

        Pilha baralho = new Pilha(tamanhoBaralho);

        Carta [] baralhoArray = new Carta[tamanhoBaralho];

        int counter = 0;
        for (Naipe naipe : vetorNaipes) {
            for (int ordem : vetorOrdem) {
                baralhoArray[counter] = new Carta(ordem, naipe);
                counter += 1;

            }

        }

        shuffle(baralhoArray);

        for(Carta carta : baralhoArray){
            baralho.empilhar(carta);
        }

        return baralho;

    }

    public static boolean cartaEstaNoBaralho(Carta carta, Carta[] baralho){

        boolean cartaEstaNoBaralho = false;

        for (Carta c : baralho) {
            if (c.eIgual(carta)) {
                cartaEstaNoBaralho = true;
                break;
            }

        }

        return cartaEstaNoBaralho;
    }

    private static void shuffle(int[] array){

        Random rnd = new Random();
        for (int i = array.length - 1; i > 0; i--)
        {
            int index = rnd.nextInt(i + 1);
            int a = array[index];
            array[index] = array[i];
            array[i] = a;
        }

    }

    private static void shuffle(Naipe[] array){

        Random rnd = new Random();
        for (int i = array.length - 1; i > 0; i--)
        {
            int index = rnd.nextInt(i + 1);
            Naipe a = array[index];
            array[index] = array[i];
            array[i] = a;
        }

    }

    private static void shuffle(Carta[] array){

        Random rnd = new Random();
        for (int i = array.length - 1; i > 0; i--)
        {
            int index = rnd.nextInt(i + 1);
            Carta a = array[index];
            array[index] = array[i];
            array[i] = a;
        }

    }

}
