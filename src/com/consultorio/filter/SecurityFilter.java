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

		HttpServletRequest servletRequest = (HttpServletRequest) request;
		// imprime o endereco da pagina
		String endereco = servletRequest.getRequestURI();
		System.out.println(endereco);
		if (endereco.equals("/Consultorio/faces/login.xhtml")) {
			chain.doFilter(request, response);
			return;
		}

		// retorna a sessao corrente (false - para nao criar uma nova sessao)
		HttpSession session = servletRequest.getSession(false);

		Pessoa pessoa = null;
		if (session != null)
			pessoa = (Pessoa) session.getAttribute("pessoaLogado");

		if (pessoa == null) {
			((HttpServletResponse) response).sendRedirect("/Consultorio/faces/login.xhtml");
		} else {
			// segue o fluxo
			chain.doFilter(request, response);
			return;
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
