package com.example.parthistoryservice.repositories;

import java.util.Date;

import javax.persistence.TemporalType;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Temporal;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.parthistoryservice.models.Part;
import com.example.parthistoryservice.models.PartHistory;

@Repository
public interface PartHistoryRepository extends CrudRepository<PartHistory, Long> {
	@Query("SELECT p FROM Part p JOIN FETCH p.histories as h WHERE p.id=:partId")
	Part findByPartAndDates(final Long partId);

	@Query("SELECT p FROM Part p JOIN FETCH p.histories as h WHERE p.id=:partId and h.createdDateTime BETWEEN :fromDate AND :toDate")
	Part findByPartAndDates(final Long partId, @Temporal(TemporalType.DATE) @Param("fromDate") final Date fromDate,
			@Temporal(TemporalType.DATE) @Param("toDate") final Date toDate);
}
