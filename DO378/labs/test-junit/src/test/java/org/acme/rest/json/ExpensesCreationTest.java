package org.acme.rest.json;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;

public class ExpensesCreationTest {

    @Inject
    EntityManager entityManager;

    @Transactional
    public void cleanDatabase(){
        entityManager.createQuery("delete from Expense").executeUpdate();
    }
   
    @Test
    public void testCreateExpense() {

    }

}