

# DbPrivilegeGroup

Привилегия, доступная для кластера базы данных, и её группа доступа

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**privilege** | **PropertiesMysql** |  |  |
|**group** | [**GroupEnum**](#GroupEnum) | Группа доступа, к которой относится привилегия: &#x60;GLOBAL&#x60; — привилегии уровня всего кластера, &#x60;DATA&#x60; — доступ к данным, &#x60;STRUCTURE&#x60; — изменение структуры, &#x60;OTHERS&#x60; — прочие привилегии. |  |



## Enum: GroupEnum

| Name | Value |
|---- | -----|
| GLOBAL | &quot;GLOBAL&quot; |
| DATA | &quot;DATA&quot; |
| STRUCTURE | &quot;STRUCTURE&quot; |
| OTHERS | &quot;OTHERS&quot; |



