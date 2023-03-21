/*
 * Vector.java
 */

 /**
  * Contains information about Vectors
  * @author Ethan Kempenich
  * Template for code from https://rosettacode.org/wiki/Boids/Java
  */
public class Vec {
    /** x-coordinate of the vector */
    private int x;
    /** y-coordinate of the vector */
    private int y;

    /** Creates a vector
     * @param x x-coordinate of the vector
     * @param y y-coordinate of the vector
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

    /** Adds two vectors together
     * @param v Vector to be added to this vector
     */
    public void add(Vec v){
        x += v.x;
        y += v.y;
    }

    /** Subtracts a vector's coordinates from this vector's
     * @param v Vector whose coordinates are to be subtracted from this vector's
     */
    public void subtract(Vec v){
        x -= v.x;
        y -= v.y;
    }

    /** Multiplies two vectors
     * @param v Vector to be multiplied by this vector
     */
    public void multiply(int val){
        x *= val;
        y *= val;
    }

    /** Divides this vector's coordinates by another vector's
     * @param v Vector by whose coordinates this vector's coordinates will be divided
     */
    public void divide(int val){
        x /= val;
        y /= val;
    }

    /** Calculates the distance between this vector and another
     * @param v Vector whose distance from this vector is being calculated
     * @return Distance between the two vectors
     */
    public double distance(Vec v){
        return Math.sqrt(Math.pow(x - v.x,2) + Math.pow(y - v.y,2));
    }
}
