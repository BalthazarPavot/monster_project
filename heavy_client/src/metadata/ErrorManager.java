package metadata;

import java.util.ArrayList;

public class ErrorManager {

	static public ErrorManager singleton = new ErrorManager();

	private Exception currentError = null;
	private ArrayList<Exception> errors = new ArrayList<Exception>();

	private ErrorManager() {

	}

	public void setError(Exception error) {
		if (currentError != null)
			errors.add(currentError);
		currentError = error;
	}

	public void throwError() throws Exception {
		throw currentError;
	}

	public void silenceError() {
		System.err.printf("[Silenced Error] %s\n", currentError);
	}

}
