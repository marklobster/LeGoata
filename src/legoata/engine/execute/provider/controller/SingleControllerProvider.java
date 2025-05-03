package legoata.engine.execute.provider.controller;

import java.util.Scanner;

import legoata.engine.controller.Controller;

public interface SingleControllerProvider {
	public Controller constructController(Scanner scanner);
}
