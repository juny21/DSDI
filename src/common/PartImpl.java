package common;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.ArrayList;

//classe de iplementação para uma peça
public class PartImpl extends UnicastRemoteObject implements Part {
	//número de versão serial 
    private static final long serialVersionUID = 1L;
    //armazena o código da peça
    private int code;
    //armazena o nome da peça
    private String name;
    //armazena a descrição da peça
    private String description;
    //lista que armazena as subpeças da peça atual
    private List<Part> subComponents;

    public PartImpl(int code, String name, String description, List<Part> subComponents) throws RemoteException {
        this.code = code;
        this.name = name;
        this.description = description;
        this.subComponents = (subComponents != null) ? subComponents : new ArrayList<>();
    }

    //retorna o código da peça
    public int getCode() throws RemoteException {
        return code;
    }

    //retorna o nome da peça
    public String getName() throws RemoteException {
        return name;
    }

    //retorna a descrição da peça
    public String getDescription() throws RemoteException {
        return description;
    }

    //retoan a lista de subpeças da peça
    public List<Part> getSubParts() throws RemoteException {
        return subComponents;
    }

    //método para adicionar uma subpeça à lista de subpeças, juntamente com a quantidade especificada
    public void addSubPart(Part subPart, int quantity) throws RemoteException {
        // Implement your logic to add subPart to subComponents list with the given quantity
    }

    //verifica se a peça é primitiva, ou seja, se não possui subpeças
    public boolean isPrimitive() throws RemoteException {
        return subComponents.isEmpty();
    }
    

}
