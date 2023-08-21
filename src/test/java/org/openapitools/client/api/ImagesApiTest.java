/*
 * Timeweb Cloud API
 * # Введение API Timeweb Cloud позволяет вам управлять ресурсами в облаке программным способом с использованием обычных HTTP-запросов.  Множество функций, которые доступны в панели управления Timeweb Cloud, также доступны через API, что позволяет вам автоматизировать ваши собственные сценарии.  В этой документации сперва будет описан общий дизайн и принципы работы API, а после этого конкретные конечные точки. Также будут приведены примеры запросов к ним.   ## Запросы Запросы должны выполняться по протоколу `HTTPS`, чтобы гарантировать шифрование транзакций. Поддерживаются следующие методы запроса: |Метод|Применение| |--- |--- | |GET|Извлекает данные о коллекциях и отдельных ресурсах.| |POST|Для коллекций создает новый ресурс этого типа. Также используется для выполнения действий с конкретным ресурсом.| |PUT|Обновляет существующий ресурс.| |PATCH|Некоторые ресурсы поддерживают частичное обновление, то есть обновление только части атрибутов ресурса, в этом случае вместо метода PUT будет использован PATCH.| |DELETE|Удаляет ресурс.|  Методы `POST`, `PUT` и `PATCH` могут включать объект в тело запроса с типом содержимого `application/json`.  ### Параметры в запросах Некоторые коллекции поддерживают пагинацию, поиск или сортировку в запросах. В параметрах запроса требуется передать: - `limit` — обозначает количество записей, которое необходимо вернуть  - `offset` — указывает на смещение, относительно начала списка  - `search` — позволяет указать набор символов для поиска  - `sort` — можно задать правило сортировки коллекции  ## Ответы Запросы вернут один из следующих кодов состояния ответа HTTP:  |Статус|Описание| |--- |--- | |200 OK|Действие с ресурсом было выполнено успешно.| |201 Created|Ресурс был успешно создан. При этом ресурс может быть как уже готовым к использованию, так и находиться в процессе запуска.| |204 No Content|Действие с ресурсом было выполнено успешно, и ответ не содержит дополнительной информации в теле.| |400 Bad Request|Был отправлен неверный запрос, например, в нем отсутствуют обязательные параметры и т. д. Тело ответа будет содержать дополнительную информацию об ошибке.| |401 Unauthorized|Ошибка аутентификации.| |403 Forbidden|Аутентификация прошла успешно, но недостаточно прав для выполнения действия.| |404 Not Found|Запрашиваемый ресурс не найден.| |409 Conflict|Запрос конфликтует с текущим состоянием.| |423 Locked|Ресурс из запроса заблокирован от применения к нему указанного метода.| |429 Too Many Requests|Был достигнут лимит по количеству запросов в единицу времени.| |500 Internal Server Error|При выполнении запроса произошла какая-то внутренняя ошибка. Чтобы решить эту проблему, лучше всего создать тикет в панели управления.|  ### Структура успешного ответа Все конечные точки будут возвращать данные в формате `JSON`. Ответы на `GET`-запросы будут иметь на верхнем уровне следующую структуру атрибутов:  |Название поля|Тип|Описание| |--- |--- |--- | |[entity_name]|object, object[], string[], number[], boolean|Динамическое поле, которое будет меняться в зависимости от запрашиваемого ресурса и будет содержать все атрибуты, необходимые для описания этого ресурса. Например, при запросе списка баз данных будет возвращаться поле `dbs`, а при запросе конкретного облачного сервера `server`. Для некоторых конечных точек в ответе может возвращаться сразу несколько ресурсов.| |meta|object|Опционально. Объект, который содержит вспомогательную информацию о ресурсе. Чаще всего будет встречаться при запросе коллекций и содержать поле `total`, которое будет указывать на количество элементов в коллекции.| |response_id|string|Опционально. В большинстве случаев в ответе будет содержаться уникальный идентификатор ответа в формате UUIDv4, который однозначно указывает на ваш запрос внутри нашей системы. Если вам потребуется задать вопрос нашей поддержке, приложите к вопросу этот идентификатор — так мы сможем найти ответ на него намного быстрее. Также вы можете использовать этот идентификатор, чтобы убедиться, что это новый ответ на запрос и результат не был получен из кэша.|  Пример запроса на получение списка SSH-ключей: ```     HTTP/2.0 200 OK     {       \"ssh_keys\":[           {             \"body\":\"ssh-rsa AAAAB3NzaC1sdfghjkOAsBwWhs= example@device.local\",             \"created_at\":\"2021-09-15T19:52:27Z\",             \"expired_at\":null,             \"id\":5297,             \"is_default\":false,             \"name\":\"example@device.local\",             \"used_at\":null,             \"used_by\":[]           }       ],       \"meta\":{           \"total\":1       },       \"response_id\":\"94608d15-8672-4eed-8ab6-28bd6fa3cdf7\"     } ```  ### Структура ответа с ошибкой |Название поля|Тип|Описание| |--- |--- |--- | |status_code|number|Короткий числовой идентификатор ошибки.| |error_code|string|Короткий текстовый идентификатор ошибки, который уточняет числовой идентификатор и удобен для программной обработки. Самый простой пример — это код `not_found` для ошибки 404.| |message|string, string[]|Опционально. В большинстве случаев в ответе будет содержаться человекочитаемое подробное описание ошибки или ошибок, которые помогут понять, что нужно исправить.| |response_id|string|Опционально. В большинстве случае в ответе будет содержаться уникальный идентификатор ответа в формате UUIDv4, который однозначно указывает на ваш запрос внутри нашей системы. Если вам потребуется задать вопрос нашей поддержке, приложите к вопросу этот идентификатор — так мы сможем найти ответ на него намного быстрее.|  Пример: ```     HTTP/2.0 403 Forbidden     {       \"status_code\": 403,       \"error_code\":  \"forbidden\",       \"message\":     \"You do not have access for the attempted action\",       \"response_id\": \"94608d15-8672-4eed-8ab6-28bd6fa3cdf7\"     } ```  ## Статусы ресурсов Важно учесть, что при создании большинства ресурсов внутри платформы вам будет сразу возвращен ответ от сервера со статусом `200 OK` или `201 Created` и идентификатором созданного ресурса в теле ответа, но при этом этот ресурс может быть ещё в *состоянии запуска*.  Для того чтобы понять, в каком состоянии сейчас находится ваш ресурс, мы добавили поле `status` в ответ на получение информации о ресурсе.  Список статусов будет отличаться в зависимости от типа ресурса. Увидеть поддерживаемый список статусов вы сможете в описании каждого конкретного ресурса.     ## Ограничение скорости запросов (Rate Limiting) Чтобы обеспечить стабильность для всех пользователей, Timeweb Cloud защищает API от всплесков входящего трафика, анализируя количество запросов c каждого аккаунта к каждой конечной точке.  Если ваше приложение отправляет более 20 запросов в секунду на одну конечную точку, то для этого запроса API может вернуть код состояния HTTP `429 Too Many Requests`.   ## Аутентификация Доступ к API осуществляется с помощью JWT-токена. Токенами можно управлять внутри панели управления Timeweb Cloud в разделе *API и Terraform*.  Токен необходимо передавать в заголовке каждого запроса в формате: ```   Authorization: Bearer $TIMEWEB_CLOUD_TOKEN ```  ## Формат примеров API Примеры в этой документации описаны с помощью `curl`, HTTP-клиента командной строки. На компьютерах `Linux` и `macOS` обычно по умолчанию установлен `curl`, и он доступен для загрузки на всех популярных платформах, включая `Windows`.  Каждый пример разделен на несколько строк символом `\\`, который совместим с `bash`. Типичный пример выглядит так: ```   curl -X PATCH      -H \"Content-Type: application/json\"      -H \"Authorization: Bearer $TIMEWEB_CLOUD_TOKEN\"      -d '{\"name\":\"Cute Corvus\",\"comment\":\"Development Server\"}'      \"https://api.timeweb.cloud/api/v1/dedicated/1051\" ``` - Параметр `-X` задает метод запроса. Для согласованности метод будет указан во всех примерах, даже если он явно не требуется для методов `GET`. - Строки `-H` задают требуемые HTTP-заголовки. - Примеры, для которых требуется объект JSON в теле запроса, передают требуемые данные через параметр `-d`.  Чтобы использовать приведенные примеры, не подставляя каждый раз в них свой токен, вы можете добавить токен один раз в переменные окружения в вашей консоли. Например, на `Linux` это можно сделать с помощью команды:  ``` TIMEWEB_CLOUD_TOKEN=\"token\" ```  После этого токен будет автоматически подставляться в ваши запросы.  Обратите внимание, что все значения в этой документации являются примерами. Не полагайтесь на идентификаторы операционных систем, тарифов и т.д., используемые в примерах. Используйте соответствующую конечную точку для получения значений перед созданием ресурсов.   ## Версионирование API построено согласно принципам [семантического версионирования](https://semver.org/lang/ru). Это значит, что мы гарантируем обратную совместимость всех изменений в пределах одной мажорной версии.  Мажорная версия каждой конечной точки обозначается в пути запроса, например, запрос `/api/v1/servers` указывает, что этот метод имеет версию 1.
 *
 * The version of the OpenAPI document: 
 * Contact: info@timeweb.cloud
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */


