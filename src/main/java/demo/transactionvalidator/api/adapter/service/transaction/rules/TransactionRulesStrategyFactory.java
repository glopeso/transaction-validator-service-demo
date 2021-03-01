package demo.transactionvalidator.api.adapter.service.transaction.rules;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import demo.transactionvalidator.api.domain.transaction.TransactionType;
import demo.transactionvalidator.api.port.transaction.TransactionValidationRulePort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TransactionRulesStrategyFactory {

    private Map<TransactionType, TransactionValidationRulePort> strategies;

    @Autowired
    public TransactionRulesStrategyFactory (Set<TransactionValidationRulePort> strategySet) {
        createStrategy(strategySet);
    }

    public TransactionValidationRulePort findStrategy (TransactionType strategyName) {
        return strategies.get(strategyName);
    }

    private void createStrategy (Set<TransactionValidationRulePort> strategySet) {
        strategies = new HashMap<TransactionType, TransactionValidationRulePort>();
        strategySet.forEach(
                strategy -> strategies.put(strategy.getTransactionType(), strategy));
    }

}
