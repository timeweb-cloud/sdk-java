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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.openapitools.client.model.CreateAppV2Configuration;
import org.openapitools.client.model.CreateAppV2Network;
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
 * Параметры создания приложения. Для Git-репозитория обязательны provider_id, repository_id, branch_name, commit_sha, build_cmd, is_auto_deploy и framework. Для frontend также обязательны index_dir и preset_id. Для backend из Git обязателен run_cmd. Для backend из готового Docker-образа передайте docker_image и docker_image_tag; Git-параметры не требуются. Для любого backend передайте либо preset_id, либо configuration. Эти поля нельзя передавать одновременно.
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2026-10-30T15:03:29.616934Z[Etc/UTC]")
public class CreateAppV2 {
  public static final String SERIALIZED_NAME_PROVIDER_ID = "provider_id";
  @SerializedName(SERIALIZED_NAME_PROVIDER_ID)
  private String providerId;

  /**
   * Тип приложения: frontend — статический сайт, backend — серверное приложение или Docker-образ.
   */
  @JsonAdapter(TypeEnum.Adapter.class)
  public enum TypeEnum {
    FRONTEND("frontend"),
    
    BACKEND("backend");

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

  public static final String SERIALIZED_NAME_REPOSITORY_ID = "repository_id";
  @SerializedName(SERIALIZED_NAME_REPOSITORY_ID)
  private String repositoryId;

  public static final String SERIALIZED_NAME_BUILD_CMD = "build_cmd";
  @SerializedName(SERIALIZED_NAME_BUILD_CMD)
  private String buildCmd;

  public static final String SERIALIZED_NAME_ENVS = "envs";
  @SerializedName(SERIALIZED_NAME_ENVS)
  private Map<String, String> envs = new HashMap<>();

  public static final String SERIALIZED_NAME_BRANCH_NAME = "branch_name";
  @SerializedName(SERIALIZED_NAME_BRANCH_NAME)
  private String branchName;

  public static final String SERIALIZED_NAME_IS_AUTO_DEPLOY = "is_auto_deploy";
  @SerializedName(SERIALIZED_NAME_IS_AUTO_DEPLOY)
  private Boolean isAutoDeploy;

  public static final String SERIALIZED_NAME_COMMIT_SHA = "commit_sha";
  @SerializedName(SERIALIZED_NAME_COMMIT_SHA)
  private String commitSha;

  public static final String SERIALIZED_NAME_NAME = "name";
  @SerializedName(SERIALIZED_NAME_NAME)
  private String name;

  public static final String SERIALIZED_NAME_COMMENT = "comment";
  @SerializedName(SERIALIZED_NAME_COMMENT)
  private String comment;

  public static final String SERIALIZED_NAME_PRESET_ID = "preset_id";
  @SerializedName(SERIALIZED_NAME_PRESET_ID)
  private Integer presetId;

  public static final String SERIALIZED_NAME_ENV_VERSION = "env_version";
  @SerializedName(SERIALIZED_NAME_ENV_VERSION)
  private String envVersion;

