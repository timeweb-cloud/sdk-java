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
import org.openapitools.client.model.AppConfiguration;
import org.openapitools.client.model.AppDiskStatus;
import org.openapitools.client.model.AppDomainsInner;
import org.openapitools.client.model.AppProvider;
import org.openapitools.client.model.Frameworks;
import org.openapitools.client.model.Repository;

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
 * Экземпляр приложения.
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2025-03-19T13:53:53.966626Z[Etc/UTC]")
public class App {
  public static final String SERIALIZED_NAME_ID = "id";
  @SerializedName(SERIALIZED_NAME_ID)
  private BigDecimal id;

  /**
   * Тип приложения.
   */
  @JsonAdapter(TypeEnum.Adapter.class)
  public enum TypeEnum {
    BACKEND("backend"),
    
    FRONTEND("frontend");

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
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
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

  public static final String SERIALIZED_NAME_NAME = "name";
  @SerializedName(SERIALIZED_NAME_NAME)
  private String name;

  /**
   * Статус приложения.
   */
  @JsonAdapter(StatusEnum.Adapter.class)
  public enum StatusEnum {
    ACTIVE("active"),
    
    PAUSED("paused"),
    
    NO_PAID("no_paid"),
    
    DEPLOY("deploy"),
    
    FAILURE("failure"),
    
    STARTUP_ERROR("startup_error"),
    
    NEW("new"),
    
    REBOOT("reboot");

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

  public static final String SERIALIZED_NAME_PROVIDER = "provider";
  @SerializedName(SERIALIZED_NAME_PROVIDER)
  private AppProvider provider;

  public static final String SERIALIZED_NAME_IP = "ip";
  @SerializedName(SERIALIZED_NAME_IP)
  private String ip;

  public static final String SERIALIZED_NAME_DOMAINS = "domains";
  @SerializedName(SERIALIZED_NAME_DOMAINS)
  private List<AppDomainsInner> domains = new ArrayList<>();

  public static final String SERIALIZED_NAME_FRAMEWORK = "framework";
  @SerializedName(SERIALIZED_NAME_FRAMEWORK)
  private Frameworks framework;

  /**
   * Локация сервера.
   */
  @JsonAdapter(LocationEnum.Adapter.class)
  public enum LocationEnum {
    RU_1("ru-1"),
    
    PL_1("pl-1"),
    
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

  public static final String SERIALIZED_NAME_REPOSITORY = "repository";
  @SerializedName(SERIALIZED_NAME_REPOSITORY)
  private Repository repository;

  public static final String SERIALIZED_NAME_ENV_VERSION = "env_version";
  @SerializedName(SERIALIZED_NAME_ENV_VERSION)
  private String envVersion;

  public static final String SERIALIZED_NAME_ENVS = "envs";
  @SerializedName(SERIALIZED_NAME_ENVS)
  private Object envs;

  public static final String SERIALIZED_NAME_BRANCH_NAME = "branch_name";
  @SerializedName(SERIALIZED_NAME_BRANCH_NAME)
  private String branchName;

  public static final String SERIALIZED_NAME_IS_AUTO_DEPLOY = "is_auto_deploy";
  @SerializedName(SERIALIZED_NAME_IS_AUTO_DEPLOY)
  private Boolean isAutoDeploy;

  public static final String SERIALIZED_NAME_COMMIT_SHA = "commit_sha";
  @SerializedName(SERIALIZED_NAME_COMMIT_SHA)
  private String commitSha;

  public static final String SERIALIZED_NAME_COMMENT = "comment";
  @SerializedName(SERIALIZED_NAME_COMMENT)
  private String comment;

  public static final String SERIALIZED_NAME_PRESET_ID = "preset_id";
  @SerializedName(SERIALIZED_NAME_PRESET_ID)
  private BigDecimal presetId;

  public static final String SERIALIZED_NAME_INDEX_DIR = "index_dir";
  @SerializedName(SERIALIZED_NAME_INDEX_DIR)
  private String indexDir;

  public static final String SERIALIZED_NAME_BUILD_CMD = "build_cmd";
  @SerializedName(SERIALIZED_NAME_BUILD_CMD)
  private String buildCmd;

  public static final String SERIALIZED_NAME_RUN_CMD = "run_cmd";
  @SerializedName(SERIALIZED_NAME_RUN_CMD)
  private String runCmd;

