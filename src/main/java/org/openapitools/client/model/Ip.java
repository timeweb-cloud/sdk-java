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
 * Администратор домена — индивидуальный предприниматель
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2026-08-06T09:16:26.141714Z[Etc/UTC]")
public class Ip {
  /**
   * Тип администратора.
   */
  @JsonAdapter(TypeEnum.Adapter.class)
  public enum TypeEnum {
    IP("ip");

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

  public static final String SERIALIZED_NAME_IS_RESIDENT = "is_resident";
  @SerializedName(SERIALIZED_NAME_IS_RESIDENT)
  private Boolean isResident;

  public static final String SERIALIZED_NAME_BIRTHDATE = "birthdate";
  @SerializedName(SERIALIZED_NAME_BIRTHDATE)
  private String birthdate;

  public static final String SERIALIZED_NAME_PASSPORT_DATE = "passport_date";
  @SerializedName(SERIALIZED_NAME_PASSPORT_DATE)
  private String passportDate;

  public static final String SERIALIZED_NAME_PASSPORT_NUMBER = "passport_number";
  @SerializedName(SERIALIZED_NAME_PASSPORT_NUMBER)
  private String passportNumber;

  public static final String SERIALIZED_NAME_PASSPORT_PLACE = "passport_place";
  @SerializedName(SERIALIZED_NAME_PASSPORT_PLACE)
  private String passportPlace;

  public static final String SERIALIZED_NAME_PASSPORT_SERIES = "passport_series";
  @SerializedName(SERIALIZED_NAME_PASSPORT_SERIES)
  private String passportSeries;

  public static final String SERIALIZED_NAME_INN = "inn";
  @SerializedName(SERIALIZED_NAME_INN)
  private String inn;

  public static final String SERIALIZED_NAME_POSTCODE = "postcode";
  @SerializedName(SERIALIZED_NAME_POSTCODE)
  private String postcode;

  public static final String SERIALIZED_NAME_MAILING_ADDRESS = "mailing_address";
  @SerializedName(SERIALIZED_NAME_MAILING_ADDRESS)
  private String mailingAddress;

  public static final String SERIALIZED_NAME_PHONE = "phone";
  @SerializedName(SERIALIZED_NAME_PHONE)
  private String phone;

  public static final String SERIALIZED_NAME_EMAIL = "email";
  @SerializedName(SERIALIZED_NAME_EMAIL)
  private String email;

  public static final String SERIALIZED_NAME_COUNTRY_CODE = "country_code";
  @SerializedName(SERIALIZED_NAME_COUNTRY_CODE)
  private String countryCode;

  public Ip() {
  }

  public Ip type(TypeEnum type) {
    
    this.type = type;
    return this;
  }

   /**
   * Тип администратора.
   * @return type
  **/
  @javax.annotation.Nonnull
  public TypeEnum getType() {
    return type;
  }


  public void setType(TypeEnum type) {
    this.type = type;
  }


  public Ip name(String name) {
    
    this.name = name;
    return this;
  }

   /**
   * ФИО индивидуального предпринимателя.
   * @return name
  **/
  @javax.annotation.Nonnull
  public String getName() {
    return name;
  }


  public void setName(String name) {
    this.name = name;
  }


  public Ip isResident(Boolean isResident) {
    
    this.isResident = isResident;
    return this;
  }

   /**
   * Это логическое значение, которое показывает, является ли администратор резидентом РФ.
   * @return isResident
  **/
  @javax.annotation.Nonnull
  public Boolean getIsResident() {
    return isResident;
  }


  public void setIsResident(Boolean isResident) {
    this.isResident = isResident;
  }


  public Ip birthdate(String birthdate) {
    
    this.birthdate = birthdate;
    return this;
  }

   /**
   * Дата рождения.
   * @return birthdate
  **/
  @javax.annotation.Nonnull
  public String getBirthdate() {
    return birthdate;
  }


  public void setBirthdate(String birthdate) {
    this.birthdate = birthdate;
  }


