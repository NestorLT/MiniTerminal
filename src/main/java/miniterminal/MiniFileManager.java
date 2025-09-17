package miniterminal;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;

public class MiniFileManager
{
    private static File actual;

    /**
     * Constructor de la clase Miniterminal
     * 
     * @param dir se le pasa un directorio por parámetro
     * @throws Exception Si no encuentra el archivo tira una excepción
     */
    public MiniFileManager(String dir) throws Exception
    {
        if (!new File(dir).exists())
        {
            throw new Exception("ERROR no se encentra el directorio");
        }
        actual = new File(dir);
    }

    /**
     * Devuelve la ruta absoluta del directorio de trabajo actual.
     * <p>
     * Este método muestra cuál es la carpeta actual dentro del sistema de archivos.
     * 
     * @return Una cadena que representa la ruta absoluta del directorio actual.
     * @throws Exception Si el directorio actual no está definido o no existe.
     */
    public static String getPDW() throws Exception
    {
        if (actual == null || !actual.exists())
        {
            throw new Exception("ERROR no se encuentra el directorio");
        }
        return actual.getAbsolutePath();
    }

    /**
     * Cambia el directorio de trabajo actual al especificado.
     * <p>
     * El método permite cambiar a un subdirectorio indicado por {@code dir}, o a la carpeta superior
     * si se pasa el parámetro especial {@code ".."}.
     * <ul>
     * <li>Si {@code dir} es un nombre de directorio válido dentro del directorio actual, se cambia a dicho
     * directorio.</li>
     * <li>Si {@code dir} es {@code ".."}, se cambia al directorio padre del actual (si existe).</li>
     * </ul>
     * 
     * @param dir Nombre del directorio al que se desea cambiar o {@code ".."} para subir al directorio padre.
     * @return {@code true} si el cambio de directorio se realizó correctamente, {@code false} si el directorio no
     * existe.
     * @throws Exception Si ocurre un error al resolver la ruta o si el directorio especificado no existe.
     */
    public static boolean changeDir(String dir) throws Exception
    {
        if (!new File(actual, dir).exists())
        {
            return false;
        }

        File nuevaRuta = new File(getPDW());

        if (dir.equals(".."))
        {
            if (actual.getParent() == null)
            {
                nuevaRuta = new File(actual.getAbsolutePath());
            }
            else
            {
                nuevaRuta = new File(actual.getParent());
            }
        }
        else
        {
            nuevaRuta = new File(actual.getAbsolutePath(), dir);
        }
        setActual(nuevaRuta.getCanonicalFile());
        return true;
    }

    /**
     * Muestra la lista de directorios y archivos de la ruta especificada.
     * <p>
     * Dependiendo del parámetro {@code completo}, el método puede funcionar de dos formas:
     * <ul>
     * <li><b>LS (completo = false):</b> Muestra únicamente los nombres de los directorios y archivos de la carpeta
     * actual,
     * primero los directorios y luego los archivos, ambos ordenados alfabéticamente.</li>
     * <li><b>LL (completo = true):</b> Muestra, además del nombre, el tamaño (en bytes) y la fecha de última
     * modificación
     * de cada archivo o directorio.</li>
     * </ul>
     * <p>
     * Si la ruta especificada corresponde a un archivo individual, mostrará la información de ese único archivo.
     * 
     * @param dir Ruta del directorio o archivo a listar.
     * @param completo Si es {@code true}, se mostrará información detallada (ll); si es {@code false}, solo los nombres
     * (ls).
     * @throws Exception Si la ruta especificada no existe.
     */
    public static void muestraDatos(String dir, boolean completo) throws Exception
    {
        File rutaF = new File(dir);
        if (!rutaF.exists())
        {
            throw new Exception("ERROR el archivo no existe");
        }

        if (completo)
        {
            if (rutaF.isDirectory())
            {
                File[] files = rutaF.listFiles();
                Arrays.sort(files, Comparator.comparing(File::isDirectory).thenComparing(File::isFile).reversed());
                System.out.printf("%-20s %-10s %-20s\n", "NAME", "LENGTH", "LAST MODIFIED");
                System.out.println("------------------  ----------  ----------------------------");
                for (File file : files)
                {
                    System.out.printf("%-20s %-10d %-20s\n", file.getName(), file.length(),
                        new Date(file.lastModified()));
                }
            }
            else if (rutaF.isFile())
            {
                System.out.printf("%-20s \n", "NAME");
                System.out.println("------------------");
                System.out.printf("%-20s %-10d %-20s \n", rutaF.getName(), rutaF.length(),
                    new Date(rutaF.lastModified()));
            }
        }
        else
        {
            if (rutaF.isDirectory())
            {
                File[] files = rutaF.listFiles();
                Arrays.sort(files, Comparator.comparing(File::isDirectory).thenComparing(File::isFile).reversed());

                for (File file : files)
                {
                    System.out.printf("%-20s \n", file.getName());
                }
            }
            else if (rutaF.isFile())
            {
                System.out.printf("%-20s \n", rutaF.getName());
            }
        }
    }

