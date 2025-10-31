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
 * ServersConfiguratorRequirements
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2025-10-31T14:40:51.795734Z[Etc/UTC]")
public class ServersConfiguratorRequirements {
  public static final String SERIALIZED_NAME_CPU_MIN = "cpu_min";
  @SerializedName(SERIALIZED_NAME_CPU_MIN)
  private BigDecimal cpuMin;

  public static final String SERIALIZED_NAME_CPU_STEP = "cpu_step";
  @SerializedName(SERIALIZED_NAME_CPU_STEP)
  private BigDecimal cpuStep;

  public static final String SERIALIZED_NAME_CPU_MAX = "cpu_max";
  @SerializedName(SERIALIZED_NAME_CPU_MAX)
  private BigDecimal cpuMax;

  public static final String SERIALIZED_NAME_RAM_MIN = "ram_min";
  @SerializedName(SERIALIZED_NAME_RAM_MIN)
  private BigDecimal ramMin;

  public static final String SERIALIZED_NAME_RAM_STEP = "ram_step";
  @SerializedName(SERIALIZED_NAME_RAM_STEP)
  private BigDecimal ramStep;

  public static final String SERIALIZED_NAME_RAM_MAX = "ram_max";
  @SerializedName(SERIALIZED_NAME_RAM_MAX)
  private BigDecimal ramMax;

  public static final String SERIALIZED_NAME_DISK_MIN = "disk_min";
  @SerializedName(SERIALIZED_NAME_DISK_MIN)
  private BigDecimal diskMin;

  public static final String SERIALIZED_NAME_DISK_STEP = "disk_step";
  @SerializedName(SERIALIZED_NAME_DISK_STEP)
  private BigDecimal diskStep;

  public static final String SERIALIZED_NAME_DISK_MAX = "disk_max";
  @SerializedName(SERIALIZED_NAME_DISK_MAX)
  private BigDecimal diskMax;

  public static final String SERIALIZED_NAME_NETWORK_BANDWIDTH_MIN = "network_bandwidth_min";
  @SerializedName(SERIALIZED_NAME_NETWORK_BANDWIDTH_MIN)
  private BigDecimal networkBandwidthMin;

  public static final String SERIALIZED_NAME_NETWORK_BANDWIDTH_STEP = "network_bandwidth_step";
  @SerializedName(SERIALIZED_NAME_NETWORK_BANDWIDTH_STEP)
  private BigDecimal networkBandwidthStep;

  public static final String SERIALIZED_NAME_NETWORK_BANDWIDTH_MAX = "network_bandwidth_max";
  @SerializedName(SERIALIZED_NAME_NETWORK_BANDWIDTH_MAX)
  private BigDecimal networkBandwidthMax;

  public static final String SERIALIZED_NAME_GPU_MIN = "gpu_min";
  @SerializedName(SERIALIZED_NAME_GPU_MIN)
  private BigDecimal gpuMin;

  public static final String SERIALIZED_NAME_GPU_MAX = "gpu_max";
  @SerializedName(SERIALIZED_NAME_GPU_MAX)
  private BigDecimal gpuMax;

  public static final String SERIALIZED_NAME_GPU_STEP = "gpu_step";
  @SerializedName(SERIALIZED_NAME_GPU_STEP)
  private BigDecimal gpuStep;

  public ServersConfiguratorRequirements() {
  }

  public ServersConfiguratorRequirements cpuMin(BigDecimal cpuMin) {
    
    this.cpuMin = cpuMin;
    return this;
  }

   /**
   * Минимальное количество ядер процессора.
   * @return cpuMin
  **/
  @javax.annotation.Nonnull
  public BigDecimal getCpuMin() {
    return cpuMin;
  }


  public void setCpuMin(BigDecimal cpuMin) {
    this.cpuMin = cpuMin;
  }


  public ServersConfiguratorRequirements cpuStep(BigDecimal cpuStep) {
    
    this.cpuStep = cpuStep;
    return this;
  }

   /**
   * Размер шага ядер процессора.
   * @return cpuStep
  **/
  @javax.annotation.Nonnull
  public BigDecimal getCpuStep() {
    return cpuStep;
  }


  public void setCpuStep(BigDecimal cpuStep) {
    this.cpuStep = cpuStep;
  }


  public ServersConfiguratorRequirements cpuMax(BigDecimal cpuMax) {
    
    this.cpuMax = cpuMax;
    return this;
  }

   /**
   * Максимальное количество ядер процессора.
   * @return cpuMax
  **/
  @javax.annotation.Nonnull
  public BigDecimal getCpuMax() {
    return cpuMax;
  }


  public void setCpuMax(BigDecimal cpuMax) {
    this.cpuMax = cpuMax;
  }


