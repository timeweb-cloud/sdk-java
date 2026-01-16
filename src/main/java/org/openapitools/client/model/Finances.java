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
 * Платежная информация
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2026-01-16T15:24:58.872867Z[Etc/UTC]")
public class Finances {
  public static final String SERIALIZED_NAME_BALANCE = "balance";
  @SerializedName(SERIALIZED_NAME_BALANCE)
  private BigDecimal balance;

  public static final String SERIALIZED_NAME_CURRENCY = "currency";
  @SerializedName(SERIALIZED_NAME_CURRENCY)
  private String currency;

  public static final String SERIALIZED_NAME_DISCOUNT_END_DATE_AT = "discount_end_date_at";
  @SerializedName(SERIALIZED_NAME_DISCOUNT_END_DATE_AT)
  private String discountEndDateAt;

  public static final String SERIALIZED_NAME_DISCOUNT_PERCENT = "discount_percent";
  @SerializedName(SERIALIZED_NAME_DISCOUNT_PERCENT)
  private BigDecimal discountPercent;

  public static final String SERIALIZED_NAME_HOURLY_COST = "hourly_cost";
  @SerializedName(SERIALIZED_NAME_HOURLY_COST)
  private BigDecimal hourlyCost;

  public static final String SERIALIZED_NAME_HOURLY_FEE = "hourly_fee";
  @SerializedName(SERIALIZED_NAME_HOURLY_FEE)
  private BigDecimal hourlyFee;

  public static final String SERIALIZED_NAME_MONTHLY_COST = "monthly_cost";
  @SerializedName(SERIALIZED_NAME_MONTHLY_COST)
  private BigDecimal monthlyCost;

  public static final String SERIALIZED_NAME_MONTHLY_FEE = "monthly_fee";
  @SerializedName(SERIALIZED_NAME_MONTHLY_FEE)
  private BigDecimal monthlyFee;

  public static final String SERIALIZED_NAME_TOTAL_PAID = "total_paid";
  @SerializedName(SERIALIZED_NAME_TOTAL_PAID)
  private BigDecimal totalPaid;

  public static final String SERIALIZED_NAME_HOURS_LEFT = "hours_left";
  @SerializedName(SERIALIZED_NAME_HOURS_LEFT)
  private BigDecimal hoursLeft;

  public static final String SERIALIZED_NAME_AUTOPAY_CARD_INFO = "autopay_card_info";
  @SerializedName(SERIALIZED_NAME_AUTOPAY_CARD_INFO)
  private String autopayCardInfo;

  public Finances() {
  }

  public Finances balance(BigDecimal balance) {
    
    this.balance = balance;
    return this;
  }

   /**
   * Баланс аккаунта.
   * @return balance
  **/
  @javax.annotation.Nonnull
  public BigDecimal getBalance() {
    return balance;
  }


  public void setBalance(BigDecimal balance) {
    this.balance = balance;
  }


  public Finances currency(String currency) {
    
    this.currency = currency;
    return this;
  }

   /**
   * Валюта, которая используется на аккаунте.
   * @return currency
  **/
  @javax.annotation.Nonnull
  public String getCurrency() {
    return currency;
  }


  public void setCurrency(String currency) {
    this.currency = currency;
  }


  public Finances discountEndDateAt(String discountEndDateAt) {
    
    this.discountEndDateAt = discountEndDateAt;
    return this;
  }

   /**
   * Значение времени, указанное в комбинированном формате даты и времени ISO8601, которое представляет, когда заканчивается скидка для аккаунта.
   * @return discountEndDateAt
  **/
  @javax.annotation.Nullable
  public String getDiscountEndDateAt() {
    return discountEndDateAt;
  }


  public void setDiscountEndDateAt(String discountEndDateAt) {
    this.discountEndDateAt = discountEndDateAt;
  }


  public Finances discountPercent(BigDecimal discountPercent) {
    
    this.discountPercent = discountPercent;
    return this;
  }

   /**
   * Процент скидки для аккаунта.
   * @return discountPercent
  **/
  @javax.annotation.Nonnull
  public BigDecimal getDiscountPercent() {
    return discountPercent;
  }


  public void setDiscountPercent(BigDecimal discountPercent) {
    this.discountPercent = discountPercent;
  }


  public Finances hourlyCost(BigDecimal hourlyCost) {
    
    this.hourlyCost = hourlyCost;
    return this;
  }

   /**
   * Стоимость услуг на аккаунте в час.
   * @return hourlyCost
  **/
  @javax.annotation.Nonnull
  public BigDecimal getHourlyCost() {
    return hourlyCost;
  }


  public void setHourlyCost(BigDecimal hourlyCost) {
    this.hourlyCost = hourlyCost;
  }


  public Finances hourlyFee(BigDecimal hourlyFee) {
    
    this.hourlyFee = hourlyFee;
    return this;
  }

