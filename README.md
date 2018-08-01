# webspider
一款基于Java的爬虫,整体参考Scrapy的设计思路

### 项目设计UML图

![](https://note.youdao.com/yws/public/resource/3f4ca8d144526c1b025033a3c480fe60/xmlnote/F57A076C604444F29DF6CC2EB4A0FDCF/14085)

### 整体的业务流程

![](https://note.youdao.com/yws/public/resource/3f4ca8d144526c1b025033a3c480fe60/xmlnote/CB0C6B730BEB493198778DDD9F44529C/14081)

### SpiderTask 组件的调用流程图

![](https://note.youdao.com/yws/public/resource/3f4ca8d144526c1b025033a3c480fe60/xmlnote/E5DB34CE717E4460BEE9CC05265B80C9/14083)



### 关键组件介绍

#### Interface Spider
提供基础的爬虫能力
```
    void start(String... urls);

    void close();

    int taskCount();
```
#### KernelSpider 

抽象类实现Spider基本爬虫能力,并提供模板方法
供子类实现





#### RequestMiddleware
请求中间层,对Request做一些预处理，
比如设置代理ip,设置请求头.


#### Interface HtmlContent

对SpiderLocation进行解析获取到目标地址的html页面

#### Interface HtmlDefinitionParser
解析器从原始html页面中,解析成Document对象
通过两个实现类
- JsoupHtmlDefinitionParser 可以使用css语法
- XpathHtmlDefinitionParser 可以使用xpath语法


#### Interface SpiderHandler
爬虫处理逻辑,从url中获取到Document对象并封装成HtmlDefinition,给开发者,进行爬虫逻辑业务处理。

#### DownloadsPipeline
下载通道,将爬虫爬到的数据,进行预处理，生成要保存的地方


欢迎提出意见和优化点
