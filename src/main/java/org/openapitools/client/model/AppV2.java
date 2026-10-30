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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.openapitools.client.model.AppV2Configuration;
import org.openapitools.client.model.AppV2DiskStatus;
import org.openapitools.client.model.AppV2DomainsInner;
import org.openapitools.client.model.AppV2Provider;
import org.openapitools.client.model.Repository;
import org.openapitools.jackson.nullable.JsonNullable;

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
 * Приложение v2. Создание и первый деплой выполняются асинхронно. Поля сервера и инфраструктуры могут отсутствовать, пока соответствующие ресурсы не созданы.
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2026-10-30T15:03:29.616934Z[Etc/UTC]")
public class AppV2 {
  public static final String SERIALIZED_NAME_ID = "id";
  @SerializedName(SERIALIZED_NAME_ID)
  private Integer id;

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
   * Текущее состояние приложения. После принятия запроса на создание — new. Ответ 201 не означает завершение развёртывания.
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
    
    REBOOT("reboot"),
    
    BLOCKED("blocked"),
    
    REQUEST_LIMIT_EXCEEDED("request_limit_exceeded");

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
  private AppV2Provider provider;

  public static final String SERIALIZED_NAME_IP = "ip";
  @SerializedName(SERIALIZED_NAME_IP)
  private String ip;

  public static final String SERIALIZED_NAME_DOMAINS = "domains";
  @SerializedName(SERIALIZED_NAME_DOMAINS)
  private List<AppV2DomainsInner> domains = new ArrayList<>();

  /**
   * Фреймворк приложения. Поле может отсутствовать до создания инфраструктуры.
   */
  @JsonAdapter(FrameworkEnum.Adapter.class)
  public enum FrameworkEnum {
    DJANGO("django"),
    
    EXPRESS("express"),
    
    PHOENIX("phoenix"),
    
    SPRING("Spring"),
    
    LARAVEL("laravel"),
    
    BEEGO("beego"),
    
    FASTAPI("fastapi"),
    
    ASP_NET_CORE("ASP.NET Core"),
    
    HAPI("hapi"),
    
    CELERY("celery"),
    
    FLASK("flask"),
    
    GIN("gin"),
    
    DOCKER("docker"),
    
    DOCKER_COMPOSE("docker-compose"),
    
    FASTIFY("fastify"),
    
    NEST("nest"),
    
    SYMFONY("symfony"),
    
    YII("yii"),
    
    NEXT_JS("next.js"),
    
    NUXT("nuxt"),
    
    ASTRO("astro"),
    
    TANSTACK_START("tanstack-start"),
    
    SOLID_START("solid-start"),
    
    KTOR("ktor"),
    
    AXUM("axum"),
    
    ACTIX("actix"),
    
    TORNADO("tornado"),
    
    AIOHTTP("aiohttp"),
    
    FIBER("fiber"),
    
    ECHO("echo"),
    
    MICRONAUT("Micronaut"),
    
    QUARKUS("Quarkus"),
    
    REMIX("remix"),
    
    SVELTEKIT("sveltekit"),
    
    ANGULAR("angular"),
    
    EMBER("ember"),
    
    PREACT("preact"),
    
    REACT("react"),
    
    SOLID("solid"),
    
    SVELTE("svelte"),
    
    VUE("vue"),
    
    STATIC_NOBUILD("static-nobuild"),
    
    ANOTHER("another");

    private String value;

    FrameworkEnum(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public static FrameworkEnum fromValue(String value) {
      for (FrameworkEnum b : FrameworkEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }

    public static class Adapter extends TypeAdapter<FrameworkEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final FrameworkEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public FrameworkEnum read(final JsonReader jsonReader) throws IOException {
        String value =  jsonReader.nextString();
        return FrameworkEnum.fromValue(value);
      }
    }
  }

  public static final String SERIALIZED_NAME_FRAMEWORK = "framework";
  @SerializedName(SERIALIZED_NAME_FRAMEWORK)
  private FrameworkEnum framework;

  /**
   * Локация сервера. Может отсутствовать до создания сервера.
   */
  @JsonAdapter(LocationEnum.Adapter.class)
  public enum LocationEnum {
    RU_1("ru-1"),
    
    RU_2("ru-2"),
    
    RU_3("ru-3"),
    
    PL_1("pl-1"),
    
    KZ_1("kz-1"),
    
    NL_1("nl-1"),
    
    US_1("us-1"),
    
    US_2("us-2"),
    
    DE_1("de-1");

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
  private Map<String, String> envs = new HashMap<>();

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

  public static final String SERIALIZED_NAME_AVATAR_LINK = "avatar_link";
  @SerializedName(SERIALIZED_NAME_AVATAR_LINK)
  private String avatarLink;

  public static final String SERIALIZED_NAME_RUN_CMD = "run_cmd";
  @SerializedName(SERIALIZED_NAME_RUN_CMD)
  private String runCmd;

  public static final String SERIALIZED_NAME_CONFIGURATION = "configuration";
  @SerializedName(SERIALIZED_NAME_CONFIGURATION)
  private AppV2Configuration _configuration;

  public static final String SERIALIZED_NAME_DISK_STATUS = "disk_status";
  @SerializedName(SERIALIZED_NAME_DISK_STATUS)
  private AppV2DiskStatus diskStatus;

  public static final String SERIALIZED_NAME_IS_QEMU_AGENT = "is_qemu_agent";
  @SerializedName(SERIALIZED_NAME_IS_QEMU_AGENT)
  private Boolean isQemuAgent;

  /**
   * Язык или способ развёртывания. Может отсутствовать до создания инфраструктуры. Поле может отсутствовать до создания инфраструктуры.
   */
  @JsonAdapter(LanguageEnum.Adapter.class)
  public enum LanguageEnum {
    JAVASCRIPT("javascript"),
    
