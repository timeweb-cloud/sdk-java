# McpApi

All URIs are relative to *https://api.timeweb.cloud*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**deleteMcpSearchSession**](McpApi.md#deleteMcpSearchSession) | **DELETE** /api/v1/mcp/search | Завершение сессии сервера MCP с поиском инструментов |
| [**deleteMcpSession**](McpApi.md#deleteMcpSession) | **DELETE** /api/v1/mcp | Завершение сессии сервера MCP |
| [**openMcpSearchStream**](McpApi.md#openMcpSearchStream) | **GET** /api/v1/mcp/search | Открытие потока событий сервера MCP с поиском инструментов |
| [**openMcpStream**](McpApi.md#openMcpStream) | **GET** /api/v1/mcp | Открытие потока событий сервера MCP |
| [**sendMcpMessage**](McpApi.md#sendMcpMessage) | **POST** /api/v1/mcp | Отправка JSON-RPC-сообщения серверу MCP |
| [**sendMcpSearchMessage**](McpApi.md#sendMcpSearchMessage) | **POST** /api/v1/mcp/search | Отправка JSON-RPC-сообщения серверу MCP с поиском инструментов |


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

