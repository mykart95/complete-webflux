package com.webfluxs.sec09.dto;

import java.util.UUID;

public record UploadResponse(UUID confirmationId, Long productCount) {
}
