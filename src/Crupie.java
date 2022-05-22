import java.util.Arrays;
import java.util.Scanner;

public class Crupie {

    Scanner reader;
    private  Pilha baralho;
    private  Carta[] freeCellArray;
    private int numeroDePilhasDeJogo = 4;
    private int numeroDeFreeCells = 4;

    private final int numeroDeNaipes = 4;

    private int numeroDeCartas;
    private int[] tamanhoPilhaDeJogoArray;
    private int[] tamanhoPilhaSaidaArray;

    private boolean jogoEmAndamento = false;

    public Crupie(Scanner reader){
        this.setReader(reader);
    }

    public void setReader(Scanner reader) {
        this.reader = reader;
    }

    public void iniciarMainLoopFreecell() throws Exception {

        System.out.println("Iniciando jogo de Freecell!");

        System.out.println("Digite o numero de freecells que deseja: ");
        this.numeroDeFreeCells = -1;
        do {
            this.numeroDeFreeCells = this.reader.nextInt();
        } while(this.numeroDeFreeCells<=0);

        System.out.println("Digite o numero de pilhas de jogo que deseja: ");
        this.numeroDePilhasDeJogo = -1;
        do {
            this.numeroDePilhasDeJogo = this.reader.nextInt();
        } while(this.numeroDePilhasDeJogo<=0);

        this.jogoEmAndamento = true;

        this.freeCellArray = new Carta[this.numeroDeFreeCells];

        ConstrutorDeBaralho construtorDeBaralho = new ConstrutorDeBaralho();
        this.baralho = construtorDeBaralho.construirBaralho(this.numeroDeNaipes);

        this.numeroDeCartas = this.baralho.getTamanhoPreenchido();

        this.tamanhoPilhaDeJogoArray = new int [this.numeroDePilhasDeJogo];

        int tamanhoInicialPilhasDeJogo = this.baralho.getTamanho() / this.numeroDePilhasDeJogo;

        Arrays.fill(this.tamanhoPilhaDeJogoArray, tamanhoInicialPilhasDeJogo);

        this.tamanhoPilhaSaidaArray = new int [this.numeroDeNaipes];
        Arrays.fill(this.tamanhoPilhaSaidaArray, 0);

        int tamanhoRemanescente = this.baralho.getTamanho() - (tamanhoInicialPilhasDeJogo*numeroDePilhasDeJogo);

        while(tamanhoRemanescente > 0){
            for(int i = 0; i< this.tamanhoPilhaDeJogoArray.length; i++){
                this.tamanhoPilhaDeJogoArray[i] += 1;
                tamanhoRemanescente -=1;
                if (tamanhoRemanescente ==0){
                    break;
                }

            }

        }


        Arrays.fill(this.freeCellArray, null);

        System.out.print("Emabaralhando.");
        for (int i = 0; i<10; i++){
            Thread.sleep(80);
            System.out.print(".");
        }
        System.out.println(".");
        System.out.println();



        do {

            this.printarJogo();
            this.perguntarEExecutarJogada();
            this.checarIntegridade();

        } while (this.jogoEmAndamento);


    }



    private boolean verificarSeVenceuOjogo(){

        int acumulador = 0;
        for(int i: this.tamanhoPilhaSaidaArray){
            acumulador += i;
        }
        return acumulador == this.baralho.getTamanho();

    }

    private void checarIntegridade() throws Exception {

        int numeroAtualDeCartas = this.baralho.getTamanhoPreenchido() + this.numeroDeFreeCells - this.numeroDeFreeCellsDisponiveis();

        if(numeroAtualDeCartas != this.numeroDeCartas){
            throw new Exception("Alguma carta se perdeu");
        }

    }

    private void imprimirMensagemFimDeJogo(boolean vitoria){
        System.out.print("Jogo terminou! ");
        if(vitoria){
            System.out.println("Você venceu :D Parabéns!!");
            System.out.println("Jogue novamente !");
        }
        else{
            System.out.println("Sem mais jogadas. Voce perdeu ;(");
            System.out.println("Tente novamente !");
        }
    }

