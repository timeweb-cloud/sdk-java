# AiAgentsApi

All URIs are relative to *https://api.timeweb.cloud*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**addAdditionalTokenPackage**](AiAgentsApi.md#addAdditionalTokenPackage) | **POST** /api/v1/cloud-ai/agents/{id}/add-additional-token-package | Добавление дополнительного пакета токенов |
| [**createAgent**](AiAgentsApi.md#createAgent) | **POST** /api/v1/cloud-ai/agents | Создание AI агента |
| [**deleteAgent**](AiAgentsApi.md#deleteAgent) | **DELETE** /api/v1/cloud-ai/agents/{id} | Удаление AI агента |
| [**getAgent**](AiAgentsApi.md#getAgent) | **GET** /api/v1/cloud-ai/agents/{id} | Получение AI агента |
| [**getAgentStatistics**](AiAgentsApi.md#getAgentStatistics) | **GET** /api/v1/cloud-ai/agents/{id}/statistic | Получение статистики использования токенов агента |
| [**getAgents**](AiAgentsApi.md#getAgents) | **GET** /api/v1/cloud-ai/agents | Получение списка AI агентов |
| [**getAgentsTokenPackages**](AiAgentsApi.md#getAgentsTokenPackages) | **GET** /api/v1/cloud-ai/token-packages/agents | Получение списка пакетов токенов для агентов |
| [**getKnowledgebasesTokenPackages**](AiAgentsApi.md#getKnowledgebasesTokenPackages) | **GET** /api/v1/cloud-ai/token-packages/knowledge-bases | Получение списка пакетов токенов для баз знаний |
| [**getModels**](AiAgentsApi.md#getModels) | **GET** /api/v1/cloud-ai/models | Получение списка моделей |
| [**updateAgent**](AiAgentsApi.md#updateAgent) | **PATCH** /api/v1/cloud-ai/agents/{id} | Обновление AI агента |


<a id="addAdditionalTokenPackage"></a>
# **addAdditionalTokenPackage**
> addAdditionalTokenPackage(id, addTokenPackage)

Добавление дополнительного пакета токенов

Чтобы добавить дополнительный пакет токенов для AI агента, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/agents/{id}/add-additional-token-package&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.AiAgentsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    AiAgentsApi apiInstance = new AiAgentsApi(defaultClient);
    Integer id = 1; // Integer | ID агента
    AddTokenPackage addTokenPackage = new AddTokenPackage(); // AddTokenPackage | 
    try {
      apiInstance.addAdditionalTokenPackage(id, addTokenPackage);
    } catch (ApiException e) {
      System.err.println("Exception when calling AiAgentsApi#addAdditionalTokenPackage");
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
| **id** | **Integer**| ID агента | |
| **addTokenPackage** | [**AddTokenPackage**](AddTokenPackage.md)|  | [optional] |

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
| **204** | Дополнительный пакет токенов успешно добавлен |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="createAgent"></a>
# **createAgent**
> CreateAgent201Response createAgent(createAgent)

Создание AI агента

Чтобы создать AI агента, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/agents&#x60;, задав необходимые атрибуты.  Агент будет создан с использованием предоставленной информации. Тело ответа будет содержать объект JSON с информацией о созданном агенте.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.AiAgentsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    AiAgentsApi apiInstance = new AiAgentsApi(defaultClient);
    CreateAgent createAgent = new CreateAgent(); // CreateAgent | 
    try {
      CreateAgent201Response result = apiInstance.createAgent(createAgent);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AiAgentsApi#createAgent");
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
| **createAgent** | [**CreateAgent**](CreateAgent.md)|  | |

### Return type

[**CreateAgent201Response**](CreateAgent201Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Объект JSON c ключом &#x60;agent&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="deleteAgent"></a>
# **deleteAgent**
> deleteAgent(id)

Удаление AI агента

Чтобы удалить AI агента, отправьте DELETE-запрос на &#x60;/api/v1/cloud-ai/agents/{id}&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.AiAgentsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    AiAgentsApi apiInstance = new AiAgentsApi(defaultClient);
    Integer id = 1; // Integer | ID агента
    try {
      apiInstance.deleteAgent(id);
    } catch (ApiException e) {
      System.err.println("Exception when calling AiAgentsApi#deleteAgent");
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
| **id** | **Integer**| ID агента | |

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
| **204** | AI агент успешно удален |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getAgent"></a>
# **getAgent**
> CreateAgent201Response getAgent(id)

Получение AI агента

Чтобы получить информацию об AI агенте, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/agents/{id}&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.AiAgentsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    AiAgentsApi apiInstance = new AiAgentsApi(defaultClient);
    Integer id = 1; // Integer | ID агента
    try {
      CreateAgent201Response result = apiInstance.getAgent(id);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AiAgentsApi#getAgent");
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
| **id** | **Integer**| ID агента | |

### Return type

[**CreateAgent201Response**](CreateAgent201Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Объект JSON c ключом &#x60;agent&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getAgentStatistics"></a>
# **getAgentStatistics**
> GetAgentStatistics200Response getAgentStatistics(id, startTime, endTime, interval)

Получение статистики использования токенов агента

Чтобы получить статистику использования токенов AI агента, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/agents/{id}/statistic&#x60;.  Можно указать временной диапазон и интервал агрегации.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.AiAgentsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    AiAgentsApi apiInstance = new AiAgentsApi(defaultClient);
    Integer id = 1; // Integer | ID агента
    OffsetDateTime startTime = OffsetDateTime.parse("2024-10-01T00:00Z"); // OffsetDateTime | Начало временного диапазона (ISO 8601)
    OffsetDateTime endTime = OffsetDateTime.parse("2024-10-16T23:59:59.999Z"); // OffsetDateTime | Конец временного диапазона (ISO 8601)
    BigDecimal interval = new BigDecimal("60"); // BigDecimal | Интервал в минутах (по умолчанию 60)
    try {
      GetAgentStatistics200Response result = apiInstance.getAgentStatistics(id, startTime, endTime, interval);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AiAgentsApi#getAgentStatistics");
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
| **id** | **Integer**| ID агента | |
| **startTime** | **OffsetDateTime**| Начало временного диапазона (ISO 8601) | [optional] |
| **endTime** | **OffsetDateTime**| Конец временного диапазона (ISO 8601) | [optional] |
| **interval** | **BigDecimal**| Интервал в минутах (по умолчанию 60) | [optional] [default to 60] |

### Return type

[**GetAgentStatistics200Response**](GetAgentStatistics200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Объект JSON c ключами &#x60;agent_statistics&#x60; и &#x60;meta&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getAgents"></a>
# **getAgents**
> GetAgents200Response getAgents()

Получение списка AI агентов

Чтобы получить список AI агентов, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/agents&#x60;.  Тело ответа будет представлять собой объект JSON с ключом &#x60;agents&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.AiAgentsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    AiAgentsApi apiInstance = new AiAgentsApi(defaultClient);
    try {
      GetAgents200Response result = apiInstance.getAgents();
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AiAgentsApi#getAgents");
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

[**GetAgents200Response**](GetAgents200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Объект JSON c ключами &#x60;agents&#x60; и &#x60;meta&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getAgentsTokenPackages"></a>
# **getAgentsTokenPackages**
> GetAgentsTokenPackages200Response getAgentsTokenPackages()

Получение списка пакетов токенов для агентов

Чтобы получить список доступных пакетов токенов для AI агентов, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/token-packages/agents&#x60;.  Тело ответа будет представлять собой объект JSON с ключом &#x60;token_packages&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.AiAgentsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    AiAgentsApi apiInstance = new AiAgentsApi(defaultClient);
    try {
      GetAgentsTokenPackages200Response result = apiInstance.getAgentsTokenPackages();
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AiAgentsApi#getAgentsTokenPackages");
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

[**GetAgentsTokenPackages200Response**](GetAgentsTokenPackages200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Объект JSON c ключами &#x60;token_packages&#x60; и &#x60;meta&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getKnowledgebasesTokenPackages"></a>
# **getKnowledgebasesTokenPackages**
> GetAgentsTokenPackages200Response getKnowledgebasesTokenPackages()

Получение списка пакетов токенов для баз знаний

Чтобы получить список доступных пакетов токенов для баз знаний, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/token-packages/knowledge-bases&#x60;.  Тело ответа будет представлять собой объект JSON с ключом &#x60;token_packages&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.AiAgentsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    AiAgentsApi apiInstance = new AiAgentsApi(defaultClient);
    try {
      GetAgentsTokenPackages200Response result = apiInstance.getKnowledgebasesTokenPackages();
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AiAgentsApi#getKnowledgebasesTokenPackages");
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

[**GetAgentsTokenPackages200Response**](GetAgentsTokenPackages200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Объект JSON c ключами &#x60;token_packages&#x60; и &#x60;meta&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getModels"></a>
# **getModels**
> GetModels200Response getModels()

Получение списка моделей

Чтобы получить список доступных AI моделей, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/models&#x60;.  Тело ответа будет представлять собой объект JSON с ключом &#x60;models&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.AiAgentsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    AiAgentsApi apiInstance = new AiAgentsApi(defaultClient);
    try {
      GetModels200Response result = apiInstance.getModels();
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AiAgentsApi#getModels");
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

[**GetModels200Response**](GetModels200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Объект JSON c ключами &#x60;models&#x60; и &#x60;meta&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="updateAgent"></a>
# **updateAgent**
> CreateAgent201Response updateAgent(id, updateAgent)

Обновление AI агента

Чтобы обновить AI агента, отправьте PATCH-запрос на &#x60;/api/v1/cloud-ai/agents/{id}&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.AiAgentsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    AiAgentsApi apiInstance = new AiAgentsApi(defaultClient);
    Integer id = 1; // Integer | ID агента
    UpdateAgent updateAgent = new UpdateAgent(); // UpdateAgent | 
    try {
      CreateAgent201Response result = apiInstance.updateAgent(id, updateAgent);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AiAgentsApi#updateAgent");
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
| **id** | **Integer**| ID агента | |
| **updateAgent** | [**UpdateAgent**](UpdateAgent.md)|  | |

### Return type

[**CreateAgent201Response**](CreateAgent201Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Объект JSON c ключом &#x60;agent&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

