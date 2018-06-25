package com.adqt.springservice.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
@javax.persistence.Table(name="table_info")
public class TableInformation implements Serializable{

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@javax.persistence.Column(name="id")
	private int id;
	
	@javax.persistence.Column(name="table_name")
	private String tableName;

	@OneToMany(mappedBy="tableInformation",orphanRemoval=true,cascade=CascadeType.ALL,fetch=FetchType.EAGER)
	private Set<ColumnInformation> columnInformations;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public Set<ColumnInformation> getColumnInformations() {
		return columnInformations;
	}

	public void setColumnInformations(Set<ColumnInformation> columns) {
		this.columnInformations = columns;
	}

	@Override
	public String toString() {
		return "Table [id=" + id + ", tableName=" + tableName + ", columns=" + columnInformations + "]";
	}
	
}
