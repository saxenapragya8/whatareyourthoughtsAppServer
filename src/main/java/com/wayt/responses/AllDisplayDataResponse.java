package com.wayt.responses;

import java.util.List;
import java.util.Map;

public class AllDisplayDataResponse {

	List<ParticipationResponse> participations;
	List<ConversationResponse> conversations;
	List<CommentResponse> comments;
	Map<Integer, List<String>> convIdUserNamesMap;
	
	public AllDisplayDataResponse(){}
	public AllDisplayDataResponse(Map<Integer, List<String>> convIdUserNamesMap, List<ParticipationResponse> participations, List<ConversationResponse> conversations, List<CommentResponse> comments){
		this.participations = participations;
		this.conversations = conversations;
		this.comments = comments;
		this.convIdUserNamesMap = convIdUserNamesMap;
	}

	public List<ParticipationResponse> getParticipations() {
		return participations;
	}

	public List<ConversationResponse> getConversations() {
		return conversations;
	}

	public List<CommentResponse> getComments() {
		return comments;
	}
	
	public Map<Integer, List<String>> getConvIdUserNamesMap() {
		return convIdUserNamesMap;
	}
	@Override
	public String toString() {
		return "AllDisplayDataResponse [participations=" + participations + ", conversations=" + conversations
				+ ", comments=" + comments + ", convIdUserNamesMap=" + convIdUserNamesMap + "]";
	}
	
	
}
