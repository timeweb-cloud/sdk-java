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
import java.util.ArrayList;
import java.util.List;
import org.openapitools.client.model.AvailabilityZone;
import org.openapitools.client.model.DatabaseClusterChildServicesInner;
import org.openapitools.client.model.DatabaseClusterDisk;
import org.openapitools.client.model.DatabaseClusterDiskAutoscaling;
import org.openapitools.client.model.DatabaseClusterDomainsInner;
import org.openapitools.client.model.DatabaseClusterMaintenanceSlot;
import org.openapitools.client.model.DatabaseClusterNetworksInner;
import org.openapitools.client.model.DatabaseClusterParentServicesInner;
import org.openapitools.client.model.DatabaseClusterReplicaListInner;
import org.openapitools.client.model.Mysql;

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
 * Кластер базы данных
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2026-08-06T15:06:51.562821Z[Etc/UTC]")
public class DatabaseCluster {
  public static final String SERIALIZED_NAME_ID = "id";
  @SerializedName(SERIALIZED_NAME_ID)
  private BigDecimal id;

  public static final String SERIALIZED_NAME_CREATED_AT = "created_at";
  @SerializedName(SERIALIZED_NAME_CREATED_AT)
  private String createdAt;

  /**
   * Локация сервера.
   */
  @JsonAdapter(LocationEnum.Adapter.class)
  public enum LocationEnum {
    RU_1("ru-1"),
    
    RU_3("ru-3"),
    
    PL_1("pl-1"),
    
    NL_1("nl-1"),
    
    DE_1("de-1"),
    
    US_2("us-2"),
    
    US_3("us-3");

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
      return null;
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

  public static final String SERIALIZED_NAME_NAME = "name";
  @SerializedName(SERIALIZED_NAME_NAME)
  private String name;

  public static final String SERIALIZED_NAME_DESCRIPTION = "description";
  @SerializedName(SERIALIZED_NAME_DESCRIPTION)
  private String description;

  public static final String SERIALIZED_NAME_NETWORKS = "networks";
  @SerializedName(SERIALIZED_NAME_NETWORKS)
  private List<DatabaseClusterNetworksInner> networks = new ArrayList<>();

  public static final String SERIALIZED_NAME_IS_ENABLED_PUBLIC_IPV6 = "is_enabled_public_ipv6";
  @SerializedName(SERIALIZED_NAME_IS_ENABLED_PUBLIC_IPV6)
  private Boolean isEnabledPublicIpv6;

  /**
   * Тип базы данных. Список возможных значений шире, чем список типов, доступных при создании нового кластера.
   */
  @JsonAdapter(TypeEnum.Adapter.class)
  public enum TypeEnum {
    MYSQL("mysql"),
    
    MYSQL5("mysql5"),
    
    MYSQL8_4("mysql8_4"),
    
    POSTGRES("postgres"),
    
    POSTGRES14("postgres14"),
    
    POSTGRES15("postgres15"),
    
    POSTGRES16("postgres16"),
    
    POSTGRES17("postgres17"),
    
    POSTGRES18("postgres18"),
    
    REDIS("redis"),
    
    REDIS7("redis7"),
    
    REDIS8_1("redis8_1"),
    
    VALKEY("valkey"),
    
    VALKEY7("valkey7"),
    
    VALKEY8_1("valkey8_1"),
    
    VALKEY9_1("valkey9_1"),
    
    MONGODB("mongodb"),
    
    MONGODB4("mongodb4"),
    
    MONGODB6("mongodb6"),
    
    MONGODB7("mongodb7"),
    
    MONGODB8_0("mongodb8_0"),
    
    OPENSEARCH("opensearch"),
    
    OPENSEARCH2_19("opensearch2_19"),
    
    CLICKHOUSE("clickhouse"),
    
    CLICKHOUSE24("clickhouse24"),
    
    CLICKHOUSE25("clickhouse25"),
    
    KAFKA("kafka"),
    
    RABBITMQ("rabbitmq"),
    
    RABBITMQ4_0("rabbitmq4_0");

    private String value;

    TypeEnum(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public static TypeEnum fromValue(String value) {
      for (TypeEnum b : TypeEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      return null;
    }

    public static class Adapter extends TypeAdapter<TypeEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final TypeEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public TypeEnum read(final JsonReader jsonReader) throws IOException {
        String value =  jsonReader.nextString();
        return TypeEnum.fromValue(value);
      }
    }
  }

  public static final String SERIALIZED_NAME_TYPE = "type";
  @SerializedName(SERIALIZED_NAME_TYPE)
  private TypeEnum type;

  /**
   * Тип хеширования кластера базы данных (mysql5 | mysql | postgres).
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

  public static final String SERIALIZED_NAME_AVATAR_LINK = "avatar_link";
  @SerializedName(SERIALIZED_NAME_AVATAR_LINK)
  private String avatarLink;

  public static final String SERIALIZED_NAME_PORT = "port";
  @SerializedName(SERIALIZED_NAME_PORT)
  private Integer port;

  /**
   * Текущий статус кластера базы данных. Значение &#x60;read_only&#x60; означает, что запись в кластер заблокирована из-за переполнения диска — чтобы снять блокировку, освободите место или увеличьте размер диска.
   */
  @JsonAdapter(StatusEnum.Adapter.class)
  public enum StatusEnum {
    STARTED("started"),
    
    STARTING("starting"),
    
    STOPPED("stopped"),
    
