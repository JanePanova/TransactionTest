package LoyaltyPlant.Controller;

import LoyaltyPlant.ApplicationException;
import LoyaltyPlant.DAO.TransactionDAO;
import LoyaltyPlant.Model.Transaction;
import LoyaltyPlant.Model.Wallet;
import LoyaltyPlant.Service.TransactionService;
import com.fasterxml.jackson.core.JsonFactory;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by JaneJava on 5/3/15.
 */
@RestController
@RequestMapping(value = "api/transaction")
public class TransactionController {
    static final Logger logger = Logger.getLogger(TransactionController.class);

    @Autowired
    TransactionService transactionService;

    @RequestMapping(value = "/add/{id}", method = RequestMethod.POST)
    public ResponseEntity<Void> addMoney(@PathVariable int id, @RequestBody Transaction transaction) {
        transactionService.addMoney(id, transaction.getMoney());
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/withdraw/{id}", method = RequestMethod.POST)
    public ResponseEntity<Void> withdrawMoney(@PathVariable int id, @RequestBody Transaction transaction) {
        if (!transactionService.withdrawMoney(id, transaction.getMoney())) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = {"", "/"}, method = RequestMethod.POST)
    public ResponseEntity<Void> makeTransaction(@RequestBody Transaction transaction) {
        logger.info(transaction.toString());
        transactionService.makeTransaction(transaction.getFrom(), transaction.getTo(), transaction.getMoney());
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
    public ResponseEntity<List<Transaction>> getAllTransactions(
            @RequestParam(value = "start", defaultValue = "-1", required = false) int startFrom) throws ApplicationException {
        return ResponseEntity.ok(transactionService.getAll(startFrom));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ApplicationException.class)
    public String handleApplicationException(ApplicationException ex) {
        return "{\"message\":\"" + ex.getMessage() + "\"}";
    }
}
