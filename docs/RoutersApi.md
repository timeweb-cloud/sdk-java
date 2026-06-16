# RoutersApi

All URIs are relative to *https://api.timeweb.cloud*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**addNetworks**](RoutersApi.md#addNetworks) | **POST** /api/v1/routers/{router_id}/networks | Подключение сетей к роутеру |
| [**createRouter**](RoutersApi.md#createRouter) | **POST** /api/v1/routers | Создание роутера |
| [**deleteDnat**](RoutersApi.md#deleteDnat) | **DELETE** /api/v1/routers/{router_id}/dnat-rules/{dnat_id} | Удаление правила проброса портов |
| [**deleteRouter**](RoutersApi.md#deleteRouter) | **DELETE** /api/v1/routers/{router_id} | Удаление роутера |
| [**deleteRouterNat**](RoutersApi.md#deleteRouterNat) | **DELETE** /api/v1/routers/{router_id}/networks/{network_name}/nat | Выключение NAT для сети |
| [**deleteRouterNetwork**](RoutersApi.md#deleteRouterNetwork) | **DELETE** /api/v1/routers/{router_id}/networks/{network_name} | Удаление сети из роутера |
| [**deleteStaticRoute**](RoutersApi.md#deleteStaticRoute) | **DELETE** /api/v1/routers/{router_id}/static-routes/{static_route_id} | Удаление статического маршрута |
| [**getAvailableStaticRoutes**](RoutersApi.md#getAvailableStaticRoutes) | **GET** /api/v1/routers/{router_id}/static-routes/available | Получение доступных подсетей для статических маршрутов |
| [**getDnat**](RoutersApi.md#getDnat) | **GET** /api/v1/routers/{router_id}/dnat-rules | Получение списка правил проброса портов |
| [**getDnatRule**](RoutersApi.md#getDnatRule) | **GET** /api/v1/routers/{router_id}/dnat-rules/{dnat_id} | Получение правила проброса портов |
| [**getNetworks**](RoutersApi.md#getNetworks) | **GET** /api/v1/routers/{router_id}/networks | Получение списка сетей роутера |
| [**getRouter**](RoutersApi.md#getRouter) | **GET** /api/v1/routers/{router_id} | Получение информации о роутере |
| [**getRouterAvailableNetworks**](RoutersApi.md#getRouterAvailableNetworks) | **GET** /api/v1/routers/networks/available | Получение списка доступных сетей |
| [**getRouterPresets**](RoutersApi.md#getRouterPresets) | **GET** /api/v1/presets/routers | Получение списка тарифов роутеров |
| [**getRouterStatistics**](RoutersApi.md#getRouterStatistics) | **GET** /api/v1/routers/{router_id}/statistics/{time_from}/{period}/{keys} | Получение статистики роутера |
| [**getRouters**](RoutersApi.md#getRouters) | **GET** /api/v1/routers | Получение списка роутеров |
| [**getStaticRoutes**](RoutersApi.md#getStaticRoutes) | **GET** /api/v1/routers/{router_id}/static-routes | Получение списка статических маршрутов |
| [**patchNetwork**](RoutersApi.md#patchNetwork) | **PATCH** /api/v1/routers/{router_id}/networks/{network_name} | Обновление информации о сети |
| [**patchNetworks**](RoutersApi.md#patchNetworks) | **PATCH** /api/v1/routers/{router_id}/networks | Обновление сетей роутера |
| [**postDnat**](RoutersApi.md#postDnat) | **POST** /api/v1/routers/{router_id}/dnat-rules | Добавление правила проброса портов |
| [**postStaticRoute**](RoutersApi.md#postStaticRoute) | **POST** /api/v1/routers/{router_id}/static-routes | Добавление статического маршрута |
| [**updateRouter**](RoutersApi.md#updateRouter) | **PATCH** /api/v1/routers/{router_id} | Обновление информации о роутере |
| [**updateRouterNat**](RoutersApi.md#updateRouterNat) | **PATCH** /api/v1/routers/{router_id}/networks/{network_name}/nat | Включение NAT для сети |


<a id="addNetworks"></a>
# **addNetworks**
> NetworksResponse addNetworks(routerId, networkIn)

Подключение сетей к роутеру

Чтобы подключить сети к роутеру, отправьте POST-запрос на &#x60;/api/v1/routers/{router_id}/networks&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.RoutersApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    RoutersApi apiInstance = new RoutersApi(defaultClient);
    String routerId = "routerId_example"; // String | ID роутера
    NetworkIn networkIn = new NetworkIn(); // NetworkIn | 
    try {
      NetworksResponse result = apiInstance.addNetworks(routerId, networkIn);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling RoutersApi#addNetworks");
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
| **routerId** | **String**| ID роутера | |
| **networkIn** | [**NetworkIn**](NetworkIn.md)|  | |

### Return type

[**NetworksResponse**](NetworksResponse.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Список сетей роутера |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="createRouter"></a>
# **createRouter**
> RouterResponse createRouter(routerIn)

Создание роутера

Чтобы создать роутер, отправьте POST-запрос на &#x60;/api/v1/routers&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.RoutersApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    RoutersApi apiInstance = new RoutersApi(defaultClient);
    RouterIn routerIn = new RouterIn(); // RouterIn | 
    try {
      RouterResponse result = apiInstance.createRouter(routerIn);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling RoutersApi#createRouter");
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
| **routerIn** | [**RouterIn**](RouterIn.md)|  | |

### Return type

[**RouterResponse**](RouterResponse.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Информация о роутере |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="deleteDnat"></a>
# **deleteDnat**
> deleteDnat(routerId, dnatId)

Удаление правила проброса портов

Чтобы удалить правило проброса портов (DNAT), отправьте DELETE-запрос на &#x60;/api/v1/routers/{router_id}/dnat-rules/{dnat_id}&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.RoutersApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    RoutersApi apiInstance = new RoutersApi(defaultClient);
    String routerId = "routerId_example"; // String | ID роутера
    String dnatId = "dnatId_example"; // String | ID правила
    try {
      apiInstance.deleteDnat(routerId, dnatId);
    } catch (ApiException e) {
      System.err.println("Exception when calling RoutersApi#deleteDnat");
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
| **routerId** | **String**| ID роутера | |
| **dnatId** | **String**| ID правила | |

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
| **200** | Правило удалено |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="deleteRouter"></a>
# **deleteRouter**
> deleteRouter(routerId)

Удаление роутера

Чтобы удалить роутер, отправьте DELETE-запрос на &#x60;/api/v1/routers/{router_id}&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.RoutersApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    RoutersApi apiInstance = new RoutersApi(defaultClient);
    String routerId = "routerId_example"; // String | ID роутера
    try {
      apiInstance.deleteRouter(routerId);
    } catch (ApiException e) {
      System.err.println("Exception when calling RoutersApi#deleteRouter");
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
| **routerId** | **String**| ID роутера | |

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
| **200** | Роутер удален |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="deleteRouterNat"></a>
# **deleteRouterNat**
> RouterResponse deleteRouterNat(routerId, networkName)

Выключение NAT для сети

Чтобы выключить NAT для сети роутера, отправьте DELETE-запрос на &#x60;/api/v1/routers/{router_id}/networks/{network_name}/nat&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.RoutersApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    RoutersApi apiInstance = new RoutersApi(defaultClient);
    String routerId = "routerId_example"; // String | ID роутера
    String networkName = "networkName_example"; // String | Имя сети
    try {
      RouterResponse result = apiInstance.deleteRouterNat(routerId, networkName);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling RoutersApi#deleteRouterNat");
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
| **routerId** | **String**| ID роутера | |
| **networkName** | **String**| Имя сети | |

### Return type

[**RouterResponse**](RouterResponse.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Информация о роутере |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="deleteRouterNetwork"></a>
# **deleteRouterNetwork**
> deleteRouterNetwork(routerId, networkName)

Удаление сети из роутера

Чтобы отключить сеть от роутера, отправьте DELETE-запрос на &#x60;/api/v1/routers/{router_id}/networks/{network_name}&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.RoutersApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    RoutersApi apiInstance = new RoutersApi(defaultClient);
    String routerId = "routerId_example"; // String | ID роутера
    String networkName = "networkName_example"; // String | Имя сети
    try {
      apiInstance.deleteRouterNetwork(routerId, networkName);
    } catch (ApiException e) {
      System.err.println("Exception when calling RoutersApi#deleteRouterNetwork");
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
| **routerId** | **String**| ID роутера | |
| **networkName** | **String**| Имя сети | |

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
| **200** | Сеть отключена от роутера |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="deleteStaticRoute"></a>
# **deleteStaticRoute**
> deleteStaticRoute(routerId, staticRouteId)

Удаление статического маршрута

Чтобы удалить статический маршрут, отправьте DELETE-запрос на &#x60;/api/v1/routers/{router_id}/static-routes/{static_route_id}&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.RoutersApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    RoutersApi apiInstance = new RoutersApi(defaultClient);
    String routerId = "routerId_example"; // String | ID роутера
    String staticRouteId = "staticRouteId_example"; // String | ID статического маршрута
    try {
      apiInstance.deleteStaticRoute(routerId, staticRouteId);
    } catch (ApiException e) {
      System.err.println("Exception when calling RoutersApi#deleteStaticRoute");
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
| **routerId** | **String**| ID роутера | |
| **staticRouteId** | **String**| ID статического маршрута | |

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
| **200** | Статический маршрут удален |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getAvailableStaticRoutes"></a>
# **getAvailableStaticRoutes**
> AvailableStaticRoutesResponse getAvailableStaticRoutes(routerId)

Получение доступных подсетей для статических маршрутов

Чтобы получить список подсетей, доступных для добавления статических маршрутов, отправьте GET-запрос на &#x60;/api/v1/routers/{router_id}/static-routes/available&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.RoutersApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    RoutersApi apiInstance = new RoutersApi(defaultClient);
    String routerId = "routerId_example"; // String | ID роутера
    try {
      AvailableStaticRoutesResponse result = apiInstance.getAvailableStaticRoutes(routerId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling RoutersApi#getAvailableStaticRoutes");
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
| **routerId** | **String**| ID роутера | |

### Return type

[**AvailableStaticRoutesResponse**](AvailableStaticRoutesResponse.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Доступные подсети |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getDnat"></a>
# **getDnat**
> DnatRulesResponse getDnat(routerId)

Получение списка правил проброса портов

Чтобы получить список правил проброса портов (DNAT), отправьте GET-запрос на &#x60;/api/v1/routers/{router_id}/dnat-rules&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.RoutersApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    RoutersApi apiInstance = new RoutersApi(defaultClient);
    String routerId = "routerId_example"; // String | ID роутера
    try {
      DnatRulesResponse result = apiInstance.getDnat(routerId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling RoutersApi#getDnat");
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
| **routerId** | **String**| ID роутера | |

### Return type

[**DnatRulesResponse**](DnatRulesResponse.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Список правил проброса портов |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getDnatRule"></a>
# **getDnatRule**
> DnatRuleResponse getDnatRule(routerId, dnatId)

Получение правила проброса портов

Чтобы получить информацию о правиле проброса портов (DNAT), отправьте GET-запрос на &#x60;/api/v1/routers/{router_id}/dnat-rules/{dnat_id}&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.RoutersApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    RoutersApi apiInstance = new RoutersApi(defaultClient);
    String routerId = "routerId_example"; // String | ID роутера
    String dnatId = "dnatId_example"; // String | ID правила
    try {
      DnatRuleResponse result = apiInstance.getDnatRule(routerId, dnatId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling RoutersApi#getDnatRule");
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
| **routerId** | **String**| ID роутера | |
| **dnatId** | **String**| ID правила | |

### Return type

[**DnatRuleResponse**](DnatRuleResponse.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Правило проброса портов |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getNetworks"></a>
# **getNetworks**
> NetworksResponse getNetworks(routerId)

Получение списка сетей роутера

Чтобы получить список сетей роутера, отправьте GET-запрос на &#x60;/api/v1/routers/{router_id}/networks&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.RoutersApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    RoutersApi apiInstance = new RoutersApi(defaultClient);
    String routerId = "routerId_example"; // String | ID роутера
    try {
      NetworksResponse result = apiInstance.getNetworks(routerId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling RoutersApi#getNetworks");
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
| **routerId** | **String**| ID роутера | |

### Return type

[**NetworksResponse**](NetworksResponse.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Список сетей роутера |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getRouter"></a>
# **getRouter**
> RouterResponse getRouter(routerId)

Получение информации о роутере

Чтобы получить информацию о роутере, отправьте GET-запрос на &#x60;/api/v1/routers/{router_id}&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.RoutersApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    RoutersApi apiInstance = new RoutersApi(defaultClient);
    String routerId = "routerId_example"; // String | ID роутера
    try {
      RouterResponse result = apiInstance.getRouter(routerId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling RoutersApi#getRouter");
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
| **routerId** | **String**| ID роутера | |

### Return type

[**RouterResponse**](RouterResponse.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Информация о роутере |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getRouterAvailableNetworks"></a>
# **getRouterAvailableNetworks**
> AvailableNetworksResponse getRouterAvailableNetworks()

Получение списка доступных сетей

Чтобы получить список локальных сетей, доступных для подключения к роутеру, отправьте GET-запрос на &#x60;/api/v1/routers/networks/available&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.RoutersApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    RoutersApi apiInstance = new RoutersApi(defaultClient);
    try {
      AvailableNetworksResponse result = apiInstance.getRouterAvailableNetworks();
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling RoutersApi#getRouterAvailableNetworks");
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

[**AvailableNetworksResponse**](AvailableNetworksResponse.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Список доступных сетей |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getRouterPresets"></a>
# **getRouterPresets**
> RouterPresetsResponse getRouterPresets()

Получение списка тарифов роутеров

Чтобы получить список доступных тарифов роутеров, отправьте GET-запрос на &#x60;/api/v1/presets/routers&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.RoutersApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    RoutersApi apiInstance = new RoutersApi(defaultClient);
    try {
      RouterPresetsResponse result = apiInstance.getRouterPresets();
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling RoutersApi#getRouterPresets");
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

[**RouterPresetsResponse**](RouterPresetsResponse.md)

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

<a id="getRouterStatistics"></a>
# **getRouterStatistics**
> RouterStatisticsResponse getRouterStatistics(routerId, timeFrom, period, keys, nodeId)

Получение статистики роутера

Чтобы получить статистику роутера, отправьте GET-запрос на &#x60;/api/v1/routers/{router_id}/statistics/{time_from}/{period}/{keys}&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.RoutersApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    RoutersApi apiInstance = new RoutersApi(defaultClient);
    String routerId = "routerId_example"; // String | ID роутера
    String timeFrom = "timeFrom_example"; // String | Начало периода
    String period = "period_example"; // String | Период агрегации
    String keys = "keys_example"; // String | Ключи метрик
    String nodeId = "nodeId_example"; // String | ID ноды
    try {
      RouterStatisticsResponse result = apiInstance.getRouterStatistics(routerId, timeFrom, period, keys, nodeId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling RoutersApi#getRouterStatistics");
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
| **routerId** | **String**| ID роутера | |
| **timeFrom** | **String**| Начало периода | |
| **period** | **String**| Период агрегации | |
| **keys** | **String**| Ключи метрик | |
| **nodeId** | **String**| ID ноды | [optional] |

### Return type

[**RouterStatisticsResponse**](RouterStatisticsResponse.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Статистика роутера |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getRouters"></a>
# **getRouters**
> RoutersResponse getRouters()

Получение списка роутеров

Чтобы получить список роутеров, отправьте GET-запрос на &#x60;/api/v1/routers&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.RoutersApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    RoutersApi apiInstance = new RoutersApi(defaultClient);
    try {
      RoutersResponse result = apiInstance.getRouters();
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling RoutersApi#getRouters");
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

[**RoutersResponse**](RoutersResponse.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Список роутеров |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getStaticRoutes"></a>
# **getStaticRoutes**
> StaticRoutesResponse getStaticRoutes(routerId)

Получение списка статических маршрутов

Чтобы получить список статических маршрутов роутера, отправьте GET-запрос на &#x60;/api/v1/routers/{router_id}/static-routes&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.RoutersApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    RoutersApi apiInstance = new RoutersApi(defaultClient);
    String routerId = "routerId_example"; // String | ID роутера
    try {
      StaticRoutesResponse result = apiInstance.getStaticRoutes(routerId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling RoutersApi#getStaticRoutes");
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
| **routerId** | **String**| ID роутера | |

### Return type

[**StaticRoutesResponse**](StaticRoutesResponse.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Список статических маршрутов |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="patchNetwork"></a>
# **patchNetwork**
> NetworkResponse patchNetwork(routerId, networkName, networkEdit)

Обновление информации о сети

Чтобы включить или выключить DHCP в сети роутера, отправьте PATCH-запрос на &#x60;/api/v1/routers/{router_id}/networks/{network_name}&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.RoutersApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    RoutersApi apiInstance = new RoutersApi(defaultClient);
    String routerId = "routerId_example"; // String | ID роутера
    String networkName = "networkName_example"; // String | Имя сети
    NetworkEdit networkEdit = new NetworkEdit(); // NetworkEdit | 
    try {
      NetworkResponse result = apiInstance.patchNetwork(routerId, networkName, networkEdit);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling RoutersApi#patchNetwork");
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
| **routerId** | **String**| ID роутера | |
| **networkName** | **String**| Имя сети | |
| **networkEdit** | [**NetworkEdit**](NetworkEdit.md)|  | |

### Return type

[**NetworkResponse**](NetworkResponse.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Информация о сети |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="patchNetworks"></a>
# **patchNetworks**
> NetworksResponse patchNetworks(routerId, networkIn)

Обновление сетей роутера

Чтобы обновить набор сетей роутера, отправьте PATCH-запрос на &#x60;/api/v1/routers/{router_id}/networks&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.RoutersApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    RoutersApi apiInstance = new RoutersApi(defaultClient);
    String routerId = "routerId_example"; // String | ID роутера
    NetworkIn networkIn = new NetworkIn(); // NetworkIn | 
    try {
      NetworksResponse result = apiInstance.patchNetworks(routerId, networkIn);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling RoutersApi#patchNetworks");
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
| **routerId** | **String**| ID роутера | |
| **networkIn** | [**NetworkIn**](NetworkIn.md)|  | |

### Return type

[**NetworksResponse**](NetworksResponse.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Список сетей роутера |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="postDnat"></a>
# **postDnat**
> DnatRuleResponse postDnat(routerId, dnatIn)

Добавление правила проброса портов

Чтобы добавить правило проброса портов (DNAT), отправьте POST-запрос на &#x60;/api/v1/routers/{router_id}/dnat-rules&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.RoutersApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    RoutersApi apiInstance = new RoutersApi(defaultClient);
    String routerId = "routerId_example"; // String | ID роутера
    DnatIn dnatIn = new DnatIn(); // DnatIn | 
    try {
      DnatRuleResponse result = apiInstance.postDnat(routerId, dnatIn);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling RoutersApi#postDnat");
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
| **routerId** | **String**| ID роутера | |
| **dnatIn** | [**DnatIn**](DnatIn.md)|  | |

### Return type

[**DnatRuleResponse**](DnatRuleResponse.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Правило проброса портов |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="postStaticRoute"></a>
# **postStaticRoute**
> StaticRouteResponse postStaticRoute(routerId, staticRouteIn)

Добавление статического маршрута

Чтобы добавить статический маршрут, отправьте POST-запрос на &#x60;/api/v1/routers/{router_id}/static-routes&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.RoutersApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    RoutersApi apiInstance = new RoutersApi(defaultClient);
    String routerId = "routerId_example"; // String | ID роутера
    StaticRouteIn staticRouteIn = new StaticRouteIn(); // StaticRouteIn | 
    try {
      StaticRouteResponse result = apiInstance.postStaticRoute(routerId, staticRouteIn);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling RoutersApi#postStaticRoute");
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
| **routerId** | **String**| ID роутера | |
| **staticRouteIn** | [**StaticRouteIn**](StaticRouteIn.md)|  | |

### Return type

[**StaticRouteResponse**](StaticRouteResponse.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Статический маршрут |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="updateRouter"></a>
# **updateRouter**
> RouterResponse updateRouter(routerId, routerEdit)

Обновление информации о роутере

Чтобы обновить информацию о роутере, отправьте PATCH-запрос на &#x60;/api/v1/routers/{router_id}&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.RoutersApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    RoutersApi apiInstance = new RoutersApi(defaultClient);
    String routerId = "routerId_example"; // String | ID роутера
    RouterEdit routerEdit = new RouterEdit(); // RouterEdit | 
    try {
      RouterResponse result = apiInstance.updateRouter(routerId, routerEdit);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling RoutersApi#updateRouter");
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
| **routerId** | **String**| ID роутера | |
| **routerEdit** | [**RouterEdit**](RouterEdit.md)|  | |

### Return type

[**RouterResponse**](RouterResponse.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Информация о роутере |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="updateRouterNat"></a>
# **updateRouterNat**
> RouterResponse updateRouterNat(routerId, networkName, natIn)

Включение NAT для сети

Чтобы включить NAT для сети роутера, отправьте PATCH-запрос на &#x60;/api/v1/routers/{router_id}/networks/{network_name}/nat&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.RoutersApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    RoutersApi apiInstance = new RoutersApi(defaultClient);
    String routerId = "routerId_example"; // String | ID роутера
    String networkName = "networkName_example"; // String | Имя сети
    NatIn natIn = new NatIn(); // NatIn | 
    try {
      RouterResponse result = apiInstance.updateRouterNat(routerId, networkName, natIn);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling RoutersApi#updateRouterNat");
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
| **routerId** | **String**| ID роутера | |
| **networkName** | **String**| Имя сети | |
| **natIn** | [**NatIn**](NatIn.md)|  | |

### Return type

[**RouterResponse**](RouterResponse.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Информация о роутере |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

