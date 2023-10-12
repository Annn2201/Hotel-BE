package com.doan.apidoan.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
@Builder
@Getter
@Setter
@AllArgsConstructor
public class PagingDTO<T> {
    private T resource;
    private int page;
    private long total;

    public static class PagingDTOBuilder<T>{
        public PagingDTOBuilder fromPage(Page data, Class type) {

            List<?> content = data.getContent();
            if (Objects.nonNull(type)) {
                ModelMapper mapper = new ModelMapper();
                mapper.getConfiguration().setAmbiguityIgnored(true);
                this.resource = (T) content.stream()
                        .map(item -> mapper.map(item, type))
                        .collect(Collectors.toList());
            } else {
                this.resource = (T) content;
            }
            this.page = data.getTotalPages();
            this.total = data.getTotalElements();
            return this;
        }

        public PagingDTOBuilder fromPage(Page data) {
            this.resource = (T) data.getContent();
            this.page = data.getTotalPages();
            this.total = data.getTotalElements();
            return this;
        }
    }
}
