package org.legoata.model.structure;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

import org.legoata.model.LGObject;

public class LGCollection implements Iterable<LGObject> {
	
	private Map<UUID, LGObject> map = new HashMap<UUID, LGObject>();

	@Override
	public Iterator<LGObject> iterator() {
		return this.map.values().iterator();
	}

	public void put(LGObject obj) {
		this.map.put(obj.getId(), obj);
	}

	public LGObject get(UUID id) {
		return this.map.get(id);
	}

	public LGObject remove(UUID id) {
		return this.map.remove(id);
	}

	public boolean contains(UUID id) {
		return this.map.containsKey(id);
	}

	public int size() {
		return this.map.size();
	}
	
}
