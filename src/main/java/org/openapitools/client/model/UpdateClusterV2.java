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
import org.openapitools.client.model.Mysql;
import org.openapitools.client.model.UpdateClusterConfiguration;
import org.openapitools.client.model.UpdateClusterV2DiskAutoscaling;
import org.openapitools.client.model.UpdateClusterV2MaintenanceSlot;

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
 * Изменение кластера базы данных (v2)
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2026-08-06T15:06:51.562821Z[Etc/UTC]")
public class UpdateClusterV2 {
  public static final String SERIALIZED_NAME_NAME = "name";
  @SerializedName(SERIALIZED_NAME_NAME)
  private String name;

  public static final String SERIALIZED_NAME_PRESET_ID = "preset_id";
  @SerializedName(SERIALIZED_NAME_PRESET_ID)
  private Integer presetId;

  public static final String SERIALIZED_NAME_CONFIGURATION = "configuration";
  @SerializedName(SERIALIZED_NAME_CONFIGURATION)
  private UpdateClusterConfiguration _configuration;

  public static final String SERIALIZED_NAME_CONFIG_PARAMETERS = "config_parameters";
  @SerializedName(SERIALIZED_NAME_CONFIG_PARAMETERS)
  private Mysql configParameters;

  /**
   * Тип хеширования базы данных (mysql | postgres).
   */
  @JsonAdapter(HashTypeEnum.Adapter.class)
  public enum HashTypeEnum {
    CACHING_SHA2("caching_sha2"),
    
    MYSQL_NATIVE("mysql_native");

    private String value;

    HashTypeEnum(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public static HashTypeEnum fromValue(String value) {
      for (HashTypeEnum b : HashTypeEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }

    public static class Adapter extends TypeAdapter<HashTypeEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final HashTypeEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public HashTypeEnum read(final JsonReader jsonReader) throws IOException {
        String value =  jsonReader.nextString();
        return HashTypeEnum.fromValue(value);
      }
    }
  }

  public static final String SERIALIZED_NAME_HASH_TYPE = "hash_type";
  @SerializedName(SERIALIZED_NAME_HASH_TYPE)
  private HashTypeEnum hashType;

  public static final String SERIALIZED_NAME_DESCRIPTION = "description";
  @SerializedName(SERIALIZED_NAME_DESCRIPTION)
  private String description;

  public static final String SERIALIZED_NAME_IS_ENABLED_PUBLIC_NETWORK = "is_enabled_public_network";
  @SerializedName(SERIALIZED_NAME_IS_ENABLED_PUBLIC_NETWORK)
  private Boolean isEnabledPublicNetwork;

  public static final String SERIALIZED_NAME_IS_ENABLED_PUBLIC_IPV6 = "is_enabled_public_ipv6";
  @SerializedName(SERIALIZED_NAME_IS_ENABLED_PUBLIC_IPV6)
  private Boolean isEnabledPublicIpv6;

  public static final String SERIALIZED_NAME_FLOATING_IP = "floating_ip";
  @SerializedName(SERIALIZED_NAME_FLOATING_IP)
  private String floatingIp;

  public static final String SERIALIZED_NAME_IS_SECURE_CONNECTION_ENABLE = "is_secure_connection_enable";
  @SerializedName(SERIALIZED_NAME_IS_SECURE_CONNECTION_ENABLE)
  private Boolean isSecureConnectionEnable;

  public static final String SERIALIZED_NAME_MAINTENANCE_SLOT = "maintenance_slot";
  @SerializedName(SERIALIZED_NAME_MAINTENANCE_SLOT)
  private UpdateClusterV2MaintenanceSlot maintenanceSlot;

  public static final String SERIALIZED_NAME_DISK_AUTOSCALING = "disk_autoscaling";
  @SerializedName(SERIALIZED_NAME_DISK_AUTOSCALING)
  private UpdateClusterV2DiskAutoscaling diskAutoscaling;

  public UpdateClusterV2() {
  }

  public UpdateClusterV2 name(String name) {
    
    this.name = name;
    return this;
  }

   /**
   * Название кластера базы данных.
   * @return name
  **/
  @javax.annotation.Nullable
  public String getName() {
    return name;
  }


  public void setName(String name) {
    this.name = name;
  }


  public UpdateClusterV2 presetId(Integer presetId) {
    
    this.presetId = presetId;
    return this;
  }

   /**
   * ID тарифа. Нельзя передавать вместе с &#x60;configuration&#x60;
   * @return presetId
  **/
  @javax.annotation.Nullable
  public Integer getPresetId() {
    return presetId;
  }


