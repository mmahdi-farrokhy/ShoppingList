package shop;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class PdfServiceImplementer implements PdfService{

    public static final String PDF_PATH = "src/main//files/List.txt";

    @Override
    public void saveAsPdf(String text) {
        try (FileOutputStream fos = new FileOutputStream(PDF_PATH)) {
            fos.write(text.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
