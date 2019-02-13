package org.sopt.serminar3_sopt2.lombok;


public class Main {

    public static void main(String args[]){

        TestData testData = new TestData();
        testData.getEmail();
        testData.setUserIdx(0);
        testData.toString();

        TestGetterSetter testGetterSetter = new TestGetterSetter();
        testGetterSetter.setEmail("mor2222@naver.com");
        testGetterSetter.getUserIdx();

        TestAllArgsConstructor testAllArgsConstructor = new TestAllArgsConstructor(1, "김민정", "mor2222@naver.com");

        TestNonNull testNonNull = new TestNonNull();

        TestRequiredArgsConstructor testRequiredArgsConstructor = new TestRequiredArgsConstructor("김민정", "mor2222@naver.com");

        Testbuilder.TestbuilderBuilder builder = new Testbuilder.TestbuilderBuilder();

        Testbuilder.builder()
                .userIdx(1)
                .name("김민정")
                .email("mor2222@naver.com")
                .build();

        TestValue testValue = new TestValue(0, "김민정", "mor2222@naver.com");
        TestValue testValue1 = testValue.withUserIdx(1);
    }
}
