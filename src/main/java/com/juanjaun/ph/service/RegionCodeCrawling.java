package com.juanjaun.ph.service;

import com.juanjaun.ph.entity.SysRegion;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RegionCodeCrawling {

    /**
     * 国家统计局的首页链接
     */
    private static final String LINK = "http://www.stats.gov.cn/sj/tjbz/tjyqhdmhcxhfdm/2023/";
    /**
     * 本次需要采集的省份，多个用逗号分隔，使用前先填写好需要爬取的省份
     * 代码粘贴即用，使用前要先修改需要爬取的省份，如果爬取全国所有省份，使用ArrayList的无参构造方法即可。
     * // 爬取“北京市”行政区划信息
     * private static final List PROVINCE_LIST = new ArrayList<>(Arrays.asList(“北京市”));
     * // 爬取全国所有省份信息
     * private static final List PROVINCE_LIST = new ArrayList<>();
     */
    private static final List<String> PROVINCE_LIST = new ArrayList<>(Arrays.asList("贵州省"));
    /**
     * 存放结果的List
     */
    private static List<SysRegion> REGION_RESULT_LIST = new ArrayList<>();


    public static void main(String[] args) throws IOException {
        int treeLevel = 1;
        getProvince(treeLevel);
    }


    /**
     * 爬取省
     * @param treeLevel
     * @throws IOException
     */
    private static void getProvince(Integer treeLevel) throws IOException {
        // 爬取省份
        Document document = Jsoup.connect(LINK).get();

        Elements provincetrAll = document.select(".provincetr");
        Long sort = 0L;
        for (Element provincetr : provincetrAll) {
            for (Element td : provincetr.children()) {
                Elements a = td.select("a");
                String href = a.attr("href");       // 11.html
                String name = a.text();                         // 浙江省
                if("".equals(href)){
                    // 爬取结束
                    continue;
                }
                Long code = Long.valueOf(href.split("\\.")[0]);
                if (PROVINCE_LIST.isEmpty() || PROVINCE_LIST.contains(name)) {
                    SysRegion region = new SysRegion();
                    region.setId(code);
                    region.setPid(0L);
                    region.setName(name);
                    region.setTreeLevel(treeLevel);
                    region.setLeaf(0);
                    region.setSort(++sort);
                    System.out.println(region.toString());
                    REGION_RESULT_LIST.add(region);

                    //爬取市的入参

                    System.out.println("href 市:"+href);
                    System.out.println("treeLevel:"+treeLevel);
                    System.out.println("code:"+code);

                    getCity(href, treeLevel, code);

                }
            }
        }
    }

    /**
     * 爬取市  http://www.stats.gov.cn/sj/tjbz/tjyqhdmhcxhfdm/2023/52.html
     * @param cityHref /52.html
     * @param treeLevel 2及
     * @param provinceCode  52 省编码
     * @throws IOException
     */
    private static void getCity(String cityHref, Integer treeLevel, Long provinceCode) throws IOException {

        treeLevel = treeLevel + 1;
        Document document = Jsoup.connect(LINK + cityHref).get();

        Elements citytrAll = document.select(".citytr");
        Long sort = 0L;
        for (Element citytr : citytrAll) {
            Element codetd = citytr.child(0);
            Element nametd = citytr.child(1);

            String href = codetd.select("a").attr("href");  // 33/3308.html
            String code = codetd.select("a").text();        // 330800000000
            String name = nametd.select("a").text();        // 衢州市


            SysRegion region = new SysRegion();
            region.setId(Long.valueOf(code));
            region.setPid(provinceCode);
            region.setName(name);
            region.setTreeLevel(treeLevel);
            region.setSort(++sort);
            System.out.println(region.toString());
            REGION_RESULT_LIST.add(region);

            System.out.println("href 区:"+href);
            System.out.println("treeLevel:"+treeLevel);
            System.out.println("code:"+code);
            //爬取区
            getDistrict(href, treeLevel, code);
        }
    }

    /**
     * 爬取区 列入 贵阳市下面的区  http://www.stats.gov.cn/sj/tjbz/tjyqhdmhcxhfdm/2023/52/5201.html
     * @param districtHref 52/5201.html
     * @param treeLevel 2级
     * @param cityCode 520100000000 贵阳市的编码
     * @throws IOException
     */
    private static void getDistrict(String districtHref, Integer treeLevel, String cityCode) throws IOException {
        treeLevel = treeLevel + 1;
        Document document = Jsoup.connect(LINK + districtHref).get();

        Elements countryAll = document.select(".countytr");

        Long sort = 0L;
        for (Element countrytr : countryAll) {

            Element codetd = countrytr.child(0);
            Element nametd = countrytr.child(1);

            String href = codetd.select("a").attr("href");  // 02/330281.html
            if ("".equals(href)) {
                // 该级别空的，为市辖区。
                continue;
            }
            String code = codetd.select("a").text();        // 330281000000
            String name = nametd.select("a").text();        // 余姚市

            SysRegion region = new SysRegion();
            region.setId(Long.valueOf(code));
            region.setPid(Long.valueOf(cityCode));
            region.setName(name);
            region.setTreeLevel(treeLevel);
            region.setLeaf(1);
            region.setSort(++sort);
            System.out.println(region.toString());
            REGION_RESULT_LIST.add(region);

            //爬取镇，街道
//            getStreet(href, treeLevel, code);
        }
    }

    /**
     * 爬取镇，街道
     * @param streetHref
     * @param treeLevel
     * @param streetCode
     * @throws IOException
     */
    private static void getStreet(String streetHref, Integer treeLevel, String streetCode) throws IOException {
        treeLevel = treeLevel + 1;
        Document document = Jsoup.connect(LINK + streetCode.substring(0, 2) + "/" + streetHref).get();

        Elements townAll = document.select(".towntr");

        Long sort = 0L;
        for (Element towntr : townAll) {
            Element codetd = towntr.child(0);
            Element nametd = towntr.child(1);

            String href = codetd.select("a").attr("href");  // 12/330212001.html
            String code = codetd.select("a").text();        // 330212001000
            String name = nametd.select("a").text();        // 下应街道

            SysRegion region = new SysRegion();
            region.setId(Long.valueOf(code));
            region.setPid(Long.valueOf(streetCode));
            region.setName(name);
            region.setTreeLevel(treeLevel);
            region.setLeaf(1);
            region.setSort(++sort);
            System.out.println(region.toString());
            REGION_RESULT_LIST.add(region);

            //爬起村，社区
            getCommunity(href, treeLevel, code);
        }
    }

    /**
     * 爬起村，社区
     * @param communityHref
     * @param treeLevel
     * @param communityCode
     * @throws IOException
     */
    private static void getCommunity(String communityHref, Integer treeLevel, String communityCode) throws IOException {
        treeLevel = treeLevel + 1;
        String a = communityCode.substring(0, 2);
        String b = communityCode.substring(2, 4);
        Document document = Jsoup.connect(LINK + a + "/" + b + "/" + communityHref).get();

        Elements villagetrAll = document.select(".villagetr");
        Long sort = 0L;
        for (Element villagetr : villagetrAll) {
            Element codetd = villagetr.child(0);
            Element nametd = villagetr.child(2);

            String code = codetd.text();        // 330212001005
            String name = nametd.text();        // 东兴社区居委会

            SysRegion region = new SysRegion();
            region.setId(Long.valueOf(code));
            region.setPid(Long.valueOf(communityCode));
            region.setName(name);
            region.setTreeLevel(treeLevel);
            region.setLeaf(1);
            region.setSort(++sort);
            System.out.println(region.toString());
            REGION_RESULT_LIST.add(region);
        }
    }


}