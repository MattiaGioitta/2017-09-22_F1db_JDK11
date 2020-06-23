package it.polito.tdp.formulaone.model;

public class Event implements Comparable<Event>{
	
	public enum EventType{
		INGARA,
		PAUSA,
	}

	private EventType tipo;
	private int time;
	private Driver driver;
	private LapTime lap;
	/**
	 * @return the lap
	 */
	public LapTime getLap() {
		return lap;
	}
	/**
	 * @param tipo
	 * @param time
	 * @param driver
	 * @param lap
	 */
	public Event(EventType tipo, int time, Driver driver, LapTime lap) {
		super();
		this.tipo = tipo;
		this.time = time;
		this.driver = driver;
		this.lap = lap;
	}
	/**
	 * @param tipo
	 * @param time
	 * @param driver
	 */
	public Event(EventType tipo, int time, Driver driver) {
		super();
		this.tipo = tipo;
		this.time = time;
		this.driver = driver;
	}
	/**
	 * @return the tipo
	 */
	public EventType getTipo() {
		return tipo;
	}
	/**
	 * @return the time
	 */
	public int getTime() {
		return time;
	}
	/**
	 * @return the driver
	 */
	public Driver getDriver() {
		return driver;
	}
	@Override
	public int compareTo(Event o) {
		// TODO Auto-generated method stub
		return this.time-o.time;
	}
	
	
}
