package com.adqt.springservice.service;

import com.adqt.springservice.entity.ColumnInformation;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

public class Schema implements Serializable{

    Set<ColumnInformation> columns;

    public Schema(Set<ColumnInformation> columns) {
        this.columns= columns;
    }

    public Set<ColumnInformation> getColumns() {
        return columns;
    }

    public void setColumns(Set<ColumnInformation> columns) {
        this.columns = columns;
    }

    public ColumnInformation getColumn(Integer columnIndex) {
        for(ColumnInformation column:columns){
            if(column.getColumnIndex() == columnIndex)
                return column;
        }
        return null;
    }
}
