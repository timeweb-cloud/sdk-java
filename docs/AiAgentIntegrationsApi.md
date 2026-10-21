# AiAgentIntegrationsApi

All URIs are relative to *https://api.timeweb.cloud*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**createAgentBotMaxBot**](AiAgentIntegrationsApi.md#createAgentBotMaxBot) | **POST** /api/v1/cloud-ai/max-bots | Подключение бота MAX к агенту |
| [**createAgentBotTelegramBot**](AiAgentIntegrationsApi.md#createAgentBotTelegramBot) | **POST** /api/v1/cloud-ai/bots | Подключение Telegram-бота к агенту |
| [**createAgentIntegrationCrisp**](AiAgentIntegrationsApi.md#createAgentIntegrationCrisp) | **POST** /api/v1/cloud-ai/agent/{agent_id}/crisp | Подключение агента к Crisp |
| [**createAgentIntegrationJivo**](AiAgentIntegrationsApi.md#createAgentIntegrationJivo) | **POST** /api/v1/cloud-ai/agent/{agent_id}/jivo | Подключение агента к Jivo |
| [**deleteAgentBotMaxBot**](AiAgentIntegrationsApi.md#deleteAgentBotMaxBot) | **DELETE** /api/v1/cloud-ai/max-bots/{bot_id} | Отключение бота MAX |
| [**deleteAgentBotTelegramBot**](AiAgentIntegrationsApi.md#deleteAgentBotTelegramBot) | **DELETE** /api/v1/cloud-ai/bots/{bot_id} | Отключение Telegram-бота |
| [**deleteAgentIntegrationCrisp**](AiAgentIntegrationsApi.md#deleteAgentIntegrationCrisp) | **DELETE** /api/v1/cloud-ai/agent/{agent_id}/crisp | Отключение агента от Crisp |
| [**deleteAgentIntegrationJivo**](AiAgentIntegrationsApi.md#deleteAgentIntegrationJivo) | **DELETE** /api/v1/cloud-ai/agent/{agent_id}/jivo | Отключение агента от Jivo |
| [**getAgentBotMaxBot**](AiAgentIntegrationsApi.md#getAgentBotMaxBot) | **GET** /api/v1/cloud-ai/max-bots/{bot_id} | Получение информации о боте MAX |
| [**getAgentBotMaxBots**](AiAgentIntegrationsApi.md#getAgentBotMaxBots) | **GET** /api/v1/cloud-ai/max-bots | Получение списка ботов MAX |
| [**getAgentBotMaxBotsByAgent**](AiAgentIntegrationsApi.md#getAgentBotMaxBotsByAgent) | **GET** /api/v1/cloud-ai/agents/{agent_id}/max-bots | Получение списка ботов MAX агента |
| [**getAgentBotTelegramBot**](AiAgentIntegrationsApi.md#getAgentBotTelegramBot) | **GET** /api/v1/cloud-ai/bots/{bot_id} | Получение информации о Telegram-боте |
| [**getAgentBotTelegramBots**](AiAgentIntegrationsApi.md#getAgentBotTelegramBots) | **GET** /api/v1/cloud-ai/bots | Получение списка Telegram-ботов |
| [**getAgentBotTelegramBotsByAgent**](AiAgentIntegrationsApi.md#getAgentBotTelegramBotsByAgent) | **GET** /api/v1/cloud-ai/agents/{agent_id}/bots | Получение списка Telegram-ботов агента |
| [**getAgentIntegrationCrisp**](AiAgentIntegrationsApi.md#getAgentIntegrationCrisp) | **GET** /api/v1/cloud-ai/agent/{agent_id}/crisp | Получение интеграции агента с Crisp |
| [**getAgentIntegrationJivo**](AiAgentIntegrationsApi.md#getAgentIntegrationJivo) | **GET** /api/v1/cloud-ai/agent/{agent_id}/jivo | Получение интеграции агента с Jivo |
| [**updateAgentBotMaxBot**](AiAgentIntegrationsApi.md#updateAgentBotMaxBot) | **PATCH** /api/v1/cloud-ai/max-bots/{bot_id} | Изменение бота MAX |
| [**updateAgentBotTelegramBot**](AiAgentIntegrationsApi.md#updateAgentBotTelegramBot) | **PATCH** /api/v1/cloud-ai/bots/{bot_id} | Изменение Telegram-бота |


<a id="createAgentBotMaxBot"></a>
# **createAgentBotMaxBot**
> CreateAgentBotTelegramBot201Response createAgentBotMaxBot(createMaxBot)

Подключение бота MAX к агенту

Чтобы подключить бота MAX к AI агенту, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/max-bots&#x60;, передав ID агента и токен бота от @MasterBot.  Платформа проверит токен в MAX, сохранит бота и сама подпишет его на события (вебхук) — настраивать подписку в MAX не нужно. После этого агент начинает отвечать пользователям бота.  Один бот может быть подключен только к одному агенту. Если бот уже подключен к агенту, который был удален, бот будет переподключен к новому агенту. Ограничение доступа по списку пользователей для ботов MAX не поддерживается.  Ошибки: - &#x60;400 invalid_max_token&#x60; — MAX не принял токен; - &#x60;400 max_webhook_error&#x60; — не удалось подписать бота на события; - &#x60;409 bot_already_exists&#x60; — бот уже подключен к существующему агенту или к другому аккаунту.  Тело ответа будет представлять собой объект JSON с ключом &#x60;bot&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.AiAgentIntegrationsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    AiAgentIntegrationsApi apiInstance = new AiAgentIntegrationsApi(defaultClient);
    CreateMaxBot createMaxBot = new CreateMaxBot(); // CreateMaxBot | 
    try {
      CreateAgentBotTelegramBot201Response result = apiInstance.createAgentBotMaxBot(createMaxBot);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AiAgentIntegrationsApi#createAgentBotMaxBot");
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
| **createMaxBot** | [**CreateMaxBot**](CreateMaxBot.md)|  | |

### Return type

[**CreateAgentBotTelegramBot201Response**](CreateAgentBotTelegramBot201Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Объект JSON c ключом &#x60;bot&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **409** | Конфликт |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="createAgentBotTelegramBot"></a>
# **createAgentBotTelegramBot**
> CreateAgentBotTelegramBot201Response createAgentBotTelegramBot(createBot)

Подключение Telegram-бота к агенту

Чтобы подключить Telegram-бота к AI агенту, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/bots&#x60;, передав ID агента и токен бота от @BotFather.  Платформа проверит токен в Telegram, сохранит бота и сама установит ему вебхук — настраивать вебхук в Telegram не нужно. После этого агент начинает отвечать пользователям бота. Ранее накопленные у бота обновления сбрасываются.  Один бот может быть подключен только к одному агенту. Если бот уже подключен к агенту, который был удален, бот будет переподключен к новому агенту.  Ошибки: - &#x60;400 invalid_telegram_token&#x60; — Telegram не принял токен; - &#x60;400 telegram_webhook_error&#x60; — не удалось установить вебхук; - &#x60;409 bot_already_exists&#x60; — бот уже подключен к существующему агенту или к другому аккаунту.  Тело ответа будет представлять собой объект JSON с ключом &#x60;bot&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.AiAgentIntegrationsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    AiAgentIntegrationsApi apiInstance = new AiAgentIntegrationsApi(defaultClient);
    CreateBot createBot = new CreateBot(); // CreateBot | 
    try {
      CreateAgentBotTelegramBot201Response result = apiInstance.createAgentBotTelegramBot(createBot);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AiAgentIntegrationsApi#createAgentBotTelegramBot");
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
| **createBot** | [**CreateBot**](CreateBot.md)|  | |

### Return type

[**CreateAgentBotTelegramBot201Response**](CreateAgentBotTelegramBot201Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Объект JSON c ключом &#x60;bot&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **409** | Конфликт |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="createAgentIntegrationCrisp"></a>
# **createAgentIntegrationCrisp**
> GetAgentIntegrationCrisp200Response createAgentIntegrationCrisp(agentId, createCrispIntegration)

Подключение агента к Crisp

Чтобы подключить AI агента к Crisp, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/agent/{agent_id}/crisp&#x60;, передав Website ID сайта и API-токен Crisp (Identifier и Key).  Платформа проверит токен в Crisp и вернет &#x60;webhook_secret&#x60;. Затем добавьте в Crisp вебхук &#x60;https://api.timeweb.cloud/api/v1/crisp/{website_id}/webhook/{webhook_secret}&#x60; — подробнее в разделе «Интеграции AI агентов».  К одному агенту можно подключить один сайт Crisp, и один сайт — только к одному агенту.  Ошибки: - &#x60;400 invalid_crisp_token&#x60; — Crisp не принял токен для этого сайта; - &#x60;409 bot_already_exists&#x60; — у агента уже есть интеграция с Crisp или сайт подключен к другому агенту; - &#x60;503&#x60; — интеграция с Crisp временно недоступна.  Тело ответа будет представлять собой объект JSON с ключом &#x60;crisp_integration&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.AiAgentIntegrationsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    AiAgentIntegrationsApi apiInstance = new AiAgentIntegrationsApi(defaultClient);
    Integer agentId = 1; // Integer | ID агента
    CreateCrispIntegration createCrispIntegration = new CreateCrispIntegration(); // CreateCrispIntegration | 
    try {
      GetAgentIntegrationCrisp200Response result = apiInstance.createAgentIntegrationCrisp(agentId, createCrispIntegration);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AiAgentIntegrationsApi#createAgentIntegrationCrisp");
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
| **agentId** | **Integer**| ID агента | |
| **createCrispIntegration** | [**CreateCrispIntegration**](CreateCrispIntegration.md)|  | |

### Return type

[**GetAgentIntegrationCrisp200Response**](GetAgentIntegrationCrisp200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Объект JSON c ключом &#x60;crisp_integration&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **409** | Конфликт |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |
| **503** | Интеграция с Crisp временно недоступна |  -  |

<a id="createAgentIntegrationJivo"></a>
# **createAgentIntegrationJivo**
> GetAgentIntegrationJivo200Response createAgentIntegrationJivo(agentId, createJivoIntegration)

Подключение агента к Jivo

Чтобы подключить AI агента к Jivo, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/agent/{agent_id}/jivo&#x60;, передав Provider ID, выданный поддержкой Jivo.  Подготовка описана в разделе «Интеграции AI агентов»: адрес для событий и токен бота нужно заранее передать в поддержку Jivo.  Если агент не найден, вернется ошибка &#x60;404 agent_not_found&#x60;.  К одному агенту можно подключить одну интеграцию Jivo. Если она уже есть, вернется ошибка &#x60;409 bot_already_exists&#x60;.  Тело ответа будет представлять собой объект JSON с ключом &#x60;jivo_integration&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.AiAgentIntegrationsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    AiAgentIntegrationsApi apiInstance = new AiAgentIntegrationsApi(defaultClient);
    Integer agentId = 1; // Integer | ID агента
    CreateJivoIntegration createJivoIntegration = new CreateJivoIntegration(); // CreateJivoIntegration | 
    try {
      GetAgentIntegrationJivo200Response result = apiInstance.createAgentIntegrationJivo(agentId, createJivoIntegration);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AiAgentIntegrationsApi#createAgentIntegrationJivo");
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
| **agentId** | **Integer**| ID агента | |
| **createJivoIntegration** | [**CreateJivoIntegration**](CreateJivoIntegration.md)|  | |

### Return type

[**GetAgentIntegrationJivo200Response**](GetAgentIntegrationJivo200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Объект JSON c ключом &#x60;jivo_integration&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **409** | Конфликт |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="deleteAgentBotMaxBot"></a>
# **deleteAgentBotMaxBot**
> deleteAgentBotMaxBot(botId)

Отключение бота MAX

Чтобы отключить бота MAX от агента, отправьте DELETE-запрос на &#x60;/api/v1/cloud-ai/max-bots/{bot_id}&#x60;.  Платформа отпишет бота от событий в MAX и удалит подключение. Сам бот в MAX не удаляется — его можно подключить снова.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.AiAgentIntegrationsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    AiAgentIntegrationsApi apiInstance = new AiAgentIntegrationsApi(defaultClient);
    UUID botId = UUID.fromString("550e8400-e29b-41d4-a716-446655440000"); // UUID | ID бота (поле `id` из ответа)
    try {
      apiInstance.deleteAgentBotMaxBot(botId);
    } catch (ApiException e) {
      System.err.println("Exception when calling AiAgentIntegrationsApi#deleteAgentBotMaxBot");
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
| **botId** | **UUID**| ID бота (поле &#x60;id&#x60; из ответа) | |

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
| **204** | Бот MAX отключен |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="deleteAgentBotTelegramBot"></a>
# **deleteAgentBotTelegramBot**
> deleteAgentBotTelegramBot(botId)

Отключение Telegram-бота

Чтобы отключить Telegram-бота от агента, отправьте DELETE-запрос на &#x60;/api/v1/cloud-ai/bots/{bot_id}&#x60;.  Платформа снимет вебхук бота в Telegram и удалит подключение. Сам бот в Telegram не удаляется — его можно подключить снова.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.AiAgentIntegrationsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    AiAgentIntegrationsApi apiInstance = new AiAgentIntegrationsApi(defaultClient);
    UUID botId = UUID.fromString("550e8400-e29b-41d4-a716-446655440000"); // UUID | ID бота (поле `id` из ответа)
    try {
      apiInstance.deleteAgentBotTelegramBot(botId);
    } catch (ApiException e) {
      System.err.println("Exception when calling AiAgentIntegrationsApi#deleteAgentBotTelegramBot");
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
| **botId** | **UUID**| ID бота (поле &#x60;id&#x60; из ответа) | |

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
| **204** | Telegram-бот отключен |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="deleteAgentIntegrationCrisp"></a>
# **deleteAgentIntegrationCrisp**
> deleteAgentIntegrationCrisp(agentId)

Отключение агента от Crisp

Чтобы отключить AI агента от Crisp, отправьте DELETE-запрос на &#x60;/api/v1/cloud-ai/agent/{agent_id}/crisp&#x60;.  После удаления интеграции агент перестает отвечать в чатах Crisp. Вебхук в настройках Crisp можно удалить вручную.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.AiAgentIntegrationsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    AiAgentIntegrationsApi apiInstance = new AiAgentIntegrationsApi(defaultClient);
    Integer agentId = 1; // Integer | ID агента
    try {
      apiInstance.deleteAgentIntegrationCrisp(agentId);
    } catch (ApiException e) {
      System.err.println("Exception when calling AiAgentIntegrationsApi#deleteAgentIntegrationCrisp");
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
| **agentId** | **Integer**| ID агента | |

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
| **204** | Интеграция с Crisp удалена |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="deleteAgentIntegrationJivo"></a>
# **deleteAgentIntegrationJivo**
> deleteAgentIntegrationJivo(agentId)

Отключение агента от Jivo

Чтобы отключить AI агента от Jivo, отправьте DELETE-запрос на &#x60;/api/v1/cloud-ai/agent/{agent_id}/jivo&#x60;.  После удаления интеграции агент перестает отвечать на события Jivo. Настройки бота в самом Jivo не меняются.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.AiAgentIntegrationsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    AiAgentIntegrationsApi apiInstance = new AiAgentIntegrationsApi(defaultClient);
    Integer agentId = 1; // Integer | ID агента
    try {
      apiInstance.deleteAgentIntegrationJivo(agentId);
    } catch (ApiException e) {
      System.err.println("Exception when calling AiAgentIntegrationsApi#deleteAgentIntegrationJivo");
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
| **agentId** | **Integer**| ID агента | |

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
| **204** | Интеграция с Jivo удалена |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getAgentBotMaxBot"></a>
# **getAgentBotMaxBot**
> CreateAgentBotTelegramBot201Response getAgentBotMaxBot(botId)

Получение информации о боте MAX

Чтобы получить информацию о боте MAX, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/max-bots/{bot_id}&#x60;.  Тело ответа будет представлять собой объект JSON с ключом &#x60;bot&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.AiAgentIntegrationsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    AiAgentIntegrationsApi apiInstance = new AiAgentIntegrationsApi(defaultClient);
    UUID botId = UUID.fromString("550e8400-e29b-41d4-a716-446655440000"); // UUID | ID бота (поле `id` из ответа)
    try {
      CreateAgentBotTelegramBot201Response result = apiInstance.getAgentBotMaxBot(botId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AiAgentIntegrationsApi#getAgentBotMaxBot");
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
| **botId** | **UUID**| ID бота (поле &#x60;id&#x60; из ответа) | |

### Return type

[**CreateAgentBotTelegramBot201Response**](CreateAgentBotTelegramBot201Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Объект JSON c ключом &#x60;bot&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getAgentBotMaxBots"></a>
# **getAgentBotMaxBots**
> GetAgentBotMaxBots200Response getAgentBotMaxBots()

Получение списка ботов MAX

Чтобы получить список ботов MAX, подключенных к агентам аккаунта, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/max-bots&#x60;.  Тело ответа будет представлять собой объект JSON с ключами &#x60;bots&#x60; и &#x60;meta&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.AiAgentIntegrationsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    AiAgentIntegrationsApi apiInstance = new AiAgentIntegrationsApi(defaultClient);
    try {
      GetAgentBotMaxBots200Response result = apiInstance.getAgentBotMaxBots();
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AiAgentIntegrationsApi#getAgentBotMaxBots");
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

[**GetAgentBotMaxBots200Response**](GetAgentBotMaxBots200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Объект JSON c ключами &#x60;bots&#x60; и &#x60;meta&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getAgentBotMaxBotsByAgent"></a>
# **getAgentBotMaxBotsByAgent**
> GetAgentBotMaxBotsByAgent200Response getAgentBotMaxBotsByAgent(agentId)

Получение списка ботов MAX агента

Чтобы получить список ботов MAX, подключенных к AI агенту, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/agents/{agent_id}/max-bots&#x60;.  Тело ответа будет представлять собой объект JSON с ключами &#x60;bots&#x60; и &#x60;meta&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.AiAgentIntegrationsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    AiAgentIntegrationsApi apiInstance = new AiAgentIntegrationsApi(defaultClient);
    Integer agentId = 1; // Integer | ID агента
    try {
      GetAgentBotMaxBotsByAgent200Response result = apiInstance.getAgentBotMaxBotsByAgent(agentId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AiAgentIntegrationsApi#getAgentBotMaxBotsByAgent");
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
| **agentId** | **Integer**| ID агента | |

### Return type

[**GetAgentBotMaxBotsByAgent200Response**](GetAgentBotMaxBotsByAgent200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Объект JSON c ключами &#x60;bots&#x60; и &#x60;meta&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getAgentBotTelegramBot"></a>
# **getAgentBotTelegramBot**
> CreateAgentBotTelegramBot201Response getAgentBotTelegramBot(botId)

Получение информации о Telegram-боте

Чтобы получить информацию о Telegram-боте, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/bots/{bot_id}&#x60;.  Тело ответа будет представлять собой объект JSON с ключом &#x60;bot&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.AiAgentIntegrationsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    AiAgentIntegrationsApi apiInstance = new AiAgentIntegrationsApi(defaultClient);
    UUID botId = UUID.fromString("550e8400-e29b-41d4-a716-446655440000"); // UUID | ID бота (поле `id` из ответа)
    try {
      CreateAgentBotTelegramBot201Response result = apiInstance.getAgentBotTelegramBot(botId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AiAgentIntegrationsApi#getAgentBotTelegramBot");
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
| **botId** | **UUID**| ID бота (поле &#x60;id&#x60; из ответа) | |

### Return type

[**CreateAgentBotTelegramBot201Response**](CreateAgentBotTelegramBot201Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Объект JSON c ключом &#x60;bot&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getAgentBotTelegramBots"></a>
# **getAgentBotTelegramBots**
> GetAgentBotTelegramBots200Response getAgentBotTelegramBots()

Получение списка Telegram-ботов

Чтобы получить список Telegram-ботов, подключенных к агентам аккаунта, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/bots&#x60;.  Тело ответа будет представлять собой объект JSON с ключами &#x60;bots&#x60; и &#x60;meta&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.AiAgentIntegrationsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    AiAgentIntegrationsApi apiInstance = new AiAgentIntegrationsApi(defaultClient);
    try {
      GetAgentBotTelegramBots200Response result = apiInstance.getAgentBotTelegramBots();
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AiAgentIntegrationsApi#getAgentBotTelegramBots");
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

[**GetAgentBotTelegramBots200Response**](GetAgentBotTelegramBots200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Объект JSON c ключами &#x60;bots&#x60; и &#x60;meta&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getAgentBotTelegramBotsByAgent"></a>
# **getAgentBotTelegramBotsByAgent**
> GetAgentBotTelegramBotsByAgent200Response getAgentBotTelegramBotsByAgent(agentId)

Получение списка Telegram-ботов агента

Чтобы получить список Telegram-ботов, подключенных к AI агенту, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/agents/{agent_id}/bots&#x60;.  Тело ответа будет представлять собой объект JSON с ключами &#x60;bots&#x60; и &#x60;meta&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.AiAgentIntegrationsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    AiAgentIntegrationsApi apiInstance = new AiAgentIntegrationsApi(defaultClient);
    Integer agentId = 1; // Integer | ID агента
    try {
      GetAgentBotTelegramBotsByAgent200Response result = apiInstance.getAgentBotTelegramBotsByAgent(agentId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AiAgentIntegrationsApi#getAgentBotTelegramBotsByAgent");
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
| **agentId** | **Integer**| ID агента | |

### Return type

[**GetAgentBotTelegramBotsByAgent200Response**](GetAgentBotTelegramBotsByAgent200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Объект JSON c ключами &#x60;bots&#x60; и &#x60;meta&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getAgentIntegrationCrisp"></a>
# **getAgentIntegrationCrisp**
> GetAgentIntegrationCrisp200Response getAgentIntegrationCrisp(agentId)

Получение интеграции агента с Crisp

Чтобы получить интеграцию AI агента с Crisp, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/agent/{agent_id}/crisp&#x60;.  В ответе есть &#x60;webhook_secret&#x60; — по нему можно восстановить адрес вебхука для Crisp. Если агент не подключен к Crisp, вернется ошибка 404.  Тело ответа будет представлять собой объект JSON с ключом &#x60;crisp_integration&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.AiAgentIntegrationsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    AiAgentIntegrationsApi apiInstance = new AiAgentIntegrationsApi(defaultClient);
    Integer agentId = 1; // Integer | ID агента
    try {
      GetAgentIntegrationCrisp200Response result = apiInstance.getAgentIntegrationCrisp(agentId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AiAgentIntegrationsApi#getAgentIntegrationCrisp");
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
| **agentId** | **Integer**| ID агента | |

### Return type

[**GetAgentIntegrationCrisp200Response**](GetAgentIntegrationCrisp200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Объект JSON c ключом &#x60;crisp_integration&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |
| **503** | Интеграция с Crisp временно недоступна |  -  |

<a id="getAgentIntegrationJivo"></a>
# **getAgentIntegrationJivo**
> GetAgentIntegrationJivo200Response getAgentIntegrationJivo(agentId)

Получение интеграции агента с Jivo

Чтобы получить интеграцию AI агента с Jivo, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/agent/{agent_id}/jivo&#x60;.  Если агент не подключен к Jivo, вернется ошибка 404.  Тело ответа будет представлять собой объект JSON с ключом &#x60;jivo_integration&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.AiAgentIntegrationsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    AiAgentIntegrationsApi apiInstance = new AiAgentIntegrationsApi(defaultClient);
    Integer agentId = 1; // Integer | ID агента
    try {
      GetAgentIntegrationJivo200Response result = apiInstance.getAgentIntegrationJivo(agentId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AiAgentIntegrationsApi#getAgentIntegrationJivo");
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
| **agentId** | **Integer**| ID агента | |

### Return type

[**GetAgentIntegrationJivo200Response**](GetAgentIntegrationJivo200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Объект JSON c ключом &#x60;jivo_integration&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="updateAgentBotMaxBot"></a>
# **updateAgentBotMaxBot**
> CreateAgentBotTelegramBot201Response updateAgentBotMaxBot(botId, updateMaxBot)

Изменение бота MAX

Чтобы изменить бота MAX, отправьте PATCH-запрос на &#x60;/api/v1/cloud-ai/max-bots/{bot_id}&#x60;, передав только изменяемые поля.  Через &#x60;token&#x60; можно заменить токен того же бота: платформа проверит новый токен и заново подпишет бота на события. Перенести подключение на другого бота нельзя — для этого удалите бота и подключите новый.  Ошибки: - &#x60;400 invalid_max_token&#x60; — MAX не принял токен или токен принадлежит другому боту; - &#x60;400 max_webhook_error&#x60; — не удалось подписать бота на события.  Тело ответа будет представлять собой объект JSON с ключом &#x60;bot&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.AiAgentIntegrationsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    AiAgentIntegrationsApi apiInstance = new AiAgentIntegrationsApi(defaultClient);
    UUID botId = UUID.fromString("550e8400-e29b-41d4-a716-446655440000"); // UUID | ID бота (поле `id` из ответа)
    UpdateMaxBot updateMaxBot = new UpdateMaxBot(); // UpdateMaxBot | 
    try {
      CreateAgentBotTelegramBot201Response result = apiInstance.updateAgentBotMaxBot(botId, updateMaxBot);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AiAgentIntegrationsApi#updateAgentBotMaxBot");
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
| **botId** | **UUID**| ID бота (поле &#x60;id&#x60; из ответа) | |
| **updateMaxBot** | [**UpdateMaxBot**](UpdateMaxBot.md)|  | |

### Return type

[**CreateAgentBotTelegramBot201Response**](CreateAgentBotTelegramBot201Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Объект JSON c ключом &#x60;bot&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="updateAgentBotTelegramBot"></a>
# **updateAgentBotTelegramBot**
> CreateAgentBotTelegramBot201Response updateAgentBotTelegramBot(botId, updateBot)

Изменение Telegram-бота

Чтобы изменить Telegram-бота, отправьте PATCH-запрос на &#x60;/api/v1/cloud-ai/bots/{bot_id}&#x60;, передав только изменяемые поля.  Через &#x60;token&#x60; можно заменить токен того же бота, например после его перевыпуска в @BotFather: платформа проверит новый токен и переустановит вебхук. Перенести подключение на другого бота нельзя — для этого удалите бота и подключите новый. Агента, к которому подключен бот, этим запросом сменить нельзя.  Ошибки: - &#x60;400 invalid_telegram_token&#x60; — Telegram не принял токен или токен принадлежит другому боту; - &#x60;400 telegram_webhook_error&#x60; — не удалось переустановить вебхук.  Тело ответа будет представлять собой объект JSON с ключом &#x60;bot&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.AiAgentIntegrationsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    AiAgentIntegrationsApi apiInstance = new AiAgentIntegrationsApi(defaultClient);
    UUID botId = UUID.fromString("550e8400-e29b-41d4-a716-446655440000"); // UUID | ID бота (поле `id` из ответа)
    UpdateBot updateBot = new UpdateBot(); // UpdateBot | 
    try {
      CreateAgentBotTelegramBot201Response result = apiInstance.updateAgentBotTelegramBot(botId, updateBot);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AiAgentIntegrationsApi#updateAgentBotTelegramBot");
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
| **botId** | **UUID**| ID бота (поле &#x60;id&#x60; из ответа) | |
| **updateBot** | [**UpdateBot**](UpdateBot.md)|  | |

### Return type

[**CreateAgentBotTelegramBot201Response**](CreateAgentBotTelegramBot201Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Объект JSON c ключом &#x60;bot&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

