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
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import org.openapitools.client.model.AvailabilityZone;
import org.openapitools.client.model.VdsDisksInner;
import org.openapitools.client.model.VdsImage;
import org.openapitools.client.model.VdsNetworksInner;
import org.openapitools.client.model.VdsOs;
import org.openapitools.client.model.VdsSoftware;

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
 * Сервер
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2025-10-08T11:03:53.274930Z[Etc/UTC]")
public class Vds {
  public static final String SERIALIZED_NAME_ID = "id";
  @SerializedName(SERIALIZED_NAME_ID)
  private BigDecimal id;

  public static final String SERIALIZED_NAME_NAME = "name";
  @SerializedName(SERIALIZED_NAME_NAME)
  private String name;

  public static final String SERIALIZED_NAME_COMMENT = "comment";
  @SerializedName(SERIALIZED_NAME_COMMENT)
  private String comment;

  public static final String SERIALIZED_NAME_CREATED_AT = "created_at";
  @SerializedName(SERIALIZED_NAME_CREATED_AT)
  private String createdAt;

  public static final String SERIALIZED_NAME_OS = "os";
  @SerializedName(SERIALIZED_NAME_OS)
  private VdsOs os;

  public static final String SERIALIZED_NAME_SOFTWARE = "software";
  @SerializedName(SERIALIZED_NAME_SOFTWARE)
  private VdsSoftware software;

  public static final String SERIALIZED_NAME_PRESET_ID = "preset_id";
  @SerializedName(SERIALIZED_NAME_PRESET_ID)
  private BigDecimal presetId;

  /**
   * Локация сервера.
   */
  @JsonAdapter(LocationEnum.Adapter.class)
  public enum LocationEnum {
    RU_1("ru-1"),
    
    RU_2("ru-2"),
    
    RU_3("ru-3"),
    
    PL_1("pl-1"),
    
    KZ_1("kz-1"),
    
    NL_1("nl-1");

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

  public static final String SERIALIZED_NAME_CONFIGURATOR_ID = "configurator_id";
  @SerializedName(SERIALIZED_NAME_CONFIGURATOR_ID)
  private BigDecimal configuratorId;

  /**
   * Режим загрузки ОС сервера.
   */
  @JsonAdapter(BootModeEnum.Adapter.class)
  public enum BootModeEnum {
    STD("std"),
    
    SINGLE("single"),
    
    CD("cd");

    private String value;

    BootModeEnum(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public static BootModeEnum fromValue(String value) {
      for (BootModeEnum b : BootModeEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }

    public static class Adapter extends TypeAdapter<BootModeEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final BootModeEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public BootModeEnum read(final JsonReader jsonReader) throws IOException {
        String value =  jsonReader.nextString();
        return BootModeEnum.fromValue(value);
      }
    }
  }

  public static final String SERIALIZED_NAME_BOOT_MODE = "boot_mode";
  @SerializedName(SERIALIZED_NAME_BOOT_MODE)
  private BootModeEnum bootMode;

  /**
   * Статус сервера.
   */
  @JsonAdapter(StatusEnum.Adapter.class)
  public enum StatusEnum {
    INSTALLING("installing"),
    
    SOFTWARE_INSTALL("software_install"),
    
    REINSTALLING("reinstalling"),
    
    ON("on"),
    
    OFF("off"),
    
    TURNING_ON("turning_on"),
    
    TURNING_OFF("turning_off"),
    
    HARD_TURNING_OFF("hard_turning_off"),
    
    REBOOTING("rebooting"),
    
    HARD_REBOOTING("hard_rebooting"),
    
    REMOVING("removing"),
    
    REMOVED("removed"),
    
    CLONING("cloning"),
    
    TRANSFER("transfer"),
    
    BLOCKED("blocked"),
    
    CONFIGURING("configuring"),
    
    NO_PAID("no_paid"),
    
    PERMANENT_BLOCKED("permanent_blocked");

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

  public static final String SERIALIZED_NAME_START_AT = "start_at";
  @SerializedName(SERIALIZED_NAME_START_AT)
  private OffsetDateTime startAt;

