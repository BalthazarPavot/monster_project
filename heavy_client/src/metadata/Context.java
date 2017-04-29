package metadata;

import java.awt.Dimension;

import user.Project;
import user.User;

public class Context {

	static public Context singleton = new Context();

	private ErrorManager errorManager = null;
	private boolean running = true;
	private Dimension screenDimensions = new Dimension (640, 480) ;

	public User user = new User ();
	public Project project = new Project () ;

	private Context() {
		this(null);
	}

	private Context(ErrorManager errorManager) {
		if (errorManager == null)
			this.errorManager = ErrorManager.singleton;
		else
			this.errorManager = errorManager;
	}

	public void setError(Exception error) {
		try {
			setError(error, false);
		} catch (Exception e) {
			// should never ocure
			e.printStackTrace();
			System.out.println("An error occurend in the error handler!");
		}
	}

	public void setError(Exception error, Boolean raise) throws Exception {
		this.errorManager.setError(error);
		if (raise) {
			setRunning(false);
			errorManager.throwError();
		} else
			errorManager.silenceError();
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

	public boolean isRunning() {
		return running;
	}

	public void loadDefaultConfiguration() {
	}

	public void loadConfiguration(String[] args) {

	}

	public Dimension getDimension() {
		return screenDimensions ;
	}

}
