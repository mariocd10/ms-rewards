package utils;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

public class WaitMethods {
	private WebElement elem = null;
	private List<WebElement> elems = null;
	private WebDriver driver = null;

	public WaitMethods(WebDriver driver) {
		this.driver = driver;
	}

	private WebElement fluentWait(ExpectedCondition<?> expCond, int timeout) {
		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(timeout, TimeUnit.SECONDS)
				.pollingEvery(2, TimeUnit.SECONDS).ignoring(NoSuchElementException.class);
		elem = (WebElement) wait.until(expCond); // returns object but casted to WebElem
		return elem;
	}
	
	private List<WebElement> fluentWaitList(ExpectedCondition<?> expCond, int timeout) {
		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(timeout, TimeUnit.SECONDS)
				.pollingEvery(2, TimeUnit.SECONDS).ignoring(NoSuchElementException.class);
		elems = (List<WebElement>) wait.until(expCond); // returns object but casted to WebElem
		return elems;
	}
	
	

	public WebElement waitForElemPresence(By locator, int timeout) {
		try {
			elem = fluentWait(ExpectedConditions.presenceOfElementLocated(locator), timeout);
			return elem;
		} catch (NoSuchElementException | TimeoutException e) {
			return null;
		}
	}

	public List<WebElement> waitForElemListPresence(By locator, int timeout) {
		try {
			return (List<WebElement>) fluentWaitList(ExpectedConditions.presenceOfAllElementsLocatedBy(locator), timeout);
		} catch (NoSuchElementException | TimeoutException e) {
			return null;
		}
	}

}
