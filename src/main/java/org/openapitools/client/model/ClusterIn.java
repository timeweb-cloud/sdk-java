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
import java.util.ArrayList;
import java.util.List;
import org.openapitools.client.model.ClusterInClusterNetworkCidr;
import org.openapitools.client.model.ClusterInConfiguration;
import org.openapitools.client.model.ClusterInMaintenanceSlot;
import org.openapitools.client.model.ClusterInOidcProvider;
import org.openapitools.client.model.NodeGroupIn;

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
 * ClusterIn
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2025-10-07T16:09:35.828584Z[Etc/UTC]")
public class ClusterIn {
  public static final String SERIALIZED_NAME_NAME = "name";
  @SerializedName(SERIALIZED_NAME_NAME)
  private String name;

  public static final String SERIALIZED_NAME_DESCRIPTION = "description";
  @SerializedName(SERIALIZED_NAME_DESCRIPTION)
  private String description;

  public static final String SERIALIZED_NAME_K8S_VERSION = "k8s_version";
  @SerializedName(SERIALIZED_NAME_K8S_VERSION)
  private String k8sVersion;

  /**
   * Зона доступности
   */
  @JsonAdapter(AvailabilityZoneEnum.Adapter.class)
  public enum AvailabilityZoneEnum {
    SPB_3("spb-3"),
    
    MSK_1("msk-1"),
    
    AMS_1("ams-1"),
    
    FRA_1("fra-1");

    private String value;

    AvailabilityZoneEnum(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public static AvailabilityZoneEnum fromValue(String value) {
      for (AvailabilityZoneEnum b : AvailabilityZoneEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }

    public static class Adapter extends TypeAdapter<AvailabilityZoneEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final AvailabilityZoneEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public AvailabilityZoneEnum read(final JsonReader jsonReader) throws IOException {
        String value =  jsonReader.nextString();
        return AvailabilityZoneEnum.fromValue(value);
      }
    }
  }

  public static final String SERIALIZED_NAME_AVAILABILITY_ZONE = "availability_zone";
  @SerializedName(SERIALIZED_NAME_AVAILABILITY_ZONE)
  private AvailabilityZoneEnum availabilityZone;

  /**
   * Тип используемого сетевого драйвера в кластере
   */
  @JsonAdapter(NetworkDriverEnum.Adapter.class)
  public enum NetworkDriverEnum {
    KUBEROUTER("kuberouter"),
    
    CALICO("calico"),
    
    FLANNEL("flannel"),
    
    CILIUM("cilium");

    private String value;

    NetworkDriverEnum(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public static NetworkDriverEnum fromValue(String value) {
      for (NetworkDriverEnum b : NetworkDriverEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }

    public static class Adapter extends TypeAdapter<NetworkDriverEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final NetworkDriverEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public NetworkDriverEnum read(final JsonReader jsonReader) throws IOException {
        String value =  jsonReader.nextString();
        return NetworkDriverEnum.fromValue(value);
      }
    }
  }

  public static final String SERIALIZED_NAME_NETWORK_DRIVER = "network_driver";
  @SerializedName(SERIALIZED_NAME_NETWORK_DRIVER)
  private NetworkDriverEnum networkDriver;

  public static final String SERIALIZED_NAME_IS_INGRESS = "is_ingress";
  @SerializedName(SERIALIZED_NAME_IS_INGRESS)
  private Boolean isIngress;

  public static final String SERIALIZED_NAME_IS_K8S_DASHBOARD = "is_k8s_dashboard";
  @SerializedName(SERIALIZED_NAME_IS_K8S_DASHBOARD)
  private Boolean isK8sDashboard;

  public static final String SERIALIZED_NAME_PRESET_ID = "preset_id";
  @SerializedName(SERIALIZED_NAME_PRESET_ID)
  private Integer presetId;

  public static final String SERIALIZED_NAME_CONFIGURATION = "configuration";
  @SerializedName(SERIALIZED_NAME_CONFIGURATION)
  private ClusterInConfiguration _configuration;

  public static final String SERIALIZED_NAME_MASTER_NODES_COUNT = "master_nodes_count";
  @SerializedName(SERIALIZED_NAME_MASTER_NODES_COUNT)
  private Integer masterNodesCount;

  public static final String SERIALIZED_NAME_WORKER_GROUPS = "worker_groups";
  @SerializedName(SERIALIZED_NAME_WORKER_GROUPS)
  private List<NodeGroupIn> workerGroups;

