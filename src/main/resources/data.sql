insert into PAYMENTS (ID, PAYMENTTYPE, AMOUNT, DEBTORIBAN, CREDITORIBAN, CURRENCY, BIC, DETAILS, CANCELLED, CREATED_BY,
                           CREATED_AT, UPDATED_BY, UPDATED_AT)
values (1, '1', 500, 'EE3434242343', 'EE131231231231', 'EUR', null, 'payment details', false, 'init', CURRENT_TIMESTAMP(), null,
        null);

insert into PAYMENTS (ID, PAYMENTTYPE, AMOUNT, DEBTORIBAN, CREDITORIBAN, CURRENCY, BIC, DETAILS, CANCELLED, CREATED_BY,
                           CREATED_AT, UPDATED_BY, UPDATED_AT)
values (2, '1', 123, 'EE14121231', 'LV3434234234', 'USD', 'TESTBIC', null, false, 'init', CURRENT_TIMESTAMP(), null,
        null);
