package common;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

//Interface para uma peça

public interface Part extends Remote {
	//retorna o código da peça
    int getCode() throws RemoteException;
    
    //retorna o nome da peça
    String getName() throws RemoteException;
    
    //retorna a descrição da peça
    String getDescription() throws RemoteException;
    
    //retorna lista de subpeças da peça
    List<Part> getSubParts() throws RemoteException;
    
    //adiciona uma subpeça à peça com a quantidade especificada
    void addSubPart(Part subPart, int quantity) throws RemoteException;
    
    //verifica se a peça é uma peça primitiva (não possui subpeças)
    boolean isPrimitive() throws RemoteException;
}
