package com.MapsObjet;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.testNG.ClaseBase;


public class MapObjetInicio extends ClaseBase
{
	//CONSTRUCTOR DE LA CLASE
	public MapObjetInicio(WebDriver driver) 
	{
		super(driver);
	}
		
	//ELEMENTOS PAGINA INICIAL 	
	protected By register =By.xpath("//a[contains(text(),'REGISTER')]");
	protected By lastName =By.xpath("//tbody/tr[2]/td[2]/input[1]");
	protected By btnSubmit =By.xpath("//*[@name='submit']");
	protected By resultado =By.xpath("/html/body/div/table/tbody/tr/td[2]/table/tbody/tr[4]/td/table/tbody/tr/td[2]/table/tbody/tr[3]/td");	
	
}
