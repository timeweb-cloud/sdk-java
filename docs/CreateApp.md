

# CreateApp


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**providerId** | **UUID** | ID провайдера. |  |
|**type** | [**TypeEnum**](#TypeEnum) | Тип приложения. |  |
|**repositoryId** | **UUID** | ID репозитория. |  |
|**buildCmd** | **String** | Команда сборки приложения. |  |
|**envs** | **Object** | Переменные окружения приложения. Объект с ключами и значениями типа string. |  [optional] |
|**branchName** | **String** | Название ветки репозитория из которой необходимо собрать приложение. |  |
|**isAutoDeploy** | **Boolean** | Автоматический деплой. |  |
|**commitSha** | **String** | Хэш коммита из которого необходимо собрать приложение. |  |
|**name** | **String** | Имя приложения. |  |
|**comment** | **String** | Комментарий к приложению. |  |
|**presetId** | **BigDecimal** | ID тарифа. |  |
|**envVersion** | **String** | Версия окружения. |  [optional] |
|**framework** | **Frameworks** |  |  |
|**indexDir** | **String** | Путь к директории с индексным файлом. Обязателен для приложений &#x60;type: frontend&#x60;. Не используется для приложений &#x60;type: backend&#x60;. Значение всегда должно начинаться с &#x60;/&#x60;. |  [optional] |
|**runCmd** | **String** | Команда для запуска приложения. Обязательна для приложений &#x60;type: backend&#x60;. Не используется для приложений &#x60;type: frontend&#x60;. |  [optional] |



## Enum: TypeEnum

| Name | Value |
|---- | -----|
| FRONTEND | &quot;frontend&quot; |
| BACKEND | &quot;backend&quot; |



