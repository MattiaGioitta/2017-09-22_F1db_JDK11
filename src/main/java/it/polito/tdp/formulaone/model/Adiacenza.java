package it.polito.tdp.formulaone.model;

public class Adiacenza {
	
	private Race r1;
	private Race r2;
	private int weight;
	/**
	 * @param r1
	 * @param r2
	 * @param weight
	 */
	public Adiacenza(Race r1, Race r2, int weight) {
		super();
		this.r1 = r1;
		this.r2 = r2;
		this.weight = weight;
	}
	/**
	 * @return the r1
	 */
	public Race getR1() {
		return r1;
	}
	/**
	 * @param r1 the r1 to set
	 */
	public void setR1(Race r1) {
		this.r1 = r1;
	}
	/**
	 * @return the r2
	 */
	public Race getR2() {
		return r2;
	}
	/**
	 * @param r2 the r2 to set
	 */
	public void setR2(Race r2) {
		this.r2 = r2;
	}
	/**
	 * @return the weight
	 */
	public int getWeight() {
		return weight;
	}
	/**
	 * @param weight the weight to set
	 */
	public void setWeight(int weight) {
		this.weight = weight;
	}
	@Override
	public String toString() {
		return r1.getName() + ", " + r2.getName() + ", peso=" + weight ;
	}
	
	

}
