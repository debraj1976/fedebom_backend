package com.example.parthistoryservice.service;

import java.util.List;

import com.example.parthistoryservice.models.Dropdown;
import com.example.parthistoryservice.models.KVPair;

public interface DropdownService {
	List<Dropdown> findDropdownsByCategory(final String category);

	List<KVPair> findAllCategories();
}