package org.openapitools.client.api;

import org.openapitools.client.ApiException;
import org.openapitools.client.model.BaseError;
import org.openapitools.client.model.CreateImage201Response;
import org.openapitools.client.model.CreateImageDownloadUrl201Response;
import org.openapitools.client.model.GetFinances400Response;
import org.openapitools.client.model.GetFinances401Response;
import org.openapitools.client.model.GetFinances404Response;
import org.openapitools.client.model.GetFinances429Response;
import org.openapitools.client.model.GetFinances500Response;
import org.openapitools.client.model.GetImageDownloadURLs200Response;
import org.openapitools.client.model.GetImages200Response;
import org.openapitools.client.model.ImageInAPI;
import org.openapitools.client.model.ImageUpdateAPI;
import org.openapitools.client.model.ImageUrlIn;
import org.openapitools.client.model.UploadImage200Response;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * API tests for ImagesApi
 */
@Disabled
public class ImagesApiTest {

    private final ImagesApi api = new ImagesApi();

    /**
     * Создание образа
     *
     * Чтобы создать образ, отправьте POST запрос в &#x60;/api/v1/images&#x60;, задав необходимые атрибуты.   Для загрузки собственного образа вам нужно отправить параметры &#x60;location&#x60;, &#x60;os&#x60; и не указывать &#x60;disk_id&#x60;. Поддерживается два способа загрузки:  1. По ссылке. Для этого укажите &#x60;upload_url&#x60; с ссылкой на загрузку образа 2. Из файла. Для этого воспользуйтесь методом POST &#x60;/api/v1/images/{image_id}&#x60; Образ будет создан с использованием предоставленной информации.    Тело ответа будет содержать объект JSON с информацией о созданном образе.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void createImageTest() throws ApiException {
        ImageInAPI imageInAPI = null;
        CreateImage201Response response = api.createImage(imageInAPI);
        // TODO: test validations
    }

    /**
     * Создание ссылки на скачивание образа
     *
     * Чтобы создать ссылку на скачивание образа, отправьте запрос POST в &#x60;/api/v1/images/{image_id}/download-url&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void createImageDownloadUrlTest() throws ApiException {
        String imageId = null;
        ImageUrlIn imageUrlIn = null;
        CreateImageDownloadUrl201Response response = api.createImageDownloadUrl(imageId, imageUrlIn);
        // TODO: test validations
    }

    /**
     * Удаление образа
     *
     * Чтобы удалить образ, отправьте запрос DELETE в &#x60;/api/v1/images/{image_id}&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void deleteImageTest() throws ApiException {
        String imageId = null;
        api.deleteImage(imageId);
        // TODO: test validations
    }

    /**
     * Удаление ссылки на образ
     *
     * Чтобы удалить ссылку на образ, отправьте DELETE запрос в &#x60;/api/v1/images/{image_id}/download-url/{image_url_id}&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void deleteImageDownloadURLTest() throws ApiException {
        String imageId = null;
        String imageUrlId = null;
        api.deleteImageDownloadURL(imageId, imageUrlId);
        // TODO: test validations
    }

    /**
     * Получение информации о образе
     *
     * Чтобы получить образ, отправьте запрос GET в &#x60;/api/v1/images/{image_id}&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getImageTest() throws ApiException {
        String imageId = null;
        CreateImage201Response response = api.getImage(imageId);
        // TODO: test validations
    }

    /**
     * Получение информации о ссылке на скачивание образа
     *
     * Чтобы получить информацию о ссылке на скачивание образа, отправьте запрос GET в &#x60;/api/v1/images/{image_id}/download-url/{image_url_id}&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getImageDownloadURLTest() throws ApiException {
        String imageId = null;
        String imageUrlId = null;
        CreateImageDownloadUrl201Response response = api.getImageDownloadURL(imageId, imageUrlId);
        // TODO: test validations
    }

    /**
     * Получение информации о ссылках на скачивание образов
     *
     * Чтобы получить информацию о ссылках на скачивание образов, отправьте запрос GET в &#x60;/api/v1/images/{image_id}/download-url&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getImageDownloadURLsTest() throws ApiException {
        String imageId = null;
        Integer limit = null;
        Integer offset = null;
        GetImageDownloadURLs200Response response = api.getImageDownloadURLs(imageId, limit, offset);
        // TODO: test validations
    }

    /**
     * Получение списка образов
     *
     * Чтобы получить список образов, отправьте GET запрос на &#x60;/api/v1/images&#x60;
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getImagesTest() throws ApiException {
        Integer limit = null;
        Integer offset = null;
        GetImages200Response response = api.getImages(limit, offset);
        // TODO: test validations
    }

    /**
     * Обновление информации о образе
     *
     * Чтобы обновить только определенные атрибуты образа, отправьте запрос PATCH в &#x60;/api/v1/images/{image_id}&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void updateImageTest() throws ApiException {
        String imageId = null;
        ImageUpdateAPI imageUpdateAPI = null;
        CreateImage201Response response = api.updateImage(imageId, imageUpdateAPI);
        // TODO: test validations
    }

    /**
     * Загрузка образа
     *
     * Чтобы загрузить свой образ, отправьте POST запрос в &#x60;/api/v1/images/{image_id}&#x60;, отправив файл как &#x60;multipart/form-data&#x60;, указав имя файла в заголовке &#x60;Content-Disposition&#x60;.   Перед загрузкой, нужно создать образ используя POST &#x60;/api/v1/images&#x60;, указав параметры &#x60;location&#x60;, &#x60;os&#x60;   Тело ответа будет содержать объект JSON с информацией о загруженном образе.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void uploadImageTest() throws ApiException {
        String imageId = null;
        String contentDisposition = null;
        UploadImage200Response response = api.uploadImage(imageId, contentDisposition);
        // TODO: test validations
    }

}
