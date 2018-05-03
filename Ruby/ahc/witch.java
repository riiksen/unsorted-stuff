import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

public class Program extends TimerTask{

	static WebDriver driver;
	static Wait<WebDriver> wait;
	public static boolean giveaway = false;
	public static double procent = 0;
	public static String id = "76561198324573476";
	public static int balance = 0;
	public static int lastbal = 0;
	public static boolean bot = false;
	public static JavascriptExecutor js;

	public static void main(String[] args) throws InterruptedException {
		bot = Boolean.valueOf(args[0]);
		String login1 = "";
		String haslo1 = "";
		String guardcode = "";
		if(bot) {
			procent = Double.parseDouble(args[1]);
			giveaway = Boolean.valueOf(args[2]);
			login1 = args[3];
			haslo1 = args[4];
			guardcode = args[5];
			if(!(procent>=0.00 && procent<=1.00)) {
				throw new InterruptedException("out of range 0.00 to 1.00");
			}
		}
		else {
			giveaway = Boolean.valueOf(args[1]);
			login1 = args[2];
			haslo1 = args[3];
			guardcode = args[4];
		}

		System.setProperty("webdriver.chrome.driver","/usr/bin/chromedriver"); // /Users/Seweryn/Desktop/java-libraries/chromedriver.exe

		DesiredCapabilities capabilities = DesiredCapabilities.chrome();
		ChromeOptions options = new ChromeOptions();
		options.addArguments("no-sandbox");
		options.addArguments("load-extension=/root/0.1606_0");
		capabilities.setCapability(ChromeOptions.CAPABILITY, options);

		driver = new ChromeDriver(capabilities);
		driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);

		wait = new FluentWait<WebDriver>(driver)
				.withTimeout(20, TimeUnit.SECONDS)
				.pollingEvery(1, TimeUnit.SECONDS)
				.ignoring(NoSuchElementException.class)
				.ignoring(StaleElementReferenceException.class);


