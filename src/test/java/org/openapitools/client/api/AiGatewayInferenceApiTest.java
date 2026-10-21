/*
 * Timeweb Cloud API
 * # Введение API Timeweb Cloud позволяет вам управлять ресурсами в облаке программным способом с использованием обычных HTTP-запросов.  Множество функций, которые доступны в панели управления Timeweb Cloud, также доступны через API, что позволяет вам автоматизировать ваши собственные сценарии.  В этой документации сперва будет описан общий дизайн и принципы работы API, а после этого конкретные конечные точки. Также будут приведены примеры запросов к ним.   ## Запросы Запросы должны выполняться по протоколу `HTTPS`, чтобы гарантировать шифрование транзакций. Поддерживаются следующие методы запроса: |Метод|Применение| |--- |--- | |GET|Извлекает данные о коллекциях и отдельных ресурсах.| |POST|Для коллекций создает новый ресурс этого типа. Также используется для выполнения действий с конкретным ресурсом.| |PUT|Обновляет существующий ресурс.| |PATCH|Некоторые ресурсы поддерживают частичное обновление, то есть обновление только части атрибутов ресурса, в этом случае вместо метода PUT будет использован PATCH.| |DELETE|Удаляет ресурс.|  Методы `POST`, `PUT` и `PATCH` могут включать объект в тело запроса с типом содержимого `application/json`.  ### Параметры в запросах Некоторые коллекции поддерживают пагинацию, поиск или сортировку в запросах. В параметрах запроса требуется передать: - `limit` — обозначает количество записей, которое необходимо вернуть  - `offset` — указывает на смещение, относительно начала списка  - `search` — позволяет указать набор символов для поиска  - `sort` — можно задать правило сортировки коллекции  ## Ответы Запросы вернут один из следующих кодов состояния ответа HTTP:  |Статус|Описание| |--- |--- | |200 OK|Действие с ресурсом было выполнено успешно.| |201 Created|Ресурс был успешно создан. При этом ресурс может быть как уже готовым к использованию, так и находиться в процессе запуска.| |204 No Content|Действие с ресурсом было выполнено успешно, и ответ не содержит дополнительной информации в теле.| |400 Bad Request|Был отправлен неверный запрос, например, в нем отсутствуют обязательные параметры и т. д. Тело ответа будет содержать дополнительную информацию об ошибке.| |401 Unauthorized|Ошибка аутентификации.| |403 Forbidden|Аутентификация прошла успешно, но недостаточно прав для выполнения действия.| |404 Not Found|Запрашиваемый ресурс не найден.| |409 Conflict|Запрос конфликтует с текущим состоянием.| |423 Locked|Ресурс из запроса заблокирован от применения к нему указанного метода.| |429 Too Many Requests|Был достигнут лимит по количеству запросов в единицу времени.| |500 Internal Server Error|При выполнении запроса произошла какая-то внутренняя ошибка. Чтобы решить эту проблему, лучше всего создать тикет в панели управления.|  ### Структура успешного ответа Все конечные точки будут возвращать данные в формате `JSON`. Ответы на `GET`-запросы будут иметь на верхнем уровне следующую структуру атрибутов:  |Название поля|Тип|Описание| |--- |--- |--- | |[entity_name]|object, object[], string[], number[], boolean|Динамическое поле, которое будет меняться в зависимости от запрашиваемого ресурса и будет содержать все атрибуты, необходимые для описания этого ресурса. Например, при запросе списка баз данных будет возвращаться поле `dbs`, а при запросе конкретного облачного сервера `server`. Для некоторых конечных точек в ответе может возвращаться сразу несколько ресурсов.| |meta|object|Опционально. Объект, который содержит вспомогательную информацию о ресурсе. Чаще всего будет встречаться при запросе коллекций и содержать поле `total`, которое будет указывать на количество элементов в коллекции.| |response_id|string|Опционально. В большинстве случаев в ответе будет содержаться ID ответа в формате UUIDv4, который однозначно указывает на ваш запрос внутри нашей системы. Если вам потребуется задать вопрос нашей поддержке, приложите к вопросу этот ID— так мы сможем найти ответ на него намного быстрее. Также вы можете использовать этот ID, чтобы убедиться, что это новый ответ на запрос и результат не был получен из кэша.|  Пример запроса на получение списка SSH-ключей: ```     HTTP/2.0 200 OK     {       \"ssh_keys\":[           {             \"body\":\"ssh-rsa AAAAB3NzaC1sdfghjkOAsBwWhs= example@device.local\",             \"created_at\":\"2021-09-15T19:52:27Z\",             \"expired_at\":null,             \"id\":5297,             \"is_default\":false,             \"name\":\"example@device.local\",             \"used_at\":null,             \"used_by\":[]           }       ],       \"meta\":{           \"total\":1       },       \"response_id\":\"94608d15-8672-4eed-8ab6-28bd6fa3cdf7\"     } ```  ### Структура ответа с ошибкой |Название поля|Тип|Описание| |--- |--- |--- | |status_code|number|Короткий числовой идентификатор ошибки.| |error_code|string|Короткий текстовый идентификатор ошибки, который уточняет числовой идентификатор и удобен для программной обработки. Самый простой пример — это код `not_found` для ошибки 404.| |message|string, string[]|Опционально. В большинстве случаев в ответе будет содержаться человекочитаемое подробное описание ошибки или ошибок, которые помогут понять, что нужно исправить.| |response_id|string|Опционально. В большинстве случае в ответе будет содержаться ID ответа в формате UUIDv4, который однозначно указывает на ваш запрос внутри нашей системы. Если вам потребуется задать вопрос нашей поддержке, приложите к вопросу этот ID — так мы сможем найти ответ на него намного быстрее.|  Пример: ```     HTTP/2.0 403 Forbidden     {       \"status_code\": 403,       \"error_code\":  \"forbidden\",       \"message\":     \"You do not have access for the attempted action\",       \"response_id\": \"94608d15-8672-4eed-8ab6-28bd6fa3cdf7\"     } ```  ## Статусы ресурсов Важно учесть, что при создании большинства ресурсов внутри платформы вам будет сразу возвращен ответ от сервера со статусом `200 OK` или `201 Created` и ID созданного ресурса в теле ответа, но при этом этот ресурс может быть ещё в *состоянии запуска*.  Для того чтобы понять, в каком состоянии сейчас находится ваш ресурс, мы добавили поле `status` в ответ на получение информации о ресурсе.  Список статусов будет отличаться в зависимости от типа ресурса. Увидеть поддерживаемый список статусов вы сможете в описании каждого конкретного ресурса.     ## Ограничение скорости запросов (Rate Limiting) Чтобы обеспечить стабильность для всех пользователей, Timeweb Cloud защищает API от всплесков входящего трафика, анализируя количество запросов c каждого аккаунта к каждой конечной точке.  Если ваше приложение отправляет более 20 запросов в секунду на одну конечную точку, то для этого запроса API может вернуть код состояния HTTP `429 Too Many Requests`.   ## Аутентификация Доступ к API осуществляется с помощью JWT-токена. Токенами можно управлять внутри панели управления Timeweb Cloud в разделе *API и Terraform*.  Токен необходимо передавать в заголовке каждого запроса в формате: ```   Authorization: Bearer $TIMEWEB_CLOUD_TOKEN ```  ## Формат примеров API Примеры в этой документации описаны с помощью `curl`, HTTP-клиента командной строки. На компьютерах `Linux` и `macOS` обычно по умолчанию установлен `curl`, и он доступен для загрузки на всех популярных платформах, включая `Windows`.  Каждый пример разделен на несколько строк символом `\\`, который совместим с `bash`. Типичный пример выглядит так: ```   curl -X PATCH      -H \"Content-Type: application/json\"      -H \"Authorization: Bearer $TIMEWEB_CLOUD_TOKEN\"      -d '{\"name\":\"Cute Corvus\",\"comment\":\"Development Server\"}'      \"https://api.timeweb.cloud/api/v1/dedicated/1051\" ``` - Параметр `-X` задает метод запроса. Для согласованности метод будет указан во всех примерах, даже если он явно не требуется для методов `GET`. - Строки `-H` задают требуемые HTTP-заголовки. - Примеры, для которых требуется объект JSON в теле запроса, передают требуемые данные через параметр `-d`.  Чтобы использовать приведенные примеры, не подставляя каждый раз в них свой токен, вы можете добавить токен один раз в переменные окружения в вашей консоли. Например, на `Linux` это можно сделать с помощью команды:  ``` TIMEWEB_CLOUD_TOKEN=\"token\" ```  После этого токен будет автоматически подставляться в ваши запросы.  Обратите внимание, что все значения в этой документации являются примерами. Не полагайтесь на IDы операционных систем, тарифов и т.д., используемые в примерах. Используйте соответствующую конечную точку для получения значений перед созданием ресурсов.   ## Версионирование API построено согласно принципам [семантического версионирования](https://semver.org/lang/ru). Это значит, что мы гарантируем обратную совместимость всех изменений в пределах одной мажорной версии.  Мажорная версия каждой конечной точки обозначается в пути запроса, например, запрос `/api/v1/servers` указывает, что этот метод имеет версию 1.
 *
 * The version of the OpenAPI document: 1.0.0
 * Contact: info@timeweb.cloud
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */


package org.openapitools.client.api;

import org.openapitools.client.ApiException;
import org.openapitools.client.model.AiGatewayBatch;
import org.openapitools.client.model.AiGatewayBatchCreateRequest;
import org.openapitools.client.model.AiGatewayBatchList;
import org.openapitools.client.model.AiGatewayChatCompletion;
import org.openapitools.client.model.AiGatewayChatCompletionChunk;
import org.openapitools.client.model.AiGatewayChatCompletionRequest;
import org.openapitools.client.model.AiGatewayCompletion;
import org.openapitools.client.model.AiGatewayCompletionRequest;
import org.openapitools.client.model.AiGatewayEmbeddingList;
import org.openapitools.client.model.AiGatewayEmbeddingRequest;
import org.openapitools.client.model.AiGatewayError;
import org.openapitools.client.model.AiGatewayFile;
import org.openapitools.client.model.AiGatewayFileDeleted;
import org.openapitools.client.model.AiGatewayImageGenerationRequest;
import org.openapitools.client.model.AiGatewayImagesResponse;
import org.openapitools.client.model.AiGatewayModelList;
import org.openapitools.client.model.AiGatewayResponse;
import org.openapitools.client.model.AiGatewayResponseCreateRequest;
import org.openapitools.client.model.AiGatewayResponseDeleted;
import org.openapitools.client.model.AiGatewaySpeechRequest;
import org.openapitools.client.model.AiGatewayTranscription;
import org.openapitools.client.model.AiGatewayValidationError;
import java.io.File;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * API tests for AiGatewayInferenceApi
 */