    /**
     * Crea un nuevo directorio dentro del directorio de trabajo actual.
     * <p>
     * El método intenta crear el directorio especificado por {@code dir}.
     * Si es necesario, también crea los directorios intermedios que no existan.
     * 
     * @param dir Nombre del directorio que se desea crear.
     * @return {@code true} si el directorio se creó correctamente o ya existía, {@code false} si no se pudo crear.
     * @throws Exception Si el directorio actual no existe.
     */
    public static boolean creaMkdir(String dir) throws Exception
    {
        if (!actual.exists())
        {
            throw new Exception("ERROR no se encontro el directorio");
        }
        return new File(actual.getAbsolutePath(), dir).mkdirs();
    }

    /**
     * Elimina un archivo o directorio del directorio de trabajo actual.
     * <p>
     * Si se especifica un archivo, se elimina directamente.
     * Si se especifica un directorio:
     * <ul>
     * <li>Se eliminan todos los archivos dentro del directorio.</li>
     * <li>Las subcarpetas no se eliminan, y se mostrará un aviso al usuario.</li>
     * <li>Después de eliminar los archivos, intenta eliminar el propio directorio.</li>
     * </ul>
     * 
     * @param dir Nombre del archivo o directorio que se desea eliminar.
     * @return {@code true} si el archivo o directorio se eliminó correctamente o si contiene subcarpetas (aunque no se
     * eliminen), {@code false} si el archivo o directorio no existe.
     */
    public static boolean borraFile(String dir)
    {
        if (!new File(actual, dir).exists())
        {
            return false;
        }

        File dirF = new File(actual, dir);
        boolean subcarpetas = false;

        if (dirF.isDirectory())
        {
            File[] files = dirF.listFiles();

            for (File file : files)
            {
                if (file.isDirectory())
                {
                    subcarpetas = true;
                    continue;
                }
                file.delete();
            }

            if (subcarpetas)
            {
                System.out.println("No se han eliminado las subcarpetas");
                return true;
            }
        }
        return dirF.delete();
    }

    /**
     * Mueve o renombra un archivo o directorio dentro del directorio de trabajo actual.
     * <p>
     * Este método mueve o renombra {@code FILE1} a la ubicación o nombre especificado por {@code FILE2}.
     * Si {@code FILE2} es un directorio, el archivo o directorio original se moverá dentro de él.
     * Si la ruta destino no existe, se crearán los directorios necesarios.
     * 
     * @param f1 Nombre del archivo o directorio de origen.
     * @param f2 Nombre del archivo o directorio de destino, o el directorio donde se moverá el origen.
     * @return {@code true} si la operación se realizó con éxito, {@code false} si el archivo o directorio de origen no
     * existe o si no se pudo mover o renombrar.
     */
    public static boolean mueveFile(String f1, String f2)
    {
        File origen = new File(actual, f1);
        if (!origen.exists())
            return false;

        File destino = new File(actual, f2);

        if (destino.isDirectory())
        {
            destino = new File(destino, origen.getName());
        }
        else
        {
            File padre = destino.getParentFile();

            if (padre != null && !padre.exists())
            {
                padre.mkdirs();
            }
        }

        return origen.renameTo(destino);
    }

