package com.nikhil.helper;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class PegiableResponse<T> {
	
	private List<T> content;
	
	private long totalElements;
	
	private int totalPages;
	
	private int pageNumber;
	
	private int pageSize;
	
	private Boolean isFirst;
	
	private Boolean isLast;

}