  public ServersConfiguratorRequirements ramMin(BigDecimal ramMin) {
    
    this.ramMin = ramMin;
    return this;
  }

   /**
   * Минимальное количество оперативной памяти (в Мб).
   * @return ramMin
  **/
  @javax.annotation.Nonnull
  public BigDecimal getRamMin() {
    return ramMin;
  }


  public void setRamMin(BigDecimal ramMin) {
    this.ramMin = ramMin;
  }


  public ServersConfiguratorRequirements ramStep(BigDecimal ramStep) {
    
    this.ramStep = ramStep;
    return this;
  }

   /**
   * Размер шага оперативной памяти.
   * @return ramStep
  **/
  @javax.annotation.Nonnull
  public BigDecimal getRamStep() {
    return ramStep;
  }


  public void setRamStep(BigDecimal ramStep) {
    this.ramStep = ramStep;
  }


  public ServersConfiguratorRequirements ramMax(BigDecimal ramMax) {
    
    this.ramMax = ramMax;
    return this;
  }

   /**
   * Максимальное количество оперативной памяти (в Мб).
   * @return ramMax
  **/
  @javax.annotation.Nonnull
  public BigDecimal getRamMax() {
    return ramMax;
  }


  public void setRamMax(BigDecimal ramMax) {
    this.ramMax = ramMax;
  }


  public ServersConfiguratorRequirements diskMin(BigDecimal diskMin) {
    
    this.diskMin = diskMin;
    return this;
  }

   /**
   * Минимальный размер диска (в Мб).
   * @return diskMin
  **/
  @javax.annotation.Nonnull
  public BigDecimal getDiskMin() {
    return diskMin;
  }


  public void setDiskMin(BigDecimal diskMin) {
    this.diskMin = diskMin;
  }


  public ServersConfiguratorRequirements diskStep(BigDecimal diskStep) {
    
    this.diskStep = diskStep;
    return this;
  }

   /**
   * Размер шага диска
   * @return diskStep
  **/
  @javax.annotation.Nonnull
  public BigDecimal getDiskStep() {
    return diskStep;
  }


  public void setDiskStep(BigDecimal diskStep) {
    this.diskStep = diskStep;
  }


  public ServersConfiguratorRequirements diskMax(BigDecimal diskMax) {
    
    this.diskMax = diskMax;
    return this;
  }

   /**
   * Максимальный размер диска (в Мб).
   * @return diskMax
  **/
  @javax.annotation.Nonnull
  public BigDecimal getDiskMax() {
    return diskMax;
  }


  public void setDiskMax(BigDecimal diskMax) {
    this.diskMax = diskMax;
  }


  public ServersConfiguratorRequirements networkBandwidthMin(BigDecimal networkBandwidthMin) {
    
    this.networkBandwidthMin = networkBandwidthMin;
    return this;
  }

   /**
   * Минимальныая пропускная способноть интернет-канала (в Мб)
   * @return networkBandwidthMin
  **/
  @javax.annotation.Nonnull
  public BigDecimal getNetworkBandwidthMin() {
    return networkBandwidthMin;
  }


  public void setNetworkBandwidthMin(BigDecimal networkBandwidthMin) {
    this.networkBandwidthMin = networkBandwidthMin;
  }


  public ServersConfiguratorRequirements networkBandwidthStep(BigDecimal networkBandwidthStep) {
    
    this.networkBandwidthStep = networkBandwidthStep;
    return this;
  }

   /**
   * Размер шага пропускной способноти интернет-канала (в Мб)
   * @return networkBandwidthStep
  **/
  @javax.annotation.Nonnull
  public BigDecimal getNetworkBandwidthStep() {
    return networkBandwidthStep;
  }


  public void setNetworkBandwidthStep(BigDecimal networkBandwidthStep) {
    this.networkBandwidthStep = networkBandwidthStep;
  }


  public ServersConfiguratorRequirements networkBandwidthMax(BigDecimal networkBandwidthMax) {
    
    this.networkBandwidthMax = networkBandwidthMax;
    return this;
  }

   /**
   * Максимальная пропускная способноть интернет-канала (в Мб)
   * @return networkBandwidthMax
  **/
  @javax.annotation.Nonnull
  public BigDecimal getNetworkBandwidthMax() {
    return networkBandwidthMax;
  }


  public void setNetworkBandwidthMax(BigDecimal networkBandwidthMax) {
    this.networkBandwidthMax = networkBandwidthMax;
  }


  public ServersConfiguratorRequirements gpuMin(BigDecimal gpuMin) {
    
    this.gpuMin = gpuMin;
    return this;
  }

