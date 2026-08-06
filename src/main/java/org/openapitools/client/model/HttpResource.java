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
import org.openapitools.client.model.TrafficUsage;
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
 * CDN-ресурс
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2026-08-06T15:06:51.562821Z[Etc/UTC]")
public class HttpResource {
  public static final String SERIALIZED_NAME_ID = "id";
  @SerializedName(SERIALIZED_NAME_ID)
  private Integer id;

  public static final String SERIALIZED_NAME_NAME = "name";
  @SerializedName(SERIALIZED_NAME_NAME)
  private String name;

  public static final String SERIALIZED_NAME_DESCRIPTION = "description";
  @SerializedName(SERIALIZED_NAME_DESCRIPTION)
  private String description;

  public static final String SERIALIZED_NAME_SOURCE = "source";
  @SerializedName(SERIALIZED_NAME_SOURCE)
  private String source;

  public static final String SERIALIZED_NAME_TRAFFIC_USAGE = "traffic_usage";
  @SerializedName(SERIALIZED_NAME_TRAFFIC_USAGE)
  private TrafficUsage trafficUsage;

  /**
   * Статус CDN-ресурса. - &#x60;created&#x60; — ресурс создан и раздает контент; - &#x60;processing&#x60; — конфигурация применяется на стороне CDN; - &#x60;stopped&#x60; — раздача приостановлена; - &#x60;failed&#x60; — настройка ресурса завершилась с ошибкой; - &#x60;no_paid&#x60; — ресурс не оплачен; - &#x60;blocked&#x60; — ресурс заблокирован; - &#x60;traffic_limit_exceeded&#x60; — раздача остановлена автоматически из-за достижения лимита трафика &#x60;traffic_limit_bytes&#x60;.
   */
  @JsonAdapter(StatusEnum.Adapter.class)
  public enum StatusEnum {
    CREATED("created"),
    
    PROCESSING("processing"),
    
    STOPPED("stopped"),
    
    FAILED("failed"),
    
    NO_PAID("no_paid"),
    
    BLOCKED("blocked"),
    
    TRAFFIC_LIMIT_EXCEEDED("traffic_limit_exceeded");

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

  public static final String SERIALIZED_NAME_CDN_DOMAIN = "cdn_domain";
  @SerializedName(SERIALIZED_NAME_CDN_DOMAIN)
  private String cdnDomain;

  public static final String SERIALIZED_NAME_PRESET_ID = "preset_id";
  @SerializedName(SERIALIZED_NAME_PRESET_ID)
  private Integer presetId;

  public static final String SERIALIZED_NAME_PROJECT_ID = "project_id";
  @SerializedName(SERIALIZED_NAME_PROJECT_ID)
  private Integer projectId;

  public static final String SERIALIZED_NAME_AVATAR_LINK = "avatar_link";
  @SerializedName(SERIALIZED_NAME_AVATAR_LINK)
  private String avatarLink;

  public static final String SERIALIZED_NAME_STORAGE_ID = "storage_id";
  @SerializedName(SERIALIZED_NAME_STORAGE_ID)
  private Integer storageId;

  public static final String SERIALIZED_NAME_TRAFFIC_LIMIT_BYTES = "traffic_limit_bytes";
  @SerializedName(SERIALIZED_NAME_TRAFFIC_LIMIT_BYTES)
  private Integer trafficLimitBytes;

  public HttpResource() {
  }

  public HttpResource id(Integer id) {
    
    this.id = id;
    return this;
  }

   /**
   * ID CDN-ресурса. Генерируется автоматически при создании.
   * @return id
  **/
  @javax.annotation.Nonnull
  public Integer getId() {
    return id;
  }


  public void setId(Integer id) {
    this.id = id;
  }


  public HttpResource name(String name) {
    
    this.name = name;
    return this;
  }

   /**
   * Название CDN-ресурса.
   * @return name
  **/
  @javax.annotation.Nonnull
  public String getName() {
    return name;
  }


  public void setName(String name) {
    this.name = name;
  }


