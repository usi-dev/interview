package org.test.sampes.s001;

import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Slf4j
public class Collections001 {

    @Test
    void check() {
        List<User> list = Arrays.asList(
                User.of("Rick", Address.of("Moscow", "Street1")),
                User.of("Morty", Address.of("Piter", "Street2")),
                User.of("Homer", Address.of("Moscow", "Street3")),
                User.of("Marge", Address.of("Piter", "Street4")),
                User.of("Bart", Address.of("Moscow", "Street5")),
                User.of("Lisa", Address.of("Moscow", "Street6"))
        );

        Map<String, Long> result = doWork(list);

        // RESULT: {Piter=2, Moscow=4}
        Assertions.assertEquals(2L, result.get("Piter"));
        Assertions.assertEquals(4L, result.get("Moscow"));
    }

    private static Map<String, Long> doWork(List<User> list) {
        //...
        return Collections.emptyMap();
    }

}

@Value(staticConstructor = "of")
class User {
    String name;
    Address addresses;
}

@Value(staticConstructor = "of")
class Address {
    String city;
    String street;
}