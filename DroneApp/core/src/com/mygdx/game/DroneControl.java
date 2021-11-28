package com.mygdx.game;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Formatter;
import java.util.HashMap;

public class DroneControl {

    // ----------------------
    // Attributs
    // ----------------------

    public static final int LEFT_ROTATION = -1;
    public static final int RIGHT_ROTATION = 1;
    public static final int NO_ROTATION = 0;


    boolean security;           // true if security is On, false if not
    float frontMove;            // indicate nature of front move by sign and intensity by the number | front is positive, back is negative
    float lateralMove;          // indicate nature of lateral move by sign and intensity by the number | left is negative, right is positive
    double motorPercentage;        // indicate percentage of drone motor power to use
    int rotation;               // -1 for left rotation, 1 for right rotation, 0 for no rotation
    NumberFormat format;           // to limit the number of digits for position

    private static DroneControl instance = null; // Singleton

    // ----------------------
    // Methods
    // ----------------------

    private DroneControl(){
        security = true;
        frontMove = 0;
        lateralMove = 0;
        motorPercentage = 0;
        rotation = 0;
        format = new DecimalFormat("#0.000");
    }

    public static DroneControl getInstance(){
        if(instance == null){
            //synchronized (instance) {
                if (instance == null)
                    instance = new DroneControl();
            //}

        }
        return instance;
    }

    /**
     * Set the security to the other value
     */
    public void switchSecurity(){
        if(security){
            security = false;
        }
        else{
            security = true;
        }
    }

    /**
     * Start a left Rotation
     */
    public void leftRotation(){
        rotation = LEFT_ROTATION;
    }

    /**
     * Start a right Rotation
     */
    public void rightRotation(){
        rotation = RIGHT_ROTATION;
    }

    /**
     * End rotation
     */
    public void endRotation(){
        rotation = NO_ROTATION;
    }

    /**
     * Change the motor percentage
     *
     * @param percentage
     */
    public void updateMotorPercentage(int percentage){
        if(percentage > 0 && percentage < 100){
            motorPercentage = percentage;
        }
    }

    /**
     * Set the movements for the drone
     * @param frontMove
     * @param lateralMove
     */
    public void setMoves(float frontMove, float lateralMove){
        this.frontMove = frontMove;
        this.lateralMove = lateralMove;
    }

    // ------ Getters and setters ------

    public boolean isSecurity() {
        return security;
    }

    public void setSecurity(boolean security) {
        this.security = security;
    }

    public float getFrontMove() {
        return frontMove;
    }

    public void setFrontMove(float frontMove) {
        this.frontMove = frontMove;
    }

    public float getLateralMove() {
        return lateralMove;
    }

    public void setLateralMove(float lateralMove) {
        this.lateralMove = lateralMove;
    }

    public double getMotorPercentage() {
        return motorPercentage;
    }

    public void setMotorPercentage(double motorPercentage) {
        this.motorPercentage = motorPercentage;
    }

    public int getRotation() {
        return rotation;
    }

    public void setRotation(int rotation) {
        this.rotation = rotation;
    }

    public String getJson(){
        HashMap<String, Object> mapValue = new HashMap<>();
        final GsonBuilder builder = new GsonBuilder();
        final Gson gson = builder.create();
        mapValue.put("forwardmove", format.format(getFrontMove()));
        mapValue.put("leftmove", format.format(getLateralMove()));
        mapValue.put("motorpower", format.format(getMotorPercentage()));
        mapValue.put("leftrotation", format.format(getRotation()));
        return gson.toJson(mapValue).replace("\n", "");
    }

    public String toString(){
        return "{\"forwardmove\" : "+ format.format(frontMove)+ " ,\"leftmove\":" + format.format(lateralMove) + ", \"motorpower\": " + format.format(motorPercentage) + ",\"leftrotation\" " + format.format(rotation)+"}";
    }
}