		driver.get("chrome-extension://neodgnejhhhlcdoglifbmioajmagpeci/options.html");
		if(driver.findElement(By.id("enable_checkbox")).isSelected() == false) {
			wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.id("enable_checkbox")))).click();
		}
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.name("account_key")))).sendKeys("NOHEJJJ");
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.id("save")))).click();

		driver.navigate().to("https://csgowitch.com/");
		Thread.sleep(6000);
		if(driver.findElements(By.id("btn-login")).size() != 0) {
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.id("btn-login")))).click();

		WebElement login = wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.id("steamAccountName"))));
		WebElement haslo = wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.id("steamPassword"))));



		for(int i = 0; i<=login1.length()-1; i++) {
		login.sendKeys(Character.toString(login1.charAt(i)));
		Thread.sleep((int) (Math.random() * 300));
		}
		for(int i = 0; i<=haslo1.length()-1; i++) {
		haslo.sendKeys(Character.toString(haslo1.charAt(i)));
		Thread.sleep((int) (Math.random() * 300));
		}
		haslo.submit();

		WebElement steamguard = wait.until(ExpectedConditions.elementToBeClickable(By.id("twofactorcode_entry")));
		for(int i = 0; i<=guardcode.length()-1; i++) {
		steamguard.sendKeys(Character.toString(guardcode.charAt(i)));
		Thread.sleep((int) (Math.random() * 500));
		}
		steamguard.sendKeys(Keys.ENTER);
		Thread.sleep(5000);
		}
		login1 = "";
		haslo1 = "";
		guardcode = "";

		Timer timer = new Timer();

		System.out.println("Bot zostal uruchomiony.");
		Thread.sleep(2000);

		timer.scheduleAtFixedRate(new Program(), 0, 20000);
		}

	@Override
	public void run() {
		try {
			collect_hourly();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void join_giveaway(){
		try {
			if(driver.findElement(By.id("sidebar")).isDisplayed() == false) {
				js.executeScript("document.getElementsByClassName(\"btn-open btn-toggle-chat\")[0].click();");
			}
		if(driver.findElement(By.id("btn-tab-giveaway")).isDisplayed()) {
		wait.until(ExpectedConditions.elementToBeClickable(By.id("btn-tab-giveaway"))).click();

			Thread.sleep(500);
		if(driver.findElement(By.id("giveaway-button")).isDisplayed()) {

			Thread.sleep(500);

		wait.until(ExpectedConditions.elementToBeClickable(By.id("giveaway-button"))).click();

			Thread.sleep(500);
			enter_giveaway_captcha();

			Thread.sleep(1000);
			driver.navigate().refresh();
			Thread.sleep(6000);
		}
		}
	}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			if(driver.findElements(By.id("btn-login")).size() !=0) {
				driver.close();
				System.exit(0);
				System.out.println("finnaly - blad!");
			}
		}
	}

	public static void collect_hourly() throws InterruptedException {
		try {
		String czas = "";

		if(driver.findElement(By.id("sidebar")).isDisplayed() == false) {
			js.executeScript("document.getElementsByClassName(\"btn-open btn-toggle-chat\")[0].click();");
		}
		if(driver.findElements(By.id("btn-tab-chat")).size() != 0) {
		wait.until(ExpectedConditions.elementToBeClickable(By.id("btn-tab-chat"))).click();
		js=(JavascriptExecutor) driver;
		czas = js.executeScript("return document.querySelector(\"a[class='btn-info-box free-coins-switcher']\").text;").toString();

		if(czas.contains("Collect")) {
		Thread.sleep(1000);
		js.executeScript("document.querySelector(\"a[class='btn-info-box free-coins-switcher']\").click();");
		enter_hourlycoins_captcha();
		Thread.sleep(1000);
		driver.navigate().refresh();
			Thread.sleep(6000);

		}

		if(bot) {
		check_bot();
		}

		}
		System.out.println("czas " +czas);
			if(giveaway) {
				join_giveaway();
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			if(driver.findElements(By.id("btn-login")).size() !=0) {
				driver.close();
				System.exit(0);
				System.out.println("finnaly - blad!");
			}
		}
	}

	public static void check_bot() throws InterruptedException {
		wait.until(ExpectedConditions.elementToBeClickable(By.id("btn-tab-chat"))).click();
		String strbalance = js.executeScript("return document.querySelector(\"p[class=balance]\").innerHTML").toString();
		strbalance = strbalance.replaceAll(",", "");
		balance = Integer.valueOf(strbalance);
		if(balance>lastbal && bot == true) {
			int sendbal = 0;
			if(procent == 1.00) {
				sendbal = balance;
			}
			else {
				sendbal = (int) Math.round((balance-lastbal) * procent);
			}
			driver.findElement(By.xpath("/html/body/main/aside[@id='sidebar']/div[@id='chat']/form[@id='chat-form']/textarea")).sendKeys("/send " +id + " " +sendbal);
			driver.findElement(By.xpath("/html/body/main/aside[@id='sidebar']/div[@id='chat']/form[@id='chat-form']/button")).click();
		}
		System.out.println("balance: " + balance + " lastbalance: " + lastbal);
		lastbal = balance;
		Thread.sleep(1000);
	}

	public static void enter_giveaway_captcha() throws InterruptedException {
		try {
			Thread.sleep(1500);
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/main/aside[@id='sidebar']/form[@id='giveaway-form']/div[@class='padding']/input"))).sendKeys(Keys.CONTROL,Keys.SHIFT,Keys.NUMPAD6);
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/main/aside[@id='sidebar']/form[@id='giveaway-form']/div[@class='padding']/input"))).sendKeys(Keys.CONTROL,Keys.SHIFT,Keys.NUMPAD3);
			int input_lz=0;
			int execution_time = 0;
			while(input_lz !=5 && input_lz!=6) {
				input_lz = js.executeScript("return document.getElementsByName(\"captcha\")[1].value;").toString().length();
				boolean solved = Boolean.valueOf(js.executeScript("return document.body.contains(document.getElementsByClassName(\"antigate_solver solved\")[0]);").toString());
				if(solved) {
					input_lz = 5;
				}
				if(execution_time>=70) {
					input_lz = 5;
				}
				execution_time++;
				Thread.sleep(500);
			}
			if(driver.findElements(By.xpath("/html/body/main/aside[@id='sidebar']/form[@id='giveaway-form']/div[@class='padding']/input")).size()!=0) {
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/main/aside[@id='sidebar']/form[@id='giveaway-form']/div[@class='padding']/input"))).sendKeys(Keys.ENTER);
			}
		}
		catch(Exception e) {
			driver.navigate().refresh();
			e.printStackTrace();
		}
		finally {
			if(driver.findElements(By.id("btn-login")).size() !=0) {
				driver.close();
				System.exit(0);
				System.out.println("finnaly - blad!");
			}
		}
	}
	public static void enter_hourlycoins_captcha() throws InterruptedException {
		try {
			Thread.sleep(1500);
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/main/aside[@id='sidebar']/form[@id='free-coins']/div[@class='padding']/input"))).sendKeys(Keys.CONTROL,Keys.SHIFT,Keys.NUMPAD6);
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/main/aside[@id='sidebar']/form[@id='free-coins']/div[@class='padding']/input"))).sendKeys(Keys.CONTROL,Keys.SHIFT,Keys.NUMPAD3);
			int input_lz=0;
			int execution_time = 0;
			while(input_lz !=5 && input_lz!=6) {
				input_lz = js.executeScript("return document.getElementsByName(\"captcha\")[0].value;").toString().length();
				boolean solved = Boolean.valueOf(js.executeScript("return document.body.contains(document.getElementsByClassName(\"antigate_solver solved\")[0]);").toString());
				if(solved) {
					input_lz = 5;
				}
				if(execution_time>=70) {
					input_lz = 5;
				}
				execution_time++;
				Thread.sleep(1000);
			}
			if(driver.findElements(By.xpath("/html/body/main/aside[@id='sidebar']/form[@id='free-coins']/div[@class='padding']/input")).size()!=0) {
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/main/aside[@id='sidebar']/form[@id='free-coins']/div[@class='padding']/input"))).sendKeys(Keys.ENTER);
			}
		}
	catch(Exception e) {
		driver.navigate().refresh();
		e.printStackTrace();
	}
		finally {
			if(driver.findElements(By.id("btn-login")).size() != 0) {
				driver.close();
				System.exit(0);
				System.out.println("finnaly - blad!");
			}
		}
	}

	// WIEM TROCHE SŁABO TEN KOD WYGLĄDA XD

}
