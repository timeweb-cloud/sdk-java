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
import org.openapitools.client.model.ConfigDeliveryPackaging;
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
 * Настройки раздачи контента
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2026-08-06T15:06:51.562821Z[Etc/UTC]")
public class ConfigDelivery {
  public static final String SERIALIZED_NAME_HTTP3 = "http3";
  @SerializedName(SERIALIZED_NAME_HTTP3)
  private Boolean http3;

  public static final String SERIALIZED_NAME_GZIP = "gzip";
  @SerializedName(SERIALIZED_NAME_GZIP)
  private Boolean gzip;

  public static final String SERIALIZED_NAME_LARGE_FILES = "large_files";
  @SerializedName(SERIALIZED_NAME_LARGE_FILES)
  private Boolean largeFiles;

  public static final String SERIALIZED_NAME_SLICE_SIZE = "slice_size";
  @SerializedName(SERIALIZED_NAME_SLICE_SIZE)
  private Integer sliceSize;

  public static final String SERIALIZED_NAME_IMAGE_OPTIMIZATION = "image_optimization";
  @SerializedName(SERIALIZED_NAME_IMAGE_OPTIMIZATION)
  private Boolean imageOptimization;

  public static final String SERIALIZED_NAME_PACKAGING = "packaging";
  @SerializedName(SERIALIZED_NAME_PACKAGING)
  private ConfigDeliveryPackaging packaging;

  public ConfigDelivery() {
  }

  public ConfigDelivery http3(Boolean http3) {
    
    this.http3 = http3;
    return this;
  }

   /**
   * Раздавать контент по HTTP/3 (QUIC).
   * @return http3
  **/
  @javax.annotation.Nullable
  public Boolean getHttp3() {
    return http3;
  }


  public void setHttp3(Boolean http3) {
    this.http3 = http3;
  }


  public ConfigDelivery gzip(Boolean gzip) {
    
    this.gzip = gzip;
    return this;
  }

   /**
   * Сжимать ответы алгоритмом gzip.
   * @return gzip
  **/
  @javax.annotation.Nullable
  public Boolean getGzip() {
    return gzip;
  }


  public void setGzip(Boolean gzip) {
    this.gzip = gzip;
  }


  public ConfigDelivery largeFiles(Boolean largeFiles) {
    
    this.largeFiles = largeFiles;
    return this;
  }

   /**
   * Режим раздачи больших файлов.
   * @return largeFiles
  **/
  @javax.annotation.Nullable
  public Boolean getLargeFiles() {
    return largeFiles;
  }


  public void setLargeFiles(Boolean largeFiles) {
    this.largeFiles = largeFiles;
  }


  public ConfigDelivery sliceSize(Integer sliceSize) {
    
    this.sliceSize = sliceSize;
    return this;
  }

   /**
   * Размер слайса в мегабайтах — файл забирается с источника и кэшируется частями такого размера. &#x60;null&#x60; — не использовать слайсинг.
   * minimum: 1
   * maximum: 1024
   * @return sliceSize
  **/
  @javax.annotation.Nullable
  public Integer getSliceSize() {
    return sliceSize;
  }


  public void setSliceSize(Integer sliceSize) {
    this.sliceSize = sliceSize;
  }


  public ConfigDelivery imageOptimization(Boolean imageOptimization) {
    
    this.imageOptimization = imageOptimization;
    return this;
  }

   /**
   * Оптимизировать изображения на лету.
   * @return imageOptimization
  **/
  @javax.annotation.Nullable
  public Boolean getImageOptimization() {
    return imageOptimization;
  }


  public void setImageOptimization(Boolean imageOptimization) {
    this.imageOptimization = imageOptimization;
  }


  public ConfigDelivery packaging(ConfigDeliveryPackaging packaging) {
    
    this.packaging = packaging;
    return this;
  }

   /**
   * Get packaging
   * @return packaging
  **/
  @javax.annotation.Nullable
  public ConfigDeliveryPackaging getPackaging() {
    return packaging;
  }


