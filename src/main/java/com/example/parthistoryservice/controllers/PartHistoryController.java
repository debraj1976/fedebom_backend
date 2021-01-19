package com.example.parthistoryservice.controllers;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.parthistoryservice.models.Part;
import com.example.parthistoryservice.models.PartHistory;
import com.example.parthistoryservice.service.PartHistoryService;
import com.google.common.collect.Lists;

@RestController
@RequestMapping({ "/parthistoryservice" })
@CrossOrigin(origins = { "http://localhost:4200" })
public class PartHistoryController {

	private static DateTimeFormatter formatter;
	@Autowired
	private PartHistoryService partHistoryService;

	@GetMapping(produces = { "application/json" })
	public ResponseEntity<List<Part>> getAllParts() {
		final List<Part> parts = this.partHistoryService.findAllParts();
		return new ResponseEntity<List<Part>>(parts, HttpStatus.OK);
	}

	@GetMapping(path = { "/history/{id}" }, produces = { "application/json" })
	public ResponseEntity<List<PartHistory>> getPartHistoryByPart(@PathVariable("id") final Long partId) {
		final List<PartHistory> partHistories = (List<PartHistory>) this.partHistoryService
				.getPartHistoryByPart(partId);
		return new ResponseEntity<List<PartHistory>>(partHistories, HttpStatus.OK);
	}

	@GetMapping(path = { "/historywithdates/{id}" }, produces = { "application/json" })
	public ResponseEntity<List<PartHistory>> getPartHistoryByPartDates(@PathVariable("id") final Long partId,
			@RequestParam("fromDate") final String fromDate, @RequestParam("toDate") final String toDate) {
		final List<PartHistory> partHistories = this.partHistoryService.getPartHistoryByPartAndFromToDates(partId,
				Date.valueOf(LocalDate.parse(fromDate, PartHistoryController.formatter)),
				Date.valueOf(LocalDate.parse(toDate, PartHistoryController.formatter)));
		return new ResponseEntity<List<PartHistory>>(partHistories, HttpStatus.OK);
	}

	@GetMapping(path = { "/historywithcriteriasearch" }, produces = { "application/json" })
	public ResponseEntity<List<PartHistory>> getPartHistoryBySearch(@RequestParam("id") final Long partId,
			@RequestParam("fromDate") final String fromDate, @RequestParam("toDate") final String toDate,
			@RequestParam("base") final String base, @RequestParam("prefix") final String prefix,
			@RequestParam("suffix") final String suffix, @RequestParam("usageId") final Long usageId) {
		final List<Part> parts = (List<Part>) this.partHistoryService.findByCriteria(base, prefix, suffix, usageId);
		List<PartHistory> partHistories = new ArrayList<PartHistory>();
		if (StringUtils.isEmpty(fromDate) && StringUtils.isEmpty(toDate)) {
			partHistories = this.getPartHistories(parts);
		} else {
			partHistories = this.partHistoryService.getPartHistoryByPartAndFromToDates(partId,
					Date.valueOf(LocalDate.parse(fromDate, PartHistoryController.formatter)),
					Date.valueOf(LocalDate.parse(toDate, PartHistoryController.formatter)));
		}
		return new ResponseEntity<List<PartHistory>>(partHistories, HttpStatus.OK);
	}

	private List<PartHistory> getPartHistories(final List<Part> parts) {
		if (!CollectionUtils.isEmpty(parts)) {
			return parts.stream().map(p -> p.getHistories()).flatMap(Collection::stream).collect(Collectors.toList());
		}
		return Lists.newArrayList();
	}

	static {
		PartHistoryController.formatter = DateTimeFormatter.ofPattern("MM/d/yyyy");
	}
}
