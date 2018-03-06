package utils;

import java.net.MalformedURLException;
import java.net.SocketException;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class SeleniumBase {

	private WebDriver driver;

	public SeleniumBase(WebDriver driver) {
		this.driver = driver;
	}
	
	protected WebDriver getDriver()
	{
		return driver;
	}

	/**
	 * Closes current window, quitting browser if window was last instance of
	 * browser
	 */
	protected void closeDriver() {
		driver.close();
	}

	/**
	 * Quits driver and all associations of it
	 */
	protected void quitDriver() {
		driver.quit();
	}

	

}
