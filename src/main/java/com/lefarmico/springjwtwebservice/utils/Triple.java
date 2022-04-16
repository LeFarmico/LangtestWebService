package com.lefarmico.springjwtwebservice.utils;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor
@EqualsAndHashCode
@Getter
public class Triple<F, S, T> {
    private F first;
    private S second;
    private T third;
}
