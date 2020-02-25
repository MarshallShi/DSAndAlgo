package dsandalgo.random;

public class RandomPointInACircle {

    //http://www.anderswallin.net/2009/05/uniform-random-points-in-a-circle-using-polar-coordinates/

    double radius, x_center, y_center;
    public RandomPointInACircle(double radius, double x_center, double y_center) {
        this.radius=radius;
        this.x_center=x_center;
        this.y_center=y_center;
    }

    public double[] randPoint() {
        double len= Math.sqrt(Math.random())*radius;
        double deg= Math.random()*2*Math.PI;
        double x= x_center+len*Math.cos(deg);
        double y= y_center+len*Math.sin(deg);
        return new double[]{x,y};
    }
}
