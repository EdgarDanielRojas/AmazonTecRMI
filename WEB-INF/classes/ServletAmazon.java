/**
 * @(#)Servlet5.java
 *
 *
 * @author 
 * @version 1.00 2017/8/18
 */
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletContext;

/*import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;*/

import java.util.Date;
import java.util.Properties;

import java.io.PrintWriter;
import java.io.IOException;

import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;

//import amazonModel.AmazonADjdbc;



public class ServletAmazon extends HttpServlet
{
//private AmazonADjdbc amazonad = new AmazonADjdbc();
	/*private String host;
    private String port;
    private String user;
    private String pass;
    
public void init() {
        // reads SMTP server setting from web.xml file
        ServletContext context = getServletContext();
        host = context.getInitParameter("host");
        port = context.getInitParameter("port");
        user = context.getInitParameter("user");
        pass = context.getInitParameter("pass");
    }*/
// Atributos RMI
Registry registro; //= LocateRegistry.getRegistry("localhost");
MensajeInterface remoteObject;// = (MensajeInterface)registro.lookup("serverRMIDinamico");

  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException
  {
  	String mensaje = "";
  	String albums="",artista="",idAlbum="",nombreAlbum="";
  	try{
  		registro = LocateRegistry.getRegistry("localhost");
		remoteObject = (MensajeInterface)registro.lookup("serverRMIDinamico");
  		String boton = request.getParameter("bIngresado");
  		if(boton.equals("Consulta Canciones")){
  			nombreAlbum = request.getParameter("album");
  			idAlbum = request.getParameter("id");
	  		String canciones = remoteObject.obtenerCanciones(idAlbum);
	  		response.sendRedirect("Cancion.jsp?cancion="+canciones+"&album="+nombreAlbum+"&id="+idAlbum);
  		}
  		else if(boton.equals("Consultar Albums")){
	  		artista  = request.getParameter("tfArtista");
	  		albums=remoteObject.obtenerCatalogo();
	  		response.sendRedirect("Respuesta.jsp?artista=Todos&album="+albums);	
	  	}
	  	else if(boton.equals("Buscar Artista")){
	  		artista  = request.getParameter("tfArtista");
	  		if(artista!=null){
	  			albums=remoteObject.obtenerAlbums(artista);
	  			if(albums.equals("NO_LOCALIZADO")){
	  				response.sendRedirect("NoLocalizado.jsp");
	  			}
	  			else{
	  				response.sendRedirect("Respuesta.jsp?artista="+artista+"&album="+albums);
	  			}
	  		}
	  		else{
	  			response.sendRedirect("NoLocalizado.jsp");
	  		}
	  	}
	  	else if(boton.equals("Comprar Album")){
	  		nombreAlbum = request.getParameter("album");
  			idAlbum = request.getParameter("id");
  			String costo= remoteObject.obtenerCosto(idAlbum);
  			response.sendRedirect("Compra.jsp?album="+nombreAlbum+"&idAlbum="+idAlbum+"&costo="+costo);
	  	}
	  	else if(boton.equals("Sign in")){
	  		String email,password;
	  		email = request.getParameter("tfEmail");
	  		password = request.getParameter("tfPassword");
	  		String resultado = remoteObject.verifyUser(email,password);
	  		if(!resultado.equals("NO_LOCALIZADO")){
	  			idAlbum=request.getParameter("id");
	  			String infoAlbum = remoteObject.infoAlbum(idAlbum);
		  		response.sendRedirect("Comprar.jsp?idAlbum="+idAlbum+"&"+resultado+"&"+infoAlbum);
		  		
	  		}
	  		else{
	  			//insertar usuario no localizado
	  		}
	  	}
	  	else if(boton.equals("Registrar")){
	  		idAlbum= request.getParameter("id");
	  		response.sendRedirect("Registrar.jsp?idAlbum="+idAlbum);
	  	}
	  	else if(boton.equals("Capturar Datos")){
	  		String email,password,nombre,direccion,telefono,notarjeta,tarjeta;
	  		email = request.getParameter("tfEmail");
	  		password= request.getParameter("tfPassword");
	  		nombre= request.getParameter("tfNombre");
	  		direccion= request.getParameter("tfDireccion");
	  		telefono= request.getParameter("tfTelefono");
	  		notarjeta = request.getParameter("tfNoTarjeta");
	  		tarjeta= request.getParameter("tfTarjeta");
	  		idAlbum= request.getParameter("id");
	  		String resultado = remoteObject.altaUsuario(email,nombre,direccion,notarjeta,tarjeta,password,telefono);
	  		if(resultado.equals("Captura correcta")){
	  			if(idAlbum!=null){
	  				String infoAlbum = remoteObject.infoAlbum(idAlbum);
		  			response.sendRedirect("Comprar.jsp?idAlbum="+idAlbum+"&email="+email+"&nombre="+nombre+"&direccion="+direccion+"&notarjeta="+notarjeta+"&"+infoAlbum);
		  		}
		  		else{
		  			response.sendRedirect("album.html");
		  		}
	  		}
	  		else{
	  			response.sendRedirect("Error.jsp");
	  		}
	  		
	  	}
	  	else if(boton.equals("Cancelar")){
	  		response.sendRedirect("album.html");
	  	}
	  	else if(boton.equals("Registrar Compra")){
	  		idAlbum= request.getParameter("id");
	  		String email = request.getParameter("email");
	  		String cantidad= request.getParameter("tfCantidad");
	  		String resultado = remoteObject.compra(idAlbum,email,cantidad);
	  		//sendEmail(host, port, user, pass, email, "Confirmacion de Compra",resultado);
	  		response.sendRedirect("Exito.jsp");
	  	}	
  		}	
  	catch(Exception ex){
  		System.out.println(ex);
  		response.sendRedirect("Error.jsp");
  	}
  	
  }

  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException
  {
      doGet(request,response);
  }
  /*
  public static void sendEmail(String host, String port,
            final String userName, final String password, String toAddress,
            String subject, String message) throws AddressException,
            MessagingException {
 
        // sets SMTP server properties
        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
 
        // creates a new session with an authenticator
        Authenticator auth = new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(userName, password);
            }
        };
 
        Session session = Session.getInstance(properties, auth);
 
        // creates a new e-mail message
        Message msg = new MimeMessage(session);
 
        msg.setFrom(new InternetAddress(userName));
        InternetAddress[] toAddresses = { new InternetAddress(toAddress) };
        msg.setRecipients(Message.RecipientType.TO, toAddresses);
        msg.setSubject(subject);
        msg.setSentDate(new Date());
        msg.setText(message);
 
        // sends the e-mail
        Transport.send(msg);
 
    }*/
}