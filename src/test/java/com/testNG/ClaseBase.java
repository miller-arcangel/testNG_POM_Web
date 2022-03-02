package com.testNG;

import java.io.File;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import com.Utilidades.GenerarReportePdf;

public class ClaseBase
{
	protected WebDriver driver;
	String rutaOutut;
	
	public void setRutaOutut(String rutaOutut) {
		this.rutaOutut = rutaOutut;
	}

	//CONSTRUCTOR DE CLASE
	public ClaseBase(WebDriver driver) 
	{
		super();
	}

	//METODO DE NAVEGADOR
	public static WebDriver chomeDriverConnetion() 
	{
		WebDriver _driver = null;
		try
		{
			// SETEAR LAS OPCIONES DE NAVEGADOR
			ChromeOptions chromeOptions = new ChromeOptions();
			chromeOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);

			// SETEAR LAS PROPIEDADES DEL NAVEGADOR
			System.setProperty("webdriver.chrome.driver", "./driver/chromedriver.exe");
			 _driver = new ChromeDriver();

			// MAXIMIZAR NAVEGADOR
			_driver.manage().window().maximize();
			return _driver;
		}
		catch (Exception e) 
		{
			System.out.println(e);
		}
		return _driver;
	}

	//METODO CLIK
	public void click(By locator, File rutaCarpeta, String generarEvidencia) throws Exception 
	{
		try
		{
			driver.findElement(locator).click();
			tiempoEspera(2);
			captureScreen(rutaCarpeta,locator,generarEvidencia);
		}
		
		catch (Exception e) 
		{
			captureScreenError(rutaCarpeta,locator,generarEvidencia,e.toString());
			throw new InterruptedException();
		}
		
	}
		
	//METODO BORRAR
	public void borrar(By locator,File rutaCarpeta, String generarEvidencia) throws Exception 
	{
		try
		{
			driver.findElement(locator).clear();
			tiempoEspera(2);
			captureScreen(rutaCarpeta, locator,generarEvidencia);
		}		
		catch (Exception e) 
		{
			captureScreenError(rutaCarpeta,locator,generarEvidencia,e.toString());
			throw new InterruptedException();
		}
	}

	//METODO ENVIAR TEXTO
	public void sendkey(String inputText, By locator, File rutaCarpeta,String generarEvidencia) throws Exception 
	{
		try
		{
			driver.findElement(locator).sendKeys(inputText);
			tiempoEspera(2);
			captureScreen(rutaCarpeta, locator,generarEvidencia);
		}		
		catch (Exception e) 
		{
			captureScreenError(rutaCarpeta,locator,generarEvidencia,e.toString());
			throw new InterruptedException();
		}
	}
			
	//METODO ENTER SUBMIN
	public void submit(By locator,File rutaCarpeta,String generarEvidencia) throws Exception 
	{
		driver.findElement(locator).submit();
		captureScreen(rutaCarpeta, locator,generarEvidencia);
	}
	
	public String capturarValorSelenium(By locator) throws Exception 
	{
		String valor = driver.findElement(locator).getText();
		return valor;
	}
	
	//METODO TIEMPO DE ESPERA
	public void tiempoEspera(long tiempo) throws InterruptedException 
	{
		Thread.sleep(tiempo*1000);
	}
	
	public File crearCarpeta(String nomTest)
	{
		//ALCACENAMOS LA FECHA DEL SITEMA
		String fecha = fechaHora();
		//CCREARMOS EL NOMBRE DE LA CARPETA
		String nomCarpeta = nomTest+"-"+fecha;
		//OBTENEMOS LA RUTA DE ALOJAMIENTO DE SALIDA Y EL NOMBRE DEL TEST A EJECUTAR
		File directorio = new File(rutaOutut+nomCarpeta);
		//CREAMOS LA CARPETA
		directorio.mkdir();
		return directorio;		 
	}
		
	public void captureScreen(File rutaCarpeta, By locator, String generarEvidencia) throws Exception 
	{
		if (generarEvidencia.equals("Si"))
		{
			String hora = HoraSistema();
			File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(scrFile, new File(rutaCarpeta+"\\"+hora+".png"));
			String rutaImagen =new File(rutaCarpeta+"\\"+hora+".png").toString();
			
			//INSTACIAMOS LA CLASE GENERAR PDF
			GenerarReportePdf informePdf = new GenerarReportePdf();
			//SE PROCEDE A INSERTAR LOCALIZADOR HE IMAGEN EN EL PDF
			informePdf.crearbody(locator,rutaImagen);
			//ELIMINAR IMAGEN CREADA
			
			eliminarArchivo(rutaImagen);
		}
	}	
	
	public void captureScreenError(File rutaCarpeta, By locator, String generarEvidencia, String msnError) throws Exception 
	{
		if (generarEvidencia.equals("Si"))
		{
			String hora = HoraSistema();
			File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(scrFile, new File(rutaCarpeta+"\\"+hora+".png"));
			String rutaImagen =new File(rutaCarpeta+"\\"+hora+".png").toString();
			
			//INSTACIAMOS LA CLASE GENERAR PDF
			GenerarReportePdf informePdf = new GenerarReportePdf();
			//SE PROCEDE A INSERTAR LOCALIZADOR HE IMAGEN EN EL PDF
			informePdf.crearbodyError(locator,rutaImagen,msnError);
			//ELIMINAR IMAGEN CREADA
			
			eliminarArchivo(rutaImagen);
		}
	}	
	
	public void eliminarArchivo(String rutaImagen) 
	{
		File fichero = new File(rutaImagen);
		fichero.delete();
	}
		
	public static String fechaHora()
	{
		//TOMAMOS LA FECHA DEL SISTEMA
		LocalDateTime fechaSistema = LocalDateTime.now();
		//DEFINIR FORMATO FECHA
		DateTimeFormatter fecha = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmmss");
		//DAR FORMATO A LA FECHA DEL SITEMA
		String formatFecha = fecha.format(fechaSistema);		
		return formatFecha;
	}
	
	public static String fechaHora2()
	{
		//TOMAMOS LA FECHA DEL SISTEMA
		LocalDateTime fechaSistema = LocalDateTime.now();
		//DEFINIR FORMATO FECHA
		DateTimeFormatter fecha = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		//DAR FORMATO A LA FECHA DEL SITEMA
		String formatFecha = fecha.format(fechaSistema);		
		return formatFecha;
	}
	
	public static String HoraSistema()
	{
		//TOMAMOS LA FECHA DEL SISTEMA
		LocalTime horaSistema = LocalTime.now();
		//DEFINIR FORMATO FECHA
		DateTimeFormatter fecha = DateTimeFormatter.ofPattern("HHmmss");
		//DAR FORMATO A LA FECHA DEL SITEMA
		String hora = fecha.format(horaSistema);		
		return hora;
	}
}