  public static final String SERIALIZED_NAME_NETWORK_ID = "network_id";
  @SerializedName(SERIALIZED_NAME_NETWORK_ID)
  private String networkId;

  public static final String SERIALIZED_NAME_PROJECT_ID = "project_id";
  @SerializedName(SERIALIZED_NAME_PROJECT_ID)
  private Integer projectId;

  public static final String SERIALIZED_NAME_MAINTENANCE_SLOT = "maintenance_slot";
  @SerializedName(SERIALIZED_NAME_MAINTENANCE_SLOT)
  private ClusterInMaintenanceSlot maintenanceSlot;

  public static final String SERIALIZED_NAME_OIDC_PROVIDER = "oidc_provider";
  @SerializedName(SERIALIZED_NAME_OIDC_PROVIDER)
  private ClusterInOidcProvider oidcProvider;

  public static final String SERIALIZED_NAME_CLUSTER_NETWORK_CIDR = "cluster_network_cidr";
  @SerializedName(SERIALIZED_NAME_CLUSTER_NETWORK_CIDR)
  private ClusterInClusterNetworkCidr clusterNetworkCidr;

  public ClusterIn() {
  }

  public ClusterIn name(String name) {
    
    this.name = name;
    return this;
  }

   /**
   * Название кластера
   * @return name
  **/
  @javax.annotation.Nonnull
  public String getName() {
    return name;
  }


  public void setName(String name) {
    this.name = name;
  }


  public ClusterIn description(String description) {
    
    this.description = description;
    return this;
  }

   /**
   * Описание кластера
   * @return description
  **/
  @javax.annotation.Nullable
  public String getDescription() {
    return description;
  }


  public void setDescription(String description) {
    this.description = description;
  }


  public ClusterIn k8sVersion(String k8sVersion) {
    
    this.k8sVersion = k8sVersion;
    return this;
  }

   /**
   * Версия Kubernetes
   * @return k8sVersion
  **/
  @javax.annotation.Nonnull
  public String getK8sVersion() {
    return k8sVersion;
  }


  public void setK8sVersion(String k8sVersion) {
    this.k8sVersion = k8sVersion;
  }


  public ClusterIn availabilityZone(AvailabilityZoneEnum availabilityZone) {
    
    this.availabilityZone = availabilityZone;
    return this;
  }

   /**
   * Зона доступности
   * @return availabilityZone
  **/
  @javax.annotation.Nullable
  public AvailabilityZoneEnum getAvailabilityZone() {
    return availabilityZone;
  }


  public void setAvailabilityZone(AvailabilityZoneEnum availabilityZone) {
    this.availabilityZone = availabilityZone;
  }


  public ClusterIn networkDriver(NetworkDriverEnum networkDriver) {
    
    this.networkDriver = networkDriver;
    return this;
  }

   /**
   * Тип используемого сетевого драйвера в кластере
   * @return networkDriver
  **/
  @javax.annotation.Nonnull
  public NetworkDriverEnum getNetworkDriver() {
    return networkDriver;
  }


  public void setNetworkDriver(NetworkDriverEnum networkDriver) {
    this.networkDriver = networkDriver;
  }


  public ClusterIn isIngress(Boolean isIngress) {
    
    this.isIngress = isIngress;
    return this;
  }

   /**
   * Логическое значение, которое показывает, использовать ли Ingress в кластере
   * @return isIngress
  **/
  @javax.annotation.Nullable
  public Boolean getIsIngress() {
    return isIngress;
  }


  public void setIsIngress(Boolean isIngress) {
    this.isIngress = isIngress;
  }


  public ClusterIn isK8sDashboard(Boolean isK8sDashboard) {
    
    this.isK8sDashboard = isK8sDashboard;
    return this;
  }

   /**
   * Логическое значение, которое показывает, использовать ли Kubernetes Dashboard в кластере
   * @return isK8sDashboard
  **/
  @javax.annotation.Nullable
  public Boolean getIsK8sDashboard() {
    return isK8sDashboard;
  }


  public void setIsK8sDashboard(Boolean isK8sDashboard) {
    this.isK8sDashboard = isK8sDashboard;
  }


  public ClusterIn presetId(Integer presetId) {
    
    this.presetId = presetId;
    return this;
  }

   /**
   * ID тарифа мастер-ноды. Нельзя передавать вместе с &#x60;configuration&#x60;
   * @return presetId
  **/
  @javax.annotation.Nullable
  public Integer getPresetId() {
    return presetId;
  }


