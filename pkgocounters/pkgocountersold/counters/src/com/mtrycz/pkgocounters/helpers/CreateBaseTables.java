package com.mtrycz.pkgocounters.helpers;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

public class CreateBaseTables {

	static final String INSERT_TYPE =
			"Insert into Types values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
	static final String INSERT_FASTMOVE =
			"Insert into Fastmoves values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
	static final String INSERT_SPECIALMOVE =
			"Insert into Specialmoves values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
	static final String INSERT_POKEMON =
			"Insert into Pokemon values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
	static final String INSERT_MOVESET =
			"Insert into Movesets(ID, Pokemon, Fastmove, Specialmove) values (?, ?, ?, ?);";

	public static void createMovesetsTable(Connection c) throws SQLException, IOException {

		Statement stmt1 = c.createStatement();
		stmt1.executeUpdate("DROP TABLE IF EXISTS Movesets");
		String sql = "CREATE TABLE IF NOT EXISTS Movesets ("+
				"ID INTEGER PRIMARY KEY,"+
				"Pokemon Integer not null,"+
				"Fastmove integer not null,"+
				"Specialmove integer not null)";

		stmt1.executeUpdate(sql);

		Reader in = new FileReader("resources/Pokemon Go - All Movesets basic.csv");
		Iterable<CSVRecord> records = CSVFormat.EXCEL.withDelimiter(';').parse(in);

		int id = 0;
		for (CSVRecord record : records) {

			String pokemon = record.get(1);
			int pokemonId = CreateBaseTables.lookup(c, "Pokemon", pokemon);
			String[] fastmoves = new String[2];
			String[] specialmoves = new String[4];

			fastmoves[0] = record.get(3);
			fastmoves[1] = record.get(4);
			specialmoves[0] = record.get(6);
			specialmoves[1] = record.get(7);
			specialmoves[2] = record.get(8);
			specialmoves[3] = record.get(9);

			for (String fastmove : fastmoves) {
				for (String specialmove : specialmoves) {

					if ( notEmpty(fastmove) && notEmpty(specialmove) ) {
						int fastmoveid = CreateBaseTables.lookup(c, "Fastmoves", fastmove);
						int specialmoveid = CreateBaseTables.lookup(c, "Specialmoves", specialmove);

						PreparedStatement stmt = c.prepareStatement(INSERT_MOVESET);

						stmt.setInt(1, id++);
						stmt.setInt(2, pokemonId);
						stmt.setInt(3, fastmoveid);
						stmt.setInt(4, specialmoveid);

						stmt.executeUpdate();
					}
				}
			}
		}
	}


	private static boolean notEmpty(String s) {
		return s != null && !s.equals("");
	}


	public static void createPokemonTable(Connection c) throws SQLException, IOException {

		Statement stmt1 = c.createStatement();
		stmt1.executeUpdate("DROP TABLE IF EXISTS Pokemon");
		String sql = "CREATE TABLE IF NOT EXISTS Pokemon ("+
				"ID INT PRIMARY KEY,"+
				"Name Text not null ,"+
				"Evolutions int not null,"+
				"Available text not null,"+
				"Type1 text not null,"+
				"Type2 text not null,"+
				"Attack real not null,"+
				"Defense real not null,"+
				"Stamina real not null,"+
				"Tankiness real not null,"+
				"MinIVCPCap real not null,"+
				"MaxIVCPCap real not null)";

		stmt1.executeUpdate(sql);
		stmt1.executeUpdate("DELETE FROM Pokemon");

		Reader in = new FileReader("resources/Pokemon GO Species_Moveset Analysis Tool species data.csv");
		Iterable<CSVRecord> records = CSVFormat.EXCEL.withDelimiter(';').parse(in);

		PreparedStatement stmt = c.prepareStatement(INSERT_POKEMON);
		for (CSVRecord record : records) {

			stmt.setInt(1,  Integer.parseInt(record.get(1)) );
			stmt.setString(2, record.get(2) );
			stmt.setInt(3, Integer.parseInt(record.get(3)) );
			stmt.setString(4, record.get(4) );
			stmt.setString(5, record.get(5) );
			stmt.setString(6, record.get(6) );
			stmt.setDouble(7,  Double.parseDouble( record.get(7) ) );
			stmt.setDouble(8,  Double.parseDouble( record.get(8) ));
			stmt.setDouble(9,  Double.parseDouble( record.get(9) ) );
			stmt.setDouble(10,  Double.parseDouble( record.get(10) ));
			stmt.setDouble(11,  Double.parseDouble( record.get(11).replace("." ,"") ) );
			stmt.setDouble(12,  Double.parseDouble( record.get(12).replace("." ,"") ) );


			try {
				stmt.executeUpdate();
			} catch (SQLException e) {
				System.out.println(record);
				throw e;
			}
		}
		stmt1.close();
		stmt.close();
	}

