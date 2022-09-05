package com.gemartueres.toyrobotservice.service;

import com.gemartueres.toyrobotservice.constant.FacingEnum;
import com.gemartueres.toyrobotservice.constant.TurnEnum;
import com.gemartueres.toyrobotservice.exception.ToyRobotException;
import com.gemartueres.toyrobotservice.model.ToyRobot;

import java.util.HashMap;
import java.util.Objects;

public class ToyRobotServiceImpl implements ToyRobotService {

    public ToyRobotServiceImpl(int tableSize) {
        this.tableSize = tableSize;
    }

    private final int tableSize;
    private final HashMap<Long, ToyRobot> toyRobotHashMap = new HashMap<>();

    @Override
    public ToyRobot get(long id) {
        if (toyRobotHashMap.isEmpty()) {
            throw new ToyRobotException("No Toy Robot created yet");
        }

        ToyRobot toyRobot = toyRobotHashMap.get(id);
        if (Objects.isNull(toyRobot)) {
            throw new ToyRobotException("Toy Robot ID not exists");
        }

        return toyRobot;
    }

    @Override
    public void move(long id) {
        ToyRobot toyRobot = this.get(id);
        FacingEnum facing = toyRobot.getFacing();
        switch (facing) {
            case NORTH:
                validateMove(toyRobot.getY() + 1);
                toyRobot.setPosition(toyRobot.getX(), toyRobot.getY() + 1);
                break;
            case EAST:
                validateMove(toyRobot.getX() + 1);
                toyRobot.setPosition(toyRobot.getX() + 1, toyRobot.getY());
                break;
            case SOUTH:
                validateMove(toyRobot.getY() - 1);
                toyRobot.setPosition(toyRobot.getX(), toyRobot.getY() - 1);
                break;
            case WEST:
                validateMove(toyRobot.getX() - 1);
                toyRobot.setPosition(toyRobot.getX() - 1, toyRobot.getY());
                break;
            default:
                break;
        }

        toyRobotHashMap.put(toyRobot.getId(), toyRobot);
    }

    private void validateMove(int position) {
        if (position > tableSize || position < 0) {
            throw new ToyRobotException("Invalid move");
        }
    }

    @Override
    public void turn(long id, TurnEnum turn) {
        ToyRobot toyRobot = this.get(id);
        FacingEnum facing = toyRobot.getFacing();

        switch (facing) {
            case NORTH:
                toyRobot.setFacing(TurnEnum.LEFT.equals(turn) ? FacingEnum.WEST : FacingEnum.EAST);
                break;
            case EAST:
                toyRobot.setFacing(TurnEnum.LEFT.equals(turn) ? FacingEnum.NORTH : FacingEnum.SOUTH);
                break;
            case SOUTH:
                toyRobot.setFacing(TurnEnum.LEFT.equals(turn) ? FacingEnum.EAST : FacingEnum.WEST);
                break;
            case WEST:
                toyRobot.setFacing(TurnEnum.LEFT.equals(turn) ? FacingEnum.SOUTH : FacingEnum.NORTH);
                break;
            default:
                break;
        }

        toyRobotHashMap.put(toyRobot.getId(), toyRobot);
    }

    @Override
    public void create(long id, int x, int y, FacingEnum facing) {
        validateCreate(x, y);
        ToyRobot toyRobot = new ToyRobot();
        toyRobot.setId(id);
        toyRobot.setFacing(facing);
        toyRobot.setPosition(x, y);

        toyRobotHashMap.put(id, toyRobot);
    }

    private void validateCreate(int x, int y) {
        if( (x > tableSize || x < 0) || (y > tableSize || y < 0)) {
            throw new ToyRobotException("Invalid position");
        }
    }
}
