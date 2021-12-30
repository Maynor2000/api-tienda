package com.ifee09.com.tienda.models;

import java.time.LocalDateTime;

public class ResponseHttp {
	private String status;
	private int codeState;
	private String message;
	private LocalDateTime time;
	/**
	 * @param status
	 * @param codeState
	 * @param message
	 * @param time
	 */
	public ResponseHttp(String status, int codeState, String message, LocalDateTime time) {
		super();
		this.status = status;
		this.codeState = codeState;
		this.message = message;
		this.time = time;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return the codeState
	 */
	public int getCodeState() {
		return codeState;
	}
	/**
	 * @param codeState the codeState to set
	 */
	public void setCodeState(int codeState) {
		this.codeState = codeState;
	}
	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}
	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	/**
	 * @return the time
	 */
	public LocalDateTime getTime() {
		return time;
	}
	/**
	 * @param time the time to set
	 */
	public void setTime(LocalDateTime time) {
		this.time = time;
	}
	@Override
	public String toString() {
		return "ResponseHttp [status=" + status + ", codeState=" + codeState + ", message=" + message + ", time=" + time
				+ "]";
	}
	
	
}
