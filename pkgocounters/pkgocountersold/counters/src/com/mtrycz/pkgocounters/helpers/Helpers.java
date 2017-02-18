package com.mtrycz.pkgocounters.helpers;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import model.DPSCounter;
import model.PokeMonMoveSet;

public final class Helpers {

	private static Map<String, Map<String, Double>> types = null;

	public static void initTypes (Connection c) throws SQLException {
		types = new HashMap<>();

		Statement stmt = c.createStatement();
		ResultSet resultset = stmt.executeQuery("Select * from types;");

		while (resultset.next()) {

			Map<String, Double> type = new HashMap<>();

			type.put("normal", resultset.getDouble("Normal"));
			type.put("fire", resultset.getDouble("Fire"));
			type.put("water", resultset.getDouble("Water"));
			type.put("electric", resultset.getDouble("Electric"));
			type.put("grass", resultset.getDouble("Grass"));
			type.put("ice", resultset.getDouble("Ice"));
			type.put("fighting", resultset.getDouble("Fighting"));
			type.put("poison", resultset.getDouble("Poison"));
			type.put("ground", resultset.getDouble("Ground"));
			type.put("flying", resultset.getDouble("Flying"));
			type.put("psychic", resultset.getDouble("Psychic"));
			type.put("bug", resultset.getDouble("Bug"));
			type.put("rock", resultset.getDouble("Rock"));
			type.put("ghost", resultset.getDouble("Ghost"));
			type.put("dragon", resultset.getDouble("Dragon"));
			type.put("dark", resultset.getDouble("Dark"));
			type.put("steel", resultset.getDouble("Steel"));
			type.put("fairy", resultset.getDouble("Fairy"));
			type.put("none", resultset.getDouble("None"));

			types.put(resultset.getString("Name").toLowerCase(), type);

		}
		resultset.close();
		stmt.close();
	}

	public static double getEffectivness(String attackType, String recieverType) {
		attackType = attackType != null && !attackType.equals("") ? attackType : "none";
		recieverType = recieverType != null && !recieverType.equals("") ? recieverType : "none";
		return types.get(attackType.toLowerCase()).get(recieverType.toLowerCase());
	}

	public static Object[] beanToObjectArray(Object in) throws IntrospectionException, IllegalAccessException, IllegalArgumentException {

		BeanInfo beanInfo = Introspector.getBeanInfo(in.getClass());
		PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
		Object[] out = new Object[propertyDescriptors.length - 1 ];
		int i = 0;
		for (PropertyDescriptor propertyDesc : propertyDescriptors) {
		    String propertyName = propertyDesc.getName();
		    try {
		    	if (!propertyDesc.getName().equals("class")) {
			    	Object value = propertyDesc.getReadMethod().invoke(in);
			    	out[i++] = value;
		    	}
		    } catch (InvocationTargetException e) {
		    	// this never happens
		    }
		}

		return out;
	}

    public static DPSCounter updateFields(PokeMonMoveSet attacker, PokeMonMoveSet defender) {
        // TODO Auto-generated method stub
        return null;
    }

    public static int calculateHP(double pokemonStamina, int level) {
        if ( level != 25 ) throw new IllegalArgumentException("Imlplemented only for lvl 25");
        return (int) Math.floor(pokemonStamina * 0.6745819393*0.6745819393 );
    }

    public static int calculateMaxCP(PokeMonMoveSet pokemon, int level) {

        if ( level != 25 ) throw new IllegalArgumentException("Imlplemented only for lvl 25");
        return (int) Math.floor(pokemon.getPokemonAttack()
                * Math.sqrt(pokemon.getPokemonDefense())
                        * Math.sqrt(pokemon.getPokemonStamina())
                        * 0.6745819393*0.6745819393 / 10);


    }

}
