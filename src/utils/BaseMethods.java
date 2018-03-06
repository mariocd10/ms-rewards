package utils;

import java.net.MalformedURLException;
import java.net.SocketException;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class BaseMethods {
	private WebDriver driver = null;

	protected Set<String> availableWindows;
	protected int count;

	public BaseMethods(WebDriver driver) {
		this.driver = driver;
	}
	
	/**
	 * Navigates to webpage
	 * 
	 * @param TestURL
	 *            The URL that will be navigate to
	 * @throws InterruptedException
	 * @throws MalformedURLException
	 * @throws SocketException 
	 */
	public void navigateTo(String TestURL) throws InterruptedException {
		driver.get(TestURL);
	}

	protected void CheckForAlertToBePresent() throws InterruptedException {
		while (true) {
			try {
				if (ExpectedConditions.alertIsPresent() != null) {
					driver.switchTo().alert().accept();
					// Slow down loop to allow additional alerts to appear
					Thread.sleep(500);
				}
			} catch (NoAlertPresentException e) {
				break;
			} catch (UnhandledAlertException e) {
				break;
			}

		}
	}

	protected void CheckForSSLCert() throws InterruptedException {
		while (true) {
			try {
				if (ExpectedConditions.presenceOfElementLocated(By
						.id("overridelink")) != null) {
					driver.findElement(By.id("overridelink")).click();
					CheckForAlertToBePresent();
					break;
				}
			} catch (NoSuchElementException e) {
				break;
			}

		}
	}

	/**
	 * JavaScript based mouse over
	 * 
	 * @param elem
	 *            The element the hover will be performed on
	 */
	protected void hoverJS(WebElement elem) {
		try {
			if (isElemPresent(elem)) {

				String mouseOverScript = "if(document.createEvent){var evObj = document.createEvent('MouseEvents');evObj.initEvent('mouseover', true, false); arguments[0].dispatchEvent(evObj);} else if(document.createEventObject) { arguments[0].fireEvent('onmouseover');}";

				((JavascriptExecutor) driver).executeScript(mouseOverScript,elem);

			} else {
				System.out.println("Element was not visible to hover " + "\n");

			}
		} catch (StaleElementReferenceException e) {
			System.out.println("Element with " + elem
					+ "is not attached to the page document"
					+ e.getStackTrace());
		} catch (NoSuchElementException e) {
			System.out.println("Element " + elem
					+ " was not found in DOM" + e.getStackTrace());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error occurred while hovering"
					+ e.getStackTrace());
		}
	}

	/**
	 * Javascript based sendKeys
	 * 
	 * @param element
	 *            The element the sendKeys will be sent to
	 * @param keys
	 *            The string of text to be sent
	 */
	protected void sendKeysJScript(WebElement element, String keys) {
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].value='" + keys + "';", element);
	}

	/**
	 * Javascript based click
	 * 
	 * @param element
	 *            The element the hover will be preformed on
	 */
	protected void clickJScript(WebElement element) {
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click();", element);
	}

	/**
	 * Checks for elements presence
	 * 
	 * @param element
	 *            The element the check will be performed on
	 */
	protected static boolean isElemPresent(WebElement element) {
		boolean flag = false;
		try {
			if (element.isDisplayed() || element.isEnabled())
				flag = true;
		} catch (NoSuchElementException e) {
			flag = false;
		} catch (StaleElementReferenceException e) {
			flag = false;
		}
		return flag;
	}

	/**
	 * Switches to specified window determined by String passed to it
	 *
	 * @param windowName
	 *            String of partial text window title may contain
	 */
	public void SwitchWindows(String windowName) throws InterruptedException {
		boolean contLoop = true;
		String windowTitle = "";

		while (contLoop) {
			for (String windowHandle : driver.getWindowHandles()) {
				try {
					driver.switchTo().window(windowHandle);
					windowTitle = driver.getTitle();
				} catch (Exception e) {
					break;
				}
				if (windowTitle.contains(windowName)) {
					contLoop = false;
					break;
				}
			}
		}
		System.out.println("Final Window Title: " + driver.getTitle());
	}

	/**
	 * Switches to specified window determined by String passed to it
	 *
	 * @param windowName
	 *            String of partial text window title may contain
	 * @param attempts
	 *            Int of how many attempts should be made at switching windows
	 */
	public void SwitchWindows(String windowName, int attempts)
			throws InterruptedException {
		int contLoop = 0;
		String windowTitle = null;
		while (contLoop <= attempts) {
			for (String windowHandle : driver.getWindowHandles()) {
				try {
					driver.switchTo().window(windowHandle);
					windowTitle = driver.getTitle();
				} catch (Exception e) {
					contLoop++;
					break;
				}
				if (windowTitle.contains(windowName)) {
					break;
				} else {
					contLoop++;
				}
			}
		}
		System.out.println("Final Window Title: " + driver.getTitle());
	}

	/**
	 * Switches to specified window determined by String passed to it
	 *
	 * @param context
	 *            String of partial text page source may contain
	 */
	public void SwitchWindowsByPageSource(String context)
			throws InterruptedException {
		boolean contLoop = true;
		String pageSource = "";

		while (contLoop) {

			for (String windowHandle : driver.getWindowHandles()) {
				try {
					driver.switchTo().window(windowHandle);
					pageSource = driver.getPageSource();
				} catch (Exception e) {
					break;
				}
				if (pageSource.contains(context)) {
					contLoop = false;
					break;
				}
			}
		}
		System.out.println("Final Window Title: " + driver.getTitle());
	}

	/**
	 * Switches to specified window determined by String passed to it
	 *
	 * @param context
	 *            String of partial text page source may contain
	 * @param attempts
	 *            Int of how many attempts should be made at switching windows
	 */
	public void SwitchWindowsByPageSource(String context, int attempts)
			throws InterruptedException {
		int contLoop = 0;
		String pageSource = null;
		while (contLoop <= attempts) {
			for (String windowHandle : driver.getWindowHandles()) {
				try {
					driver.switchTo().window(windowHandle);
					pageSource = driver.getPageSource();
				} catch (Exception e) {
					contLoop++;
					break;
				}
				if (pageSource.contains(context)) {
					break;
				} else {
					contLoop++;
				}
			}
		}
		System.out.println("Final Window Title: " + driver.getTitle());
	}
