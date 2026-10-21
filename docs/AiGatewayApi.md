# AiGatewayApi

All URIs are relative to *https://api.timeweb.cloud*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**createAiKey**](AiGatewayApi.md#createAiKey) | **POST** /api/v1/cloud-ai/ai-keys | Создание ключа AI Gateway |
| [**createAiKeyLimit**](AiGatewayApi.md#createAiKeyLimit) | **POST** /api/v1/cloud-ai/ai-keys/{key_id}/entity-limits | Создание лимита ключа AI Gateway |
| [**deleteAiKey**](AiGatewayApi.md#deleteAiKey) | **DELETE** /api/v1/cloud-ai/ai-keys/{key_id} | Удаление ключа AI Gateway |
| [**deleteAiKeyLimit**](AiGatewayApi.md#deleteAiKeyLimit) | **DELETE** /api/v1/cloud-ai/ai-keys/{key_id}/entity-limits/{limit_id} | Удаление лимита ключа AI Gateway |
| [**getAiKeyCallsStatistic**](AiGatewayApi.md#getAiKeyCallsStatistic) | **GET** /api/v1/cloud-ai/ai-keys/statistic/calls | Получение статистики запросов ключа AI Gateway |
| [**getAiKeyLimit**](AiGatewayApi.md#getAiKeyLimit) | **GET** /api/v1/cloud-ai/ai-keys/{key_id}/entity-limits/{limit_id} | Получение лимита ключа AI Gateway |
| [**getAiKeyLimits**](AiGatewayApi.md#getAiKeyLimits) | **GET** /api/v1/cloud-ai/ai-keys/{key_id}/entity-limits | Получение списка лимитов ключа AI Gateway |
| [**getAiKeyModelsStatistic**](AiGatewayApi.md#getAiKeyModelsStatistic) | **GET** /api/v1/cloud-ai/ai-keys/statistic/models | Получение списка моделей, использованных ключом AI Gateway |
| [**getAiKeyTokenUsageStatistic**](AiGatewayApi.md#getAiKeyTokenUsageStatistic) | **GET** /api/v1/cloud-ai/ai-keys/statistic/token-usage | Получение статистики расхода токенов ключа AI Gateway |
| [**getAiKeys**](AiGatewayApi.md#getAiKeys) | **GET** /api/v1/cloud-ai/ai-keys | Получение списка ключей AI Gateway |
| [**regenerateAiKey**](AiGatewayApi.md#regenerateAiKey) | **PUT** /api/v1/cloud-ai/ai-keys/{key_id}/regenerate | Перевыпуск ключа AI Gateway |
| [**syncAiKeyLimits**](AiGatewayApi.md#syncAiKeyLimits) | **PUT** /api/v1/cloud-ai/ai-keys/{key_id}/entity-limits/sync | Синхронизация лимитов ключа AI Gateway |
| [**updateAiKey**](AiGatewayApi.md#updateAiKey) | **PATCH** /api/v1/cloud-ai/ai-keys/{key_id} | Изменение ключа AI Gateway |
| [**updateAiKeyLimit**](AiGatewayApi.md#updateAiKeyLimit) | **PATCH** /api/v1/cloud-ai/ai-keys/{key_id}/entity-limits/{limit_id} | Изменение лимита ключа AI Gateway |


<a id="createAiKey"></a>
# **createAiKey**
> CreateAiKey201Response createAiKey(createAiKey)

Создание ключа AI Gateway

Чтобы создать ключ AI Gateway, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/ai-keys&#x60;.  Значение ключа (&#x60;sk-...&#x60;) возвращается в поле &#x60;key&#x60; только в ответе на этот запрос — сохраните его. Если значение утеряно, перевыпустите ключ.  Если список &#x60;fallback_models&#x60; некорректен, вернётся 400 с &#x60;error_code&#x60; &#x60;fallback_chain_invalid&#x60; (больше двух моделей или повторы, правило — в &#x60;details.reason&#x60;) или &#x60;fallback_model_not_found&#x60; (модели нет среди действующих текстовых моделей, список — в &#x60;details.model_names&#x60;).

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.AiGatewayApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    AiGatewayApi apiInstance = new AiGatewayApi(defaultClient);
    CreateAiKey createAiKey = new CreateAiKey(); // CreateAiKey | 
    try {
      CreateAiKey201Response result = apiInstance.createAiKey(createAiKey);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AiGatewayApi#createAiKey");
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
| **createAiKey** | [**CreateAiKey**](CreateAiKey.md)|  | |

### Return type

[**CreateAiKey201Response**](CreateAiKey201Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Объект JSON c ключами &#x60;ai_key&#x60; и &#x60;key&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="createAiKeyLimit"></a>
# **createAiKeyLimit**
> CreateAiKeyLimit201Response createAiKeyLimit(keyId, createAiKeyLimit)

Создание лимита ключа AI Gateway

Чтобы ограничить расход ключа AI Gateway, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/ai-keys/{key_id}/entity-limits&#x60;.  Лимит задаётся в токенах (&#x60;limit&#x60;) или в деньгах (&#x60;money_limit&#x60;, приоритетнее) на период &#x60;limit_type&#x60; — для всех моделей или для одной (&#x60;model_id&#x60;). Когда лимит исчерпан, запросы ключа к AI Gateway API завершаются ошибкой 422 до начала следующего периода. Если от лимита осталась часть, максимальный размер ответа текстового запроса уменьшается так, чтобы уложиться в остаток.  На один период и одну модель (или на все модели) можно завести только один лимит — повторный вернёт 409 с &#x60;error_code&#x60; &#x60;entity_keys_limit_already_exists&#x60;. Если не передан ни &#x60;limit&#x60;, ни &#x60;money_limit&#x60;, вернётся 400 с &#x60;error_code&#x60; &#x60;entity_keys_limit_value_required&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.AiGatewayApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    AiGatewayApi apiInstance = new AiGatewayApi(defaultClient);
    UUID keyId = UUID.fromString("550e8400-e29b-41d4-a716-446655440000"); // UUID | ID ключа AI Gateway (UUID)
    CreateAiKeyLimit createAiKeyLimit = new CreateAiKeyLimit(); // CreateAiKeyLimit | 
    try {
      CreateAiKeyLimit201Response result = apiInstance.createAiKeyLimit(keyId, createAiKeyLimit);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AiGatewayApi#createAiKeyLimit");
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
| **keyId** | **UUID**| ID ключа AI Gateway (UUID) | |
| **createAiKeyLimit** | [**CreateAiKeyLimit**](CreateAiKeyLimit.md)|  | |

### Return type

[**CreateAiKeyLimit201Response**](CreateAiKeyLimit201Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Объект JSON c ключом &#x60;ai_key_entity_limit&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **409** | Конфликт |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="deleteAiKey"></a>
# **deleteAiKey**
> deleteAiKey(keyId)

Удаление ключа AI Gateway

Чтобы удалить ключ AI Gateway, отправьте DELETE-запрос на &#x60;/api/v1/cloud-ai/ai-keys/{key_id}&#x60;.  После удаления запросы с этим ключом перестают приниматься. Ключи, которыми управляет платформа (&#x60;is_readonly: true&#x60;), удалить нельзя: вернётся 403 с &#x60;error_code&#x60; &#x60;ai_key_is_readonly&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.AiGatewayApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    AiGatewayApi apiInstance = new AiGatewayApi(defaultClient);
    UUID keyId = UUID.fromString("550e8400-e29b-41d4-a716-446655440000"); // UUID | ID ключа AI Gateway (UUID)
    try {
      apiInstance.deleteAiKey(keyId);
    } catch (ApiException e) {
      System.err.println("Exception when calling AiGatewayApi#deleteAiKey");
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
| **keyId** | **UUID**| ID ключа AI Gateway (UUID) | |

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
| **204** | Ключ удалён |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="deleteAiKeyLimit"></a>
# **deleteAiKeyLimit**
> deleteAiKeyLimit(keyId, limitId)

Удаление лимита ключа AI Gateway

Чтобы удалить лимит ключа AI Gateway, отправьте DELETE-запрос на &#x60;/api/v1/cloud-ai/ai-keys/{key_id}/entity-limits/{limit_id}&#x60;.  Если лимит относится к другому ключу, вернётся 403.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.AiGatewayApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    AiGatewayApi apiInstance = new AiGatewayApi(defaultClient);
    UUID keyId = UUID.fromString("550e8400-e29b-41d4-a716-446655440000"); // UUID | ID ключа AI Gateway (UUID)
    UUID limitId = UUID.fromString("0f6b7d8e-1c2a-4b3d-9e8f-7a6b5c4d3e2f"); // UUID | ID лимита (UUID)
    try {
      apiInstance.deleteAiKeyLimit(keyId, limitId);
    } catch (ApiException e) {
      System.err.println("Exception when calling AiGatewayApi#deleteAiKeyLimit");
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
| **keyId** | **UUID**| ID ключа AI Gateway (UUID) | |
| **limitId** | **UUID**| ID лимита (UUID) | |

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
| **204** | Лимит удалён |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getAiKeyCallsStatistic"></a>
# **getAiKeyCallsStatistic**
> GetAiKeyCallsStatistic200Response getAiKeyCallsStatistic(entityId, entityType, modelName, startTime, endTime, interval)

Получение статистики запросов ключа AI Gateway

Чтобы получить количество запросов ключа по интервалам времени, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/ai-keys/statistic/calls&#x60;.  Ключ задаётся параметрами &#x60;entity_id&#x60; (ID ключа) и &#x60;entity_type&#x60; (тип ключа). Если ключ не найден, вернётся 404. Период по умолчанию — последние 24 часа. Точки возвращаются для каждого интервала периода, включая интервалы без запросов.  Тело ответа будет представлять собой объект JSON с ключами &#x60;ai_key_calls_statistics&#x60; и &#x60;meta&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.AiGatewayApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    AiGatewayApi apiInstance = new AiGatewayApi(defaultClient);
    String entityId = "550e8400-e29b-41d4-a716-446655440000"; // String | ID ключа — поле `id` из списка ключей.
    String entityType = "aikey"; // String | Тип ключа — поле `entity_type` из списка ключей: `aikey` — ключ, созданный пользователем, `k8s` — ключ кластера Kubernetes. Если тип не совпадает с типом ключа, вернётся 404.
    String modelName = "openai/gpt-5.4-mini,openai/gpt-4.1-mini"; // String | Фильтр по моделям: ID модели или несколько ID через запятую. Значение другого формата (например, с заглавными буквами) игнорируется — статистика возвращается по всем моделям.
    OffsetDateTime startTime = OffsetDateTime.parse("2026-10-19T00:00Z"); // OffsetDateTime | Начало периода в формате ISO 8601. По умолчанию — за 24 часа до `end_time`.
    OffsetDateTime endTime = OffsetDateTime.parse("2026-10-20T00:00Z"); // OffsetDateTime | Конец периода в формате ISO 8601. По умолчанию — текущий момент.
    Integer interval = 60; // Integer | Шаг агрегации в минутах.
    try {
      GetAiKeyCallsStatistic200Response result = apiInstance.getAiKeyCallsStatistic(entityId, entityType, modelName, startTime, endTime, interval);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AiGatewayApi#getAiKeyCallsStatistic");
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
| **entityId** | **String**| ID ключа — поле &#x60;id&#x60; из списка ключей. | |
| **entityType** | **String**| Тип ключа — поле &#x60;entity_type&#x60; из списка ключей: &#x60;aikey&#x60; — ключ, созданный пользователем, &#x60;k8s&#x60; — ключ кластера Kubernetes. Если тип не совпадает с типом ключа, вернётся 404. | [enum: aikey, k8s] |
| **modelName** | **String**| Фильтр по моделям: ID модели или несколько ID через запятую. Значение другого формата (например, с заглавными буквами) игнорируется — статистика возвращается по всем моделям. | [optional] |
| **startTime** | **OffsetDateTime**| Начало периода в формате ISO 8601. По умолчанию — за 24 часа до &#x60;end_time&#x60;. | [optional] |
| **endTime** | **OffsetDateTime**| Конец периода в формате ISO 8601. По умолчанию — текущий момент. | [optional] |
| **interval** | **Integer**| Шаг агрегации в минутах. | [optional] [default to 60] |

### Return type

[**GetAiKeyCallsStatistic200Response**](GetAiKeyCallsStatistic200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Объект JSON c ключами &#x60;ai_key_calls_statistics&#x60; и &#x60;meta&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getAiKeyLimit"></a>
# **getAiKeyLimit**
> CreateAiKeyLimit201Response getAiKeyLimit(keyId, limitId)

Получение лимита ключа AI Gateway

Чтобы получить лимит ключа AI Gateway, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/ai-keys/{key_id}/entity-limits/{limit_id}&#x60;.  Если лимит относится к другому ключу, вернётся 403 с &#x60;error_code&#x60; &#x60;entity_keys_limit_forbidden&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.AiGatewayApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    AiGatewayApi apiInstance = new AiGatewayApi(defaultClient);
    UUID keyId = UUID.fromString("550e8400-e29b-41d4-a716-446655440000"); // UUID | ID ключа AI Gateway (UUID)
    UUID limitId = UUID.fromString("0f6b7d8e-1c2a-4b3d-9e8f-7a6b5c4d3e2f"); // UUID | ID лимита (UUID)
    try {
      CreateAiKeyLimit201Response result = apiInstance.getAiKeyLimit(keyId, limitId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AiGatewayApi#getAiKeyLimit");
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
| **keyId** | **UUID**| ID ключа AI Gateway (UUID) | |
| **limitId** | **UUID**| ID лимита (UUID) | |

### Return type

[**CreateAiKeyLimit201Response**](CreateAiKeyLimit201Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Объект JSON c ключом &#x60;ai_key_entity_limit&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getAiKeyLimits"></a>
# **getAiKeyLimits**
> GetAiKeyLimits200Response getAiKeyLimits(keyId, entityType, limitType)

Получение списка лимитов ключа AI Gateway

Чтобы получить лимиты расхода ключа AI Gateway, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/ai-keys/{key_id}/entity-limits&#x60;.  Тело ответа будет представлять собой объект JSON с ключами &#x60;ai_key_entity_limits&#x60; и &#x60;meta&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.AiGatewayApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    AiGatewayApi apiInstance = new AiGatewayApi(defaultClient);
    UUID keyId = UUID.fromString("550e8400-e29b-41d4-a716-446655440000"); // UUID | ID ключа AI Gateway (UUID)
    String entityType = "aikey"; // String | Фильтр по типу сущности лимита (`entity_type`).
    String limitType = "hour"; // String | Фильтр по периоду лимита.
    try {
      GetAiKeyLimits200Response result = apiInstance.getAiKeyLimits(keyId, entityType, limitType);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AiGatewayApi#getAiKeyLimits");
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
| **keyId** | **UUID**| ID ключа AI Gateway (UUID) | |
| **entityType** | **String**| Фильтр по типу сущности лимита (&#x60;entity_type&#x60;). | [optional] |
| **limitType** | **String**| Фильтр по периоду лимита. | [optional] [enum: hour, day, week, month] |

### Return type

[**GetAiKeyLimits200Response**](GetAiKeyLimits200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Объект JSON c ключами &#x60;ai_key_entity_limits&#x60; и &#x60;meta&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getAiKeyModelsStatistic"></a>
# **getAiKeyModelsStatistic**
> GetAiKeyModelsStatistic200Response getAiKeyModelsStatistic(entityId, entityType, modelName, startTime, endTime)

Получение списка моделей, использованных ключом AI Gateway

Чтобы получить список моделей, к которым обращался ключ за период, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/ai-keys/statistic/models&#x60;.  Ключ задаётся параметрами &#x60;entity_id&#x60; (ID ключа) и &#x60;entity_type&#x60; (тип ключа). Если ключ не найден, вернётся 404. Период по умолчанию — последние 24 часа. Модели отсортированы по алфавиту.  Тело ответа будет представлять собой объект JSON с ключами &#x60;ai_key_models_statistics&#x60; и &#x60;meta&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.AiGatewayApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    AiGatewayApi apiInstance = new AiGatewayApi(defaultClient);
    String entityId = "550e8400-e29b-41d4-a716-446655440000"; // String | ID ключа — поле `id` из списка ключей.
    String entityType = "aikey"; // String | Тип ключа — поле `entity_type` из списка ключей: `aikey` — ключ, созданный пользователем, `k8s` — ключ кластера Kubernetes. Если тип не совпадает с типом ключа, вернётся 404.
    String modelName = "openai/gpt-5.4-mini,openai/gpt-4.1-mini"; // String | Фильтр по моделям: ID модели или несколько ID через запятую. Значение другого формата (например, с заглавными буквами) игнорируется — статистика возвращается по всем моделям.
    OffsetDateTime startTime = OffsetDateTime.parse("2026-10-19T00:00Z"); // OffsetDateTime | Начало периода в формате ISO 8601. По умолчанию — за 24 часа до `end_time`.
    OffsetDateTime endTime = OffsetDateTime.parse("2026-10-20T00:00Z"); // OffsetDateTime | Конец периода в формате ISO 8601. По умолчанию — текущий момент.
    try {
      GetAiKeyModelsStatistic200Response result = apiInstance.getAiKeyModelsStatistic(entityId, entityType, modelName, startTime, endTime);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AiGatewayApi#getAiKeyModelsStatistic");
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
| **entityId** | **String**| ID ключа — поле &#x60;id&#x60; из списка ключей. | |
| **entityType** | **String**| Тип ключа — поле &#x60;entity_type&#x60; из списка ключей: &#x60;aikey&#x60; — ключ, созданный пользователем, &#x60;k8s&#x60; — ключ кластера Kubernetes. Если тип не совпадает с типом ключа, вернётся 404. | [enum: aikey, k8s] |
| **modelName** | **String**| Фильтр по моделям: ID модели или несколько ID через запятую. Значение другого формата (например, с заглавными буквами) игнорируется — статистика возвращается по всем моделям. | [optional] |
| **startTime** | **OffsetDateTime**| Начало периода в формате ISO 8601. По умолчанию — за 24 часа до &#x60;end_time&#x60;. | [optional] |
| **endTime** | **OffsetDateTime**| Конец периода в формате ISO 8601. По умолчанию — текущий момент. | [optional] |

### Return type

[**GetAiKeyModelsStatistic200Response**](GetAiKeyModelsStatistic200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Объект JSON c ключами &#x60;ai_key_models_statistics&#x60; и &#x60;meta&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getAiKeyTokenUsageStatistic"></a>
# **getAiKeyTokenUsageStatistic**
> GetAiKeyTokenUsageStatistic200Response getAiKeyTokenUsageStatistic(entityId, entityType, modelName, startTime, endTime, interval)

Получение статистики расхода токенов ключа AI Gateway

Чтобы получить расход токенов и стоимость запросов ключа по интервалам времени, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/ai-keys/statistic/token-usage&#x60;.  Ключ задаётся параметрами &#x60;entity_id&#x60; (ID ключа) и &#x60;entity_type&#x60; (тип ключа). Если ключ не найден, вернётся 404. Период по умолчанию — последние 24 часа. Точки возвращаются для каждого интервала периода, включая интервалы без запросов.  Тело ответа будет представлять собой объект JSON с ключами &#x60;ai_key_token_statistics&#x60; и &#x60;meta&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.AiGatewayApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    AiGatewayApi apiInstance = new AiGatewayApi(defaultClient);
    String entityId = "550e8400-e29b-41d4-a716-446655440000"; // String | ID ключа — поле `id` из списка ключей.
    String entityType = "aikey"; // String | Тип ключа — поле `entity_type` из списка ключей: `aikey` — ключ, созданный пользователем, `k8s` — ключ кластера Kubernetes. Если тип не совпадает с типом ключа, вернётся 404.
    String modelName = "openai/gpt-5.4-mini,openai/gpt-4.1-mini"; // String | Фильтр по моделям: ID модели или несколько ID через запятую. Значение другого формата (например, с заглавными буквами) игнорируется — статистика возвращается по всем моделям.
    OffsetDateTime startTime = OffsetDateTime.parse("2026-10-19T00:00Z"); // OffsetDateTime | Начало периода в формате ISO 8601. По умолчанию — за 24 часа до `end_time`.
    OffsetDateTime endTime = OffsetDateTime.parse("2026-10-20T00:00Z"); // OffsetDateTime | Конец периода в формате ISO 8601. По умолчанию — текущий момент.
    Integer interval = 60; // Integer | Шаг агрегации в минутах.
    try {
      GetAiKeyTokenUsageStatistic200Response result = apiInstance.getAiKeyTokenUsageStatistic(entityId, entityType, modelName, startTime, endTime, interval);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AiGatewayApi#getAiKeyTokenUsageStatistic");
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
| **entityId** | **String**| ID ключа — поле &#x60;id&#x60; из списка ключей. | |
| **entityType** | **String**| Тип ключа — поле &#x60;entity_type&#x60; из списка ключей: &#x60;aikey&#x60; — ключ, созданный пользователем, &#x60;k8s&#x60; — ключ кластера Kubernetes. Если тип не совпадает с типом ключа, вернётся 404. | [enum: aikey, k8s] |
| **modelName** | **String**| Фильтр по моделям: ID модели или несколько ID через запятую. Значение другого формата (например, с заглавными буквами) игнорируется — статистика возвращается по всем моделям. | [optional] |
| **startTime** | **OffsetDateTime**| Начало периода в формате ISO 8601. По умолчанию — за 24 часа до &#x60;end_time&#x60;. | [optional] |
| **endTime** | **OffsetDateTime**| Конец периода в формате ISO 8601. По умолчанию — текущий момент. | [optional] |
| **interval** | **Integer**| Шаг агрегации в минутах. | [optional] [default to 60] |

### Return type

[**GetAiKeyTokenUsageStatistic200Response**](GetAiKeyTokenUsageStatistic200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Объект JSON c ключами &#x60;ai_key_token_statistics&#x60; и &#x60;meta&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getAiKeys"></a>
# **getAiKeys**
> GetAiKeys200Response getAiKeys()

Получение списка ключей AI Gateway

Чтобы получить список ключей AI Gateway, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/ai-keys&#x60;.  В списке — ключи, созданные пользователем, и ключи кластеров Kubernetes, выпущенные платформой. Значения ключей (&#x60;sk-...&#x60;) не возвращаются.  Тело ответа будет представлять собой объект JSON с ключами &#x60;ai_keys&#x60; и &#x60;meta&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.AiGatewayApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    AiGatewayApi apiInstance = new AiGatewayApi(defaultClient);
    try {
      GetAiKeys200Response result = apiInstance.getAiKeys();
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AiGatewayApi#getAiKeys");
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

[**GetAiKeys200Response**](GetAiKeys200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Объект JSON c ключами &#x60;ai_keys&#x60; и &#x60;meta&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="regenerateAiKey"></a>
# **regenerateAiKey**
> CreateAiKey201Response regenerateAiKey(keyId)

Перевыпуск ключа AI Gateway

Чтобы перевыпустить ключ AI Gateway, отправьте PUT-запрос на &#x60;/api/v1/cloud-ai/ai-keys/{key_id}/regenerate&#x60;.  Ключ получает новое значение, старое перестаёт действовать сразу. Название, лимиты, запасные модели и статистика сохраняются. Новое значение (&#x60;sk-...&#x60;) возвращается в поле &#x60;key&#x60; только в ответе на этот запрос — сохраните его. Поле &#x60;expires_at&#x60; в ответе не заполняется.  Ключи, которыми управляет платформа (&#x60;is_readonly: true&#x60;), перевыпустить нельзя: вернётся 403 с &#x60;error_code&#x60; &#x60;ai_key_is_readonly&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.AiGatewayApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    AiGatewayApi apiInstance = new AiGatewayApi(defaultClient);
    UUID keyId = UUID.fromString("550e8400-e29b-41d4-a716-446655440000"); // UUID | ID ключа AI Gateway (UUID)
    try {
      CreateAiKey201Response result = apiInstance.regenerateAiKey(keyId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AiGatewayApi#regenerateAiKey");
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
| **keyId** | **UUID**| ID ключа AI Gateway (UUID) | |

### Return type

[**CreateAiKey201Response**](CreateAiKey201Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Объект JSON c ключами &#x60;ai_key&#x60; и &#x60;key&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="syncAiKeyLimits"></a>
# **syncAiKeyLimits**
> GetAiKeyLimits200Response syncAiKeyLimits(keyId, syncAiKeyLimits)

Синхронизация лимитов ключа AI Gateway

Чтобы задать все лимиты ключа AI Gateway на один период одним запросом, отправьте PUT-запрос на &#x60;/api/v1/cloud-ai/ai-keys/{key_id}/entity-limits/sync&#x60;.  Лимиты из &#x60;limits&#x60; создаются или обновляются (по &#x60;model_id&#x60;), а лимиты того же периода, которых нет в запросе, удаляются. Изменения применяются целиком или не применяются совсем.  Если в записи не задан ни &#x60;limit&#x60;, ни &#x60;money_limit&#x60; или &#x60;money_limit&#x60; снимается без &#x60;limit&#x60;, вернётся 400 с &#x60;error_code&#x60; &#x60;entity_keys_limit_value_required&#x60;. Если лимиты одновременно изменил другой запрос, вернётся 409 с &#x60;error_code&#x60; &#x60;entity_keys_limits_sync_conflict&#x60; — повторите запрос.  Тело ответа будет представлять собой объект JSON с ключами &#x60;ai_key_entity_limits&#x60; и &#x60;meta&#x60; — лимиты периода после синхронизации.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.AiGatewayApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    AiGatewayApi apiInstance = new AiGatewayApi(defaultClient);
    UUID keyId = UUID.fromString("550e8400-e29b-41d4-a716-446655440000"); // UUID | ID ключа AI Gateway (UUID)
    SyncAiKeyLimits syncAiKeyLimits = new SyncAiKeyLimits(); // SyncAiKeyLimits | 
    try {
      GetAiKeyLimits200Response result = apiInstance.syncAiKeyLimits(keyId, syncAiKeyLimits);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AiGatewayApi#syncAiKeyLimits");
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
| **keyId** | **UUID**| ID ключа AI Gateway (UUID) | |
| **syncAiKeyLimits** | [**SyncAiKeyLimits**](SyncAiKeyLimits.md)|  | |

### Return type

[**GetAiKeyLimits200Response**](GetAiKeyLimits200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Объект JSON c ключами &#x60;ai_key_entity_limits&#x60; и &#x60;meta&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **409** | Конфликт |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="updateAiKey"></a>
# **updateAiKey**
> UpdateAiKey200Response updateAiKey(keyId, updateAiKey)

Изменение ключа AI Gateway

Чтобы изменить ключ AI Gateway, отправьте PATCH-запрос на &#x60;/api/v1/cloud-ai/ai-keys/{key_id}&#x60;, передав изменяемые поля.  Ключи, которыми управляет платформа (&#x60;is_readonly: true&#x60; — ключи AI-решений и кластеров Kubernetes), изменить нельзя: вернётся 403 с &#x60;error_code&#x60; &#x60;ai_key_is_readonly&#x60;.  Ошибки в &#x60;fallback_models&#x60; — как при создании ключа (400). Поле &#x60;expires_at&#x60; в ответе этого запроса не заполняется — срок действия ключа смотрите в списке ключей.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.AiGatewayApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    AiGatewayApi apiInstance = new AiGatewayApi(defaultClient);
    UUID keyId = UUID.fromString("550e8400-e29b-41d4-a716-446655440000"); // UUID | ID ключа AI Gateway (UUID)
    UpdateAiKey updateAiKey = new UpdateAiKey(); // UpdateAiKey | 
    try {
      UpdateAiKey200Response result = apiInstance.updateAiKey(keyId, updateAiKey);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AiGatewayApi#updateAiKey");
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
| **keyId** | **UUID**| ID ключа AI Gateway (UUID) | |
| **updateAiKey** | [**UpdateAiKey**](UpdateAiKey.md)|  | |

### Return type

[**UpdateAiKey200Response**](UpdateAiKey200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Объект JSON c ключом &#x60;ai_key&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="updateAiKeyLimit"></a>
# **updateAiKeyLimit**
> CreateAiKeyLimit201Response updateAiKeyLimit(keyId, limitId, updateAiKeyLimit)

Изменение лимита ключа AI Gateway

Чтобы изменить лимит ключа AI Gateway, отправьте PATCH-запрос на &#x60;/api/v1/cloud-ai/ai-keys/{key_id}/entity-limits/{limit_id}&#x60;.  Не переданные поля не меняются. Если не передан ни &#x60;limit&#x60;, ни &#x60;money_limit&#x60; или &#x60;money_limit&#x60; снимается без &#x60;limit&#x60;, вернётся 400 с &#x60;error_code&#x60; &#x60;entity_keys_limit_value_required&#x60;. Если лимит относится к другому ключу, вернётся 403.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.AiGatewayApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    AiGatewayApi apiInstance = new AiGatewayApi(defaultClient);
    UUID keyId = UUID.fromString("550e8400-e29b-41d4-a716-446655440000"); // UUID | ID ключа AI Gateway (UUID)
    UUID limitId = UUID.fromString("0f6b7d8e-1c2a-4b3d-9e8f-7a6b5c4d3e2f"); // UUID | ID лимита (UUID)
    UpdateAiKeyLimit updateAiKeyLimit = new UpdateAiKeyLimit(); // UpdateAiKeyLimit | 
    try {
      CreateAiKeyLimit201Response result = apiInstance.updateAiKeyLimit(keyId, limitId, updateAiKeyLimit);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AiGatewayApi#updateAiKeyLimit");
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
| **keyId** | **UUID**| ID ключа AI Gateway (UUID) | |
| **limitId** | **UUID**| ID лимита (UUID) | |
| **updateAiKeyLimit** | [**UpdateAiKeyLimit**](UpdateAiKeyLimit.md)|  | |

### Return type

[**CreateAiKeyLimit201Response**](CreateAiKeyLimit201Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Объект JSON c ключом &#x60;ai_key_entity_limit&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

