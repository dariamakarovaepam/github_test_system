package com.epam.testsystem.github.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * github_test
 * Created on 05.07.17.
 */

@Data
@Builder
public class Task {
    private long id;
    private long userId;
    private LocalDateTime registerTime;
    private boolean successful;
    private TaskStatus status;
}
