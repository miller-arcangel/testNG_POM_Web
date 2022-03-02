package com.Utilidades;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;

import org.openqa.selenium.By;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.testNG.ClaseBase;

public class GenerarReportePdf 
{ 
	static String nombre="prueba";
	static String fecha;
	static Document documento; 
	static FileOutputStream archivo; 
	static Paragraph titulo;
	String rutaImagen;
	String hora;
	String horaIni,horaFin;

	public void setRutaImagen(String rutaImagen)
	{
		this.rutaImagen = rutaImagen;
	}

	public void crearPlantilla(String nomTest, File rutaCarpeta)
	{		
		//INSTANCIAR DOCUMENTO
		documento = new Document();
		
		//TOMAR LA HORA DEL SISTEMA 
		 hora = ClaseBase.fechaHora();
		 horaIni = ClaseBase.fechaHora2();
		 
		try 
		{
			//CREAR RUTA Y NOMBRE DEL PDF
			archivo = new FileOutputStream(rutaCarpeta+"\\"+"Reporte-"+nomTest +"-"+ hora+".pdf"); 
			PdfWriter.getInstance (documento, archivo);
			
			//****CREAR ENCABEZADO
			//UBICACION DE LA IMAGEN
			Image header = Image.getInstance(rutaImagen);
			//TAMAÑO DE LA IMAGEN
			header.scaleToFit(250,1000);
			//header.setAlignment(Chunk.ALIGN_CENTER);
			header.setWidthPercentage(100);			
			//CREAR TITULO DEL PDF
			titulo = new Paragraph (nomTest + "\n\n" + "Fecha inicio:  "+ horaIni);
			titulo.setAlignment(1);
			
			//CREAR TABLA DE ENCABEZADO
			PdfPTable table =new PdfPTable(2);
			table.setWidthPercentage(100);
			PdfPCell pos1 = new PdfPCell(header);
			pos1.setHorizontalAlignment(1);
			PdfPCell pos2 = new PdfPCell(titulo);
			pos2.setHorizontalAlignment(1);//0=Left, 1=Center, 2=Right
			pos2.setVerticalAlignment(2);
			
			table.addCell(pos2);
			table.addCell(pos1);
		
			//GEENERAR MARGEN 
			documento.setMargins(30, 30, 30, 30);
			//ABRIR DOCUMENTO 
			documento.open();			
			
			//INSERTA LA IMAGEN
			documento.add(table);
						
			documento.add(Chunk.NEWLINE);
		}
		catch (FileNotFoundException e) 
		{			
			System.err.println (e.getMessage());
		} 
		catch (DocumentException e) 
		{
			System.err.println(e.getMessage());
		}
		 catch (IOException e)
		{
			 System.err.println("Error al logo periferia: "+e.getMessage());
		}
	}

	public void crearbody(By locator, String rutaImagen) throws DocumentException, MalformedURLException, IOException
	{
		//OBTENER EL NOMBRE DEL LOCALIZADOR
		String locator1=locator.toString();
		//DAR FORMATO A LA FUENTE
		Paragraph parrafo = new Paragraph();
		parrafo.setAlignment(Chunk.ALIGN_LEFT);
		parrafo.setFont(FontFactory.getFont("Arial",10,Font.NORMAL));
		parrafo.add("Se realiza accion sobre el elemento: "+locator1);
		
		//ADICIONAR MENSAJE AL PDG
		documento.add(parrafo);
		
		//INSERTT IMAGEN
		//UBICACION DE LA IMAGEN
		Image imagen = Image.getInstance(rutaImagen);
		imagen.setBorderColor(BaseColor.BLACK);
		
		//TAMAÑO DE LA IMAGEN
		imagen.scaleToFit(700,1000);
		imagen.setAlignment(Chunk.ALIGN_LEFT);
		documento.add(imagen);
	}
	
	public void crearbodyError(By locator, String rutaImagen, String msnError) throws DocumentException, MalformedURLException, IOException
	{
		//OBTENER EL NOMBRE DEL LOCALIZADOR
		String locator1=locator.toString();
		//DAR FORMATO A LA FUENTE
		Paragraph parrafo = new Paragraph();
		parrafo.setAlignment(Chunk.ALIGN_LEFT);
		parrafo.setFont(FontFactory.getFont("Arial",10,Font.NORMAL));
		parrafo.add("Se realiza accion sobre el elemento: "+locator1);
		//ADICIONAR MENSAJE AL PDG
		documento.add(parrafo);
		
		//INSERTT IMAGEN
		//UBICACION DE LA IMAGEN
		Image imagen = Image.getInstance(rutaImagen);
		//TAMAÑO DE LA IMAGEN
		imagen.scaleToFit(700,1000);
		imagen.setAlignment(Chunk.ALIGN_LEFT);
		documento.add(imagen);
		
		//MENSAJE ERROR
		//DAR FORMATO A LA FUENTE
		Paragraph parrafoError = new Paragraph();
		parrafoError.setAlignment(Chunk.ALIGN_LEFT);
		parrafoError.setFont(FontFactory.getFont("Arial",8,Font.NORMAL,BaseColor.BLACK));
		parrafoError.add("EL MENSAJE DE ERROR: "+"\n"+msnError);
		documento.add(parrafoError);
	}
	
	public void cerrarPlantilla() throws DocumentException
	{
		documento.add(Chunk.NEWLINE);
		//DAR FORMATO A LA FUENTE
		Paragraph parrafo = new Paragraph();
		parrafo.setAlignment(Chunk.ALIGN_RIGHT);
		parrafo.setFont(FontFactory.getFont("Arial",10,Font.BOLD));
		parrafo.add("Fecha inicio:  "+horaIni+"\n");
		
		//ADICIONAR MENSAJE AL PDG
		//documento.add(parrafo);
		horaFin = ClaseBase.fechaHora2();		 
		 parrafo.add("Fecha Fin:  "+horaFin);
		
		//ADICIONAR MENSAJE AL PDG
		documento.add(parrafo);
		documento.close();
		
	}
}
