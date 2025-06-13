package org.legoata.execute;

class TurnResult {
	
	private TurnResultCode code;
	private String message;
	
	TurnResultCode getCode() {
		return code;
	}
	void setCode(TurnResultCode code) {
		this.code = code;
	}
	String getMessage() {
		return message;
	}
	void setMessage(String message) {
		this.message = message;
	}
	
}
