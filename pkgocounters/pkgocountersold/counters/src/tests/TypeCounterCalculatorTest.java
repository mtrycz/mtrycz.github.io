package tests;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Properties;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.junit.Test;

import com.mtrycz.pkgocounters.calculators.TypeCounterCalculator;
import com.mtrycz.pkgocounters.helpers.Helpers;

import model.PokeMonMoveSet;

public class TypeCounterCalculatorTest {

	@Test
	public void test() throws ClassNotFoundException, SQLException {

		Class.forName("org.postgresql.Driver");
		Properties props = new Properties();
		props.setProperty("user","postgres");
		props.setProperty("password","postgres");
		Connection c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/mtrycz", props);
		
		Helpers.initTypes(c);
		
		
		QueryRunner run = new QueryRunner();
		ResultSetHandler<PokeMonMoveSet> h = new BeanHandler<PokeMonMoveSet>(PokeMonMoveSet.class);
		
		String attackername = "Omastar";
		String attackerfastmovename = "Water Gun";
		String attackerspecialmovename = "Hydro Pump";
		String defendername = "Vaporeon";
		String defenderfastmovename = "Water Gun";
		String defenderspecialmovename = "Aqua Tail";
		
		
		PokeMonMoveSet attacker = run.query(c,  "Select * from DMovesets "
				+ "where pokemonname = '"+ attackername +"' "
						+ "and fastmovename = '"+ attackerfastmovename +"' "
								+ "and specialmovename = '"+ attackerspecialmovename +"'", h);
		PokeMonMoveSet defender = run.query(c,  "Select * from DMovesets "
				+ "where pokemonname = '"+ defendername +"' "
						+ "and fastmovename = '"+ defenderfastmovename +"' "
								+ "and specialmovename = '"+ defenderspecialmovename +"'", h);
		
		TypeCounterCalculator calc = new TypeCounterCalculator(c);
		
		double attMod = calc.caluculateAttackerAttackModifier(attacker, defender);
		double defMod = calc.caluculateAttackerDefenseModifier(attacker, defender);
		
		System.out.println("attMod: "+ attMod + "\ndefMod: "+ defMod);
		
		
	}

}
