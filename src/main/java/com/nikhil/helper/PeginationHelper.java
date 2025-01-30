package com.nikhil.helper;

import java.util.List;

import org.springframework.data.domain.Page;

public class PeginationHelper {
	
	public static <U> PegiableResponse<U> getPeginationResponse(Page<U> page){
		
		List<U> entity = page.getContent();
		
		PegiableResponse<U> pegiableResponse = new PegiableResponse<>();
		
		pegiableResponse.setContent(entity);
		pegiableResponse.setTotalElements(page.getTotalElements());
		pegiableResponse.setTotalPages(page.getTotalPages());
		pegiableResponse.setPageNumber(page.getNumber());
		pegiableResponse.setPageSize(page.getSize());
		pegiableResponse.setIsFirst(page.isFirst());
		pegiableResponse.setIsLast(page.isLast());
		
		return pegiableResponse;
		
		
	}

}
