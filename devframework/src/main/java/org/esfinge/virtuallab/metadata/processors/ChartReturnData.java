package org.esfinge.virtuallab.metadata.processors;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

@Data
public class ChartReturnData implements Serializable {

    private Date date;
    private String dateStr;
    private Integer value;
}
