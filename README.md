# spring-cloud-demo

## Config server

> bootstrap.yml / properties 在新版本中已被棄用，若要使用也可以透過 pom 依賴  
> 配置位置是 `spring.cloud.config.uri={locaction}`

```xml

<dependency>
  <groupId>org.springframework.cloud</groupId>
  <artifactId>spring-cloud-starter-bootstrap</artifactId>
</dependency>
```

> 直接使用 application.yml / properties  
> 配置位置是 `spring.config.import=`optional:configserver:{location}

### 文件命名規則

http://localhost:8888/config-client/dev/main  
http://localhost:8888/config-client/prod  
http://localhost:8888/config-client-dev.yml  
http://localhost:8888/config-client-prod.yml  
http://localhost:8888/main/config-client-prod.yml

### 文件訪問規則

```
/{application}/{profile}[/{label}]
/{application}-{profile}.yml
/{label}/{application}-{profile}.yml
/{application}-{profile}.properties
/{label}/{application}-{profile}.properties
```

{application} 就是應用名稱，對應到配置文件上來，就是配置文件的名稱部分。

{profile} 就是配置文件的版本，我們的項目有開發版本、測試環境版本、生產環境版本，對應到配置文件上來就是以 application-{profile}.yml
加以區分，例如application-dev.yml、application-sit.yml、application-prod.yml。

{label} 表示 git 分支，默認是 main/master 分支，如果項目是以分支做區分也是可以的，那就可以通過不同的 label 來控制訪問不同的配置文件了。

## Config client

* 透過 actuator 實現自動刷新配置

http://localhost:8080/show  
http://localhost:8080/autoShow

> 透過觸發更新，可不用重啟服務取得最新資料  
> 注意: 使用 `@Value` 會取得舊資料

[POST] http://localhost:8080/actuator/refresh

發 POST 後，會回傳

```json
[
  "config.client.version",
  "data.user.username",
  "data.user.password"
]
```

## github 配置 Webhook

透過打 [POST] http://localhost:8080/actuator/refresh 太雞肋

這邊使用 ngrok 設置 webhook

### 配置的坑

* 直接在 webhook 設置 ngrok 的/actuator/refresh 會遇到 415 的問題
* 使用 RestTemplate 會遇到 Timeout 問題

### 解決方式

* 自己寫 API 去觸發更新，webhook 來打自己寫的 API

## Spring cloud bus

使用 RabbitMQ，docker 啟動，在 server 端添加配置  
透過 server 去觸發 client   
[POST] http://localhost:8888/actuator/busrefresh

## Spring cloud Netflix Eureka

基本配置 Server side、Client side
