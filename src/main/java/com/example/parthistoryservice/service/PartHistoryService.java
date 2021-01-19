package com.example.parthistoryservice.service;

import java.util.Date;
import java.util.List;

import com.example.parthistoryservice.models.Part;
import com.example.parthistoryservice.models.PartHistory;

public interface PartHistoryService {
	List<Part> findAllParts();

	List<Part> findByCriteria(final String base, final String prefix, final String suffix, final Long usageId);

	List<PartHistory> getPartHistoryByPart(final Long id);

	List<PartHistory> getPartHistoryByPartAndFromToDates(final Long id, final Date fromDate, final Date toDate);
}