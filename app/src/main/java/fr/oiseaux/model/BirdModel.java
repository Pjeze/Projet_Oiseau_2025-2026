package fr.oiseaux.model;

import java.util.List;

public interface BirdModel {
    
    int getBirdNumber();
    List<Bird> getBirds();
    void setBirdNumber(int n);
    void updateMovement();

    void setBoundaryMode(BoundaryMode mode); 
    BoundaryMode getBoundaryMode();
    

}
