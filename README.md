# 爬虫 Jsoup练习

#### 介绍
练习jsoup爬虫技术爬取数据练习

#### 软件架构
软件架构说明


#### 安装教程

导入依赖


     <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>fastjson</artifactId>
            <version>1.2.83</version>
        </dependency>

     <!-- JSOUP 解析-->
        <dependency>
            <groupId>org.jsoup</groupId>
            <artifactId>jsoup</artifactId>
            <version>1.13.1</version>
        </dependency>


#### 使用说明
https://www.dxsbb.com/news/1643.html这个地址换成要爬取数据的地址，div.tablebox这个是要获取自己需要数据的那个标签的class，这个是数据在那个标签上td
           Document document = Jsoup.connect("https://www.dxsbb.com/news/1643.html").get();
            Elements elements = document.select("div.tablebox");
            Elements tbody = elements.select("td");


#### 参与贡献

1.  Fork 本仓库
2.  新建 Feat_xxx 分支
3.  提交代码
4.  新建 Pull Request


#### 特技

1.  使用 Readme\_XXX.md 来支持不同的语言，例如 Readme\_en.md, Readme\_zh.md
2.  Gitee 官方博客 [blog.gitee.com](https://blog.gitee.com)
3.  你可以 [https://gitee.com/explore](https://gitee.com/explore) 这个地址来了解 Gitee 上的优秀开源项目
4.  [GVP](https://gitee.com/gvp) 全称是 Gitee 最有价值开源项目，是综合评定出的优秀开源项目
5.  Gitee 官方提供的使用手册 [https://gitee.com/help](https://gitee.com/help)
6.  Gitee 封面人物是一档用来展示 Gitee 会员风采的栏目 [https://gitee.com/gitee-stars/](https://gitee.com/gitee-stars/)
