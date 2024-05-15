package base.api.book.controller;

import base.api.book.dto.CopyDto;
import base.api.book.service.CopyService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:8080"})
@RequestMapping("api/copy")
public class CopyController {
    private final CopyService copyService;

    public CopyController(CopyService copyService) {
        this.copyService = copyService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CopyDto> getCopyById (@PathVariable Long id) {
        CopyDto copyDto = copyService.getCopyById(id);
        if(copyDto == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(copyDto);
    }

    @GetMapping("/search/{id}")
    public ResponseEntity<List<CopyDto>> getCopyByOwnerId (@PathVariable Long id) {
        return ResponseEntity.ok(copyService.getCopyByOwnerId(id));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<CopyDto>> findAllCopies(Pageable pageable) {
        return ResponseEntity.ok().body(copyService.findAllCopies(pageable));
    }

    @GetMapping
    public ResponseEntity<List<CopyDto>> getAllCopy () {
        return ResponseEntity.ok(copyService.getAllCopy());
    }

    @PostMapping
    public ResponseEntity<CopyDto> createCopy (@RequestBody CopyDto copyDto) {
        return ResponseEntity.ok(copyService.createCopy(copyDto));
    }

    @PutMapping ("/{id}")
    public ResponseEntity<CopyDto> updateCopy (@PathVariable Long id, @RequestBody CopyDto copyDto) {
        return ResponseEntity.ok(copyService.updateCopy(copyDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCopy (@PathVariable Long id) {
        copyService.deleteCopy(id);
        return ResponseEntity.noContent().build();
    }
}
