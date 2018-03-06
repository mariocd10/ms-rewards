package utils;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.TimeoutException;

public class SeleniumWaitMethods extends SeleniumBase {
	private WebDriver driver = getDriver();

	public SeleniumWaitMethods(WebDriver driver) {
		super(driver);
	}

	/*
	 * Methods NOT using time as variable are below - 2 minutes is static
	 */

	public Object FluentWait(ExpectedCondition<?> expectedCondition)
			throws TimeoutException {
		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				.withTimeout(120, TimeUnit.SECONDS)
				.pollingEvery(250, TimeUnit.MILLISECONDS)
				.ignoring(NoSuchElementException.class);
		return wait.until(expectedCondition);
	}

	public Object WaitForExpectedCondition(
			ExpectedCondition<?> expectedCondition) {
		return FluentWait(expectedCondition);
	}

	/**
	 * An expectation for checking that an element is present on the DOM of a
	 * page. This does not necessarily mean that the element is visible.
	 *
	 * @param locator
	 *            used to find the element
	 * @return the WebElement once it is located
	 */
	public WebElement WaitForPresenceOfElement(By locator) {
		try {
			return (WebElement) FluentWait(ExpectedConditions
					.presenceOfElementLocated(locator));
		} catch (NoSuchElementException | TimeoutException e) {
			return null;
		}
	}

	/**
	 * An expectation for checking that an element is no longer present in the
	 * DOM of a page.
	 *
	 * @param locator
	 *            used to find the element
	 */
	public boolean WaitForInvisibilityOfElementLocated(By locator) {
		try {
			return (boolean) FluentWait(ExpectedConditions
					.invisibilityOfElementLocated(locator));
		} catch (NoSuchElementException | TimeoutException e) {
			return false;
		}
	}

	/**
	 * An expectation for checking an element is visible and enabled such that
	 * you can click it.
	 *
	 * @param locator
	 *            used to find the element
	 * @return the WebElement once it is located and clickable (visible and
	 *         enabled)
	 */
	public WebElement WaitForElementToBeClickable(By locator) {
		try {
			return (WebElement) FluentWait(ExpectedConditions
					.elementToBeClickable(locator));
		} catch (NoSuchElementException | TimeoutException e) {
			return null;
		}
	}

	/**
	 * An expectation for checking that an element is present on the DOM of a
	 * page and visible. Visibility means that the element is not only displayed
	 * but also has a height and width that is greater than 0.
	 *
	 * @param locator
	 *            used to find the element
	 * @return the WebElement once it is located and visible
	 */
	public WebElement WaitForVisibilityOfElement(By locator) {
		try {
			return (WebElement) FluentWait(ExpectedConditions
					.visibilityOfElementLocated(locator));
		} catch (NoSuchElementException | TimeoutException e) {
			return null;
		}
	}

	/**
	 * An expectation for checking that there is at least one element present on
	 * a web page.
	 *
	 * @param locator
	 *            used to find the element
	 * @return the list of WebElements once they are located
	 */
	@SuppressWarnings("unchecked")
	public List<WebElement> WaitForPresenceOfElements(By locator) {
		try {
			return (List<WebElement>) FluentWait(ExpectedConditions
					.presenceOfAllElementsLocatedBy(locator));
		} catch (NoSuchElementException | TimeoutException e) {
			return null;
		}
	}

	/**
	 * An expectation for checking that all elements present on the web page
	 * that match the locator are visible. Visibility means that the elements
	 * are not only displayed but also have a height and width that is greater
	 * than 0.
	 *
	 * @param locator
	 *            used to find the element
	 * @return the list of WebElements once they are located
	 */
	@SuppressWarnings("unchecked")
	public List<WebElement> WaitForVisibilityOfAllElements(By locator) {
		try {
			return (List<WebElement>) FluentWait(ExpectedConditions
					.visibilityOfAllElementsLocatedBy(locator));
		} catch (NoSuchElementException | TimeoutException e) {
			return null;
		}
	}

	/**
	 * An expectation for checking whether the given frame is available to
	 * switch to.
	 * <p>
	 * If the frame is available it switches the given driver to the specified
	 * frame.
	 *
	 * @param frameName
	 *            used to find the frame (id or name)
	 * @return WebDriver instance after frame has been switched
	 */
	public void WaitForFrameToBeAvailableAndSwitchToIt(String frameName) {
		try {
			FluentWait(ExpectedConditions
					.frameToBeAvailableAndSwitchToIt(frameName));
		} catch (NoSuchElementException | TimeoutException e) {
			System.out.println("Frame (" + frameName + ") NOT Available");
		}
	}

