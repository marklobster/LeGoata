package org.legoata.decision.node.branching;

public class Option {
	
	private String title;
	private Object data;
	private String key;
	
	public Option(String title) {
		this.title = title;
	}
	
	public Option(String title, Object attachment) {
		this.title = title;
		this.data = attachment;
	}
	
	public Option(String title, Object attachment, String key) {
		this.title = title;
		this.data = attachment;
		this.key = key;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public Object getAttachedData() {
		return data;
	}
	
	public void setAttachedData(Object data) {
		this.data = data;
	}

	public String getKey() {
		return key;
	}
	
	public void setKey(String key) {
		this.key = key;
	}
}