    /**
     * Muestra el contenido de un archivo por pantalla.
     * <p>
     * Lee el archivo especificado por {@code dir} y muestra cada línea por la salida estándar.
     * Si el archivo no existe no realiza ninguna operación.
     * En caso de error durante la lectura, se muestra un mensaje de error por consola.
     * 
     * @param dir Nombre del archivo que se desea mostrar.
     * @return {@code true} si el archivo existe y se ha leído correctamente, {@code false} si el archivo no existe o es
     * un directorio.
     */
    public static boolean mostrar(String dir)
    {
        if (!new File(actual, dir).exists() || new File(actual, dir).isDirectory())
        {
            return false;
        }

        try (BufferedReader lector = new BufferedReader(new FileReader(new File(actual, dir))))
        {
            lector.lines().forEach(System.out::println);
        }
        catch (Exception e)
        {
            System.out.println("Error en la lectura");
        }
        return true;
    }

    /**
     * Muestra una breve ayuda con los comandos disponibles.
     * <p>
     * Este método imprime en pantalla una lista de los comandos disponibles en el programa,
     * junto con una breve descripción de lo que hace cada uno.
     */
    public static void ayuda()
    {
        System.out.println(
            """
                COMANDOS DE AYUDA
                ___________________________________________________________

                - pdw()              : Muestra cual es la carpeta actual
                - cd <DIR>           : Cambia la carpeta actual a 'DIR'
                - ls                 : Muestra la lista de direcctorios y archivos de la carpeta actual
                - ll                 : Muestra la lista de directorios y arcgivos de la carpeta actual con el tamaño y fecha de última modificación
                - mkdir <DIR>        : Crea el directorio <DIR> en la carpeta actual
                - rm <FILE>          : Borra 'FILE'
                - mv <FILE1><FILE2>  : Mueve o renombra 'FILE1' a 'FILE2'
                - mostrar <FILE>     : Muestra el contenido del fichero por pantalla
                - sustituir <FILE>
                  <cadena_original>
                  <cadena_final>     : Sustituye la cadena original por la cadena_final en el fichero
                - help               : Muestra la información de todos los comandos disponibles
                - exit               : Termina el programa""");
    }

    public static File getActual()
    {
        return actual;
    }

    public static void setActual(File actual)
    {
        MiniFileManager.actual = actual;
    }

    /**
     * Sustituye una cadena de texto por otra dentro de un archivo.
     * <p>
     * Este método lee el contenido del archivo especificado por {@code directorio},
     * reemplaza todas las ocurrencias de {@code textoViejo} por {@code textoNuevo}
     * y guarda los cambios en el mismo archivo. Si el archivo no existe o los textos
     * de búsqueda o reemplazo están vacíos, no realiza ninguna operación.
     * <p>
     * El proceso se realiza mediante la creación de un archivo temporal que contiene
     * las modificaciones. Una vez terminada la escritura, el archivo original se elimina
     * y el archivo temporal pasa a tener el nombre del archivo original.
     * 
     * @param directorio Ruta del archivo donde se realizarán las sustituciones.
     * @param textoViejo Cadena de texto que se desea reemplazar.
     * @param textoNuevo Cadena de texto que reemplazará a {@code textoViejo}.
     * @return {@code true} si la sustitución se realizó con éxito, {@code false} si el archivo no existe
     * o si alguno de los textos proporcionados está vacío.
     */
    public static boolean sustituir(String directorio, String textoViejo, String textoNuevo)
    {
        if (!new File(actual, directorio).exists() || textoNuevo.isBlank() || textoViejo.isBlank()) return false;
        File original = new File(actual, directorio);
        File archivoTemporal = new File(actual, "temp.txt");

        try (BufferedReader lector = new BufferedReader(new FileReader(original));
            BufferedWriter escritor = new BufferedWriter(new FileWriter((archivoTemporal))))
        {
            String linea;
            while ((linea = lector.readLine()) != null)
            {
                String lineaModificada = linea.replace(textoViejo, textoNuevo);
                escritor.write(lineaModificada);
                escritor.newLine(); // Añade salto de línea correctamente
            }
        }
        catch (Exception e)
        {
            System.out.println("Error al leer el archivo");
        }

        original.delete();
        archivoTemporal.renameTo(original);
        return true;

    }
}
