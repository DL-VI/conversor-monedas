package com.alura.challenge.app;

import com.alura.challenge.modulos.Controlador;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;


public class Main {
    public static Scanner sc = new Scanner(System.in);
    public static Controlador controlador = new Controlador();


    public static void main(String[] args) throws IOException, InterruptedException {
        int numero;
        String codigoMoneda;

        do {
            System.out.print("""
                    \n---------------------------------------------------------------
                                    CONVERSOR DE MONEDAS
                    ---------------------------------------------------------------
                    Digite el numero de la opcion que desea ejecutar

                    1) COP - Peso colombiano
                    2) USD - Dollar estadounidense
                    3) BRL - Real brasileño
                    4) ARS - Peso argentino
                    5) Monedas soportadas
                    6) Nueva moneda
                    7) Historial en archivo Json
                    8) Salir

                    Numero:""");
            try {

                numero = sc.nextInt();
                System.out.print("---------------------------------------------------------------\n\n");

                switch (numero) {
                    case 1:
                        codigoMoneda = "COP";
                        menuConvertidor(codigoMoneda);
                        break;

                    case 2:
                        codigoMoneda = "USD";
                        menuConvertidor(codigoMoneda);
                        break;

                    case 3:
                        codigoMoneda = "BRL";
                        menuConvertidor(codigoMoneda);
                        break;

                    case 4:
                        codigoMoneda = "ARS";
                        menuConvertidor(codigoMoneda);
                        break;

                    case 5:
                        System.out.println(controlador.todasLasMonedas());
                        break;

                    case 6:
                        sc.nextLine();
                        System.out.println("Código de moneda según el estándar ISO 4217");
                        System.out.print("Moneda base: ");
                        codigoMoneda = sc.nextLine();

                        System.out.print("---------------------------------------------------------------\n\n");
                        if (StringUtils.isNumeric(codigoMoneda))
                            System.out.println("Error: se esperaba un código de moneda, pero se ha recibido un número.");
                        else if (controlador.tasaConversion(codigoMoneda).equals("¡Error!"))
                            System.out.println("El codigo de la moneda no esta soportada.");
                        else
                            menuConvertidor(codigoMoneda.toUpperCase());
                        break;

                    case 7:
                        System.out.println(controlador.crearJson());
                        break;

                    case 8:
                        controlador.cerrarScanner();
                        System.out.println("¡Programa finalizado, gracias por usar nuestro servicio!");
                        System.out.print("\n---------------------------------------------------------------\n");
                        System.out.println(controlador.historial());
                        break;

                    default:
                        System.out.println("¡Opcion icorrecta, intentelo de nuevo!");
                }
            } catch (InputMismatchException e) {
                System.out.print("---------------------------------------------------------------\n");
                System.out.println("\nError: se esperaba un número, pero se ha recibido una cadena.");
                sc.nextLine();
                numero = 0;
            }
        } while (numero != 8);
        sc.close();
    }


    public static void menuConvertidor(String codigoMoneda) throws IOException, InterruptedException {
        System.out.println(controlador.tasaConversion(codigoMoneda));

        int numero;

        do {
            System.out.print("""
                    ---------------------------------------------------------------

                    1) Monedas soportadas
                    2) Tasa de conversiones
                    3) Convertir valor
                    4) Regresar

                    Numero:""");
            try {

                numero = sc.nextInt();
                System.out.println("---------------------------------------------------------------\n");

                switch (numero) {
                    case 1:
                        System.out.println(controlador.todasLasMonedas() + "\n");
                        break;

                    case 2:
                        System.out.println(controlador.tasaConversion(codigoMoneda));
                        break;

                    case 3:
                        System.out.printf("Valor en %s: ", codigoMoneda);
                        float valor = sc.nextFloat();
                        sc.nextLine();

                        System.out.println("\nCódigo de moneda según el estándar ISO 4217");
                        System.out.print("Moneda a convertir: ");
                        var monedaConvertidir = sc.nextLine();

                        System.out.print("---------------------------------------------------------------\n");

                        if (StringUtils.isNumeric(monedaConvertidir))
                            System.out.println("\nError: se esperaba un código de moneda, pero se ha recibido un número.\n");
                        else
                            System.out.println(controlador.nuevaMoneda(codigoMoneda, valor, monedaConvertidir));
                        break;
                    case 4:
                        System.out.println("¡Regresando al menu principal!");
                        break;

                    default:
                        System.out.println("¡Opcion icorrecta, intentelo de nuevo!\n");

                }
            } catch (InputMismatchException | NumberFormatException e) {
                System.out.print("---------------------------------------------------------------\n");
                System.out.println("\nError: se esperaba un número, pero se ha recibido una cadena.\n");
                sc.nextLine();
                numero = 0;
            }
        } while (numero != 4);
    }

}
