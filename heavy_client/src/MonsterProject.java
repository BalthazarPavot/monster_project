
/**
 * 
 */

import metadata.Context;
import screens.MainScreen;
 
/**
 * @author lain
 *
 */
public class MonsterProject {

	/**
	 * Laod the config and launch the mainscreen.
	 *
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		MainScreen screen ;

		Context.singleton.loadConfiguration(args);
		screen = new MainScreen() ;
		screen.prepare () ;
		screen.run () ;
		Context.singleton.finish () ;
		Context.singleton.errorManager.info("Existed normally");
		System.exit(0);
	}
}
