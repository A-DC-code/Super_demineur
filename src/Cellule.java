/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author zouhaib mahamoud
 */
public class Cellule {

    // -----------------------------
    // ATTRIBUTS
    // -----------------------------
    private boolean presenceBombe;
    private boolean devoilee;
    private int nbBombesAdjacentes;

    // -----------------------------
    // CONSTRUCTEUR
    // -----------------------------
    public Cellule() {
        this.presenceBombe = false;
        this.devoilee = false;
        this.nbBombesAdjacentes = 0;
    }

    // -----------------------------
    // ACCESSEURS EN LECTURE
    // -----------------------------
    public boolean getPresenceBombe() {
        return presenceBombe;
    }

    public int getNbBombesAdjacentes() {
        return nbBombesAdjacentes;
    }

    // -----------------------------
    // ACCESSEUR EN ECRITURE
    // -----------------------------
    public void setNbBombesAdjacentes(int nb) {
        this.nbBombesAdjacentes = nb;
    }

    // -----------------------------
    // METHODES
    // -----------------------------
    public void placerBombe() {
        this.presenceBombe = true;
    }

    public void revelerCellule() {
        this.devoilee = true;
    }

    // -----------------------------
    // AFFICHAGE CONSOLE
    // -----------------------------
    @Override
    public String toString() {

        // Cellule non dévoilée
        if (!devoilee) {
            return "?";
        }

        // Cellule dévoilée avec bombe
        if (presenceBombe) {
            return "B";
        }

        // Cellule dévoilée sans bombe
        if (nbBombesAdjacentes > 0) {
            return String.valueOf(nbBombesAdjacentes);
        }

        // Cellule vide (0 bombe adjacente)
        return " ";
    }

    // -----------------------------
    // MAIN DE TEST (OBLIGATOIRE)
    // -----------------------------
    public static void main(String[] args) {

        Cellule c = new Cellule();

        System.out.println("Cellule au départ : " + c);

        c.revelerCellule();
        System.out.println("Cellule révélée sans bombe : [" + c + "]");

        c = new Cellule();
        c.placerBombe();
        c.revelerCellule();
        System.out.println("Cellule avec bombe : " + c);

        c = new Cellule();
        c.setNbBombesAdjacentes(3);
        c.revelerCellule();
        System.out.println("Cellule avec 3 bombes adjacentes : " + c);
    }
}

