/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.afip.dgi.agencia66.tramites.util;

import gov.afip.dgi.agencia66.model.ControladorFiscal;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.DottedLineSeparator;
import com.itextpdf.text.pdf.draw.LineSeparator;
import gov.afip.dgi.agencia66.model.ParfrasesAnio;
import gov.afip.dgi.agencia66.controller.EntityMan;
import gov.afip.dgi.agencia66.controller.ParfrasesAnioJpaController;
import gov.afip.dgi.agencia66.model.RetiroMemoria;
import gov.afip.dgi.agencia66.util.FormateaSalidasTexto;
import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.TimeZone;


/**
 *
 * @author u84062
 */
public class ImprimeActaAnexoCF {
    
    private ControladorFiscal cf = new ControladorFiscal();
    private int anio;
    private String acta;
    private final FormateaSalidasTexto fst = new FormateaSalidasTexto();
    DecimalFormat df2 = new DecimalFormat("00");
    DecimalFormat df11 = new DecimalFormat("00000000000");
    DecimalFormat dfImp = new DecimalFormat("$ ###,###.00");
    
    public void imprime(ControladorFiscal _cf, int _anio) {
        cf = _cf;
        anio = _anio;
        acta = cf.getActa();
        
        try {
            if(cf.getTramite().startsWith("Baja Tec") ||cf.getTramite().startsWith("Cambio Tec")  ) {
                for(int i=0; i<3; i++) {
                    switch (i) {
                        case 0:
                            imprimeActaTecnico("ORIGINAL");
                            imprimeNotaTecnico();
                            break;
                        case 1:
                            imprimeActaTecnico("DUPLICADO");
                            break;
                        default:
                            imprimeActaTecnico("COPIA SISTEMAS");
                            break;
                    }
                }
            } else {
                   for(int i=0; i<3; i++) {
                       switch (i) {
                           case 0:
                               imprimeActa("ORIGINAL");
                               imprimeAnexo4();
                               break;
                           case 1:
                               imprimeActa("DUPLICADO");
                               break;
                           default:
                               imprimeActa("COPIA SISTEMAS");
                               break;
                       }
                }
            }
        } catch(IOException | DocumentException ex) {
        }
        
        
    }

    private void imprimeActa(String copia) throws IOException, DocumentException{
        String leyenda = obtieneLeyenda();
        
        Document document = new Document(PageSize.A4, 35, 30, 50, 50);
        FileOutputStream fileOutputStream = new FileOutputStream("ActaControladorFIscal"+copia+".pdf");
        
        PdfWriter writer = PdfWriter.getInstance(document, fileOutputStream);
        Rectangle rect = new Rectangle(30, 30, 580, 800);
        writer.setBoxSize("art", rect);
        EncabezadoYPieActa event = new EncabezadoYPieActa(leyenda, acta, copia);
        writer.setPageEvent(event);
        document.open();
        
        Font fontContenido = FontFactory.getFont(FontFactory.TIMES_ROMAN, 13, Font.NORMAL, BaseColor.BLACK);
        Font fontTitulos = FontFactory.getFont(FontFactory.TIMES_BOLDITALIC, 11, Font.UNDERLINE, BaseColor.RED);

        PdfPTable tablaFormulario = new PdfPTable(2);
         
         Font fontFormulario = FontFactory.getFont(FontFactory.TIMES_ROMAN, 16, Font.BOLD, BaseColor.BLACK);
         Phrase frase = new Phrase("          F. 8400/L", fontFormulario);
         PdfPCell cell = new PdfPCell(frase);
         cell.setRowspan(2);
         cell.setBorderColor(BaseColor.WHITE);
         cell.setHorizontalAlignment(Element.ALIGN_LEFT);
         cell.setBorder(0);
         tablaFormulario.addCell(cell);
         
         Font fontCopia = FontFactory.getFont(FontFactory.TIMES_ROMAN, 10, Font.NORMAL, BaseColor.DARK_GRAY);
         frase = new Phrase("ORIGINAL: para la Administración Federal", fontCopia);
         cell = new PdfPCell(frase);
         cell.setBorder(0);
         tablaFormulario.addCell(cell);
         
         frase = new Phrase("DUPLICADO: para el Ciudadano", fontCopia);
         cell = new PdfPCell(frase);
         cell.setBorder(0);
         tablaFormulario.addCell(cell);

         document.add(tablaFormulario);
         
         Paragraph paragraph = new Paragraph();
         paragraph.add(new Phrase(Chunk.NEWLINE));
         

         document.add(paragraph);
         
        PdfPTable tablaSirvase = new PdfPTable(1);
         
         fontCopia = FontFactory.getFont(FontFactory.TIMES_ROMAN, 10, Font.NORMAL, BaseColor.BLACK);
         frase = new Phrase("Sirvase citar", fontCopia);
         cell = new PdfPCell(frase);
         cell.setBorder(0);
         tablaSirvase.addCell(cell);
         
         document.add(tablaSirvase);
         
        Calendar cal = Calendar.getInstance();
        cal.setTimeZone(TimeZone.getTimeZone("America/Argentina/Buenos_Aires"));
        int dia= cal.get(Calendar.DATE);
        int mes = cal.get(Calendar.MONTH);
        int mesOk = mes+1;
        int ann = cal.get(Calendar.YEAR);
        int hora = cal.get(Calendar.HOUR_OF_DAY);
        int minutos = cal.get(Calendar.MINUTE); 
        //FormateaSalidasTexto fst = new FormateaSalidasTexto();
        String cuitFormateado = (df11.format(cf.getCuit()));
         
         paragraph
          .add(new Phrase("En la ciudad de Tigre, pcia de Bs. As. al " +df2.format(dia)+"/"+df2.format(mesOk)+"/"+df2.format(ann)
                         +" , siendo las "+df2.format(hora)+":"+df2.format(minutos) +" hs. comparece en la agencia 66 "
                         +" de la Administración Federal de Ingresos Públicos el Sr/a. " +cf.getNombre()
                         +" , Doc. N° "+cf.getDocumento() +" que exhibe, domiciliado en "+cf.getDireccion()
                         +", en su caracter de " +cf.getCaracter() +" de la entidad " +cf.getRazonSocial()
                         +" , inscripta bajo el N° de CUIT: " +fst.formatoCuit(cuitFormateado)  +", siendo atendido por el funcionario "
                         //+" , inscripta bajo el N° de CUIT: " +fst.formatoCuit(Double.toString(cf.getCuit()) )  +", siendo atendido por el funcionario "
                         +"de esta administración " +cf.getAgente() +", Legajo N°: " +cf.getLegajo() +". En este "
                         +"acto se solicita la autorización para obtener los comprobantes de auditoría"
                         +" y proceder a posteriori al bloqueo del controlador fiscal marca " +cf.getMarca()
                         +" modelo " +cf.getModelo() +" código: " +cf.getCodigo() +" N°: " +cf.getNumeroCodigo() +", punto de venta " +cf.getPuestoVenta() +", a lo que accede."
                         +" Los reportes obtenidos se detallan en el anexo IV que forma parte integrante de la presente."
                         +" Asimismo, se deja constancia que el/los precinto/s fiscal/es N°" +cf.getPrecinto() +" se observa intacto."
                         +" Se deja constancia en este acto que el servicio técnico que realice la remoción de la "        
                         +"memoria fiscal, está obligadoa entregar al contribuyente un remito donde consten "
                         +" a) N° de registro del controlador fiscal, b) identificación del técnico autorizado actuante"
                         +" mediante indicación del nombre y apellido, n° de credencial, firma y sello. No siendo para más"
                         +", previa lectura a viva voz y ratificación de la presente, se firman de conformidad tres (3) "
                         +"ejemplares de un mismo tenor y a un solo efecto el acta N°" +acta +",recibiendo el/la Sr/a. " +cf.getNombre()
                         +", duplicado de la misma. Conste.-",fontContenido));

        
            paragraph.setAlignment(Element.ALIGN_JUSTIFIED);
            // Agregar saltos de linea
            document.add(paragraph);
        
            Paragraph psl = new  Paragraph();
                    
            for(int i=0; i<5; i++) {
                psl.add(new Phrase(Chunk.NEWLINE));
                document.add(psl);
        }
       
        PdfPTable tablaFirma = new PdfPTable(2);
        
        Font fontFirma = FontFactory.getFont(FontFactory.COURIER, 8, Font.ITALIC, BaseColor.BLACK);
        frase =  new Phrase(cf.getNombre(), fontFirma);
        cell =  new PdfPCell(frase);
        cell.setBorderColor(BaseColor.WHITE);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        tablaFirma.addCell(cell);
        
        frase =  new Phrase(cf.getAgente(), fontFirma);
        cell =  new PdfPCell(frase);
        cell.setBorderColor(BaseColor.WHITE);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        tablaFirma.addCell(cell);
        
        frase =  new Phrase(cf.getDireccion(), fontFirma);
        cell =  new PdfPCell(frase);
        cell.setBorderColor(BaseColor.WHITE);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        tablaFirma.addCell(cell);
        
        frase =  new Phrase("Legajo N°" +cf.getLegajo(), fontFirma);
        cell =  new PdfPCell(frase);
        cell.setBorderColor(BaseColor.WHITE);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        tablaFirma.addCell(cell);
        
        frase =  new Phrase("Doc N°: " +cf.getDocumento() + " - " +cf.getCaracter(), fontFirma);
        cell =  new PdfPCell(frase);
        cell.setBorderColor(BaseColor.WHITE);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        tablaFirma.addCell(cell);
        
        frase =  new Phrase(Chunk.NEWLINE);
        cell =  new PdfPCell(frase);
        cell.setBorderColor(BaseColor.WHITE);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        tablaFirma.addCell(cell);
        
        document.add(tablaFirma);
        
        document.close();
 
        // Abrir el archivo
        File file = new File("ActaControladorFIscal" +copia+".pdf");
        Desktop.getDesktop().print(file);
        
    }

