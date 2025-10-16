package com.gabrielsmm.gestrun.util;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;

public class RelatorioUtil {

    public static byte[] exportarRelatorio(JasperPrint jasperPrint, String formato) throws JRException {
        if ("pdf".equalsIgnoreCase(formato)) {
            return JasperExportManager.exportReportToPdf(jasperPrint);
        } else if ("xlsx".equalsIgnoreCase(formato)) {
            try (var out = new java.io.ByteArrayOutputStream()) {
                var exporter = new JRXlsxExporter();
                exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
                exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(out));
                exporter.exportReport();
                return out.toByteArray();
            } catch (Exception e) {
                throw new JRException("Erro ao exportar XLSX", e);
            }
        } else {
            throw new IllegalArgumentException("Formato não suportado: " + formato);
        }
    }

}
