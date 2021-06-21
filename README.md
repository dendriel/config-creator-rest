# Config Creator Rest Service
Spring boot application which allows to save and retrieve resources from MongoDB.

This is part of the ``Config Creator`` project. It provides access to template and resources API.

## Template API

The template API allows performing CRUD actions over templates.

- Create a template:
    - Request: POST ``http://localhost/template``
    - Payload (body): ``{ "data": "{\"name\": \"Dendriel\"}" }``
      - A JSON object with a String data field that will be stored as a JSON object.
    - Response:
        - Success: HTTP 201 - Raw response content: \<template id\>. E.g.: ``60d070b1e2e3b75574505941``
- Replace a template:
    - Request: PUT ``http://localhost``
    - Payload (body): ``{"id": "60d070b1e2e3b75574505941", "data": "{\"name\": \"Dendriel Rozsantares\"}" }``
        - "id" must be provided.
        - Success: HTTP 204
        - Failure: HTTP 404 - if not found
- Delete a template (hard deletion):
    - Request: DELETE ``http://localhost/template/{id}``
    - Success: HTTP 204
    - Failure: HTTP 404 - if not found
- Get a template:
    - Request: GET ``http://localhost/{id}``
    - Success: HTTP 200 - JSON response content: ``{"id": "60d070b1e2e3b75574505941", "data": "{\"name\": \"Dendriel Rozsantares\"}" }``
    - Failure: HTTP 404 - if not found
- Get many templates:
    - Request: GET ``http://localhost?offset=0&limit=10``
        - Query Parameters: offset = results offset; limit = count of results after offset to retrieve
    - Success: HTTP 200 - JSON response content: ``[{"id": "60d070b1e2e3b75574505941", "data": "{\"name\": \"Dendriel Rozsantares\"}" }, {...}, {...}, ...]``
- Count template:
    - Request: GET ``http://localhost/template/count``
    - Success: HTTP 200 - Raw response content: \<number of templates\>. E.g.: ``18``


## Dependencies
This service depends on ``NPC Data Manager Auth`` service for authentication and on MongoDB for storage.

### NPC Data Manager Auth (npc-data-manager-auth)

Configure the authentication service host directly in the application.yml or via environment variable ``AUTH_URL``:

```yaml
auth:
  service:
    url: ${AUTH_URL:http://localhost:8080}
```

### MongoDB

Configure MongoDB connection in the ``mongo.properties`` configuration file or via environment variable ``MONGO_DB_HOST``,
``MONGO_DB_PORT``, ``MONGO_DB_NAME``, ``MONGO_DB_USER`` and ``MONGO_DB_PASS``:

```properties
mongo.db.host=${MONGO_DB_HOST:localhost}
mongo.db.port=${MONGO_DB_PORT:27017}
mongo.db.name=${MONGO_DB_NAME:config_creator}
mongo.db.user=${MONGO_DB_USER:root}
mongo.db.pass=${MONGO_DB_PASS:pass}
```
