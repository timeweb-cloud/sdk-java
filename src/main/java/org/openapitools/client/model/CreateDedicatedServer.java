/*
 * Timeweb Cloud API
 * # Введение API Timeweb Cloud позволяет вам управлять ресурсами в облаке программным способом с использованием обычных HTTP-запросов.  Множество функций, которые доступны в панели управления Timeweb Cloud, также доступны через API, что позволяет вам автоматизировать ваши собственные сценарии.  В этой документации сперва будет описан общий дизайн и принципы работы API, а после этого конкретные конечные точки. Также будут приведены примеры запросов к ним.   ## Запросы Запросы должны выполняться по протоколу `HTTPS`, чтобы гарантировать шифрование транзакций. Поддерживаются следующие методы запроса: |Метод|Применение| |--- |--- | |GET|Извлекает данные о коллекциях и отдельных ресурсах.| |POST|Для коллекций создает новый ресурс этого типа. Также используется для выполнения действий с конкретным ресурсом.| |PUT|Обновляет существующий ресурс.| |PATCH|Некоторые ресурсы поддерживают частичное обновление, то есть обновление только части атрибутов ресурса, в этом случае вместо метода PUT будет использован PATCH.| |DELETE|Удаляет ресурс.|  Методы `POST`, `PUT` и `PATCH` могут включать объект в тело запроса с типом содержимого `application/json`.  ### Параметры в запросах Некоторые коллекции поддерживают пагинацию, поиск или сортировку в запросах. В параметрах запроса требуется передать: - `limit` — обозначает количество записей, которое необходимо вернуть  - `offset` — указывает на смещение, относительно начала списка  - `search` — позволяет указать набор символов для поиска  - `sort` — можно задать правило сортировки коллекции  ## Ответы Запросы вернут один из следующих кодов состояния ответа HTTP:  |Статус|Описание| |--- |--- | |200 OK|Действие с ресурсом было выполнено успешно.| |201 Created|Ресурс был успешно создан. При этом ресурс может быть как уже готовым к использованию, так и находиться в процессе запуска.| |204 No Content|Действие с ресурсом было выполнено успешно, и ответ не содержит дополнительной информации в теле.| |400 Bad Request|Был отправлен неверный запрос, например, в нем отсутствуют обязательные параметры и т. д. Тело ответа будет содержать дополнительную информацию об ошибке.| |401 Unauthorized|Ошибка аутентификации.| |403 Forbidden|Аутентификация прошла успешно, но недостаточно прав для выполнения действия.| |404 Not Found|Запрашиваемый ресурс не найден.| |409 Conflict|Запрос конфликтует с текущим состоянием.| |423 Locked|Ресурс из запроса заблокирован от применения к нему указанного метода.| |429 Too Many Requests|Был достигнут лимит по количеству запросов в единицу времени.| |500 Internal Server Error|При выполнении запроса произошла какая-то внутренняя ошибка. Чтобы решить эту проблему, лучше всего создать тикет в панели управления.|  ### Структура успешного ответа Все конечные точки будут возвращать данные в формате `JSON`. Ответы на `GET`-запросы будут иметь на верхнем уровне следующую структуру атрибутов:  |Название поля|Тип|Описание| |--- |--- |--- | |[entity_name]|object, object[], string[], number[], boolean|Динамическое поле, которое будет меняться в зависимости от запрашиваемого ресурса и будет содержать все атрибуты, необходимые для описания этого ресурса. Например, при запросе списка баз данных будет возвращаться поле `dbs`, а при запросе конкретного облачного сервера `server`. Для некоторых конечных точек в ответе может возвращаться сразу несколько ресурсов.| |meta|object|Опционально. Объект, который содержит вспомогательную информацию о ресурсе. Чаще всего будет встречаться при запросе коллекций и содержать поле `total`, которое будет указывать на количество элементов в коллекции.| |response_id|string|Опционально. В большинстве случаев в ответе будет содержаться уникальный идентификатор ответа в формате UUIDv4, который однозначно указывает на ваш запрос внутри нашей системы. Если вам потребуется задать вопрос нашей поддержке, приложите к вопросу этот идентификатор — так мы сможем найти ответ на него намного быстрее. Также вы можете использовать этот идентификатор, чтобы убедиться, что это новый ответ на запрос и результат не был получен из кэша.|  Пример запроса на получение списка SSH-ключей: ```     HTTP/2.0 200 OK     {       \"ssh_keys\":[           {             \"body\":\"ssh-rsa AAAAB3NzaC1sdfghjkOAsBwWhs= example@device.local\",             \"created_at\":\"2021-09-15T19:52:27Z\",             \"expired_at\":null,             \"id\":5297,             \"is_default\":false,             \"name\":\"example@device.local\",             \"used_at\":null,             \"used_by\":[]           }       ],       \"meta\":{           \"total\":1       },       \"response_id\":\"94608d15-8672-4eed-8ab6-28bd6fa3cdf7\"     } ```  ### Структура ответа с ошибкой |Название поля|Тип|Описание| |--- |--- |--- | |status_code|number|Короткий числовой идентификатор ошибки.| |error_code|string|Короткий текстовый идентификатор ошибки, который уточняет числовой идентификатор и удобен для программной обработки. Самый простой пример — это код `not_found` для ошибки 404.| |message|string, string[]|Опционально. В большинстве случаев в ответе будет содержаться человекочитаемое подробное описание ошибки или ошибок, которые помогут понять, что нужно исправить.| |response_id|string|Опционально. В большинстве случае в ответе будет содержаться уникальный идентификатор ответа в формате UUIDv4, который однозначно указывает на ваш запрос внутри нашей системы. Если вам потребуется задать вопрос нашей поддержке, приложите к вопросу этот идентификатор — так мы сможем найти ответ на него намного быстрее.|  Пример: ```     HTTP/2.0 403 Forbidden     {       \"status_code\": 403,       \"error_code\":  \"forbidden\",       \"message\":     \"You do not have access for the attempted action\",       \"response_id\": \"94608d15-8672-4eed-8ab6-28bd6fa3cdf7\"     } ```  ## Статусы ресурсов Важно учесть, что при создании большинства ресурсов внутри платформы вам будет сразу возвращен ответ от сервера со статусом `200 OK` или `201 Created` и идентификатором созданного ресурса в теле ответа, но при этом этот ресурс может быть ещё в *состоянии запуска*.  Для того чтобы понять, в каком состоянии сейчас находится ваш ресурс, мы добавили поле `status` в ответ на получение информации о ресурсе.  Список статусов будет отличаться в зависимости от типа ресурса. Увидеть поддерживаемый список статусов вы сможете в описании каждого конкретного ресурса.     ## Ограничение скорости запросов (Rate Limiting) Чтобы обеспечить стабильность для всех пользователей, Timeweb Cloud защищает API от всплесков входящего трафика, анализируя количество запросов c каждого аккаунта к каждой конечной точке.  Если ваше приложение отправляет более 20 запросов в секунду на одну конечную точку, то для этого запроса API может вернуть код состояния HTTP `429 Too Many Requests`.   ## Аутентификация Доступ к API осуществляется с помощью JWT-токена. Токенами можно управлять внутри панели управления Timeweb Cloud в разделе *API и Terraform*.  Токен необходимо передавать в заголовке каждого запроса в формате: ```   Authorization: Bearer $TIMEWEB_CLOUD_TOKEN ```  ## Формат примеров API Примеры в этой документации описаны с помощью `curl`, HTTP-клиента командной строки. На компьютерах `Linux` и `macOS` обычно по умолчанию установлен `curl`, и он доступен для загрузки на всех популярных платформах, включая `Windows`.  Каждый пример разделен на несколько строк символом `\\`, который совместим с `bash`. Типичный пример выглядит так: ```   curl -X PATCH      -H \"Content-Type: application/json\"      -H \"Authorization: Bearer $TIMEWEB_CLOUD_TOKEN\"      -d '{\"name\":\"Cute Corvus\",\"comment\":\"Development Server\"}'      \"https://api.timeweb.cloud/api/v1/dedicated/1051\" ``` - Параметр `-X` задает метод запроса. Для согласованности метод будет указан во всех примерах, даже если он явно не требуется для методов `GET`. - Строки `-H` задают требуемые HTTP-заголовки. - Примеры, для которых требуется объект JSON в теле запроса, передают требуемые данные через параметр `-d`.  Чтобы использовать приведенные примеры, не подставляя каждый раз в них свой токен, вы можете добавить токен один раз в переменные окружения в вашей консоли. Например, на `Linux` это можно сделать с помощью команды:  ``` TIMEWEB_CLOUD_TOKEN=\"token\" ```  После этого токен будет автоматически подставляться в ваши запросы.  Обратите внимание, что все значения в этой документации являются примерами. Не полагайтесь на идентификаторы операционных систем, тарифов и т.д., используемые в примерах. Используйте соответствующую конечную точку для получения значений перед созданием ресурсов.   ## Версионирование API построено согласно принципам [семантического версионирования](https://semver.org/lang/ru). Это значит, что мы гарантируем обратную совместимость всех изменений в пределах одной мажорной версии.  Мажорная версия каждой конечной точки обозначается в пути запроса, например, запрос `/api/v1/servers` указывает, что этот метод имеет версию 1.
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
 * CreateDedicatedServer
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2024-06-14T12:52:28.658666Z[Etc/UTC]")
public class CreateDedicatedServer {
  public static final String SERIALIZED_NAME_PLAN_ID = "plan_id";
  @SerializedName(SERIALIZED_NAME_PLAN_ID)
  private BigDecimal planId;

  public static final String SERIALIZED_NAME_PRESET_ID = "preset_id";
  @SerializedName(SERIALIZED_NAME_PRESET_ID)
  private BigDecimal presetId;

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
  private BigDecimal networkDriveId;

  public static final String SERIALIZED_NAME_ADDITIONAL_IP_ADDR_ID = "additional_ip_addr_id";
  @SerializedName(SERIALIZED_NAME_ADDITIONAL_IP_ADDR_ID)
  private BigDecimal additionalIpAddrId;

  /**
   * Период оплаты.
   */
  @JsonAdapter(PaymentPeriodEnum.Adapter.class)
  public enum PaymentPeriodEnum {
    P1M("P1M"),
    
    P3M("P3M"),
    
    P6M("P6M"),
    
    P1Y("P1Y");

    private String value;

    PaymentPeriodEnum(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public static PaymentPeriodEnum fromValue(String value) {
      for (PaymentPeriodEnum b : PaymentPeriodEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }

    public static class Adapter extends TypeAdapter<PaymentPeriodEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final PaymentPeriodEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public PaymentPeriodEnum read(final JsonReader jsonReader) throws IOException {
        String value =  jsonReader.nextString();
        return PaymentPeriodEnum.fromValue(value);
      }
    }
  }

  public static final String SERIALIZED_NAME_PAYMENT_PERIOD = "payment_period";
  @SerializedName(SERIALIZED_NAME_PAYMENT_PERIOD)
  private PaymentPeriodEnum paymentPeriod;

  public static final String SERIALIZED_NAME_NAME = "name";
  @SerializedName(SERIALIZED_NAME_NAME)
  private String name;

  public static final String SERIALIZED_NAME_COMMENT = "comment";
  @SerializedName(SERIALIZED_NAME_COMMENT)
  private String comment;

  public CreateDedicatedServer() {
  }

  public CreateDedicatedServer planId(BigDecimal planId) {
    
    this.planId = planId;
    return this;
  }

   /**
   * Уникальный идентификатор списка дополнительных услуг выделенного сервера.
   * @return planId
  **/
  @javax.annotation.Nonnull
  public BigDecimal getPlanId() {
    return planId;
  }


  public void setPlanId(BigDecimal planId) {
    this.planId = planId;
  }


  public CreateDedicatedServer presetId(BigDecimal presetId) {
    
    this.presetId = presetId;
    return this;
  }

   /**
   * Уникальный идентификатор тарифа выделенного сервера.
   * @return presetId
  **/
  @javax.annotation.Nonnull
  public BigDecimal getPresetId() {
    return presetId;
  }


  public void setPresetId(BigDecimal presetId) {
    this.presetId = presetId;
  }


  public CreateDedicatedServer osId(BigDecimal osId) {
    
    this.osId = osId;
    return this;
  }

   /**
   * Уникальный идентификатор операционной системы, которая будет установлена на выделенный сервер.
   * @return osId
  **/
  @javax.annotation.Nullable
  public BigDecimal getOsId() {
    return osId;
  }


  public void setOsId(BigDecimal osId) {
    this.osId = osId;
  }


  public CreateDedicatedServer cpId(BigDecimal cpId) {
    
    this.cpId = cpId;
    return this;
  }

   /**
   * Уникальный идентификатор панели управления, которая будет установлена на выделенный сервер.
   * @return cpId
  **/
  @javax.annotation.Nullable
  public BigDecimal getCpId() {
    return cpId;
  }


  public void setCpId(BigDecimal cpId) {
    this.cpId = cpId;
  }


  public CreateDedicatedServer bandwidthId(BigDecimal bandwidthId) {
    
    this.bandwidthId = bandwidthId;
    return this;
  }

   /**
   * Уникальный идентификатор интернет-канала, который будет установлен на выделенный сервер.
   * @return bandwidthId
  **/
  @javax.annotation.Nullable
  public BigDecimal getBandwidthId() {
    return bandwidthId;
  }


  public void setBandwidthId(BigDecimal bandwidthId) {
    this.bandwidthId = bandwidthId;
  }


  public CreateDedicatedServer networkDriveId(BigDecimal networkDriveId) {
    
    this.networkDriveId = networkDriveId;
    return this;
  }

   /**
   * Уникальный идентификатор сетевого диска, который будет установлен на выделенный сервер.
   * @return networkDriveId
  **/
  @javax.annotation.Nullable
  public BigDecimal getNetworkDriveId() {
    return networkDriveId;
  }


  public void setNetworkDriveId(BigDecimal networkDriveId) {
    this.networkDriveId = networkDriveId;
  }


  public CreateDedicatedServer additionalIpAddrId(BigDecimal additionalIpAddrId) {
    
    this.additionalIpAddrId = additionalIpAddrId;
    return this;
  }

   /**
   * Уникальный идентификатор дополнительного IP-адреса, который будет установлен на выделенный сервер.
   * @return additionalIpAddrId
  **/
  @javax.annotation.Nullable
  public BigDecimal getAdditionalIpAddrId() {
    return additionalIpAddrId;
  }


  public void setAdditionalIpAddrId(BigDecimal additionalIpAddrId) {
    this.additionalIpAddrId = additionalIpAddrId;
  }


  public CreateDedicatedServer paymentPeriod(PaymentPeriodEnum paymentPeriod) {
    
    this.paymentPeriod = paymentPeriod;
    return this;
  }

   /**
   * Период оплаты.
   * @return paymentPeriod
  **/
  @javax.annotation.Nonnull
  public PaymentPeriodEnum getPaymentPeriod() {
    return paymentPeriod;
  }


  public void setPaymentPeriod(PaymentPeriodEnum paymentPeriod) {
    this.paymentPeriod = paymentPeriod;
  }


  public CreateDedicatedServer name(String name) {
    
    this.name = name;
    return this;
  }

   /**
   * Удобочитаемое имя выделенного сервера. Максимальная длина — 255 символов, имя должно быть уникальным.
   * @return name
  **/
  @javax.annotation.Nonnull
  public String getName() {
    return name;
  }


  public void setName(String name) {
    this.name = name;
  }


  public CreateDedicatedServer comment(String comment) {
    
    this.comment = comment;
    return this;
  }

   /**
   * Комментарий к выделенному серверу. Максимальная длина — 255 символов.
   * @return comment
  **/
  @javax.annotation.Nullable
  public String getComment() {
    return comment;
  }


  public void setComment(String comment) {
    this.comment = comment;
  }



  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CreateDedicatedServer createDedicatedServer = (CreateDedicatedServer) o;
    return Objects.equals(this.planId, createDedicatedServer.planId) &&
        Objects.equals(this.presetId, createDedicatedServer.presetId) &&
        Objects.equals(this.osId, createDedicatedServer.osId) &&
        Objects.equals(this.cpId, createDedicatedServer.cpId) &&
        Objects.equals(this.bandwidthId, createDedicatedServer.bandwidthId) &&
        Objects.equals(this.networkDriveId, createDedicatedServer.networkDriveId) &&
        Objects.equals(this.additionalIpAddrId, createDedicatedServer.additionalIpAddrId) &&
        Objects.equals(this.paymentPeriod, createDedicatedServer.paymentPeriod) &&
        Objects.equals(this.name, createDedicatedServer.name) &&
        Objects.equals(this.comment, createDedicatedServer.comment);
  }

  private static <T> boolean equalsNullable(JsonNullable<T> a, JsonNullable<T> b) {
    return a == b || (a != null && b != null && a.isPresent() && b.isPresent() && Objects.deepEquals(a.get(), b.get()));
  }

  @Override
  public int hashCode() {
    return Objects.hash(planId, presetId, osId, cpId, bandwidthId, networkDriveId, additionalIpAddrId, paymentPeriod, name, comment);
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
    sb.append("class CreateDedicatedServer {\n");
    sb.append("    planId: ").append(toIndentedString(planId)).append("\n");
    sb.append("    presetId: ").append(toIndentedString(presetId)).append("\n");
    sb.append("    osId: ").append(toIndentedString(osId)).append("\n");
    sb.append("    cpId: ").append(toIndentedString(cpId)).append("\n");
    sb.append("    bandwidthId: ").append(toIndentedString(bandwidthId)).append("\n");
    sb.append("    networkDriveId: ").append(toIndentedString(networkDriveId)).append("\n");
    sb.append("    additionalIpAddrId: ").append(toIndentedString(additionalIpAddrId)).append("\n");
    sb.append("    paymentPeriod: ").append(toIndentedString(paymentPeriod)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    comment: ").append(toIndentedString(comment)).append("\n");
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
    openapiFields.add("plan_id");
    openapiFields.add("preset_id");
    openapiFields.add("os_id");
    openapiFields.add("cp_id");
    openapiFields.add("bandwidth_id");
    openapiFields.add("network_drive_id");
    openapiFields.add("additional_ip_addr_id");
    openapiFields.add("payment_period");
    openapiFields.add("name");
    openapiFields.add("comment");

    // a set of required properties/fields (JSON key names)
    openapiRequiredFields = new HashSet<String>();
    openapiRequiredFields.add("plan_id");
    openapiRequiredFields.add("preset_id");
    openapiRequiredFields.add("payment_period");
    openapiRequiredFields.add("name");
  }

 /**
  * Validates the JSON Element and throws an exception if issues found
  *
  * @param jsonElement JSON Element
  * @throws IOException if the JSON Element is invalid with respect to CreateDedicatedServer
  */
  public static void validateJsonElement(JsonElement jsonElement) throws IOException {
      if (jsonElement == null) {
        if (!CreateDedicatedServer.openapiRequiredFields.isEmpty()) { // has required fields but JSON element is null
          throw new IllegalArgumentException(String.format("The required field(s) %s in CreateDedicatedServer is not found in the empty JSON string", CreateDedicatedServer.openapiRequiredFields.toString()));
        }
      }

      Set<Entry<String, JsonElement>> entries = jsonElement.getAsJsonObject().entrySet();
      // check to see if the JSON string contains additional fields
      for (Entry<String, JsonElement> entry : entries) {
        if (!CreateDedicatedServer.openapiFields.contains(entry.getKey())) {
          throw new IllegalArgumentException(String.format("The field `%s` in the JSON string is not defined in the `CreateDedicatedServer` properties. JSON: %s", entry.getKey(), jsonElement.toString()));
        }
      }

      // check to make sure all required properties/fields are present in the JSON string
      for (String requiredField : CreateDedicatedServer.openapiRequiredFields) {
        if (jsonElement.getAsJsonObject().get(requiredField) == null) {
          throw new IllegalArgumentException(String.format("The required field `%s` is not found in the JSON string: %s", requiredField, jsonElement.toString()));
        }
      }
        JsonObject jsonObj = jsonElement.getAsJsonObject();
      if (!jsonObj.get("payment_period").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `payment_period` to be a primitive type in the JSON string but got `%s`", jsonObj.get("payment_period").toString()));
      }
      if (!jsonObj.get("name").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `name` to be a primitive type in the JSON string but got `%s`", jsonObj.get("name").toString()));
      }
      if ((jsonObj.get("comment") != null && !jsonObj.get("comment").isJsonNull()) && !jsonObj.get("comment").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `comment` to be a primitive type in the JSON string but got `%s`", jsonObj.get("comment").toString()));
      }
  }

  public static class CustomTypeAdapterFactory implements TypeAdapterFactory {
    @SuppressWarnings("unchecked")
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
       if (!CreateDedicatedServer.class.isAssignableFrom(type.getRawType())) {
         return null; // this class only serializes 'CreateDedicatedServer' and its subtypes
       }
       final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);
       final TypeAdapter<CreateDedicatedServer> thisAdapter
                        = gson.getDelegateAdapter(this, TypeToken.get(CreateDedicatedServer.class));

       return (TypeAdapter<T>) new TypeAdapter<CreateDedicatedServer>() {
           @Override
           public void write(JsonWriter out, CreateDedicatedServer value) throws IOException {
             JsonObject obj = thisAdapter.toJsonTree(value).getAsJsonObject();
             elementAdapter.write(out, obj);
           }

           @Override
           public CreateDedicatedServer read(JsonReader in) throws IOException {
             JsonElement jsonElement = elementAdapter.read(in);
             validateJsonElement(jsonElement);
             return thisAdapter.fromJsonTree(jsonElement);
           }

       }.nullSafe();
    }
  }

 /**
  * Create an instance of CreateDedicatedServer given an JSON string
  *
  * @param jsonString JSON string
  * @return An instance of CreateDedicatedServer
  * @throws IOException if the JSON string is invalid with respect to CreateDedicatedServer
  */
  public static CreateDedicatedServer fromJson(String jsonString) throws IOException {
    return JSON.getGson().fromJson(jsonString, CreateDedicatedServer.class);
  }

 /**
  * Convert an instance of CreateDedicatedServer to an JSON string
  *
  * @return JSON string
  */
  public String toJson() {
    return JSON.getGson().toJson(this);
  }
}