  /**
   * Фреймворк приложения, строка с учётом регистра. Обязателен при создании из Git. Для frontend: &#x60;angular&#x60;, &#x60;astro&#x60;, &#x60;ember&#x60;, &#x60;next.js&#x60;, &#x60;nuxt&#x60;, &#x60;preact&#x60;, &#x60;react&#x60;, &#x60;remix&#x60;, &#x60;solid&#x60;, &#x60;solid-start&#x60;, &#x60;svelte&#x60;, &#x60;sveltekit&#x60;, &#x60;tanstack-start&#x60;, &#x60;vue&#x60;, &#x60;static-nobuild&#x60;, &#x60;another&#x60;. Для backend: &#x60;django&#x60;, &#x60;express&#x60;, &#x60;phoenix&#x60;, &#x60;Spring&#x60;, &#x60;laravel&#x60;, &#x60;beego&#x60;, &#x60;fastapi&#x60;, &#x60;ASP.NET Core&#x60;, &#x60;hapi&#x60;, &#x60;celery&#x60;, &#x60;flask&#x60;, &#x60;gin&#x60;, &#x60;docker&#x60;, &#x60;docker-compose&#x60;, &#x60;fastify&#x60;, &#x60;nest&#x60;, &#x60;symfony&#x60;, &#x60;yii&#x60;, &#x60;next.js&#x60;, &#x60;nuxt&#x60;, &#x60;astro&#x60;, &#x60;tanstack-start&#x60;, &#x60;solid-start&#x60;, &#x60;ktor&#x60;, &#x60;axum&#x60;, &#x60;actix&#x60;, &#x60;tornado&#x60;, &#x60;aiohttp&#x60;, &#x60;fiber&#x60;, &#x60;echo&#x60;, &#x60;Micronaut&#x60;, &#x60;Quarkus&#x60;, &#x60;remix&#x60;, &#x60;sveltekit&#x60;. Для готового Docker-образа не требуется.
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

  public static final String SERIALIZED_NAME_INDEX_DIR = "index_dir";
  @SerializedName(SERIALIZED_NAME_INDEX_DIR)
  private String indexDir;

  public static final String SERIALIZED_NAME_RUN_CMD = "run_cmd";
  @SerializedName(SERIALIZED_NAME_RUN_CMD)
  private String runCmd;

  public static final String SERIALIZED_NAME_SYSTEM_DEPENDENCIES = "system_dependencies";
  @SerializedName(SERIALIZED_NAME_SYSTEM_DEPENDENCIES)
  private List<String> systemDependencies;

  public static final String SERIALIZED_NAME_PROJECT_ID = "project_id";
  @SerializedName(SERIALIZED_NAME_PROJECT_ID)
  private Integer projectId;

  public static final String SERIALIZED_NAME_CONFIGURATION = "configuration";
  @SerializedName(SERIALIZED_NAME_CONFIGURATION)
  private CreateAppV2Configuration _configuration;

  /**
   * Язык или способ развёртывания. Обязателен в v2. Для frontend: &#x60;javascript&#x60;. Для backend: &#x60;node&#x60;, &#x60;elixir&#x60;, &#x60;go&#x60;, &#x60;php&#x60;, &#x60;python&#x60;, &#x60;ruby&#x60;, &#x60;docker&#x60;, &#x60;docker-compose&#x60;, &#x60;dotnet&#x60;, &#x60;java&#x60;, &#x60;kotlin&#x60;, &#x60;rust&#x60;, &#x60;bun&#x60;, &#x60;deno&#x60;, &#x60;custom-image&#x60;. Для SSR-приложений из Git используйте node, для готового Docker-образа — custom-image.
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

  public static final String SERIALIZED_NAME_BUILD_ENTRY_PATH = "build_entry_path";
  @SerializedName(SERIALIZED_NAME_BUILD_ENTRY_PATH)
  private String buildEntryPath;

  public static final String SERIALIZED_NAME_NETWORK = "network";
  @SerializedName(SERIALIZED_NAME_NETWORK)
  private CreateAppV2Network network;

  public static final String SERIALIZED_NAME_HEALTH_CHECK = "health_check";
  @SerializedName(SERIALIZED_NAME_HEALTH_CHECK)
  private String healthCheck;

  public static final String SERIALIZED_NAME_GLOBAL_ENV_IDS = "global_env_ids";
  @SerializedName(SERIALIZED_NAME_GLOBAL_ENV_IDS)
  private List<BigDecimal> globalEnvIds;

  public static final String SERIALIZED_NAME_DOCKER_IMAGE = "docker_image";
  @SerializedName(SERIALIZED_NAME_DOCKER_IMAGE)
  private String dockerImage;

  public static final String SERIALIZED_NAME_DOCKER_IMAGE_TAG = "docker_image_tag";
  @SerializedName(SERIALIZED_NAME_DOCKER_IMAGE_TAG)
  private String dockerImageTag;

  public static final String SERIALIZED_NAME_IS_RUSSIAN_TRUSTED_CA = "is_russian_trusted_ca";
  @SerializedName(SERIALIZED_NAME_IS_RUSSIAN_TRUSTED_CA)
  private Boolean isRussianTrustedCa = false;

  public static final String SERIALIZED_NAME_IS_TCI_TRUSTED_CA = "is_tci_trusted_ca";
  @SerializedName(SERIALIZED_NAME_IS_TCI_TRUSTED_CA)
  private Boolean isTciTrustedCa = false;

  public CreateAppV2() {
  }

  public CreateAppV2 providerId(String providerId) {
    
    this.providerId = providerId;
    return this;
  }

   /**
   * ID провайдера. Обязателен при создании из Git-репозитория. Не требуется для готового Docker-образа.
   * @return providerId
  **/
  @javax.annotation.Nullable
  public String getProviderId() {
    return providerId;
  }


  public void setProviderId(String providerId) {
    this.providerId = providerId;
  }


  public CreateAppV2 type(TypeEnum type) {
    
    this.type = type;
    return this;
  }

   /**
   * Тип приложения: frontend — статический сайт, backend — серверное приложение или Docker-образ.
   * @return type
  **/
  @javax.annotation.Nonnull
  public TypeEnum getType() {
    return type;
  }


  public void setType(TypeEnum type) {
    this.type = type;
  }


  public CreateAppV2 repositoryId(String repositoryId) {
    
    this.repositoryId = repositoryId;
    return this;
  }

   /**
   * ID репозитория. Обязателен при создании из Git-репозитория. Не требуется для готового Docker-образа.
   * @return repositoryId
  **/
  @javax.annotation.Nullable
  public String getRepositoryId() {
    return repositoryId;
  }


  public void setRepositoryId(String repositoryId) {
    this.repositoryId = repositoryId;
  }


