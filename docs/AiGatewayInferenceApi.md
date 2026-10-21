# AiGatewayInferenceApi

All URIs are relative to *https://api.timeweb.cloud*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**cancelAiGatewayBatch**](AiGatewayInferenceApi.md#cancelAiGatewayBatch) | **POST** /v1/batches/{batch_id}/cancel | Отмена батча |
| [**cancelAiGatewayResponse**](AiGatewayInferenceApi.md#cancelAiGatewayResponse) | **POST** /v1/responses/{response_id}/cancel | Отмена фонового ответа (Responses API) |
| [**createAiGatewayBatch**](AiGatewayInferenceApi.md#createAiGatewayBatch) | **POST** /v1/batches | Создание батча |
| [**createAiGatewayChatCompletion**](AiGatewayInferenceApi.md#createAiGatewayChatCompletion) | **POST** /v1/chat/completions | Генерация ответа в диалоге (Chat Completions) |
| [**createAiGatewayCompletion**](AiGatewayInferenceApi.md#createAiGatewayCompletion) | **POST** /v1/completions | Продолжение текста (Completions) |
| [**createAiGatewayEmbedding**](AiGatewayInferenceApi.md#createAiGatewayEmbedding) | **POST** /v1/embeddings | Получение эмбеддингов |
| [**createAiGatewayImage**](AiGatewayInferenceApi.md#createAiGatewayImage) | **POST** /v1/images/generations | Генерация изображений |
| [**createAiGatewayImageEdit**](AiGatewayInferenceApi.md#createAiGatewayImageEdit) | **POST** /v1/images/edits | Редактирование изображений |
| [**createAiGatewayResponse**](AiGatewayInferenceApi.md#createAiGatewayResponse) | **POST** /v1/responses | Создание ответа (Responses API) |
| [**createAiGatewaySpeech**](AiGatewayInferenceApi.md#createAiGatewaySpeech) | **POST** /v1/audio/speech | Синтез речи |
| [**createAiGatewayTranscription**](AiGatewayInferenceApi.md#createAiGatewayTranscription) | **POST** /v1/audio/transcriptions | Распознавание речи |
| [**deleteAiGatewayFile**](AiGatewayInferenceApi.md#deleteAiGatewayFile) | **DELETE** /v1/files/{file_id} | Удаление файла |
| [**deleteAiGatewayResponse**](AiGatewayInferenceApi.md#deleteAiGatewayResponse) | **DELETE** /v1/responses/{response_id} | Удаление ответа (Responses API) |
| [**downloadAiGatewayFileContent**](AiGatewayInferenceApi.md#downloadAiGatewayFileContent) | **GET** /v1/files/{file_id}/content | Скачивание файла |
| [**getAiGatewayBatch**](AiGatewayInferenceApi.md#getAiGatewayBatch) | **GET** /v1/batches/{batch_id} | Получение батча |
| [**getAiGatewayBatches**](AiGatewayInferenceApi.md#getAiGatewayBatches) | **GET** /v1/batches | Получение списка батчей |
| [**getAiGatewayFile**](AiGatewayInferenceApi.md#getAiGatewayFile) | **GET** /v1/files/{file_id} | Получение информации о файле |
| [**getAiGatewayModels**](AiGatewayInferenceApi.md#getAiGatewayModels) | **GET** /v1/models | Получение списка моделей AI Gateway |
| [**getAiGatewayResponse**](AiGatewayInferenceApi.md#getAiGatewayResponse) | **GET** /v1/responses/{response_id} | Получение ответа (Responses API) |
| [**uploadAiGatewayFile**](AiGatewayInferenceApi.md#uploadAiGatewayFile) | **POST** /v1/files | Загрузка файла |


<a id="cancelAiGatewayBatch"></a>
# **cancelAiGatewayBatch**
> AiGatewayBatch cancelAiGatewayBatch(batchId)

Отмена батча

Чтобы отменить батч, отправьте POST-запрос на &#x60;/v1/batches/{batch_id}/cancel&#x60;.  Батч переходит в статус &#x60;cancelling&#x60;, отмена выполняется асинхронно и может занять несколько минут; итоговый статус &#x60;cancelled&#x60; проверяйте через &#x60;GET /v1/batches/{batch_id}&#x60;. Пока отмена не завершилась, входной файл батча удалить нельзя.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.AiGatewayInferenceApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: AiGatewayKey
    HttpBearerAuth AiGatewayKey = (HttpBearerAuth) defaultClient.getAuthentication("AiGatewayKey");
    AiGatewayKey.setBearerToken("BEARER TOKEN");

    AiGatewayInferenceApi apiInstance = new AiGatewayInferenceApi(defaultClient);
    String batchId = "batch_abc123"; // String | ID батча
    try {
      AiGatewayBatch result = apiInstance.cancelAiGatewayBatch(batchId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AiGatewayInferenceApi#cancelAiGatewayBatch");
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
| **batchId** | **String**| ID батча | |

### Return type

[**AiGatewayBatch**](AiGatewayBatch.md)

### Authorization

[AiGatewayKey](../README.md#AiGatewayKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Отменяемый батч |  -  |
| **401** | Ключ не передан или имеет неверный формат |  -  |
| **403** | Ключ недействителен или аккаунт заблокирован |  -  |
| **404** | Батч не найден |  -  |
| **500** | Внутренняя ошибка или некорректный ответ провайдера модели. Повторите запрос позже |  -  |
| **503** | Сервис или провайдер модели временно недоступен. Повторите запрос позже |  -  |

<a id="cancelAiGatewayResponse"></a>
# **cancelAiGatewayResponse**
> AiGatewayResponse cancelAiGatewayResponse(responseId)

Отмена фонового ответа (Responses API)

Чтобы отменить ответ, запущенный с &#x60;\&quot;background\&quot;: true&#x60;, отправьте POST-запрос на &#x60;/v1/responses/{response_id}/cancel&#x60;.  Отмена может завершиться не сразу: итоговый статус &#x60;cancelled&#x60; проверяйте через &#x60;GET /v1/responses/{response_id}&#x60;. Ответ, созданный без &#x60;background&#x60;, отменить нельзя — вернётся ошибка 400. Неизвестный &#x60;response_id&#x60; также возвращает 400.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.AiGatewayInferenceApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: AiGatewayKey
    HttpBearerAuth AiGatewayKey = (HttpBearerAuth) defaultClient.getAuthentication("AiGatewayKey");
    AiGatewayKey.setBearerToken("BEARER TOKEN");

    AiGatewayInferenceApi apiInstance = new AiGatewayInferenceApi(defaultClient);
    String responseId = "resp_abc123"; // String | ID ответа Responses API
    try {
      AiGatewayResponse result = apiInstance.cancelAiGatewayResponse(responseId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AiGatewayInferenceApi#cancelAiGatewayResponse");
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
| **responseId** | **String**| ID ответа Responses API | |

### Return type

[**AiGatewayResponse**](AiGatewayResponse.md)

### Authorization

[AiGatewayKey](../README.md#AiGatewayKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Отменяемый ответ |  -  |
| **400** | Ответ нельзя отменить (создан без &#x60;background&#x60;) или не найден |  -  |
| **401** | Ключ не передан или имеет неверный формат |  -  |
| **403** | Ключ недействителен или аккаунт заблокирован |  -  |
| **500** | Внутренняя ошибка или некорректный ответ провайдера модели. Повторите запрос позже |  -  |

<a id="createAiGatewayBatch"></a>
# **createAiGatewayBatch**
> AiGatewayBatch createAiGatewayBatch(aiGatewayBatchCreateRequest)

Создание батча

Чтобы запустить асинхронную обработку входного файла, загруженного с &#x60;purpose&#x3D;batch&#x60;, отправьте POST-запрос на &#x60;/v1/batches&#x60;.  При создании батча его оценочная стоимость резервируется на балансе, фактическая стоимость списывается по цене Batch API после выполнения. Модели, эндпоинты и коэффициенты цены Batch API — в поле &#x60;batch&#x60; каталога моделей (&#x60;GET https://api.timeweb.cloud/api/v3/cloud-ai/models&#x60;). Batch API доступен только для ключей AI Gateway.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.AiGatewayInferenceApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: AiGatewayKey
    HttpBearerAuth AiGatewayKey = (HttpBearerAuth) defaultClient.getAuthentication("AiGatewayKey");
    AiGatewayKey.setBearerToken("BEARER TOKEN");

    AiGatewayInferenceApi apiInstance = new AiGatewayInferenceApi(defaultClient);
    AiGatewayBatchCreateRequest aiGatewayBatchCreateRequest = new AiGatewayBatchCreateRequest(); // AiGatewayBatchCreateRequest | 
    try {
      AiGatewayBatch result = apiInstance.createAiGatewayBatch(aiGatewayBatchCreateRequest);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AiGatewayInferenceApi#createAiGatewayBatch");
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
| **aiGatewayBatchCreateRequest** | [**AiGatewayBatchCreateRequest**](AiGatewayBatchCreateRequest.md)|  | |

### Return type

[**AiGatewayBatch**](AiGatewayBatch.md)

### Authorization

[AiGatewayKey](../README.md#AiGatewayKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Созданный батч |  -  |
| **400** | Некорректный запрос: неверный &#x60;input_file_id&#x60;, &#x60;endpoint&#x60; не совпадает с &#x60;url&#x60; строк файла, неверный &#x60;completion_window&#x60; |  -  |
| **401** | Ключ не передан или имеет неверный формат |  -  |
| **402** | Недостаточно средств на балансе: оценочная стоимость запроса больше доступного баланса (с учётом ещё не списанной стоимости недавних и выполняющихся запросов) |  -  |
| **403** | Ключ недействителен, аккаунт заблокирован или Batch API недоступен для ключа |  -  |
| **404** | Модель не найдена: неизвестный или не переданный &#x60;model&#x60; |  -  |
| **405** | Модель не поддерживает Batch API или этот эндпоинт |  -  |
| **410** | Модель снята с обслуживания |  -  |
| **422** | Исчерпан лимит расхода ключа |  -  |
| **429** | Достигнут лимит очереди Batch API для модели — дождитесь завершения ваших батчей и повторите запрос |  -  |
| **500** | Внутренняя ошибка или некорректный ответ провайдера модели. Повторите запрос позже |  -  |
| **503** | Сервис или провайдер модели временно недоступен. Повторите запрос позже |  -  |

<a id="createAiGatewayChatCompletion"></a>
# **createAiGatewayChatCompletion**
> AiGatewayChatCompletion createAiGatewayChatCompletion(requestBody)

Генерация ответа в диалоге (Chat Completions)

Чтобы получить следующий ответ модели в диалоге, отправьте POST-запрос на &#x60;/v1/chat/completions&#x60;.  Запрос не хранит состояние: передавайте всю историю диалога в &#x60;messages&#x60;. Поддерживаются вызов функций (&#x60;tools&#x60;), структурированный ответ (&#x60;response_format&#x60;) и изображения во входных сообщениях (для моделей с поддержкой зрения).  С &#x60;\&quot;stream\&quot;: true&#x60; ответ приходит потоком Server-Sent Events: события &#x60;chat.completion.chunk&#x60; в строках &#x60;data:&#x60;, в конце — &#x60;data: [DONE]&#x60;. С &#x60;\&quot;stream_options\&quot;: {\&quot;include_usage\&quot;: true}&#x60; последний чанк содержит расход токенов.  Если у ключа настроены запасные модели и запрошенная модель не ответила, запрос повторяется на запасных. Заголовок ответа &#x60;x-timeweb-attempted-fallbacks&#x60; показывает, сколько запасных моделей было опробовано.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.AiGatewayInferenceApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: AiGatewayKey
    HttpBearerAuth AiGatewayKey = (HttpBearerAuth) defaultClient.getAuthentication("AiGatewayKey");
    AiGatewayKey.setBearerToken("BEARER TOKEN");

    AiGatewayInferenceApi apiInstance = new AiGatewayInferenceApi(defaultClient);
    Map<String, Object> requestBody = null; // Map<String, Object> | 
    try {
      AiGatewayChatCompletion result = apiInstance.createAiGatewayChatCompletion(requestBody);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AiGatewayInferenceApi#createAiGatewayChatCompletion");
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
| **requestBody** | [**Map&lt;String, Object&gt;**](Object.md)|  | |

### Return type

[**AiGatewayChatCompletion**](AiGatewayChatCompletion.md)

### Authorization

[AiGatewayKey](../README.md#AiGatewayKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, text/event-stream

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Ответ модели или, с &#x60;\&quot;stream\&quot;: true&#x60;, поток чанков |  -  |
| **400** | Некорректный запрос: не переданы или неверны параметры (например, пустой &#x60;messages&#x60;), модель не поддерживает операцию (например, модель эмбеддингов) или временно недоступна |  -  |
| **401** | Ключ не передан или имеет неверный формат |  -  |
| **402** | Недостаточно средств на балансе: оценочная стоимость запроса больше доступного баланса (с учётом ещё не списанной стоимости недавних и выполняющихся запросов) |  -  |
| **403** | Ключ недействителен или аккаунт заблокирован |  -  |
| **404** | Модель не найдена: неизвестный или не переданный &#x60;model&#x60; |  -  |
| **410** | Модель снята с обслуживания |  -  |
| **422** | Исчерпан лимит расхода ключа |  -  |
| **429** | Превышен лимит запросов провайдера модели (текст ошибки — от провайдера). Повторите запрос позже |  -  |
| **500** | Внутренняя ошибка или некорректный ответ провайдера модели. Повторите запрос позже |  -  |
| **503** | Сервис или провайдер модели временно недоступен. Повторите запрос позже |  -  |

<a id="createAiGatewayCompletion"></a>
# **createAiGatewayCompletion**
> AiGatewayCompletion createAiGatewayCompletion(requestBody)

Продолжение текста (Completions)

Чтобы получить продолжение текста &#x60;prompt&#x60;, отправьте POST-запрос на &#x60;/v1/completions&#x60;.  Это устаревший API OpenAI — для новых интеграций используйте &#x60;/v1/chat/completions&#x60;. С &#x60;\&quot;stream\&quot;: true&#x60; ответ приходит потоком Server-Sent Events и заканчивается &#x60;data: [DONE]&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.AiGatewayInferenceApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: AiGatewayKey
    HttpBearerAuth AiGatewayKey = (HttpBearerAuth) defaultClient.getAuthentication("AiGatewayKey");
    AiGatewayKey.setBearerToken("BEARER TOKEN");

    AiGatewayInferenceApi apiInstance = new AiGatewayInferenceApi(defaultClient);
    Map<String, Object> requestBody = null; // Map<String, Object> | 
    try {
      AiGatewayCompletion result = apiInstance.createAiGatewayCompletion(requestBody);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AiGatewayInferenceApi#createAiGatewayCompletion");
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
| **requestBody** | [**Map&lt;String, Object&gt;**](Object.md)|  | |

### Return type

[**AiGatewayCompletion**](AiGatewayCompletion.md)

### Authorization

[AiGatewayKey](../README.md#AiGatewayKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, text/event-stream

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Продолжение текста или, с &#x60;\&quot;stream\&quot;: true&#x60;, поток событий |  -  |
| **400** | Некорректный запрос: не переданы или неверны параметры, модель не поддерживает операцию или временно недоступна |  -  |
| **401** | Ключ не передан или имеет неверный формат |  -  |
| **402** | Недостаточно средств на балансе: оценочная стоимость запроса больше доступного баланса (с учётом ещё не списанной стоимости недавних и выполняющихся запросов) |  -  |
| **403** | Ключ недействителен или аккаунт заблокирован |  -  |
| **404** | Модель не найдена или снята с обслуживания |  -  |
| **422** | Исчерпан лимит расхода ключа |  -  |
| **429** | Превышен лимит запросов провайдера модели (текст ошибки — от провайдера). Повторите запрос позже |  -  |
| **500** | Внутренняя ошибка или некорректный ответ провайдера модели. Повторите запрос позже |  -  |
| **503** | Сервис или провайдер модели временно недоступен. Повторите запрос позже |  -  |

<a id="createAiGatewayEmbedding"></a>
# **createAiGatewayEmbedding**
> AiGatewayEmbeddingList createAiGatewayEmbedding(requestBody)

Получение эмбеддингов

Чтобы получить векторные представления текстов, отправьте POST-запрос на &#x60;/v1/embeddings&#x60;.  Используйте модель с &#x60;mode: embedding&#x60; из &#x60;GET /v1/models&#x60;. Эмбеддинги возвращаются в порядке входных текстов.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.AiGatewayInferenceApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: AiGatewayKey
    HttpBearerAuth AiGatewayKey = (HttpBearerAuth) defaultClient.getAuthentication("AiGatewayKey");
    AiGatewayKey.setBearerToken("BEARER TOKEN");

    AiGatewayInferenceApi apiInstance = new AiGatewayInferenceApi(defaultClient);
    Map<String, Object> requestBody = null; // Map<String, Object> | 
    try {
      AiGatewayEmbeddingList result = apiInstance.createAiGatewayEmbedding(requestBody);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AiGatewayInferenceApi#createAiGatewayEmbedding");
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
| **requestBody** | [**Map&lt;String, Object&gt;**](Object.md)|  | |

### Return type

[**AiGatewayEmbeddingList**](AiGatewayEmbeddingList.md)

### Authorization

[AiGatewayKey](../README.md#AiGatewayKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Эмбеддинги |  -  |
| **400** | Некорректный запрос: не передан &#x60;input&#x60;, модель не поддерживает эмбеддинги или временно недоступна |  -  |
| **401** | Ключ не передан или имеет неверный формат |  -  |
| **402** | Недостаточно средств на балансе: оценочная стоимость запроса больше доступного баланса (с учётом ещё не списанной стоимости недавних и выполняющихся запросов) |  -  |
| **403** | Ключ недействителен или аккаунт заблокирован |  -  |
| **404** | Модель не найдена: неизвестный или не переданный &#x60;model&#x60; |  -  |
| **410** | Модель снята с обслуживания |  -  |
| **422** | Исчерпан лимит расхода ключа |  -  |
| **429** | Превышен лимит запросов провайдера модели (текст ошибки — от провайдера). Повторите запрос позже |  -  |
| **500** | Внутренняя ошибка или некорректный ответ провайдера модели. Повторите запрос позже |  -  |
| **503** | Сервис или провайдер модели временно недоступен. Повторите запрос позже |  -  |

<a id="createAiGatewayImage"></a>
# **createAiGatewayImage**
> AiGatewayImagesResponse createAiGatewayImage(requestBody)

Генерация изображений

Чтобы сгенерировать изображения по текстовому описанию, отправьте POST-запрос на &#x60;/v1/images/generations&#x60;.  Используйте модель с &#x60;mode: image_generation&#x60; из &#x60;GET /v1/models&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.AiGatewayInferenceApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: AiGatewayKey
    HttpBearerAuth AiGatewayKey = (HttpBearerAuth) defaultClient.getAuthentication("AiGatewayKey");
    AiGatewayKey.setBearerToken("BEARER TOKEN");

    AiGatewayInferenceApi apiInstance = new AiGatewayInferenceApi(defaultClient);
    Map<String, Object> requestBody = null; // Map<String, Object> | 
    try {
      AiGatewayImagesResponse result = apiInstance.createAiGatewayImage(requestBody);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AiGatewayInferenceApi#createAiGatewayImage");
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
| **requestBody** | [**Map&lt;String, Object&gt;**](Object.md)|  | |

### Return type

[**AiGatewayImagesResponse**](AiGatewayImagesResponse.md)

### Authorization

[AiGatewayKey](../README.md#AiGatewayKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Сгенерированные изображения |  -  |
| **400** | Некорректный запрос: не переданы или неверны параметры, модель не поддерживает операцию или временно недоступна |  -  |
| **401** | Ключ не передан или имеет неверный формат |  -  |
| **402** | Недостаточно средств на балансе: оценочная стоимость запроса больше доступного баланса (с учётом ещё не списанной стоимости недавних и выполняющихся запросов) |  -  |
| **403** | Ключ недействителен или аккаунт заблокирован |  -  |
| **404** | Модель не найдена: неизвестный или не переданный &#x60;model&#x60; |  -  |
| **410** | Модель снята с обслуживания |  -  |
| **422** | Исчерпан лимит расхода ключа |  -  |
| **429** | Превышен лимит запросов провайдера модели (текст ошибки — от провайдера). Повторите запрос позже |  -  |
| **500** | Внутренняя ошибка или некорректный ответ провайдера модели. Повторите запрос позже |  -  |
| **503** | Сервис или провайдер модели временно недоступен. Повторите запрос позже |  -  |

<a id="createAiGatewayImageEdit"></a>
# **createAiGatewayImageEdit**
> AiGatewayImagesResponse createAiGatewayImageEdit(model, image, prompt, mask, n, size, quality)

Редактирование изображений

Чтобы изменить или дополнить изображения по описанию, отправьте POST-запрос на &#x60;/v1/images/edits&#x60; (multipart/form-data).  Доступно для моделей GPT Image (&#x60;openai/gpt-image-*&#x60;) и FLUX.2 (&#x60;black_forest_labs/flux-2-*&#x60;). Для других моделей, в том числе неизвестных, возвращается ошибка 405.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.AiGatewayInferenceApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: AiGatewayKey
    HttpBearerAuth AiGatewayKey = (HttpBearerAuth) defaultClient.getAuthentication("AiGatewayKey");
    AiGatewayKey.setBearerToken("BEARER TOKEN");

    AiGatewayInferenceApi apiInstance = new AiGatewayInferenceApi(defaultClient);
    String model = "model_example"; // String | ID модели: GPT Image (`openai/gpt-image-*`) или FLUX.2 (`black_forest_labs/flux-2-*`)
    File image = new File("/path/to/file"); // File | Исходное изображение или несколько файлов в поле `image[]`
    String prompt = "prompt_example"; // String | Что изменить
    File mask = new File("/path/to/file"); // File | Маска: прозрачные области будут изменены
    Integer n = 1; // Integer | Количество изображений
    String size = "size_example"; // String | Размер изображения
    String quality = "quality_example"; // String | Качество изображения
    try {
      AiGatewayImagesResponse result = apiInstance.createAiGatewayImageEdit(model, image, prompt, mask, n, size, quality);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AiGatewayInferenceApi#createAiGatewayImageEdit");
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
| **model** | **String**| ID модели: GPT Image (&#x60;openai/gpt-image-*&#x60;) или FLUX.2 (&#x60;black_forest_labs/flux-2-*&#x60;) | |
| **image** | **File**| Исходное изображение или несколько файлов в поле &#x60;image[]&#x60; | |
| **prompt** | **String**| Что изменить | |
| **mask** | **File**| Маска: прозрачные области будут изменены | [optional] |
| **n** | **Integer**| Количество изображений | [optional] [default to 1] |
| **size** | **String**| Размер изображения | [optional] |
| **quality** | **String**| Качество изображения | [optional] |

### Return type

[**AiGatewayImagesResponse**](AiGatewayImagesResponse.md)

### Authorization

[AiGatewayKey](../README.md#AiGatewayKey)

### HTTP request headers

 - **Content-Type**: multipart/form-data
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Изменённые изображения |  -  |
| **400** | Некорректный запрос: не переданы или неверны параметры, модель не поддерживает операцию или временно недоступна |  -  |
| **401** | Ключ не передан или имеет неверный формат |  -  |
| **402** | Недостаточно средств на балансе: оценочная стоимость запроса больше доступного баланса (с учётом ещё не списанной стоимости недавних и выполняющихся запросов) |  -  |
| **403** | Ключ недействителен или аккаунт заблокирован |  -  |
| **405** | Модель не поддерживает редактирование изображений или не найдена |  -  |
| **410** | Модель снята с обслуживания |  -  |
| **422** | Исчерпан лимит расхода ключа |  -  |
| **429** | Превышен лимит запросов провайдера модели (текст ошибки — от провайдера). Повторите запрос позже |  -  |
| **500** | Внутренняя ошибка или некорректный ответ провайдера модели. Повторите запрос позже |  -  |
| **503** | Сервис или провайдер модели временно недоступен. Повторите запрос позже |  -  |

<a id="createAiGatewayResponse"></a>
# **createAiGatewayResponse**
> AiGatewayResponse createAiGatewayResponse(requestBody)

Создание ответа (Responses API)

Чтобы получить ответ модели через OpenAI Responses API, отправьте POST-запрос на &#x60;/v1/responses&#x60;.  Диалог можно продолжить по &#x60;previous_response_id&#x60;, долгие запросы — выполнить в фоне с &#x60;\&quot;background\&quot;: true&#x60; и получать результат через &#x60;GET /v1/responses/{response_id}&#x60;.  С &#x60;\&quot;stream\&quot;: true&#x60; ответ приходит потоком типизированных событий Server-Sent Events (&#x60;response.created&#x60;, &#x60;response.output_text.delta&#x60;, &#x60;response.completed&#x60; и др.), в конце потока — &#x60;data: [DONE]&#x60;.  Если у ключа настроены запасные модели и запрошенная модель не ответила, запрос повторяется на запасных (заголовок &#x60;x-timeweb-attempted-fallbacks&#x60;).

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.AiGatewayInferenceApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: AiGatewayKey
    HttpBearerAuth AiGatewayKey = (HttpBearerAuth) defaultClient.getAuthentication("AiGatewayKey");
    AiGatewayKey.setBearerToken("BEARER TOKEN");

    AiGatewayInferenceApi apiInstance = new AiGatewayInferenceApi(defaultClient);
    Map<String, Object> requestBody = null; // Map<String, Object> | 
    try {
      AiGatewayResponse result = apiInstance.createAiGatewayResponse(requestBody);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AiGatewayInferenceApi#createAiGatewayResponse");
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
| **requestBody** | [**Map&lt;String, Object&gt;**](Object.md)|  | |

### Return type

[**AiGatewayResponse**](AiGatewayResponse.md)

### Authorization

[AiGatewayKey](../README.md#AiGatewayKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json, text/event-stream

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Ответ или, с &#x60;\&quot;stream\&quot;: true&#x60;, поток событий |  -  |
| **400** | Некорректный запрос: не переданы или неверны параметры, модель не поддерживает операцию или временно недоступна |  -  |
| **401** | Ключ не передан или имеет неверный формат |  -  |
| **402** | Недостаточно средств на балансе: оценочная стоимость запроса больше доступного баланса (с учётом ещё не списанной стоимости недавних и выполняющихся запросов) |  -  |
| **403** | Ключ недействителен или аккаунт заблокирован |  -  |
| **404** | Модель не найдена: неизвестный или не переданный &#x60;model&#x60; |  -  |
| **410** | Модель снята с обслуживания |  -  |
| **422** | Исчерпан лимит расхода ключа |  -  |
| **429** | Превышен лимит запросов провайдера модели (текст ошибки — от провайдера). Повторите запрос позже |  -  |
| **500** | Внутренняя ошибка или некорректный ответ провайдера модели. Повторите запрос позже |  -  |
| **503** | Сервис или провайдер модели временно недоступен. Повторите запрос позже |  -  |

<a id="createAiGatewaySpeech"></a>
# **createAiGatewaySpeech**
> File createAiGatewaySpeech(requestBody)

Синтез речи

Чтобы озвучить текст, отправьте POST-запрос на &#x60;/v1/audio/speech&#x60;.  Используйте модель с &#x60;mode: audio_speech&#x60; из &#x60;GET /v1/models&#x60;. Ответ — аудиофайл в формате &#x60;response_format&#x60; (по умолчанию &#x60;mp3&#x60;).

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.AiGatewayInferenceApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: AiGatewayKey
    HttpBearerAuth AiGatewayKey = (HttpBearerAuth) defaultClient.getAuthentication("AiGatewayKey");
    AiGatewayKey.setBearerToken("BEARER TOKEN");

    AiGatewayInferenceApi apiInstance = new AiGatewayInferenceApi(defaultClient);
    Map<String, Object> requestBody = null; // Map<String, Object> | 
    try {
      File result = apiInstance.createAiGatewaySpeech(requestBody);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AiGatewayInferenceApi#createAiGatewaySpeech");
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
| **requestBody** | [**Map&lt;String, Object&gt;**](Object.md)|  | |

### Return type

[**File**](File.md)

### Authorization

[AiGatewayKey](../README.md#AiGatewayKey)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: audio/mpeg, audio/wav, application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Аудиофайл |  -  |
| **400** | Некорректный запрос: не переданы или неверны параметры, модель не поддерживает операцию или временно недоступна |  -  |
| **401** | Ключ не передан или имеет неверный формат |  -  |
| **402** | Недостаточно средств на балансе: оценочная стоимость запроса больше доступного баланса (с учётом ещё не списанной стоимости недавних и выполняющихся запросов) |  -  |
| **403** | Ключ недействителен или аккаунт заблокирован |  -  |
| **410** | Модель снята с обслуживания |  -  |
| **422** | Исчерпан лимит расхода ключа |  -  |
| **429** | Превышен лимит запросов провайдера модели (текст ошибки — от провайдера). Повторите запрос позже |  -  |
| **500** | Внутренняя ошибка или некорректный ответ провайдера модели. Повторите запрос позже |  -  |
| **503** | Сервис или провайдер модели временно недоступен. Повторите запрос позже |  -  |

<a id="createAiGatewayTranscription"></a>
# **createAiGatewayTranscription**
> AiGatewayTranscription createAiGatewayTranscription(_file, model, language, prompt, responseFormat, temperature)

Распознавание речи

Чтобы распознать речь в аудиофайле, отправьте POST-запрос на &#x60;/v1/audio/transcriptions&#x60; (multipart/form-data).  Доступно для моделей распознавания речи &#x60;*-transcribe&#x60; (например, &#x60;openai/gpt-4o-mini-transcribe&#x60;). Для других моделей, в том числе неизвестных, возвращается ошибка 405. Ответ возвращается в JSON.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.AiGatewayInferenceApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: AiGatewayKey
    HttpBearerAuth AiGatewayKey = (HttpBearerAuth) defaultClient.getAuthentication("AiGatewayKey");
    AiGatewayKey.setBearerToken("BEARER TOKEN");

    AiGatewayInferenceApi apiInstance = new AiGatewayInferenceApi(defaultClient);
    File _file = new File("/path/to/file"); // File | Аудиофайл
    String model = "model_example"; // String | ID модели распознавания речи (`*-transcribe`)
    String language = "language_example"; // String | Язык аудио в формате ISO-639-1
    String prompt = "prompt_example"; // String | Подсказка для распознавания: термины, имена, стиль
    String responseFormat = "responseFormat_example"; // String | Формат ответа
    BigDecimal temperature = new BigDecimal(78); // BigDecimal | Температура выборки
    try {
      AiGatewayTranscription result = apiInstance.createAiGatewayTranscription(_file, model, language, prompt, responseFormat, temperature);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AiGatewayInferenceApi#createAiGatewayTranscription");
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
| **_file** | **File**| Аудиофайл | |
| **model** | **String**| ID модели распознавания речи (&#x60;*-transcribe&#x60;) | |
| **language** | **String**| Язык аудио в формате ISO-639-1 | [optional] |
| **prompt** | **String**| Подсказка для распознавания: термины, имена, стиль | [optional] |
| **responseFormat** | **String**| Формат ответа | [optional] |
| **temperature** | **BigDecimal**| Температура выборки | [optional] |

### Return type

[**AiGatewayTranscription**](AiGatewayTranscription.md)

### Authorization

[AiGatewayKey](../README.md#AiGatewayKey)

### HTTP request headers

 - **Content-Type**: multipart/form-data
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Распознанный текст |  -  |
| **400** | Некорректный запрос, например повреждённый аудиофайл |  -  |
| **401** | Ключ не передан или имеет неверный формат |  -  |
| **402** | Недостаточно средств на балансе: оценочная стоимость запроса больше доступного баланса (с учётом ещё не списанной стоимости недавних и выполняющихся запросов) |  -  |
| **403** | Ключ недействителен или аккаунт заблокирован |  -  |
| **405** | Модель не поддерживает распознавание речи или не найдена |  -  |
| **410** | Модель снята с обслуживания |  -  |
| **422** | Исчерпан лимит расхода ключа (формат OpenAI) или не прошла проверка параметров multipart-запроса, например не передан файл (формат &#x60;{\&quot;detail\&quot;: ...}&#x60;) |  -  |
| **429** | Превышен лимит запросов провайдера модели (текст ошибки — от провайдера). Повторите запрос позже |  -  |
| **500** | Внутренняя ошибка или некорректный ответ провайдера модели. Повторите запрос позже |  -  |
| **503** | Сервис или провайдер модели временно недоступен. Повторите запрос позже |  -  |

<a id="deleteAiGatewayFile"></a>
# **deleteAiGatewayFile**
> AiGatewayFileDeleted deleteAiGatewayFile(fileId)

Удаление файла

Чтобы удалить файл, отправьте DELETE-запрос на &#x60;/v1/files/{file_id}&#x60;.  Входной файл батча, который ещё выполняется или отменяется, и результаты батча, расход которого ещё не списан, удалить нельзя — вернётся ошибка 409.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.AiGatewayInferenceApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: AiGatewayKey
    HttpBearerAuth AiGatewayKey = (HttpBearerAuth) defaultClient.getAuthentication("AiGatewayKey");
    AiGatewayKey.setBearerToken("BEARER TOKEN");

    AiGatewayInferenceApi apiInstance = new AiGatewayInferenceApi(defaultClient);
    String fileId = "file-abc123"; // String | ID файла
    try {
      AiGatewayFileDeleted result = apiInstance.deleteAiGatewayFile(fileId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AiGatewayInferenceApi#deleteAiGatewayFile");
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
| **fileId** | **String**| ID файла | |

### Return type

[**AiGatewayFileDeleted**](AiGatewayFileDeleted.md)

### Authorization

[AiGatewayKey](../README.md#AiGatewayKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Результат удаления |  -  |
| **401** | Ключ не передан или имеет неверный формат |  -  |
| **403** | Ключ недействителен или аккаунт заблокирован |  -  |
| **404** | Файл не найден |  -  |
| **409** | Файл используется батчем, который ещё выполняется |  -  |
| **500** | Внутренняя ошибка или некорректный ответ провайдера модели. Повторите запрос позже |  -  |

<a id="deleteAiGatewayResponse"></a>
# **deleteAiGatewayResponse**
> AiGatewayResponseDeleted deleteAiGatewayResponse(responseId)

Удаление ответа (Responses API)

Чтобы удалить сохранённый ответ, отправьте DELETE-запрос на &#x60;/v1/responses/{response_id}&#x60;.  Неизвестный или уже удалённый &#x60;response_id&#x60; возвращает ошибку 400.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.AiGatewayInferenceApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: AiGatewayKey
    HttpBearerAuth AiGatewayKey = (HttpBearerAuth) defaultClient.getAuthentication("AiGatewayKey");
    AiGatewayKey.setBearerToken("BEARER TOKEN");

    AiGatewayInferenceApi apiInstance = new AiGatewayInferenceApi(defaultClient);
    String responseId = "resp_abc123"; // String | ID ответа Responses API
    try {
      AiGatewayResponseDeleted result = apiInstance.deleteAiGatewayResponse(responseId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AiGatewayInferenceApi#deleteAiGatewayResponse");
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
| **responseId** | **String**| ID ответа Responses API | |

### Return type

[**AiGatewayResponseDeleted**](AiGatewayResponseDeleted.md)

### Authorization

[AiGatewayKey](../README.md#AiGatewayKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Результат удаления |  -  |
| **400** | Ответ с таким &#x60;response_id&#x60; не найден (в том числе уже удалён) или запрос некорректен |  -  |
| **401** | Ключ не передан или имеет неверный формат |  -  |
| **403** | Ключ недействителен или аккаунт заблокирован |  -  |
| **500** | Внутренняя ошибка или некорректный ответ провайдера модели. Повторите запрос позже |  -  |

<a id="downloadAiGatewayFileContent"></a>
# **downloadAiGatewayFileContent**
> File downloadAiGatewayFileContent(fileId)

Скачивание файла

Чтобы скачать содержимое файла — например, результаты выполненного батча (&#x60;output_file_id&#x60;), — отправьте GET-запрос на &#x60;/v1/files/{file_id}/content&#x60;.  Перед скачиванием результатов получите батч через &#x60;GET /v1/batches/{batch_id}&#x60; и убедитесь, что он в статусе &#x60;completed&#x60;. Файлы с &#x60;purpose&#x3D;user_data&#x60; скачать нельзя — вернётся ошибка 400.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.AiGatewayInferenceApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: AiGatewayKey
    HttpBearerAuth AiGatewayKey = (HttpBearerAuth) defaultClient.getAuthentication("AiGatewayKey");
    AiGatewayKey.setBearerToken("BEARER TOKEN");

    AiGatewayInferenceApi apiInstance = new AiGatewayInferenceApi(defaultClient);
    String fileId = "file-abc123"; // String | ID файла
    try {
      File result = apiInstance.downloadAiGatewayFileContent(fileId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AiGatewayInferenceApi#downloadAiGatewayFileContent");
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
| **fileId** | **String**| ID файла | |

### Return type

[**File**](File.md)

### Authorization

[AiGatewayKey](../README.md#AiGatewayKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/octet-stream, application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Содержимое файла |  -  |
| **400** | Файл этого назначения скачать нельзя |  -  |
| **401** | Ключ не передан или имеет неверный формат |  -  |
| **403** | Ключ недействителен или аккаунт заблокирован |  -  |
| **404** | Файл не найден |  -  |
| **500** | Внутренняя ошибка или некорректный ответ провайдера модели. Повторите запрос позже |  -  |

<a id="getAiGatewayBatch"></a>
# **getAiGatewayBatch**
> AiGatewayBatch getAiGatewayBatch(batchId)

Получение батча

Чтобы получить статус батча, отправьте GET-запрос на &#x60;/v1/batches/{batch_id}&#x60;.  Когда статус станет &#x60;completed&#x60;, скачайте результаты по &#x60;output_file_id&#x60; через &#x60;GET /v1/files/{file_id}/content&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.AiGatewayInferenceApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: AiGatewayKey
    HttpBearerAuth AiGatewayKey = (HttpBearerAuth) defaultClient.getAuthentication("AiGatewayKey");
    AiGatewayKey.setBearerToken("BEARER TOKEN");

    AiGatewayInferenceApi apiInstance = new AiGatewayInferenceApi(defaultClient);
    String batchId = "batch_abc123"; // String | ID батча
    try {
      AiGatewayBatch result = apiInstance.getAiGatewayBatch(batchId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AiGatewayInferenceApi#getAiGatewayBatch");
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
| **batchId** | **String**| ID батча | |

### Return type

[**AiGatewayBatch**](AiGatewayBatch.md)

### Authorization

[AiGatewayKey](../README.md#AiGatewayKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Батч |  -  |
| **401** | Ключ не передан или имеет неверный формат |  -  |
| **403** | Ключ недействителен или аккаунт заблокирован |  -  |
| **404** | Батч не найден |  -  |
| **500** | Внутренняя ошибка или некорректный ответ провайдера модели. Повторите запрос позже |  -  |
| **503** | Сервис или провайдер модели временно недоступен. Повторите запрос позже |  -  |

<a id="getAiGatewayBatches"></a>
# **getAiGatewayBatches**
> AiGatewayBatchList getAiGatewayBatches(limit, after)

Получение списка батчей

Чтобы получить список батчей, отправьте GET-запрос на &#x60;/v1/batches&#x60;.  Для следующей страницы передайте в &#x60;after&#x60; значение &#x60;last_id&#x60;, пока &#x60;has_more&#x60; равно &#x60;true&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.AiGatewayInferenceApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: AiGatewayKey
    HttpBearerAuth AiGatewayKey = (HttpBearerAuth) defaultClient.getAuthentication("AiGatewayKey");
    AiGatewayKey.setBearerToken("BEARER TOKEN");

    AiGatewayInferenceApi apiInstance = new AiGatewayInferenceApi(defaultClient);
    Integer limit = 20; // Integer | Количество батчей на странице
    String after = "batch_abc123"; // String | Вернуть батчи после батча с этим ID (значение `last_id` предыдущей страницы)
    try {
      AiGatewayBatchList result = apiInstance.getAiGatewayBatches(limit, after);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AiGatewayInferenceApi#getAiGatewayBatches");
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
| **limit** | **Integer**| Количество батчей на странице | [optional] |
| **after** | **String**| Вернуть батчи после батча с этим ID (значение &#x60;last_id&#x60; предыдущей страницы) | [optional] |

### Return type

[**AiGatewayBatchList**](AiGatewayBatchList.md)

### Authorization

[AiGatewayKey](../README.md#AiGatewayKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Список батчей |  -  |
| **401** | Ключ не передан или имеет неверный формат |  -  |
| **403** | Ключ недействителен или аккаунт заблокирован |  -  |
| **422** | Неверные параметры запроса, например нечисловой &#x60;limit&#x60; |  -  |
| **500** | Внутренняя ошибка или некорректный ответ провайдера модели. Повторите запрос позже |  -  |
| **503** | Сервис или провайдер модели временно недоступен. Повторите запрос позже |  -  |

<a id="getAiGatewayFile"></a>
# **getAiGatewayFile**
> AiGatewayFile getAiGatewayFile(fileId)

Получение информации о файле

Чтобы получить информацию о загруженном файле, отправьте GET-запрос на &#x60;/v1/files/{file_id}&#x60;.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.AiGatewayInferenceApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: AiGatewayKey
    HttpBearerAuth AiGatewayKey = (HttpBearerAuth) defaultClient.getAuthentication("AiGatewayKey");
    AiGatewayKey.setBearerToken("BEARER TOKEN");

    AiGatewayInferenceApi apiInstance = new AiGatewayInferenceApi(defaultClient);
    String fileId = "file-abc123"; // String | ID файла
    try {
      AiGatewayFile result = apiInstance.getAiGatewayFile(fileId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AiGatewayInferenceApi#getAiGatewayFile");
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
| **fileId** | **String**| ID файла | |

### Return type

[**AiGatewayFile**](AiGatewayFile.md)

### Authorization

[AiGatewayKey](../README.md#AiGatewayKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Файл |  -  |
| **401** | Ключ не передан или имеет неверный формат |  -  |
| **403** | Ключ недействителен или аккаунт заблокирован |  -  |
| **404** | Файл не найден |  -  |
| **500** | Внутренняя ошибка или некорректный ответ провайдера модели. Повторите запрос позже |  -  |

<a id="getAiGatewayModels"></a>
# **getAiGatewayModels**
> AiGatewayModelList getAiGatewayModels()

Получение списка моделей AI Gateway

Чтобы получить список моделей, доступных ключу, отправьте GET-запрос на &#x60;/v1/models&#x60;.  Поле &#x60;mode&#x60; показывает назначение модели. В списке могут оставаться модели, снятые с обслуживания: запросы к ним возвращают ошибку 410. Актуальный статус, цены и возможности моделей — в каталоге &#x60;GET https://api.timeweb.cloud/api/v3/cloud-ai/models&#x60; (снятые модели — &#x60;is_stopped: true&#x60;).

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.AiGatewayInferenceApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: AiGatewayKey
    HttpBearerAuth AiGatewayKey = (HttpBearerAuth) defaultClient.getAuthentication("AiGatewayKey");
    AiGatewayKey.setBearerToken("BEARER TOKEN");

    AiGatewayInferenceApi apiInstance = new AiGatewayInferenceApi(defaultClient);
    try {
      AiGatewayModelList result = apiInstance.getAiGatewayModels();
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AiGatewayInferenceApi#getAiGatewayModels");
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

[**AiGatewayModelList**](AiGatewayModelList.md)

### Authorization

[AiGatewayKey](../README.md#AiGatewayKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Список моделей |  -  |
| **401** | Ключ не передан или имеет неверный формат |  -  |
| **403** | Ключ недействителен или аккаунт заблокирован |  -  |
| **500** | Внутренняя ошибка или некорректный ответ провайдера модели. Повторите запрос позже |  -  |

<a id="getAiGatewayResponse"></a>
# **getAiGatewayResponse**
> AiGatewayResponse getAiGatewayResponse(responseId)

Получение ответа (Responses API)

Чтобы получить сохранённый ответ — например, дождаться завершения фонового ответа, — отправьте GET-запрос на &#x60;/v1/responses/{response_id}&#x60;.  Неизвестный или удалённый &#x60;response_id&#x60; возвращает ошибку 400.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.AiGatewayInferenceApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: AiGatewayKey
    HttpBearerAuth AiGatewayKey = (HttpBearerAuth) defaultClient.getAuthentication("AiGatewayKey");
    AiGatewayKey.setBearerToken("BEARER TOKEN");

    AiGatewayInferenceApi apiInstance = new AiGatewayInferenceApi(defaultClient);
    String responseId = "resp_abc123"; // String | ID ответа Responses API
    try {
      AiGatewayResponse result = apiInstance.getAiGatewayResponse(responseId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AiGatewayInferenceApi#getAiGatewayResponse");
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
| **responseId** | **String**| ID ответа Responses API | |

### Return type

[**AiGatewayResponse**](AiGatewayResponse.md)

### Authorization

[AiGatewayKey](../README.md#AiGatewayKey)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Ответ |  -  |
| **400** | Ответ с таким &#x60;response_id&#x60; не найден (в том числе уже удалён) или запрос некорректен |  -  |
| **401** | Ключ не передан или имеет неверный формат |  -  |
| **403** | Ключ недействителен или аккаунт заблокирован |  -  |
| **500** | Внутренняя ошибка или некорректный ответ провайдера модели. Повторите запрос позже |  -  |

<a id="uploadAiGatewayFile"></a>
# **uploadAiGatewayFile**
> AiGatewayFile uploadAiGatewayFile(_file, purpose, model, targetModelNames)

Загрузка файла

Чтобы загрузить файл, отправьте POST-запрос на &#x60;/v1/files&#x60; (multipart/form-data).  Файл загружается для конкретной модели, поэтому &#x60;model&#x60; (или &#x60;target_model_names&#x60;) обязателен: - &#x60;purpose&#x3D;batch&#x60; — входной JSONL-файл Batch API. Все строки должны обращаться к одной модели — той, что указана в &#x60;model&#x60;, иначе вернётся 400. Размер — до 50 МБ и до 50 000 запросов; - &#x60;user_data&#x60; и другие назначения OpenAI — файлы, на которые можно сослаться по &#x60;file_id&#x60; в сообщениях.  Загрузка бесплатна. Сохраняйте &#x60;id&#x60; загруженных файлов: получить список файлов нельзя (&#x60;GET /v1/files&#x60; возвращает 405).

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.AiGatewayInferenceApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.timeweb.cloud");
    
    // Configure HTTP bearer authorization: AiGatewayKey
    HttpBearerAuth AiGatewayKey = (HttpBearerAuth) defaultClient.getAuthentication("AiGatewayKey");
    AiGatewayKey.setBearerToken("BEARER TOKEN");

    AiGatewayInferenceApi apiInstance = new AiGatewayInferenceApi(defaultClient);
    File _file = new File("/path/to/file"); // File | Файл. Для Batch API — JSONL, по одному запросу в строке
    String purpose = "purpose_example"; // String | Назначение: `batch` — входной файл Batch API, `user_data` и другие назначения OpenAI — файлы для передачи модели
    String model = "model_example"; // String | Модель, для которой загружается файл. Обязателен, если не передан `target_model_names`
    String targetModelNames = "targetModelNames_example"; // String | Список моделей через запятую — вместо `model`
    try {
      AiGatewayFile result = apiInstance.uploadAiGatewayFile(_file, purpose, model, targetModelNames);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AiGatewayInferenceApi#uploadAiGatewayFile");
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
| **_file** | **File**| Файл. Для Batch API — JSONL, по одному запросу в строке | |
| **purpose** | **String**| Назначение: &#x60;batch&#x60; — входной файл Batch API, &#x60;user_data&#x60; и другие назначения OpenAI — файлы для передачи модели | |
| **model** | **String**| Модель, для которой загружается файл. Обязателен, если не передан &#x60;target_model_names&#x60; | [optional] |
| **targetModelNames** | **String**| Список моделей через запятую — вместо &#x60;model&#x60; | [optional] |

### Return type

[**AiGatewayFile**](AiGatewayFile.md)

### Authorization

[AiGatewayKey](../README.md#AiGatewayKey)

### HTTP request headers

 - **Content-Type**: multipart/form-data
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Загруженный файл |  -  |
| **400** | Некорректный запрос, например модель в строках файла не совпадает с &#x60;model&#x60; |  -  |
| **401** | Ключ не передан или имеет неверный формат |  -  |
| **403** | Ключ недействителен или аккаунт заблокирован |  -  |
| **422** | Не прошла проверка параметров, например не передан файл |  -  |
| **500** | Внутренняя ошибка или некорректный ответ провайдера модели. Повторите запрос позже |  -  |

