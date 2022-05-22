import javax.lang.model.type.NullType;

public class Pilha {

    private int tamanho;
    private int topo;
    private Carta[] baralhoEmpilhado;

    public Pilha(int tamanho){
        this.tamanho = tamanho;
        this.baralhoEmpilhado = new Carta[this.tamanho];
        this.topo = -1;

    }

    public int getTamanho() {
        return this.tamanho;
    }

    public int getTopo(){
        return this.topo;
    }

    public int getTamanhoPreenchido(){
        return this.topo +1;
    }

    public boolean empilhar(Carta carta){

        if (this.topo +1 >= this.tamanho){
            System.out.println("Erro ao empilhar! Pilha cheia");
            return false;
        }

        this.topo++;
        this.baralhoEmpilhado[this.topo] = carta;
        return true;
    }

    public Carta desempilhar(){
        Carta cartaNull = null;
        if (this.topo < 0){
            System.out.println("Erro ao desempilhar! Pilha vazia");
            return cartaNull;
        }

        Carta carta = this.baralhoEmpilhado[this.topo];
        this.baralhoEmpilhado[this.topo] = cartaNull;
        this.topo --;

        return carta;

    }
}
