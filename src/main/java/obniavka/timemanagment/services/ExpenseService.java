package obniavka.timemanagment.services;

import obniavka.timemanagment.data.Expense;
import obniavka.timemanagment.data.Receipt;
import obniavka.timemanagment.domain.AssignmentDto;
import obniavka.timemanagment.domain.ExpenseDto;
import obniavka.timemanagment.domain.ReceiptDto;
import obniavka.timemanagment.domain.UserDto;
import obniavka.timemanagment.repository.AssignmentRepository;
import obniavka.timemanagment.repository.ExpenseRepository;
import obniavka.timemanagment.repository.ReceiptRepository;
import obniavka.timemanagment.repository.UserRepository;
import obniavka.timemanagment.utils.ExpenseMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ExpenseService {
    private final ExpenseRepository expenseRepository;
    private final ReceiptRepository receiptRepository;
    private final AssignmentRepository assignmentRepository;
    private final UserRepository userRepository;

    public ExpenseService(ExpenseRepository expenseRepository, ReceiptRepository receiptRepository, AssignmentRepository assignmentRepository, UserRepository userRepository) {
        this.expenseRepository = expenseRepository;
        this.receiptRepository = receiptRepository;
        this.assignmentRepository = assignmentRepository;
        this.userRepository = userRepository;
    }

    ExpenseMapper mapper = Mappers.getMapper(ExpenseMapper.class);

    @Transactional
    public ExpenseDto persistExpenseInDb(final ExpenseDto expenseDto, final List<ReceiptDto> receiptDtos){
        Expense expense = mapper.map(expenseDto);

        Set<Receipt> receipts = new HashSet<>();
        for (ReceiptDto receiptDto : receiptDtos) {
            Receipt receipt = new Receipt();
            receipt.setReceipt_url(receiptDto.getReceipt_url());
            receipt.setFileName(receiptDto.getFileName());
            receipt.setExpense(expense);
            receipts.add(receipt);
        }

        expense.setReceipts(receipts);

        Expense savedExpense = expenseRepository.save(expense);

        return mapper.map(savedExpense);
    }

    @Transactional
    public void deleteExpense(Long expenseId) {
        Expense expense = expenseRepository.getById(expenseId);
        Set<Receipt> receipts = expense.getReceipts();

        for (Receipt receipt:receipts){
            receiptRepository.deleteById(receipt.getId());
        }

        userRepository.findById(expense.getUser().getId()).get().getExpenses().remove(expense);
        assignmentRepository.findById(expense.getAssignment().getId()).get().getExpenses().remove(expense);
        expenseRepository.deleteById(expenseId);

    }

    public Page<ExpenseDto> getAllExpensesByUser(final UserDto userDto, final Pageable pageable){
        return expenseRepository.findByUser(mapper.map(userDto), pageable).map(mapper::map);
    }

    public Page<ExpenseDto> fetchAllUnprocessedExpenses(Pageable pageable){
        return expenseRepository.findUnprocessedExpenses(pageable).map(mapper::map);
    }

    public List<ExpenseDto> fetchAllExpensesById(List<Long> ids){
        return mapper.map(expenseRepository.findAllById(ids));
    }

    public Double calculateTotalSum(List<ExpenseDto> expenses){
        return expenses.stream().mapToDouble(ExpenseDto::getPrice).sum();
    }

    public void acceptExpenses(List<ExpenseDto> expenses){
        Expense expense;

        for (ExpenseDto exp : expenses){
            expense = expenseRepository.findById(exp.getId()).get();
            expense.setAccepted(true);
            expenseRepository.save(expense);
        }
    }

}