    NODE("node"),
    
    ELIXIR("elixir"),
    
    GO("go"),
    
    PHP("php"),
    
    PYTHON("python"),
    
    RUBY("ruby"),
    
    DOCKER("docker"),
    
    DOCKER_COMPOSE("docker-compose"),
    
    DOTNET("dotnet"),
    
    JAVA("java"),
    
    KOTLIN("kotlin"),
    
    RUST("rust"),
    
    BUN("bun"),
    
    DENO("deno"),
    
    CUSTOM_IMAGE("custom-image");

    private String value;

    LanguageEnum(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public static LanguageEnum fromValue(String value) {
      for (LanguageEnum b : LanguageEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }

    public static class Adapter extends TypeAdapter<LanguageEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final LanguageEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public LanguageEnum read(final JsonReader jsonReader) throws IOException {
        String value =  jsonReader.nextString();
        return LanguageEnum.fromValue(value);
      }
    }
  }

  public static final String SERIALIZED_NAME_LANGUAGE = "language";
  @SerializedName(SERIALIZED_NAME_LANGUAGE)
  private LanguageEnum language;

  public static final String SERIALIZED_NAME_START_TIME = "start_time";
  @SerializedName(SERIALIZED_NAME_START_TIME)
  private OffsetDateTime startTime;

  public static final String SERIALIZED_NAME_BRANCH = "branch";
  @SerializedName(SERIALIZED_NAME_BRANCH)
  private String branch;

  public static final String SERIALIZED_NAME_CONFIGURATOR_ID = "configurator_id";
  @SerializedName(SERIALIZED_NAME_CONFIGURATOR_ID)
  private Integer configuratorId;

  public static final String SERIALIZED_NAME_PROJECT_ID = "project_id";
  @SerializedName(SERIALIZED_NAME_PROJECT_ID)
  private Integer projectId;

  public static final String SERIALIZED_NAME_SYSTEM_DEPENDENCIES = "system_dependencies";
  @SerializedName(SERIALIZED_NAME_SYSTEM_DEPENDENCIES)
  private List<String> systemDependencies;

  public static final String SERIALIZED_NAME_CREATED_AT = "created_at";
  @SerializedName(SERIALIZED_NAME_CREATED_AT)
  private OffsetDateTime createdAt;

  /**
   * Зона доступности сервера. Может быть null или отсутствовать до создания сервера.
   */
  @JsonAdapter(AvailabilityZoneEnum.Adapter.class)
  public enum AvailabilityZoneEnum {
    SPB_1("spb-1"),
    
    SPB_2("spb-2"),
    
    SPB_3("spb-3"),
    
    SPB_4("spb-4"),
    
    NSK_1("nsk-1"),
    
    AMS_1("ams-1"),
    
    GDN_1("gdn-1"),
    
    ALA_1("ala-1"),
    
    NYC_1("nyc-1"),
    
    MSK_1("msk-1");

    private String value;

    AvailabilityZoneEnum(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public static AvailabilityZoneEnum fromValue(String value) {
      for (AvailabilityZoneEnum b : AvailabilityZoneEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      return null;
    }

    public static class Adapter extends TypeAdapter<AvailabilityZoneEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final AvailabilityZoneEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public AvailabilityZoneEnum read(final JsonReader jsonReader) throws IOException {
        String value =  jsonReader.nextString();
        return AvailabilityZoneEnum.fromValue(value);
      }
    }
  }

  public static final String SERIALIZED_NAME_AVAILABILITY_ZONE = "availability_zone";
  @SerializedName(SERIALIZED_NAME_AVAILABILITY_ZONE)
  private AvailabilityZoneEnum availabilityZone;

  public static final String SERIALIZED_NAME_REQUESTS_LIMIT = "requests_limit";
  @SerializedName(SERIALIZED_NAME_REQUESTS_LIMIT)
  private Integer requestsLimit;

  public static final String SERIALIZED_NAME_BUILD_ENTRY_PATH = "build_entry_path";
  @SerializedName(SERIALIZED_NAME_BUILD_ENTRY_PATH)
  private String buildEntryPath;

  public static final String SERIALIZED_NAME_HEALTH_CHECK = "health_check";
  @SerializedName(SERIALIZED_NAME_HEALTH_CHECK)
  private String healthCheck;

  public static final String SERIALIZED_NAME_DOCKER_IMAGE = "docker_image";
  @SerializedName(SERIALIZED_NAME_DOCKER_IMAGE)
  private String dockerImage;

  public static final String SERIALIZED_NAME_DOCKER_IMAGE_TAG = "docker_image_tag";
  @SerializedName(SERIALIZED_NAME_DOCKER_IMAGE_TAG)
  private String dockerImageTag;

  public static final String SERIALIZED_NAME_IS_RUSSIAN_TRUSTED_CA = "is_russian_trusted_ca";
  @SerializedName(SERIALIZED_NAME_IS_RUSSIAN_TRUSTED_CA)
  private Boolean isRussianTrustedCa;

  public static final String SERIALIZED_NAME_IS_TCI_TRUSTED_CA = "is_tci_trusted_ca";
  @SerializedName(SERIALIZED_NAME_IS_TCI_TRUSTED_CA)
  private Boolean isTciTrustedCa;

  public static final String SERIALIZED_NAME_BLOCKED_PORTS = "blocked_ports";
  @SerializedName(SERIALIZED_NAME_BLOCKED_PORTS)
  private List<Integer> blockedPorts;

  public static final String SERIALIZED_NAME_REVERSE_PROXY = "reverse_proxy";
  @SerializedName(SERIALIZED_NAME_REVERSE_PROXY)
  private Object reverseProxy;

  public AppV2() {
  }

  public AppV2 id(Integer id) {
    
    this.id = id;
    return this;
  }

