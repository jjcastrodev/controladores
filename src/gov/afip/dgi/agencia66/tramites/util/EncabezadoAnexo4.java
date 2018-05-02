/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.afip.dgi.agencia66.tramites.util;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author u84062
 */
public class EncabezadoAnexo4 extends PdfPageEventHelper {
    
    private int contador=0;
    
    public EncabezadoAnexo4() {        
    }
    
    @Override
    public void onStartPage(PdfWriter writer,Document document) {
    	Rectangle rect = writer.getBoxSize("art");
        
        contador++;
        Image image = null;
        try {
            image = Image.getInstance("image/logoAfip.png");
        } catch (BadElementException ex) {
            Logger.getLogger(EncabezadoAnexo4.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(EncabezadoAnexo4.class.getName()).log(Level.SEVERE, null, ex);
        }

        image.scaleAbsolute(140f, 60f);
        PdfPCell cell = new PdfPCell(image);
        cell.setRowspan(4);
        cell.setBorderColor(BaseColor.WHITE);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setBorder(0);

        
        PdfPTable tablaCabeza = new PdfPTable(3);
        
        // Agregar la celda a la tabla
        tablaCabeza.addCell(cell);
        
        Font fontTitulo = FontFactory.getFont(FontFactory.COURIER.toString(), 10, Font.BOLD, BaseColor.BLACK);
        Phrase frase =  new Phrase("ANEXO IV", fontTitulo);
        cell =  new PdfPCell(frase);
        cell.setBorderColor(BaseColor.WHITE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBorder(0);
        tablaCabeza.addCell(cell);
        
        Font fontHoja = FontFactory.getFont(FontFactory.COURIER.toString(), 8, Font.BOLD, BaseColor.DARK_GRAY);
        cell =  new PdfPCell(new Phrase("HOJA N° "+contador +"/2", fontHoja));
        cell.setBorderColor(BaseColor.WHITE);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cell.setBorder(0);
        tablaCabeza.addCell(cell);
        try {
            document.add(tablaCabeza);
        } catch (DocumentException ex) {
            Logger.getLogger(EncabezadoAnexo4.class.getName()).log(Level.SEVERE, null, ex);
        }
    } 
}
