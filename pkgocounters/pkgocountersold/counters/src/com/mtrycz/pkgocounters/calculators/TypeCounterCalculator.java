package com.mtrycz.pkgocounters.calculators;

import java.beans.IntrospectionException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.function.Function;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import static com.mtrycz.pkgocounters.helpers.Helpers.getEffectivness;

import model.PokeMonMoveSet;
import model.TypeCounters;

public class TypeCounterCalculator implements Calculator{

	public static final String TABLE_NAME = "TypeCounters";
	//	private PreparedStatement stmt = null;

	public TypeCounterCalculator(Connection c) throws SQLException {
		recreateTable(c);
	}

	@Override
	public String getTableName() {
		return TABLE_NAME;
	}

	private void recreateTable(Connection c) throws SQLException {

		Statement stmt = c.createStatement();
		stmt.executeUpdate("DROP TABLE IF EXISTS "+ TABLE_NAME);
		stmt.executeUpdate("CREATE TABLE "+ TABLE_NAME +"("
				+ "AttackerMoveset INTEGER NOT NULL,"
				+ "AttackerName TEXT NOT NULL,"
				+ "AttackerType1 TEXT NOT NULL,"
				+ "AttackerType2 TEXT NOT NULL,"
				+ "AttackerFastmove TEXT NOT NULL,"
				+ "AttackerFastmoveType TEXT NOT NULL,"
				+ "AttackerSpecialmove TEXT NOT NULL,"
				+ "AttackerSpecialmoveType TEXT NOT NULL,"
				+ "DefenderMoveset INTEGER NOT NULL,"
				+ "DefenderName TEXT NOT NULL,"
				+ "DefenderType1 TEXT NOT NULL,"
				+ "DefenderType2 TEXT NOT NULL,"
				+ "DefenderFastmove TEXT NOT NULL,"
				+ "DefenderFastmoveType TEXT NOT NULL,"
				+ "DefenderSpecialmove TEXT NOT NULL,"
				+ "DefenderSpecialmoveType TEXT NOT NULL,"
				+ "AttackerAttackModifier REAL NOT NULL,"
				+ "AttackerDefenseModifier REAL NOT NULL,"
				+ "AttackerAdvantage REAL NOT NULL,"
				+ "PRIMARY KEY (AttackerMoveset, DefenderMoveset) )");
		stmt.close();
	}

	@Override
	public void makeAndInsertCalculation(Connection c, PokeMonMoveSet attacker, PokeMonMoveSet defender) {


		try {

			PreparedStatement stmt = c.prepareStatement("INSERT INTO "+ TABLE_NAME +" values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

			stmt.setInt(1, attacker.getId());
			stmt.setString(2, attacker.getPokemonName());
			stmt.setString(3, attacker.getPokemonType1());
			stmt.setString(4, attacker.getPokemonType2());
			stmt.setString(5, attacker.getFastmovename());
			stmt.setString(6, attacker.getFastmovetype());
			stmt.setString(7, attacker.getSpecialmovename());
			stmt.setString(8, attacker.getSpecialmovetype());
			stmt.setInt(9, defender.getId());
			stmt.setString(10, defender.getPokemonName());
			stmt.setString(11, defender.getPokemonType1());
			stmt.setString(12, defender.getPokemonType2());
			stmt.setString(13, defender.getFastmovename());
			stmt.setString(14, defender.getFastmovetype());
			stmt.setString(15, defender.getSpecialmovename());
			stmt.setString(16, defender.getSpecialmovetype());

			// quid
			double attackerAttackModifier = caluculateAttackerAttackModifier(attacker, defender);
			double attackerDefenseModifier = caluculateAttackerDefenseModifier(attacker, defender);
			stmt.setDouble(17, attackerAttackModifier);
			stmt.setDouble(18, attackerDefenseModifier);
			stmt.setDouble(19, attackerAttackModifier * attackerDefenseModifier);

			stmt.executeUpdate();
			stmt.close();

		} catch (SQLException e) {
			System.out.println("ERROR: Insert failed for "+ attacker.getId() + " and "+ defender.getId());
			e.printStackTrace();
		}
	}

	/**
	 * If the special attack is weak (mod less than 1), we can just choose not to use it. 
	 * Else, we average the two mods to make the combined attack modifier.
	 */
	public double caluculateAttackerAttackModifier(PokeMonMoveSet attacker, PokeMonMoveSet defender) {

		String aType1 = attacker.getPokemonType1();
		String aType2 = attacker.getPokemonType2();
		String afmType = attacker.getFastmovetype();
		String asmType = attacker.getSpecialmovetype();

		String dType1 = defender.getPokemonType1();
		String dType2 = defender.getPokemonType2();

		// STAB
		double fastmoveModifier = afmType.equalsIgnoreCase(aType1) || afmType.equalsIgnoreCase(aType2) ? 1.25 : 1.0;
		double specialmoveModifier = asmType.equalsIgnoreCase(aType1) || asmType.equalsIgnoreCase(aType2) ? 1.25 : 1.0;

		// type effectivness
		fastmoveModifier = fastmoveModifier * getEffectivness(afmType, dType1) * getEffectivness(afmType, dType2);
		specialmoveModifier = specialmoveModifier * getEffectivness(asmType, dType1) * getEffectivness(asmType, dType2);

		if(specialmoveModifier < 1) return fastmoveModifier;
		else return (fastmoveModifier + specialmoveModifier) /2;
	}

	/**
	 * Don't account for STAB here, because it's something we don't have any influence on.
	 * The AI will ALYWAYS choose to interleave the attacks, so we just average the two modifiers
	 * To be able to combine the defense modifier with the attack modifier, the defender's advantage is inverted
	 * (ie 0.8 becomes 1.25, 0.64 becomes 1.56, 1.25 becomes 0.8, etc)
	 */
	public double caluculateAttackerDefenseModifier(PokeMonMoveSet attacker, PokeMonMoveSet defender) {

		String aType1 = attacker.getPokemonType1();
		String aType2 = attacker.getPokemonType2();


		String sfmType = defender.getFastmovetype();
		String ssmType = defender.getSpecialmovetype();

		double fastmoveModifier = getEffectivness(sfmType, aType1) * getEffectivness(sfmType, aType2);
		double specialmoveModifier =  getEffectivness(ssmType, aType1) * getEffectivness(ssmType, aType2);

		double combinedModifier = (fastmoveModifier + specialmoveModifier) /2;
		double attackerDefenseModfier = 1 / combinedModifier;

		return attackerDefenseModfier;
	}

}
