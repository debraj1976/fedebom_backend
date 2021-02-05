package com.example.parthistoryservice.controllers;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.example.parthistoryservice.repositories.PartHistoryRepository;
import com.example.parthistoryservice.repositories.PartRepository;
import com.example.parthistoryservice.repositories.PartRepository2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

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

	@Autowired
	private PartRepository partRepository;

	@Autowired
	private PartRepository2 partRepository2;

	@Autowired
	private PartHistoryRepository partHistoryRepository;

	@GetMapping(produces = { "application/json" })
	public ResponseEntity<List<Part>> getAllParts() {
		final List<Part> parts = this.partHistoryService.findAllParts();
		return new ResponseEntity<List<Part>>(parts, HttpStatus.OK);
	}

	@PostMapping(path = {"/savepart"}, produces = { "application/json" })
	public ResponseEntity<Part> savePart(@RequestBody Part part){

		Part _part = partRepository.save(part);

		return new ResponseEntity(_part, HttpStatus.CREATED);

	}

	@PostMapping(path = {"/saveparthistory/{part_id}"}, produces = { "application/json" })
	public ResponseEntity<PartHistory> savePartHistory(@RequestBody PartHistory partHistory,@PathVariable("part_id") final Long partId ){

		Part part = partRepository2.getOne(partId);
		partHistory.setPart(part);
		PartHistory _parthistory = partHistoryRepository.save(partHistory);

		return new ResponseEntity(_parthistory, HttpStatus.CREATED);

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
