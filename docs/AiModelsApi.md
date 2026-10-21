# AiModelsApi

All URIs are relative to *https://api.timeweb.cloud*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**getAgentsTokenPackages**](AiModelsApi.md#getAgentsTokenPackages) | **GET** /api/v1/cloud-ai/token-packages/agents | Получение списка пакетов токенов для агентов |
| [**getAiModelParameters**](AiModelsApi.md#getAiModelParameters) | **GET** /api/v3/cloud-ai/model-parameters | Получение справочника характеристик моделей |
| [**getAiModelTokenPackages**](AiModelsApi.md#getAiModelTokenPackages) | **GET** /api/v1/cloud-ai/models/{id}/token-packages | Получение пакетов токенов модели |
| [**getAiProvider**](AiModelsApi.md#getAiProvider) | **GET** /api/v1/cloud-ai/providers/{id} | Получение провайдера моделей |
| [**getAiProviders**](AiModelsApi.md#getAiProviders) | **GET** /api/v1/cloud-ai/providers | Получение списка провайдеров моделей |
| [**getKnowledgebasesTokenPackages**](AiModelsApi.md#getKnowledgebasesTokenPackages) | **GET** /api/v1/cloud-ai/token-packages/knowledge-bases | Получение списка пакетов токенов для баз знаний |
| [**getModels**](AiModelsApi.md#getModels) | **GET** /api/v1/cloud-ai/models | Получение списка моделей (v1) |
| [**getModelsV3**](AiModelsApi.md#getModelsV3) | **GET** /api/v3/cloud-ai/models | Получение списка моделей |


<a id="getAgentsTokenPackages"></a>
# **getAgentsTokenPackages**
> GetAgentsTokenPackages200Response getAgentsTokenPackages()

Получение списка пакетов токенов для агентов

Метод устарел.  Чтобы получить список пакетов токенов для AI агентов всех моделей, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/token-packages/agents&#x60;.  Тело ответа будет представлять собой объект JSON с ключами &#x60;token_packages&#x60; и &#x60;meta&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.AiModelsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    AiModelsApi apiInstance = new AiModelsApi(defaultClient);
    try {
      GetAgentsTokenPackages200Response result = apiInstance.getAgentsTokenPackages();
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AiModelsApi#getAgentsTokenPackages");
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
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getAiModelParameters"></a>
# **getAiModelParameters**
> GetAiModelParameters200Response getAiModelParameters()

Получение справочника характеристик моделей

Чтобы получить справочник характеристик, которые возвращаются в &#x60;parameter_values&#x60; моделей, отправьте GET-запрос на &#x60;/api/v3/cloud-ai/model-parameters&#x60;.  Справочник описывает название, тип, единицу измерения и направление сравнения каждой характеристики. Цены кэша (&#x60;cost_cache_read&#x60;, &#x60;cost_cache_write&#x60;) в справочник не входят.  Тело ответа будет представлять собой объект JSON с ключами &#x60;model_parameters&#x60; и &#x60;meta&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.AiModelsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    AiModelsApi apiInstance = new AiModelsApi(defaultClient);
    try {
      GetAiModelParameters200Response result = apiInstance.getAiModelParameters();
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AiModelsApi#getAiModelParameters");
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

[**GetAiModelParameters200Response**](GetAiModelParameters200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Объект JSON c ключами &#x60;model_parameters&#x60; и &#x60;meta&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getAiModelTokenPackages"></a>
# **getAiModelTokenPackages**
> GetAiModelTokenPackages200Response getAiModelTokenPackages(id)

Получение пакетов токенов модели

Чтобы получить пакеты токенов, доступные для AI модели, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/models/{id}/token-packages&#x60;.  Тело ответа будет представлять собой объект JSON с ключами &#x60;token_packages&#x60; и &#x60;meta&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.AiModelsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    AiModelsApi apiInstance = new AiModelsApi(defaultClient);
    Integer id = 17; // Integer | ID модели
    try {
      GetAiModelTokenPackages200Response result = apiInstance.getAiModelTokenPackages(id);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AiModelsApi#getAiModelTokenPackages");
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
| **id** | **Integer**| ID модели | |

### Return type

[**GetAiModelTokenPackages200Response**](GetAiModelTokenPackages200Response.md)

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
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getAiProvider"></a>
# **getAiProvider**
> GetAiProvider200Response getAiProvider(id)

Получение провайдера моделей

Чтобы получить информацию о провайдере AI моделей, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/providers/{id}&#x60;.  Тело ответа будет представлять собой объект JSON с ключом &#x60;provider&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.AiModelsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    AiModelsApi apiInstance = new AiModelsApi(defaultClient);
    Integer id = 1; // Integer | ID провайдера
    try {
      GetAiProvider200Response result = apiInstance.getAiProvider(id);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AiModelsApi#getAiProvider");
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
| **id** | **Integer**| ID провайдера | |

### Return type

[**GetAiProvider200Response**](GetAiProvider200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Объект JSON c ключом &#x60;provider&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getAiProviders"></a>
# **getAiProviders**
> GetAiProviders200Response getAiProviders()

Получение списка провайдеров моделей

Чтобы получить список провайдеров AI моделей, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/providers&#x60;.  Провайдер модели — поле &#x60;provider_id&#x60; модели.  Тело ответа будет представлять собой объект JSON с ключами &#x60;providers&#x60; и &#x60;meta&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.AiModelsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    AiModelsApi apiInstance = new AiModelsApi(defaultClient);
    try {
      GetAiProviders200Response result = apiInstance.getAiProviders();
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AiModelsApi#getAiProviders");
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

[**GetAiProviders200Response**](GetAiProviders200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Объект JSON c ключами &#x60;providers&#x60; и &#x60;meta&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getKnowledgebasesTokenPackages"></a>
# **getKnowledgebasesTokenPackages**
> GetKnowledgebasesTokenPackages200Response getKnowledgebasesTokenPackages()

Получение списка пакетов токенов для баз знаний

Чтобы получить список пакетов токенов для баз знаний, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/token-packages/knowledge-bases&#x60;.  Базовый пакет (&#x60;package_type: base&#x60;) указывается при создании базы знаний, дополнительный (&#x60;package_type: additional&#x60;) покупается через &#x60;POST /api/v1/cloud-ai/knowledge-bases/{id}/add-additional-token-package&#x60;.  Тело ответа будет представлять собой объект JSON с ключами &#x60;token_packages&#x60; и &#x60;meta&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.AiModelsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    AiModelsApi apiInstance = new AiModelsApi(defaultClient);
    try {
      GetKnowledgebasesTokenPackages200Response result = apiInstance.getKnowledgebasesTokenPackages();
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AiModelsApi#getKnowledgebasesTokenPackages");
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

[**GetKnowledgebasesTokenPackages200Response**](GetKnowledgebasesTokenPackages200Response.md)

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
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getModels"></a>
# **getModels**
> GetModels200Response getModels()

Получение списка моделей (v1)

Метод устарел — используйте &#x60;GET /api/v3/cloud-ai/models&#x60;.  Чтобы получить список AI моделей, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/models&#x60;.  Тело ответа будет представлять собой объект JSON с ключами &#x60;models&#x60; и &#x60;meta&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.AiModelsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    AiModelsApi apiInstance = new AiModelsApi(defaultClient);
    try {
      GetModels200Response result = apiInstance.getModels();
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AiModelsApi#getModels");
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
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getModelsV3"></a>
# **getModelsV3**
> GetModelsV3200Response getModelsV3()

Получение списка моделей

Чтобы получить список AI моделей с ценами и характеристиками, отправьте GET-запрос на &#x60;/api/v3/cloud-ai/models&#x60;.  Цены и характеристики (контекст, модальности, индексы качества) возвращаются в словаре &#x60;parameter_values&#x60;, их названия и единицы — в справочнике &#x60;GET /api/v3/cloud-ai/model-parameters&#x60;. В списке есть и устаревшие модели — проверяйте &#x60;is_deprecated&#x60; и &#x60;is_stopped&#x60;.  Тело ответа будет представлять собой объект JSON с ключами &#x60;models&#x60; и &#x60;meta&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.AiModelsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    AiModelsApi apiInstance = new AiModelsApi(defaultClient);
    try {
      GetModelsV3200Response result = apiInstance.getModelsV3();
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AiModelsApi#getModelsV3");
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

[**GetModelsV3200Response**](GetModelsV3200Response.md)

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
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

