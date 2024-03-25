package com.mangadex.lab2.exceptions;

import java.util.Date;

public record ErrorDetails(Date date, String message, String details) {
}