  public CreateAppV2 buildCmd(String buildCmd) {
    
    this.buildCmd = buildCmd;
    return this;
  }

   /**
   * Команда сборки приложения из Git-репозитория. Обязательна для Git; можно передать пустую строку, если сборка не нужна.
   * @return buildCmd
  **/
  @javax.annotation.Nullable
  public String getBuildCmd() {
    return buildCmd;
  }


  public void setBuildCmd(String buildCmd) {
    this.buildCmd = buildCmd;
  }


  public CreateAppV2 envs(Map<String, String> envs) {
    
    this.envs = envs;
    return this;
  }

  public CreateAppV2 putEnvsItem(String key, String envsItem) {
    if (this.envs == null) {
      this.envs = new HashMap<>();
    }
    this.envs.put(key, envsItem);
    return this;
  }

   /**
   * Переменные окружения приложения. Ключи и значения — строки. Длина ключа — не более 255 символов, значения — не более 10240. По умолчанию пустой объект.
   * @return envs
  **/
  @javax.annotation.Nullable
  public Map<String, String> getEnvs() {
    return envs;
  }


  public void setEnvs(Map<String, String> envs) {
    this.envs = envs;
  }


  public CreateAppV2 branchName(String branchName) {
    
    this.branchName = branchName;
    return this;
  }

   /**
   * Ветка Git-репозитория, из которой необходимо собрать приложение. Обязательна при создании из Git.
   * @return branchName
  **/
  @javax.annotation.Nullable
  public String getBranchName() {
    return branchName;
  }


  public void setBranchName(String branchName) {
    this.branchName = branchName;
  }


  public CreateAppV2 isAutoDeploy(Boolean isAutoDeploy) {
    
    this.isAutoDeploy = isAutoDeploy;
    return this;
  }

   /**
   * Автоматически запускать деплой при изменении Git-репозитория. Поле обязательно при создании из Git. Для провайдера git и готового Docker-образа автодеплой выключен.
   * @return isAutoDeploy
  **/
  @javax.annotation.Nullable
  public Boolean getIsAutoDeploy() {
    return isAutoDeploy;
  }


  public void setIsAutoDeploy(Boolean isAutoDeploy) {
    this.isAutoDeploy = isAutoDeploy;
  }


  public CreateAppV2 commitSha(String commitSha) {
    
    this.commitSha = commitSha;
    return this;
  }

   /**
   * Хэш коммита для первого деплоя. Обязателен при создании из Git.
   * @return commitSha
  **/
  @javax.annotation.Nullable
  public String getCommitSha() {
    return commitSha;
  }


  public void setCommitSha(String commitSha) {
    this.commitSha = commitSha;
  }


  public CreateAppV2 name(String name) {
    
    this.name = name;
    return this;
  }

   /**
   * Имя приложения, от 1 до 255 символов. Допустимы латинские и русские буквы, цифры, пробелы и символы - . ( ) , &amp; # [ ] _.
   * @return name
  **/
  @javax.annotation.Nonnull
  public String getName() {
    return name;
  }


  public void setName(String name) {
    this.name = name;
  }


  public CreateAppV2 comment(String comment) {
    
    this.comment = comment;
    return this;
  }

   /**
   * Комментарий к приложению. Обязательное поле; можно передать пустую строку. Допустимы те же символы, что и в имени приложения.
   * @return comment
  **/
  @javax.annotation.Nonnull
  public String getComment() {
    return comment;
  }


  public void setComment(String comment) {
    this.comment = comment;
  }


  public CreateAppV2 presetId(Integer presetId) {
    
    this.presetId = presetId;
    return this;
  }

   /**
   * ID тарифа приложения. Обязателен для frontend. Для backend передайте либо preset_id, либо configuration. Нельзя передавать вместе с configuration. Доступные тарифы: GET /api/v1/presets/apps.
   * minimum: 1
   * @return presetId
  **/
  @javax.annotation.Nullable
  public Integer getPresetId() {
    return presetId;
  }


  public void setPresetId(Integer presetId) {
    this.presetId = presetId;
  }


  public CreateAppV2 envVersion(String envVersion) {
    
    this.envVersion = envVersion;
    return this;
  }

   /**
   * Версия окружения для сборки и запуска, например версия Node.js. Можно передать null.
   * @return envVersion
  **/
  @javax.annotation.Nullable
  public String getEnvVersion() {
    return envVersion;
  }


  public void setEnvVersion(String envVersion) {
    this.envVersion = envVersion;
  }


  public CreateAppV2 framework(FrameworkEnum framework) {
    
    this.framework = framework;
    return this;
  }