  public static final String SERIALIZED_NAME_CONFIGURATION = "configuration";
  @SerializedName(SERIALIZED_NAME_CONFIGURATION)
  private AppConfiguration _configuration;

  public static final String SERIALIZED_NAME_DISK_STATUS = "disk_status";
  @SerializedName(SERIALIZED_NAME_DISK_STATUS)
  private AppDiskStatus diskStatus;

  public static final String SERIALIZED_NAME_IS_QEMU_AGENT = "is_qemu_agent";
  @SerializedName(SERIALIZED_NAME_IS_QEMU_AGENT)
  private Boolean isQemuAgent;

  public static final String SERIALIZED_NAME_LANGUAGE = "language";
  @SerializedName(SERIALIZED_NAME_LANGUAGE)
  private String language;

  public static final String SERIALIZED_NAME_START_TIME = "start_time";
  @SerializedName(SERIALIZED_NAME_START_TIME)
  private OffsetDateTime startTime;

  public App() {
  }

  public App id(BigDecimal id) {
    
    this.id = id;
    return this;
  }

   /**
   * ID для каждого экземпляра приложения. Автоматически генерируется при создании.
   * @return id
  **/
  @javax.annotation.Nonnull
  public BigDecimal getId() {
    return id;
  }


  public void setId(BigDecimal id) {
    this.id = id;
  }


  public App type(TypeEnum type) {
    
    this.type = type;
    return this;
  }

   /**
   * Тип приложения.
   * @return type
  **/
  @javax.annotation.Nonnull
  public TypeEnum getType() {
    return type;
  }


  public void setType(TypeEnum type) {
    this.type = type;
  }


  public App name(String name) {
    
    this.name = name;
    return this;
  }

   /**
   * Удобочитаемое имя, установленное для приложения.
   * @return name
  **/
  @javax.annotation.Nonnull
  public String getName() {
    return name;
  }


  public void setName(String name) {
    this.name = name;
  }


  public App status(StatusEnum status) {
    
    this.status = status;
    return this;
  }

   /**
   * Статус приложения.
   * @return status
  **/
  @javax.annotation.Nonnull
  public StatusEnum getStatus() {
    return status;
  }


  public void setStatus(StatusEnum status) {
    this.status = status;
  }


  public App provider(AppProvider provider) {
    
    this.provider = provider;
    return this;
  }

   /**
   * Get provider
   * @return provider
  **/
  @javax.annotation.Nonnull
  public AppProvider getProvider() {
    return provider;
  }


  public void setProvider(AppProvider provider) {
    this.provider = provider;
  }


  public App ip(String ip) {
    
    this.ip = ip;
    return this;
  }

   /**
   * IPv4-адрес приложения.
   * @return ip
  **/
  @javax.annotation.Nonnull
  public String getIp() {
    return ip;
  }


  public void setIp(String ip) {
    this.ip = ip;
  }


  public App domains(List<AppDomainsInner> domains) {
    
    this.domains = domains;
    return this;
  }

  public App addDomainsItem(AppDomainsInner domainsItem) {
    if (this.domains == null) {
      this.domains = new ArrayList<>();
    }
    this.domains.add(domainsItem);
    return this;
  }

   /**
   * Get domains
   * @return domains
  **/
  @javax.annotation.Nonnull
  public List<AppDomainsInner> getDomains() {
    return domains;
  }


  public void setDomains(List<AppDomainsInner> domains) {
    this.domains = domains;
  }


  public App framework(Frameworks framework) {
    
    this.framework = framework;
    return this;
  }

   /**
   * Get framework
   * @return framework
  **/
  @javax.annotation.Nonnull
  public Frameworks getFramework() {
    return framework;
  }


  public void setFramework(Frameworks framework) {
    this.framework = framework;
  }


