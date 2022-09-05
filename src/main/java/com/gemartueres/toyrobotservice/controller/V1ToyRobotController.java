package com.gemartueres.toyrobotservice.controller;

import com.gemartueres.toyrobotservice.constant.FacingEnum;
import com.gemartueres.toyrobotservice.constant.TurnEnum;
import com.gemartueres.toyrobotservice.exception.RequestException;
import com.gemartueres.toyrobotservice.model.ToyRobot;
import com.gemartueres.toyrobotservice.service.ToyRobotService;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class V1ToyRobotController {

    private static final String PARAM_SEPARATOR = ",";

    /**
     * hardcoded the robot id since the requirement is just 1 robot
     * but can be used in the future for multi-robot scenario (v2).
     */
    private static final long ROBOT_ID = 1;

    private static final String REGEX_PARAM_PATTERN = "\\d,\\d,\\w*";

    public V1ToyRobotController(ToyRobotService toyRobotService) {
        this.toyRobotService = toyRobotService;
    }

    private final ToyRobotService toyRobotService;

    public void place(String params) {
        try {
            validateParams(params);

            String[] paramsArray = params.trim().split(PARAM_SEPARATOR);
            int x = Integer.parseInt(paramsArray[0]);
            int y = Integer.parseInt(paramsArray[1]);
            String facing = paramsArray[2];

            toyRobotService.create(ROBOT_ID, x, y, FacingEnum.valueOf(facing));
        } catch (RuntimeException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void move() {
        try {
            toyRobotService.move(ROBOT_ID);
        } catch (RuntimeException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void turn(TurnEnum turnEnum) {
        try {
            toyRobotService.turn(ROBOT_ID, turnEnum);
        } catch (RuntimeException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void report() {
        try {
            ToyRobot toyRobot = toyRobotService.get(ROBOT_ID);
            System.out.println("Output: " + toyRobot.getX() + "," + toyRobot.getY() + "," + toyRobot.getFacing().name());
        } catch (RuntimeException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void validateParams(String params) {
        Pattern pattern = Pattern.compile(REGEX_PARAM_PATTERN);
        Matcher matcher = pattern.matcher(params);

        if(!matcher.matches()) {
            throw new RequestException("Invalid parameter format");
        }
    }
}
