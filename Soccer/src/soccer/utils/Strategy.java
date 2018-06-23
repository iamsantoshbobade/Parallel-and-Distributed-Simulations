package soccer.utils;

import java.util.Random;

import soccer.constants.Constants;

public class Strategy {
	
	public static int bruteForce(int[] local_area, int ball_direction, int x, int y){
		


		 //* If right next to the ball, on the west, get to east side.*/
		 
		if (local_area[Constants.N] == Constants.BALL) return(Constants.NE);
		if (local_area[Constants.NE] == Constants.BALL) return(Constants.E);
		if (local_area[Constants.E] == Constants.BALL) return(Constants.SE);
		if (local_area[Constants.SE] == Constants.BALL) return(Constants.E);
		if (local_area[Constants.S] == Constants.BALL) return(Constants.SE);


		// * If already on the west, kick!
		 
		if (local_area[Constants.SW] == Constants.BALL) return(Constants.KICK);
		if (local_area[Constants.W] == Constants.BALL) return(Constants.KICK);
		if (local_area[Constants.NW] == Constants.BALL) return(Constants.KICK);


		 //* If not near the ball, just go to it.
		 
		return(ball_direction);

		
	}
	
public static int LuisStrategy(int[] local_area, int ball_direction, int x, int y){
	
	if (ball_infront(local_area)) {

		// just kick it from in front
		if ((local_area[Constants.SW] == Constants.BALL)
				&& (y == (Constants.MAX_Y - 3))) {

			return Constants.S;
		}

		if ((local_area[Constants.NW] == Constants.BALL) && (y == 2)) {

			return Constants.N;
		}

		if (local_area[Constants.W] == Constants.BALL) {
			return (Constants.KICK);
		}
	}

	else {

		// if the ball is on north o south,
		// stay on the east of it for kick it from in front nex time

		if (local_area[Constants.N] == Constants.BALL) {

			return Constants.NE;
		}

		if (local_area[Constants.S] == Constants.BALL) {

			return Constants.SE;
		}

		// if not near, just put behind ball:

		switch (ball_direction) {
		case Constants.N:
			if (local_area[Constants.NE] == Constants.EMPTY)
				return Constants.NE;
			
			return random_heading();

		case Constants.NE:
			if (local_area[Constants.NE] == Constants.EMPTY)
				return Constants.NE;
			
			return random_heading();
			
		case Constants.NW:
			if (local_area[Constants.NW] == Constants.EMPTY)
				return Constants.NW;
			
			return random_heading();
			
		case Constants.W:
			if ((local_area[Constants.NW] == Constants.EMPTY)) {
				return Constants.NW;
			}
			if (local_area[Constants.W] == Constants.EMPTY)
				return Constants.W;

			if ((local_area[Constants.SW] == Constants.EMPTY))
				return Constants.SW;
			
			return random_heading();

		case Constants.E:
			if ((local_area[Constants.NE] == Constants.EMPTY)) {
				return Constants.NE;
			}
			if ((local_area[Constants.SE] == Constants.EMPTY)) {
				return Constants.SE;
			}

			if (local_area[Constants.E] == Constants.EMPTY)
				return Constants.E;
			return random_heading();

		case Constants.S:
			if (local_area[Constants.SE] == Constants.EMPTY)
				return Constants.SE;
			if (local_area[Constants.S] == Constants.EMPTY)
				return Constants.S;
			if (local_area[Constants.SW] == Constants.EMPTY)
				return Constants.SW;
			return random_heading();
		case Constants.SW:
			if (local_area[Constants.SW] == Constants.EMPTY) return Constants.SW;
			return random_heading();
		case Constants.SE:
			if (local_area[Constants.SE] == Constants.EMPTY) return Constants.SE;
			return random_heading();
		default:

			return random_heading();

		}
	}

	return ball_direction;

	
	}


private static int random_heading() {
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

private static boolean ball_infront(int[] local_area) {
	// TODO Auto-generated method stub
	if ((local_area[Constants.NW] == Constants.BALL)
			|| (local_area[Constants.W] == Constants.BALL)
			|| (local_area[Constants.SW] == Constants.BALL)) {
		return true;
	} else {
		return false;
	}
}


/*
public static int youStrategy(int[] local_area, int ball_direction, int x, int y){
	
}
*/

}
