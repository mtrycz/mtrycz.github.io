package com.mtrycz.pkgocounters;

import java.io.IOException;
import java.sql.*;
import java.util.List;
import java.util.Properties;
import java.util.function.Function;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.mtrycz.pkgocounters.calculators.Calculator;
import com.mtrycz.pkgocounters.calculators.TypeCounterCalculator;
import com.mtrycz.pkgocounters.helpers.CreateBaseTables;

import model.PokeMonMoveSet;

/**
 * Get a copy of TDO Excell
 * extract sheets as csv, put in resource/xxx.csv
 * 
 * 
 * @author mtrycz
 *
 */
public class SetupTablesApp {

	public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException {
		Connection c = null;
//		Class.forName("org.sqlite.JDBC");
//		c = DriverManager.getConnection("jdbc:sqlite:mtrycz.db");
		
		Class.forName("org.postgresql.Driver");
		Properties props = new Properties();
		props.setProperty("user","postgres");
		props.setProperty("password","postgres");
		c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/mtrycz", props);
		
		CreateBaseTables.createTypesTable(c);
		CreateBaseTables.createFastMovesTable(c);
		CreateBaseTables.createSpecialMovesTable(c);
		CreateBaseTables.createLevelCPTable(c);
		CreateBaseTables.createPokemonTable(c);
		CreateBaseTables.createMovesetsTable(c);

		CreateBaseTables.denormalizeTables(c);

		c.close();
	}

}