   /**
   * Абонентская плата в час (с учетом скидок).
   * @return hourlyFee
  **/
  @javax.annotation.Nonnull
  public BigDecimal getHourlyFee() {
    return hourlyFee;
  }


  public void setHourlyFee(BigDecimal hourlyFee) {
    this.hourlyFee = hourlyFee;
  }


  public Finances monthlyCost(BigDecimal monthlyCost) {
    
    this.monthlyCost = monthlyCost;
    return this;
  }

   /**
   * Стоимость услуг на аккаунте в месяц.
   * @return monthlyCost
  **/
  @javax.annotation.Nonnull
  public BigDecimal getMonthlyCost() {
    return monthlyCost;
  }


  public void setMonthlyCost(BigDecimal monthlyCost) {
    this.monthlyCost = monthlyCost;
  }


  public Finances monthlyFee(BigDecimal monthlyFee) {
    
    this.monthlyFee = monthlyFee;
    return this;
  }

   /**
   * Абонентская плата в месяц (с учетом скидок).
   * @return monthlyFee
  **/
  @javax.annotation.Nonnull
  public BigDecimal getMonthlyFee() {
    return monthlyFee;
  }


  public void setMonthlyFee(BigDecimal monthlyFee) {
    this.monthlyFee = monthlyFee;
  }


  public Finances totalPaid(BigDecimal totalPaid) {
    
    this.totalPaid = totalPaid;
    return this;
  }

   /**
   * Общая сумма платежей на аккаунте.
   * @return totalPaid
  **/
  @javax.annotation.Nonnull
  public BigDecimal getTotalPaid() {
    return totalPaid;
  }


  public void setTotalPaid(BigDecimal totalPaid) {
    this.totalPaid = totalPaid;
  }


  public Finances hoursLeft(BigDecimal hoursLeft) {
    
    this.hoursLeft = hoursLeft;
    return this;
  }

   /**
   * Сколько часов работы услуг оплачено на аккаунте.
   * @return hoursLeft
  **/
  @javax.annotation.Nullable
  public BigDecimal getHoursLeft() {
    return hoursLeft;
  }


  public void setHoursLeft(BigDecimal hoursLeft) {
    this.hoursLeft = hoursLeft;
  }


  public Finances autopayCardInfo(String autopayCardInfo) {
    
    this.autopayCardInfo = autopayCardInfo;
    return this;
  }

   /**
   * Информация о карте, используемой для автоплатежей.
   * @return autopayCardInfo
  **/
  @javax.annotation.Nullable
  public String getAutopayCardInfo() {
    return autopayCardInfo;
  }


