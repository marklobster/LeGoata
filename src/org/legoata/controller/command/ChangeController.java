package org.legoata.controller.command;

public class ChangeController implements TurnCommand {
	
	private String controllerName = null;

	public ChangeController(String controller) {
		this.controllerName = controller;
	}
	
	public String getControllerName() {
		return this.controllerName;
	}

}
