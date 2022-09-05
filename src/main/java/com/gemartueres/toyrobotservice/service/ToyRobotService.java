package com.gemartueres.toyrobotservice.service;

import com.gemartueres.toyrobotservice.constant.FacingEnum;
import com.gemartueres.toyrobotservice.constant.TurnEnum;
import com.gemartueres.toyrobotservice.model.ToyRobot;

public interface ToyRobotService {

    ToyRobot get(long id);

    void move(long id);

    void turn(long id, TurnEnum turn);

    void create(long id, int x, int y, FacingEnum facing);

}