  public HttpResource description(String description) {
    
    this.description = description;
    return this;
  }

   /**
   * Описание CDN-ресурса.
   * @return description
  **/
  @javax.annotation.Nullable
  public String getDescription() {
    return description;
  }


  public void setDescription(String description) {
    this.description = description;
  }


  public HttpResource source(String source) {
    
    this.source = source;
    return this;
  }

   /**
   * Источник контента: имя S3-бакета, если ресурс создан поверх хранилища, либо хост origin-сервера.
   * @return source
  **/
  @javax.annotation.Nonnull
  public String getSource() {
    return source;
  }


  public void setSource(String source) {
    this.source = source;
  }


  public HttpResource trafficUsage(TrafficUsage trafficUsage) {
    
    this.trafficUsage = trafficUsage;
    return this;
  }

   /**
   * Get trafficUsage
   * @return trafficUsage
  **/
  @javax.annotation.Nonnull
  public TrafficUsage getTrafficUsage() {
    return trafficUsage;
  }


  public void setTrafficUsage(TrafficUsage trafficUsage) {
    this.trafficUsage = trafficUsage;
  }


  public HttpResource status(StatusEnum status) {
    
    this.status = status;
    return this;
  }

   /**
   * Статус CDN-ресурса. - &#x60;created&#x60; — ресурс создан и раздает контент; - &#x60;processing&#x60; — конфигурация применяется на стороне CDN; - &#x60;stopped&#x60; — раздача приостановлена; - &#x60;failed&#x60; — настройка ресурса завершилась с ошибкой; - &#x60;no_paid&#x60; — ресурс не оплачен; - &#x60;blocked&#x60; — ресурс заблокирован; - &#x60;traffic_limit_exceeded&#x60; — раздача остановлена автоматически из-за достижения лимита трафика &#x60;traffic_limit_bytes&#x60;.
   * @return status
  **/
  @javax.annotation.Nonnull
  public StatusEnum getStatus() {
    return status;
  }


  public void setStatus(StatusEnum status) {
    this.status = status;
  }


  public HttpResource cdnDomain(String cdnDomain) {
    
    this.cdnDomain = cdnDomain;
    return this;
  }

   /**
   * Технический домен, выданный ресурсу. Доступен сразу после создания и всегда остается в списке доменов ресурса.
   * @return cdnDomain
  **/
  @javax.annotation.Nonnull
  public String getCdnDomain() {
    return cdnDomain;
  }


  public void setCdnDomain(String cdnDomain) {
    this.cdnDomain = cdnDomain;
  }


  public HttpResource presetId(Integer presetId) {
    
    this.presetId = presetId;
    return this;
  }

   /**
   * ID тарифа CDN. Список доступных тарифов можно получить в &#x60;/api/v1/cdn/presets&#x60;.
   * @return presetId
  **/
  @javax.annotation.Nonnull
  public Integer getPresetId() {
    return presetId;
  }


  public void setPresetId(Integer presetId) {
    this.presetId = presetId;
  }


  public HttpResource projectId(Integer projectId) {
    
    this.projectId = projectId;
    return this;
  }

   /**
   * ID проекта, к которому привязан ресурс.
   * @return projectId
  **/
  @javax.annotation.Nullable
  public Integer getProjectId() {
    return projectId;
  }


  public void setProjectId(Integer projectId) {
    this.projectId = projectId;
  }


  public HttpResource avatarLink(String avatarLink) {
    
    this.avatarLink = avatarLink;
    return this;
  }

   /**
   * Ссылка на аватар ресурса.
   * @return avatarLink
  **/
  @javax.annotation.Nullable
  public String getAvatarLink() {
    return avatarLink;
  }


  public void setAvatarLink(String avatarLink) {
    this.avatarLink = avatarLink;
  }


  public HttpResource storageId(Integer storageId) {
    
    this.storageId = storageId;
    return this;
  }

