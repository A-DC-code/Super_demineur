/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author zouhaib mahamoud
 */
public class Cellule {


    private boolean presenceBombe;
    private boolean devoilee;
    private int nbBombesAdjacentes;

    private boolean presenceDrapeau;


    public Cellule() {
        this.presenceBombe = false;
        this.devoilee = false;
        this.nbBombesAdjacentes = 0;

       
        this.presenceDrapeau = false;
    }

  
    public boolean getPresenceBombe() {
        return presenceBombe;
    }

    public int getNbBombesAdjacentes() {
        return nbBombesAdjacentes;
    }


    public boolean getPresenceDrapeau() {
        return presenceDrapeau;
    }


    public void setNbBombesAdjacentes(int nb) {
        this.nbBombesAdjacentes = nb;
    }

    
    public void placerBombe() {
        this.presenceBombe = true;
    }

    public void revelerCellule() {
        
        if (presenceDrapeau) {
            return;
        }
        this.devoilee = true;
    }

  
    public void basculerDrapeau() {
        if (devoilee) {
            return;
        }
        presenceDrapeau = !presenceDrapeau;
    }


    @Override
    public String toString() {

        // Cellule non dévoilée
        if (!devoilee) {
            if (presenceDrapeau) {
                return "F"; // Flag (drapeau)
            }
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

        // Cellule vide 
        return "0";
    }




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

