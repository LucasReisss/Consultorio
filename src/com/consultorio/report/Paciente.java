package com.consultorio.report;

import java.sql.Connection;
import java.util.HashMap;

import javax.servlet.annotation.WebServlet;

import com.consultorio.factory.JDBCFactory;

@WebServlet("/pacientesReport")
public class Paciente extends ReportServlet {


	private static final long serialVersionUID = -4008819968239926066L;

	@Override
	public String getArquivoJasper() {
		return "Paciente.jasper";
	}

	@Override
	public HashMap<String, Class<?>> getParametros() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Connection getConnection() {
		return JDBCFactory.getConnection();
	}

}
