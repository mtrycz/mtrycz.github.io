package com.mtrycz.pkgocounters.calculators;

import java.sql.Connection;
import java.sql.SQLException;

import model.PokeMonMoveSet;

public interface Calculator {
	
	public String getTableName();
	public void makeAndInsertCalculation(Connection c, PokeMonMoveSet attacker, PokeMonMoveSet defender);
	
}
