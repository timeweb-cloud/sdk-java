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
 * Model tests for Mysql
 */
public class MysqlTest {
    private final Mysql model = new Mysql();

    /**
     * Model tests for Mysql
     */
    @Test
    public void testMysql() {
        // TODO: test Mysql
    }

    /**
     * Test the property 'joinBufferSize'
     */
    @Test
    public void joinBufferSizeTest() {
        // TODO: test joinBufferSize
    }

    /**
     * Test the property 'maxConnections'
     */
    @Test
    public void maxConnectionsTest() {
        // TODO: test maxConnections
    }

    /**
     * Test the property 'sortBufferSize'
     */
    @Test
    public void sortBufferSizeTest() {
        // TODO: test sortBufferSize
    }

    /**
     * Test the property 'threadCacheSize'
     */
    @Test
    public void threadCacheSizeTest() {
        // TODO: test threadCacheSize
    }

    /**
     * Test the property 'innodbBufferPoolSize'
     */
    @Test
    public void innodbBufferPoolSizeTest() {
        // TODO: test innodbBufferPoolSize
    }

    /**
     * Test the property 'autoIncrementIncrement'
     */
    @Test
    public void autoIncrementIncrementTest() {
        // TODO: test autoIncrementIncrement
    }

    /**
     * Test the property 'autoIncrementOffset'
     */
    @Test
    public void autoIncrementOffsetTest() {
        // TODO: test autoIncrementOffset
    }

    /**
     * Test the property 'innodbIoCapacity'
     */
    @Test
    public void innodbIoCapacityTest() {
        // TODO: test innodbIoCapacity
    }

    /**
     * Test the property 'innodbPurgeThreads'
     */
    @Test
    public void innodbPurgeThreadsTest() {
        // TODO: test innodbPurgeThreads
    }

    /**
     * Test the property 'innodbReadIoThreads'
     */
    @Test
    public void innodbReadIoThreadsTest() {
        // TODO: test innodbReadIoThreads
    }

    /**
     * Test the property 'innodbThreadConcurrency'
     */
    @Test
    public void innodbThreadConcurrencyTest() {
        // TODO: test innodbThreadConcurrency
    }

    /**
     * Test the property 'innodbWriteIoThreads'
     */
    @Test
    public void innodbWriteIoThreadsTest() {
        // TODO: test innodbWriteIoThreads
    }

    /**
     * Test the property 'innodbLogFileSize'
     */
    @Test
    public void innodbLogFileSizeTest() {
        // TODO: test innodbLogFileSize
    }

    /**
     * Test the property 'maxAllowedPacket'
     */
    @Test
    public void maxAllowedPacketTest() {
        // TODO: test maxAllowedPacket
    }

    /**
     * Test the property 'maxHeapTableSize'
     */
    @Test
    public void maxHeapTableSizeTest() {
        // TODO: test maxHeapTableSize
    }

    /**
     * Test the property 'sqlMode'
     */
    @Test
    public void sqlModeTest() {
        // TODO: test sqlMode
    }

    /**
     * Test the property 'queryCacheType'
     */
    @Test
    public void queryCacheTypeTest() {
        // TODO: test queryCacheType
    }

    /**
     * Test the property 'queryCacheSize'
     */
    @Test
    public void queryCacheSizeTest() {
        // TODO: test queryCacheSize
    }

    /**
     * Test the property 'queryCacheLimit'
     */
    @Test
    public void queryCacheLimitTest() {
        // TODO: test queryCacheLimit
    }

    /**
     * Test the property 'innodbFlushLogAtTrxCommit'
     */
    @Test
    public void innodbFlushLogAtTrxCommitTest() {
        // TODO: test innodbFlushLogAtTrxCommit
    }

    /**
     * Test the property 'transactionIsolation'
     */
    @Test
    public void transactionIsolationTest() {
        // TODO: test transactionIsolation
    }

    /**
     * Test the property 'longQueryTime'
     */
    @Test
    public void longQueryTimeTest() {
        // TODO: test longQueryTime
    }

    /**
     * Test the property 'tmpTableSize'
     */
    @Test
    public void tmpTableSizeTest() {
        // TODO: test tmpTableSize
    }

    /**
     * Test the property 'tableOpenCache'
     */
    @Test
    public void tableOpenCacheTest() {
        // TODO: test tableOpenCache
    }

    /**
     * Test the property 'tableOpenCacheInstances'
     */
    @Test
    public void tableOpenCacheInstancesTest() {
        // TODO: test tableOpenCacheInstances
    }

