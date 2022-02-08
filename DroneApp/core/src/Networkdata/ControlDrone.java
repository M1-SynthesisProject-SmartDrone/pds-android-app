package Networkdata;

public class ControlDrone {

    // --- Variables ---

    private float x;
    private float y;
    private double z;
    private int r;

    // --- Methods ---

    public ControlDrone() {
        this.x = 0;
        this.y = 0;
        this.z = 0;
        this.r = 0;
    }

    public ControlDrone(float x, float y, double z, int r) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.r = r;
    }

    public void updateData(float x, float y, double z, int r){
        this.x = x;
        this.y = y;
        this.z = z;
        this.r = r;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public int getR() {
        return r;
    }

    public void setR(int r) {
        this.r = r;
    }
}
