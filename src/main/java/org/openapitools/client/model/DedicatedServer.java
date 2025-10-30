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
 * Выделенный сервер
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2025-10-30T12:35:37.057389Z[Etc/UTC]")
public class DedicatedServer {
  public static final String SERIALIZED_NAME_ID = "id";
  @SerializedName(SERIALIZED_NAME_ID)
  private BigDecimal id;

  public static final String SERIALIZED_NAME_CPU_DESCRIPTION = "cpu_description";
  @SerializedName(SERIALIZED_NAME_CPU_DESCRIPTION)
  private String cpuDescription;

  public static final String SERIALIZED_NAME_HDD_DESCRIPTION = "hdd_description";
  @SerializedName(SERIALIZED_NAME_HDD_DESCRIPTION)
  private String hddDescription;

  public static final String SERIALIZED_NAME_RAM_DESCRIPTION = "ram_description";
  @SerializedName(SERIALIZED_NAME_RAM_DESCRIPTION)
  private String ramDescription;

  public static final String SERIALIZED_NAME_CREATED_AT = "created_at";
  @SerializedName(SERIALIZED_NAME_CREATED_AT)
  private OffsetDateTime createdAt;

  public static final String SERIALIZED_NAME_IP = "ip";
  @SerializedName(SERIALIZED_NAME_IP)
  private String ip;

  public static final String SERIALIZED_NAME_IPMI_IP = "ipmi_ip";
  @SerializedName(SERIALIZED_NAME_IPMI_IP)
  private String ipmiIp;

  public static final String SERIALIZED_NAME_IPMI_LOGIN = "ipmi_login";
  @SerializedName(SERIALIZED_NAME_IPMI_LOGIN)
  private String ipmiLogin;

  public static final String SERIALIZED_NAME_IPMI_PASSWORD = "ipmi_password";
  @SerializedName(SERIALIZED_NAME_IPMI_PASSWORD)
  private String ipmiPassword;

  public static final String SERIALIZED_NAME_IPV6 = "ipv6";
  @SerializedName(SERIALIZED_NAME_IPV6)
  private String ipv6;

  public static final String SERIALIZED_NAME_NODE_ID = "node_id";
  @SerializedName(SERIALIZED_NAME_NODE_ID)
  private BigDecimal nodeId;

  public static final String SERIALIZED_NAME_NAME = "name";
  @SerializedName(SERIALIZED_NAME_NAME)
  private String name;

  public static final String SERIALIZED_NAME_COMMENT = "comment";
  @SerializedName(SERIALIZED_NAME_COMMENT)
  private String comment;

  public static final String SERIALIZED_NAME_VNC_PASS = "vnc_pass";
  @SerializedName(SERIALIZED_NAME_VNC_PASS)
  private String vncPass;

  /**
   * Строка состояния, указывающая состояние выделенного сервера. Может быть «installing», «installed», «on» или «off».
   */
  @JsonAdapter(StatusEnum.Adapter.class)
  public enum StatusEnum {
    INSTALLING("installing"),
    
    INSTALLED("installed"),
    
    ON("on"),
    
    OFF("off");

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

  public static final String SERIALIZED_NAME_OS_ID = "os_id";
  @SerializedName(SERIALIZED_NAME_OS_ID)
  private BigDecimal osId;

  public static final String SERIALIZED_NAME_CP_ID = "cp_id";
  @SerializedName(SERIALIZED_NAME_CP_ID)
  private BigDecimal cpId;

  public static final String SERIALIZED_NAME_BANDWIDTH_ID = "bandwidth_id";
  @SerializedName(SERIALIZED_NAME_BANDWIDTH_ID)
  private BigDecimal bandwidthId;

  public static final String SERIALIZED_NAME_NETWORK_DRIVE_ID = "network_drive_id";
  @SerializedName(SERIALIZED_NAME_NETWORK_DRIVE_ID)
  private List<BigDecimal> networkDriveId;

  public static final String SERIALIZED_NAME_ADDITIONAL_IP_ADDR_ID = "additional_ip_addr_id";
  @SerializedName(SERIALIZED_NAME_ADDITIONAL_IP_ADDR_ID)
  private List<BigDecimal> additionalIpAddrId;

