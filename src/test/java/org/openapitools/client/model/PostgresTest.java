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


package org.openapitools.client.model;

import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * Model tests for Postgres
 */
public class PostgresTest {
    private final Postgres model = new Postgres();

    /**
     * Model tests for Postgres
     */
    @Test
    public void testPostgres() {
        // TODO: test Postgres
    }

    /**
     * Test the property 'maxConnections'
     */
    @Test
    public void maxConnectionsTest() {
        // TODO: test maxConnections
    }

    /**
     * Test the property 'autovacuumAnalyzeScaleFactor'
     */
    @Test
    public void autovacuumAnalyzeScaleFactorTest() {
        // TODO: test autovacuumAnalyzeScaleFactor
    }

    /**
     * Test the property 'autovacuumMaxWorkers'
     */
    @Test
    public void autovacuumMaxWorkersTest() {
        // TODO: test autovacuumMaxWorkers
    }

    /**
     * Test the property 'autovacuumNaptime'
     */
    @Test
    public void autovacuumNaptimeTest() {
        // TODO: test autovacuumNaptime
    }

    /**
     * Test the property 'autovacuumVacuumInsertScaleFactor'
     */
    @Test
    public void autovacuumVacuumInsertScaleFactorTest() {
        // TODO: test autovacuumVacuumInsertScaleFactor
    }

    /**
     * Test the property 'autovacuumVacuumScaleFactor'
     */
    @Test
    public void autovacuumVacuumScaleFactorTest() {
        // TODO: test autovacuumVacuumScaleFactor
    }

    /**
     * Test the property 'autovacuumWorkMem'
     */
    @Test
    public void autovacuumWorkMemTest() {
        // TODO: test autovacuumWorkMem
    }

    /**
     * Test the property 'bgwriterDelay'
     */
    @Test
    public void bgwriterDelayTest() {
        // TODO: test bgwriterDelay
    }

    /**
     * Test the property 'bgwriterLruMaxpages'
     */
    @Test
    public void bgwriterLruMaxpagesTest() {
        // TODO: test bgwriterLruMaxpages
    }

    /**
     * Test the property 'deadlockTimeout'
     */
    @Test
    public void deadlockTimeoutTest() {
        // TODO: test deadlockTimeout
    }

    /**
     * Test the property 'ginPendingListLimit'
     */
    @Test
    public void ginPendingListLimitTest() {
        // TODO: test ginPendingListLimit
    }

    /**
     * Test the property 'idleInTransactionSessionTimeout'
     */
    @Test
    public void idleInTransactionSessionTimeoutTest() {
        // TODO: test idleInTransactionSessionTimeout
    }

    /**
     * Test the property 'joinCollapseLimit'
     */
    @Test
    public void joinCollapseLimitTest() {
        // TODO: test joinCollapseLimit
    }

    /**
     * Test the property 'lockTimeout'
     */
    @Test
    public void lockTimeoutTest() {
        // TODO: test lockTimeout
    }

    /**
     * Test the property 'maxPreparedTransactions'
     */
    @Test
    public void maxPreparedTransactionsTest() {
        // TODO: test maxPreparedTransactions
    }

    /**
     * Test the property 'sharedBuffers'
     */
    @Test
    public void sharedBuffersTest() {
        // TODO: test sharedBuffers
    }

    /**
     * Test the property 'logMinDurationStatement'
     */
    @Test
    public void logMinDurationStatementTest() {
        // TODO: test logMinDurationStatement
    }

    /**
     * Test the property 'walBuffers'
     */
    @Test
    public void walBuffersTest() {
        // TODO: test walBuffers
    }

    /**
     * Test the property 'tempBuffers'
     */
    @Test
    public void tempBuffersTest() {
        // TODO: test tempBuffers
    }

    /**
     * Test the property 'workMem'
     */
    @Test
    public void workMemTest() {
        // TODO: test workMem
    }

    /**
     * Test the property 'defaultTransactionIsolation'
     */
    @Test
    public void defaultTransactionIsolationTest() {
        // TODO: test defaultTransactionIsolation
    }

    /**
     * Test the property 'effectiveCacheSize'
     */
    @Test
    public void effectiveCacheSizeTest() {
        // TODO: test effectiveCacheSize
    }

    /**
     * Test the property 'maxWalSize'
     */
    @Test
    public void maxWalSizeTest() {
        // TODO: test maxWalSize
    }

