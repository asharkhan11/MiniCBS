package in.banking.cbs.action_service.controller;

import in.banking.cbs.action_service.DTO.BranchDto;
import in.banking.cbs.action_service.entity.Branch;
import in.banking.cbs.action_service.message.Response;
import in.banking.cbs.action_service.service.BranchService;
import in.banking.cbs.action_service.utility.MapObject;
import in.banking.cbs.action_service.utility.ResponseStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/branch")
public class BranchController {


    @Autowired
    private BranchService branchService;

    @PostMapping
    public ResponseEntity<Response<Branch>> createBranch(@RequestBody BranchDto branchDto) {

        Branch bank = branchService.createBranch(branchDto);

        Response<Branch> response = Response.<Branch>builder()
                .status(ResponseStatus.CREATED)
                .message("branch created successfully")
                .data(bank)
                .build();

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{branchId}")
    public ResponseEntity<Response<Branch>> updateBranch(@PathVariable int branchId, @RequestBody BranchDto branchDto) {
        Branch updatedBranch = branchService.updateBranch(branchId, branchDto);

        Response<Branch> response = Response.<Branch>builder()
                .status(ResponseStatus.UPDATED)
                .message("Branch updated successfully")
                .data(updatedBranch)
                .build();

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{branchId}")
    public ResponseEntity<Response<Void>> deleteBranch(@PathVariable int branchId) {
        branchService.deleteBranch(branchId);

        Response<Void> response = Response.<Void>builder()
                .status(ResponseStatus.DELETED)
                .message("Branch deleted successfully")
                .build();

        return ResponseEntity.ok(response);
    }
}
