# McpApi

All URIs are relative to *https://api.timeweb.cloud*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**connectAgentToolToAgent**](McpApi.md#connectAgentToolToAgent) | **POST** /api/v1/cloud-ai/agents/{agent_id}/tools | Подключение MCP-инструмента к агенту |
| [**createAgentTool**](McpApi.md#createAgentTool) | **POST** /api/v1/cloud-ai/tools | Подключение собственного MCP-сервера |
| [**deleteAgentTool**](McpApi.md#deleteAgentTool) | **DELETE** /api/v1/cloud-ai/tools/{id} | Удаление MCP-инструмента |
| [**deleteMcpSearchSession**](McpApi.md#deleteMcpSearchSession) | **DELETE** /api/v1/mcp/search | Завершение сессии сервера MCP с поиском инструментов |
| [**deleteMcpSession**](McpApi.md#deleteMcpSession) | **DELETE** /api/v1/mcp | Завершение сессии сервера MCP |
| [**disconnectAgentToolFromAgent**](McpApi.md#disconnectAgentToolFromAgent) | **DELETE** /api/v1/cloud-ai/agents/{agent_id}/tools/{tool_id} | Отключение MCP-инструмента от агента |
| [**getAgentTool**](McpApi.md#getAgentTool) | **GET** /api/v1/cloud-ai/tools/{id} | Получение MCP-инструмента |
| [**getAgentToolOAuth2AuthorizeUrl**](McpApi.md#getAgentToolOAuth2AuthorizeUrl) | **GET** /api/v1/cloud-ai/tools/oauth2/authorize-url/{tool_id} | Получение ссылки на OAuth2-авторизацию инструмента |
| [**getAgentToolTemplatePrices**](McpApi.md#getAgentToolTemplatePrices) | **GET** /api/v1/cloud-ai/tools/templates/prices | Получение цен системных инструментов |
| [**getAgentToolTemplates**](McpApi.md#getAgentToolTemplates) | **GET** /api/v1/cloud-ai/tools/templates/marketplace | Получение шаблонов MCP-инструментов из маркетплейса |
| [**getAgentTools**](McpApi.md#getAgentTools) | **GET** /api/v1/cloud-ai/tools | Получение списка MCP-инструментов |
| [**getAgentToolsByAgent**](McpApi.md#getAgentToolsByAgent) | **GET** /api/v1/cloud-ai/agents/{agent_id}/tools | Получение MCP-инструментов агента |
| [**installAgentToolFromTemplate**](McpApi.md#installAgentToolFromTemplate) | **POST** /api/v1/cloud-ai/tools/install | Установка MCP-инструмента из маркетплейса |
| [**openMcpSearchStream**](McpApi.md#openMcpSearchStream) | **GET** /api/v1/mcp/search | Открытие потока событий сервера MCP с поиском инструментов |
| [**openMcpStream**](McpApi.md#openMcpStream) | **GET** /api/v1/mcp | Открытие потока событий сервера MCP |
| [**refreshAgentToolMethods**](McpApi.md#refreshAgentToolMethods) | **POST** /api/v1/cloud-ai/tools/{id}/refresh-methods | Обновление списка методов MCP-инструмента |
| [**sendMcpMessage**](McpApi.md#sendMcpMessage) | **POST** /api/v1/mcp | Отправка JSON-RPC-сообщения серверу MCP |
| [**sendMcpSearchMessage**](McpApi.md#sendMcpSearchMessage) | **POST** /api/v1/mcp/search | Отправка JSON-RPC-сообщения серверу MCP с поиском инструментов |
| [**updateAgentTool**](McpApi.md#updateAgentTool) | **PUT** /api/v1/cloud-ai/tools/{id} | Изменение MCP-инструмента |
| [**updateAgentToolConnection**](McpApi.md#updateAgentToolConnection) | **PUT** /api/v1/cloud-ai/agents/{agent_id}/tools/{tool_id} | Изменение разрешённых методов MCP-инструмента у агента |


<a id="connectAgentToolToAgent"></a>
# **connectAgentToolToAgent**
> connectAgentToolToAgent(agentId, connectAgentTool)

Подключение MCP-инструмента к агенту

Чтобы подключить MCP-инструмент к агенту, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/agents/{agent_id}/tools&#x60;.  В &#x60;allowed_methods&#x60; перечислите методы, которые агенту разрешено вызывать. Подходят только имена из &#x60;methods[].name&#x60; инструмента, иначе вернётся 400 с &#x60;error_code&#x60; &#x60;invalid_methods_provided&#x60;. Повторно подключить уже подключённый инструмент нельзя. Чтобы изменить список методов, используйте PUT-запрос.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.McpApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    McpApi apiInstance = new McpApi(defaultClient);
    Integer agentId = 113191; // Integer | ID агента
    ConnectAgentTool connectAgentTool = new ConnectAgentTool(); // ConnectAgentTool | 
    try {
      apiInstance.connectAgentToolToAgent(agentId, connectAgentTool);
    } catch (ApiException e) {
      System.err.println("Exception when calling McpApi#connectAgentToolToAgent");
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
| **connectAgentTool** | [**ConnectAgentTool**](ConnectAgentTool.md)|  | |

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
| **201** | Инструмент подключён к агенту. Тело ответа пустое |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="createAgentTool"></a>
# **createAgentTool**
> CreateAgentTool201Response createAgentTool(createTool)

Подключение собственного MCP-сервера

Чтобы подключить собственный MCP-сервер, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/tools&#x60;.  Платформа сразу подключается к серверу и сохраняет список его методов. Если сервер недоступен или отвечает ошибкой, инструмент не создаётся: вернётся 400 с &#x60;error_code&#x60; &#x60;invalid_methods_provided&#x60;. Адреса частных сетей и localhost отклоняются с ошибкой 400.  Для &#x60;auth_data.type &#x3D; oauth2&#x60; подключение к серверу при создании не проверяется. Инструмент создаётся в статусе &#x60;pending_auth&#x60; с пустым списком методов, а методы загружаются после авторизации пользователя: см. «Получение ссылки на OAuth2-авторизацию инструмента». Если не удалось определить адреса авторизации или зарегистрировать клиента, вернётся 401 с &#x60;error_code&#x60; &#x60;tool_authentication_failed&#x60;.  Чтобы агент мог пользоваться инструментом, подключите его к агенту.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.McpApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    McpApi apiInstance = new McpApi(defaultClient);
    CreateTool createTool = new CreateTool(); // CreateTool | 
    try {
      CreateAgentTool201Response result = apiInstance.createAgentTool(createTool);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling McpApi#createAgentTool");
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
| **createTool** | [**CreateTool**](CreateTool.md)|  | |

### Return type

[**CreateAgentTool201Response**](CreateAgentTool201Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Объект JSON c ключом &#x60;tool&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="deleteAgentTool"></a>
# **deleteAgentTool**
> deleteAgentTool(id)

Удаление MCP-инструмента

Чтобы удалить MCP-инструмент, отправьте DELETE-запрос на &#x60;/api/v1/cloud-ai/tools/{id}&#x60;.  Инструмент автоматически отключается от всех агентов.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.McpApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    McpApi apiInstance = new McpApi(defaultClient);
    Integer id = 417; // Integer | ID инструмента
    try {
      apiInstance.deleteAgentTool(id);
    } catch (ApiException e) {
      System.err.println("Exception when calling McpApi#deleteAgentTool");
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
| **id** | **Integer**| ID инструмента | |

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
| **200** | Инструмент удалён. Тело ответа пустое |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="deleteMcpSearchSession"></a>
# **deleteMcpSearchSession**
> deleteMcpSearchSession()

Завершение сессии сервера MCP с поиском инструментов

Чтобы завершить сессию сервера MCP &#x60;timeweb-cloud-mcp-search&#x60;, отправьте DELETE-запрос на &#x60;/api/v1/mcp/search&#x60;.  Сервер не хранит сессий (не выдаёт &#x60;Mcp-Session-Id&#x60;), поэтому завершать нечего: ответ всегда 200 без тела.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.McpApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    McpApi apiInstance = new McpApi(defaultClient);
    try {
      apiInstance.deleteMcpSearchSession();
    } catch (ApiException e) {
      System.err.println("Exception when calling McpApi#deleteMcpSearchSession");
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

null (empty response body)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Готово, тела нет |  -  |
| **401** | Не авторизован |  -  |
| **429** | Превышен лимит запросов для токена |  -  |

<a id="deleteMcpSession"></a>
# **deleteMcpSession**
> deleteMcpSession()

Завершение сессии сервера MCP

Чтобы завершить сессию сервера MCP &#x60;timeweb-cloud-mcp&#x60;, отправьте DELETE-запрос на &#x60;/api/v1/mcp&#x60;.  Сервер не хранит сессий (не выдаёт &#x60;Mcp-Session-Id&#x60;), поэтому завершать нечего: ответ всегда 200 без тела.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.McpApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    McpApi apiInstance = new McpApi(defaultClient);
    try {
      apiInstance.deleteMcpSession();
    } catch (ApiException e) {
      System.err.println("Exception when calling McpApi#deleteMcpSession");
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

null (empty response body)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Готово, тела нет |  -  |
| **401** | Не авторизован |  -  |
| **429** | Превышен лимит запросов для токена |  -  |

<a id="disconnectAgentToolFromAgent"></a>
# **disconnectAgentToolFromAgent**
> disconnectAgentToolFromAgent(agentId, toolId)

Отключение MCP-инструмента от агента

Чтобы отключить MCP-инструмент от агента, отправьте DELETE-запрос на &#x60;/api/v1/cloud-ai/agents/{agent_id}/tools/{tool_id}&#x60;.  Сам инструмент остаётся в аккаунте и у других агентов.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.McpApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    McpApi apiInstance = new McpApi(defaultClient);
    Integer agentId = 113191; // Integer | ID агента
    Integer toolId = 417; // Integer | ID инструмента
    try {
      apiInstance.disconnectAgentToolFromAgent(agentId, toolId);
    } catch (ApiException e) {
      System.err.println("Exception when calling McpApi#disconnectAgentToolFromAgent");
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
| **toolId** | **Integer**| ID инструмента | |

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
| **200** | Инструмент отключён от агента. Тело ответа пустое |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getAgentTool"></a>
# **getAgentTool**
> CreateAgentTool201Response getAgentTool(id)

Получение MCP-инструмента

Чтобы получить MCP-инструмент, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/tools/{id}&#x60;.  По полю &#x60;status&#x60; можно отследить завершение OAuth2-авторизации: после неё статус меняется с &#x60;pending_auth&#x60; на &#x60;active&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.McpApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    McpApi apiInstance = new McpApi(defaultClient);
    Integer id = 417; // Integer | ID инструмента
    try {
      CreateAgentTool201Response result = apiInstance.getAgentTool(id);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling McpApi#getAgentTool");
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
| **id** | **Integer**| ID инструмента | |

### Return type

[**CreateAgentTool201Response**](CreateAgentTool201Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Объект JSON c ключом &#x60;tool&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getAgentToolOAuth2AuthorizeUrl"></a>
# **getAgentToolOAuth2AuthorizeUrl**
> AuthorizeUrl getAgentToolOAuth2AuthorizeUrl(toolId, redirectUrl)

Получение ссылки на OAuth2-авторизацию инструмента

Чтобы получить ссылку на OAuth2-авторизацию MCP-инструмента, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/tools/oauth2/authorize-url/{tool_id}&#x60;.  Откройте &#x60;authorize_url&#x60; в браузере пользователя. Ссылка действует 30 минут. После входа и согласия провайдер вернёт пользователя на адрес возврата платформы &#x60;https://api.timeweb.cloud/api/v1/cloud-ai/oauth2/callback&#x60;. Платформа обменяет код на токены, переведёт инструмент в статус &#x60;active&#x60; и загрузит список методов.  Чтобы узнать результат, запрашивайте инструмент, пока его статус не сменится с &#x60;pending_auth&#x60; на &#x60;active&#x60;. Этим же запросом можно повторно авторизовать уже активный инструмент.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.McpApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    McpApi apiInstance = new McpApi(defaultClient);
    Integer toolId = 417; // Integer | ID инструмента
    String redirectUrl = "https://timeweb.cloud/my/cloud-ai"; // String | Куда вернуть пользователя после авторизации. Принимаются только адреса на доменах `timeweb.cloud` и `timeweb.net` и их поддоменах; другие адреса молча игнорируются. После успешной авторизации к адресу добавляются `oauth_success=true&tool_id=<id>`, после ошибки — `oauth_error=<текст>&tool_id=<id>`. Без параметра страница возврата показывает результат в виде JSON.
    try {
      AuthorizeUrl result = apiInstance.getAgentToolOAuth2AuthorizeUrl(toolId, redirectUrl);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling McpApi#getAgentToolOAuth2AuthorizeUrl");
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
| **toolId** | **Integer**| ID инструмента | |
| **redirectUrl** | **String**| Куда вернуть пользователя после авторизации. Принимаются только адреса на доменах &#x60;timeweb.cloud&#x60; и &#x60;timeweb.net&#x60; и их поддоменах; другие адреса молча игнорируются. После успешной авторизации к адресу добавляются &#x60;oauth_success&#x3D;true&amp;tool_id&#x3D;&lt;id&gt;&#x60;, после ошибки — &#x60;oauth_error&#x3D;&lt;текст&gt;&amp;tool_id&#x3D;&lt;id&gt;&#x60;. Без параметра страница возврата показывает результат в виде JSON. | [optional] |

### Return type

[**AuthorizeUrl**](AuthorizeUrl.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Объект JSON c ключом &#x60;authorize_url&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getAgentToolTemplatePrices"></a>
# **getAgentToolTemplatePrices**
> GetAgentToolTemplatePrices200Response getAgentToolTemplatePrices(locale)

Получение цен системных инструментов

Чтобы получить цены системных инструментов платформы, например веб-поиска, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/tools/templates/prices&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.McpApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    McpApi apiInstance = new McpApi(defaultClient);
    String locale = "ru"; // String | Валюта цены: `ru` — в рублях, `en` — в долларах США. По умолчанию `ru`.
    try {
      GetAgentToolTemplatePrices200Response result = apiInstance.getAgentToolTemplatePrices(locale);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling McpApi#getAgentToolTemplatePrices");
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
| **locale** | **String**| Валюта цены: &#x60;ru&#x60; — в рублях, &#x60;en&#x60; — в долларах США. По умолчанию &#x60;ru&#x60;. | [optional] [default to ru] [enum: ru, en] |

### Return type

[**GetAgentToolTemplatePrices200Response**](GetAgentToolTemplatePrices200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Объект JSON c ключами &#x60;system_tool_prices&#x60; и &#x60;meta&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getAgentToolTemplates"></a>
# **getAgentToolTemplates**
> GetAgentToolTemplates200Response getAgentToolTemplates()

Получение шаблонов MCP-инструментов из маркетплейса

Чтобы получить готовые интеграции маркетплейса, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/tools/templates/marketplace&#x60;.  Сначала идут самые популярные шаблоны. Поле &#x60;config_schema&#x60; описывает, что нужно передать в &#x60;config_values&#x60; при установке.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.McpApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    McpApi apiInstance = new McpApi(defaultClient);
    try {
      GetAgentToolTemplates200Response result = apiInstance.getAgentToolTemplates();
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling McpApi#getAgentToolTemplates");
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

[**GetAgentToolTemplates200Response**](GetAgentToolTemplates200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Объект JSON c ключами &#x60;tool_templates&#x60; и &#x60;meta&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getAgentTools"></a>
# **getAgentTools**
> GetAgentTools200Response getAgentTools()

Получение списка MCP-инструментов

Чтобы получить список MCP-инструментов аккаунта, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/tools&#x60;.  В списке есть и собственные MCP-серверы, и инструменты, установленные из маркетплейса. Сначала идут новые. Удалённые инструменты не возвращаются. Секреты в &#x60;auth_data&#x60; заменены на &#x60;********&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.McpApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    McpApi apiInstance = new McpApi(defaultClient);
    try {
      GetAgentTools200Response result = apiInstance.getAgentTools();
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling McpApi#getAgentTools");
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

[**GetAgentTools200Response**](GetAgentTools200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Объект JSON c ключами &#x60;tools&#x60; и &#x60;meta&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getAgentToolsByAgent"></a>
# **getAgentToolsByAgent**
> GetAgentToolsByAgent200Response getAgentToolsByAgent(agentId)

Получение MCP-инструментов агента

Чтобы получить MCP-инструменты, подключённые к агенту, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/agents/{agent_id}/tools&#x60;.  В ответе — инструменты целиком, в том же виде, что и в списке инструментов аккаунта. Списка разрешённых агенту методов в ответе нет.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.McpApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    McpApi apiInstance = new McpApi(defaultClient);
    Integer agentId = 113191; // Integer | ID агента
    try {
      GetAgentToolsByAgent200Response result = apiInstance.getAgentToolsByAgent(agentId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling McpApi#getAgentToolsByAgent");
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

[**GetAgentToolsByAgent200Response**](GetAgentToolsByAgent200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Объект JSON c ключами &#x60;agent_tools&#x60; и &#x60;meta&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="installAgentToolFromTemplate"></a>
# **installAgentToolFromTemplate**
> CreateAgentTool201Response installAgentToolFromTemplate(installTool)

Установка MCP-инструмента из маркетплейса

Чтобы установить MCP-инструмент из шаблона маркетплейса, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/tools/install&#x60;.  Значения полей из &#x60;config_schema&#x60; шаблона передайте в &#x60;config_values&#x60;. Платформа соберёт из них заголовки авторизации, подключится к MCP-серверу и сохранит список методов. Если у шаблона есть &#x60;config_schema&#x60;, поле &#x60;auth_data&#x60; не используется. Если подключиться к серверу не удалось, вернётся 400 с &#x60;error_code&#x60; &#x60;invalid_methods_provided&#x60;.  Один шаблон можно установить несколько раз, например с разными ключами.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.McpApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    McpApi apiInstance = new McpApi(defaultClient);
    InstallTool installTool = new InstallTool(); // InstallTool | 
    try {
      CreateAgentTool201Response result = apiInstance.installAgentToolFromTemplate(installTool);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling McpApi#installAgentToolFromTemplate");
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
| **installTool** | [**InstallTool**](InstallTool.md)|  | |

### Return type

[**CreateAgentTool201Response**](CreateAgentTool201Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Объект JSON c ключом &#x60;tool&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="openMcpSearchStream"></a>
# **openMcpSearchStream**
> String openMcpSearchStream(accept, mcPProtocolVersion)

Открытие потока событий сервера MCP с поиском инструментов

Чтобы открыть поток сообщений, которые сервер MCP &#x60;timeweb-cloud-mcp-search&#x60; отправляет по своей инициативе, отправьте GET-запрос на &#x60;/api/v1/mcp/search&#x60;.  Сервер не отправляет сообщений по своей инициативе, поэтому поток не содержит событий и остаётся открытым, пока его не закроет шлюз. Через шлюз заголовки ответа на этот запрос могут долго не приходить. Открывать поток не нужно: работайте с сервером только POST-запросами — если клиент позволяет выбрать режим, отключите в нём GET-поток.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.McpApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    McpApi apiInstance = new McpApi(defaultClient);
    String accept = "text/event-stream"; // String | Должен содержать `text/event-stream`, иначе сервер ответит 406
    String mcPProtocolVersion = "2025-11-25"; // String | Версия протокола, согласованная в `initialize`. Неподдерживаемое значение — ответ 400
    try {
      String result = apiInstance.openMcpSearchStream(accept, mcPProtocolVersion);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling McpApi#openMcpSearchStream");
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
| **accept** | **String**| Должен содержать &#x60;text/event-stream&#x60;, иначе сервер ответит 406 | |
| **mcPProtocolVersion** | **String**| Версия протокола, согласованная в &#x60;initialize&#x60;. Неподдерживаемое значение — ответ 400 | [optional] [enum: 2025-11-25, 2025-06-18, 2025-03-26, 2024-11-05, 2024-10-07] |

### Return type

**String**

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: text/event-stream, application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Поток событий без событий: сервер не отправляет сообщений по своей инициативе |  -  |
| **401** | Не авторизован |  -  |
| **406** | Заголовок &#x60;Accept&#x60; не содержит &#x60;text/event-stream&#x60; |  -  |
| **429** | Превышен лимит запросов для токена |  -  |

<a id="openMcpStream"></a>
# **openMcpStream**
> String openMcpStream(accept, mcPProtocolVersion)

Открытие потока событий сервера MCP

Чтобы открыть поток сообщений, которые сервер MCP &#x60;timeweb-cloud-mcp&#x60; отправляет по своей инициативе, отправьте GET-запрос на &#x60;/api/v1/mcp&#x60;.  Сервер не отправляет сообщений по своей инициативе, поэтому поток не содержит событий и остаётся открытым, пока его не закроет шлюз. Через шлюз заголовки ответа на этот запрос могут долго не приходить. Открывать поток не нужно: работайте с сервером только POST-запросами — если клиент позволяет выбрать режим, отключите в нём GET-поток.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.McpApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    McpApi apiInstance = new McpApi(defaultClient);
    String accept = "text/event-stream"; // String | Должен содержать `text/event-stream`, иначе сервер ответит 406
    String mcPProtocolVersion = "2025-11-25"; // String | Версия протокола, согласованная в `initialize`. Неподдерживаемое значение — ответ 400
    try {
      String result = apiInstance.openMcpStream(accept, mcPProtocolVersion);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling McpApi#openMcpStream");
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
| **accept** | **String**| Должен содержать &#x60;text/event-stream&#x60;, иначе сервер ответит 406 | |
| **mcPProtocolVersion** | **String**| Версия протокола, согласованная в &#x60;initialize&#x60;. Неподдерживаемое значение — ответ 400 | [optional] [enum: 2025-11-25, 2025-06-18, 2025-03-26, 2024-11-05, 2024-10-07] |

### Return type

**String**

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: text/event-stream, application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Поток событий без событий: сервер не отправляет сообщений по своей инициативе |  -  |
| **401** | Не авторизован |  -  |
| **406** | Заголовок &#x60;Accept&#x60; не содержит &#x60;text/event-stream&#x60; |  -  |
| **429** | Превышен лимит запросов для токена |  -  |

<a id="refreshAgentToolMethods"></a>
# **refreshAgentToolMethods**
> CreateAgentTool201Response refreshAgentToolMethods(id)

Обновление списка методов MCP-инструмента

Чтобы заново получить список методов с MCP-сервера, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/tools/{id}/refresh-methods&#x60;.  Список &#x60;methods&#x60; заменяется полученным с сервера, а методы, которых больше нет, убираются из разрешённых у всех агентов. Если обновление прошло успешно, инструмент переходит в статус &#x60;active&#x60;. Если сервер недоступен, инструмент переходит в статус &#x60;error&#x60; с причиной в &#x60;status_info&#x60;, а запрос возвращает 400 с &#x60;error_code&#x60; &#x60;invalid_methods_provided&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.McpApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    McpApi apiInstance = new McpApi(defaultClient);
    Integer id = 417; // Integer | ID инструмента
    try {
      CreateAgentTool201Response result = apiInstance.refreshAgentToolMethods(id);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling McpApi#refreshAgentToolMethods");
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
| **id** | **Integer**| ID инструмента | |

### Return type

[**CreateAgentTool201Response**](CreateAgentTool201Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Объект JSON c ключом &#x60;tool&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="sendMcpMessage"></a>
# **sendMcpMessage**
> JsonRpcResultResponse sendMcpMessage(accept, jsonRpcRequest, mcPProtocolVersion, xMcpConfirmationUi)

Отправка JSON-RPC-сообщения серверу MCP

Чтобы отправить запрос или уведомление серверу MCP &#x60;timeweb-cloud-mcp&#x60;, отправьте POST-запрос на &#x60;/api/v1/mcp&#x60; с сообщением JSON-RPC 2.0 в теле.  Через этот метод идут все запросы MCP: &#x60;initialize&#x60;, &#x60;tools/list&#x60; (все инструменты сразу), &#x60;tools/call&#x60;, &#x60;resources/list&#x60;, &#x60;resources/read&#x60;, &#x60;ping&#x60;. Ответ на запрос приходит потоком &#x60;text/event-stream&#x60; с одним событием &#x60;message&#x60;, уведомление принимается с кодом 202 без тела. Изменяющие инструменты выполняются в два вызова с &#x60;confirm_token&#x60; — см. описание раздела.  Заголовки &#x60;x-tool-access&#x60; и &#x60;x-allowed-tools&#x60; здесь не действуют — используйте &#x60;/api/v1/mcp/search&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.McpApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    McpApi apiInstance = new McpApi(defaultClient);
    String accept = "application/json, text/event-stream"; // String | Должен содержать и `application/json`, и `text/event-stream`, иначе сервер ответит 406
    JsonRpcRequest jsonRpcRequest = new JsonRpcRequest(); // JsonRpcRequest | 
    String mcPProtocolVersion = "2025-11-25"; // String | Версия протокола, согласованная в `initialize`. Неподдерживаемое значение — ответ 400
    String xMcpConfirmationUi = "text"; // String | Как `request_user_confirmation` спрашивает пользователя. `text` (по умолчанию) — пользователь отвечает в чате, на `/api/v1/mcp` инструмент в этом режиме не выводится. `buttons` — инструмент возвращает `ui_action` с кнопками для клиентов, которые умеют их показывать. Регистр не важен, другие значения игнорируются
    try {
      JsonRpcResultResponse result = apiInstance.sendMcpMessage(accept, jsonRpcRequest, mcPProtocolVersion, xMcpConfirmationUi);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling McpApi#sendMcpMessage");
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
| **accept** | **String**| Должен содержать и &#x60;application/json&#x60;, и &#x60;text/event-stream&#x60;, иначе сервер ответит 406 | |
| **jsonRpcRequest** | [**JsonRpcRequest**](JsonRpcRequest.md)|  | |
| **mcPProtocolVersion** | **String**| Версия протокола, согласованная в &#x60;initialize&#x60;. Неподдерживаемое значение — ответ 400 | [optional] [enum: 2025-11-25, 2025-06-18, 2025-03-26, 2024-11-05, 2024-10-07] |
| **xMcpConfirmationUi** | **String**| Как &#x60;request_user_confirmation&#x60; спрашивает пользователя. &#x60;text&#x60; (по умолчанию) — пользователь отвечает в чате, на &#x60;/api/v1/mcp&#x60; инструмент в этом режиме не выводится. &#x60;buttons&#x60; — инструмент возвращает &#x60;ui_action&#x60; с кнопками для клиентов, которые умеют их показывать. Регистр не важен, другие значения игнорируются | [optional] [enum: text, buttons] |

### Return type

[**JsonRpcResultResponse**](JsonRpcResultResponse.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: text/event-stream, application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Ответ на запрос: поток &#x60;text/event-stream&#x60; с одним событием &#x60;message&#x60;. В &#x60;data&#x60; события — ответ JSON-RPC (схема и примеры ниже): успешный или с ошибкой JSON-RPC, например &#x60;-32601&#x60; для неизвестного метода. Пример потока:  &#x60;&#x60;&#x60; event: message data: {\&quot;jsonrpc\&quot;:\&quot;2.0\&quot;,\&quot;id\&quot;:1,\&quot;result\&quot;:{\&quot;tools\&quot;:[...]}} &#x60;&#x60;&#x60; |  -  |
| **202** | Уведомление принято, тела нет |  -  |
| **400** | Тело не является корректным JSON или сообщением JSON-RPC (&#x60;-32700&#x60;), заголовок &#x60;MCP-Protocol-Version&#x60; содержит неподдерживаемую версию (&#x60;-32000&#x60;) |  -  |
| **401** | Не авторизован |  -  |
| **406** | Заголовок &#x60;Accept&#x60; не содержит &#x60;application/json&#x60; или &#x60;text/event-stream&#x60; |  -  |
| **415** | &#x60;Content-Type&#x60; не &#x60;application/json&#x60; |  -  |
| **429** | Превышен лимит запросов для токена |  -  |

<a id="sendMcpSearchMessage"></a>
# **sendMcpSearchMessage**
> JsonRpcResultResponse sendMcpSearchMessage(accept, jsonRpcRequest, mcPProtocolVersion, xToolAccess, xAllowedTools, xMcpConfirmationUi)

Отправка JSON-RPC-сообщения серверу MCP с поиском инструментов

Чтобы отправить запрос или уведомление серверу MCP &#x60;timeweb-cloud-mcp-search&#x60;, отправьте POST-запрос на &#x60;/api/v1/mcp/search&#x60; с сообщением JSON-RPC 2.0 в теле.  &#x60;tools/list&#x60; возвращает четыре инструмента: &#x60;search_tools&#x60;, &#x60;get_tool_definition&#x60;, &#x60;execute_tool&#x60; и &#x60;request_user_confirmation&#x60;. Все операции облака находятся через &#x60;search_tools&#x60; и выполняются через &#x60;execute_tool&#x60;; изменяющие операции — в два вызова &#x60;execute_tool&#x60; с &#x60;confirm_token&#x60; внутри &#x60;arguments&#x60;.  Доступ модели можно сузить заголовками &#x60;x-tool-access&#x60; и &#x60;x-allowed-tools&#x60;. Недопустимое значение &#x60;x-tool-access&#x60; — ответ 400 с ошибкой &#x60;-32600&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.McpApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    McpApi apiInstance = new McpApi(defaultClient);
    String accept = "application/json, text/event-stream"; // String | Должен содержать и `application/json`, и `text/event-stream`, иначе сервер ответит 406
    JsonRpcRequest jsonRpcRequest = new JsonRpcRequest(); // JsonRpcRequest | 
    String mcPProtocolVersion = "2025-11-25"; // String | Версия протокола, согласованная в `initialize`. Неподдерживаемое значение — ответ 400
    String xToolAccess = "read-only"; // String | Только операции чтения: поиск и определения пропускают изменяющие инструменты, а `execute_tool` отказывает с `tool_not_allowed`. Единственное допустимое значение — `read-only` (регистр не важен); любое другое, в том числе пустое, — ответ 400 с ошибкой `-32600`
    String xAllowedTools = "list_servers,get_server,server_action"; // String | Имена разрешённых операций через запятую — все остальные недоступны. Неизвестные имена игнорируются, пустое значение не разрешает ничего. Вместе с `x-tool-access` операция должна пройти оба ограничения
    String xMcpConfirmationUi = "text"; // String | Как `request_user_confirmation` спрашивает пользователя. `text` (по умолчанию) — пользователь отвечает в чате, на `/api/v1/mcp` инструмент в этом режиме не выводится. `buttons` — инструмент возвращает `ui_action` с кнопками для клиентов, которые умеют их показывать. Регистр не важен, другие значения игнорируются
    try {
      JsonRpcResultResponse result = apiInstance.sendMcpSearchMessage(accept, jsonRpcRequest, mcPProtocolVersion, xToolAccess, xAllowedTools, xMcpConfirmationUi);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling McpApi#sendMcpSearchMessage");
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
| **accept** | **String**| Должен содержать и &#x60;application/json&#x60;, и &#x60;text/event-stream&#x60;, иначе сервер ответит 406 | |
| **jsonRpcRequest** | [**JsonRpcRequest**](JsonRpcRequest.md)|  | |
| **mcPProtocolVersion** | **String**| Версия протокола, согласованная в &#x60;initialize&#x60;. Неподдерживаемое значение — ответ 400 | [optional] [enum: 2025-11-25, 2025-06-18, 2025-03-26, 2024-11-05, 2024-10-07] |
| **xToolAccess** | **String**| Только операции чтения: поиск и определения пропускают изменяющие инструменты, а &#x60;execute_tool&#x60; отказывает с &#x60;tool_not_allowed&#x60;. Единственное допустимое значение — &#x60;read-only&#x60; (регистр не важен); любое другое, в том числе пустое, — ответ 400 с ошибкой &#x60;-32600&#x60; | [optional] [enum: read-only] |
| **xAllowedTools** | **String**| Имена разрешённых операций через запятую — все остальные недоступны. Неизвестные имена игнорируются, пустое значение не разрешает ничего. Вместе с &#x60;x-tool-access&#x60; операция должна пройти оба ограничения | [optional] |
| **xMcpConfirmationUi** | **String**| Как &#x60;request_user_confirmation&#x60; спрашивает пользователя. &#x60;text&#x60; (по умолчанию) — пользователь отвечает в чате, на &#x60;/api/v1/mcp&#x60; инструмент в этом режиме не выводится. &#x60;buttons&#x60; — инструмент возвращает &#x60;ui_action&#x60; с кнопками для клиентов, которые умеют их показывать. Регистр не важен, другие значения игнорируются | [optional] [enum: text, buttons] |

### Return type

[**JsonRpcResultResponse**](JsonRpcResultResponse.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: text/event-stream, application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Ответ на запрос: поток &#x60;text/event-stream&#x60; с одним событием &#x60;message&#x60;. В &#x60;data&#x60; события — ответ JSON-RPC (схема и примеры ниже): успешный или с ошибкой JSON-RPC, например &#x60;-32601&#x60; для неизвестного метода. Пример потока:  &#x60;&#x60;&#x60; event: message data: {\&quot;jsonrpc\&quot;:\&quot;2.0\&quot;,\&quot;id\&quot;:1,\&quot;result\&quot;:{\&quot;tools\&quot;:[...]}} &#x60;&#x60;&#x60; |  -  |
| **202** | Уведомление принято, тела нет |  -  |
| **400** | Тело не является корректным JSON или сообщением JSON-RPC (&#x60;-32700&#x60;), заголовок &#x60;MCP-Protocol-Version&#x60; содержит неподдерживаемую версию (&#x60;-32000&#x60;) или недопустимое значение &#x60;x-tool-access&#x60; (&#x60;-32600&#x60;) |  -  |
| **401** | Не авторизован |  -  |
| **406** | Заголовок &#x60;Accept&#x60; не содержит &#x60;application/json&#x60; или &#x60;text/event-stream&#x60; |  -  |
| **415** | &#x60;Content-Type&#x60; не &#x60;application/json&#x60; |  -  |
| **429** | Превышен лимит запросов для токена |  -  |

<a id="updateAgentTool"></a>
# **updateAgentTool**
> CreateAgentTool201Response updateAgentTool(id, updateTool)

Изменение MCP-инструмента

Чтобы изменить MCP-инструмент, отправьте PUT-запрос на &#x60;/api/v1/cloud-ai/tools/{id}&#x60;, передав только изменяемые поля.  Если передать в &#x60;auth_data&#x60; секрет &#x60;********&#x60; из ответа API, сохранённый секрет не изменится. Изменение адреса или авторизации не обновляет список методов: после него вызовите обновление методов. Статус &#x60;inactive&#x60; выключает инструмент у всех агентов, к которым он подключён.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.McpApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    McpApi apiInstance = new McpApi(defaultClient);
    Integer id = 417; // Integer | ID инструмента
    UpdateTool updateTool = new UpdateTool(); // UpdateTool | 
    try {
      CreateAgentTool201Response result = apiInstance.updateAgentTool(id, updateTool);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling McpApi#updateAgentTool");
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
| **id** | **Integer**| ID инструмента | |
| **updateTool** | [**UpdateTool**](UpdateTool.md)|  | |

### Return type

[**CreateAgentTool201Response**](CreateAgentTool201Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Объект JSON c ключом &#x60;tool&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="updateAgentToolConnection"></a>
# **updateAgentToolConnection**
> updateAgentToolConnection(agentId, toolId, updateAgentTool)

Изменение разрешённых методов MCP-инструмента у агента

Чтобы изменить список методов, которые агенту разрешено вызывать, отправьте PUT-запрос на &#x60;/api/v1/cloud-ai/agents/{agent_id}/tools/{tool_id}&#x60;.  Список заменяется целиком. Подходят только имена из &#x60;methods[].name&#x60; инструмента, иначе вернётся 400 с &#x60;error_code&#x60; &#x60;invalid_methods_provided&#x60;. Если инструмент не подключён к агенту, вернётся 404 с &#x60;error_code&#x60; &#x60;agent-tool_link_not_found&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.McpApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    McpApi apiInstance = new McpApi(defaultClient);
    Integer agentId = 113191; // Integer | ID агента
    Integer toolId = 417; // Integer | ID инструмента
    UpdateAgentTool updateAgentTool = new UpdateAgentTool(); // UpdateAgentTool | 
    try {
      apiInstance.updateAgentToolConnection(agentId, toolId, updateAgentTool);
    } catch (ApiException e) {
      System.err.println("Exception when calling McpApi#updateAgentToolConnection");
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
| **toolId** | **Integer**| ID инструмента | |
| **updateAgentTool** | [**UpdateAgentTool**](UpdateAgentTool.md)|  | |

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
| **200** | Запрос выполнен. Тело ответа пустое |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

