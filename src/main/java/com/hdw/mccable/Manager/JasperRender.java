package com.hdw.mccable.Manager;

import java.util.List;
import java.util.Map;

public class JasperRender extends JasperReportService {

    public void setBeanList(List<? extends Object> list){
        this.beanList = list;
    }

    public void setParams(Map<String, Object> params){
        this.params = params;
    }

}
