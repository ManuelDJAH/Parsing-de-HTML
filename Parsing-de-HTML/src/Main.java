import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.HTMLDocument;
import java.io.*;
import java.nio.file.*;
import java.util.regex.*;

public class Main {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Para usar el programa realizar completar el siguiente orden: <ejemplo.html> <Palabra>");
            return;
        }

        String Archivohtml = args[0];
        String Palabra = args[1];
        String logFileName = "file-" + Palabra + ".log";

        try {

            String htmlContent = new String(Files.readAllBytes(Paths.get(Archivohtml)));
            HTMLEditorKit kit = new HTMLEditorKit();
            HTMLDocument doc = (HTMLDocument) kit.createDefaultDocument();
            kit.read(new StringReader(htmlContent), doc, 0);

            Pattern pattern = Pattern.compile(Pattern.quote(Palabra), Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(htmlContent);

            try (BufferedWriter logWriter = new BufferedWriter(new FileWriter(logFileName))) {
                logWriter.write("Archivo HTML: " + Archivohtml + "\n");
                logWriter.write("Palabra clave: " + Palabra + "\n");

                int count = 0;
                while (matcher.find()) {
                    int Posicion = matcher.start();
                    System.out.println("Palabra encontrada en la posición: " + Posicion);
                    logWriter.write("Posición: " + Posicion + "\n");
                    count++;
                }

                if (count == 0) {
                    System.out.println("No se encontro la palabra");
                } else {
                    System.out.println("Se encontro la palabra " + count + " veces.");
                }
            }

        } catch (Exception e) {
            System.err.println("Ocurrió un error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}