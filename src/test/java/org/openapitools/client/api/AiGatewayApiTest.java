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
import org.openapitools.client.model.CreateAiKey;
import org.openapitools.client.model.CreateAiKey201Response;
import org.openapitools.client.model.CreateAiKeyLimit;
import org.openapitools.client.model.CreateAiKeyLimit201Response;
import org.openapitools.client.model.GetAccountStatus403Response;
import org.openapitools.client.model.GetAiKeyCallsStatistic200Response;
import org.openapitools.client.model.GetAiKeyLimits200Response;
import org.openapitools.client.model.GetAiKeyModelsStatistic200Response;
import org.openapitools.client.model.GetAiKeyTokenUsageStatistic200Response;
import org.openapitools.client.model.GetAiKeys200Response;
import org.openapitools.client.model.GetFinances400Response;
import org.openapitools.client.model.GetFinances401Response;
import org.openapitools.client.model.GetFinances429Response;
import org.openapitools.client.model.GetFinances500Response;
import org.openapitools.client.model.GetImage404Response;
import java.time.OffsetDateTime;
import org.openapitools.client.model.SyncAiKeyLimits;
import java.util.UUID;
import org.openapitools.client.model.UpdateAiKey;
import org.openapitools.client.model.UpdateAiKey200Response;
import org.openapitools.client.model.UpdateAiKeyLimit;
import org.openapitools.client.model.UpdateDatabaseInstance409Response;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * API tests for AiGatewayApi
 */
@Disabled
public class AiGatewayApiTest {

    private final AiGatewayApi api = new AiGatewayApi();