   /**
   * ID S3-хранилища, которое используется в качестве источника контента. &#x60;null&#x60;, если источником является origin-сервер.
   * @return storageId
  **/
  @javax.annotation.Nullable
  public Integer getStorageId() {
    return storageId;
  }


  public void setStorageId(Integer storageId) {
    this.storageId = storageId;
  }


  public HttpResource trafficLimitBytes(Integer trafficLimitBytes) {
    
    this.trafficLimitBytes = trafficLimitBytes;
    return this;
  }

   /**
   * Лимит исходящего трафика на расчетный месяц, в байтах. При достижении лимита раздача останавливается, а ресурс переходит в статус &#x60;traffic_limit_exceeded&#x60;. &#x60;null&#x60; — лимит не задан.
   * @return trafficLimitBytes
  **/
  @javax.annotation.Nullable
  public Integer getTrafficLimitBytes() {
    return trafficLimitBytes;
  }


  public void setTrafficLimitBytes(Integer trafficLimitBytes) {
    this.trafficLimitBytes = trafficLimitBytes;
  }



  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    HttpResource httpResource = (HttpResource) o;
    return Objects.equals(this.id, httpResource.id) &&
        Objects.equals(this.name, httpResource.name) &&
        Objects.equals(this.description, httpResource.description) &&
        Objects.equals(this.source, httpResource.source) &&
        Objects.equals(this.trafficUsage, httpResource.trafficUsage) &&
        Objects.equals(this.status, httpResource.status) &&
        Objects.equals(this.cdnDomain, httpResource.cdnDomain) &&
        Objects.equals(this.presetId, httpResource.presetId) &&
        Objects.equals(this.projectId, httpResource.projectId) &&
        Objects.equals(this.avatarLink, httpResource.avatarLink) &&
        Objects.equals(this.storageId, httpResource.storageId) &&
        Objects.equals(this.trafficLimitBytes, httpResource.trafficLimitBytes);
  }

  private static <T> boolean equalsNullable(JsonNullable<T> a, JsonNullable<T> b) {
    return a == b || (a != null && b != null && a.isPresent() && b.isPresent() && Objects.deepEquals(a.get(), b.get()));
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, description, source, trafficUsage, status, cdnDomain, presetId, projectId, avatarLink, storageId, trafficLimitBytes);
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
    sb.append("class HttpResource {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    source: ").append(toIndentedString(source)).append("\n");
    sb.append("    trafficUsage: ").append(toIndentedString(trafficUsage)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    cdnDomain: ").append(toIndentedString(cdnDomain)).append("\n");
    sb.append("    presetId: ").append(toIndentedString(presetId)).append("\n");
    sb.append("    projectId: ").append(toIndentedString(projectId)).append("\n");
    sb.append("    avatarLink: ").append(toIndentedString(avatarLink)).append("\n");
    sb.append("    storageId: ").append(toIndentedString(storageId)).append("\n");
    sb.append("    trafficLimitBytes: ").append(toIndentedString(trafficLimitBytes)).append("\n");
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
    openapiFields.add("description");
    openapiFields.add("source");
    openapiFields.add("traffic_usage");
    openapiFields.add("status");
    openapiFields.add("cdn_domain");
    openapiFields.add("preset_id");
    openapiFields.add("project_id");
    openapiFields.add("avatar_link");
    openapiFields.add("storage_id");
    openapiFields.add("traffic_limit_bytes");

    // a set of required properties/fields (JSON key names)
    openapiRequiredFields = new HashSet<String>();
    openapiRequiredFields.add("id");
    openapiRequiredFields.add("name");
    openapiRequiredFields.add("source");
    openapiRequiredFields.add("traffic_usage");
    openapiRequiredFields.add("status");
    openapiRequiredFields.add("cdn_domain");
    openapiRequiredFields.add("preset_id");
  }