    private void printarJogo(){


        System.out.println("Mesa do freecell :");
        System.out.println();

        Pilha pilhaTemporaria = new Pilha(this.baralho.getTamanho());

        int tamanhoMaximoPilhaDejogo = -1;
        for( int tamanho: this.tamanhoPilhaDeJogoArray){
            tamanhoMaximoPilhaDejogo = Math.max(tamanhoMaximoPilhaDejogo, tamanho);
        }

        int tamanhoMaximoPilhasDeJogoLabels= tamanhoMaximoPilhaDejogo+1;
        String[] descricaoCartaArray = new String[tamanhoMaximoPilhasDeJogoLabels];
        Arrays.fill(descricaoCartaArray, "");



        System.out.println("Pilhas de jogo: ");
        for (int i = 0; i < this.tamanhoPilhaDeJogoArray.length; i++) {

            int tamanhoPilhaDeJogo = this.tamanhoPilhaDeJogoArray[i];
            for (int j = 0; j < tamanhoMaximoPilhasDeJogoLabels ; j++) {

                if (j == 0){
                    descricaoCartaArray[j] +=   "    Pilha " + i + "   |";
                }
                else{
                    Carta c = null;
                    if (j-1<tamanhoPilhaDeJogo){
                        c = this.baralho.desempilhar();
                        pilhaTemporaria.empilhar(c);
                    }
                    if(c != null) {
                        descricaoCartaArray[j]+= c.nomeCartaPadronizado() + " | ";
                    } else {
                        descricaoCartaArray[j]+= "             | ";
                    }
                }


            }
        }

        for (String s : descricaoCartaArray) {
            if (!s.equals("")) {
                System.out.println(s);
                for (int j = 0; j < s.length(); j++) {
                    System.out.print("_");
                }
                System.out.println();
            }
        }


        descricaoCartaArray = new String[2];
        Arrays.fill(descricaoCartaArray, "");

        System.out.println();
        System.out.println("Pilhas de saida: ");
        for (int i = 0; i < this.tamanhoPilhaSaidaArray.length; i++) {
            descricaoCartaArray[0] += "Pilha de saida " + i + " | ";
            int tamanhoPilhaDeSaida = this.tamanhoPilhaSaidaArray[i];

            if (tamanhoPilhaDeSaida == 0){
                descricaoCartaArray[1] += "                 | ";
            }

            for (int j = 0; j < tamanhoPilhaDeSaida; j++) {

                Carta c = this.baralho.desempilhar();
                if (j==tamanhoPilhaDeSaida -1){
                    descricaoCartaArray[1] += "  "+c.nomeCartaPadronizado() + "   | ";
                }
                pilhaTemporaria.empilhar(c);

            }
        }

        for (String s : descricaoCartaArray) {
            if (!s.equals("")) {
                System.out.println(s);
                for (int j = 0; j < s.length(); j++) {
                    System.out.print("_");
                }
                System.out.println();
            }
        }

        descricaoCartaArray = new String[2];
        Arrays.fill(descricaoCartaArray, "");

        System.out.println();
        System.out.println("Freecells: ");
        for (int i = 0; i < this.freeCellArray.length; i++){
            Carta c = this.freeCellArray[i];
            descricaoCartaArray[0] +="  Freecell "+i+ " | ";
            if(c == null){
                descricaoCartaArray[1] += "             | ";
            }
            else {
                descricaoCartaArray[1] += c.nomeCartaPadronizado() + " | ";
            }

        }

        for (String s : descricaoCartaArray) {
            if (!s.equals("")) {
                System.out.println(s);
                for (int j = 0; j < s.length(); j++) {
                    System.out.print("_");
                }
                System.out.println();
            }
        }

        System.out.println();

        int tamanhoPreenchido = pilhaTemporaria.getTamanhoPreenchido();
        for (int i= 0; i< tamanhoPreenchido;i ++){
            Carta c = pilhaTemporaria.desempilhar();
            this.baralho.empilhar(c);
        }


    }

    public void perguntarEExecutarJogada(){

        System.out.println("Escolha uma jogada: ");
        int numeroJogadas = this.listarOuExecutarJogadas(false, -1) -1;


        if( numeroJogadas == 0){
            this.imprimirMensagemFimDeJogo(false);
            this.jogoEmAndamento = false;
            return;
        }


        boolean jogadaInvalida;

        int jogadaEscolhida;
        do {
            jogadaEscolhida = this.reader.nextInt();
            jogadaInvalida = jogadaEscolhida > numeroJogadas | jogadaEscolhida <= 0;
            if (jogadaInvalida){
                System.out.println("Entrada invalida! Digite um numero entre 1 e "+ numeroJogadas );
            }
        } while (jogadaInvalida);


        this.listarOuExecutarJogadas(true, jogadaEscolhida);

        boolean venceu = this.verificarSeVenceuOjogo();

        if(venceu){
            this.imprimirMensagemFimDeJogo(true);
            this.jogoEmAndamento = false;
        }

    }