  public void setPresetId(Integer presetId) {
    this.presetId = presetId;
  }


  public ClusterIn _configuration(ClusterInConfiguration _configuration) {
    
    this._configuration = _configuration;
    return this;
  }

   /**
   * Get _configuration
   * @return _configuration
  **/
  @javax.annotation.Nullable
  public ClusterInConfiguration getConfiguration() {
    return _configuration;
  }


  public void setConfiguration(ClusterInConfiguration _configuration) {
    this._configuration = _configuration;
  }


  public ClusterIn masterNodesCount(Integer masterNodesCount) {
    
    this.masterNodesCount = masterNodesCount;
    return this;
  }

   /**
   * Количество мастер нод
   * @return masterNodesCount
  **/
  @javax.annotation.Nullable
  public Integer getMasterNodesCount() {
    return masterNodesCount;
  }


  public void setMasterNodesCount(Integer masterNodesCount) {
    this.masterNodesCount = masterNodesCount;
  }


  public ClusterIn workerGroups(List<NodeGroupIn> workerGroups) {
    
    this.workerGroups = workerGroups;
    return this;
  }

  public ClusterIn addWorkerGroupsItem(NodeGroupIn workerGroupsItem) {
    if (this.workerGroups == null) {
      this.workerGroups = new ArrayList<>();
    }
    this.workerGroups.add(workerGroupsItem);
    return this;
  }

   /**
   * Группы воркеров в кластере
   * @return workerGroups
  **/
  @javax.annotation.Nullable
  public List<NodeGroupIn> getWorkerGroups() {
    return workerGroups;
  }


  public void setWorkerGroups(List<NodeGroupIn> workerGroups) {
    this.workerGroups = workerGroups;
  }


  public ClusterIn networkId(String networkId) {
    
    this.networkId = networkId;
    return this;
  }

   /**
   * ID приватной сети
   * @return networkId
  **/
  @javax.annotation.Nullable
  public String getNetworkId() {
    return networkId;
  }


  public void setNetworkId(String networkId) {
    this.networkId = networkId;
  }


  public ClusterIn projectId(Integer projectId) {
    
    this.projectId = projectId;
    return this;
  }

   /**
   * ID проекта
   * @return projectId
  **/
  @javax.annotation.Nullable
  public Integer getProjectId() {
    return projectId;
  }


  public void setProjectId(Integer projectId) {
    this.projectId = projectId;
  }


  public ClusterIn maintenanceSlot(ClusterInMaintenanceSlot maintenanceSlot) {
    
    this.maintenanceSlot = maintenanceSlot;
    return this;
  }

   /**
   * Get maintenanceSlot
   * @return maintenanceSlot
  **/
  @javax.annotation.Nullable
  public ClusterInMaintenanceSlot getMaintenanceSlot() {
    return maintenanceSlot;
  }


  public void setMaintenanceSlot(ClusterInMaintenanceSlot maintenanceSlot) {
    this.maintenanceSlot = maintenanceSlot;
  }


  public ClusterIn oidcProvider(ClusterInOidcProvider oidcProvider) {
    
    this.oidcProvider = oidcProvider;
    return this;
  }

   /**
   * Get oidcProvider
   * @return oidcProvider
  **/
  @javax.annotation.Nullable
  public ClusterInOidcProvider getOidcProvider() {
    return oidcProvider;
  }


  public void setOidcProvider(ClusterInOidcProvider oidcProvider) {
    this.oidcProvider = oidcProvider;
  }


  public ClusterIn clusterNetworkCidr(ClusterInClusterNetworkCidr clusterNetworkCidr) {
    
    this.clusterNetworkCidr = clusterNetworkCidr;
    return this;
  }

   /**
   * Get clusterNetworkCidr
   * @return clusterNetworkCidr
  **/
  @javax.annotation.Nullable
  public ClusterInClusterNetworkCidr getClusterNetworkCidr() {
    return clusterNetworkCidr;
  }