  public void setPresetId(Integer presetId) {
    this.presetId = presetId;
  }


  public UpdateClusterV2 _configuration(UpdateClusterConfiguration _configuration) {
    
    this._configuration = _configuration;
    return this;
  }

   /**
   * Get _configuration
   * @return _configuration
  **/
  @javax.annotation.Nullable
  public UpdateClusterConfiguration getConfiguration() {
    return _configuration;
  }


  public void setConfiguration(UpdateClusterConfiguration _configuration) {
    this._configuration = _configuration;
  }


  public UpdateClusterV2 configParameters(Mysql configParameters) {
    
    this.configParameters = configParameters;
    return this;
  }

   /**
   * Get configParameters
   * @return configParameters
  **/
  @javax.annotation.Nullable
  public Mysql getConfigParameters() {
    return configParameters;
  }


  public void setConfigParameters(Mysql configParameters) {
    this.configParameters = configParameters;
  }


  public UpdateClusterV2 hashType(HashTypeEnum hashType) {
    
    this.hashType = hashType;
    return this;
  }

   /**
   * Тип хеширования базы данных (mysql | postgres).
   * @return hashType
  **/
  @javax.annotation.Nullable
  public HashTypeEnum getHashType() {
    return hashType;
  }


  public void setHashType(HashTypeEnum hashType) {
    this.hashType = hashType;
  }


  public UpdateClusterV2 description(String description) {
    
    this.description = description;
    return this;
  }

   /**
   * Описание кластера базы данных
   * @return description
  **/
  @javax.annotation.Nullable
  public String getDescription() {
    return description;
  }


  public void setDescription(String description) {
    this.description = description;
  }


  public UpdateClusterV2 isEnabledPublicNetwork(Boolean isEnabledPublicNetwork) {
    
    this.isEnabledPublicNetwork = isEnabledPublicNetwork;
    return this;
  }

   /**
   * Доступность публичного IP-адреса
   * @return isEnabledPublicNetwork
  **/
  @javax.annotation.Nullable
  public Boolean getIsEnabledPublicNetwork() {
    return isEnabledPublicNetwork;
  }


  public void setIsEnabledPublicNetwork(Boolean isEnabledPublicNetwork) {
    this.isEnabledPublicNetwork = isEnabledPublicNetwork;
  }


  public UpdateClusterV2 isEnabledPublicIpv6(Boolean isEnabledPublicIpv6) {
    
    this.isEnabledPublicIpv6 = isEnabledPublicIpv6;
    return this;
  }

   /**
   * Использование публичного IPv6-адреса.
   * @return isEnabledPublicIpv6
  **/
  @javax.annotation.Nullable
  public Boolean getIsEnabledPublicIpv6() {
    return isEnabledPublicIpv6;
  }


  public void setIsEnabledPublicIpv6(Boolean isEnabledPublicIpv6) {
    this.isEnabledPublicIpv6 = isEnabledPublicIpv6;
  }


  public UpdateClusterV2 floatingIp(String floatingIp) {
    
    this.floatingIp = floatingIp;
    return this;
  }

   /**
   * Плавающий IP-адрес, который нужно привязать к кластеру базы данных. Передается сам адрес, а не его ID; адрес должен быть свободен (не привязан к другому сервису).
   * @return floatingIp
  **/
  @javax.annotation.Nullable
  public String getFloatingIp() {
    return floatingIp;
  }


  public void setFloatingIp(String floatingIp) {
    this.floatingIp = floatingIp;
  }


  public UpdateClusterV2 isSecureConnectionEnable(Boolean isSecureConnectionEnable) {
    
    this.isSecureConnectionEnable = isSecureConnectionEnable;
    return this;
  }

   /**
   * Включить защищенное подключение к кластеру базы данных. Обратите внимание: в ответе это же значение возвращается под ключом &#x60;is_secure_connection_enabled&#x60;.
   * @return isSecureConnectionEnable
  **/
  @javax.annotation.Nullable
  public Boolean getIsSecureConnectionEnable() {
    return isSecureConnectionEnable;
  }


  public void setIsSecureConnectionEnable(Boolean isSecureConnectionEnable) {
    this.isSecureConnectionEnable = isSecureConnectionEnable;
  }


  public UpdateClusterV2 maintenanceSlot(UpdateClusterV2MaintenanceSlot maintenanceSlot) {
    
    this.maintenanceSlot = maintenanceSlot;
    return this;
  }

