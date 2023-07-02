package org.binar.kamihikoukiairlines.service;

import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.*;
import org.binar.kamihikoukiairlines.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
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
}
