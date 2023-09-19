package org.interview.review01;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
@ToString
public class ManagerDataByIdResponse implements Serializable {
    private String id;
    private String addInfo;
    private String fullName;
    private String email;
}
