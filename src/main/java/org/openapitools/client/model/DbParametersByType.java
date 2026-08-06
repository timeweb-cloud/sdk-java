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
import java.util.ArrayList;
import java.util.List;

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
 * Имена параметров конфигурации, доступных для каждого типа кластера базы данных. Ключ объекта — тип кластера (значение поля &#x60;type&#x60; при создании кластера), значение — массив имён параметров, которые можно передать в &#x60;config_parameters&#x60; для кластера этого типа. Наборы параметров различаются между версиями одной СУБД. Значения параметров этот метод не возвращает — рекомендуемые значения можно получить в &#x60;GET /api/v1/dbs/default-parameters&#x60;.
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2026-08-06T15:06:51.562821Z[Etc/UTC]")
public class DbParametersByType {
  public static final String SERIALIZED_NAME_MYSQL5 = "mysql5";
  @SerializedName(SERIALIZED_NAME_MYSQL5)
  private List<String> mysql5 = new ArrayList<>();

  public static final String SERIALIZED_NAME_MYSQL = "mysql";
  @SerializedName(SERIALIZED_NAME_MYSQL)
  private List<String> mysql = new ArrayList<>();

  public static final String SERIALIZED_NAME_MYSQL84 = "mysql8_4";
  @SerializedName(SERIALIZED_NAME_MYSQL84)
  private List<String> mysql84 = new ArrayList<>();

  public static final String SERIALIZED_NAME_POSTGRES = "postgres";
  @SerializedName(SERIALIZED_NAME_POSTGRES)
  private List<String> postgres = new ArrayList<>();

  public static final String SERIALIZED_NAME_POSTGRES14 = "postgres14";
  @SerializedName(SERIALIZED_NAME_POSTGRES14)
  private List<String> postgres14 = new ArrayList<>();

  public static final String SERIALIZED_NAME_POSTGRES15 = "postgres15";
  @SerializedName(SERIALIZED_NAME_POSTGRES15)
  private List<String> postgres15 = new ArrayList<>();

  public static final String SERIALIZED_NAME_POSTGRES16 = "postgres16";
  @SerializedName(SERIALIZED_NAME_POSTGRES16)
  private List<String> postgres16 = new ArrayList<>();

  public static final String SERIALIZED_NAME_POSTGRES17 = "postgres17";
  @SerializedName(SERIALIZED_NAME_POSTGRES17)
  private List<String> postgres17 = new ArrayList<>();

  public static final String SERIALIZED_NAME_POSTGRES18 = "postgres18";
  @SerializedName(SERIALIZED_NAME_POSTGRES18)
  private List<String> postgres18 = new ArrayList<>();

  public static final String SERIALIZED_NAME_REDIS = "redis";
  @SerializedName(SERIALIZED_NAME_REDIS)
  private List<String> redis = new ArrayList<>();

  public static final String SERIALIZED_NAME_REDIS7 = "redis7";
  @SerializedName(SERIALIZED_NAME_REDIS7)
  private List<String> redis7 = new ArrayList<>();

  public static final String SERIALIZED_NAME_REDIS81 = "redis8_1";
  @SerializedName(SERIALIZED_NAME_REDIS81)
  private List<String> redis81 = new ArrayList<>();

  public static final String SERIALIZED_NAME_VALKEY = "valkey";
  @SerializedName(SERIALIZED_NAME_VALKEY)
  private List<String> valkey = new ArrayList<>();

  public static final String SERIALIZED_NAME_VALKEY7 = "valkey7";
  @SerializedName(SERIALIZED_NAME_VALKEY7)
  private List<String> valkey7 = new ArrayList<>();

  public static final String SERIALIZED_NAME_VALKEY81 = "valkey8_1";
  @SerializedName(SERIALIZED_NAME_VALKEY81)
  private List<String> valkey81 = new ArrayList<>();

  public static final String SERIALIZED_NAME_VALKEY91 = "valkey9_1";
  @SerializedName(SERIALIZED_NAME_VALKEY91)
  private List<String> valkey91 = new ArrayList<>();

  public static final String SERIALIZED_NAME_MONGODB4 = "mongodb4";
  @SerializedName(SERIALIZED_NAME_MONGODB4)
  private List<String> mongodb4 = new ArrayList<>();

  public static final String SERIALIZED_NAME_MONGODB = "mongodb";
  @SerializedName(SERIALIZED_NAME_MONGODB)
  private List<String> mongodb = new ArrayList<>();

  public static final String SERIALIZED_NAME_MONGODB6 = "mongodb6";
  @SerializedName(SERIALIZED_NAME_MONGODB6)
  private List<String> mongodb6 = new ArrayList<>();

  public static final String SERIALIZED_NAME_MONGODB7 = "mongodb7";
  @SerializedName(SERIALIZED_NAME_MONGODB7)
  private List<String> mongodb7 = new ArrayList<>();

  public static final String SERIALIZED_NAME_MONGODB80 = "mongodb8_0";
  @SerializedName(SERIALIZED_NAME_MONGODB80)
  private List<String> mongodb80 = new ArrayList<>();

  public static final String SERIALIZED_NAME_OPENSEARCH = "opensearch";
  @SerializedName(SERIALIZED_NAME_OPENSEARCH)
  private List<String> opensearch = new ArrayList<>();

  public static final String SERIALIZED_NAME_OPENSEARCH219 = "opensearch2_19";
  @SerializedName(SERIALIZED_NAME_OPENSEARCH219)
  private List<String> opensearch219 = new ArrayList<>();

  public static final String SERIALIZED_NAME_CLICKHOUSE = "clickhouse";
  @SerializedName(SERIALIZED_NAME_CLICKHOUSE)
  private List<String> clickhouse = new ArrayList<>();

  public static final String SERIALIZED_NAME_CLICKHOUSE24 = "clickhouse24";
  @SerializedName(SERIALIZED_NAME_CLICKHOUSE24)
  private List<String> clickhouse24 = new ArrayList<>();

  public static final String SERIALIZED_NAME_CLICKHOUSE25 = "clickhouse25";
  @SerializedName(SERIALIZED_NAME_CLICKHOUSE25)
  private List<String> clickhouse25 = new ArrayList<>();

  public static final String SERIALIZED_NAME_KAFKA = "kafka";
  @SerializedName(SERIALIZED_NAME_KAFKA)
  private List<String> kafka = new ArrayList<>();

  public static final String SERIALIZED_NAME_RABBITMQ = "rabbitmq";
  @SerializedName(SERIALIZED_NAME_RABBITMQ)
  private List<String> rabbitmq = new ArrayList<>();

  public static final String SERIALIZED_NAME_RABBITMQ40 = "rabbitmq4_0";
  @SerializedName(SERIALIZED_NAME_RABBITMQ40)
  private List<String> rabbitmq40 = new ArrayList<>();

  public DbParametersByType() {
  }

  public DbParametersByType mysql5(List<String> mysql5) {
    
    this.mysql5 = mysql5;
    return this;
  }

  public DbParametersByType addMysql5Item(String mysql5Item) {
    if (this.mysql5 == null) {
      this.mysql5 = new ArrayList<>();
    }
    this.mysql5.add(mysql5Item);
    return this;
  }

   /**
   * Параметры, доступные для кластеров типа &#x60;mysql5&#x60;.
   * @return mysql5
  **/
  @javax.annotation.Nonnull
  public List<String> getMysql5() {
    return mysql5;
  }


  public void setMysql5(List<String> mysql5) {
    this.mysql5 = mysql5;
  }


  public DbParametersByType mysql(List<String> mysql) {
    
    this.mysql = mysql;
    return this;
  }

  public DbParametersByType addMysqlItem(String mysqlItem) {
    if (this.mysql == null) {
      this.mysql = new ArrayList<>();
    }
    this.mysql.add(mysqlItem);
    return this;
  }

   /**
   * Параметры, доступные для кластеров типа &#x60;mysql&#x60;.
   * @return mysql
  **/
  @javax.annotation.Nonnull
  public List<String> getMysql() {
    return mysql;
  }


  public void setMysql(List<String> mysql) {
    this.mysql = mysql;
  }


  public DbParametersByType mysql84(List<String> mysql84) {
    
    this.mysql84 = mysql84;
    return this;
  }

  public DbParametersByType addMysql84Item(String mysql84Item) {
    if (this.mysql84 == null) {
      this.mysql84 = new ArrayList<>();
    }
    this.mysql84.add(mysql84Item);
    return this;
  }

   /**
   * Параметры, доступные для кластеров типа &#x60;mysql8_4&#x60;.
   * @return mysql84
  **/
  @javax.annotation.Nonnull
  public List<String> getMysql84() {
    return mysql84;
  }


  public void setMysql84(List<String> mysql84) {
    this.mysql84 = mysql84;
  }


  public DbParametersByType postgres(List<String> postgres) {
    
    this.postgres = postgres;
    return this;
  }

  public DbParametersByType addPostgresItem(String postgresItem) {
    if (this.postgres == null) {
      this.postgres = new ArrayList<>();
    }
    this.postgres.add(postgresItem);
    return this;
  }

   /**
   * Параметры, доступные для кластеров типа &#x60;postgres&#x60; (PostgreSQL 13).
   * @return postgres
  **/
  @javax.annotation.Nonnull
  public List<String> getPostgres() {
    return postgres;
  }


  public void setPostgres(List<String> postgres) {
    this.postgres = postgres;
  }


  public DbParametersByType postgres14(List<String> postgres14) {
    
    this.postgres14 = postgres14;
    return this;
  }

  public DbParametersByType addPostgres14Item(String postgres14Item) {
    if (this.postgres14 == null) {
      this.postgres14 = new ArrayList<>();
    }
    this.postgres14.add(postgres14Item);
    return this;
  }

   /**
   * Параметры, доступные для кластеров типа &#x60;postgres14&#x60;.
   * @return postgres14
  **/
  @javax.annotation.Nonnull
  public List<String> getPostgres14() {
    return postgres14;
  }


  public void setPostgres14(List<String> postgres14) {
    this.postgres14 = postgres14;
  }


  public DbParametersByType postgres15(List<String> postgres15) {
    
    this.postgres15 = postgres15;
    return this;
  }

  public DbParametersByType addPostgres15Item(String postgres15Item) {
    if (this.postgres15 == null) {
      this.postgres15 = new ArrayList<>();
    }
    this.postgres15.add(postgres15Item);
    return this;
  }

   /**
   * Параметры, доступные для кластеров типа &#x60;postgres15&#x60;.
   * @return postgres15
  **/
  @javax.annotation.Nonnull
  public List<String> getPostgres15() {
    return postgres15;
  }


  public void setPostgres15(List<String> postgres15) {
    this.postgres15 = postgres15;
  }


  public DbParametersByType postgres16(List<String> postgres16) {
    
    this.postgres16 = postgres16;
    return this;
  }

  public DbParametersByType addPostgres16Item(String postgres16Item) {
    if (this.postgres16 == null) {
      this.postgres16 = new ArrayList<>();
    }
    this.postgres16.add(postgres16Item);
    return this;
  }

   /**
   * Параметры, доступные для кластеров типа &#x60;postgres16&#x60;.
   * @return postgres16
  **/
  @javax.annotation.Nonnull
  public List<String> getPostgres16() {
    return postgres16;
  }


  public void setPostgres16(List<String> postgres16) {
    this.postgres16 = postgres16;
  }


  public DbParametersByType postgres17(List<String> postgres17) {
    
    this.postgres17 = postgres17;
    return this;
  }

  public DbParametersByType addPostgres17Item(String postgres17Item) {
    if (this.postgres17 == null) {
      this.postgres17 = new ArrayList<>();
    }
    this.postgres17.add(postgres17Item);
    return this;
  }

   /**
   * Параметры, доступные для кластеров типа &#x60;postgres17&#x60;.
   * @return postgres17
  **/
  @javax.annotation.Nonnull
  public List<String> getPostgres17() {
    return postgres17;
  }


  public void setPostgres17(List<String> postgres17) {
    this.postgres17 = postgres17;
  }


  public DbParametersByType postgres18(List<String> postgres18) {
    
    this.postgres18 = postgres18;
    return this;
  }

  public DbParametersByType addPostgres18Item(String postgres18Item) {
    if (this.postgres18 == null) {
      this.postgres18 = new ArrayList<>();
    }
    this.postgres18.add(postgres18Item);
    return this;
  }

   /**
   * Параметры, доступные для кластеров типа &#x60;postgres18&#x60;. Набор отличается от предыдущих версий PostgreSQL — например, добавлены &#x60;io_method&#x60; и &#x60;io_workers&#x60;.
   * @return postgres18
  **/
  @javax.annotation.Nonnull
  public List<String> getPostgres18() {
    return postgres18;
  }


  public void setPostgres18(List<String> postgres18) {
    this.postgres18 = postgres18;
  }


  public DbParametersByType redis(List<String> redis) {
    
    this.redis = redis;
    return this;
  }

  public DbParametersByType addRedisItem(String redisItem) {
    if (this.redis == null) {
      this.redis = new ArrayList<>();
    }
    this.redis.add(redisItem);
    return this;
  }

   /**
   * Параметры, доступные для кластеров типа &#x60;redis&#x60;.
   * @return redis
  **/
  @javax.annotation.Nonnull
  public List<String> getRedis() {
    return redis;
  }


  public void setRedis(List<String> redis) {
    this.redis = redis;
  }


  public DbParametersByType redis7(List<String> redis7) {
    
    this.redis7 = redis7;
    return this;
  }

  public DbParametersByType addRedis7Item(String redis7Item) {
    if (this.redis7 == null) {
      this.redis7 = new ArrayList<>();
    }
    this.redis7.add(redis7Item);
    return this;
  }

   /**
   * Параметры, доступные для кластеров типа &#x60;redis7&#x60;.
   * @return redis7
  **/
  @javax.annotation.Nonnull
  public List<String> getRedis7() {
    return redis7;
  }


  public void setRedis7(List<String> redis7) {
    this.redis7 = redis7;
  }


  public DbParametersByType redis81(List<String> redis81) {
    
    this.redis81 = redis81;
    return this;
  }

  public DbParametersByType addRedis81Item(String redis81Item) {
    if (this.redis81 == null) {
      this.redis81 = new ArrayList<>();
    }
    this.redis81.add(redis81Item);
    return this;
  }

   /**
   * Параметры, доступные для кластеров типа &#x60;redis8_1&#x60;.
   * @return redis81
  **/
  @javax.annotation.Nonnull
  public List<String> getRedis81() {
    return redis81;
  }


  public void setRedis81(List<String> redis81) {
    this.redis81 = redis81;
  }


  public DbParametersByType valkey(List<String> valkey) {
    
    this.valkey = valkey;
    return this;
  }

  public DbParametersByType addValkeyItem(String valkeyItem) {
    if (this.valkey == null) {
      this.valkey = new ArrayList<>();
    }
    this.valkey.add(valkeyItem);
    return this;
  }

   /**
   * Параметры, доступные для кластеров типа &#x60;valkey&#x60;.
   * @return valkey
  **/
  @javax.annotation.Nonnull
  public List<String> getValkey() {
    return valkey;
  }


  public void setValkey(List<String> valkey) {
    this.valkey = valkey;
  }


  public DbParametersByType valkey7(List<String> valkey7) {
    
    this.valkey7 = valkey7;
    return this;
  }

  public DbParametersByType addValkey7Item(String valkey7Item) {
    if (this.valkey7 == null) {
      this.valkey7 = new ArrayList<>();
    }
    this.valkey7.add(valkey7Item);
    return this;
  }

   /**
   * Параметры, доступные для кластеров типа &#x60;valkey7&#x60;.
   * @return valkey7
  **/
  @javax.annotation.Nonnull
  public List<String> getValkey7() {
    return valkey7;
  }


  public void setValkey7(List<String> valkey7) {
    this.valkey7 = valkey7;
  }


  public DbParametersByType valkey81(List<String> valkey81) {
    
    this.valkey81 = valkey81;
    return this;
  }

  public DbParametersByType addValkey81Item(String valkey81Item) {
    if (this.valkey81 == null) {
      this.valkey81 = new ArrayList<>();
    }
    this.valkey81.add(valkey81Item);
    return this;
  }

   /**
   * Параметры, доступные для кластеров типа &#x60;valkey8_1&#x60;.
   * @return valkey81
  **/
  @javax.annotation.Nonnull
  public List<String> getValkey81() {
    return valkey81;
  }


  public void setValkey81(List<String> valkey81) {
    this.valkey81 = valkey81;
  }


  public DbParametersByType valkey91(List<String> valkey91) {
    
    this.valkey91 = valkey91;
    return this;
  }

  public DbParametersByType addValkey91Item(String valkey91Item) {
    if (this.valkey91 == null) {
      this.valkey91 = new ArrayList<>();
    }
    this.valkey91.add(valkey91Item);
    return this;
  }

   /**
   * Параметры, доступные для кластеров типа &#x60;valkey9_1&#x60;.
   * @return valkey91
  **/
  @javax.annotation.Nonnull
  public List<String> getValkey91() {
    return valkey91;
  }


  public void setValkey91(List<String> valkey91) {
    this.valkey91 = valkey91;
  }


  public DbParametersByType mongodb4(List<String> mongodb4) {
    
    this.mongodb4 = mongodb4;
    return this;
  }

  public DbParametersByType addMongodb4Item(String mongodb4Item) {
    if (this.mongodb4 == null) {
      this.mongodb4 = new ArrayList<>();
    }
    this.mongodb4.add(mongodb4Item);
    return this;
  }

   /**
   * Для кластеров типа &#x60;mongodb4&#x60; настраиваемых параметров нет — всегда пустой массив.
   * @return mongodb4
  **/
  @javax.annotation.Nonnull
  public List<String> getMongodb4() {
    return mongodb4;
  }


  public void setMongodb4(List<String> mongodb4) {
    this.mongodb4 = mongodb4;
  }


  public DbParametersByType mongodb(List<String> mongodb) {
    
    this.mongodb = mongodb;
    return this;
  }

  public DbParametersByType addMongodbItem(String mongodbItem) {
    if (this.mongodb == null) {
      this.mongodb = new ArrayList<>();
    }
    this.mongodb.add(mongodbItem);
    return this;
  }

   /**
   * Для кластеров типа &#x60;mongodb&#x60; настраиваемых параметров нет — всегда пустой массив.
   * @return mongodb
  **/
  @javax.annotation.Nonnull
  public List<String> getMongodb() {
    return mongodb;
  }


  public void setMongodb(List<String> mongodb) {
    this.mongodb = mongodb;
  }


  public DbParametersByType mongodb6(List<String> mongodb6) {
    
    this.mongodb6 = mongodb6;
    return this;
  }

  public DbParametersByType addMongodb6Item(String mongodb6Item) {
    if (this.mongodb6 == null) {
      this.mongodb6 = new ArrayList<>();
    }
    this.mongodb6.add(mongodb6Item);
    return this;
  }

   /**
   * Для кластеров типа &#x60;mongodb6&#x60; настраиваемых параметров нет — всегда пустой массив.
   * @return mongodb6
  **/
  @javax.annotation.Nonnull
  public List<String> getMongodb6() {
    return mongodb6;
  }


  public void setMongodb6(List<String> mongodb6) {
    this.mongodb6 = mongodb6;
  }


  public DbParametersByType mongodb7(List<String> mongodb7) {
    
    this.mongodb7 = mongodb7;
    return this;
  }

  public DbParametersByType addMongodb7Item(String mongodb7Item) {
    if (this.mongodb7 == null) {
      this.mongodb7 = new ArrayList<>();
    }
    this.mongodb7.add(mongodb7Item);
    return this;
  }

   /**
   * Для кластеров типа &#x60;mongodb7&#x60; настраиваемых параметров нет — всегда пустой массив.
   * @return mongodb7
  **/
  @javax.annotation.Nonnull
  public List<String> getMongodb7() {
    return mongodb7;
  }


  public void setMongodb7(List<String> mongodb7) {
    this.mongodb7 = mongodb7;
  }


  public DbParametersByType mongodb80(List<String> mongodb80) {
    
    this.mongodb80 = mongodb80;
    return this;
  }

  public DbParametersByType addMongodb80Item(String mongodb80Item) {
    if (this.mongodb80 == null) {
      this.mongodb80 = new ArrayList<>();
    }
    this.mongodb80.add(mongodb80Item);
    return this;
  }

   /**
   * Для кластеров типа &#x60;mongodb8_0&#x60; настраиваемых параметров нет — всегда пустой массив.
   * @return mongodb80
  **/
  @javax.annotation.Nonnull
  public List<String> getMongodb80() {
    return mongodb80;
  }


  public void setMongodb80(List<String> mongodb80) {
    this.mongodb80 = mongodb80;
  }


  public DbParametersByType opensearch(List<String> opensearch) {
    
    this.opensearch = opensearch;
    return this;
  }

  public DbParametersByType addOpensearchItem(String opensearchItem) {
    if (this.opensearch == null) {
      this.opensearch = new ArrayList<>();
    }
    this.opensearch.add(opensearchItem);
    return this;
  }

   /**
   * Для кластеров типа &#x60;opensearch&#x60; настраиваемых параметров нет — всегда пустой массив.
   * @return opensearch
  **/
  @javax.annotation.Nonnull
  public List<String> getOpensearch() {
    return opensearch;
  }


  public void setOpensearch(List<String> opensearch) {
    this.opensearch = opensearch;
  }


  public DbParametersByType opensearch219(List<String> opensearch219) {
    
    this.opensearch219 = opensearch219;
    return this;
  }

  public DbParametersByType addOpensearch219Item(String opensearch219Item) {
    if (this.opensearch219 == null) {
      this.opensearch219 = new ArrayList<>();
    }
    this.opensearch219.add(opensearch219Item);
    return this;
  }

   /**
   * Для кластеров типа &#x60;opensearch2_19&#x60; настраиваемых параметров нет — всегда пустой массив.
   * @return opensearch219
  **/
  @javax.annotation.Nonnull
  public List<String> getOpensearch219() {
    return opensearch219;
  }


  public void setOpensearch219(List<String> opensearch219) {
    this.opensearch219 = opensearch219;
  }


  public DbParametersByType clickhouse(List<String> clickhouse) {
    
    this.clickhouse = clickhouse;
    return this;
  }

  public DbParametersByType addClickhouseItem(String clickhouseItem) {
    if (this.clickhouse == null) {
      this.clickhouse = new ArrayList<>();
    }
    this.clickhouse.add(clickhouseItem);
    return this;
  }

   /**
   * Для кластеров типа &#x60;clickhouse&#x60; настраиваемых параметров нет — всегда пустой массив.
   * @return clickhouse
  **/
  @javax.annotation.Nonnull
  public List<String> getClickhouse() {
    return clickhouse;
  }


  public void setClickhouse(List<String> clickhouse) {
    this.clickhouse = clickhouse;
  }


  public DbParametersByType clickhouse24(List<String> clickhouse24) {
    
    this.clickhouse24 = clickhouse24;
    return this;
  }

  public DbParametersByType addClickhouse24Item(String clickhouse24Item) {
    if (this.clickhouse24 == null) {
      this.clickhouse24 = new ArrayList<>();
    }
    this.clickhouse24.add(clickhouse24Item);
    return this;
  }

   /**
   * Для кластеров типа &#x60;clickhouse24&#x60; настраиваемых параметров нет — всегда пустой массив.
   * @return clickhouse24
  **/
  @javax.annotation.Nonnull
  public List<String> getClickhouse24() {
    return clickhouse24;
  }


  public void setClickhouse24(List<String> clickhouse24) {
    this.clickhouse24 = clickhouse24;
  }


  public DbParametersByType clickhouse25(List<String> clickhouse25) {
    
    this.clickhouse25 = clickhouse25;
    return this;
  }

  public DbParametersByType addClickhouse25Item(String clickhouse25Item) {
    if (this.clickhouse25 == null) {
      this.clickhouse25 = new ArrayList<>();
    }
    this.clickhouse25.add(clickhouse25Item);
    return this;
  }

   /**
   * Для кластеров типа &#x60;clickhouse25&#x60; настраиваемых параметров нет — всегда пустой массив.
   * @return clickhouse25
  **/
  @javax.annotation.Nonnull
  public List<String> getClickhouse25() {
    return clickhouse25;
  }


  public void setClickhouse25(List<String> clickhouse25) {
    this.clickhouse25 = clickhouse25;
  }


  public DbParametersByType kafka(List<String> kafka) {
    
    this.kafka = kafka;
    return this;
  }

  public DbParametersByType addKafkaItem(String kafkaItem) {
    if (this.kafka == null) {
      this.kafka = new ArrayList<>();
    }
    this.kafka.add(kafkaItem);
    return this;
  }

   /**
   * Для кластеров типа &#x60;kafka&#x60; настраиваемых параметров нет — всегда пустой массив.
   * @return kafka
  **/
  @javax.annotation.Nonnull
  public List<String> getKafka() {
    return kafka;
  }


  public void setKafka(List<String> kafka) {
    this.kafka = kafka;
  }


  public DbParametersByType rabbitmq(List<String> rabbitmq) {
    
    this.rabbitmq = rabbitmq;
    return this;
  }

  public DbParametersByType addRabbitmqItem(String rabbitmqItem) {
    if (this.rabbitmq == null) {
      this.rabbitmq = new ArrayList<>();
    }
    this.rabbitmq.add(rabbitmqItem);
    return this;
  }

   /**
   * Для кластеров типа &#x60;rabbitmq&#x60; настраиваемых параметров нет — всегда пустой массив.
   * @return rabbitmq
  **/
  @javax.annotation.Nonnull
  public List<String> getRabbitmq() {
    return rabbitmq;
  }


  public void setRabbitmq(List<String> rabbitmq) {
    this.rabbitmq = rabbitmq;
  }


  public DbParametersByType rabbitmq40(List<String> rabbitmq40) {
    
    this.rabbitmq40 = rabbitmq40;
    return this;
  }

  public DbParametersByType addRabbitmq40Item(String rabbitmq40Item) {
    if (this.rabbitmq40 == null) {
      this.rabbitmq40 = new ArrayList<>();
    }
    this.rabbitmq40.add(rabbitmq40Item);
    return this;
  }

   /**
   * Для кластеров типа &#x60;rabbitmq4_0&#x60; настраиваемых параметров нет — всегда пустой массив.
   * @return rabbitmq40
  **/
  @javax.annotation.Nonnull
  public List<String> getRabbitmq40() {
    return rabbitmq40;
  }


  public void setRabbitmq40(List<String> rabbitmq40) {
    this.rabbitmq40 = rabbitmq40;
  }



  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DbParametersByType dbParametersByType = (DbParametersByType) o;
    return Objects.equals(this.mysql5, dbParametersByType.mysql5) &&
        Objects.equals(this.mysql, dbParametersByType.mysql) &&
        Objects.equals(this.mysql84, dbParametersByType.mysql84) &&
        Objects.equals(this.postgres, dbParametersByType.postgres) &&
        Objects.equals(this.postgres14, dbParametersByType.postgres14) &&
        Objects.equals(this.postgres15, dbParametersByType.postgres15) &&
        Objects.equals(this.postgres16, dbParametersByType.postgres16) &&
        Objects.equals(this.postgres17, dbParametersByType.postgres17) &&
        Objects.equals(this.postgres18, dbParametersByType.postgres18) &&
        Objects.equals(this.redis, dbParametersByType.redis) &&
        Objects.equals(this.redis7, dbParametersByType.redis7) &&
        Objects.equals(this.redis81, dbParametersByType.redis81) &&
        Objects.equals(this.valkey, dbParametersByType.valkey) &&
        Objects.equals(this.valkey7, dbParametersByType.valkey7) &&
        Objects.equals(this.valkey81, dbParametersByType.valkey81) &&
        Objects.equals(this.valkey91, dbParametersByType.valkey91) &&
        Objects.equals(this.mongodb4, dbParametersByType.mongodb4) &&
        Objects.equals(this.mongodb, dbParametersByType.mongodb) &&
        Objects.equals(this.mongodb6, dbParametersByType.mongodb6) &&
        Objects.equals(this.mongodb7, dbParametersByType.mongodb7) &&
        Objects.equals(this.mongodb80, dbParametersByType.mongodb80) &&
        Objects.equals(this.opensearch, dbParametersByType.opensearch) &&
        Objects.equals(this.opensearch219, dbParametersByType.opensearch219) &&
        Objects.equals(this.clickhouse, dbParametersByType.clickhouse) &&
        Objects.equals(this.clickhouse24, dbParametersByType.clickhouse24) &&
        Objects.equals(this.clickhouse25, dbParametersByType.clickhouse25) &&
        Objects.equals(this.kafka, dbParametersByType.kafka) &&
        Objects.equals(this.rabbitmq, dbParametersByType.rabbitmq) &&
        Objects.equals(this.rabbitmq40, dbParametersByType.rabbitmq40);
  }

  @Override
  public int hashCode() {
    return Objects.hash(mysql5, mysql, mysql84, postgres, postgres14, postgres15, postgres16, postgres17, postgres18, redis, redis7, redis81, valkey, valkey7, valkey81, valkey91, mongodb4, mongodb, mongodb6, mongodb7, mongodb80, opensearch, opensearch219, clickhouse, clickhouse24, clickhouse25, kafka, rabbitmq, rabbitmq40);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DbParametersByType {\n");
    sb.append("    mysql5: ").append(toIndentedString(mysql5)).append("\n");
    sb.append("    mysql: ").append(toIndentedString(mysql)).append("\n");
    sb.append("    mysql84: ").append(toIndentedString(mysql84)).append("\n");
    sb.append("    postgres: ").append(toIndentedString(postgres)).append("\n");
    sb.append("    postgres14: ").append(toIndentedString(postgres14)).append("\n");
    sb.append("    postgres15: ").append(toIndentedString(postgres15)).append("\n");
    sb.append("    postgres16: ").append(toIndentedString(postgres16)).append("\n");
    sb.append("    postgres17: ").append(toIndentedString(postgres17)).append("\n");
    sb.append("    postgres18: ").append(toIndentedString(postgres18)).append("\n");
    sb.append("    redis: ").append(toIndentedString(redis)).append("\n");
    sb.append("    redis7: ").append(toIndentedString(redis7)).append("\n");
    sb.append("    redis81: ").append(toIndentedString(redis81)).append("\n");
    sb.append("    valkey: ").append(toIndentedString(valkey)).append("\n");
    sb.append("    valkey7: ").append(toIndentedString(valkey7)).append("\n");
    sb.append("    valkey81: ").append(toIndentedString(valkey81)).append("\n");
    sb.append("    valkey91: ").append(toIndentedString(valkey91)).append("\n");
    sb.append("    mongodb4: ").append(toIndentedString(mongodb4)).append("\n");
    sb.append("    mongodb: ").append(toIndentedString(mongodb)).append("\n");
    sb.append("    mongodb6: ").append(toIndentedString(mongodb6)).append("\n");
    sb.append("    mongodb7: ").append(toIndentedString(mongodb7)).append("\n");
    sb.append("    mongodb80: ").append(toIndentedString(mongodb80)).append("\n");
    sb.append("    opensearch: ").append(toIndentedString(opensearch)).append("\n");
    sb.append("    opensearch219: ").append(toIndentedString(opensearch219)).append("\n");
    sb.append("    clickhouse: ").append(toIndentedString(clickhouse)).append("\n");
    sb.append("    clickhouse24: ").append(toIndentedString(clickhouse24)).append("\n");
    sb.append("    clickhouse25: ").append(toIndentedString(clickhouse25)).append("\n");
    sb.append("    kafka: ").append(toIndentedString(kafka)).append("\n");
    sb.append("    rabbitmq: ").append(toIndentedString(rabbitmq)).append("\n");
    sb.append("    rabbitmq40: ").append(toIndentedString(rabbitmq40)).append("\n");
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
    openapiFields.add("mysql5");
    openapiFields.add("mysql");
    openapiFields.add("mysql8_4");
    openapiFields.add("postgres");
    openapiFields.add("postgres14");
    openapiFields.add("postgres15");
    openapiFields.add("postgres16");
    openapiFields.add("postgres17");
    openapiFields.add("postgres18");
    openapiFields.add("redis");
    openapiFields.add("redis7");
    openapiFields.add("redis8_1");
    openapiFields.add("valkey");
    openapiFields.add("valkey7");
    openapiFields.add("valkey8_1");
    openapiFields.add("valkey9_1");
    openapiFields.add("mongodb4");
    openapiFields.add("mongodb");
    openapiFields.add("mongodb6");
    openapiFields.add("mongodb7");
    openapiFields.add("mongodb8_0");
    openapiFields.add("opensearch");
    openapiFields.add("opensearch2_19");
    openapiFields.add("clickhouse");
    openapiFields.add("clickhouse24");
    openapiFields.add("clickhouse25");
    openapiFields.add("kafka");
    openapiFields.add("rabbitmq");
    openapiFields.add("rabbitmq4_0");

    // a set of required properties/fields (JSON key names)
    openapiRequiredFields = new HashSet<String>();
    openapiRequiredFields.add("mysql5");
    openapiRequiredFields.add("mysql");
    openapiRequiredFields.add("mysql8_4");
    openapiRequiredFields.add("postgres");
    openapiRequiredFields.add("postgres14");
    openapiRequiredFields.add("postgres15");
    openapiRequiredFields.add("postgres16");
    openapiRequiredFields.add("postgres17");
    openapiRequiredFields.add("postgres18");
    openapiRequiredFields.add("redis");
    openapiRequiredFields.add("redis7");
    openapiRequiredFields.add("redis8_1");
    openapiRequiredFields.add("valkey");
    openapiRequiredFields.add("valkey7");
    openapiRequiredFields.add("valkey8_1");
    openapiRequiredFields.add("valkey9_1");
    openapiRequiredFields.add("mongodb4");
    openapiRequiredFields.add("mongodb");
    openapiRequiredFields.add("mongodb6");
    openapiRequiredFields.add("mongodb7");
    openapiRequiredFields.add("mongodb8_0");
    openapiRequiredFields.add("opensearch");
    openapiRequiredFields.add("opensearch2_19");
    openapiRequiredFields.add("clickhouse");
    openapiRequiredFields.add("clickhouse24");
    openapiRequiredFields.add("clickhouse25");
    openapiRequiredFields.add("kafka");
    openapiRequiredFields.add("rabbitmq");
    openapiRequiredFields.add("rabbitmq4_0");
  }

 /**
  * Validates the JSON Element and throws an exception if issues found
  *
  * @param jsonElement JSON Element
  * @throws IOException if the JSON Element is invalid with respect to DbParametersByType
  */
  public static void validateJsonElement(JsonElement jsonElement) throws IOException {
      if (jsonElement == null) {
        if (!DbParametersByType.openapiRequiredFields.isEmpty()) { // has required fields but JSON element is null
          throw new IllegalArgumentException(String.format("The required field(s) %s in DbParametersByType is not found in the empty JSON string", DbParametersByType.openapiRequiredFields.toString()));
        }
      }

      Set<Entry<String, JsonElement>> entries = jsonElement.getAsJsonObject().entrySet();
      // check to see if the JSON string contains additional fields
      for (Entry<String, JsonElement> entry : entries) {
        if (!DbParametersByType.openapiFields.contains(entry.getKey())) {
          throw new IllegalArgumentException(String.format("The field `%s` in the JSON string is not defined in the `DbParametersByType` properties. JSON: %s", entry.getKey(), jsonElement.toString()));
        }
      }

      // check to make sure all required properties/fields are present in the JSON string
      for (String requiredField : DbParametersByType.openapiRequiredFields) {
        if (jsonElement.getAsJsonObject().get(requiredField) == null) {
          throw new IllegalArgumentException(String.format("The required field `%s` is not found in the JSON string: %s", requiredField, jsonElement.toString()));
        }
      }
        JsonObject jsonObj = jsonElement.getAsJsonObject();
      // ensure the required json array is present
      if (jsonObj.get("mysql5") == null) {
        throw new IllegalArgumentException("Expected the field `linkedContent` to be an array in the JSON string but got `null`");
      } else if (!jsonObj.get("mysql5").isJsonArray()) {
        throw new IllegalArgumentException(String.format("Expected the field `mysql5` to be an array in the JSON string but got `%s`", jsonObj.get("mysql5").toString()));
      }
      // ensure the required json array is present
      if (jsonObj.get("mysql") == null) {
        throw new IllegalArgumentException("Expected the field `linkedContent` to be an array in the JSON string but got `null`");
      } else if (!jsonObj.get("mysql").isJsonArray()) {
        throw new IllegalArgumentException(String.format("Expected the field `mysql` to be an array in the JSON string but got `%s`", jsonObj.get("mysql").toString()));
      }
      // ensure the required json array is present
      if (jsonObj.get("mysql8_4") == null) {
        throw new IllegalArgumentException("Expected the field `linkedContent` to be an array in the JSON string but got `null`");
      } else if (!jsonObj.get("mysql8_4").isJsonArray()) {
        throw new IllegalArgumentException(String.format("Expected the field `mysql8_4` to be an array in the JSON string but got `%s`", jsonObj.get("mysql8_4").toString()));
      }
      // ensure the required json array is present
      if (jsonObj.get("postgres") == null) {
        throw new IllegalArgumentException("Expected the field `linkedContent` to be an array in the JSON string but got `null`");
      } else if (!jsonObj.get("postgres").isJsonArray()) {
        throw new IllegalArgumentException(String.format("Expected the field `postgres` to be an array in the JSON string but got `%s`", jsonObj.get("postgres").toString()));
      }
      // ensure the required json array is present
      if (jsonObj.get("postgres14") == null) {
        throw new IllegalArgumentException("Expected the field `linkedContent` to be an array in the JSON string but got `null`");
      } else if (!jsonObj.get("postgres14").isJsonArray()) {
        throw new IllegalArgumentException(String.format("Expected the field `postgres14` to be an array in the JSON string but got `%s`", jsonObj.get("postgres14").toString()));
      }
      // ensure the required json array is present
      if (jsonObj.get("postgres15") == null) {
        throw new IllegalArgumentException("Expected the field `linkedContent` to be an array in the JSON string but got `null`");
      } else if (!jsonObj.get("postgres15").isJsonArray()) {
        throw new IllegalArgumentException(String.format("Expected the field `postgres15` to be an array in the JSON string but got `%s`", jsonObj.get("postgres15").toString()));
      }
      // ensure the required json array is present
      if (jsonObj.get("postgres16") == null) {
        throw new IllegalArgumentException("Expected the field `linkedContent` to be an array in the JSON string but got `null`");
      } else if (!jsonObj.get("postgres16").isJsonArray()) {
        throw new IllegalArgumentException(String.format("Expected the field `postgres16` to be an array in the JSON string but got `%s`", jsonObj.get("postgres16").toString()));
      }
      // ensure the required json array is present
      if (jsonObj.get("postgres17") == null) {
        throw new IllegalArgumentException("Expected the field `linkedContent` to be an array in the JSON string but got `null`");
      } else if (!jsonObj.get("postgres17").isJsonArray()) {
        throw new IllegalArgumentException(String.format("Expected the field `postgres17` to be an array in the JSON string but got `%s`", jsonObj.get("postgres17").toString()));
      }
      // ensure the required json array is present
      if (jsonObj.get("postgres18") == null) {
        throw new IllegalArgumentException("Expected the field `linkedContent` to be an array in the JSON string but got `null`");
      } else if (!jsonObj.get("postgres18").isJsonArray()) {
        throw new IllegalArgumentException(String.format("Expected the field `postgres18` to be an array in the JSON string but got `%s`", jsonObj.get("postgres18").toString()));
      }
      // ensure the required json array is present
      if (jsonObj.get("redis") == null) {
        throw new IllegalArgumentException("Expected the field `linkedContent` to be an array in the JSON string but got `null`");
      } else if (!jsonObj.get("redis").isJsonArray()) {
        throw new IllegalArgumentException(String.format("Expected the field `redis` to be an array in the JSON string but got `%s`", jsonObj.get("redis").toString()));
      }
      // ensure the required json array is present
      if (jsonObj.get("redis7") == null) {
        throw new IllegalArgumentException("Expected the field `linkedContent` to be an array in the JSON string but got `null`");
      } else if (!jsonObj.get("redis7").isJsonArray()) {
        throw new IllegalArgumentException(String.format("Expected the field `redis7` to be an array in the JSON string but got `%s`", jsonObj.get("redis7").toString()));
      }
      // ensure the required json array is present
      if (jsonObj.get("redis8_1") == null) {
        throw new IllegalArgumentException("Expected the field `linkedContent` to be an array in the JSON string but got `null`");
      } else if (!jsonObj.get("redis8_1").isJsonArray()) {
        throw new IllegalArgumentException(String.format("Expected the field `redis8_1` to be an array in the JSON string but got `%s`", jsonObj.get("redis8_1").toString()));
      }
      // ensure the required json array is present
      if (jsonObj.get("valkey") == null) {
        throw new IllegalArgumentException("Expected the field `linkedContent` to be an array in the JSON string but got `null`");
      } else if (!jsonObj.get("valkey").isJsonArray()) {
        throw new IllegalArgumentException(String.format("Expected the field `valkey` to be an array in the JSON string but got `%s`", jsonObj.get("valkey").toString()));
      }
      // ensure the required json array is present
      if (jsonObj.get("valkey7") == null) {
        throw new IllegalArgumentException("Expected the field `linkedContent` to be an array in the JSON string but got `null`");
      } else if (!jsonObj.get("valkey7").isJsonArray()) {
        throw new IllegalArgumentException(String.format("Expected the field `valkey7` to be an array in the JSON string but got `%s`", jsonObj.get("valkey7").toString()));
      }
      // ensure the required json array is present
      if (jsonObj.get("valkey8_1") == null) {
        throw new IllegalArgumentException("Expected the field `linkedContent` to be an array in the JSON string but got `null`");
      } else if (!jsonObj.get("valkey8_1").isJsonArray()) {
        throw new IllegalArgumentException(String.format("Expected the field `valkey8_1` to be an array in the JSON string but got `%s`", jsonObj.get("valkey8_1").toString()));
      }
      // ensure the required json array is present
      if (jsonObj.get("valkey9_1") == null) {
        throw new IllegalArgumentException("Expected the field `linkedContent` to be an array in the JSON string but got `null`");
      } else if (!jsonObj.get("valkey9_1").isJsonArray()) {
        throw new IllegalArgumentException(String.format("Expected the field `valkey9_1` to be an array in the JSON string but got `%s`", jsonObj.get("valkey9_1").toString()));
      }
      // ensure the required json array is present
      if (jsonObj.get("mongodb4") == null) {
        throw new IllegalArgumentException("Expected the field `linkedContent` to be an array in the JSON string but got `null`");
      } else if (!jsonObj.get("mongodb4").isJsonArray()) {
        throw new IllegalArgumentException(String.format("Expected the field `mongodb4` to be an array in the JSON string but got `%s`", jsonObj.get("mongodb4").toString()));
      }
      // ensure the required json array is present
      if (jsonObj.get("mongodb") == null) {
        throw new IllegalArgumentException("Expected the field `linkedContent` to be an array in the JSON string but got `null`");
      } else if (!jsonObj.get("mongodb").isJsonArray()) {
        throw new IllegalArgumentException(String.format("Expected the field `mongodb` to be an array in the JSON string but got `%s`", jsonObj.get("mongodb").toString()));
      }
      // ensure the required json array is present
      if (jsonObj.get("mongodb6") == null) {
        throw new IllegalArgumentException("Expected the field `linkedContent` to be an array in the JSON string but got `null`");
      } else if (!jsonObj.get("mongodb6").isJsonArray()) {
        throw new IllegalArgumentException(String.format("Expected the field `mongodb6` to be an array in the JSON string but got `%s`", jsonObj.get("mongodb6").toString()));
      }
      // ensure the required json array is present
      if (jsonObj.get("mongodb7") == null) {
        throw new IllegalArgumentException("Expected the field `linkedContent` to be an array in the JSON string but got `null`");
      } else if (!jsonObj.get("mongodb7").isJsonArray()) {
        throw new IllegalArgumentException(String.format("Expected the field `mongodb7` to be an array in the JSON string but got `%s`", jsonObj.get("mongodb7").toString()));
      }
      // ensure the required json array is present
      if (jsonObj.get("mongodb8_0") == null) {
        throw new IllegalArgumentException("Expected the field `linkedContent` to be an array in the JSON string but got `null`");
      } else if (!jsonObj.get("mongodb8_0").isJsonArray()) {
        throw new IllegalArgumentException(String.format("Expected the field `mongodb8_0` to be an array in the JSON string but got `%s`", jsonObj.get("mongodb8_0").toString()));
      }
      // ensure the required json array is present
      if (jsonObj.get("opensearch") == null) {
        throw new IllegalArgumentException("Expected the field `linkedContent` to be an array in the JSON string but got `null`");
      } else if (!jsonObj.get("opensearch").isJsonArray()) {
        throw new IllegalArgumentException(String.format("Expected the field `opensearch` to be an array in the JSON string but got `%s`", jsonObj.get("opensearch").toString()));
      }
      // ensure the required json array is present
      if (jsonObj.get("opensearch2_19") == null) {
        throw new IllegalArgumentException("Expected the field `linkedContent` to be an array in the JSON string but got `null`");
      } else if (!jsonObj.get("opensearch2_19").isJsonArray()) {
        throw new IllegalArgumentException(String.format("Expected the field `opensearch2_19` to be an array in the JSON string but got `%s`", jsonObj.get("opensearch2_19").toString()));
      }
      // ensure the required json array is present
      if (jsonObj.get("clickhouse") == null) {
        throw new IllegalArgumentException("Expected the field `linkedContent` to be an array in the JSON string but got `null`");
      } else if (!jsonObj.get("clickhouse").isJsonArray()) {
        throw new IllegalArgumentException(String.format("Expected the field `clickhouse` to be an array in the JSON string but got `%s`", jsonObj.get("clickhouse").toString()));
      }
      // ensure the required json array is present
      if (jsonObj.get("clickhouse24") == null) {
        throw new IllegalArgumentException("Expected the field `linkedContent` to be an array in the JSON string but got `null`");
      } else if (!jsonObj.get("clickhouse24").isJsonArray()) {
        throw new IllegalArgumentException(String.format("Expected the field `clickhouse24` to be an array in the JSON string but got `%s`", jsonObj.get("clickhouse24").toString()));
      }
      // ensure the required json array is present
      if (jsonObj.get("clickhouse25") == null) {
        throw new IllegalArgumentException("Expected the field `linkedContent` to be an array in the JSON string but got `null`");
      } else if (!jsonObj.get("clickhouse25").isJsonArray()) {
        throw new IllegalArgumentException(String.format("Expected the field `clickhouse25` to be an array in the JSON string but got `%s`", jsonObj.get("clickhouse25").toString()));
      }
      // ensure the required json array is present
      if (jsonObj.get("kafka") == null) {
        throw new IllegalArgumentException("Expected the field `linkedContent` to be an array in the JSON string but got `null`");
      } else if (!jsonObj.get("kafka").isJsonArray()) {
        throw new IllegalArgumentException(String.format("Expected the field `kafka` to be an array in the JSON string but got `%s`", jsonObj.get("kafka").toString()));
      }
      // ensure the required json array is present
      if (jsonObj.get("rabbitmq") == null) {
        throw new IllegalArgumentException("Expected the field `linkedContent` to be an array in the JSON string but got `null`");
      } else if (!jsonObj.get("rabbitmq").isJsonArray()) {
        throw new IllegalArgumentException(String.format("Expected the field `rabbitmq` to be an array in the JSON string but got `%s`", jsonObj.get("rabbitmq").toString()));
      }
      // ensure the required json array is present
      if (jsonObj.get("rabbitmq4_0") == null) {
        throw new IllegalArgumentException("Expected the field `linkedContent` to be an array in the JSON string but got `null`");
      } else if (!jsonObj.get("rabbitmq4_0").isJsonArray()) {
        throw new IllegalArgumentException(String.format("Expected the field `rabbitmq4_0` to be an array in the JSON string but got `%s`", jsonObj.get("rabbitmq4_0").toString()));
      }
  }

  public static class CustomTypeAdapterFactory implements TypeAdapterFactory {
    @SuppressWarnings("unchecked")
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
       if (!DbParametersByType.class.isAssignableFrom(type.getRawType())) {
         return null; // this class only serializes 'DbParametersByType' and its subtypes
       }
       final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);
       final TypeAdapter<DbParametersByType> thisAdapter
                        = gson.getDelegateAdapter(this, TypeToken.get(DbParametersByType.class));

       return (TypeAdapter<T>) new TypeAdapter<DbParametersByType>() {
           @Override
           public void write(JsonWriter out, DbParametersByType value) throws IOException {
             JsonObject obj = thisAdapter.toJsonTree(value).getAsJsonObject();
             elementAdapter.write(out, obj);
           }

           @Override
           public DbParametersByType read(JsonReader in) throws IOException {
             JsonElement jsonElement = elementAdapter.read(in);
             validateJsonElement(jsonElement);
             return thisAdapter.fromJsonTree(jsonElement);
           }

       }.nullSafe();
    }
  }

 /**
  * Create an instance of DbParametersByType given an JSON string
  *
  * @param jsonString JSON string
  * @return An instance of DbParametersByType
  * @throws IOException if the JSON string is invalid with respect to DbParametersByType
  */
  public static DbParametersByType fromJson(String jsonString) throws IOException {
    return JSON.getGson().fromJson(jsonString, DbParametersByType.class);
  }

 /**
  * Convert an instance of DbParametersByType to an JSON string
  *
  * @return JSON string
  */
  public String toJson() {
    return JSON.getGson().toJson(this);
  }
}

