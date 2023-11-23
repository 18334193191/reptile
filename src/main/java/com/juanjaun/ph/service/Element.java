package com.juanjaun.ph.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * @Author: zhaocun
 * @Date: 2023/10/09/15:54
 * @Description:
 */
public class Element {
    public static void main(String[] args) {
        try {
            Document document = Jsoup.connect("https://www.dxsbb.com/news/1643.html").get();

/*            Elements td = document.getElementsByTag("td");
            System.out.println(td);*/
//            Elements tablebox = document.getElementsByClass("tablebox");
//            System.out.println(tablebox);
            org.jsoup.nodes.Element article = document.getElementById("article");
            System.out.println(article);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
