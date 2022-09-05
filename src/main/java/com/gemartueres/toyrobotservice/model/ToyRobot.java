package com.gemartueres.toyrobotservice.model;

import com.gemartueres.toyrobotservice.constant.FacingEnum;

public class ToyRobot {

    private long id;

    private int x;

    private int y;

    private FacingEnum facing;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public FacingEnum getFacing() {
        return facing;
    }

    public void setFacing(FacingEnum facing) {
        this.facing = facing;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
