package com.parting.dippin.domain.member.service;

import java.util.List;
import java.util.Random;
import org.springframework.stereotype.Service;

@Service
public class NicknameGeneratorService {

    private final List<String> adjectives = List.of(
        "용감한", "귀여운", "똑똑한", "빠른", "천천히 걷는", "조용한", "활기찬", "느긋한"
    );

    private final List<String> animals = List.of(
        "호랑이", "여우", "펭귄", "토끼", "강아지", "고양이", "독수리", "곰", "늑대", "사슴"
    );

    private final Random random = new Random();

    public String generate() {

        String adjective = adjectives.get(random.nextInt(adjectives.size()));
        String animal = animals.get(random.nextInt(animals.size()));
        int randomNumber = random.nextInt(9000) + 1000;

        return adjective + animal + randomNumber;
    }
}
