package com.juanjaun.ph.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * @Author: zhaocun
 * @Date: 2023/09/26/17:23
 * @Description:
 */
public class school {
    public static void main(String[] args) {
        try {
            Document document = Jsoup.connect("https://www.dxsbb.com/news/1643.html").get();
            Elements elements = document.select("div.tablebox");
            Elements tbody = elements.select("td");
            for (int i = 0; i <tbody.size() ; i++) {
                if (tbody.get(i).text().contains("学院") || tbody.get(i).text().contains("大学")){
                    System.out.println(tbody.get(i).text());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
