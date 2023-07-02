package org.binar.kamihikoukiairlines.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.binar.kamihikoukiairlines.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/invoice")
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
}
