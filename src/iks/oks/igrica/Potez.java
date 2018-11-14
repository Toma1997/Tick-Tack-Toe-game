package iks.oks.igrica;

public class Potez {

    private int i; // red poteza
    private int j; // kolona poteza
    private int v; // vrednost stanja s tim potezom

    public Potez(int v, int i, int j) {
        this.i = i;
        this.j = j;
        this.v = v;
    }
    
    public int red(){
        return i;
    }
    
    public int kolona(){
        return j;
    }
    
    public int vrednost(){
        return v;
    }
    
}
