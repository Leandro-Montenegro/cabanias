/* Utilizaremos ArrayList para gestionar tipos de cabañas y reservas */

import java.util.ArrayList;
import java.util.Scanner;
import java.util.InputMismatchException;

public class SistemaReservas {
    // ArrayList para almacenar los tipos de cabañas disponibles
    private static ArrayList<Cabania> tiposCabanias = new ArrayList<>();
    private static ArrayList<String> reservasRealizadas = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        inicializarTiposCabanias();
        mostrarMenuCliente();
    }
    
    /* Inicializa los 3 tipos de cabañas fijos usando ArrayList.add() */
    private static void inicializarTiposCabanias() {
        try {
            tiposCabanias.add(new Gold("Cabaña Gold", 500.0, 4));
            tiposCabanias.add(new Premium("Cabaña Premium", 250.0, 6));
            tiposCabanias.add(new Estandar("Cabaña Estándar", 80.0, 2));
            System.out.println("Tipos de cabañas disponibles cargados correctamente.");
        } catch (Exception e) {
            System.err.println("Error al cargar tipos de cabañas: " + e.getMessage());
        }
    }
    
    /* Menú principal */
    
    private static void mostrarMenuCliente() {
        int opcion = 0;
        
        do {
            try {
                System.out.println("\n=== BIENVENIDO AL SISTEMA DE RESERVAS ===");
                System.out.println("1. Ver tipos de cabañas disponibles");
                System.out.println("2. Ver detalles y servicios de cada cabaña");
                System.out.println("3. Realizar reserva");
                System.out.println("0. Salir");
                System.out.print("Seleccione una opción: ");
                
                opcion = scanner.nextInt();
                scanner.nextLine(); // Limpiar buffer
                
                switch (opcion) {
                    case 1:
                        mostrarTiposCabanias();
                        break;
                    case 2:
                        mostrarDetallesCompletos();
                        break;
                    case 3:
                        realizarReserva();
                        break;
                    case 0:
                        System.out.println("¡Gracias por visitarnos! Esperamos verle pronto.");
                        break;
                    default:
                        System.out.println("Opción inválida. Solo disponibles: 1, 2, 3, 0");
                }
            } catch (InputMismatchException e) {
                System.err.println("Error: Debe ingresar un número válido.");
                scanner.nextLine(); // Limpiar buffer
            } catch (Exception e) {
                System.err.println("Error inesperado: " + e.getMessage());
            }
        } while (opcion != 0);
    }
    
    private static void mostrarTiposCabanias() {
        try {
            System.out.println("\n TIPOS DE CABAÑAS DISPONIBLES:");
            System.out.println("===================================================");
            
            for (int i = 0; i < tiposCabanias.size(); i++) {
                Cabania cabania = tiposCabanias.get(i);
                System.out.println((i + 1) + ". " + cabania.getTipoCabania().toUpperCase());
                System.out.println("Precio: $" + cabania.getPrecioPorNoche() + " por noche");
                System.out.println("Capacidad: " + cabania.getCapacidadMaxima() + " personas");
                System.out.println();
            }
        } catch (Exception e) {
            System.err.println("Error al mostrar tipos de cabañas: " + e.getMessage());
        }
    }
    
    private static void mostrarDetallesCompletos() {
        try {
            System.out.println("\nDETALLES COMPLETOS DE NUESTRAS CABAÑAS:");
            System.out.println("===================================================");
            
            for (int i = 0; i < tiposCabanias.size(); i++) {
                Cabania cabania = tiposCabanias.get(i);
                System.out.println("\n" + (i + 1) + ". CABAÑA " + cabania.getTipoCabania().toUpperCase());
                cabania.mostrarDetalles();
                System.out.println(cabania.getServicios());
                System.out.println("===================================================");
            }
        } catch (Exception e) {
            System.err.println("Error al mostrar detalles: " + e.getMessage());
        }
    }
    
    /* Procesa reserva del cliente con validaciones y manejo de excepciones */
    private static void realizarReserva() {
        try {
            System.out.println("\n REALIZAR RESERVA");
            System.out.println("===================================================");
            
            // Mostrar opciones disponibles
            System.out.println("Seleccione el tipo de cabaña que desea reservar:");
            for (int i = 0; i < tiposCabanias.size(); i++) {
                Cabania cabania = tiposCabanias.get(i);
                System.out.println((i + 1) + ". " + cabania.getTipoCabania() + " - $" + cabania.getPrecioPorNoche() + "/noche");
            }
            
            System.out.print("Ingrese su opción (1-" + tiposCabanias.size() + "): ");
            int opcionCabania = scanner.nextInt();
            scanner.nextLine();
            
            if (opcionCabania < 1 || opcionCabania > tiposCabanias.size()) {
                throw new IllegalArgumentException("Opción de cabaña inválida.");
            }
            
            Cabania cabaniaSeleccionada = tiposCabanias.get(opcionCabania - 1);
            
            // Solicitar datos del cliente usando métodos String
            System.out.print("Ingrese su nombre completo: ");
            String nombreCliente = scanner.nextLine().trim();
            
            if (nombreCliente.isEmpty() || nombreCliente.length() < 2) {
                throw new IllegalArgumentException("El nombre debe tener al menos 2 caracteres.");
            }
            
            System.out.print("Número de noches: ");
            int noches = scanner.nextInt();
            scanner.nextLine();
            
            if (noches <= 0) {
                throw new IllegalArgumentException("El número de noches debe ser positivo.");
            }
            
            double precioTotal = cabaniaSeleccionada.calcularPrecioTotal(noches);

            /*System.currentTimeMillis() Devuelve el tiempo actual en milisegundos
            *desde el 1 de enero de 1970 (Epoch). Es un número muy grande que cambia cada milisegundo*/
            
            String codigoReserva = "RES" + System.currentTimeMillis() % 10000;
            String detalleReserva = nombreCliente + " - " + cabaniaSeleccionada.getTipoCabania() + 
                                  " - " + noches + " noches - $" + precioTotal;
            
            reservasRealizadas.add(detalleReserva);
            
            // Mostrar confirmación
            System.out.println("\n ¡RESERVA CONFIRMADA!");
            System.out.println("===================================================");
            System.out.println("Código de reserva: " + codigoReserva);
            System.out.println("Cliente: " + nombreCliente.toUpperCase());
            System.out.println("Cabaña: " + cabaniaSeleccionada.getTipoCabania());
            System.out.println("Noches: " + noches);
            System.out.println("Precio total: $" + precioTotal);
            System.out.println("===================================================");
            System.out.println("¡Gracias por su reserva! Le esperamos pronto.");
            
        } catch (InputMismatchException e) {
            System.err.println("Error: Debe ingresar un número válido.");
            scanner.nextLine();
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        } catch (Exception e) {
            System.err.println("Error al procesar la reserva: " + e.getMessage());
        }
    }
}