    public int listarOuExecutarJogadas(boolean executarJogada, int idJogadaEscolhida){

        int jogadaID = 1;
        boolean movimentoValido;

        Carta [] cartasMexiveisDasPilhasDeJogo = this.obterCartasMexiveisDasPilhasDeJogo();


        movimentoValido = this.validarMovimentoParaFreecell();
        // Movimento das cartas para freecells
        if (movimentoValido){
            for(int i = 0; i < cartasMexiveisDasPilhasDeJogo.length; i++){

                Carta carta = cartasMexiveisDasPilhasDeJogo[i];

                if (carta != null ){

                    if(!(executarJogada)){
                        System.out.println(jogadaID + " - Mover carta " + carta.nomeCarta() + " (pilha " + i +") para freecell");
                    }

                    else if (jogadaID == idJogadaEscolhida){
                        this.moverCartaDePilhaDeJogoParaFreecell(i);
                    }

                    jogadaID++;
                }


            }
        }


        // Movimento das pilhas de jogo para outras pilhas de jogo
        for (int i = 0; i< this.numeroDePilhasDeJogo; i++){

            for (int j = 0; j< this.numeroDePilhasDeJogo; j++){

                movimentoValido =  validarMovimentoCartaEntrePilhasDeJogo( i, j, cartasMexiveisDasPilhasDeJogo);

                if(movimentoValido){

                    if(!(executarJogada)){
                        Carta carta = cartasMexiveisDasPilhasDeJogo[i];
                        System.out.println(jogadaID + " - Mover carta " + carta.nomeCarta() + " (pilha " + i +") para a pilha "+ j);
                    }

                    else if (jogadaID == idJogadaEscolhida){
                        this.moverCartaEntrePilhasDeJogo(i,j);
                    }

                    jogadaID++;

                }

            }

        }

        // Movimento das freecells para pilhas de jogo

        for (int i = 0; i< this.numeroDeFreeCells; i++){

            Carta carta = this.freeCellArray[i];

            for (int j = 0; j< this.numeroDePilhasDeJogo; j++){

                movimentoValido =  validarMovimentoCartaParaPilhaDeJogo( carta, j, cartasMexiveisDasPilhasDeJogo);

                if(movimentoValido){

                    if(!(executarJogada)){

                        System.out.println(jogadaID + " - Mover carta " + carta.nomeCarta() + " (freecell " + i +") para a pilha "+ j);
                    }

                    else if (jogadaID == idJogadaEscolhida){
                        this.moverCartaDeFreecellParaPilhaDejogo(i, j);
                    }

                    jogadaID++;

                }

            }

        }


        // Movimento das pilhas de jogo para pilhas de saida
        for (int i = 0; i< this.numeroDePilhasDeJogo; i++){

            Carta carta = cartasMexiveisDasPilhasDeJogo[i];
            movimentoValido =  valirdarMovimentoDeCartaParaPilhaDeSaida(carta);

            if(movimentoValido){

                if(!(executarJogada)){

                    System.out.println(jogadaID + " - Mover carta " + carta.nomeCarta() + " (pilha " + i +") para a pilha de saida");
                }

                else if (jogadaID == idJogadaEscolhida){
                    this.moverCartaDePilhaDeJogoParaPilhaDeSaida(i);
                }

                jogadaID++;

            }

        }


        // Movimento das freecells para pilhas de saida
        for (int i = 0; i< this.freeCellArray.length; i++){

            Carta carta = freeCellArray[i];
            movimentoValido =  valirdarMovimentoDeCartaParaPilhaDeSaida(carta);

            if(movimentoValido){

                if(!(executarJogada)){

                    System.out.println(jogadaID + " - Mover carta " + carta.nomeCarta() + " (freecell " + i +") para a pilha de saida");
                }

                else if (jogadaID == idJogadaEscolhida){
                    this.moverCartaDeFreecellParaPilhaDeSaida(i);
                }

                jogadaID++;

            }

        }


        return jogadaID;

    }

