package legoata.engine.decision.node.branching;

public class Option {
	
	private String title;
	private Object data;
	
	public Option(String title) {
		this.title = title;
	}
	
	public Option(String title, Object attachment) {
		this.title = title;
		this.data = attachment;
	}
	
	public String getTitle() {
		return title;
	}
	
	public Object getAttachedData() {
		return data;
	}
}
