package com.mtrycz.pkgocounters.calculators;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import com.mtrycz.pkgocounters.helpers.Helpers;

import model.PokeMonMoveSet;

public class DpsCalculator implements Calculator {

    static public final String TABLE_NAME = "dpscounters";

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    public DpsCalculator(Connection c) throws SQLException {
        recreateTable(c);
    }

    private void recreateTable(Connection c) throws SQLException {

        Statement stmt = c.createStatement();
        stmt.executeUpdate("DROP TABLE IF EXISTS "+ TABLE_NAME);
        stmt.executeUpdate("CREATE TABLE "+ TABLE_NAME +"("
                + "AttackerMoveset INTEGER NOT NULL,"
                + "AttackerName TEXT NOT NULL,"
                + "AttackerAttack INT NOT NULL,"
                + "AttackerDefense INT NOT NULL,"
                + "AttackerStamina INT NOT NULL,"
                + "AttackerLevel REAL NOT NULL,"
                + "AttackerMaxHP REAL NOT NULL,"
                + "AttackerMaxCP REAL NOT NULL,"
                + "AttackerType1 TEXT NOT NULL,"
                + "AttackerType2 TEXT NOT NULL,"

                + "AttackerFastmove TEXT NOT NULL,"
                + "AttackerFastmoveType TEXT NOT NULL,"
                + "AttackerFastmovePower INT NOT NULL,"
                + "AttackerFastmoveEnergyGenerated INT NOT NULL,"
                + "AttackerFastmoveDuration INT NOT NULL,"
                + "AttackerSpecialmove TEXT NOT NULL,"
                + "AttackerSpecialmoveType TEXT NOT NULL,"
                + "AttackerSpecialmovePower INT NOT NULL,"
                + "AttackerSpecialmoveDuration INT NOT NULL,"
                + "AttackerSpecialmoveEnergyConsumed INT NOT NULL,"

                + "AttackerSpecialmoveStamLoss INT NOT NULL,"
                + "DefenderMoveset INTEGER NOT NULL,"
                + "DefenderName TEXT NOT NULL,"
                + "DefenderAttack INT NOT NULL,"
                + "DefenderDefense INT NOT NULL,"
                + "DefenderStamina INT NOT NULL,"
                + "DefenderLevel REAL NOT NULL,"
                + "DefenderMaxHP INT NOT NULL,"
                + "DefenderMaxCP INT NOT NULL,"
                + "DefenderType1 TEXT NOT NULL,"

                + "DefenderType2 TEXT NOT NULL,"
                + "DefenderFastmove TEXT NOT NULL,"
                + "DefenderFastmoveType TEXT NOT NULL,"
                + "DefenderFastmovePower INT NOT NULL,"
                + "DefenderFastmoveEnergyGenerated INT NOT NULL,"
                + "DefenderFastmoveDuration INT NOT NULL,"
                + "DefenderSpecialmove TEXT NOT NULL,"
                + "DefenderSpecialmoveType TEXT NOT NULL,"
                + "DefenderSpecialmovePower INT NOT NULL,"
                + "DefenderSpecialmovePower INT NOT NULL,"

                + "DefenderSpecialmoveDuration INT NOT NULL,"
                + "DefenderSpecialmoveEnergyConsumed INT NOT NULL,"
                + "DefenderSpecialmoveStamLoss INT NOT NULL,"
                + "AttackerAttackModifier REAL NOT NULL,"
                + "AttackerDefenseModifier REAL NOT NULL,"
                + "AttackerAdvantage REAL NOT NULL,"
                + "Battleduration REAL NOT NULL,"
                + "HPLeft REAL NOT NULL,"
                + "HPLeftPercent REAL NOT NULL,"
                + "PRIMARY KEY (AttackerMoveset, DefenderMoveset) )");
        stmt.close();
    }
    @Override
    public void makeAndInsertCalculation(Connection c, PokeMonMoveSet attacker, PokeMonMoveSet defender) {


        try {
            PreparedStatement stmt = c.prepareStatement("INSERT INTO "+ TABLE_NAME +" values (?, ?, ?, ?, ?, ?, ?, ?, ?, ??, ?, ?, ?, ?, ?, ?, ?, ?, ??, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

            int i = 1;

            stmt.setInt(i++, attacker.getId());
            stmt.setString(i++, attacker.getPokemonName());
            stmt.setDouble(i++, attacker.getPokemonAttack());
            stmt.setDouble(i++, attacker.getPokemonDefense());
            stmt.setDouble(i++, attacker.getPokemonStamina());
            stmt.setDouble(i++, 25 );
            stmt.setInt(i++, Helpers.calculateHP(attacker.getPokemonStamina(), 25) );
            stmt.setInt(i++, Helpers.calculateMaxCP(attacker, 25) );
            stmt.setString(i++, attacker.getPokemonType1());
            stmt.setString(i++, attacker.getPokemonType2());

            stmt.setInt(i++, attacker.getFastmove());
            stmt.setString(i++, attacker.getFastmovetype());
            stmt.setInt(i++, attacker.getFastmovepower());
            stmt.setInt(i++, defender.getId());
            stmt.setString(i++, defender.getPokemonName());
            stmt.setString(i++, defender.getPokemonType1());
            stmt.setString(i++, defender.getPokemonType2());
            stmt.setString(i++, defender.getFastmovename());
            stmt.setString(i++, defender.getFastmovetype());
            stmt.setString(i++, defender.getSpecialmovename());

            stmt.setString(i++, defender.getSpecialmovetype());
            stmt.setInt(i++, attacker.getId());
            stmt.setString(i++, attacker.getPokemonName());
            stmt.setString(i++, attacker.getPokemonType1());
            stmt.setString(i++, attacker.getPokemonType2());
            stmt.setString(i++, attacker.getFastmovename());
            stmt.setString(i++, attacker.getFastmovetype());
            stmt.setString(i++, attacker.getSpecialmovename());
            stmt.setString(i++, attacker.getSpecialmovetype());
            stmt.setInt(i++, defender.getId());

            stmt.setString(i++, defender.getPokemonName());
            stmt.setString(i++, defender.getPokemonType1());
            stmt.setString(i++, defender.getPokemonType2());
            stmt.setString(i++, defender.getFastmovename());
            stmt.setString(i++, defender.getFastmovetype());
            stmt.setString(i++, defender.getSpecialmovename());
            stmt.setString(i++, defender.getSpecialmovetype());



        } catch (SQLException e) {
            System.out.println("ERROR: Insert failed for "+ attacker.getId() + " and "+ defender.getId());
            e.printStackTrace();
        }





    }

}
