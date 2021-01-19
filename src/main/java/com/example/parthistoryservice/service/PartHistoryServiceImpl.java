package com.example.parthistoryservice.service;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.parthistoryservice.models.Part;
import com.example.parthistoryservice.models.PartHistory;
import com.example.parthistoryservice.repositories.PartHistoryRepository;
import com.example.parthistoryservice.repositories.PartRepository;
import com.google.common.collect.Lists;

@Service
public class PartHistoryServiceImpl implements PartHistoryService {
	@Autowired
	private PartRepository partRepository;
	@Autowired
	private PartHistoryRepository partHistoryRepository;

	public List<Part> findAllParts() {
		return this.partRepository.findAllByOrderByCategoryAscNameAsc();
	}

	public List<PartHistory> getPartHistoryByPart(final Long id) {
		final Optional<Part> part = this.partRepository.findById(id);
		return part.get().getHistories();
	}

	public List<PartHistory> getPartHistoryByPartAndFromToDates(final Long id, final Date fromDate, final Date toDate) {
		final Part part = this.partHistoryRepository.findByPartAndDates(id, fromDate, toDate);
		if (Objects.isNull(part)) {
			return Lists.newArrayList();
		}
		return this.partHistoryRepository.findByPartAndDates(id, fromDate, toDate).getHistories();
	}

	public List<Part> findByCriteria(final String base, final String prefix, final String suffix, final Long usageId) {
		return this.partRepository.findAllByCriteria(base, prefix, suffix, usageId);
	}
}
