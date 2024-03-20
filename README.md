
# REST application
## Сервис использует MangaDexAPI для получения информации о манге.
**[MangaDexAPI](https://api.mangadex.org/docs/)**<br>
## Sringboot + JPA + Hibernate
## DB - PostgreSQL
# manga <-> genre many to many
# manga <-> author many to one, bidirectional

# Documentation
## /manga
### GET /manga/list - вся манга в базе данных
### GET /manga/find/{id} - получение информации о манге с определённым id
### POST /manga/save - добавление новой манги в базу данных
### PUT /manga/update - обновление информации о конкретной манге
### DELETE /manga/delete/{id} - удаление манги по определённому id
### GET /manga/manga_dex?titleName=.. - сохранение в базу данных манги по названию с MangaDexAPI

## /genre
### GET /genre/list - получить все жанры 
### GET /genre/find/{id} - получить жанр по id
### GET /genre/update - обновление всех жанров
### POST /genre/save - добавить жанр
### PUT /genre/update - обновить жанр
### DELETE /genre/{id} - удалить жанр по id

## /author
### GET /author/list - получить всех авторов
### GET /author/find/{authorId} - получить автора по id
### POST /author/save - добавить автора
### PUT /author/update - обновить автора
### DELETE /author/delete/{authorId} - удалить автора по id
