

# AppV2Provider


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**id** | **String** | ID провайдера Git. Для готового Docker-образа — пустая строка. |  |
|**type** | [**TypeEnum**](#TypeEnum) | Тип провайдера исходного кода или образа. |  |



## Enum: TypeEnum

| Name | Value |
|---- | -----|
| GITHUB | &quot;github&quot; |
| GITLAB | &quot;gitlab&quot; |
| BITBUCKET | &quot;bitbucket&quot; |
| GIT | &quot;git&quot; |
| DOCKERHUB | &quot;dockerhub&quot; |



