import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.ArrayList;

public class ETF_S9_Eduardo_Stegmaier {

    //Crear Arreglos / Listas

    // matriz de asientos // 0 =libre, 1 = comprado                                                  
    public static int[][] matriz_entrada = { 
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, // VIP
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, // Palco
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, // Platea alta
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, // Platea baja
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0} // Galeria
    };
    public static final int[] arreglo_precios = {25000, 20000, 15000, 12000, 10000};

    public static double[] descuentos = {0.10, 0.15, 0.20, 0.25}; // lista de descuentos
    //  Orden de < (10% niño , 15% estudiante, 20% mujer, 25% tercera edad)

    public static ArrayList<int[]> lista_ventas = new ArrayList<>(); // lista de ventas 
    public static int n_lista_ventas = 0; // ( id_cliente, id_venta, ubicacion, asiento, precio final, edad, sexo)

    public static ArrayList<int[]> lista_reservas = new ArrayList<>(); // lista de reservas 
    public static int n_reservas = 0; // 
   

    public static final String[] arreglo_texto = {"VIP        ","Palco      ","Platea alta","Platea baja","Galeria    ",
         "ID Cliente   ","ID Venta     ","Ubicacion    ","Asiento      ","Precio       ","Edad         "}; // arreglo de texto
    

    //Variables Estaticas
    public static int id_venta = 0; 
    public static int id_cliente = 1; 
    public static int encontrado = 0; 

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("     _________________________ \n     Bienvenido al Teatro Moro \n     _________________________ \n");

        //bucle para el menu principal
        int opcion;
        do {
            System.out.println("         ______________\n         Menu principal \n         ______________\n \nSeleccione una opcion: \n1. Comprar entradas \n2. Ver Descuentos" +
                    "\n3. Editar o Eliminar Ventas \n4. Buscar asientos disponibles \n5.Ver Historial de entradas Compradas \n6. Cambiar precios \n7. salir \n");

            opcion = validacion(scanner, 1, 7);

            switch (opcion) {
                case 1:
                    // Comprar entradas
                    print_asientos();
                    comprarEntradas(scanner); 
                    salir(scanner,opcion);
                    break;
                case 2:
                    // Ver y Editar promociones
                    verPromociones();
                    
                    salir(scanner,opcion);
                    break;
                case 3:
                    int id;
                    // Editar ventas
                    System.out.println("¿Que accion desea realizar?\n1. Editar Asiento \n2. Eliminar venta \n3. Cancelar");
                    opcion = validacion(scanner,1,3);
                    if (opcion == 1) {
                        print_venta_cliente();
                        System.out.println("Seleccione el ID de la venta a editar: ");
                        id = validacion(scanner, 1, n_lista_ventas);
                        cambiarAsiento(scanner,id);
                    } else if (opcion == 2){
                        print_venta_cliente();
                        System.out.println("Seleccione el ID de la venta a eliminar: ");
                        id = validacion(scanner, 1, n_lista_ventas);
                        eliminarVenta(scanner,id);
                    }
                    salir(scanner,opcion);
                    break;
                case 4:
                    // Buscar entradas disponibles
                    print_asientos();
                    salir(scanner,opcion);
                    break;
                case 5:
                    //  Mostrar ventas
                    System.out.println("Seleccione una opción: \n1. Mostrar todas las ventas \n2. Mostrar Boleta por cliente \n3. Cancelar");
                    opcion = validacion(scanner, 1, 3);
                    if (opcion==1){
                        print_venta_cliente();
                    } else if (opcion==2){
                        System.out.println("Seleccione el ID del cliente: ");
                        id = validacion(scanner, 0, id_cliente-1);
                        mostrarResumenCompraCliente(id,lista_ventas);;
                    }
                    salir(scanner,opcion);
                    break;
                case 6:
                    // Cambiar precios
                    cambiarPrecio(scanner);
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
        for (int i = -1; i <= 4; i++) {
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
        
        n_lista_ventas = lista_ventas.size(); 

        int total = 0;
        System.out.println("Historial de ventas: \n");
        for (int i = 0; i < 6; i++) {           
            System.out.print("- "+ arreglo_texto[i+5] + "/ ");
            for (int j = 0; j < n_lista_ventas; j++) {
                int[] arreglo_lista_ventas = lista_ventas.get(j);  
                System.out.print("/  "+ arreglo_lista_ventas[i] + "  /  ");
            }
            System.out.println();
        }
        System.out.println();
        for (int i=0;i<n_lista_ventas;i++){
            int[] arreglo_lista_ventas = lista_ventas.get(i);
            total= total + arreglo_lista_ventas[4] ;
        }

        // Imprimir el total de asientos vendidos
        System.out.println("Asientos vendidos: " + n_lista_ventas);
        System.out.println("Total de compras:  $ " + total );
              
    }

    // Metodo para mostrar boleta por cliente
    public static void mostrarResumenCompraCliente(int idCliente, ArrayList<int[]> lista) {
        System.out.println("     _______________________ \n         Resumen de Compra \n     _______________________\n");

        int total = 0;
        int contador = 0;
        for (int[] venta : lista) {
            if (venta[0] == idCliente) { // índice 0 es id_cliente
                int ubicacion = venta[2];
                int asiento = venta[3];
                int precioFinal = venta[4];

                total += precioFinal;

                System.out.println(" - Entrada " + (contador + 1) +
                    " | Ubicación: " + arreglo_texto[ubicacion - 1] +
                    " | Asiento: " + asiento +
                    " | Precio final: $ " + precioFinal);

                contador++;
            }
        }
        if (contador == 0) {
            System.out.println("No se encontraron compras para el cliente con ID: " + idCliente);
        } else {
            System.out.println("_______________________________________________________");
            System.out.println(" Total de la compra: $                     " + total);
            System.out.println("_______________________________________________________");
        }
    }


    // Método para comprar entradas
    private static void comprarEntradas(Scanner scanner) {
        int ubicacion;    
        int asiento;
        //Elegir y reservar asiento
        int precio_final;
        boolean salir = false;
        do {
            do {
                System.out.println("Ingrese la ubicacion (1-5): ");
                System.out.println("1. VIP \n2. Palco \n3. Platea Alta \n4. Platea Baja \n5. Galeria \n");
                ubicacion = validacion(scanner, 1, 5);
                System.out.println("Ingrese el número del asiento (1-10): ");
                asiento = validacion(scanner, 1, 10);
            
                boolean comprado = verificarAsiento(ubicacion, asiento); // Verificar si el asiento está ocupado
                boolean reservado = verificarReserva(ubicacion, asiento); // Verificar si el asiento ya está reservado 

                if (!comprado && !reservado) {
                    System.out.println("Asiento Disponible.\n");
                    salir = true; // Salir del bucle si la reserva es válida
                } else if (comprado) {
                    System.out.println("El asiento ya está comprado. Por favor, elija otro.\n");
                } else if (reservado) {
                    System.out.println("El asiento ya está reservado en esta transacción. Por favor, elija otro.\n");
                }
            } while (!salir);  

            //Ingresar edad
            System.out.println("Ingrese su edad:  \n");
            int edad = validacion(scanner, 1, 100);

            System.out.println("Ingrese su sexo:  \n0. Mujer  \n1. Hombre\n");
            int sexo = validacion(scanner, 0, 1);

    
            // Calcular Descuento                 
            double descuento_aplicado = 0;      
            boolean[]condicion_descuento = {edad <= 10, edad <= 18 ,sexo == 0, edad >= 75};//(10% niño , 15% estudiante, 20% mujer, 25% tercera edad) Mayor descuento reemplaza menor descuento
            String[] arreglo_texto_descuento = {"10% niño", "15% estudiante", "20% mujer", "25% tercera edad"};
            String texto_descuento = "";
            for (int i=0; i<4; i++){
                if (condicion_descuento[i]){
                    descuento_aplicado = descuentos[i];
                    texto_descuento = arreglo_texto_descuento[i];
                }
            }

            // Precio final
            int precio_base = arreglo_precios[ubicacion - 1];
            precio_final = (int) (precio_base * (1 - descuento_aplicado));
                                              
            id_venta =id_venta + 1; // id venta
            agregarInfo(ubicacion, asiento, precio_final, edad, sexo, id_cliente, id_venta); // Agregar a lista de reservas y ventas

            /////////////////////////////////
            // Resumen de la compra                                            
            System.out.println("     _______________________ \n             Resumen  \n     _______________________\n");
            System.out.println(" *Entrada " + "Ubicacion: " + arreglo_texto[ubicacion - 1] + "  Asiento: " + asiento  + " Edad: " + edad + " años ");
            System.out.println("_______________________________________________________");
            System.out.print(" Precio base  : $ " + precio_base);
            System.out.println(" con un descuento de " + texto_descuento );
            System.out.println(" Precio final : $ " + precio_final  );
            System.out.println("_______________________________________________________");

            System.out.println("Entrada reservada con exito\n ");
            System.out.println("¿Desea agregar mas entradas? (1: Sí, 2: No)");  
            int seguir = validacion(scanner, 1, 2);
            if (seguir == 2) {
                salir = true;
            } else {
                salir = false;
            }

            }while (!salir);


        // Mostrar resumen de la compra
        mostrarResumenCompraCliente(id_cliente, lista_reservas);


        n_reservas= lista_reservas.size();

        System.out.println("¿Desea comprar las entradas? (1: Sí, 2: No)");  
        int pago = validacion(scanner, 1, 2);


        if (pago == 1) {
            System.out.println("Compra exitosa. Gracias por su compra.\n");

            // marcar el asiento como comprado (matriz entrada)
            for (int i = 0; i < n_reservas; i++) {

                int[] arreglo_reserva = lista_reservas.get(i);

                matriz_entrada[arreglo_reserva[2]-1][arreglo_reserva[3]-1] = 1; // Marcar el asiento como comprado
                
                int[] arreglo_lista_ventas = {arreglo_reserva[0], arreglo_reserva[1], arreglo_reserva[2], arreglo_reserva[3], arreglo_reserva[4], arreglo_reserva[5], arreglo_reserva[6]};
                lista_ventas.add(arreglo_lista_ventas); //agregar ventas a la lista

            }
            n_lista_ventas = lista_ventas.size(); // Actualizar el tamaño
            lista_reservas.clear(); // Limpiar la lista de reservas
            id_cliente++; // Incrementar el ID del cliente para la siguiente compra
                
        } else {
            lista_reservas.clear();
             // Eliminar la venta
            System.out.println("Compra cancelada.\n");
        }
    }    


    /////Verificaciones de entradas/////
    // Disponibilidad de asiento en Reserva
    private static boolean verificarReserva(int ubicacion, int asiento) {
        if (lista_reservas.size() > 0) {
            for (int i = 0; i < n_reservas; i++) {
                int[] arreglo_reserva = lista_reservas.get(i);
                if (arreglo_reserva[0] == ubicacion && arreglo_reserva[1] == asiento) {
                    return true; // El asiento ya está reservado
                }
            }
        }    
        return false; 
    }

    // Disponibilidad de asiento en Ventas
    private static Boolean verificarAsiento(int ubicacion, int asiento) {
        return matriz_entrada[ubicacion - 1][asiento - 1] == 1; // Retorna true si el asiento está ocupado
    }

    //verificar si existe id a editar o eliminar
    private static void verificarIdVenta(Scanner scanner, int id ){
        encontrado = 0; //Reiniciar
        for (int i = 0; i < n_lista_ventas; i++) {
            int[] arreglo_lista_ventas = lista_ventas.get(i);
            if (arreglo_lista_ventas[1] == id) {
                encontrado = 1; // indicar que se encontró la venta
                break;
            }
        }
    }    
    /////                         /////

    // Metodo para agregar info a las listas de ventas y reservas
    private static void agregarInfo(int ubicacion, int asiento, int precio_final, int edad, int sexo,int id_cliente, int id_venta) {
        int[] arreglo_reservas = {id_cliente, id_venta, ubicacion, asiento, precio_final, edad, sexo};
        //int [] arreglo_lista_ventas = {id_cliente, id_venta, ubicacion, asiento, precio_final, edad, sexo};

        lista_reservas.add(arreglo_reservas);
        //lista_ventas.add(arreglo_lista_ventas);

        n_reservas= lista_reservas.size();
        //n_lista_ventas = lista_ventas.size();
    }

    // Metodo para ver promociones
    private static void verPromociones() {
        System.out.println("Promociones disponibles: (se aplica mayor descuento)\n \n1. Descuento del 10% para niños menores de 10 años."+
            "\n2. Descuento del 15% para estudiantes menores de 18 años. \n3. Descuento del 20% para mujeres. \n4. Descuento del 25% para personas mayores de 75 años. \n");
    }

    //Metodo para eliminar ventas
    private static void eliminarVenta(Scanner scanner, int id) {

        verificarIdVenta(scanner,id);
        if (encontrado == 1) {
            for (int i = 0; i < n_lista_ventas; i++) {

                int[] arreglo_lista_ventas = lista_ventas.get(i);
                if (arreglo_lista_ventas[1] == id) {
                    //Eliminar de la lista de ventas
                    lista_ventas.remove(i);
                    n_lista_ventas = lista_ventas.size(); // Actualizar el tamaño de la lista de ventas

                    matriz_entrada[arreglo_lista_ventas[2]-1][arreglo_lista_ventas[3]-1] = 0; // Marcar el asiento como libre

                }
            }
            System.out.println("Venta eliminada con éxito.");
        }else {
            System.out.println("No se encontró la venta con este ID");
        }
    }

    //Metodo para cambiar asientos
    private static void cambiarAsiento(Scanner scanner, int id ) {
        verificarIdVenta(scanner,id);
        int indice=0;
        
        if (encontrado == 1){
            boolean ocupado=false;
            int nueva_ubicacion;
            int nuevo_asiento;
            do {
                System.out.println("Ingrese la nueva ubicación (1-5): ");
                System.out.println("1. VIP \n2. Palco \n3. Platea Alta \n4. Platea Baja \n5. Galeria \n");
                nueva_ubicacion = validacion(scanner, 1, 5);
                System.out.println("Ingrese el nuevo número del asiento (1-10): ");
                nuevo_asiento = validacion(scanner, 1, 10);
        
                ocupado = verificarAsiento(nueva_ubicacion, nuevo_asiento); // Verificar si el asiento está ocupado

            }while(ocupado);
            
            //Ingresar edad
            System.out.println("Ingrese su edad:  \n");
            int nueva_edad = validacion(scanner, 1, 100);
    

            System.out.println("Ingrese su sexo:  \n0. Mujer  \n1. Hombre\n");
            int nuevo_sexo = validacion(scanner, 0, 1);

    
            // Calcular Descuento                 
            double descuento_aplicado = 0;      
            boolean[]condicion_descuento = {nueva_edad <= 10, nueva_edad <= 18 ,nuevo_sexo == 0, nueva_edad >= 75};

            for (int i=0; i<4; i++){
                if (condicion_descuento[i]){
                    descuento_aplicado = descuentos[i];
                }
            }

            // Precio final
            int precio_base = arreglo_precios[nueva_ubicacion - 1];
            int nuevo_precio_final = (int) (precio_base * (1 - descuento_aplicado));
            int[] arreglo_lista_ventas;
            

            for (int i = 0; i < n_lista_ventas; i++) {
                arreglo_lista_ventas = lista_ventas.get(i);
                if (arreglo_lista_ventas[1] == id) {
                    indice=i;
                    matriz_entrada[arreglo_lista_ventas[2]-1][arreglo_lista_ventas[3]-1] = 0;

                }
            }

            arreglo_lista_ventas= lista_ventas.get(indice);

            int[] arreglo_nuevo = {arreglo_lista_ventas[0],arreglo_lista_ventas[1], nueva_ubicacion, nuevo_asiento, nuevo_precio_final, nueva_edad, nuevo_sexo};

            lista_ventas.set(indice, arreglo_nuevo); // Actualizar la lista de ventas

            matriz_entrada[nueva_ubicacion-1][nuevo_asiento-1] = 1;

            System.out.println("Venta editada con éxito.");
        } else {
            System.out.println("No se encontró la venta con este ID");
        }





    }


    //Metodo para cambiar precios 
    private static void cambiarPrecio(Scanner scanner) {
        System.out.println("Ingrese el nuevo precio: ");
        int nuevo_precio = validacion(scanner, 0, 100000);
        System.out.println("Ingrese el id de la ubicacion a cambiar: \n1. VIP \n2. Palco \n2. Platea Alta \n3. Platea Baja \n4. General \n");
        int id_ubicacion = validacion(scanner, 1, 5);
        arreglo_precios[id_ubicacion - 1] = nuevo_precio; // Actualizar el precio de la ubicación
        System.out.println("Precio actualizado con éxito.");
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

    // Método para salir 
    private static int salir(Scanner scanner,int opcion) {
        System.out.println("1. Volver al menu principal? \n2. Salir\n");
        int choice = validacion(scanner, 1, 2);
        if (choice == 2) {
            System.out.println("Gracias por su visita");
            
            opcion = 7; // Salir del bucle principal
        }else {
            System.out.println("Volviendo al menu principal...");
        }
        return opcion;
    }  

}