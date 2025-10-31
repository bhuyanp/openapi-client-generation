package io.github.bhuyanp.restapp.common;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class CommonContext {
    private String errorMessage;
    private int httpStatusCode;
}
