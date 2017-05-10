package screens;

import metadata.Context;
import network.HTTPResponse;

public class DocumentManager implements Runnable {

	private boolean running = true;
	static private Integer HEART_BEAT_RATE = 500;
	private Context context = Context.singleton;
	private int rate = HEART_BEAT_RATE;
	public MainScreen screen = null;

	public DocumentManager(MainScreen mainScreen) {
		screen = mainScreen;
	}

	@Override
	public void run() {
		context.errorManager.info("The chat manager has started.");
		running = true;
		while (running) {
			try {
				Thread.sleep(rate);
				updateContent();
			} catch (InterruptedException e) {
				context.setSilencedError(e);
				context.errorManager.info("The chat has been interrupted.");
				return;
			}

		}
	}

	private void updateContent() {
		int cursor = screen.documentTextArea.getCaretPosition();
		int selectStart = screen.documentTextArea.getSelectionStart();
		int selectEnd = screen.documentTextArea.getSelectionEnd();

		context.document.setContent(screen.getDocumentContent());
		HTTPResponse response = context.client.getDocumentContent(context.document.getId());
		if (response.getErrorCode() == 200) {
			context.document.setContent(response.getContent());
			screen.setDocumentContent(context.document.getContent());
		}
		int length = context.document.getContent().length() ;
		screen.documentTextArea.setCaretPosition(Math.min(cursor, length));
		screen.documentTextArea.setSelectionStart(Math.min(cursor, selectStart));
		screen.documentTextArea.setSelectionEnd(Math.min(cursor, selectEnd));
	}

	public void sendInsert(int offset, int length, String content) {
		System.err.printf("insert %s at %d (%d)\n", content, offset, length);
		HTTPResponse response = context.client.sendInsertDocumentContent(context.document, offset, length, content);
		if (response.getErrorCode() == 200) {
			context.document.setContent(screen.getDocumentContent());
		}
	}

	public void sendRemove(int offset, int length) {
		System.err.printf("remove from %s (%d)\n", offset, length);
		HTTPResponse response = context.client.sendRemoveDocumentContent(context.document, offset, length);
		if (response.getErrorCode() == 200) {
			context.document.setContent(screen.getDocumentContent());
		}

	}
}
