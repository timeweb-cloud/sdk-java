/*
 * Timeweb Cloud API
 * # Введение API Timeweb Cloud позволяет вам управлять ресурсами в облаке программным способом с использованием обычных HTTP-запросов.  Множество функций, которые доступны в панели управления Timeweb Cloud, также доступны через API, что позволяет вам автоматизировать ваши собственные сценарии.  В этой документации сперва будет описан общий дизайн и принципы работы API, а после этого конкретные конечные точки. Также будут приведены примеры запросов к ним.   ## Запросы Запросы должны выполняться по протоколу `HTTPS`, чтобы гарантировать шифрование транзакций. Поддерживаются следующие методы запроса: |Метод|Применение| |--- |--- | |GET|Извлекает данные о коллекциях и отдельных ресурсах.| |POST|Для коллекций создает новый ресурс этого типа. Также используется для выполнения действий с конкретным ресурсом.| |PUT|Обновляет существующий ресурс.| |PATCH|Некоторые ресурсы поддерживают частичное обновление, то есть обновление только части атрибутов ресурса, в этом случае вместо метода PUT будет использован PATCH.| |DELETE|Удаляет ресурс.|  Методы `POST`, `PUT` и `PATCH` могут включать объект в тело запроса с типом содержимого `application/json`.  ### Параметры в запросах Некоторые коллекции поддерживают пагинацию, поиск или сортировку в запросах. В параметрах запроса требуется передать: - `limit` — обозначает количество записей, которое необходимо вернуть  - `offset` — указывает на смещение, относительно начала списка  - `search` — позволяет указать набор символов для поиска  - `sort` — можно задать правило сортировки коллекции  ## Ответы Запросы вернут один из следующих кодов состояния ответа HTTP:  |Статус|Описание| |--- |--- | |200 OK|Действие с ресурсом было выполнено успешно.| |201 Created|Ресурс был успешно создан. При этом ресурс может быть как уже готовым к использованию, так и находиться в процессе запуска.| |204 No Content|Действие с ресурсом было выполнено успешно, и ответ не содержит дополнительной информации в теле.| |400 Bad Request|Был отправлен неверный запрос, например, в нем отсутствуют обязательные параметры и т. д. Тело ответа будет содержать дополнительную информацию об ошибке.| |401 Unauthorized|Ошибка аутентификации.| |403 Forbidden|Аутентификация прошла успешно, но недостаточно прав для выполнения действия.| |404 Not Found|Запрашиваемый ресурс не найден.| |409 Conflict|Запрос конфликтует с текущим состоянием.| |423 Locked|Ресурс из запроса заблокирован от применения к нему указанного метода.| |429 Too Many Requests|Был достигнут лимит по количеству запросов в единицу времени.| |500 Internal Server Error|При выполнении запроса произошла какая-то внутренняя ошибка. Чтобы решить эту проблему, лучше всего создать тикет в панели управления.|  ### Структура успешного ответа Все конечные точки будут возвращать данные в формате `JSON`. Ответы на `GET`-запросы будут иметь на верхнем уровне следующую структуру атрибутов:  |Название поля|Тип|Описание| |--- |--- |--- | |[entity_name]|object, object[], string[], number[], boolean|Динамическое поле, которое будет меняться в зависимости от запрашиваемого ресурса и будет содержать все атрибуты, необходимые для описания этого ресурса. Например, при запросе списка баз данных будет возвращаться поле `dbs`, а при запросе конкретного облачного сервера `server`. Для некоторых конечных точек в ответе может возвращаться сразу несколько ресурсов.| |meta|object|Опционально. Объект, который содержит вспомогательную информацию о ресурсе. Чаще всего будет встречаться при запросе коллекций и содержать поле `total`, которое будет указывать на количество элементов в коллекции.| |response_id|string|Опционально. В большинстве случаев в ответе будет содержаться ID ответа в формате UUIDv4, который однозначно указывает на ваш запрос внутри нашей системы. Если вам потребуется задать вопрос нашей поддержке, приложите к вопросу этот ID— так мы сможем найти ответ на него намного быстрее. Также вы можете использовать этот ID, чтобы убедиться, что это новый ответ на запрос и результат не был получен из кэша.|  Пример запроса на получение списка SSH-ключей: ```     HTTP/2.0 200 OK     {       \"ssh_keys\":[           {             \"body\":\"ssh-rsa AAAAB3NzaC1sdfghjkOAsBwWhs= example@device.local\",             \"created_at\":\"2021-09-15T19:52:27Z\",             \"expired_at\":null,             \"id\":5297,             \"is_default\":false,             \"name\":\"example@device.local\",             \"used_at\":null,             \"used_by\":[]           }       ],       \"meta\":{           \"total\":1       },       \"response_id\":\"94608d15-8672-4eed-8ab6-28bd6fa3cdf7\"     } ```  ### Структура ответа с ошибкой |Название поля|Тип|Описание| |--- |--- |--- | |status_code|number|Короткий числовой идентификатор ошибки.| |error_code|string|Короткий текстовый идентификатор ошибки, который уточняет числовой идентификатор и удобен для программной обработки. Самый простой пример — это код `not_found` для ошибки 404.| |message|string, string[]|Опционально. В большинстве случаев в ответе будет содержаться человекочитаемое подробное описание ошибки или ошибок, которые помогут понять, что нужно исправить.| |response_id|string|Опционально. В большинстве случае в ответе будет содержаться ID ответа в формате UUIDv4, который однозначно указывает на ваш запрос внутри нашей системы. Если вам потребуется задать вопрос нашей поддержке, приложите к вопросу этот ID — так мы сможем найти ответ на него намного быстрее.|  Пример: ```     HTTP/2.0 403 Forbidden     {       \"status_code\": 403,       \"error_code\":  \"forbidden\",       \"message\":     \"You do not have access for the attempted action\",       \"response_id\": \"94608d15-8672-4eed-8ab6-28bd6fa3cdf7\"     } ```  ## Статусы ресурсов Важно учесть, что при создании большинства ресурсов внутри платформы вам будет сразу возвращен ответ от сервера со статусом `200 OK` или `201 Created` и ID созданного ресурса в теле ответа, но при этом этот ресурс может быть ещё в *состоянии запуска*.  Для того чтобы понять, в каком состоянии сейчас находится ваш ресурс, мы добавили поле `status` в ответ на получение информации о ресурсе.  Список статусов будет отличаться в зависимости от типа ресурса. Увидеть поддерживаемый список статусов вы сможете в описании каждого конкретного ресурса.     ## Ограничение скорости запросов (Rate Limiting) Чтобы обеспечить стабильность для всех пользователей, Timeweb Cloud защищает API от всплесков входящего трафика, анализируя количество запросов c каждого аккаунта к каждой конечной точке.  Если ваше приложение отправляет более 20 запросов в секунду на одну конечную точку, то для этого запроса API может вернуть код состояния HTTP `429 Too Many Requests`.   ## Аутентификация Доступ к API осуществляется с помощью JWT-токена. Токенами можно управлять внутри панели управления Timeweb Cloud в разделе *API и Terraform*.  Токен необходимо передавать в заголовке каждого запроса в формате: ```   Authorization: Bearer $TIMEWEB_CLOUD_TOKEN ```  ## Формат примеров API Примеры в этой документации описаны с помощью `curl`, HTTP-клиента командной строки. На компьютерах `Linux` и `macOS` обычно по умолчанию установлен `curl`, и он доступен для загрузки на всех популярных платформах, включая `Windows`.  Каждый пример разделен на несколько строк символом `\\`, который совместим с `bash`. Типичный пример выглядит так: ```   curl -X PATCH      -H \"Content-Type: application/json\"      -H \"Authorization: Bearer $TIMEWEB_CLOUD_TOKEN\"      -d '{\"name\":\"Cute Corvus\",\"comment\":\"Development Server\"}'      \"https://api.timeweb.cloud/api/v1/dedicated/1051\" ``` - Параметр `-X` задает метод запроса. Для согласованности метод будет указан во всех примерах, даже если он явно не требуется для методов `GET`. - Строки `-H` задают требуемые HTTP-заголовки. - Примеры, для которых требуется объект JSON в теле запроса, передают требуемые данные через параметр `-d`.  Чтобы использовать приведенные примеры, не подставляя каждый раз в них свой токен, вы можете добавить токен один раз в переменные окружения в вашей консоли. Например, на `Linux` это можно сделать с помощью команды:  ``` TIMEWEB_CLOUD_TOKEN=\"token\" ```  После этого токен будет автоматически подставляться в ваши запросы.  Обратите внимание, что все значения в этой документации являются примерами. Не полагайтесь на IDы операционных систем, тарифов и т.д., используемые в примерах. Используйте соответствующую конечную точку для получения значений перед созданием ресурсов.   ## Версионирование API построено согласно принципам [семантического версионирования](https://semver.org/lang/ru). Это значит, что мы гарантируем обратную совместимость всех изменений в пределах одной мажорной версии.  Мажорная версия каждой конечной точки обозначается в пути запроса, например, запрос `/api/v1/servers` указывает, что этот метод имеет версию 1.
 *
 * The version of the OpenAPI document: 1.0.0
 * Contact: info@timeweb.cloud
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */


