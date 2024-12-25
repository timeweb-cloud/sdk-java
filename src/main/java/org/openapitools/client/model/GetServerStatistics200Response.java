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
import java.util.ArrayList;
import java.util.List;
import org.openapitools.client.model.GetServerStatistics200ResponseCpuInner;
import org.openapitools.client.model.GetServerStatistics200ResponseDiskInner;
import org.openapitools.client.model.GetServerStatistics200ResponseNetworkTrafficInner;
import org.openapitools.client.model.GetServerStatistics200ResponseRamInner;

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
 * GetServerStatistics200Response
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2024-12-25T12:35:47.138766Z[Etc/UTC]")
public class GetServerStatistics200Response {
  public static final String SERIALIZED_NAME_CPU = "cpu";
  @SerializedName(SERIALIZED_NAME_CPU)
  private List<GetServerStatistics200ResponseCpuInner> cpu = new ArrayList<>();

  public static final String SERIALIZED_NAME_NETWORK_TRAFFIC = "network_traffic";
  @SerializedName(SERIALIZED_NAME_NETWORK_TRAFFIC)
  private List<GetServerStatistics200ResponseNetworkTrafficInner> networkTraffic = new ArrayList<>();

  public static final String SERIALIZED_NAME_DISK = "disk";
  @SerializedName(SERIALIZED_NAME_DISK)
  private List<GetServerStatistics200ResponseDiskInner> disk = new ArrayList<>();

  public static final String SERIALIZED_NAME_RAM = "ram";
  @SerializedName(SERIALIZED_NAME_RAM)
  private List<GetServerStatistics200ResponseRamInner> ram = new ArrayList<>();

  public GetServerStatistics200Response() {
  }

  public GetServerStatistics200Response cpu(List<GetServerStatistics200ResponseCpuInner> cpu) {
    
    this.cpu = cpu;
    return this;
  }

  public GetServerStatistics200Response addCpuItem(GetServerStatistics200ResponseCpuInner cpuItem) {
    if (this.cpu == null) {
      this.cpu = new ArrayList<>();
    }
    this.cpu.add(cpuItem);
    return this;
  }

   /**
   * Get cpu
   * @return cpu
  **/
  @javax.annotation.Nonnull
  public List<GetServerStatistics200ResponseCpuInner> getCpu() {
    return cpu;
  }


  public void setCpu(List<GetServerStatistics200ResponseCpuInner> cpu) {
    this.cpu = cpu;
  }


  public GetServerStatistics200Response networkTraffic(List<GetServerStatistics200ResponseNetworkTrafficInner> networkTraffic) {
    
    this.networkTraffic = networkTraffic;
    return this;
  }

  public GetServerStatistics200Response addNetworkTrafficItem(GetServerStatistics200ResponseNetworkTrafficInner networkTrafficItem) {
    if (this.networkTraffic == null) {
      this.networkTraffic = new ArrayList<>();
    }
    this.networkTraffic.add(networkTrafficItem);
    return this;
  }

   /**
   * Get networkTraffic
   * @return networkTraffic
  **/
  @javax.annotation.Nonnull
  public List<GetServerStatistics200ResponseNetworkTrafficInner> getNetworkTraffic() {
    return networkTraffic;
  }


  public void setNetworkTraffic(List<GetServerStatistics200ResponseNetworkTrafficInner> networkTraffic) {
    this.networkTraffic = networkTraffic;
  }


  public GetServerStatistics200Response disk(List<GetServerStatistics200ResponseDiskInner> disk) {
    
    this.disk = disk;
    return this;
  }

  public GetServerStatistics200Response addDiskItem(GetServerStatistics200ResponseDiskInner diskItem) {
    if (this.disk == null) {
      this.disk = new ArrayList<>();
    }
    this.disk.add(diskItem);
    return this;
  }

   /**
   * Статистика основного диска
   * @return disk
  **/
  @javax.annotation.Nonnull
  public List<GetServerStatistics200ResponseDiskInner> getDisk() {
    return disk;
  }


