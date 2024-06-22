package pages.Home;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pages.PageBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage extends PageBase {
    public Logger log;
    private By fashion = By.xpath("//h2[text()='Shop deals in Fashion']");
    private By Refresh_your_space = By.xpath("//h2[text()='Refresh your space']");
    private By pcs = By.xpath("//h2[text()='Deals in PCs']");
    private By dadGift = By.xpath("//h2[text()='Deals on gifts for Dad']");
	public HomePage(WebDriver driver){
        super(driver);
        log = LogManager.getLogger(this.getClass());
    }

    public boolean checkFashion(){
       return waitUntilVisible(fashion);
    }

    public boolean checkRefresh_your_space(){
        log.info("home page logs");
        return waitUntilVisible(Refresh_your_space);
    }
    public boolean checkpcs(){
        log.info("home page logs");
        return waitUntilVisible(pcs);
    }
    public boolean checkDadGift(){
        log.info("home page logs");
        return waitUntilVisible(dadGift);
    }

	
}
