package com.example.demo.controladores;

import com.example.demo.services.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @GetMapping("/clientes")
    public ResponseEntity<byte[]> getReport() {
        try {
            byte[] data = reportService.generateReport();

            // Set headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("inline", "clientes_report.pdf");

            return ResponseEntity.ok().headers(headers).body(data);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }

    // Nuevo endpoint para el reporte de habitaciones frecuentes
    @GetMapping("/habitaciones-frecuentes")
    public ResponseEntity<byte[]> getHabitacionesFrecuentesReport() {
        try {
            byte[] data = reportService.generateHabitacionesFrecuentesReport();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("inline", "habitaciones_frecuentes_report.pdf");
            return ResponseEntity.ok().headers(headers).body(data);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }

    // Nuevo endpoint para el reporte de habitaciones frecuentes
    @GetMapping("/ultimas-reservas")
    public ResponseEntity<byte[]> generateUltimasReservas() {
        try {
            byte[] data = reportService.generateHabitacionesFrecuentesReport();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("inline", "Ultimas-Reservas.pdf");
            return ResponseEntity.ok().headers(headers).body(data);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }
}
