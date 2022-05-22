public class Naipe {

    private int naipeID;

    public Naipe(int naipeID){
        this.setNaipeID(naipeID);
    }

    private void setNaipeID(int naipeID){
        this.naipeID = naipeID;
    }

    public int getNaipeID(){
        return this.naipeID;
    }

    public String getSimbolo(){

        return switch (this.naipeID) {
            case 0 -> "Paus";
            case 1 -> "Copas";
            case 2 -> "Espada";
            case 3 -> "Ouros";
            default -> "Naipe " + this.naipeID;
        };
    }

    public int getNaipeType(){
        return (this.naipeID+1) % 2;
    }

}
