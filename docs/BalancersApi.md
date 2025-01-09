# BalancersApi

All URIs are relative to *https://api.timeweb.cloud*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**addIPsToBalancer**](BalancersApi.md#addIPsToBalancer) | **POST** /api/v1/balancers/{balancer_id}/ips | Добавление IP-адресов к балансировщику |
| [**createBalancer**](BalancersApi.md#createBalancer) | **POST** /api/v1/balancers | Создание бaлансировщика |
| [**createBalancerRule**](BalancersApi.md#createBalancerRule) | **POST** /api/v1/balancers/{balancer_id}/rules | Создание правила для балансировщика |
| [**deleteBalancer**](BalancersApi.md#deleteBalancer) | **DELETE** /api/v1/balancers/{balancer_id} | Удаление балансировщика |
| [**deleteBalancerRule**](BalancersApi.md#deleteBalancerRule) | **DELETE** /api/v1/balancers/{balancer_id}/rules/{rule_id} | Удаление правила для балансировщика |
| [**deleteIPsFromBalancer**](BalancersApi.md#deleteIPsFromBalancer) | **DELETE** /api/v1/balancers/{balancer_id}/ips | Удаление IP-адресов из балансировщика |
| [**getBalancer**](BalancersApi.md#getBalancer) | **GET** /api/v1/balancers/{balancer_id} | Получение бaлансировщика |
| [**getBalancerIPs**](BalancersApi.md#getBalancerIPs) | **GET** /api/v1/balancers/{balancer_id}/ips | Получение списка IP-адресов балансировщика |
| [**getBalancerRules**](BalancersApi.md#getBalancerRules) | **GET** /api/v1/balancers/{balancer_id}/rules | Получение правил балансировщика |
| [**getBalancers**](BalancersApi.md#getBalancers) | **GET** /api/v1/balancers | Получение списка всех бaлансировщиков |
| [**getBalancersPresets**](BalancersApi.md#getBalancersPresets) | **GET** /api/v1/presets/balancers | Получение списка тарифов для балансировщика |
| [**updateBalancer**](BalancersApi.md#updateBalancer) | **PATCH** /api/v1/balancers/{balancer_id} | Обновление балансировщика |
| [**updateBalancerRule**](BalancersApi.md#updateBalancerRule) | **PATCH** /api/v1/balancers/{balancer_id}/rules/{rule_id} | Обновление правила для балансировщика |


<a id="addIPsToBalancer"></a>
# **addIPsToBalancer**
> addIPsToBalancer(balancerId, addIPsToBalancerRequest)

Добавление IP-адресов к балансировщику

Чтобы добавить &#x60;IP&#x60;-адреса к балансировщику, отправьте запрос POST в &#x60;api/v1/balancers/{balancer_id}/ips&#x60;. 

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.BalancersApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    BalancersApi apiInstance = new BalancersApi(defaultClient);
    Integer balancerId = 56; // Integer | ID балансировщика
    AddIPsToBalancerRequest addIPsToBalancerRequest = new AddIPsToBalancerRequest(); // AddIPsToBalancerRequest | 
    try {
      apiInstance.addIPsToBalancer(balancerId, addIPsToBalancerRequest);
    } catch (ApiException e) {
      System.err.println("Exception when calling BalancersApi#addIPsToBalancer");
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
| **balancerId** | **Integer**| ID балансировщика | |
| **addIPsToBalancerRequest** | [**AddIPsToBalancerRequest**](AddIPsToBalancerRequest.md)|  | |

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
| **204** | &#x60;Ip&#x60; адреса добавлены к балансировщику |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="createBalancer"></a>
# **createBalancer**
> CreateBalancer200Response createBalancer(createBalancer)

Создание бaлансировщика

Чтобы создать бaлансировщик на вашем аккаунте, отправьте POST-запрос на &#x60;/api/v1/balancers&#x60;, задав необходимые атрибуты.  Балансировщик будет создан с использованием предоставленной информации. Тело ответа будет содержать объект JSON с информацией о созданном балансировщике.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.BalancersApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    BalancersApi apiInstance = new BalancersApi(defaultClient);
    CreateBalancer createBalancer = new CreateBalancer(); // CreateBalancer | 
    try {
      CreateBalancer200Response result = apiInstance.createBalancer(createBalancer);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling BalancersApi#createBalancer");
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
| **createBalancer** | [**CreateBalancer**](CreateBalancer.md)|  | |

### Return type

[**CreateBalancer200Response**](CreateBalancer200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Ответ будет представлять собой объект JSON c ключом &#x60;balancer&#x60;. |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="createBalancerRule"></a>
# **createBalancerRule**
> CreateBalancerRule200Response createBalancerRule(balancerId, createRule)

Создание правила для балансировщика

Чтобы создать правило для балансировщика, отправьте запрос POST в &#x60;api/v1/balancers/{balancer_id}/rules&#x60;. 

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.BalancersApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    BalancersApi apiInstance = new BalancersApi(defaultClient);
    Integer balancerId = 56; // Integer | ID балансировщика
    CreateRule createRule = new CreateRule(); // CreateRule | 
    try {
      CreateBalancerRule200Response result = apiInstance.createBalancerRule(balancerId, createRule);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling BalancersApi#createBalancerRule");
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
| **balancerId** | **Integer**| ID балансировщика | |
| **createRule** | [**CreateRule**](CreateRule.md)|  | |

### Return type

[**CreateBalancerRule200Response**](CreateBalancerRule200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Ответ будет представлять собой объект JSON c ключом &#x60;rule&#x60;. |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="deleteBalancer"></a>
# **deleteBalancer**
> DeleteBalancer200Response deleteBalancer(balancerId, hash, code)

Удаление балансировщика

Чтобы удалить балансировщик, отправьте запрос DELETE в &#x60;api/v1/balancers/{balancer_id}&#x60;. 

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.BalancersApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    BalancersApi apiInstance = new BalancersApi(defaultClient);
    Integer balancerId = 56; // Integer | ID балансировщика
    String hash = "15095f25-aac3-4d60-a788-96cb5136f186"; // String | Хеш, который совместно с кодом авторизации надо отправить для удаления, если включено подтверждение удаления сервисов через Телеграм.
    String code = "0000"; // String | Код подтверждения, который придет к вам в Телеграм, после запроса удаления, если включено подтверждение удаления сервисов.  При помощи API токена сервисы можно удалять без подтверждения, если параметр токена `is_able_to_delete` установлен в значение `true`
    try {
      DeleteBalancer200Response result = apiInstance.deleteBalancer(balancerId, hash, code);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling BalancersApi#deleteBalancer");
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
| **balancerId** | **Integer**| ID балансировщика | |
| **hash** | **String**| Хеш, который совместно с кодом авторизации надо отправить для удаления, если включено подтверждение удаления сервисов через Телеграм. | [optional] |
| **code** | **String**| Код подтверждения, который придет к вам в Телеграм, после запроса удаления, если включено подтверждение удаления сервисов.  При помощи API токена сервисы можно удалять без подтверждения, если параметр токена &#x60;is_able_to_delete&#x60; установлен в значение &#x60;true&#x60; | [optional] |

### Return type

[**DeleteBalancer200Response**](DeleteBalancer200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Объект JSON c ключом &#x60;balancer_delete&#x60; |  -  |
| **204** | Балансировщик успешно удален. |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="deleteBalancerRule"></a>
# **deleteBalancerRule**
> deleteBalancerRule(balancerId, ruleId)

Удаление правила для балансировщика

Чтобы удалить правило для балансировщика, отправьте запрос DELETE в &#x60;api/v1/balancers/{balancer_id}/rules/{rule_id}&#x60;. 

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.BalancersApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    BalancersApi apiInstance = new BalancersApi(defaultClient);
    Integer balancerId = 56; // Integer | ID балансировщика
    Integer ruleId = 56; // Integer | ID правила для балансировщика
    try {
      apiInstance.deleteBalancerRule(balancerId, ruleId);
    } catch (ApiException e) {
      System.err.println("Exception when calling BalancersApi#deleteBalancerRule");
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
| **balancerId** | **Integer**| ID балансировщика | |
| **ruleId** | **Integer**| ID правила для балансировщика | |

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
| **204** | Правило удалено из балансировщика |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="deleteIPsFromBalancer"></a>
# **deleteIPsFromBalancer**
> deleteIPsFromBalancer(balancerId, addIPsToBalancerRequest)

Удаление IP-адресов из балансировщика

Чтобы удалить &#x60;IP&#x60;-адреса из балансировщика, отправьте запрос DELETE в &#x60;api/v1/balancers/{balancer_id}/ips&#x60;. 

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.BalancersApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    BalancersApi apiInstance = new BalancersApi(defaultClient);
    Integer balancerId = 56; // Integer | ID балансировщика
    AddIPsToBalancerRequest addIPsToBalancerRequest = new AddIPsToBalancerRequest(); // AddIPsToBalancerRequest | 
    try {
      apiInstance.deleteIPsFromBalancer(balancerId, addIPsToBalancerRequest);
    } catch (ApiException e) {
      System.err.println("Exception when calling BalancersApi#deleteIPsFromBalancer");
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
| **balancerId** | **Integer**| ID балансировщика | |
| **addIPsToBalancerRequest** | [**AddIPsToBalancerRequest**](AddIPsToBalancerRequest.md)|  | |

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
| **204** | &#x60;Ip&#x60; адрес удален из балансировщика |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getBalancer"></a>
# **getBalancer**
> CreateBalancer200Response getBalancer(balancerId)

Получение бaлансировщика

Чтобы отобразить информацию об отдельном балансировщике, отправьте запрос GET на &#x60;api/v1/balancers/{balancer_id}&#x60;. 

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.BalancersApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    BalancersApi apiInstance = new BalancersApi(defaultClient);
    Integer balancerId = 56; // Integer | ID балансировщика
    try {
      CreateBalancer200Response result = apiInstance.getBalancer(balancerId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling BalancersApi#getBalancer");
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
| **balancerId** | **Integer**| ID балансировщика | |

### Return type

[**CreateBalancer200Response**](CreateBalancer200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Ответ будет представлять собой объект JSON с ключом &#x60;balancer&#x60;. |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getBalancerIPs"></a>
# **getBalancerIPs**
> GetBalancerIPs200Response getBalancerIPs(balancerId)

Получение списка IP-адресов балансировщика

Чтобы добавить &#x60;IP&#x60;-адреса к балансировщику, отправьте запрос GET в &#x60;api/v1/balancers/{balancer_id}/ips&#x60;. 

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.BalancersApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    BalancersApi apiInstance = new BalancersApi(defaultClient);
    Integer balancerId = 56; // Integer | ID балансировщика
    try {
      GetBalancerIPs200Response result = apiInstance.getBalancerIPs(balancerId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling BalancersApi#getBalancerIPs");
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
| **balancerId** | **Integer**| ID балансировщика | |

### Return type

[**GetBalancerIPs200Response**](GetBalancerIPs200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Ответ будет представлять собой объект JSON c ключом &#x60;ips&#x60;. |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getBalancerRules"></a>
# **getBalancerRules**
> GetBalancerRules200Response getBalancerRules(balancerId)

Получение правил балансировщика

Чтобы получить правила балансировщика, отправьте запрос GET в &#x60;api/v1/balancers/{balancer_id}/rules&#x60;. 

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.BalancersApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    BalancersApi apiInstance = new BalancersApi(defaultClient);
    Integer balancerId = 56; // Integer | ID балансировщика
    try {
      GetBalancerRules200Response result = apiInstance.getBalancerRules(balancerId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling BalancersApi#getBalancerRules");
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
| **balancerId** | **Integer**| ID балансировщика | |

### Return type

[**GetBalancerRules200Response**](GetBalancerRules200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Ответ будет представлять собой объект JSON c ключом &#x60;rules&#x60;. |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getBalancers"></a>
# **getBalancers**
> GetBalancers200Response getBalancers(limit, offset)

Получение списка всех бaлансировщиков

Чтобы получить список всех бaлансировщиков на вашем аккаунте, отправьте GET-запрос на &#x60;/api/v1/balancers&#x60;.   Тело ответа будет представлять собой объект JSON с ключом &#x60;balancers&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.BalancersApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    BalancersApi apiInstance = new BalancersApi(defaultClient);
    Integer limit = 100; // Integer | Обозначает количество записей, которое необходимо вернуть.
    Integer offset = 0; // Integer | Указывает на смещение относительно начала списка.
    try {
      GetBalancers200Response result = apiInstance.getBalancers(limit, offset);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling BalancersApi#getBalancers");
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

[**GetBalancers200Response**](GetBalancers200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Ответ будет представлять собой объект JSON c ключом &#x60;balancers&#x60;. |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getBalancersPresets"></a>
# **getBalancersPresets**
> GetBalancersPresets200Response getBalancersPresets()

Получение списка тарифов для балансировщика

Чтобы получить список тарифов для балансировщика, отправьте GET-запрос на &#x60;/api/v1/presets/balancers&#x60;.   Тело ответа будет представлять собой объект JSON с ключом &#x60;balancers_presets&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.BalancersApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    BalancersApi apiInstance = new BalancersApi(defaultClient);
    try {
      GetBalancersPresets200Response result = apiInstance.getBalancersPresets();
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling BalancersApi#getBalancersPresets");
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

[**GetBalancersPresets200Response**](GetBalancersPresets200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Тарифы успешно получены |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="updateBalancer"></a>
# **updateBalancer**
> CreateBalancer200Response updateBalancer(balancerId, updateBalancer)

Обновление балансировщика

Чтобы обновить только определенные атрибуты балансировщика, отправьте запрос PATCH в &#x60;api/v1/balancers/{balancer_id}&#x60;. 

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.BalancersApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    BalancersApi apiInstance = new BalancersApi(defaultClient);
    Integer balancerId = 56; // Integer | ID балансировщика
    UpdateBalancer updateBalancer = new UpdateBalancer(); // UpdateBalancer | 
    try {
      CreateBalancer200Response result = apiInstance.updateBalancer(balancerId, updateBalancer);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling BalancersApi#updateBalancer");
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
| **balancerId** | **Integer**| ID балансировщика | |
| **updateBalancer** | [**UpdateBalancer**](UpdateBalancer.md)|  | |

### Return type

[**CreateBalancer200Response**](CreateBalancer200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Ответ будет представлять собой объект JSON с ключом &#x60;balancer&#x60;. |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="updateBalancerRule"></a>
# **updateBalancerRule**
> CreateBalancerRule200Response updateBalancerRule(balancerId, ruleId, updateRule)

Обновление правила для балансировщика

Чтобы обновить правило для балансировщика, отправьте запрос PATCH в &#x60;api/v1/balancers/{balancer_id}/rules/{rule_id}&#x60;. 

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.BalancersApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    BalancersApi apiInstance = new BalancersApi(defaultClient);
    Integer balancerId = 56; // Integer | ID балансировщика
    Integer ruleId = 56; // Integer | ID правила для балансировщика
    UpdateRule updateRule = new UpdateRule(); // UpdateRule | 
    try {
      CreateBalancerRule200Response result = apiInstance.updateBalancerRule(balancerId, ruleId, updateRule);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling BalancersApi#updateBalancerRule");
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
| **balancerId** | **Integer**| ID балансировщика | |
| **ruleId** | **Integer**| ID правила для балансировщика | |
| **updateRule** | [**UpdateRule**](UpdateRule.md)|  | |

### Return type

[**CreateBalancerRule200Response**](CreateBalancerRule200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Ответ будет представлять собой объект JSON c ключом &#x60;rule&#x60;. |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

