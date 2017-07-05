package com.epam.testsystem.github.dao;

import com.epam.testsystem.github.model.Task;
import com.epam.testsystem.github.model.TaskStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * github_test
 * Created on 05.07.17.
 */

@Repository
@Transactional(propagation = Propagation.MANDATORY)
@RequiredArgsConstructor
public class TaskDao {
    private static final RowMapper<Task> TASK_ROW_MAPPER = (rs, rowNum) ->
            Task.builder()
                    .id(rs.getLong("id"))
                    .registerTime(rs.getTimestamp("register_time").toLocalDateTime())
                    .status(TaskStatus.valueOf(rs.getString("status")))
                    .successful(rs.getBoolean("successful"))
                    .build();

    private final JdbcTemplate jdbcTemplate;

    public Task add(final long userId) {
        final LocalDateTime registerTime = LocalDateTime.now();
        jdbcTemplate.update(
                "INSERT INTO tasks(user_id, register_time) " +
                        "VALUES(?, ?)",
                userId, registerTime);

        final Integer id = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);

        return Task.builder()
                .id(id)
                .userId(userId)
                .registerTime(registerTime)
                .successful(false)
                .status(TaskStatus.PROGRESS)
                .build();

    }

    public List<Task> findAllInProgress() {
        return jdbcTemplate.query(
                "SELECT * FROM tasks WHERE status = ?",
                new Object[]{TaskStatus.PROGRESS.name()},
                TASK_ROW_MAPPER
        );
    }

    public boolean setResultById(final long userId, final long id, final boolean successful) {
        return jdbcTemplate.update(
                "UPDATE tasks SET successful = ?, status = ? WHERE user_id = ? AND id = ?",
                successful, TaskStatus.CHECKED.name(), userId, id
        ) > 0;
    }
}
