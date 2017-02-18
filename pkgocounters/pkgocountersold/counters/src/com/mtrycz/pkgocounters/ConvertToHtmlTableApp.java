package com.mtrycz.pkgocounters;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import model.TypeCounters;

public class ConvertToHtmlTableApp {

	public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException {

		Connection c = null;
		//		Class.forName("org.sqlite.JDBC");
		//		c = DriverManager.getConnection("jdbc:sqlite:mtrycz.db");

		Class.forName("org.postgresql.Driver");
		Properties props = new Properties();
		props.setProperty("user","postgres");
		props.setProperty("password","postgres");
		c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/mtrycz", props);

		QueryRunner run = new QueryRunner();
		ResultSetHandler<List<TypeCounters>> h = new BeanListHandler<TypeCounters>(TypeCounters.class);

		String[] limits = new String[] {"1.563" , "1.95"}; // 1.563, 1.95

		for (String limit : limits) {

			List<TypeCounters> counters = run.query(c, "select * from typecounters where attackeradvantage > "+ limit +" order by attackeradvantage desc, attackermoveset asc, defendermoveset asc;", h);

			Reader in = new FileReader("results/allcounters.csv");
			Iterable<CSVRecord> records = CSVFormat.EXCEL.withDelimiter(';').parse(in);

			FileWriter output = new FileWriter("results/results_table"+ limit +".html");

			output.append("<table id='counters' class='table table-striped table-hover'>");
			output.append("<thead><tr><th>Attacker</th><th>Fastmove</th><th>Specialmove</th><th>Defender</th><th>Fastmove</th><th>Specialmove</th><th>Atk Bonus</th><th>Def Bonus</th><th>Tot Bonus</th></tr></thead>\n");

			for (TypeCounters counter : counters) {

				StringBuilder sb = new StringBuilder();

				sb.append("<tr>");

				sb.append("<td>"+ counter.getAttackerName() +"</td>");
				sb.append("<td>"+ counter.getAttackerFastmove() +"</td>");
				sb.append("<td>"+ counter.getAttackerSpecialmove() +"</td>");
				sb.append("<td>"+ counter.getDefenderName() +"</td>");
				sb.append("<td>"+ counter.getDefenderFastmove() +"</td>");
				sb.append("<td>"+ counter.getDefenderSpecialmove() +"</td>");
				sb.append("<td>"+ counter.getAttackerAttackModifier() +"</td>");
				sb.append("<td>"+ counter.getAttackerDefenseModifier() +"</td>");
				sb.append("<td>"+ counter.getAttackerAdvantage() +"</td>");

				sb.append("</tr>");
				sb.append("\n");

				output.write(sb.toString());
			}

			output.append("</table>");
			output.flush();
			output.close();
		}
	}

}