  public static final String SERIALIZED_NAME_IS_DDOS_GUARD = "is_ddos_guard";
  @SerializedName(SERIALIZED_NAME_IS_DDOS_GUARD)
  private Boolean isDdosGuard;

  public static final String SERIALIZED_NAME_IS_MASTER_SSH = "is_master_ssh";
  @SerializedName(SERIALIZED_NAME_IS_MASTER_SSH)
  private Boolean isMasterSsh;

  public static final String SERIALIZED_NAME_IS_DEDICATED_CPU = "is_dedicated_cpu";
  @SerializedName(SERIALIZED_NAME_IS_DEDICATED_CPU)
  private Boolean isDedicatedCpu;

  public static final String SERIALIZED_NAME_GPU = "gpu";
  @SerializedName(SERIALIZED_NAME_GPU)
  private BigDecimal gpu;

  public static final String SERIALIZED_NAME_CPU = "cpu";
  @SerializedName(SERIALIZED_NAME_CPU)
  private BigDecimal cpu;

  public static final String SERIALIZED_NAME_CPU_FREQUENCY = "cpu_frequency";
  @SerializedName(SERIALIZED_NAME_CPU_FREQUENCY)
  private String cpuFrequency;

  public static final String SERIALIZED_NAME_RAM = "ram";
  @SerializedName(SERIALIZED_NAME_RAM)
  private BigDecimal ram;

  public static final String SERIALIZED_NAME_DISKS = "disks";
  @SerializedName(SERIALIZED_NAME_DISKS)
  private List<VdsDisksInner> disks = new ArrayList<>();

  public static final String SERIALIZED_NAME_AVATAR_ID = "avatar_id";
  @Deprecated
  @SerializedName(SERIALIZED_NAME_AVATAR_ID)
  private String avatarId;

  public static final String SERIALIZED_NAME_AVATAR_LINK = "avatar_link";
  @SerializedName(SERIALIZED_NAME_AVATAR_LINK)
  private String avatarLink;

  public static final String SERIALIZED_NAME_VNC_PASS = "vnc_pass";
  @SerializedName(SERIALIZED_NAME_VNC_PASS)
  private String vncPass;

  public static final String SERIALIZED_NAME_ROOT_PASS = "root_pass";
  @SerializedName(SERIALIZED_NAME_ROOT_PASS)
  private String rootPass;

  public static final String SERIALIZED_NAME_IMAGE = "image";
  @SerializedName(SERIALIZED_NAME_IMAGE)
  private VdsImage image;

  public static final String SERIALIZED_NAME_NETWORKS = "networks";
  @SerializedName(SERIALIZED_NAME_NETWORKS)
  private List<VdsNetworksInner> networks = new ArrayList<>();

  public static final String SERIALIZED_NAME_CLOUD_INIT = "cloud_init";
  @SerializedName(SERIALIZED_NAME_CLOUD_INIT)
  private String cloudInit;

  public static final String SERIALIZED_NAME_IS_QEMU_AGENT = "is_qemu_agent";
  @SerializedName(SERIALIZED_NAME_IS_QEMU_AGENT)
  private Boolean isQemuAgent;

  public static final String SERIALIZED_NAME_AVAILABILITY_ZONE = "availability_zone";
  @SerializedName(SERIALIZED_NAME_AVAILABILITY_ZONE)
  private AvailabilityZone availabilityZone;

  public Vds() {
  }

  public Vds id(BigDecimal id) {
    
    this.id = id;
    return this;
  }

   /**
   * ID для каждого экземпляра сервера. Автоматически генерируется при создании.
   * @return id
  **/
  @javax.annotation.Nonnull
  public BigDecimal getId() {
    return id;
  }


  public void setId(BigDecimal id) {
    this.id = id;
  }


  public Vds name(String name) {
    
    this.name = name;
    return this;
  }

   /**
   * Удобочитаемое имя, установленное для сервера.
   * @return name
  **/
  @javax.annotation.Nonnull
  public String getName() {
    return name;
  }


  public void setName(String name) {
    this.name = name;
  }