    /**
     * Test the property 'innodbFlushMethod'
     */
    @Test
    public void innodbFlushMethodTest() {
        // TODO: test innodbFlushMethod
    }

    /**
     * Test the property 'innodbStrictMode'
     */
    @Test
    public void innodbStrictModeTest() {
        // TODO: test innodbStrictMode
    }

    /**
     * Test the property 'slowQueryLog'
     */
    @Test
    public void slowQueryLogTest() {
        // TODO: test slowQueryLog
    }

    /**
     * Test the property 'binlogCacheSize'
     */
    @Test
    public void binlogCacheSizeTest() {
        // TODO: test binlogCacheSize
    }

    /**
     * Test the property 'binlogGroupCommitSyncDelay'
     */
    @Test
    public void binlogGroupCommitSyncDelayTest() {
        // TODO: test binlogGroupCommitSyncDelay
    }

    /**
     * Test the property 'binlogRowImage'
     */
    @Test
    public void binlogRowImageTest() {
        // TODO: test binlogRowImage
    }

    /**
     * Test the property 'binlogRowsQueryLogEvents'
     */
    @Test
    public void binlogRowsQueryLogEventsTest() {
        // TODO: test binlogRowsQueryLogEvents
    }

    /**
     * Test the property 'characterSetServer'
     */
    @Test
    public void characterSetServerTest() {
        // TODO: test characterSetServer
    }

    /**
     * Test the property 'explicitDefaultsForTimestamp'
     */
    @Test
    public void explicitDefaultsForTimestampTest() {
        // TODO: test explicitDefaultsForTimestamp
    }

    /**
     * Test the property 'groupConcatMaxLen'
     */
    @Test
    public void groupConcatMaxLenTest() {
        // TODO: test groupConcatMaxLen
    }

    /**
     * Test the property 'innodbAdaptiveHashIndex'
     */
    @Test
    public void innodbAdaptiveHashIndexTest() {
        // TODO: test innodbAdaptiveHashIndex
    }

    /**
     * Test the property 'innodbLockWaitTimeout'
     */
    @Test
    public void innodbLockWaitTimeoutTest() {
        // TODO: test innodbLockWaitTimeout
    }

    /**
     * Test the property 'innodbNumaInterleave'
     */
    @Test
    public void innodbNumaInterleaveTest() {
        // TODO: test innodbNumaInterleave
    }

    /**
     * Test the property 'netReadTimeout'
     */
    @Test
    public void netReadTimeoutTest() {
        // TODO: test netReadTimeout
    }

    /**
     * Test the property 'netWriteTimeout'
     */
    @Test
    public void netWriteTimeoutTest() {
        // TODO: test netWriteTimeout
    }

    /**
     * Test the property 'regexpTimeLimit'
     */
    @Test
    public void regexpTimeLimitTest() {
        // TODO: test regexpTimeLimit
    }

    /**
     * Test the property 'syncBinlog'
     */
    @Test
    public void syncBinlogTest() {
        // TODO: test syncBinlog
    }

    /**
     * Test the property 'tableDefinitionCache'
     */
    @Test
    public void tableDefinitionCacheTest() {
        // TODO: test tableDefinitionCache
    }

    /**
     * Test the property 'logBinTrustFunctionCreators'
     */
    @Test
    public void logBinTrustFunctionCreatorsTest() {
        // TODO: test logBinTrustFunctionCreators
    }

    /**
     * Test the property 'skipNameResolve'
     */
    @Test
    public void skipNameResolveTest() {
        // TODO: test skipNameResolve
    }

    /**
     * Test the property 'innodbRedoLogCapacity'
     */
    @Test
    public void innodbRedoLogCapacityTest() {
        // TODO: test innodbRedoLogCapacity
    }

    /**
     * Test the property 'waitTimeout'
     */
    @Test
    public void waitTimeoutTest() {
        // TODO: test waitTimeout
    }

    /**
     * Test the property 'interactiveTimeout'
     */
    @Test
    public void interactiveTimeoutTest() {
        // TODO: test interactiveTimeout
    }

    /**
     * Test the property 'defaultTimeZone'
     */
    @Test
    public void defaultTimeZoneTest() {
        // TODO: test defaultTimeZone
    }

    /**
     * Test the property 'pxcStrictMode'
     */
    @Test
    public void pxcStrictModeTest() {
        // TODO: test pxcStrictMode
    }

}
