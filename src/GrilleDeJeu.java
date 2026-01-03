/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author zouhaib mahamoud
 */
import java.util.Random;

public class GrilleDeJeu {

    // -----------------------------
    // ATTRIBUTS
    // -----------------------------
    private Cellule[][] matriceCellules;
    private int nbLignes;
    private int nbColonnes;
    private int nbBombes;

    // -----------------------------
    // CONSTRUCTEUR
    // -----------------------------
    public GrilleDeJeu(int nbLignes, int nbColonnes, int nbBombes) {
        this.nbLignes = nbLignes;
        this.nbColonnes = nbColonnes;
        this.nbBombes = nbBombes;

        // Création de la matrice + cellules
        matriceCellules = new Cellule[nbLignes][nbColonnes];
        for (int i = 0; i < nbLignes; i++) {
            for (int j = 0; j < nbColonnes; j++) {
                matriceCellules[i][j] = new Cellule();
            }
        }
    }

    // -----------------------------
    // GETTERS
    // -----------------------------
    public int getNbLignes() {
        return nbLignes;
    }

    public int getNbColonnes() {
        return nbColonnes;
    }

    public int getNbBombes() {
        return nbBombes;
    }

    // -----------------------------
    // PLACEMENT ALEATOIRE DES BOMBES
    // -----------------------------
    public void placerBombesAleatoirement() {
        Random r = new Random();
        int bombesPlacees = 0;

        while (bombesPlacees < nbBombes) {
            int i = r.nextInt(nbLignes);
            int j = r.nextInt(nbColonnes);

            // Si pas déjà une bombe, on place
            if (!matriceCellules[i][j].getPresenceBombe()) {
                matriceCellules[i][j].placerBombe();
                bombesPlacees++;
            }
        }
    }

    // -----------------------------
    // CALCUL DES BOMBES ADJACENTES
    // -----------------------------
    public void calculerBombesAdjacentes() {

        for (int i = 0; i < nbLignes; i++) {
            for (int j = 0; j < nbColonnes; j++) {

                // Si bombe : on peut laisser nbBombesAdjacentes à 0
                if (matriceCellules[i][j].getPresenceBombe()) {
                    continue;
                }

                int compteur = 0;

                // Parcours des voisins (8 directions)
                for (int x = i - 1; x <= i + 1; x++) {
                    for (int y = j - 1; y <= j + 1; y++) {

                        // ignorer la cellule elle-même
                        if (x == i && y == j) continue;

                        // vérifier limites
                        if (x >= 0 && x < nbLignes && y >= 0 && y < nbColonnes) {
                            if (matriceCellules[x][y].getPresenceBombe()) {
                                compteur++;
                            }
                        }
                    }
                }

                matriceCellules[i][j].setNbBombesAdjacentes(compteur);
            }
        }
    }


    public void revelerCellule(int ligne, int colonne) {

        // Vérifier limites
        if (ligne < 0 || ligne >= nbLignes || colonne < 0 || colonne >= nbColonnes) {
            return;
        }

        Cellule c = matriceCellules[ligne][colonne];

        // Si déjà dévoilée, on stop
        // (on détecte ça via son affichage : si ce n'est pas "?" alors elle est dévoilée)
        if (!c.toString().equals("?")) {
            return;
        }

        // Révéler la cellule
        c.revelerCellule();

        // Si bombe : on ne propage pas
        if (c.getPresenceBombe()) {
            return;
        }

        // Si 0 bombe adjacente : propagation sur voisins
        if (c.getNbBombesAdjacentes() == 0) {
            for (int x = ligne - 1; x <= ligne + 1; x++) {
                for (int y = colonne - 1; y <= colonne + 1; y++) {
                    if (x == ligne && y == colonne) continue;
                    revelerCellule(x, y);
                }
            }
        }
    }


    public boolean getPresenceBombe(int i, int j) {
        return matriceCellules[i][j].getPresenceBombe();
    }

