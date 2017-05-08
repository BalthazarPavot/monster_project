package network;

import org.apache.http.HttpResponse;

public class HTTPResponse {

	static String DEFAULT_CONTEXT = "";
	static Integer DEFAULT_ERROR_CODE = 200;

	private HttpResponse originalResponse ;
	private String content = DEFAULT_CONTEXT;
	private Integer errorCode = DEFAULT_ERROR_CODE;

	public HTTPResponse() {
		this(DEFAULT_ERROR_CODE, DEFAULT_CONTEXT);
	}

	public HTTPResponse(Integer errorCode) {
		this(errorCode, DEFAULT_CONTEXT);
	}

	public HTTPResponse(String content) {
		this(DEFAULT_ERROR_CODE, content);
	}

	public HTTPResponse(Integer errorCode, String content) {
		this.content = content;
		this.errorCode = errorCode;
		originalResponse = null ;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getErrorCode() {
		return errorCode;
	}

	public boolean hasErrorCode(int errorCode) {
		return this.errorCode == errorCode;
	}

	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}

	@Override
	public String toString() {
		return "HTTPResponse [errorCode=" + errorCode + ", content=" + content + "]";
	}

	public HttpResponse getOriginalResponse() {
		return originalResponse;
	}

	public void setOriginalResponse(org.apache.http.HttpResponse originalResponse) {
		this.originalResponse = originalResponse;
	}
}
