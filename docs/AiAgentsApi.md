# AiAgentsApi

All URIs are relative to *https://api.timeweb.cloud*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**addAdditionalTokenPackage**](AiAgentsApi.md#addAdditionalTokenPackage) | **POST** /api/v1/cloud-ai/agents/{id}/add-additional-token-package | Добавление дополнительного пакета токенов |
| [**changeAgentModel**](AiAgentsApi.md#changeAgentModel) | **PATCH** /api/v1/cloud-ai/agents/{id}/model | Смена модели AI агента |
| [**createAgent**](AiAgentsApi.md#createAgent) | **POST** /api/v1/cloud-ai/agents | Создание AI агента |
| [**createAgentEntityLimit**](AiAgentsApi.md#createAgentEntityLimit) | **POST** /api/v1/cloud-ai/agents/{id}/entity-limits | Создание лимита агента |
| [**createAgentV2**](AiAgentsApi.md#createAgentV2) | **POST** /api/v2/cloud-ai/agents | Создание AI агента |
| [**deleteAgent**](AiAgentsApi.md#deleteAgent) | **DELETE** /api/v1/cloud-ai/agents/{id} | Удаление AI агента |
| [**deleteAgentEntityLimit**](AiAgentsApi.md#deleteAgentEntityLimit) | **DELETE** /api/v1/cloud-ai/agents/{id}/entity-limits/{limit_id} | Удаление лимита агента |
| [**exportAgentChatsCsv**](AiAgentsApi.md#exportAgentChatsCsv) | **GET** /api/v1/cloud-ai/agents/{id}/chats/docs | Выгрузка чатов агента в CSV |
| [**getAgent**](AiAgentsApi.md#getAgent) | **GET** /api/v1/cloud-ai/agents/{id} | Получение AI агента |
| [**getAgentChatList**](AiAgentsApi.md#getAgentChatList) | **GET** /api/v1/cloud-ai/agents/{id}/chat-list | Получение списка чатов агента |
| [**getAgentChatMessages**](AiAgentsApi.md#getAgentChatMessages) | **GET** /api/v1/cloud-ai/agents/{id}/chat-list/{chat_id}/messages | Получение сообщений чата агента |
| [**getAgentEntityLimit**](AiAgentsApi.md#getAgentEntityLimit) | **GET** /api/v1/cloud-ai/agents/{id}/entity-limits/{limit_id} | Получение лимита агента |
| [**getAgentEntityLimits**](AiAgentsApi.md#getAgentEntityLimits) | **GET** /api/v1/cloud-ai/agents/{id}/entity-limits | Получение лимитов агента |
| [**getAgentStatistics**](AiAgentsApi.md#getAgentStatistics) | **GET** /api/v1/cloud-ai/agents/{id}/statistic | Получение статистики использования токенов агента |
| [**getAgentV3**](AiAgentsApi.md#getAgentV3) | **GET** /api/v3/cloud-ai/agents/{id} | Получение AI агента (v3) |
| [**getAgents**](AiAgentsApi.md#getAgents) | **GET** /api/v1/cloud-ai/agents | Получение списка AI агентов |
| [**getAgentsV3**](AiAgentsApi.md#getAgentsV3) | **GET** /api/v3/cloud-ai/agents | Получение списка AI агентов (v3) |
| [**syncAgentEntityLimits**](AiAgentsApi.md#syncAgentEntityLimits) | **PUT** /api/v1/cloud-ai/agents/{id}/entity-limits/sync | Синхронизация лимитов агента за период |
| [**updateAgent**](AiAgentsApi.md#updateAgent) | **PATCH** /api/v1/cloud-ai/agents/{id} | Обновление AI агента |
| [**updateAgentEntityLimit**](AiAgentsApi.md#updateAgentEntityLimit) | **PATCH** /api/v1/cloud-ai/agents/{id}/entity-limits/{limit_id} | Изменение лимита агента |


<a id="addAdditionalTokenPackage"></a>
# **addAdditionalTokenPackage**
> addAdditionalTokenPackage(id, addTokenPackage)

Добавление дополнительного пакета токенов

Чтобы докупить дополнительные пакеты токенов для AI агента, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/agents/{id}/add-additional-token-package&#x60;.  Метод относится к устаревшей схеме оплаты пакетами токенов и работает только для агентов с пакетом (&#x60;token_package_id&#x60; в ответе агента). Для агентов с поресурсной оплатой вернётся 400 &#x60;per-resource_agents_do_not_support_additional_token_packages&#x60;. Стоимость (цена дополнительного пакета модели агента × &#x60;count&#x60;) сразу списывается с баланса; при нехватке средств — 400 &#x60;insufficient_balance&#x60;, если подписка агента истекла — 400 &#x60;subscription_expired&#x60;. После покупки заблокированный агент разблокируется.  Для токенов дополнительных пользователей аккаунта нужна роль &#x60;ai:write&#x60;.

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

<a id="changeAgentModel"></a>
# **changeAgentModel**
> CreateAgent201Response changeAgentModel(id, changeAgentModel)

Смена модели AI агента

Чтобы сменить основную модель AI агента, отправьте PATCH-запрос на &#x60;/api/v1/cloud-ai/agents/{id}/model&#x60;.  Параметры модели из &#x60;settings.model&#x60;, которые новая модель не поддерживает, отбрасываются. Смена модели доступна только агентам с поресурсной оплатой (для агента с пакетом токенов — 400 &#x60;model_change_is_allowed_only_for_per-resource_account_and_agent&#x60;). Если модель не найдена или недоступна — 404 &#x60;model_not_found&#x60;.  Для токенов дополнительных пользователей аккаунта нужна роль &#x60;ai:write&#x60;.

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
    ChangeAgentModel changeAgentModel = new ChangeAgentModel(); // ChangeAgentModel | 
    try {
      CreateAgent201Response result = apiInstance.changeAgentModel(id, changeAgentModel);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AiAgentsApi#changeAgentModel");
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
| **changeAgentModel** | [**ChangeAgentModel**](ChangeAgentModel.md)|  | |

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

<a id="createAgent"></a>
# **createAgent**
> CreateAgent201Response createAgent(createAgent)

Создание AI агента

Чтобы создать AI агента, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/agents&#x60;, задав необходимые атрибуты.  Метод устарел — используйте &#x60;POST /api/v2/cloud-ai/agents&#x60;. Поле &#x60;token_package_id&#x60; обязательно, но не учитывается: агент всегда создаётся с поресурсной оплатой (оплачиваются фактические запросы к модели).  Для токенов дополнительных пользователей аккаунта нужна роль &#x60;ai:write&#x60;.

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
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="createAgentEntityLimit"></a>
# **createAgentEntityLimit**
> CreateAgentEntityLimit201Response createAgentEntityLimit(id, createAgentEntityLimit)

Создание лимита агента

Чтобы создать лимит AI агента, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/agents/{id}/entity-limits&#x60;.  Лимиты агента ограничивают расход на его запросы за период (&#x60;hour&#x60;, &#x60;day&#x60;, &#x60;week&#x60;, &#x60;month&#x60;) — в токенах (&#x60;limit&#x60;) или в деньгах (&#x60;money_limit&#x60;, приоритетнее). Лимит действует на все модели агента (&#x60;model_id&#x60; &#x3D; &#x60;null&#x60;) или на одну модель. При достижении лимита запросы к агенту отклоняются до конца периода.  Нужно передать &#x60;limit&#x60; или &#x60;money_limit&#x60; (иначе — 400 &#x60;entity_keys_limit_value_required&#x60;). Если лимит с тем же периодом и моделью уже есть — 409 &#x60;entity_keys_limit_already_exists&#x60; (измените его или используйте синхронизацию).  Для токенов дополнительных пользователей аккаунта нужна роль &#x60;ai:write&#x60;.

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
    CreateAgentEntityLimit createAgentEntityLimit = new CreateAgentEntityLimit(); // CreateAgentEntityLimit | 
    try {
      CreateAgentEntityLimit201Response result = apiInstance.createAgentEntityLimit(id, createAgentEntityLimit);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AiAgentsApi#createAgentEntityLimit");
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
| **createAgentEntityLimit** | [**CreateAgentEntityLimit**](CreateAgentEntityLimit.md)|  | |

### Return type

[**CreateAgentEntityLimit201Response**](CreateAgentEntityLimit201Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Объект JSON c ключом &#x60;agent_entity_limit&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **409** | Конфликт |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="createAgentV2"></a>
# **createAgentV2**
> CreateAgent201Response createAgentV2(createAgentV2)

Создание AI агента

Чтобы создать AI агента, отправьте POST-запрос на &#x60;/api/v2/cloud-ai/agents&#x60;, задав необходимые атрибуты.  Агент создаётся с поресурсной оплатой: оплачиваются фактические запросы к модели. Если не переданы настройки виджета, подставляются значения по умолчанию. Дневной лимит расходов можно задать сразу полем &#x60;money_limit&#x60;, остальные лимиты — через лимиты агента (&#x60;/api/v1/cloud-ai/agents/{id}/entity-limits&#x60;).  Если модель не найдена или недоступна, вернётся 404 &#x60;model_not_found&#x60;; если модель не поддерживается — 400 &#x60;model_not_supported&#x60;; недопустимые параметры модели в &#x60;settings.model&#x60; — 400.  Вызывать созданного агента из своих приложений можно через OpenAI-совместимое API по его &#x60;access_id&#x60; — см. [документацию](https://agent.timeweb.cloud/docs).  Для токенов дополнительных пользователей аккаунта нужна роль &#x60;ai:write&#x60;.

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
    CreateAgentV2 createAgentV2 = new CreateAgentV2(); // CreateAgentV2 | 
    try {
      CreateAgent201Response result = apiInstance.createAgentV2(createAgentV2);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AiAgentsApi#createAgentV2");
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
| **createAgentV2** | [**CreateAgentV2**](CreateAgentV2.md)|  | |

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
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="deleteAgent"></a>
# **deleteAgent**
> deleteAgent(id, deleteAgent)

Удаление AI агента

Чтобы удалить AI агента, отправьте DELETE-запрос на &#x60;/api/v1/cloud-ai/agents/{id}&#x60;.  Вместе с агентом отключаются его базы знаний, навыки и интеграции (Telegram, MAX, Jivo, Crisp). В теле запроса можно необязательно указать причину удаления.  Для токенов дополнительных пользователей аккаунта нужна роль &#x60;ai:write&#x60;.

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
    DeleteAgent deleteAgent = new DeleteAgent(); // DeleteAgent | 
    try {
      apiInstance.deleteAgent(id, deleteAgent);
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
| **deleteAgent** | [**DeleteAgent**](DeleteAgent.md)|  | [optional] |

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
| **204** | AI агент успешно удален |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="deleteAgentEntityLimit"></a>
# **deleteAgentEntityLimit**
> deleteAgentEntityLimit(id, limitId)

Удаление лимита агента

Чтобы удалить лимит AI агента, отправьте DELETE-запрос на &#x60;/api/v1/cloud-ai/agents/{id}/entity-limits/{limit_id}&#x60;.  Для токенов дополнительных пользователей аккаунта нужна роль &#x60;ai:write&#x60;.

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
    UUID limitId = UUID.fromString("550e8400-e29b-41d4-a716-446655440000"); // UUID | ID лимита агента
    try {
      apiInstance.deleteAgentEntityLimit(id, limitId);
    } catch (ApiException e) {
      System.err.println("Exception when calling AiAgentsApi#deleteAgentEntityLimit");
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
| **limitId** | **UUID**| ID лимита агента | |

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

<a id="exportAgentChatsCsv"></a>
# **exportAgentChatsCsv**
> ExportAgentChatsCsv200Response exportAgentChatsCsv(id, offset, limit, order, from, to, locale)

Выгрузка чатов агента в CSV

Чтобы выгрузить чаты AI агента в формате CSV, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/agents/{id}/chats/docs&#x60;.  CSV возвращается строкой в поле &#x60;chats_csv.csv&#x60; тела JSON. &#x60;limit&#x60; и &#x60;offset&#x60; применяются к сообщениям, а не к чатам. Если передан &#x60;from&#x60;, выгружаются сообщения от &#x60;from&#x60; до &#x60;to&#x60; (по умолчанию — до текущего момента). Чаты внешнего чата не выгружаются.  Для токенов дополнительных пользователей аккаунта нужна роль &#x60;ai:read&#x60;.

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
    Integer offset = 0; // Integer | Смещение относительно начала списка
    Integer limit = 100; // Integer | Количество сообщений в ответе
    String order = "asc"; // String | Сортировка по дате сообщения
    OffsetDateTime from = OffsetDateTime.parse("2026-10-01T00:00Z"); // OffsetDateTime | Начало периода (дата и время в формате ISO 8601)
    OffsetDateTime to = OffsetDateTime.parse("2026-10-20T23:59:59.999Z"); // OffsetDateTime | Конец периода (дата и время в формате ISO 8601)
    String locale = "ru"; // String | Язык заголовков и значений CSV
    try {
      ExportAgentChatsCsv200Response result = apiInstance.exportAgentChatsCsv(id, offset, limit, order, from, to, locale);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AiAgentsApi#exportAgentChatsCsv");
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
| **offset** | **Integer**| Смещение относительно начала списка | [optional] [default to 0] |
| **limit** | **Integer**| Количество сообщений в ответе | [optional] [default to 100] |
| **order** | **String**| Сортировка по дате сообщения | [optional] [default to desc] [enum: asc, desc] |
| **from** | **OffsetDateTime**| Начало периода (дата и время в формате ISO 8601) | [optional] |
| **to** | **OffsetDateTime**| Конец периода (дата и время в формате ISO 8601) | [optional] |
| **locale** | **String**| Язык заголовков и значений CSV | [optional] [default to ru] [enum: ru, en] |

### Return type

[**ExportAgentChatsCsv200Response**](ExportAgentChatsCsv200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Объект JSON c ключом &#x60;chats_csv&#x60; |  -  |
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

Чтобы получить информацию об AI агенте, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/agents/{id}&#x60;.  Для токенов дополнительных пользователей аккаунта нужна роль &#x60;ai:read&#x60;.

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

<a id="getAgentChatList"></a>
# **getAgentChatList**
> GetAgentChatList200Response getAgentChatList(id, offset, limit, order, from, to, reactionType, responseTimeRanges)

Получение списка чатов агента

Чтобы получить список чатов (диалогов) AI агента, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/agents/{id}/chat-list&#x60;.  Для каждого чата возвращаются первое сообщение пользователя, количество оценок и среднее время ответа. Чаты внешнего чата сюда не входят. Фильтр по периоду применяется, только если переданы оба параметра &#x60;from&#x60; и &#x60;to&#x60;. В &#x60;meta.metrics&#x60; — сводка по чатам для фильтров.  Для токенов дополнительных пользователей аккаунта нужна роль &#x60;ai:read&#x60;.

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
    Integer offset = 0; // Integer | Смещение относительно начала списка
    Integer limit = 100; // Integer | Количество чатов в ответе
    String order = "asc"; // String | Сортировка по дате первого сообщения
    OffsetDateTime from = OffsetDateTime.parse("2026-10-01T00:00Z"); // OffsetDateTime | Начало периода (дата и время в формате ISO 8601)
    OffsetDateTime to = OffsetDateTime.parse("2026-10-20T23:59:59.999Z"); // OffsetDateTime | Конец периода (дата и время в формате ISO 8601)
    String reactionType = "likes"; // String | Фильтр по оценкам: `likes` — есть положительная, `dislikes` — есть отрицательная, `with` — есть любая, `none` — без оценок
    List<String> responseTimeRanges = Arrays.asList(); // List<String> | Фильтр по среднему времени ответа в секундах: диапазоны `мин-макс` или `мин-` («от мин и больше»). Можно передать несколько диапазонов — они объединяются по ИЛИ.
    try {
      GetAgentChatList200Response result = apiInstance.getAgentChatList(id, offset, limit, order, from, to, reactionType, responseTimeRanges);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AiAgentsApi#getAgentChatList");
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
| **offset** | **Integer**| Смещение относительно начала списка | [optional] [default to 0] |
| **limit** | **Integer**| Количество чатов в ответе | [optional] [default to 100] |
| **order** | **String**| Сортировка по дате первого сообщения | [optional] [default to asc] [enum: asc, desc] |
| **from** | **OffsetDateTime**| Начало периода (дата и время в формате ISO 8601) | [optional] |
| **to** | **OffsetDateTime**| Конец периода (дата и время в формате ISO 8601) | [optional] |
| **reactionType** | **String**| Фильтр по оценкам: &#x60;likes&#x60; — есть положительная, &#x60;dislikes&#x60; — есть отрицательная, &#x60;with&#x60; — есть любая, &#x60;none&#x60; — без оценок | [optional] [enum: likes, dislikes, with, none] |
| **responseTimeRanges** | [**List&lt;String&gt;**](String.md)| Фильтр по среднему времени ответа в секундах: диапазоны &#x60;мин-макс&#x60; или &#x60;мин-&#x60; («от мин и больше»). Можно передать несколько диапазонов — они объединяются по ИЛИ. | [optional] |

### Return type

[**GetAgentChatList200Response**](GetAgentChatList200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Объект JSON c ключами &#x60;chat_list&#x60; и &#x60;meta&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getAgentChatMessages"></a>
# **getAgentChatMessages**
> GetAgentChatMessages200Response getAgentChatMessages(id, chatId, offset, limit, order, from, to)

Получение сообщений чата агента

Чтобы получить сообщения чата AI агента, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/agents/{id}/chat-list/{chat_id}/messages&#x60;.  Возвращаются сообщения пользователя и ответы агента. Фильтр по периоду применяется, только если переданы оба параметра &#x60;from&#x60; и &#x60;to&#x60;.  Для токенов дополнительных пользователей аккаунта нужна роль &#x60;ai:read&#x60;.

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
    String chatId = "ai-agent-1-0b1c2d3e-4f50-4a6b-8c7d-9e0f1a2b3c4d"; // String | ID чата (диалога) из поля `id` списка чатов агента
    Integer offset = 0; // Integer | Смещение относительно начала списка
    Integer limit = 100; // Integer | Количество сообщений в ответе
    String order = "asc"; // String | Сортировка по дате сообщения
    OffsetDateTime from = OffsetDateTime.parse("2026-10-01T00:00Z"); // OffsetDateTime | Начало периода (дата и время в формате ISO 8601)
    OffsetDateTime to = OffsetDateTime.parse("2026-10-20T23:59:59.999Z"); // OffsetDateTime | Конец периода (дата и время в формате ISO 8601)
    try {
      GetAgentChatMessages200Response result = apiInstance.getAgentChatMessages(id, chatId, offset, limit, order, from, to);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AiAgentsApi#getAgentChatMessages");
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
| **chatId** | **String**| ID чата (диалога) из поля &#x60;id&#x60; списка чатов агента | |
| **offset** | **Integer**| Смещение относительно начала списка | [optional] [default to 0] |
| **limit** | **Integer**| Количество сообщений в ответе | [optional] [default to 100] |
| **order** | **String**| Сортировка по дате сообщения | [optional] [default to desc] [enum: asc, desc] |
| **from** | **OffsetDateTime**| Начало периода (дата и время в формате ISO 8601) | [optional] |
| **to** | **OffsetDateTime**| Конец периода (дата и время в формате ISO 8601) | [optional] |

### Return type

[**GetAgentChatMessages200Response**](GetAgentChatMessages200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Объект JSON c ключами &#x60;chat_list_messages&#x60; и &#x60;meta&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getAgentEntityLimit"></a>
# **getAgentEntityLimit**
> CreateAgentEntityLimit201Response getAgentEntityLimit(id, limitId)

Получение лимита агента

Чтобы получить лимит AI агента, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/agents/{id}/entity-limits/{limit_id}&#x60;.  Для токенов дополнительных пользователей аккаунта нужна роль &#x60;ai:read&#x60;.

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
    UUID limitId = UUID.fromString("550e8400-e29b-41d4-a716-446655440000"); // UUID | ID лимита агента
    try {
      CreateAgentEntityLimit201Response result = apiInstance.getAgentEntityLimit(id, limitId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AiAgentsApi#getAgentEntityLimit");
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
| **limitId** | **UUID**| ID лимита агента | |

### Return type

[**CreateAgentEntityLimit201Response**](CreateAgentEntityLimit201Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Объект JSON c ключом &#x60;agent_entity_limit&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getAgentEntityLimits"></a>
# **getAgentEntityLimits**
> GetAgentEntityLimits200Response getAgentEntityLimits(id, limitType)

Получение лимитов агента

Чтобы получить лимиты AI агента, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/agents/{id}/entity-limits&#x60;.  Лимиты агента ограничивают расход на его запросы за период (&#x60;hour&#x60;, &#x60;day&#x60;, &#x60;week&#x60;, &#x60;month&#x60;) — в токенах (&#x60;limit&#x60;) или в деньгах (&#x60;money_limit&#x60;, приоритетнее). Лимит действует на все модели агента (&#x60;model_id&#x60; &#x3D; &#x60;null&#x60;) или на одну модель. При достижении лимита запросы к агенту отклоняются до конца периода.  Для токенов дополнительных пользователей аккаунта нужна роль &#x60;ai:read&#x60;.

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
    String limitType = "hour"; // String | Фильтр по периоду лимита
    try {
      GetAgentEntityLimits200Response result = apiInstance.getAgentEntityLimits(id, limitType);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AiAgentsApi#getAgentEntityLimits");
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
| **limitType** | **String**| Фильтр по периоду лимита | [optional] [enum: hour, day, week, month] |

### Return type

[**GetAgentEntityLimits200Response**](GetAgentEntityLimits200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Объект JSON c ключами &#x60;agent_entity_limits&#x60; и &#x60;meta&#x60; |  -  |
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

Чтобы получить статистику использования токенов AI агента, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/agents/{id}/statistic&#x60;.  Статистика разбита на интервалы длиной &#x60;interval&#x60; минут: для каждого интервала возвращается по точке на каждую модель, к которой были запросы за период, а для интервалов без запросов — одна нулевая точка без &#x60;model_name&#x60;. По умолчанию возвращаются последние 24 часа.  Для токенов дополнительных пользователей аккаунта нужна роль &#x60;ai:read&#x60;.

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
    OffsetDateTime startTime = OffsetDateTime.parse("2026-10-19T00:00Z"); // OffsetDateTime | Начало периода (ISO 8601). По умолчанию — за 24 часа до `endTime`.
    OffsetDateTime endTime = OffsetDateTime.parse("2026-10-20T00:00Z"); // OffsetDateTime | Конец периода (ISO 8601). По умолчанию — текущий момент.
    Integer interval = 60; // Integer | Длина интервала агрегации в минутах
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
| **startTime** | **OffsetDateTime**| Начало периода (ISO 8601). По умолчанию — за 24 часа до &#x60;endTime&#x60;. | [optional] |
| **endTime** | **OffsetDateTime**| Конец периода (ISO 8601). По умолчанию — текущий момент. | [optional] |
| **interval** | **Integer**| Длина интервала агрегации в минутах | [optional] [default to 60] |

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

<a id="getAgentV3"></a>
# **getAgentV3**
> GetAgentV3200Response getAgentV3(id)

Получение AI агента (v3)

Чтобы получить информацию об AI агенте, отправьте GET-запрос на &#x60;/api/v3/cloud-ai/agents/{id}&#x60;.  Отличается от &#x60;/api/v1/cloud-ai/agents/{id}&#x60; тем, что вместо &#x60;knowledge_bases_ids&#x60; возвращает &#x60;knowledge_bases&#x60; — подключённые базы знаний с режимом поиска.  Для токенов дополнительных пользователей аккаунта нужна роль &#x60;ai:read&#x60;.

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
      GetAgentV3200Response result = apiInstance.getAgentV3(id);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AiAgentsApi#getAgentV3");
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

[**GetAgentV3200Response**](GetAgentV3200Response.md)

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

<a id="getAgents"></a>
# **getAgents**
> GetAgents200Response getAgents()

Получение списка AI агентов

Чтобы получить список AI агентов, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/agents&#x60;.  Возвращаются все агенты аккаунта (без пагинации), включая приостановленных и заблокированных. Тело ответа будет представлять собой объект JSON с ключами &#x60;agents&#x60; и &#x60;meta&#x60;. Подключённые базы знаний вместе с режимом поиска возвращает метод &#x60;/api/v3/cloud-ai/agents&#x60;.  Для токенов дополнительных пользователей аккаунта нужна роль &#x60;ai:read&#x60;.

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
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getAgentsV3"></a>
# **getAgentsV3**
> GetAgentsV3200Response getAgentsV3()

Получение списка AI агентов (v3)

Чтобы получить список AI агентов, отправьте GET-запрос на &#x60;/api/v3/cloud-ai/agents&#x60;.  Отличается от &#x60;/api/v1/cloud-ai/agents&#x60; тем, что вместо &#x60;knowledge_bases_ids&#x60; возвращает &#x60;knowledge_bases&#x60; — подключённые базы знаний с режимом поиска.  Для токенов дополнительных пользователей аккаунта нужна роль &#x60;ai:read&#x60;.

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
      GetAgentsV3200Response result = apiInstance.getAgentsV3();
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AiAgentsApi#getAgentsV3");
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

[**GetAgentsV3200Response**](GetAgentsV3200Response.md)

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
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="syncAgentEntityLimits"></a>
# **syncAgentEntityLimits**
> GetAgentEntityLimits200Response syncAgentEntityLimits(id, syncAgentEntityLimits)

Синхронизация лимитов агента за период

Чтобы задать полный набор лимитов AI агента за период, отправьте PUT-запрос на &#x60;/api/v1/cloud-ai/agents/{id}/entity-limits/sync&#x60;.  Лимиты из запроса создаются или обновляются (сопоставление по &#x60;model_id&#x60;), а лимиты этого периода, которых нет в запросе, удаляются. Лимиты других периодов не затрагиваются. В ответе — актуальные лимиты периода. Если лимиты одновременно изменил другой запрос, вернётся 409 &#x60;entity_keys_limits_sync_conflict&#x60; — повторите запрос.  Для токенов дополнительных пользователей аккаунта нужна роль &#x60;ai:write&#x60;.

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
    SyncAgentEntityLimits syncAgentEntityLimits = new SyncAgentEntityLimits(); // SyncAgentEntityLimits | 
    try {
      GetAgentEntityLimits200Response result = apiInstance.syncAgentEntityLimits(id, syncAgentEntityLimits);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AiAgentsApi#syncAgentEntityLimits");
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
| **syncAgentEntityLimits** | [**SyncAgentEntityLimits**](SyncAgentEntityLimits.md)|  | |

### Return type

[**GetAgentEntityLimits200Response**](GetAgentEntityLimits200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Объект JSON c ключами &#x60;agent_entity_limits&#x60; и &#x60;meta&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **409** | Конфликт |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="updateAgent"></a>
# **updateAgent**
> CreateAgent201Response updateAgent(id, updateAgent)

Обновление AI агента

Чтобы обновить AI агента, отправьте PATCH-запрос на &#x60;/api/v1/cloud-ai/agents/{id}&#x60;, передав изменяемые поля.  Особенности: - &#x60;settings&#x60; заменяются целиком (кроме &#x60;web_search_allowed_sites&#x60;, &#x60;additional_models&#x60; и &#x60;memory_enabled&#x60;, которые сохраняются, если не переданы) — передавайте полный объект настроек, включая &#x60;widget&#x60;; - основную модель меняет отдельный метод &#x60;PATCH /api/v1/cloud-ai/agents/{id}/model&#x60;; - поле &#x60;token_limit&#x60; больше не поддерживается: запрос с ним отклоняется с кодом 410 — лимиты задаются через &#x60;/api/v1/cloud-ai/agents/{id}/entity-limits&#x60;; - ошибки привязки &#x60;ai_gateway_key_id&#x60;: 400 &#x60;ai_gateway_key_not_found&#x60;, &#x60;agent_is_already_migrated_to_another_ai_gateway_key&#x60;, &#x60;agent_is_not_eligible_for_ai_gateway_migration&#x60;; 503 — проверку временно не удалось выполнить; - изменить статус заблокированного или удалённого агента нельзя (400 &#x60;agent_can&#39;t_change_status&#x60;).  Для токенов дополнительных пользователей аккаунта нужна роль &#x60;ai:write&#x60;.

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
| **410** | Передано удалённое поле &#x60;token_limit&#x60; |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |
| **503** | Не удалось проверить возможность привязки &#x60;ai_gateway_key_id&#x60;, повторите запрос позже |  -  |

<a id="updateAgentEntityLimit"></a>
# **updateAgentEntityLimit**
> CreateAgentEntityLimit201Response updateAgentEntityLimit(id, limitId, updateAgentEntityLimit)

Изменение лимита агента

Чтобы изменить значение лимита AI агента, отправьте PATCH-запрос на &#x60;/api/v1/cloud-ai/agents/{id}/entity-limits/{limit_id}&#x60;.  Непереданные поля не меняются. Снять денежный лимит (&#x60;money_limit&#x60;: &#x60;null&#x60;) можно только вместе с передачей &#x60;limit&#x60;.  Для токенов дополнительных пользователей аккаунта нужна роль &#x60;ai:write&#x60;.

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
    UUID limitId = UUID.fromString("550e8400-e29b-41d4-a716-446655440000"); // UUID | ID лимита агента
    UpdateAgentEntityLimit updateAgentEntityLimit = new UpdateAgentEntityLimit(); // UpdateAgentEntityLimit | 
    try {
      CreateAgentEntityLimit201Response result = apiInstance.updateAgentEntityLimit(id, limitId, updateAgentEntityLimit);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AiAgentsApi#updateAgentEntityLimit");
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
| **limitId** | **UUID**| ID лимита агента | |
| **updateAgentEntityLimit** | [**UpdateAgentEntityLimit**](UpdateAgentEntityLimit.md)|  | |

### Return type

[**CreateAgentEntityLimit201Response**](CreateAgentEntityLimit201Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Объект JSON c ключом &#x60;agent_entity_limit&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

