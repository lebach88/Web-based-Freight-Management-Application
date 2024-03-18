package com.dev.backendspringboot.service;

import java.time.ZoneId;
import java.time.ZonedDateTime;

public class Time {
    public static final ZonedDateTime NOW = ZonedDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh"));
}
