package network;

import org.apache.http.HttpResponse;

/**
 * Just a class that represents an http response with its code and content.
 * @author Balthazar Pavot
 *
 */
public class HTTPResponse {

	static String DEFAULT_CONTEXT = "";
	static Integer DEFAULT_ERROR_CODE = 200;

	private HttpResponse originalResponse ;
	private String content = DEFAULT_CONTEXT;
	private Integer errorCode = DEFAULT_ERROR_CODE;

    /**
     * 
     */
	public HTTPResponse() {
		this(DEFAULT_ERROR_CODE, DEFAULT_CONTEXT);
	}

    /**
     * 
     * @param errorCode
     */
	public HTTPResponse(Integer errorCode) {
		this(errorCode, DEFAULT_CONTEXT);
	}

    /**
     * 
     * @param content
     */
	public HTTPResponse(String content) {
		this(DEFAULT_ERROR_CODE, content);
	}

    /**
     * 
     * @param errorCode
     * @param content
     */
	public HTTPResponse(Integer errorCode, String content) {
		this.content = content;
		this.errorCode = errorCode;
		originalResponse = null ;
	}

    /**
     * 
     * @return
     */
	public String getContent() {
		return content;
	}

    /**
     * 
     * @param content
     */
	public void setContent(String content) {
		this.content = content;
	}

    /**
     * 
     * @return
     */
	public Integer getErrorCode() {
		return errorCode;
	}

    /**
     * 
     * @param errorCode
     * @return
     */
	public boolean hasErrorCode(int errorCode) {
		return this.errorCode == errorCode;
	}

    /**
     * 
     * @param errorCode
     */
	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}

    /**
     * 
     */
	@Override
	public String toString() {
		return "HTTPResponse [errorCode=" + errorCode + ", content=" + content + "]";
	}

    /**
     * 
     * @return
     */
	public HttpResponse getOriginalResponse() {
		return originalResponse;
	}

    /**
     * 
     * @param originalResponse
     */
	public void setOriginalResponse(org.apache.http.HttpResponse originalResponse) {
		this.originalResponse = originalResponse;
	}
}
