import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class LinkTest {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		String url = "";
		HttpURLConnection httpConnection = null;
		int responseCode, totalTags = 0, validUrls = 0, activeCount = 0, inactiveCount = 0;
		
		System.setProperty("webdriver.chrome.driver", "drivers//chromedriver.exe");
		
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("https://www.nordicnaturals.com/consumers/");
		

		List<WebElement> links = driver.findElements(By.tagName("a"));
		System.out.println("Total value in the " +links.size());
		totalTags = links.size();
		
		Iterator<WebElement> it = links.iterator();
		
		while (it.hasNext()) {
			url = it.next().getAttribute("href");

			if (url != null && url.contains("http"))
			{
				
				validUrls++; 
				
				try
				{
					httpConnection = (HttpURLConnection) (new URL(url).openConnection());
					httpConnection.setRequestMethod("HEAD");
					httpConnection.connect();
					responseCode = httpConnection.getResponseCode();
					
					if (responseCode >= 400) 
					{
						System.out.println(url + " is a broken link");
						inactiveCount++;
					} 
					else 
					{
						System.out.println(url + " is an active link");
						activeCount++;
					}
					
				} 
				catch (Exception e) {
					activeCount++;
					System.out.println("The URL is not valid.");
					System.out.println(e.getMessage());
				}
			}
		}

		System.out.println("There are " + totalTags + " anchor tags in which " + validUrls + " are valid links, "
				+ inactiveCount + " are broken and " + activeCount + " are active links");
		
			driver.quit();
	}
}