    /**
     * Создание ключа AI Gateway
     *
     * Чтобы создать ключ AI Gateway, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/ai-keys&#x60;.  Значение ключа (&#x60;sk-...&#x60;) возвращается в поле &#x60;key&#x60; только в ответе на этот запрос — сохраните его. Если значение утеряно, перевыпустите ключ.  Если список &#x60;fallback_models&#x60; некорректен, вернётся 400 с &#x60;error_code&#x60; &#x60;fallback_chain_invalid&#x60; (больше двух моделей или повторы, правило — в &#x60;details.reason&#x60;) или &#x60;fallback_model_not_found&#x60; (модели нет среди действующих текстовых моделей, список — в &#x60;details.model_names&#x60;).
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void createAiKeyTest() throws ApiException {
        CreateAiKey createAiKey = null;
        CreateAiKey201Response response = api.createAiKey(createAiKey);
        // TODO: test validations
    }

    /**
     * Создание лимита ключа AI Gateway
     *
     * Чтобы ограничить расход ключа AI Gateway, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/ai-keys/{key_id}/entity-limits&#x60;.  Лимит задаётся в токенах (&#x60;limit&#x60;) или в деньгах (&#x60;money_limit&#x60;, приоритетнее) на период &#x60;limit_type&#x60; — для всех моделей или для одной (&#x60;model_id&#x60;). Когда лимит исчерпан, запросы ключа к AI Gateway API завершаются ошибкой 422 до начала следующего периода. Если от лимита осталась часть, максимальный размер ответа текстового запроса уменьшается так, чтобы уложиться в остаток.  На один период и одну модель (или на все модели) можно завести только один лимит — повторный вернёт 409 с &#x60;error_code&#x60; &#x60;entity_keys_limit_already_exists&#x60;. Если не передан ни &#x60;limit&#x60;, ни &#x60;money_limit&#x60;, вернётся 400 с &#x60;error_code&#x60; &#x60;entity_keys_limit_value_required&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void createAiKeyLimitTest() throws ApiException {
        UUID keyId = null;
        CreateAiKeyLimit createAiKeyLimit = null;
        CreateAiKeyLimit201Response response = api.createAiKeyLimit(keyId, createAiKeyLimit);
        // TODO: test validations
    }

    /**
     * Удаление ключа AI Gateway
     *
     * Чтобы удалить ключ AI Gateway, отправьте DELETE-запрос на &#x60;/api/v1/cloud-ai/ai-keys/{key_id}&#x60;.  После удаления запросы с этим ключом перестают приниматься. Ключи, которыми управляет платформа (&#x60;is_readonly: true&#x60;), удалить нельзя: вернётся 403 с &#x60;error_code&#x60; &#x60;ai_key_is_readonly&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void deleteAiKeyTest() throws ApiException {
        UUID keyId = null;
        api.deleteAiKey(keyId);
        // TODO: test validations
    }

    /**
     * Удаление лимита ключа AI Gateway
     *
     * Чтобы удалить лимит ключа AI Gateway, отправьте DELETE-запрос на &#x60;/api/v1/cloud-ai/ai-keys/{key_id}/entity-limits/{limit_id}&#x60;.  Если лимит относится к другому ключу, вернётся 403.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void deleteAiKeyLimitTest() throws ApiException {
        UUID keyId = null;
        UUID limitId = null;
        api.deleteAiKeyLimit(keyId, limitId);
        // TODO: test validations
    }

    /**
     * Получение статистики запросов ключа AI Gateway
     *
     * Чтобы получить количество запросов ключа по интервалам времени, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/ai-keys/statistic/calls&#x60;.  Ключ задаётся параметрами &#x60;entity_id&#x60; (ID ключа) и &#x60;entity_type&#x60; (тип ключа). Если ключ не найден, вернётся 404. Период по умолчанию — последние 24 часа. Точки возвращаются для каждого интервала периода, включая интервалы без запросов.  Тело ответа будет представлять собой объект JSON с ключами &#x60;ai_key_calls_statistics&#x60; и &#x60;meta&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getAiKeyCallsStatisticTest() throws ApiException {
        String entityId = null;
        String entityType = null;
        String modelName = null;
        OffsetDateTime startTime = null;
        OffsetDateTime endTime = null;
        Integer interval = null;
        GetAiKeyCallsStatistic200Response response = api.getAiKeyCallsStatistic(entityId, entityType, modelName, startTime, endTime, interval);
        // TODO: test validations
    }

    /**
     * Получение лимита ключа AI Gateway
     *
     * Чтобы получить лимит ключа AI Gateway, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/ai-keys/{key_id}/entity-limits/{limit_id}&#x60;.  Если лимит относится к другому ключу, вернётся 403 с &#x60;error_code&#x60; &#x60;entity_keys_limit_forbidden&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getAiKeyLimitTest() throws ApiException {
        UUID keyId = null;
        UUID limitId = null;
        CreateAiKeyLimit201Response response = api.getAiKeyLimit(keyId, limitId);
        // TODO: test validations
    }

    /**
     * Получение списка лимитов ключа AI Gateway
     *
     * Чтобы получить лимиты расхода ключа AI Gateway, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/ai-keys/{key_id}/entity-limits&#x60;.  Тело ответа будет представлять собой объект JSON с ключами &#x60;ai_key_entity_limits&#x60; и &#x60;meta&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getAiKeyLimitsTest() throws ApiException {
        UUID keyId = null;
        String entityType = null;
        String limitType = null;
        GetAiKeyLimits200Response response = api.getAiKeyLimits(keyId, entityType, limitType);
        // TODO: test validations
    }

    /**
     * Получение списка моделей, использованных ключом AI Gateway
     *
     * Чтобы получить список моделей, к которым обращался ключ за период, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/ai-keys/statistic/models&#x60;.  Ключ задаётся параметрами &#x60;entity_id&#x60; (ID ключа) и &#x60;entity_type&#x60; (тип ключа). Если ключ не найден, вернётся 404. Период по умолчанию — последние 24 часа. Модели отсортированы по алфавиту.  Тело ответа будет представлять собой объект JSON с ключами &#x60;ai_key_models_statistics&#x60; и &#x60;meta&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getAiKeyModelsStatisticTest() throws ApiException {
        String entityId = null;
        String entityType = null;
        String modelName = null;
        OffsetDateTime startTime = null;
        OffsetDateTime endTime = null;
        GetAiKeyModelsStatistic200Response response = api.getAiKeyModelsStatistic(entityId, entityType, modelName, startTime, endTime);
        // TODO: test validations
    }

    /**
     * Получение статистики расхода токенов ключа AI Gateway
     *
     * Чтобы получить расход токенов и стоимость запросов ключа по интервалам времени, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/ai-keys/statistic/token-usage&#x60;.  Ключ задаётся параметрами &#x60;entity_id&#x60; (ID ключа) и &#x60;entity_type&#x60; (тип ключа). Если ключ не найден, вернётся 404. Период по умолчанию — последние 24 часа. Точки возвращаются для каждого интервала периода, включая интервалы без запросов.  Тело ответа будет представлять собой объект JSON с ключами &#x60;ai_key_token_statistics&#x60; и &#x60;meta&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getAiKeyTokenUsageStatisticTest() throws ApiException {
        String entityId = null;
        String entityType = null;
        String modelName = null;
        OffsetDateTime startTime = null;
        OffsetDateTime endTime = null;
        Integer interval = null;
        GetAiKeyTokenUsageStatistic200Response response = api.getAiKeyTokenUsageStatistic(entityId, entityType, modelName, startTime, endTime, interval);
        // TODO: test validations
    }

    /**
     * Получение списка ключей AI Gateway
     *
     * Чтобы получить список ключей AI Gateway, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/ai-keys&#x60;.  В списке — ключи, созданные пользователем, и ключи кластеров Kubernetes, выпущенные платформой. Значения ключей (&#x60;sk-...&#x60;) не возвращаются.  Тело ответа будет представлять собой объект JSON с ключами &#x60;ai_keys&#x60; и &#x60;meta&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getAiKeysTest() throws ApiException {
        GetAiKeys200Response response = api.getAiKeys();
        // TODO: test validations
    }

    /**
     * Перевыпуск ключа AI Gateway
     *
     * Чтобы перевыпустить ключ AI Gateway, отправьте PUT-запрос на &#x60;/api/v1/cloud-ai/ai-keys/{key_id}/regenerate&#x60;.  Ключ получает новое значение, старое перестаёт действовать сразу. Название, лимиты, запасные модели и статистика сохраняются. Новое значение (&#x60;sk-...&#x60;) возвращается в поле &#x60;key&#x60; только в ответе на этот запрос — сохраните его. Поле &#x60;expires_at&#x60; в ответе не заполняется.  Ключи, которыми управляет платформа (&#x60;is_readonly: true&#x60;), перевыпустить нельзя: вернётся 403 с &#x60;error_code&#x60; &#x60;ai_key_is_readonly&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void regenerateAiKeyTest() throws ApiException {
        UUID keyId = null;
        CreateAiKey201Response response = api.regenerateAiKey(keyId);
        // TODO: test validations
    }

    /**
     * Синхронизация лимитов ключа AI Gateway
     *
     * Чтобы задать все лимиты ключа AI Gateway на один период одним запросом, отправьте PUT-запрос на &#x60;/api/v1/cloud-ai/ai-keys/{key_id}/entity-limits/sync&#x60;.  Лимиты из &#x60;limits&#x60; создаются или обновляются (по &#x60;model_id&#x60;), а лимиты того же периода, которых нет в запросе, удаляются. Изменения применяются целиком или не применяются совсем.  Если в записи не задан ни &#x60;limit&#x60;, ни &#x60;money_limit&#x60; или &#x60;money_limit&#x60; снимается без &#x60;limit&#x60;, вернётся 400 с &#x60;error_code&#x60; &#x60;entity_keys_limit_value_required&#x60;. Если лимиты одновременно изменил другой запрос, вернётся 409 с &#x60;error_code&#x60; &#x60;entity_keys_limits_sync_conflict&#x60; — повторите запрос.  Тело ответа будет представлять собой объект JSON с ключами &#x60;ai_key_entity_limits&#x60; и &#x60;meta&#x60; — лимиты периода после синхронизации.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void syncAiKeyLimitsTest() throws ApiException {
        UUID keyId = null;
        SyncAiKeyLimits syncAiKeyLimits = null;
        GetAiKeyLimits200Response response = api.syncAiKeyLimits(keyId, syncAiKeyLimits);
        // TODO: test validations
    }

    /**
     * Изменение ключа AI Gateway
     *
     * Чтобы изменить ключ AI Gateway, отправьте PATCH-запрос на &#x60;/api/v1/cloud-ai/ai-keys/{key_id}&#x60;, передав изменяемые поля.  Ключи, которыми управляет платформа (&#x60;is_readonly: true&#x60; — ключи AI-решений и кластеров Kubernetes), изменить нельзя: вернётся 403 с &#x60;error_code&#x60; &#x60;ai_key_is_readonly&#x60;.  Ошибки в &#x60;fallback_models&#x60; — как при создании ключа (400). Поле &#x60;expires_at&#x60; в ответе этого запроса не заполняется — срок действия ключа смотрите в списке ключей.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void updateAiKeyTest() throws ApiException {
        UUID keyId = null;
        UpdateAiKey updateAiKey = null;
        UpdateAiKey200Response response = api.updateAiKey(keyId, updateAiKey);
        // TODO: test validations
    }

    /**
     * Изменение лимита ключа AI Gateway
     *
     * Чтобы изменить лимит ключа AI Gateway, отправьте PATCH-запрос на &#x60;/api/v1/cloud-ai/ai-keys/{key_id}/entity-limits/{limit_id}&#x60;.  Не переданные поля не меняются. Если не передан ни &#x60;limit&#x60;, ни &#x60;money_limit&#x60; или &#x60;money_limit&#x60; снимается без &#x60;limit&#x60;, вернётся 400 с &#x60;error_code&#x60; &#x60;entity_keys_limit_value_required&#x60;. Если лимит относится к другому ключу, вернётся 403.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void updateAiKeyLimitTest() throws ApiException {
        UUID keyId = null;
        UUID limitId = null;
        UpdateAiKeyLimit updateAiKeyLimit = null;
        CreateAiKeyLimit201Response response = api.updateAiKeyLimit(keyId, limitId, updateAiKeyLimit);
        // TODO: test validations
    }

}
