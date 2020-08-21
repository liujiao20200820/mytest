package test1;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.chrome.ChromeDriver;

public class WebTest {


    @SuppressWarnings("deprecation")
	public static void main(String[] args) throws Exception {

            String chromePath = "C://Users//yjt//AppData//Local//Google//Chrome//Application//chromedriver.exe";
            System.setProperty("webdriver.chrome.driver", chromePath);
            WebDriver driver = new ChromeDriver();
            driver.get("https://www.baidu.com");
            Thread.sleep(3000);
            driver.findElement(By.id("kw")).sendKeys("Bing");
            Thread.sleep(1000);

            //获取“百度一下”元素，并自动点击
            driver.findElement(By.id("su")).click();
            Thread.sleep(3000);
            //得到要点击的“下一页”链接
            WebElement Whref = driver.findElement(By.cssSelector("a.J_Ajax.btn.next"));
            Whref.click();
            
            //结果列表输出并保存在文件
            List<WebElement> ll = driver.findElements(By.cssSelector(".title > a"));
            BufferedWriter out = new BufferedWriter(new FileWriter("out.txt"));
            System.out.print("结果列表：");
            for(WebElement w:ll){
                System.out.print(w.getText());
                //分隔符
                System.out.print(" -->  ");
                System.out.print(w.getAttribute("href") + "\n");
                out.write(w.getAttribute("href")+"\n");
                }

            driver.quit();
            
            //读取文本并输出统计结果
            Pattern p = Pattern.compile("(?<=http://|\\.)[^.]*?\\.(com)",Pattern.CASE_INSENSITIVE);
            System.out.print("结果统计：");
            InputStream file = new FileInputStream("out.txt");
            ArrayList<String> strArray = new ArrayList<String>();
            InputStreamReader inputStreamReader = new InputStreamReader(file);    
            int i = 0 , count = 1;
            String webAddress = null;
            BufferedReader br = new BufferedReader(inputStreamReader);
            String line = "";
            line = br.readLine();
            while(line != null) {
            	Matcher m = p.matcher(line);  
            	if(m.find()){
                  webAddress = m.group();
            	}   
                strArray.add(webAddress);
                line = br.readLine();
            }
            
            Map<String, Integer> map = new HashMap<String, Integer>();
            for(String item: strArray){
            	if(map.containsKey(item)){
            		map.put(item, map.get(item).intValue() + 1);
            	}
            	else{
            		map.put(item, new Integer(1));
            	}
            }
            Iterator<String> keys = map.keySet().iterator();
            while(keys.hasNext()){
            	String key = keys.next();
            	System.out.print(key + " --> " + map.get(key).intValue() + "\n");
            }
       
      }



}
