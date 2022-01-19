package com.myfirstproject.homework;

import com.myfirstproject.utilities.TestBase;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CountryPopulation extends TestBase {

    @Test
    public void CountryPopulation(){

        driver.get("https://www.worldometers.info/world-population/population-by-country/");
        List<WebElement> count = driver.findElements(By.xpath("//*[@id=\"example2\"]//tbody//tr//td[2]"));
        List<WebElement> landA = driver.findElements(By.xpath("//*[@id=\"example2\"]//tbody//tr//td[7]"));


        String [] country = new String[count.size()];
        int [] landArea = new int[count.size()];

        for(int i=0; i<count.size();i++){
            country[i] = count.get(i).getText();
            String a = landA.get(i).getText();
            a = a.replaceAll(",","");
            landArea[i]= Integer.valueOf(a);
        }

        String aaa ="";
        int bbb = 0;

        for(int i=0; i<count.size(); i++){
            for (int b=i+1; b< count.size();b++){
                if(landArea[i]<landArea[b]){
                    aaa = country[i];
                    country[i] = country[b];
                    country[b] = aaa;
                    bbb = landArea[i];
                    landArea[i] = landArea[b];
                    landArea[b] = bbb;
                }
            }
            countryLandArea(country[i],landArea[i],i);
        }

        System.out.println(Arrays.toString(country));
        System.out.println(Arrays.toString(landArea));
    }


    public void countryLandArea(String a, int b, int c) {
        try{
            String path = "./src/test/java/resources/CountryPopulation2020.xlsx";
            FileInputStream fileInputStream = new FileInputStream(path);
            Workbook workbook = WorkbookFactory.create(fileInputStream);
            Sheet sheet = workbook.getSheetAt(0);
            sheet.createRow(c).createCell(0).setCellValue(a);
            sheet.getRow(c).createCell(1).setCellValue(b);
            FileOutputStream fileOutputStream = new FileOutputStream(path);
            workbook.write(fileOutputStream);

        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
