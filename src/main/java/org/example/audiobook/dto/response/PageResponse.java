package org.example.audiobook.dto.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PageResponse<T> {
    private int page;

    private int size;

    private long totalElements;

    private int totalPages;

    private List<T> content;
}