  public Ip passportDate(String passportDate) {
    
    this.passportDate = passportDate;
    return this;
  }

   /**
   * Дата выдачи паспорта.
   * @return passportDate
  **/
  @javax.annotation.Nonnull
  public String getPassportDate() {
    return passportDate;
  }


  public void setPassportDate(String passportDate) {
    this.passportDate = passportDate;
  }


  public Ip passportNumber(String passportNumber) {
    
    this.passportNumber = passportNumber;
    return this;
  }

   /**
   * Номер паспорта.
   * @return passportNumber
  **/
  @javax.annotation.Nonnull
  public String getPassportNumber() {
    return passportNumber;
  }


  public void setPassportNumber(String passportNumber) {
    this.passportNumber = passportNumber;
  }


  public Ip passportPlace(String passportPlace) {
    
    this.passportPlace = passportPlace;
    return this;
  }

   /**
   * Кем выдан паспорт.
   * @return passportPlace
  **/
  @javax.annotation.Nonnull
  public String getPassportPlace() {
    return passportPlace;
  }


  public void setPassportPlace(String passportPlace) {
    this.passportPlace = passportPlace;
  }


  public Ip passportSeries(String passportSeries) {
    
    this.passportSeries = passportSeries;
    return this;
  }

   /**
   * Серия паспорта.
   * @return passportSeries
  **/
  @javax.annotation.Nonnull
  public String getPassportSeries() {
    return passportSeries;
  }


  public void setPassportSeries(String passportSeries) {
    this.passportSeries = passportSeries;
  }


  public Ip inn(String inn) {
    
    this.inn = inn;
    return this;
  }

   /**
   * ИНН индивидуального предпринимателя.
   * @return inn
  **/
  @javax.annotation.Nonnull
  public String getInn() {
    return inn;
  }


  public void setInn(String inn) {
    this.inn = inn;
  }


  public Ip postcode(String postcode) {
    
    this.postcode = postcode;
    return this;
  }

   /**
   * Почтовый индекс.
   * @return postcode
  **/
  @javax.annotation.Nonnull
  public String getPostcode() {
    return postcode;
  }


  public void setPostcode(String postcode) {
    this.postcode = postcode;
  }


  public Ip mailingAddress(String mailingAddress) {
    
    this.mailingAddress = mailingAddress;
    return this;
  }

   /**
   * Почтовый адрес.
   * @return mailingAddress
  **/
  @javax.annotation.Nonnull
  public String getMailingAddress() {
    return mailingAddress;
  }


  public void setMailingAddress(String mailingAddress) {
    this.mailingAddress = mailingAddress;
  }


  public Ip phone(String phone) {
    
    this.phone = phone;
    return this;
  }

   /**
   * Контактный телефон.
   * @return phone
  **/
  @javax.annotation.Nonnull
  public String getPhone() {
    return phone;
  }


  public void setPhone(String phone) {
    this.phone = phone;
  }


  public Ip email(String email) {
    
    this.email = email;
    return this;
  }

   /**
   * Адрес электронной почты.
   * @return email
  **/
  @javax.annotation.Nonnull
  public String getEmail() {
    return email;
  }


  public void setEmail(String email) {
    this.email = email;
  }


  public Ip countryCode(String countryCode) {
    
    this.countryCode = countryCode;
    return this;
  }

   /**
   * Код страны. Только для нерезидентов РФ (&#x60;is_resident: false&#x60;); для резидентов поле передавать не нужно.
   * @return countryCode
  **/
  @javax.annotation.Nullable
  public String getCountryCode() {
    return countryCode;
  }


