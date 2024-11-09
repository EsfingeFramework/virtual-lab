package org.esfinge.virtuallab.metadata.processors;

import java.io.Serializable;
import java.util.Date;

public class ChartReturnData implements Serializable {

    private Date date;
    private String dateStr;
    private Integer value;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDateStr() {
        return dateStr;
    }

    public void setDateStr(String dateStr) {
        this.dateStr = dateStr;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

}
