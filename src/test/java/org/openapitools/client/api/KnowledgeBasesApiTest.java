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
import org.openapitools.client.model.CreateDocument;
import org.openapitools.client.model.CreateKnowledgebase;
import org.openapitools.client.model.CreateKnowledgebase201Response;
import org.openapitools.client.model.CreateKnowledgebaseDocument201Response;
import java.io.File;
import org.openapitools.client.model.GetAccountStatus403Response;
import org.openapitools.client.model.GetFinances400Response;
import org.openapitools.client.model.GetFinances401Response;
import org.openapitools.client.model.GetFinances429Response;
import org.openapitools.client.model.GetFinances500Response;
import org.openapitools.client.model.GetImage404Response;
import org.openapitools.client.model.GetKnowledgebaseDocumentsV2200Response;
import org.openapitools.client.model.GetKnowledgebaseV3200Response;
import org.openapitools.client.model.GetKnowledgebases200Response;
import org.openapitools.client.model.GetKnowledgebasesV2200Response;
import org.openapitools.client.model.GetKnowledgebasesV3200Response;
import org.openapitools.client.model.KnowledgebaseAddTokenPackage;
import org.openapitools.client.model.LinkKnowledgebase;
import org.openapitools.client.model.UpdateDocument;
import org.openapitools.client.model.UpdateKnowledgebase;
import org.openapitools.client.model.UploadResult;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * API tests for KnowledgeBasesApi
 */
@Disabled
public class KnowledgeBasesApiTest {

    private final KnowledgeBasesApi api = new KnowledgeBasesApi();

