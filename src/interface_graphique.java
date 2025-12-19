/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
import javax.swing.JButton;
import java.awt.GridLayout;
import java.awt.Font;
import javax.swing.JOptionPane;
import java.awt.Color;


public class interface_graphique extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(interface_graphique.class.getName());
    private GrilleDeJeu grille;
    private JButton[][] boutons;
    private int nbLignes;
    private int nbColonnes;
    private int nbBombes;
    private int bombesRestantes; // Ajouté pour suivre les bombes restantes (non utilisé ici car pas de drapeaux, mais peut être étendu)

    /**
   
     */
    public interface_graphique(int nbLignes, int nbColonnes, int nbBombes) {
        initComponents();
        this.nbLignes = nbLignes;
        this.nbColonnes = nbColonnes;
        this.nbBombes = nbBombes;
        this.bombesRestantes = nbBombes; // Initialisation
        jLabel1.setText("Bombes restantes: " + bombesRestantes); // Mise à jour du label
    }

    private void initialiserJeu() {
        grille = new GrilleDeJeu(nbLignes, nbColonnes, nbBombes);
        grille.placerBombesAleatoirement();
        grille.calculerBombesAdjacentes();

        PanneauGrille.removeAll();
        PanneauGrille.setLayout(new GridLayout(nbLignes, nbColonnes)); // Suppression des gaps pour cohérence, ou ajoutez-les si souhaité

        boutons = new JButton[nbLignes][nbColonnes];

        for (int i = 0; i < nbLignes; i++) {
            for (int j = 0; j < nbColonnes; j++) {
                JButton bouton = new JButton("?");
                bouton.setFont(new Font("Arial", Font.BOLD, 16));

                final int ligne = i;
                final int colonne = j;

                bouton.addActionListener(e -> clicCellule(ligne, colonne));

                boutons[i][j] = bouton;
                PanneauGrille.add(bouton);
            }
        }

        PanneauGrille.revalidate();
        PanneauGrille.repaint();
    }

    private void clicCellule(int ligne, int colonne) {
        grille.revelerCellule(ligne, colonne);
        mettreAJourAffichage();

        if (grille.getPresenceBombe(ligne, colonne)) {
            JOptionPane.showMessageDialog(this, "Bombe ! Partie perdue");
            afficherToutesLesBombes();
            desactiverTousBoutons(); // Désactiver après défaite
        } else if (grille.toutesCellulesRevelees()) {
            JOptionPane.showMessageDialog(this, "Victoire !");
            desactiverTousBoutons(); // Désactiver après victoire
        }
    }

    private void mettreAJourAffichage() {
        for (int i = 0; i < nbLignes; i++) {
            for (int j = 0; j < nbColonnes; j++) {
                String val = grille.getAffichageCellule(i, j);
                boutons[i][j].setText(val);

                if (val.equals("?")) {
                    boutons[i][j].setBackground(new Color(100, 255, 100));
                } else if (val.equals("B")) {
                    boutons[i][j].setBackground(Color.RED);
                } else if (val.equals("0")) { // Gérer les cellules vides (0 bombes adjacentes)
                    boutons[i][j].setText(""); // Texte vide
                    boutons[i][j].setBackground(Color.WHITE);
                } else if (val.equals("1")) {
                    boutons[i][j].setBackground(new Color(0, 255, 255));
                } else if (val.equals("2")) {
                    boutons[i][j].setBackground(new Color(0, 200, 255));
                } else if (val.equals("3")) {
                    boutons[i][j].setBackground(new Color(0, 160, 255));
                } else if (val.equals("4")) {
                    boutons[i][j].setBackground(new Color(0, 50, 255));
                } else if (val.equals("5")) {
                    boutons[i][j].setBackground(new Color(80, 0, 255));
                } else if (val.equals("6")) {
                    boutons[i][j].setBackground(new Color(255, 0, 165));
                } else if (val.equals("7")) {
                    boutons[i][j].setBackground(new Color(255, 0, 255));
                } else if (val.equals("8")) {
                    boutons[i][j].setBackground(new Color(255, 0, 100));
                }

                // Désactiver le clic si révélé
                if (!val.equals("?")) {
                    boutons[i][j].setEnabled(false);
                }
            }
        }
    }

   
    private void afficherToutesLesBombes() {
        for (int i = 0; i < nbLignes; i++) {
            for (int j = 0; j < nbColonnes; j++) {
                if (grille.getPresenceBombe(i, j)) {
                    boutons[i][j].setText("B");
                    boutons[i][j].setBackground(Color.RED);
                    boutons[i][j].setEnabled(false);
                }
            }
        }
    }

    // Méthode ajoutée pour désactiver tous les boutons après fin de partie
    private void desactiverTousBoutons() {
        for (int i = 0; i < nbLignes; i++) {
            for (int j = 0; j < nbColonnes; j++) {
                boutons[i][j].setEnabled(false);
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        PanneauGrille = new javax.swing.JPanel();
        bouton_commencer = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        PanneauGrille.setBackground(new java.awt.Color(0, 153, 0));
        PanneauGrille.setLayout(new java.awt.GridLayout(10, 10, 25, 25));
        getContentPane().add(PanneauGrille, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 10, 550, 550));

        bouton_commencer.setText("commencer");
        bouton_commencer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bouton_commencerActionPerformed(evt);
            }
        });
        getContentPane().add(bouton_commencer, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 580, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void bouton_commencerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bouton_commencerActionPerformed
initialiserJeu();
    }//GEN-LAST:event_bouton_commencerActionPerformed

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanneauGrille;
    private javax.swing.JButton bouton_commencer;
    // End of variables declaration//GEN-END:variables

}