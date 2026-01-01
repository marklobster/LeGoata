package org.legoata.model.structure;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

import org.legoata.tracking.LGTrackable;

public class HashMapCollection implements LGCollection {
	
	private Map<UUID, LGTrackable> map = new HashMap<UUID, LGTrackable>();

	@Override
	public Iterator<LGTrackable> iterator() {
		return this.map.values().iterator();
	}

	@Override
	public void put(LGTrackable obj) {
		this.map.put(obj.getId(), obj);
	}

	@Override
	public LGTrackable get(UUID id) {
		return this.map.get(id);
	}

	@Override
	public LGTrackable remove(UUID id) {
		return this.map.remove(id);
	}

	@Override
	public boolean contains(UUID id) {
		return this.map.containsKey(id);
	}

	@Override
	public int size() {
		return this.map.size();
	}
	
}
