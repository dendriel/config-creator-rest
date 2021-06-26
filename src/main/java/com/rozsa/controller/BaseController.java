package com.rozsa.controller;

import com.rozsa.business.BaseBusiness;
import com.rozsa.controller.mapper.BaseMapper;
import com.rozsa.model.BaseModel;
import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
public class BaseController<
        TModel extends BaseModel,
        TDto,
        TBusiness extends BaseBusiness<TModel>,
        TMapper extends BaseMapper<TModel, TDto>
    > {

    private final TBusiness business;
    private final TMapper mapper;

    @GetMapping("/{id}")
    public ResponseEntity<TDto> get(@PathVariable("id") ObjectId id) {
        TModel data = business.find(id);
        if (data == null) {
            return ResponseEntity.notFound().build();
        }

        TDto dto = mapper.toDto(data);
        return ResponseEntity.ok().body(dto);
    }

    @GetMapping("/all")
    public List<TDto> getAll(@RequestParam("offset") int offset, @RequestParam("limit") int limit) {
        List<TModel> templates = business.findAll(offset, limit);
        return mapper.toDtos(templates);
    }

    @GetMapping("/count")
    public long count() {
        return business.count();
    }

    @PostMapping
    public ResponseEntity<String> create(@RequestBody TDto templateDto) {
        TModel data = mapper.fromDto(templateDto);
        data = business.create(data);

        return ResponseEntity.status(HttpStatus.CREATED).body(data.getId().toString());
    }

    @PutMapping
    public ResponseEntity<Void> update(@RequestBody TDto templateDto) {
        TModel data = mapper.fromDto(templateDto);

        if (business.update(data) == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remove(@PathVariable("id") ObjectId id) {
        if (business.remove(id)) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