   /**
   * Фреймворк приложения, строка с учётом регистра. Обязателен при создании из Git. Для frontend: &#x60;angular&#x60;, &#x60;astro&#x60;, &#x60;ember&#x60;, &#x60;next.js&#x60;, &#x60;nuxt&#x60;, &#x60;preact&#x60;, &#x60;react&#x60;, &#x60;remix&#x60;, &#x60;solid&#x60;, &#x60;solid-start&#x60;, &#x60;svelte&#x60;, &#x60;sveltekit&#x60;, &#x60;tanstack-start&#x60;, &#x60;vue&#x60;, &#x60;static-nobuild&#x60;, &#x60;another&#x60;. Для backend: &#x60;django&#x60;, &#x60;express&#x60;, &#x60;phoenix&#x60;, &#x60;Spring&#x60;, &#x60;laravel&#x60;, &#x60;beego&#x60;, &#x60;fastapi&#x60;, &#x60;ASP.NET Core&#x60;, &#x60;hapi&#x60;, &#x60;celery&#x60;, &#x60;flask&#x60;, &#x60;gin&#x60;, &#x60;docker&#x60;, &#x60;docker-compose&#x60;, &#x60;fastify&#x60;, &#x60;nest&#x60;, &#x60;symfony&#x60;, &#x60;yii&#x60;, &#x60;next.js&#x60;, &#x60;nuxt&#x60;, &#x60;astro&#x60;, &#x60;tanstack-start&#x60;, &#x60;solid-start&#x60;, &#x60;ktor&#x60;, &#x60;axum&#x60;, &#x60;actix&#x60;, &#x60;tornado&#x60;, &#x60;aiohttp&#x60;, &#x60;fiber&#x60;, &#x60;echo&#x60;, &#x60;Micronaut&#x60;, &#x60;Quarkus&#x60;, &#x60;remix&#x60;, &#x60;sveltekit&#x60;. Для готового Docker-образа не требуется.
   * @return framework
  **/
  @javax.annotation.Nullable
  public FrameworkEnum getFramework() {
    return framework;
  }


  public void setFramework(FrameworkEnum framework) {
    this.framework = framework;
  }


  public CreateAppV2 indexDir(String indexDir) {
    
    this.indexDir = indexDir;
    return this;
  }

   /**
   * Путь к каталогу с собранными статическими файлами, например /dist. Обязателен для frontend. Не используется для backend.
   * @return indexDir
  **/
  @javax.annotation.Nullable
  public String getIndexDir() {
    return indexDir;
  }


  public void setIndexDir(String indexDir) {
    this.indexDir = indexDir;
  }


  public CreateAppV2 runCmd(String runCmd) {
    
    this.runCmd = runCmd;
    return this;
  }

   /**
   * Команда запуска backend-приложения из Git. Обязательна для backend из Git. Не требуется для готового Docker-образа и frontend.
   * @return runCmd
  **/
  @javax.annotation.Nullable
  public String getRunCmd() {
    return runCmd;
  }


  public void setRunCmd(String runCmd) {
    this.runCmd = runCmd;
  }


  public CreateAppV2 systemDependencies(List<String> systemDependencies) {
    
    this.systemDependencies = systemDependencies;
    return this;
  }

  public CreateAppV2 addSystemDependenciesItem(String systemDependenciesItem) {
    if (this.systemDependencies == null) {
      this.systemDependencies = new ArrayList<>();
    }
    this.systemDependencies.add(systemDependenciesItem);
    return this;
  }

   /**
   * Системные пакеты для установки в окружение приложения. Допустимы строчные латинские буквы, цифры и символы . + - &#x3D;. По умолчанию пустой массив.
   * @return systemDependencies
  **/
  @javax.annotation.Nullable
  public List<String> getSystemDependencies() {
    return systemDependencies;
  }


  public void setSystemDependencies(List<String> systemDependencies) {
    this.systemDependencies = systemDependencies;
  }


  public CreateAppV2 projectId(Integer projectId) {
    
    this.projectId = projectId;
    return this;
  }

   /**
   * ID проекта, в который необходимо добавить приложение. Проект должен принадлежать аккаунту.
   * @return projectId
  **/
  @javax.annotation.Nullable
  public Integer getProjectId() {
    return projectId;
  }


  public void setProjectId(Integer projectId) {
    this.projectId = projectId;
  }


  public CreateAppV2 _configuration(CreateAppV2Configuration _configuration) {
    
    this._configuration = _configuration;
    return this;
  }

   /**
   * Get _configuration
   * @return _configuration
  **/
  @javax.annotation.Nullable
  public CreateAppV2Configuration getConfiguration() {
    return _configuration;
  }


  public void setConfiguration(CreateAppV2Configuration _configuration) {
    this._configuration = _configuration;
  }


  public CreateAppV2 language(LanguageEnum language) {
    
    this.language = language;
    return this;
  }

   /**
   * Язык или способ развёртывания. Обязателен в v2. Для frontend: &#x60;javascript&#x60;. Для backend: &#x60;node&#x60;, &#x60;elixir&#x60;, &#x60;go&#x60;, &#x60;php&#x60;, &#x60;python&#x60;, &#x60;ruby&#x60;, &#x60;docker&#x60;, &#x60;docker-compose&#x60;, &#x60;dotnet&#x60;, &#x60;java&#x60;, &#x60;kotlin&#x60;, &#x60;rust&#x60;, &#x60;bun&#x60;, &#x60;deno&#x60;, &#x60;custom-image&#x60;. Для SSR-приложений из Git используйте node, для готового Docker-образа — custom-image.
   * @return language
  **/
  @javax.annotation.Nonnull
  public LanguageEnum getLanguage() {
    return language;
  }


