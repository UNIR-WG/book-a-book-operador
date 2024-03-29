package net.unir.missi.desarrollowebfullstack.bookabook.operador.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.unir.missi.desarrollowebfullstack.bookabook.operador.model.sql.Loan;
import net.unir.missi.desarrollowebfullstack.bookabook.operador.search.SearchCriteria;
import net.unir.missi.desarrollowebfullstack.bookabook.operador.search.SearchOperation;
import net.unir.missi.desarrollowebfullstack.bookabook.operador.search.SearchStatement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
@Slf4j
public class LoanRepository {
     private LoanJpaRepository loanJpaRepository;

     @Autowired
    public LoanRepository(LoanJpaRepository loanJpaRepository) {
        this.loanJpaRepository = loanJpaRepository;
    }

    public List<Loan> findAll() { return loanJpaRepository.findAll(); }
    public Loan findById(Long id) { return loanJpaRepository.findById(id).orElse(null); }
    public List<Loan> findByClientId(Long clientId){ return loanJpaRepository.findByClientId(clientId); }
    public List<Loan> findByBookIdAndIsReturned(Long bookId, boolean isRturned){ return loanJpaRepository.findByBookIdAndIsReturned(bookId, isRturned); }
    public Loan save(Loan loan){ return loanJpaRepository.save(loan); }
    public Loan delete(Loan loan)
    {
        Optional<Loan> l = this.loanJpaRepository.findById(loan.getId());
        loanJpaRepository.delete(loan);
        return l.get();
    }

    public List<Loan> search(Long bookId, Long clientId, LocalDate loanDate, LocalDate returnDate, LocalDate dueDate, Boolean isReturned, Integer renewalCount) {
        SearchCriteria<Loan> spec = new SearchCriteria<>();

        if(bookId != null) {
            if (bookId != null && bookId != 0) {
                spec.add(new SearchStatement("bookId", bookId, SearchOperation.EQUAL));
            }
        }
        if(clientId != null) {
            if (clientId != null && clientId != 0) {
                spec.add(new SearchStatement("clientId", clientId, SearchOperation.EQUAL));
            }
        }
        if(loanDate != null){
            if (loanDate != null) {
                spec.add(new SearchStatement("loanDate", loanDate, SearchOperation.EQUAL));
            }
        }
        if(returnDate != null){
            if (returnDate != null) {
                spec.add(new SearchStatement("returnDate", returnDate, SearchOperation.EQUAL));
            }
        }
        if(dueDate != null){
            if (dueDate != null) {
                spec.add(new SearchStatement("dueDate", dueDate, SearchOperation.EQUAL));
            }
        }
        if(isReturned != null){
            if (isReturned != null) {
                spec.add(new SearchStatement("isReturned", isReturned, SearchOperation.EQUAL));
            }
        }
        if(renewalCount != null){
            if (renewalCount != null) {
                spec.add(new SearchStatement("renewalCount", renewalCount, SearchOperation.EQUAL));
            }
        }

        List<Loan> loanList = loanJpaRepository.findAll(spec);
        return loanList;
    }
}
