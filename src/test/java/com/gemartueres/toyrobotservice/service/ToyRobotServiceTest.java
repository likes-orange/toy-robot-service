package com.gemartueres.toyrobotservice.service;

import com.gemartueres.toyrobotservice.constant.FacingEnum;
import com.gemartueres.toyrobotservice.constant.TurnEnum;
import com.gemartueres.toyrobotservice.model.ToyRobot;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ToyRobotServiceTest {

    private final Integer TABLE_SIZE = 5;

    private final ToyRobotService toyRobotService = new ToyRobotServiceImpl(TABLE_SIZE);

    @Test
    void testGetSuccess() {
        int id = 1;
        int x = 0;
        int y = 0;

        FacingEnum facingEnum = FacingEnum.NORTH;
        toyRobotService.create(id, x, y, facingEnum);

        ToyRobot toyRobot = toyRobotService.get(id);

        assertNotNull(toyRobot);
        assertEquals(id, toyRobot.getId());
        assertEquals(x, toyRobot.getX());
        assertEquals(y, toyRobot.getY());
        assertEquals(facingEnum, toyRobot.getFacing());

    }

    @Test
    void testGetFailedEmptyToyRobotMap() {
        try {
            int id = 1;
            toyRobotService.get(id);
        } catch (RuntimeException e) {
            assertEquals("No Toy Robot created yet", e.getMessage());
        }
    }

    @Test
    void testGetFailedNotFound() {
        try {
            toyRobotService.create(2, 0, 0, FacingEnum.NORTH);
            toyRobotService.get(1);
        } catch (RuntimeException e) {
            assertEquals("Toy Robot ID not exists", e.getMessage());
        }
    }

    @Test
    void testMoveSuccess() {
        int expectedX = 0;
        int expectedY = 1;
        FacingEnum expectedFacing = FacingEnum.NORTH;

        toyRobotService.create(1, 0, 0, FacingEnum.NORTH);
        toyRobotService.move(1);
        ToyRobot toyRobot = toyRobotService.get(1);

        assertNotNull(toyRobot);
        assertEquals(expectedX, toyRobot.getX());
        assertEquals(expectedY, toyRobot.getY());
        assertEquals(expectedFacing, toyRobot.getFacing());
    }

    @Test
    void testMoveFailedEmptyToyRobot() {
        try {
            toyRobotService.move(1);
        } catch (RuntimeException e) {
            assertEquals("No Toy Robot created yet", e.getMessage());
        }
    }

    @Test
    void testMoveFailedInvalidMove() {
        try {
            toyRobotService.create(1, 5, 5, FacingEnum.NORTH);
            toyRobotService.move(1);
        } catch (RuntimeException e) {
            assertEquals("Invalid move", e.getMessage());
        }
    }

    @Test
    void testTurnSuccess() {
        FacingEnum expectedFacing = FacingEnum.EAST;

        toyRobotService.create(1, 0, 0, FacingEnum.NORTH);
        toyRobotService.turn(1, TurnEnum.RIGHT);
        ToyRobot toyRobot = toyRobotService.get(1);

        assertNotNull(toyRobot);
        assertEquals(expectedFacing, toyRobot.getFacing());
    }

    @Test
    void testTurnFailedEmptyToyRobot() {
        try {
            toyRobotService.turn(1, TurnEnum.RIGHT);
        } catch (RuntimeException e) {
            assertEquals("No Toy Robot created yet", e.getMessage());
        }
    }
}
