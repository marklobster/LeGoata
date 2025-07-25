package org.legoata.samples.gofish.decision;

import java.util.UUID;

import org.legoata.samples.gofish.asset.Rank;

public class CardRequest {
	
	private UUID opponent;
	private Rank rank;
	
	public UUID getOpponent() {
		return opponent;
	}
	public void setOpponent(UUID opponent) {
		this.opponent = opponent;
	}
	public Rank getRank() {
		return rank;
	}
	public void setRank(Rank rank) {
		this.rank = rank;
	}
	
}
