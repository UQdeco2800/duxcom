package uq.deco2800.duxcom.savegame;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

import javax.ws.rs.WebApplicationException;

import uq.deco2800.duxcom.objectives.Objective;
import uq.deco2800.duxcom.objectives.ScoreObjective;
import uq.deco2800.duxcom.scoring.ScoreSystem;
import uq.deco2800.singularity.clients.duxcom.DuxcomClient;
import uq.deco2800.singularity.common.representations.duxcom.PlayerStats;

public class SaveObjectives {
	
	// Instance variables
	private PlayerStats stats; // Player Progress 
	
	private DuxcomClient client; 
	
	/**
	 * Constructor
	 */
	public SaveObjectives(){
			stats = new PlayerStats();
		}
		
	/**
	 * Saves the current score to the server
	 * 
	 * @throws JsonProcessingException 
	 * 
	 * @throws WebApplicationException 
	 */
	public void saveObjectives(String userID, List<Objective> objectives) throws JsonProcessingException{

//		// Get Score
//		Integer saveScore = getScoreObject(objectives).getScore();
//		
//		// Set UserID 
//		stats.setUserId(userID);
//		stats.setScore(saveScore.toString());
//		stats.setLevel("1");
//		client.createPlayerStats(stats);
	}
	
	/**
	 * 	Get the Score Objective from all the Objectives
	 * 
	 * @param objectives
	 * 		List of all the objectives 
	 * @return
	 * 		The score Objective from all the objectives
	 */
	private ScoreSystem getScoreObject(List<Objective> objectives){
		for (Objective objective: objectives){
			if(objective instanceof ScoreObjective){
				Objective k = (ScoreObjective)objective;
				return (ScoreSystem) k.getObjectiveTarget();
			}
		}
		return null;
	}
	
}