  public App location(LocationEnum location) {
    
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


  public App repository(Repository repository) {
    
    this.repository = repository;
    return this;
  }

   /**
   * Get repository
   * @return repository
  **/
  @javax.annotation.Nonnull
  public Repository getRepository() {
    return repository;
  }


  public void setRepository(Repository repository) {
    this.repository = repository;
  }


  public App envVersion(String envVersion) {
    
    this.envVersion = envVersion;
    return this;
  }

   /**
   * Версия окружения.
   * @return envVersion
  **/
  @javax.annotation.Nullable
  public String getEnvVersion() {
    return envVersion;
  }


  public void setEnvVersion(String envVersion) {
    this.envVersion = envVersion;
  }


  public App envs(Object envs) {
    
    this.envs = envs;
    return this;
  }

   /**
   * Переменные окружения приложения. Объект с ключами и значениями типа string.
   * @return envs
  **/
  @javax.annotation.Nonnull
  public Object getEnvs() {
    return envs;
  }


  public void setEnvs(Object envs) {
    this.envs = envs;
  }


  public App branchName(String branchName) {
    
    this.branchName = branchName;
    return this;
  }

   /**
   * Название ветки репозитория из которой собрано приложение.
   * @return branchName
  **/
  @javax.annotation.Nonnull
  public String getBranchName() {
    return branchName;
  }


  public void setBranchName(String branchName) {
    this.branchName = branchName;
  }


  public App isAutoDeploy(Boolean isAutoDeploy) {
    
    this.isAutoDeploy = isAutoDeploy;
    return this;
  }

   /**
   * Включен ли автоматический деплой.
   * @return isAutoDeploy
  **/
  @javax.annotation.Nonnull
  public Boolean getIsAutoDeploy() {
    return isAutoDeploy;
  }


  public void setIsAutoDeploy(Boolean isAutoDeploy) {
    this.isAutoDeploy = isAutoDeploy;
  }


  public App commitSha(String commitSha) {
    
    this.commitSha = commitSha;
    return this;
  }

   /**
   * Хэш коммита из которого собрано приложеие.
   * @return commitSha
  **/
  @javax.annotation.Nonnull
  public String getCommitSha() {
    return commitSha;
  }


  public void setCommitSha(String commitSha) {
    this.commitSha = commitSha;
  }


  public App comment(String comment) {
    
    this.comment = comment;
    return this;
  }

   /**
   * Комментарий к приложению.
   * @return comment
  **/
  @javax.annotation.Nonnull
  public String getComment() {
    return comment;
  }


  public void setComment(String comment) {
    this.comment = comment;
  }


  public App presetId(BigDecimal presetId) {
    
    this.presetId = presetId;
    return this;
  }

   /**
   * ID тарифа.
   * @return presetId
  **/
  @javax.annotation.Nonnull
  public BigDecimal getPresetId() {
    return presetId;
  }


  public void setPresetId(BigDecimal presetId) {
    this.presetId = presetId;
  }


  public App indexDir(String indexDir) {
    
    this.indexDir = indexDir;
    return this;
  }

   /**
   * Путь к директории с индексным файлом. Определен для приложений &#x60;type: frontend&#x60;. Для приложений &#x60;type: backend&#x60; всегда null.
   * @return indexDir
  **/
  @javax.annotation.Nullable
  public String getIndexDir() {
    return indexDir;
  }


  public void setIndexDir(String indexDir) {
    this.indexDir = indexDir;
  }


  public App buildCmd(String buildCmd) {
    
    this.buildCmd = buildCmd;
    return this;
  }

   /**
   * Команда сборки приложения.
   * @return buildCmd
  **/
  @javax.annotation.Nonnull
  public String getBuildCmd() {
    return buildCmd;
  }


  public void setBuildCmd(String buildCmd) {
    this.buildCmd = buildCmd;
  }


  public App runCmd(String runCmd) {
    
    this.runCmd = runCmd;
    return this;
  }

   /**
   * Команда для запуска приложения. Определена для приложений &#x60;type: backend&#x60;. Для приложений &#x60;type: frontend&#x60; всегда null.
   * @return runCmd
  **/
  @javax.annotation.Nullable
  public String getRunCmd() {
    return runCmd;
  }


  public void setRunCmd(String runCmd) {
    this.runCmd = runCmd;
  }


  public App _configuration(AppConfiguration _configuration) {
    
    this._configuration = _configuration;
    return this;
  }

   /**
   * Get _configuration
   * @return _configuration
  **/
  @javax.annotation.Nullable
  public AppConfiguration getConfiguration() {
    return _configuration;
  }


  public void setConfiguration(AppConfiguration _configuration) {
    this._configuration = _configuration;
  }


  public App diskStatus(AppDiskStatus diskStatus) {
    
    this.diskStatus = diskStatus;
    return this;
  }

   /**
   * Get diskStatus
   * @return diskStatus
  **/
  @javax.annotation.Nullable
  public AppDiskStatus getDiskStatus() {
    return diskStatus;
  }


  public void setDiskStatus(AppDiskStatus diskStatus) {
    this.diskStatus = diskStatus;
  }


  public App isQemuAgent(Boolean isQemuAgent) {
    
    this.isQemuAgent = isQemuAgent;
    return this;
  }

   /**
   * Включен ли агент QEMU.
   * @return isQemuAgent
  **/
  @javax.annotation.Nonnull
  public Boolean getIsQemuAgent() {
    return isQemuAgent;
  }


  public void setIsQemuAgent(Boolean isQemuAgent) {
    this.isQemuAgent = isQemuAgent;
  }


  public App language(String language) {
    
    this.language = language;
    return this;
  }

   /**
   * Язык программирования приложения.
   * @return language
  **/
  @javax.annotation.Nonnull
  public String getLanguage() {
    return language;
  }


  public void setLanguage(String language) {
    this.language = language;
  }


  public App startTime(OffsetDateTime startTime) {
    
    this.startTime = startTime;
    return this;
  }

   /**
   * Время запуска приложения.
   * @return startTime
  **/
  @javax.annotation.Nonnull
  public OffsetDateTime getStartTime() {
    return startTime;
  }


  public void setStartTime(OffsetDateTime startTime) {
    this.startTime = startTime;
  }



  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    App app = (App) o;
    return Objects.equals(this.id, app.id) &&
        Objects.equals(this.type, app.type) &&
        Objects.equals(this.name, app.name) &&
        Objects.equals(this.status, app.status) &&
        Objects.equals(this.provider, app.provider) &&
        Objects.equals(this.ip, app.ip) &&
        Objects.equals(this.domains, app.domains) &&
        Objects.equals(this.framework, app.framework) &&
        Objects.equals(this.location, app.location) &&
        Objects.equals(this.repository, app.repository) &&
        Objects.equals(this.envVersion, app.envVersion) &&
        Objects.equals(this.envs, app.envs) &&
        Objects.equals(this.branchName, app.branchName) &&
        Objects.equals(this.isAutoDeploy, app.isAutoDeploy) &&
        Objects.equals(this.commitSha, app.commitSha) &&
        Objects.equals(this.comment, app.comment) &&
        Objects.equals(this.presetId, app.presetId) &&
        Objects.equals(this.indexDir, app.indexDir) &&
        Objects.equals(this.buildCmd, app.buildCmd) &&
        Objects.equals(this.runCmd, app.runCmd) &&
        Objects.equals(this._configuration, app._configuration) &&
        Objects.equals(this.diskStatus, app.diskStatus) &&
        Objects.equals(this.isQemuAgent, app.isQemuAgent) &&
        Objects.equals(this.language, app.language) &&
        Objects.equals(this.startTime, app.startTime);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, type, name, status, provider, ip, domains, framework, location, repository, envVersion, envs, branchName, isAutoDeploy, commitSha, comment, presetId, indexDir, buildCmd, runCmd, _configuration, diskStatus, isQemuAgent, language, startTime);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class App {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    provider: ").append(toIndentedString(provider)).append("\n");
    sb.append("    ip: ").append(toIndentedString(ip)).append("\n");
    sb.append("    domains: ").append(toIndentedString(domains)).append("\n");
    sb.append("    framework: ").append(toIndentedString(framework)).append("\n");
    sb.append("    location: ").append(toIndentedString(location)).append("\n");
    sb.append("    repository: ").append(toIndentedString(repository)).append("\n");
    sb.append("    envVersion: ").append(toIndentedString(envVersion)).append("\n");
    sb.append("    envs: ").append(toIndentedString(envs)).append("\n");
    sb.append("    branchName: ").append(toIndentedString(branchName)).append("\n");
    sb.append("    isAutoDeploy: ").append(toIndentedString(isAutoDeploy)).append("\n");
    sb.append("    commitSha: ").append(toIndentedString(commitSha)).append("\n");
    sb.append("    comment: ").append(toIndentedString(comment)).append("\n");
    sb.append("    presetId: ").append(toIndentedString(presetId)).append("\n");
    sb.append("    indexDir: ").append(toIndentedString(indexDir)).append("\n");
    sb.append("    buildCmd: ").append(toIndentedString(buildCmd)).append("\n");
    sb.append("    runCmd: ").append(toIndentedString(runCmd)).append("\n");
    sb.append("    _configuration: ").append(toIndentedString(_configuration)).append("\n");
    sb.append("    diskStatus: ").append(toIndentedString(diskStatus)).append("\n");
    sb.append("    isQemuAgent: ").append(toIndentedString(isQemuAgent)).append("\n");
    sb.append("    language: ").append(toIndentedString(language)).append("\n");
    sb.append("    startTime: ").append(toIndentedString(startTime)).append("\n");
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
    openapiFields.add("type");
    openapiFields.add("name");
    openapiFields.add("status");
    openapiFields.add("provider");
    openapiFields.add("ip");
    openapiFields.add("domains");
    openapiFields.add("framework");
    openapiFields.add("location");
    openapiFields.add("repository");
    openapiFields.add("env_version");
    openapiFields.add("envs");
    openapiFields.add("branch_name");
    openapiFields.add("is_auto_deploy");
    openapiFields.add("commit_sha");
    openapiFields.add("comment");
    openapiFields.add("preset_id");
    openapiFields.add("index_dir");
    openapiFields.add("build_cmd");
    openapiFields.add("run_cmd");
    openapiFields.add("configuration");
    openapiFields.add("disk_status");
    openapiFields.add("is_qemu_agent");
    openapiFields.add("language");
    openapiFields.add("start_time");

    // a set of required properties/fields (JSON key names)
    openapiRequiredFields = new HashSet<String>();
    openapiRequiredFields.add("id");
    openapiRequiredFields.add("type");
    openapiRequiredFields.add("name");
    openapiRequiredFields.add("status");
    openapiRequiredFields.add("provider");
    openapiRequiredFields.add("ip");
    openapiRequiredFields.add("domains");
    openapiRequiredFields.add("framework");
    openapiRequiredFields.add("location");
    openapiRequiredFields.add("repository");
    openapiRequiredFields.add("env_version");
    openapiRequiredFields.add("envs");
    openapiRequiredFields.add("branch_name");
    openapiRequiredFields.add("is_auto_deploy");
    openapiRequiredFields.add("commit_sha");
    openapiRequiredFields.add("comment");
    openapiRequiredFields.add("preset_id");
    openapiRequiredFields.add("index_dir");
    openapiRequiredFields.add("build_cmd");
    openapiRequiredFields.add("run_cmd");
    openapiRequiredFields.add("configuration");
    openapiRequiredFields.add("disk_status");
    openapiRequiredFields.add("is_qemu_agent");
    openapiRequiredFields.add("language");
    openapiRequiredFields.add("start_time");
  }

