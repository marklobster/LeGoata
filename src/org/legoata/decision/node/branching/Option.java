package org.legoata.decision.node.branching;

public class Option {
	
	private String title;
	private Object data;
	private String label;
	
	public Option(String title) {
		this.title = title;
	}
	
	public Option(String title, Object attachment) {
		this.title = title;
		this.data = attachment;
	}
	
	public Option(String title, String label) {
		this.title = title;
		this.label = label;
	}
	
	public Option(String title, Object attachment, String label) {
		this.title = title;
		this.data = attachment;
		this.label = label;
	}
	
	public String getTitle() {
		return title;
	}
	
	public Object getAttachedData() {
		return data;
	}

	public String getLabel() {
		return label;
	}
}
