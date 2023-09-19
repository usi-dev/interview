package org.interview.review01;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Информация о менеджере
 */
@Getter
@Setter
@AllArgsConstructor
@Builder
public class ManagerData {

    /**
     * ФИО сотрудника
     */
    private final String fullName;

    /**
     * Адрес электронной почты
     */
    private final String email;
}