	public static void createTypesTable(Connection c) throws SQLException, FileNotFoundException, IOException {
		Statement stmt1 = c.createStatement();
		stmt1.executeUpdate("DROP TABLE IF EXISTS Types ");
		String sql = "CREATE TABLE if not exists Types " +
				"(ID INT PRIMARY KEY     NOT NULL," +
				" Name           TEXT    NOT NULL, " +
				" Normal            REAL     NOT NULL, " +
				" Fire        REAL     NOT NULL, " +
				" Water        REAL     NOT NULL, " +
				" Electric        REAL     NOT NULL, " +
				" Grass        REAL     NOT NULL, " +
				" Ice        REAL     NOT NULL, " +
				" Fighting        REAL     NOT NULL, " +
				" Poison        REAL     NOT NULL, " +
				" Ground        REAL     NOT NULL, " +
				" Flying        REAL     NOT NULL, " +
				" Psychic        REAL     NOT NULL, " +
				" Bug            REAL   NOT NULL," +
				" Rock        REAL     NOT NULL, " +
				" Ghost        REAL     NOT NULL, " +
				" Dragon        REAL     NOT NULL, " +
				" Dark        REAL     NOT NULL, " +
				" Steel        REAL     NOT NULL, " +
				" Fairy        REAL     NOT NULL, " +
				" None         REAL NOT NULL)";
		stmt1.executeUpdate(sql);
		stmt1.executeUpdate("DELETE FROM Types");

		System.out.println("Opened database successfully");

		Reader in = new FileReader("resources/Copy of Pokemon v4 - TDO types.csv");
		Iterable<CSVRecord> records = CSVFormat.EXCEL.withDelimiter(';').parse(in);

		PreparedStatement stmt = c.prepareStatement(INSERT_TYPE);

		int i = 0;
		for (CSVRecord record : records) {
			String name = record.get(0).toLowerCase();
			double normal = Double.parseDouble(record.get(1).replace(',', '.'));
			double fire = Double.parseDouble(record.get(2).replace(',', '.'));
			double water = Double.parseDouble(record.get(3).replace(',', '.'));
			double electric = Double.parseDouble(record.get(4).replace(',', '.'));
			double grass = Double.parseDouble(record.get(5).replace(',', '.'));
			double ice = Double.parseDouble(record.get(6).replace(',', '.'));
			double fighting = Double.parseDouble(record.get(7).replace(',', '.'));
			double poison = Double.parseDouble(record.get(8).replace(',', '.'));
			double ground = Double.parseDouble(record.get(9).replace(',', '.'));
			double flying = Double.parseDouble(record.get(10).replace(',', '.'));
			double psychic = Double.parseDouble(record.get(11).replace(',', '.'));
			double bug = Double.parseDouble(record.get(12).replace(',', '.'));
			double rock = Double.parseDouble(record.get(13).replace(',', '.'));
			double ghost = Double.parseDouble(record.get(14).replace(',', '.'));
			double dragon = Double.parseDouble(record.get(15).replace(',', '.'));
			double dark = Double.parseDouble(record.get(16).replace(',', '.'));
			double steel = Double.parseDouble(record.get(17).replace(',', '.'));
			double fairy = Double.parseDouble(record.get(18).replace(',', '.'));
			double none = Double.parseDouble(record.get(19).replace(',', '.'));

			//			System.out.println(String.format("%s %s %s %s", index, name, type1, type2));
			stmt.setInt(1, i++);
			stmt.setString(2, name);
			stmt.setDouble(3, normal);
			stmt.setDouble(4, fire);
			stmt.setDouble(5, water);
			stmt.setDouble(6, electric);
			stmt.setDouble(7, grass);
			stmt.setDouble(8, ice);
			stmt.setDouble(9, fighting);
			stmt.setDouble(10, poison);
			stmt.setDouble(11, ground);
			stmt.setDouble(12, flying);
			stmt.setDouble(13, psychic);
			stmt.setDouble(14, bug);
			stmt.setDouble(15, rock);
			stmt.setDouble(16, ghost);
			stmt.setDouble(17, dragon);
			stmt.setDouble(18, dark);
			stmt.setDouble(19, steel);
			stmt.setDouble(20, fairy);
			stmt.setDouble(21, none);

			stmt.executeUpdate();
		}
		stmt1.close();
		stmt.close();
	}

