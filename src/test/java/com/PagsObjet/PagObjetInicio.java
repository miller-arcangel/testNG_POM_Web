package com.PagsObjet;

import java.io.File;
import org.openqa.selenium.WebDriver;
import com.MapsObjet.MapObjetInicio;

public class PagObjetInicio extends MapObjetInicio 
{
	String url;
	
	public void setUrl(String url) {
		this.url = url;
	}

	// CREAR CONSTRUCTOR DE LA CLASE
	public PagObjetInicio(WebDriver driver)
	{
		super(driver);
		this.driver=driver;
	}

	// METODO INICIAL
	public void urlAcceso()
	{
		driver.get(url);
	}

	// METODO PRIMERA PRUEBA
	public String busquedaInicial(String firstName, File rutaCarpeta, String generarEvidencia) throws Exception
	{
		try 
		{
			tiempoEspera(3);
			click(register, rutaCarpeta,generarEvidencia);
			sendkey(firstName, lastName, rutaCarpeta,generarEvidencia);
			click(btnSubmit, rutaCarpeta, generarEvidencia);			
		}
		catch (Exception e) 
		{
			System.out.println(e);
			
		}
		String valor = capturarValorSelenium(resultado);
		return valor;
	}
}
