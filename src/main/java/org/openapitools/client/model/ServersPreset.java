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
 * ServersPreset
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2025-04-04T10:30:33.640412Z[Etc/UTC]")
public class ServersPreset {
  public static final String SERIALIZED_NAME_ID = "id";
  @SerializedName(SERIALIZED_NAME_ID)
  private BigDecimal id;

  /**
   * Локация сервера.
   */
  @JsonAdapter(LocationEnum.Adapter.class)
  public enum LocationEnum {
    RU_1("ru-1"),
    
    PL_1("pl-1"),
    
    KZ_1("kz-1");

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

  public static final String SERIALIZED_NAME_PRICE = "price";
  @SerializedName(SERIALIZED_NAME_PRICE)
  private BigDecimal price;

  public static final String SERIALIZED_NAME_CPU = "cpu";
  @SerializedName(SERIALIZED_NAME_CPU)
  private BigDecimal cpu;

  public static final String SERIALIZED_NAME_CPU_FREQUENCY = "cpu_frequency";
  @SerializedName(SERIALIZED_NAME_CPU_FREQUENCY)
  private String cpuFrequency;

  public static final String SERIALIZED_NAME_RAM = "ram";
  @SerializedName(SERIALIZED_NAME_RAM)
  private BigDecimal ram;

  public static final String SERIALIZED_NAME_DISK = "disk";
  @SerializedName(SERIALIZED_NAME_DISK)
  private BigDecimal disk;

  /**
   * Тип диска.
   */
  @JsonAdapter(DiskTypeEnum.Adapter.class)
  public enum DiskTypeEnum {
    SSD("ssd"),
    
    NVME("nvme"),
    
    HDD("hdd");

    private String value;

    DiskTypeEnum(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public static DiskTypeEnum fromValue(String value) {
      for (DiskTypeEnum b : DiskTypeEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }

    public static class Adapter extends TypeAdapter<DiskTypeEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final DiskTypeEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public DiskTypeEnum read(final JsonReader jsonReader) throws IOException {
        String value =  jsonReader.nextString();
        return DiskTypeEnum.fromValue(value);
      }
    }
  }

  public static final String SERIALIZED_NAME_DISK_TYPE = "disk_type";
  @SerializedName(SERIALIZED_NAME_DISK_TYPE)
  private DiskTypeEnum diskType;

  public static final String SERIALIZED_NAME_BANDWIDTH = "bandwidth";
  @SerializedName(SERIALIZED_NAME_BANDWIDTH)
  private BigDecimal bandwidth;

  public static final String SERIALIZED_NAME_DESCRIPTION = "description";
  @SerializedName(SERIALIZED_NAME_DESCRIPTION)
  private String description;

  public static final String SERIALIZED_NAME_DESCRIPTION_SHORT = "description_short";
  @SerializedName(SERIALIZED_NAME_DESCRIPTION_SHORT)
  private String descriptionShort;

  public static final String SERIALIZED_NAME_IS_ALLOWED_LOCAL_NETWORK = "is_allowed_local_network";
  @SerializedName(SERIALIZED_NAME_IS_ALLOWED_LOCAL_NETWORK)
  private Boolean isAllowedLocalNetwork;

  public static final String SERIALIZED_NAME_TAGS = "tags";
  @SerializedName(SERIALIZED_NAME_TAGS)
  private List<String> tags = new ArrayList<>();

  public ServersPreset() {
  }

  public ServersPreset id(BigDecimal id) {
    
    this.id = id;
    return this;
  }

   /**
   * ID тарифа сервера.
   * @return id
  **/
  @javax.annotation.Nonnull
  public BigDecimal getId() {
    return id;
  }


  public void setId(BigDecimal id) {
    this.id = id;
  }


