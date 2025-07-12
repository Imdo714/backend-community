package com.back_community.api.studyLog.category.domain.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StudyLogCategory {

    JAVA("자바"),
    CODING_TEST("코딩테스트"),
    PYTHON("파이썬")
    ;

    private final String text;

}
