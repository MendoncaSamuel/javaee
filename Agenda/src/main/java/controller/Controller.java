package controller;

import java.io.IOException;
import java.util.ArrayList;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import Model.DAO;
import Model.JavaBeans;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//conectar o url do controller com o main
//mapeamento
@WebServlet(urlPatterns = { "/Controller", "/main", "/main2", "/insert", "/select", "/update", "/delete", "report"})
//@WebServlet(urlPatterns= {"/controller","/main2"})

public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
	DAO dao = new DAO();
	JavaBeans contato = new JavaBeans();
	

	public Controller() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		// response.getWriter().append("Served at: ").append(request.getContextPath());

		// teste de conexão
		// dao.testedeconexao();

		String requisicao = request.getServletPath();
		System.out.println(requisicao);

		if (requisicao.equals("/main")) {
			contatos(request, response);
		}
		else if (requisicao.equals("/insert")) {
			novoContato(request, response);
		}
		
		else if (requisicao.equals("/main2")) {
			listacontatos(request, response);
		}
		else if(requisicao.equals("/select")) {
			selecionarContato(request, response);		
			}
		else if(requisicao.equals("/update")) {
			editarContato(request,response);
		}
		else if(requisicao.equals("/delete")) {
			excluirContato(request,response);
		}
		else if(requisicao.equals("/report")) {
			gerarRelatorio(request,response);
		}
		else {
			response.sendRedirect("Index.html");
		}
		
	}

	protected void contatos(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		//response.sendRedirect("agenda.jsp");
		//CRIANDO UM OBJETO QUE IRÁ RECEBER OS DADOS DA CLASSE JavaBeans
		ArrayList<JavaBeans> lista = dao.listarContatos();
		//Encaminhamento do objeoto lista ao documento agenda.jsp
		request.setAttribute("contatos", lista);
		RequestDispatcher rd = request.getRequestDispatcher("agenda.jsp");
		rd.forward(request, response);
		
		
		
		//TESTE DE RECEBIMENTO DA LISTA
		/*for(int i=0; i<lista.size(); i++) {
			System.out.println(lista.get(i).getIdcon());
			System.out.println(lista.get(i).getNome());
			System.out.println(lista.get(i).getFone());
			System.out.println(lista.get(i).getEmail());
			
		}*/
	}

	protected void listacontatos(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		response.sendRedirect("listadecontatos.jsp");
	}
	
	//Novo Contato
	protected void novoContato(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		//teste de recebimento de dados do formulário novo.html
		/*
		 * System.out.println(request.getParameter("nome"));
		 *System.out.println(request.getParameter("fone"));
		 *System.out.println(request.getParameter("email"));
		 */
	}
	//Editar contato
		protected void selecionarContato (HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException  {
			//Recebimento do id de contato que sera editado
			String idcon = request.getParameter("idcon");
			
			//Teste de receptação de atributo
			//System.out.println(idcon);
			
			//"integer.parseInt()" converter "String" para "int"
			contato.setIdcon(Integer.parseInt(idcon));
			//Executar o metodo selecioanrContato pelo DAO
			dao.selecionarContato(contato);
			
			/*System.out.println(contato.getIdcon());
			System.out.println(contato.getNome());
			System.out.println(contato.getFone());
			System.out.println(contato.getEmail());*/
			
			//Settar os atributos da classe Java Beans ao formulário com o conteúdo 
			request.setAttribute("Idcon", contato.getIdcon());
			request.setAttribute("Nome", contato.getNome());
			request.setAttribute("Fone", contato.getFone());
			request.setAttribute("Email", contato.getEmail());
			//Encaminhar ao documento Editar.jsp
			RequestDispatcher rd = request.getRequestDispatcher("editar.jsp");
			rd.forward(request,  response);
			
			
			
		}
		protected void editarContato (HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
			//Teste de recebimento para edição  após pressionar o botao salvar
			/*System.out.println(request.getParameter("idcon"));
			System.out.println(request.getParameter("Nome"));
			System.out.println(request.getParameter("Fone"));
			System.out.println(request.getParameter("Email"));*/
			
			//Settar os atributos do JavaBeans
			contato.setIdcon(Integer.parseInt(request.getParameter("idcon")));
			contato.setNome(request.getParameter("nome"));
			contato.setFone(request.getParameter("fone"));
			contato.setEmail(request.getParameter("email"));
			//exevcutar o método alterarContato da classe DAO
			dao.alterarContato(contato);
			//redirecionar para o documento agenda.jsp (salvar alterações)
			response.sendRedirect("main"); 
		}
		
		protected void excluirContato(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
			String idcon = request.getParameter("idcon"); 
			//System.out.println(idcon);
			
			contato.setIdcon(Integer.parseInt(idcon));
			//Passar método servlet para DAO
			dao.deletarContato(contato);
			//REDIRECIONAR PARA AGENDA.JSP
			response.sendRedirect("main");
			
		}
		protected void gerarRelatorio(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
			Document documento = new Document();
			
			try {
				//Tipo de conteúdo
				response.setContentType("application/pdf");
				
				//Nome do documento
				response.addHeader("Content-Disposition", "inline; filename="+"Contatos");
				
				//Criar o documento
				PdfWriter.getInstance(documento, response.getOutputStream());
				
				//Abrir o documento -> conteúdo
				documento.open();
				documento.add(new Paragraph("Lista de contatos: "));
				
				//listagem de contatos
				documento.add(new Paragraph(" "));
				
				//Criar uma tabela no pdf
				PdfPTable tabela = new PdfPTable(4);
				
				//Cabeçalho
				PdfPCell col1 = new PdfPCell(new Paragraph("Id: "));
				PdfPCell col2 = new PdfPCell(new Paragraph("Nome: "));
				PdfPCell col3 = new PdfPCell(new Paragraph("Fone: "));
				PdfPCell col4 = new PdfPCell(new Paragraph("E-mail: "));
				tabela.addCell(col1);
				tabela.addCell(col2);
				tabela.addCell(col3);
				tabela.addCell(col4);
				//Popular a tabela com os contatos
				ArrayList<JavaBeans> lista = dao.listarContatos();
				for (int i = 0; i < lista.size(); i++) {
					tabela.addCell(Integer.toString(lista.get(i).getIdcon()));
					tabela.addCell(lista.get(i).getNome());
					tabela.addCell(lista.get(i).getFone());
					tabela.addCell(lista.get(i).getEmail());
				}
				
				documento.add(tabela);
				
				
				
				documento.close();
				} catch (Exception e) {
				System.out.println(e);
				documento.close();
			}
		}
		
		/*setar os atributos da classe JavaBeans.java
		contato.setNome(request.getParameter("nome"));
		contato.setFone(request.getParameter("fone"));
		contato.setEmail(request.getParameter("email"));
		
		//invocar o metodo inserir contato passando o objeto contato (como parametro)
		dao.inserirContato(contato);
		//Redirecionar para a Pagina agenda.jsp
		response.sendRedirect("main");*/
	}
