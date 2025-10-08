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
import org.openapitools.client.model.AvailabilityZone;
import org.openapitools.client.model.ConfigParameters;
import org.openapitools.client.model.DbDiskStats;
import org.openapitools.client.model.DbType;

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
 * База данных
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2025-10-08T11:03:53.274930Z[Etc/UTC]")
public class Db {
  public static final String SERIALIZED_NAME_ID = "id";
  @SerializedName(SERIALIZED_NAME_ID)
  private BigDecimal id;

  public static final String SERIALIZED_NAME_CREATED_AT = "created_at";
  @SerializedName(SERIALIZED_NAME_CREATED_AT)
  private String createdAt;

  public static final String SERIALIZED_NAME_ACCOUNT_ID = "account_id";
  @SerializedName(SERIALIZED_NAME_ACCOUNT_ID)
  private String accountId;

  public static final String SERIALIZED_NAME_LOGIN = "login";
  @SerializedName(SERIALIZED_NAME_LOGIN)
  private String login;

  /**
   * Локация сервера.
   */
  @JsonAdapter(LocationEnum.Adapter.class)
  public enum LocationEnum {
    RU_1("ru-1"),
    
    RU_2("ru-2"),
    
    PL_1("pl-1"),
    
    KZ_1("kz-1");

    private String value;

    LocationEnum(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public static LocationEnum fromValue(String value) {
      for (LocationEnum b : LocationEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }

    public static class Adapter extends TypeAdapter<LocationEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final LocationEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public LocationEnum read(final JsonReader jsonReader) throws IOException {
        String value =  jsonReader.nextString();
        return LocationEnum.fromValue(value);
      }
    }
  }

  public static final String SERIALIZED_NAME_LOCATION = "location";
  @SerializedName(SERIALIZED_NAME_LOCATION)
  private LocationEnum location;

  public static final String SERIALIZED_NAME_PASSWORD = "password";
  @SerializedName(SERIALIZED_NAME_PASSWORD)
  private String password;

  public static final String SERIALIZED_NAME_NAME = "name";
  @SerializedName(SERIALIZED_NAME_NAME)
  private String name;

  public static final String SERIALIZED_NAME_HOST = "host";
  @SerializedName(SERIALIZED_NAME_HOST)
  private String host;

  public static final String SERIALIZED_NAME_TYPE = "type";
  @SerializedName(SERIALIZED_NAME_TYPE)
  private DbType type;

  /**
   * Тип хеширования базы данных (mysql5 | mysql | postgres).
   */
  @JsonAdapter(HashTypeEnum.Adapter.class)
  public enum HashTypeEnum {
    CACHING_SHA2("caching_sha2"),
    
    MYSQL_NATIVE("mysql_native"),
    
    NULL("null");

    private String value;

    HashTypeEnum(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public static HashTypeEnum fromValue(String value) {
      for (HashTypeEnum b : HashTypeEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      return null;
    }

    public static class Adapter extends TypeAdapter<HashTypeEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final HashTypeEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public HashTypeEnum read(final JsonReader jsonReader) throws IOException {
        String value =  jsonReader.nextString();
        return HashTypeEnum.fromValue(value);
      }
    }
  }

  public static final String SERIALIZED_NAME_HASH_TYPE = "hash_type";
  @SerializedName(SERIALIZED_NAME_HASH_TYPE)
  private HashTypeEnum hashType;

  public static final String SERIALIZED_NAME_PORT = "port";
  @SerializedName(SERIALIZED_NAME_PORT)
  private Integer port;

  public static final String SERIALIZED_NAME_IP = "ip";
  @SerializedName(SERIALIZED_NAME_IP)
  private String ip;

  public static final String SERIALIZED_NAME_LOCAL_IP = "local_ip";
  @SerializedName(SERIALIZED_NAME_LOCAL_IP)
  private String localIp;

  /**
   * Текущий статус базы данных.
   */
  @JsonAdapter(StatusEnum.Adapter.class)
  public enum StatusEnum {
    STARTED("started"),
    
    STARTING("starting"),
    
    STOPPED("stopped"),
    
    NO_PAID("no_paid");

    private String value;

    StatusEnum(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public static StatusEnum fromValue(String value) {
      for (StatusEnum b : StatusEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }

    public static class Adapter extends TypeAdapter<StatusEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final StatusEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public StatusEnum read(final JsonReader jsonReader) throws IOException {
        String value =  jsonReader.nextString();
        return StatusEnum.fromValue(value);
      }
    }
  }