    private Carta [] obterCartasMexiveisDasPilhasDeJogo(){

        Carta [] cartasMexiveisDasPilhasDeJogo = new Carta[this.numeroDePilhasDeJogo];

        Pilha pilhaTemporaria = new Pilha(this.baralho.getTamanhoPreenchido());
        for (int i = 0; i < this.tamanhoPilhaDeJogoArray.length; i++) {
            int tamanhoPilhaDeJogo= this.tamanhoPilhaDeJogoArray[i];

            for (int j = 0; j < tamanhoPilhaDeJogo; j++) {

                Carta c = this.baralho.desempilhar();
                if (j == tamanhoPilhaDeJogo -1){
                    cartasMexiveisDasPilhasDeJogo[i] = c;
                }
                pilhaTemporaria.empilhar(c);

            }
        }

        int tamanhoPreenchido = pilhaTemporaria.getTamanhoPreenchido();
        for (int i= 0; i< tamanhoPreenchido;i ++){
            Carta c = pilhaTemporaria.desempilhar();
            this.baralho.empilhar(c);

        }

        return cartasMexiveisDasPilhasDeJogo;

    }

    private int numeroDeFreeCellsDisponiveis(){

        int numeroDeFreeCellsDisponiveis = 0;

        for (Carta freecell : this.freeCellArray) {
            if (freecell == null) {
                numeroDeFreeCellsDisponiveis++;
            }
        }

        return numeroDeFreeCellsDisponiveis;

    }

    // Métodos de validação

    private boolean validarMovimentoCartaEntrePilhasDeJogo(int pilhaDeJogoIDOrigem, int pilhaDeJogoIDDestino, Carta [] cartasMexiveisDasPilhasDeJogo){

        if (pilhaDeJogoIDOrigem == pilhaDeJogoIDDestino){
            return false;
        }

        Carta carta = cartasMexiveisDasPilhasDeJogo[pilhaDeJogoIDOrigem];

        return this.validarMovimentoCartaParaPilhaDeJogo(carta,  pilhaDeJogoIDDestino, cartasMexiveisDasPilhasDeJogo);


    }

    private boolean validarMovimentoCartaParaPilhaDeJogo (Carta carta, int pilhaDeJogoIDDestino, Carta [] cartasMexiveisDasPilhasDeJogo){

        if (carta == null){
            return false;
        }

        if (cartasMexiveisDasPilhasDeJogo[pilhaDeJogoIDDestino] == null){
            return true;
        }

        else {

            Carta cartaTopoDestino = cartasMexiveisDasPilhasDeJogo[pilhaDeJogoIDDestino];
            return  carta.naipeEInvertido(cartaTopoDestino) & carta.cartaConsecutiva(cartaTopoDestino);

        }

    }

    private boolean validarMovimentoParaFreecell(){
        return this.numeroDeFreeCellsDisponiveis() > 0;
    }

    private boolean valirdarMovimentoDeCartaParaPilhaDeSaida(Carta carta){

        if (carta == null){
            return false;
        }

        int pilhaDeSaidaID = carta.getNaipe().getNaipeID();

        return (carta.getOrdem()  - this.tamanhoPilhaSaidaArray[pilhaDeSaidaID] ) == 1;

    }



    // Métodos de baixo nível para manipular cartas na pilha
    private Carta retirarCarta(int posicao){

        Pilha pilhaTemporaria = new Pilha(posicao+1);
        for (int i = 0; i < posicao - 1; i++) {
            Carta carta = this.baralho.desempilhar();
            pilhaTemporaria.empilhar(carta);
        }

        Carta cartaASerRetirada = this.baralho.desempilhar();

        int tamanhoPreenchido = pilhaTemporaria.getTamanhoPreenchido();
        for (int i= 0; i< tamanhoPreenchido;i ++){
            Carta c = pilhaTemporaria.desempilhar();
            this.baralho.empilhar(c);

        }

        return cartaASerRetirada;

    }

