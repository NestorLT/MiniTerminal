package miniterminal;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* Clase MiniTerminal: Clase principal (con función main) que se encargará de interactuar
 con el usuario e interpretar los comandos (qué comando se pide, argumentos, etc.).
 Utilizará la segunda clase para realizar las operaciones de gestión de archivos. Manejará
 todas las posibles excepciones. */

/**
 * Clase MiniTerminal
 * Clase principal que se encargará de interactuar con el usuario e interpretar los comandos
 * 
 * @author Nestor Sebastian Linares Talero
 * @version 1.0
 * @param args
 */
public class MiniTerminal
{

    /**
     * Funcion principal del programa
     * 
     */
    @SuppressWarnings("unused")
    public static void main(String[] args)
    {
        Scanner reader = new Scanner(System.in);
        MiniFileManager mf = null;

        try
        {
            mf = new MiniFileManager(System.getProperty("user.dir"));
        }
        catch (Exception e)
        {
            System.out.println("EROR al instanciar MiniFileManager");
        }

        String operacion = "";
        do
        {
            try
            {
                System.out.print(MiniFileManager.getPDW() + "> ");
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            operacion = reader.nextLine();

            if (operacion.isBlank())
            {
                System.out.println("Linea no encontrada");
                continue;
            }

            String[] argumentos = operacion.split(" ");
            List<String> argumento = transformarArugmentos(operacion);

            if (argumento.isEmpty()) continue;

            String directorio = argumento.size() > 1 ? argumento.get(1) : "";
            String directorio2 = argumento.size() > 2 ? argumento.get(2) : "";
            String directorio3 = argumento.size() > 3 ? argumento.get(3) : "";

            String comando = argumento.get(0).toLowerCase();

            switch (comando)
            {
                case "cd" -> {
                    try
                    {
                        MiniFileManager.changeDir(directorio);
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
                case "ls" -> {
                    try
                    {
                        MiniFileManager.muestraDatos(MiniFileManager.getPDW(), false);
                    }
                    catch (Exception e)
                    {
                        System.out.println("Error al mostrar el directorio");
                        e.printStackTrace();
                    }
                }
                case "ll" -> {
                    try
                    {
                        MiniFileManager.muestraDatos(MiniFileManager.getPDW(), true);
                    }
                    catch (Exception e)
                    {
                        System.out.println("Error al mostrar el directorio");
                        e.printStackTrace();
                    }
                }
                case "mkdir" -> {
                    try
                    {
                        MiniFileManager.creaMkdir(directorio);
                    }
                    catch (Exception e)
                    {
                        System.out.println("Error al crear el directorio");
                        e.printStackTrace();
                    }
                }
                case "rm" -> MiniFileManager.borraFile(directorio);
                case "mv" -> {
                    // Falta implementar mover carpetas con espacios
                    MiniFileManager.mueveFile(directorio, directorio2);
                } // undone
                case "mostrar" -> MiniFileManager.mostrar(directorio);
                case "sustituir" -> MiniFileManager.sustituir(directorio, directorio2, directorio3);
                case "help" -> MiniFileManager.ayuda();
                case "exit" -> System.out.println("Hasta la próxima ^^");
                default -> System.out.println("Escriba una opción válida");
            }

        } while (!operacion.matches("exit"));

        reader.close();
    }

    /**
     * Método que sirve para poder transformar los argumentos en Strings.
     * Divide según grupos con comillas
     * 
     * @param argumento El comando completo para dividir
     * @return Lista con los distintos grupos de Strings
     */
    public static List<String> transformarArugmentos(String argumento)
    {
        List<String> args = new ArrayList<>();

        Matcher m = Pattern.compile("\"([^\"]*)\"|(\\S+)").matcher(argumento);

        while (m.find())
        {
            if (m.group(1) != null)
            {
                args.add(m.group(1));
            }
            else
            {
                args.add(m.group(2));
            }
        }

        return args;
    }

}