    /**
     * Покупка дополнительных пакетов токенов
     *
     * Чтобы докупить токены для базы знаний, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/knowledge-bases/{id}/add-additional-token-package&#x60;.  Покупается дополнительный пакет (&#x60;package_type: additional&#x60;) из &#x60;GET /api/v1/cloud-ai/token-packages/knowledge-bases&#x60; в количестве &#x60;count&#x60;. Стоимость списывается с баланса, токены добавляются к текущему периоду подписки, заблокированная база знаний разблокируется.  Возможные ошибки: - &#x60;400 insufficient_balance&#x60; — на балансе недостаточно средств - &#x60;400 subscription_expired&#x60; — база знаний заблокирована и дата продления подписки прошла: продлите подписку в панели управления - &#x60;404 additional_token_package_not_found&#x60; — дополнительный пакет недоступен
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void addAdditionalTokenPackageToKnowledgebaseTest() throws ApiException {
        Integer id = null;
        KnowledgebaseAddTokenPackage knowledgebaseAddTokenPackage = null;
        api.addAdditionalTokenPackageToKnowledgebase(id, knowledgebaseAddTokenPackage);
        // TODO: test validations
    }

    /**
     * Создание базы знаний
     *
     * Чтобы создать базу знаний, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/knowledge-bases&#x60;, задав необходимые атрибуты.  Для базы знаний в вашем аккаунте создаётся кластер базы данных OpenSearch по выбранному тарифу в указанной приватной сети, а с баланса сразу списывается стоимость пакета токенов. Подписка продлевается автоматически раз в период пакета.  Пока кластер создаётся, база знаний находится в статусе &#x60;creating&#x60;; документы, добавленные в это время, будут проиндексированы после перехода в &#x60;active&#x60;.  Возможные ошибки: - &#x60;404 token_package_not_found&#x60; — пакет токенов не найден или недоступен - &#x60;400 invalid_token_package_type&#x60; — пакет не является базовым пакетом для баз знаний - &#x60;400 insufficient_balance&#x60; — на балансе недостаточно средств для оплаты пакета  Тело ответа будет представлять собой объект JSON с ключом &#x60;knowledge_base&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void createKnowledgebaseTest() throws ApiException {
        CreateKnowledgebase createKnowledgebase = null;
        CreateKnowledgebase201Response response = api.createKnowledgebase(createKnowledgebase);
        // TODO: test validations
    }

    /**
     * Добавление документа по ссылке
     *
     * Чтобы добавить в базу знаний документ по ссылке, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/knowledge-bases/{id}/documents&#x60;.  Перед добавлением выполняется HEAD-запрос к &#x60;url&#x60;: адрес должен быть публично доступен, а ответ — содержать заголовок &#x60;Content-Type&#x60; одного из поддерживаемых форматов (текст, Markdown, HTML, XML, CSV, PDF, DOC, DOCX, XLS, XLSX; JSON не поддерживается). Документ сразу ставится в очередь на индексацию. Чтобы содержимое по ссылке переиндексировалось автоматически, задайте &#x60;indexing_schedule&#x60;.  Возможные ошибки: - &#x60;400 url_is_not_accessible&#x60; — адрес недоступен или вернул ошибку - &#x60;400 unsupported_file_type&#x60; — неподдерживаемый &#x60;Content-Type&#x60;  Тело ответа будет представлять собой объект JSON с ключом &#x60;document&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void createKnowledgebaseDocumentTest() throws ApiException {
        Integer id = null;
        CreateDocument createDocument = null;
        CreateKnowledgebaseDocument201Response response = api.createKnowledgebaseDocument(id, createDocument);
        // TODO: test validations
    }

    /**
     * Удаление документа из базы знаний
     *
     * Чтобы удалить документ из базы знаний, отправьте DELETE-запрос на &#x60;/api/v1/cloud-ai/knowledge-bases/{id}/documents/{document_id}&#x60;.  Документ удаляется вместе с его фрагментами в поисковом индексе.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void deleteDocumentTest() throws ApiException {
        Integer id = null;
        Integer documentId = null;
        api.deleteDocument(id, documentId);
        // TODO: test validations
    }

    /**
     * Удаление базы знаний
     *
     * Чтобы удалить базу знаний, отправьте DELETE-запрос на &#x60;/api/v1/cloud-ai/knowledge-bases/{id}&#x60;.  База знаний отвязывается от всех агентов, её кластер базы данных OpenSearch удаляется, подписка закрывается.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void deleteKnowledgebaseTest() throws ApiException {
        Integer id = null;
        api.deleteKnowledgebase(id);
        // TODO: test validations
    }

    /**
     * Скачивание файла документа
     *
     * Чтобы скачать загруженный файл документа, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/knowledge-bases/{id}/documents/{document_id}/download&#x60;.  Доступно только для документов типа &#x60;file&#x60;. Файл возвращается потоком с исходным MIME-типом в заголовке &#x60;Content-Type&#x60; и именем в &#x60;Content-Disposition&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void downloadDocumentTest() throws ApiException {
        Integer id = null;
        Integer documentId = null;
        File response = api.downloadDocument(id, documentId);
        // TODO: test validations
    }

    /**
     * Получение базы знаний со списком документов
     *
     * Чтобы получить информацию о базе знаний вместе со всеми её документами, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/knowledge-bases/{id}&#x60;.  Тело ответа будет представлять собой объект JSON с ключом &#x60;knowledge_base&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getKnowledgebaseTest() throws ApiException {
        Integer id = null;
        CreateKnowledgebase201Response response = api.getKnowledgebase(id);
        // TODO: test validations
    }

    /**
     * Получение списка документов базы знаний
     *
     * Чтобы получить список документов базы знаний с пагинацией, отправьте GET-запрос на &#x60;/api/v2/cloud-ai/knowledge-bases/{id}/documents&#x60;.  Некорректные значения параметров пагинации и сортировки не приводят к ошибке — вместо них применяются значения по умолчанию.  Тело ответа будет представлять собой объект JSON с ключами &#x60;knowledgebase_documents&#x60; и &#x60;meta&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getKnowledgebaseDocumentsV2Test() throws ApiException {
        Integer id = null;
        Integer limit = null;
        Integer offset = null;
        String sortBy = null;
        String sortOrder = null;
        String search = null;
        GetKnowledgebaseDocumentsV2200Response response = api.getKnowledgebaseDocumentsV2(id, limit, offset, sortBy, sortOrder, search);
        // TODO: test validations
    }

    /**
     * Получение базы знаний
     *
     * Чтобы получить информацию о базе знаний, отправьте GET-запрос на &#x60;/api/v3/cloud-ai/knowledge-bases/{id}&#x60;.  Тело ответа будет представлять собой объект JSON с ключом &#x60;knowledge_base&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getKnowledgebaseV3Test() throws ApiException {
        Integer id = null;
        GetKnowledgebaseV3200Response response = api.getKnowledgebaseV3(id);
        // TODO: test validations
    }

    /**
     * Получение списка баз знаний (v1)
     *
     * Метод устарел — используйте &#x60;GET /api/v3/cloud-ai/knowledge-bases&#x60;.  Чтобы получить список баз знаний вместе со всеми их документами, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/knowledge-bases&#x60;.  Тело ответа будет представлять собой объект JSON с ключами &#x60;knowledge_bases&#x60; и &#x60;meta&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getKnowledgebasesTest() throws ApiException {
        GetKnowledgebases200Response response = api.getKnowledgebases();
        // TODO: test validations
    }

    /**
     * Получение списка баз знаний (v2)
     *
     * Чтобы получить список баз знаний, отправьте GET-запрос на &#x60;/api/v2/cloud-ai/knowledge-bases&#x60;.  Вместо списка документов возвращается их количество, привязанные агенты — списком ID. Режим поиска для каждого агента возвращает &#x60;GET /api/v3/cloud-ai/knowledge-bases&#x60;.  Тело ответа будет представлять собой объект JSON с ключами &#x60;knowledge_bases&#x60; и &#x60;meta&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getKnowledgebasesV2Test() throws ApiException {
        GetKnowledgebasesV2200Response response = api.getKnowledgebasesV2();
        // TODO: test validations
    }

    /**
     * Получение списка баз знаний
     *
     * Чтобы получить список баз знаний, отправьте GET-запрос на &#x60;/api/v3/cloud-ai/knowledge-bases&#x60;.  Ответ содержит количество документов и привязанных агентов с режимом поиска. Документы базы знаний запрашивайте отдельно — &#x60;GET /api/v2/cloud-ai/knowledge-bases/{id}/documents&#x60;.  Тело ответа будет представлять собой объект JSON с ключами &#x60;knowledge_bases&#x60; и &#x60;meta&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getKnowledgebasesV3Test() throws ApiException {
        GetKnowledgebasesV3200Response response = api.getKnowledgebasesV3();
        // TODO: test validations
    }

    /**
     * Привязка базы знаний к агенту
     *
     * Чтобы привязать базу знаний к AI агенту, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/knowledge-bases/{id}/link/{agent_id}&#x60;.  Тело запроса необязательно: в нём можно выбрать режим поиска &#x60;retrievalMode&#x60; (по умолчанию &#x60;agentic&#x60;). Изменить режим для существующей привязки можно запросом PATCH на тот же адрес.  Возможные ошибки: &#x60;404 knowledgebase_not_found&#x60;, &#x60;404 agent_not_found&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void linkKnowledgebaseToAgentTest() throws ApiException {
        Integer id = null;
        Integer agentId = null;
        LinkKnowledgebase linkKnowledgebase = null;
        api.linkKnowledgebaseToAgent(id, agentId, linkKnowledgebase);
        // TODO: test validations
    }

    /**
     * Переиндексация документа
     *
     * Чтобы переиндексировать документ, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/knowledge-bases/{id}/documents/{document_id}/reindex&#x60;.  Документ ставится в очередь на индексацию; следите за полем &#x60;status&#x60; документа. Для документа по ссылке содержимое скачивается заново. Если база знаний не в статусе &#x60;active&#x60;, документ будет проиндексирован после активации.  Возможные ошибки: &#x60;404 knowledgebase_not_found&#x60;, &#x60;404 document_not_found&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void reindexDocumentTest() throws ApiException {
        Integer id = null;
        Integer documentId = null;
        api.reindexDocument(id, documentId);
        // TODO: test validations
    }

    /**
     * Отвязка базы знаний от агента
     *
     * Чтобы отвязать базу знаний от AI агента, отправьте DELETE-запрос на &#x60;/api/v1/cloud-ai/knowledge-bases/{id}/link/{agent_id}&#x60;.  Возможные ошибки: &#x60;404 knowledgebase_not_found&#x60;, &#x60;404 agent_not_found&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void unlinkKnowledgebaseFromAgentTest() throws ApiException {
        Integer id = null;
        Integer agentId = null;
        api.unlinkKnowledgebaseFromAgent(id, agentId);
        // TODO: test validations
    }

    /**
     * Изменение базы знаний
     *
     * Чтобы изменить название или описание базы знаний, отправьте PATCH-запрос на &#x60;/api/v1/cloud-ai/knowledge-bases/{id}&#x60;.  Тело ответа будет представлять собой объект JSON с ключом &#x60;knowledge_base&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void updateKnowledgebaseTest() throws ApiException {
        Integer id = null;
        UpdateKnowledgebase updateKnowledgebase = null;
        CreateKnowledgebase201Response response = api.updateKnowledgebase(id, updateKnowledgebase);
        // TODO: test validations
    }

    /**
     * Изменение режима поиска по базе знаний для агента
     *
     * Чтобы изменить режим поиска по привязанной базе знаний для AI агента, отправьте PATCH-запрос на &#x60;/api/v1/cloud-ai/knowledge-bases/{id}/link/{agent_id}&#x60;.  Если &#x60;retrievalMode&#x60; не передан, устанавливается &#x60;agentic&#x60;. Если база знаний не привязана к агенту, запрос ничего не меняет и также возвращает &#x60;204&#x60;.  Возможные ошибки: &#x60;404 knowledgebase_not_found&#x60;, &#x60;404 agent_not_found&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void updateKnowledgebaseAgentLinkTest() throws ApiException {
        Integer id = null;
        Integer agentId = null;
        LinkKnowledgebase linkKnowledgebase = null;
        api.updateKnowledgebaseAgentLink(id, agentId, linkKnowledgebase);
        // TODO: test validations
    }

    /**
     * Изменение документа
     *
     * Чтобы изменить документ базы знаний, отправьте PATCH-запрос на &#x60;/api/v1/cloud-ai/knowledge-bases/{id}/documents/{document_id}&#x60;.  Можно изменить название, расписание переиндексации и — только для документов типа &#x60;link&#x60; — адрес. После смены адреса документ переиндексируется; изменение названия и расписания переиндексацию не запускает. Новый адрес проверяется так же, как при добавлении документа.  Тело ответа будет представлять собой объект JSON с ключом &#x60;document&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void updateKnowledgebaseDocumentTest() throws ApiException {
        Integer id = null;
        Integer documentId = null;
        UpdateDocument updateDocument = null;
        CreateKnowledgebaseDocument201Response response = api.updateKnowledgebaseDocument(id, documentId, updateDocument);
        // TODO: test validations
    }

    /**
     * Загрузка файлов в базу знаний
     *
     * Чтобы загрузить файлы в базу знаний, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/knowledge-bases/{id}/upload&#x60; с файлами в поле &#x60;files&#x60; формата &#x60;multipart/form-data&#x60;. Для каждого файла создаётся документ типа &#x60;file&#x60;, после загрузки документы ставятся в очередь на индексацию (если база знаний в статусе &#x60;active&#x60;; иначе — после активации).  Поддерживаемые форматы: TXT, Markdown (&#x60;md&#x60;, &#x60;markdown&#x60;), HTML (&#x60;html&#x60;, &#x60;htm&#x60;), XML, CSV, PDF, DOC, DOCX, XLS, XLSX, а также другие файлы с MIME-типом &#x60;text/_*&#x60;. JSON (&#x60;json&#x60;, &#x60;jsonl&#x60;, &#x60;ndjson&#x60;) не поддерживается. Размер одного файла — не более 50 МБ.  Если хотя бы один файл неподдерживаемого формата, запрос завершается ошибкой.  Тело ответа будет представлять собой объект JSON с ключами &#x60;uploadedFiles&#x60; и &#x60;documents&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void uploadFilesToKnowledgebaseTest() throws ApiException {
        Integer id = null;
        List<File> files = null;
        UploadResult response = api.uploadFilesToKnowledgebase(id, files);
        // TODO: test validations
    }

}
