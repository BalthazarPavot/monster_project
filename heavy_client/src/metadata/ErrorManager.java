package metadata;

/**
 * The error manager is here to centralize the handling of errors.
 * @author Balthazar Pavot
 *
 */
public class ErrorManager {

	static public ErrorManager singleton = new ErrorManager();

	private Exception currentError = null;

	private ErrorManager() {

	}

    /**
     * Set the curent and inform it has been silenced.
     * @param error
     */
	public void setSilentError(Exception error) {
		setError(error);
		silenceError();
	}

    /**
     * Set the curent error
     * @param error
     */
	public void setError(Exception error) {
		currentError = error;
	}

	/**
	 * Throws the curent error
	 * @throws Exception
	 */
	public void throwError() throws Exception {
		throw currentError;
	}

	/**
	 * Inform an error has been silenced on stderr.
	 */
	public void silenceError() {
		System.err.printf("[Silenced Error] %s\n", currentError);
	}

	/**
	 * Give informationd on stderr
	 * 
	 * @param info
	 * @param strings
	 */
	public void info(String info, Object... strings) {
		System.err.format(String.format("[info] %s\n", info), strings);
	}

}