  public static final String SERIALIZED_NAME_STATUS = "status";
  @SerializedName(SERIALIZED_NAME_STATUS)
  private StatusEnum status;

  public static final String SERIALIZED_NAME_PRESET_ID = "preset_id";
  @SerializedName(SERIALIZED_NAME_PRESET_ID)
  private Integer presetId;

  public static final String SERIALIZED_NAME_DISK_STATS = "disk_stats";
  @SerializedName(SERIALIZED_NAME_DISK_STATS)
  private DbDiskStats diskStats;

  public static final String SERIALIZED_NAME_CONFIG_PARAMETERS = "config_parameters";
  @SerializedName(SERIALIZED_NAME_CONFIG_PARAMETERS)
  private ConfigParameters configParameters;

  public static final String SERIALIZED_NAME_IS_ONLY_LOCAL_IP_ACCESS = "is_only_local_ip_access";
  @SerializedName(SERIALIZED_NAME_IS_ONLY_LOCAL_IP_ACCESS)
  private Boolean isOnlyLocalIpAccess;

  public static final String SERIALIZED_NAME_AVAILABILITY_ZONE = "availability_zone";
  @SerializedName(SERIALIZED_NAME_AVAILABILITY_ZONE)
  private AvailabilityZone availabilityZone;

  public Db() {
  }

  public Db id(BigDecimal id) {
    
    this.id = id;
    return this;
  }

   /**
   * ID для каждого экземпляра базы данных. Автоматически генерируется при создании.
   * @return id
  **/
  @javax.annotation.Nonnull
  public BigDecimal getId() {
    return id;
  }


  public void setId(BigDecimal id) {
    this.id = id;
  }


  public Db createdAt(String createdAt) {
    
    this.createdAt = createdAt;
    return this;
  }

   /**
   * Значение времени, указанное в комбинированном формате даты и времени ISO8601, которое представляет, когда была создана база данных.
   * @return createdAt
  **/
  @javax.annotation.Nonnull
  public String getCreatedAt() {
    return createdAt;
  }


  public void setCreatedAt(String createdAt) {
    this.createdAt = createdAt;
  }


  public Db accountId(String accountId) {
    
    this.accountId = accountId;
    return this;
  }

   /**
   * ID пользователя.
   * @return accountId
  **/
  @javax.annotation.Nonnull
  public String getAccountId() {
    return accountId;
  }


  public void setAccountId(String accountId) {
    this.accountId = accountId;
  }


  public Db login(String login) {
    
    this.login = login;
    return this;
  }

   /**
   * Логин для подключения к базе данных.
   * @return login
  **/
  @javax.annotation.Nonnull
  public String getLogin() {
    return login;
  }


  public void setLogin(String login) {
    this.login = login;
  }


  public Db location(LocationEnum location) {
    
    this.location = location;
    return this;
  }

   /**
   * Локация сервера.
   * @return location
  **/
  @javax.annotation.Nullable
  public LocationEnum getLocation() {
    return location;
  }


  public void setLocation(LocationEnum location) {
    this.location = location;
  }


  public Db password(String password) {
    
    this.password = password;
    return this;
  }

   /**
   * Пароль для подключения к базе данных.
   * @return password
  **/
  @javax.annotation.Nonnull
  public String getPassword() {
    return password;
  }


  public void setPassword(String password) {
    this.password = password;
  }


  public Db name(String name) {
    
    this.name = name;
    return this;
  }

   /**
   * Название базы данных.
   * @return name
  **/
  @javax.annotation.Nonnull
  public String getName() {
    return name;
  }


  public void setName(String name) {
    this.name = name;
  }


  public Db host(String host) {
    
    this.host = host;
    return this;
  }

   /**
   * Хост.
   * @return host
  **/
  @javax.annotation.Nullable
  public String getHost() {
    return host;
  }


  public void setHost(String host) {
    this.host = host;
  }


  public Db type(DbType type) {
    
    this.type = type;
    return this;
  }

   /**
   * Get type
   * @return type
  **/
  @javax.annotation.Nonnull
  public DbType getType() {
    return type;
  }


  public void setType(DbType type) {
    this.type = type;
  }


  public Db hashType(HashTypeEnum hashType) {
    
    this.hashType = hashType;
    return this;
  }