    NO_PAID("no_paid"),
    
    LAN_TRANSFER("lan_transfer"),
    
    ERROR("error"),
    
    BLOCKED("blocked"),
    
    BACKUP_RECOVERY("backup_recovery"),
    
    TRANSFER("transfer"),
    
    REBOOTING("rebooting"),
    
    TURNING_OFF("turning_off"),
    
    TURNING_ON("turning_on"),
    
    READ_ONLY("read_only"),
    
    USER_TRANSFER("user_transfer");

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

  public static final String SERIALIZED_NAME_CONFIGURATOR_ID = "configurator_id";
  @SerializedName(SERIALIZED_NAME_CONFIGURATOR_ID)
  private Integer configuratorId;

  public static final String SERIALIZED_NAME_CPU = "cpu";
  @SerializedName(SERIALIZED_NAME_CPU)
  private Integer cpu;

  public static final String SERIALIZED_NAME_CPU_FREQUENCY = "cpu_frequency";
  @SerializedName(SERIALIZED_NAME_CPU_FREQUENCY)
  private String cpuFrequency;

  public static final String SERIALIZED_NAME_IS_DEDICATED_CPU = "is_dedicated_cpu";
  @SerializedName(SERIALIZED_NAME_IS_DEDICATED_CPU)
  private Boolean isDedicatedCpu;

  public static final String SERIALIZED_NAME_RAM = "ram";
  @SerializedName(SERIALIZED_NAME_RAM)
  private Integer ram;

  public static final String SERIALIZED_NAME_DISK = "disk";
  @SerializedName(SERIALIZED_NAME_DISK)
  private DatabaseClusterDisk disk;

  public static final String SERIALIZED_NAME_HAS_ADDITIONAL_DISK = "has_additional_disk";
  @SerializedName(SERIALIZED_NAME_HAS_ADDITIONAL_DISK)
  private Boolean hasAdditionalDisk;

  public static final String SERIALIZED_NAME_DISK_AUTOSCALING = "disk_autoscaling";
  @SerializedName(SERIALIZED_NAME_DISK_AUTOSCALING)
  private DatabaseClusterDiskAutoscaling diskAutoscaling;

  public static final String SERIALIZED_NAME_CONFIG_PARAMETERS = "config_parameters";
  @SerializedName(SERIALIZED_NAME_CONFIG_PARAMETERS)
  private Mysql configParameters;

  public static final String SERIALIZED_NAME_IS_ENABLED_PUBLIC_NETWORK = "is_enabled_public_network";
  @SerializedName(SERIALIZED_NAME_IS_ENABLED_PUBLIC_NETWORK)
  private Boolean isEnabledPublicNetwork;

  public static final String SERIALIZED_NAME_IS_SECURE_CONNECTION_ENABLED = "is_secure_connection_enabled";
  @SerializedName(SERIALIZED_NAME_IS_SECURE_CONNECTION_ENABLED)
  private Boolean isSecureConnectionEnabled;

  public static final String SERIALIZED_NAME_IS_AUTOBACKUPS_ENABLED = "is_autobackups_enabled";
  @SerializedName(SERIALIZED_NAME_IS_AUTOBACKUPS_ENABLED)
  private Boolean isAutobackupsEnabled;

  public static final String SERIALIZED_NAME_IS_BACKUP_SCHEDULE_ENABLED = "is_backup_schedule_enabled";
  @SerializedName(SERIALIZED_NAME_IS_BACKUP_SCHEDULE_ENABLED)
  private Boolean isBackupScheduleEnabled;

  public static final String SERIALIZED_NAME_AVAILABILITY_ZONE = "availability_zone";
  @SerializedName(SERIALIZED_NAME_AVAILABILITY_ZONE)
  private AvailabilityZone availabilityZone;

  public static final String SERIALIZED_NAME_PROJECT_ID = "project_id";
  @SerializedName(SERIALIZED_NAME_PROJECT_ID)
  private Integer projectId;

  public static final String SERIALIZED_NAME_REPLICA_LIST = "replica_list";
  @SerializedName(SERIALIZED_NAME_REPLICA_LIST)
  private List<DatabaseClusterReplicaListInner> replicaList = new ArrayList<>();

  public static final String SERIALIZED_NAME_DOMAINS = "domains";
  @SerializedName(SERIALIZED_NAME_DOMAINS)
  private List<DatabaseClusterDomainsInner> domains = new ArrayList<>();

  public static final String SERIALIZED_NAME_CHILD_SERVICES = "child_services";
  @SerializedName(SERIALIZED_NAME_CHILD_SERVICES)
  private List<DatabaseClusterChildServicesInner> childServices = new ArrayList<>();

  public static final String SERIALIZED_NAME_PARENT_SERVICES = "parent_services";
  @SerializedName(SERIALIZED_NAME_PARENT_SERVICES)
  private List<DatabaseClusterParentServicesInner> parentServices = new ArrayList<>();

  public static final String SERIALIZED_NAME_MAINTENANCE_SLOT = "maintenance_slot";
  @SerializedName(SERIALIZED_NAME_MAINTENANCE_SLOT)
  private DatabaseClusterMaintenanceSlot maintenanceSlot;

  public DatabaseCluster() {
  }