   /**
   * Get maintenanceSlot
   * @return maintenanceSlot
  **/
  @javax.annotation.Nullable
  public UpdateClusterV2MaintenanceSlot getMaintenanceSlot() {
    return maintenanceSlot;
  }


  public void setMaintenanceSlot(UpdateClusterV2MaintenanceSlot maintenanceSlot) {
    this.maintenanceSlot = maintenanceSlot;
  }


  public UpdateClusterV2 diskAutoscaling(UpdateClusterV2DiskAutoscaling diskAutoscaling) {
    
    this.diskAutoscaling = diskAutoscaling;
    return this;
  }

   /**
   * Get diskAutoscaling
   * @return diskAutoscaling
  **/
  @javax.annotation.Nullable
  public UpdateClusterV2DiskAutoscaling getDiskAutoscaling() {
    return diskAutoscaling;
  }


  public void setDiskAutoscaling(UpdateClusterV2DiskAutoscaling diskAutoscaling) {
    this.diskAutoscaling = diskAutoscaling;
  }



  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UpdateClusterV2 updateClusterV2 = (UpdateClusterV2) o;
    return Objects.equals(this.name, updateClusterV2.name) &&
        Objects.equals(this.presetId, updateClusterV2.presetId) &&
        Objects.equals(this._configuration, updateClusterV2._configuration) &&
        Objects.equals(this.configParameters, updateClusterV2.configParameters) &&
        Objects.equals(this.hashType, updateClusterV2.hashType) &&
        Objects.equals(this.description, updateClusterV2.description) &&
        Objects.equals(this.isEnabledPublicNetwork, updateClusterV2.isEnabledPublicNetwork) &&
        Objects.equals(this.isEnabledPublicIpv6, updateClusterV2.isEnabledPublicIpv6) &&
        Objects.equals(this.floatingIp, updateClusterV2.floatingIp) &&
        Objects.equals(this.isSecureConnectionEnable, updateClusterV2.isSecureConnectionEnable) &&
        Objects.equals(this.maintenanceSlot, updateClusterV2.maintenanceSlot) &&
        Objects.equals(this.diskAutoscaling, updateClusterV2.diskAutoscaling);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, presetId, _configuration, configParameters, hashType, description, isEnabledPublicNetwork, isEnabledPublicIpv6, floatingIp, isSecureConnectionEnable, maintenanceSlot, diskAutoscaling);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UpdateClusterV2 {\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    presetId: ").append(toIndentedString(presetId)).append("\n");
    sb.append("    _configuration: ").append(toIndentedString(_configuration)).append("\n");
    sb.append("    configParameters: ").append(toIndentedString(configParameters)).append("\n");
    sb.append("    hashType: ").append(toIndentedString(hashType)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    isEnabledPublicNetwork: ").append(toIndentedString(isEnabledPublicNetwork)).append("\n");
    sb.append("    isEnabledPublicIpv6: ").append(toIndentedString(isEnabledPublicIpv6)).append("\n");
    sb.append("    floatingIp: ").append(toIndentedString(floatingIp)).append("\n");
    sb.append("    isSecureConnectionEnable: ").append(toIndentedString(isSecureConnectionEnable)).append("\n");
    sb.append("    maintenanceSlot: ").append(toIndentedString(maintenanceSlot)).append("\n");
    sb.append("    diskAutoscaling: ").append(toIndentedString(diskAutoscaling)).append("\n");
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
    openapiFields.add("name");
    openapiFields.add("preset_id");
    openapiFields.add("configuration");
    openapiFields.add("config_parameters");
    openapiFields.add("hash_type");
    openapiFields.add("description");
    openapiFields.add("is_enabled_public_network");
    openapiFields.add("is_enabled_public_ipv6");
    openapiFields.add("floating_ip");
    openapiFields.add("is_secure_connection_enable");
    openapiFields.add("maintenance_slot");
    openapiFields.add("disk_autoscaling");

    // a set of required properties/fields (JSON key names)
    openapiRequiredFields = new HashSet<String>();
  }

 /**
  * Validates the JSON Element and throws an exception if issues found
  *
  * @param jsonElement JSON Element
  * @throws IOException if the JSON Element is invalid with respect to UpdateClusterV2
  */
  public static void validateJsonElement(JsonElement jsonElement) throws IOException {
      if (jsonElement == null) {
        if (!UpdateClusterV2.openapiRequiredFields.isEmpty()) { // has required fields but JSON element is null
          throw new IllegalArgumentException(String.format("The required field(s) %s in UpdateClusterV2 is not found in the empty JSON string", UpdateClusterV2.openapiRequiredFields.toString()));
        }
      }

      Set<Entry<String, JsonElement>> entries = jsonElement.getAsJsonObject().entrySet();
      // check to see if the JSON string contains additional fields
      for (Entry<String, JsonElement> entry : entries) {
        if (!UpdateClusterV2.openapiFields.contains(entry.getKey())) {
          throw new IllegalArgumentException(String.format("The field `%s` in the JSON string is not defined in the `UpdateClusterV2` properties. JSON: %s", entry.getKey(), jsonElement.toString()));
        }
      }
        JsonObject jsonObj = jsonElement.getAsJsonObject();
      if ((jsonObj.get("name") != null && !jsonObj.get("name").isJsonNull()) && !jsonObj.get("name").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `name` to be a primitive type in the JSON string but got `%s`", jsonObj.get("name").toString()));
      }
      // validate the optional field `configuration`
      if (jsonObj.get("configuration") != null && !jsonObj.get("configuration").isJsonNull()) {
        UpdateClusterConfiguration.validateJsonElement(jsonObj.get("configuration"));
      }
      // validate the optional field `config_parameters`
      if (jsonObj.get("config_parameters") != null && !jsonObj.get("config_parameters").isJsonNull()) {
        Mysql.validateJsonElement(jsonObj.get("config_parameters"));
      }
      if ((jsonObj.get("hash_type") != null && !jsonObj.get("hash_type").isJsonNull()) && !jsonObj.get("hash_type").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `hash_type` to be a primitive type in the JSON string but got `%s`", jsonObj.get("hash_type").toString()));
      }
      if ((jsonObj.get("description") != null && !jsonObj.get("description").isJsonNull()) && !jsonObj.get("description").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `description` to be a primitive type in the JSON string but got `%s`", jsonObj.get("description").toString()));
      }
      if ((jsonObj.get("floating_ip") != null && !jsonObj.get("floating_ip").isJsonNull()) && !jsonObj.get("floating_ip").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `floating_ip` to be a primitive type in the JSON string but got `%s`", jsonObj.get("floating_ip").toString()));
      }
      // validate the optional field `maintenance_slot`
      if (jsonObj.get("maintenance_slot") != null && !jsonObj.get("maintenance_slot").isJsonNull()) {
        UpdateClusterV2MaintenanceSlot.validateJsonElement(jsonObj.get("maintenance_slot"));
      }
      // validate the optional field `disk_autoscaling`
      if (jsonObj.get("disk_autoscaling") != null && !jsonObj.get("disk_autoscaling").isJsonNull()) {
        UpdateClusterV2DiskAutoscaling.validateJsonElement(jsonObj.get("disk_autoscaling"));
      }
  }

  public static class CustomTypeAdapterFactory implements TypeAdapterFactory {
    @SuppressWarnings("unchecked")
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
       if (!UpdateClusterV2.class.isAssignableFrom(type.getRawType())) {
         return null; // this class only serializes 'UpdateClusterV2' and its subtypes
       }
       final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);
       final TypeAdapter<UpdateClusterV2> thisAdapter
                        = gson.getDelegateAdapter(this, TypeToken.get(UpdateClusterV2.class));

       return (TypeAdapter<T>) new TypeAdapter<UpdateClusterV2>() {
           @Override
           public void write(JsonWriter out, UpdateClusterV2 value) throws IOException {
             JsonObject obj = thisAdapter.toJsonTree(value).getAsJsonObject();
             elementAdapter.write(out, obj);
           }

           @Override
           public UpdateClusterV2 read(JsonReader in) throws IOException {
             JsonElement jsonElement = elementAdapter.read(in);
             validateJsonElement(jsonElement);
             return thisAdapter.fromJsonTree(jsonElement);
           }

       }.nullSafe();
    }
  }

 /**
  * Create an instance of UpdateClusterV2 given an JSON string
  *
  * @param jsonString JSON string
  * @return An instance of UpdateClusterV2
  * @throws IOException if the JSON string is invalid with respect to UpdateClusterV2
  */
  public static UpdateClusterV2 fromJson(String jsonString) throws IOException {
    return JSON.getGson().fromJson(jsonString, UpdateClusterV2.class);
  }

 /**
  * Convert an instance of UpdateClusterV2 to an JSON string
  *
  * @return JSON string
  */
  public String toJson() {
    return JSON.getGson().toJson(this);
  }
}

