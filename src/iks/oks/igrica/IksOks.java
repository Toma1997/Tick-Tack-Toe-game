package iks.oks.igrica;

public class IksOks {

    // Incijalizacija igraca i dodela karaktera
    static final char PRAZNO = ' ';
    static final char KOMP = 'X';
    static final char COVEK = 'O';

    // Inicijalizacija opcija koje ce program proveravati
    static final int COVEK_POBEDNIK = 0;
    static final int NERESENO = 1;
    static final int NEJASNO = 2;
    static final int KOMP_POBEDNIK = 3;

    private char[][] tabla; // tabla za igranje    

    // Konstruktor
    public IksOks() {
        tabla = new char[3][3];
        praznaTabla();
    }

    // Definisanje prazne table
    public void praznaTabla() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                tabla[i][j] = PRAZNO;
            }
        }
    }

    // Prikazivanje table
    public void prikaziTablu() {
        System.out.println();
        System.out.println("    0   1   2");
        System.out.println(" -------------");
        for (int i = 0; i < 3; i++) {
            System.out.print(i);
            System.out.print(" |");

            for (int j = 0; j < 3; j++) {
                System.out.print(" " + tabla[i][j] + " |");
            }

            System.out.println();
            System.out.println(" -------------");
        }
    }

    // Proveravanje isporavnosti datog poteza
    public boolean ispravanPotez(int i, int j) { // unosimo broj reda i kolone(presek)
        if (i < 0 || i >= 3 || j < 0 || j >= 3 || tabla[i][j] != PRAZNO) {
            return false;
        } else {
            return true;
        }
    }

    // Proveri da li je dato polje prazno
    public boolean praznoPolje(int i, int j) {
        return tabla[i][j] == PRAZNO;
    }

    // Provera da li su sva polja popunjena
    public boolean punaTabla() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (praznoPolje(i, j)) {
                    return false;
                }
            }
        }
        return true;
    }

    // Odredjivanje da li je igrac pobednik
    public boolean pobeda(char igrac) {
        int i, j; // redovi i kolone(namerno definisani van petlje da bi se vrsila provera pobede)

        // Pregledati sve redove
        for (i = 0; i < 3; i++) {
            for (j = 0; j < 3; j++) {
                if (tabla[i][j] != igrac) {
                    break;
                }
            }
            if (j >= 3) { // ako se j inkrementirao na 3 posle druge petlje
                return true; // jedan red je pun svim X ili O         
            }
        }

        // Pregledati sve kolone
        for (j = 0; j < 3; j++) {
            for (i = 0; i < 3; i++) {
                if (tabla[i][j] != igrac) {
                    break;
                }
            }
            if (i >= 3) { // ako se i inkrementirao na 3 posle druge petlje
                return true; // jedna kolona je puna svim X ili O         
            }
        }

        // Pregledati obe dijagonale
        if (tabla[0][0] == igrac && tabla[1][1] == igrac && tabla[2][2] == igrac) {
            return true;
        }
        if (tabla[0][2] == igrac && tabla[1][1] == igrac && tabla[2][0] == igrac) {
            return true;
        }

        return false;
    }

    // Odigravanje jednog poteza bez provere ispravnosti
    public void odigrajPotez(int i, int j, char igrac) {
        tabla[i][j] = igrac;
    }

    // Izracunavanje vrednosti trenutnog stanja
    public int rezultatStanja() {
        return pobeda(KOMP) ? KOMP_POBEDNIK
                : // kratka forma IF - ELSE za return ( ? -> if , : -> else)
                pobeda(COVEK) ? COVEK_POBEDNIK
                : punaTabla() ? NERESENO : NEJASNO;
    }

    // Nalazenje najboljeg poteza igraca za trenutnu poziciju
    public Potez najboljiPotez(char igrac) {
        char protivnik; // protivnicki igrac
        Potez najPotProtiv; // najbolji potez protivnika
        int ni = 0, nj = 0; // koordinate najboljeg poteza (inicijalizacija)
        int privremeniRezultat; // (privremeni) rezultat datog stanja

        privremeniRezultat = rezultatStanja();

        if (privremeniRezultat != NEJASNO) { // zavrsno stanje (ili je nereseno ili je neko pobedio)
            return new Potez(privremeniRezultat, -1, -1); // vrati nepostojeci potez jer je igra gotova
        }

        if (igrac == KOMP) { // trenutni igrac je kompjuter
            protivnik = COVEK; // protivnik je korisnik
            privremeniRezultat = COVEK_POBEDNIK; // inicijalizacija da je korsnik pobednik
        } else { // trenutni igrac je korisnik
            protivnik = KOMP; // protivnik je kompjuter
            privremeniRezultat = KOMP_POBEDNIK; // inicijalizacija da je kompjuter pobednik
        }

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (praznoPolje(i, j)) {
                    odigrajPotez(i, j, igrac); // pokusaj potez
                    najPotProtiv = najboljiPotez(protivnik); // rekurzivno se trazi nabolji potez protivnika
                    // i cuva ga da bi smo nasli za nas najbolji potez
                    odigrajPotez(i, j, PRAZNO); // vrati potez

                    if ((igrac == KOMP && najPotProtiv.vrednost() > privremeniRezultat)
                            || (igrac == COVEK && najPotProtiv.vrednost() < privremeniRezultat)) {

                        privremeniRezultat = najPotProtiv.vrednost(); // Zapamti najbolji dosadasnji rezultat stanja
                        ni = i; // red najboljeg poteza
                        nj = j; // kolona najboljeg poteza
                    }
                }
            }
        }
        return new Potez(privremeniRezultat, ni, nj);
    }

}