  public void setClusterNetworkCidr(ClusterInClusterNetworkCidr clusterNetworkCidr) {
    this.clusterNetworkCidr = clusterNetworkCidr;
  }



  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ClusterIn clusterIn = (ClusterIn) o;
    return Objects.equals(this.name, clusterIn.name) &&
        Objects.equals(this.description, clusterIn.description) &&
        Objects.equals(this.k8sVersion, clusterIn.k8sVersion) &&
        Objects.equals(this.availabilityZone, clusterIn.availabilityZone) &&
        Objects.equals(this.networkDriver, clusterIn.networkDriver) &&
        Objects.equals(this.isIngress, clusterIn.isIngress) &&
        Objects.equals(this.isK8sDashboard, clusterIn.isK8sDashboard) &&
        Objects.equals(this.presetId, clusterIn.presetId) &&
        Objects.equals(this._configuration, clusterIn._configuration) &&
        Objects.equals(this.masterNodesCount, clusterIn.masterNodesCount) &&
        Objects.equals(this.workerGroups, clusterIn.workerGroups) &&
        Objects.equals(this.networkId, clusterIn.networkId) &&
        Objects.equals(this.projectId, clusterIn.projectId) &&
        Objects.equals(this.maintenanceSlot, clusterIn.maintenanceSlot) &&
        Objects.equals(this.oidcProvider, clusterIn.oidcProvider) &&
        Objects.equals(this.clusterNetworkCidr, clusterIn.clusterNetworkCidr);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, description, k8sVersion, availabilityZone, networkDriver, isIngress, isK8sDashboard, presetId, _configuration, masterNodesCount, workerGroups, networkId, projectId, maintenanceSlot, oidcProvider, clusterNetworkCidr);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ClusterIn {\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    k8sVersion: ").append(toIndentedString(k8sVersion)).append("\n");
    sb.append("    availabilityZone: ").append(toIndentedString(availabilityZone)).append("\n");
    sb.append("    networkDriver: ").append(toIndentedString(networkDriver)).append("\n");
    sb.append("    isIngress: ").append(toIndentedString(isIngress)).append("\n");
    sb.append("    isK8sDashboard: ").append(toIndentedString(isK8sDashboard)).append("\n");
    sb.append("    presetId: ").append(toIndentedString(presetId)).append("\n");
    sb.append("    _configuration: ").append(toIndentedString(_configuration)).append("\n");
    sb.append("    masterNodesCount: ").append(toIndentedString(masterNodesCount)).append("\n");
    sb.append("    workerGroups: ").append(toIndentedString(workerGroups)).append("\n");
    sb.append("    networkId: ").append(toIndentedString(networkId)).append("\n");
    sb.append("    projectId: ").append(toIndentedString(projectId)).append("\n");
    sb.append("    maintenanceSlot: ").append(toIndentedString(maintenanceSlot)).append("\n");
    sb.append("    oidcProvider: ").append(toIndentedString(oidcProvider)).append("\n");
    sb.append("    clusterNetworkCidr: ").append(toIndentedString(clusterNetworkCidr)).append("\n");
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
    openapiFields.add("description");
    openapiFields.add("k8s_version");
    openapiFields.add("availability_zone");
    openapiFields.add("network_driver");
    openapiFields.add("is_ingress");
    openapiFields.add("is_k8s_dashboard");
    openapiFields.add("preset_id");
    openapiFields.add("configuration");
    openapiFields.add("master_nodes_count");
    openapiFields.add("worker_groups");
    openapiFields.add("network_id");
    openapiFields.add("project_id");
    openapiFields.add("maintenance_slot");
    openapiFields.add("oidc_provider");
    openapiFields.add("cluster_network_cidr");

    // a set of required properties/fields (JSON key names)
    openapiRequiredFields = new HashSet<String>();
    openapiRequiredFields.add("name");
    openapiRequiredFields.add("k8s_version");
    openapiRequiredFields.add("network_driver");
  }