  public DatabaseCluster id(BigDecimal id) {
    
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


  public DatabaseCluster createdAt(String createdAt) {
    
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


  public DatabaseCluster location(LocationEnum location) {
    
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


  public DatabaseCluster name(String name) {
    
    this.name = name;
    return this;
  }

   /**
   * Название кластера базы данных.
   * @return name
  **/
  @javax.annotation.Nonnull
  public String getName() {
    return name;
  }


  public void setName(String name) {
    this.name = name;
  }


  public DatabaseCluster description(String description) {
    
    this.description = description;
    return this;
  }

   /**
   * Описание кластера базы данных.
   * @return description
  **/
  @javax.annotation.Nonnull
  public String getDescription() {
    return description;
  }


  public void setDescription(String description) {
    this.description = description;
  }


  public DatabaseCluster networks(List<DatabaseClusterNetworksInner> networks) {
    
    this.networks = networks;
    return this;
  }

  public DatabaseCluster addNetworksItem(DatabaseClusterNetworksInner networksItem) {
    if (this.networks == null) {
      this.networks = new ArrayList<>();
    }
    this.networks.add(networksItem);
    return this;
  }

   /**
   * Список сетей кластера базы данных.
   * @return networks
  **/
  @javax.annotation.Nonnull
  public List<DatabaseClusterNetworksInner> getNetworks() {
    return networks;
  }


  public void setNetworks(List<DatabaseClusterNetworksInner> networks) {
    this.networks = networks;
  }


  public DatabaseCluster isEnabledPublicIpv6(Boolean isEnabledPublicIpv6) {
    
    this.isEnabledPublicIpv6 = isEnabledPublicIpv6;
    return this;
  }

   /**
   * Использование публичного IPv6-адреса.
   * @return isEnabledPublicIpv6
  **/
  @javax.annotation.Nonnull
  public Boolean getIsEnabledPublicIpv6() {
    return isEnabledPublicIpv6;
  }


  public void setIsEnabledPublicIpv6(Boolean isEnabledPublicIpv6) {
    this.isEnabledPublicIpv6 = isEnabledPublicIpv6;
  }


  public DatabaseCluster type(TypeEnum type) {
    
    this.type = type;
    return this;
  }

   /**
   * Тип базы данных. Список возможных значений шире, чем список типов, доступных при создании нового кластера.
   * @return type
  **/
  @javax.annotation.Nullable
  public TypeEnum getType() {
    return type;
  }


  public void setType(TypeEnum type) {
    this.type = type;
  }


  public DatabaseCluster hashType(HashTypeEnum hashType) {
    
    this.hashType = hashType;
    return this;
  }

   /**
   * Тип хеширования кластера базы данных (mysql5 | mysql | postgres).
   * @return hashType
  **/
  @javax.annotation.Nullable
  public HashTypeEnum getHashType() {
    return hashType;
  }


  public void setHashType(HashTypeEnum hashType) {
    this.hashType = hashType;
  }


  public DatabaseCluster avatarLink(String avatarLink) {
    
    this.avatarLink = avatarLink;
    return this;
  }

   /**
   * Ссылка на аватар для базы данных.
   * @return avatarLink
  **/
  @javax.annotation.Nullable
  public String getAvatarLink() {
    return avatarLink;
  }


  public void setAvatarLink(String avatarLink) {
    this.avatarLink = avatarLink;
  }


  public DatabaseCluster port(Integer port) {
    
    this.port = port;
    return this;
  }

   /**
   * Порт
   * @return port
  **/
  @javax.annotation.Nullable
  public Integer getPort() {
    return port;
  }


  public void setPort(Integer port) {
    this.port = port;
  }


  public DatabaseCluster status(StatusEnum status) {
    
    this.status = status;
    return this;
  }

   /**
   * Текущий статус кластера базы данных. Значение &#x60;read_only&#x60; означает, что запись в кластер заблокирована из-за переполнения диска — чтобы снять блокировку, освободите место или увеличьте размер диска.
   * @return status
  **/
  @javax.annotation.Nonnull
  public StatusEnum getStatus() {
    return status;
  }


  public void setStatus(StatusEnum status) {
    this.status = status;
  }


  public DatabaseCluster presetId(Integer presetId) {
    
    this.presetId = presetId;
    return this;
  }

   /**
   * ID тарифа. Равен &#x60;null&#x60; у кластеров, созданных через конфигуратор — в этом случае заполнен &#x60;configurator_id&#x60;.
   * @return presetId
  **/
  @javax.annotation.Nullable
  public Integer getPresetId() {
    return presetId;
  }


  public void setPresetId(Integer presetId) {
    this.presetId = presetId;
  }


  public DatabaseCluster configuratorId(Integer configuratorId) {
    
    this.configuratorId = configuratorId;
    return this;
  }

   /**
   * ID конфигуратора. Равен &#x60;null&#x60; у кластеров, созданных по тарифу.
   * @return configuratorId
  **/
  @javax.annotation.Nullable
  public Integer getConfiguratorId() {
    return configuratorId;
  }


  public void setConfiguratorId(Integer configuratorId) {
    this.configuratorId = configuratorId;
  }


  public DatabaseCluster cpu(Integer cpu) {
    
    this.cpu = cpu;
    return this;
  }

   /**
   * Количество ядер процессора.
   * @return cpu
  **/
  @javax.annotation.Nullable
  public Integer getCpu() {
    return cpu;
  }


  public void setCpu(Integer cpu) {
    this.cpu = cpu;
  }


  public DatabaseCluster cpuFrequency(String cpuFrequency) {
    
    this.cpuFrequency = cpuFrequency;
    return this;
  }

   /**
   * Частота процессора.
   * @return cpuFrequency
  **/
  @javax.annotation.Nullable
  public String getCpuFrequency() {
    return cpuFrequency;
  }


  public void setCpuFrequency(String cpuFrequency) {
    this.cpuFrequency = cpuFrequency;
  }


  public DatabaseCluster isDedicatedCpu(Boolean isDedicatedCpu) {
    
    this.isDedicatedCpu = isDedicatedCpu;
    return this;
  }

   /**
   * Используются ли выделенные ядра процессора.
   * @return isDedicatedCpu
  **/
  @javax.annotation.Nonnull
  public Boolean getIsDedicatedCpu() {
    return isDedicatedCpu;
  }


  public void setIsDedicatedCpu(Boolean isDedicatedCpu) {
    this.isDedicatedCpu = isDedicatedCpu;
  }


  public DatabaseCluster ram(Integer ram) {
    
    this.ram = ram;
    return this;
  }

   /**
   * Объем оперативной памяти (в Мб).
   * @return ram
  **/
  @javax.annotation.Nullable
  public Integer getRam() {
    return ram;
  }


  public void setRam(Integer ram) {
    this.ram = ram;
  }


  public DatabaseCluster disk(DatabaseClusterDisk disk) {
    
    this.disk = disk;
    return this;
  }

   /**
   * Get disk
   * @return disk
  **/
  @javax.annotation.Nullable
  public DatabaseClusterDisk getDisk() {
    return disk;
  }


  public void setDisk(DatabaseClusterDisk disk) {
    this.disk = disk;
  }


  public DatabaseCluster hasAdditionalDisk(Boolean hasAdditionalDisk) {
    
    this.hasAdditionalDisk = hasAdditionalDisk;
    return this;
  }

   /**
   * Подключен ли к кластеру дополнительный диск.
   * @return hasAdditionalDisk
  **/
  @javax.annotation.Nonnull
  public Boolean getHasAdditionalDisk() {
    return hasAdditionalDisk;
  }


  public void setHasAdditionalDisk(Boolean hasAdditionalDisk) {
    this.hasAdditionalDisk = hasAdditionalDisk;
  }


  public DatabaseCluster diskAutoscaling(DatabaseClusterDiskAutoscaling diskAutoscaling) {
    
    this.diskAutoscaling = diskAutoscaling;
    return this;
  }

   /**
   * Get diskAutoscaling
   * @return diskAutoscaling
  **/
  @javax.annotation.Nullable
  public DatabaseClusterDiskAutoscaling getDiskAutoscaling() {
    return diskAutoscaling;
  }


  public void setDiskAutoscaling(DatabaseClusterDiskAutoscaling diskAutoscaling) {
    this.diskAutoscaling = diskAutoscaling;
  }


  public DatabaseCluster configParameters(Mysql configParameters) {
    
    this.configParameters = configParameters;
    return this;
  }

   /**
   * Get configParameters
   * @return configParameters
  **/
  @javax.annotation.Nonnull
  public Mysql getConfigParameters() {
    return configParameters;
  }


  public void setConfigParameters(Mysql configParameters) {
    this.configParameters = configParameters;
  }


  public DatabaseCluster isEnabledPublicNetwork(Boolean isEnabledPublicNetwork) {
    
    this.isEnabledPublicNetwork = isEnabledPublicNetwork;
    return this;
  }

   /**
   * Доступность публичного IP-адреса
   * @return isEnabledPublicNetwork
  **/
  @javax.annotation.Nonnull
  public Boolean getIsEnabledPublicNetwork() {
    return isEnabledPublicNetwork;
  }


  public void setIsEnabledPublicNetwork(Boolean isEnabledPublicNetwork) {
    this.isEnabledPublicNetwork = isEnabledPublicNetwork;
  }


  public DatabaseCluster isSecureConnectionEnabled(Boolean isSecureConnectionEnabled) {
    
    this.isSecureConnectionEnabled = isSecureConnectionEnabled;
    return this;
  }

   /**
   * Включено ли защищенное подключение к кластеру базы данных.
   * @return isSecureConnectionEnabled
  **/
  @javax.annotation.Nonnull
  public Boolean getIsSecureConnectionEnabled() {
    return isSecureConnectionEnabled;
  }


  public void setIsSecureConnectionEnabled(Boolean isSecureConnectionEnabled) {
    this.isSecureConnectionEnabled = isSecureConnectionEnabled;
  }


  public DatabaseCluster isAutobackupsEnabled(Boolean isAutobackupsEnabled) {
    
    this.isAutobackupsEnabled = isAutobackupsEnabled;
    return this;
  }

   /**
   * Включены ли автоматические резервные копии кластера базы данных.
   * @return isAutobackupsEnabled
  **/
  @javax.annotation.Nonnull
  public Boolean getIsAutobackupsEnabled() {
    return isAutobackupsEnabled;
  }


  public void setIsAutobackupsEnabled(Boolean isAutobackupsEnabled) {
    this.isAutobackupsEnabled = isAutobackupsEnabled;
  }


  public DatabaseCluster isBackupScheduleEnabled(Boolean isBackupScheduleEnabled) {
    
    this.isBackupScheduleEnabled = isBackupScheduleEnabled;
    return this;
  }

   /**
   * Включено ли расписание резервного копирования кластера базы данных.
   * @return isBackupScheduleEnabled
  **/
  @javax.annotation.Nonnull
  public Boolean getIsBackupScheduleEnabled() {
    return isBackupScheduleEnabled;
  }


  public void setIsBackupScheduleEnabled(Boolean isBackupScheduleEnabled) {
    this.isBackupScheduleEnabled = isBackupScheduleEnabled;
  }


  public DatabaseCluster availabilityZone(AvailabilityZone availabilityZone) {
    
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


  public DatabaseCluster projectId(Integer projectId) {
    
    this.projectId = projectId;
    return this;
  }

   /**
   * ID проекта, в котором находится кластер базы данных.
   * @return projectId
  **/
  @javax.annotation.Nullable
  public Integer getProjectId() {
    return projectId;
  }


  public void setProjectId(Integer projectId) {
    this.projectId = projectId;
  }


  public DatabaseCluster replicaList(List<DatabaseClusterReplicaListInner> replicaList) {
    
    this.replicaList = replicaList;
    return this;
  }

  public DatabaseCluster addReplicaListItem(DatabaseClusterReplicaListInner replicaListItem) {
    if (this.replicaList == null) {
      this.replicaList = new ArrayList<>();
    }
    this.replicaList.add(replicaListItem);
    return this;
  }

   /**
   * Список реплик кластера базы данных.
   * @return replicaList
  **/
  @javax.annotation.Nonnull
  public List<DatabaseClusterReplicaListInner> getReplicaList() {
    return replicaList;
  }


  public void setReplicaList(List<DatabaseClusterReplicaListInner> replicaList) {
    this.replicaList = replicaList;
  }


  public DatabaseCluster domains(List<DatabaseClusterDomainsInner> domains) {
    
    this.domains = domains;
    return this;
  }

  public DatabaseCluster addDomainsItem(DatabaseClusterDomainsInner domainsItem) {
    if (this.domains == null) {
      this.domains = new ArrayList<>();
    }
    this.domains.add(domainsItem);
    return this;
  }

   /**
   * Список доменов кластера базы данных. Если публичная сеть отключена (&#x60;is_enabled_public_network: false&#x60;), список всегда пустой.
   * @return domains
  **/
  @javax.annotation.Nonnull
  public List<DatabaseClusterDomainsInner> getDomains() {
    return domains;
  }


  public void setDomains(List<DatabaseClusterDomainsInner> domains) {
    this.domains = domains;
  }


  public DatabaseCluster childServices(List<DatabaseClusterChildServicesInner> childServices) {
    
    this.childServices = childServices;
    return this;
  }

  public DatabaseCluster addChildServicesItem(DatabaseClusterChildServicesInner childServicesItem) {
    if (this.childServices == null) {
      this.childServices = new ArrayList<>();
    }
    this.childServices.add(childServicesItem);
    return this;
  }

   /**
   * Список дочерних сервисов кластера базы данных.
   * @return childServices
  **/
  @javax.annotation.Nonnull
  public List<DatabaseClusterChildServicesInner> getChildServices() {
    return childServices;
  }


  public void setChildServices(List<DatabaseClusterChildServicesInner> childServices) {
    this.childServices = childServices;
  }


  public DatabaseCluster parentServices(List<DatabaseClusterParentServicesInner> parentServices) {
    
    this.parentServices = parentServices;
    return this;
  }

  public DatabaseCluster addParentServicesItem(DatabaseClusterParentServicesInner parentServicesItem) {
    if (this.parentServices == null) {
      this.parentServices = new ArrayList<>();
    }
    this.parentServices.add(parentServicesItem);
    return this;
  }

   /**
   * Список родительских сервисов кластера базы данных.
   * @return parentServices
  **/
  @javax.annotation.Nonnull
  public List<DatabaseClusterParentServicesInner> getParentServices() {
    return parentServices;
  }


  public void setParentServices(List<DatabaseClusterParentServicesInner> parentServices) {
    this.parentServices = parentServices;
  }


  public DatabaseCluster maintenanceSlot(DatabaseClusterMaintenanceSlot maintenanceSlot) {
    
    this.maintenanceSlot = maintenanceSlot;
    return this;
  }

   /**
   * Get maintenanceSlot
   * @return maintenanceSlot
  **/
  @javax.annotation.Nonnull
  public DatabaseClusterMaintenanceSlot getMaintenanceSlot() {
    return maintenanceSlot;
  }


  public void setMaintenanceSlot(DatabaseClusterMaintenanceSlot maintenanceSlot) {
    this.maintenanceSlot = maintenanceSlot;
  }



  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DatabaseCluster databaseCluster = (DatabaseCluster) o;
    return Objects.equals(this.id, databaseCluster.id) &&
        Objects.equals(this.createdAt, databaseCluster.createdAt) &&
        Objects.equals(this.location, databaseCluster.location) &&
        Objects.equals(this.name, databaseCluster.name) &&
        Objects.equals(this.description, databaseCluster.description) &&
        Objects.equals(this.networks, databaseCluster.networks) &&
        Objects.equals(this.isEnabledPublicIpv6, databaseCluster.isEnabledPublicIpv6) &&
        Objects.equals(this.type, databaseCluster.type) &&
        Objects.equals(this.hashType, databaseCluster.hashType) &&
        Objects.equals(this.avatarLink, databaseCluster.avatarLink) &&
        Objects.equals(this.port, databaseCluster.port) &&
        Objects.equals(this.status, databaseCluster.status) &&
        Objects.equals(this.presetId, databaseCluster.presetId) &&
        Objects.equals(this.configuratorId, databaseCluster.configuratorId) &&
        Objects.equals(this.cpu, databaseCluster.cpu) &&
        Objects.equals(this.cpuFrequency, databaseCluster.cpuFrequency) &&
        Objects.equals(this.isDedicatedCpu, databaseCluster.isDedicatedCpu) &&
        Objects.equals(this.ram, databaseCluster.ram) &&
        Objects.equals(this.disk, databaseCluster.disk) &&
        Objects.equals(this.hasAdditionalDisk, databaseCluster.hasAdditionalDisk) &&
        Objects.equals(this.diskAutoscaling, databaseCluster.diskAutoscaling) &&
        Objects.equals(this.configParameters, databaseCluster.configParameters) &&
        Objects.equals(this.isEnabledPublicNetwork, databaseCluster.isEnabledPublicNetwork) &&
        Objects.equals(this.isSecureConnectionEnabled, databaseCluster.isSecureConnectionEnabled) &&
        Objects.equals(this.isAutobackupsEnabled, databaseCluster.isAutobackupsEnabled) &&
        Objects.equals(this.isBackupScheduleEnabled, databaseCluster.isBackupScheduleEnabled) &&
        Objects.equals(this.availabilityZone, databaseCluster.availabilityZone) &&
        Objects.equals(this.projectId, databaseCluster.projectId) &&
        Objects.equals(this.replicaList, databaseCluster.replicaList) &&
        Objects.equals(this.domains, databaseCluster.domains) &&
        Objects.equals(this.childServices, databaseCluster.childServices) &&
        Objects.equals(this.parentServices, databaseCluster.parentServices) &&
        Objects.equals(this.maintenanceSlot, databaseCluster.maintenanceSlot);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, createdAt, location, name, description, networks, isEnabledPublicIpv6, type, hashType, avatarLink, port, status, presetId, configuratorId, cpu, cpuFrequency, isDedicatedCpu, ram, disk, hasAdditionalDisk, diskAutoscaling, configParameters, isEnabledPublicNetwork, isSecureConnectionEnabled, isAutobackupsEnabled, isBackupScheduleEnabled, availabilityZone, projectId, replicaList, domains, childServices, parentServices, maintenanceSlot);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DatabaseCluster {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    createdAt: ").append(toIndentedString(createdAt)).append("\n");
    sb.append("    location: ").append(toIndentedString(location)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    networks: ").append(toIndentedString(networks)).append("\n");
    sb.append("    isEnabledPublicIpv6: ").append(toIndentedString(isEnabledPublicIpv6)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    hashType: ").append(toIndentedString(hashType)).append("\n");
    sb.append("    avatarLink: ").append(toIndentedString(avatarLink)).append("\n");
    sb.append("    port: ").append(toIndentedString(port)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    presetId: ").append(toIndentedString(presetId)).append("\n");
    sb.append("    configuratorId: ").append(toIndentedString(configuratorId)).append("\n");
    sb.append("    cpu: ").append(toIndentedString(cpu)).append("\n");
    sb.append("    cpuFrequency: ").append(toIndentedString(cpuFrequency)).append("\n");
    sb.append("    isDedicatedCpu: ").append(toIndentedString(isDedicatedCpu)).append("\n");
    sb.append("    ram: ").append(toIndentedString(ram)).append("\n");
    sb.append("    disk: ").append(toIndentedString(disk)).append("\n");
    sb.append("    hasAdditionalDisk: ").append(toIndentedString(hasAdditionalDisk)).append("\n");
    sb.append("    diskAutoscaling: ").append(toIndentedString(diskAutoscaling)).append("\n");
    sb.append("    configParameters: ").append(toIndentedString(configParameters)).append("\n");
    sb.append("    isEnabledPublicNetwork: ").append(toIndentedString(isEnabledPublicNetwork)).append("\n");
    sb.append("    isSecureConnectionEnabled: ").append(toIndentedString(isSecureConnectionEnabled)).append("\n");
    sb.append("    isAutobackupsEnabled: ").append(toIndentedString(isAutobackupsEnabled)).append("\n");
    sb.append("    isBackupScheduleEnabled: ").append(toIndentedString(isBackupScheduleEnabled)).append("\n");
    sb.append("    availabilityZone: ").append(toIndentedString(availabilityZone)).append("\n");
    sb.append("    projectId: ").append(toIndentedString(projectId)).append("\n");
    sb.append("    replicaList: ").append(toIndentedString(replicaList)).append("\n");
    sb.append("    domains: ").append(toIndentedString(domains)).append("\n");
    sb.append("    childServices: ").append(toIndentedString(childServices)).append("\n");
    sb.append("    parentServices: ").append(toIndentedString(parentServices)).append("\n");
    sb.append("    maintenanceSlot: ").append(toIndentedString(maintenanceSlot)).append("\n");
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
    openapiFields.add("location");
    openapiFields.add("name");
    openapiFields.add("description");
    openapiFields.add("networks");
    openapiFields.add("is_enabled_public_ipv6");
    openapiFields.add("type");
    openapiFields.add("hash_type");
    openapiFields.add("avatar_link");
    openapiFields.add("port");
    openapiFields.add("status");
    openapiFields.add("preset_id");
    openapiFields.add("configurator_id");
    openapiFields.add("cpu");
    openapiFields.add("cpu_frequency");
    openapiFields.add("is_dedicated_cpu");
    openapiFields.add("ram");
    openapiFields.add("disk");
    openapiFields.add("has_additional_disk");
    openapiFields.add("disk_autoscaling");
    openapiFields.add("config_parameters");
    openapiFields.add("is_enabled_public_network");
    openapiFields.add("is_secure_connection_enabled");
    openapiFields.add("is_autobackups_enabled");
    openapiFields.add("is_backup_schedule_enabled");
    openapiFields.add("availability_zone");
    openapiFields.add("project_id");
    openapiFields.add("replica_list");
    openapiFields.add("domains");
    openapiFields.add("child_services");
    openapiFields.add("parent_services");
    openapiFields.add("maintenance_slot");

    // a set of required properties/fields (JSON key names)
    openapiRequiredFields = new HashSet<String>();
    openapiRequiredFields.add("id");
    openapiRequiredFields.add("created_at");
    openapiRequiredFields.add("location");
    openapiRequiredFields.add("name");
    openapiRequiredFields.add("description");
    openapiRequiredFields.add("networks");
    openapiRequiredFields.add("is_enabled_public_ipv6");
    openapiRequiredFields.add("type");
    openapiRequiredFields.add("hash_type");
    openapiRequiredFields.add("avatar_link");
    openapiRequiredFields.add("port");
    openapiRequiredFields.add("status");
    openapiRequiredFields.add("preset_id");
    openapiRequiredFields.add("configurator_id");
    openapiRequiredFields.add("cpu");
    openapiRequiredFields.add("cpu_frequency");
    openapiRequiredFields.add("is_dedicated_cpu");
    openapiRequiredFields.add("ram");
    openapiRequiredFields.add("disk");
    openapiRequiredFields.add("has_additional_disk");
    openapiRequiredFields.add("disk_autoscaling");
    openapiRequiredFields.add("config_parameters");
    openapiRequiredFields.add("is_enabled_public_network");
    openapiRequiredFields.add("is_secure_connection_enabled");
    openapiRequiredFields.add("is_autobackups_enabled");
    openapiRequiredFields.add("is_backup_schedule_enabled");
    openapiRequiredFields.add("availability_zone");
    openapiRequiredFields.add("replica_list");
    openapiRequiredFields.add("domains");
    openapiRequiredFields.add("child_services");
    openapiRequiredFields.add("parent_services");
    openapiRequiredFields.add("maintenance_slot");
  }

 /**
  * Validates the JSON Element and throws an exception if issues found
  *
  * @param jsonElement JSON Element
  * @throws IOException if the JSON Element is invalid with respect to DatabaseCluster
  */
  public static void validateJsonElement(JsonElement jsonElement) throws IOException {
      if (jsonElement == null) {
        if (!DatabaseCluster.openapiRequiredFields.isEmpty()) { // has required fields but JSON element is null
          throw new IllegalArgumentException(String.format("The required field(s) %s in DatabaseCluster is not found in the empty JSON string", DatabaseCluster.openapiRequiredFields.toString()));
        }
      }

      Set<Entry<String, JsonElement>> entries = jsonElement.getAsJsonObject().entrySet();
      // check to see if the JSON string contains additional fields
      for (Entry<String, JsonElement> entry : entries) {
        if (!DatabaseCluster.openapiFields.contains(entry.getKey())) {
          throw new IllegalArgumentException(String.format("The field `%s` in the JSON string is not defined in the `DatabaseCluster` properties. JSON: %s", entry.getKey(), jsonElement.toString()));
        }
      }

      // check to make sure all required properties/fields are present in the JSON string
      for (String requiredField : DatabaseCluster.openapiRequiredFields) {
        if (jsonElement.getAsJsonObject().get(requiredField) == null) {
          throw new IllegalArgumentException(String.format("The required field `%s` is not found in the JSON string: %s", requiredField, jsonElement.toString()));
        }
      }
        JsonObject jsonObj = jsonElement.getAsJsonObject();
      if (!jsonObj.get("created_at").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `created_at` to be a primitive type in the JSON string but got `%s`", jsonObj.get("created_at").toString()));
      }
      if ((jsonObj.get("location") != null && !jsonObj.get("location").isJsonNull()) && !jsonObj.get("location").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `location` to be a primitive type in the JSON string but got `%s`", jsonObj.get("location").toString()));
      }
      if (!jsonObj.get("name").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `name` to be a primitive type in the JSON string but got `%s`", jsonObj.get("name").toString()));
      }
      if (!jsonObj.get("description").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `description` to be a primitive type in the JSON string but got `%s`", jsonObj.get("description").toString()));
      }
      // ensure the json data is an array
      if (!jsonObj.get("networks").isJsonArray()) {
        throw new IllegalArgumentException(String.format("Expected the field `networks` to be an array in the JSON string but got `%s`", jsonObj.get("networks").toString()));
      }

      JsonArray jsonArraynetworks = jsonObj.getAsJsonArray("networks");
      // validate the required field `networks` (array)
      for (int i = 0; i < jsonArraynetworks.size(); i++) {
        DatabaseClusterNetworksInner.validateJsonElement(jsonArraynetworks.get(i));
      };
      if ((jsonObj.get("type") != null && !jsonObj.get("type").isJsonNull()) && !jsonObj.get("type").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `type` to be a primitive type in the JSON string but got `%s`", jsonObj.get("type").toString()));
      }
      if ((jsonObj.get("hash_type") != null && !jsonObj.get("hash_type").isJsonNull()) && !jsonObj.get("hash_type").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `hash_type` to be a primitive type in the JSON string but got `%s`", jsonObj.get("hash_type").toString()));
      }
      if ((jsonObj.get("avatar_link") != null && !jsonObj.get("avatar_link").isJsonNull()) && !jsonObj.get("avatar_link").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `avatar_link` to be a primitive type in the JSON string but got `%s`", jsonObj.get("avatar_link").toString()));
      }
      if (!jsonObj.get("status").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `status` to be a primitive type in the JSON string but got `%s`", jsonObj.get("status").toString()));
      }
      if ((jsonObj.get("cpu_frequency") != null && !jsonObj.get("cpu_frequency").isJsonNull()) && !jsonObj.get("cpu_frequency").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `cpu_frequency` to be a primitive type in the JSON string but got `%s`", jsonObj.get("cpu_frequency").toString()));
      }
      // validate the required field `disk`
      DatabaseClusterDisk.validateJsonElement(jsonObj.get("disk"));
      // validate the required field `disk_autoscaling`
      DatabaseClusterDiskAutoscaling.validateJsonElement(jsonObj.get("disk_autoscaling"));
      // validate the required field `config_parameters`
      Mysql.validateJsonElement(jsonObj.get("config_parameters"));
      // ensure the json data is an array
      if (!jsonObj.get("replica_list").isJsonArray()) {
        throw new IllegalArgumentException(String.format("Expected the field `replica_list` to be an array in the JSON string but got `%s`", jsonObj.get("replica_list").toString()));
      }

      JsonArray jsonArrayreplicaList = jsonObj.getAsJsonArray("replica_list");
      // validate the required field `replica_list` (array)
      for (int i = 0; i < jsonArrayreplicaList.size(); i++) {
        DatabaseClusterReplicaListInner.validateJsonElement(jsonArrayreplicaList.get(i));
      };
      // ensure the json data is an array
      if (!jsonObj.get("domains").isJsonArray()) {
        throw new IllegalArgumentException(String.format("Expected the field `domains` to be an array in the JSON string but got `%s`", jsonObj.get("domains").toString()));
      }

      JsonArray jsonArraydomains = jsonObj.getAsJsonArray("domains");
      // validate the required field `domains` (array)
      for (int i = 0; i < jsonArraydomains.size(); i++) {
        DatabaseClusterDomainsInner.validateJsonElement(jsonArraydomains.get(i));
      };
      // ensure the json data is an array
      if (!jsonObj.get("child_services").isJsonArray()) {
        throw new IllegalArgumentException(String.format("Expected the field `child_services` to be an array in the JSON string but got `%s`", jsonObj.get("child_services").toString()));
      }

      JsonArray jsonArraychildServices = jsonObj.getAsJsonArray("child_services");
      // validate the required field `child_services` (array)
      for (int i = 0; i < jsonArraychildServices.size(); i++) {
        DatabaseClusterChildServicesInner.validateJsonElement(jsonArraychildServices.get(i));
      };
      // ensure the json data is an array
      if (!jsonObj.get("parent_services").isJsonArray()) {
        throw new IllegalArgumentException(String.format("Expected the field `parent_services` to be an array in the JSON string but got `%s`", jsonObj.get("parent_services").toString()));
      }

      JsonArray jsonArrayparentServices = jsonObj.getAsJsonArray("parent_services");
      // validate the required field `parent_services` (array)
      for (int i = 0; i < jsonArrayparentServices.size(); i++) {
        DatabaseClusterParentServicesInner.validateJsonElement(jsonArrayparentServices.get(i));
      };
      // validate the required field `maintenance_slot`
      DatabaseClusterMaintenanceSlot.validateJsonElement(jsonObj.get("maintenance_slot"));
  }

  public static class CustomTypeAdapterFactory implements TypeAdapterFactory {
    @SuppressWarnings("unchecked")
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
       if (!DatabaseCluster.class.isAssignableFrom(type.getRawType())) {
         return null; // this class only serializes 'DatabaseCluster' and its subtypes
       }
       final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);
       final TypeAdapter<DatabaseCluster> thisAdapter
                        = gson.getDelegateAdapter(this, TypeToken.get(DatabaseCluster.class));

       return (TypeAdapter<T>) new TypeAdapter<DatabaseCluster>() {
           @Override
           public void write(JsonWriter out, DatabaseCluster value) throws IOException {
             JsonObject obj = thisAdapter.toJsonTree(value).getAsJsonObject();
             elementAdapter.write(out, obj);
           }

           @Override
           public DatabaseCluster read(JsonReader in) throws IOException {
             JsonElement jsonElement = elementAdapter.read(in);
             validateJsonElement(jsonElement);
             return thisAdapter.fromJsonTree(jsonElement);
           }

       }.nullSafe();
    }
  }

 /**
  * Create an instance of DatabaseCluster given an JSON string
  *
  * @param jsonString JSON string
  * @return An instance of DatabaseCluster
  * @throws IOException if the JSON string is invalid with respect to DatabaseCluster
  */
  public static DatabaseCluster fromJson(String jsonString) throws IOException {
    return JSON.getGson().fromJson(jsonString, DatabaseCluster.class);
  }

 /**
  * Convert an instance of DatabaseCluster to an JSON string
  *
  * @return JSON string
  */
  public String toJson() {
    return JSON.getGson().toJson(this);
  }
}

