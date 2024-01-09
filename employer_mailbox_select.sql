SELECT 
    mailbox_employer.id_box_emp, 
    users.login AS student_login,
    offers.id_offer, 
    mailbox_employer.mess_type,
    users_login.login AS employer_login,
    offers.title
FROM 
    users
INNER JOIN 
    mailbox_employer ON users.id_user = mailbox_employer.id_stud
INNER JOIN 
    offers ON mailbox_employer.id_offer = offers.id_offer
LEFT JOIN 
    users AS users_login ON offers.id_empl = users_login.id_user
WHERE 
    offers.id_empl IN (1, 2, 3);
