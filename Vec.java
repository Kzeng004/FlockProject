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
    private int x;
    /** y-coordinate of the vec */
    private int y;

    /** Creates a vec
     * @param x x-coordinate of the vec
     * @param y y-coordinate of the vec
     */
    public Vec(int x, int y){
        this.x = x;
        this.y = y;
    }

    public void setPoints(int newX, int newY){
        x = newX;
        y = newY;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
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

    /** Calculates the distance between this vec and another
     * @param v Vec whose distance from this vec is being calculated
     * @return Distance between the two vecs
     */
    public double distance(Vec v){
        return Math.sqrt(Math.pow(x - v.x,2) + Math.pow(y - v.y,2));
    }
}
