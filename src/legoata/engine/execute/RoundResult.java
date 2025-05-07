package legoata.engine.execute;

class RoundResult {
	
	private RoundResultCode code;
	private String gameOpName;
	
	public RoundResultCode getCode() {
		return code;
	}
	public void setCode(RoundResultCode code) {
		this.code = code;
	}
	public String getGameOpName() {
		return gameOpName;
	}
	public void setGameOpName(String gameOpName) {
		this.gameOpName = gameOpName;
	}

}
