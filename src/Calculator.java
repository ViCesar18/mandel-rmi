import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Calculator extends Remote {
  long add(long a, long b) throws RemoteException;
}
