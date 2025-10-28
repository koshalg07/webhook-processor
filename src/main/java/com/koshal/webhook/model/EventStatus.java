package com.koshal.webhook.model;

/*
    represents the processing state of webhook events
 */
public enum EventStatus {
    RECEIVED,
    PROCESSED,
    FAILED,
    DLQ
}