  public void setPackaging(ConfigDeliveryPackaging packaging) {
    this.packaging = packaging;
  }



  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ConfigDelivery configDelivery = (ConfigDelivery) o;
    return Objects.equals(this.http3, configDelivery.http3) &&
        Objects.equals(this.gzip, configDelivery.gzip) &&
        Objects.equals(this.largeFiles, configDelivery.largeFiles) &&
        Objects.equals(this.sliceSize, configDelivery.sliceSize) &&
        Objects.equals(this.imageOptimization, configDelivery.imageOptimization) &&
        Objects.equals(this.packaging, configDelivery.packaging);
  }

  private static <T> boolean equalsNullable(JsonNullable<T> a, JsonNullable<T> b) {
    return a == b || (a != null && b != null && a.isPresent() && b.isPresent() && Objects.deepEquals(a.get(), b.get()));
  }

  @Override
  public int hashCode() {
    return Objects.hash(http3, gzip, largeFiles, sliceSize, imageOptimization, packaging);
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
    sb.append("class ConfigDelivery {\n");
    sb.append("    http3: ").append(toIndentedString(http3)).append("\n");
    sb.append("    gzip: ").append(toIndentedString(gzip)).append("\n");
    sb.append("    largeFiles: ").append(toIndentedString(largeFiles)).append("\n");
    sb.append("    sliceSize: ").append(toIndentedString(sliceSize)).append("\n");
    sb.append("    imageOptimization: ").append(toIndentedString(imageOptimization)).append("\n");
    sb.append("    packaging: ").append(toIndentedString(packaging)).append("\n");
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
    openapiFields.add("http3");
    openapiFields.add("gzip");
    openapiFields.add("large_files");
    openapiFields.add("slice_size");
    openapiFields.add("image_optimization");
    openapiFields.add("packaging");

    // a set of required properties/fields (JSON key names)
    openapiRequiredFields = new HashSet<String>();
  }

 /**
  * Validates the JSON Element and throws an exception if issues found
  *
  * @param jsonElement JSON Element
  * @throws IOException if the JSON Element is invalid with respect to ConfigDelivery
  */
  public static void validateJsonElement(JsonElement jsonElement) throws IOException {
      if (jsonElement == null) {
        if (!ConfigDelivery.openapiRequiredFields.isEmpty()) { // has required fields but JSON element is null
          throw new IllegalArgumentException(String.format("The required field(s) %s in ConfigDelivery is not found in the empty JSON string", ConfigDelivery.openapiRequiredFields.toString()));
        }
      }

      Set<Entry<String, JsonElement>> entries = jsonElement.getAsJsonObject().entrySet();
      // check to see if the JSON string contains additional fields
      for (Entry<String, JsonElement> entry : entries) {
        if (!ConfigDelivery.openapiFields.contains(entry.getKey())) {
          throw new IllegalArgumentException(String.format("The field `%s` in the JSON string is not defined in the `ConfigDelivery` properties. JSON: %s", entry.getKey(), jsonElement.toString()));
        }
      }
        JsonObject jsonObj = jsonElement.getAsJsonObject();
      // validate the optional field `packaging`
      if (jsonObj.get("packaging") != null && !jsonObj.get("packaging").isJsonNull()) {
        ConfigDeliveryPackaging.validateJsonElement(jsonObj.get("packaging"));
      }
  }

  public static class CustomTypeAdapterFactory implements TypeAdapterFactory {
    @SuppressWarnings("unchecked")
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
       if (!ConfigDelivery.class.isAssignableFrom(type.getRawType())) {
         return null; // this class only serializes 'ConfigDelivery' and its subtypes
       }
       final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);
       final TypeAdapter<ConfigDelivery> thisAdapter
                        = gson.getDelegateAdapter(this, TypeToken.get(ConfigDelivery.class));

       return (TypeAdapter<T>) new TypeAdapter<ConfigDelivery>() {
           @Override
           public void write(JsonWriter out, ConfigDelivery value) throws IOException {
             JsonObject obj = thisAdapter.toJsonTree(value).getAsJsonObject();
             elementAdapter.write(out, obj);
           }

           @Override
           public ConfigDelivery read(JsonReader in) throws IOException {
             JsonElement jsonElement = elementAdapter.read(in);
             validateJsonElement(jsonElement);
             return thisAdapter.fromJsonTree(jsonElement);
           }

       }.nullSafe();
    }
  }

 /**
  * Create an instance of ConfigDelivery given an JSON string
  *
  * @param jsonString JSON string
  * @return An instance of ConfigDelivery
  * @throws IOException if the JSON string is invalid with respect to ConfigDelivery
  */
  public static ConfigDelivery fromJson(String jsonString) throws IOException {
    return JSON.getGson().fromJson(jsonString, ConfigDelivery.class);
  }

 /**
  * Convert an instance of ConfigDelivery to an JSON string
  *
  * @return JSON string
  */
  public String toJson() {
    return JSON.getGson().toJson(this);
  }
}

