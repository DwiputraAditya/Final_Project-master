package org.binar.kamihikoukiairlines.service;

import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import org.binar.kamihikoukiairlines.model.Booking;
import org.binar.kamihikoukiairlines.model.Passenger;
import org.binar.kamihikoukiairlines.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class InvoiceService {
    @Autowired
    private DataSource dataSource;
    @Autowired
    BookingRepository bookingRepository;

    public Connection getConnection(){
        try {
            return dataSource.getConnection();
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    public JasperPrint generateInvoice(Long bookingId) throws Exception {
        InputStream fileReport = new ClassPathResource("ticket/TiketPesawat.jrxml").getInputStream();
        JasperReport jasperReport = JasperCompileManager.compileReport(fileReport);
        Map<String, Object> params = new HashMap<>();
        params.put("bookingId", bookingId);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, getConnection());
        log.info("Successfully generated ticket for booking id: " + bookingId);
        return jasperPrint;
    }

    /*public byte[] generateTicket(Long bookingId) throws Exception {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new Exception("Booking not found"));

        InputStream reportTemplate = getClass().getResourceAsStream("/ticket/TiketPesawat.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(reportTemplate);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("bookingId", bookingId);
        List<Passenger> passengers = booking.getPassengersList();
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(passengers);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
        byte[] pdfBytes = JasperExportManager.exportReportToPdf(jasperPrint);

        log.info("Has successfully  generate ticket for booking id: " + bookingId);
        return pdfBytes;
    }*/


    /*public JasperPrint generateInvoice(Long bookingId) throws Exception{
        InputStream fileReport = new ClassPathResource("ticket/TiketPesawat.jasper").getInputStream();
        JasperReport jasperReport = (JasperReport) JRLoader.loadObject(fileReport);
        Map<String, Object> params = new HashMap<>();
        params.put("bookingId", bookingId);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, getConnection());
        return jasperPrint;
    }*/

    /*public JasperPrint generateTicket(Long bookingId) throws Exception {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new Exception("Booking not found"));

        InputStream reportTemplate = getClass().getResourceAsStream("/ticket/kamihikouki.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(reportTemplate);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("bookingId", bookingId);
        List<Passenger> passengers = booking.getPassengersList();
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(passengers);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
        byte[] pdfBytes = outputStream.toByteArray();

        log.info("Successfully generated ticket for booking id: " + bookingId);
        return pdfBytes;
    }*/

}