  public void setCountryCode(String countryCode) {
    this.countryCode = countryCode;
  }



  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Ip ip = (Ip) o;
    return Objects.equals(this.type, ip.type) &&
        Objects.equals(this.name, ip.name) &&
        Objects.equals(this.isResident, ip.isResident) &&
        Objects.equals(this.birthdate, ip.birthdate) &&
        Objects.equals(this.passportDate, ip.passportDate) &&
        Objects.equals(this.passportNumber, ip.passportNumber) &&
        Objects.equals(this.passportPlace, ip.passportPlace) &&
        Objects.equals(this.passportSeries, ip.passportSeries) &&
        Objects.equals(this.inn, ip.inn) &&
        Objects.equals(this.postcode, ip.postcode) &&
        Objects.equals(this.mailingAddress, ip.mailingAddress) &&
        Objects.equals(this.phone, ip.phone) &&
        Objects.equals(this.email, ip.email) &&
        Objects.equals(this.countryCode, ip.countryCode);
  }

  private static <T> boolean equalsNullable(JsonNullable<T> a, JsonNullable<T> b) {
    return a == b || (a != null && b != null && a.isPresent() && b.isPresent() && Objects.deepEquals(a.get(), b.get()));
  }

  @Override
  public int hashCode() {
    return Objects.hash(type, name, isResident, birthdate, passportDate, passportNumber, passportPlace, passportSeries, inn, postcode, mailingAddress, phone, email, countryCode);
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
    sb.append("class Ip {\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    isResident: ").append(toIndentedString(isResident)).append("\n");
    sb.append("    birthdate: ").append(toIndentedString(birthdate)).append("\n");
    sb.append("    passportDate: ").append(toIndentedString(passportDate)).append("\n");
    sb.append("    passportNumber: ").append(toIndentedString(passportNumber)).append("\n");
    sb.append("    passportPlace: ").append(toIndentedString(passportPlace)).append("\n");
    sb.append("    passportSeries: ").append(toIndentedString(passportSeries)).append("\n");
    sb.append("    inn: ").append(toIndentedString(inn)).append("\n");
    sb.append("    postcode: ").append(toIndentedString(postcode)).append("\n");
    sb.append("    mailingAddress: ").append(toIndentedString(mailingAddress)).append("\n");
    sb.append("    phone: ").append(toIndentedString(phone)).append("\n");
    sb.append("    email: ").append(toIndentedString(email)).append("\n");
    sb.append("    countryCode: ").append(toIndentedString(countryCode)).append("\n");
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
    openapiFields.add("type");
    openapiFields.add("name");
    openapiFields.add("is_resident");
    openapiFields.add("birthdate");
    openapiFields.add("passport_date");
    openapiFields.add("passport_number");
    openapiFields.add("passport_place");
    openapiFields.add("passport_series");
    openapiFields.add("inn");
    openapiFields.add("postcode");
    openapiFields.add("mailing_address");
    openapiFields.add("phone");
    openapiFields.add("email");
    openapiFields.add("country_code");

    // a set of required properties/fields (JSON key names)
    openapiRequiredFields = new HashSet<String>();
    openapiRequiredFields.add("type");
    openapiRequiredFields.add("name");
    openapiRequiredFields.add("is_resident");
    openapiRequiredFields.add("birthdate");
    openapiRequiredFields.add("passport_date");
    openapiRequiredFields.add("passport_number");
    openapiRequiredFields.add("passport_place");
    openapiRequiredFields.add("passport_series");
    openapiRequiredFields.add("inn");
    openapiRequiredFields.add("postcode");
    openapiRequiredFields.add("mailing_address");
    openapiRequiredFields.add("phone");
    openapiRequiredFields.add("email");
  }