  public void setLanguage(LanguageEnum language) {
    this.language = language;
  }


  public CreateAppV2 buildEntryPath(String buildEntryPath) {
    
    this.buildEntryPath = buildEntryPath;
    return this;
  }

   /**
   * Каталог приложения в Git-репозитории, относительно которого выполняется сборка. Непустой путь начинается с /, не содержит .. или двойной начальный /. Допустимы латинские буквы, цифры, . - _ /. Можно передать пустую строку.
   * @return buildEntryPath
  **/
  @javax.annotation.Nullable
  public String getBuildEntryPath() {
    return buildEntryPath;
  }


  public void setBuildEntryPath(String buildEntryPath) {
    this.buildEntryPath = buildEntryPath;
  }


  public CreateAppV2 network(CreateAppV2Network network) {
    
    this.network = network;
    return this;
  }

   /**
   * Get network
   * @return network
  **/
  @javax.annotation.Nullable
  public CreateAppV2Network getNetwork() {
    return network;
  }


  public void setNetwork(CreateAppV2Network network) {
    this.network = network;
  }


  public CreateAppV2 healthCheck(String healthCheck) {
    
    this.healthCheck = healthCheck;
    return this;
  }

   /**
   * HTTP-путь для проверки работоспособности backend-приложения.
   * @return healthCheck
  **/
  @javax.annotation.Nullable
  public String getHealthCheck() {
    return healthCheck;
  }


  public void setHealthCheck(String healthCheck) {
    this.healthCheck = healthCheck;
  }


  public CreateAppV2 globalEnvIds(List<BigDecimal> globalEnvIds) {
    
    this.globalEnvIds = globalEnvIds;
    return this;
  }

  public CreateAppV2 addGlobalEnvIdsItem(BigDecimal globalEnvIdsItem) {
    if (this.globalEnvIds == null) {
      this.globalEnvIds = new ArrayList<>();
    }
    this.globalEnvIds.add(globalEnvIdsItem);
    return this;
  }

   /**
   * ID глобальных переменных окружения аккаунта, которые необходимо связать с приложением.
   * @return globalEnvIds
  **/
  @javax.annotation.Nullable
  public List<BigDecimal> getGlobalEnvIds() {
    return globalEnvIds;
  }


  public void setGlobalEnvIds(List<BigDecimal> globalEnvIds) {
    this.globalEnvIds = globalEnvIds;
  }


  public CreateAppV2 dockerImage(String dockerImage) {
    
    this.dockerImage = dockerImage;
    return this;
  }

   /**
   * Имя готового Docker-образа для backend, без тега. Передавайте вместе с docker_image_tag. При указании обоих полей Git-параметры не требуются.
   * @return dockerImage
  **/
  @javax.annotation.Nullable
  public String getDockerImage() {
    return dockerImage;
  }


  public void setDockerImage(String dockerImage) {
    this.dockerImage = dockerImage;
  }


  public CreateAppV2 dockerImageTag(String dockerImageTag) {
    
    this.dockerImageTag = dockerImageTag;
    return this;
  }

   /**
   * Тег готового Docker-образа. Передавайте вместе с docker_image.
   * @return dockerImageTag
  **/
  @javax.annotation.Nullable
  public String getDockerImageTag() {
    return dockerImageTag;
  }


  public void setDockerImageTag(String dockerImageTag) {
    this.dockerImageTag = dockerImageTag;
  }


  public CreateAppV2 isRussianTrustedCa(Boolean isRussianTrustedCa) {
    
    this.isRussianTrustedCa = isRussianTrustedCa;
    return this;
  }

   /**
   * Добавить доверенные корневые сертификаты Минцифры в окружение backend-приложения. Применяется в российском регионе.
   * @return isRussianTrustedCa
  **/
  @javax.annotation.Nullable
  public Boolean getIsRussianTrustedCa() {
    return isRussianTrustedCa;
  }


  public void setIsRussianTrustedCa(Boolean isRussianTrustedCa) {
    this.isRussianTrustedCa = isRussianTrustedCa;
  }


  public CreateAppV2 isTciTrustedCa(Boolean isTciTrustedCa) {
    
    this.isTciTrustedCa = isTciTrustedCa;
    return this;
  }

   /**
   * Добавить доверенные корневые сертификаты ТЦИ в окружение backend-приложения. Применяется в российском регионе.
   * @return isTciTrustedCa
  **/
  @javax.annotation.Nullable
  public Boolean getIsTciTrustedCa() {
    return isTciTrustedCa;
  }


