package uq.deco2800.duxcom.objectives;

import uq.deco2800.duxcom.scoring.ScoreSystem;

/**
 * An objective for getting to a particular score in a game.
 * @require scoring != null && targetScore > 0
 * @ensure description = "Reach -targetScore- points."
 * Created by Tom B on 3/09/2016.
 */
public class ScoreObjective extends Objective {
    public ScoreObjective(ScoreSystem scoring, int targetScore) {
        super(scoring, targetScore, "Reach " + targetScore + " points");
    }
}