   /**
   * Минимальное количество видеокарт
   * @return gpuMin
  **/
  @javax.annotation.Nullable
  public BigDecimal getGpuMin() {
    return gpuMin;
  }


  public void setGpuMin(BigDecimal gpuMin) {
    this.gpuMin = gpuMin;
  }


  public ServersConfiguratorRequirements gpuMax(BigDecimal gpuMax) {
    
    this.gpuMax = gpuMax;
    return this;
  }

   /**
   * Максимальное количество видеокарт
   * @return gpuMax
  **/
  @javax.annotation.Nullable
  public BigDecimal getGpuMax() {
    return gpuMax;
  }


  public void setGpuMax(BigDecimal gpuMax) {
    this.gpuMax = gpuMax;
  }


  public ServersConfiguratorRequirements gpuStep(BigDecimal gpuStep) {
    
    this.gpuStep = gpuStep;
    return this;
  }

   /**
   * Размер шага видеокарт
   * @return gpuStep
  **/
  @javax.annotation.Nullable
  public BigDecimal getGpuStep() {
    return gpuStep;
  }


  public void setGpuStep(BigDecimal gpuStep) {
    this.gpuStep = gpuStep;
  }



  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ServersConfiguratorRequirements serversConfiguratorRequirements = (ServersConfiguratorRequirements) o;
    return Objects.equals(this.cpuMin, serversConfiguratorRequirements.cpuMin) &&
        Objects.equals(this.cpuStep, serversConfiguratorRequirements.cpuStep) &&
        Objects.equals(this.cpuMax, serversConfiguratorRequirements.cpuMax) &&
        Objects.equals(this.ramMin, serversConfiguratorRequirements.ramMin) &&
        Objects.equals(this.ramStep, serversConfiguratorRequirements.ramStep) &&
        Objects.equals(this.ramMax, serversConfiguratorRequirements.ramMax) &&
        Objects.equals(this.diskMin, serversConfiguratorRequirements.diskMin) &&
        Objects.equals(this.diskStep, serversConfiguratorRequirements.diskStep) &&
        Objects.equals(this.diskMax, serversConfiguratorRequirements.diskMax) &&
        Objects.equals(this.networkBandwidthMin, serversConfiguratorRequirements.networkBandwidthMin) &&
        Objects.equals(this.networkBandwidthStep, serversConfiguratorRequirements.networkBandwidthStep) &&
        Objects.equals(this.networkBandwidthMax, serversConfiguratorRequirements.networkBandwidthMax) &&
        Objects.equals(this.gpuMin, serversConfiguratorRequirements.gpuMin) &&
        Objects.equals(this.gpuMax, serversConfiguratorRequirements.gpuMax) &&
        Objects.equals(this.gpuStep, serversConfiguratorRequirements.gpuStep);
  }

  @Override
  public int hashCode() {
    return Objects.hash(cpuMin, cpuStep, cpuMax, ramMin, ramStep, ramMax, diskMin, diskStep, diskMax, networkBandwidthMin, networkBandwidthStep, networkBandwidthMax, gpuMin, gpuMax, gpuStep);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ServersConfiguratorRequirements {\n");
    sb.append("    cpuMin: ").append(toIndentedString(cpuMin)).append("\n");
    sb.append("    cpuStep: ").append(toIndentedString(cpuStep)).append("\n");
    sb.append("    cpuMax: ").append(toIndentedString(cpuMax)).append("\n");
    sb.append("    ramMin: ").append(toIndentedString(ramMin)).append("\n");
    sb.append("    ramStep: ").append(toIndentedString(ramStep)).append("\n");
    sb.append("    ramMax: ").append(toIndentedString(ramMax)).append("\n");
    sb.append("    diskMin: ").append(toIndentedString(diskMin)).append("\n");
    sb.append("    diskStep: ").append(toIndentedString(diskStep)).append("\n");
    sb.append("    diskMax: ").append(toIndentedString(diskMax)).append("\n");
    sb.append("    networkBandwidthMin: ").append(toIndentedString(networkBandwidthMin)).append("\n");
    sb.append("    networkBandwidthStep: ").append(toIndentedString(networkBandwidthStep)).append("\n");
    sb.append("    networkBandwidthMax: ").append(toIndentedString(networkBandwidthMax)).append("\n");
    sb.append("    gpuMin: ").append(toIndentedString(gpuMin)).append("\n");
    sb.append("    gpuMax: ").append(toIndentedString(gpuMax)).append("\n");
    sb.append("    gpuStep: ").append(toIndentedString(gpuStep)).append("\n");
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
    openapiFields.add("cpu_min");
    openapiFields.add("cpu_step");
    openapiFields.add("cpu_max");
    openapiFields.add("ram_min");
    openapiFields.add("ram_step");
    openapiFields.add("ram_max");
    openapiFields.add("disk_min");
    openapiFields.add("disk_step");
    openapiFields.add("disk_max");
    openapiFields.add("network_bandwidth_min");
    openapiFields.add("network_bandwidth_step");
    openapiFields.add("network_bandwidth_max");
    openapiFields.add("gpu_min");
    openapiFields.add("gpu_max");
    openapiFields.add("gpu_step");

    // a set of required properties/fields (JSON key names)
    openapiRequiredFields = new HashSet<String>();
    openapiRequiredFields.add("cpu_min");
    openapiRequiredFields.add("cpu_step");
    openapiRequiredFields.add("cpu_max");
    openapiRequiredFields.add("ram_min");
    openapiRequiredFields.add("ram_step");
    openapiRequiredFields.add("ram_max");
    openapiRequiredFields.add("disk_min");
    openapiRequiredFields.add("disk_step");
    openapiRequiredFields.add("disk_max");
    openapiRequiredFields.add("network_bandwidth_min");
    openapiRequiredFields.add("network_bandwidth_step");
    openapiRequiredFields.add("network_bandwidth_max");
    openapiRequiredFields.add("gpu_min");
    openapiRequiredFields.add("gpu_max");
    openapiRequiredFields.add("gpu_step");
  }

 /**
  * Validates the JSON Element and throws an exception if issues found
  *
  * @param jsonElement JSON Element
  * @throws IOException if the JSON Element is invalid with respect to ServersConfiguratorRequirements
  */
  public static void validateJsonElement(JsonElement jsonElement) throws IOException {
      if (jsonElement == null) {
        if (!ServersConfiguratorRequirements.openapiRequiredFields.isEmpty()) { // has required fields but JSON element is null
          throw new IllegalArgumentException(String.format("The required field(s) %s in ServersConfiguratorRequirements is not found in the empty JSON string", ServersConfiguratorRequirements.openapiRequiredFields.toString()));
        }
      }

      Set<Entry<String, JsonElement>> entries = jsonElement.getAsJsonObject().entrySet();
      // check to see if the JSON string contains additional fields
      for (Entry<String, JsonElement> entry : entries) {
        if (!ServersConfiguratorRequirements.openapiFields.contains(entry.getKey())) {
          throw new IllegalArgumentException(String.format("The field `%s` in the JSON string is not defined in the `ServersConfiguratorRequirements` properties. JSON: %s", entry.getKey(), jsonElement.toString()));
        }
      }

      // check to make sure all required properties/fields are present in the JSON string
      for (String requiredField : ServersConfiguratorRequirements.openapiRequiredFields) {
        if (jsonElement.getAsJsonObject().get(requiredField) == null) {
          throw new IllegalArgumentException(String.format("The required field `%s` is not found in the JSON string: %s", requiredField, jsonElement.toString()));
        }
      }
        JsonObject jsonObj = jsonElement.getAsJsonObject();
  }

  public static class CustomTypeAdapterFactory implements TypeAdapterFactory {
    @SuppressWarnings("unchecked")
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
       if (!ServersConfiguratorRequirements.class.isAssignableFrom(type.getRawType())) {
         return null; // this class only serializes 'ServersConfiguratorRequirements' and its subtypes
       }
       final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);
       final TypeAdapter<ServersConfiguratorRequirements> thisAdapter
                        = gson.getDelegateAdapter(this, TypeToken.get(ServersConfiguratorRequirements.class));

       return (TypeAdapter<T>) new TypeAdapter<ServersConfiguratorRequirements>() {
           @Override
           public void write(JsonWriter out, ServersConfiguratorRequirements value) throws IOException {
             JsonObject obj = thisAdapter.toJsonTree(value).getAsJsonObject();
             elementAdapter.write(out, obj);
           }

           @Override
           public ServersConfiguratorRequirements read(JsonReader in) throws IOException {
             JsonElement jsonElement = elementAdapter.read(in);
             validateJsonElement(jsonElement);
             return thisAdapter.fromJsonTree(jsonElement);
           }

       }.nullSafe();
    }
  }

 /**
  * Create an instance of ServersConfiguratorRequirements given an JSON string
  *
  * @param jsonString JSON string
  * @return An instance of ServersConfiguratorRequirements
  * @throws IOException if the JSON string is invalid with respect to ServersConfiguratorRequirements
  */
  public static ServersConfiguratorRequirements fromJson(String jsonString) throws IOException {
    return JSON.getGson().fromJson(jsonString, ServersConfiguratorRequirements.class);
  }

 /**
  * Convert an instance of ServersConfiguratorRequirements to an JSON string
  *
  * @return JSON string
  */
  public String toJson() {
    return JSON.getGson().toJson(this);
  }
}

