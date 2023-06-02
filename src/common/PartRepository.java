package common;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

//Interface para um repositório de peças
public interface PartRepository extends Remote {
	
	//retorna o nome do repositório
    String getRepositoryName() throws RemoteException;
    
    //retorna o número de peças no repositório
    int getNumberOfParts() throws RemoteException;
    
    //retorna a peça com o código especificado do repositório
    List<Part> getAllParts() throws RemoteException;
    
    //retorna a peça com o código especificado do repositório
    Part getPartByCode(int code) throws RemoteException;
    
    //adiciona uma peça ao repositório
    void addPart(Part part) throws RemoteException;
}
