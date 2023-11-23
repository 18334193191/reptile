package com.juanjaun.ph.service;

import com.alibaba.fastjson.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: zhaocun
 * @Date: 2023/09/26/11:56
 * @Description:
 */
public class ZhongXue {
    static List<JSONObject>list=new ArrayList();
    public static void main(String[] args) {
        try {
            Document document = Jsoup.connect("https://www.ruyile.com/xuexiao/?a=4443&t=3").get();
            // 选择包含地址的<div>元素并提取文本内容
            Elements elements = document.select("div.fy");
            Elements span = elements.select("span");
            System.out.println(span);
            int i = Integer.parseInt(span.text());
            ps(i);
            System.out.println(list.size());
//            Document document = Jsoup.connect("https://www.ruyile.com/xuexiao/?t=0").get();
//            Elements select = document.select("div.qylb");
//            Element element = select.get(1);
//            Element a = element.select("a").get(6);
//            String href = a.attr("href");
////            System.out.println(a.size());
//            System.out.println(href);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void ps(int s) {
        int i = 1;
        while (i <= s) {
            String url = "https://www.ruyile.com/xuexiao/?a=4443&t=3&p=" + i;
            try {
                Document document = Jsoup.connect(url).get();
                // 选择包含地址的<div>元素并提取文本内容
                Elements elements = document.select("div.sk");
                for (Element element : elements) {
                    JSONObject jsonObject = new JSONObject();
                    String text = element.text();
                    int dzi = text.indexOf("地址：") + 3;
                    String dz = text.substring(dzi);
                    String xs = element.select("a").text();
                    jsonObject.put("地址",dz);
                    jsonObject.put("学校",xs);
                    System.out.println(xs);
                    System.out.println(dz);
                   list.add(jsonObject);
                }
                ++i;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
