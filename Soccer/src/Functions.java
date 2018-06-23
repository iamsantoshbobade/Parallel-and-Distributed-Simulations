import soccer.constants.Constants;

public class Functions {

	//private static final int SE = 0;
	
	/******************************************************************

		swap_heading() 

		Turn heading 180 degrees.

	******************************************************************/

	
	public  int swap_heading(int heading)
	{
	switch(heading)
		{
		case Constants.NW: return(Constants.SE); 
		case Constants.N: return(Constants.S); 
		case Constants.NE: return(Constants.SW);
		case Constants.E: return(Constants.W); 
		case Constants.SE: return(Constants.NW);
		case Constants.S: return(Constants.N); 
		case Constants.SW: return(Constants.NE);
		case Constants.W: return(Constants.E); 
		case Constants.KICK: return(Constants.KICK);
		case Constants.DO_NOTHING: return(Constants.DO_NOTHING);
		default: return(Constants.DO_NOTHING); 
		}
	}

	
}
