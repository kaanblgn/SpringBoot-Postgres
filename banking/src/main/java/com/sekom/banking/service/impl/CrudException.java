package com.sekom.banking.service.impl;

import com.sekom.banking.enums.EntityType;

public class CrudException extends RuntimeException {

    public CrudException(String message) {
        super(message);
    }
    public CrudException(String message, Throwable cause) {
        super(message, cause);
    }

    public static CrudException entityNotFound(EntityType entityType) {
        return new CrudException(String.format("%s not found", entityType.getTypeName()));
    }
    public static CrudException entityNotFound(EntityType entityType, String entityId) {
        return new CrudException(String.format("%s not found with ID: %s", entityType.getTypeName(), entityId));
    }

    public static CrudException entitiesNotFound(EntityType entityType) {
        return new CrudException(String.format("No %s were found", entityType.getTypeName()));
    }

    public static CrudException createEntityFailed(EntityType entityType, Throwable cause) {
        String message = String.format("Failed to create %s", entityType.getTypeName());
        return new CrudException(message, cause);
    }

    public static CrudException updateEntityFailed(EntityType entityType, String entityId, Throwable cause) {
        String message = String.format("Failed to update %s with ID %s", entityType.getTypeName(), entityId);
        return new CrudException(message, cause);
    }

    public static CrudException updateEntityFailedDuplicated(EntityType entityType, String entityId) {
        String message = String.format("Failed to update %s with ID %s because duplicated input", entityType.getTypeName(), entityId);
        return new CrudException(message);
    }

    public static CrudException deleteEntityFailed(EntityType entityType, String entityId, Throwable cause) {
        String message = String.format("Failed to delete %s with ID %s", entityType.getTypeName(), entityId);
        return new CrudException(message, cause);
    }
}
