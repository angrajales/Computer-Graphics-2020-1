/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Math;

import Scene.Material;
import Scene.Colour;

/**
 *
 * @author htrefftz
 */
public interface Intersectable {
    /**
     * Return an array with the intersections between this object and the
     * given ray
     * @param ray Ray to intersect the object with
     * @return Array of intersections. If no intersections are found the
     * first element in the array is 0. The other values are "u" values in
     * (value of the parameter) in the ray (which is a parametric equation of
     * line segment)
     */
    public Solutions intersect(Ray ray);
    /**
     * Return the material of this object
     * @return Material of this object
     */
    public Material getMaterial();
    /**
     * Compute the normal at he point of intersection and call the shader
     * to compute the color of the object
     * @param ray Ray that intersects the object
     * @param minT Parameter of the ray at the intersection point
     * @return Color at the point of intersection
     */
    public Colour callShader(Ray ray, double minT);
    /**
     * Given a point on the object's surface, compute the normal of the 
     * object at that point
     * @param p Point at the surface of the object
     * @return Normal at the point
     */
    public Vector4 computeNormal(Point p);
    
    /**
     * Given a point on the object's surface, compute the uv coordinates
     * of the surface, for texture purposes
     * @param p Point at the surface of the object
     * @return uv coordinates of the point
     */
    public UVCoordinates computeUV(Point p);
}
