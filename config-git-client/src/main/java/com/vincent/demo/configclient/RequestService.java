package com.vincent.demo.configclient;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RequestService {

  public void okhttpPost() {
    MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
    Request request = new Request.Builder()
        .url("http://localhost:8080/actuator/refresh")
        .post(RequestBody.create("{}", mediaType))
        .build();

    OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(5000,
        TimeUnit.MILLISECONDS).readTimeout(6000, TimeUnit.MILLISECONDS).build();

    okHttpClient.newCall(request).enqueue(new Callback() {
      @Override
      public void onFailure(Call call, IOException e) {
        log.info(">>>>>>>>>>>>>>>> 請求發生錯誤,錯誤訊息:{}", e);
      }

      @Override
      public void onResponse(Call call, Response response) throws IOException {
        log.info("{} {} {}", response.protocol(), response.code(), response.message());
        Headers headers = response.headers();
        for (int i = 0; i < headers.size(); i++) {
          log.info("{} : {}", headers.name(i), headers.value(i));
        }
        log.info("onResponse: {}", Objects.requireNonNull(response.body()).string());
      }
    });
  }
}