  public Vds comment(String comment) {
    
    this.comment = comment;
    return this;
  }

   /**
   * Комментарий к серверу.
   * @return comment
  **/
  @javax.annotation.Nonnull
  public String getComment() {
    return comment;
  }


  public void setComment(String comment) {
    this.comment = comment;
  }


  public Vds createdAt(String createdAt) {
    
    this.createdAt = createdAt;
    return this;
  }

   /**
   * Дата создания сервера в формате ISO8061.
   * @return createdAt
  **/
  @javax.annotation.Nonnull
  public String getCreatedAt() {
    return createdAt;
  }


  public void setCreatedAt(String createdAt) {
    this.createdAt = createdAt;
  }


  public Vds os(VdsOs os) {
    
    this.os = os;
    return this;
  }

   /**
   * Get os
   * @return os
  **/
  @javax.annotation.Nonnull
  public VdsOs getOs() {
    return os;
  }


  public void setOs(VdsOs os) {
    this.os = os;
  }


  public Vds software(VdsSoftware software) {
    
    this.software = software;
    return this;
  }

   /**
   * Get software
   * @return software
  **/
  @javax.annotation.Nullable
  public VdsSoftware getSoftware() {
    return software;
  }


  public void setSoftware(VdsSoftware software) {
    this.software = software;
  }


  public Vds presetId(BigDecimal presetId) {
    
    this.presetId = presetId;
    return this;
  }

   /**
   * ID тарифа сервера.
   * @return presetId
  **/
  @javax.annotation.Nullable
  public BigDecimal getPresetId() {
    return presetId;
  }


  public void setPresetId(BigDecimal presetId) {
    this.presetId = presetId;
  }


  public Vds location(LocationEnum location) {
    
    this.location = location;
    return this;
  }

   /**
   * Локация сервера.
   * @return location
  **/
  @javax.annotation.Nonnull
  public LocationEnum getLocation() {
    return location;
  }


  public void setLocation(LocationEnum location) {
    this.location = location;
  }


  public Vds configuratorId(BigDecimal configuratorId) {
    
    this.configuratorId = configuratorId;
    return this;
  }

   /**
   * ID конфигуратора сервера.
   * @return configuratorId
  **/
  @javax.annotation.Nullable
  public BigDecimal getConfiguratorId() {
    return configuratorId;
  }


  public void setConfiguratorId(BigDecimal configuratorId) {
    this.configuratorId = configuratorId;
  }


  public Vds bootMode(BootModeEnum bootMode) {
    
    this.bootMode = bootMode;
    return this;
  }

   /**
   * Режим загрузки ОС сервера.
   * @return bootMode
  **/
  @javax.annotation.Nonnull
  public BootModeEnum getBootMode() {
    return bootMode;
  }


  public void setBootMode(BootModeEnum bootMode) {
    this.bootMode = bootMode;
  }


  public Vds status(StatusEnum status) {
    
    this.status = status;
    return this;
  }

   /**
   * Статус сервера.
   * @return status
  **/
  @javax.annotation.Nonnull
  public StatusEnum getStatus() {
    return status;
  }


  public void setStatus(StatusEnum status) {
    this.status = status;
  }


  public Vds startAt(OffsetDateTime startAt) {
    
    this.startAt = startAt;
    return this;
  }

   /**
   * Значение времени, указанное в комбинированном формате даты и времени ISO8601, которое представляет, когда был запущен сервер.
   * @return startAt
  **/
  @javax.annotation.Nullable
  public OffsetDateTime getStartAt() {
    return startAt;
  }


  public void setStartAt(OffsetDateTime startAt) {
    this.startAt = startAt;
  }


  public Vds isDdosGuard(Boolean isDdosGuard) {
    
    this.isDdosGuard = isDdosGuard;
    return this;
  }

   /**
   * Это логическое значение, которое показывает, включена ли защита от DDoS у данного сервера.
   * @return isDdosGuard
  **/
  @javax.annotation.Nonnull
  public Boolean getIsDdosGuard() {
    return isDdosGuard;
  }