   /**
   * ID для каждого экземпляра приложения. Автоматически генерируется при создании.
   * @return id
  **/
  @javax.annotation.Nonnull
  public Integer getId() {
    return id;
  }


  public void setId(Integer id) {
    this.id = id;
  }


  public AppV2 type(TypeEnum type) {
    
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


  public AppV2 name(String name) {
    
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


  public AppV2 status(StatusEnum status) {
    
    this.status = status;
    return this;
  }

   /**
   * Текущее состояние приложения. После принятия запроса на создание — new. Ответ 201 не означает завершение развёртывания.
   * @return status
  **/
  @javax.annotation.Nonnull
  public StatusEnum getStatus() {
    return status;
  }


  public void setStatus(StatusEnum status) {
    this.status = status;
  }


  public AppV2 provider(AppV2Provider provider) {
    
    this.provider = provider;
    return this;
  }

   /**
   * Get provider
   * @return provider
  **/
  @javax.annotation.Nonnull
  public AppV2Provider getProvider() {
    return provider;
  }


  public void setProvider(AppV2Provider provider) {
    this.provider = provider;
  }


  public AppV2 ip(String ip) {
    
    this.ip = ip;
    return this;
  }

   /**
   * Публичный IPv4-адрес. Отсутствует, пока сервер и IP-адрес не созданы.
   * @return ip
  **/
  @javax.annotation.Nullable
  public String getIp() {
    return ip;
  }


  public void setIp(String ip) {
    this.ip = ip;
  }


  public AppV2 domains(List<AppV2DomainsInner> domains) {
    
    this.domains = domains;
    return this;
  }

  public AppV2 addDomainsItem(AppV2DomainsInner domainsItem) {
    if (this.domains == null) {
      this.domains = new ArrayList<>();
    }
    this.domains.add(domainsItem);
    return this;
  }

   /**
   * Домены приложения. В начальном ответе на создание массив пуст.
   * @return domains
  **/
  @javax.annotation.Nonnull
  public List<AppV2DomainsInner> getDomains() {
    return domains;
  }


  public void setDomains(List<AppV2DomainsInner> domains) {
    this.domains = domains;
  }


  public AppV2 framework(FrameworkEnum framework) {
    
    this.framework = framework;
    return this;
  }

   /**
   * Фреймворк приложения. Поле может отсутствовать до создания инфраструктуры.
   * @return framework
  **/
  @javax.annotation.Nullable
  public FrameworkEnum getFramework() {
    return framework;
  }


  public void setFramework(FrameworkEnum framework) {
    this.framework = framework;
  }


  public AppV2 location(LocationEnum location) {
    
    this.location = location;
    return this;
  }

   /**
   * Локация сервера. Может отсутствовать до создания сервера.
   * @return location
  **/
  @javax.annotation.Nullable
  public LocationEnum getLocation() {
    return location;
  }


  public void setLocation(LocationEnum location) {
    this.location = location;
  }


  public AppV2 repository(Repository repository) {
    
    this.repository = repository;
    return this;
  }

   /**
   * Get repository
   * @return repository
  **/
  @javax.annotation.Nullable
  public Repository getRepository() {
    return repository;
  }


  public void setRepository(Repository repository) {
    this.repository = repository;
  }


  public AppV2 envVersion(String envVersion) {
    
    this.envVersion = envVersion;
    return this;
  }

   /**
   * Версия окружения. Поле может отсутствовать до создания инфраструктуры.
   * @return envVersion
  **/
  @javax.annotation.Nullable
  public String getEnvVersion() {
    return envVersion;
  }


  public void setEnvVersion(String envVersion) {
    this.envVersion = envVersion;
  }


  public AppV2 envs(Map<String, String> envs) {
    
    this.envs = envs;
    return this;
  }

  public AppV2 putEnvsItem(String key, String envsItem) {
    if (this.envs == null) {
      this.envs = new HashMap<>();
    }
    this.envs.put(key, envsItem);
    return this;
  }

   /**
   * Переменные окружения приложения. Объект с ключами и значениями типа string. Поле может отсутствовать до создания инфраструктуры.
   * @return envs
  **/
  @javax.annotation.Nullable
  public Map<String, String> getEnvs() {
    return envs;
  }


  public void setEnvs(Map<String, String> envs) {
    this.envs = envs;
  }


  public AppV2 isAutoDeploy(Boolean isAutoDeploy) {
    
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


  public AppV2 commitSha(String commitSha) {
    
    this.commitSha = commitSha;
    return this;
  }

   /**
   * Хэш коммита, из которого собрано приложение.
   * @return commitSha
  **/
  @javax.annotation.Nullable
  public String getCommitSha() {
    return commitSha;
  }


  public void setCommitSha(String commitSha) {
    this.commitSha = commitSha;
  }


  public AppV2 comment(String comment) {
    
    this.comment = comment;
    return this;
  }

   /**
   * Комментарий к приложению.
   * @return comment
  **/
  @javax.annotation.Nullable
  public String getComment() {
    return comment;
  }


  public void setComment(String comment) {
    this.comment = comment;
  }


  public AppV2 presetId(BigDecimal presetId) {
    
    this.presetId = presetId;
    return this;
  }

   /**
   * ID тарифа приложения; 0 при использовании конфигуратора.
   * @return presetId
  **/
  @javax.annotation.Nonnull
  public BigDecimal getPresetId() {
    return presetId;
  }


  public void setPresetId(BigDecimal presetId) {
    this.presetId = presetId;
  }


  public AppV2 indexDir(String indexDir) {
    
    this.indexDir = indexDir;
    return this;
  }

   /**
   * Каталог со статическими файлами frontend-приложения. До создания инфраструктуры поле отсутствует. Для backend — null.
   * @return indexDir
  **/
  @javax.annotation.Nullable
  public String getIndexDir() {
    return indexDir;
  }


  public void setIndexDir(String indexDir) {
    this.indexDir = indexDir;
  }


  public AppV2 buildCmd(String buildCmd) {
    
    this.buildCmd = buildCmd;
    return this;
  }

   /**
   * Команда сборки приложения. Поле может отсутствовать до создания инфраструктуры.
   * @return buildCmd
  **/
  @javax.annotation.Nullable
  public String getBuildCmd() {
    return buildCmd;
  }


  public void setBuildCmd(String buildCmd) {
    this.buildCmd = buildCmd;
  }


  public AppV2 avatarLink(String avatarLink) {
    
    this.avatarLink = avatarLink;
    return this;
  }

   /**
   * Ссылка на аватар приложения.
   * @return avatarLink
  **/
  @javax.annotation.Nullable
  public String getAvatarLink() {
    return avatarLink;
  }


  public void setAvatarLink(String avatarLink) {
    this.avatarLink = avatarLink;
  }


  public AppV2 runCmd(String runCmd) {
    
    this.runCmd = runCmd;
    return this;
  }

   /**
   * Команда запуска backend-приложения. До создания инфраструктуры поле отсутствует. Для frontend — null.
   * @return runCmd
  **/
  @javax.annotation.Nullable
  public String getRunCmd() {
    return runCmd;
  }


  public void setRunCmd(String runCmd) {
    this.runCmd = runCmd;
  }


  public AppV2 _configuration(AppV2Configuration _configuration) {
    
    this._configuration = _configuration;
    return this;
  }

   /**
   * Get _configuration
   * @return _configuration
  **/
  @javax.annotation.Nullable
  public AppV2Configuration getConfiguration() {
    return _configuration;
  }


  public void setConfiguration(AppV2Configuration _configuration) {
    this._configuration = _configuration;
  }


  public AppV2 diskStatus(AppV2DiskStatus diskStatus) {
    
    this.diskStatus = diskStatus;
    return this;
  }

   /**
   * Get diskStatus
   * @return diskStatus
  **/
  @javax.annotation.Nullable
  public AppV2DiskStatus getDiskStatus() {
    return diskStatus;
  }


  public void setDiskStatus(AppV2DiskStatus diskStatus) {
    this.diskStatus = diskStatus;
  }


  public AppV2 isQemuAgent(Boolean isQemuAgent) {
    
    this.isQemuAgent = isQemuAgent;
    return this;
  }

   /**
   * Включён ли агент QEMU. Для frontend — null. Для backend поле отсутствует до создания сервера.
   * @return isQemuAgent
  **/
  @javax.annotation.Nullable
  public Boolean getIsQemuAgent() {
    return isQemuAgent;
  }


  public void setIsQemuAgent(Boolean isQemuAgent) {
    this.isQemuAgent = isQemuAgent;
  }


  public AppV2 language(LanguageEnum language) {
    
    this.language = language;
    return this;
  }

   /**
   * Язык или способ развёртывания. Может отсутствовать до создания инфраструктуры. Поле может отсутствовать до создания инфраструктуры.
   * @return language
  **/
  @javax.annotation.Nullable
  public LanguageEnum getLanguage() {
    return language;
  }


  public void setLanguage(LanguageEnum language) {
    this.language = language;
  }


  public AppV2 startTime(OffsetDateTime startTime) {
    
    this.startTime = startTime;
    return this;
  }

   /**
   * Время последнего изменения записи приложения.
   * @return startTime
  **/
  @javax.annotation.Nonnull
  public OffsetDateTime getStartTime() {
    return startTime;
  }


  public void setStartTime(OffsetDateTime startTime) {
    this.startTime = startTime;
  }


  public AppV2 branch(String branch) {
    
    this.branch = branch;
    return this;
  }

   /**
   * Ветка Git-репозитория. В ответе v2 поле называется branch.
   * @return branch
  **/
  @javax.annotation.Nullable
  public String getBranch() {
    return branch;
  }


  public void setBranch(String branch) {
    this.branch = branch;
  }


  public AppV2 configuratorId(Integer configuratorId) {
    
    this.configuratorId = configuratorId;
    return this;
  }

   /**
   * ID конфигуратора приложения; null при использовании готового тарифа.
   * @return configuratorId
  **/
  @javax.annotation.Nullable
  public Integer getConfiguratorId() {
    return configuratorId;
  }


  public void setConfiguratorId(Integer configuratorId) {
    this.configuratorId = configuratorId;
  }


  public AppV2 projectId(Integer projectId) {
    
    this.projectId = projectId;
    return this;
  }

   /**
   * ID проекта приложения. Может отсутствовать, если проект не задан.
   * @return projectId
  **/
  @javax.annotation.Nullable
  public Integer getProjectId() {
    return projectId;
  }


  public void setProjectId(Integer projectId) {
    this.projectId = projectId;
  }


  public AppV2 systemDependencies(List<String> systemDependencies) {
    
    this.systemDependencies = systemDependencies;
    return this;
  }

  public AppV2 addSystemDependenciesItem(String systemDependenciesItem) {
    if (this.systemDependencies == null) {
      this.systemDependencies = new ArrayList<>();
    }
    this.systemDependencies.add(systemDependenciesItem);
    return this;
  }

   /**
   * Системные пакеты приложения. Поле отсутствует, если зависимости не заданы.
   * @return systemDependencies
  **/
  @javax.annotation.Nullable
  public List<String> getSystemDependencies() {
    return systemDependencies;
  }


  public void setSystemDependencies(List<String> systemDependencies) {
    this.systemDependencies = systemDependencies;
  }


  public AppV2 createdAt(OffsetDateTime createdAt) {
    
    this.createdAt = createdAt;
    return this;
  }

   /**
   * Время создания записи приложения.
   * @return createdAt
  **/
  @javax.annotation.Nonnull
  public OffsetDateTime getCreatedAt() {
    return createdAt;
  }


  public void setCreatedAt(OffsetDateTime createdAt) {
    this.createdAt = createdAt;
  }


  public AppV2 availabilityZone(AvailabilityZoneEnum availabilityZone) {
    
    this.availabilityZone = availabilityZone;
    return this;
  }

   /**
   * Зона доступности сервера. Может быть null или отсутствовать до создания сервера.
   * @return availabilityZone
  **/
  @javax.annotation.Nullable
  public AvailabilityZoneEnum getAvailabilityZone() {
    return availabilityZone;
  }


  public void setAvailabilityZone(AvailabilityZoneEnum availabilityZone) {
    this.availabilityZone = availabilityZone;
  }


  public AppV2 requestsLimit(Integer requestsLimit) {
    
    this.requestsLimit = requestsLimit;
    return this;
  }

   /**
   * Ограничение количества запросов; null, если индивидуальный лимит не задан.
   * @return requestsLimit
  **/
  @javax.annotation.Nullable
  public Integer getRequestsLimit() {
    return requestsLimit;
  }


  public void setRequestsLimit(Integer requestsLimit) {
    this.requestsLimit = requestsLimit;
  }


  public AppV2 buildEntryPath(String buildEntryPath) {
    
    this.buildEntryPath = buildEntryPath;
    return this;
  }

   /**
   * Каталог приложения в Git-репозитории, относительно которого выполняется сборка. Может отсутствовать до создания инфраструктуры.
   * @return buildEntryPath
  **/
  @javax.annotation.Nullable
  public String getBuildEntryPath() {
    return buildEntryPath;
  }


  public void setBuildEntryPath(String buildEntryPath) {
    this.buildEntryPath = buildEntryPath;
  }


  public AppV2 healthCheck(String healthCheck) {
    
    this.healthCheck = healthCheck;
    return this;
  }

   /**
   * HTTP-путь для проверки работоспособности backend-приложения. Может отсутствовать до создания инфраструктуры.
   * @return healthCheck
  **/
  @javax.annotation.Nullable
  public String getHealthCheck() {
    return healthCheck;
  }


  public void setHealthCheck(String healthCheck) {
    this.healthCheck = healthCheck;
  }


  public AppV2 dockerImage(String dockerImage) {
    
    this.dockerImage = dockerImage;
    return this;
  }

   /**
   * Имя готового Docker-образа без тега. Присутствует для backend из готового Docker-образа.
   * @return dockerImage
  **/
  @javax.annotation.Nullable
  public String getDockerImage() {
    return dockerImage;
  }


  public void setDockerImage(String dockerImage) {
    this.dockerImage = dockerImage;
  }


  public AppV2 dockerImageTag(String dockerImageTag) {
    
    this.dockerImageTag = dockerImageTag;
    return this;
  }

   /**
   * Тег готового Docker-образа. Присутствует для backend из готового Docker-образа.
   * @return dockerImageTag
  **/
  @javax.annotation.Nullable
  public String getDockerImageTag() {
    return dockerImageTag;
  }


  public void setDockerImageTag(String dockerImageTag) {
    this.dockerImageTag = dockerImageTag;
  }


  public AppV2 isRussianTrustedCa(Boolean isRussianTrustedCa) {
    
    this.isRussianTrustedCa = isRussianTrustedCa;
    return this;
  }

   /**
   * Добавлены ли доверенные корневые сертификаты Минцифры в окружение backend-приложения. Поле возвращается в российском регионе после создания инфраструктуры.
   * @return isRussianTrustedCa
  **/
  @javax.annotation.Nullable
  public Boolean getIsRussianTrustedCa() {
    return isRussianTrustedCa;
  }


  public void setIsRussianTrustedCa(Boolean isRussianTrustedCa) {
    this.isRussianTrustedCa = isRussianTrustedCa;
  }


  public AppV2 isTciTrustedCa(Boolean isTciTrustedCa) {
    
    this.isTciTrustedCa = isTciTrustedCa;
    return this;
  }

   /**
   * Добавлены ли доверенные корневые сертификаты ТЦИ в окружение backend-приложения. Поле возвращается в российском регионе после создания инфраструктуры.
   * @return isTciTrustedCa
  **/
  @javax.annotation.Nullable
  public Boolean getIsTciTrustedCa() {
    return isTciTrustedCa;
  }


  public void setIsTciTrustedCa(Boolean isTciTrustedCa) {
    this.isTciTrustedCa = isTciTrustedCa;
  }


  public AppV2 blockedPorts(List<Integer> blockedPorts) {
    
    this.blockedPorts = blockedPorts;
    return this;
  }

  public AppV2 addBlockedPortsItem(Integer blockedPortsItem) {
    if (this.blockedPorts == null) {
      this.blockedPorts = new ArrayList<>();
    }
    this.blockedPorts.add(blockedPortsItem);
    return this;
  }

   /**
   * Заблокированные порты backend-приложения. Поле может отсутствовать до создания сервера.
   * @return blockedPorts
  **/
  @javax.annotation.Nullable
  public List<Integer> getBlockedPorts() {
    return blockedPorts;
  }


  public void setBlockedPorts(List<Integer> blockedPorts) {
    this.blockedPorts = blockedPorts;
  }


  public AppV2 reverseProxy(Object reverseProxy) {
    
    this.reverseProxy = reverseProxy;
    return this;
  }

   /**
   * Настройки обратного прокси. В начальном ответе на создание — null.
   * @return reverseProxy
  **/
  @javax.annotation.Nullable
  public Object getReverseProxy() {
    return reverseProxy;
  }


  public void setReverseProxy(Object reverseProxy) {
    this.reverseProxy = reverseProxy;
  }



  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AppV2 appV2 = (AppV2) o;
    return Objects.equals(this.id, appV2.id) &&
        Objects.equals(this.type, appV2.type) &&
        Objects.equals(this.name, appV2.name) &&
        Objects.equals(this.status, appV2.status) &&
        Objects.equals(this.provider, appV2.provider) &&
        Objects.equals(this.ip, appV2.ip) &&
        Objects.equals(this.domains, appV2.domains) &&
        Objects.equals(this.framework, appV2.framework) &&
        Objects.equals(this.location, appV2.location) &&
        Objects.equals(this.repository, appV2.repository) &&
        Objects.equals(this.envVersion, appV2.envVersion) &&
        Objects.equals(this.envs, appV2.envs) &&
        Objects.equals(this.isAutoDeploy, appV2.isAutoDeploy) &&
        Objects.equals(this.commitSha, appV2.commitSha) &&
        Objects.equals(this.comment, appV2.comment) &&
        Objects.equals(this.presetId, appV2.presetId) &&
        Objects.equals(this.indexDir, appV2.indexDir) &&
        Objects.equals(this.buildCmd, appV2.buildCmd) &&
        Objects.equals(this.avatarLink, appV2.avatarLink) &&
        Objects.equals(this.runCmd, appV2.runCmd) &&
        Objects.equals(this._configuration, appV2._configuration) &&
        Objects.equals(this.diskStatus, appV2.diskStatus) &&
        Objects.equals(this.isQemuAgent, appV2.isQemuAgent) &&
        Objects.equals(this.language, appV2.language) &&
        Objects.equals(this.startTime, appV2.startTime) &&
        Objects.equals(this.branch, appV2.branch) &&
        Objects.equals(this.configuratorId, appV2.configuratorId) &&
        Objects.equals(this.projectId, appV2.projectId) &&
        Objects.equals(this.systemDependencies, appV2.systemDependencies) &&
        Objects.equals(this.createdAt, appV2.createdAt) &&
        Objects.equals(this.availabilityZone, appV2.availabilityZone) &&
        Objects.equals(this.requestsLimit, appV2.requestsLimit) &&
        Objects.equals(this.buildEntryPath, appV2.buildEntryPath) &&
        Objects.equals(this.healthCheck, appV2.healthCheck) &&
        Objects.equals(this.dockerImage, appV2.dockerImage) &&
        Objects.equals(this.dockerImageTag, appV2.dockerImageTag) &&
        Objects.equals(this.isRussianTrustedCa, appV2.isRussianTrustedCa) &&
        Objects.equals(this.isTciTrustedCa, appV2.isTciTrustedCa) &&
        Objects.equals(this.blockedPorts, appV2.blockedPorts) &&
        Objects.equals(this.reverseProxy, appV2.reverseProxy);
  }

  private static <T> boolean equalsNullable(JsonNullable<T> a, JsonNullable<T> b) {
    return a == b || (a != null && b != null && a.isPresent() && b.isPresent() && Objects.deepEquals(a.get(), b.get()));
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, type, name, status, provider, ip, domains, framework, location, repository, envVersion, envs, isAutoDeploy, commitSha, comment, presetId, indexDir, buildCmd, avatarLink, runCmd, _configuration, diskStatus, isQemuAgent, language, startTime, branch, configuratorId, projectId, systemDependencies, createdAt, availabilityZone, requestsLimit, buildEntryPath, healthCheck, dockerImage, dockerImageTag, isRussianTrustedCa, isTciTrustedCa, blockedPorts, reverseProxy);
  }

  private static <T> int hashCodeNullable(JsonNullable<T> a) {
    if (a == null) {
      return 1;
    }
    return a.isPresent() ? Arrays.deepHashCode(new Object[]{a.get()}) : 31;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AppV2 {\n");
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
    sb.append("    isAutoDeploy: ").append(toIndentedString(isAutoDeploy)).append("\n");
    sb.append("    commitSha: ").append(toIndentedString(commitSha)).append("\n");
    sb.append("    comment: ").append(toIndentedString(comment)).append("\n");
    sb.append("    presetId: ").append(toIndentedString(presetId)).append("\n");
    sb.append("    indexDir: ").append(toIndentedString(indexDir)).append("\n");
    sb.append("    buildCmd: ").append(toIndentedString(buildCmd)).append("\n");
    sb.append("    avatarLink: ").append(toIndentedString(avatarLink)).append("\n");
    sb.append("    runCmd: ").append(toIndentedString(runCmd)).append("\n");
    sb.append("    _configuration: ").append(toIndentedString(_configuration)).append("\n");
    sb.append("    diskStatus: ").append(toIndentedString(diskStatus)).append("\n");
    sb.append("    isQemuAgent: ").append(toIndentedString(isQemuAgent)).append("\n");
    sb.append("    language: ").append(toIndentedString(language)).append("\n");
    sb.append("    startTime: ").append(toIndentedString(startTime)).append("\n");
    sb.append("    branch: ").append(toIndentedString(branch)).append("\n");
    sb.append("    configuratorId: ").append(toIndentedString(configuratorId)).append("\n");
    sb.append("    projectId: ").append(toIndentedString(projectId)).append("\n");
    sb.append("    systemDependencies: ").append(toIndentedString(systemDependencies)).append("\n");
    sb.append("    createdAt: ").append(toIndentedString(createdAt)).append("\n");
    sb.append("    availabilityZone: ").append(toIndentedString(availabilityZone)).append("\n");
    sb.append("    requestsLimit: ").append(toIndentedString(requestsLimit)).append("\n");
    sb.append("    buildEntryPath: ").append(toIndentedString(buildEntryPath)).append("\n");
    sb.append("    healthCheck: ").append(toIndentedString(healthCheck)).append("\n");
    sb.append("    dockerImage: ").append(toIndentedString(dockerImage)).append("\n");
    sb.append("    dockerImageTag: ").append(toIndentedString(dockerImageTag)).append("\n");
    sb.append("    isRussianTrustedCa: ").append(toIndentedString(isRussianTrustedCa)).append("\n");
    sb.append("    isTciTrustedCa: ").append(toIndentedString(isTciTrustedCa)).append("\n");
    sb.append("    blockedPorts: ").append(toIndentedString(blockedPorts)).append("\n");
    sb.append("    reverseProxy: ").append(toIndentedString(reverseProxy)).append("\n");
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
    openapiFields.add("is_auto_deploy");
    openapiFields.add("commit_sha");
    openapiFields.add("comment");
    openapiFields.add("preset_id");
    openapiFields.add("index_dir");
    openapiFields.add("build_cmd");
    openapiFields.add("avatar_link");
    openapiFields.add("run_cmd");
    openapiFields.add("configuration");
    openapiFields.add("disk_status");
    openapiFields.add("is_qemu_agent");
    openapiFields.add("language");
    openapiFields.add("start_time");
    openapiFields.add("branch");
    openapiFields.add("configurator_id");
    openapiFields.add("project_id");
    openapiFields.add("system_dependencies");
    openapiFields.add("created_at");
    openapiFields.add("availability_zone");
    openapiFields.add("requests_limit");
    openapiFields.add("build_entry_path");
    openapiFields.add("health_check");
    openapiFields.add("docker_image");
    openapiFields.add("docker_image_tag");
    openapiFields.add("is_russian_trusted_ca");
    openapiFields.add("is_tci_trusted_ca");
    openapiFields.add("blocked_ports");
    openapiFields.add("reverse_proxy");

    // a set of required properties/fields (JSON key names)
    openapiRequiredFields = new HashSet<String>();
    openapiRequiredFields.add("id");
    openapiRequiredFields.add("type");
    openapiRequiredFields.add("name");
    openapiRequiredFields.add("status");
    openapiRequiredFields.add("provider");
    openapiRequiredFields.add("domains");
    openapiRequiredFields.add("is_auto_deploy");
    openapiRequiredFields.add("preset_id");
    openapiRequiredFields.add("avatar_link");
    openapiRequiredFields.add("start_time");
    openapiRequiredFields.add("configurator_id");
    openapiRequiredFields.add("created_at");
    openapiRequiredFields.add("requests_limit");
    openapiRequiredFields.add("reverse_proxy");
  }

 /**
  * Validates the JSON Element and throws an exception if issues found
  *
  * @param jsonElement JSON Element
  * @throws IOException if the JSON Element is invalid with respect to AppV2
  */
  public static void validateJsonElement(JsonElement jsonElement) throws IOException {
      if (jsonElement == null) {
        if (!AppV2.openapiRequiredFields.isEmpty()) { // has required fields but JSON element is null
          throw new IllegalArgumentException(String.format("The required field(s) %s in AppV2 is not found in the empty JSON string", AppV2.openapiRequiredFields.toString()));
        }
      }

      Set<Entry<String, JsonElement>> entries = jsonElement.getAsJsonObject().entrySet();
      // check to see if the JSON string contains additional fields
      for (Entry<String, JsonElement> entry : entries) {
        if (!AppV2.openapiFields.contains(entry.getKey())) {
          throw new IllegalArgumentException(String.format("The field `%s` in the JSON string is not defined in the `AppV2` properties. JSON: %s", entry.getKey(), jsonElement.toString()));
        }
      }

      // check to make sure all required properties/fields are present in the JSON string
      for (String requiredField : AppV2.openapiRequiredFields) {
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
      AppV2Provider.validateJsonElement(jsonObj.get("provider"));
      if ((jsonObj.get("ip") != null && !jsonObj.get("ip").isJsonNull()) && !jsonObj.get("ip").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `ip` to be a primitive type in the JSON string but got `%s`", jsonObj.get("ip").toString()));
      }
      // ensure the json data is an array
      if (!jsonObj.get("domains").isJsonArray()) {
        throw new IllegalArgumentException(String.format("Expected the field `domains` to be an array in the JSON string but got `%s`", jsonObj.get("domains").toString()));
      }

      JsonArray jsonArraydomains = jsonObj.getAsJsonArray("domains");
      // validate the required field `domains` (array)
      for (int i = 0; i < jsonArraydomains.size(); i++) {
        AppV2DomainsInner.validateJsonElement(jsonArraydomains.get(i));
      };
      if ((jsonObj.get("framework") != null && !jsonObj.get("framework").isJsonNull()) && !jsonObj.get("framework").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `framework` to be a primitive type in the JSON string but got `%s`", jsonObj.get("framework").toString()));
      }
      if ((jsonObj.get("location") != null && !jsonObj.get("location").isJsonNull()) && !jsonObj.get("location").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `location` to be a primitive type in the JSON string but got `%s`", jsonObj.get("location").toString()));
      }
      // validate the optional field `repository`
      if (jsonObj.get("repository") != null && !jsonObj.get("repository").isJsonNull()) {
        Repository.validateJsonElement(jsonObj.get("repository"));
      }
      if ((jsonObj.get("env_version") != null && !jsonObj.get("env_version").isJsonNull()) && !jsonObj.get("env_version").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `env_version` to be a primitive type in the JSON string but got `%s`", jsonObj.get("env_version").toString()));
      }
      if ((jsonObj.get("commit_sha") != null && !jsonObj.get("commit_sha").isJsonNull()) && !jsonObj.get("commit_sha").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `commit_sha` to be a primitive type in the JSON string but got `%s`", jsonObj.get("commit_sha").toString()));
      }
      if ((jsonObj.get("comment") != null && !jsonObj.get("comment").isJsonNull()) && !jsonObj.get("comment").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `comment` to be a primitive type in the JSON string but got `%s`", jsonObj.get("comment").toString()));
      }
      if ((jsonObj.get("index_dir") != null && !jsonObj.get("index_dir").isJsonNull()) && !jsonObj.get("index_dir").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `index_dir` to be a primitive type in the JSON string but got `%s`", jsonObj.get("index_dir").toString()));
      }
      if ((jsonObj.get("build_cmd") != null && !jsonObj.get("build_cmd").isJsonNull()) && !jsonObj.get("build_cmd").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `build_cmd` to be a primitive type in the JSON string but got `%s`", jsonObj.get("build_cmd").toString()));
      }
      if ((jsonObj.get("avatar_link") != null && !jsonObj.get("avatar_link").isJsonNull()) && !jsonObj.get("avatar_link").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `avatar_link` to be a primitive type in the JSON string but got `%s`", jsonObj.get("avatar_link").toString()));
      }
      if ((jsonObj.get("run_cmd") != null && !jsonObj.get("run_cmd").isJsonNull()) && !jsonObj.get("run_cmd").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `run_cmd` to be a primitive type in the JSON string but got `%s`", jsonObj.get("run_cmd").toString()));
      }
      // validate the optional field `configuration`
      if (jsonObj.get("configuration") != null && !jsonObj.get("configuration").isJsonNull()) {
        AppV2Configuration.validateJsonElement(jsonObj.get("configuration"));
      }
      // validate the optional field `disk_status`
      if (jsonObj.get("disk_status") != null && !jsonObj.get("disk_status").isJsonNull()) {
        AppV2DiskStatus.validateJsonElement(jsonObj.get("disk_status"));
      }
      if ((jsonObj.get("language") != null && !jsonObj.get("language").isJsonNull()) && !jsonObj.get("language").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `language` to be a primitive type in the JSON string but got `%s`", jsonObj.get("language").toString()));
      }
      if ((jsonObj.get("branch") != null && !jsonObj.get("branch").isJsonNull()) && !jsonObj.get("branch").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `branch` to be a primitive type in the JSON string but got `%s`", jsonObj.get("branch").toString()));
      }
      // ensure the optional json data is an array if present
      if (jsonObj.get("system_dependencies") != null && !jsonObj.get("system_dependencies").isJsonArray()) {
        throw new IllegalArgumentException(String.format("Expected the field `system_dependencies` to be an array in the JSON string but got `%s`", jsonObj.get("system_dependencies").toString()));
      }
      if ((jsonObj.get("availability_zone") != null && !jsonObj.get("availability_zone").isJsonNull()) && !jsonObj.get("availability_zone").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `availability_zone` to be a primitive type in the JSON string but got `%s`", jsonObj.get("availability_zone").toString()));
      }
      if ((jsonObj.get("build_entry_path") != null && !jsonObj.get("build_entry_path").isJsonNull()) && !jsonObj.get("build_entry_path").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `build_entry_path` to be a primitive type in the JSON string but got `%s`", jsonObj.get("build_entry_path").toString()));
      }
      if ((jsonObj.get("health_check") != null && !jsonObj.get("health_check").isJsonNull()) && !jsonObj.get("health_check").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `health_check` to be a primitive type in the JSON string but got `%s`", jsonObj.get("health_check").toString()));
      }
      if ((jsonObj.get("docker_image") != null && !jsonObj.get("docker_image").isJsonNull()) && !jsonObj.get("docker_image").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `docker_image` to be a primitive type in the JSON string but got `%s`", jsonObj.get("docker_image").toString()));
      }
      if ((jsonObj.get("docker_image_tag") != null && !jsonObj.get("docker_image_tag").isJsonNull()) && !jsonObj.get("docker_image_tag").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `docker_image_tag` to be a primitive type in the JSON string but got `%s`", jsonObj.get("docker_image_tag").toString()));
      }
      // ensure the optional json data is an array if present
      if (jsonObj.get("blocked_ports") != null && !jsonObj.get("blocked_ports").isJsonArray()) {
        throw new IllegalArgumentException(String.format("Expected the field `blocked_ports` to be an array in the JSON string but got `%s`", jsonObj.get("blocked_ports").toString()));
      }
  }

  public static class CustomTypeAdapterFactory implements TypeAdapterFactory {
    @SuppressWarnings("unchecked")
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
       if (!AppV2.class.isAssignableFrom(type.getRawType())) {
         return null; // this class only serializes 'AppV2' and its subtypes
       }
       final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);
       final TypeAdapter<AppV2> thisAdapter
                        = gson.getDelegateAdapter(this, TypeToken.get(AppV2.class));

       return (TypeAdapter<T>) new TypeAdapter<AppV2>() {
           @Override
           public void write(JsonWriter out, AppV2 value) throws IOException {
             JsonObject obj = thisAdapter.toJsonTree(value).getAsJsonObject();
             elementAdapter.write(out, obj);
           }

           @Override
           public AppV2 read(JsonReader in) throws IOException {
             JsonElement jsonElement = elementAdapter.read(in);
             validateJsonElement(jsonElement);
             return thisAdapter.fromJsonTree(jsonElement);
           }

       }.nullSafe();
    }
  }

 /**
  * Create an instance of AppV2 given an JSON string
  *
  * @param jsonString JSON string
  * @return An instance of AppV2
  * @throws IOException if the JSON string is invalid with respect to AppV2
  */
  public static AppV2 fromJson(String jsonString) throws IOException {
    return JSON.getGson().fromJson(jsonString, AppV2.class);
  }

 /**
  * Convert an instance of AppV2 to an JSON string
  *
  * @return JSON string
  */
  public String toJson() {
    return JSON.getGson().toJson(this);
  }
}

