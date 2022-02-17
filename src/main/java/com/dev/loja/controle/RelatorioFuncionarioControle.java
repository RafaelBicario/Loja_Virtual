package com.dev.loja.controle;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.util.JRLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;

@Controller
public class RelatorioFuncionarioControle {

    @Autowired
    private DataSource dataSource;

    @GetMapping("/listaUsuarios")
    public void listaUsuarios(HttpServletResponse response) throws JRException, SQLException, IOException {
        InputStream jasperFile = this.getClass().getResourceAsStream("/relatorios/funcionarios.jasper");

        JasperReport jasperReport = (JasperReport) JRLoader.loadObject(jasperFile);

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,null,dataSource.getConnection());

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition","inline;filename=relatoriofuncionarios.pdf");

        OutputStream outStream = response.getOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);
    };
}
