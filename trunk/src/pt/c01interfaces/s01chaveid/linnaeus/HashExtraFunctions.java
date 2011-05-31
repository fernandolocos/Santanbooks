package pt.c01interfaces.s01chaveid.linnaeus;

import java.util.HashMap;
import java.util.Vector;


public class HashExtraFunctions
{
	public static Object getKey(HashMap<Object, Object> hsh, Object value) {
		Object key = null;
		for (Object obj : hsh.keySet()) {
			if (obj != null && obj.equals(value))
				key = obj;
		}
		return key;
	}
	
	public static String getKey(HashMap<String, Integer> hsh, Integer value, Vector<String> lista) {
		String key = null;
		for (String str : hsh.keySet()) {
			if (str != null && hsh.get(str).equals(value) && !lista.contains(str))
				key = str;
		}
		return key;
	}
}
