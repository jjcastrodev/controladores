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
public class EncabezadoYPieActa extends PdfPageEventHelper {
    
    private String _frase, _acta, _orden, _copia;
    
    public EncabezadoYPieActa(String leyenda, String acta, String copia) {
        _frase = leyenda;
        _acta = acta;
        _copia = copia;
    }
    
    @Override
    public void onStartPage(PdfWriter writer,Document document) {
    	Rectangle rect = writer.getBoxSize("art");
        Image image = null;
        try {
            image = Image.getInstance("image/logoAfip.png");
        } catch (BadElementException ex) {
            Logger.getLogger(EncabezadoYPieActa.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(EncabezadoYPieActa.class.getName()).log(Level.SEVERE, null, ex);
        }

        image.scaleAbsolute(140f, 60f);
        PdfPCell cell = new PdfPCell(image);
        cell.setRowspan(4);
        cell.setBorderColor(BaseColor.WHITE);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setBorder(0);

        
        PdfPTable tablaCabeza = new PdfPTable(2);
        
        // Agregar la celda a la tabla
        tablaCabeza.addCell(cell);
        
        Font fontFrase = FontFactory.getFont(FontFactory.COURIER.toString(), 7, Font.BOLD, BaseColor.DARK_GRAY);
        //Phrase frase =  new Phrase("\"" + "2017 - Año de las energías renovables" +"\"", fontFrase);
        Phrase frase =  new Phrase(_frase, fontFrase);
        cell =  new PdfPCell(frase);
        cell.setBorderColor(BaseColor.WHITE);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cell.setBorder(0);
        tablaCabeza.addCell(cell);
        
        cell =  new PdfPCell(new Phrase(Chunk.NEWLINE));
        cell.setBorderColor(BaseColor.WHITE);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cell.setBorder(0);
        tablaCabeza.addCell(cell);
        
        //String numeroActa = "0750662016123458744"; 
        Font fontNumero = FontFactory.getFont(FontFactory.TIMES_ROMAN.toString(), 13, Font.BOLD, BaseColor.BLACK);
        frase = new Phrase("N°: " +_acta, fontNumero);
        cell = new PdfPCell(frase);
        cell.setBorderColor(BaseColor.WHITE);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cell.setBorder(0);
        tablaCabeza.addCell(cell);

        try {
            document.add(tablaCabeza);
            
            
            //ColumnText.showTextAligned(writer.getDirectContent(),Element.ALIGN_CENTER, image, rect.getLeft(), rect.getTop(), 0);
            //ColumnText.showTextAligned(writer.getDirectContent(),Element.ALIGN_CENTER, new Phrase("Top Right"), rect.getRight(), rect.getTop(), 0);
        } catch (DocumentException ex) {
            Logger.getLogger(EncabezadoYPieActa.class.getName()).log(Level.SEVERE, null, ex);
        }
    } 
    @Override
    public void onEndPage(PdfWriter writer,Document document) {
        
            Rectangle rect = writer.getBoxSize("art");
            //ColumnText.showTextAligned(writer.getDirectContent(),Element.ALIGN_CENTER, new Phrase("Bottom Left"), rect.getLeft(), rect.getBottom(), 0);
            //ColumnText.showTextAligned(writer.getDirectContent(),Element.ALIGN_CENTER, new Phrase("Bottom Right"), rect.getRight(), rect.getBottom(), 0);
           // PdfPTable tablaPie = new PdfPTable(2);
            
            Font fontPie = FontFactory.getFont(FontFactory.TIMES_ROMAN.toString(), 9, Font.BOLD, BaseColor.BLACK);
            
            Phrase frase1 = new Phrase(_copia, fontPie);
            PdfPCell cell = new PdfPCell(frase1);
            /*cell.setBorderColor(BaseColor.WHITE);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setBorder(0);
            tablaPie.addCell(cell);*/
            
            fontPie = FontFactory.getFont(FontFactory.TIMES_ROMAN.toString(), 5, Font.NORMAL, BaseColor.BLACK);
            Phrase frase2 = new Phrase("Utilizar solo el frente del formulario.", fontPie);
            /*cell = new PdfPCell(frase2);
            cell.setBorderColor(BaseColor.WHITE);
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cell.setBorder(0);
            tablaPie.addCell(cell);*/
          //  document.add(tablaPie);
          ColumnText.showTextAligned(writer.getDirectContent(),Element.ALIGN_CENTER, frase1, rect.getLeft()+10, rect.getBottom(), 0);
          ColumnText.showTextAligned(writer.getDirectContent(),Element.ALIGN_CENTER, frase2, rect.getRight()-25, rect.getBottom(), 0);
        
    }
    
}