   /**
   * Тип хеширования базы данных (mysql5 | mysql | postgres).
   * @return hashType
  **/
  @javax.annotation.Nullable
  public HashTypeEnum getHashType() {
    return hashType;
  }


  public void setHashType(HashTypeEnum hashType) {
    this.hashType = hashType;
  }


  public Db port(Integer port) {
    
    this.port = port;
    return this;
  }

   /**
   * Порт
   * @return port
  **/
  @javax.annotation.Nonnull
  public Integer getPort() {
    return port;
  }


  public void setPort(Integer port) {
    this.port = port;
  }


  public Db ip(String ip) {
    
    this.ip = ip;
    return this;
  }

   /**
   * IP-адрес сетевого интерфейса IPv4.
   * @return ip
  **/
  @javax.annotation.Nullable
  public String getIp() {
    return ip;
  }


  public void setIp(String ip) {
    this.ip = ip;
  }


  public Db localIp(String localIp) {
    
    this.localIp = localIp;
    return this;
  }

   /**
   * IP-адрес локального сетевого интерфейса IPv4.
   * @return localIp
  **/
  @javax.annotation.Nullable
  public String getLocalIp() {
    return localIp;
  }


  public void setLocalIp(String localIp) {
    this.localIp = localIp;
  }


  public Db status(StatusEnum status) {
    
    this.status = status;
    return this;
  }

   /**
   * Текущий статус базы данных.
   * @return status
  **/
  @javax.annotation.Nonnull
  public StatusEnum getStatus() {
    return status;
  }


  public void setStatus(StatusEnum status) {
    this.status = status;
  }


  public Db presetId(Integer presetId) {
    
    this.presetId = presetId;
    return this;
  }

   /**
   * ID тарифа.
   * @return presetId
  **/
  @javax.annotation.Nonnull
  public Integer getPresetId() {
    return presetId;
  }


  public void setPresetId(Integer presetId) {
    this.presetId = presetId;
  }


  public Db diskStats(DbDiskStats diskStats) {
    
    this.diskStats = diskStats;
    return this;
  }

   /**
   * Get diskStats
   * @return diskStats
  **/
  @javax.annotation.Nullable
  public DbDiskStats getDiskStats() {
    return diskStats;
  }


  public void setDiskStats(DbDiskStats diskStats) {
    this.diskStats = diskStats;
  }


  public Db configParameters(ConfigParameters configParameters) {
    
    this.configParameters = configParameters;
    return this;
  }

   /**
   * Get configParameters
   * @return configParameters
  **/
  @javax.annotation.Nonnull
  public ConfigParameters getConfigParameters() {
    return configParameters;
  }


  public void setConfigParameters(ConfigParameters configParameters) {
    this.configParameters = configParameters;
  }


  public Db isOnlyLocalIpAccess(Boolean isOnlyLocalIpAccess) {
    
    this.isOnlyLocalIpAccess = isOnlyLocalIpAccess;
    return this;
  }

   /**
   * Это логическое значение, которое показывает, доступна ли база данных только по локальному IP адресу.
   * @return isOnlyLocalIpAccess
  **/
  @javax.annotation.Nonnull
  public Boolean getIsOnlyLocalIpAccess() {
    return isOnlyLocalIpAccess;
  }


  public void setIsOnlyLocalIpAccess(Boolean isOnlyLocalIpAccess) {
    this.isOnlyLocalIpAccess = isOnlyLocalIpAccess;
  }


  public Db availabilityZone(AvailabilityZone availabilityZone) {
    
    this.availabilityZone = availabilityZone;
    return this;
  }

   /**
   * Get availabilityZone
   * @return availabilityZone
  **/
  @javax.annotation.Nonnull
  public AvailabilityZone getAvailabilityZone() {
    return availabilityZone;
  }


