package com.adqt.springservice.dto;

import java.io.Serializable;

public class DataQualityStatDTO implements Serializable {

	private static final long serialVersionUID = 1207833351585694128L;

	private String tableName;
	private long totalRowCount;
	private long qualifiedRowCount;
	private double percentage;
	private boolean isBelowThreshold;
	
	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public long getTotalRowCount() {
		return totalRowCount;
	}

	public void setTotalRowCount(long totalRowCount) {
		this.totalRowCount = totalRowCount;
	}

	public long getQualifiedRowCount() {
		return qualifiedRowCount;
	}

	public void setQualifiedRowCount(long qualifiedRowCount) {
		this.qualifiedRowCount = qualifiedRowCount;
	}
	
	public double getPercentage() {
		return percentage;
	}

	public void setPercentage(double percentage) {
		this.percentage = percentage;
	}

	public boolean isBelowThreshold() {
		return isBelowThreshold;
	}

	public void setBelowThreshold(boolean isBelowThreshold) {
		this.isBelowThreshold = isBelowThreshold;
	}

	@Override
	public String toString() {
		return "DataQualityStatDTO [tableName=" + tableName + ", totalRowCount=" + totalRowCount
				+ ", qualifiedRowCount=" + qualifiedRowCount + ", percentage=" + percentage + ", isBelowThreshold="
				+ isBelowThreshold + "]";
	}

}
