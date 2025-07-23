package org.legoata.samples.gofish;

import java.util.UUID;

public class Keys {
	
	private static UUID gameKey = UUID.randomUUID();
	private static UUID deckKey = UUID.randomUUID();
	private static UUID userKey = UUID.randomUUID();
	private static UUID niceBotKey = UUID.randomUUID();
	private static UUID meanBotKey = UUID.randomUUID();
	
	public static UUID getGameKey() {
		return gameKey;
	}
	
	public static UUID getDeckKey() {
		return deckKey;
	}
	
	public static UUID getUserKey() {
		return userKey;
	}
	
	public static UUID getNiceBotKey() {
		return niceBotKey;
	}

	public static UUID getMeanBotKey() {
		return meanBotKey;
	}
	
}