@Disabled
public class AiGatewayInferenceApiTest {

    private final AiGatewayInferenceApi api = new AiGatewayInferenceApi();

    /**
     * Отмена батча
     *
     * Чтобы отменить батч, отправьте POST-запрос на &#x60;/v1/batches/{batch_id}/cancel&#x60;.  Батч переходит в статус &#x60;cancelling&#x60;, отмена выполняется асинхронно и может занять несколько минут; итоговый статус &#x60;cancelled&#x60; проверяйте через &#x60;GET /v1/batches/{batch_id}&#x60;. Пока отмена не завершилась, входной файл батча удалить нельзя.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void cancelAiGatewayBatchTest() throws ApiException {
        String batchId = null;
        AiGatewayBatch response = api.cancelAiGatewayBatch(batchId);
        // TODO: test validations
    }

    /**
     * Отмена фонового ответа (Responses API)
     *
     * Чтобы отменить ответ, запущенный с &#x60;\&quot;background\&quot;: true&#x60;, отправьте POST-запрос на &#x60;/v1/responses/{response_id}/cancel&#x60;.  Отмена может завершиться не сразу: итоговый статус &#x60;cancelled&#x60; проверяйте через &#x60;GET /v1/responses/{response_id}&#x60;. Ответ, созданный без &#x60;background&#x60;, отменить нельзя — вернётся ошибка 400. Неизвестный &#x60;response_id&#x60; также возвращает 400.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void cancelAiGatewayResponseTest() throws ApiException {
        String responseId = null;
        AiGatewayResponse response = api.cancelAiGatewayResponse(responseId);
        // TODO: test validations
    }

    /**
     * Создание батча
     *
     * Чтобы запустить асинхронную обработку входного файла, загруженного с &#x60;purpose&#x3D;batch&#x60;, отправьте POST-запрос на &#x60;/v1/batches&#x60;.  При создании батча его оценочная стоимость резервируется на балансе, фактическая стоимость списывается по цене Batch API после выполнения. Модели, эндпоинты и коэффициенты цены Batch API — в поле &#x60;batch&#x60; каталога моделей (&#x60;GET https://api.timeweb.cloud/api/v3/cloud-ai/models&#x60;). Batch API доступен только для ключей AI Gateway.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void createAiGatewayBatchTest() throws ApiException {
        AiGatewayBatchCreateRequest aiGatewayBatchCreateRequest = null;
        AiGatewayBatch response = api.createAiGatewayBatch(aiGatewayBatchCreateRequest);
        // TODO: test validations
    }

    /**
     * Генерация ответа в диалоге (Chat Completions)
     *
     * Чтобы получить следующий ответ модели в диалоге, отправьте POST-запрос на &#x60;/v1/chat/completions&#x60;.  Запрос не хранит состояние: передавайте всю историю диалога в &#x60;messages&#x60;. Поддерживаются вызов функций (&#x60;tools&#x60;), структурированный ответ (&#x60;response_format&#x60;) и изображения во входных сообщениях (для моделей с поддержкой зрения).  С &#x60;\&quot;stream\&quot;: true&#x60; ответ приходит потоком Server-Sent Events: события &#x60;chat.completion.chunk&#x60; в строках &#x60;data:&#x60;, в конце — &#x60;data: [DONE]&#x60;. С &#x60;\&quot;stream_options\&quot;: {\&quot;include_usage\&quot;: true}&#x60; последний чанк содержит расход токенов.  Если у ключа настроены запасные модели и запрошенная модель не ответила, запрос повторяется на запасных. Заголовок ответа &#x60;x-timeweb-attempted-fallbacks&#x60; показывает, сколько запасных моделей было опробовано.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void createAiGatewayChatCompletionTest() throws ApiException {
        Map<String, Object> requestBody = null;
        AiGatewayChatCompletion response = api.createAiGatewayChatCompletion(requestBody);
        // TODO: test validations
    }

    /**
     * Продолжение текста (Completions)
     *
     * Чтобы получить продолжение текста &#x60;prompt&#x60;, отправьте POST-запрос на &#x60;/v1/completions&#x60;.  Это устаревший API OpenAI — для новых интеграций используйте &#x60;/v1/chat/completions&#x60;. С &#x60;\&quot;stream\&quot;: true&#x60; ответ приходит потоком Server-Sent Events и заканчивается &#x60;data: [DONE]&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void createAiGatewayCompletionTest() throws ApiException {
        Map<String, Object> requestBody = null;
        AiGatewayCompletion response = api.createAiGatewayCompletion(requestBody);
        // TODO: test validations
    }

    /**
     * Получение эмбеддингов
     *
     * Чтобы получить векторные представления текстов, отправьте POST-запрос на &#x60;/v1/embeddings&#x60;.  Используйте модель с &#x60;mode: embedding&#x60; из &#x60;GET /v1/models&#x60;. Эмбеддинги возвращаются в порядке входных текстов.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void createAiGatewayEmbeddingTest() throws ApiException {
        Map<String, Object> requestBody = null;
        AiGatewayEmbeddingList response = api.createAiGatewayEmbedding(requestBody);
        // TODO: test validations
    }

    /**
     * Генерация изображений
     *
     * Чтобы сгенерировать изображения по текстовому описанию, отправьте POST-запрос на &#x60;/v1/images/generations&#x60;.  Используйте модель с &#x60;mode: image_generation&#x60; из &#x60;GET /v1/models&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void createAiGatewayImageTest() throws ApiException {
        Map<String, Object> requestBody = null;
        AiGatewayImagesResponse response = api.createAiGatewayImage(requestBody);
        // TODO: test validations
    }

    /**
     * Редактирование изображений
     *
     * Чтобы изменить или дополнить изображения по описанию, отправьте POST-запрос на &#x60;/v1/images/edits&#x60; (multipart/form-data).  Доступно для моделей GPT Image (&#x60;openai/gpt-image-*&#x60;) и FLUX.2 (&#x60;black_forest_labs/flux-2-*&#x60;). Для других моделей, в том числе неизвестных, возвращается ошибка 405.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void createAiGatewayImageEditTest() throws ApiException {
        String model = null;
        File image = null;
        String prompt = null;
        File mask = null;
        Integer n = null;
        String size = null;
        String quality = null;
        AiGatewayImagesResponse response = api.createAiGatewayImageEdit(model, image, prompt, mask, n, size, quality);
        // TODO: test validations
    }

    /**
     * Создание ответа (Responses API)
     *
     * Чтобы получить ответ модели через OpenAI Responses API, отправьте POST-запрос на &#x60;/v1/responses&#x60;.  Диалог можно продолжить по &#x60;previous_response_id&#x60;, долгие запросы — выполнить в фоне с &#x60;\&quot;background\&quot;: true&#x60; и получать результат через &#x60;GET /v1/responses/{response_id}&#x60;.  С &#x60;\&quot;stream\&quot;: true&#x60; ответ приходит потоком типизированных событий Server-Sent Events (&#x60;response.created&#x60;, &#x60;response.output_text.delta&#x60;, &#x60;response.completed&#x60; и др.), в конце потока — &#x60;data: [DONE]&#x60;.  Если у ключа настроены запасные модели и запрошенная модель не ответила, запрос повторяется на запасных (заголовок &#x60;x-timeweb-attempted-fallbacks&#x60;).
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void createAiGatewayResponseTest() throws ApiException {
        Map<String, Object> requestBody = null;
        AiGatewayResponse response = api.createAiGatewayResponse(requestBody);
        // TODO: test validations
    }

    /**
     * Синтез речи
     *
     * Чтобы озвучить текст, отправьте POST-запрос на &#x60;/v1/audio/speech&#x60;.  Используйте модель с &#x60;mode: audio_speech&#x60; из &#x60;GET /v1/models&#x60;. Ответ — аудиофайл в формате &#x60;response_format&#x60; (по умолчанию &#x60;mp3&#x60;).
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void createAiGatewaySpeechTest() throws ApiException {
        Map<String, Object> requestBody = null;
        File response = api.createAiGatewaySpeech(requestBody);
        // TODO: test validations
    }

    /**
     * Распознавание речи
     *
     * Чтобы распознать речь в аудиофайле, отправьте POST-запрос на &#x60;/v1/audio/transcriptions&#x60; (multipart/form-data).  Доступно для моделей распознавания речи &#x60;*-transcribe&#x60; (например, &#x60;openai/gpt-4o-mini-transcribe&#x60;). Для других моделей, в том числе неизвестных, возвращается ошибка 405. Ответ возвращается в JSON.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void createAiGatewayTranscriptionTest() throws ApiException {
        File _file = null;
        String model = null;
        String language = null;
        String prompt = null;
        String responseFormat = null;
        BigDecimal temperature = null;
        AiGatewayTranscription response = api.createAiGatewayTranscription(_file, model, language, prompt, responseFormat, temperature);
        // TODO: test validations
    }

    /**
     * Удаление файла
     *
     * Чтобы удалить файл, отправьте DELETE-запрос на &#x60;/v1/files/{file_id}&#x60;.  Входной файл батча, который ещё выполняется или отменяется, и результаты батча, расход которого ещё не списан, удалить нельзя — вернётся ошибка 409.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void deleteAiGatewayFileTest() throws ApiException {
        String fileId = null;
        AiGatewayFileDeleted response = api.deleteAiGatewayFile(fileId);
        // TODO: test validations
    }

    /**
     * Удаление ответа (Responses API)
     *
     * Чтобы удалить сохранённый ответ, отправьте DELETE-запрос на &#x60;/v1/responses/{response_id}&#x60;.  Неизвестный или уже удалённый &#x60;response_id&#x60; возвращает ошибку 400.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void deleteAiGatewayResponseTest() throws ApiException {
        String responseId = null;
        AiGatewayResponseDeleted response = api.deleteAiGatewayResponse(responseId);
        // TODO: test validations
    }

    /**
     * Скачивание файла
     *
     * Чтобы скачать содержимое файла — например, результаты выполненного батча (&#x60;output_file_id&#x60;), — отправьте GET-запрос на &#x60;/v1/files/{file_id}/content&#x60;.  Перед скачиванием результатов получите батч через &#x60;GET /v1/batches/{batch_id}&#x60; и убедитесь, что он в статусе &#x60;completed&#x60;. Файлы с &#x60;purpose&#x3D;user_data&#x60; скачать нельзя — вернётся ошибка 400.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void downloadAiGatewayFileContentTest() throws ApiException {
        String fileId = null;
        File response = api.downloadAiGatewayFileContent(fileId);
        // TODO: test validations
    }

    /**
     * Получение батча
     *
     * Чтобы получить статус батча, отправьте GET-запрос на &#x60;/v1/batches/{batch_id}&#x60;.  Когда статус станет &#x60;completed&#x60;, скачайте результаты по &#x60;output_file_id&#x60; через &#x60;GET /v1/files/{file_id}/content&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getAiGatewayBatchTest() throws ApiException {
        String batchId = null;
        AiGatewayBatch response = api.getAiGatewayBatch(batchId);
        // TODO: test validations
    }

    /**
     * Получение списка батчей
     *
     * Чтобы получить список батчей, отправьте GET-запрос на &#x60;/v1/batches&#x60;.  Для следующей страницы передайте в &#x60;after&#x60; значение &#x60;last_id&#x60;, пока &#x60;has_more&#x60; равно &#x60;true&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getAiGatewayBatchesTest() throws ApiException {
        Integer limit = null;
        String after = null;
        AiGatewayBatchList response = api.getAiGatewayBatches(limit, after);
        // TODO: test validations
    }

    /**
     * Получение информации о файле
     *
     * Чтобы получить информацию о загруженном файле, отправьте GET-запрос на &#x60;/v1/files/{file_id}&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getAiGatewayFileTest() throws ApiException {
        String fileId = null;
        AiGatewayFile response = api.getAiGatewayFile(fileId);
        // TODO: test validations
    }

    /**
     * Получение списка моделей AI Gateway
     *
     * Чтобы получить список моделей, доступных ключу, отправьте GET-запрос на &#x60;/v1/models&#x60;.  Поле &#x60;mode&#x60; показывает назначение модели. В списке могут оставаться модели, снятые с обслуживания: запросы к ним возвращают ошибку 410. Актуальный статус, цены и возможности моделей — в каталоге &#x60;GET https://api.timeweb.cloud/api/v3/cloud-ai/models&#x60; (снятые модели — &#x60;is_stopped: true&#x60;).
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getAiGatewayModelsTest() throws ApiException {
        AiGatewayModelList response = api.getAiGatewayModels();
        // TODO: test validations
    }

    /**
     * Получение ответа (Responses API)
     *
     * Чтобы получить сохранённый ответ — например, дождаться завершения фонового ответа, — отправьте GET-запрос на &#x60;/v1/responses/{response_id}&#x60;.  Неизвестный или удалённый &#x60;response_id&#x60; возвращает ошибку 400.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getAiGatewayResponseTest() throws ApiException {
        String responseId = null;
        AiGatewayResponse response = api.getAiGatewayResponse(responseId);
        // TODO: test validations
    }

    /**
     * Загрузка файла
     *
     * Чтобы загрузить файл, отправьте POST-запрос на &#x60;/v1/files&#x60; (multipart/form-data).  Файл загружается для конкретной модели, поэтому &#x60;model&#x60; (или &#x60;target_model_names&#x60;) обязателен: - &#x60;purpose&#x3D;batch&#x60; — входной JSONL-файл Batch API. Все строки должны обращаться к одной модели — той, что указана в &#x60;model&#x60;, иначе вернётся 400. Размер — до 50 МБ и до 50 000 запросов; - &#x60;user_data&#x60; и другие назначения OpenAI — файлы, на которые можно сослаться по &#x60;file_id&#x60; в сообщениях.  Загрузка бесплатна. Сохраняйте &#x60;id&#x60; загруженных файлов: получить список файлов нельзя (&#x60;GET /v1/files&#x60; возвращает 405).
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void uploadAiGatewayFileTest() throws ApiException {
        File _file = null;
        String purpose = null;
        String model = null;
        String targetModelNames = null;
        AiGatewayFile response = api.uploadAiGatewayFile(_file, purpose, model, targetModelNames);
        // TODO: test validations
    }

}
