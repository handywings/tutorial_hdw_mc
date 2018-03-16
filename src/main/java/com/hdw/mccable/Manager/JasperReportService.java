package com.hdw.mccable.Manager;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.googlecode.jthaipdf.jasperreports.engine.ThaiExporterManager;

import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JExcelApiExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;

public class JasperReportService {
    private Logger LOG = LoggerFactory.getLogger(JasperReportService.class);

    protected List<? extends Object> beanList;

    protected Map<String, Object> params = new HashMap<String, Object>();

    public byte[] processStream(ParamsEnum.StreamType type, JasperReport compiledReport) throws Exception {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Start Processing processStream()...");
        }

        JasperPrint print = null;
        ByteArrayOutputStream baos = null;

        try {

            LOG.info("Param type is " + type);

            baos = new ByteArrayOutputStream();

            print = JasperFillManager.fillReport(compiledReport, params,
                    new JRBeanCollectionDataSource(getJavaBeanList()));

            if(ParamsEnum.StreamType.PDF.equals(type))
            {
                ThaiExporterManager.exportReportToPdfStream(print, baos);
            }
            else if(ParamsEnum.StreamType.EXCEL.equals(type))
            {
//              JRXlsExporter exporterXLS = new JRXlsExporter();
//              exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, print);
//              exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, baos);
//              exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.TRUE);
//              exporterXLS.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
//              exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
//              exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
//              exporterXLS.exportReport();
              
              JRExporter exporter = new JExcelApiExporter();

              // Configura el exporter          
              exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
              exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
              exporter.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE); 
              exporter.setParameter(JRXlsExporterParameter.IGNORE_PAGE_MARGINS, Boolean.TRUE); 
              
              exporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.TRUE);
              exporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
              //exporter.setParameter(JRXlsExporterParameter.IS_IGNORE_GRAPHICS, Boolean.TRUE); 
              exporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_COLUMNS, Boolean.TRUE);
              exporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);

              // Exporta a Excel
              exporter.exportReport();
            }
            else
            {
                LOG.error(String.format("Report type '%1$s' dose not support...", type));
                throw new Exception("Report type '" + type + "' is not supported");
            }

            return baos.toByteArray();

        } catch(Throwable t) {
            LOG.error("Cannot export report to stream.", t);

            throw new Exception("Cannot export report due to " + t.getMessage(), t);
        } finally {
            if (null != baos) {
                baos.close();
                baos = null;
            }
            if (LOG.isDebugEnabled()) {
                LOG.info("Complete Processing processStream()...");
            }
        }

    }

    protected Map<String, Object> createParams() throws Exception {
        Map<String, Object> params = new HashMap<String, Object>();

        return params;
    }

    public void addParams(String key, Object value) {
        if (null == params) {
            params = new HashMap<String, Object>();
        }

        if(!params.containsKey("REPORT_LOCALE")){
            params.put("REPORT_LOCALE", Locale.US);
        }

        if (LOG.isDebugEnabled()) {
            LOG.debug(String.format("Input Params: (key=%1$s , value=%2$s)",
                    key, value));
        }

        params.put(key, value);
    }

    public Map<String,Object> getReportParams(){
        return this.params;
    }

    protected List<? extends Object> getJavaBeanList() throws Exception{
        if (LOG.isDebugEnabled()) {
            if(null != beanList){
                LOG.debug("Input JavaBean Not Null");
            }
        }
        return beanList;
    }
}