package org.openapitools.client.model;

import java.util.Objects;
import java.util.Arrays;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.math.BigDecimal;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.openapitools.client.JSON;

/**
 * UpdateBalancer
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2025-05-13T14:01:57.127467Z[Etc/UTC]")
public class UpdateBalancer {
  public static final String SERIALIZED_NAME_NAME = "name";
  @SerializedName(SERIALIZED_NAME_NAME)
  private String name;

  /**
   * Алгоритм переключений балансировщика.
   */
  @JsonAdapter(AlgoEnum.Adapter.class)
  public enum AlgoEnum {
    ROUNDROBIN("roundrobin"),
    
    LEASTCONN("leastconn");

    private String value;

    AlgoEnum(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public static AlgoEnum fromValue(String value) {
      for (AlgoEnum b : AlgoEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }

    public static class Adapter extends TypeAdapter<AlgoEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final AlgoEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public AlgoEnum read(final JsonReader jsonReader) throws IOException {
        String value =  jsonReader.nextString();
        return AlgoEnum.fromValue(value);
      }
    }
  }

  public static final String SERIALIZED_NAME_ALGO = "algo";
  @SerializedName(SERIALIZED_NAME_ALGO)
  private AlgoEnum algo;

  public static final String SERIALIZED_NAME_IS_STICKY = "is_sticky";
  @SerializedName(SERIALIZED_NAME_IS_STICKY)
  private Boolean isSticky;

  public static final String SERIALIZED_NAME_IS_USE_PROXY = "is_use_proxy";
  @SerializedName(SERIALIZED_NAME_IS_USE_PROXY)
  private Boolean isUseProxy;

  public static final String SERIALIZED_NAME_IS_SSL = "is_ssl";
  @SerializedName(SERIALIZED_NAME_IS_SSL)
  private Boolean isSsl;

  public static final String SERIALIZED_NAME_IS_KEEPALIVE = "is_keepalive";
  @SerializedName(SERIALIZED_NAME_IS_KEEPALIVE)
  private Boolean isKeepalive;

  /**
   * Протокол.
   */
  @JsonAdapter(ProtoEnum.Adapter.class)
  public enum ProtoEnum {
    HTTP("http"),
    
    HTTP2("http2"),
    
    HTTPS("https"),
    
    TCP("tcp");

    private String value;

    ProtoEnum(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public static ProtoEnum fromValue(String value) {
      for (ProtoEnum b : ProtoEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }

    public static class Adapter extends TypeAdapter<ProtoEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final ProtoEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public ProtoEnum read(final JsonReader jsonReader) throws IOException {
        String value =  jsonReader.nextString();
        return ProtoEnum.fromValue(value);
      }
    }
  }

  public static final String SERIALIZED_NAME_PROTO = "proto";
  @SerializedName(SERIALIZED_NAME_PROTO)
  private ProtoEnum proto;

  public static final String SERIALIZED_NAME_PORT = "port";
  @SerializedName(SERIALIZED_NAME_PORT)
  private BigDecimal port;

  public static final String SERIALIZED_NAME_PATH = "path";
  @SerializedName(SERIALIZED_NAME_PATH)
  private String path;

  public static final String SERIALIZED_NAME_INTER = "inter";
  @SerializedName(SERIALIZED_NAME_INTER)
  private BigDecimal inter;

  public static final String SERIALIZED_NAME_TIMEOUT = "timeout";
  @SerializedName(SERIALIZED_NAME_TIMEOUT)
  private BigDecimal timeout;

  public static final String SERIALIZED_NAME_FALL = "fall";
  @SerializedName(SERIALIZED_NAME_FALL)
  private BigDecimal fall;

  public static final String SERIALIZED_NAME_RISE = "rise";
  @SerializedName(SERIALIZED_NAME_RISE)
  private BigDecimal rise;

  public static final String SERIALIZED_NAME_MAXCONN = "maxconn";
  @SerializedName(SERIALIZED_NAME_MAXCONN)
  private BigDecimal maxconn;

  public static final String SERIALIZED_NAME_CONNECT_TIMEOUT = "connect_timeout";
  @SerializedName(SERIALIZED_NAME_CONNECT_TIMEOUT)
  private BigDecimal connectTimeout;

  public static final String SERIALIZED_NAME_CLIENT_TIMEOUT = "client_timeout";
  @SerializedName(SERIALIZED_NAME_CLIENT_TIMEOUT)
  private BigDecimal clientTimeout;

  public static final String SERIALIZED_NAME_SERVER_TIMEOUT = "server_timeout";
  @SerializedName(SERIALIZED_NAME_SERVER_TIMEOUT)
  private BigDecimal serverTimeout;

  public static final String SERIALIZED_NAME_HTTPREQUEST_TIMEOUT = "httprequest_timeout";
  @SerializedName(SERIALIZED_NAME_HTTPREQUEST_TIMEOUT)
  private BigDecimal httprequestTimeout;

  public UpdateBalancer() {
  }

  public UpdateBalancer name(String name) {
    
    this.name = name;
    return this;
  }

   /**
   * Удобочитаемое имя, установленное для балансировщика.
   * @return name
  **/
  @javax.annotation.Nullable
  public String getName() {
    return name;
  }


  public void setName(String name) {
    this.name = name;
  }


  public UpdateBalancer algo(AlgoEnum algo) {
    
    this.algo = algo;
    return this;
  }

   /**
   * Алгоритм переключений балансировщика.
   * @return algo
  **/
  @javax.annotation.Nullable
  public AlgoEnum getAlgo() {
    return algo;
  }


  public void setAlgo(AlgoEnum algo) {
    this.algo = algo;
  }


  public UpdateBalancer isSticky(Boolean isSticky) {
    
    this.isSticky = isSticky;
    return this;
  }

   /**
   * Это логическое значение, которое показывает, сохраняется ли сессия.
   * @return isSticky
  **/
  @javax.annotation.Nullable
  public Boolean getIsSticky() {
    return isSticky;
  }


  public void setIsSticky(Boolean isSticky) {
    this.isSticky = isSticky;
  }


  public UpdateBalancer isUseProxy(Boolean isUseProxy) {
    
    this.isUseProxy = isUseProxy;
    return this;
  }

   /**
   * Это логическое значение, которое показывает, выступает ли балансировщик в качестве прокси.
   * @return isUseProxy
  **/
  @javax.annotation.Nullable
  public Boolean getIsUseProxy() {
    return isUseProxy;
  }


  public void setIsUseProxy(Boolean isUseProxy) {
    this.isUseProxy = isUseProxy;
  }


  public UpdateBalancer isSsl(Boolean isSsl) {
    
    this.isSsl = isSsl;
    return this;
  }

   /**
   * Это логическое значение, которое показывает, требуется ли перенаправление на SSL.
   * @return isSsl
  **/
  @javax.annotation.Nullable
  public Boolean getIsSsl() {
    return isSsl;
  }


  public void setIsSsl(Boolean isSsl) {
    this.isSsl = isSsl;
  }


  public UpdateBalancer isKeepalive(Boolean isKeepalive) {
    
    this.isKeepalive = isKeepalive;
    return this;
  }

   /**
   * Это логическое значение, которое показывает, выдает ли балансировщик сигнал о проверке жизнеспособности.
   * @return isKeepalive
  **/
  @javax.annotation.Nullable
  public Boolean getIsKeepalive() {
    return isKeepalive;
  }


  public void setIsKeepalive(Boolean isKeepalive) {
    this.isKeepalive = isKeepalive;
  }


  public UpdateBalancer proto(ProtoEnum proto) {
    
    this.proto = proto;
    return this;
  }

   /**
   * Протокол.
   * @return proto
  **/
  @javax.annotation.Nullable
  public ProtoEnum getProto() {
    return proto;
  }


  public void setProto(ProtoEnum proto) {
    this.proto = proto;
  }


  public UpdateBalancer port(BigDecimal port) {
    
    this.port = port;
    return this;
  }

   /**
   * Порт балансировщика.
   * @return port
  **/
  @javax.annotation.Nullable
  public BigDecimal getPort() {
    return port;
  }


  public void setPort(BigDecimal port) {
    this.port = port;
  }


  public UpdateBalancer path(String path) {
    
    this.path = path;
    return this;
  }

   /**
   * Адрес балансировщика.
   * @return path
  **/
  @javax.annotation.Nullable
  public String getPath() {
    return path;
  }


  public void setPath(String path) {
    this.path = path;
  }


  public UpdateBalancer inter(BigDecimal inter) {
    
    this.inter = inter;
    return this;
  }

   /**
   * Интервал проверки.
   * @return inter
  **/
  @javax.annotation.Nullable
  public BigDecimal getInter() {
    return inter;
  }


  public void setInter(BigDecimal inter) {
    this.inter = inter;
  }


  public UpdateBalancer timeout(BigDecimal timeout) {
    
    this.timeout = timeout;
    return this;
  }

   /**
   * Таймаут ответа балансировщика.
   * @return timeout
  **/
  @javax.annotation.Nullable
  public BigDecimal getTimeout() {
    return timeout;
  }


  public void setTimeout(BigDecimal timeout) {
    this.timeout = timeout;
  }


  public UpdateBalancer fall(BigDecimal fall) {
    
    this.fall = fall;
    return this;
  }

   /**
   * Порог количества ошибок.
   * @return fall
  **/
  @javax.annotation.Nullable
  public BigDecimal getFall() {
    return fall;
  }


  public void setFall(BigDecimal fall) {
    this.fall = fall;
  }


  public UpdateBalancer rise(BigDecimal rise) {
    
    this.rise = rise;
    return this;
  }

   /**
   * Порог количества успешных ответов.
   * @return rise
  **/
  @javax.annotation.Nullable
  public BigDecimal getRise() {
    return rise;
  }


  public void setRise(BigDecimal rise) {
    this.rise = rise;
  }


  public UpdateBalancer maxconn(BigDecimal maxconn) {
    
    this.maxconn = maxconn;
    return this;
  }

   /**
   * Максимальное количество соединений.
   * @return maxconn
  **/
  @javax.annotation.Nullable
  public BigDecimal getMaxconn() {
    return maxconn;
  }


  public void setMaxconn(BigDecimal maxconn) {
    this.maxconn = maxconn;
  }


  public UpdateBalancer connectTimeout(BigDecimal connectTimeout) {
    
    this.connectTimeout = connectTimeout;
    return this;
  }

   /**
   * Таймаут подключения.
   * @return connectTimeout
  **/
  @javax.annotation.Nullable
  public BigDecimal getConnectTimeout() {
    return connectTimeout;
  }


  public void setConnectTimeout(BigDecimal connectTimeout) {
    this.connectTimeout = connectTimeout;
  }


  public UpdateBalancer clientTimeout(BigDecimal clientTimeout) {
    
    this.clientTimeout = clientTimeout;
    return this;
  }

   /**
   * Таймаут клиента.
   * @return clientTimeout
  **/
  @javax.annotation.Nullable
  public BigDecimal getClientTimeout() {
    return clientTimeout;
  }


  public void setClientTimeout(BigDecimal clientTimeout) {
    this.clientTimeout = clientTimeout;
  }


  public UpdateBalancer serverTimeout(BigDecimal serverTimeout) {
    
    this.serverTimeout = serverTimeout;
    return this;
  }

   /**
   * Таймаут сервера.
   * @return serverTimeout
  **/
  @javax.annotation.Nullable
  public BigDecimal getServerTimeout() {
    return serverTimeout;
  }


  public void setServerTimeout(BigDecimal serverTimeout) {
    this.serverTimeout = serverTimeout;
  }


  public UpdateBalancer httprequestTimeout(BigDecimal httprequestTimeout) {
    
    this.httprequestTimeout = httprequestTimeout;
    return this;
  }

   /**
   * Таймаут HTTP запроса.
   * @return httprequestTimeout
  **/
  @javax.annotation.Nullable
  public BigDecimal getHttprequestTimeout() {
    return httprequestTimeout;
  }


  public void setHttprequestTimeout(BigDecimal httprequestTimeout) {
    this.httprequestTimeout = httprequestTimeout;
  }



  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UpdateBalancer updateBalancer = (UpdateBalancer) o;
    return Objects.equals(this.name, updateBalancer.name) &&
        Objects.equals(this.algo, updateBalancer.algo) &&
        Objects.equals(this.isSticky, updateBalancer.isSticky) &&
        Objects.equals(this.isUseProxy, updateBalancer.isUseProxy) &&
        Objects.equals(this.isSsl, updateBalancer.isSsl) &&
        Objects.equals(this.isKeepalive, updateBalancer.isKeepalive) &&
        Objects.equals(this.proto, updateBalancer.proto) &&
        Objects.equals(this.port, updateBalancer.port) &&
        Objects.equals(this.path, updateBalancer.path) &&
        Objects.equals(this.inter, updateBalancer.inter) &&
        Objects.equals(this.timeout, updateBalancer.timeout) &&
        Objects.equals(this.fall, updateBalancer.fall) &&
        Objects.equals(this.rise, updateBalancer.rise) &&
        Objects.equals(this.maxconn, updateBalancer.maxconn) &&
        Objects.equals(this.connectTimeout, updateBalancer.connectTimeout) &&
        Objects.equals(this.clientTimeout, updateBalancer.clientTimeout) &&
        Objects.equals(this.serverTimeout, updateBalancer.serverTimeout) &&
        Objects.equals(this.httprequestTimeout, updateBalancer.httprequestTimeout);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, algo, isSticky, isUseProxy, isSsl, isKeepalive, proto, port, path, inter, timeout, fall, rise, maxconn, connectTimeout, clientTimeout, serverTimeout, httprequestTimeout);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UpdateBalancer {\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    algo: ").append(toIndentedString(algo)).append("\n");
    sb.append("    isSticky: ").append(toIndentedString(isSticky)).append("\n");
    sb.append("    isUseProxy: ").append(toIndentedString(isUseProxy)).append("\n");
    sb.append("    isSsl: ").append(toIndentedString(isSsl)).append("\n");
    sb.append("    isKeepalive: ").append(toIndentedString(isKeepalive)).append("\n");
    sb.append("    proto: ").append(toIndentedString(proto)).append("\n");
    sb.append("    port: ").append(toIndentedString(port)).append("\n");
    sb.append("    path: ").append(toIndentedString(path)).append("\n");
    sb.append("    inter: ").append(toIndentedString(inter)).append("\n");
    sb.append("    timeout: ").append(toIndentedString(timeout)).append("\n");
    sb.append("    fall: ").append(toIndentedString(fall)).append("\n");
    sb.append("    rise: ").append(toIndentedString(rise)).append("\n");
    sb.append("    maxconn: ").append(toIndentedString(maxconn)).append("\n");
    sb.append("    connectTimeout: ").append(toIndentedString(connectTimeout)).append("\n");
    sb.append("    clientTimeout: ").append(toIndentedString(clientTimeout)).append("\n");
    sb.append("    serverTimeout: ").append(toIndentedString(serverTimeout)).append("\n");
    sb.append("    httprequestTimeout: ").append(toIndentedString(httprequestTimeout)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }


  public static HashSet<String> openapiFields;
  public static HashSet<String> openapiRequiredFields;

  static {
    // a set of all properties/fields (JSON key names)
    openapiFields = new HashSet<String>();
    openapiFields.add("name");
    openapiFields.add("algo");
    openapiFields.add("is_sticky");
    openapiFields.add("is_use_proxy");
    openapiFields.add("is_ssl");
    openapiFields.add("is_keepalive");
    openapiFields.add("proto");
    openapiFields.add("port");
    openapiFields.add("path");
    openapiFields.add("inter");
    openapiFields.add("timeout");
    openapiFields.add("fall");
    openapiFields.add("rise");
    openapiFields.add("maxconn");
    openapiFields.add("connect_timeout");
    openapiFields.add("client_timeout");
    openapiFields.add("server_timeout");
    openapiFields.add("httprequest_timeout");

    // a set of required properties/fields (JSON key names)
    openapiRequiredFields = new HashSet<String>();
  }

 /**
  * Validates the JSON Element and throws an exception if issues found
  *
  * @param jsonElement JSON Element
  * @throws IOException if the JSON Element is invalid with respect to UpdateBalancer
  */
  public static void validateJsonElement(JsonElement jsonElement) throws IOException {
      if (jsonElement == null) {
        if (!UpdateBalancer.openapiRequiredFields.isEmpty()) { // has required fields but JSON element is null
          throw new IllegalArgumentException(String.format("The required field(s) %s in UpdateBalancer is not found in the empty JSON string", UpdateBalancer.openapiRequiredFields.toString()));
        }
      }

      Set<Entry<String, JsonElement>> entries = jsonElement.getAsJsonObject().entrySet();
      // check to see if the JSON string contains additional fields
      for (Entry<String, JsonElement> entry : entries) {
        if (!UpdateBalancer.openapiFields.contains(entry.getKey())) {
          throw new IllegalArgumentException(String.format("The field `%s` in the JSON string is not defined in the `UpdateBalancer` properties. JSON: %s", entry.getKey(), jsonElement.toString()));
        }
      }
        JsonObject jsonObj = jsonElement.getAsJsonObject();
      if ((jsonObj.get("name") != null && !jsonObj.get("name").isJsonNull()) && !jsonObj.get("name").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `name` to be a primitive type in the JSON string but got `%s`", jsonObj.get("name").toString()));
      }
      if ((jsonObj.get("algo") != null && !jsonObj.get("algo").isJsonNull()) && !jsonObj.get("algo").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `algo` to be a primitive type in the JSON string but got `%s`", jsonObj.get("algo").toString()));
      }
      if ((jsonObj.get("proto") != null && !jsonObj.get("proto").isJsonNull()) && !jsonObj.get("proto").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `proto` to be a primitive type in the JSON string but got `%s`", jsonObj.get("proto").toString()));
      }
      if ((jsonObj.get("path") != null && !jsonObj.get("path").isJsonNull()) && !jsonObj.get("path").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `path` to be a primitive type in the JSON string but got `%s`", jsonObj.get("path").toString()));
      }
  }

  public static class CustomTypeAdapterFactory implements TypeAdapterFactory {
    @SuppressWarnings("unchecked")
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
       if (!UpdateBalancer.class.isAssignableFrom(type.getRawType())) {
         return null; // this class only serializes 'UpdateBalancer' and its subtypes
       }
       final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);
       final TypeAdapter<UpdateBalancer> thisAdapter
                        = gson.getDelegateAdapter(this, TypeToken.get(UpdateBalancer.class));

       return (TypeAdapter<T>) new TypeAdapter<UpdateBalancer>() {
           @Override
           public void write(JsonWriter out, UpdateBalancer value) throws IOException {
             JsonObject obj = thisAdapter.toJsonTree(value).getAsJsonObject();
             elementAdapter.write(out, obj);
           }

           @Override
           public UpdateBalancer read(JsonReader in) throws IOException {
             JsonElement jsonElement = elementAdapter.read(in);
             validateJsonElement(jsonElement);
             return thisAdapter.fromJsonTree(jsonElement);
           }

       }.nullSafe();
    }
  }

 /**
  * Create an instance of UpdateBalancer given an JSON string
  *
  * @param jsonString JSON string
  * @return An instance of UpdateBalancer
  * @throws IOException if the JSON string is invalid with respect to UpdateBalancer
  */
  public static UpdateBalancer fromJson(String jsonString) throws IOException {
    return JSON.getGson().fromJson(jsonString, UpdateBalancer.class);
  }

 /**
  * Convert an instance of UpdateBalancer to an JSON string
  *
  * @return JSON string
  */
  public String toJson() {
    return JSON.getGson().toJson(this);
  }
}

