import java.util.InputMismatchException;
import java.util.Scanner;

public class Exp2_S4_Eduardo_Stegmaier {

    //Creacion de Arreglos// Usados en el método print_asientos y main
    static int[] fila_a = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    static int[] fila_b = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    static int[] fila_c = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    static int[] fila_d = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};


    public static void main(String[] args) {

        // (Entrada) Definición  variables y arreglos
        Scanner scanner = new Scanner(System.in);
        int opcion; int asiento; int fila; double descuento=0; int edad=0; boolean valido;

        String[] fila_texto= {"A","B","C","D"};
        String[] asientos_texto= {"1","2","3","4","5","6","7","8","9","10"};
        
        //////////
        // Bucle para el menú principal // do while
        do {

            // Menu principal//

            // (Entrada) Validación opciones // 1. Comprar entradas  2. Salir
            print_menu();
            
            opcion=validacion(scanner, 1, 2);

            if (opcion == 1) {
                print_asientos(scanner, fila_texto, asientos_texto);
            } else {
                System.out.println("Gracias por su visita");
                break; // Salir del bucle si la opción es 2
            }
            
            // (Entrada) ingreso/Validación de fila/asiento
            // bucle para validar el asiento seleccionado
            do {
                valido=false;
                System.out.println("Ingrese la fila seleccionada (1-4):  \n");
                fila=validacion(scanner, 1, 4);
        
                System.out.println("Ingrese el número de asiento (1-10):  \n");
                asiento=validacion(scanner, 1, 10);

                //Validar si el asiento está ocupado
                if (fila==1 && fila_a[asiento-1]==1) {
                    System.out.println("Asiento no disponible, seleccione otro asiento.");
                } else if (fila==2 && fila_b[asiento-1]==1) {
                    System.out.println("Asiento no disponible, seleccione otro asiento.");
                } else if (fila==3 && fila_c[asiento-1]==1) {
                    System.out.println("Asiento no disponible, seleccione otro asiento.");
                } else if (fila==4 && fila_d[asiento-1]==1) {
                    System.out.println("Asiento no disponible, seleccione otro asiento.");
                } else {
                    valido=true; // Salir del bucle si el asiento está disponible
                }
            } while (!valido); 
            
            //(ENTRADA) Ingresar/Validar edad
            System.out.println("Ingrese su edad:  \n");
            edad=validacion(scanner, 1, 100);
            

            // PROCESO //

            //(PROCESO) calcular descuento
            descuento= edad < 18 ? 0.1 : (edad >= 60 ? 0.15 : 0);

            //(PROCESO) Calcular precio base y precio final         
            int[] precios = {20000, 15000, 12000, 10000};
            int precio_base = precios[fila - 1];
 
            int precio_final = (int) (precio_base * (1 - descuento));


            // SALIDA //

            // (SALIDA) Mostrar Resumen de compra 
            //ubicacion, precio base, descuento aplicado, precio final

            System.out.println("     _______________________");
            System.out.println("        Resumen de compra");
            System.out.println("     _______________________\n");
            System.out.println("1 Entrada: " + "Fila:" + fila_texto[fila-1] + "  Asiento:" + asientos_texto[asiento-1] + "\n");
        
            System.out.println("Edad: " + edad + " años \n" );
            System.out.println("Precio Base: $ " + precio_base + " con " + Math.floor(descuento*100) 
            + "% de Descuento "+ (edad < 18 ? "(Estudiante) " : (edad >= 60 ? "(Tercera edad) " : "(no aplica) ")) + "\nValor Final: $ " + precio_final + "\n" );
            

            // (SALIDA) Mostrar mensaje de confirmación y opciones de salir o volver/comprar otra entrada
            // (Entrada) Validación opciones

            System.out.println("¿Desea confirmar la compra? \n1. Si \n2. No \n");
            opcion=validacion(scanner, 1, 2);
            if (opcion == 2) {
                System.out.println("¿Volver al menu principal? \n1. Si \n2. No \n");
            } else {
                //Actualizar el arreglo de asientos ocupados
                if (fila==1) {
                    fila_a[asiento-1]=1;
                } else if (fila==2) {
                    fila_b[asiento-1]=1;
                } else if (fila==3) {
                    fila_c[asiento-1]=1;
                } else if (fila==4) {
                    fila_d[asiento-1]=1;
                }
                System.out.println("Gracias por su compra, disfrute la función!");
                System.out.println("¿Desea comprar otra entrada? \n1. Si \n2. No \n");
            }
            
            // Validar si el usuario desea volver al menú principal o salir
            // (Entrada) Validación opciones
            // (SALIDA) mostrar mensaje final
            opcion=validacion(scanner, 1, 2);
            
            if (opcion == 2) {
                System.out.println("Gracias por su visita");
            } else {
                System.out.println("Volviendo al menu principal...");
            }

        } while (opcion == 1);

    }
    
    // Método para imprimir los asientos disponibles
    private static void print_asientos(Scanner scanner, String[] fila_texto, String[] asientos_texto) {
        
        System.out.println("Seleccione el asiento que desea comprar \n");
        for (int i = 0; i <= 3; i++) {
            // Imprimir fila
               System.out.print(i+1 + ". " +"Fila " + fila_texto[i] + ":  ");
               int fila_disponible = 0;

            // Imprimir asientos 
            for (int j = 0; j <= 9; j++) {
                if (i == 0) {
                    fila_disponible = fila_a[j];
                } else if (i == 1) {
                    fila_disponible = fila_b[j];
                } else if (i == 2) {
                    fila_disponible = fila_c[j];
                } else if (i == 3) {
                    fila_disponible = fila_d[j];
                }
                // Imprimir X si el valor de fila_disponible es 1 (ocupado) o espacio si es 0 (disponible)
                System.out.print((fila_disponible==0?"  ":"X ") + fila_texto[i] + asientos_texto[j] + " / ");
            }
            System.out.println();
        }
        System.out.println();
        System.out.println("X(no disponible)");
        System.out.println();
    }

    // Método para imprimir el menú de opciones
    private static void print_menu() {

        System.out.println("     _______________________");
        System.out.println("    Bienvenidos al Teatro Moro");
        System.out.println("     _______________________");
        System.out.println("\nSeleccione una opción \n1. Comprar entradas  \n2. Salir \n");
    }

    // Método para validar la entrada del usuario
    private static int validacion(Scanner scanner, int min, int max) {
        int entrada = 0; 
        boolean entradaValida = false;
        do {
            try {
                entrada = scanner.nextInt(); // Intentamos leer un entero
                if (entrada < min || entrada > max) {
                    System.out.println("Selección Incorrecta. Ingrese un valor válido (" + min + "-" + max + ")");
                } else {
                    entradaValida = true; // Si está en el rango, marcamos como válida
                }
            } catch (InputMismatchException e) { // la excepción si no es un entero
                System.out.println("Error: Por favor, ingrese un número entero válido."); 
                scanner.next(); 
            }
        } while (!entradaValida);
        return entrada;
    }
}