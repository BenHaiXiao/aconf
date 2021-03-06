# AConf客户端配置中心介绍

## 一、	What is AConf

### 1.1 背景 

客户端(android,Ios,PC,web,xcx等等)
1. 客户端在版本迭代时，尤其是在进行灰度发布时，由于需要调整很多配置项的值用以观察结果，需要频繁发版修改配置；
2. 客户端对接业务众多，尤其是大公司，大企业一个客户端对接后面众多业务部门，每个部门负责的功能模块不一致，
  同时客户端每次获取配置都需要发送多条请求，加重了客户端压力。
3. 客户端对接的众多业务部门，配置各业务自行管理的，且分散在各个平台，不利于管理；
4. 开发新的配置功能往往需要经历编码，测试，上线等等复杂流程，且风险较高，配置中心统一动态修改配置，提升开发效率。

  ### 2.1简介
  AConf 主要应用于客户端配置统一管理；
  支持如下功能：
5. 配置下发方式要支持pull和push模式，pull即为配置生效后，
  pull客户端主动向服务器发送请求拉取最新数据；push则是在配置后通过长连接形式发送最新数据(该方式预留接口实现，每个公司处理客户端的长连接方式有所差异，目前提供接口规范)。
6. 配置下发条件可控，例如：只对尾号为9的uid或者手机型号iphoneX的客户端下发配置；
7. 配置下发条件可配置性高，通用性好，例如：同样是通过uid进行条件控制，可以是uid以9结尾的用户，可以是uid在一定范围内的用户，可以是uid为指定内容的一批用户，也可以是随机向500名用户发送等。
8. 能够满足多个应用的多个业务接入。


## 系统设计

### 2.1	总体模型

整个系统分为配置下发进程、管理后台和客户端SDK三部分。
如下图所示，管理后台进行应用配置、业务配置和配置项配置的管理。配置下发进程负责处理下发逻辑，为符合下发条件的用户下发配置。客户端SDK封装协议，并在客户端本地缓存配置，方便客户端调用。
 ![image](https://github.com/BenHaiXiao/aconf/blob/master/doc/pic/a0.png?raw=true)
说明：
1. 缓存采用ecache，配置比较少时，建议都配置为在内存中，管理后台更新配置，每个配置下发进程定时更新缓存，达到配置修改的而目的。
2. 配置中心支持多个应用App，一个App对应多个业务部门bss，一个业务bss支持多个配置项config，
  一个配置项支持多个过滤条件condition（每个condition可配置不同的下发值）,一个condition对应多个filter。
3. push线程定时读取mysql中的的push消息。当用户在后台配置push消息后，该消息将被存储到本地ecache中，随后广播消息被广播线程读取，并立即给指定客户端或者列表发送push


### 2.3	拦截器模型
 配置 最复杂多变的部分应该就是下发条件了。对于业务功能的配置，不同的业务会有不同的下发条件。对于灰度发布的配置，不同的客户端也有不同的灰度方案。为了适用于各种类型的下发条件，对下发条件进行抽象，可以概括成以下两种类型：
（1）	计数条件
计数型条件适用于“随机500观众灰度”这样的下发条件，对所有下发配置的用户进行记录。
（2）	边界值条件
大多数情况下的条件都可以表示成边界值条件模型。
边界值条件的逻辑结构：

| 原属性    | 比较属性   | 界定值    | 判定关系 |
| ------ | ------ | ------ | ---- |
| uid    | uid    | 300000 | >    |
| market | market | 小米     | =    |
| os     | os     | 6s     | !=   |


原属性和比较属性之间使用ValueAdapter转换。转换器可以以模板的形式提供，用户可在后台配置。
对应以上两种类型的条件，设计了两种条件拦截器：
 ![image](https://github.com/BenHaiXiao/aconf/blob/master/doc/pic/a1.png?raw=true)
计数拦截器通过计数器控制条件计数，超过设置值则停止计数，且请求总是不通过；
边界值拦截器通过ValueAdapter将原值转换为比较值，再使用通用判决器将比较值和界定值进行比较。多种拦截器可搭配使用。
所有拦截器和使用到的数据在本地均有缓存，请求时无需读库获取。

综上所述，应用、业务、配置项和拦截器之间的关系如下如所示：
 ![image](https://github.com/BenHaiXiao/aconf/blob/master/doc/pic/a2.png?raw=true)
举例:
1. 例1 对所有进入991频道的小米渠道用户下发配置。
   ![image](https://github.com/BenHaiXiao/aconf/blob/master/doc/pic/a3.png?raw=true)
2. 例2 对所有进入尾号为9的频道的勋爵用户下发配置。
  ![image](https://github.com/BenHaiXiao/aconf/blob/master/doc/pic/a4.png?raw=true)
3. 例3 将配置随机发放给500名进入1931频道的观众。
  ![image](https://github.com/BenHaiXiao/aconf/blob/master/doc/pic/a5.png?raw=true)

  ### 2.4逻辑流程
  ![image](https://github.com/BenHaiXiao/aconf/blob/master/doc/pic/a7.png?raw=true)
  业务可配置全局拦截器，若不符合全局条件则直接返回默认列表。


###  2.4 已集成协议

参数列表：

| 参数         | 类型     | 说明                                      |
| ---------- | ------ | --------------------------------------- |
| bssCode    | string | 业务代号，可在管理后台配置，全局唯一。例如："mob-video"       |
| bssVersion | i64    | 当前客户端业务版本号，用于增量下发，默认值设置为0               |
| bssMode    | string | 下发模式(可选),all--全量下发,newest--增量下发         |
| version    | string | 客户端版本号，格式：x.x.x                         |
| client     | string | 客户端类型：pc, ios, android, ipad, web, etc. |
| osVersion  | string | 操作系统版本，例如：android 6.0.1, iOS 10         |
| market     | string | 渠道号                                     |
| net        | string | 网络类型,3G,4G,wifi等                        |
| isp        | string | 网络提供商                                   |
| model      | string | 手机型号                                    |
| extendInfo | Map    | 扩展字段                                    |