package com.example.parthistoryservice.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.parthistoryservice.models.Dropdown;
import com.example.parthistoryservice.models.KVPair;
import com.example.parthistoryservice.service.DropdownService;

@RestController
@RequestMapping({ "/dropdownservice" })
@CrossOrigin(origins = { "http://localhost:4200" })
public class DropdownController {

	@Autowired
	private DropdownService dropdownService;

	@GetMapping(produces = { "application/json" })
	public ResponseEntity<List<KVPair>> getAllPartCategories() {
		final List<KVPair> dropdowns = this.dropdownService.findAllCategories();
		return new ResponseEntity<List<KVPair>>(dropdowns, HttpStatus.OK);
	}

	@GetMapping(path = { "/category/{name}" }, produces = { "application/json" })
	public ResponseEntity<List<Dropdown>> getPartHistoryByPart(@PathVariable("name") final String name) {
		final List<Dropdown> dropdowns = this.dropdownService.findDropdownsByCategory(name);
		return new ResponseEntity<List<Dropdown>>(dropdowns, HttpStatus.OK);
	}
}
