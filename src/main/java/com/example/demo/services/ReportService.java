package com.example.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

@Service
public class ReportService {

    @Autowired
    private DataSource dataSource;

    public byte[] generateReport() throws Exception {
        // Load the JRXML file
        ClassPathResource resource = new ClassPathResource("reports/ReporteHosica.jrxml");
        InputStream inputStream = resource.getInputStream();

        // Compile the Jasper report from .jrxml to .jasper
        JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);

        // Parameters for the report
        Map<String, Object> parameters = new HashMap<>();

        // Get database connection
        try (Connection connection = dataSource.getConnection()) {
            // Fill the report
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, connection);

            // Export the report to a PDF file
            return JasperExportManager.exportReportToPdf(jasperPrint);
        }
    }

    // Nuevo método para generar el reporte de habitaciones frecuentes
    public byte[] generateHabitacionesFrecuentesReport() throws Exception {
        ClassPathResource resource = new ClassPathResource("reports/ReportHabFrecuente.jrxml");
        InputStream inputStream = resource.getInputStream();
        JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);
        Map<String, Object> parameters = new HashMap<>();
        try (Connection connection = dataSource.getConnection()) {
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, connection);
            return JasperExportManager.exportReportToPdf(jasperPrint);
        }
    }

    // Nuevo método para generar el reporte de Ultimas Reservas
    public byte[] generateUltimasReservas() throws Exception {
        ClassPathResource resource = new ClassPathResource("reports/ReporteUltimasReservas.jrxml");
        InputStream inputStream = resource.getInputStream();
        JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);
        Map<String, Object> parameters = new HashMap<>();
        try (Connection connection = dataSource.getConnection()) {
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, connection);
            return JasperExportManager.exportReportToPdf(jasperPrint);
        }
    }
}