	/**
	 * An expectation for checking whether the given frame is available to
	 * switch to.
	 * <p>
	 * If the frame is available it switches the given driver to the specified
	 * frame and checks if the specified element is available
	 * <p>
	 * If unavailable, return to default content and begin loop again
	 *
	 * @param frameName
	 *            used to find the frame (id or name)
	 * @param locator
	 *            element wanted inside frame
	 * @returns WebElement based off locator
	 */
	public WebElement WaitForFrameElement(String frameName, By locator) {
		WebElement frameElement = null;
		while (true) {
			frameElement = WaitForPresenceOfElement(30, locator);
			if (frameElement != null) {
				break;
			} else {
				driver.switchTo().defaultContent();
				WaitForFrameToBeAvailableAndSwitchToIt(30, frameName);
			}
		}
		return frameElement;
	}

	public WebElement WaitForFrameElementVisibility(String frameName, By locator) {
		WebElement frameElement = null;
		while (true) {
			frameElement = WaitForVisibilityOfElement(30, locator);
			if (frameElement != null) {
				break;
			} else {
				driver.switchTo().defaultContent();
				WaitForFrameToBeAvailableAndSwitchToIt(30, frameName);
			}
		}
		return frameElement;
	}

	/*
	 * Methods using time as var are below
	 */

	private Object FluentWait(int timeInSeconds,ExpectedCondition<?> expectedCondition) throws TimeoutException {
		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				.withTimeout(timeInSeconds, TimeUnit.SECONDS)
				.pollingEvery(250, TimeUnit.MILLISECONDS)
				.ignoring(NoSuchElementException.class);
		return wait.until(expectedCondition);
	}

	public Object WaitForExpectedCondition(int timeInSeconds,
			ExpectedCondition<?> expectedCondition) {
		return FluentWait(timeInSeconds, expectedCondition);
	}

	/**
	 * An expectation for checking that an element is present on the DOM of a
	 * page. This does not necessarily mean that the element is visible.
	 *
	 * @param locator
	 *            used to find the element
	 * @return the WebElement once it is located
	 */
	public WebElement WaitForPresenceOfElement(int timeInSeconds, By locator) {
		try {
			return (WebElement) FluentWait(timeInSeconds,
					ExpectedConditions.presenceOfElementLocated(locator));
		} catch (NoSuchElementException | TimeoutException e) {
			return null;
		}
	}

	/**
	 * An expectation for checking that an element is no longer present in the
	 * DOM of a page.
	 *
	 * @param locator
	 *            used to find the element
	 */
	public boolean WaitForInvisibilityOfElementLocated(int timeInSeconds,
			By locator) {
		try {
			return (boolean) FluentWait(timeInSeconds,
					ExpectedConditions.invisibilityOfElementLocated(locator));
		} catch (NoSuchElementException | TimeoutException e) {
			return false;
		}
	}

	/**
	 * An expectation for checking an element is visible and enabled such that
	 * you can click it.
	 *
	 * @param locator
	 *            used to find the element
	 * @return the WebElement once it is located and clickable (visible and
	 *         enabled)
	 */
	public WebElement WaitForElementToBeClickable(int timeInSeconds, By locator) {
		try {
			return (WebElement) FluentWait(timeInSeconds,
					ExpectedConditions.elementToBeClickable(locator));
		} catch (NoSuchElementException | TimeoutException e) {
			return null;
		}
	}

	/**
	 * An expectation for checking that an element is present on the DOM of a
	 * page and visible. Visibility means that the element is not only displayed
	 * but also has a height and width that is greater than 0.
	 *
	 * @param locator
	 *            used to find the element
	 * @return the WebElement once it is located and visible
	 */
	public WebElement WaitForVisibilityOfElement(int timeInSeconds, By locator) {
		try {
			return (WebElement) FluentWait(timeInSeconds,
					ExpectedConditions.visibilityOfElementLocated(locator));
		} catch (NoSuchElementException | TimeoutException e) {
			return null;
		}
	}