 /**
  * Validates the JSON Element and throws an exception if issues found
  *
  * @param jsonElement JSON Element
  * @throws IOException if the JSON Element is invalid with respect to App
  */
  public static void validateJsonElement(JsonElement jsonElement) throws IOException {
      if (jsonElement == null) {
        if (!App.openapiRequiredFields.isEmpty()) { // has required fields but JSON element is null
          throw new IllegalArgumentException(String.format("The required field(s) %s in App is not found in the empty JSON string", App.openapiRequiredFields.toString()));
        }
      }

      Set<Entry<String, JsonElement>> entries = jsonElement.getAsJsonObject().entrySet();
      // check to see if the JSON string contains additional fields
      for (Entry<String, JsonElement> entry : entries) {
        if (!App.openapiFields.contains(entry.getKey())) {
          throw new IllegalArgumentException(String.format("The field `%s` in the JSON string is not defined in the `App` properties. JSON: %s", entry.getKey(), jsonElement.toString()));
        }
      }

      // check to make sure all required properties/fields are present in the JSON string
      for (String requiredField : App.openapiRequiredFields) {
        if (jsonElement.getAsJsonObject().get(requiredField) == null) {
          throw new IllegalArgumentException(String.format("The required field `%s` is not found in the JSON string: %s", requiredField, jsonElement.toString()));
        }
      }
        JsonObject jsonObj = jsonElement.getAsJsonObject();
      if (!jsonObj.get("type").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `type` to be a primitive type in the JSON string but got `%s`", jsonObj.get("type").toString()));
      }
      if (!jsonObj.get("name").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `name` to be a primitive type in the JSON string but got `%s`", jsonObj.get("name").toString()));
      }
      if (!jsonObj.get("status").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `status` to be a primitive type in the JSON string but got `%s`", jsonObj.get("status").toString()));
      }
      // validate the required field `provider`
      AppProvider.validateJsonElement(jsonObj.get("provider"));
      if (!jsonObj.get("ip").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `ip` to be a primitive type in the JSON string but got `%s`", jsonObj.get("ip").toString()));
      }
      // ensure the json data is an array
      if (!jsonObj.get("domains").isJsonArray()) {
        throw new IllegalArgumentException(String.format("Expected the field `domains` to be an array in the JSON string but got `%s`", jsonObj.get("domains").toString()));
      }

      JsonArray jsonArraydomains = jsonObj.getAsJsonArray("domains");
      // validate the required field `domains` (array)
      for (int i = 0; i < jsonArraydomains.size(); i++) {
        AppDomainsInner.validateJsonElement(jsonArraydomains.get(i));
      };
      if (!jsonObj.get("location").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `location` to be a primitive type in the JSON string but got `%s`", jsonObj.get("location").toString()));
      }
      // validate the required field `repository`
      Repository.validateJsonElement(jsonObj.get("repository"));
      if ((jsonObj.get("env_version") != null && !jsonObj.get("env_version").isJsonNull()) && !jsonObj.get("env_version").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `env_version` to be a primitive type in the JSON string but got `%s`", jsonObj.get("env_version").toString()));
      }
      if (!jsonObj.get("branch_name").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `branch_name` to be a primitive type in the JSON string but got `%s`", jsonObj.get("branch_name").toString()));
      }
      if (!jsonObj.get("commit_sha").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `commit_sha` to be a primitive type in the JSON string but got `%s`", jsonObj.get("commit_sha").toString()));
      }
      if (!jsonObj.get("comment").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `comment` to be a primitive type in the JSON string but got `%s`", jsonObj.get("comment").toString()));
      }
      if ((jsonObj.get("index_dir") != null && !jsonObj.get("index_dir").isJsonNull()) && !jsonObj.get("index_dir").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `index_dir` to be a primitive type in the JSON string but got `%s`", jsonObj.get("index_dir").toString()));
      }
      if (!jsonObj.get("build_cmd").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `build_cmd` to be a primitive type in the JSON string but got `%s`", jsonObj.get("build_cmd").toString()));
      }
      if ((jsonObj.get("run_cmd") != null && !jsonObj.get("run_cmd").isJsonNull()) && !jsonObj.get("run_cmd").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `run_cmd` to be a primitive type in the JSON string but got `%s`", jsonObj.get("run_cmd").toString()));
      }
      // validate the required field `configuration`
      AppConfiguration.validateJsonElement(jsonObj.get("configuration"));
      // validate the required field `disk_status`
      AppDiskStatus.validateJsonElement(jsonObj.get("disk_status"));
      if (!jsonObj.get("language").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `language` to be a primitive type in the JSON string but got `%s`", jsonObj.get("language").toString()));
      }
  }

