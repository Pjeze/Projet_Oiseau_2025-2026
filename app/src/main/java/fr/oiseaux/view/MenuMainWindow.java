package fr.oiseaux.view;

import java.awt.Menu;
import java.awt.MenuItem;

public class MenuMainWindow extends java.awt.MenuBar {
    
    public MenuItem menuBoids, menuVicsek;

    public MenuMainWindow () {

        Menu menuModel = new Menu("Model");
        menuBoids = new MenuItem("Boids");
        menuVicsek = new MenuItem("Vicsek");

        menuModel.add(menuBoids);
        menuModel.addSeparator();
        menuModel.add(menuVicsek);

        add(menuModel);

    }

    public MenuItem getMenuBoids() {
        return this.menuBoids;
    }

    public MenuItem getMenuVicsek() {
        return this.menuVicsek;
    }
}
