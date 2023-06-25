package org.binar.kamihikoukiairlines.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.binar.kamihikoukiairlines.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/invoice")
@CrossOrigin(origins = "http://localhost:3000")
@Tag(name = "Invoice/Ticket", description = "Invoice/Ticket Controller | contains : Generate Ticket/Cetak Tiket")
public class InvoiceController {

    @Autowired
    InvoiceService invoiceService;
    @Autowired
    private HttpServletResponse response;

    @GetMapping("/generateTicket")
    public void getInvoiceReport(Long bookingId) throws Exception{
        response.setContentType("application/pdf");
        response.setHeader("Content-disposition", "attachment; filename=\"tiket.pdf\"");
        JasperPrint jasperPrint = invoiceService.generateInvoice(bookingId);
        JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
    }

    /*public ResponseEntity<byte[]> generateTicket(@PathVariable Long bookingId) {
        try {
            byte[] ticketBytes = invoiceService.generateTicket(bookingId);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", "ticket.pdf");
            return new ResponseEntity<>(ticketBytes, headers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }*/

    /*public ResponseEntity<?> printTicket(@PathVariable Long bookingId) {
        try {
            byte[] pdfBytes = invoiceService.generateTicket(bookingId);
            ByteArrayResource resource = new ByteArrayResource(pdfBytes);

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=ticket.pdf");

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(resource);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }*/
}
