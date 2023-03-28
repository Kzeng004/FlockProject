/** Helper class to store xy locations */
public class Point {
    /** Making public for each access. */
    public double x = 0;
    public double y = 0;
    
    public Point(double xx, double yy) {
        x = xx;
        y = yy;
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
     * Limits the point's magnitude to some value
     * @param l value to which to limit the point's magnitude
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

    public String toString() {
        return "["+x+","+y+"]";
    }
}