  public void setIsDdosGuard(Boolean isDdosGuard) {
    this.isDdosGuard = isDdosGuard;
  }


  public Vds isMasterSsh(Boolean isMasterSsh) {
    
    this.isMasterSsh = isMasterSsh;
    return this;
  }

   /**
   * Это логическое значение, которое показывает, доступно ли подключение по SSH для поддержки.
   * @return isMasterSsh
  **/
  @javax.annotation.Nonnull
  public Boolean getIsMasterSsh() {
    return isMasterSsh;
  }


  public void setIsMasterSsh(Boolean isMasterSsh) {
    this.isMasterSsh = isMasterSsh;
  }


  public Vds isDedicatedCpu(Boolean isDedicatedCpu) {
    
    this.isDedicatedCpu = isDedicatedCpu;
    return this;
  }

   /**
   * Это логическое значение, которое показывает, является ли CPU выделенным.
   * @return isDedicatedCpu
  **/
  @javax.annotation.Nonnull
  public Boolean getIsDedicatedCpu() {
    return isDedicatedCpu;
  }


  public void setIsDedicatedCpu(Boolean isDedicatedCpu) {
    this.isDedicatedCpu = isDedicatedCpu;
  }


  public Vds gpu(BigDecimal gpu) {
    
    this.gpu = gpu;
    return this;
  }

   /**
   * Количество видеокарт сервера.
   * @return gpu
  **/
  @javax.annotation.Nonnull
  public BigDecimal getGpu() {
    return gpu;
  }


  public void setGpu(BigDecimal gpu) {
    this.gpu = gpu;
  }


  public Vds cpu(BigDecimal cpu) {
    
    this.cpu = cpu;
    return this;
  }

   /**
   * Количество ядер процессора сервера.
   * @return cpu
  **/
  @javax.annotation.Nonnull
  public BigDecimal getCpu() {
    return cpu;
  }


  public void setCpu(BigDecimal cpu) {
    this.cpu = cpu;
  }


  public Vds cpuFrequency(String cpuFrequency) {
    
    this.cpuFrequency = cpuFrequency;
    return this;
  }

   /**
   * Частота ядер процессора сервера.
   * @return cpuFrequency
  **/
  @javax.annotation.Nonnull
  public String getCpuFrequency() {
    return cpuFrequency;
  }


  public void setCpuFrequency(String cpuFrequency) {
    this.cpuFrequency = cpuFrequency;
  }


  public Vds ram(BigDecimal ram) {
    
    this.ram = ram;
    return this;
  }

   /**
   * Размер (в Мб) ОЗУ сервера.
   * @return ram
  **/
  @javax.annotation.Nonnull
  public BigDecimal getRam() {
    return ram;
  }


  public void setRam(BigDecimal ram) {
    this.ram = ram;
  }


  public Vds disks(List<VdsDisksInner> disks) {
    
    this.disks = disks;
    return this;
  }

  public Vds addDisksItem(VdsDisksInner disksItem) {
    if (this.disks == null) {
      this.disks = new ArrayList<>();
    }
    this.disks.add(disksItem);
    return this;
  }

   /**
   * Список дисков сервера.
   * @return disks
  **/
  @javax.annotation.Nonnull
  public List<VdsDisksInner> getDisks() {
    return disks;
  }


  public void setDisks(List<VdsDisksInner> disks) {
    this.disks = disks;
  }


  @Deprecated
  public Vds avatarId(String avatarId) {
    
    this.avatarId = avatarId;
    return this;
  }

   /**
   * ID аватара сервера.
   * @return avatarId
   * @deprecated
  **/
  @Deprecated
  @javax.annotation.Nullable
  public String getAvatarId() {
    return avatarId;
  }


  @Deprecated
  public void setAvatarId(String avatarId) {
    this.avatarId = avatarId;
  }


  public Vds avatarLink(String avatarLink) {
    
    this.avatarLink = avatarLink;
    return this;
  }

   /**
   * Ссылка на аватар сервера.
   * @return avatarLink
  **/
  @javax.annotation.Nullable
  public String getAvatarLink() {
    return avatarLink;
  }