  public ServersPreset location(LocationEnum location) {
    
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


  public ServersPreset price(BigDecimal price) {
    
    this.price = price;
    return this;
  }

   /**
   * Стоимость в рублях.
   * @return price
  **/
  @javax.annotation.Nonnull
  public BigDecimal getPrice() {
    return price;
  }


  public void setPrice(BigDecimal price) {
    this.price = price;
  }


  public ServersPreset cpu(BigDecimal cpu) {
    
    this.cpu = cpu;
    return this;
  }

   /**
   * Количество ядер процессора.
   * @return cpu
  **/
  @javax.annotation.Nonnull
  public BigDecimal getCpu() {
    return cpu;
  }


  public void setCpu(BigDecimal cpu) {
    this.cpu = cpu;
  }


  public ServersPreset cpuFrequency(String cpuFrequency) {
    
    this.cpuFrequency = cpuFrequency;
    return this;
  }

   /**
   * Частота процессора.
   * @return cpuFrequency
  **/
  @javax.annotation.Nonnull
  public String getCpuFrequency() {
    return cpuFrequency;
  }


  public void setCpuFrequency(String cpuFrequency) {
    this.cpuFrequency = cpuFrequency;
  }


  public ServersPreset ram(BigDecimal ram) {
    
    this.ram = ram;
    return this;
  }

   /**
   * Количество (в Мб) оперативной памяти.
   * @return ram
  **/
  @javax.annotation.Nonnull
  public BigDecimal getRam() {
    return ram;
  }


  public void setRam(BigDecimal ram) {
    this.ram = ram;
  }


  public ServersPreset disk(BigDecimal disk) {
    
    this.disk = disk;
    return this;
  }

   /**
   * Размер диска (в Мб).
   * @return disk
  **/
  @javax.annotation.Nonnull
  public BigDecimal getDisk() {
    return disk;
  }


  public void setDisk(BigDecimal disk) {
    this.disk = disk;
  }


  public ServersPreset diskType(DiskTypeEnum diskType) {
    
    this.diskType = diskType;
    return this;
  }

   /**
   * Тип диска.
   * @return diskType
  **/
  @javax.annotation.Nonnull
  public DiskTypeEnum getDiskType() {
    return diskType;
  }


  public void setDiskType(DiskTypeEnum diskType) {
    this.diskType = diskType;
  }


  public ServersPreset bandwidth(BigDecimal bandwidth) {
    
    this.bandwidth = bandwidth;
    return this;
  }

   /**
   * Пропускная способность тарифа.
   * @return bandwidth
  **/
  @javax.annotation.Nonnull
  public BigDecimal getBandwidth() {
    return bandwidth;
  }


  public void setBandwidth(BigDecimal bandwidth) {
    this.bandwidth = bandwidth;
  }


  public ServersPreset description(String description) {
    
    this.description = description;
    return this;
  }

   /**
   * Описание тарифа.
   * @return description
  **/
  @javax.annotation.Nonnull
  public String getDescription() {
    return description;
  }


  public void setDescription(String description) {
    this.description = description;
  }


  public ServersPreset descriptionShort(String descriptionShort) {
    
    this.descriptionShort = descriptionShort;
    return this;
  }

   /**
   * Короткое описание тарифа.
   * @return descriptionShort
  **/
  @javax.annotation.Nonnull
  public String getDescriptionShort() {
    return descriptionShort;
  }


  public void setDescriptionShort(String descriptionShort) {
    this.descriptionShort = descriptionShort;
  }


  public ServersPreset isAllowedLocalNetwork(Boolean isAllowedLocalNetwork) {
    
    this.isAllowedLocalNetwork = isAllowedLocalNetwork;
    return this;
  }

   /**
   * Есть возможность подключения локальной сети
   * @return isAllowedLocalNetwork
  **/
  @javax.annotation.Nonnull
  public Boolean getIsAllowedLocalNetwork() {
    return isAllowedLocalNetwork;
  }


  public void setIsAllowedLocalNetwork(Boolean isAllowedLocalNetwork) {
    this.isAllowedLocalNetwork = isAllowedLocalNetwork;
  }


  public ServersPreset tags(List<String> tags) {
    
    this.tags = tags;
    return this;
  }

  public ServersPreset addTagsItem(String tagsItem) {
    if (this.tags == null) {
      this.tags = new ArrayList<>();
    }
    this.tags.add(tagsItem);
    return this;
  }

   /**
   * Список тегов тарифа.
   * @return tags
  **/
  @javax.annotation.Nonnull
  public List<String> getTags() {
    return tags;
  }


  public void setTags(List<String> tags) {
    this.tags = tags;
  }



  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ServersPreset serversPreset = (ServersPreset) o;
    return Objects.equals(this.id, serversPreset.id) &&
        Objects.equals(this.location, serversPreset.location) &&
        Objects.equals(this.price, serversPreset.price) &&
        Objects.equals(this.cpu, serversPreset.cpu) &&
        Objects.equals(this.cpuFrequency, serversPreset.cpuFrequency) &&
        Objects.equals(this.ram, serversPreset.ram) &&
        Objects.equals(this.disk, serversPreset.disk) &&
        Objects.equals(this.diskType, serversPreset.diskType) &&
        Objects.equals(this.bandwidth, serversPreset.bandwidth) &&
        Objects.equals(this.description, serversPreset.description) &&
        Objects.equals(this.descriptionShort, serversPreset.descriptionShort) &&
        Objects.equals(this.isAllowedLocalNetwork, serversPreset.isAllowedLocalNetwork) &&
        Objects.equals(this.tags, serversPreset.tags);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, location, price, cpu, cpuFrequency, ram, disk, diskType, bandwidth, description, descriptionShort, isAllowedLocalNetwork, tags);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ServersPreset {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    location: ").append(toIndentedString(location)).append("\n");
    sb.append("    price: ").append(toIndentedString(price)).append("\n");
    sb.append("    cpu: ").append(toIndentedString(cpu)).append("\n");
    sb.append("    cpuFrequency: ").append(toIndentedString(cpuFrequency)).append("\n");
    sb.append("    ram: ").append(toIndentedString(ram)).append("\n");
    sb.append("    disk: ").append(toIndentedString(disk)).append("\n");
    sb.append("    diskType: ").append(toIndentedString(diskType)).append("\n");
    sb.append("    bandwidth: ").append(toIndentedString(bandwidth)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    descriptionShort: ").append(toIndentedString(descriptionShort)).append("\n");
    sb.append("    isAllowedLocalNetwork: ").append(toIndentedString(isAllowedLocalNetwork)).append("\n");
    sb.append("    tags: ").append(toIndentedString(tags)).append("\n");
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
    openapiFields.add("location");
    openapiFields.add("price");
    openapiFields.add("cpu");
    openapiFields.add("cpu_frequency");
    openapiFields.add("ram");
    openapiFields.add("disk");
    openapiFields.add("disk_type");
    openapiFields.add("bandwidth");
    openapiFields.add("description");
    openapiFields.add("description_short");
    openapiFields.add("is_allowed_local_network");
    openapiFields.add("tags");

    // a set of required properties/fields (JSON key names)
    openapiRequiredFields = new HashSet<String>();
    openapiRequiredFields.add("id");
    openapiRequiredFields.add("location");
    openapiRequiredFields.add("price");
    openapiRequiredFields.add("cpu");
    openapiRequiredFields.add("cpu_frequency");
    openapiRequiredFields.add("ram");
    openapiRequiredFields.add("disk");
    openapiRequiredFields.add("disk_type");
    openapiRequiredFields.add("bandwidth");
    openapiRequiredFields.add("description");
    openapiRequiredFields.add("description_short");
    openapiRequiredFields.add("is_allowed_local_network");
    openapiRequiredFields.add("tags");
  }

 /**
  * Validates the JSON Element and throws an exception if issues found
  *
  * @param jsonElement JSON Element
  * @throws IOException if the JSON Element is invalid with respect to ServersPreset
  */
  public static void validateJsonElement(JsonElement jsonElement) throws IOException {
      if (jsonElement == null) {
        if (!ServersPreset.openapiRequiredFields.isEmpty()) { // has required fields but JSON element is null
          throw new IllegalArgumentException(String.format("The required field(s) %s in ServersPreset is not found in the empty JSON string", ServersPreset.openapiRequiredFields.toString()));
        }
      }

      Set<Entry<String, JsonElement>> entries = jsonElement.getAsJsonObject().entrySet();
      // check to see if the JSON string contains additional fields
      for (Entry<String, JsonElement> entry : entries) {
        if (!ServersPreset.openapiFields.contains(entry.getKey())) {
          throw new IllegalArgumentException(String.format("The field `%s` in the JSON string is not defined in the `ServersPreset` properties. JSON: %s", entry.getKey(), jsonElement.toString()));
        }
      }

      // check to make sure all required properties/fields are present in the JSON string
      for (String requiredField : ServersPreset.openapiRequiredFields) {
        if (jsonElement.getAsJsonObject().get(requiredField) == null) {
          throw new IllegalArgumentException(String.format("The required field `%s` is not found in the JSON string: %s", requiredField, jsonElement.toString()));
        }
      }
        JsonObject jsonObj = jsonElement.getAsJsonObject();
      if (!jsonObj.get("location").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `location` to be a primitive type in the JSON string but got `%s`", jsonObj.get("location").toString()));
      }
      if (!jsonObj.get("cpu_frequency").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `cpu_frequency` to be a primitive type in the JSON string but got `%s`", jsonObj.get("cpu_frequency").toString()));
      }
      if (!jsonObj.get("disk_type").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `disk_type` to be a primitive type in the JSON string but got `%s`", jsonObj.get("disk_type").toString()));
      }
      if (!jsonObj.get("description").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `description` to be a primitive type in the JSON string but got `%s`", jsonObj.get("description").toString()));
      }
      if (!jsonObj.get("description_short").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `description_short` to be a primitive type in the JSON string but got `%s`", jsonObj.get("description_short").toString()));
      }
      // ensure the required json array is present
      if (jsonObj.get("tags") == null) {
        throw new IllegalArgumentException("Expected the field `linkedContent` to be an array in the JSON string but got `null`");
      } else if (!jsonObj.get("tags").isJsonArray()) {
        throw new IllegalArgumentException(String.format("Expected the field `tags` to be an array in the JSON string but got `%s`", jsonObj.get("tags").toString()));
      }
  }

  public static class CustomTypeAdapterFactory implements TypeAdapterFactory {
    @SuppressWarnings("unchecked")
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
       if (!ServersPreset.class.isAssignableFrom(type.getRawType())) {
         return null; // this class only serializes 'ServersPreset' and its subtypes
       }
       final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);
       final TypeAdapter<ServersPreset> thisAdapter
                        = gson.getDelegateAdapter(this, TypeToken.get(ServersPreset.class));

       return (TypeAdapter<T>) new TypeAdapter<ServersPreset>() {
           @Override
           public void write(JsonWriter out, ServersPreset value) throws IOException {
             JsonObject obj = thisAdapter.toJsonTree(value).getAsJsonObject();
             elementAdapter.write(out, obj);
           }

           @Override
           public ServersPreset read(JsonReader in) throws IOException {
             JsonElement jsonElement = elementAdapter.read(in);
             validateJsonElement(jsonElement);
             return thisAdapter.fromJsonTree(jsonElement);
           }

       }.nullSafe();
    }
  }

 /**
  * Create an instance of ServersPreset given an JSON string
  *
  * @param jsonString JSON string
  * @return An instance of ServersPreset
  * @throws IOException if the JSON string is invalid with respect to ServersPreset
  */
  public static ServersPreset fromJson(String jsonString) throws IOException {
    return JSON.getGson().fromJson(jsonString, ServersPreset.class);
  }

 /**
  * Convert an instance of ServersPreset to an JSON string
  *
  * @return JSON string
  */
  public String toJson() {
    return JSON.getGson().toJson(this);
  }
}