	/**
	 * An expectation for checking that there is at least one element present on
	 * a web page.
	 *
	 * @param locator
	 *            used to find the element
	 * @return the list of WebElements once they are located
	 */
	@SuppressWarnings("unchecked")
	public List<WebElement> WaitForPresenceOfElements(int timeInSeconds,
			By locator) {
		try {
			return (List<WebElement>) FluentWait(timeInSeconds,
					ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
		} catch (NoSuchElementException | TimeoutException e) {
			return null;
		}
	}

	/**
	 * An expectation for checking that all elements present on the web page
	 * that match the locator are visible. Visibility means that the elements
	 * are not only displayed but also have a height and width that is greater
	 * than 0.
	 *
	 * @param locator
	 *            used to find the element
	 * @return the list of WebElements once they are located
	 */
	@SuppressWarnings("unchecked")
	public List<WebElement> WaitForVisibilityOfAllElements(int timeInSeconds,
			By locator) {
		try {
			return (List<WebElement>) FluentWait(timeInSeconds,
					ExpectedConditions
							.visibilityOfAllElementsLocatedBy(locator));
		} catch (NoSuchElementException | TimeoutException e) {
			return null;
		}
	}

	/**
	 * An expectation for checking whether the given frame is available to
	 * switch to.
	 * <p>
	 * If the frame is available it switches the given driver to the specified
	 * frame.
	 *
	 * @param frameName
	 *            used to find the frame (id or name)
	 * @return WebDriver instance after frame has been switched
	 */
	public void WaitForFrameToBeAvailableAndSwitchToIt(int timeInSeconds,
			String frameName) {
		try {
			FluentWait(timeInSeconds,
					ExpectedConditions
							.frameToBeAvailableAndSwitchToIt(frameName));
		} catch (NoSuchElementException | TimeoutException e) {
			System.out.println("Frame (" + frameName + ") NOT Available");
		}
	}

	/**
	 * An expectation for checking whether the given frame is available to
	 * switch to.
	 * <p>
	 * If the frame is available it switches the given driver to the specified
	 * frame and checks if the specified element is available
	 * <p>
	 * If unavailable, return to default content and begin loop again
	 *
	 * @param frameName
	 *            used to find the frame (id or name)
	 * @param locator
	 *            element wanted inside frame
	 */
	public WebElement WaitForFrameElement(String frameName, By locator,
			int timeInSeconds) {
		WebElement frameElement = null;
		while (true) {
			frameElement = WaitForPresenceOfElement(timeInSeconds, locator);
			if (frameElement != null) {
				break;
			} else {
				driver.switchTo().defaultContent();
				WaitForFrameToBeAvailableAndSwitchToIt(timeInSeconds, frameName);
			}
		}
		return frameElement;
	}

	public WebElement WaitForFrameElementVisibility(String frameName,
			By locator, int timeInSeconds) {
		WebElement frameElement = null;
		while (true) {
			frameElement = WaitForVisibilityOfElement(timeInSeconds, locator);
			if (frameElement != null) {
				break;
			} else {
				driver.switchTo().defaultContent();
				WaitForFrameToBeAvailableAndSwitchToIt(timeInSeconds, frameName);
			}
		}
		return frameElement;
	}
/*
	public void persistentClick(By locator) throws InterruptedException {
		int attempts = 0;
		WebElement element = WaitForElementToBeClickable(locator);
		element.click();
		CheckForAlertToBePresent();
		CheckForSSLCert();
		while (WaitForInvisibilityOfElementLocated(5, locator) != true
				&& attempts <= 2) {
			try {
				element.click();
				CheckForAlertToBePresent();
				CheckForSSLCert();
			} catch (WebDriverException e) {
			}
			attempts++;
		}
	}

	public void persistentClick(int timeInSeconds, By locator)
			throws InterruptedException {
		WebElement element = WaitForElementToBeClickable(timeInSeconds, locator);
		element.click();
		CheckForAlertToBePresent();
		CheckForSSLCert();
		while (WaitForInvisibilityOfElementLocated(timeInSeconds, locator) != true) {
			try {
				element.click();
				CheckForAlertToBePresent();
				CheckForSSLCert();
			} catch (Exception e) {
			}
		}
	}

	public void persistentClick(int timeInSeconds, int attmepts, By locator)
			throws InterruptedException {
		int attempts = 0;
		WebElement element = WaitForElementToBeClickable(timeInSeconds, locator);
		element.click();
		CheckForAlertToBePresent();
		CheckForSSLCert();
		while (WaitForInvisibilityOfElementLocated(timeInSeconds, locator) != true
				&& attempts <= attempts) {
			try {
				element.click();
				CheckForAlertToBePresent();
				CheckForSSLCert();
			} catch (Exception e) {
			}
			attempts++;
		}
	}
*/
}