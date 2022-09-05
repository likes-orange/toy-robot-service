package com.gemartueres.toyrobotservice;

import com.gemartueres.toyrobotservice.constant.CommandEnum;
import com.gemartueres.toyrobotservice.constant.TurnEnum;
import com.gemartueres.toyrobotservice.controller.V1ToyRobotController;
import com.gemartueres.toyrobotservice.service.ToyRobotService;
import com.gemartueres.toyrobotservice.service.ToyRobotServiceImpl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

public class ToyRobotServiceApplication {

	private static final String COMMAND_SEPARATOR = " ";

	public static void main(String[] args) {
		ToyRobotService toyRobotService = new ToyRobotServiceImpl(5);
		V1ToyRobotController v1ToyRobotController = new V1ToyRobotController(toyRobotService);

		List<String> commandsFromFileList = getCommandsFromFile(args[0]);
		if (commandsFromFileList.isEmpty()) {
			System.out.println("Error: File not found");
			return;
		}

		for(String commandFromFile : commandsFromFileList) {
			System.out.println(commandFromFile);
			String[] command = commandFromFile.trim().split(COMMAND_SEPARATOR);
			if (command.length == 2) {
				processCommand(v1ToyRobotController, command[0], command[1]);
			} else {
				processCommand(v1ToyRobotController, command[0], "");
			}
		}
	}

	private static void processCommand(V1ToyRobotController toyRobotController, String command, String params) {
		CommandEnum commandEnum = CommandEnum.valueOf(command);

		switch (commandEnum) {
			case PLACE:
				toyRobotController.place(params);
				break;
			case MOVE:
				toyRobotController.move();
				break;
			case LEFT:
				toyRobotController.turn(TurnEnum.LEFT);
				break;
			case RIGHT:
				toyRobotController.turn(TurnEnum.RIGHT);
				break;
			case REPORT:
				toyRobotController.report();
				break;
			default:
				System.out.println("Error: Invalid COMMAND");
				break;
		}
	}

	private static List<String> getCommandsFromFile(String arg) {
		try {
			return Files.readAllLines(Paths.get(arg));
		} catch (IOException e) {
			return Collections.emptyList();
		}
	}

}