	public static void createFastMovesTable(Connection c) throws SQLException, IOException {

		Statement stmt1 = c.createStatement();
		stmt1.executeUpdate("DROP TABLE IF EXISTS Fastmoves");
		String sql = "CREATE TABLE IF NOT EXISTS Fastmoves ("+
				"Name TEXT NOT NULL ,"+
				"ID INT PRIMARY KEY ,"+
				"Animation REAL NOT NULL ,"+
				"Type TEXT NOT NULL ,"+
				"Power REAL  NOT NULL ,"+
				"Accuracy REAL  NOT NULL ,"+
				"StamLoss REAL NOT NULL ,"+
				"TrainerMin REAL NOT NULL ,"+
				"TrainerMax REAL  NOT NULL ,"+
				"fx TEXT NOT NULL,"+
				"Duration REAL NOT NULL ,"+
				"Start REAL  NOT NULL ,"+
				"Stop REAL NOT NULL ,"+
				"Energy REAL NOT NULL )";

		stmt1.executeUpdate(sql);

		Reader in = new FileReader("resources/Copy of Pokemon v4 - TDO fastmoves.csv");
		Iterable<CSVRecord> records = CSVFormat.EXCEL.withDelimiter(';').parse(in);

		PreparedStatement stmt = c.prepareStatement(INSERT_FASTMOVE);
		for (CSVRecord record : records) {

			stmt.setString(1, record.get(0));
			stmt.setInt(   2, Integer.parseInt(record.get(1)) );
			stmt.setDouble(3, Double.parseDouble(record.get(2).replace(',', '.')) );
			stmt.setString(4, record.get(3));
			stmt.setDouble(5, Double.parseDouble(record.get(4).replace(',', '.')) );
			stmt.setDouble(6, Double.parseDouble(record.get(5).replace(',', '.')) );
			stmt.setDouble(7, Double.parseDouble(record.get(6).replace(',', '.')) );
			stmt.setDouble(8, Double.parseDouble(record.get(7).replace(',', '.')) );
			stmt.setDouble(9, Double.parseDouble(record.get(8).replace(',', '.')) );
			stmt.setString(10, record.get(9));
			stmt.setDouble(11, Double.parseDouble(record.get(10).replace(',', '.')) );
			stmt.setDouble(12, Double.parseDouble(record.get(11).replace(',', '.')) );
			stmt.setDouble(13, Double.parseDouble(record.get(12).replace(',', '.')) );
			stmt.setDouble(14,Double.parseDouble(record.get(13).replace(',', '.')));


//			for (int i = 1; i <= 14; i++)
//				stmt.setString(i, record.get(i-1));

			stmt.executeUpdate();
		}
		stmt1.close();
		stmt.close();

	}

