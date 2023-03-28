/*
 * Vec.java
 */

 /**
  * Helper class to store xy locations - Can be used as points or vectors
  * @author Ethan Kempenich
  * Template for code from https://rosettacode.org/wiki/Boids/Java
  */
public class Point {
    /** x-coordinate */
    public double x;
    /** y-coordinate */
    public double y;

    /** 
     * Creates a point
     * @param x x-coordinate
     * @param y y-coordinate
     */
    public Point(double x, double y){
        this.x = x;
        this.y = y;
    }

    /** Normalizes the point */
    public void normalize(){
        if (x != 0){
            x /= Math.abs(x);
        }
        if (y != 0){
            y /= Math.abs(y);
        }
    }

    /** 
     * Limits the point's coordinates (or magnitude if it's a vector) to some value
     * @param l value to which to limit the point's coordinates or magnitude
     */
    public void limit(double l){
        if (x > l){
            x = l;
        }else if (x < (-1) * l){
            x = (-1) * l;
        }
        if (y > l){
            y = l;
        }else if (y < (-1) * l){
            y = (-1) * l;
        }
    }

    /** 
     * Calculates the magnitude of this point used as a vector
     * @return This point's magnitude
     */
    public double mag(){
        return Math.sqrt(Math.pow(x,2) + Math.pow(y,2));
    }

    /** 
     * Calculates the dot product of this point used as a vector and another
     * @param v The other vector
     * @return Dot product of this vector and another
     */
    public double dot(Point v){
        return x * v.x + y * v.y;
    }

    /** 
     * Adds two points together
     * @param p Point to be added to this point
     */
    public void add(Point p){
        x += p.x;
        y += p.y;
    }

    /** 
     * Subtracts a point's coordinates from this point's
     * @param p Point whose coordinates are to be subtracted from this point's
     */
    public void subtract(Point p){
        x -= p.x;
        y -= p.y;
    }

    /** 
     * Subtracts one point's coordinates from another's
     * @param p1 Point whose coordinates are to be subtracted from
     * @param p2 Point whose coordinates are to be used to reduce the other point's coordinates
     */
    public static Point subtract(Point p1,Point p2){
        return new Point(p1.x - p2.x,p1.y - p2.y);
    }

    /** 
     * Multiplies this point's coordinates by a set number
     * @param val Value to be multiplied by this point
     */
    public void multiply(double val){
        x *= val;
        y *= val;
    }

    /** 
     * Divides this point's coordinates by a set number
     * @param val Value by whose coordinates this point's coordinates will be divided
     */
    public void divide(double val){
        x /= val;
        y /= val;
    }

    /** 
     * Calculates the distance between two points
     * @param v1 First point
     * @param v2 Second point
     * @return Distance between the two points
     */
    public static double distance(Point p1, Point p2){
        return Math.sqrt(Math.pow(p1.x - p2.x,2) + Math.pow(p1.y - p2.y,2));
    }

    /** 
     * Calculates the angle between two points
     * @param v1 First point
     * @param v2 Second point
     * @return Angle between the two points
     */
    public static double angleBetween(Point p1, Point p2){
        return Math.acos(p1.dot(p2) / (p1.mag() * p2.mag()));
    }

    public String toString(){
        return "["+x+","+y+"]";
    }
}