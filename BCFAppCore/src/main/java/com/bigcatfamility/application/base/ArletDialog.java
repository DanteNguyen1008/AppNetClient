package com.bigcatfamility.application.base;

public class ArletDialog {
	private String sTitle, sMessage, sOK, sCancel;
	private INLDialogActionListener listener;

	public ArletDialog(String sTitle, String sMessage, String sOK, String sCancel, INLDialogActionListener listener) {
		this.sTitle = sTitle;
		this.sMessage = sMessage;
		this.sOK = sOK;
		this.sCancel = sCancel;
		this.listener = listener;
	}
	
	public void SetINLDialogActionListener(INLDialogActionListener listener) {
		this.listener = listener;
	}
	
	public INLDialogActionListener GetINLDialogActionListener() {
		return this.listener;
	}

	public String getOKString() {
		return sOK;
	}

	public void setOKString(String sOK) {
		this.sOK = sOK;
	}

	public String getCancelString() {
		return sCancel;
	}

	public void setCancelString(String sCancel) {
		this.sCancel = sCancel;
	}

	public String getTitle() {
		return sTitle;
	}

	public void setTitle(String sTitle) {
		this.sTitle = sTitle;
	}

	public String getMessage() {
		return sMessage;
	}

	public void setMessage(String sMessage) {
		this.sMessage = sMessage;
	}

	public interface INLDialogActionListener {
		public void OnOKButtonClick();
		public void OnCancelButtonClick();
	}
}