  public void setAutopayCardInfo(String autopayCardInfo) {
    this.autopayCardInfo = autopayCardInfo;
  }



  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Finances finances = (Finances) o;
    return Objects.equals(this.balance, finances.balance) &&
        Objects.equals(this.currency, finances.currency) &&
        Objects.equals(this.discountEndDateAt, finances.discountEndDateAt) &&
        Objects.equals(this.discountPercent, finances.discountPercent) &&
        Objects.equals(this.hourlyCost, finances.hourlyCost) &&
        Objects.equals(this.hourlyFee, finances.hourlyFee) &&
        Objects.equals(this.monthlyCost, finances.monthlyCost) &&
        Objects.equals(this.monthlyFee, finances.monthlyFee) &&
        Objects.equals(this.totalPaid, finances.totalPaid) &&
        Objects.equals(this.hoursLeft, finances.hoursLeft) &&
        Objects.equals(this.autopayCardInfo, finances.autopayCardInfo);
  }

  @Override
  public int hashCode() {
    return Objects.hash(balance, currency, discountEndDateAt, discountPercent, hourlyCost, hourlyFee, monthlyCost, monthlyFee, totalPaid, hoursLeft, autopayCardInfo);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Finances {\n");
    sb.append("    balance: ").append(toIndentedString(balance)).append("\n");
    sb.append("    currency: ").append(toIndentedString(currency)).append("\n");
    sb.append("    discountEndDateAt: ").append(toIndentedString(discountEndDateAt)).append("\n");
    sb.append("    discountPercent: ").append(toIndentedString(discountPercent)).append("\n");
    sb.append("    hourlyCost: ").append(toIndentedString(hourlyCost)).append("\n");
    sb.append("    hourlyFee: ").append(toIndentedString(hourlyFee)).append("\n");
    sb.append("    monthlyCost: ").append(toIndentedString(monthlyCost)).append("\n");
    sb.append("    monthlyFee: ").append(toIndentedString(monthlyFee)).append("\n");
    sb.append("    totalPaid: ").append(toIndentedString(totalPaid)).append("\n");
    sb.append("    hoursLeft: ").append(toIndentedString(hoursLeft)).append("\n");
    sb.append("    autopayCardInfo: ").append(toIndentedString(autopayCardInfo)).append("\n");
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
    openapiFields.add("balance");
    openapiFields.add("currency");
    openapiFields.add("discount_end_date_at");
    openapiFields.add("discount_percent");
    openapiFields.add("hourly_cost");
    openapiFields.add("hourly_fee");
    openapiFields.add("monthly_cost");
    openapiFields.add("monthly_fee");
    openapiFields.add("total_paid");
    openapiFields.add("hours_left");
    openapiFields.add("autopay_card_info");

    // a set of required properties/fields (JSON key names)
    openapiRequiredFields = new HashSet<String>();
    openapiRequiredFields.add("balance");
    openapiRequiredFields.add("currency");
    openapiRequiredFields.add("discount_end_date_at");
    openapiRequiredFields.add("discount_percent");
    openapiRequiredFields.add("hourly_cost");
    openapiRequiredFields.add("hourly_fee");
    openapiRequiredFields.add("monthly_cost");
    openapiRequiredFields.add("monthly_fee");
    openapiRequiredFields.add("total_paid");
    openapiRequiredFields.add("hours_left");
    openapiRequiredFields.add("autopay_card_info");
  }

 /**
  * Validates the JSON Element and throws an exception if issues found
  *
  * @param jsonElement JSON Element
  * @throws IOException if the JSON Element is invalid with respect to Finances
  */
  public static void validateJsonElement(JsonElement jsonElement) throws IOException {
      if (jsonElement == null) {
        if (!Finances.openapiRequiredFields.isEmpty()) { // has required fields but JSON element is null
          throw new IllegalArgumentException(String.format("The required field(s) %s in Finances is not found in the empty JSON string", Finances.openapiRequiredFields.toString()));
        }
      }

      Set<Entry<String, JsonElement>> entries = jsonElement.getAsJsonObject().entrySet();
      // check to see if the JSON string contains additional fields
      for (Entry<String, JsonElement> entry : entries) {
        if (!Finances.openapiFields.contains(entry.getKey())) {
          throw new IllegalArgumentException(String.format("The field `%s` in the JSON string is not defined in the `Finances` properties. JSON: %s", entry.getKey(), jsonElement.toString()));
        }
      }

      // check to make sure all required properties/fields are present in the JSON string
      for (String requiredField : Finances.openapiRequiredFields) {
        if (jsonElement.getAsJsonObject().get(requiredField) == null) {
          throw new IllegalArgumentException(String.format("The required field `%s` is not found in the JSON string: %s", requiredField, jsonElement.toString()));
        }
      }
        JsonObject jsonObj = jsonElement.getAsJsonObject();
      if (!jsonObj.get("currency").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `currency` to be a primitive type in the JSON string but got `%s`", jsonObj.get("currency").toString()));
      }
      if ((jsonObj.get("discount_end_date_at") != null && !jsonObj.get("discount_end_date_at").isJsonNull()) && !jsonObj.get("discount_end_date_at").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `discount_end_date_at` to be a primitive type in the JSON string but got `%s`", jsonObj.get("discount_end_date_at").toString()));
      }
      if ((jsonObj.get("autopay_card_info") != null && !jsonObj.get("autopay_card_info").isJsonNull()) && !jsonObj.get("autopay_card_info").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `autopay_card_info` to be a primitive type in the JSON string but got `%s`", jsonObj.get("autopay_card_info").toString()));
      }
  }

  public static class CustomTypeAdapterFactory implements TypeAdapterFactory {
    @SuppressWarnings("unchecked")
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
       if (!Finances.class.isAssignableFrom(type.getRawType())) {
         return null; // this class only serializes 'Finances' and its subtypes
       }
       final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);
       final TypeAdapter<Finances> thisAdapter
                        = gson.getDelegateAdapter(this, TypeToken.get(Finances.class));

       return (TypeAdapter<T>) new TypeAdapter<Finances>() {
           @Override
           public void write(JsonWriter out, Finances value) throws IOException {
             JsonObject obj = thisAdapter.toJsonTree(value).getAsJsonObject();
             elementAdapter.write(out, obj);
           }

           @Override
           public Finances read(JsonReader in) throws IOException {
             JsonElement jsonElement = elementAdapter.read(in);
             validateJsonElement(jsonElement);
             return thisAdapter.fromJsonTree(jsonElement);
           }

       }.nullSafe();
    }
  }

 /**
  * Create an instance of Finances given an JSON string
  *
  * @param jsonString JSON string
  * @return An instance of Finances
  * @throws IOException if the JSON string is invalid with respect to Finances
  */
  public static Finances fromJson(String jsonString) throws IOException {
    return JSON.getGson().fromJson(jsonString, Finances.class);
  }

 /**
  * Convert an instance of Finances to an JSON string
  *
  * @return JSON string
  */
  public String toJson() {
    return JSON.getGson().toJson(this);
  }
}

