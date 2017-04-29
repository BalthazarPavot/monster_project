
/**
 * 
 */

import metadata.Context;
import screens.Screen;
import screens.ScreenGenerator;

/**
 * @author lain
 *
 */
public class MonsterProject {

	/**
	 * Launch the mainloop giving the first screen id.
	 *
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		Context.singleton.loadConfiguration(args);
		mainloop(ScreenGenerator.MAIN_MENU_SCREEN);
	}

	/**
	 * Create the context and the error manager, load the config and create the
	 * screen generator giving the context. <br />
	 * Launch the mainloop that prepare the current screen, make it run, get the
	 * main screen id and loop again while the user does not decide to quit.
	 * 
	 * @param screenID
	 * @throws Exception
	 */
	public static void mainloop(int screenID) throws Exception {
		ScreenGenerator screenGenerator;
		Screen screen;
		Context context;

		context = Context.singleton;
		screenGenerator = new ScreenGenerator(context);
		do {
			screenGenerator.prepareScreen(screenID);
			screen = screenGenerator.getScreen();
			if (screen == null)
				context.setError(new Exception(String.format("Could not create asked screen with id %d.", screenID)),
						false);
			screenID = screen.run();
		} while (context.isRunning() && screenID != ScreenGenerator.QUIT_SCREEN);
		System.exit(0);
	}

}