 /**
  * Validates the JSON Element and throws an exception if issues found
  *
  * @param jsonElement JSON Element
  * @throws IOException if the JSON Element is invalid with respect to HttpResource
  */
  public static void validateJsonElement(JsonElement jsonElement) throws IOException {
      if (jsonElement == null) {
        if (!HttpResource.openapiRequiredFields.isEmpty()) { // has required fields but JSON element is null
          throw new IllegalArgumentException(String.format("The required field(s) %s in HttpResource is not found in the empty JSON string", HttpResource.openapiRequiredFields.toString()));
        }
      }

      Set<Entry<String, JsonElement>> entries = jsonElement.getAsJsonObject().entrySet();
      // check to see if the JSON string contains additional fields
      for (Entry<String, JsonElement> entry : entries) {
        if (!HttpResource.openapiFields.contains(entry.getKey())) {
          throw new IllegalArgumentException(String.format("The field `%s` in the JSON string is not defined in the `HttpResource` properties. JSON: %s", entry.getKey(), jsonElement.toString()));
        }
      }

      // check to make sure all required properties/fields are present in the JSON string
      for (String requiredField : HttpResource.openapiRequiredFields) {
        if (jsonElement.getAsJsonObject().get(requiredField) == null) {
          throw new IllegalArgumentException(String.format("The required field `%s` is not found in the JSON string: %s", requiredField, jsonElement.toString()));
        }
      }
        JsonObject jsonObj = jsonElement.getAsJsonObject();
      if (!jsonObj.get("name").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `name` to be a primitive type in the JSON string but got `%s`", jsonObj.get("name").toString()));
      }
      if ((jsonObj.get("description") != null && !jsonObj.get("description").isJsonNull()) && !jsonObj.get("description").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `description` to be a primitive type in the JSON string but got `%s`", jsonObj.get("description").toString()));
      }
      if (!jsonObj.get("source").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `source` to be a primitive type in the JSON string but got `%s`", jsonObj.get("source").toString()));
      }
      // validate the required field `traffic_usage`
      TrafficUsage.validateJsonElement(jsonObj.get("traffic_usage"));
      if (!jsonObj.get("status").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `status` to be a primitive type in the JSON string but got `%s`", jsonObj.get("status").toString()));
      }
      if (!jsonObj.get("cdn_domain").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `cdn_domain` to be a primitive type in the JSON string but got `%s`", jsonObj.get("cdn_domain").toString()));
      }
      if ((jsonObj.get("avatar_link") != null && !jsonObj.get("avatar_link").isJsonNull()) && !jsonObj.get("avatar_link").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `avatar_link` to be a primitive type in the JSON string but got `%s`", jsonObj.get("avatar_link").toString()));
      }
  }

  public static class CustomTypeAdapterFactory implements TypeAdapterFactory {
    @SuppressWarnings("unchecked")
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
       if (!HttpResource.class.isAssignableFrom(type.getRawType())) {
         return null; // this class only serializes 'HttpResource' and its subtypes
       }
       final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);
       final TypeAdapter<HttpResource> thisAdapter
                        = gson.getDelegateAdapter(this, TypeToken.get(HttpResource.class));

       return (TypeAdapter<T>) new TypeAdapter<HttpResource>() {
           @Override
           public void write(JsonWriter out, HttpResource value) throws IOException {
             JsonObject obj = thisAdapter.toJsonTree(value).getAsJsonObject();
             elementAdapter.write(out, obj);
           }

           @Override
           public HttpResource read(JsonReader in) throws IOException {
             JsonElement jsonElement = elementAdapter.read(in);
             validateJsonElement(jsonElement);
             return thisAdapter.fromJsonTree(jsonElement);
           }

       }.nullSafe();
    }
  }

 /**
  * Create an instance of HttpResource given an JSON string
  *
  * @param jsonString JSON string
  * @return An instance of HttpResource
  * @throws IOException if the JSON string is invalid with respect to HttpResource
  */
  public static HttpResource fromJson(String jsonString) throws IOException {
    return JSON.getGson().fromJson(jsonString, HttpResource.class);
  }

 /**
  * Convert an instance of HttpResource to an JSON string
  *
  * @return JSON string
  */
  public String toJson() {
    return JSON.getGson().toJson(this);
  }
}