    /**
     * Test the property 'minWalSize'
     */
    @Test
    public void minWalSizeTest() {
        // TODO: test minWalSize
    }

    /**
     * Test the property 'walLevel'
     */
    @Test
    public void walLevelTest() {
        // TODO: test walLevel
    }

    /**
     * Test the property 'maxReplicationSlots'
     */
    @Test
    public void maxReplicationSlotsTest() {
        // TODO: test maxReplicationSlots
    }

    /**
     * Test the property 'maxWalSenders'
     */
    @Test
    public void maxWalSendersTest() {
        // TODO: test maxWalSenders
    }

    /**
     * Test the property 'maxWorkerProcesses'
     */
    @Test
    public void maxWorkerProcessesTest() {
        // TODO: test maxWorkerProcesses
    }

    /**
     * Test the property 'maxLogicalReplicationWorkers'
     */
    @Test
    public void maxLogicalReplicationWorkersTest() {
        // TODO: test maxLogicalReplicationWorkers
    }

    /**
     * Test the property 'maxParallelMaintenanceWorkers'
     */
    @Test
    public void maxParallelMaintenanceWorkersTest() {
        // TODO: test maxParallelMaintenanceWorkers
    }

    /**
     * Test the property 'maxParallelWorkers'
     */
    @Test
    public void maxParallelWorkersTest() {
        // TODO: test maxParallelWorkers
    }

    /**
     * Test the property 'maxParallelWorkersPerGather'
     */
    @Test
    public void maxParallelWorkersPerGatherTest() {
        // TODO: test maxParallelWorkersPerGather
    }

    /**
     * Test the property 'arrayNulls'
     */
    @Test
    public void arrayNullsTest() {
        // TODO: test arrayNulls
    }

    /**
     * Test the property 'backendFlushAfter'
     */
    @Test
    public void backendFlushAfterTest() {
        // TODO: test backendFlushAfter
    }

    /**
     * Test the property 'backslashQuote'
     */
    @Test
    public void backslashQuoteTest() {
        // TODO: test backslashQuote
    }

    /**
     * Test the property 'bgwriterFlushAfter'
     */
    @Test
    public void bgwriterFlushAfterTest() {
        // TODO: test bgwriterFlushAfter
    }

    /**
     * Test the property 'bgwriterLruMultiplier'
     */
    @Test
    public void bgwriterLruMultiplierTest() {
        // TODO: test bgwriterLruMultiplier
    }

    /**
     * Test the property 'defaultTransactionReadOnly'
     */
    @Test
    public void defaultTransactionReadOnlyTest() {
        // TODO: test defaultTransactionReadOnly
    }

    /**
     * Test the property 'enableHashagg'
     */
    @Test
    public void enableHashaggTest() {
        // TODO: test enableHashagg
    }

    /**
     * Test the property 'enableHashjoin'
     */
    @Test
    public void enableHashjoinTest() {
        // TODO: test enableHashjoin
    }

    /**
     * Test the property 'enableIncrementalSort'
     */
    @Test
    public void enableIncrementalSortTest() {
        // TODO: test enableIncrementalSort
    }

    /**
     * Test the property 'enableIndexscan'
     */
    @Test
    public void enableIndexscanTest() {
        // TODO: test enableIndexscan
    }

    /**
     * Test the property 'enableIndexonlyscan'
     */
    @Test
    public void enableIndexonlyscanTest() {
        // TODO: test enableIndexonlyscan
    }

    /**
     * Test the property 'enableMaterial'
     */
    @Test
    public void enableMaterialTest() {
        // TODO: test enableMaterial
    }

    /**
     * Test the property 'enableMemoize'
     */
    @Test
    public void enableMemoizeTest() {
        // TODO: test enableMemoize
    }

    /**
     * Test the property 'enableMergejoin'
     */
    @Test
    public void enableMergejoinTest() {
        // TODO: test enableMergejoin
    }

    /**
     * Test the property 'enableParallelAppend'
     */
    @Test
    public void enableParallelAppendTest() {
        // TODO: test enableParallelAppend
    }

    /**
     * Test the property 'enableParallelHash'
     */
    @Test
    public void enableParallelHashTest() {
        // TODO: test enableParallelHash
    }

    /**
     * Test the property 'enablePartitionPruning'
     */
    @Test
    public void enablePartitionPruningTest() {
        // TODO: test enablePartitionPruning
    }

