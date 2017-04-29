package screens;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import metadata.Context;

public class Screen extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1545374598886584677L;

	public static final String LOGIN_FEATURE = "You must be logged to use this feature. Use our website to create an account." ;;

	protected static JFrame mainFrame = null;

	protected Context context = null;
	protected boolean screenHasFinished = false;
	protected ActionListener actionListener = null;
	protected ArrayList<Component> widgetList = new ArrayList<Component>();
	protected int nextScreenID = ScreenGenerator.MAIN_MENU_SCREEN;

	public Screen() {
		context = Context.singleton ;
		if (Screen.mainFrame == null)
			Screen.mainFrame = new JFrame();
		this.initScreen();
	}

	/**
	 * Initialize the screen with a new frame, a layout and trigger the first
	 * display.
	 */
	protected void initScreen() {
		setLayout(new BorderLayout ());
		Screen.mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Screen.mainFrame.getContentPane().add(this);
		Screen.mainFrame.setBounds(0, 0, context.getDimension().width,
				context.getDimension().height);
		this.screenHasFinished = false;
		Screen.mainFrame.setVisible(true);
	}

	/**
	 * Must be overwritten.
	 */
	public void prepare() throws IllegalStateException {
		throw new IllegalStateException("The screen did not overwite the method prepare");
	}

	/**
	 * Make the screen to run.
	 * 
	 * @return The id of the next game screen.
	 */
	public int run() throws IllegalStateException {
		if (actionListener == null)
			throw new IllegalStateException();
		display () ;
		while (!this.screenHasFinished) {
//			display();
			try {
				Thread.sleep(15);
			} catch (InterruptedException e) {
				this.applicationTermination();
			}
		}
		screenTermination () ;
		return this.nextScreenID;
	}

	public void screenTermination() {
		for (Component widget : widgetList)
			this.remove(widget);
		widgetList = new ArrayList<>() ;
		this.removeAll();
		screenHasFinished = true;
	}

	/**
	 * Trigger the game termination by finishing the display loop and saying
	 * that the new screen is the exit screen.
	 */
	private void applicationTermination() {
		this.nextScreenID = ScreenGenerator.QUIT_SCREEN;
		this.screenHasFinished = true;
	}

	/**
	 * Remove all widgets, add again all the widgets to their position.
	 */
	protected void display() {
		this.repaint();
	}

}