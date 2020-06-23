package it.polito.tdp.formulaone.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.formulaone.db.FormulaOneDAO;

public class Model {
	
	private FormulaOneDAO dao;
	private Graph<Race,DefaultWeightedEdge> graph;
	private Map<Integer,Race> idMap;
	private Simulator sim;
	
	public Model() {
		this.dao = new FormulaOneDAO();
		this.idMap = new HashMap<>();
	}

	public List<Season> getSeasons() {
		return this.dao.getAllSeasons();
	}

	public void createGraph(Season chosen) {
		this.graph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		this.dao.loadRaces(idMap);
		List<Adiacenza> adiacenze = this.dao.getAdiacenze(chosen,idMap);
		for(Adiacenza a : adiacenze) {
			if(!this.graph.containsVertex(a.getR1())) {
				this.graph.addVertex(a.getR1());
			}
			if(!this.graph.containsVertex(a.getR2())) {
				this.graph.addVertex(a.getR2());
			}
			if(this.graph.getEdge(a.getR1(), a.getR2()) == null) {
				Graphs.addEdgeWithVertices(this.graph, a.getR1(), a.getR2(),a.getWeight());
			}
		}
	}

	public Integer nVertici() {
		// TODO Auto-generated method stub
		return this.graph.vertexSet().size();
	}

	public Integer nArchi() {
		// TODO Auto-generated method stub
		return this.graph.edgeSet().size();
	}

	public List<Adiacenza> getMassimi() {
		List<Adiacenza> l = new ArrayList<>();
		double peso = 0.0;
		for(DefaultWeightedEdge e: this.graph.edgeSet()) {
			if(this.graph.getEdgeWeight(e)>peso) {
				peso = this.graph.getEdgeWeight(e);
			}
		}
		for(DefaultWeightedEdge e: this.graph.edgeSet()) {
			if(this.graph.getEdgeWeight(e)==peso) {
				l.add(new Adiacenza(this.graph.getEdgeSource(e),this.graph.getEdgeTarget(e),(int)this.graph.getEdgeWeight(e)));
			}
		}
		return l;
	}

	public List<Race> getGare() {
		List<Race> l = new ArrayList<>();
		for(DefaultWeightedEdge e : this.graph.edgeSet()) {
			if(!l.contains(this.graph.getEdgeSource(e))) {
				l.add(this.graph.getEdgeSource(e));
			}
			if(!l.contains(this.graph.getEdgeTarget(e))) {
				l.add(this.graph.getEdgeTarget(e));
			}
			
		}
		return l;
	}

	public void simula(Integer t, Double prob, Race scelto) {
		this.sim = new Simulator();
		this.sim.init(prob, t, scelto);
		this.sim.run();
		
	}

	public List<Driver> getDrivers() {
		// TODO Auto-generated method stub
		return this.sim.getDrivers();
	}

	
}
