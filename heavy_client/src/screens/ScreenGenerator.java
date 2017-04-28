package screens;

import metadata.Context;

public class ScreenGenerator {

	private Screen screen = null ;

	public ScreenGenerator(Context context) {
		// TODO Auto-generated constructor stub
	}

	public static final int MAIN_MENU_SCREEN = 1;
	public static final int QUIT_SCREEN = 2;

	public void prepareScreen(int screenID) {
		switch (screenID) {
		case MAIN_MENU_SCREEN:
			prepareMainscreen () ;
		case QUIT_SCREEN:
		default:
			break ;
		}
	}

	private void prepareMainscreen() {
		// TODO Auto-generated method stub
		
	}

	public Screen getScreen() {
		return screen;
	}

}
