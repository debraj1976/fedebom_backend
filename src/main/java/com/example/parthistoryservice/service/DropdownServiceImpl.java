package com.example.parthistoryservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.parthistoryservice.models.Dropdown;
import com.example.parthistoryservice.models.KVPair;
import com.example.parthistoryservice.repositories.DropdownRepository;

@Service
public class DropdownServiceImpl implements DropdownService {
	@Autowired
	private DropdownRepository dropdownRepository;

	public List<Dropdown> findDropdownsByCategory(final String category) {
		return this.dropdownRepository.findAllByCategoryOrderByNameAsc(category);
	}

	public List<KVPair> findAllCategories() {
		return this.dropdownRepository.findAllCategories();
	}
}
