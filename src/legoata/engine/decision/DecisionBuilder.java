package legoata.engine.decision;

public class DecisionBuilder {

	private Decision decision;
	private OptionSet optionSet;
	
	public DecisionBuilder() {
		this.decision = new Decision();
	}
	
	public DecisionBuilder(OptionSet optionSet) {
		this.optionSet = optionSet;
	}
	
	public Decision getDecision() {
		return this.decision;
	}
	
	public OptionSet getOptionSet() {
		return this.optionSet;
	}
	
	public void setOptionSet(OptionSet optionSet) {
		this.optionSet = optionSet;
	}
}
