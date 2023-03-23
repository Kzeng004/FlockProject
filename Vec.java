/*
 * Vec.java
 */

 /**
  * Contains information about Vecs
  * @author Ethan Kempenich
  * Template for code from https://rosettacode.org/wiki/Boids/Java
  */
public class Vec {
    /** x-coordinate of the vec */
    public int x;
    /** y-coordinate of the vec */
    public int y;

    /** Creates a vec
     * @param x x-coordinate of the vec
     * @param y y-coordinate of the vec
     */
    public Vec(int x, int y){
        this.x = x;
        this.y = y;
    }

    /** Calculates the magnitude of this vec
     * @return This vec's magnitude
     */
    public double mag(){
        return Math.sqrt(Math.pow(x,2) + Math.pow(y,2));
    }

    /** Calculates the dot product of this vec and another
     * @param v The other vec
     * @return Dot product of this vec and another
     */
    public double dot(Vec v){
        return x * v.x + y * v.y;
    }

    /** Adds two vecs together
     * @param v Vec to be added to this vec
     */
    public void add(Vec v){
        x += v.x;
        y += v.y;
    }

    /** Subtracts a vec's coordinates from this vec's
     * @param v Vec whose coordinates are to be subtracted from this vec's
     */
    public void subtract(Vec v){
        x -= v.x;
        y -= v.y;
    }

    public static Vec subtract(Vec v1, Vec v2){
        return new Vec(v1.x - v2.x,v1.y - v2.y);
    }

    /** Multiplies two vecs
     * @param v Vec to be multiplied by this vec
     */
    public void multiply(int val){
        x *= val;
        y *= val;
    }

    /** Divides this vec's coordinates by another vec's
     * @param v Vec by whose coordinates this vec's coordinates will be divided
     */
    public void divide(int val){
        x /= val;
        y /= val;
    }

    /** Calculates the distance between two vecs
     * @param v1 First vec
     * @param v2 Second vec
     * @return Distance between the two vecs
     */
    public static double distance(Vec v1, Vec v2){
        return Math.sqrt(Math.pow(v1.x - v2.x,2) + Math.pow(v1.y - v2.y,2));
    }

    /** Calculates the angle between two vecs
     * @param v1 First vec
     * @param v2 Second vec
     * @return Angle between the two vecs
     */
    public static double angleBetween(Vec v1, Vec v2){
        return Math.acos(v1.dot(v2) / (v1.mag() * v2.mag()));
    }
}
