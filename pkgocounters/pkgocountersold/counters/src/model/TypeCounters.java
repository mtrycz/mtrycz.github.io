package model;

public class TypeCounters {

	private int  AttackerMoveset;
	private String AttackerName; 
	private String AttackerFastmove; 
	private String AttackerSpecialmove; 
	private int DefenderMoveset; 
	private String DefenderName; 
	private String DefenderFastmove; 
	private String DefenderSpecialmove; 
	private double AttackerAttackModifier;
	private double AttackerDefenseModifier;
	
	
	public double getAttackerAdvantage() {
		return AttackerAdvantage;
	}


	private double AttackerAdvantage;
	
	public int getAttackerMoveset() {
		return AttackerMoveset;
	}
	public void setAttackerMoveset(int attackerMoveset) {
		AttackerMoveset = attackerMoveset;
	}
	public double getAttackerAttackModifier() {
		return AttackerAttackModifier;
	}
	public void setAttackerAttackModifier(double attackerAttackModifier) {
		AttackerAttackModifier = attackerAttackModifier;
	}
	public double getAttackerDefenseModifier() {
		return AttackerDefenseModifier;
	}
	public void setAttackerDefenseModifier(double attackerDefenseModifier) {
		AttackerDefenseModifier = attackerDefenseModifier;
	}
	public void setAttackerAdvantage(double attackerAdvantage) {
		AttackerAdvantage = attackerAdvantage;
	}
	public String getAttackerName() {
		return AttackerName;
	}
	public void setAttackerName(String attackerName) {
		AttackerName = attackerName;
	}
	public String getAttackerFastmove() {
		return AttackerFastmove;
	}
	public void setAttackerFastmove(String attackerFastmove) {
		AttackerFastmove = attackerFastmove;
	}
	public String getAttackerSpecialmove() {
		return AttackerSpecialmove;
	}
	public void setAttackerSpecialmove(String attackerSpecialmove) {
		AttackerSpecialmove = attackerSpecialmove;
	}
	public int getDefenderMoveset() {
		return DefenderMoveset;
	}
	public void setDefenderMoveset(int defenderMoveset) {
		DefenderMoveset = defenderMoveset;
	}
	public String getDefenderName() {
		return DefenderName;
	}
	public void setDefenderName(String defenderName) {
		DefenderName = defenderName;
	}
	public String getDefenderFastmove() {
		return DefenderFastmove;
	}
	public void setDefenderFastmove(String defenderFastmove) {
		DefenderFastmove = defenderFastmove;
	}
	public String getDefenderSpecialmove() {
		return DefenderSpecialmove;
	}
	public void setDefenderSpecialmove(String defenderSpecialmove) {
		DefenderSpecialmove = defenderSpecialmove;
	}
	
	
	public Object[] toObjectArray() {
		Object[] out = new Object[11];
		int i = 0;
		
		out[i++] = AttackerMoveset;
		out[i++] = AttackerName; 
		out[i++] = AttackerFastmove; 
		out[i++] = AttackerSpecialmove; 
		out[i++] = DefenderMoveset; 
		out[i++] = DefenderName; 
		out[i++] = DefenderFastmove; 
		out[i++] = DefenderSpecialmove; 
		out[i++] = AttackerAttackModifier;
		out[i++] = AttackerDefenseModifier;
		out[i++] = AttackerAdvantage;

		return out;
	}
	
}