  public void setAvatarLink(String avatarLink) {
    this.avatarLink = avatarLink;
  }


  public Vds vncPass(String vncPass) {
    
    this.vncPass = vncPass;
    return this;
  }

   /**
   * Пароль от VNC.
   * @return vncPass
  **/
  @javax.annotation.Nonnull
  public String getVncPass() {
    return vncPass;
  }


  public void setVncPass(String vncPass) {
    this.vncPass = vncPass;
  }


  public Vds rootPass(String rootPass) {
    
    this.rootPass = rootPass;
    return this;
  }

   /**
   * Пароль root сервера или пароль Администратора для серверов Windows.
   * @return rootPass
  **/
  @javax.annotation.Nullable
  public String getRootPass() {
    return rootPass;
  }


  public void setRootPass(String rootPass) {
    this.rootPass = rootPass;
  }


  public Vds image(VdsImage image) {
    
    this.image = image;
    return this;
  }

   /**
   * Get image
   * @return image
  **/
  @javax.annotation.Nullable
  public VdsImage getImage() {
    return image;
  }


  public void setImage(VdsImage image) {
    this.image = image;
  }


  public Vds networks(List<VdsNetworksInner> networks) {
    
    this.networks = networks;
    return this;
  }

  public Vds addNetworksItem(VdsNetworksInner networksItem) {
    if (this.networks == null) {
      this.networks = new ArrayList<>();
    }
    this.networks.add(networksItem);
    return this;
  }

   /**
   * Список сетей сервера.
   * @return networks
  **/
  @javax.annotation.Nonnull
  public List<VdsNetworksInner> getNetworks() {
    return networks;
  }


  public void setNetworks(List<VdsNetworksInner> networks) {
    this.networks = networks;
  }


  public Vds cloudInit(String cloudInit) {
    
    this.cloudInit = cloudInit;
    return this;
  }

   /**
   * Cloud-init скрипт.
   * @return cloudInit
  **/
  @javax.annotation.Nullable
  public String getCloudInit() {
    return cloudInit;
  }


  public void setCloudInit(String cloudInit) {
    this.cloudInit = cloudInit;
  }


  public Vds isQemuAgent(Boolean isQemuAgent) {
    
    this.isQemuAgent = isQemuAgent;
    return this;
  }

   /**
   * Это логическое значение, которое показывает, включен ли QEMU-agent на сервере.
   * @return isQemuAgent
  **/
  @javax.annotation.Nonnull
  public Boolean getIsQemuAgent() {
    return isQemuAgent;
  }


  public void setIsQemuAgent(Boolean isQemuAgent) {
    this.isQemuAgent = isQemuAgent;
  }


