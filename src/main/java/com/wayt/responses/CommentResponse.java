package com.wayt.responses;

public class CommentResponse {

	private Integer id;
	private Integer participationId;
	private Integer convId;
	private String content;
	
	public CommentResponse(){}
	public CommentResponse(Integer id, Integer participationId, Integer convId, String content){
		this.id = id;
		this.participationId = participationId;
		this.convId = convId;
		this.content = content;
	}

	public Integer getId() {
		return id;
	}

	public Integer getParticipationId() {
		return participationId;
	}

	public Integer getConvId() {
		return convId;
	}

	public String getContent() {
		return content;
	}
	@Override
	public String toString() {
		return "CommentResponse [id=" + id + ", participationId=" + participationId + ", convId=" + convId
				+ ", content=" + content + "]";
	}
	
	
}
