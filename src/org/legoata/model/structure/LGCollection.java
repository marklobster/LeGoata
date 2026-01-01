package org.legoata.model.structure;

import java.util.UUID;

import org.legoata.tracking.LGTrackable;

public interface LGCollection extends Iterable<LGTrackable> {
	
	public void put(LGTrackable obj);
	public LGTrackable get(UUID id);
	public LGTrackable remove(UUID id);
	public boolean contains(UUID id);
	public int size();
	
}
