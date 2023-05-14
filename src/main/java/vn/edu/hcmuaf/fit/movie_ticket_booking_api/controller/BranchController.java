package vn.edu.hcmuaf.fit.movie_ticket_booking_api.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.branch.*;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.exception.BaseException;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.handler.response.HttpResponse;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.handler.response.HttpResponseSuccess;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.service.branch.BranchService;

import java.util.List;

@RestController
@RequestMapping("/branch")
public class BranchController {
    private final BranchService branchService;

    @Autowired
    public BranchController(BranchService branchService) {
        this.branchService = branchService;
    }

    @RequestMapping("/search")
    public ResponseEntity<HttpResponse> getAllBranches(@RequestBody BranchSearch search) {
        List<BranchDto> branchDtoList = branchService.getAllBranches(search);
        return ResponseEntity.ok(HttpResponseSuccess.success(branchDtoList).build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<HttpResponse> getBranchById(@PathVariable("id") Long id) throws BaseException {
        BranchDto branchDto = branchService.getBranchById(id);
        return ResponseEntity.ok(HttpResponseSuccess.success(branchDto).build());
    }

    @PostMapping("/create")
    public ResponseEntity<HttpResponse> createBranch(@RequestBody @Valid BranchCreate branchCreate) throws BaseException {
        BranchDto branchDto = branchService.createBranch(branchCreate);
        return ResponseEntity.ok(HttpResponseSuccess.success(branchDto).build());
    }

    @PutMapping("/update")
    public ResponseEntity<HttpResponse> updateBranch(@RequestBody @Valid BranchUpdate branchUpdate) throws BaseException {
        BranchDto updatedBranch = branchService.updateBranch(branchUpdate);
        return ResponseEntity.ok(HttpResponseSuccess.success(updatedBranch).build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpResponse> deleteBranch(@PathVariable("id") Long id) throws BaseException {
        branchService.deleteBranch(id);
        return ResponseEntity.ok(HttpResponseSuccess.success().build());
    }
}
