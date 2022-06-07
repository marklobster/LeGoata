package legoata.engine.decision;

public abstract class Option {
	
	private String key;
	private String title;
	
	public Option(String key, String title) {
		this.title = title;
		this.key = key;
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getKey() {
		return key;
	}
}
