package com.example.parthistoryservice.models;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "Part")
public class Part implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "part_id")
	private Long id;
	private String category;
	private String name;
	private String prefix;
	private String suffix;
	private String base;
	@Column(name = "usage_id")
	private Long usageId;
	@Column(name = "created_date_time", columnDefinition = "TIMESTAMP")
	private Date createdDateTime;
	@Column(name = "updated_date_time", columnDefinition = "TIMESTAMP")
	private Date updatedDateTime;
	@JsonIgnore
	@OneToMany(mappedBy = "part", fetch = FetchType.LAZY, cascade = { CascadeType.ALL })
	@OrderBy("id ASC")
	private List<PartHistory> histories;

	public Long getId() {
		return this.id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public List<PartHistory> getHistories() {
		return this.histories;
	}

	public void setHistories(final List<PartHistory> histories) {
		this.histories = histories;
	}

	public String getCategory() {
		return this.category;
	}

	public void setCategory(final String category) {
		this.category = category;
	}

	public Date getCreatedDateTime() {
		return this.createdDateTime;
	}

	public void setCreatedDateTime(final Date createdDateTime) {
		this.createdDateTime = createdDateTime;
	}

	public Date getUpdatedDateTime() {
		return this.updatedDateTime;
	}

	public void setUpdatedDateTime(final Date updatedDateTime) {
		this.updatedDateTime = updatedDateTime;
	}

	public String getPrefix() {
		return this.prefix;
	}

	public void setPrefix(final String prefix) {
		this.prefix = prefix;
	}

	public String getSuffix() {
		return this.suffix;
	}

	public void setSuffix(final String suffix) {
		this.suffix = suffix;
	}

	public String getBase() {
		return this.base;
	}

	public void setBase(final String base) {
		this.base = base;
	}

	public Long getUsageId() {
		return this.usageId;
	}

	public void setUsageId(final Long usageId) {
		this.usageId = usageId;
	}
}
