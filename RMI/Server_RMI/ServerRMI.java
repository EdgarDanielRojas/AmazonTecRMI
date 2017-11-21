//import java.rmi.Naming;   // No se usa en dinamico
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

public class ServerRMI
{

    public static void main(String args[]) throws Exception
    {
      MensajeInterface mensajeInterface = new MensajeImplement();
      MensajeInterface remoteObject = (MensajeInterface)UnicastRemoteObject.exportObject(mensajeInterface, 0);

      Registry registro = LocateRegistry.getRegistry();

      registro.bind("serverRMIDinamico", remoteObject);

      System.out.println("Servidor RMI registrado e iniciado en el puerto: "+Registry.REGISTRY_PORT);
      System.out.println("Servidor preparado para recibir conexiones:\n");
    }
}
