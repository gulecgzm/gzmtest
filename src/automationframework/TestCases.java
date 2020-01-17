package automationframework;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;
import java.util.List;
import org.testng.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;


public class TestCases {
	
	public String baseUrl = "https://www.amazon.com/";
	String driverPath = "C:\\Users\\gizemg\\Desktop\\chromedriver.exe";
	public WebDriver driver;
	public String selectedProduct;
	
	 @BeforeTest  
	  public void beforeTest() {
		  
		  System.setProperty("webdriver.chrome.driver", driverPath);
		  driver = new ChromeDriver();
		  driver.get(baseUrl);
		  driver.manage().window().maximize();
		  
		  
		  Assert.assertTrue(driver.getTitle().contains("Amazon"));
			System.out.println("Amazon page is opened!");
			
		  }
	 
	
		
	  @Test (priority=0)
	  public void Login() {
		  
		  	driver.findElement(By.id("nav-link-accountList")).click();
		  	
		  	try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		  	
		  	driver.findElement(By.className("nav-action-button")).click();
		    driver.findElement(By.id("ap_email")).sendKeys("gizem_test@outlook.com");
		    driver.findElement(By.id("continue")).click();
			driver.findElement(By.id("ap_password")).sendKeys("test12345");
			driver.findElement(By.id("signInSubmit")).click();
			
	  }
	  
	  @Test (priority=1)
	  public void SearchProduct() {
			
		   //search for samsung
			
			driver.findElement(By.id("twotabsearchtextbox")).sendKeys("samsung");
			driver.findElement(By.className("nav-input")).click();
			
			//check listed products
			
			  Assert.assertTrue(driver.findElement(By.cssSelector(".a-color-state.a-text-bold")).getText().contains("samsung"));
			  System.out.println("results are displayed for samsung");
			
			//navigate to 2nd page
			
			driver.findElement(By.cssSelector("ul.a-pagination li:nth-child(3)")).click();


			 //color of 2nd page number for check
			
			 String color = driver.findElement(By.cssSelector("#search > div.s-desktop-width-max.s-desktop-content.sg-row > div.sg-col-20-of-24.sg-col-28-of-32.sg-col-16-of-20.sg-col.s-right-column.sg-col-32-of-36.sg-col-8-of-12.sg-col-12-of-16.sg-col-24-of-28 > div > span:nth-child(10) > div > span > div > div > ul > li.a-selected > a")).getCssValue("color").trim();    
			 String color_hex[];  
			 color_hex = color.replace("rgba(", "").split(",");       
			 String actual_hex = String.format("#%02x%02x%02x", Integer.parseInt(color_hex[0].trim()), Integer.parseInt(color_hex[1].trim()), Integer.parseInt(color_hex[2].trim()));  
			 System.out.println("Color code should be '#c45500' and actual page color:" + actual_hex );
	  }
			 
		@Test (priority=2)
		public void selectProduct() {
			 
			 //select 3th product
			 driver.findElement(By.cssSelector(".s-result-item[data-index='2'] .a-link-normal")).click();
			 
			 //add to list
			 
			 driver.findElement(By.id("wishListMainButton")).click();
			 
			 try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 
			 /* --add wish list for first list create--
			WebElement wishButton= driver.findElement(By.id("WLNEW_list_type_WL"));
			JavascriptExecutor ex = (JavascriptExecutor) driver;
			ex.executeScript("arguments[0].click();", wishButton);
			
			driver.findElement(By.cssSelector("#WLNEW_create .a-button-input")).click(); */
			
			//related token for added product
			 
			String title =driver.findElement(By.xpath("//*[@id=\"WLHUC_info\"]/div[1]/ul/li[2]/table/tbody/tr/td/a")).getAttribute("href");
			String titleToken[]= title.split("/");
			selectedProduct = titleToken[5]; 
			
			//close pop-up
			
			driver.findElement(By.cssSelector("[id^=a-popover-] > div > header > button")).click();

			 try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
			
		
		@Test (priority=3)
		public void deletefromWishList() {
			
			driver.findElement(By.id("nav-link-accountList")).click();

			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//wishList
			driver.findElement(By.xpath("//*[@id=\"nav-flyout-wl-items\"]/div/a[1]/span")).click();
			
			
			List<WebElement> wes1 = driver.findElements(
					By.cssSelector("[id=g-items] > li"));
			
			for (WebElement we1 : wes1) {
				String productId= we1.getAttribute("data-reposition-action-params").substring(24, 34);
				
				if(productId.equals(selectedProduct)) {
					
					we1.findElement(By.className("a-button-input")).click(); //delete product
					
				}
				
			}
				 
	}

	  @AfterTest
	  public void afterTest() {
		  //driver.close();
	  }

}
