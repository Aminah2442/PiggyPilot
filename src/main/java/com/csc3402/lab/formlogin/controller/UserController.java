package com.csc3402.lab.formlogin.controller;

import com.csc3402.lab.formlogin.model.Group;
import com.csc3402.lab.formlogin.model.Transaction;
import com.csc3402.lab.formlogin.model.User;
import com.csc3402.lab.formlogin.repository.GroupRepository;
import com.csc3402.lab.formlogin.repository.UserRepository;
import com.csc3402.lab.formlogin.service.GroupService;
import com.csc3402.lab.formlogin.service.TransactionService;
import com.csc3402.lab.formlogin.service.UserService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user/")
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final GroupService groupService;
    private final TransactionService transactionService;

    private final GroupRepository groupRepository;

    public UserController(UserService userService, UserRepository userRepository, TransactionService transactionService, GroupService groupService, GroupRepository groupRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.transactionService = transactionService;
        this.groupService = groupService;
        this.groupRepository = groupRepository;
    }

    //    ---------     DASHBOARD     ----------     //
    @GetMapping("/")
    public String dashboard() {
        return "dashboard";
    }



    //    ---------     PROFILE     ----------     //
    @GetMapping("/profile")
    public String userProfile(Model model) {

        // Get the logged-in user's email from the security context
        String email = getCurrentUserEmail();

        // Find the user by email
        User user = userRepository.findByEmail(email);
        if (user == null) {
            return "redirect:/login?logout"; // Updating email logs user out
        }
        model.addAttribute("user", user);
        return "profile";
    }

    @PostMapping("/profile/update")
    public String updateUserProfile(@Valid @ModelAttribute("user") User user, BindingResult result, Model model) {

        // Check if the new email already exists in the database
        User existingUserByEmail = userRepository.findByEmail(user.getEmail());
        if (existingUserByEmail != null && !existingUserByEmail.getUserId().equals(user.getUserId())) {
            return "redirect:/user/profile?error";
        }
        if (result.hasErrors()) {
            model.addAttribute("user", user);
            return "profile";
        }

        // Update user details in the database
        userService.updateUser(user);
        return "redirect:/user/profile?success";
    }

    private String getCurrentUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }

        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else {
            return principal.toString();
        }
    }

    //    ---------     TRANSACTION     ----------     //
    @GetMapping("/transaction")
    public String getTransactions(Model model) {
        String email = getCurrentUserEmail();
        User user = userRepository.findByEmail(email);
        List<Group> groups = groupService.listGroupsByUserId(user.getUserId());
        List<Group> transactionGroup = groupRepository.findByUsers(user);

        List<Transaction> allTransactions = new ArrayList<>();
        for (Group group : transactionGroup) {
            List<Transaction> transactions = transactionService.listTransactionsByBudgetId(group.getBudgetId());
            allTransactions.addAll(transactions);
        }

        model.addAttribute("groups", groups);
        model.addAttribute("transactions", allTransactions);

        Map<Long, Double> budgetLeft = groupService.calculateBudgetLeft(user.getUserId());
        model.addAttribute("budgetLeft", budgetLeft);
        return "transaction";
    }




    //    ---------     BUDGET     ----------     //
    @GetMapping("/budget")
    public String userBudget() {
        return "budget";
    }
}
