
public class Carta {

    private int ordem;
    private String simbolo;
    private Naipe naipe;

    public Carta(int ordem, Naipe naipe) {
        this.setOrdem(ordem);
        this.setNaipe(naipe);
        this.identificarSimbolo();

    }

    private void setOrdem(int ordem){
        this.ordem = ordem;
    }

    private void setNaipe(Naipe naipe){
        this.naipe = naipe;
    }

    public Naipe getNaipe(){
        return this.naipe;
    }

    public String nomeCarta(){

        return this.simbolo +" de "+ this.naipe.getSimbolo();
    }

    public String nomeCartaPadronizado(){

        String simbolo = this.simbolo;
        String naipe = this.naipe.getSimbolo();

        if (simbolo.length()==1){
            simbolo = " " + this.simbolo;
        }

        if (naipe.length()==5){
            naipe += " ";
        }

        if (naipe.length()==4){
            naipe += "  ";
        }

        return simbolo +" de "+ naipe;
    }

    public String representacaoVisualCarta(){

        int tamanhoLinha = 16;
        StringBuilder representacaoVisualCarta = new StringBuilder();

        representacaoVisualCarta.append("_".repeat(tamanhoLinha));
        representacaoVisualCarta.append("\n");
        representacaoVisualCarta.append("|");

        String nomeCarta =  this.nomeCarta();
        representacaoVisualCarta.append(" ".repeat(Math.max(0, tamanhoLinha - nomeCarta.length() - 2)));

        representacaoVisualCarta.append(nomeCarta());

        representacaoVisualCarta.append("|");
        representacaoVisualCarta.append("\n");
        representacaoVisualCarta.append("_".repeat(tamanhoLinha));
        representacaoVisualCarta.append("\n");

        return representacaoVisualCarta.toString();

    }

    public void printarCarta(){

        System.out.print(this.representacaoVisualCarta());

    }

    public boolean eIgual(Carta c){

        return this.naipe.getNaipeID() == c.naipe.getNaipeID() & this.ordem == c.ordem;
    }

    public boolean naipeEInvertido(Carta c){

        return this.naipe.getNaipeType() != c.naipe.getNaipeType();
    }

    public boolean maiorOrdem(Carta c) {
        return this.ordem > c.ordem;
    }

    public boolean cartaConsecutiva(Carta c) {
        return this.ordem == c.ordem -1;
    }

    private void identificarSimbolo() {

        if ( this.ordem == 1){
            this.simbolo = "A";
            return;
        }

        else if (this.ordem <= 10){
            this.simbolo = Integer.toString(this.ordem);
            return;
        }

        switch (this.ordem) {
            case 11 -> this.simbolo = "J";
            case 12 -> this.simbolo = "Q";
            case 13 -> this.simbolo = "K";
            default -> this.simbolo = Integer.toString(this.ordem);
        }

    }

    public int getOrdem() {
        return this.ordem;
    }
}
