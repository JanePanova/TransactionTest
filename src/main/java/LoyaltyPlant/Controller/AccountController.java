package LoyaltyPlant.Controller;

import LoyaltyPlant.ApplicationException;
import LoyaltyPlant.Model.Account;
import LoyaltyPlant.Model.AccountWithWallet;
import LoyaltyPlant.Service.AccountService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by JaneJava on 5/3/15.
 */
@RestController
@RequestMapping(value = {"api/account"})
public class AccountController {

    static final Logger logger = Logger.getLogger(AccountController.class);

    @Autowired
    AccountService accountService;

    @RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
    public ResponseEntity<List<Account>> getAllAccounts(
            @RequestParam(value = "start", required = false, defaultValue = "-1")
            int start) {
        return ResponseEntity.ok(accountService.getAll(start));
    }

    @RequestMapping(value = {"/{id}"}, method = RequestMethod.GET)
    public ResponseEntity<AccountWithWallet> getAccount(@PathVariable int id) {
        return ResponseEntity.ok(accountService.getAccountWithWallet(id));
    }

    @RequestMapping(value = {"", "/"}, method = RequestMethod.POST)
    public ResponseEntity<Void> createAccount(@RequestBody Account account) {
        accountService.put(account);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = {"/{id}"}, method = RequestMethod.PUT)
    public ResponseEntity<Void> updateAccount(@PathVariable Integer id, @RequestBody Account account) {
        account.setId(id);
        accountService.update(account);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = {"/{id}"}, method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteAccount(@PathVariable int id) {
        accountService.delete(id);
        return ResponseEntity.ok().build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ApplicationException.class)
    public String handleApplicationException(ApplicationException ex) {
        return "{\"message\":\"" + ex.getMessage() + "\"}";
    }
}
