package pt.c01interfaces.s01chaveid.frango;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

class MultiMap extends HashMap<Object, Object> {
	
	private static final long serialVersionUID = 1L;

	public MultiMap() {
        super();
    }

    @SuppressWarnings("unchecked")
	public Object insertValue(Object key, Object value) {
        List<Object> l = (List<Object>) super.get(key);

        if (l == null) {
            l = new ArrayList<Object>();
        }

        l.add(value);

        return super.put(key, l);
    }

}
