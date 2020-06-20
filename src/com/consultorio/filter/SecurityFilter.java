package com.consultorio.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.consultorio.model.Pessoa;

@WebFilter(filterName = "SecurityFilter", urlPatterns = { "/faces/*" })

public class SecurityFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

// 	 	Para desabilitar o filter, descomente as duas proximas linhas e comente o restante		
//		chain.doFilter(request, response);
//		return;
		
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;

		HttpSession session = (HttpSession) req.getSession();

		Pessoa pessoa = (Pessoa) session.getAttribute("usuarioLogado");
		String link = req.getRequestURL().toString();
		System.out.println(link);

		if (!link.equalsIgnoreCase("http://localhost:8080/Consultorio/faces/login.xhtml") && pessoa == null) {

			res.sendRedirect(req.getContextPath() + "/faces/login.xhtml");
			return;

		} else if (pessoa != null && link.equalsIgnoreCase("http://localhost:8080/Consultorio/faces/login.xhtml")) {
			res.sendRedirect(req.getContextPath() + "/faces/home.xhtml");
			return;
		} else {
			chain.doFilter(request, response);
		}
	}

	

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		System.out.println("SecurityFilter Iniciado.");
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

}