	public static void createSpecialMovesTable(Connection c) throws SQLException, IOException {
		Statement stmt1 = c.createStatement();
		stmt1.executeUpdate("DROP TABLE IF EXISTS Specialmoves");
		String sql = "CREATE TABLE IF NOT EXISTS Specialmoves ("+
				"ID INTEGER PRIMARY KEY,"+
				"Name  TEXT  NOT NULL,"+
				"Type  TEXT  NOT NULL,"+
				"Pwr  REAL  NOT NULL,"+
				"Acc  REAL  NOT NULL,"+
				"Crit  REAL  NOT NULL,"+
				"StamLoss  REAL  NOT NULL,"+
				"Heal  REAL  NOT NULL,"+
				"DurMS  REAL  NOT NULL,"+
				"Startms  REAL  NOT NULL,"+
				"Endms  REAL  NOT NULL,"+
				"EnergyDelta  REAL  NOT NULL)";
		stmt1.executeUpdate(sql);
		stmt1.executeUpdate("DELETE FROM Specialmoves");

		Reader in = new FileReader("resources/Copy of Pokemon v4 - TDO specialmoves.csv");
		Iterable<CSVRecord> records = CSVFormat.EXCEL.withDelimiter(';').parse(in);

		PreparedStatement stmt = c.prepareStatement(INSERT_SPECIALMOVE);
		boolean firstSkipped = false;
		for (CSVRecord record : records) {

			if(!firstSkipped) {firstSkipped = true; continue;}

			stmt.setInt(1,  Integer.parseInt(record.get(0)) );
			stmt.setString(2, record.get(1) );
			stmt.setString(3,record.get(2) );
			stmt.setDouble(4,  Double.parseDouble(record.get(3).replace(',', '.')) );
			stmt.setDouble(5, Double.parseDouble(record.get(4).replace(',', '.')) );
			stmt.setDouble(6, Double.parseDouble(record.get(5).replace(',', '.')) );
			stmt.setDouble(7, Double.parseDouble(record.get(6).replace(',', '.')) );
			stmt.setDouble(8, Double.parseDouble(record.get(7).replace(',', '.')) );
			stmt.setDouble(9, Double.parseDouble(record.get(8).replace(',', '.')) );
			stmt.setDouble(10,  Double.parseDouble(record.get(9).replace(',', '.')) );
			stmt.setDouble(11, Double.parseDouble(record.get(10).replace(',', '.')) );
			stmt.setDouble(12, Double.parseDouble(record.get(11).replace(',', '.')) );

			stmt.executeUpdate();
		}

		stmt1.close();
		stmt.close();

	}

	public static void createLevelCPTable(Connection c) throws SQLException, IOException {
		Statement stmt1 = c.createStatement();
		stmt1.executeUpdate("DROP TABLE IF EXISTS LevelCP");
		String sql = "CREATE TABLE IF NOT EXISTS LevelCP ("+
				"LEVEL REAL PRIMARY KEY,"+
				"CPMultiplier REAL NOT NULL)";
		stmt1.executeUpdate(sql);
		stmt1.executeUpdate("DELETE FROM LevelCP");

		Reader in = new FileReader("resources/Pokemon GO Species_Moveset Analysis Tool levelcp.csv");
		Iterable<CSVRecord> records = CSVFormat.EXCEL.withDelimiter(';').parse(in);

		PreparedStatement stmt = c.prepareStatement("Insert into LevelCP values (?, ?)");
		for (CSVRecord record : records) {

			stmt.setDouble(1,  Double.parseDouble( record.get(0).replace(',', '.')) );
			stmt.setDouble(2, Double.parseDouble(  record.get(1).replace(',', '.')) );

			stmt.executeUpdate();
		}

		stmt1.close();
		stmt.close();
	}