  public static final String SERIALIZED_NAME_PLAN_ID = "plan_id";
  @SerializedName(SERIALIZED_NAME_PLAN_ID)
  private BigDecimal planId;

  public static final String SERIALIZED_NAME_PRICE = "price";
  @SerializedName(SERIALIZED_NAME_PRICE)
  private BigDecimal price;

  /**
   * Локация сервера.
   */
  @JsonAdapter(LocationEnum.Adapter.class)
  public enum LocationEnum {
    RU_1("ru-1"),
    
    PL_1("pl-1"),
    
    KZ_1("kz-1"),
    
    NL_1("nl-1"),
    
    TR_1("tr-1"),
    
    US_2("us-2"),
    
    DE_1("de-1"),
    
    FI_1("fi-1");

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

  public static final String SERIALIZED_NAME_AUTOINSTALL_READY = "autoinstall_ready";
  @SerializedName(SERIALIZED_NAME_AUTOINSTALL_READY)
  private BigDecimal autoinstallReady;

  public static final String SERIALIZED_NAME_PASSWORD = "password";
  @SerializedName(SERIALIZED_NAME_PASSWORD)
  private String password;

  public static final String SERIALIZED_NAME_AVATAR_LINK = "avatar_link";
  @SerializedName(SERIALIZED_NAME_AVATAR_LINK)
  private String avatarLink;

  public static final String SERIALIZED_NAME_IS_PRE_INSTALLED = "is_pre_installed";
  @SerializedName(SERIALIZED_NAME_IS_PRE_INSTALLED)
  private Boolean isPreInstalled;

  public static final String SERIALIZED_NAME_PRESET_ID = "preset_id";
  @SerializedName(SERIALIZED_NAME_PRESET_ID)
  private Integer presetId;

  public static final String SERIALIZED_NAME_PROJECT_ID = "project_id";
  @SerializedName(SERIALIZED_NAME_PROJECT_ID)
  private Integer projectId;

  public DedicatedServer() {
  }

  public DedicatedServer id(BigDecimal id) {
    
    this.id = id;
    return this;
  }

   /**
   * ID для каждого экземпляра выделенного сервера. Автоматически генерируется при создании.
   * @return id
  **/
  @javax.annotation.Nonnull
  public BigDecimal getId() {
    return id;
  }


  public void setId(BigDecimal id) {
    this.id = id;
  }


  public DedicatedServer cpuDescription(String cpuDescription) {
    
    this.cpuDescription = cpuDescription;
    return this;
  }

   /**
   * Описание параметров процессора выделенного сервера.
   * @return cpuDescription
  **/
  @javax.annotation.Nonnull
  public String getCpuDescription() {
    return cpuDescription;
  }


  public void setCpuDescription(String cpuDescription) {
    this.cpuDescription = cpuDescription;
  }


  public DedicatedServer hddDescription(String hddDescription) {
    
    this.hddDescription = hddDescription;
    return this;
  }

   /**
   * Описание параметров жёсткого диска выделенного сервера.
   * @return hddDescription
  **/
  @javax.annotation.Nonnull
  public String getHddDescription() {
    return hddDescription;
  }


  public void setHddDescription(String hddDescription) {
    this.hddDescription = hddDescription;
  }


  public DedicatedServer ramDescription(String ramDescription) {
    
    this.ramDescription = ramDescription;
    return this;
  }

   /**
   * Описание ОЗУ выделенного сервера.
   * @return ramDescription
  **/
  @javax.annotation.Nonnull
  public String getRamDescription() {
    return ramDescription;
  }


  public void setRamDescription(String ramDescription) {
    this.ramDescription = ramDescription;
  }


  public DedicatedServer createdAt(OffsetDateTime createdAt) {
    
    this.createdAt = createdAt;
    return this;
  }

   /**
   * Значение времени, указанное в комбинированном формате даты и времени ISO8601, которое представляет, когда был создан выделенный сервер.
   * @return createdAt
  **/
  @javax.annotation.Nonnull
  public OffsetDateTime getCreatedAt() {
    return createdAt;
  }


