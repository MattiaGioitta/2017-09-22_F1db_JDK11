package it.polito.tdp.formulaone.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.formulaone.model.Adiacenza;
import it.polito.tdp.formulaone.model.LapTime;
import it.polito.tdp.formulaone.model.Race;
import it.polito.tdp.formulaone.model.Season;

public class FormulaOneDAO {

	public List<Season> getAllSeasons() {
		String sql = "SELECT year, url FROM seasons ORDER BY year";
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();
			List<Season> list = new ArrayList<>();
			while (rs.next()) {
				list.add(new Season(rs.getInt("year"), rs.getString("url")));
			}
			conn.close();
			return list;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<Adiacenza> getAdiacenze(Season chosen, Map<Integer, Race> idMap) {
		final String sql = "SELECT r1.raceId AS rd1,r2.raceId AS rd2, COUNT(r1.driverId) AS peso " + 
				"FROM results AS r1,results AS r2,races AS ra1,races AS ra2 " + 
				"WHERE r1.raceId=ra1.raceId " + 
				"AND r2.raceId=ra2.raceId " + 
				"AND ra1.year=? " + 
				"AND ra2.year=? " + 
				"AND r1.raceId<>r2.raceId " + 
				"AND r1.driverId=r2.driverId " + 
				"AND r1.statusId=1 " + 
				"AND r2.statusId=1 " + 
				"AND r1.raceId>r2.raceId " + 
				"GROUP BY r1.raceId,r2.raceId";
		List<Adiacenza> lista = new ArrayList<>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, chosen.getYear());
			st.setInt(2, chosen.getYear());
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				Adiacenza a = new Adiacenza(idMap.get(rs.getInt("rd1")),idMap.get(rs.getInt("rd2")),rs.getInt("peso"));
				lista.add(a);
			}
			conn.close();
			return lista;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
	}

	public void loadRaces(Map<Integer, Race> idMap) {
		final String sql = "SELECT * FROM races";
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				int raceId = rs.getInt("raceId");
				int year = rs.getInt("year");
				int round = rs.getInt("round");
				int circuitId = rs.getInt("circuitId"); // refers to {@link Circuit}
				String name = rs.getString("name");
				LocalDate date = rs.getDate("date").toLocalDate();
				LocalTime time = null;
				String url = rs.getString("url");
				Race r = new Race(raceId,year,round,circuitId,name,date,time,url);
				idMap.put(r.getRaceId(),r);
			}
			conn.close();
			

		} catch (SQLException e) {
			e.printStackTrace();
			
		}
	}

	public List<LapTime> getTempi(Race scelto) {
		final String sql = "SELECT * " + 
				"FROM laptimes AS l " + 
				"WHERE l.raceId=?";
		List<LapTime> lista = new ArrayList<>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, scelto.getRaceId());
			
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				int raceId =scelto.getRaceId(); // refers to {@link Race}
				int driverId = rs.getInt("driverId"); // referst to {@link Driver}
				int lap = rs.getInt("lap");
				// NOT: only the combination of the 3 fields (raceId, driverId, lap) is guaranteed to be unique
				int position = rs.getInt("position");
				String time = rs.getString("time"); // printable version of lap time
				int miliseconds = rs.getInt("milliseconds");
				LapTime l = new LapTime(raceId,driverId,lap,position,time,miliseconds);
				lista.add(l);
			}
			conn.close();
			return lista;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
	}

}

