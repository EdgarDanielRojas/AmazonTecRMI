import java.rmi.Remote;
import java.rmi.RemoteException;

public interface MensajeInterface extends Remote
{
    //public String mensaje(String message) throws RemoteException;
    
    public String altaUsuario(String email,String nombre, String direccion, String notarjeta, String tarjeta, String password, String telefono) throws RemoteException;
	
	public String idCliente(String email) throws RemoteException;
	
	public void actualizarInventario(String album,String cantidad) throws RemoteException;
	
	public String infoDiscoVenta(String album) throws RemoteException;
		
	public String nombreCliente(String id) throws RemoteException;
		
	public String compra(String album,String cliente, String cantidad) throws RemoteException;
		
	public String infoAlbum(String id) throws RemoteException;
		
	public String verifyUser(String email,String password) throws RemoteException;
		
	public String obtenerCanciones(String album) throws RemoteException;
		
	public String obtenerArtista(String id) throws RemoteException;
		
	public String obtenerId(String artista) throws RemoteException;
		
	public String obtenerCosto(String id) throws RemoteException;
	
	public String obtenerAlbums(String artista) throws RemoteException;
		
	public String obtenerCatalogo() throws RemoteException;
}
