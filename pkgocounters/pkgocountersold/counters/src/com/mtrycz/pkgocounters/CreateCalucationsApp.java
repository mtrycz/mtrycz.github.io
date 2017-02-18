package com.mtrycz.pkgocounters;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.mtrycz.pkgocounters.calculators.Calculator;
import com.mtrycz.pkgocounters.calculators.DpsCalculator;
import com.mtrycz.pkgocounters.helpers.Helpers;

import model.PokeMonMoveSet;

public class CreateCalucationsApp {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		Connection c = null;
//		Class.forName("org.sqlite.JDBC");
//		c = DriverManager.getConnection("jdbc:sqlite:mtrycz.db");

		Class.forName("org.postgresql.Driver");
		Properties props = new Properties();
		props.setProperty("user","postgres");
		props.setProperty("password","postgres");
		c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/mtrycz", props);

		Helpers.initTypes(c);

//		Calculator calculator = new TypeCounterCalculator(c);
		Calculator calculator = new DpsCalculator(c);
		createCalcuations(c, calculator);

		c.close();
	}

	private static void createCalcuations(Connection c, Calculator calculator) throws SQLException {

		QueryRunner run = new QueryRunner();
		ResultSetHandler<List<PokeMonMoveSet>> h = new BeanListHandler<PokeMonMoveSet>(PokeMonMoveSet.class);

		List<PokeMonMoveSet> attackers = run.query(c, "SELECT * FROM DMovesets", h);
		List<PokeMonMoveSet> defenders = run.query(c, "SELECT * FROM DMovesets", h);

		AtomicInteger progress = new AtomicInteger();
		int total = attackers.size() * defenders.size();

		Date start = new Date();
		attackers.parallelStream()
//			.filter(attacker -> attacker.getPokemonAvailable().equalsIgnoreCase("yes") )
//			.filter(attacker -> attacker.getPokemonEvolutions() == 1)
		.limit(5)
			.forEach(attacker -> {
				defenders.stream()
				.limit(5)
//					.filter(defender -> defender.getPokemonAvailable().equalsIgnoreCase("yes") )
//					.filter(defender -> defender.getPokemonEvolutions() == 1)
					.forEach(defender -> {
						int currentProgress = progress.incrementAndGet();
						if (currentProgress % 10000 == 0)
							System.out.println("Progress: "+ currentProgress +"/"+ total +" ("+ (currentProgress*100/total) +"%) in "+ (new Date().getTime() - start.getTime())+"ms");
						calculator.makeAndInsertCalculation(c, attacker, defender);
					});
			});
		Date end = new Date();

		long time = end.getTime() - start.getTime();
		System.out.println("finished in "+ (time) + "ms. ("+ time / 1000 / 60 +" mins)");
	}
}
