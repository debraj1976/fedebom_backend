package com.example.parthistoryservice.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.parthistoryservice.models.Dropdown;
import com.example.parthistoryservice.models.KVPair;

@Repository
public interface DropdownRepository extends CrudRepository<Dropdown, Long> {
	List<Dropdown> findAllByCategoryOrderByNameAsc(final String category);

	@Query(value = "select distinct category as name, category as value from dropdown order by name, value", nativeQuery = true)
	List<KVPair> findAllCategories();
}