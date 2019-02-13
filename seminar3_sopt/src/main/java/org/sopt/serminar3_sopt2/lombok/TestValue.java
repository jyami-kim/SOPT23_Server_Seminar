package org.sopt.serminar3_sopt2.lombok;

import lombok.AccessLevel;
import lombok.Value;
import lombok.experimental.NonFinal;
import lombok.experimental.Wither;

@Value
public class TestValue {
    @Wither(AccessLevel.PROTECTED)
    private int userIdx;
    private String name;
    @NonFinal
    private String email;
}