  public void setAvailabilityZone(AvailabilityZone availabilityZone) {
    this.availabilityZone = availabilityZone;
  }



  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Db db = (Db) o;
    return Objects.equals(this.id, db.id) &&
        Objects.equals(this.createdAt, db.createdAt) &&
        Objects.equals(this.accountId, db.accountId) &&
        Objects.equals(this.login, db.login) &&
        Objects.equals(this.location, db.location) &&
        Objects.equals(this.password, db.password) &&
        Objects.equals(this.name, db.name) &&
        Objects.equals(this.host, db.host) &&
        Objects.equals(this.type, db.type) &&
        Objects.equals(this.hashType, db.hashType) &&
        Objects.equals(this.port, db.port) &&
        Objects.equals(this.ip, db.ip) &&
        Objects.equals(this.localIp, db.localIp) &&
        Objects.equals(this.status, db.status) &&
        Objects.equals(this.presetId, db.presetId) &&
        Objects.equals(this.diskStats, db.diskStats) &&
        Objects.equals(this.configParameters, db.configParameters) &&
        Objects.equals(this.isOnlyLocalIpAccess, db.isOnlyLocalIpAccess) &&
        Objects.equals(this.availabilityZone, db.availabilityZone);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, createdAt, accountId, login, location, password, name, host, type, hashType, port, ip, localIp, status, presetId, diskStats, configParameters, isOnlyLocalIpAccess, availabilityZone);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Db {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    createdAt: ").append(toIndentedString(createdAt)).append("\n");
    sb.append("    accountId: ").append(toIndentedString(accountId)).append("\n");
    sb.append("    login: ").append(toIndentedString(login)).append("\n");
    sb.append("    location: ").append(toIndentedString(location)).append("\n");
    sb.append("    password: ").append(toIndentedString(password)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    host: ").append(toIndentedString(host)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    hashType: ").append(toIndentedString(hashType)).append("\n");
    sb.append("    port: ").append(toIndentedString(port)).append("\n");
    sb.append("    ip: ").append(toIndentedString(ip)).append("\n");
    sb.append("    localIp: ").append(toIndentedString(localIp)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    presetId: ").append(toIndentedString(presetId)).append("\n");
    sb.append("    diskStats: ").append(toIndentedString(diskStats)).append("\n");
    sb.append("    configParameters: ").append(toIndentedString(configParameters)).append("\n");
    sb.append("    isOnlyLocalIpAccess: ").append(toIndentedString(isOnlyLocalIpAccess)).append("\n");
    sb.append("    availabilityZone: ").append(toIndentedString(availabilityZone)).append("\n");
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
    openapiFields.add("id");
    openapiFields.add("created_at");
    openapiFields.add("account_id");
    openapiFields.add("login");
    openapiFields.add("location");
    openapiFields.add("password");
    openapiFields.add("name");
    openapiFields.add("host");
    openapiFields.add("type");
    openapiFields.add("hash_type");
    openapiFields.add("port");
    openapiFields.add("ip");
    openapiFields.add("local_ip");
    openapiFields.add("status");
    openapiFields.add("preset_id");
    openapiFields.add("disk_stats");
    openapiFields.add("config_parameters");
    openapiFields.add("is_only_local_ip_access");
    openapiFields.add("availability_zone");

    // a set of required properties/fields (JSON key names)
    openapiRequiredFields = new HashSet<String>();
    openapiRequiredFields.add("id");
    openapiRequiredFields.add("created_at");
    openapiRequiredFields.add("account_id");
    openapiRequiredFields.add("login");
    openapiRequiredFields.add("password");
    openapiRequiredFields.add("name");
    openapiRequiredFields.add("host");
    openapiRequiredFields.add("type");
    openapiRequiredFields.add("hash_type");
    openapiRequiredFields.add("port");
    openapiRequiredFields.add("ip");
    openapiRequiredFields.add("local_ip");
    openapiRequiredFields.add("status");
    openapiRequiredFields.add("preset_id");
    openapiRequiredFields.add("disk_stats");
    openapiRequiredFields.add("config_parameters");
    openapiRequiredFields.add("is_only_local_ip_access");
    openapiRequiredFields.add("availability_zone");
  }

 /**
  * Validates the JSON Element and throws an exception if issues found
  *
  * @param jsonElement JSON Element
  * @throws IOException if the JSON Element is invalid with respect to Db
  */
  public static void validateJsonElement(JsonElement jsonElement) throws IOException {
      if (jsonElement == null) {
        if (!Db.openapiRequiredFields.isEmpty()) { // has required fields but JSON element is null
          throw new IllegalArgumentException(String.format("The required field(s) %s in Db is not found in the empty JSON string", Db.openapiRequiredFields.toString()));
        }
      }

      Set<Entry<String, JsonElement>> entries = jsonElement.getAsJsonObject().entrySet();
      // check to see if the JSON string contains additional fields
      for (Entry<String, JsonElement> entry : entries) {
        if (!Db.openapiFields.contains(entry.getKey())) {
          throw new IllegalArgumentException(String.format("The field `%s` in the JSON string is not defined in the `Db` properties. JSON: %s", entry.getKey(), jsonElement.toString()));
        }
      }

      // check to make sure all required properties/fields are present in the JSON string
      for (String requiredField : Db.openapiRequiredFields) {
        if (jsonElement.getAsJsonObject().get(requiredField) == null) {
          throw new IllegalArgumentException(String.format("The required field `%s` is not found in the JSON string: %s", requiredField, jsonElement.toString()));
        }
      }
        JsonObject jsonObj = jsonElement.getAsJsonObject();
      if (!jsonObj.get("created_at").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `created_at` to be a primitive type in the JSON string but got `%s`", jsonObj.get("created_at").toString()));
      }
      if (!jsonObj.get("account_id").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `account_id` to be a primitive type in the JSON string but got `%s`", jsonObj.get("account_id").toString()));
      }
      if (!jsonObj.get("login").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `login` to be a primitive type in the JSON string but got `%s`", jsonObj.get("login").toString()));
      }
      if ((jsonObj.get("location") != null && !jsonObj.get("location").isJsonNull()) && !jsonObj.get("location").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `location` to be a primitive type in the JSON string but got `%s`", jsonObj.get("location").toString()));
      }
      if (!jsonObj.get("password").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `password` to be a primitive type in the JSON string but got `%s`", jsonObj.get("password").toString()));
      }
      if (!jsonObj.get("name").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `name` to be a primitive type in the JSON string but got `%s`", jsonObj.get("name").toString()));
      }
      if ((jsonObj.get("host") != null && !jsonObj.get("host").isJsonNull()) && !jsonObj.get("host").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `host` to be a primitive type in the JSON string but got `%s`", jsonObj.get("host").toString()));
      }
      if ((jsonObj.get("hash_type") != null && !jsonObj.get("hash_type").isJsonNull()) && !jsonObj.get("hash_type").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `hash_type` to be a primitive type in the JSON string but got `%s`", jsonObj.get("hash_type").toString()));
      }
      if ((jsonObj.get("ip") != null && !jsonObj.get("ip").isJsonNull()) && !jsonObj.get("ip").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `ip` to be a primitive type in the JSON string but got `%s`", jsonObj.get("ip").toString()));
      }
      if ((jsonObj.get("local_ip") != null && !jsonObj.get("local_ip").isJsonNull()) && !jsonObj.get("local_ip").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `local_ip` to be a primitive type in the JSON string but got `%s`", jsonObj.get("local_ip").toString()));
      }
      if (!jsonObj.get("status").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `status` to be a primitive type in the JSON string but got `%s`", jsonObj.get("status").toString()));
      }
      // validate the required field `disk_stats`
      DbDiskStats.validateJsonElement(jsonObj.get("disk_stats"));
      // validate the required field `config_parameters`
      ConfigParameters.validateJsonElement(jsonObj.get("config_parameters"));
  }

  public static class CustomTypeAdapterFactory implements TypeAdapterFactory {
    @SuppressWarnings("unchecked")
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
       if (!Db.class.isAssignableFrom(type.getRawType())) {
         return null; // this class only serializes 'Db' and its subtypes
       }
       final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);
       final TypeAdapter<Db> thisAdapter
                        = gson.getDelegateAdapter(this, TypeToken.get(Db.class));

       return (TypeAdapter<T>) new TypeAdapter<Db>() {
           @Override
           public void write(JsonWriter out, Db value) throws IOException {
             JsonObject obj = thisAdapter.toJsonTree(value).getAsJsonObject();
             elementAdapter.write(out, obj);
           }

           @Override
           public Db read(JsonReader in) throws IOException {
             JsonElement jsonElement = elementAdapter.read(in);
             validateJsonElement(jsonElement);
             return thisAdapter.fromJsonTree(jsonElement);
           }

       }.nullSafe();
    }
  }

 /**
  * Create an instance of Db given an JSON string
  *
  * @param jsonString JSON string
  * @return An instance of Db
  * @throws IOException if the JSON string is invalid with respect to Db
  */
  public static Db fromJson(String jsonString) throws IOException {
    return JSON.getGson().fromJson(jsonString, Db.class);
  }

 /**
  * Convert an instance of Db to an JSON string
  *
  * @return JSON string
  */
  public String toJson() {
    return JSON.getGson().toJson(this);
  }
}