    private void adicionarCarta(Carta carta, int posicao){

        Pilha pilhaTemporaria = new Pilha(this.baralho.getTamanhoPreenchido() +1);

        for (int i = 0; i < posicao - 1; i++) {
            Carta c = this.baralho.desempilhar();
            pilhaTemporaria.empilhar(c);
        }

        pilhaTemporaria.empilhar(carta);

        int tamanhoPreenchido = pilhaTemporaria.getTamanhoPreenchido();
        for (int i= 0; i< tamanhoPreenchido;i ++){
            Carta c = pilhaTemporaria.desempilhar();
            this.baralho.empilhar(c);
        }

    }

    // Métodos de alto nível para manipular cartas na pilha
    private Carta retirarCartaPilhaDeJogo(int pilhaDeJogoID){

        int posicao = 0;
        for (int i = 0; i < pilhaDeJogoID +1; i++) {
            posicao += this.tamanhoPilhaDeJogoArray[i];
        }

        if (this.tamanhoPilhaDeJogoArray[pilhaDeJogoID] == 0) {
            return null;
        }

        Carta cartaASerRetirada = this.retirarCarta(posicao);
        this.tamanhoPilhaDeJogoArray[pilhaDeJogoID] -= 1;
        return cartaASerRetirada;
    }

    private Carta retirarCartaFreecell(int freecellID){

        Carta carta = this.freeCellArray[freecellID];
        this.freeCellArray[freecellID] = null;
        return carta;

    }

    private void adicionarCartaPilhaDeJogo(Carta carta , int pilhaDeJogoID){

        int posicao = 0;
        for (int i = 0; i < pilhaDeJogoID +1; i++) {
            posicao += this.tamanhoPilhaDeJogoArray[i];
        }

        posicao = posicao +1;

        this.adicionarCarta(carta, posicao);
        this.tamanhoPilhaDeJogoArray[pilhaDeJogoID] += 1;

    }

    private void adicionarCartaPilhaSaida(Carta carta, int pilhaSaidaID){


        int posicao = 0;
        for (int j : this.tamanhoPilhaDeJogoArray) {
            posicao += j;
        }

        for (int i = 0; i < pilhaSaidaID +1; i++) {
            posicao += this.tamanhoPilhaSaidaArray[i];
        }

        posicao = posicao +1;

        this.adicionarCarta(carta, posicao);
        this.tamanhoPilhaSaidaArray[pilhaSaidaID] += 1;

    }

    // Métodos para mover cartas entre as pilhas do jogo / freecells

    private void moverCartaEntrePilhasDeJogo(int pilhaDeJogoIDOrigem, int pilhaDeJogoIDDestino){

        Carta carta = this.retirarCartaPilhaDeJogo(pilhaDeJogoIDOrigem);
        this.adicionarCartaPilhaDeJogo( carta, pilhaDeJogoIDDestino);

    }

    private void moverCartaDeFreecellParaPilhaDejogo(int freecellID, int pilhaDeJogoIDDestino){

        Carta carta = this.retirarCartaFreecell(freecellID);
        if (carta != null){
            this.adicionarCartaPilhaDeJogo( carta, pilhaDeJogoIDDestino);
        }

    }


    private void moverCartaDePilhaDeJogoParaPilhaDeSaida(int pilhaDeJogoIDOrigem){

        Carta carta = this.retirarCartaPilhaDeJogo(pilhaDeJogoIDOrigem);
        int pilhaDeSaidaID = carta.getNaipe().getNaipeID();
        this.adicionarCartaPilhaSaida( carta, pilhaDeSaidaID);

    }

    private void moverCartaDeFreecellParaPilhaDeSaida(int freecellIDOrigem){

        Carta carta = this.retirarCartaFreecell(freecellIDOrigem);
        int pilhaDeSaidaID = carta.getNaipe().getNaipeID();
        this.adicionarCartaPilhaSaida( carta, pilhaDeSaidaID);

    }

    public void moverCartaDePilhaDeJogoParaFreecell(int pilhaDeJogoOrigemID){

        Carta carta = this.retirarCartaPilhaDeJogo(pilhaDeJogoOrigemID);
        this.moverCartaParaFreecell(carta);

    }

    public void moverCartaParaFreecell(Carta carta){

        for (int i = 0; i < this.freeCellArray.length; i++){

            if (this.freeCellArray[i] == null){
                this.freeCellArray[i] = carta;
                return;
            }

        }

    }


}
