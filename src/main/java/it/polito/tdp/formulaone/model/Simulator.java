package it.polito.tdp.formulaone.model;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import it.polito.tdp.formulaone.db.FormulaOneDAO;
import it.polito.tdp.formulaone.model.Event.EventType;

public class Simulator {
	
	//coda
	private PriorityQueue<Event> queue;
	
	
	private double prob;
	private int t;
	private List<LapTime> tempi;
	private FormulaOneDAO dao;
	private List<Driver> drivers;
	
	public void init(double prob, int t, Race scelto) {
		this.t = t;
		this.prob = prob;
		this.drivers = new ArrayList<>();
		this.dao = new FormulaOneDAO();
		this.queue = new PriorityQueue<>();
		this.tempi = new ArrayList<>();
		this.tempi = this.dao.getTempi(scelto);
		for(LapTime l : this.tempi) {
			Driver d = new Driver(l.getDriverId());
			if(!this.drivers.contains(d)) {
				this.drivers.add(d);
			}			
			Event e = new Event(EventType.INGARA,l.getMiliseconds(),d,l);
			this.queue.add(e);
		}
	}
	
	public void run() {
		while(!this.queue.isEmpty()) {
			Event e = this.queue.poll();
			processEvent(e);
		}
	}

	private void processEvent(Event e) {
		switch(e.getTipo()) {
		case INGARA:
			
			Double p = Math.random();
			if(p<prob) {
				this.queue.add(new Event(EventType.PAUSA,e.getTime()+t,e.getDriver(),e.getLap()));
			}
			else {
				if(isPrimo(e.getDriver(),e.getTime(),e.getLap())) {
					e.getDriver().incrementaPunti();
				}
			}
			
			break;
		case PAUSA:
			if(isPrimo(e.getDriver(),e.getTime(),e.getLap())) {
				e.getDriver().incrementaPunti();
			}
			break;
		}
	}

	private boolean isPrimo(Driver driver, int i, LapTime lapTime) {
		int bestGiro = Integer.MAX_VALUE;
		Driver best = null;
		for(LapTime l : this.tempi) {
			if(l.getLap()==lapTime.getLap()) {
				if(l.getMiliseconds()<bestGiro) {
					bestGiro = l.getMiliseconds();
					best = new Driver(l.getDriverId());
				}
			}
		}
		if(best.equals(driver)) {
			return true;
		}
		return false;
	}

	/**
	 * @return the drivers
	 */
	public List<Driver> getDrivers() {
		return drivers;
	}
	

}
