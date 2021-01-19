package com.example.parthistoryservice.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.parthistoryservice.models.Part;

@Repository
public interface PartRepository extends CrudRepository<Part, Long> {
	List<Part> findAllByOrderByCategoryAscNameAsc();

	@Query("SELECT p from Part p WHERE p.base = :base AND p.prefix = :prefix AND p.suffix = :suffix And p.usageId = :usageId")
	List<Part> findAllByCriteria(final String base, final String prefix, final String suffix, final Long usageId);
}