	static int lookup(Connection c, String table, String name) throws SQLException {

		PreparedStatement stmt = null;
		ResultSet result = null;
		try {
			stmt = c.prepareStatement("Select ID from "+ table +" where Name = ?");
			stmt.setString(1, name);

			result = stmt.executeQuery();
			result.next();

			return result.getInt(1);
		} catch (Exception e) {
			System.out.println("Select ID from "+ table +" where Name = '"+ name +"'");
			throw e;
		} finally {
			result.close();
			stmt.close();
		}
	}


	public static void denormalizeTables(Connection c) throws SQLException {

		Statement stmt = c.createStatement();
		stmt.executeUpdate("Drop table if exists DMovesets");
		stmt.executeUpdate("CREATE TABLE DMovesets ("+
				"Id INTEGER PRIMARY KEY NOT NULL ,"+
				"Pokemon Integer not null,"+
				"PokemonName TEXT ,"+
				"PokemonAvailable TEXT  ,"+
				"PokemonEvolutions REAL  ,"+
				"PokemonType1 TEXT  ,"+
				"PokemonType2 TEXT  ,"+
				"PokemonAttack REAL  ,"+
				"PokemonDefense REAL  ,"+
				"PokemonStamina REAL  ,"+
				"Fastmove integer not null,"+
				"Fastmovename TEXT  ,"+
				"Fastmovetype TEXT  ,"+
				"Fastmoveduration REAL ,"+
				"Fastmovepower REAL ,"+
				"Fastmoveenergy REAL ,"+
				"Specialmove integer not null,"+
				"Specialmovename TEXT," +
				"Specialmovetype TEXT  ,"+
				"Specialmoveduration REAL  ,"+
				"Specialmovepower REAL  ," +
				"Specialmoveenergy REAL  ," +
				"UNIQUE (Pokemon, Fastmove, Specialmove))");

		stmt.executeUpdate("Insert into DMovesets(Id, Pokemon, Fastmove, Specialmove) select ID, Pokemon, Fastmove, Specialmove from Movesets");

		stmt.executeUpdate("update DMovesets "+
				"set PokemonName = (select Name from Pokemon where Pokemon = ID), "+
				"PokemonAvailable = (select Pokemon.Available from Pokemon where Pokemon = ID), "+
				"PokemonEvolutions = (select Pokemon.Evolutions from Pokemon where Pokemon = ID),"+
				"PokemonType1 = (select Pokemon.Type1 from Pokemon where Pokemon = ID),"+
				"PokemonType2 = (select Pokemon.Type2 from Pokemon where Pokemon = ID),"+
				"PokemonAttack = (select Pokemon.Attack from Pokemon where Pokemon = ID),"+
				"PokemonDefense = (select Pokemon.Defense from Pokemon where Pokemon = ID),"+
				"PokemonStamina = (select Pokemon.Stamina from Pokemon where Pokemon = ID);");

		stmt.executeUpdate("update DMovesets "+
				"set Fastmovename = (select Name from fastmoves where fastmove = ID), "+
				"Fastmovetype = (select fastmoves.\"type\" from fastmoves where fastmove = ID), "+
				"Fastmoveduration = (select fastmoves.Duration from fastmoves where fastmove = ID), "+
				"Fastmovepower = (select fastmoves.Power from fastmoves where fastmove = ID), " +
				"Fastmoveenergy = (select fastmoves.Energy from fastmoves where fastmove = ID);");

		stmt.executeUpdate("update DMovesets "+
				"set specialmovename = (select Name from Specialmoves where specialmove = ID), "+
				"specialmovetype = (select Specialmoves.\"type\" from Specialmoves where specialmove = ID), "+
				"specialmoveduration = (select Specialmoves.DurMS from Specialmoves where specialmove = ID), "+
				"specialmovepower = (select Specialmoves.Pwr from Specialmoves where specialmove = ID), " +
				"specialmoveenergy = (select Specialmoves.EnergyDelta from Specialmoves where specialmove = ID);");


	}

}
