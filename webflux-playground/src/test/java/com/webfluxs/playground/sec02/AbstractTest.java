package com.webfluxs.playground.sec02;

import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {
        "sec=sec01"
})
public abstract class AbstractTest {
}
