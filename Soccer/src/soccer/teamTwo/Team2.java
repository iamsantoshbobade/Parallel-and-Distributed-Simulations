package soccer.teamTwo;

import java.util.Random;

import soccer.constants.Constants;
import soccer.utils.Strategy;

public class Team2 {

	
	int strategy=0;
	
	public int getStrategy() {
		return strategy;
	}

	public void setStrategy(int strategy) {
		this.strategy = strategy;
	}

	public Team2(int Stra) {
		// TODO Auto-generated constructor stub
		
		strategy=Stra;
	}

	public int teamPlayer(int[] local_area, int ball_direction, int x, int y) {

		if (this.getStrategy() == 0) {

			return Strategy.bruteForce(local_area, ball_direction, x, y);
		} 
		
		if (this.getStrategy() == 1) {
		
			return Strategy.LuisStrategy(local_area, ball_direction, x, y);
		
			
		}
		/* if (this.strategy()==number of your strategy){
		 *
		 * return Strategy.yourStartegy(local_area, ball_direction, x, y)
		 } 
		 */
		
		return ball_direction;

	}

	private int random_heading() {
		// TODO Auto-generated method stub

		int m;

		Random r = new Random();

		m = ((r.nextInt(10000) / 256) % 8);
		switch (m) {
		case 0:
			return (Constants.N);
		case 1:
			return (Constants.S);
		case 2:
			return (Constants.E);
		case 3:
			return (Constants.W);
		case 4:
			return (Constants.NW);
		case 5:
			return (Constants.NE);
		case 6:
			return (Constants.SW);
		case 7:
			return (Constants.SE);
		default:
			return (Constants.N);

		}
	}

	private boolean ball_infront(int[] local_area) {
		// TODO Auto-generated method stub
		if ((local_area[Constants.NW] == Constants.BALL)
				|| (local_area[Constants.W] == Constants.BALL)
				|| (local_area[Constants.SW] == Constants.BALL)) {
			return true;
		} else {
			return false;
		}
	}

}
