import com.googlecode.lanterna.gui.Action;
import com.googlecode.lanterna.gui.Window;
import com.googlecode.lanterna.gui.component.Button;
import com.googlecode.lanterna.gui.component.CheckBox;
import com.googlecode.lanterna.gui.component.Label;
import com.googlecode.lanterna.gui.component.Panel;
import com.googlecode.lanterna.gui.component.Panel.Orientation;
import com.googlecode.lanterna.gui.component.TextBox;
import com.googlecode.lanterna.terminal.Terminal.Color;


public class MyWindow extends Window {

	public MyWindow(String title) {
		super("ASCII SOCCER");
		// TODO Auto-generated constructor stub
		
		Panel panel=new Panel("Set up soccer teams", Orientation.VERTICAL);
		
		panel.addComponent(new Label("WEST TEAM NAME: "));
		final TextBox west=new TextBox(null, 20);
		panel.addComponent(west);
		
		panel.addComponent(new Label("EAST TEAM NAME: "));
		final TextBox east=new TextBox(null, 20);
		panel.addComponent(east);
		
		panel.addComponent(new Label("WEST TEAM STRATEGY"));
		final CheckBox westStr1=new CheckBox("BruteForce", true);
		final CheckBox westStr2=new CheckBox("Luis Strategy", false);
		panel.addComponent(westStr1);
		panel.addComponent(westStr2);
		
		
		panel.addComponent(new Label("EAST TEAM STRATEGY"));
		final CheckBox eastStr1=new CheckBox("BruteForce", false);
		final CheckBox eastStr2=new CheckBox("Luis Strategy", true);
		panel.addComponent(eastStr1);
		panel.addComponent(eastStr2);
		
		panel.addComponent(new Label("POINTS"));
		final TextBox points=new TextBox(null, 10);
		panel.addComponent(points);
		
		
		
		panel.addComponent(new Button("Play", new Action() {
			
			@Override
			public void doAction() {
				// TODO Auto-generated method stub
			try {
				String westName=west.getText();
				System.out.println(westName);
				String eastName=east.getText();
				
				int weststr=0;
				int eaststr=0;
				
				if (westStr1.isSelected()){
					weststr=0;
				}
				if (westStr2.isSelected()){
					weststr=1;
				}
				
				if (eastStr1.isSelected()){
					eaststr=0;
				}
				if (eastStr2.isSelected()){
					eaststr=1;
				}
				
				System.out.println("westStra "+weststr);
				System.out.println("eastStra "+eaststr);
				System.out.println("points "+points.getText());
				
				int pts=Integer.parseInt(points.getText());
				
				
				Soccer.playSoccer(westName, eastName, weststr, eaststr, pts);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				
			}
		}));
		
		panel.addComponent(new Button("Close ", new Action() {
			
			@Override
			public void doAction() {
				// TODO Auto-generated method stub
			System.exit(0);	
			}
		}));
		
		
		this.addComponent(panel);
	}

}
