import gnu.getopt.Getopt;

import java.io.ObjectInputStream.GetField;
import java.util.Random;

import com.googlecode.lanterna.TerminalFacade;
import com.googlecode.lanterna.gui.GUIScreen;
import com.googlecode.lanterna.gui.GUIScreen.Position;
import com.googlecode.lanterna.input.Key;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.ScreenCharacterStyle;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.Terminal.Color;
import com.googlecode.lanterna.terminal.swing.TerminalAppearance;

import soccer.teamOne.Team;
import soccer.teamTwo.Team2;
import soccer.utils.Sscanf;

public class Soccer {

	// * Do not attempt to access these varibles from your
	// * team function. That is a violation of the rules!

	static int ball_x, ball_y;
	static int[] player_x, player_y;// 8
	static int[][] field;// max x,y
	static int display = 1; // indicates whether or not to display stuff
	static int points = 7; // How many points til a win ?

	static int opterr = 0;

	// char * optarg;

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub

		
		GUIScreen guiscreen= TerminalFacade.createGUIScreen();
		guiscreen.getScreen().startScreen();
		MyWindow myw=new MyWindow("ASCII SOCCER");
		guiscreen.showWindow(myw, Position.NEW_CORNER_WINDOW);
		guiscreen.getScreen().stopScreen();
		
			}
	
	
	public static void playSoccer(String westTeam,String eastTeam, int westStra,int eastStra, int puntos) throws InterruptedException{
		
		
		char ch, c;

		int i, j, k, cur_x, cur_y, backwards_cur_x, backwards_cur_y;
		int point_over = 0;
		int game_over = 0;
		int count = 0, player_nearby = 0;
		int overall_count = 0;
		int[] local_field = new int[9];// 9
		int[] local_backwards_field = new int[9];// 9
		int[] local_ball_field = new int[9];// 9
		int ball_direction = Constants.N;
		int backwards_ball_direction = Constants.N;
		int player_move = Constants.N;
		int kick_direction = -1;
		int kick_steps = 0;
		double temp_angle = 0;
		double path = 0;
		int cur = 0;
		int east_score = 0, west_score = 0;
		int slow = 500;
		int last_ball_x = 0, last_ball_y = 0;

		player_x = new int[8];
		player_y = new int[8];

		field = new int[Constants.MAX_X][Constants.MAX_Y];

		Random random = new Random();

		cur = 0;
		int carac = 0;

//		Getopt g = new Getopt("soccer", args, "dg:s:p:");
		
		points=puntos;


		/*
		 * Init screen
		 */
		Screen sc;
		sc = TerminalFacade.createScreen();


		Thread.sleep(2000);

		/*
		 * Call user initialization routines.
		 */
		// EASTinitialize_game();
		// WESTinitialize_game();

		// sc.stopScreen();

		while (game_over != 1) {

			// * Set up for the point.

			initialize(sc);
			if (display == 1) {
				report_score(westTeam,eastTeam,west_score, east_score, sc);
			}
			point_over = 0;
			count = 0;
			overall_count = 0;
			kick_direction = -1;
			kick_steps = 0;
			Thread.sleep(1000);

			// * Call user initialization functions.

			// EASTinitialize_point();
			// WESTinitialize_point();

			// * Play the point til one team wins.

			while (point_over != 1) {

				// * Player's posit

				cur_x = player_x[cur];
				cur_y = player_y[cur];

				// * Note the local field around the player

				k = 0;
				for (j = 0; j < 3; j++)
					for (i = 0; i < 3; i++) {
						local_field[k++] = field[cur_x + i - 1][cur_y + j - 1];
					}

				/*
				 * Note the local field around the ball
				 */
				k = 0;
				for (j = 0; j < 3; j++)
					for (i = 0; i < 3; i++) {
						local_ball_field[k++] = field[ball_x + i - 1][ball_y
								+ j - 1];
					}

				/*
				 * Figure out heading to ball
				 */
				if ((ball_x == cur_x) && (ball_y == cur_y))
					temp_angle = 0;
				else
					temp_angle = Math.atan2((ball_x - cur_x), (ball_y - cur_y));
				temp_angle += Constants.PI;
				temp_angle = 360.0 * temp_angle / (2.0 * Constants.PI);
				ball_direction = Constants.N;
				if (temp_angle > 22.5 + 0 * 45)
					ball_direction = Constants.NW;
				if (temp_angle > 22.5 + 1 * 45)
					ball_direction = Constants.W;
				if (temp_angle > 22.5 + 2 * 45)
					ball_direction = Constants.SW;
				if (temp_angle > 22.5 + 3 * 45)
					ball_direction = Constants.S;
				if (temp_angle > 22.5 + 4 * 45)
					ball_direction = Constants.SE;
				if (temp_angle > 22.5 + 5 * 45)
					ball_direction = Constants.E;
				if (temp_angle > 22.5 + 6 * 45)
					ball_direction = Constants.NE;
				if (temp_angle > 22.5 + 7 * 45)
					ball_direction = Constants.N;

				/*
				 * Construct backwards sensing for western players.
				 */
				for (i = 0; i <= 8; i++)
					local_backwards_field[i] = local_field[8 - i];
				backwards_ball_direction = swap_heading(ball_direction);
				backwards_cur_x = (Constants.MAX_X - 1) - cur_x;
				backwards_cur_y = (Constants.MAX_Y - 1) - cur_y;

				/*
				 * Find out which way to go. The variable cur indicates which
				 * player we are moving for now. East players are 0 2 4 6. The
				 * local_field and ball_direction are reversed for west players
				 * so they can think they are going east! Then their output is
				 * reversed again.
				 */
				Team team = new Team(westStra);
				Team2 team2 = new Team2(eastStra);
				switch (cur) {
				case 0:
					player_move = team2.teamPlayer(local_field, ball_direction,
							cur_x, cur_y);
					break;
				case 1:
					player_move = swap_heading(team.teamPlayer(
							local_backwards_field, backwards_ball_direction,
							backwards_cur_x, backwards_cur_y));
					break;
				case 2:
					player_move = team2.teamPlayer(local_field, ball_direction,
							cur_x, cur_y);
					break;
				case 3:
					player_move = swap_heading(team.teamPlayer(
							local_backwards_field, backwards_ball_direction,
							backwards_cur_x, backwards_cur_y));
					break;
				case 4:
					player_move = team2.teamPlayer(local_field, ball_direction,
							cur_x, cur_y);
					break;
				case 5:
					player_move = swap_heading(team.teamPlayer(
							local_backwards_field, backwards_ball_direction,
							backwards_cur_x, backwards_cur_y));
					break;
				case 6:
					player_move = team2.teamPlayer(local_field, ball_direction,
							cur_x, cur_y);
					break;
				case 7:
					player_move = swap_heading(team.teamPlayer(
							local_backwards_field, backwards_ball_direction,
							backwards_cur_x, backwards_cur_y));
					break;
				}

				/*
				 * Check for move = PLAYER
				 */
				if (player_move == Constants.PLAYER) {
					player_move = Constants.DO_NOTHING;
				}

				/*
				 * Check for KICK. If the player wants to kick set up the kick,
				 * then set player_move to be heading in the direction of the
				 * ball.
				 */
				if (player_move == Constants.KICK) {
					for (i = 0; i <= 8; i++) {
						/*--- Where is the ball ?---*/
						if (local_field[i] == Constants.BALL) {
							kick_direction = i;
							kick_steps = Constants.KICK_DIST;
							player_move = i;
							break;
						}
					}
					if (player_move == Constants.KICK) {
						/*--- Ball was NOT nearby ---*/
						player_move = Constants.N;
					}
				}

				/*
				 * Make sure heading is legal.
				 */
				if ((player_move < 0) || (player_move > 8)) {
					player_move = Constants.DO_NOTHING;
				}

				/*
				 * Move the player in the world
				 */
				if (display == 1) {
					// mvaddch(cur_y, cur_x, ' '); /* erase old spot */
					sc.putString(cur_x, cur_y, " ", Color.WHITE, Color.BLACK,
							ScreenCharacterStyle.Bold);
					sc.refresh();
				}
				field[cur_x][cur_y] = Constants.EMPTY;

				/*
				 * If the cell we are going to is empty, or the ball is there
				 * and the next cell after that is empty, then . . .
				 */
				if ((local_field[player_move] == Constants.EMPTY)
						|| ((local_field[player_move] == Constants.BALL) && ((local_ball_field[player_move] == Constants.EMPTY) || (local_ball_field[player_move] == Constants.GOAL)))) {
					switch (player_move) {
					case Constants.N:
						player_y[cur]--;
						path++;
						break;
					case Constants.S:
						player_y[cur]++;
						path++;
						break;
					case Constants.E:
						player_x[cur]++;
						path++;
						break;
					case Constants.W:
						player_x[cur]--;
						path++;
						break;
					case Constants.NE:
						player_y[cur]--;
						player_x[cur]++;
						path += Constants.SQR2;
						break;
					case Constants.NW:
						player_y[cur]--;
						player_x[cur]--;
						path += Constants.SQR2;
						break;
					case Constants.SE:
						player_y[cur]++;
						player_x[cur]++;
						path += Constants.SQR2;
						break;
					case Constants.SW:
						player_y[cur]++;
						player_x[cur]--;
						path += Constants.SQR2;
						break;
					}
				}

				/*
				 * Mark the field with the player's new position.
				 */
				if ((cur % 2) == 0)
					field[player_x[cur]][player_y[cur]] = Constants.EAST_PLAYER;
				else
					field[player_x[cur]][player_y[cur]] = Constants.WEST_PLAYER;

				/*
				 * Now move the ball.
				 */
				if ((local_field[player_move] == Constants.BALL)
						&& ((local_ball_field[player_move] == Constants.EMPTY) || (local_ball_field[player_move] == Constants.GOAL))) {
					field[ball_x][ball_y] = Constants.EMPTY;
					if (display == 1) {
						// mvaddch(ball_y, ball_x, ' '); /* new position */
						sc.putString(ball_x, ball_y, " ", Color.WHITE,
								Color.BLACK, ScreenCharacterStyle.Bold);
						sc.refresh();
					}
					switch (player_move) {
					case Constants.N:
						ball_y--;
						break;
					case Constants.S:
						ball_y++;
						break;
					case Constants.E:
						ball_x++;
						break;
					case Constants.W:
						ball_x--;
						break;
					case Constants.NE:
						ball_y--;
						ball_x++;
						break;
					case Constants.NW:
						ball_y--;
						ball_x--;
						break;
					case Constants.SE:
						ball_y++;
						ball_x++;
						break;
					case Constants.SW:
						ball_y++;
						ball_x--;
						break;
					}
				}

				/*
				 * Place the ball on the field.
				 */
				field[ball_x][ball_y] = Constants.BALL;

				/*
				 * Now handle the case of a KICK
				 */
				if (kick_steps > 0) {
					/*--- Revise the local ball field ---*/
					k = 0;
					for (j = 0; j < 3; j++)
						for (i = 0; i < 3; i++) {
							//System.out.println("ball x: "+ ball_x+ "ball y: "+ ball_y);
							int xaxis=ball_x+i-1;
							if ((xaxis>0) && (xaxis<80)){
							local_ball_field[k++] = field[ball_x + i - 1][ball_y+ j - 1];
							}
						}

					/*--- Erase old position ---*/
					field[ball_x][ball_y] = Constants.EMPTY;
					if (display == 1) {
						// mvaddch(ball_y, ball_x, ' ');
						sc.putString(ball_x, ball_y, " ", Color.WHITE,
								Color.BLACK, ScreenCharacterStyle.Bold);
						sc.refresh();
					}

					/*--- Propel the ball if the space is empty ---*/
					if ((local_ball_field[kick_direction] == Constants.EMPTY)
							|| (local_ball_field[kick_direction] == Constants.GOAL))
						switch (kick_direction) {
						case Constants.N:
							ball_y--;
							break;
						case Constants.S:
							ball_y++;
							break;
						case Constants.E:
							ball_x++;
							break;
						case Constants.W:
							ball_x--;
							break;
						case Constants.NE:
							ball_y--;
							ball_x++;
							break;
						case Constants.NW:
							ball_y--;
							ball_x--;
							break;
						case Constants.SE:
							ball_y++;
							ball_x++;
							break;
						case Constants.SW:
							ball_y++;
							ball_x--;
							break;
						default:
							break;
						}
					else {
						kick_direction = -1;
						kick_steps = 0;
					}
					kick_steps--;
					field[ball_x][ball_y] = Constants.BALL;
				}

				/*
				 * Mark the new locations of the ball and the player on the
				 * field.
				 */
				if (display == 1) {
					// mvaddch(ball_y, ball_x, 'O');
					sc.putString(ball_x, ball_y, "O", Color.WHITE, Color.BLACK,
							ScreenCharacterStyle.Bold);
					sc.refresh();
				}
				if ((cur % 2) == 0) {
					if (display == 1) {
						// mvaddch(player_y[cur], player_x[cur], '<');
						sc.putString(player_x[cur], player_y[cur], "<",
								Color.WHITE, Color.BLACK,
								ScreenCharacterStyle.Bold);
						// sc.refresh();
					}
				} else {
					if (display == 1) {
						// mvaddch(player_y[cur], player_x[cur], '>');
						sc.putString(player_x[cur], player_y[cur], ">",
								Color.WHITE, Color.BLACK,
								ScreenCharacterStyle.Bold);
						// sc.refresh();
					}
				}
				if (display == 1) {
					// wrefresh(game_win);
					Key key = sc.readInput();

					if (key != null) {
						char keyPressed = key.getCharacter();
						//System.out.println("key " + keyPressed);
						switch (keyPressed) {
						case 's': {

							slow = slow * 4;
							break;
						}
						case 'f': {

							slow = slow / 2;
							break;
						}
						case 'q':{
							game_over=1;
							point_over=1;
							break;
						}

						}

					}
					sc.refresh();
					Thread.sleep(slow);
				}

				/*
				 * Check for a score.
				 */
				if (ball_x <= 0) {
					east_score++;
					point_over = 1;
					if (display == 0) {
						// printf("%s vs %s: %d to %d \n",WESTteam_name(),EASTteam_name(),
						// west_score,east_score);
						// fflush(stdout);
					}
					// EASTwon_point(); /* Advise the teams of the point */
					// WESTlost_point();
				}
				if (ball_x >= (Constants.MAX_X - 1)) {
					west_score++;
					point_over = 1;
					if (display == 0) {
						// printf("%s vs %s: %d to %d \n",WESTteam_name(),EASTteam_name(),
						// west_score,east_score);
						// fflush(stdout);
						//System.out.println("team 1 " + west_score + "team 2"
							//	+ east_score);
					}
					// WESTwon_point(); /* Advise the teams of the point */
					// EASTlost_point();
				}

				/*
				 * Check for a stalemate.
				 */
				player_nearby = 0;
				for (i = 0; i <= 8; i++) {
					if ((local_ball_field[i] == Constants.WEST_PLAYER)
							|| (local_ball_field[i] == Constants.EAST_PLAYER))
						player_nearby = 1;
				}
				if (player_nearby != 0
						&& ((last_ball_x == ball_x) && (last_ball_y == ball_y))) {
					count++;

				}

				if (count > Constants.TIME_LIMIT) {
					nudge_ball(sc);
					count = 0;
				}
				if ((last_ball_x != ball_x) || (last_ball_y != ball_y))
					count = 0;
				last_ball_x = ball_x;
				last_ball_y = ball_y;

				//System.out.println(count);
				/*
				 * Check for user input.
				 */
				/*
				 * if (display) { fd_set inset; struct timeval timeout;
				 * FD_ZERO(&inset); FD_SET(0, &inset); timeout.tv_sec =
				 * timeout.tv_usec = 0; if (select(FD_SETSIZE, &inset, NULL,
				 * NULL, &timeout) > 0) { int input = getch(); if (input == 'q')
				 * point_over = game_over = 1; if (input == 's') slow*=2; if
				 * (input == 'f') slow/=2; if (slow < 1) slow=1; } if (slow>1) {
				 * timeout.tv_usec = (slow-1)*20; select(32, NULL, NULL, NULL,
				 * &timeout); } }
				 */

				/*
				 * Go on to the next player.
				 */
				cur++;
				if (cur == 8)
					cur = 0;

				/*
				 * Check for big timeout.
				 */
				overall_count++;
				if (overall_count >= Constants.TIME_OUT) {
					// printf("TIME_OUT\n");
					System.out.println("TIME OUT");
					/* erase old spot */
					if (display == 1) {
						// mvaddch(ball_x, ball_y, ' ');
						sc.putString(ball_x, ball_y, " ", Color.WHITE,
								Color.BLACK, ScreenCharacterStyle.Bold);
					}
					field[ball_x][ball_y] = Constants.EMPTY;
					ball_x = 38;
					ball_y = 20;
					replace_ball(sc);
					overall_count = 0;
					// EASTlost_point(); /* punish teams */
					// WESTlost_point();
					if (display == 1) {
						// wrefresh(game_win);
						sc.refresh();
					}
				}

			}

			/*
			 * Check for first to 7 wins.
			 */
			if ((west_score == points) || (east_score == points))
				game_over = 1;

		}

		// EASTgame_over();
		// WESTgame_over();
		if (display == 1) {
			report_score(westTeam,eastTeam,west_score, east_score, sc);
		}
		if (display == 1) {
			// endwin();
			sc.stopScreen();
		}
		// printf("\n");
		System.out.println(westTeam+" " + west_score + eastTeam+" "
				+ east_score);
		// west_score,east_score);
		if (west_score < east_score)
			System.out.println(" won " + eastTeam);
		else
			System.out.println(" won " + westTeam);


		
		
	}

	private static void report_score(String westTeam, String eastTeam, int west_score, int east_score, Screen sc) {
		// TODO Auto-generated method stub

		String score_line = " ";
		/*
		 * sprintf(score_line, "%s %d                              %s %d",
		 * WESTteam_name(), west_score, EASTteam_name(), east_score);
		 */
		score_line = westTeam+": " + west_score + " "+eastTeam+": "
				+ east_score;

		if (display == 1) {
			// mvaddstr(Constants.MAX_Y, 0, score_line);
			sc.putString(0, Constants.MAX_Y, score_line, Color.WHITE,
					Color.BLACK, ScreenCharacterStyle.Bold);
		}
		score_line = "ASCII=Soccer=v2.0====(q)uit====(s)lower====(f)aster";
		if (display == 1) {
			// mvaddstr(0, 0, score_line);
			sc.putString(0, 0, score_line, Color.WHITE, Color.BLACK,
					ScreenCharacterStyle.Bold);
		}
		if (display == 1) {
			// wrefresh(game_win);
			sc.refresh();
		}

	}

	private static void nudge_ball(Screen sc) {
		// TODO Auto-generated method stub

		int i = 1;
		int temp;

		if (display == 1) {
			// mvaddch(ball_y, ball_x, ' ');
			sc.putString(ball_x, ball_y, " ", Color.WHITE, Color.BLACK,
					ScreenCharacterStyle.Bold);
		}
		Random rand = new Random();

		do {
			temp = ball_y + ((rand.nextInt(10000) / 256) % i) - (i / 2);
			//System.out.println("temp" + temp);
			if (temp < 1)
				temp = 1;
			if (temp > 22)
				temp = 22;
			i++;
			if (i > 40)
				i = 40;
		} while ((field[ball_x][temp] != Constants.EMPTY)
				&& (field[ball_x][temp] != Constants.GOAL));
		field[ball_x][ball_y] = Constants.EMPTY;
		ball_y = temp;
		field[ball_x][ball_y] = Constants.BALL;
		if (display == 1) {
			// mvaddch(ball_y, ball_x, 'O');
			sc.putString(ball_x, ball_y, "O", Color.WHITE, Color.BLACK,
					ScreenCharacterStyle.Bold);
			sc.refresh();
		}

	}

	private static int swap_heading(int heading) {
		// TODO Auto-generated method stub

		switch (heading) {
		case Constants.NW:
			return (Constants.SE); // break;
		case Constants.N:
			return (Constants.S); // break;
		case Constants.NE:
			return (Constants.SW); // break;
		case Constants.E:
			return (Constants.W); // break;
		case Constants.SE:
			return (Constants.NW); // break;
		case Constants.S:
			return (Constants.N); // break;
		case Constants.SW:
			return (Constants.NE); // break;
		case Constants.W:
			return (Constants.E); // break;
		case Constants.KICK:
			return (Constants.KICK);
		case Constants.DO_NOTHING:
			return (Constants.DO_NOTHING);
		default:
			return (Constants.DO_NOTHING); // break;
		}
	}

	private static void initialize(Screen sc) {
		// TODO Auto-generated method stub
		char c;
		int i, j, val, r;

		/*--- Clear the screen ---*/
		if (display == 1) {
			sc.clear();
			// wclear(game_win);
		}

		/*--- Clear out the field map. ---*/
		for (i = 0; i < Constants.MAX_X; i++) {
			for (j = 0; j < Constants.MAX_Y; j++) {
				field[i][j] = Constants.EMPTY;
			}
		}

		/*--- Location of ball ---*/
		ball_x = 38;
		ball_y = 20;
		replace_ball(sc);

		/*--- Locations of players ---*/
		for (i = 0; i <= 6; i += 2) {
			player_x[i] = 46;
			player_y[i] = (i / 2 + 1) * 6 - 4;
			field[player_x[i]][player_y[i]] = Constants.EAST_PLAYER;
			if (display == 1) {
				// mvaddch(player_y[i], player_x[i], '<');
				sc.putString(player_x[i], player_y[i], "<", Color.WHITE,
						Color.BLACK, ScreenCharacterStyle.Bold);
			}

			player_x[i + 1] = 30;
			player_y[i + 1] = (i / 2 + 1) * 6 - 4;
			field[player_x[i + 1]][player_y[i + 1]] = Constants.WEST_PLAYER;
			if (display == 1) {
				// mvaddch(player_y[i+1], player_x[i+1], '>');
				sc.putString(player_x[i + 1], player_y[i + 1], ">",
						Color.WHITE, Color.BLACK, ScreenCharacterStyle.Bold);
			}
		}

		/*
		 * Put boundary around the field. That will keep the players in bounds
		 * all the time.
		 */
		for (i = 0; i < Constants.MAX_X; i++) {
			for (j = 0; j < Constants.MAX_Y; j++) {
				if ((i == 0) || (i == (Constants.MAX_X - 1))) {
					field[i][j] = Constants.GOAL;
					if (display == 1) {
						// mvaddch(j, i, '|');
						sc.putString(i, j, "|", Color.WHITE, Color.BLACK,
								ScreenCharacterStyle.Bold);
					}
				}
				if ((j == 0) || (j == (Constants.MAX_Y - 1))) {
					field[i][j] = Constants.BOUNDARY;
					if (display == 1) {
						// mvaddch(j, i, '=');
						sc.putString(i, j, "=", Color.WHITE, Color.BLACK,
								ScreenCharacterStyle.Bold);

					}
				}
			}
		}

		/*--- Refresh the screen ---*/
		if (display == 1) {
			// wrefresh(game_win);
			sc.refresh();
		}

	}

	private static void replace_ball(Screen sc) {
		// TODO Auto-generated method stub

		Random rand = new Random();
		sc.startScreen();

		if (display == 1) {
			// mvaddch(ball_y, ball_x, ' ');
			// sc.putString(ball_x, ball_y, ' ', Terminal.Color.WHITE,
			// Terminal.Color.BLACK,);
			// sc.putString(ball_x, ball_y, null, , arg4, arg5);
			// ScreenCharacterStyle.
			sc.putString(ball_x, ball_y, " ", Terminal.Color.WHITE,
					Terminal.Color.BLACK, ScreenCharacterStyle.Bold);
			sc.refresh();
		}
		do {
			field[ball_x][ball_y] = Constants.EMPTY;
			ball_y = rand.nextInt(100) % 20 + 1;
		} while (field[ball_x][ball_y] != Constants.EMPTY);
		field[ball_x][ball_y] = Constants.BALL;
		if (display == 1) {
			// mvaddch(ball_y, ball_x, 'O');
			sc.putString(ball_x, ball_y, "O", Color.WHITE, Color.BLACK,
					ScreenCharacterStyle.Bold);
		}
		if (display == 1) {
			// wrefresh(game_win);
			sc.refresh();
		}
	}

}
