package test;

import org.testng.annotations.Test;

import bingPages.LandingPage;
import bingPages.ResultsPage;
import utils.BaseMethods;
import utils.WordGen;

import org.testng.annotations.BeforeMethod;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.annotations.AfterMethod;

public class SearchRewardsTest {
	public WebDriver driver;

	@Test
	public void main() {
		try {
			// click on the first story on bing landing page
			LandingPage lp = new LandingPage(driver);
			lp.fst_story().click();
			Thread.sleep(5000);

			// on the results page, iterate through the stories row
			int count = 10;
			ResultsPage rp = new ResultsPage(driver);
			List<WebElement> row;
			for (int i = 2; i < count; i++) {
				// because the page refreshes every click, the elments need to get fetched each
				// time
				System.out.println("Search");
				row = rp.articleRow();
				if (row == null) {
					break;
				}
				row.get(i).click();
				Thread.sleep(5000);
			}

			// now do manual searches
			WordGen wg = new WordGen();
			String searchQuery;
			for (int j = 0; j < count; j++) {
				System.out.println("Query Search");
				searchQuery = wg.generateWord();
				rp.searchBox().clear();
				rp.searchBox().sendKeys(searchQuery);
				rp.searchBoxSubmit().click();
				Thread.sleep(5000);
			}

			System.out.println("Test Completed");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@BeforeMethod
	public void beforeMethod() throws InterruptedException {
		final String Edge_property = "webdriver.edge.driver";
		// final String ie_driver_path =
		// "C:\\Users\\mario\\Documents\\WebDrivers\\IEDriverServer.exe"; //reference ie
		// driver web agent 
		final String edge_driver_path = "C:\\Users\\mario\\Documents\\WebDrivers\\MicrosoftWebDriver.exe"; // reference edge driver web agent
		System.setProperty(Edge_property, edge_driver_path); // set the system path to point to the ie driver
		driver = new EdgeDriver(); // create the driver
		BaseMethods base = new BaseMethods(driver);
		base.navigateTo("bing.com");
		Thread.sleep(5000);
	}

	@AfterMethod
	public void afterMethod() {
		driver.quit();
	}

}
