# KubernetesApi

All URIs are relative to *https://api.timeweb.cloud*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**createCluster**](KubernetesApi.md#createCluster) | **POST** /api/v1/k8s/clusters | Создание кластера |
| [**createClusterNodeGroup**](KubernetesApi.md#createClusterNodeGroup) | **POST** /api/v1/k8s/clusters/{cluster_id}/groups | Создание группы нод |
| [**deleteCluster**](KubernetesApi.md#deleteCluster) | **DELETE** /api/v1/k8s/clusters/{cluster_id} | Удаление кластера |
| [**deleteClusterNode**](KubernetesApi.md#deleteClusterNode) | **DELETE** /api/v1/k8s/clusters/{cluster_id}/nodes/{node_id} | Удаление ноды |
| [**deleteClusterNodeGroup**](KubernetesApi.md#deleteClusterNodeGroup) | **DELETE** /api/v1/k8s/clusters/{cluster_id}/groups/{group_id} | Удаление группы нод |
| [**getCluster**](KubernetesApi.md#getCluster) | **GET** /api/v1/k8s/clusters/{cluster_id} | Получение информации о кластере |
| [**getClusterKubeconfig**](KubernetesApi.md#getClusterKubeconfig) | **GET** /api/v1/k8s/clusters/{cluster_id}/kubeconfig | Получение файла kubeconfig |
| [**getClusterNodeGroup**](KubernetesApi.md#getClusterNodeGroup) | **GET** /api/v1/k8s/clusters/{cluster_id}/groups/{group_id} | Получение информации о группе нод |
| [**getClusterNodeGroups**](KubernetesApi.md#getClusterNodeGroups) | **GET** /api/v1/k8s/clusters/{cluster_id}/groups | Получение групп нод кластера |
| [**getClusterNodes**](KubernetesApi.md#getClusterNodes) | **GET** /api/v1/k8s/clusters/{cluster_id}/nodes | Получение списка нод |
| [**getClusterNodesFromGroup**](KubernetesApi.md#getClusterNodesFromGroup) | **GET** /api/v1/k8s/clusters/{cluster_id}/groups/{group_id}/nodes | Получение списка нод, принадлежащих группе |
| [**getClusterResources**](KubernetesApi.md#getClusterResources) | **GET** /api/v1/k8s/clusters/{cluster_id}/resources | Получение ресурсов кластера |
| [**getClusters**](KubernetesApi.md#getClusters) | **GET** /api/v1/k8s/clusters | Получение списка кластеров |
| [**getK8SNetworkDrivers**](KubernetesApi.md#getK8SNetworkDrivers) | **GET** /api/v1/k8s/network-drivers | Получение списка сетевых драйверов k8s |
| [**getK8SVersions**](KubernetesApi.md#getK8SVersions) | **GET** /api/v1/k8s/k8s-versions | Получение списка версий k8s |
| [**getKubernetesPresets**](KubernetesApi.md#getKubernetesPresets) | **GET** /api/v1/presets/k8s | Получение списка тарифов |
| [**increaseCountOfNodesInGroup**](KubernetesApi.md#increaseCountOfNodesInGroup) | **POST** /api/v1/k8s/clusters/{cluster_id}/groups/{group_id}/nodes | Увеличение количества нод в группе на указанное количество |
| [**reduceCountOfNodesInGroup**](KubernetesApi.md#reduceCountOfNodesInGroup) | **DELETE** /api/v1/k8s/clusters/{cluster_id}/groups/{group_id}/nodes | Уменьшение количества нод в группе на указанное количество |
| [**updateCluster**](KubernetesApi.md#updateCluster) | **PATCH** /api/v1/k8s/clusters/{cluster_id} | Обновление информации о кластере |


<a id="createCluster"></a>
# **createCluster**
> ClusterResponse createCluster(clusterIn)

Создание кластера

Чтобы создать кластер, отправьте POST-запрос на &#x60;/api/v1/k8s/clusters&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.KubernetesApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    KubernetesApi apiInstance = new KubernetesApi(defaultClient);
    ClusterIn clusterIn = new ClusterIn(); // ClusterIn | 
    try {
      ClusterResponse result = apiInstance.createCluster(clusterIn);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling KubernetesApi#createCluster");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **clusterIn** | [**ClusterIn**](ClusterIn.md)|  | |

### Return type

[**ClusterResponse**](ClusterResponse.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Информация о кластере |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="createClusterNodeGroup"></a>
# **createClusterNodeGroup**
> NodeGroupResponse createClusterNodeGroup(clusterId, nodeGroupIn)

Создание группы нод

Чтобы создать группу нод кластера, отправьте POST-запрос в &#x60;/api/v1/k8s/clusters/{cluster_id}/groups&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.KubernetesApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    KubernetesApi apiInstance = new KubernetesApi(defaultClient);
    Integer clusterId = 56; // Integer | Уникальный идентификатор кластера
    NodeGroupIn nodeGroupIn = new NodeGroupIn(); // NodeGroupIn | 
    try {
      NodeGroupResponse result = apiInstance.createClusterNodeGroup(clusterId, nodeGroupIn);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling KubernetesApi#createClusterNodeGroup");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **clusterId** | **Integer**| Уникальный идентификатор кластера | |
| **nodeGroupIn** | [**NodeGroupIn**](NodeGroupIn.md)|  | |

### Return type

[**NodeGroupResponse**](NodeGroupResponse.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Информация о группе нод |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="deleteCluster"></a>
# **deleteCluster**
> DeleteCluster200Response deleteCluster(clusterId, hash, code)

Удаление кластера

Чтобы удалить кластер, отправьте DELETE-запрос в &#x60;/api/v1/k8s/clusters/{cluster_id}&#x60;

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.KubernetesApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    KubernetesApi apiInstance = new KubernetesApi(defaultClient);
    Integer clusterId = 56; // Integer | Уникальный идентификатор кластера
    String hash = "15095f25-aac3-4d60-a788-96cb5136f186"; // String | Хеш, который совместно с кодом авторизации надо отправить для удаления, если включено подтверждение удаления сервисов через Телеграм.
    String code = "0000"; // String | Код подтверждения, который придет к вам в Телеграм, после запроса удаления, если включено подтверждение удаления сервисов.  При помощи API токена сервисы можно удалять без подтверждения, если параметр токена `is_able_to_delete` установлен в значение `true`
    try {
      DeleteCluster200Response result = apiInstance.deleteCluster(clusterId, hash, code);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling KubernetesApi#deleteCluster");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **clusterId** | **Integer**| Уникальный идентификатор кластера | |
| **hash** | **String**| Хеш, который совместно с кодом авторизации надо отправить для удаления, если включено подтверждение удаления сервисов через Телеграм. | [optional] |
| **code** | **String**| Код подтверждения, который придет к вам в Телеграм, после запроса удаления, если включено подтверждение удаления сервисов.  При помощи API токена сервисы можно удалять без подтверждения, если параметр токена &#x60;is_able_to_delete&#x60; установлен в значение &#x60;true&#x60; | [optional] |

### Return type

[**DeleteCluster200Response**](DeleteCluster200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Объект JSON c ключом &#x60;cluster_delete&#x60; |  -  |
| **204** | Кластер удален |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="deleteClusterNode"></a>
# **deleteClusterNode**
> deleteClusterNode(clusterId, nodeId)

Удаление ноды

Чтобы удалить ноду, отправьте DELETE-запрос в &#x60;/api/v1/k8s/clusters/{cluster_id}/nodes/{node_id}&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.KubernetesApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    KubernetesApi apiInstance = new KubernetesApi(defaultClient);
    Integer clusterId = 56; // Integer | Уникальный идентификатор кластера
    Integer nodeId = 56; // Integer | Уникальный идентификатор группы нод
    try {
      apiInstance.deleteClusterNode(clusterId, nodeId);
    } catch (ApiException e) {
      System.err.println("Exception when calling KubernetesApi#deleteClusterNode");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **clusterId** | **Integer**| Уникальный идентификатор кластера | |
| **nodeId** | **Integer**| Уникальный идентификатор группы нод | |

### Return type

null (empty response body)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **204** | Нода удалена |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="deleteClusterNodeGroup"></a>
# **deleteClusterNodeGroup**
> deleteClusterNodeGroup(clusterId, groupId)

Удаление группы нод

Чтобы удалить группу нод, отправьте DELETE-запрос в &#x60;/api/v1/k8s/clusters/{cluster_id}/groups/{group_id}&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.KubernetesApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    KubernetesApi apiInstance = new KubernetesApi(defaultClient);
    Integer clusterId = 56; // Integer | Уникальный идентификатор кластера
    Integer groupId = 56; // Integer | Уникальный идентификатор группы
    try {
      apiInstance.deleteClusterNodeGroup(clusterId, groupId);
    } catch (ApiException e) {
      System.err.println("Exception when calling KubernetesApi#deleteClusterNodeGroup");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **clusterId** | **Integer**| Уникальный идентификатор кластера | |
| **groupId** | **Integer**| Уникальный идентификатор группы | |

### Return type

null (empty response body)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **204** | Группа нод удалена |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getCluster"></a>
# **getCluster**
> ClusterResponse getCluster(clusterId)

Получение информации о кластере

Чтобы получить информацию о кластере, отправьте GET-запрос в &#x60;/api/v1/k8s/clusters/{cluster_id}&#x60;

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.KubernetesApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    KubernetesApi apiInstance = new KubernetesApi(defaultClient);
    Integer clusterId = 56; // Integer | Уникальный идентификатор кластера
    try {
      ClusterResponse result = apiInstance.getCluster(clusterId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling KubernetesApi#getCluster");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **clusterId** | **Integer**| Уникальный идентификатор кластера | |

### Return type

[**ClusterResponse**](ClusterResponse.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Информация о кластере |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getClusterKubeconfig"></a>
# **getClusterKubeconfig**
> String getClusterKubeconfig(clusterId)

Получение файла kubeconfig

Чтобы получить файл kubeconfig, отправьте GET-запрос в &#x60;/api/v1/k8s/clusters/{cluster_id}/kubeconfig&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.KubernetesApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    KubernetesApi apiInstance = new KubernetesApi(defaultClient);
    Integer clusterId = 56; // Integer | Уникальный идентификатор кластера
    try {
      String result = apiInstance.getClusterKubeconfig(clusterId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling KubernetesApi#getClusterKubeconfig");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **clusterId** | **Integer**| Уникальный идентификатор кластера | |

### Return type

**String**

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/yaml, application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Загрузка конфигурации подключения к кластеру |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getClusterNodeGroup"></a>
# **getClusterNodeGroup**
> NodeGroupResponse getClusterNodeGroup(clusterId, groupId)

Получение информации о группе нод

Чтобы получить информацию о группе нод, отправьте GET-запрос в &#x60;/api/v1/k8s/clusters/{cluster_id}/groups/{group_id}&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.KubernetesApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    KubernetesApi apiInstance = new KubernetesApi(defaultClient);
    Integer clusterId = 56; // Integer | Уникальный идентификатор кластера
    Integer groupId = 56; // Integer | Уникальный идентификатор группы
    try {
      NodeGroupResponse result = apiInstance.getClusterNodeGroup(clusterId, groupId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling KubernetesApi#getClusterNodeGroup");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **clusterId** | **Integer**| Уникальный идентификатор кластера | |
| **groupId** | **Integer**| Уникальный идентификатор группы | |

### Return type

[**NodeGroupResponse**](NodeGroupResponse.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Информация о группе нод |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getClusterNodeGroups"></a>
# **getClusterNodeGroups**
> NodeGroupsResponse getClusterNodeGroups(clusterId)

Получение групп нод кластера

Чтобы получить группы нод кластера, отправьте GET-запрос в &#x60;/api/v1/k8s/clusters/{cluster_id}/groups&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.KubernetesApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    KubernetesApi apiInstance = new KubernetesApi(defaultClient);
    Integer clusterId = 56; // Integer | Уникальный идентификатор кластера
    try {
      NodeGroupsResponse result = apiInstance.getClusterNodeGroups(clusterId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling KubernetesApi#getClusterNodeGroups");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **clusterId** | **Integer**| Уникальный идентификатор кластера | |

### Return type

[**NodeGroupsResponse**](NodeGroupsResponse.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Список групп нод |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getClusterNodes"></a>
# **getClusterNodes**
> NodesResponse getClusterNodes(clusterId)

Получение списка нод

Чтобы получить список нод, отправьте GET-запрос в &#x60;/api/v1/k8s/clusters/{cluster_id}/nodes&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.KubernetesApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    KubernetesApi apiInstance = new KubernetesApi(defaultClient);
    Integer clusterId = 56; // Integer | Уникальный идентификатор кластера
    try {
      NodesResponse result = apiInstance.getClusterNodes(clusterId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling KubernetesApi#getClusterNodes");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **clusterId** | **Integer**| Уникальный идентификатор кластера | |

### Return type

[**NodesResponse**](NodesResponse.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Список нод |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getClusterNodesFromGroup"></a>
# **getClusterNodesFromGroup**
> NodesResponse getClusterNodesFromGroup(clusterId, groupId, limit, offset)

Получение списка нод, принадлежащих группе

Чтобы получить список нод принадлежащих группе, отправьте GET-запрос в &#x60;/api/v1/k8s/clusters/{cluster_id}/groups/{group_id}/nodes&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.KubernetesApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    KubernetesApi apiInstance = new KubernetesApi(defaultClient);
    Integer clusterId = 56; // Integer | Уникальный идентификатор кластера
    Integer groupId = 56; // Integer | Уникальный идентификатор группы
    Integer limit = 100; // Integer | Обозначает количество записей, которое необходимо вернуть.
    Integer offset = 0; // Integer | Указывает на смещение, относительно начала списка.
    try {
      NodesResponse result = apiInstance.getClusterNodesFromGroup(clusterId, groupId, limit, offset);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling KubernetesApi#getClusterNodesFromGroup");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **clusterId** | **Integer**| Уникальный идентификатор кластера | |
| **groupId** | **Integer**| Уникальный идентификатор группы | |
| **limit** | **Integer**| Обозначает количество записей, которое необходимо вернуть. | [optional] [default to 100] |
| **offset** | **Integer**| Указывает на смещение, относительно начала списка. | [optional] [default to 0] |

### Return type

[**NodesResponse**](NodesResponse.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Список нод |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getClusterResources"></a>
# **getClusterResources**
> ResourcesResponse getClusterResources(clusterId)

Получение ресурсов кластера

Устаревший метод, работает только для старых кластеров. \\  Чтобы получить ресурсы кластера, отправьте GET-запрос в &#x60;/api/v1/k8s/clusters/{cluster_id}/resources&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.KubernetesApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    KubernetesApi apiInstance = new KubernetesApi(defaultClient);
    Integer clusterId = 56; // Integer | Уникальный идентификатор кластера
    try {
      ResourcesResponse result = apiInstance.getClusterResources(clusterId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling KubernetesApi#getClusterResources");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **clusterId** | **Integer**| Уникальный идентификатор кластера | |

### Return type

[**ResourcesResponse**](ResourcesResponse.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Список ресурсов |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getClusters"></a>
# **getClusters**
> ClustersResponse getClusters(limit, offset)

Получение списка кластеров

Чтобы получить список кластеров, отправьте GET-запрос на &#x60;/api/v1/k8s/clusters&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.KubernetesApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    KubernetesApi apiInstance = new KubernetesApi(defaultClient);
    Integer limit = 100; // Integer | Обозначает количество записей, которое необходимо вернуть.
    Integer offset = 0; // Integer | Указывает на смещение относительно начала списка.
    try {
      ClustersResponse result = apiInstance.getClusters(limit, offset);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling KubernetesApi#getClusters");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **limit** | **Integer**| Обозначает количество записей, которое необходимо вернуть. | [optional] [default to 100] |
| **offset** | **Integer**| Указывает на смещение относительно начала списка. | [optional] [default to 0] |

### Return type

[**ClustersResponse**](ClustersResponse.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Список кластеров |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getK8SNetworkDrivers"></a>
# **getK8SNetworkDrivers**
> NetworkDriversResponse getK8SNetworkDrivers()

Получение списка сетевых драйверов k8s

Чтобы получить список сетевых драйверов k8s, отправьте GET-запрос в &#x60;/api/v1/k8s/network-drivers&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.KubernetesApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    KubernetesApi apiInstance = new KubernetesApi(defaultClient);
    try {
      NetworkDriversResponse result = apiInstance.getK8SNetworkDrivers();
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling KubernetesApi#getK8SNetworkDrivers");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**NetworkDriversResponse**](NetworkDriversResponse.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Список сетевых драйверов k8s |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getK8SVersions"></a>
# **getK8SVersions**
> K8SVersionsResponse getK8SVersions()

Получение списка версий k8s

Чтобы получить список версий k8s, отправьте GET-запрос в &#x60;/api/v1/k8s/k8s-versions&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.KubernetesApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    KubernetesApi apiInstance = new KubernetesApi(defaultClient);
    try {
      K8SVersionsResponse result = apiInstance.getK8SVersions();
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling KubernetesApi#getK8SVersions");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**K8SVersionsResponse**](K8SVersionsResponse.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Список версий k8s |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getKubernetesPresets"></a>
# **getKubernetesPresets**
> PresetsResponse getKubernetesPresets()

Получение списка тарифов

Чтобы получить список тарифов, отправьте GET-запрос в &#x60;/api/v1/presets/k8s&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.KubernetesApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    KubernetesApi apiInstance = new KubernetesApi(defaultClient);
    try {
      PresetsResponse result = apiInstance.getKubernetesPresets();
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling KubernetesApi#getKubernetesPresets");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**PresetsResponse**](PresetsResponse.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Список тарифов |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="increaseCountOfNodesInGroup"></a>
# **increaseCountOfNodesInGroup**
> NodesResponse increaseCountOfNodesInGroup(clusterId, groupId, nodeCount)

Увеличение количества нод в группе на указанное количество

Чтобы увеличить количество нод в группе на указанное значение, отправьте POST-запрос на &#x60;/api/v1/k8s/clusters/{cluster_id}/groups/{group_id}/nodes&#x60;

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.KubernetesApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    KubernetesApi apiInstance = new KubernetesApi(defaultClient);
    Integer clusterId = 56; // Integer | Уникальный идентификатор кластера
    Integer groupId = 56; // Integer | Уникальный идентификатор группы
    NodeCount nodeCount = new NodeCount(); // NodeCount | 
    try {
      NodesResponse result = apiInstance.increaseCountOfNodesInGroup(clusterId, groupId, nodeCount);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling KubernetesApi#increaseCountOfNodesInGroup");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **clusterId** | **Integer**| Уникальный идентификатор кластера | |
| **groupId** | **Integer**| Уникальный идентификатор группы | |
| **nodeCount** | [**NodeCount**](NodeCount.md)|  | |

### Return type

[**NodesResponse**](NodesResponse.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Список нод |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="reduceCountOfNodesInGroup"></a>
# **reduceCountOfNodesInGroup**
> reduceCountOfNodesInGroup(clusterId, groupId, nodeCount)

Уменьшение количества нод в группе на указанное количество

Чтобы уменьшить количество нод в группе на указанное значение, отправьте DELETE-запрос в &#x60;/api/v1/k8s/clusters/{cluster_id}/groups/{group_id}/nodes&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.KubernetesApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    KubernetesApi apiInstance = new KubernetesApi(defaultClient);
    Integer clusterId = 56; // Integer | Уникальный идентификатор кластера
    Integer groupId = 56; // Integer | Уникальный идентификатор группы
    NodeCount nodeCount = new NodeCount(); // NodeCount | 
    try {
      apiInstance.reduceCountOfNodesInGroup(clusterId, groupId, nodeCount);
    } catch (ApiException e) {
      System.err.println("Exception when calling KubernetesApi#reduceCountOfNodesInGroup");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **clusterId** | **Integer**| Уникальный идентификатор кластера | |
| **groupId** | **Integer**| Уникальный идентификатор группы | |
| **nodeCount** | [**NodeCount**](NodeCount.md)|  | |

### Return type

null (empty response body)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **204** | Количество нод уменьшено |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="updateCluster"></a>
# **updateCluster**
> ClusterResponse updateCluster(clusterId, clusterEdit)

Обновление информации о кластере

Чтобы обновить информацию о кластере, отправьте PATCH-запрос в &#x60;/api/v1/k8s/clusters/{cluster_id}&#x60;

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.KubernetesApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    KubernetesApi apiInstance = new KubernetesApi(defaultClient);
    Integer clusterId = 56; // Integer | Уникальный идентификатор кластера
    ClusterEdit clusterEdit = new ClusterEdit(); // ClusterEdit | 
    try {
      ClusterResponse result = apiInstance.updateCluster(clusterId, clusterEdit);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling KubernetesApi#updateCluster");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **clusterId** | **Integer**| Уникальный идентификатор кластера | |
| **clusterEdit** | [**ClusterEdit**](ClusterEdit.md)|  | |

### Return type

[**ClusterResponse**](ClusterResponse.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Информация о кластере |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