    private void imprimeAnexo4() throws IOException, DocumentException{

        Document document = new Document(PageSize.A4, 35, 30, 50, 50);
        FileOutputStream fileOutputStream = new FileOutputStream("Anexo4.pdf");
        
        PdfWriter writer = PdfWriter.getInstance(document, fileOutputStream);
        Rectangle rect = new Rectangle(30, 30, 580, 800);
        writer.setBoxSize("art", rect);
        EncabezadoAnexo4 event = new EncabezadoAnexo4();
        writer.setPageEvent(event);
        document.open();
        
        
        Font fontTitulos = FontFactory.getFont(FontFactory.COURIER, 8, Font.BOLDITALIC, BaseColor.BLACK);
        PdfPCell cell;

        Paragraph parrafo =  new Paragraph();
        Phrase frase = new Phrase();

/** Imprime el cuit del Controlador Fiscal */
        PdfPTable tablaCuit = new PdfPTable(1);
        cell = new PdfPCell(new Phrase(Chunk.NEWLINE));
        cell.setBorder(0);
        tablaCuit.addCell(cell);
        String cuitFormateado = (df11.format(cf.getCuit()));
        cell = new PdfPCell(new Phrase("C.U.I.T: "+ fst.formatoCuit(cuitFormateado), fontTitulos));
        cell.setBorder(0);
        tablaCuit.addCell(cell);
        cell = new PdfPCell(new Phrase(Chunk.NEWLINE));
        cell.setBorder(0);
        tablaCuit.addCell(cell);
        document.add(tablaCuit);
        

/** Imprime la razon Social en tabla de 2 columnas */        
        PdfPTable tablaRZ = new PdfPTable(2);
        Font fontApellido = FontFactory.getFont(FontFactory.COURIER, 8, Font.BOLD, BaseColor.BLACK);
        cell =  new PdfPCell(new Phrase("APELLIDO Y NOMBRE O RAZON SOCIAL",fontApellido));
        cell.setBackgroundColor(BaseColor.GRAY);
        tablaRZ.addCell(cell);
        fontApellido = FontFactory.getFont(FontFactory.COURIER, 8, Font.NORMAL, BaseColor.BLACK);
        cell =  new PdfPCell(new Phrase(cf.getRazonSocial(),fontApellido));
        cell.setBackgroundColor(BaseColor.WHITE);
        tablaRZ.addCell(cell);
        document.add(tablaRZ);
        frase = new Phrase(Chunk.NEWLINE);
       // document.add(frase);
        
/** Imprime el titulo de la tabla del controlador Fiscal */        
        PdfPTable tablaTitulo = new PdfPTable(2);
        float[] medidasCeldas = {0.04f, 1.50f};
        tablaTitulo.setWidths(medidasCeldas);
        fontTitulos = FontFactory.getFont(FontFactory.COURIER, 8, Font.BOLD, BaseColor.BLACK);

        cell = new PdfPCell(new Phrase("1", fontTitulos));
        cell.setBackgroundColor(BaseColor.DARK_GRAY);
        tablaTitulo.addCell(cell);
        
        cell = new PdfPCell(new Phrase("DATOS DEL CONTROLADOR FISCAL", fontTitulos));
        cell.setBackgroundColor(BaseColor.GRAY);
        tablaTitulo.addCell(cell);
        document.add(tablaTitulo);

/** Imprime la primer parte de la tabla del Controlador Fiscal */        
        PdfPTable tablacf = new PdfPTable(5);
        float[] medidasCeldas2 = {0.16f, 1.50f, 1.50f, 1.50f, 1.50f};
        tablacf.setWidths(medidasCeldas2);
        cell = new PdfPCell(new Phrase(Chunk.NEWLINE));
        cell.setBorder(0);
        tablacf.addCell(cell); /* La primera va en blanco para simular un identado */
        
           /* TITULOS */
        cell = new PdfPCell(new Phrase("EQUIPO", fontApellido));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        tablacf.addCell(cell);
        cell = new PdfPCell(new Phrase("Según F445/D", fontApellido));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        tablacf.addCell(cell);
        cell = new PdfPCell(new Phrase("Según Equipo", fontApellido));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        tablacf.addCell(cell);
        cell = new PdfPCell(new Phrase("Según L.U.R.", fontApellido));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        tablacf.addCell(cell);
        
        cell = new PdfPCell(new Phrase(Chunk.NEWLINE));
        cell.setBorder(0);
        tablacf.addCell(cell); /* La primera va en blanco para simular un identado */
        cell = new PdfPCell(new Phrase("MARCA", fontApellido));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        tablacf.addCell(cell);
        cell = new PdfPCell(new Phrase(""));
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        tablacf.addCell(cell);
        cell = new PdfPCell(new Phrase(cf.getMarca(), fontApellido));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        tablacf.addCell(cell);
        cell = new PdfPCell(new Phrase(cf.getMarca(), fontApellido));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        tablacf.addCell(cell);
        
        cell = new PdfPCell(new Phrase(Chunk.NEWLINE));
        cell.setBorder(0);
        tablacf.addCell(cell); /* La primera va en blanco para simular un identado */
        cell = new PdfPCell(new Phrase("MODELO", fontApellido));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        tablacf.addCell(cell);
        cell = new PdfPCell(new Phrase(""));
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        tablacf.addCell(cell);
        cell = new PdfPCell(new Phrase(cf.getModelo(), fontApellido));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        tablacf.addCell(cell);
        cell = new PdfPCell(new Phrase(cf.getModelo(), fontApellido));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        tablacf.addCell(cell);
        
        cell = new PdfPCell(new Phrase(Chunk.NEWLINE));
        cell.setBorder(0);
        tablacf.addCell(cell); /* La primera va en blanco para simular un identado */
        cell = new PdfPCell(new Phrase("CODIGO", fontApellido));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        tablacf.addCell(cell);
        cell = new PdfPCell(new Phrase(cf.getCodigo(), fontApellido));
        tablacf.addCell(cell);
        cell = new PdfPCell(new Phrase(cf.getCodigo(), fontApellido));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        tablacf.addCell(cell);
        cell = new PdfPCell(new Phrase(cf.getCodigo(), fontApellido));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        tablacf.addCell(cell);
        
        cell = new PdfPCell(new Phrase(Chunk.NEWLINE));
        cell.setBorder(0);
        tablacf.addCell(cell); /* La primera va en blanco para simular un identado */
        cell = new PdfPCell(new Phrase("N° de SERIE", fontApellido));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        tablacf.addCell(cell);
        cell = new PdfPCell(new Phrase(cf.getNumeroCodigo(), fontApellido));
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        tablacf.addCell(cell);
        cell = new PdfPCell(new Phrase(cf.getNumeroCodigo(), fontApellido));
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        tablacf.addCell(cell);
        cell = new PdfPCell(new Phrase(cf.getNumeroCodigo(), fontApellido));
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        tablacf.addCell(cell);
        
        cell = new PdfPCell(new Phrase(Chunk.NEWLINE));
        cell.setBorder(0);
        tablacf.addCell(cell); /* La primera va en blanco para simular un identado */
        cell = new PdfPCell(new Phrase("PUNTO DE VENTA", fontApellido));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        tablacf.addCell(cell);
        cell = new PdfPCell(new Phrase(Integer.toString(cf.getPuestoVenta()) , fontApellido));
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        tablacf.addCell(cell);
        cell = new PdfPCell(new Phrase(Integer.toString(cf.getPuestoVenta()), fontApellido));
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        tablacf.addCell(cell);
        cell = new PdfPCell(new Phrase(Integer.toString(cf.getPuestoVenta()), fontApellido));
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        tablacf.addCell(cell);
        
        cell = new PdfPCell(new Phrase(Chunk.NEWLINE));
        cell.setBorder(0);
        tablacf.addCell(cell); /* La primera va en blanco para simular un identado */
        cell = new PdfPCell(new Phrase("PRECINTO", fontApellido));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        tablacf.addCell(cell);
        cell = new PdfPCell(new Phrase(cf.getPrecinto445(), fontApellido));
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        tablacf.addCell(cell);
        cell = new PdfPCell(new Phrase(cf.getPrecinto(), fontApellido));
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        tablacf.addCell(cell);
        cell = new PdfPCell(new Phrase(cf.getPrecintolur(), fontApellido));
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        tablacf.addCell(cell);
        
               /*Registros personalizados del final */
        cell = new PdfPCell(new Phrase(Chunk.NEWLINE));
        cell.setBorder(0);
        tablacf.addCell(cell); /* La primera va en blanco para simular un identado */
        cell = new PdfPCell(new Phrase("INTERVENCIONES DEL SERVICIO TECNICO ASENTADAS", fontApellido));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setColspan(3);
        tablacf.addCell(cell);
        cell = new PdfPCell(new Phrase(Integer.toString(cf.getIntervenTecnico()) , fontApellido));
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        tablacf.addCell(cell);
        
        cell = new PdfPCell(new Phrase(Chunk.NEWLINE));
        cell.setBorder(0);
        tablacf.addCell(cell); /* La primera va en blanco para simular un identado */
        cell = new PdfPCell(new Phrase("INTERVENCIONES DE AFIP ASENTADAS", fontApellido));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setColspan(3);
        tablacf.addCell(cell);
        cell = new PdfPCell(new Phrase(Integer.toString(cf.getIntervenAfip()), fontApellido));
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        tablacf.addCell(cell);
        
        cell = new PdfPCell(new Phrase(Chunk.NEWLINE));
        cell.setBorder(0);
        tablacf.addCell(cell); /* La primera va en blanco para simular un identado */
        cell = new PdfPCell(new Phrase("ESTADO", fontApellido));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        tablacf.addCell(cell);
        cell = new PdfPCell(new Phrase(cf.getEstado(), fontApellido));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        tablacf.addCell(cell);
        cell = new PdfPCell(new Phrase("EL C.FISCAL SE ENCUENTRA OPERATIVO", fontApellido));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        tablacf.addCell(cell);
        cell = new PdfPCell(new Phrase(cf.getOperativo()==1?"SI":"NO", fontApellido));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        tablacf.addCell(cell);
        
        cell = new PdfPCell(new Phrase(Chunk.NEWLINE));
        cell.setBorder(0);
        tablacf.addCell(cell); /* La primera va en blanco para simular un identado */
        cell = new PdfPCell(new Phrase("OBSERVACIONES:" +cf.getObservaciones1(), fontApellido));
        cell.setColspan(4);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        tablacf.addCell(cell);

        document.add(tablacf);
        //document.add(frase);

/** Imprime el titulo de la tabla del Cierre Z */        
        tablaTitulo = new PdfPTable(3);
        float[] medidasCeldasTitulos = {0.05f, 2.0f, 0.25f};
        tablaTitulo.setWidths(medidasCeldasTitulos);
        fontTitulos = FontFactory.getFont(FontFactory.COURIER, 10, Font.BOLD, BaseColor.BLACK);

        cell = new PdfPCell(new Phrase("2", fontTitulos));
        cell.setBackgroundColor(BaseColor.DARK_GRAY);
        tablaTitulo.addCell(cell);
        cell = new PdfPCell(new Phrase("CIERRE \"Z\" - SE OBTUVO EL COMPROBANTE DIARIO DE CIERRE Z DE FECHA", fontTitulos));
        cell.setBackgroundColor(BaseColor.GRAY);
        tablaTitulo.addCell(cell);
        cell = new PdfPCell(new Phrase(cf.getTieneultimoZ()==1?"SI":"NO", fontApellido));
               
        tablaTitulo.addCell(cell);
        document.add(tablaTitulo);

        /* REGISTROS DE LA TABLA */
        float[] medidasCeldas3 = {0.12f, 1.70f, 0.60f, 2.50f, 0.60f};
        tablacf = new PdfPTable(5);
        tablacf.setWidths(medidasCeldas3);
        cell = new PdfPCell(new Phrase(Chunk.NEWLINE));
        cell.setBorder(0);
        tablacf.addCell(cell); /* La primera va en blanco para simular un identado */        
        cell = new PdfPCell(new Phrase("PUNTO DE VENTA", fontApellido));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        tablacf.addCell(cell);
        cell = new PdfPCell(new Phrase(String.valueOf(cf.getPuestoVenta()), fontApellido));
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        tablacf.addCell(cell);
        cell = new PdfPCell(new Phrase("LOGOTIPO FISCAL CORRECTO", fontApellido));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        tablacf.addCell(cell);
        cell = new PdfPCell(new Phrase(cf.getLogo()==1?"SI":"NO", fontApellido));
        tablacf.addCell(cell);
        cell = new PdfPCell(new Phrase(Chunk.NEWLINE));
        cell.setBorder(0);
        tablacf.addCell(cell); /* La primera va en blanco para simular un identado */        
        cell = new PdfPCell(new Phrase("COMPROBANTE Z N°", fontApellido));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        tablacf.addCell(cell);
        cell = new PdfPCell(new Phrase(String.valueOf(cf.getCompZ()), fontApellido));
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        tablacf.addCell(cell);
        cell = new PdfPCell(new Phrase("N° SERIE CORRECTO", fontApellido));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        tablacf.addCell(cell);
        cell = new PdfPCell(new Phrase(cf.getSerie()==1?"SI":"NO", fontApellido));
        tablacf.addCell(cell);
        cell = new PdfPCell(new Phrase(Chunk.NEWLINE));
        cell.setBorder(0);
        tablacf.addCell(cell); /* La primera va en blanco para simular un identado */        
        cell = new PdfPCell(new Phrase("TOTAL VENTA DIARIA", fontApellido));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        tablacf.addCell(cell);
        cell = new PdfPCell(new Phrase(dfImp.format(cf.getTotalDiaria()), fontApellido));
        //cell = new PdfPCell(new Phrase(String.valueOf(cf.getTotalDiaria()), fontApellido));
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        tablacf.addCell(cell);
        cell = new PdfPCell(new Phrase("FECHA CORRECTA", fontApellido));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        tablacf.addCell(cell);
        cell = new PdfPCell(new Phrase(cf.getFecha()==1?"SI":"NO", fontApellido));
        tablacf.addCell(cell);

        cell = new PdfPCell(new Phrase(Chunk.NEWLINE));
        cell.setBorder(0);
        tablacf.addCell(cell); /* La primera va en blanco para simular un identado */        
        cell = new PdfPCell(new Phrase("TOTAL IVA DIARIO", fontApellido));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        tablacf.addCell(cell);
        cell = new PdfPCell(new Phrase(dfImp.format(cf.getTotalIvaDiario()), fontApellido));
        //cell = new PdfPCell(new Phrase(String.valueOf(cf.getTotalIvaDiario()), fontApellido));
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        tablacf.addCell(cell);
        cell = new PdfPCell(new Phrase("ULTIMO TICKET B/C EMITIDO", fontApellido));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        tablacf.addCell(cell);
        cell = new PdfPCell(new Phrase(String.valueOf(cf.getUltimocingresado()), fontApellido));
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        tablacf.addCell(cell);
        
        cell = new PdfPCell(new Phrase(Chunk.NEWLINE));
        cell.setBorder(0);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        tablacf.addCell(cell); /* La primera va en blanco para simular un identado */        
        cell = new PdfPCell(new Phrase("CONCEPTOS NO GRAVADOS", fontApellido));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        tablacf.addCell(cell);
        cell = new PdfPCell(new Phrase(dfImp.format(cf.getNoGravados()), fontApellido));
        //cell = new PdfPCell(new Phrase(String.valueOf(cf.getNoGravados()), fontApellido));
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        tablacf.addCell(cell);
        cell = new PdfPCell(new Phrase("ÚLTIMA FACTURA A EMITIDA", fontApellido));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        tablacf.addCell(cell);
        cell = new PdfPCell(new Phrase("0", fontApellido));
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        tablacf.addCell(cell);

        document.add(tablacf);
      //  document.add(frase);

        /* ENCABEZADO DE TABLA LECTURA Y CAPTURA */
        
        /** Imprime el titulo de la tabla del Cierre Z */        
        tablaTitulo = new PdfPTable(2);
        float[] medidasCeldasTitulos2 = {0.05f, 2.0f};
        tablaTitulo.setWidths(medidasCeldasTitulos2);
        
        cell = new PdfPCell(new Phrase("3", fontTitulos));
        cell.setBackgroundColor(BaseColor.DARK_GRAY);
        tablaTitulo.addCell(cell);
        cell = new PdfPCell(new Phrase("LECTURA Y CAPTURA DE DATOS DE LA MEMORIA FISCAL", fontTitulos));
        cell.setBackgroundColor(BaseColor.GRAY);
        tablaTitulo.addCell(cell);
        document.add(tablaTitulo);

        /* REGISTROS DE LA TABLA */
        
        float[] medidasCeldas4 = {0.14f, 1.50f, 0.80f, 2.30f, 0.80f};
        tablacf = new PdfPTable(5);
        tablacf.setWidths(medidasCeldas4);
        
        cell = new PdfPCell(new Phrase(Chunk.NEWLINE));
        cell.setBorder(0);
        tablacf.addCell(cell); /* La primera va en blanco para simular un identado */        
        cell = new PdfPCell(new Phrase("TRANSFERENCIA DE DATOS A LA PC", fontApellido));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setColspan(4);
        tablacf.addCell(cell);
        
        cell = new PdfPCell(new Phrase(Chunk.NEWLINE));
        cell.setBorder(0);
        tablacf.addCell(cell); /* La primera va en blanco para simular un identado */        
        cell = new PdfPCell(new Phrase("SE EXTRAJERON LOS DATOS DE LA MEMORIA FISCAL Y SE TRANSFIRIERON A LA PC", fontApellido));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setColspan(3);
        tablacf.addCell(cell);
        cell = new PdfPCell(new Phrase("SI", fontApellido));
        cell.setHorizontalAlignment(Element.ALIGN_MIDDLE);
        tablacf.addCell(cell);

        cell = new PdfPCell(new Phrase(Chunk.NEWLINE));
        cell.setBorder(0);
        tablacf.addCell(cell); /* La primera va en blanco para simular un identado */        
        cell = new PdfPCell(new Phrase("COMPROBANTE GLOBAL DE AUDITORIA", fontApellido));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setColspan(4);
        tablacf.addCell(cell);
        
        cell = new PdfPCell(new Phrase(Chunk.NEWLINE));
        cell.setBorder(0);
        tablacf.addCell(cell); /* La primera va en blanco para simular un identado */        
        cell = new PdfPCell(new Phrase("SE EMITIO EL COMPROBANTE GLOBAL DE AUDITORIA", fontApellido));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setColspan(3);
        tablacf.addCell(cell);
        cell = new PdfPCell(new Phrase(cf.getCompaudit()==1?"SI":"NO", fontApellido));
        cell.setHorizontalAlignment(Element.ALIGN_MIDDLE);
        tablacf.addCell(cell);
        
        cell = new PdfPCell(new Phrase(Chunk.NEWLINE));
        cell.setBorder(0);
        tablacf.addCell(cell); /* La primera va en blanco para simular un identado */        
        cell = new PdfPCell(new Phrase("PUNTO DE VENTA N°", fontApellido));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setColspan(3);
        tablacf.addCell(cell);
        cell = new PdfPCell(new Phrase( Integer.toString(cf.getPuestoVenta()), fontApellido));
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        tablacf.addCell(cell);

        cell = new PdfPCell(new Phrase(Chunk.NEWLINE));
        cell.setBorder(0);
        tablacf.addCell(cell); /* La primera va en blanco para simular un identado */        
        cell = new PdfPCell(new Phrase("COMPROBANTE Z DESDE", fontApellido));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        tablacf.addCell(cell);
        cell = new PdfPCell(new Phrase(Integer.toString(cf.getZDesde()), fontApellido));
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        tablacf.addCell(cell);
        cell = new PdfPCell(new Phrase("HASTA", fontApellido));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        tablacf.addCell(cell);
        cell = new PdfPCell(new Phrase(Integer.toString(cf.getZHasta()), fontApellido));
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        tablacf.addCell(cell);
        
        cell = new PdfPCell(new Phrase(Chunk.NEWLINE));
        cell.setBorder(0);
        tablacf.addCell(cell); /* La primera va en blanco para simular un identado */        
        cell = new PdfPCell(new Phrase("PERIODO LECTURA DESDE", fontApellido));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        tablacf.addCell(cell);
        cell = new PdfPCell(new Phrase(fst.fechaImprimir(cf.getFechaInicial()), fontApellido));
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        tablacf.addCell(cell);
        cell = new PdfPCell(new Phrase("FECHA HASTA", fontApellido));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        tablacf.addCell(cell);
        cell = new PdfPCell(new Phrase(fst.fechaImprimir(cf.getFechaFinal()), fontApellido));
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        tablacf.addCell(cell);
        
        cell = new PdfPCell(new Phrase(Chunk.NEWLINE));
        cell.setBorder(0);
        tablacf.addCell(cell); /* La primera va en blanco para simular un identado */        
        cell = new PdfPCell(new Phrase("MONTO TOTAL VENTAS ACUMULADO", fontApellido));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setColspan(3);
        tablacf.addCell(cell);
        cell = new PdfPCell(new Phrase(dfImp.format(cf.getVentaingresado()), fontApellido));
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        tablacf.addCell(cell);

        cell = new PdfPCell(new Phrase(Chunk.NEWLINE));
        cell.setBorder(0);
        tablacf.addCell(cell); /* La primera va en blanco para simular un identado */        
        cell = new PdfPCell(new Phrase("MONTO TOTAL IVA ACUMULADO", fontApellido));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setColspan(3);
        tablacf.addCell(cell);
        cell = new PdfPCell(new Phrase(dfImp.format(cf.getIvaingresado()), fontApellido));
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        tablacf.addCell(cell);

        cell = new PdfPCell(new Phrase(Chunk.NEWLINE));
        cell.setBorder(0);
        tablacf.addCell(cell); /* La primera va en blanco para simular un identado */        
        cell = new PdfPCell(new Phrase("CONCEPTOS NO GRAVADOS", fontApellido));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setColspan(3);
        tablacf.addCell(cell);
        cell = new PdfPCell(new Phrase(dfImp.format(cf.getNogravadoingresado()), fontApellido));
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        tablacf.addCell(cell);
        
        cell = new PdfPCell(new Phrase(Chunk.NEWLINE));
        cell.setBorder(0);
        tablacf.addCell(cell); /* La primera va en blanco para simular un identado */        
        cell = new PdfPCell(new Phrase("ULTIMO N° COMPROBANTE FISCAL EMITIDO, TICKET O FACTURA B O C", fontApellido));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setColspan(3);
        tablacf.addCell(cell);
        cell = new PdfPCell(new Phrase(Integer.toString(cf.getUltimocingresado()), fontApellido));
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        tablacf.addCell(cell);

        cell = new PdfPCell(new Phrase(Chunk.NEWLINE));
        cell.setBorder(0);
        tablacf.addCell(cell); /* La primera va en blanco para simular un identado */        
        cell = new PdfPCell(new Phrase("ULTIMO N° COMPROBANTE FISCAL EMITIDO FACTURA A", fontApellido));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setColspan(3);
        tablacf.addCell(cell);
        cell = new PdfPCell(new Phrase(Integer.toString(cf.getUltimoaingresado()), fontApellido));
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        tablacf.addCell(cell);
        
        cell = new PdfPCell(new Phrase(Chunk.NEWLINE));
        cell.setBorder(0);
        tablacf.addCell(cell); /* La primera va en blanco para simular un identado */        
        cell = new PdfPCell(new Phrase("CANTIDAD DE BLOQUEOS", fontApellido));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setColspan(3);
        tablacf.addCell(cell);
        cell = new PdfPCell(new Phrase(Integer.toString(cf.getBloqueos()), fontApellido));
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        tablacf.addCell(cell);
        
        cell = new PdfPCell(new Phrase(Chunk.NEWLINE));
        cell.setBorder(0);
        tablacf.addCell(cell); /* La primera va en blanco para simular un identado */        
        cell = new PdfPCell(new Phrase("LOGOTIPO FISCAL CORRECTO", fontApellido));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setColspan(3);
        tablacf.addCell(cell);
        cell = new PdfPCell(new Phrase(cf.getLogo()==1?"SI":"NO", fontApellido));
        cell.setHorizontalAlignment(Element.ALIGN_MIDDLE);
        tablacf.addCell(cell);
        
        cell = new PdfPCell(new Phrase(Chunk.NEWLINE));
        cell.setBorder(0);
        tablacf.addCell(cell); /* La primera va en blanco para simular un identado */        
        cell = new PdfPCell(new Phrase("N° DE SERIE CORRECTO", fontApellido));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setColspan(3);
        tablacf.addCell(cell);
        cell = new PdfPCell(new Phrase(cf.getSerie()==1?"SI":"NO", fontApellido));
        cell.setHorizontalAlignment(Element.ALIGN_MIDDLE);
        tablacf.addCell(cell);
        
        cell = new PdfPCell(new Phrase(Chunk.NEWLINE));
        cell.setBorder(0);
        tablacf.addCell(cell); /* La primera va en blanco para simular un identado */        
        cell = new PdfPCell(new Phrase("FECHA CORRECTA", fontApellido));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setColspan(3);
        tablacf.addCell(cell);
        cell = new PdfPCell(new Phrase(cf.getFecha()==1?"SI":"NO", fontApellido));
        cell.setHorizontalAlignment(Element.ALIGN_MIDDLE);
        tablacf.addCell(cell);
        
        cell = new PdfPCell(new Phrase(Chunk.NEWLINE));
        cell.setBorder(0);
        tablacf.addCell(cell); /* La primera va en blanco para simular un identado */        
        cell = new PdfPCell(new Phrase("SE ADJUNTA COMPROBANTE GLOBAL DE AUDITORIA", fontApellido));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setColspan(3);
        tablacf.addCell(cell);
        cell = new PdfPCell(new Phrase(cf.getCompaudit()==1?"SI":"NO", fontApellido));
        cell.setHorizontalAlignment(Element.ALIGN_MIDDLE);
        tablacf.addCell(cell);    
        
        cell = new PdfPCell(new Phrase(Chunk.NEWLINE));
        cell.setBorder(0);
        tablacf.addCell(cell); /* La primera va en blanco para simular un identado */        
        cell = new PdfPCell(new Phrase("NO SE TRANSFIRIERON LOS DATOS DE LA MEMORIA FISCAL DEL CF A LA PC", fontApellido));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setColspan(4);
        tablacf.addCell(cell);
        
        cell = new PdfPCell(new Phrase(Chunk.NEWLINE));
        cell.setBorder(0);
        tablacf.addCell(cell); /* La primera va en blanco para simular un identado */        
        cell = new PdfPCell(new Phrase("SE EMITIO Y SE ADJUNTA EL COMPROBANTE DETALLADO DE AUDITORIA Z POR Z DEL PERIODO DE IVA", fontApellido));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setColspan(3);
        tablacf.addCell(cell);
        cell = new PdfPCell(new Phrase("NO", fontApellido));
        cell.setHorizontalAlignment(Element.ALIGN_MIDDLE);
        tablacf.addCell(cell);    
        
        cell = new PdfPCell(new Phrase(Chunk.NEWLINE));
        cell.setBorder(0);
        tablacf.addCell(cell); /* La primera va en blanco para simular un identado */        
        cell = new PdfPCell(new Phrase("PERIODO FISCALES DESDE", fontApellido));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        tablacf.addCell(cell);
        cell = new PdfPCell(new Phrase(fst.fechaImprimir(cf.getFechaInicial()), fontApellido));
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        tablacf.addCell(cell);
        cell = new PdfPCell(new Phrase("FECHA HASTA", fontApellido));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        tablacf.addCell(cell);
        cell = new PdfPCell(new Phrase(fst.fechaImprimir(cf.getFechaFinal()), fontApellido));
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        tablacf.addCell(cell);
        
        cell = new PdfPCell(new Phrase(Chunk.NEWLINE));
        cell.setBorder(0);
        tablacf.addCell(cell); /* La primera va en blanco para simular un identado */        
        cell = new PdfPCell(new Phrase("SE RECIBIO LA CARCAZA CON LA MEMORIA FISCAL DEL CONTROLADOR FISCAL", fontApellido));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setColspan(3);
        tablacf.addCell(cell);
        cell = new PdfPCell(new Phrase("SI | NO", fontApellido));
        cell.setHorizontalAlignment(Element.ALIGN_MIDDLE);
        tablacf.addCell(cell);    
        
        cell = new PdfPCell(new Phrase(Chunk.NEWLINE));
        cell.setBorder(0);
        tablacf.addCell(cell); /* La primera va en blanco para simular un identado */
        cell = new PdfPCell(new Phrase("OBSERVACIONES:" +cf.getObservaciones2(), fontApellido));
        cell.setColspan(4);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        tablacf.addCell(cell);
        document.add(tablacf);

    /* ENCABEZADO DE TABLA BLOQUEO */
        
        /** Imprime el titulo de la tabla del Cierre Z */        
        tablaTitulo = new PdfPTable(2);
        //float[] medidasCeldasTitulos2 = {0.05f, 2.0f};
        tablaTitulo.setWidths(medidasCeldasTitulos2);
        
        cell = new PdfPCell(new Phrase("4", fontTitulos));
        cell.setBackgroundColor(BaseColor.DARK_GRAY);
        tablaTitulo.addCell(cell);
        cell = new PdfPCell(new Phrase("BLOQUEO DEL CONTROLADOR FISCAL", fontTitulos));
        cell.setBackgroundColor(BaseColor.GRAY);
        tablaTitulo.addCell(cell);
        document.add(tablaTitulo);

        /* REGISTROS DE LA TABLA */
        
        //float[] medidasCeldas4 = {0.14f, 1.50f, 0.80f, 2.30f, 0.80f};
        tablacf = new PdfPTable(5);
        tablacf.setWidths(medidasCeldas4);
        
        cell = new PdfPCell(new Phrase(Chunk.NEWLINE));
        cell.setBorder(0);
        tablacf.addCell(cell); /* La primera va en blanco para simular un identado */        
        cell = new PdfPCell(new Phrase("SE EFECTUO EXITOSAMENTE EL BLOQUEO DEL CONTROLADOR FISCAL", fontApellido));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setColspan(3);
        tablacf.addCell(cell);
        cell = new PdfPCell(new Phrase(cf.getBloqueo()==1?"SI":"NO", fontApellido));
        cell.setHorizontalAlignment(Element.ALIGN_MIDDLE);
        tablacf.addCell(cell); 

        cell = new PdfPCell(new Phrase(Chunk.NEWLINE));
        cell.setBorder(0);
        tablacf.addCell(cell); /* La primera va en blanco para simular un identado */        
        cell = new PdfPCell(new Phrase("EL CONTROLADOR FISCAL EMITIO Y SE ADJUNTA COMPROBANTE QUE ACREDITA SU BLOQUEO", fontApellido));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setColspan(3);
        tablacf.addCell(cell);
        cell = new PdfPCell(new Phrase("SI | NO", fontApellido));
        cell.setHorizontalAlignment(Element.ALIGN_MIDDLE);
        tablacf.addCell(cell); 
        
        cell = new PdfPCell(new Phrase(Chunk.NEWLINE));
        cell.setBorder(0);
        tablacf.addCell(cell); /* La primera va en blanco para simular un identado */        
        cell = new PdfPCell(new Phrase("SE RECIBIO CARCAZA CON LA MEMORIA FISCAL DEL CONTROLADOR FISCAL", fontApellido));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setColspan(3);
        tablacf.addCell(cell);
        cell = new PdfPCell(new Phrase("SI | NO", fontApellido));
        cell.setHorizontalAlignment(Element.ALIGN_MIDDLE);
        tablacf.addCell(cell); 
        
        cell = new PdfPCell(new Phrase(Chunk.NEWLINE));
        cell.setBorder(0);
        tablacf.addCell(cell); /* La primera va en blanco para simular un identado */
        cell = new PdfPCell(new Phrase("OBSERVACIONES:", fontApellido));
        cell.setColspan(4);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        tablacf.addCell(cell);

        document.add(tablacf);
   
        /* ENCABEZADO DE TABLA INTERVENCION LUR */
        
        /** Imprime el titulo de la tabla del Cierre Z */        
        tablaTitulo = new PdfPTable(2);
        //float[] medidasCeldasTitulos2 = {0.05f, 2.0f};
        tablaTitulo.setWidths(medidasCeldasTitulos2);
        
        cell = new PdfPCell(new Phrase("5", fontTitulos));
        cell.setBackgroundColor(BaseColor.DARK_GRAY);
        tablaTitulo.addCell(cell);
        cell = new PdfPCell(new Phrase("INTERVENCION DEL LIBRO ÚNICO DE REGISTRO (L.U.R)", fontTitulos));
        cell.setBackgroundColor(BaseColor.GRAY);
        tablaTitulo.addCell(cell);
        document.add(tablaTitulo);

        /* REGISTROS DE LA TABLA */
        
        //float[] medidasCeldas4 = {0.14f, 1.50f, 0.80f, 2.30f, 0.80f};
        tablacf = new PdfPTable(5);
        tablacf.setWidths(medidasCeldas4);
        
        cell = new PdfPCell(new Phrase(Chunk.NEWLINE));
        cell.setBorder(0);
        tablacf.addCell(cell); /* La primera va en blanco para simular un identado */        
        cell = new PdfPCell(new Phrase("SE EFECTUO LA INTERVENCIÓN DEL LUR", fontApellido));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setColspan(3);
        tablacf.addCell(cell);
        cell = new PdfPCell(new Phrase(cf.getInterlur()==1?"SI":"NO", fontApellido));
        cell.setHorizontalAlignment(Element.ALIGN_MIDDLE);
        tablacf.addCell(cell); 
        
        cell = new PdfPCell(new Phrase(Chunk.NEWLINE));
        cell.setBorder(0);
        tablacf.addCell(cell); /* La primera va en blanco para simular un identado */        
        cell = new PdfPCell(new Phrase("LA CANTIDAD DE BLOQUEOS DEL COMPROBANTE GLOBAL DE AUDITORIA COINCIDE CON LAS ASENTADAS EN EL LUR", fontApellido));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setColspan(3);
        tablacf.addCell(cell);
        cell = new PdfPCell(new Phrase(cf.getCoincidebloqueo()==1?"SI":"NO", fontApellido));
        cell.setHorizontalAlignment(Element.ALIGN_MIDDLE);
        tablacf.addCell(cell);
        
        cell = new PdfPCell(new Phrase(Chunk.NEWLINE));
        cell.setBorder(0);
        tablacf.addCell(cell); /* La primera va en blanco para simular un identado */        
        cell = new PdfPCell(new Phrase("FOJA N° EN LA QUE SE INTERVINO EL LUR", fontApellido));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setColspan(3);
        tablacf.addCell(cell);
        cell = new PdfPCell(new Phrase(cf.getPaginaAfip(), fontApellido));
        cell.setHorizontalAlignment(Element.ALIGN_MIDDLE);
        tablacf.addCell(cell);
        
        cell = new PdfPCell(new Phrase(Chunk.NEWLINE));
        cell.setBorder(0);
        tablacf.addCell(cell); /* La primera va en blanco para simular un identado */
        cell = new PdfPCell(new Phrase("OBSERVACIONES:", fontApellido));
        cell.setColspan(4);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        tablacf.addCell(cell);

        document.add(tablacf);
        
        document.add(new Phrase(Chunk.NEWLINE));
        frase = new Phrase("El presente forma parte integrante del acta N° " +cf.getActa() +", de fecha "  + fst.fechaImprimir(cf.getFechaPoceso()) +" como Anexo IV", fontApellido);
        document.add(frase);
        
        document.add(new Phrase(Chunk.NEWLINE));
        document.add(new Phrase(Chunk.NEWLINE));
     
        frase = new Phrase("LUGAR Y FECHA: Tigre " + fst.fechaImprimir(cf.getFechaPoceso()), fontApellido);
        document.add(frase);
        
        for(int i = 0; i < 8; i++) {
            document.add(new Phrase(Chunk.NEWLINE));
        }
        

        PdfPTable tablaFirma = new PdfPTable(2);
        
        Font fontFirma = FontFactory.getFont(FontFactory.COURIER, 7, Font.ITALIC, BaseColor.BLACK);
        String nombre = cf.getNombre();
        frase =  new Phrase(nombre, fontFirma);
        cell =  new PdfPCell(frase);
        cell.setBorderColor(BaseColor.WHITE);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        tablaFirma.addCell(cell);
        String agente = cf.getAgente();
        
        frase =  new Phrase(agente, fontFirma);
        cell =  new PdfPCell(frase);
        cell.setBorderColor(BaseColor.WHITE);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        tablaFirma.addCell(cell);
        String domicilio = cf.getDireccion();
        
        frase =  new Phrase(domicilio, fontFirma);
        cell =  new PdfPCell(frase);
        cell.setBorderColor(BaseColor.WHITE);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        tablaFirma.addCell(cell);
        String legajo = cf.getLegajo();
        
        frase =  new Phrase("Legajo N°" +legajo, fontFirma);
        cell =  new PdfPCell(frase);
        cell.setBorderColor(BaseColor.WHITE);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        tablaFirma.addCell(cell);
        String caracter = cf.getCaracter();
        String dni = cf.getDocumento();
        
        frase =  new Phrase("Doc N°: " +dni + " - " +caracter, fontFirma);
        cell =  new PdfPCell(frase);
        cell.setBorderColor(BaseColor.WHITE);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        tablaFirma.addCell(cell);
        
        frase =  new Phrase(Chunk.NEWLINE);
        cell =  new PdfPCell(frase);
        cell.setBorderColor(BaseColor.WHITE);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        tablaFirma.addCell(cell);
        
        document.add(tablaFirma);

        document.close();
        File file = new File("Anexo4.pdf");
        //Desktop.getDesktop().open(file);
        Desktop.getDesktop().print(file);
        Desktop.getDesktop().print(file);
        document.newPage();
    }

