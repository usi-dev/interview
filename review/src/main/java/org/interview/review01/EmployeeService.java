package org.interview.review01;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class EmployeeService {

    private final static List<String> NAMES = List.of("Rick", "Morty", "Homer", "Marge", "Bart", "Lisa");
    private final Random random = new Random(System.nanoTime());


    /**
     * Поиск менеджера по id
     *
     * @return информация о менеджере
     */
    Optional<ManagerData> getManagerData(String mng) {
        if (mng.equalsIgnoreCase("NOTFOUND")) {
            throw new IllegalArgumentException("Error");
        }

        int index = random.nextInt(NAMES.size());
        String name = NAMES.get(index);

//        delay(mng);

        return Optional.of(ManagerData.builder().fullName(name).email(name + "@gmail.com").build());
    }


    private void delay(String mng) {
        int delay = 0;
        if (mng.equalsIgnoreCase("manager1")) {
            delay = 6000;
        }
        if (mng.equalsIgnoreCase("manager2")) {
            delay = 2000;
        }
        if (mng.equalsIgnoreCase("manager3")) {
            delay = 3000;
        }
        if (mng.equalsIgnoreCase("manager4")) {
            delay = 4000;
        }

        if (delay != 0) {
            try {
                TimeUnit.MILLISECONDS.sleep(delay);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
