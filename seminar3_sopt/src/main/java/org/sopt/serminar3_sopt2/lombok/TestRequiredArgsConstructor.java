package org.sopt.serminar3_sopt2.lombok;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TestRequiredArgsConstructor {
    private int userIdx;
    @NonNull
    private String name;
    @NonNull
    private String email;
}
