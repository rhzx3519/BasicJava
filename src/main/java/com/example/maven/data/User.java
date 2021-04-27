package com.example.maven.data;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author ZhengHao Lou
 * @date 2020/05/12
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class User implements Comparable<User> {
    private Integer id;
    private String name;
    private String password;
    private BigDecimal totalQty;

    @Override
    public int compareTo(User other) {
        return this.name.compareTo(other.getName());
    }

    public static void main(String[] args) {
        User user = new User();
        user.setName("Jim");
        Integer i = 12;
        log.info("{}", i.equals(user.getId()));
    }
}
