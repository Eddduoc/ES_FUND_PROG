
// S6_Teatro_Moro
//@edd11

import java.util.InputMismatchException;
import java.util.Scanner;

import java.util.Timer;
import java.util.TimerTask;


public class Exp2_S6_Eduardo_Stegmaier {

    //Definir Matricez, Arreglos y Variables

    // matriz de asientos // 0 = asiento libre, 1 = asiento comprado 
    public static int[][] matriz_entrada = { 
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, // VIP
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, // Platea alta
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, // Platea baja
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0} // General
    };
    

    // matriz de reserva / boleta
    public static int[][] matriz_boleta = { 
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0,0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, // Ubicación
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0,0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, // Asiento
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0,0, 0, 0, 0, 0, 0, 0, 0, 0, 0}  // Precio
    };
    
    // matriz de texto para mostrar en la consola
    public static final String[][] matriz_texto = { 
        {"VIP        ","Platea alta","Platea baja","General    ","","","","","",""}, // ubicacion
    };

    // arreglo de precios
    public static final int[] arreglo_precios = {20000, 15000, 12000, 10000};

    // copia de la matriz boleta
    public static int[][] copia_matriz_boleta = new int[matriz_boleta.length][matriz_boleta[0].length];


    public static int reservado_comprado = 0; // 0 = no reservado, 1 = reservado, 2 = comprado
    public static int opcion; // variable para opciones del menu principal
    private static Timer timer; // Variable estática para el Timer
    private static int error_asiento_ya_disponible=0; // error al modificar una venta existente

    public static void main(String[] args) {
        
        Scanner scanner = new Scanner(System.in);

        // ajustar tiempo para mantener la reserva 
        System.out.println("___________________________________________________________________");
        System.out.println(" Seleccionar tiempo de espera para mantener la reserva(en segundos)");
        System.out.println("___________________________________________________________________");
        int tiempo_espera=validacion(scanner, 1, 300);

        System.out.println("     _________________________");
        System.out.println("     Bienvenido al Teatro Moro");
        System.out.println("     _________________________\n");

        //bucle para el menu principal
        
        do {
            // Mostrar el menú principal
            System.out.println("          ______________\n          Menu principal \n          ______________\n" +
                "\nPrimero reserve su asiento para verificar disponibilidad. \nluego puede proceder al pago. \n" + "\nLa reserva se anulara despues de: " + tiempo_espera + " segundos. Comfirme compra antes de este tiempo.\n" +
                "\n1. Reservar entradas \n2. Comprar entradas" +
                "\n3. Ver asientos disponibles \n4. Modificar una venta existente \n5. Imprimir boleta \n6. Salir\n");

            opcion = validacion(scanner, 1, 6);

            switch (opcion) {
                case 1:
                    // Reservar entradas
                    print_asientos(scanner);
                    reservarAsiento(scanner);
                    
                    //Iniciar el cronómetro para la reserva
                    if (reservado_comprado == 1) {
                        iniciarCronometro(tiempo_espera); // 5 minutos
                    }

                    salir(scanner);
                    break;
                case 2:
                    // Comprar entradas
                    print_boleta(scanner);
                    comprar_entradas(scanner);

                    // Cancelar el cronómetro
                    if (reservado_comprado == 2) {
                        if (timer != null) {
                            timer.cancel();
                            timer = null;
                        }
                    }

                    salir(scanner);
                    break;
                case 3:
                    // Ver asientos disponibles
                    print_asientos(scanner);
                    
                    salir(scanner);
                    break;
                case 4:
                    // Modificar una venta existente
                    print_asientos(scanner);
                    borrar_venta(scanner); 
                    if (error_asiento_ya_disponible == 1) {
                        System.out.println("El asiento ya está disponible. No se puede modificar la venta.");
                        error_asiento_ya_disponible = 0; // Reiniciar el error
                        break;
                    }else {
                        System.out.println("Venta anterior cancelada, seleccione la nueva entrada: ");
                        reservarAsiento(scanner);
                        comprar_entradas(scanner);
                        if (reservado_comprado == 2) {
                            System.out.println("Venta modificada con éxito.\n");
                        }
                    }

                    salir(scanner);
                    break;
                case 5:
                    // Imprimir boleta
                    print_boleta(scanner);
                    if (reservado_comprado ==1) {
                        System.out.println("Proceder al pago? (1: Sí, 2: No)");
                        opcion = validacion(scanner, 1, 2);
                        if (opcion == 1) {
                            comprar_entradas(scanner);
                            salir(scanner);
                        } else {
                            salir(scanner);
                        }
                    } else {
                        salir(scanner);
                    }
                    
                    break;
                case 6:
                    // Salir
                    System.out.println("Gracias por su visita");
                    break;
            }
        } while (opcion != 6); // Salir del bucle si la opción es 6
        scanner.close();


    }
    
    // RESERVAR //

    // Método para reservar asientos
    private static void reservarAsiento(Scanner scanner) {

        boolean asiento_reservado=false;
        boolean salir = false;
        do {
            System.out.println("Ingrese la ubicacion (1-4): ");
            System.out.println("1. VIP \n2. Platea Alta \n3. Platea Baja \n4. General \n");
            int ubicacion = validacion(scanner, 1, 4);
            System.out.println("Ingrese el número del asiento (1-10): ");
            int asiento = validacion(scanner, 1, 10);

           //funcion para verificar si el asiento a estaba reservado
            for (int i = 0; i < matriz_boleta.length; i++) {
                if (matriz_boleta[0][i] == ubicacion && matriz_boleta[1][i] == asiento) {
                    asiento_reservado = true;
                }
            }

            // Verificar si el asiento está disponible antes de la reserva
            if (matriz_entrada[ubicacion-1][asiento-1] == 0 && asiento_reservado == false) {
                System.out.println("El precio del asiento es: $ " + arreglo_precios[ubicacion-1]);

                
                System.out.println("Asiento reservado con éxito.\n");

                int seguir = 1;
                for (int i = 0; i < matriz_boleta.length; i++) {  //asignar valores a la matriz de boleta
                    if (seguir==1 &&  matriz_boleta[0][i] == 0) {
                        matriz_boleta[0][i] = ubicacion;
                        matriz_boleta[1][i] = asiento;
                        matriz_boleta[2][i] = arreglo_precios[ubicacion-1];
                        seguir=0; 
                        break;
                    }
                }// Aquí va un Breakpoint - asegurando que la matriz se llene correctamente
                reservado_comprado = 1; // Cambiar el estado a reservado    
                salir=true; // Salir del bucle si la reserva es exitosa
            } else {
                System.out.println("El asiento ya está reservado/comprado. Por favor, elija otro.\n");
            }


        }while (!salir);
    }

    // Método para imprimir asientos en matriz entrada
    private static void print_asientos(Scanner scanner) {

        System.out.println("Asientos disponibles: \n");
        for (int i = -1; i <= 3; i++) {
            if (i==-1){
                System.out.print("                ");
                for (int k = 1; k <= 10; k++) {
                    System.out.print(" "+ k +"  ");
                }
            } else {
                System.out.print(  (i+1) + ". " +   matriz_texto[0][i]  +" / "  );
                for (int j = 0; j <= 9; j++) {
                    System.out.print((matriz_entrada[i][j] == 0 ? "O" : "X") + " / ");
                }
            }
            System.out.println();
        }
        System.out.println();
        System.out.println("X(no disponible)");
        System.out.println();
    }

    // Método para iniciar el cronómetro
    private static void iniciarCronometro(int segundos) {
        // Cancelar cualquier temporizador previo para evitar múltiples instancias
        if (timer != null) {
            timer.cancel();
        }
        timer = new Timer();
    
        // Crear una tarea para reiniciar la matriz_boleta
        TimerTask tarea = new TimerTask() {
            @Override
            public void run() {
                for (int i = 0; i < matriz_boleta.length; i++) {
                    for (int j = 0; j < matriz_boleta[0].length; j++) {
                        matriz_boleta[i][j] = 0;
                    }
                }
                // Actualizar estado de reservado_comprado
                reservado_comprado = 0;
                System.out.println("Tiempo de reserva expirado. La matriz boleta ha sido reiniciada.");
                
                // Cancelar el temporizador después de ejecutarse
                timer.cancel();
                timer = null;
            }
        };
        timer.schedule(tarea, segundos * 1000L);
    }


    // COMPRAR //
    // Método para comprar entradas
    private static void comprar_entradas(Scanner scanner) {
        if (reservado_comprado == 1) {
            System.out.println("¿Desea comprar las entradas? (1: Sí, 2: No)");  
            int pago = validacion(scanner, 1, 2);

                // Marcar el asiento como comprado (matriz entrada) 
            if (pago == 1) {
                System.out.println("Compra exitosa. Gracias por su compra.");
                for (int i = 0; i < matriz_boleta.length; i++) {
                    if (matriz_boleta[0][i] != 0) {
                        matriz_entrada[matriz_boleta[0][i]-1][matriz_boleta[1][i]-1] = 1;       
                    }
                }// Aquí va un Breakpoint - asegurando que la matriz se llene correctamente  
                // Agregar valores no vacíos de matriz_boleta a copia_matriz_boleta desde el primer espacio disponible
                for (int j = 0; j < matriz_boleta[0].length; j++) {
                    if (matriz_boleta[0][j] != 0) { 
                        for (int k = 0; k < copia_matriz_boleta[0].length; k++) {
                            if (copia_matriz_boleta[0][k] == 0) { 
                                for (int i = 0; i < matriz_boleta.length; i++) {
                                    copia_matriz_boleta[i][k] = matriz_boleta[i][j];
                                }
                                break;
                            }
                        }
                    }
                }//Aquí va un Breakpoint - asegurando que la copiade la matriz se realice correctamente

                // Limpiar la matriz después de la compra (matriz boleta)
                for (int i = 0; i < matriz_boleta.length; i++) {
                    if (matriz_boleta[0][i] != 0) {
                        matriz_boleta[0][i] = 0; 
                        matriz_boleta[1][i] = 0;
                        matriz_boleta[2][i] = 0;
                    }
                } //Aquí va un Breakpoint - asegurando que la matriz se limpie correctamente
                reservado_comprado = 2; // Cambiar el estado a comprado
                
            } else {
                System.out.println("Compra cancelada.\n");
            }
        }
    }    

    // Método para modificar una venta existente
    private static void borrar_venta(Scanner scanner) {
        // borra la venta
        System.out.println("Cual venta desea modificar?");
        System.out.println("Ingrese la ubicacion (1-4): ");
        System.out.println("1. VIP \n2. Platea Alta \n3. Platea Baja \n4. General \n");
        int ubicacion = validacion(scanner, 1, 4);
        System.out.println("Ingrese el número del asiento (1-10): ");
        int asiento = validacion(scanner, 1, 10);
        
        if(matriz_entrada[ubicacion-1][asiento-1] == 0) {
            error_asiento_ya_disponible = 1; // Asiento ya disponible
        }
        matriz_entrada[ubicacion-1][asiento-1] = 0; // Cambiar el estado del asiento a libre

        for (int i = 0; i < copia_matriz_boleta.length; i++) {
            if (copia_matriz_boleta[0][i] == ubicacion && copia_matriz_boleta[1][i] == asiento) {
                copia_matriz_boleta[0][i] = 0; // Cambiar el estado del asiento a libre
                copia_matriz_boleta[1][i] = 0;
                copia_matriz_boleta[2][i] = 0;
            }
        }
        
        // Llamar al método de reserva para seleccionar un nuevo asiento    
    }

    //   BOLETA   //  
    //calcular_total y print_matriz  > mostrar_resumen > print_boleta

    //calcular el total de la boleta
    public static int calcular_total(int[][] matriz_boleta) {
        int total = 0;
        for (int i = 0; i < matriz_boleta.length; i++) {
            if (matriz_boleta[0][i] != 0) {
                total += matriz_boleta[2][i];
            }
        }
        return total; 
    }

    //print matriz
    public static void print_matriz(int i,int[][] matriz) {
        if (matriz[0][i] != 0) {
            System.out.println(matriz_texto[0][matriz[0][i]-1] + "\t" + matriz[1][i] + "\t" + "$ "+matriz[2][i]);
        }
    }

    //mostrar el resumen de la matriz boleta
    public static void mostrar_resumen() {

        System.out.println ("Ubicación\tAsiento\tPrecio\n");
        for (int i = 0; i < matriz_boleta.length; i++) {
            if (reservado_comprado == 2) {
                print_matriz(i, copia_matriz_boleta);
            } else
            if (reservado_comprado == 1) {
                print_matriz(i,matriz_boleta);
            }
        }
        if (reservado_comprado == 2) {
            System.out.println("\nTotal:                  $ " + calcular_total(copia_matriz_boleta) );//Aquí va un Breakpoint - asegurando que el total se calcule correctamente
        } else {
            System.out.println("\nTotal:                  $ " + calcular_total(matriz_boleta) );//Aquí va un Breakpoint - asegurando que el total se calcule correctamente
        }
    }
    // Método para imprimir la boleta / Mostrar reserva
    private static void print_boleta(Scanner scanner) {

        if (reservado_comprado == 2) {
            System.out.println("Ya ha comprado entradas.\n");
            System.out.println("    ___________________");
            System.out.println("     Boleta de compra: ");
            System.out.println("    ___________________\n");
            mostrar_resumen();
        } else if (reservado_comprado == 1) {
            System.out.println("Aun no compra su reserva.\n");
            System.out.println("    Resumen de reserva: \n");
            mostrar_resumen();
        } else if (reservado_comprado == 0) {
            System.out.println("No hay reservas ni compras realizadas.\n");
        }
        
        
    }

    // EXTRA //
    // Método para validar la entrada del usuario
    private static int validacion(Scanner scanner, int min, int max) {
        int entrada = 0;
        boolean entradaValida = false;
        do {
            try {
                entrada = scanner.nextInt();
                if (entrada < min || entrada > max) {
                    System.out.println("Selección Incorrecta. Ingrese un valor válido (" + min + "-" + max + ")");
                } else {
                    entradaValida = true;
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Por favor, ingrese un número entero válido.");
                scanner.next();
            }
        } while (!entradaValida);
        return entrada;
    }    

    // Método para salir 
    private static void salir(Scanner scanner) {
        System.out.println("1. Volver al menu principal? \n2. Salir\n");
        int choice = validacion(scanner, 1, 2);
        if (choice == 2) {
            System.out.println("Gracias por su visita");
            //System.exit(0);
            opcion = 6; // Salir del bucle principal
        }
    }    
}