    /**
     * Test the property 'enablePartitionwiseJoin'
     */
    @Test
    public void enablePartitionwiseJoinTest() {
        // TODO: test enablePartitionwiseJoin
    }

    /**
     * Test the property 'enablePartitionwiseAggregate'
     */
    @Test
    public void enablePartitionwiseAggregateTest() {
        // TODO: test enablePartitionwiseAggregate
    }

    /**
     * Test the property 'enableSeqscan'
     */
    @Test
    public void enableSeqscanTest() {
        // TODO: test enableSeqscan
    }

    /**
     * Test the property 'enableSort'
     */
    @Test
    public void enableSortTest() {
        // TODO: test enableSort
    }

    /**
     * Test the property 'enableTidscan'
     */
    @Test
    public void enableTidscanTest() {
        // TODO: test enableTidscan
    }

    /**
     * Test the property 'exitOnError'
     */
    @Test
    public void exitOnErrorTest() {
        // TODO: test exitOnError
    }

    /**
     * Test the property 'fromCollapseLimit'
     */
    @Test
    public void fromCollapseLimitTest() {
        // TODO: test fromCollapseLimit
    }

    /**
     * Test the property 'jit'
     */
    @Test
    public void jitTest() {
        // TODO: test jit
    }

    /**
     * Test the property 'planCacheMode'
     */
    @Test
    public void planCacheModeTest() {
        // TODO: test planCacheMode
    }

    /**
     * Test the property 'quoteAllIdentifiers'
     */
    @Test
    public void quoteAllIdentifiersTest() {
        // TODO: test quoteAllIdentifiers
    }

    /**
     * Test the property 'standardConformingStrings'
     */
    @Test
    public void standardConformingStringsTest() {
        // TODO: test standardConformingStrings
    }

    /**
     * Test the property 'statementTimeout'
     */
    @Test
    public void statementTimeoutTest() {
        // TODO: test statementTimeout
    }

    /**
     * Test the property 'timezone'
     */
    @Test
    public void timezoneTest() {
        // TODO: test timezone
    }

    /**
     * Test the property 'transformNullEquals'
     */
    @Test
    public void transformNullEqualsTest() {
        // TODO: test transformNullEquals
    }

    /**
     * Test the property 'maxLocksPerTransaction'
     */
    @Test
    public void maxLocksPerTransactionTest() {
        // TODO: test maxLocksPerTransaction
    }

    /**
     * Test the property 'autovacuumVacuumCostLimit'
     */
    @Test
    public void autovacuumVacuumCostLimitTest() {
        // TODO: test autovacuumVacuumCostLimit
    }

    /**
     * Test the property 'checkpointTimeout'
     */
    @Test
    public void checkpointTimeoutTest() {
        // TODO: test checkpointTimeout
    }

    /**
     * Test the property 'checkpointCompletionTarget'
     */
    @Test
    public void checkpointCompletionTargetTest() {
        // TODO: test checkpointCompletionTarget
    }

    /**
     * Test the property 'walCompression'
     */
    @Test
    public void walCompressionTest() {
        // TODO: test walCompression
    }

    /**
     * Test the property 'randomPageCost'
     */
    @Test
    public void randomPageCostTest() {
        // TODO: test randomPageCost
    }

    /**
     * Test the property 'effectiveIoConcurrency'
     */
    @Test
    public void effectiveIoConcurrencyTest() {
        // TODO: test effectiveIoConcurrency
    }

    /**
     * Test the property 'logLockWaits'
     */
    @Test
    public void logLockWaitsTest() {
        // TODO: test logLockWaits
    }

    /**
     * Test the property 'logTempFiles'
     */
    @Test
    public void logTempFilesTest() {
        // TODO: test logTempFiles
    }

    /**
     * Test the property 'trackIoTiming'
     */
    @Test
    public void trackIoTimingTest() {
        // TODO: test trackIoTiming
    }

    /**
     * Test the property 'maintenanceWorkMem'
     */
    @Test
    public void maintenanceWorkMemTest() {
        // TODO: test maintenanceWorkMem
    }

    /**
     * Test the property 'idleSessionTimeout'
     */
    @Test
    public void idleSessionTimeoutTest() {
        // TODO: test idleSessionTimeout
    }

    /**
     * Test the property 'ioMethod'
     */
    @Test
    public void ioMethodTest() {
        // TODO: test ioMethod
    }

    /**
     * Test the property 'ioWorkers'
     */
    @Test
    public void ioWorkersTest() {
        // TODO: test ioWorkers
    }

}
