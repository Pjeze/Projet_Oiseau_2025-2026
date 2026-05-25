package fr.oiseaux.view;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class MenuMainWindow extends JMenuBar {
    
    public JMenuItem menuBoids, menuVicsek;

    public MenuMainWindow () {

        JMenu menuModel = new JMenu("Model");
        menuBoids = new JMenuItem("Boids");
        menuVicsek = new JMenuItem("Vicsek");

        menuModel.add(menuBoids);
        menuModel.addSeparator();
        menuModel.add(menuVicsek);

        add(menuModel);

    }

    public JMenuItem getMenuBoids() { return this.menuBoids; }
    public JMenuItem getMenuVicsek() { return this.menuVicsek; }
}
