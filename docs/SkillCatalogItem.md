

# SkillCatalogItem

Навык из каталога Timeweb Cloud

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**id** | **Integer** | ID навыка в каталоге |  |
|**key** | **String** | Уникальный ключ навыка в каталоге |  |
|**category** | [**CategoryEnum**](#CategoryEnum) | Назначение: &#x60;communication&#x60; — манера общения, &#x60;support&#x60; — регламенты обслуживания, &#x60;sales&#x60; — продажи, &#x60;content&#x60; — документы и база знаний, &#x60;safety&#x60; — ограничения и безопасность |  |
|**name** | **String** | Название навыка |  |
|**description** | **String** | Краткое описание навыка |  |
|**whenToUse** | **String** | Когда применять навык |  |
|**instruction** | **String** | Полная инструкция навыка |  |
|**version** | **Integer** | Версия навыка в каталоге |  |
|**isInstalled** | **Boolean** | Навык уже установлен в аккаунте |  |
|**installedSkillId** | **Integer** | ID установленного навыка в аккаунте |  |
|**isModified** | **Boolean** | Установленный навык изменялся владельцем |  |
|**hasCatalogUpdate** | **Boolean** | В каталоге есть версия новее установленной |  |



## Enum: CategoryEnum

| Name | Value |
|---- | -----|
| COMMUNICATION | &quot;communication&quot; |
| SUPPORT | &quot;support&quot; |
| SALES | &quot;sales&quot; |
| CONTENT | &quot;content&quot; |
| SAFETY | &quot;safety&quot; |