    public boolean toutesCellulesRevelees() {
        for (int i = 0; i < nbLignes; i++) {
            for (int j = 0; j < nbColonnes; j++) {

                // Si cellule non bombe ET pas dévoilée => pas gagné
                if (!matriceCellules[i][j].getPresenceBombe()
                        && matriceCellules[i][j].toString().equals("?")) {
                    return false;
                }
            }
        }
        return true;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        // Indices colonnes
        sb.append("    ");
        for (int j = 0; j < nbColonnes; j++) {
            sb.append(j).append(" ");
        }
        sb.append("\n");

        for (int i = 0; i < nbLignes; i++) {
            // Indice ligne
            sb.append(i).append(" | ");

            for (int j = 0; j < nbColonnes; j++) {
                sb.append(matriceCellules[i][j].toString()).append(" ");
            }
            sb.append("\n");
        }

        return sb.toString();
    }
    
    public String getAffichageCellule(int i, int j) {
        return matriceCellules[i][j].toString();
    }

    public void placerBombesAleatoirementEnEvitantZone(int ligneDepart, int colonneDepart) {
        Random generateurAleatoire = new Random();
        int bombesPlacees = 0;

        while (bombesPlacees < nbBombes) {
            int ligne = generateurAleatoire.nextInt(nbLignes);
            int colonne = generateurAleatoire.nextInt(nbColonnes);

            
            boolean estDansZoneInterdite =
                    Math.abs(ligne - ligneDepart) <= 1 &&
                    Math.abs(colonne - colonneDepart) <= 1;

            if (estDansZoneInterdite) {
                continue;
            }

            if (!matriceCellules[ligne][colonne].getPresenceBombe()) {
                matriceCellules[ligne][colonne].placerBombe();
                bombesPlacees++;
            }
        }
    } 
    public void basculerDrapeau(int ligne, int colonne) {
        if (ligne < 0 || ligne >= nbLignes || colonne < 0 || colonne >= nbColonnes) {
            return;
        }
        matriceCellules[ligne][colonne].basculerDrapeau();
    }

    public boolean getPresenceDrapeau(int ligne, int colonne) {
        return matriceCellules[ligne][colonne].getPresenceDrapeau();
    }
    
    public int obtenirNombreCellulesSansBombe() {
        int total = 0;
        for (int ligne = 0; ligne < nbLignes; ligne++) {
            for (int colonne = 0; colonne < nbColonnes; colonne++) {
                if (!matriceCellules[ligne][colonne].getPresenceBombe()) {
                    total++;
                }
            }
        }
        return total;
    }

    public int obtenirNombreCellulesReveleesSansBombe() {
        int revelees = 0;
        for (int ligne = 0; ligne < nbLignes; ligne++) {
            for (int colonne = 0; colonne < nbColonnes; colonne++) {
                if (!matriceCellules[ligne][colonne].getPresenceBombe()
                        && matriceCellules[ligne][colonne].estDevoilee()) {
                    revelees++;
                }
            }
        }
        return revelees;
    }

    public int PourcentageAvancement() {
        int casesSureesTotales = (nbLignes * nbColonnes) - nbBombes;
        if (casesSureesTotales <= 0) return 0;

        int casesSureesRevelees = 0;

        for (int ligne = 0; ligne < nbLignes; ligne++) {
            for (int colonne = 0; colonne < nbColonnes; colonne++) {
                Cellule cellule = matriceCellules[ligne][colonne];

                if (!cellule.getPresenceBombe() && cellule.estDevoilee()) {
                    casesSureesRevelees++;
                }
            }
        }

        
        return (int) Math.round((casesSureesRevelees * 100.0) / casesSureesTotales);
    }



    public static void main(String[] args) {

        GrilleDeJeu g = new GrilleDeJeu(5, 5, 5);
        g.placerBombesAleatoirement();
        g.calculerBombesAdjacentes();

        System.out.println("Grille au départ :");
        System.out.println(g);

        // Test révélation
        g.revelerCellule(2, 2);
        System.out.println("Après révélation (2,2) :");
        System.out.println(g);
    }
}

