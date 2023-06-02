package common;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

//implementação da interface PartRepository, que pode ser acessada remotamente
public class PartRepositoryImpl extends UnicastRemoteObject implements PartRepository {
	
	//número de versão serial 
    private static final long serialVersionUID = 1L;
    
    //armazena o nome do repositório
    private String repositoryName;
    
    //lista que armazena todas as peças presentes no repositório
    private List<Part> parts;

    //inicializa o repositório com um nome específico e cria uma nova lista vazia para armazenar peças
    public PartRepositoryImpl(String repositoryName) throws RemoteException {
        this.repositoryName = repositoryName;
        this.parts = new ArrayList<>();
    }

    //retorna o nome do repositório
    public String getRepositoryName() throws RemoteException {
        return repositoryName;
    }

    //retorna o número de peças presentes  no repositório, obtendo a lista 'parts'
    public int getNumberOfParts() throws RemoteException {
        return parts.size();
    }

    //retorna todas as peças presentes no repositório, retornando a lista 'parts'
    public List<Part> getAllParts() throws RemoteException {
        return parts;
    }

    //recebe um código de peça e itera sobre a lista 'parts', procurando uma peça com o código correspondente
    public Part getPartByCode(int code) throws RemoteException {
        for (Part part : parts) {
            if (part.getCode() == code) {
                return part;
            }
        }
        return null;
    }

    //recebe uma peça e a adiciona à lista 'parts'
    public void addPart(Part part) throws RemoteException {
        parts.add(part);
    }
}
