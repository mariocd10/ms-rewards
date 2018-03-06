package bingPages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import utils.WaitMethods;

public class ResultsPage {
	private static WebElement elem = null;
	private static WaitMethods wait;
	private static List<WebElement> elems = null;
	
	public ResultsPage(WebDriver driver) {
		wait = new WaitMethods(driver);
	}
	
	public List<WebElement> articleRow() {
		By loc = By.xpath("//div[@class='cardInfo']");
		elems = wait.waitForElemListPresence(loc, 10);
		return elems;
	}
	
	public WebElement searchBox() {
		By locator = By.id("sb_form_q");
		elem = wait.waitForElemPresence(locator, 10);
		return elem;
	}
	
	public WebElement searchBoxSubmit() {
		By loc = By.id("sb_form_go");
		elem = wait.waitForElemPresence(loc, 10);
		return elem;
	}
}
