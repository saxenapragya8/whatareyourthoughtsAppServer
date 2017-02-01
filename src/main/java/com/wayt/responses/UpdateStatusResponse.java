package com.wayt.responses;

public class UpdateStatusResponse {

	private boolean updateStatus = false;

	public UpdateStatusResponse(boolean status){
		this.updateStatus = status;
	}
	public UpdateStatusResponse(){}
	public boolean isUpdateStatus() {
		return updateStatus;
	}

	public void setUpdateStatus(boolean updateStatus) {
		this.updateStatus = updateStatus;
	}
	@Override
	public String toString() {
		return "RegIdUpdateResponse [updateStatus=" + updateStatus + "]";
	}
	
	
}
