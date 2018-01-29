/**
 * Copyright(c) Beijing Kungeek Science & Technology Ltd. 
 */
package com.iandtop.utils;

/**
 * 
 * <pre>
 * 服务层考虑抛出的异常类型，以触发事务的回滚操作。
 * </pre>
 * 
 *  @author Klin
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
public class IandtopException extends RuntimeException {

	private static final long serialVersionUID = 7092610902670422579L;

	protected String errorCode;

	protected String errorMsg;

	/**
	 * 默认的构造方法。
	 */
	public IandtopException() {
	}

	/**
	 * 自定义的构造方法1。
	 * 
	 * @param cause
	 *            Throwable
	 */
	public IandtopException(Throwable cause) {
		super(cause);
	}

	/**
	 * 自定义的构造方法2。
	 * 
	 * @param message
	 *            String
	 */
	public IandtopException(String message) {
		super(message);
	}

	/**
	 * 自定义的构造方法3。
	 * 
	 * @param message
	 *            String
	 * @param cause
	 *            Throwable
	 */
	public IandtopException(String message, Throwable cause) {
		super(message, cause);
	}

	public IandtopException(String message, String errorCode) {
		super(message);
		this.errorCode = errorCode;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

}