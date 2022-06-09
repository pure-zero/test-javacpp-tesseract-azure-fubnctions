package io.quarkus;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bytedeco.javacpp.*;
import org.bytedeco.leptonica.*;
import org.bytedeco.tesseract.*;
import static org.bytedeco.leptonica.global.lept.*;
import static org.bytedeco.tesseract.global.tesseract.*;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

@WebServlet(name = "ServletGreeting", urlPatterns = "/servlet/hello")
public class GreetingServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String body = "hello vertx";
        BytePointer outText;

        TessBaseAPI api = new TessBaseAPI();
        // Initialize tesseract-ocr with English, without specifying tessdata path
        if (api.Init("/home/site/wwwroot/bin/tessdata", "eng") != 0) {
            System.err.println("Could not initialize tesseract.");
            System.err.println("file exists" + new File("tessdata").list());
            body = "file exists: " + new File("/home/site/wwwroot/bin/tessdata").exists() + " " + String.join(",", new File(".").list()) + " " + new java.io.File(".").getCanonicalPath() + " " + "new File(getClass().getResource(\"/tessdata/eng.traineddata\").toURI()).exists()" + " " + String.join(",", new File("/home/site/wwwroot/").list());
            resp.setStatus(200);
            resp.addHeader("Content-Type", "text/plain");
            resp.getWriter().write(body);
            api.End();
            api.close();
            return;
        }

        // Open input image with leptonica library
        PIX image = pixRead("download.jpeg");
        api.SetImage(image);
        // Get OCR result
        outText = api.GetUTF8Text();
        body = outText.getString();
        System.out.println("OCR output:\n" + outText.getString());

        // Destroy used object and release memory
        api.End();
        outText.deallocate();
        pixDestroy(image);
        api.close();

        resp.setStatus(200);
        resp.addHeader("Content-Type", "text/plain");
        resp.getWriter().write(body);
    }
}
