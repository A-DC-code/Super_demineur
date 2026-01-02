/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
import javax.swing.JButton;
import java.awt.GridLayout;
import javax.swing.JOptionPane;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.SwingUtilities;
import javax.swing.Timer;




public class interface_graphique extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(interface_graphique.class.getName());
    private GrilleDeJeu grille;
    private JButton[][] boutons;
    private int nbLignes;
    private int nbColonnes;
    private int nbBombes;
    private int drapeauxRestants;
    private Timer timerChrono;
    private int secondesEcoulees = 0;
    private boolean jeuDemarre = false;
    private static final int TAILLE_PLATEAU = 480; 
    private static final int ESPACE_CASES = 1;     
    private static final int TAILLE_MIN_CASE = 18; 
    private boolean partieTerminee = false;
    
    
    
    /**
   
     */
    public interface_graphique(int nbLignes, int nbColonnes, int nbBombes) {
        initComponents();
        this.nbLignes = nbLignes;
        this.nbColonnes = nbColonnes;
        this.nbBombes = nbBombes;
        nbr_de_bombe.setText("nombre de Bombes: " + nbBombes);
        drapeauxRestants = nbBombes;
        drapeaux.setText("Drapeaux  : " + drapeauxRestants);
        initialiserJeu();
        bouton_recommencer.setVisible(false);
    }
    

    private void initialiserJeu() {
        
        
        grille = new GrilleDeJeu(nbLignes, nbColonnes, nbBombes);
        
        // cette commande nous sert a attendre le debut du jeux pour avoir les emplacements des bombes
        
        jeuDemarre = false;
        partieTerminee = false;
        drapeauxRestants = nbBombes;
        drapeaux.setText("Drapeaux  : " + drapeauxRestants);
        reinitialiserChrono();

        PanneauGrille.removeAll();
        int tailleCase = calculerTailleCase();

        
        PanneauGrille.setLayout(new GridLayout(nbLignes, nbColonnes, ESPACE_CASES, ESPACE_CASES));

        
        PanneauGrille.setSize(TAILLE_PLATEAU, TAILLE_PLATEAU);
        PanneauGrille.setPreferredSize(new java.awt.Dimension(TAILLE_PLATEAU, TAILLE_PLATEAU));

        PanneauGrille.setBounds(PanneauGrille.getX(), PanneauGrille.getY(), TAILLE_PLATEAU, TAILLE_PLATEAU);

        boutons = new JButton[nbLignes][nbColonnes];
        for (int i = 0; i < nbLignes; i++) {
            for (int j = 0; j < nbColonnes; j++) {
                JButton bouton = new JButton("?");
                bouton.setPreferredSize(new java.awt.Dimension(tailleCase, tailleCase));
                bouton.setMargin(new java.awt.Insets(0, 0, 0, 0));
                bouton.setBorderPainted(false);
                bouton.setFocusPainted(false);
                bouton.setContentAreaFilled(true);

                // police automatique selon taille
                int taillePolice = Math.max(12, tailleCase / 2);
                bouton.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, taillePolice));


                final int ligne = i;
                final int colonne = j;

                bouton.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent evenementSouris) {

                        if (SwingUtilities.isRightMouseButton(evenementSouris)) {
                            basculerDrapeauInterface(ligne, colonne);
                            return;
                        }

                        if (SwingUtilities.isLeftMouseButton(evenementSouris)) {
                            clicCellule(ligne, colonne);
                        }
                    }
                });

                boutons[i][j] = bouton;
                PanneauGrille.add(bouton);
            }
        }

        PanneauGrille.revalidate();
        PanneauGrille.repaint();
        pack();
        setLocationRelativeTo(null);
    }
    private void animationRevelationBombesProgressive() {
        java.util.List<int[]> positionsBombes = new java.util.ArrayList<>();

        for (int ligne = 0; ligne < nbLignes; ligne++) {
            for (int colonne = 0; colonne < nbColonnes; colonne++) {
                if (grille.getPresenceBombe(ligne, colonne)) {
                    positionsBombes.add(new int[]{ligne, colonne});
                }
            }
        }

        javax.swing.Timer timer = new javax.swing.Timer(40, null);
        final int[] index = {0};

        timer.addActionListener(e -> {
            if (index[0] >= positionsBombes.size()) {
                timer.stop();
                return;
            }

            int[] pos = positionsBombes.get(index[0]);
            int ligne = pos[0];
            int colonne = pos[1];

            boutons[ligne][colonne].setText("B");
            boutons[ligne][colonne].setBackground(Color.RED);

            index[0]++;
        });

        timer.start();
    }
    
    private int calculerTailleCase() {
        int taille = (TAILLE_PLATEAU - (nbColonnes - 1) * ESPACE_CASES) / nbColonnes;
        if (taille < TAILLE_MIN_CASE) {
            taille = TAILLE_MIN_CASE;
        }
        return taille;
    }
    
    private void reinitialiserChrono() {
        secondesEcoulees = 0;
        temps.setText("Temps : 0 s");

        if (timerChrono != null) {
            timerChrono.stop();
        }

        timerChrono = new Timer(1000, e -> {
            secondesEcoulees++;
            temps.setText("Temps : " + secondesEcoulees + " s");
        });
    }

    private void demarrerChrono() {
        if (timerChrono != null && !timerChrono.isRunning()) {
            timerChrono.start();
        }
    }

    private void arreterChrono() {
        if (timerChrono != null) {
            timerChrono.stop();
        }
    }

    
    private void clicCellule(int ligne, int colonne) {
        
        if (partieTerminee) return;
        if (!jeuDemarre) {
            grille.placerBombesAleatoirementEnEvitantZone(ligne, colonne);
            grille.calculerBombesAdjacentes();
            jeuDemarre = true;
            demarrerChrono();
            
        }
        if (grille.getPresenceDrapeau(ligne, colonne)) {
            return;
        }
        grille.revelerCellule(ligne, colonne);
        mettreAJourAffichage();

        if (grille.getPresenceBombe(ligne, colonne)) {
            JOptionPane.showMessageDialog(this, "Bombe ! Partie perdue");
            animationRevelationBombesProgressive();           
            partieTerminee = true;
            arreterChrono();
            desactiverTousBoutons();
            bouton_recommencer.setVisible(true);
        } else if (grille.toutesCellulesRevelees()) {
            JOptionPane.showMessageDialog(this, "Victoire !");
            partieTerminee = true;
            arreterChrono();
            desactiverTousBoutons();
            bouton_recommencer.setVisible(true);
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
                else if (val.equals("F")) {
                    boutons[i][j].setBackground(Color.ORANGE);
                }

                
                if (!val.equals("?") && !val.equals("F")) {
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
    
    
    private void basculerDrapeauInterface(int ligne, int colonne) {

        if (partieTerminee) return;
        if (!boutons[ligne][colonne].isEnabled()) return;
        
        boolean drapeauAvant = grille.getPresenceDrapeau(ligne, colonne);

        // si on veut poser un drapeau mais il n'en reste plus => STOP (!descendre sous 0)
        if (!drapeauAvant && drapeauxRestants == 0) return;

        grille.basculerDrapeau(ligne, colonne);

        boolean drapeauApres = grille.getPresenceDrapeau(ligne, colonne);

        if (!drapeauAvant && drapeauApres) {
            drapeauxRestants--;
        } else if (drapeauAvant && !drapeauApres) {
            drapeauxRestants++;
        }

        if (drapeauxRestants < 0) drapeauxRestants = 0;
        if (drapeauxRestants > nbBombes) drapeauxRestants = nbBombes;

        drapeaux.setText("Drapeaux : " + drapeauxRestants);

        mettreAJourAffichage();
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
        bouton_recommencer = new javax.swing.JButton();
        nbr_de_bombe = new javax.swing.JLabel();
        drapeaux = new javax.swing.JLabel();
        temps = new javax.swing.JLabel();
        bouton_menu = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        PanneauGrille.setBackground(new java.awt.Color(102, 255, 102));
        PanneauGrille.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        PanneauGrille.setLayout(new java.awt.GridLayout(10, 10, 25, 25));
        getContentPane().add(PanneauGrille, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 80, 420, 420));

        bouton_recommencer.setText("Recommencer");
        bouton_recommencer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bouton_recommencerActionPerformed(evt);
            }
        });
        getContentPane().add(bouton_recommencer, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 580, -1, -1));

        nbr_de_bombe.setText("Nombre de Bombes : ");
        getContentPane().add(nbr_de_bombe, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 50, -1, -1));

        drapeaux.setText("Drapeaux");
        getContentPane().add(drapeaux, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 50, -1, -1));

        temps.setText("temps");
        getContentPane().add(temps, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 50, -1, -1));

        bouton_menu.setText("Menu");
        bouton_menu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bouton_menuActionPerformed(evt);
            }
        });
        getContentPane().add(bouton_menu, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 250, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void bouton_recommencerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bouton_recommencerActionPerformed
        initialiserJeu();
        bouton_recommencer.setVisible(false);
    }//GEN-LAST:event_bouton_recommencerActionPerformed

    private void bouton_menuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bouton_menuActionPerformed
        arreterChrono();                 
        new AccueilDemineur().setVisible(true);  
        this.dispose();                  
    }//GEN-LAST:event_bouton_menuActionPerformed

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanneauGrille;
    private javax.swing.JButton bouton_menu;
    private javax.swing.JButton bouton_recommencer;
    private javax.swing.JLabel drapeaux;
    private javax.swing.JLabel nbr_de_bombe;
    private javax.swing.JLabel temps;
    // End of variables declaration//GEN-END:variables

}
