package com.ada.library.api.exceptionhandler.enums;

import lombok.Getter;

@Getter
public enum ProblemType {

	INVALID_DATA("/invalid-data", "Invalid data"),
	SYSTEM_ERROR("/system-error", "System error"),
	INVALID_PARAMETER("/invalid-parameter", "Invalid parameter"),
	UNREADABLE_MESSAGE("/unreadable-message", "Unreadable message"),
	RESOURCE_NOT_FOUND("/resource-not-found", "Resource not found"),
	USED_ENTITY("/used-entity", "Used Entity"),
	BUSINESS_ERROR("/business-error", "Business rule violation");
	
	private String uri;
	private String title;
	
	ProblemType(String uri, String title) {
		this.uri = "https://ada.library.com" + uri;
		this.title = title;
	}
}
