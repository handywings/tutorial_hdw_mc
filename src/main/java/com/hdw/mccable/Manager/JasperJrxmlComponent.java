package com.hdw.mccable.Manager;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.context.ServletContextAware;

import com.hdw.mccable.utils.ConstantUtils;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

public class JasperJrxmlComponent {
    private static final Logger log = LoggerFactory.getLogger(JasperJrxmlComponent.class);


    public JasperReport compileJasperReport(String fileName, HttpServletRequest request) throws JRException {

    	// dev
//    	String pathJrxml = "/home/thanarat/space/0/cable/cable/src/main/webapp/jasper/"+fileName+".jrxml";
//    	String pathJasper = "/home/thanarat/space/0/cable/cable/src/main/webapp/jasper/"+fileName+".jasper";
    	// prod
		String pathJrxml = request.getSession().getServletContext().getRealPath("/jasper/" + fileName + ".jrxml");
		String pathJasper = request.getSession().getServletContext().getRealPath("/jasper/" + fileName + ".jasper");

		log.info("pathJrxml : {}", pathJrxml);
		log.info("pathJasper : {}", pathJasper);
		File reportFile = new File(pathJasper);
		// If compiled file is not found, then compile XML template
		if (!reportFile.exists()) {
			JasperCompileManager.compileReportToFile(pathJrxml, pathJasper);
		}
		JasperReport jasperReport = (JasperReport) JRLoader.loadObjectFromFile(reportFile.getPath());
		return jasperReport;
	}

    public byte[] renderJasperReportPDF(String filePathInWebapp, List<Map<String, Object>> jobData,
            ParamsEnum.StreamType reportType, HttpServletRequest request)
            throws Exception {
        return renderJasperReportPDFWithJobObject(filePathInWebapp, (List) jobData, reportType, request);
    }

    @Deprecated
    public byte[] renderJasperReportPDFWithJobObject(String filePathInWebapp, List<Object> jobData,
            ParamsEnum.StreamType reportType, HttpServletRequest request)
            throws Exception {

        JasperReport mainReport = compileJasperReport(filePathInWebapp, request);

        //set bean list
        JasperRender jasperRender = new JasperRender();
        jasperRender.setBeanList(jobData);

        JRParameter[] compiledReportParams = mainReport.getParameters();

        if (compiledReportParams != null) {
            for (JRParameter eachParam : compiledReportParams) {
                String paramName = eachParam.getName();
                Class<?> valueClass = eachParam.getValueClass();

                if (JasperReport.class == valueClass && paramName.endsWith(".jrxml")) {

                    log.info("Compiling sub-report named {}", paramName);
                    JasperReport compiledSubReport = compileJasperReport(paramName, request);

                    jasperRender.addParams(paramName, compiledSubReport);
                }
            }
        }

        if (reportType == ParamsEnum.StreamType.PDF) {
            byte[] buffer = jasperRender.processStream(ParamsEnum.StreamType.PDF, mainReport);
            return buffer;
        } else if (reportType == ParamsEnum.StreamType.EXCEL) {
            byte[] buffer = jasperRender.processStream(ParamsEnum.StreamType.EXCEL, mainReport);
            return buffer;
        } else {
            throw new Exception("Unknown report type " + reportType);
        }
    }
}
