package io.github.brunorsch.sicredi.sessao.votacao.testutils;


import static org.apache.commons.lang3.RandomUtils.nextLong;

import org.jeasy.random.EasyRandom;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Random {
    private static final EasyRandom EASY_RANDOM = new EasyRandom();

    public static <T> T obj(Class<T> clazz) {
        EASY_RANDOM.setSeed(nextLong());
        return EASY_RANDOM.nextObject(clazz);
    }

}