    private String obtieneLeyenda() {
        //@SuppressWarnings("UnusedAssignment")
        ParfrasesAnio pfa = new ParfrasesAnio();
        ParfrasesAnioJpaController jpa = new ParfrasesAnioJpaController(EntityMan.getInstance());
        pfa = jpa.findParfrasesAnio(anio);
        return pfa.getFrase();
    }

    /****************************************************************************************
     * 
     * 
     */
    
    private void imprimeActaTecnico(String copia) throws IOException, DocumentException{
        String leyenda = obtieneLeyenda();
        
        Document document = new Document(PageSize.A4, 35, 30, 50, 50);
        FileOutputStream fileOutputStream = new FileOutputStream("ActaControladorFIscal"+copia+".pdf");
        
        PdfWriter writer = PdfWriter.getInstance(document, fileOutputStream);
        Rectangle rect = new Rectangle(30, 30, 580, 800);
        writer.setBoxSize("art", rect);
        EncabezadoYPieActa event = new EncabezadoYPieActa(leyenda, acta, copia);
        writer.setPageEvent(event);
        document.open();
        
        Font fontContenido = FontFactory.getFont(FontFactory.TIMES_ROMAN, 13, Font.NORMAL, BaseColor.BLACK);
        Font fontTitulos = FontFactory.getFont(FontFactory.TIMES_BOLDITALIC, 11, Font.UNDERLINE, BaseColor.RED);

        PdfPTable tablaFormulario = new PdfPTable(2);
         
         Font fontFormulario = FontFactory.getFont(FontFactory.TIMES_ROMAN, 16, Font.BOLD, BaseColor.BLACK);
         Phrase frase = new Phrase("          F. 8400/L", fontFormulario);
         PdfPCell cell = new PdfPCell(frase);
         cell.setRowspan(2);
         cell.setBorderColor(BaseColor.WHITE);
         cell.setHorizontalAlignment(Element.ALIGN_LEFT);
         cell.setBorder(0);
         tablaFormulario.addCell(cell);
         
         Font fontCopia = FontFactory.getFont(FontFactory.TIMES_ROMAN, 10, Font.NORMAL, BaseColor.DARK_GRAY);
         frase = new Phrase("ORIGINAL: para la Administración Federal", fontCopia);
         cell = new PdfPCell(frase);
         cell.setBorder(0);
         tablaFormulario.addCell(cell);
         
         frase = new Phrase("DUPLICADO: para el Ciudadano", fontCopia);
         cell = new PdfPCell(frase);
         cell.setBorder(0);
         tablaFormulario.addCell(cell);

         document.add(tablaFormulario);
         
         Paragraph paragraph = new Paragraph();
         paragraph.add(new Phrase(Chunk.NEWLINE));
         

         document.add(paragraph);
         
        PdfPTable tablaSirvase = new PdfPTable(1);
         
         fontCopia = FontFactory.getFont(FontFactory.TIMES_ROMAN, 10, Font.NORMAL, BaseColor.BLACK);
         frase = new Phrase("Sirvase citar", fontCopia);
         cell = new PdfPCell(frase);
         cell.setBorder(0);
         tablaSirvase.addCell(cell);
         
         document.add(tablaSirvase);
         
         Calendar cal = Calendar.getInstance();
        int dia= cal.get(Calendar.DATE);
        int mes = cal.get(Calendar.MONTH);
        int ann = cal.get(Calendar.YEAR);
        int hora = cal.get(Calendar.HOUR);
        int minutos = cal.get(Calendar.MINUTE); 
        
        String cuitFormateado = (df11.format(cf.getCuit()));
         
         paragraph
          .add(new Phrase("En la ciudad de Tigre, pcia de Bs. As. al " +dia+"/"+mes+"/"+ann
                         +" , siendo las "+hora+":"+minutos +" hs. comparece en la agencia 66 "
                         +" de la Administración Federal de Ingresos Públicos el Sr/a. " +cf.getNombre()
                         +" , Doc. N° "+cf.getDocumento() +" que exhibe, domiciliado en "+cf.getDireccion()
                         +", en su caracter de " +cf.getCaracter() +" de la entidad " +cf.getRazonSocial()
                         +" , inscripta bajo el N° de CUIT: " +cuitFormateado +", y el sr. "+cf.getTecnico()
                         +", técnico autorizado de compañia " +cf.getMarca() +" credencial N°: " +cf.getCredencial()
                         +", siendo atendido por el funcionario "
                         +"de esta administración " +cf.getAgente() +", Legajo N°: " +cf.getLegajo() +". En este "
                         +"acto se solicita la autorización para obtener los comprobantes de auditoría"
                         +" y proceder a posteriori al bloqueo del controlador fiscal marca " +cf.getMarca()
                         +" modelo " +cf.getModelo() +" código: " +cf.getCodigo() + ", punto de venta " +cf.getPuestoVenta() 
                         +", se deja constancia que ante la imposibilidad de realizar la auditoría y bloqueo, se retira"
                         +" la memoria con técnico habilitado por imposibilidad de leer el controlador. El técnico procede"
                         +" al retiro de la memoria fiscal. Ello a los efectos de enviarla para su lectura y posterior reintegro "
                         +"al contribuyente " +cf.getRazonSocial() + ". Asimismo, se deja constancia que el precinto" 
                         +" fiscal se observa intacto. Se deja constancia en este acto que el servicio técnico que realice la "
                         +"remoción de la memoria fiscal, está obligado a entregar al contribuyente un remito donde consta a) "
                         +"N° de registro del controlador fiscal, b) identificación del técnico autorizado actuante, mediante indicación "
                         +"del nombre y apellido, N° de credencial, firma y sello. No siendo para más"
                         +", previa lectura a viva voz y ratificación de la presente, se firman de conformidad tres (3) "
                         +"ejemplares de un mismo tenor y a un solo efecto el acta N°" +acta +",recibiendo el/la Sr/a. " +cf.getNombre()
                         +", duplicado de la misma. Conste.-",fontContenido));

        
            paragraph.setAlignment(Element.ALIGN_JUSTIFIED);
            // Agregar saltos de linea
            document.add(paragraph);
        
            Paragraph psl = new  Paragraph();
                    
            for(int i=0; i<5; i++) {
                psl.add(new Phrase(Chunk.NEWLINE));
                document.add(psl);
            }
       
        PdfPTable tablaFirma = new PdfPTable(3);
        
        Font fontFirma = FontFactory.getFont(FontFactory.COURIER, 8, Font.ITALIC, BaseColor.BLACK);
        frase =  new Phrase(cf.getNombre(), fontFirma);
        cell =  new PdfPCell(frase);
        cell.setBorderColor(BaseColor.WHITE);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        tablaFirma.addCell(cell);
        
        frase =  new Phrase(cf.getTecnico(), fontFirma);
        cell =  new PdfPCell(frase);
        cell.setBorderColor(BaseColor.WHITE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        tablaFirma.addCell(cell);
        
        frase =  new Phrase(cf.getAgente(), fontFirma);
        cell =  new PdfPCell(frase);
        cell.setBorderColor(BaseColor.WHITE);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        tablaFirma.addCell(cell);
        
        frase =  new Phrase(cf.getDireccion(), fontFirma);
        cell =  new PdfPCell(frase);
        cell.setBorderColor(BaseColor.WHITE);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        tablaFirma.addCell(cell);
        
        frase =  new Phrase(cf.getCredencial(), fontFirma);
        cell =  new PdfPCell(frase);
        cell.setBorderColor(BaseColor.WHITE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        tablaFirma.addCell(cell);
        
        frase =  new Phrase("Legajo N°" +cf.getLegajo(), fontFirma);
        cell =  new PdfPCell(frase);
        cell.setBorderColor(BaseColor.WHITE);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        tablaFirma.addCell(cell);
        
        frase =  new Phrase("Doc N°: " +cf.getDocumento() + " - " +cf.getCaracter(), fontFirma);
        cell =  new PdfPCell(frase);
        cell.setBorderColor(BaseColor.WHITE);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        tablaFirma.addCell(cell);
        
        frase =  new Phrase("Técnico", fontFirma);
        cell =  new PdfPCell(frase);
        cell.setBorderColor(BaseColor.WHITE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        tablaFirma.addCell(cell);
        
        frase =  new Phrase(Chunk.NEWLINE);
        cell =  new PdfPCell(frase);
        cell.setBorderColor(BaseColor.WHITE);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        tablaFirma.addCell(cell);
        
        document.add(tablaFirma);
        
        document.close();
 
        // Abrir el archivo
        File file = new File("ActaControladorFIscal" +copia+".pdf");
        Desktop.getDesktop().print(file);

    }
    
    /****************************************************************************************
     * 
     * 
     * /
     * @throws IOException
     * @throws DocumentException 
     */

    private void imprimeNotaTecnico() throws IOException, DocumentException{
        String leyenda = obtieneLeyenda();
        
        Calendar cal = Calendar.getInstance();
        cal.setTimeZone(TimeZone.getTimeZone("America/Argentina/Buenos_Aires"));
        int dia= cal.get(Calendar.DATE);
        int mes = cal.get(Calendar.MONTH);
        int mesOk = mes+1;
        int ann = cal.get(Calendar.YEAR);
        int hora = cal.get(Calendar.HOUR_OF_DAY);
        int minutos = cal.get(Calendar.MINUTE); 
        
        Document document = new Document(PageSize.A4, 35, 30, 50, 50);
        FileOutputStream fileOutputStream = new FileOutputStream("NotaControladorFIscal.pdf");
        
        PdfWriter writer = PdfWriter.getInstance(document, fileOutputStream);
        Rectangle rect = new Rectangle(30, 30, 580, 800);
        writer.setBoxSize("art", rect);
        document.open();
        
        Font fontContenido = FontFactory.getFont(FontFactory.TIMES_ROMAN, 13, Font.NORMAL, BaseColor.BLACK);
        Font fontTitulos = FontFactory.getFont(FontFactory.TIMES_BOLDITALIC, 11, Font.UNDERLINE, BaseColor.RED);

         PdfPTable tablaInicio = new PdfPTable(1);
         
         Font fontFormulario = FontFactory.getFont(FontFactory.TIMES_ROMAN, 16, Font.BOLD, BaseColor.BLACK);
         Phrase frase = new Phrase(leyenda, fontFormulario);
         PdfPCell cell = new PdfPCell(frase);
         cell.setBorderColor(BaseColor.WHITE);
         cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
         cell.setBorder(0);
         tablaInicio.addCell(cell);
         
        frase =  new Phrase(Chunk.NEWLINE);
        cell =  new PdfPCell(frase);
        cell.setBorderColor(BaseColor.WHITE);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        tablaInicio.addCell(cell);
        frase =  new Phrase(Chunk.NEWLINE);
        cell =  new PdfPCell(frase);
        cell.setBorderColor(BaseColor.WHITE);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        tablaInicio.addCell(cell);
        
        frase =  new Phrase("Nota N°: __________________________", fontFormulario);
        cell =  new PdfPCell(frase);
        cell.setBorderColor(BaseColor.WHITE);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        tablaInicio.addCell(cell);
        
        frase =  new Phrase("Sigea N°: __________________________", fontFormulario);
        cell =  new PdfPCell(frase);
        cell.setBorderColor(BaseColor.WHITE);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        tablaInicio.addCell(cell);
        
        frase =  new Phrase(Chunk.NEWLINE);
        cell =  new PdfPCell(frase);
        cell.setBorderColor(BaseColor.WHITE);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        tablaInicio.addCell(cell);
        frase =  new Phrase(Chunk.NEWLINE);
        cell =  new PdfPCell(frase);
        cell.setBorderColor(BaseColor.WHITE);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        tablaInicio.addCell(cell);
        
        frase =  new Phrase("Tigre: " +df2.format(dia)+"/"+df2.format(mesOk)+"/"+df2.format(ann), fontContenido);
        cell =  new PdfPCell(frase);
        cell.setBorderColor(BaseColor.WHITE);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        tablaInicio.addCell(cell);
        
        frase =  new Phrase(Chunk.NEWLINE);
        cell =  new PdfPCell(frase);
        cell.setBorderColor(BaseColor.WHITE);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        tablaInicio.addCell(cell);
        frase =  new Phrase(Chunk.NEWLINE);
        cell =  new PdfPCell(frase);
        cell.setBorderColor(BaseColor.WHITE);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        tablaInicio.addCell(cell);
        
        frase =  new Phrase("Asunto: Retiro de memoria", fontContenido);
        cell =  new PdfPCell(frase);
        cell.setBorderColor(BaseColor.WHITE);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        tablaInicio.addCell(cell);
        
        String cuitFormateado = (df11.format(cf.getCuit()));
        
        frase =  new Phrase("CUIT:" +cuitFormateado , fontContenido);
        cell =  new PdfPCell(frase);
        cell.setBorderColor(BaseColor.WHITE);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        tablaInicio.addCell(cell);
        
        frase =  new Phrase("N° Solicitud: " +cf.getSolicitud(), fontContenido);
        cell =  new PdfPCell(frase);
        cell.setBorderColor(BaseColor.WHITE);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        tablaInicio.addCell(cell);
        
        frase =  new Phrase(Chunk.NEWLINE);
        cell =  new PdfPCell(frase);
        cell.setBorderColor(BaseColor.WHITE);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        tablaInicio.addCell(cell);
        frase =  new Phrase(Chunk.NEWLINE);
        cell =  new PdfPCell(frase);
        cell.setBorderColor(BaseColor.WHITE);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        tablaInicio.addCell(cell);
        
        String linea = "En el dia de la fecha se hace entrega de la memoria fiscal que mas abajo se enuncia "
                       +"que llevara el acta N°: " +cf.getActa() +" , de fecha: " +df2.format(dia)+"/"+df2.format(mesOk)+"/"+df2.format(ann);
        
        frase =  new Phrase(linea, fontContenido);
        cell =  new PdfPCell(frase);
        cell.setBorderColor(BaseColor.WHITE);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        tablaInicio.addCell(cell);
        
        Chunk separador = new Chunk(new LineSeparator());
        frase =  new Phrase(separador);
        cell =  new PdfPCell(frase);
        cell.setBorderColor(BaseColor.WHITE);
        cell.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
        tablaInicio.addCell(cell);
        
        frase =  new Phrase("Presentada por: " +cf.getNombre(), fontContenido);
        cell =  new PdfPCell(frase);
        cell.setBorderColor(BaseColor.WHITE);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        tablaInicio.addCell(cell);
        
        frase =  new Phrase("Domiciliado en: " +cf.getDireccion(), fontContenido);
        cell =  new PdfPCell(frase);
        cell.setBorderColor(BaseColor.WHITE);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        tablaInicio.addCell(cell);
        
        frase =  new Phrase("N° de Doc.: " +cf.getDocumento(), fontContenido);
        cell =  new PdfPCell(frase);
        cell.setBorderColor(BaseColor.WHITE);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        tablaInicio.addCell(cell);
        
        frase =  new Phrase("Teléfono: " +cf.getTelefono(), fontContenido);
        cell =  new PdfPCell(frase);
        cell.setBorderColor(BaseColor.WHITE);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        tablaInicio.addCell(cell);
        
        frase =  new Phrase("Carácter: " +cf.getCaracter(), fontContenido);
        cell =  new PdfPCell(frase);
        cell.setBorderColor(BaseColor.WHITE);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        tablaInicio.addCell(cell);
        
        frase =  new Phrase("Motivo de presentacion: " +cf.getTramite(), fontContenido);
        cell =  new PdfPCell(frase);
        cell.setBorderColor(BaseColor.WHITE);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        tablaInicio.addCell(cell);
        
        frase =  new Phrase("Código de Máquina: " +cf.getCodigo(), fontContenido);
        cell =  new PdfPCell(frase);
        cell.setBorderColor(BaseColor.WHITE);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        tablaInicio.addCell(cell);
        
        frase =  new Phrase("Punto de Venta: " +cf.getPuestoVenta(), fontContenido);
        cell =  new PdfPCell(frase);
        cell.setBorderColor(BaseColor.WHITE);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        tablaInicio.addCell(cell);
        
        frase =  new Phrase("Modelo: " +cf.getModelo(), fontContenido);
        cell =  new PdfPCell(frase);
        cell.setBorderColor(BaseColor.WHITE);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        tablaInicio.addCell(cell);
        
        frase =  new Phrase("N° Serie: " +cf.getNumeroCodigo(), fontContenido);
        cell =  new PdfPCell(frase);
        cell.setBorderColor(BaseColor.WHITE);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        tablaInicio.addCell(cell);
        
        frase =  new Phrase("Marca: " +cf.getMarca(), fontContenido);
        cell =  new PdfPCell(frase);
        cell.setBorderColor(BaseColor.WHITE);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        tablaInicio.addCell(cell);
        
        frase =  new Phrase("Técnico que interviene: " +cf.getTecnico(), fontContenido);
        cell =  new PdfPCell(frase);
        cell.setBorderColor(BaseColor.WHITE);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        tablaInicio.addCell(cell);
        
        frase =  new Phrase("Credencial: " +cf.getCredencial(), fontContenido);
        cell =  new PdfPCell(frase);
        cell.setBorderColor(BaseColor.WHITE);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        tablaInicio.addCell(cell);
        
        frase =  new Phrase("Comentarios: " +cf.getObservaciones2(), fontContenido);
        cell =  new PdfPCell(frase);
        cell.setBorderColor(BaseColor.WHITE);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        tablaInicio.addCell(cell);
        
        document.add(tablaInicio);
        
        /** NUEVO -> Para separar la linea de firma del cuerpo de la nota */
        for(int i = 0; i < 8; i++) {
            document.add(new Phrase(Chunk.NEWLINE));
        }
        
        PdfPTable tablaFirma = new PdfPTable(2);
        Font fontFirma = FontFactory.getFont(FontFactory.COURIER, 8, Font.ITALIC, BaseColor.BLACK);
        
        frase =  new Phrase("Técnico: " +cf.getTecnico(), fontFirma);
        cell =  new PdfPCell(frase);
        cell.setBorderColor(BaseColor.WHITE);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        tablaFirma.addCell(cell);
        frase =  new Phrase("Agente: " +cf.getAgente(), fontFirma);
        cell =  new PdfPCell(frase);
        cell.setBorderColor(BaseColor.WHITE);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        tablaFirma.addCell(cell);
        
        frase =  new Phrase("Credencial: " +cf.getCredencial(), fontFirma);
        cell =  new PdfPCell(frase);
        cell.setBorderColor(BaseColor.WHITE);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        tablaFirma.addCell(cell);
        frase =  new Phrase("Legajo: " +cf.getLegajo(), fontFirma);
        cell =  new PdfPCell(frase);
        cell.setBorderColor(BaseColor.WHITE);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        tablaFirma.addCell(cell);
        
        frase =  new Phrase(Chunk.NEWLINE);
        cell =  new PdfPCell(frase);
        cell.setBorderColor(BaseColor.WHITE);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        tablaFirma.addCell(cell);
        frase =  new Phrase(Chunk.NEWLINE);
        cell =  new PdfPCell(frase);
        cell.setBorderColor(BaseColor.WHITE);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        tablaFirma.addCell(cell);
        frase =  new Phrase(Chunk.NEWLINE);
        cell =  new PdfPCell(frase);
        cell.setBorderColor(BaseColor.WHITE);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        tablaFirma.addCell(cell);
        
        document.add(tablaFirma);
        
        document.close();
 
        // Abrir el archivo
        File file = new File("NotaControladorFiscal.pdf");
        Desktop.getDesktop().print(file);
        Desktop.getDesktop().print(file);
    }

        public void imprimeActaRetornoMemoria(ControladorFiscal cf, RetiroMemoria rm, int anio) throws IOException, DocumentException{
            for(int i=0; i<3; i++) {
                        switch (i) {
                            case 0:
                                imprimeRM("ORIGINAL", cf, rm, anio);

                                break;
                            case 1:
                                imprimeRM("DUPLICADO", cf, rm, anio);
                                break;
                            default:
                                imprimeRM("COPIA SISTEMAS", cf, rm, anio);
                                break;
                        }
            }
        }
        
        private void imprimeRM(String copia, ControladorFiscal cf, RetiroMemoria rm, int _anio) throws FileNotFoundException, DocumentException, IOException {
        
            Document document = new Document(PageSize.A4, 35, 30, 50, 50);
            FileOutputStream fileOutputStream = new FileOutputStream("ActaEntregaMemoria"+copia+".pdf");
            anio = _anio;
            String leyenda = obtieneLeyenda();
            
            PdfWriter writer = PdfWriter.getInstance(document, fileOutputStream);
            Rectangle rect = new Rectangle(30, 30, 580, 800);
            writer.setBoxSize("art", rect);
            EncabezadoYPieActa event = new EncabezadoYPieActa(leyenda, rm.getActa(), copia);
            writer.setPageEvent(event);
            document.open();

            Font fontContenido = FontFactory.getFont(FontFactory.TIMES_ROMAN, 13, Font.NORMAL, BaseColor.BLACK);
            Font fontTitulos = FontFactory.getFont(FontFactory.TIMES_BOLDITALIC, 11, Font.UNDERLINE, BaseColor.RED);

            PdfPTable tablaFormulario = new PdfPTable(2);

             Font fontFormulario = FontFactory.getFont(FontFactory.TIMES_ROMAN, 16, Font.BOLD, BaseColor.BLACK);
             Phrase frase = new Phrase("          F. 8400/L", fontFormulario);
             PdfPCell cell = new PdfPCell(frase);
             cell.setRowspan(2);
             cell.setBorderColor(BaseColor.WHITE);
             cell.setHorizontalAlignment(Element.ALIGN_LEFT);
             cell.setBorder(0);
             tablaFormulario.addCell(cell);

             Font fontCopia = FontFactory.getFont(FontFactory.TIMES_ROMAN, 10, Font.NORMAL, BaseColor.DARK_GRAY);
             frase = new Phrase("ORIGINAL: para la Administración Federal", fontCopia);
             cell = new PdfPCell(frase);
             cell.setBorder(0);
             tablaFormulario.addCell(cell);

             frase = new Phrase("DUPLICADO: para el Ciudadano", fontCopia);
             cell = new PdfPCell(frase);
             cell.setBorder(0);
             tablaFormulario.addCell(cell);

             document.add(tablaFormulario);

             Paragraph paragraph = new Paragraph();
             paragraph.add(new Phrase(Chunk.NEWLINE));


             document.add(paragraph);

            PdfPTable tablaSirvase = new PdfPTable(1);

             fontCopia = FontFactory.getFont(FontFactory.TIMES_ROMAN, 10, Font.NORMAL, BaseColor.BLACK);
             frase = new Phrase("Sirvase citar", fontCopia);
             cell = new PdfPCell(frase);
             cell.setBorder(0);
             tablaSirvase.addCell(cell);

             document.add(tablaSirvase);

            Calendar cal = Calendar.getInstance();
            cal.setTimeZone(TimeZone.getTimeZone("America/Argentina/Buenos_Aires"));
            int dia= cal.get(Calendar.DATE);
            int mes = cal.get(Calendar.MONTH);
            int mesOk = mes+1;
            int ann = cal.get(Calendar.YEAR);
            int hora = cal.get(Calendar.HOUR_OF_DAY);
            int minutos = cal.get(Calendar.MINUTE); 
            //FormateaSalidasTexto fst = new FormateaSalidasTexto();
            String cuitFormateado = (df11.format(cf.getCuit()));
            String fechaActa = cf.getFechaPoceso().substring(8) +"/"+cf.getFechaPoceso().substring(5, 7) +"/" +cf.getFechaPoceso().substring(0, 4);
            System.out.println("Fecha de proceso es: " +fechaActa);

             paragraph
              .add(new Phrase("En la ciudad de Tigre, pcia de Bs. As. al " +df2.format(dia)+"/"+df2.format(mesOk)+"/"+df2.format(ann)
                             +" , siendo las "+df2.format(hora)+":"+df2.format(minutos) +" hs. comparece en la agencia 66 "
                             +" de la Administración Federal de Ingresos Públicos el Sr/a. " +rm.getRetira()
                             +" , Doc. N° "+rm.getDni() +" que exhibe, domiciliado en "+rm.getDireccion()
                             +", en su caracter de " +rm.getCaracter() +" de la entidad " +cf.getRazonSocial()
                             +" , inscripta bajo el N° de CUIT: " +fst.formatoCuit(cuitFormateado)  +", siendo atendido por el funcionario "
                             //+" , inscripta bajo el N° de CUIT: " +fst.formatoCuit(Double.toString(cf.getCuit()) )  +", siendo atendido por el funcionario "
                             +"de esta administración " +rm.getAgente() +", Legajo N°: " +rm.getLegajo() +". Conforme quedara "
                             +"sentado en Acta F.8400 N°: " +cf.getActa()+" del " +fechaActa
                             +", habiendose leido la información obrante en la memoria fiscal retirada en misma fecha, " 
                             +" correspondiente al controlador fiscal marca: " +cf.getMarca() +" modelo: " +cf.getModelo() +" código: " +cf.getCodigo() +" N°: " 
                             +cf.getNumeroCodigo() +", punto de venta " +cf.getPuestoVenta() +", se han obtenido los reportes de auditoría que arrojan "
                             +"la siguiente información: N° de Z inicial: " +cf.getZDesde() +"; N° de Z final: " +cf.getZHasta() +" monto total de ventas: " 
                             +dfImp.format(cf.getVentaingresado()) +"; monto total de IVA: " +dfImp.format(cf.getIvaingresado()) +"; último ticket emitido: " +cf.getUltimocingresado()
                             +" con cantidad de bloqueos: " +cf.getBloqueos() +". No siendo para mas , previa lectura a viva voz y ratificación de la presente, se firman de conformidad tres (3) "
                             +"ejemplares de un mismo tenor y a un solo efecto el acta N°" +rm.getActa()+", recibiendo el/la Sr/a. " +rm.getRetira()
                             +" duplicado de la misma. Conste.-",fontContenido));


                paragraph.setAlignment(Element.ALIGN_JUSTIFIED);
                // Agregar saltos de linea
                document.add(paragraph);

                Paragraph psl = new  Paragraph();

                for(int i=0; i<5; i++) {
                    psl.add(new Phrase(Chunk.NEWLINE));
                    document.add(psl);
            }

            PdfPTable tablaFirma = new PdfPTable(2);

            Font fontFirma = FontFactory.getFont(FontFactory.COURIER, 8, Font.ITALIC, BaseColor.BLACK);
            frase =  new Phrase(rm.getRetira(), fontFirma);
            cell =  new PdfPCell(frase);
            cell.setBorderColor(BaseColor.WHITE);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            tablaFirma.addCell(cell);

            frase =  new Phrase(rm.getAgente(), fontFirma);
            cell =  new PdfPCell(frase);
            cell.setBorderColor(BaseColor.WHITE);
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            tablaFirma.addCell(cell);

            frase =  new Phrase(rm.getDireccion(), fontFirma);
            cell =  new PdfPCell(frase);
            cell.setBorderColor(BaseColor.WHITE);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            tablaFirma.addCell(cell);

            frase =  new Phrase("Legajo N°" +rm.getLegajo(), fontFirma);
            cell =  new PdfPCell(frase);
            cell.setBorderColor(BaseColor.WHITE);
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            tablaFirma.addCell(cell);

            frase =  new Phrase("Doc N°: " +rm.getDni() + " - " +rm.getCaracter(), fontFirma);
            cell =  new PdfPCell(frase);
            cell.setBorderColor(BaseColor.WHITE);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            tablaFirma.addCell(cell);

            frase =  new Phrase(Chunk.NEWLINE);
            cell =  new PdfPCell(frase);
            cell.setBorderColor(BaseColor.WHITE);
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            tablaFirma.addCell(cell);

            document.add(tablaFirma);

            document.close();

            // Abrir el archivo
            File file = new File("ActaEntregaMemoria" +copia+".pdf");
            Desktop.getDesktop().print(file);
    }

    
}
