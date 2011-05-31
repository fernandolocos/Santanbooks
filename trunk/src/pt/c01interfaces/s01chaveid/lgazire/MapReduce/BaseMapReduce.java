package pt.c01interfaces.s01chaveid.lgazire.MapReduce;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

public class BaseMapReduce {
	protected String GetToken(Entry<String, Boolean> item){
		
		String tipoCaracteristica = item.getKey().toLowerCase();
		
		if (!tipoCaracteristica.startsWith("1") && !tipoCaracteristica.startsWith("0"))
		{
			tipoCaracteristica = (item.getValue()) ? "1" + tipoCaracteristica : "0" + tipoCaracteristica;
		}
		return tipoCaracteristica;
	}
	
}