 /**
  * Validates the JSON Element and throws an exception if issues found
  *
  * @param jsonElement JSON Element
  * @throws IOException if the JSON Element is invalid with respect to Ip
  */
  public static void validateJsonElement(JsonElement jsonElement) throws IOException {
      if (jsonElement == null) {
        if (!Ip.openapiRequiredFields.isEmpty()) { // has required fields but JSON element is null
          throw new IllegalArgumentException(String.format("The required field(s) %s in Ip is not found in the empty JSON string", Ip.openapiRequiredFields.toString()));
        }
      }

      Set<Entry<String, JsonElement>> entries = jsonElement.getAsJsonObject().entrySet();
      // check to see if the JSON string contains additional fields
      for (Entry<String, JsonElement> entry : entries) {
        if (!Ip.openapiFields.contains(entry.getKey())) {
          throw new IllegalArgumentException(String.format("The field `%s` in the JSON string is not defined in the `Ip` properties. JSON: %s", entry.getKey(), jsonElement.toString()));
        }
      }

      // check to make sure all required properties/fields are present in the JSON string
      for (String requiredField : Ip.openapiRequiredFields) {
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
      if (!jsonObj.get("birthdate").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `birthdate` to be a primitive type in the JSON string but got `%s`", jsonObj.get("birthdate").toString()));
      }
      if (!jsonObj.get("passport_date").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `passport_date` to be a primitive type in the JSON string but got `%s`", jsonObj.get("passport_date").toString()));
      }
      if (!jsonObj.get("passport_number").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `passport_number` to be a primitive type in the JSON string but got `%s`", jsonObj.get("passport_number").toString()));
      }
      if (!jsonObj.get("passport_place").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `passport_place` to be a primitive type in the JSON string but got `%s`", jsonObj.get("passport_place").toString()));
      }
      if (!jsonObj.get("passport_series").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `passport_series` to be a primitive type in the JSON string but got `%s`", jsonObj.get("passport_series").toString()));
      }
      if (!jsonObj.get("inn").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `inn` to be a primitive type in the JSON string but got `%s`", jsonObj.get("inn").toString()));
      }
      if (!jsonObj.get("postcode").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `postcode` to be a primitive type in the JSON string but got `%s`", jsonObj.get("postcode").toString()));
      }
      if (!jsonObj.get("mailing_address").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `mailing_address` to be a primitive type in the JSON string but got `%s`", jsonObj.get("mailing_address").toString()));
      }
      if (!jsonObj.get("phone").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `phone` to be a primitive type in the JSON string but got `%s`", jsonObj.get("phone").toString()));
      }
      if (!jsonObj.get("email").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `email` to be a primitive type in the JSON string but got `%s`", jsonObj.get("email").toString()));
      }
      if ((jsonObj.get("country_code") != null && !jsonObj.get("country_code").isJsonNull()) && !jsonObj.get("country_code").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `country_code` to be a primitive type in the JSON string but got `%s`", jsonObj.get("country_code").toString()));
      }
  }

  public static class CustomTypeAdapterFactory implements TypeAdapterFactory {
    @SuppressWarnings("unchecked")
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
       if (!Ip.class.isAssignableFrom(type.getRawType())) {
         return null; // this class only serializes 'Ip' and its subtypes
       }
       final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);
       final TypeAdapter<Ip> thisAdapter
                        = gson.getDelegateAdapter(this, TypeToken.get(Ip.class));

       return (TypeAdapter<T>) new TypeAdapter<Ip>() {
           @Override
           public void write(JsonWriter out, Ip value) throws IOException {
             JsonObject obj = thisAdapter.toJsonTree(value).getAsJsonObject();
             elementAdapter.write(out, obj);
           }

           @Override
           public Ip read(JsonReader in) throws IOException {
             JsonElement jsonElement = elementAdapter.read(in);
             validateJsonElement(jsonElement);
             return thisAdapter.fromJsonTree(jsonElement);
           }

       }.nullSafe();
    }
  }

 /**
  * Create an instance of Ip given an JSON string
  *
  * @param jsonString JSON string
  * @return An instance of Ip
  * @throws IOException if the JSON string is invalid with respect to Ip
  */
  public static Ip fromJson(String jsonString) throws IOException {
    return JSON.getGson().fromJson(jsonString, Ip.class);
  }

 /**
  * Convert an instance of Ip to an JSON string
  *
  * @return JSON string
  */
  public String toJson() {
    return JSON.getGson().toJson(this);
  }
}

