/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Scene;

/**
 *
 * @author htrefftz
 */
public class AmbientLight {
    Colour color;

    /**
     * Constructor
     * @param color Colour of the ambient light 
     */
    public AmbientLight(Colour color) {
        this.color = color;
    }    
}
