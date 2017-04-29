package screens;

import metadata.Context;

public class ScreenGenerator {

	private Screen screen = null;

	public ScreenGenerator(Context context) {
	}

	public static final int MAIN_MENU_SCREEN = 1;
	public static final int QUIT_SCREEN = 2;

	public void prepareScreen(int screenID) {
		switch (screenID) {
		case MAIN_MENU_SCREEN:
			prepareMainscreen();
			break;
		case QUIT_SCREEN:
		default:
			break;
		}
	}

	private void prepareMainscreen() {
		screen = new MainScreen();
		screen.prepare();
	}

	public Screen getScreen() {
		return screen;
	}

}