  public void setIsTciTrustedCa(Boolean isTciTrustedCa) {
    this.isTciTrustedCa = isTciTrustedCa;
  }



  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CreateAppV2 createAppV2 = (CreateAppV2) o;
    return Objects.equals(this.providerId, createAppV2.providerId) &&
        Objects.equals(this.type, createAppV2.type) &&
        Objects.equals(this.repositoryId, createAppV2.repositoryId) &&
        Objects.equals(this.buildCmd, createAppV2.buildCmd) &&
        Objects.equals(this.envs, createAppV2.envs) &&
        Objects.equals(this.branchName, createAppV2.branchName) &&
        Objects.equals(this.isAutoDeploy, createAppV2.isAutoDeploy) &&
        Objects.equals(this.commitSha, createAppV2.commitSha) &&
        Objects.equals(this.name, createAppV2.name) &&
        Objects.equals(this.comment, createAppV2.comment) &&
        Objects.equals(this.presetId, createAppV2.presetId) &&
        Objects.equals(this.envVersion, createAppV2.envVersion) &&
        Objects.equals(this.framework, createAppV2.framework) &&
        Objects.equals(this.indexDir, createAppV2.indexDir) &&
        Objects.equals(this.runCmd, createAppV2.runCmd) &&
        Objects.equals(this.systemDependencies, createAppV2.systemDependencies) &&
        Objects.equals(this.projectId, createAppV2.projectId) &&
        Objects.equals(this._configuration, createAppV2._configuration) &&
        Objects.equals(this.language, createAppV2.language) &&
        Objects.equals(this.buildEntryPath, createAppV2.buildEntryPath) &&
        Objects.equals(this.network, createAppV2.network) &&
        Objects.equals(this.healthCheck, createAppV2.healthCheck) &&
        Objects.equals(this.globalEnvIds, createAppV2.globalEnvIds) &&
        Objects.equals(this.dockerImage, createAppV2.dockerImage) &&
        Objects.equals(this.dockerImageTag, createAppV2.dockerImageTag) &&
        Objects.equals(this.isRussianTrustedCa, createAppV2.isRussianTrustedCa) &&
        Objects.equals(this.isTciTrustedCa, createAppV2.isTciTrustedCa);
  }

  private static <T> boolean equalsNullable(JsonNullable<T> a, JsonNullable<T> b) {
    return a == b || (a != null && b != null && a.isPresent() && b.isPresent() && Objects.deepEquals(a.get(), b.get()));
  }