 /**
  * Validates the JSON Element and throws an exception if issues found
  *
  * @param jsonElement JSON Element
  * @throws IOException if the JSON Element is invalid with respect to ClusterIn
  */
  public static void validateJsonElement(JsonElement jsonElement) throws IOException {
      if (jsonElement == null) {
        if (!ClusterIn.openapiRequiredFields.isEmpty()) { // has required fields but JSON element is null
          throw new IllegalArgumentException(String.format("The required field(s) %s in ClusterIn is not found in the empty JSON string", ClusterIn.openapiRequiredFields.toString()));
        }
      }

      Set<Entry<String, JsonElement>> entries = jsonElement.getAsJsonObject().entrySet();
      // check to see if the JSON string contains additional fields
      for (Entry<String, JsonElement> entry : entries) {
        if (!ClusterIn.openapiFields.contains(entry.getKey())) {
          throw new IllegalArgumentException(String.format("The field `%s` in the JSON string is not defined in the `ClusterIn` properties. JSON: %s", entry.getKey(), jsonElement.toString()));
        }
      }

      // check to make sure all required properties/fields are present in the JSON string
      for (String requiredField : ClusterIn.openapiRequiredFields) {
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
      if (!jsonObj.get("k8s_version").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `k8s_version` to be a primitive type in the JSON string but got `%s`", jsonObj.get("k8s_version").toString()));
      }
      if ((jsonObj.get("availability_zone") != null && !jsonObj.get("availability_zone").isJsonNull()) && !jsonObj.get("availability_zone").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `availability_zone` to be a primitive type in the JSON string but got `%s`", jsonObj.get("availability_zone").toString()));
      }
      if (!jsonObj.get("network_driver").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `network_driver` to be a primitive type in the JSON string but got `%s`", jsonObj.get("network_driver").toString()));
      }
      // validate the optional field `configuration`
      if (jsonObj.get("configuration") != null && !jsonObj.get("configuration").isJsonNull()) {
        ClusterInConfiguration.validateJsonElement(jsonObj.get("configuration"));
      }
      if (jsonObj.get("worker_groups") != null && !jsonObj.get("worker_groups").isJsonNull()) {
        JsonArray jsonArrayworkerGroups = jsonObj.getAsJsonArray("worker_groups");
        if (jsonArrayworkerGroups != null) {
          // ensure the json data is an array
          if (!jsonObj.get("worker_groups").isJsonArray()) {
            throw new IllegalArgumentException(String.format("Expected the field `worker_groups` to be an array in the JSON string but got `%s`", jsonObj.get("worker_groups").toString()));
          }

          // validate the optional field `worker_groups` (array)
          for (int i = 0; i < jsonArrayworkerGroups.size(); i++) {
            NodeGroupIn.validateJsonElement(jsonArrayworkerGroups.get(i));
          };
        }
      }
      if ((jsonObj.get("network_id") != null && !jsonObj.get("network_id").isJsonNull()) && !jsonObj.get("network_id").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `network_id` to be a primitive type in the JSON string but got `%s`", jsonObj.get("network_id").toString()));
      }
      // validate the optional field `maintenance_slot`
      if (jsonObj.get("maintenance_slot") != null && !jsonObj.get("maintenance_slot").isJsonNull()) {
        ClusterInMaintenanceSlot.validateJsonElement(jsonObj.get("maintenance_slot"));
      }
      // validate the optional field `oidc_provider`
      if (jsonObj.get("oidc_provider") != null && !jsonObj.get("oidc_provider").isJsonNull()) {
        ClusterInOidcProvider.validateJsonElement(jsonObj.get("oidc_provider"));
      }
      // validate the optional field `cluster_network_cidr`
      if (jsonObj.get("cluster_network_cidr") != null && !jsonObj.get("cluster_network_cidr").isJsonNull()) {
        ClusterInClusterNetworkCidr.validateJsonElement(jsonObj.get("cluster_network_cidr"));
      }
  }

  public static class CustomTypeAdapterFactory implements TypeAdapterFactory {
    @SuppressWarnings("unchecked")
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
       if (!ClusterIn.class.isAssignableFrom(type.getRawType())) {
         return null; // this class only serializes 'ClusterIn' and its subtypes
       }
       final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);
       final TypeAdapter<ClusterIn> thisAdapter
                        = gson.getDelegateAdapter(this, TypeToken.get(ClusterIn.class));

       return (TypeAdapter<T>) new TypeAdapter<ClusterIn>() {
           @Override
           public void write(JsonWriter out, ClusterIn value) throws IOException {
             JsonObject obj = thisAdapter.toJsonTree(value).getAsJsonObject();
             elementAdapter.write(out, obj);
           }

           @Override
           public ClusterIn read(JsonReader in) throws IOException {
             JsonElement jsonElement = elementAdapter.read(in);
             validateJsonElement(jsonElement);
             return thisAdapter.fromJsonTree(jsonElement);
           }

       }.nullSafe();
    }
  }

 /**
  * Create an instance of ClusterIn given an JSON string
  *
  * @param jsonString JSON string
  * @return An instance of ClusterIn
  * @throws IOException if the JSON string is invalid with respect to ClusterIn
  */
  public static ClusterIn fromJson(String jsonString) throws IOException {
    return JSON.getGson().fromJson(jsonString, ClusterIn.class);
  }

 /**
  * Convert an instance of ClusterIn to an JSON string
  *
  * @return JSON string
  */
  public String toJson() {
    return JSON.getGson().toJson(this);
  }
}

