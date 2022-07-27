package com.yanggc.controller.test;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Description:
 *
 * @author: YangGC
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Staff {
    private Long staffNo;
    private String name;
    private Integer age;
}
