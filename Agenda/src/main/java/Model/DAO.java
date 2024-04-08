package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class DAO {
	// Módulo de conexão
	// parametros para conexão

	private String driver = "com.mysql.cj.jdbc.Driver";
	private String url = "jdbc:mysql://127.0.0.1:3306/dbagenda?useTimezone=true&serverTimezone=UTC";

	private String user = "root";
	private String password = "backend@24";

	// Metodo de conexão
	private Connection conectar() {
		Connection con = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, user, password);
			return con;
		} catch (Exception e) {
			System.out.println(e);
			return null;
			// TODO: handle exception
		}
	}
	
	//CRUD -> CREATE
	public void inserirContato(JavaBeans contato) {
		String create = "insert into contatos(nome,fone,email) values(?,?,?);";
		
		try {
			//Abrir a conexão
			Connection con = conectar();
			//Preparar a consulta(query) para execução no Banco de Dados
			PreparedStatement pst = con.prepareStatement(create);
			//Substituir os parametros (?) pelo conteudo dos atributos da classe JavaBeans
			pst.setString(1, contato.getNome());
			pst.setString(2, contato.getFone());
			pst.setString(3, contato.getEmail());
			//Executar a query (Ctrl + Enter)
			pst.executeUpdate();
			//Encerrar a conexao com o BD
			con.close();
		} catch (Exception e) {
			System.out.println(e);
			
		}
	}
		//Crud -> READ
		public ArrayList<JavaBeans> listarContatos(){
		//CRIANDO O OBJETO PARA ACESSAR A CLASSE JAVA BEANS
				ArrayList<JavaBeans> contato = new ArrayList();
				String read = "select * from contatos order by idcon;";
			try {
				Connection con = conectar();
				PreparedStatement pst = con.prepareStatement(read);
				ResultSet rs = pst.executeQuery();
				//o laço abaixo será executado enquanto houver contatos
				while(rs.next()){
					//Variasveis de apoio que recebem os dados do banco
					int idcon = rs.getInt(1);
					String nome = rs.getString(2);
					String fone = rs.getString(3);
					String email = rs.getString(4);
					//POPULANDO O ARRAY LIST
					contato.add(new JavaBeans(idcon, nome, fone, email));
				}
					con.close();
					return contato;
			} catch (Exception e) {
				System.out.println(e);
				return null;
			}
		}
		
		//CRUD -> UPDATE
			//Selecionar contato
		public void selecionarContato(JavaBeans contato) {
			//pst
			String read2 = "select * from contatos where idcon = ?";
			
			try {
				Connection con = conectar();
				PreparedStatement pst = con.prepareStatement(read2);
				
				pst.setString(1, Integer.toString(contato.getIdcon()));
				//ou "pst.setInt(1, contato.idcon()):"
				
				ResultSet rs = pst.executeQuery();
				
				//Laço while para receber dados do BD e enviar para a classe JavaBeans
				while(rs.next()) {
					contato.setIdcon(rs.getInt(1));
					contato.setNome(rs.getString(2));
					contato.setFone(rs.getString(3));
					contato.setEmail(rs.getString(4));
				}
				con.close();
			}	catch(Exception e) {
					System.out.println(e);
					
			}
		}
		public void alterarContato(JavaBeans contato) {
			String atualizar = "update contatos set nome=?,fone=?,email=? where idcon=?;";

			try {
				// Abrir conexão
				Connection con = conectar();
				// Preparar a consulta (query) para execuçâo no Banco de Dados:
				PreparedStatement pst = con.prepareStatement(atualizar);
				// Substituir os parâmetros (?) pelo conteúdo dos atributos da classe JavaBeans
				pst.setString(1, contato.getNome());
				pst.setString(2, contato.getFone());
				pst.setString(3, contato.getEmail());
				pst.setInt(4, contato.getIdcon());
				// Executar a query (Ctrl + enter)
				pst.executeUpdate();
				// Encerrar a conexão com o Banco de Dados
				con.close();

			} catch (Exception e) {
				System.out.println(e);
			
			}
		}
		public void deletarContato(JavaBeans contato) {
			String delete = "delete from contatos where idcon=?;";
			try {
			//abrir conexao com banco de dados
			Connection con = conectar();
			//preparar a query para execução no banco de dados
			PreparedStatement pst = con.prepareStatement(delete);
			//substituir os parametros da classe java beans
			pst.setInt(1, contato.getIdcon());
			//executar comando
			pst.executeUpdate();
			//Encerrar a conexao com banco de dados
			con.close();
			
				
			} catch (Exception e) {
			

			}
		}
		
	// teste de conexão
	
	/*public void testedeconexao() {
		try {
			Connection con = conectar();
			System.out.println(con);
			con.close();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}
	}*/
	
}
