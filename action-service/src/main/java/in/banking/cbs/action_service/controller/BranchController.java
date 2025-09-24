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

    @PutMapping("/{bankId}")
    public ResponseEntity<Response<Branch>> updateBranch(@PathVariable int bankId, @RequestBody BranchDto branchDto) {
        return null;
    }

    @DeleteMapping("/{bankId}")
    public ResponseEntity<Response<Void>> deleteBranch(@PathVariable int bankId) {
        return null;
    }
    
    
}
