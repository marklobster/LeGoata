package legoata.engine.controller.command;

public class SwapController implements FrameworkCommand {
	
	private String controllerName;

	public SwapController(String controller) {
		this.controllerName = controller;
	}
	
	public String getControllerName() {
		return this.controllerName;
	}

}