/*
	*//**
	 * Looks for text to be visible on current webpage
	 *
	 * @param tag
	 *            Tag of html code looking for
	 * @param tagvalue
	 *            Value found in tag which is being looked for
	 */
	/*
	public boolean textVerify(String tag, String tagvalue) {
		String tagValueVisible = null;

		try {
			tagValueVisible = WaitForVisibilityOfElement(By.xpath("//" + tag + "[contains(text(),'" + tagvalue+ "')]")).getText();
		} catch (Exception e) {
		}

		if (tagValueVisible != null) {
			if ((tagValueVisible.toUpperCase())
					.contains(tagvalue.toUpperCase())) {
				System.out.println("Tag Value Was Found: " + tagValueVisible);
				return true;
			} else {
				System.out.println("Tagvalues do not match");
				return false;
			}
		} else {
			System.out.println("Tagvalue found null");
			return false;
		}
	}
	*//**
	 * Looks for text to be visible on current webpage using xpath
	 *
	 * @param tag
	 *            Tag of html code looking for
	 * @param tagvalue
	 *            Value found in tag which is being looked for
	 *//*
	public boolean xpathVerification(String xpath) {
		WebElement locator = null;
		try {
			locator = WaitForVisibilityOfElement(By.xpath(xpath));
		} catch (Exception e) {
		}

		if (locator != null) {
			System.out.println("Locator Was Found");
			return true;
		} else {
			System.out.println("Locator not found");
			return false;
		}
	}

	public void sendKeysMatch(WebElement element, CharSequence text)
			throws InterruptedException {
		int maxTime = 10000;
		int maxTimeSeconds = maxTime / 1000;
		Boolean match = false;
		while (match != true) {
			long sysTime = System.currentTimeMillis();
			element.sendKeys(text);
			long sysTimeNow = System.currentTimeMillis();
			if (sysTimeNow > (sysTime + maxTime)) {
				new LogstashHelper().postToLogstash();
				System.out.println("[WARNING: Slow Typing Detected - Send Keys Exceeded " + maxTimeSeconds + " Second(s)]");
			}

			if (element.getText() == text || element.getAttribute("value").equals(text)) {
				match = true;
			} else {
				element.clear();
			}
		}
	}

	public boolean textMatch(WebElement element, CharSequence text)
			throws InterruptedException {
		String getText = element.getText();
		String getAttributeValue = element.getAttribute("value");

		if (getText.equals(text) || getAttributeValue.equals(text)) {
			return true;
		} else {
			return false;
		}
	}

	public void SSOLogin(String username, String password) throws Exception {
		WebElement usernameElement = WaitForVisibilityOfElement(By
				.xpath("//input[@name='USER']"));
		WebElement passwordElement = WaitForVisibilityOfElement(By
				.xpath("//input[@name='PASSWORD' or @name='password']"));

		while (true) {

			// Enter Username
			sendKeysMatch(usernameElement, username);

			// Enter Password
			sendKeysMatch(passwordElement, password);

			if (textMatch(usernameElement, username) == true
					&& textMatch(passwordElement, password) == true) {
				break;
			}
		}

		// Click Submit
		persistentClick(By.name("btn_logon"));
	}*/
}
