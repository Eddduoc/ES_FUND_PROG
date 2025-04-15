import java.util.InputMismatchException;
import java.util.Scanner;

public class Exp2_S5_Eduardo_Stegmaier {

    // Creación de Arreglos y Variables
    static int[] vip = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    static int[] platea_alta = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    static int[] platea_baja = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    static int[] general = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

    static String[] ubicacion_texto = {"VIP        ", "Platea Alta", "Platea Baja", "General    "};
    static String[] asiento_texto = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};

    static int[] precios = {20000, 15000, 12000, 10000};
    static int index = 0; // Índice para las entradas compradas

    // Matriz para guardar las entradas compradas
    static String[][] entradasCompradas = new String[10][3]; // 10 entradas, 3 campos (precio, edad, ubicación)

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("     _________________________");
        System.out.println("     Bienvenido al Teatro Moro");
        System.out.println("     _________________________\n");

        //bucle para el menu principal
        int opcion;
        do {
            System.out.println(" ______________\n Menu principal \n ______________\n \n1. Comprar entradas \n2. Ver promociones" +
                    "\n3. Buscar entradas disponibles \n4. Eliminar entradas \n5. Pagar\n6. Salir\n");

            opcion = validacion(scanner, 1, 6);

            switch (opcion) {
                case 1:
                    // Comprar entradas
                    comprarEntradas(scanner);
                    salir(scanner);
                    break;
                case 2:
                    // Ver promociones
                    verPromociones();
                    salir(scanner);
                    break;
                case 3:
                    // Buscar entradas disponibles
                    print_asientos(scanner, ubicacion_texto, asiento_texto);
                    salir(scanner);
                    break;
                case 4:
                    // Eliminar entradas
                    eliminarEntradas(scanner);
                    salir(scanner);
                    break;
                case 5:
                    // Pagar
                    pagar(scanner);
                    salir(scanner);
                    break;
                case 6:
                    // Salir
                    System.out.println("Gracias por su visita");
                    break;
            }
        } while (opcion != 6);
        scanner.close();
    }

    // Método para registrar una entrada comprada
    private static void registrarEntrada(int index, int precioFinal, double edad, String ubicacionAsiento) {
        entradasCompradas[index][0] = String.valueOf(precioFinal);
        entradasCompradas[index][1] = String.valueOf(edad);
        entradasCompradas[index][2] = ubicacionAsiento;
    }

    //Metodo para Comprar Entradas
    private static void comprarEntradas(Scanner scanner) {
        int tipo_entrada, asiento;
        boolean disponible;

        do {
            disponible = true;
            //Seleccionar ubicacion de entrada 
            System.out.println("Seleccione el tipo de entrada que desea comprar: \n");
            System.out.println("1. VIP \n2. Platea Alta \n3. Platea Baja \n4. General \n");

            tipo_entrada = validacion(scanner, 1, 4);

            // Seleccionar asientos disponibles
            System.out.println("Asientos disponibles \n");
            System.out.println("Seleccione el asiento que desea comprar: \n");
            for (int i = 0; i < 10; i++) {
                System.out.print(" " + (i + 1) + ": " + (tipo_entrada == 1 ? vip[i] : tipo_entrada == 2 ? platea_alta[i] : tipo_entrada == 3 ? platea_baja[i] : general[i]) + " /");
            }
            System.out.println(" ");

            asiento = validacion(scanner, 1, 10);

            // Validar si el asiento está disponible
            if (tipo_entrada == 1 && vip[asiento - 1] == 1) {
                System.out.println("El asiento ya está ocupado. Por favor, seleccione otro.");
                disponible = false;
            } else if (tipo_entrada == 2 && platea_alta[asiento - 1] == 1) {
                System.out.println("El asiento ya está ocupado. Por favor, seleccione otro.");
                disponible = false;
            } else if (tipo_entrada == 3 && platea_baja[asiento - 1] == 1) {
                System.out.println("El asiento ya está ocupado. Por favor, seleccione otro.");
                disponible = false;
            } else if (tipo_entrada == 4 && general[asiento - 1] == 1) {
                System.out.println("El asiento ya está ocupado. Por favor, seleccione otro.");
                disponible = false;
            }
        } while (!disponible);

        //Ingresar/Validar edad
        System.out.println("Ingrese su edad:  \n");
        int edad = validacion(scanner, 1, 100);

        // calcular descuento / precio base y precio final 
        double descuento = (edad < 18 ? 0.1 : (edad >= 60 ? 0.15 : 0));
        int precio_base = precios[tipo_entrada - 1];
        int precio_final = (int) (precio_base * (1 - descuento));

        // Resumen de la compra
        System.out.println("     _______________________");
        System.out.println("        Resumen de compra");
        System.out.println("     _______________________\n");
        System.out.println("1 Entrada: " + "Fila: " + ubicacion_texto[tipo_entrada - 1] + "  Asiento: " + asiento_texto[asiento - 1] + "\n");

        System.out.println("Edad: " + edad + " años \n");
        System.out.println("El precio base de la entrada es: $ " + precio_base);
        System.out.println("Con un descuento de " + descuento * 100
                + "% " + (edad < 18 ? "(Estudiante) " : (edad >= 60 ? "(Tercera edad) " : "(no aplica) ")) + "\n");

        System.out.println("El precio final de la entrada es: $ " + precio_final + "\n");
        System.out.println("Entrada Añadida a su compra.\n");

        // Marcar asiento como ocupado
        if (tipo_entrada == 1) {
            vip[asiento - 1] = 1;
        } else if (tipo_entrada == 2) {
            platea_alta[asiento - 1] = 1;
        } else if (tipo_entrada == 3) {
            platea_baja[asiento - 1] = 1;
        } else if (tipo_entrada == 4) {
            general[asiento - 1] = 1;
        }

        // Registrar la entrada comprada
        if (index < entradasCompradas.length) {
            registrarEntrada(index, precio_final, edad, ubicacion_texto[tipo_entrada - 1] + " " + asiento_texto[asiento - 1]);
            index++;
        } else {
            System.out.println("No se pueden registrar más entradas. Límite alcanzado.");
        }
    }

    //Metodo para ver promociones    
    private static void verPromociones() {
        System.out.println("Promociones disponibles: \n1. Promoción de un 30% de descuento comprando más de 3 entradas\n");
    }

    // Método para imprimir asientos disponibles
    private static void print_asientos(Scanner scanner, String[] fila_texto, String[] asientos_texto) {
        System.out.println("Asientos disponibles \n");
        for (int i = 0; i <= 3; i++) {
            System.out.print(i + 1 + ". " + fila_texto[i] + ":  ");
            int fila_disponible = 0;

            for (int j = 0; j <= 9; j++) {
                if (i == 0) {
                    fila_disponible = vip[j];
                } else if (i == 1) {
                    fila_disponible = platea_alta[j];
                } else if (i == 2) {
                    fila_disponible = platea_baja[j];
                } else if (i == 3) {
                    fila_disponible = general[j];
                }
                System.out.print((fila_disponible == 0 ? " - " : " X ") + asientos_texto[j] + " / ");
            }
            System.out.println();
        }
        System.out.println();
        System.out.println("X(no disponible)");
        System.out.println();
    }

    // Método para eliminar entradas
    private static void eliminarEntradas(Scanner scanner) {
        System.out.println("Ingrese la ubicación de la entrada que desea eliminar (1.vip - 2.platea alta - 3.platea baja - 4.general): \n");
        int ubicacion_entrada = validacion(scanner, 1, 4);
        System.out.println("Ingrese el número de entrada que desea eliminar (1-10): \n");
        int numero_entrada = validacion(scanner, 1, 10);

        switch (ubicacion_entrada) {
            case 1:
                vip[numero_entrada - 1] = 0;
                break;
            case 2:
                platea_alta[numero_entrada - 1] = 0;
                break;
            case 3:
                platea_baja[numero_entrada - 1] = 0;
                break;
            case 4:
                general[numero_entrada - 1] = 0;
                break;
        }

        // Eliminar la entrada de la matriz de entradas compradas
        for (int i = 0; i < index; i++) {
            if (entradasCompradas[i][2] != null && entradasCompradas[i][2].equals(ubicacion_texto[ubicacion_entrada - 1] + " " + asiento_texto[numero_entrada - 1])) {
                for (int j = i; j < index - 1; j++) {
                    entradasCompradas[j] = entradasCompradas[j + 1];
                }
                entradasCompradas[index - 1] = new String[3];
                index--;
                break;
            }
        }
        System.out.println("Entrada eliminada correctamente.\n");
    }

    private static int calcularTotal() {
        int total = 0;
        for (int i = 0; i < index; i++) {
            if (entradasCompradas[i][0] != null) {
                total += Integer.parseInt(entradasCompradas[i][0]);
            }
        }
        return total;
    }

    // Método para pagar entradas
    private static void pagar(Scanner scanner) {
        System.out.println("Pagar entradas \n");
        System.out.println("Resumen de entradas compradas: \n");

        for (int i = 0; i < index; i++) {
            if (entradasCompradas[i][0] != null) {
                System.out.println("Entrada " + (i + 1) + ": Precio: " + entradasCompradas[i][0] +
                        ", Edad: " + entradasCompradas[i][1] +
                        ", Ubicación: " + entradasCompradas[i][2]);
            }
        }
        System.out.println("");

        int total = calcularTotal();
        System.out.println("Total sin descuento: $ " + total + "\n");
        if (index >= 3) {
            System.out.println("Descuento del 30% aplicado por comprar más de 3 entradas.");
            total = (int) (total * 0.7);
        }

        System.out.println("Total a pagar: $ " + total + "\n");

        System.out.println("¿Confirmar pago? (1. Sí / 2. No)");
        int confirmarPago = validacion(scanner, 1, 2);
        if (confirmarPago == 1) {
            System.out.println("Pago confirmado. Gracias por su compra. \n");
            for (int i = 0; i < index; i++) {
                entradasCompradas[i] = new String[3];
            }
            index = 0;
            for (int i = 0; i < 10; i++) {
                vip[i] = 0;
                platea_alta[i] = 0;
                platea_baja[i] = 0;
                general[i] = 0;
            }
        } else {
            System.out.println("Pago cancelado.\n");
        }
    }

    // Método para salir 
    private static void salir(Scanner scanner) {
        System.out.println("1. Volver al menu principal? \n2. Salir\n");
        int choice = validacion(scanner, 1, 2);
        if (choice == 2) {
            System.out.println("Gracias por su visita");
            System.exit(0);
        }
    }

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
}