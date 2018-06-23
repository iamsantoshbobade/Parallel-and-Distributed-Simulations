package soccer.teamOne;

import soccer.constants.*;
import soccer.utils.Strategy;

public class Team {

	int strategy = 0;

	public int getStrategy() {
		return strategy;
	}

	public void setStrategy(int strategy) {
		this.strategy = strategy;
	}

	public Team(int Stra) {
		// TODO Auto-generated constructor stub

		strategy = Stra;
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

	// -----------------------------------------------------

	// player2()

	// -----------------------------------------------------
	int player2(int[] local_area, int ball_direction, int x, int y) {

		// * Just do the same thing as player1

		return (teamPlayer(local_area, ball_direction, x, y));
	}

	/*-----------------------------------------------------

	 player3()

	 -----------------------------------------------------
	 */

	int player3(int[] local_area, int ball_direction, int x, int y) {

		// * Just do the same thing as player1

		return (teamPlayer(local_area, ball_direction, x, y));
	}

	/*
	 * -----------------------------------------------------
	 * 
	 * player4()
	 * 
	 * -----------------------------------------------------
	 */
	int player4(int[] local_area, int ball_direction, int x, int y) {

		// * Just do the same thing as player1

		return (teamPlayer(local_area, ball_direction, x, y));
	}

	/*
	 * -----------------------------------------------------
	 * 
	 * team_name()
	 * 
	 * Every team must have a name. Make sure it is exactly 20 characters. Pad
	 * with spaces.
	 * 
	 * -----------------------------------------------------
	 */

	String team_name() {
		String s;

		// "####################\0" //<--- 20 characters
		s = "Krazy Kickers";
		return (s);
	}

	/*
	 * -----------------------------------------------------
	 * 
	 * initialize_game()
	 * 
	 * This function is called only once per game, before play begins. You can
	 * leave it empty, or put code here to initialize your variables, etc.
	 * 
	 * -----------------------------------------------------
	 */
	void initialize_game() {

	}

	/*-----------------------------------------------------

	 initialize_point()

	 This function is called once per point, just
	 before play begins for that point.
	 You can leave it empty, or put code here to initialize 
	 your variables, etc.

	 -----------------------------------------------------
	 */
	void initialize_point() {
	}

	/*-----------------------------------------------------

	 lost_point()

	 If your team loses a point, this function is
	 called, otherwise, if you win the point, won_point()
	 is called.  You can leave it empty, or put code here 
	 for negative re-inforcement, etc.

	 -----------------------------------------------------*/
	void lost_point() {
	}

	/*-----------------------------------------------------

	 won_point()

	 If your team wins a point, this function is
	 called, otherwise, if you lose the point, lost_point()
	 is called.  You can leave it empty, or put code here 
	 for positive re-inforcement, etc.

	 -----------------------------------------------------*/
	void won_point() {
	}

	void game_over() {
	}

}
