package bingPages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import utils.WaitMethods;

public class LandingPage{
	
	private static WebElement element = null;
	private static WaitMethods wait;
	
	public LandingPage(WebDriver driver) {
		wait = new WaitMethods(driver);
	}
	
	public WebElement fst_story() {
		By story = By.xpath("//li[@class=' pntile'][1]");
		element = wait.waitForElemPresence(story, 20);
		return element;
	}
	
	public WebElement searchBox() {
		By inputBox = By.xpath("//input[@id='sb_form_q']");
		element = wait.waitForElemPresence(inputBox, 10);
		return element;
	}
	
	public WebElement searchBoxSubmit() {
		By icon = By.xpath("//input[@id='sb_form_go']");
		element = wait.waitForElemPresence(icon, 10);
		return element;
	}
}
