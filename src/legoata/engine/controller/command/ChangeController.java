package legoata.engine.controller.command;

public class ChangeController implements TurnCommand {
	
	private String controllerName = null;
	
	public ChangeController() {
		
	}

	public ChangeController(String controller) {
		this.controllerName = controller;
	}
	
	public String getControllerName() {
		return this.controllerName;
	}

}