  public void setCreatedAt(OffsetDateTime createdAt) {
    this.createdAt = createdAt;
  }


  public DedicatedServer ip(String ip) {
    
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


  public DedicatedServer ipmiIp(String ipmiIp) {
    
    this.ipmiIp = ipmiIp;
    return this;
  }

   /**
   * IP-адрес сетевого интерфейса IPMI.
   * @return ipmiIp
  **/
  @javax.annotation.Nullable
  public String getIpmiIp() {
    return ipmiIp;
  }


  public void setIpmiIp(String ipmiIp) {
    this.ipmiIp = ipmiIp;
  }


  public DedicatedServer ipmiLogin(String ipmiLogin) {
    
    this.ipmiLogin = ipmiLogin;
    return this;
  }

   /**
   * Логин, используемый для входа в IPMI-консоль.
   * @return ipmiLogin
  **/
  @javax.annotation.Nullable
  public String getIpmiLogin() {
    return ipmiLogin;
  }


  public void setIpmiLogin(String ipmiLogin) {
    this.ipmiLogin = ipmiLogin;
  }


  public DedicatedServer ipmiPassword(String ipmiPassword) {
    
    this.ipmiPassword = ipmiPassword;
    return this;
  }

   /**
   * Пароль, используемый для входа в IPMI-консоль.
   * @return ipmiPassword
  **/
  @javax.annotation.Nullable
  public String getIpmiPassword() {
    return ipmiPassword;
  }


  public void setIpmiPassword(String ipmiPassword) {
    this.ipmiPassword = ipmiPassword;
  }


  public DedicatedServer ipv6(String ipv6) {
    
    this.ipv6 = ipv6;
    return this;
  }

   /**
   * IP-адрес сетевого интерфейса IPv6.
   * @return ipv6
  **/
  @javax.annotation.Nullable
  public String getIpv6() {
    return ipv6;
  }


  public void setIpv6(String ipv6) {
    this.ipv6 = ipv6;
  }


  public DedicatedServer nodeId(BigDecimal nodeId) {
    
    this.nodeId = nodeId;
    return this;
  }

   /**
   * Внутренний дополнительный ID сервера.
   * @return nodeId
  **/
  @javax.annotation.Nullable
  public BigDecimal getNodeId() {
    return nodeId;
  }


  public void setNodeId(BigDecimal nodeId) {
    this.nodeId = nodeId;
  }


  public DedicatedServer name(String name) {
    
    this.name = name;
    return this;
  }

   /**
   * Удобочитаемое имя, установленное для выделенного сервера.
   * @return name
  **/
  @javax.annotation.Nonnull
  public String getName() {
    return name;
  }


  public void setName(String name) {
    this.name = name;
  }


  public DedicatedServer comment(String comment) {
    
    this.comment = comment;
    return this;
  }

   /**
   * Комментарий к выделенному серверу.
   * @return comment
  **/
  @javax.annotation.Nonnull
  public String getComment() {
    return comment;
  }


  public void setComment(String comment) {
    this.comment = comment;
  }


  public DedicatedServer vncPass(String vncPass) {
    
    this.vncPass = vncPass;
    return this;
  }

   /**
   * Пароль для подключения к VNC-консоли выделенного сервера.
   * @return vncPass
  **/
  @javax.annotation.Nullable
  public String getVncPass() {
    return vncPass;
  }


  public void setVncPass(String vncPass) {
    this.vncPass = vncPass;
  }


  public DedicatedServer status(StatusEnum status) {
    
    this.status = status;
    return this;
  }

   /**
   * Строка состояния, указывающая состояние выделенного сервера. Может быть «installing», «installed», «on» или «off».
   * @return status
  **/
  @javax.annotation.Nonnull
  public StatusEnum getStatus() {
    return status;
  }


  public void setStatus(StatusEnum status) {
    this.status = status;
  }


  public DedicatedServer osId(BigDecimal osId) {
    
    this.osId = osId;
    return this;
  }

   /**
   * ID операционной системы, установленной на выделенный сервер.
   * @return osId
  **/
  @javax.annotation.Nullable
  public BigDecimal getOsId() {
    return osId;
  }


  public void setOsId(BigDecimal osId) {
    this.osId = osId;
  }


  public DedicatedServer cpId(BigDecimal cpId) {
    
    this.cpId = cpId;
    return this;
  }

   /**
   * ID панели управления, установленной на выделенный сервер.
   * @return cpId
  **/
  @javax.annotation.Nullable
  public BigDecimal getCpId() {
    return cpId;
  }


  public void setCpId(BigDecimal cpId) {
    this.cpId = cpId;
  }


  public DedicatedServer bandwidthId(BigDecimal bandwidthId) {
    
    this.bandwidthId = bandwidthId;
    return this;
  }

   /**
   * ID интернет-канала, установленного на выделенный сервер.
   * @return bandwidthId
  **/
  @javax.annotation.Nullable
  public BigDecimal getBandwidthId() {
    return bandwidthId;
  }


  public void setBandwidthId(BigDecimal bandwidthId) {
    this.bandwidthId = bandwidthId;
  }


  public DedicatedServer networkDriveId(List<BigDecimal> networkDriveId) {
    
    this.networkDriveId = networkDriveId;
    return this;
  }

  public DedicatedServer addNetworkDriveIdItem(BigDecimal networkDriveIdItem) {
    if (this.networkDriveId == null) {
      this.networkDriveId = new ArrayList<>();
    }
    this.networkDriveId.add(networkDriveIdItem);
    return this;
  }

   /**
   * Массив уникальных ID сетевых дисков, подключенных к выделенному серверу.
   * @return networkDriveId
  **/
  @javax.annotation.Nullable
  public List<BigDecimal> getNetworkDriveId() {
    return networkDriveId;
  }


  public void setNetworkDriveId(List<BigDecimal> networkDriveId) {
    this.networkDriveId = networkDriveId;
  }


  public DedicatedServer additionalIpAddrId(List<BigDecimal> additionalIpAddrId) {
    
    this.additionalIpAddrId = additionalIpAddrId;
    return this;
  }

  public DedicatedServer addAdditionalIpAddrIdItem(BigDecimal additionalIpAddrIdItem) {
    if (this.additionalIpAddrId == null) {
      this.additionalIpAddrId = new ArrayList<>();
    }
    this.additionalIpAddrId.add(additionalIpAddrIdItem);
    return this;
  }

   /**
   * Массив уникальных ID дополнительных IP-адресов, подключенных к выделенному серверу.
   * @return additionalIpAddrId
  **/
  @javax.annotation.Nullable
  public List<BigDecimal> getAdditionalIpAddrId() {
    return additionalIpAddrId;
  }


  public void setAdditionalIpAddrId(List<BigDecimal> additionalIpAddrId) {
    this.additionalIpAddrId = additionalIpAddrId;
  }


  public DedicatedServer planId(BigDecimal planId) {
    
    this.planId = planId;
    return this;
  }

   /**
   * ID списка дополнительных услуг выделенного сервера.
   * @return planId
  **/
  @javax.annotation.Nullable
  public BigDecimal getPlanId() {
    return planId;
  }


  public void setPlanId(BigDecimal planId) {
    this.planId = planId;
  }


  public DedicatedServer price(BigDecimal price) {
    
    this.price = price;
    return this;
  }

   /**
   * Стоимость выделенного сервера.
   * @return price
  **/
  @javax.annotation.Nonnull
  public BigDecimal getPrice() {
    return price;
  }


  public void setPrice(BigDecimal price) {
    this.price = price;
  }


  public DedicatedServer location(LocationEnum location) {
    
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


  public DedicatedServer autoinstallReady(BigDecimal autoinstallReady) {
    
    this.autoinstallReady = autoinstallReady;
    return this;
  }

   /**
   * Количество готовых к автоматической выдаче серверов. Если значение равно 0, сервер будет установлен через инженеров.
   * @return autoinstallReady
  **/
  @javax.annotation.Nonnull
  public BigDecimal getAutoinstallReady() {
    return autoinstallReady;
  }


  public void setAutoinstallReady(BigDecimal autoinstallReady) {
    this.autoinstallReady = autoinstallReady;
  }


  public DedicatedServer password(String password) {
    
    this.password = password;
    return this;
  }

   /**
   * Пароль root сервера или пароль Администратора для серверов Windows.
   * @return password
  **/
  @javax.annotation.Nullable
  public String getPassword() {
    return password;
  }


  public void setPassword(String password) {
    this.password = password;
  }


  public DedicatedServer avatarLink(String avatarLink) {
    
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


  public DedicatedServer isPreInstalled(Boolean isPreInstalled) {
    
    this.isPreInstalled = isPreInstalled;
    return this;
  }

   /**
   * Это логическое значение, которое показывает, готов ли выделенный сервер к моментальной выдаче.
   * @return isPreInstalled
  **/
  @javax.annotation.Nonnull
  public Boolean getIsPreInstalled() {
    return isPreInstalled;
  }


  public void setIsPreInstalled(Boolean isPreInstalled) {
    this.isPreInstalled = isPreInstalled;
  }


  public DedicatedServer presetId(Integer presetId) {
    
    this.presetId = presetId;
    return this;
  }

   /**
   * ID тарифа сервера.
   * @return presetId
  **/
  @javax.annotation.Nonnull
  public Integer getPresetId() {
    return presetId;
  }


  public void setPresetId(Integer presetId) {
    this.presetId = presetId;
  }


  public DedicatedServer projectId(Integer projectId) {
    
    this.projectId = projectId;
    return this;
  }

   /**
   * ID проекта
   * @return projectId
  **/
  @javax.annotation.Nonnull
  public Integer getProjectId() {
    return projectId;
  }


  public void setProjectId(Integer projectId) {
    this.projectId = projectId;
  }



  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DedicatedServer dedicatedServer = (DedicatedServer) o;
    return Objects.equals(this.id, dedicatedServer.id) &&
        Objects.equals(this.cpuDescription, dedicatedServer.cpuDescription) &&
        Objects.equals(this.hddDescription, dedicatedServer.hddDescription) &&
        Objects.equals(this.ramDescription, dedicatedServer.ramDescription) &&
        Objects.equals(this.createdAt, dedicatedServer.createdAt) &&
        Objects.equals(this.ip, dedicatedServer.ip) &&
        Objects.equals(this.ipmiIp, dedicatedServer.ipmiIp) &&
        Objects.equals(this.ipmiLogin, dedicatedServer.ipmiLogin) &&
        Objects.equals(this.ipmiPassword, dedicatedServer.ipmiPassword) &&
        Objects.equals(this.ipv6, dedicatedServer.ipv6) &&
        Objects.equals(this.nodeId, dedicatedServer.nodeId) &&
        Objects.equals(this.name, dedicatedServer.name) &&
        Objects.equals(this.comment, dedicatedServer.comment) &&
        Objects.equals(this.vncPass, dedicatedServer.vncPass) &&
        Objects.equals(this.status, dedicatedServer.status) &&
        Objects.equals(this.osId, dedicatedServer.osId) &&
        Objects.equals(this.cpId, dedicatedServer.cpId) &&
        Objects.equals(this.bandwidthId, dedicatedServer.bandwidthId) &&
        Objects.equals(this.networkDriveId, dedicatedServer.networkDriveId) &&
        Objects.equals(this.additionalIpAddrId, dedicatedServer.additionalIpAddrId) &&
        Objects.equals(this.planId, dedicatedServer.planId) &&
        Objects.equals(this.price, dedicatedServer.price) &&
        Objects.equals(this.location, dedicatedServer.location) &&
        Objects.equals(this.autoinstallReady, dedicatedServer.autoinstallReady) &&
        Objects.equals(this.password, dedicatedServer.password) &&
        Objects.equals(this.avatarLink, dedicatedServer.avatarLink) &&
        Objects.equals(this.isPreInstalled, dedicatedServer.isPreInstalled) &&
        Objects.equals(this.presetId, dedicatedServer.presetId) &&
        Objects.equals(this.projectId, dedicatedServer.projectId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, cpuDescription, hddDescription, ramDescription, createdAt, ip, ipmiIp, ipmiLogin, ipmiPassword, ipv6, nodeId, name, comment, vncPass, status, osId, cpId, bandwidthId, networkDriveId, additionalIpAddrId, planId, price, location, autoinstallReady, password, avatarLink, isPreInstalled, presetId, projectId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DedicatedServer {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    cpuDescription: ").append(toIndentedString(cpuDescription)).append("\n");
    sb.append("    hddDescription: ").append(toIndentedString(hddDescription)).append("\n");
    sb.append("    ramDescription: ").append(toIndentedString(ramDescription)).append("\n");
    sb.append("    createdAt: ").append(toIndentedString(createdAt)).append("\n");
    sb.append("    ip: ").append(toIndentedString(ip)).append("\n");
    sb.append("    ipmiIp: ").append(toIndentedString(ipmiIp)).append("\n");
    sb.append("    ipmiLogin: ").append(toIndentedString(ipmiLogin)).append("\n");
    sb.append("    ipmiPassword: ").append(toIndentedString(ipmiPassword)).append("\n");
    sb.append("    ipv6: ").append(toIndentedString(ipv6)).append("\n");
    sb.append("    nodeId: ").append(toIndentedString(nodeId)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    comment: ").append(toIndentedString(comment)).append("\n");
    sb.append("    vncPass: ").append(toIndentedString(vncPass)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    osId: ").append(toIndentedString(osId)).append("\n");
    sb.append("    cpId: ").append(toIndentedString(cpId)).append("\n");
    sb.append("    bandwidthId: ").append(toIndentedString(bandwidthId)).append("\n");
    sb.append("    networkDriveId: ").append(toIndentedString(networkDriveId)).append("\n");
    sb.append("    additionalIpAddrId: ").append(toIndentedString(additionalIpAddrId)).append("\n");
    sb.append("    planId: ").append(toIndentedString(planId)).append("\n");
    sb.append("    price: ").append(toIndentedString(price)).append("\n");
    sb.append("    location: ").append(toIndentedString(location)).append("\n");
    sb.append("    autoinstallReady: ").append(toIndentedString(autoinstallReady)).append("\n");
    sb.append("    password: ").append(toIndentedString(password)).append("\n");
    sb.append("    avatarLink: ").append(toIndentedString(avatarLink)).append("\n");
    sb.append("    isPreInstalled: ").append(toIndentedString(isPreInstalled)).append("\n");
    sb.append("    presetId: ").append(toIndentedString(presetId)).append("\n");
    sb.append("    projectId: ").append(toIndentedString(projectId)).append("\n");
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
    openapiFields.add("cpu_description");
    openapiFields.add("hdd_description");
    openapiFields.add("ram_description");
    openapiFields.add("created_at");
    openapiFields.add("ip");
    openapiFields.add("ipmi_ip");
    openapiFields.add("ipmi_login");
    openapiFields.add("ipmi_password");
    openapiFields.add("ipv6");
    openapiFields.add("node_id");
    openapiFields.add("name");
    openapiFields.add("comment");
    openapiFields.add("vnc_pass");
    openapiFields.add("status");
    openapiFields.add("os_id");
    openapiFields.add("cp_id");
    openapiFields.add("bandwidth_id");
    openapiFields.add("network_drive_id");
    openapiFields.add("additional_ip_addr_id");
    openapiFields.add("plan_id");
    openapiFields.add("price");
    openapiFields.add("location");
    openapiFields.add("autoinstall_ready");
    openapiFields.add("password");
    openapiFields.add("avatar_link");
    openapiFields.add("is_pre_installed");
    openapiFields.add("preset_id");
    openapiFields.add("project_id");

    // a set of required properties/fields (JSON key names)
    openapiRequiredFields = new HashSet<String>();
    openapiRequiredFields.add("id");
    openapiRequiredFields.add("cpu_description");
    openapiRequiredFields.add("hdd_description");
    openapiRequiredFields.add("ram_description");
    openapiRequiredFields.add("created_at");
    openapiRequiredFields.add("ip");
    openapiRequiredFields.add("ipmi_ip");
    openapiRequiredFields.add("ipmi_login");
    openapiRequiredFields.add("ipmi_password");
    openapiRequiredFields.add("ipv6");
    openapiRequiredFields.add("node_id");
    openapiRequiredFields.add("name");
    openapiRequiredFields.add("comment");
    openapiRequiredFields.add("vnc_pass");
    openapiRequiredFields.add("status");
    openapiRequiredFields.add("os_id");
    openapiRequiredFields.add("cp_id");
    openapiRequiredFields.add("bandwidth_id");
    openapiRequiredFields.add("network_drive_id");
    openapiRequiredFields.add("additional_ip_addr_id");
    openapiRequiredFields.add("plan_id");
    openapiRequiredFields.add("price");
    openapiRequiredFields.add("location");
    openapiRequiredFields.add("autoinstall_ready");
    openapiRequiredFields.add("password");
    openapiRequiredFields.add("avatar_link");
    openapiRequiredFields.add("is_pre_installed");
    openapiRequiredFields.add("preset_id");
    openapiRequiredFields.add("project_id");
  }

 /**
  * Validates the JSON Element and throws an exception if issues found
  *
  * @param jsonElement JSON Element
  * @throws IOException if the JSON Element is invalid with respect to DedicatedServer
  */
  public static void validateJsonElement(JsonElement jsonElement) throws IOException {
      if (jsonElement == null) {
        if (!DedicatedServer.openapiRequiredFields.isEmpty()) { // has required fields but JSON element is null
          throw new IllegalArgumentException(String.format("The required field(s) %s in DedicatedServer is not found in the empty JSON string", DedicatedServer.openapiRequiredFields.toString()));
        }
      }

      Set<Entry<String, JsonElement>> entries = jsonElement.getAsJsonObject().entrySet();
      // check to see if the JSON string contains additional fields
      for (Entry<String, JsonElement> entry : entries) {
        if (!DedicatedServer.openapiFields.contains(entry.getKey())) {
          throw new IllegalArgumentException(String.format("The field `%s` in the JSON string is not defined in the `DedicatedServer` properties. JSON: %s", entry.getKey(), jsonElement.toString()));
        }
      }

      // check to make sure all required properties/fields are present in the JSON string
      for (String requiredField : DedicatedServer.openapiRequiredFields) {
        if (jsonElement.getAsJsonObject().get(requiredField) == null) {
          throw new IllegalArgumentException(String.format("The required field `%s` is not found in the JSON string: %s", requiredField, jsonElement.toString()));
        }
      }
        JsonObject jsonObj = jsonElement.getAsJsonObject();
      if (!jsonObj.get("cpu_description").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `cpu_description` to be a primitive type in the JSON string but got `%s`", jsonObj.get("cpu_description").toString()));
      }
      if (!jsonObj.get("hdd_description").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `hdd_description` to be a primitive type in the JSON string but got `%s`", jsonObj.get("hdd_description").toString()));
      }
      if (!jsonObj.get("ram_description").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `ram_description` to be a primitive type in the JSON string but got `%s`", jsonObj.get("ram_description").toString()));
      }
      if ((jsonObj.get("ip") != null && !jsonObj.get("ip").isJsonNull()) && !jsonObj.get("ip").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `ip` to be a primitive type in the JSON string but got `%s`", jsonObj.get("ip").toString()));
      }
      if ((jsonObj.get("ipmi_ip") != null && !jsonObj.get("ipmi_ip").isJsonNull()) && !jsonObj.get("ipmi_ip").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `ipmi_ip` to be a primitive type in the JSON string but got `%s`", jsonObj.get("ipmi_ip").toString()));
      }
      if ((jsonObj.get("ipmi_login") != null && !jsonObj.get("ipmi_login").isJsonNull()) && !jsonObj.get("ipmi_login").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `ipmi_login` to be a primitive type in the JSON string but got `%s`", jsonObj.get("ipmi_login").toString()));
      }
      if ((jsonObj.get("ipmi_password") != null && !jsonObj.get("ipmi_password").isJsonNull()) && !jsonObj.get("ipmi_password").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `ipmi_password` to be a primitive type in the JSON string but got `%s`", jsonObj.get("ipmi_password").toString()));
      }
      if ((jsonObj.get("ipv6") != null && !jsonObj.get("ipv6").isJsonNull()) && !jsonObj.get("ipv6").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `ipv6` to be a primitive type in the JSON string but got `%s`", jsonObj.get("ipv6").toString()));
      }
      if (!jsonObj.get("name").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `name` to be a primitive type in the JSON string but got `%s`", jsonObj.get("name").toString()));
      }
      if (!jsonObj.get("comment").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `comment` to be a primitive type in the JSON string but got `%s`", jsonObj.get("comment").toString()));
      }
      if ((jsonObj.get("vnc_pass") != null && !jsonObj.get("vnc_pass").isJsonNull()) && !jsonObj.get("vnc_pass").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `vnc_pass` to be a primitive type in the JSON string but got `%s`", jsonObj.get("vnc_pass").toString()));
      }
      if (!jsonObj.get("status").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `status` to be a primitive type in the JSON string but got `%s`", jsonObj.get("status").toString()));
      }
      // ensure the required json array is present
      if (jsonObj.get("network_drive_id") == null) {
        throw new IllegalArgumentException("Expected the field `linkedContent` to be an array in the JSON string but got `null`");
      } else if (!jsonObj.get("network_drive_id").isJsonArray()) {
        throw new IllegalArgumentException(String.format("Expected the field `network_drive_id` to be an array in the JSON string but got `%s`", jsonObj.get("network_drive_id").toString()));
      }
      // ensure the required json array is present
      if (jsonObj.get("additional_ip_addr_id") == null) {
        throw new IllegalArgumentException("Expected the field `linkedContent` to be an array in the JSON string but got `null`");
      } else if (!jsonObj.get("additional_ip_addr_id").isJsonArray()) {
        throw new IllegalArgumentException(String.format("Expected the field `additional_ip_addr_id` to be an array in the JSON string but got `%s`", jsonObj.get("additional_ip_addr_id").toString()));
      }
      if (!jsonObj.get("location").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `location` to be a primitive type in the JSON string but got `%s`", jsonObj.get("location").toString()));
      }
      if ((jsonObj.get("password") != null && !jsonObj.get("password").isJsonNull()) && !jsonObj.get("password").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `password` to be a primitive type in the JSON string but got `%s`", jsonObj.get("password").toString()));
      }
      if ((jsonObj.get("avatar_link") != null && !jsonObj.get("avatar_link").isJsonNull()) && !jsonObj.get("avatar_link").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `avatar_link` to be a primitive type in the JSON string but got `%s`", jsonObj.get("avatar_link").toString()));
      }
  }

  public static class CustomTypeAdapterFactory implements TypeAdapterFactory {
    @SuppressWarnings("unchecked")
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
       if (!DedicatedServer.class.isAssignableFrom(type.getRawType())) {
         return null; // this class only serializes 'DedicatedServer' and its subtypes
       }
       final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);
       final TypeAdapter<DedicatedServer> thisAdapter
                        = gson.getDelegateAdapter(this, TypeToken.get(DedicatedServer.class));

       return (TypeAdapter<T>) new TypeAdapter<DedicatedServer>() {
           @Override
           public void write(JsonWriter out, DedicatedServer value) throws IOException {
             JsonObject obj = thisAdapter.toJsonTree(value).getAsJsonObject();
             elementAdapter.write(out, obj);
           }

           @Override
           public DedicatedServer read(JsonReader in) throws IOException {
             JsonElement jsonElement = elementAdapter.read(in);
             validateJsonElement(jsonElement);
             return thisAdapter.fromJsonTree(jsonElement);
           }

       }.nullSafe();
    }
  }

 /**
  * Create an instance of DedicatedServer given an JSON string
  *
  * @param jsonString JSON string
  * @return An instance of DedicatedServer
  * @throws IOException if the JSON string is invalid with respect to DedicatedServer
  */
  public static DedicatedServer fromJson(String jsonString) throws IOException {
    return JSON.getGson().fromJson(jsonString, DedicatedServer.class);
  }

 /**
  * Convert an instance of DedicatedServer to an JSON string
  *
  * @return JSON string
  */
  public String toJson() {
    return JSON.getGson().toJson(this);
  }
}

