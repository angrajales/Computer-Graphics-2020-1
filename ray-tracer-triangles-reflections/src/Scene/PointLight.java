/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Scene;

import Math.Point;

/**
 *
 * @author htrefftz
 */
public class PointLight {
    Point point;
    Colour color;

    /**
     * Constructor
     * @param point Place of the light in the scene
     * @param color Colour of the light
     */
    public PointLight(Point point, Colour color) {
        this.point = point;
        this.color = color;
    }
        
}
