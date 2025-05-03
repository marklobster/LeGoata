package legoata.engine.execute.provider.controller;

import java.util.Scanner;

import legoata.engine.controller.Controller;

public interface ControllerProvider {

	public Controller getController(String name, Scanner scanner);

}
