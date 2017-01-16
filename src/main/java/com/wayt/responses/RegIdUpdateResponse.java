package com.wayt.responses;

public class RegIdUpdateResponse {

	private boolean updateStatus = false;

	public RegIdUpdateResponse(boolean status){
		this.updateStatus = status;
	}
	public RegIdUpdateResponse(){}
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