  public void setDisk(List<GetServerStatistics200ResponseDiskInner> disk) {
    this.disk = disk;
  }


  public GetServerStatistics200Response ram(List<GetServerStatistics200ResponseRamInner> ram) {
    
    this.ram = ram;
    return this;
  }

  public GetServerStatistics200Response addRamItem(GetServerStatistics200ResponseRamInner ramItem) {
    if (this.ram == null) {
      this.ram = new ArrayList<>();
    }
    this.ram.add(ramItem);
    return this;
  }

   /**
   * Get ram
   * @return ram
  **/
  @javax.annotation.Nonnull
  public List<GetServerStatistics200ResponseRamInner> getRam() {
    return ram;
  }


  public void setRam(List<GetServerStatistics200ResponseRamInner> ram) {
    this.ram = ram;
  }



  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    GetServerStatistics200Response getServerStatistics200Response = (GetServerStatistics200Response) o;
    return Objects.equals(this.cpu, getServerStatistics200Response.cpu) &&
        Objects.equals(this.networkTraffic, getServerStatistics200Response.networkTraffic) &&
        Objects.equals(this.disk, getServerStatistics200Response.disk) &&
        Objects.equals(this.ram, getServerStatistics200Response.ram);
  }

  @Override
  public int hashCode() {
    return Objects.hash(cpu, networkTraffic, disk, ram);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class GetServerStatistics200Response {\n");
    sb.append("    cpu: ").append(toIndentedString(cpu)).append("\n");
    sb.append("    networkTraffic: ").append(toIndentedString(networkTraffic)).append("\n");
    sb.append("    disk: ").append(toIndentedString(disk)).append("\n");
    sb.append("    ram: ").append(toIndentedString(ram)).append("\n");
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
    openapiFields.add("cpu");
    openapiFields.add("network_traffic");
    openapiFields.add("disk");
    openapiFields.add("ram");

    // a set of required properties/fields (JSON key names)
    openapiRequiredFields = new HashSet<String>();
    openapiRequiredFields.add("cpu");
    openapiRequiredFields.add("network_traffic");
    openapiRequiredFields.add("disk");
    openapiRequiredFields.add("ram");
  }

 /**
  * Validates the JSON Element and throws an exception if issues found
  *
  * @param jsonElement JSON Element
  * @throws IOException if the JSON Element is invalid with respect to GetServerStatistics200Response
  */
  public static void validateJsonElement(JsonElement jsonElement) throws IOException {
      if (jsonElement == null) {
        if (!GetServerStatistics200Response.openapiRequiredFields.isEmpty()) { // has required fields but JSON element is null
          throw new IllegalArgumentException(String.format("The required field(s) %s in GetServerStatistics200Response is not found in the empty JSON string", GetServerStatistics200Response.openapiRequiredFields.toString()));
        }
      }

      Set<Entry<String, JsonElement>> entries = jsonElement.getAsJsonObject().entrySet();
      // check to see if the JSON string contains additional fields
      for (Entry<String, JsonElement> entry : entries) {
        if (!GetServerStatistics200Response.openapiFields.contains(entry.getKey())) {
          throw new IllegalArgumentException(String.format("The field `%s` in the JSON string is not defined in the `GetServerStatistics200Response` properties. JSON: %s", entry.getKey(), jsonElement.toString()));
        }
      }

      // check to make sure all required properties/fields are present in the JSON string
      for (String requiredField : GetServerStatistics200Response.openapiRequiredFields) {
        if (jsonElement.getAsJsonObject().get(requiredField) == null) {
          throw new IllegalArgumentException(String.format("The required field `%s` is not found in the JSON string: %s", requiredField, jsonElement.toString()));
        }
      }
        JsonObject jsonObj = jsonElement.getAsJsonObject();
      // ensure the json data is an array
      if (!jsonObj.get("cpu").isJsonArray()) {
        throw new IllegalArgumentException(String.format("Expected the field `cpu` to be an array in the JSON string but got `%s`", jsonObj.get("cpu").toString()));
      }

      JsonArray jsonArraycpu = jsonObj.getAsJsonArray("cpu");
      // validate the required field `cpu` (array)
      for (int i = 0; i < jsonArraycpu.size(); i++) {
        GetServerStatistics200ResponseCpuInner.validateJsonElement(jsonArraycpu.get(i));
      };
      // ensure the json data is an array
      if (!jsonObj.get("network_traffic").isJsonArray()) {
        throw new IllegalArgumentException(String.format("Expected the field `network_traffic` to be an array in the JSON string but got `%s`", jsonObj.get("network_traffic").toString()));
      }

      JsonArray jsonArraynetworkTraffic = jsonObj.getAsJsonArray("network_traffic");
      // validate the required field `network_traffic` (array)
      for (int i = 0; i < jsonArraynetworkTraffic.size(); i++) {
        GetServerStatistics200ResponseNetworkTrafficInner.validateJsonElement(jsonArraynetworkTraffic.get(i));
      };
      // ensure the json data is an array
      if (!jsonObj.get("disk").isJsonArray()) {
        throw new IllegalArgumentException(String.format("Expected the field `disk` to be an array in the JSON string but got `%s`", jsonObj.get("disk").toString()));
      }

      JsonArray jsonArraydisk = jsonObj.getAsJsonArray("disk");
      // validate the required field `disk` (array)
      for (int i = 0; i < jsonArraydisk.size(); i++) {
        GetServerStatistics200ResponseDiskInner.validateJsonElement(jsonArraydisk.get(i));
      };
      // ensure the json data is an array
      if (!jsonObj.get("ram").isJsonArray()) {
        throw new IllegalArgumentException(String.format("Expected the field `ram` to be an array in the JSON string but got `%s`", jsonObj.get("ram").toString()));
      }

      JsonArray jsonArrayram = jsonObj.getAsJsonArray("ram");
      // validate the required field `ram` (array)
      for (int i = 0; i < jsonArrayram.size(); i++) {
        GetServerStatistics200ResponseRamInner.validateJsonElement(jsonArrayram.get(i));
      };
  }

  public static class CustomTypeAdapterFactory implements TypeAdapterFactory {
    @SuppressWarnings("unchecked")
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
       if (!GetServerStatistics200Response.class.isAssignableFrom(type.getRawType())) {
         return null; // this class only serializes 'GetServerStatistics200Response' and its subtypes
       }
       final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);
       final TypeAdapter<GetServerStatistics200Response> thisAdapter
                        = gson.getDelegateAdapter(this, TypeToken.get(GetServerStatistics200Response.class));

       return (TypeAdapter<T>) new TypeAdapter<GetServerStatistics200Response>() {
           @Override
           public void write(JsonWriter out, GetServerStatistics200Response value) throws IOException {
             JsonObject obj = thisAdapter.toJsonTree(value).getAsJsonObject();
             elementAdapter.write(out, obj);
           }

           @Override
           public GetServerStatistics200Response read(JsonReader in) throws IOException {
             JsonElement jsonElement = elementAdapter.read(in);
             validateJsonElement(jsonElement);
             return thisAdapter.fromJsonTree(jsonElement);
           }

       }.nullSafe();
    }
  }

 /**
  * Create an instance of GetServerStatistics200Response given an JSON string
  *
  * @param jsonString JSON string
  * @return An instance of GetServerStatistics200Response
  * @throws IOException if the JSON string is invalid with respect to GetServerStatistics200Response
  */
  public static GetServerStatistics200Response fromJson(String jsonString) throws IOException {
    return JSON.getGson().fromJson(jsonString, GetServerStatistics200Response.class);
  }

 /**
  * Convert an instance of GetServerStatistics200Response to an JSON string
  *
  * @return JSON string
  */
  public String toJson() {
    return JSON.getGson().toJson(this);
  }
}