  public static class CustomTypeAdapterFactory implements TypeAdapterFactory {
    @SuppressWarnings("unchecked")
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
       if (!App.class.isAssignableFrom(type.getRawType())) {
         return null; // this class only serializes 'App' and its subtypes
       }
       final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);
       final TypeAdapter<App> thisAdapter
                        = gson.getDelegateAdapter(this, TypeToken.get(App.class));

       return (TypeAdapter<T>) new TypeAdapter<App>() {
           @Override
           public void write(JsonWriter out, App value) throws IOException {
             JsonObject obj = thisAdapter.toJsonTree(value).getAsJsonObject();
             elementAdapter.write(out, obj);
           }

           @Override
           public App read(JsonReader in) throws IOException {
             JsonElement jsonElement = elementAdapter.read(in);
             validateJsonElement(jsonElement);
             return thisAdapter.fromJsonTree(jsonElement);
           }

       }.nullSafe();
    }
  }

 /**
  * Create an instance of App given an JSON string
  *
  * @param jsonString JSON string
  * @return An instance of App
  * @throws IOException if the JSON string is invalid with respect to App
  */
  public static App fromJson(String jsonString) throws IOException {
    return JSON.getGson().fromJson(jsonString, App.class);
  }

 /**
  * Convert an instance of App to an JSON string
  *
  * @return JSON string
  */
  public String toJson() {
    return JSON.getGson().toJson(this);
  }
}

