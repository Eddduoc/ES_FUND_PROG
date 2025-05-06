import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.ArrayList;

public class TeatroMoro {

    //Crear Arreglos

    // matriz de asientos // 0 =libre, 1 = omprado 
    public static int[][] matriz_entrada = { 
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, // VIP
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, // Platea alta
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, // Platea baja
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0} // General
    };
    
    public static int[][] venta_cliente = new int[6][40];
    public static int venta_cliente_lenght = 40; // longitud y ancho de la matriz al ser fija
    public static int venta_cliente_width = 6; 
  
    // crear Arraylist
    public static ArrayList<int[]> lista_reservas = new ArrayList<>(); // lista de reservas // ( ubicacion, asiento, precio)
   
    public static ArrayList<Double> descuentos_promociones = new ArrayList<>();
    
    public static void descuentos() {
        descuentos_promociones.add(0.1); // Descuento del 10% para estudiantes menores de 18 años
        descuentos_promociones.add(0.15); // Descuento del 15% para personas mayores de 60 años   
        descuentos_promociones.add(0.20); // Descuento del 20% por compra de 3 entradas o más
    }


    // arreglo de texto, precios
    public static final String[] arreglo_texto = {"VIP        ","Platea alta","Platea baja","General    ",
                                                "ID Cliente   ","ID Venta     ","Ubicacion    ","Asiento      ","Precio       ","Edad         "}; // ubicacion
    
    public static final int[] arreglo_precios = {20000, 15000, 12000, 10000};


    //Variavles Estaticas
    public static int opcion; // variable para opciones del menu principal
    public static int id_venta = 0; 
    public static int id_cliente = 1; 
    public static int encontrado = 0; 

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        // Inicializar descuentos
        descuentos();

        System.out.println("     _________________________");
        System.out.println("     Bienvenido al Teatro Moro");
        System.out.println("     _________________________\n");

        //bucle para el menu principal
        int opcion;
        do {
            System.out.println(" ______________\n Menu principal \n ______________\n \n1. Comprar entradas \n2. Ver promociones y Descuentos" +
                    "\n3. Editar o Eliminar Ventas \n4. Buscar asientos disponibles \n5.Ver Historial de entradas Compradas \n6. Cambiar precios \n7. salir \n");

            opcion = validacion(scanner, 1, 7);

            switch (opcion) {
                case 1:
                    // Comprar entradas
                    print_asientos();
                    comprarEntradas(scanner);  //Falta
                    salir(scanner);
                    break;
                case 2:
                    // Ver y Editar promociones
                    verPromociones();
                    System.out.println("¿Desea editar las promociones? (1: Sí, 2: No)");
                    opcion = validacion(scanner, 1, 2);
                    if (opcion == 1) {
                        editarPromociones(scanner);
                    } else {
                        System.out.println("No se realizaron cambios en las promociones.");
                    }
                    salir(scanner);
                    break;
                case 3:
                    // Editar ventas
                    System.out.println("¿Que accion desea realizar?\n1. Editar Asiento \n2. Eliminar venta \n3. Cancelar");
                    opcion = validacion(scanner,1,3);
                    if (opcion == 1) {
                        print_venta_cliente();
                        System.out.println("Seleccione el ID de la venta a editar: ");
                        cambiarAsiento(scanner);
                        if (encontrado == 1) {
                            System.out.println("Venta editada con éxito.");
                        } else if (encontrado == 0) {
                            System.out.println("No se encontró la venta con ID: " + id_venta);
                        }
                        

                    } else if (opcion == 2){
                        print_venta_cliente();
                        System.out.println("Seleccione el ID de la venta a eliminar: ");

                        eliminarVenta(scanner);
                        if (encontrado == 1) {
                            System.out.println("Venta eliminada con éxito.");
                        } else if (encontrado == 0) {
                            System.out.println("No se encontró la venta con ID: " + id_venta);
                        }
                    }
                    salir(scanner);
                    break;
                case 4:
                    // Buscar entradas disponibles
                    print_asientos();
                    salir(scanner);
                    break;
                case 5:
                    //  Mostrar ventas
                    print_venta_cliente();
                    salir(scanner);
                    break;
                case 6:
                    // Cambiar precios
                    System.out.println("¿Desea cambiar los precios de las ubicaciones? (1: Sí, 2: No)");
                    opcion = validacion(scanner, 1, 2);
                    if (opcion == 1) {
                        cambiarPrecio(scanner);
                    } else {
                        System.out.println("No se realizaron cambios en los precios.");
                    }
                    break;
                case 7:
                    // Salir
                    System.out.println("Gracias por su visita");
                    break;
            }
        } while (opcion != 7);
        scanner.close();
    }


    // Método para imprimir asientos en matriz entrada 
    private static void print_asientos() {

        System.out.println("Asientos disponibles: \n");
        for (int i = -1; i <= 3; i++) {
            if (i==-1){
                System.out.print("                ");
                for (int k = 1; k <= 10; k++) {
                    System.out.print(" "+ k +"  ");
                }
            } else {
                System.out.print(  (i+1) + ". " +   arreglo_texto[i]  +" / "  );
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

    // Metodo para imprimir venta_cliente                
    private static void print_venta_cliente() {
        System.out.println("Venta cliente: \n");
        for (int i = 0; i < venta_cliente_width; i++) {
            
            System.out.print("- "+ arreglo_texto[i+4] + "/ ");

            for (int j = 0; j < venta_cliente_lenght; j++) {

                if (venta_cliente[i][j] == 0) {
                    continue; // Ignorar los valores cero
                }
                if (i == 4) {
                    System.out.print("/  "+venta_cliente[i][j] + "  /  ");
                } else {
                    System.out.print("/    "+venta_cliente[i][j] + "    /  ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }



    private static int ubicacion;
    private static int asiento;

    // Método para comprar entradas
    private static void comprarEntradas(Scanner scanner) {

        //Elegir y reservar asiento
        
        boolean reservado=false;
        int n_reservas;
        int precio_final;
        boolean salir = false;
        do {
            do {
                System.out.println("Ingrese la ubicacion (1-4): ");
                System.out.println("1. VIP \n2. Platea Alta \n3. Platea Baja \n4. General \n");
                ubicacion = validacion(scanner, 1, 4);
                System.out.println("Ingrese el número del asiento (1-10): ");
                asiento = validacion(scanner, 1, 10);

                
                reservado = lista_reservas.stream()                                                                     //(ARREGLAR)
                    .anyMatch(arreglo_reserva -> arreglo_reserva[0] == ubicacion && arreglo_reserva[1] == asiento);

                // Verificar si el asiento está disponible antes de la reserva
                if (matriz_entrada[ubicacion-1][asiento-1] == 0 && !reservado) {

                    System.out.println("Asiento Disponible.\n");

                    salir=true; // Salir del bucle si la reserva es exitosa
                }else if (matriz_entrada[ubicacion-1][asiento-1] == 1) {
                    System.out.println("El asiento ya está comprado. Por favor, elija otro.\n");
                }else if (reservado) {
                    System.out.println("El asiento ya está reservado. Por favor, elija otro.\n");
                }

            }while (!salir);   

            //Ingresar edad
            System.out.println("Ingrese su edad:  \n");
            int edad = validacion(scanner, 1, 100);
    
            // calcular descuento / precio base y precio final                   
            double descuento = (edad < 18 ? descuentos_promociones.get(0) : (edad >= 60 ? descuentos_promociones.get(1) : 0));      //(CAMBIAR)
            int precio_base = arreglo_precios[ubicacion - 1];
            precio_final = (int) (precio_base * (1 - descuento));
    
            //Agregar a Lista                                                (ACTUALIZAR)

            id_venta =id_venta + 1; // id venta
            int[] arreglo_compra = {ubicacion, asiento, precio_final,id_cliente,id_venta};
            lista_reservas.add(arreglo_compra);
            n_reservas= lista_reservas.size();
          

            //agregar datos cliente                                                (ACTUALIZAR)
            venta_cliente[0][id_venta-1] =id_cliente; //id cliente
            venta_cliente[1][id_venta-1] =id_venta; //id venta
            venta_cliente[2][id_venta-1] =ubicacion;
            venta_cliente[3][id_venta-1] =asiento;
            venta_cliente[4][id_venta-1] =precio_final;
            venta_cliente[5][id_venta-1] =edad;

            // Resumen de la compra                                            
            System.out.println("     _______________________");
            System.out.println("             Resumen ");
            System.out.println("     _______________________\n");
            System.out.println(" *Entrada " + "Fila: " + arreglo_texto[ubicacion - 1] + "  Asiento: " + asiento  + " Edad: " + edad + " años \n");
            System.out.println("_______________________________________________________");
            System.out.print(" Precio base  : $ " + precio_base);
            System.out.println(" con un descuento de " + descuento * 100
                    + "% " + (edad < 18 ? "(Estudiante) " : (edad >= 60 ? "(Tercera edad) " : "(no aplica) ")) );

            System.out.println(" Precio final : $ " + precio_final );
            System.out.println("_______________________________________________________");

            System.out.println("Entrada reservada con exito\n ");
            System.out.println("¿Desea agregar mas entradas? (1: Sí, 2: No)");  
            int seguir = validacion(scanner, 1, 2);
            if (seguir == 2) {
                salir = true;
                id_cliente++; // Incrementar el ID del cliente para la siguiente compra
            } else {
                salir = false;
            }

            }while (!salir);


        // Mostrar resumen de la compra
        

        System.out.println("     _______________________");
        System.out.println("         Resumen de Compra ");
        System.out.println("     _______________________\n");

        // calcular el total de lista reservas
        int total = 0;
        int total_descuento = 0; // Total con descuento
        for (int i = 0; i < n_reservas; i++) {
            int[] arreglo_reserva = lista_reservas.get(i);

            int precio_singular = arreglo_reserva[2]; // Precio
            total += precio_singular; // Sumar el precio al total
        }
        if (n_reservas >= 3) {
            total_descuento = (int) (total - (total * descuentos_promociones.get(2))); // Descuento del 20% por compra de 3 entradas o más
            
        }
        //print lista reservas 
        for (int i = 0; i < n_reservas; i++) {
            int[] arreglo_reserva = lista_reservas.get(i);
            System.out.println(" -Entrada "+ String.valueOf(i+1) +" Fila: " + arreglo_texto[arreglo_reserva[0]-1] + "  Asiento: " + arreglo_reserva[1] + "  Precio: $ " + arreglo_reserva[2]);
        }
        //print total
        System.out.println("_______________________________________________________");
        System.out.println(" Total de la compra: $                     " + total + "\n");
        if (n_reservas >= 3) {
            System.out.println(" con un descuento de " + ( 20 ) + "% por compra de 3 entradas o más\n");
            System.out.println(" Total a pagar: $                          " + total_descuento);
        } 
        System.out.println("_______________________________________________________");


        n_reservas= lista_reservas.size();

        System.out.println("¿Desea comprar las entradas? (1: Sí, 2: No)");  
        int pago = validacion(scanner, 1, 2);


        if (pago == 1) {
            System.out.println("Compra exitosa. Gracias por su compra.\n");

            // marcar el asiento como comprado (matriz entrada)
            for (int i = 0; i < n_reservas; i++) {

                int[] arreglo_reserva = lista_reservas.get(i);

                matriz_entrada[arreglo_reserva[0]-1][arreglo_reserva[1]-1] = 1; // Marcar el asiento como comprado
            }
            // Limpiar la lista después de la compra (matriz boleta)
            lista_reservas.clear(); // Limpiar la lista de reservas
                
        } else {
            System.out.println("Compra cancelada.\n");
        }
    }    

    // Metodo para ver promociones
    private static void verPromociones() {

        System.out.println("Promociones disponibles: \n");
        System.out.println("1. Descuento del " + descuentos_promociones.get(0)*100 + " % para estudiantes menores de 18 años.");
        System.out.println("2. Descuento del " + descuentos_promociones.get(1)*100 + " % para personas mayores de 60 años.");
        System.out.println("3. Descuento del " + descuentos_promociones.get(2)*100 + " % por compra de 3 entradas o más.\n");
    }

    //Metodo para editar promociones
    private static void editarPromociones(Scanner scanner) {
        System.out.println("ingrese el nuevo descuento (0-100): ");
        int nuevo_descuento = validacion(scanner, 0, 100);
        System.out.println("ingrese el nuevo id de la promocion: ");
        int id_promocion = validacion(scanner, 1, 3);
        if (id_promocion == 1) {
            descuentos_promociones.set(0, nuevo_descuento / 100.0); // Actualizar el descuento del 10%
        } else if (id_promocion == 2) {
            descuentos_promociones.set(1, nuevo_descuento / 100.0); // Actualizar el descuento del 15%
        } else if (id_promocion == 3) {
            descuentos_promociones.set(2, nuevo_descuento / 100.0); // Actualizar el descuento del 20%
        }
        System.out.println("Descuento actualizado con éxito. \n");
    }
    
    //Metodo para eliminar ventas
    private static void eliminarVenta(Scanner scanner) {

        int id = validacion(scanner, 1, venta_cliente_lenght); // Validar el ID de la venta a eliminar
        verificarIdVenta(scanner,id);

        if (encontrado == 1) {
            for (int i = 0; i < venta_cliente_lenght; i++) {
                if (venta_cliente[1][i] == id) {

                    // Eliminar la venta del arreglo venta_cliente
                    for (int j = i; j < venta_cliente_lenght; j++) {
                        if (j<venta_cliente_lenght-1) {
                            for (int k = 0; k < venta_cliente_width; k++) {
                                venta_cliente[k][j] = venta_cliente[k][j + 1];
                            }
                        } else {
                            for (int k = 0; k < venta_cliente_width; k++) {
                                venta_cliente[k][j] = 0; // Limpiar el último elemento
                            }
                        }
                    }
                    //Eliminar de la matriz_entrada
                    matriz_entrada[venta_cliente[2][i]-1][venta_cliente[3][i]-1] = 0; // Marcar el asiento como libre
                }
            }
            //System.out.println("Venta eliminada con éxito.");
        }
            
    }

    //Metodo para cambiar asientos
    private static void cambiarAsiento(Scanner scanner) {

        int id = validacion(scanner, 1, venta_cliente_lenght); 
        verificarIdVenta(scanner,id);
        
        if (encontrado == 1){
            boolean disponible=false;
            int nueva_ubicacion;
            int nuevo_asiento;
            do {
                System.out.println("Ingrese la nueva ubicación (1-4): ");
                nueva_ubicacion = validacion(scanner, 1, 4);
                System.out.println("Ingrese el nuevo número del asiento (1-10): ");
                nuevo_asiento = validacion(scanner, 1, 10);
        
                disponible = verificarAsiento(nueva_ubicacion, nuevo_asiento); // Verificar si el asiento está ocupado

            }while(!disponible);
            
            //Ingresar edad
            System.out.println("Ingrese su edad:  \n");
            int edad = validacion(scanner, 1, 100);
    
            // calcular descuento / precio base y precio final                   
            double descuento = (edad < 18 ? descuentos_promociones.get(0) : (edad >= 60 ? descuentos_promociones.get(1) : 0));      //(CAMBIAR)
            int precio_base = arreglo_precios[ubicacion - 1];
            int precio_final = (int) (precio_base * (1 - descuento));

            for (int i = 0; i < venta_cliente_lenght; i++) {
                if (venta_cliente[1][i] == id_venta) {
                    // Cambiar la ubicación y el asiento
                    venta_cliente[i][2] = nueva_ubicacion;
                    venta_cliente[i][3] = nuevo_asiento;
                    venta_cliente[i][4] = precio_final; 
                    venta_cliente[i][5] = edad; // Actualizar la edad
                    break;
                }
            }

        }





    }


    //Metodo para cambiar precios (Aun no implementado)
    private static void cambiarPrecio(Scanner scanner) {
        System.out.println("Ingrese el nuevo precio: ");
        int nuevo_precio = validacion(scanner, 0, 100000);
        System.out.println("Ingrese el id de la ubicacion a cambiar: \n1. VIP \n2. Platea Alta \n3. Platea Baja \n4. General \n");
        int id_ubicacion = validacion(scanner, 1, 4);
        arreglo_precios[id_ubicacion - 1] = nuevo_precio; // Actualizar el precio de la ubicación
        System.out.println("Precio actualizado con éxito.");
    }

    //Metodo para verificar que el asiento no este vendido
    private static Boolean verificarAsiento(int ubicacion, int asiento) {
        if (matriz_entrada[ubicacion - 1][asiento - 1] == 0) {
            System.out.println("Asiento disponible.");
        } else {
            System.out.println("El asiento ya está vendido. Por favor, elija otro.");
        }
        return matriz_entrada[ubicacion - 1][asiento - 1] == 0; // Retorna true si el asiento está disponible
    }


    //Metodo para verificar id de venta a editar o eliminar
    private static void verificarIdVenta(Scanner scanner, int id ){

        encontrado = 0; //Reiniciar
        for (int i = 0; i < venta_cliente_lenght; i++) {
            if (venta_cliente[1][i] == id) {
                encontrado = 1; // indicar que se encontró la venta
                break;
            }
        }
    }    

    // Método para validar la entrada del usuario (LISTO)
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

    // Método para salir  (LISTO)
    private static void salir(Scanner scanner) {
        System.out.println("1. Volver al menu principal? \n2. Salir\n");
        int choice = validacion(scanner, 1, 2);
        if (choice == 2) {
            System.out.println("Gracias por su visita");
            //System.exit(0);
            opcion = 7; // Salir del bucle principal
        }
    }  

}