  public Vds availabilityZone(AvailabilityZone availabilityZone) {
    
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
    Vds vds = (Vds) o;
    return Objects.equals(this.id, vds.id) &&
        Objects.equals(this.name, vds.name) &&
        Objects.equals(this.comment, vds.comment) &&
        Objects.equals(this.createdAt, vds.createdAt) &&
        Objects.equals(this.os, vds.os) &&
        Objects.equals(this.software, vds.software) &&
        Objects.equals(this.presetId, vds.presetId) &&
        Objects.equals(this.location, vds.location) &&
        Objects.equals(this.configuratorId, vds.configuratorId) &&
        Objects.equals(this.bootMode, vds.bootMode) &&
        Objects.equals(this.status, vds.status) &&
        Objects.equals(this.startAt, vds.startAt) &&
        Objects.equals(this.isDdosGuard, vds.isDdosGuard) &&
        Objects.equals(this.isMasterSsh, vds.isMasterSsh) &&
        Objects.equals(this.isDedicatedCpu, vds.isDedicatedCpu) &&
        Objects.equals(this.gpu, vds.gpu) &&
        Objects.equals(this.cpu, vds.cpu) &&
        Objects.equals(this.cpuFrequency, vds.cpuFrequency) &&
        Objects.equals(this.ram, vds.ram) &&
        Objects.equals(this.disks, vds.disks) &&
        Objects.equals(this.avatarId, vds.avatarId) &&
        Objects.equals(this.avatarLink, vds.avatarLink) &&
        Objects.equals(this.vncPass, vds.vncPass) &&
        Objects.equals(this.rootPass, vds.rootPass) &&
        Objects.equals(this.image, vds.image) &&
        Objects.equals(this.networks, vds.networks) &&
        Objects.equals(this.cloudInit, vds.cloudInit) &&
        Objects.equals(this.isQemuAgent, vds.isQemuAgent) &&
        Objects.equals(this.availabilityZone, vds.availabilityZone);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, comment, createdAt, os, software, presetId, location, configuratorId, bootMode, status, startAt, isDdosGuard, isMasterSsh, isDedicatedCpu, gpu, cpu, cpuFrequency, ram, disks, avatarId, avatarLink, vncPass, rootPass, image, networks, cloudInit, isQemuAgent, availabilityZone);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Vds {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    comment: ").append(toIndentedString(comment)).append("\n");
    sb.append("    createdAt: ").append(toIndentedString(createdAt)).append("\n");
    sb.append("    os: ").append(toIndentedString(os)).append("\n");
    sb.append("    software: ").append(toIndentedString(software)).append("\n");
    sb.append("    presetId: ").append(toIndentedString(presetId)).append("\n");
    sb.append("    location: ").append(toIndentedString(location)).append("\n");
    sb.append("    configuratorId: ").append(toIndentedString(configuratorId)).append("\n");
    sb.append("    bootMode: ").append(toIndentedString(bootMode)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    startAt: ").append(toIndentedString(startAt)).append("\n");
    sb.append("    isDdosGuard: ").append(toIndentedString(isDdosGuard)).append("\n");
    sb.append("    isMasterSsh: ").append(toIndentedString(isMasterSsh)).append("\n");
    sb.append("    isDedicatedCpu: ").append(toIndentedString(isDedicatedCpu)).append("\n");
    sb.append("    gpu: ").append(toIndentedString(gpu)).append("\n");
    sb.append("    cpu: ").append(toIndentedString(cpu)).append("\n");
    sb.append("    cpuFrequency: ").append(toIndentedString(cpuFrequency)).append("\n");
    sb.append("    ram: ").append(toIndentedString(ram)).append("\n");
    sb.append("    disks: ").append(toIndentedString(disks)).append("\n");
    sb.append("    avatarId: ").append(toIndentedString(avatarId)).append("\n");
    sb.append("    avatarLink: ").append(toIndentedString(avatarLink)).append("\n");
    sb.append("    vncPass: ").append(toIndentedString(vncPass)).append("\n");
    sb.append("    rootPass: ").append(toIndentedString(rootPass)).append("\n");
    sb.append("    image: ").append(toIndentedString(image)).append("\n");
    sb.append("    networks: ").append(toIndentedString(networks)).append("\n");
    sb.append("    cloudInit: ").append(toIndentedString(cloudInit)).append("\n");
    sb.append("    isQemuAgent: ").append(toIndentedString(isQemuAgent)).append("\n");
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
    openapiFields.add("name");
    openapiFields.add("comment");
    openapiFields.add("created_at");
    openapiFields.add("os");
    openapiFields.add("software");
    openapiFields.add("preset_id");
    openapiFields.add("location");
    openapiFields.add("configurator_id");
    openapiFields.add("boot_mode");
    openapiFields.add("status");
    openapiFields.add("start_at");
    openapiFields.add("is_ddos_guard");
    openapiFields.add("is_master_ssh");
    openapiFields.add("is_dedicated_cpu");
    openapiFields.add("gpu");
    openapiFields.add("cpu");
    openapiFields.add("cpu_frequency");
    openapiFields.add("ram");
    openapiFields.add("disks");
    openapiFields.add("avatar_id");
    openapiFields.add("avatar_link");
    openapiFields.add("vnc_pass");
    openapiFields.add("root_pass");
    openapiFields.add("image");
    openapiFields.add("networks");
    openapiFields.add("cloud_init");
    openapiFields.add("is_qemu_agent");
    openapiFields.add("availability_zone");

    // a set of required properties/fields (JSON key names)
    openapiRequiredFields = new HashSet<String>();
    openapiRequiredFields.add("id");
    openapiRequiredFields.add("name");
    openapiRequiredFields.add("comment");
    openapiRequiredFields.add("created_at");
    openapiRequiredFields.add("os");
    openapiRequiredFields.add("software");
    openapiRequiredFields.add("preset_id");
    openapiRequiredFields.add("location");
    openapiRequiredFields.add("configurator_id");
    openapiRequiredFields.add("boot_mode");
    openapiRequiredFields.add("status");
    openapiRequiredFields.add("start_at");
    openapiRequiredFields.add("is_ddos_guard");
    openapiRequiredFields.add("is_master_ssh");
    openapiRequiredFields.add("is_dedicated_cpu");
    openapiRequiredFields.add("gpu");
    openapiRequiredFields.add("cpu");
    openapiRequiredFields.add("cpu_frequency");
    openapiRequiredFields.add("ram");
    openapiRequiredFields.add("disks");
    openapiRequiredFields.add("avatar_id");
    openapiRequiredFields.add("avatar_link");
    openapiRequiredFields.add("vnc_pass");
    openapiRequiredFields.add("root_pass");
    openapiRequiredFields.add("image");
    openapiRequiredFields.add("networks");
    openapiRequiredFields.add("cloud_init");
    openapiRequiredFields.add("is_qemu_agent");
    openapiRequiredFields.add("availability_zone");
  }

 /**
  * Validates the JSON Element and throws an exception if issues found
  *
  * @param jsonElement JSON Element
  * @throws IOException if the JSON Element is invalid with respect to Vds
  */
  public static void validateJsonElement(JsonElement jsonElement) throws IOException {
      if (jsonElement == null) {
        if (!Vds.openapiRequiredFields.isEmpty()) { // has required fields but JSON element is null
          throw new IllegalArgumentException(String.format("The required field(s) %s in Vds is not found in the empty JSON string", Vds.openapiRequiredFields.toString()));
        }
      }

      Set<Entry<String, JsonElement>> entries = jsonElement.getAsJsonObject().entrySet();
      // check to see if the JSON string contains additional fields
      for (Entry<String, JsonElement> entry : entries) {
        if (!Vds.openapiFields.contains(entry.getKey())) {
          throw new IllegalArgumentException(String.format("The field `%s` in the JSON string is not defined in the `Vds` properties. JSON: %s", entry.getKey(), jsonElement.toString()));
        }
      }

      // check to make sure all required properties/fields are present in the JSON string
      for (String requiredField : Vds.openapiRequiredFields) {
        if (jsonElement.getAsJsonObject().get(requiredField) == null) {
          throw new IllegalArgumentException(String.format("The required field `%s` is not found in the JSON string: %s", requiredField, jsonElement.toString()));
        }
      }
        JsonObject jsonObj = jsonElement.getAsJsonObject();
      if (!jsonObj.get("name").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `name` to be a primitive type in the JSON string but got `%s`", jsonObj.get("name").toString()));
      }
      if (!jsonObj.get("comment").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `comment` to be a primitive type in the JSON string but got `%s`", jsonObj.get("comment").toString()));
      }
      if (!jsonObj.get("created_at").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `created_at` to be a primitive type in the JSON string but got `%s`", jsonObj.get("created_at").toString()));
      }
      // validate the required field `os`
      VdsOs.validateJsonElement(jsonObj.get("os"));
      // validate the required field `software`
      VdsSoftware.validateJsonElement(jsonObj.get("software"));
      if (!jsonObj.get("location").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `location` to be a primitive type in the JSON string but got `%s`", jsonObj.get("location").toString()));
      }
      if (!jsonObj.get("boot_mode").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `boot_mode` to be a primitive type in the JSON string but got `%s`", jsonObj.get("boot_mode").toString()));
      }
      if (!jsonObj.get("status").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `status` to be a primitive type in the JSON string but got `%s`", jsonObj.get("status").toString()));
      }
      if (!jsonObj.get("cpu_frequency").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `cpu_frequency` to be a primitive type in the JSON string but got `%s`", jsonObj.get("cpu_frequency").toString()));
      }
      // ensure the json data is an array
      if (!jsonObj.get("disks").isJsonArray()) {
        throw new IllegalArgumentException(String.format("Expected the field `disks` to be an array in the JSON string but got `%s`", jsonObj.get("disks").toString()));
      }

      JsonArray jsonArraydisks = jsonObj.getAsJsonArray("disks");
      // validate the required field `disks` (array)
      for (int i = 0; i < jsonArraydisks.size(); i++) {
        VdsDisksInner.validateJsonElement(jsonArraydisks.get(i));
      };
      if ((jsonObj.get("avatar_id") != null && !jsonObj.get("avatar_id").isJsonNull()) && !jsonObj.get("avatar_id").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `avatar_id` to be a primitive type in the JSON string but got `%s`", jsonObj.get("avatar_id").toString()));
      }
      if ((jsonObj.get("avatar_link") != null && !jsonObj.get("avatar_link").isJsonNull()) && !jsonObj.get("avatar_link").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `avatar_link` to be a primitive type in the JSON string but got `%s`", jsonObj.get("avatar_link").toString()));
      }
      if (!jsonObj.get("vnc_pass").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `vnc_pass` to be a primitive type in the JSON string but got `%s`", jsonObj.get("vnc_pass").toString()));
      }
      if ((jsonObj.get("root_pass") != null && !jsonObj.get("root_pass").isJsonNull()) && !jsonObj.get("root_pass").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `root_pass` to be a primitive type in the JSON string but got `%s`", jsonObj.get("root_pass").toString()));
      }
      // validate the required field `image`
      VdsImage.validateJsonElement(jsonObj.get("image"));
      // ensure the json data is an array
      if (!jsonObj.get("networks").isJsonArray()) {
        throw new IllegalArgumentException(String.format("Expected the field `networks` to be an array in the JSON string but got `%s`", jsonObj.get("networks").toString()));
      }

      JsonArray jsonArraynetworks = jsonObj.getAsJsonArray("networks");
      // validate the required field `networks` (array)
      for (int i = 0; i < jsonArraynetworks.size(); i++) {
        VdsNetworksInner.validateJsonElement(jsonArraynetworks.get(i));
      };
      if ((jsonObj.get("cloud_init") != null && !jsonObj.get("cloud_init").isJsonNull()) && !jsonObj.get("cloud_init").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `cloud_init` to be a primitive type in the JSON string but got `%s`", jsonObj.get("cloud_init").toString()));
      }
  }

  public static class CustomTypeAdapterFactory implements TypeAdapterFactory {
    @SuppressWarnings("unchecked")
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
       if (!Vds.class.isAssignableFrom(type.getRawType())) {
         return null; // this class only serializes 'Vds' and its subtypes
       }
       final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);
       final TypeAdapter<Vds> thisAdapter
                        = gson.getDelegateAdapter(this, TypeToken.get(Vds.class));

       return (TypeAdapter<T>) new TypeAdapter<Vds>() {
           @Override
           public void write(JsonWriter out, Vds value) throws IOException {
             JsonObject obj = thisAdapter.toJsonTree(value).getAsJsonObject();
             elementAdapter.write(out, obj);
           }

           @Override
           public Vds read(JsonReader in) throws IOException {
             JsonElement jsonElement = elementAdapter.read(in);
             validateJsonElement(jsonElement);
             return thisAdapter.fromJsonTree(jsonElement);
           }

       }.nullSafe();
    }
  }

 /**
  * Create an instance of Vds given an JSON string
  *
  * @param jsonString JSON string
  * @return An instance of Vds
  * @throws IOException if the JSON string is invalid with respect to Vds
  */
  public static Vds fromJson(String jsonString) throws IOException {
    return JSON.getGson().fromJson(jsonString, Vds.class);
  }

 /**
  * Convert an instance of Vds to an JSON string
  *
  * @return JSON string
  */
  public String toJson() {
    return JSON.getGson().toJson(this);
  }
}

