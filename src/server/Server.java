package server;

import common.PartRepository;
import common.PartRepositoryImpl;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Server {
    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter server name:");
            String serverName = scanner.nextLine();

            System.out.println("Enter server port:");
            int port = scanner.nextInt();
            scanner.nextLine(); // Limpar o caractere de nova linha

            //Cria uma instância do PartRepositoryImpl com o nome do servidor
            PartRepository repository = new PartRepositoryImpl(serverName);

            //Cria um registro RMI na porta especificada
            Registry registry = LocateRegistry.createRegistry(port);
            
            //Registra o repositório de peças no registro com o nome do servidor
            registry.rebind(serverName, repository);

            System.out.println("Server running...");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }
}