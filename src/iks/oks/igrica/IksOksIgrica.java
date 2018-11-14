package iks.oks.igrica;

import static iks.oks.igrica.IksOks.COVEK;
import static iks.oks.igrica.IksOks.COVEK_POBEDNIK;
import static iks.oks.igrica.IksOks.KOMP;
import static iks.oks.igrica.IksOks.KOMP_POBEDNIK;
import static iks.oks.igrica.IksOks.NERESENO;
import java.util.Scanner;

public class IksOksIgrica {
// SIMULACIJA IKS-OKS IGRE. STAVIO SAM DA JE PO DEFAULT KORISNIK OKS(O) A KOMPJUTER IKS(X)
// TABLA JE PRIKAZANA REDOVIMA I KOLONAMA INDEKSIRANIM 0,1,2 . IGRAMO KADA NA POLJE ODREDJENIH KOORDINATA ( PRESEK REDOVA I KOLONA) UNESEMO KARAKTER
// SVAKI PUT CE SE PRIKAZIVATI STANJE TABLE

    public static void main(String[] args) {
        int ci, cj; // koordinate covekovog poteza
        int ki, kj; // koordinate kompovog poteza
        char naPotezu; // igrac koji je na potezu da igra
        int r; // rezultat pozicije nakon odigranog poteza
        Potez potez; // izabrani najbolji potez kompa

        IksOks igra = new IksOks();
        Scanner tastatura = new Scanner(System.in);

        System.out.println("Hajde da igramo iks-oks: ja sam " + KOMP + ", ti si " + COVEK + ".");
        System.out.println("Vrste i kolone table su numerisane brojevima 0, 1, 2, ");
        System.out.println("a polja su predstavljena odgovarajucim koordinatama.");
        System.out.println("Na primer polje u sredini ima koordinate (1, 1) .");

        igra.prikaziTablu();
        System.out.println();

        System.out.println("Ja komp igram prvi (d/n) ?");

        String odg = tastatura.next();

        if (odg.equalsIgnoreCase("n")) {
            naPotezu = COVEK;
        } else {
            naPotezu = KOMP;
        }

        while (true) {
            if (naPotezu == KOMP) {
                potez = igra.najboljiPotez(KOMP);
                ki = potez.red();
                kj = potez.kolona();
                igra.odigrajPotez(ki, kj, KOMP);
                System.out.println("Moj potez: " + ki + " " + kj);
                igra.prikaziTablu();
                System.out.println();
                r = igra.rezultatStanja();

                if (r == KOMP_POBEDNIK) {
                    System.out.println("Kompjuter je pobedio!");
                    break;

                } else if (r == NERESENO) {
                    System.out.println("Nereseno je!");
                    break;
                }
                naPotezu = COVEK;
            }
           
            if(naPotezu == COVEK){
                do{
                    System.out.println("Tvoj potez: ");
                    ci = tastatura.nextInt(); // red covekovog poteza
                    cj = tastatura.nextInt(); // kolona covekovog poteza
                    
                } while (!igra.ispravanPotez(ci, cj)); // dokle god nije ispravan potez ponovi unos koordinata
                
                igra.odigrajPotez(ci, cj, COVEK);
                igra.prikaziTablu();
                System.out.println();
                r = igra.rezultatStanja();
                
                if (r == COVEK_POBEDNIK) {
                    System.out.println("Bravo pobedio si!");
                    break;

                } else if (r == NERESENO) {
                    System.out.println("Nereseno je!");
                    break;
                }
                naPotezu = KOMP;
            }       
        }
    }
}
