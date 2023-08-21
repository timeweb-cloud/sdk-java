# FirewallApi

All URIs are relative to *https://api.timeweb.cloud*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**addResourceToGroup**](FirewallApi.md#addResourceToGroup) | **POST** /api/v1/firewall/groups/{group_id}/resources/{resource_id} | Линковка ресурса в firewall group |
| [**createGroup**](FirewallApi.md#createGroup) | **POST** /api/v1/firewall/groups | Создание группы правил |
| [**createGroupRule**](FirewallApi.md#createGroupRule) | **POST** /api/v1/firewall/groups/{group_id}/rules | Создание firewall правила |
| [**deleteGroup**](FirewallApi.md#deleteGroup) | **DELETE** /api/v1/firewall/groups/{group_id} | Удаление группы правил |
| [**deleteGroupRule**](FirewallApi.md#deleteGroupRule) | **DELETE** /api/v1/firewall/groups/{group_id}/rules/{rule_id} | Удаление firewall правила |
| [**deleteResourceFromGroup**](FirewallApi.md#deleteResourceFromGroup) | **DELETE** /api/v1/firewall/groups/{group_id}/resources/{resource_id} | Отлинковка ресурса из firewall group |
| [**getGroup**](FirewallApi.md#getGroup) | **GET** /api/v1/firewall/groups/{group_id} | Получение информации о группе правил |
| [**getGroupResources**](FirewallApi.md#getGroupResources) | **GET** /api/v1/firewall/groups/{group_id}/resources | Получение слинкованных ресурсов |
| [**getGroupRule**](FirewallApi.md#getGroupRule) | **GET** /api/v1/firewall/groups/{group_id}/rules/{rule_id} | Получение информации о правиле |
| [**getGroupRules**](FirewallApi.md#getGroupRules) | **GET** /api/v1/firewall/groups/{group_id}/rules | Получение списка правил |
| [**getGroups**](FirewallApi.md#getGroups) | **GET** /api/v1/firewall/groups | Получение групп правил |
| [**getRulesForResource**](FirewallApi.md#getRulesForResource) | **GET** /api/v1/firewall/service/{resource_type}/{resource_id} | Получение групп правил для ресурса |
| [**updateGroup**](FirewallApi.md#updateGroup) | **PATCH** /api/v1/firewall/groups/{group_id} | Обновление группы правил |
| [**updateGroupRule**](FirewallApi.md#updateGroupRule) | **PATCH** /api/v1/firewall/groups/{group_id}/rules/{rule_id} | Обновление firewall правила |


<a id="addResourceToGroup"></a>
# **addResourceToGroup**
> AddResourceToGroup201Response addResourceToGroup(groupId, resourceId, resourceType)

Линковка ресурса в firewall group

Чтобы слинковать ресурс с группой правил, отправьте POST запрос на &#x60;/api/v1/firewall/groups/{group_id}/resources/{resource_id}&#x60;

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.FirewallApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    FirewallApi apiInstance = new FirewallApi(defaultClient);
    String groupId = "groupId_example"; // String | Идентификатор группы правил
    String resourceId = "resourceId_example"; // String | Идентификатор ресурса
    ResourceType resourceType = ResourceType.fromValue("server"); // ResourceType | 
    try {
      AddResourceToGroup201Response result = apiInstance.addResourceToGroup(groupId, resourceId, resourceType);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling FirewallApi#addResourceToGroup");
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
| **groupId** | **String**| Идентификатор группы правил | |
| **resourceId** | **String**| Идентификатор ресурса | |
| **resourceType** | [**ResourceType**](.md)|  | [optional] [default to server] [enum: server] |

### Return type

[**AddResourceToGroup201Response**](AddResourceToGroup201Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Ресурс добавлен к группе |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="createGroup"></a>
# **createGroup**
> CreateGroup201Response createGroup(firewallGroupInAPI)

Создание группы правил

Чтобы создать группу правил, отправьте POST запрос на &#x60;/api/v1/firewall/groups&#x60;

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.FirewallApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    FirewallApi apiInstance = new FirewallApi(defaultClient);
    FirewallGroupInAPI firewallGroupInAPI = new FirewallGroupInAPI(); // FirewallGroupInAPI | 
    try {
      CreateGroup201Response result = apiInstance.createGroup(firewallGroupInAPI);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling FirewallApi#createGroup");
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
| **firewallGroupInAPI** | [**FirewallGroupInAPI**](FirewallGroupInAPI.md)|  | |

### Return type

[**CreateGroup201Response**](CreateGroup201Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Группа правил создана |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="createGroupRule"></a>
# **createGroupRule**
> CreateGroupRule201Response createGroupRule(groupId, firewallRuleInAPI)

Создание firewall правила

Чтобы создать правило в группе, отправьте POST запрос на &#x60;/api/v1/firewall/groups/{group_id}/rules&#x60;

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.FirewallApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    FirewallApi apiInstance = new FirewallApi(defaultClient);
    String groupId = "groupId_example"; // String | Идентификатор группы правил
    FirewallRuleInAPI firewallRuleInAPI = new FirewallRuleInAPI(); // FirewallRuleInAPI | 
    try {
      CreateGroupRule201Response result = apiInstance.createGroupRule(groupId, firewallRuleInAPI);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling FirewallApi#createGroupRule");
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
| **groupId** | **String**| Идентификатор группы правил | |
| **firewallRuleInAPI** | [**FirewallRuleInAPI**](FirewallRuleInAPI.md)|  | |

### Return type

[**CreateGroupRule201Response**](CreateGroupRule201Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Правило создано |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="deleteGroup"></a>
# **deleteGroup**
> deleteGroup(groupId)

Удаление группы правил

Чтобы удалить группу правил, отправьте DELETE запрос на &#x60;/api/v1/firewall/groups/{group_id}&#x60;

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.FirewallApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    FirewallApi apiInstance = new FirewallApi(defaultClient);
    String groupId = "groupId_example"; // String | Идентификатор группы правил
    try {
      apiInstance.deleteGroup(groupId);
    } catch (ApiException e) {
      System.err.println("Exception when calling FirewallApi#deleteGroup");
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
| **groupId** | **String**| Идентификатор группы правил | |

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
| **204** | Группа удалена |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="deleteGroupRule"></a>
# **deleteGroupRule**
> deleteGroupRule(groupId, ruleId)

Удаление firewall правила

Чтобы удалить правило, отправьте DELETE запрос на &#x60;/api/v1/firewall/groups/{group_id}/rules/{rule_id}&#x60;

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.FirewallApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    FirewallApi apiInstance = new FirewallApi(defaultClient);
    String groupId = "groupId_example"; // String | Идентификатор группы правил
    String ruleId = "ruleId_example"; // String | Идентификатор правила
    try {
      apiInstance.deleteGroupRule(groupId, ruleId);
    } catch (ApiException e) {
      System.err.println("Exception when calling FirewallApi#deleteGroupRule");
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
| **groupId** | **String**| Идентификатор группы правил | |
| **ruleId** | **String**| Идентификатор правила | |

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
| **204** | Правило удалено |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="deleteResourceFromGroup"></a>
# **deleteResourceFromGroup**
> deleteResourceFromGroup(groupId, resourceId, resourceType)

Отлинковка ресурса из firewall group

Чтобы отлинковать ресурс от группы правил, отправьте DELETE запрос на &#x60;/api/v1/firewall/groups/{group_id}/resources/{resource_id}&#x60;

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.FirewallApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    FirewallApi apiInstance = new FirewallApi(defaultClient);
    String groupId = "groupId_example"; // String | Идентификатор группы правил
    String resourceId = "resourceId_example"; // String | Идентификатор ресурса
    ResourceType resourceType = ResourceType.fromValue("server"); // ResourceType | 
    try {
      apiInstance.deleteResourceFromGroup(groupId, resourceId, resourceType);
    } catch (ApiException e) {
      System.err.println("Exception when calling FirewallApi#deleteResourceFromGroup");
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
| **groupId** | **String**| Идентификатор группы правил | |
| **resourceId** | **String**| Идентификатор ресурса | |
| **resourceType** | [**ResourceType**](.md)|  | [optional] [default to server] [enum: server] |

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
| **204** | Ресурс удален из Группы правил |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getGroup"></a>
# **getGroup**
> CreateGroup201Response getGroup(groupId)

Получение информации о группе правил

Чтобы получить информацию о группе правил, отправьте GET запрос на &#x60;/api/v1/firewall/groups/{group_id}&#x60;

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.FirewallApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    FirewallApi apiInstance = new FirewallApi(defaultClient);
    String groupId = "groupId_example"; // String | Идентификатор группы правил
    try {
      CreateGroup201Response result = apiInstance.getGroup(groupId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling FirewallApi#getGroup");
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
| **groupId** | **String**| Идентификатор группы правил | |

### Return type

[**CreateGroup201Response**](CreateGroup201Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Информация о группе правил |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getGroupResources"></a>
# **getGroupResources**
> GetGroupResources200Response getGroupResources(groupId, limit, offset)

Получение слинкованных ресурсов

Чтобы получить слинкованных ресурсов для группы правил, отправьте GET запрос на &#x60;/api/v1/firewall/groups/{group_id}/resources&#x60;

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.FirewallApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    FirewallApi apiInstance = new FirewallApi(defaultClient);
    String groupId = "groupId_example"; // String | Идентификатор группы правил
    Integer limit = 100; // Integer | Обозначает количество записей, которое необходимо вернуть.
    Integer offset = 0; // Integer | Указывает на смещение относительно начала списка.
    try {
      GetGroupResources200Response result = apiInstance.getGroupResources(groupId, limit, offset);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling FirewallApi#getGroupResources");
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
| **groupId** | **String**| Идентификатор группы правил | |
| **limit** | **Integer**| Обозначает количество записей, которое необходимо вернуть. | [optional] [default to 100] |
| **offset** | **Integer**| Указывает на смещение относительно начала списка. | [optional] [default to 0] |

### Return type

[**GetGroupResources200Response**](GetGroupResources200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Список слинкованных ресурсов |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getGroupRule"></a>
# **getGroupRule**
> CreateGroupRule201Response getGroupRule(ruleId, groupId)

Получение информации о правиле

Чтобы получить инфомрацию о правиле, отправьте GET запрос на &#x60;/api/v1/firewall/groups/{group_id}/rules/{rule_id}&#x60;

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.FirewallApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    FirewallApi apiInstance = new FirewallApi(defaultClient);
    String ruleId = "ruleId_example"; // String | Идентификатор правила
    String groupId = "groupId_example"; // String | Идентификатор группы правил
    try {
      CreateGroupRule201Response result = apiInstance.getGroupRule(ruleId, groupId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling FirewallApi#getGroupRule");
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
| **ruleId** | **String**| Идентификатор правила | |
| **groupId** | **String**| Идентификатор группы правил | |

### Return type

[**CreateGroupRule201Response**](CreateGroupRule201Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Информация о правиле |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getGroupRules"></a>
# **getGroupRules**
> GetGroupRules200Response getGroupRules(groupId, limit, offset)

Получение списка правил

Чтобы получить список правил в группе, отправьте GET запрос на &#x60;/api/v1/firewall/groups/{group_id}/rules&#x60;

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.FirewallApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    FirewallApi apiInstance = new FirewallApi(defaultClient);
    String groupId = "groupId_example"; // String | Идентификатор группы правил
    Integer limit = 100; // Integer | Обозначает количество записей, которое необходимо вернуть.
    Integer offset = 0; // Integer | Указывает на смещение относительно начала списка.
    try {
      GetGroupRules200Response result = apiInstance.getGroupRules(groupId, limit, offset);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling FirewallApi#getGroupRules");
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
| **groupId** | **String**| Идентификатор группы правил | |
| **limit** | **Integer**| Обозначает количество записей, которое необходимо вернуть. | [optional] [default to 100] |
| **offset** | **Integer**| Указывает на смещение относительно начала списка. | [optional] [default to 0] |

### Return type

[**GetGroupRules200Response**](GetGroupRules200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Список правил |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getGroups"></a>
# **getGroups**
> GetGroups200Response getGroups(limit, offset)

Получение групп правил

Чтобы получить групп правил для аккаунта, отправьте GET запрос на &#x60;/api/v1/firewall/groups&#x60;

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.FirewallApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    FirewallApi apiInstance = new FirewallApi(defaultClient);
    Integer limit = 100; // Integer | Обозначает количество записей, которое необходимо вернуть.
    Integer offset = 0; // Integer | Указывает на смещение относительно начала списка.
    try {
      GetGroups200Response result = apiInstance.getGroups(limit, offset);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling FirewallApi#getGroups");
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

[**GetGroups200Response**](GetGroups200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Список групп правил |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getRulesForResource"></a>
# **getRulesForResource**
> GetGroups200Response getRulesForResource(resourceId, resourceType, limit, offset)

Получение групп правил для ресурса

Чтобы получить список групп правил, с которыми слинкован ресурс, отправьте GET запрос на &#x60;/api/v1/firewall/service/{resource_type}/{resource_id}&#x60;

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.FirewallApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    FirewallApi apiInstance = new FirewallApi(defaultClient);
    String resourceId = "resourceId_example"; // String | Идентификатор ресурса
    ResourceType resourceType = ResourceType.fromValue("server"); // ResourceType | 
    Integer limit = 100; // Integer | Обозначает количество записей, которое необходимо вернуть.
    Integer offset = 0; // Integer | Указывает на смещение относительно начала списка.
    try {
      GetGroups200Response result = apiInstance.getRulesForResource(resourceId, resourceType, limit, offset);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling FirewallApi#getRulesForResource");
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
| **resourceId** | **String**| Идентификатор ресурса | |
| **resourceType** | [**ResourceType**](.md)|  | [enum: server] |
| **limit** | **Integer**| Обозначает количество записей, которое необходимо вернуть. | [optional] [default to 100] |
| **offset** | **Integer**| Указывает на смещение относительно начала списка. | [optional] [default to 0] |

### Return type

[**GetGroups200Response**](GetGroups200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Список групп правил |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="updateGroup"></a>
# **updateGroup**
> CreateGroup201Response updateGroup(groupId, firewallGroupInAPI)

Обновление группы правил

Чтобы изменить группу правил, отправьте PATCH запрос на &#x60;/api/v1/firewall/groups/{group_id}&#x60;

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.FirewallApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    FirewallApi apiInstance = new FirewallApi(defaultClient);
    String groupId = "groupId_example"; // String | Идентификатор группы правил
    FirewallGroupInAPI firewallGroupInAPI = new FirewallGroupInAPI(); // FirewallGroupInAPI | 
    try {
      CreateGroup201Response result = apiInstance.updateGroup(groupId, firewallGroupInAPI);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling FirewallApi#updateGroup");
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
| **groupId** | **String**| Идентификатор группы правил | |
| **firewallGroupInAPI** | [**FirewallGroupInAPI**](FirewallGroupInAPI.md)|  | |

### Return type

[**CreateGroup201Response**](CreateGroup201Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Группа правил обновлена |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="updateGroupRule"></a>
# **updateGroupRule**
> CreateGroupRule201Response updateGroupRule(groupId, ruleId, firewallRuleInAPI)

Обновление firewall правила

Чтобы изменить правило, отправьте PATCH запрос на &#x60;/api/v1/firewall/groups/{group_id}/rules/{rule_id}&#x60;

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.FirewallApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    FirewallApi apiInstance = new FirewallApi(defaultClient);
    String groupId = "groupId_example"; // String | Идентификатор группы правил
    String ruleId = "ruleId_example"; // String | Идентификатор правила
    FirewallRuleInAPI firewallRuleInAPI = new FirewallRuleInAPI(); // FirewallRuleInAPI | 
    try {
      CreateGroupRule201Response result = apiInstance.updateGroupRule(groupId, ruleId, firewallRuleInAPI);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling FirewallApi#updateGroupRule");
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
| **groupId** | **String**| Идентификатор группы правил | |
| **ruleId** | **String**| Идентификатор правила | |
| **firewallRuleInAPI** | [**FirewallRuleInAPI**](FirewallRuleInAPI.md)|  | |

### Return type

[**CreateGroupRule201Response**](CreateGroupRule201Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Правило обновлено |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