  @Override
  public int hashCode() {
    return Objects.hash(providerId, type, repositoryId, buildCmd, envs, branchName, isAutoDeploy, commitSha, name, comment, presetId, envVersion, framework, indexDir, runCmd, systemDependencies, projectId, _configuration, language, buildEntryPath, network, healthCheck, globalEnvIds, dockerImage, dockerImageTag, isRussianTrustedCa, isTciTrustedCa);
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
    sb.append("class CreateAppV2 {\n");
    sb.append("    providerId: ").append(toIndentedString(providerId)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    repositoryId: ").append(toIndentedString(repositoryId)).append("\n");
    sb.append("    buildCmd: ").append(toIndentedString(buildCmd)).append("\n");
    sb.append("    envs: ").append(toIndentedString(envs)).append("\n");
    sb.append("    branchName: ").append(toIndentedString(branchName)).append("\n");
    sb.append("    isAutoDeploy: ").append(toIndentedString(isAutoDeploy)).append("\n");
    sb.append("    commitSha: ").append(toIndentedString(commitSha)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    comment: ").append(toIndentedString(comment)).append("\n");
    sb.append("    presetId: ").append(toIndentedString(presetId)).append("\n");
    sb.append("    envVersion: ").append(toIndentedString(envVersion)).append("\n");
    sb.append("    framework: ").append(toIndentedString(framework)).append("\n");
    sb.append("    indexDir: ").append(toIndentedString(indexDir)).append("\n");
    sb.append("    runCmd: ").append(toIndentedString(runCmd)).append("\n");
    sb.append("    systemDependencies: ").append(toIndentedString(systemDependencies)).append("\n");
    sb.append("    projectId: ").append(toIndentedString(projectId)).append("\n");
    sb.append("    _configuration: ").append(toIndentedString(_configuration)).append("\n");
    sb.append("    language: ").append(toIndentedString(language)).append("\n");
    sb.append("    buildEntryPath: ").append(toIndentedString(buildEntryPath)).append("\n");
    sb.append("    network: ").append(toIndentedString(network)).append("\n");
    sb.append("    healthCheck: ").append(toIndentedString(healthCheck)).append("\n");
    sb.append("    globalEnvIds: ").append(toIndentedString(globalEnvIds)).append("\n");
    sb.append("    dockerImage: ").append(toIndentedString(dockerImage)).append("\n");
    sb.append("    dockerImageTag: ").append(toIndentedString(dockerImageTag)).append("\n");
    sb.append("    isRussianTrustedCa: ").append(toIndentedString(isRussianTrustedCa)).append("\n");
    sb.append("    isTciTrustedCa: ").append(toIndentedString(isTciTrustedCa)).append("\n");
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
    openapiFields.add("provider_id");
    openapiFields.add("type");
    openapiFields.add("repository_id");
    openapiFields.add("build_cmd");
    openapiFields.add("envs");
    openapiFields.add("branch_name");
    openapiFields.add("is_auto_deploy");
    openapiFields.add("commit_sha");
    openapiFields.add("name");
    openapiFields.add("comment");
    openapiFields.add("preset_id");
    openapiFields.add("env_version");
    openapiFields.add("framework");
    openapiFields.add("index_dir");
    openapiFields.add("run_cmd");
    openapiFields.add("system_dependencies");
    openapiFields.add("project_id");
    openapiFields.add("configuration");
    openapiFields.add("language");
    openapiFields.add("build_entry_path");
    openapiFields.add("network");
    openapiFields.add("health_check");
    openapiFields.add("global_env_ids");
    openapiFields.add("docker_image");
    openapiFields.add("docker_image_tag");
    openapiFields.add("is_russian_trusted_ca");
    openapiFields.add("is_tci_trusted_ca");

    // a set of required properties/fields (JSON key names)
    openapiRequiredFields = new HashSet<String>();
    openapiRequiredFields.add("type");
    openapiRequiredFields.add("name");
    openapiRequiredFields.add("comment");
    openapiRequiredFields.add("language");
  }

 /**
  * Validates the JSON Element and throws an exception if issues found
  *
  * @param jsonElement JSON Element
  * @throws IOException if the JSON Element is invalid with respect to CreateAppV2
  */
  public static void validateJsonElement(JsonElement jsonElement) throws IOException {
      if (jsonElement == null) {
        if (!CreateAppV2.openapiRequiredFields.isEmpty()) { // has required fields but JSON element is null
          throw new IllegalArgumentException(String.format("The required field(s) %s in CreateAppV2 is not found in the empty JSON string", CreateAppV2.openapiRequiredFields.toString()));
        }
      }

      Set<Entry<String, JsonElement>> entries = jsonElement.getAsJsonObject().entrySet();
      // check to see if the JSON string contains additional fields
      for (Entry<String, JsonElement> entry : entries) {
        if (!CreateAppV2.openapiFields.contains(entry.getKey())) {
          throw new IllegalArgumentException(String.format("The field `%s` in the JSON string is not defined in the `CreateAppV2` properties. JSON: %s", entry.getKey(), jsonElement.toString()));
        }
      }

      // check to make sure all required properties/fields are present in the JSON string
      for (String requiredField : CreateAppV2.openapiRequiredFields) {
        if (jsonElement.getAsJsonObject().get(requiredField) == null) {
          throw new IllegalArgumentException(String.format("The required field `%s` is not found in the JSON string: %s", requiredField, jsonElement.toString()));
        }
      }
        JsonObject jsonObj = jsonElement.getAsJsonObject();
      if ((jsonObj.get("provider_id") != null && !jsonObj.get("provider_id").isJsonNull()) && !jsonObj.get("provider_id").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `provider_id` to be a primitive type in the JSON string but got `%s`", jsonObj.get("provider_id").toString()));
      }
      if (!jsonObj.get("type").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `type` to be a primitive type in the JSON string but got `%s`", jsonObj.get("type").toString()));
      }
      if ((jsonObj.get("repository_id") != null && !jsonObj.get("repository_id").isJsonNull()) && !jsonObj.get("repository_id").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `repository_id` to be a primitive type in the JSON string but got `%s`", jsonObj.get("repository_id").toString()));
      }
      if ((jsonObj.get("build_cmd") != null && !jsonObj.get("build_cmd").isJsonNull()) && !jsonObj.get("build_cmd").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `build_cmd` to be a primitive type in the JSON string but got `%s`", jsonObj.get("build_cmd").toString()));
      }
      if ((jsonObj.get("branch_name") != null && !jsonObj.get("branch_name").isJsonNull()) && !jsonObj.get("branch_name").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `branch_name` to be a primitive type in the JSON string but got `%s`", jsonObj.get("branch_name").toString()));
      }
      if ((jsonObj.get("commit_sha") != null && !jsonObj.get("commit_sha").isJsonNull()) && !jsonObj.get("commit_sha").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `commit_sha` to be a primitive type in the JSON string but got `%s`", jsonObj.get("commit_sha").toString()));
      }
      if (!jsonObj.get("name").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `name` to be a primitive type in the JSON string but got `%s`", jsonObj.get("name").toString()));
      }
      if (!jsonObj.get("comment").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `comment` to be a primitive type in the JSON string but got `%s`", jsonObj.get("comment").toString()));
      }
      if ((jsonObj.get("env_version") != null && !jsonObj.get("env_version").isJsonNull()) && !jsonObj.get("env_version").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `env_version` to be a primitive type in the JSON string but got `%s`", jsonObj.get("env_version").toString()));
      }
      if ((jsonObj.get("framework") != null && !jsonObj.get("framework").isJsonNull()) && !jsonObj.get("framework").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `framework` to be a primitive type in the JSON string but got `%s`", jsonObj.get("framework").toString()));
      }
      if ((jsonObj.get("index_dir") != null && !jsonObj.get("index_dir").isJsonNull()) && !jsonObj.get("index_dir").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `index_dir` to be a primitive type in the JSON string but got `%s`", jsonObj.get("index_dir").toString()));
      }
      if ((jsonObj.get("run_cmd") != null && !jsonObj.get("run_cmd").isJsonNull()) && !jsonObj.get("run_cmd").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `run_cmd` to be a primitive type in the JSON string but got `%s`", jsonObj.get("run_cmd").toString()));
      }
      // ensure the optional json data is an array if present
      if (jsonObj.get("system_dependencies") != null && !jsonObj.get("system_dependencies").isJsonArray()) {
        throw new IllegalArgumentException(String.format("Expected the field `system_dependencies` to be an array in the JSON string but got `%s`", jsonObj.get("system_dependencies").toString()));
      }
      // validate the optional field `configuration`
      if (jsonObj.get("configuration") != null && !jsonObj.get("configuration").isJsonNull()) {
        CreateAppV2Configuration.validateJsonElement(jsonObj.get("configuration"));
      }
      if (!jsonObj.get("language").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `language` to be a primitive type in the JSON string but got `%s`", jsonObj.get("language").toString()));
      }
      if ((jsonObj.get("build_entry_path") != null && !jsonObj.get("build_entry_path").isJsonNull()) && !jsonObj.get("build_entry_path").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `build_entry_path` to be a primitive type in the JSON string but got `%s`", jsonObj.get("build_entry_path").toString()));
      }
      // validate the optional field `network`
      if (jsonObj.get("network") != null && !jsonObj.get("network").isJsonNull()) {
        CreateAppV2Network.validateJsonElement(jsonObj.get("network"));
      }
      if ((jsonObj.get("health_check") != null && !jsonObj.get("health_check").isJsonNull()) && !jsonObj.get("health_check").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `health_check` to be a primitive type in the JSON string but got `%s`", jsonObj.get("health_check").toString()));
      }
      // ensure the optional json data is an array if present
      if (jsonObj.get("global_env_ids") != null && !jsonObj.get("global_env_ids").isJsonArray()) {
        throw new IllegalArgumentException(String.format("Expected the field `global_env_ids` to be an array in the JSON string but got `%s`", jsonObj.get("global_env_ids").toString()));
      }
      if ((jsonObj.get("docker_image") != null && !jsonObj.get("docker_image").isJsonNull()) && !jsonObj.get("docker_image").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `docker_image` to be a primitive type in the JSON string but got `%s`", jsonObj.get("docker_image").toString()));
      }
      if ((jsonObj.get("docker_image_tag") != null && !jsonObj.get("docker_image_tag").isJsonNull()) && !jsonObj.get("docker_image_tag").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `docker_image_tag` to be a primitive type in the JSON string but got `%s`", jsonObj.get("docker_image_tag").toString()));
      }
  }

  public static class CustomTypeAdapterFactory implements TypeAdapterFactory {
    @SuppressWarnings("unchecked")
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
       if (!CreateAppV2.class.isAssignableFrom(type.getRawType())) {
         return null; // this class only serializes 'CreateAppV2' and its subtypes
       }
       final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);
       final TypeAdapter<CreateAppV2> thisAdapter
                        = gson.getDelegateAdapter(this, TypeToken.get(CreateAppV2.class));

       return (TypeAdapter<T>) new TypeAdapter<CreateAppV2>() {
           @Override
           public void write(JsonWriter out, CreateAppV2 value) throws IOException {
             JsonObject obj = thisAdapter.toJsonTree(value).getAsJsonObject();
             elementAdapter.write(out, obj);
           }

           @Override
           public CreateAppV2 read(JsonReader in) throws IOException {
             JsonElement jsonElement = elementAdapter.read(in);
             validateJsonElement(jsonElement);
             return thisAdapter.fromJsonTree(jsonElement);
           }

       }.nullSafe();
    }
  }

 /**
  * Create an instance of CreateAppV2 given an JSON string
  *
  * @param jsonString JSON string
  * @return An instance of CreateAppV2
  * @throws IOException if the JSON string is invalid with respect to CreateAppV2
  */
  public static CreateAppV2 fromJson(String jsonString) throws IOException {
    return JSON.getGson().fromJson(jsonString, CreateAppV2.class);
  }

 /**
  * Convert an instance of CreateAppV2 to an JSON string
  *
  * @return JSON string
  */
  public String toJson() {
    return JSON.getGson().toJson(this);
  }
}

