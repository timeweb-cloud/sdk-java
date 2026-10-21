# AiAgentSkillsApi

All URIs are relative to *https://api.timeweb.cloud*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**applyAgentSkillCatalogUpdate**](AiAgentSkillsApi.md#applyAgentSkillCatalogUpdate) | **POST** /api/v1/cloud-ai/skills/{skill_id}/apply-catalog-update | Применение обновления навыка из каталога |
| [**connectAgentSkillsToAgent**](AiAgentSkillsApi.md#connectAgentSkillsToAgent) | **POST** /api/v1/cloud-ai/agents/{agent_id}/skills/connect | Подключение нескольких навыков к агенту |
| [**createAgentSkill**](AiAgentSkillsApi.md#createAgentSkill) | **POST** /api/v1/cloud-ai/skills | Создание навыка |
| [**deleteAgentSkill**](AiAgentSkillsApi.md#deleteAgentSkill) | **DELETE** /api/v1/cloud-ai/skills/{skill_id} | Удаление навыка |
| [**disconnectAgentSkillFromAgent**](AiAgentSkillsApi.md#disconnectAgentSkillFromAgent) | **DELETE** /api/v1/cloud-ai/agents/{agent_id}/skills/{skill_id} | Отключение навыка от агента |
| [**duplicateAgentSkill**](AiAgentSkillsApi.md#duplicateAgentSkill) | **POST** /api/v1/cloud-ai/skills/{skill_id}/duplicate | Копирование навыка |
| [**getAgentSkill**](AiAgentSkillsApi.md#getAgentSkill) | **GET** /api/v1/cloud-ai/skills/{skill_id} | Получение навыка |
| [**getAgentSkillCatalog**](AiAgentSkillsApi.md#getAgentSkillCatalog) | **GET** /api/v1/cloud-ai/skill-catalog | Получение каталога навыков |
| [**getAgentSkillLibrary**](AiAgentSkillsApi.md#getAgentSkillLibrary) | **GET** /api/v1/cloud-ai/skills | Получение списка навыков |
| [**getAgentSkillVersion**](AiAgentSkillsApi.md#getAgentSkillVersion) | **GET** /api/v1/cloud-ai/skills/{skill_id}/versions/{version} | Получение версии навыка |
| [**getAgentSkillVersions**](AiAgentSkillsApi.md#getAgentSkillVersions) | **GET** /api/v1/cloud-ai/skills/{skill_id}/versions | Получение истории версий навыка |
| [**getAgentSkillsForAgent**](AiAgentSkillsApi.md#getAgentSkillsForAgent) | **GET** /api/v1/cloud-ai/agents/{agent_id}/skills | Получение навыков агента |
| [**installAgentSkillFromCatalog**](AiAgentSkillsApi.md#installAgentSkillFromCatalog) | **POST** /api/v1/cloud-ai/skill-catalog/{catalog_item_id}/install | Установка навыка из каталога |
| [**installAndConnectAgentSkill**](AiAgentSkillsApi.md#installAndConnectAgentSkill) | **POST** /api/v1/cloud-ai/agents/{agent_id}/skills/install-and-connect | Установка навыка из каталога и включение у агента |
| [**rollbackAgentSkillVersion**](AiAgentSkillsApi.md#rollbackAgentSkillVersion) | **POST** /api/v1/cloud-ai/skills/{skill_id}/versions/{version}/rollback | Переключение навыка на версию из истории |
| [**setAgentSkillForAgent**](AiAgentSkillsApi.md#setAgentSkillForAgent) | **PUT** /api/v1/cloud-ai/agents/{agent_id}/skills/{skill_id} | Включение и выключение навыка у агента |
| [**syncAgentSkillAgentsVersions**](AiAgentSkillsApi.md#syncAgentSkillAgentsVersions) | **POST** /api/v1/cloud-ai/skills/{skill_id}/agents/sync-versions | Перевод всех агентов навыка на текущую версию |
| [**syncAgentSkillVersionsForAgent**](AiAgentSkillsApi.md#syncAgentSkillVersionsForAgent) | **POST** /api/v1/cloud-ai/agents/{agent_id}/skills/sync-versions | Перевод всех навыков агента на текущие версии |
| [**updateAgentSkill**](AiAgentSkillsApi.md#updateAgentSkill) | **PATCH** /api/v1/cloud-ai/skills/{skill_id} | Изменение навыка |
| [**updateAgentSkillVersionForAgent**](AiAgentSkillsApi.md#updateAgentSkillVersionForAgent) | **POST** /api/v1/cloud-ai/agents/{agent_id}/skills/{skill_id}/update-version | Перевод агента на текущую версию навыка |


<a id="applyAgentSkillCatalogUpdate"></a>
# **applyAgentSkillCatalogUpdate**
> CreateAgentSkill201Response applyAgentSkillCatalogUpdate(skillId)

Применение обновления навыка из каталога

Чтобы применить к установленному навыку новую версию из каталога, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/skills/{skill_id}/apply-catalog-update&#x60;.  Текст «когда применять» и инструкция заменяются каталожными, при этом создаётся новая версия: прежний текст, в том числе ваши правки, остаётся в истории. Название навыка не меняется. Если обновления нет, ничего не меняется (&#x60;meta.version_created&#x60; — &#x60;false&#x60;).  Для навыка, установленного не из каталога, вернётся 409 с &#x60;error_code&#x60; &#x60;skill_is_not_installed_from_catalog&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.AiAgentSkillsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    AiAgentSkillsApi apiInstance = new AiAgentSkillsApi(defaultClient);
    Integer skillId = 12; // Integer | ID навыка
    try {
      CreateAgentSkill201Response result = apiInstance.applyAgentSkillCatalogUpdate(skillId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AiAgentSkillsApi#applyAgentSkillCatalogUpdate");
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
| **skillId** | **Integer**| ID навыка | |

### Return type

[**CreateAgentSkill201Response**](CreateAgentSkill201Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Объект JSON c ключами &#x60;skill&#x60; и &#x60;meta&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **409** | Конфликт |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="connectAgentSkillsToAgent"></a>
# **connectAgentSkillsToAgent**
> GetAgentSkillsForAgent200Response connectAgentSkillsToAgent(agentId, connectSkills)

Подключение нескольких навыков к агенту

Чтобы подключить и сразу включить у агента несколько навыков, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/agents/{agent_id}/skills/connect&#x60;.  Навыки, которых нет в аккаунте, пропускаются без ошибки. Уже подключённые навыки включаются; если для них передана версия, агент закрепляется на ней. В ответе — только навыки, которые удалось подключить.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.AiAgentSkillsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    AiAgentSkillsApi apiInstance = new AiAgentSkillsApi(defaultClient);
    Integer agentId = 113191; // Integer | ID агента
    ConnectSkills connectSkills = new ConnectSkills(); // ConnectSkills | 
    try {
      GetAgentSkillsForAgent200Response result = apiInstance.connectAgentSkillsToAgent(agentId, connectSkills);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AiAgentSkillsApi#connectAgentSkillsToAgent");
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
| **connectSkills** | [**ConnectSkills**](ConnectSkills.md)|  | |

### Return type

[**GetAgentSkillsForAgent200Response**](GetAgentSkillsForAgent200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Объект JSON c ключами &#x60;agent_skills&#x60; и &#x60;meta&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="createAgentSkill"></a>
# **createAgentSkill**
> CreateAgentSkill201Response createAgentSkill(createSkill)

Создание навыка

Чтобы создать навык, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/skills&#x60;.  Навык создаётся с версией 1 и ни к одному агенту не подключается. Предупреждения о качестве текста приходят в &#x60;meta.warnings&#x60; и не мешают сохранению. Скрипты передаются в теле запроса. Их общий размер — до 10 МБ, а всё тело запроса — до 16 МБ; запрос с телом больше отклоняется до проверки.  Если навык с таким названием уже есть, вернётся 409 с &#x60;error_code&#x60; &#x60;skill_name_already_exists&#x60;. Если скрипты не прошли проверку (больше 20 файлов, повторяющиеся имена, общий размер больше 10 МБ, файл без содержимого), вернётся 400 с &#x60;error_code&#x60; &#x60;skill_scripts_invalid&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.AiAgentSkillsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    AiAgentSkillsApi apiInstance = new AiAgentSkillsApi(defaultClient);
    CreateSkill createSkill = new CreateSkill(); // CreateSkill | 
    try {
      CreateAgentSkill201Response result = apiInstance.createAgentSkill(createSkill);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AiAgentSkillsApi#createAgentSkill");
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
| **createSkill** | [**CreateSkill**](CreateSkill.md)|  | |

### Return type

[**CreateAgentSkill201Response**](CreateAgentSkill201Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Объект JSON c ключами &#x60;skill&#x60; и &#x60;meta&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **409** | Конфликт |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="deleteAgentSkill"></a>
# **deleteAgentSkill**
> deleteAgentSkill(skillId)

Удаление навыка

Чтобы удалить навык, отправьте DELETE-запрос на &#x60;/api/v1/cloud-ai/skills/{skill_id}&#x60;.  Навык отключается от всех агентов, история версий и скрипты удаляются. Срабатывания навыка под старыми ответами агентов сохраняются.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.AiAgentSkillsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    AiAgentSkillsApi apiInstance = new AiAgentSkillsApi(defaultClient);
    Integer skillId = 12; // Integer | ID навыка
    try {
      apiInstance.deleteAgentSkill(skillId);
    } catch (ApiException e) {
      System.err.println("Exception when calling AiAgentSkillsApi#deleteAgentSkill");
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
| **skillId** | **Integer**| ID навыка | |

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
| **204** | Навык удалён |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="disconnectAgentSkillFromAgent"></a>
# **disconnectAgentSkillFromAgent**
> disconnectAgentSkillFromAgent(agentId, skillId)

Отключение навыка от агента

Чтобы полностью отключить навык от агента, отправьте DELETE-запрос на &#x60;/api/v1/cloud-ai/agents/{agent_id}/skills/{skill_id}&#x60;.  Закреплённая версия при этом не сохраняется. Чтобы выключить навык на время, используйте PUT-запрос с &#x60;is_enabled: false&#x60;. Если навык не подключён к агенту, вернётся 404 с &#x60;error_code&#x60; &#x60;agent_skill_link_not_found&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.AiAgentSkillsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    AiAgentSkillsApi apiInstance = new AiAgentSkillsApi(defaultClient);
    Integer agentId = 113191; // Integer | ID агента
    Integer skillId = 12; // Integer | ID навыка
    try {
      apiInstance.disconnectAgentSkillFromAgent(agentId, skillId);
    } catch (ApiException e) {
      System.err.println("Exception when calling AiAgentSkillsApi#disconnectAgentSkillFromAgent");
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
| **skillId** | **Integer**| ID навыка | |

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
| **204** | Навык отключён от агента |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="duplicateAgentSkill"></a>
# **duplicateAgentSkill**
> CreateAgentSkill201Response duplicateAgentSkill(skillId)

Копирование навыка

Чтобы создать копию навыка, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/skills/{skill_id}/duplicate&#x60;.  Копия получает свободное название вида «Название (копия)» или «Название (копия 2)», текст и скрипты текущей версии оригинала и собственную историю с версии 1. К агентам копия не подключается. Копия навыка из каталога становится обычным навыком аккаунта.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.AiAgentSkillsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    AiAgentSkillsApi apiInstance = new AiAgentSkillsApi(defaultClient);
    Integer skillId = 12; // Integer | ID навыка
    try {
      CreateAgentSkill201Response result = apiInstance.duplicateAgentSkill(skillId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AiAgentSkillsApi#duplicateAgentSkill");
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
| **skillId** | **Integer**| ID навыка | |

### Return type

[**CreateAgentSkill201Response**](CreateAgentSkill201Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Объект JSON c ключами &#x60;skill&#x60; и &#x60;meta&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **409** | Конфликт |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getAgentSkill"></a>
# **getAgentSkill**
> GetAgentSkill200Response getAgentSkill(skillId)

Получение навыка

Чтобы получить карточку навыка, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/skills/{skill_id}&#x60;.  В карточке есть скрипты текущей версии (без содержимого), счётчик версий и агенты, к которым подключён навык.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.AiAgentSkillsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    AiAgentSkillsApi apiInstance = new AiAgentSkillsApi(defaultClient);
    Integer skillId = 12; // Integer | ID навыка
    try {
      GetAgentSkill200Response result = apiInstance.getAgentSkill(skillId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AiAgentSkillsApi#getAgentSkill");
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
| **skillId** | **Integer**| ID навыка | |

### Return type

[**GetAgentSkill200Response**](GetAgentSkill200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Объект JSON c ключом &#x60;skill&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getAgentSkillCatalog"></a>
# **getAgentSkillCatalog**
> GetAgentSkillCatalog200Response getAgentSkillCatalog()

Получение каталога навыков

Чтобы получить каталог готовых навыков Timeweb Cloud, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/skill-catalog&#x60;.  Навыки приходят в порядке показа, сгруппированные по &#x60;category&#x60;. Инструкция возвращается полностью, чтобы её можно было прочитать до установки. Для установленных навыков заполнены &#x60;is_installed&#x60;, &#x60;installed_skill_id&#x60;, &#x60;is_modified&#x60; и &#x60;has_catalog_update&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.AiAgentSkillsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    AiAgentSkillsApi apiInstance = new AiAgentSkillsApi(defaultClient);
    try {
      GetAgentSkillCatalog200Response result = apiInstance.getAgentSkillCatalog();
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AiAgentSkillsApi#getAgentSkillCatalog");
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

[**GetAgentSkillCatalog200Response**](GetAgentSkillCatalog200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Объект JSON c ключами &#x60;skill_catalog_items&#x60; и &#x60;meta&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getAgentSkillLibrary"></a>
# **getAgentSkillLibrary**
> GetAgentSkillLibrary200Response getAgentSkillLibrary(search)

Получение списка навыков

Чтобы получить навыки аккаунта, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/skills&#x60;.  Сначала идут недавно изменённые. Параметр &#x60;search&#x60; ищет по названию и по тексту инструкции.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.AiAgentSkillsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    AiAgentSkillsApi apiInstance = new AiAgentSkillsApi(defaultClient);
    String search = "возврат"; // String | Поиск по названию навыка и по тексту инструкции
    try {
      GetAgentSkillLibrary200Response result = apiInstance.getAgentSkillLibrary(search);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AiAgentSkillsApi#getAgentSkillLibrary");
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
| **search** | **String**| Поиск по названию навыка и по тексту инструкции | [optional] |

### Return type

[**GetAgentSkillLibrary200Response**](GetAgentSkillLibrary200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Объект JSON c ключами &#x60;skills&#x60; и &#x60;meta&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getAgentSkillVersion"></a>
# **getAgentSkillVersion**
> GetAgentSkillVersion200Response getAgentSkillVersion(skillId, version)

Получение версии навыка

Чтобы получить текст конкретной версии навыка, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/skills/{skill_id}/versions/{version}&#x60;.  Номер версии сработавшего навыка есть в срабатываниях под ответами агента.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.AiAgentSkillsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    AiAgentSkillsApi apiInstance = new AiAgentSkillsApi(defaultClient);
    Integer skillId = 12; // Integer | ID навыка
    Integer version = 2; // Integer | Номер версии навыка
    try {
      GetAgentSkillVersion200Response result = apiInstance.getAgentSkillVersion(skillId, version);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AiAgentSkillsApi#getAgentSkillVersion");
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
| **skillId** | **Integer**| ID навыка | |
| **version** | **Integer**| Номер версии навыка | |

### Return type

[**GetAgentSkillVersion200Response**](GetAgentSkillVersion200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Объект JSON c ключом &#x60;skill_version&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getAgentSkillVersions"></a>
# **getAgentSkillVersions**
> GetAgentSkillVersions200Response getAgentSkillVersions(skillId)

Получение истории версий навыка

Чтобы получить историю версий навыка, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/skills/{skill_id}/versions&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.AiAgentSkillsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    AiAgentSkillsApi apiInstance = new AiAgentSkillsApi(defaultClient);
    Integer skillId = 12; // Integer | ID навыка
    try {
      GetAgentSkillVersions200Response result = apiInstance.getAgentSkillVersions(skillId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AiAgentSkillsApi#getAgentSkillVersions");
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
| **skillId** | **Integer**| ID навыка | |

### Return type

[**GetAgentSkillVersions200Response**](GetAgentSkillVersions200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Объект JSON c ключами &#x60;skill_versions&#x60; и &#x60;meta&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="getAgentSkillsForAgent"></a>
# **getAgentSkillsForAgent**
> GetAgentSkillsForAgent200Response getAgentSkillsForAgent(agentId)

Получение навыков агента

Чтобы получить навыки агента, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/agents/{agent_id}/skills&#x60;.  В ответе все навыки аккаунта: неподключённые приходят с &#x60;is_connected: false&#x60;. &#x60;has_newer_version: true&#x60; означает, что агент закреплён на устаревшей версии.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.AiAgentSkillsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    AiAgentSkillsApi apiInstance = new AiAgentSkillsApi(defaultClient);
    Integer agentId = 113191; // Integer | ID агента
    try {
      GetAgentSkillsForAgent200Response result = apiInstance.getAgentSkillsForAgent(agentId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AiAgentSkillsApi#getAgentSkillsForAgent");
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

[**GetAgentSkillsForAgent200Response**](GetAgentSkillsForAgent200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Объект JSON c ключами &#x60;agent_skills&#x60; и &#x60;meta&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="installAgentSkillFromCatalog"></a>
# **installAgentSkillFromCatalog**
> InstallAgentSkillFromCatalog200Response installAgentSkillFromCatalog(catalogItemId)

Установка навыка из каталога

Чтобы установить навык из каталога в библиотеку аккаунта, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/skill-catalog/{catalog_item_id}/install&#x60;.  Повторная установка не создаёт копию, а возвращает уже установленный навык с &#x60;meta.already_installed: true&#x60;. Если название занято вашим навыком, к нему добавляется «(копия)». Чтобы сразу включить навык у агента, используйте &#x60;POST /api/v1/cloud-ai/agents/{agent_id}/skills/install-and-connect&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.AiAgentSkillsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    AiAgentSkillsApi apiInstance = new AiAgentSkillsApi(defaultClient);
    Integer catalogItemId = 3; // Integer | ID навыка в каталоге
    try {
      InstallAgentSkillFromCatalog200Response result = apiInstance.installAgentSkillFromCatalog(catalogItemId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AiAgentSkillsApi#installAgentSkillFromCatalog");
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
| **catalogItemId** | **Integer**| ID навыка в каталоге | |

### Return type

[**InstallAgentSkillFromCatalog200Response**](InstallAgentSkillFromCatalog200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Объект JSON c ключами &#x60;skill&#x60; и &#x60;meta&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **409** | Конфликт |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="installAndConnectAgentSkill"></a>
# **installAndConnectAgentSkill**
> InstallAndConnectAgentSkill200Response installAndConnectAgentSkill(agentId, installAndConnectSkill)

Установка навыка из каталога и включение у агента

Чтобы установить навык из каталога и сразу включить его у агента, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/agents/{agent_id}/skills/install-and-connect&#x60;.  Если навык уже установлен, копия не создаётся (&#x60;meta.already_installed: true&#x60;), навык просто включается у агента.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.AiAgentSkillsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    AiAgentSkillsApi apiInstance = new AiAgentSkillsApi(defaultClient);
    Integer agentId = 113191; // Integer | ID агента
    InstallAndConnectSkill installAndConnectSkill = new InstallAndConnectSkill(); // InstallAndConnectSkill | 
    try {
      InstallAndConnectAgentSkill200Response result = apiInstance.installAndConnectAgentSkill(agentId, installAndConnectSkill);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AiAgentSkillsApi#installAndConnectAgentSkill");
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
| **installAndConnectSkill** | [**InstallAndConnectSkill**](InstallAndConnectSkill.md)|  | |

### Return type

[**InstallAndConnectAgentSkill200Response**](InstallAndConnectAgentSkill200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Объект JSON c ключами &#x60;agent_skill&#x60; и &#x60;meta&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **409** | Конфликт |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="rollbackAgentSkillVersion"></a>
# **rollbackAgentSkillVersion**
> CreateAgentSkill201Response rollbackAgentSkillVersion(skillId, version)

Переключение навыка на версию из истории

Чтобы сделать версию из истории текущей, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/skills/{skill_id}/versions/{version}/rollback&#x60;.  Новая версия не создаётся (&#x60;meta.version_created&#x60; — &#x60;false&#x60;), название навыка не меняется. Агенты остаются на своих закреплённых версиях. Если выбранная версия уже текущая, ничего не меняется.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.AiAgentSkillsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    AiAgentSkillsApi apiInstance = new AiAgentSkillsApi(defaultClient);
    Integer skillId = 12; // Integer | ID навыка
    Integer version = 2; // Integer | Номер версии навыка
    try {
      CreateAgentSkill201Response result = apiInstance.rollbackAgentSkillVersion(skillId, version);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AiAgentSkillsApi#rollbackAgentSkillVersion");
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
| **skillId** | **Integer**| ID навыка | |
| **version** | **Integer**| Номер версии навыка | |

### Return type

[**CreateAgentSkill201Response**](CreateAgentSkill201Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Объект JSON c ключами &#x60;skill&#x60; и &#x60;meta&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="setAgentSkillForAgent"></a>
# **setAgentSkillForAgent**
> SetAgentSkillForAgent200Response setAgentSkillForAgent(agentId, skillId, setAgentSkill)

Включение и выключение навыка у агента

Чтобы включить или выключить навык у агента, отправьте PUT-запрос на &#x60;/api/v1/cloud-ai/agents/{agent_id}/skills/{skill_id}&#x60;.  Первое включение подключает навык и закрепляет агента на текущей версии навыка или на версии из &#x60;version&#x60;. Выключение сохраняет закреплённую версию, поэтому повторное включение вернёт то же поведение. Изменения действуют сразу. Выключение неподключённого навыка ничего не меняет.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.AiAgentSkillsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    AiAgentSkillsApi apiInstance = new AiAgentSkillsApi(defaultClient);
    Integer agentId = 113191; // Integer | ID агента
    Integer skillId = 12; // Integer | ID навыка
    SetAgentSkill setAgentSkill = new SetAgentSkill(); // SetAgentSkill | 
    try {
      SetAgentSkillForAgent200Response result = apiInstance.setAgentSkillForAgent(agentId, skillId, setAgentSkill);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AiAgentSkillsApi#setAgentSkillForAgent");
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
| **skillId** | **Integer**| ID навыка | |
| **setAgentSkill** | [**SetAgentSkill**](SetAgentSkill.md)|  | |

### Return type

[**SetAgentSkillForAgent200Response**](SetAgentSkillForAgent200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Объект JSON c ключом &#x60;agent_skill&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="syncAgentSkillAgentsVersions"></a>
# **syncAgentSkillAgentsVersions**
> SyncAgentSkillAgentsVersions200Response syncAgentSkillAgentsVersions(skillId)

Перевод всех агентов навыка на текущую версию

Чтобы перевести всех агентов, у которых включён навык, на его текущую версию, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/skills/{skill_id}/agents/sync-versions&#x60;.  Агенты, у которых навык выключен, остаются на своих версиях. Сколько агентов переведено, видно в &#x60;meta.updated_count&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.AiAgentSkillsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    AiAgentSkillsApi apiInstance = new AiAgentSkillsApi(defaultClient);
    Integer skillId = 12; // Integer | ID навыка
    try {
      SyncAgentSkillAgentsVersions200Response result = apiInstance.syncAgentSkillAgentsVersions(skillId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AiAgentSkillsApi#syncAgentSkillAgentsVersions");
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
| **skillId** | **Integer**| ID навыка | |

### Return type

[**SyncAgentSkillAgentsVersions200Response**](SyncAgentSkillAgentsVersions200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Объект JSON c ключами &#x60;skill&#x60; и &#x60;meta&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="syncAgentSkillVersionsForAgent"></a>
# **syncAgentSkillVersionsForAgent**
> SyncAgentSkillVersionsForAgent200Response syncAgentSkillVersionsForAgent(agentId)

Перевод всех навыков агента на текущие версии

Чтобы перевести все включённые у агента навыки на их текущие версии, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/agents/{agent_id}/skills/sync-versions&#x60;.  В ответе все навыки аккаунта с состоянием у агента, как в списке навыков агента. Сколько навыков переведено, видно в &#x60;updated_count&#x60; в корне ответа.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.AiAgentSkillsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    AiAgentSkillsApi apiInstance = new AiAgentSkillsApi(defaultClient);
    Integer agentId = 113191; // Integer | ID агента
    try {
      SyncAgentSkillVersionsForAgent200Response result = apiInstance.syncAgentSkillVersionsForAgent(agentId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AiAgentSkillsApi#syncAgentSkillVersionsForAgent");
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

[**SyncAgentSkillVersionsForAgent200Response**](SyncAgentSkillVersionsForAgent200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Объект JSON c ключами &#x60;agent_skills&#x60;, &#x60;meta&#x60; и &#x60;updated_count&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="updateAgentSkill"></a>
# **updateAgentSkill**
> CreateAgentSkill201Response updateAgentSkill(skillId, updateSkill)

Изменение навыка

Чтобы изменить навык, отправьте PATCH-запрос на &#x60;/api/v1/cloud-ai/skills/{skill_id}&#x60;, передав только изменяемые поля.  Изменение «когда применять», инструкции или скриптов создаёт новую версию. Переименование и сохранение без изменений версию не создают (&#x60;meta.version_created&#x60;). Подключённые агенты остаются на своих версиях. Сколько агентов затронуто, видно в &#x60;meta.affected_agents_count&#x60;. Если в истории уже 50 версий, самая старая свободная версия удаляется (&#x60;meta.pruned_versions&#x60;). Скрипты передаются в теле запроса. Их общий размер — до 10 МБ, а всё тело запроса — до 16 МБ; запрос с телом больше отклоняется до проверки.  Возможные ошибки 409: &#x60;skill_name_already_exists&#x60; — название занято; &#x60;skill_versions_limit_exceeded&#x60; — все 50 версий заняты текущей и закреплёнными за агентами; &#x60;skill_was_modified_concurrently&#x60; — навык одновременно изменён другим запросом, повторите сохранение.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.AiAgentSkillsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    AiAgentSkillsApi apiInstance = new AiAgentSkillsApi(defaultClient);
    Integer skillId = 12; // Integer | ID навыка
    UpdateSkill updateSkill = new UpdateSkill(); // UpdateSkill | 
    try {
      CreateAgentSkill201Response result = apiInstance.updateAgentSkill(skillId, updateSkill);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AiAgentSkillsApi#updateAgentSkill");
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
| **skillId** | **Integer**| ID навыка | |
| **updateSkill** | [**UpdateSkill**](UpdateSkill.md)|  | |

### Return type

[**CreateAgentSkill201Response**](CreateAgentSkill201Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Объект JSON c ключами &#x60;skill&#x60; и &#x60;meta&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **409** | Конфликт |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

<a id="updateAgentSkillVersionForAgent"></a>
# **updateAgentSkillVersionForAgent**
> SetAgentSkillForAgent200Response updateAgentSkillVersionForAgent(agentId, skillId)

Перевод агента на текущую версию навыка

Чтобы перевести агента на текущую версию навыка, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/agents/{agent_id}/skills/{skill_id}/update-version&#x60;.  Если навык не подключён к агенту, вернётся 404 с &#x60;error_code&#x60; &#x60;agent_skill_link_not_found&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.AiAgentSkillsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: Bearer
    HttpBearerAuth Bearer = (HttpBearerAuth) defaultClient.getAuthentication("Bearer");
    Bearer.setBearerToken("BEARER TOKEN");

    AiAgentSkillsApi apiInstance = new AiAgentSkillsApi(defaultClient);
    Integer agentId = 113191; // Integer | ID агента
    Integer skillId = 12; // Integer | ID навыка
    try {
      SetAgentSkillForAgent200Response result = apiInstance.updateAgentSkillVersionForAgent(agentId, skillId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AiAgentSkillsApi#updateAgentSkillVersionForAgent");
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
| **skillId** | **Integer**| ID навыка | |

### Return type

[**SetAgentSkillForAgent200Response**](SetAgentSkillForAgent200Response.md)

### Authorization

[Bearer](../README.md#Bearer)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Объект JSON c ключом &#x60;agent_skill&#x60; |  -  |
| **400** | Некорректный запрос |  -  |
| **401** | Не авторизован |  -  |
| **403** | Запрещено |  -  |
| **404** | Не найдено |  -  |
| **429** | Слишком много запросов |  -  |
| **500** | Внутренняя ошибка сервера |  -  |

