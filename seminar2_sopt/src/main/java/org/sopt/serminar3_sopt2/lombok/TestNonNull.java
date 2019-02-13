package org.sopt.serminar3_sopt2.lombok;

import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public class TestNonNull {
    private int userIdx;
    @NonNull
    private String name;
    private String email;

    public TestNonNull(){
        this.name = null;
    }
}
