package client;

import common.PartRepository;
import common.Part;
import common.PartImpl;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Client {
	//Referência para o repositório peças
    private static PartRepository repository;
    
    //Referência para a peça atualmente selecionada
    private static Part currentPart;
    
    //Lista de subpeças para adicionar a uma peça
    private static List<Part> subParts;
    
    // Campo para rastrear o código da próxima parte a ser adicionada
    private static int nextPartCode = 1; 

    public static void main(String[] args) {
        try {
            String serverHostname = "localhost"; // Define o hostname como localhost

            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter server name:");
            String serverName = scanner.nextLine();

            System.out.println("Enter server port:");
            
            int port = scanner.nextInt();
            scanner.nextLine(); 

            Registry registry = LocateRegistry.getRegistry(serverHostname, port);
            repository = (PartRepository) registry.lookup(serverName);

            System.out.println("Connected to server: " + serverName);

            boolean quit = false;

            while (!quit) {
                System.out.println("\n--- Menu ---");
                System.out.println("1. Bind");
                System.out.println("2. List Parts");
                System.out.println("3. Get Part by Code");
                System.out.println("4. Show Current Part");
                System.out.println("5. Clear Subpart List");
                System.out.println("6. Add Subpart");
                System.out.println("7. Add Part");
                System.out.println("8. Quit");
                System.out.print("Enter your choice: ");

                int choice;
                String choiceStr = scanner.nextLine();
                try {
                    choice = Integer.parseInt(choiceStr);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid choice. Please try again.");
                    continue;
                }

                switch (choice) {
                    case 1:
                        bind(scanner);
                        break;
                    case 2:
                        listParts();
                        break;
                    case 3:
                        getPartByCode(scanner);
                        break;
                    case 4:
                        showCurrentPart();
                        break;
                    case 5:
                        clearSubpartList();
                        break;
                    case 6:
                        addSubpart();
                        break;
                    case 7:
                        addPart();
                        break;
                    case 8:
                        quit = true;
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }

            scanner.close();
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }

    //Método para se conectar a um servidor RMI
    private static void bind(Scanner scanner) {
        try {
            System.out.print("Enter server name: ");
            String serverName = scanner.nextLine();
            System.out.print("Enter server hostname: ");
            String serverHostname = scanner.nextLine();
            System.out.print("Enter server port: ");
            int port = scanner.nextInt();
            scanner.nextLine(); 

            Registry registry = LocateRegistry.getRegistry(serverHostname, port);
            repository = (PartRepository) registry.lookup(serverName);

            System.out.println("Connected to server: " + serverHostname + ":" + port);
        } catch (Exception e) {
            System.err.println("Error connecting to server: " + e.toString());
        }
    }


    //Método para listar todas as peças no repositório
    private static void listParts() throws RemoteException {
        List<Part> parts = repository.getAllParts();

        System.out.println("\n--- Parts in Repository ---");
        for (Part part : parts) {
            System.out.println("Code: " + part.getCode());
            System.out.println("Name: " + part.getName());
            System.out.println("Description: " + part.getDescription());
            System.out.println("Is Primitive: " + (part.isPrimitive() ? "Yes" : "No"));
            System.out.println("----------------------------");
        }
    }

    //Método para obter uma peça com base no código fornecido
    private static void getPartByCode(Scanner scanner) {
        try {
            System.out.print("Enter part code: ");
            int code = scanner.nextInt();
            scanner.nextLine(); 

            Part part = repository.getPartByCode(code);
            if (part != null) {
            	currentPart = part;
            	System.out.println("Part retrieved successfully. Code: " + part.getCode());
            } else {
            	System.out.println("Part not found.");
            }
            } catch (RemoteException e) {
            	System.err.println("Error accessing remote server: " + e.toString());
            }
    }
    
    //Método para mostrar as informações da peça atualmente selecionada
    private static void showCurrentPart() {
        if (currentPart != null) {
            try {
                System.out.println("\n--- Current Part Information ---");
                System.out.println("Code: " + currentPart.getCode());
                System.out.println("Name: " + currentPart.getName());
                System.out.println("Description: " + currentPart.getDescription());
                System.out.println("Is Primitive: " + (currentPart.isPrimitive() ? "Yes" : "No"));
                System.out.println("----------------------------");
            } catch (RemoteException e) {
                System.err.println("Error accessing remote server: " + e.toString());
            }
        } else {
            System.out.println("No current part set.");
        }
    }

    //Método para limpar a lista de subpeças
    private static void clearSubpartList() {
        subParts = new ArrayList<>();
        System.out.println("Subpart list cleared.");
    }

    //Método para adicionar uma subpeça à lista de subpeças
    private static void addSubpart() {
        if (currentPart != null) {
            subParts = new ArrayList<>(); // Inicializa a lista subParts

            Scanner scanner = new Scanner(System.in);
            try {
                System.out.print("Enter number of subparts to add: ");
                int numSubparts = scanner.nextInt();
                scanner.nextLine(); 

                for (int i = 0; i < numSubparts; i++) {
                    System.out.print("Enter subpart code: ");
                    int subpartCode = scanner.nextInt();
                    scanner.nextLine(); 

                    Part subpart = repository.getPartByCode(subpartCode);
                    if (subpart != null) {
                        subParts.add(subpart);
                        System.out.println("Subpart added successfully. Code: " + subpart.getCode());
                    } else {
                        System.out.println("Subpart not found.");
                    }
                }
            } catch (RemoteException e) {
                System.err.println("Error accessing remote server: " + e.toString());
            }
        } else {
            System.out.println("No current part set. Please retrieve a part first.");
        }
    }

    //Método para adicionar uma nova peça ao repositório
    private static void addPart() {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.print("Enter part name: ");
            String name = scanner.nextLine();
            System.out.print("Enter part description: ");
            String description = scanner.nextLine();

            Part part = new PartImpl(nextPartCode, name, description, subParts);
            repository.addPart(part);
            nextPartCode++;

            System.out.println("Part added successfully.");
        } catch (RemoteException e) {
            System.err.println("Error accessing remote server: " + e.toString());
        }
    }

}